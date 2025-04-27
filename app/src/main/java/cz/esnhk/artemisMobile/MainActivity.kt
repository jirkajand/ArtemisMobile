package cz.esnhk.artemisMobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import cz.esnhk.artemisMobile.api.ApiClient
import cz.esnhk.artemisMobile.consts.Routes
import cz.esnhk.artemisMobile.repository.DataStoreManager
import cz.esnhk.artemisMobile.screens.*
import cz.esnhk.artemisMobile.ui.theme.ArtemisMobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @SuppressLint("FlowOperatorInvokedInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin { //inicializace DI
            androidContext(this@MainActivity)
            modules(
                viewModelModule,
                repositoryModule
            ) //nastaveni modulu (ze souboru AppModules)
        }
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val dataStoreManager = remember { DataStoreManager(applicationContext) }
            val isLoggedIn by dataStoreManager.isLoggedInFlow.collectAsState(initial = false)

            // Convert the Flow to a StateFlow
            val isLoggedInFlow = dataStoreManager.isLoggedInFlow.stateIn(
                scope = lifecycleScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )

            MainScreen(navController, isLoggedInFlow, dataStoreManager)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    isLoggedInFlow: StateFlow<Boolean>,
    dataStoreManager: DataStoreManager
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isLoggedIn by isLoggedInFlow.collectAsState(initial = false)

    if (isLoggedIn) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Menu", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                    HorizontalDivider()
                    DrawerItem("Dashboard") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Routes.Dashboard)
                    }
                    DrawerItem("My students") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Routes.MyStudents)
                    }
                    DrawerItem("Events") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Routes.Events)
                    }
                    DrawerItem("Semester info") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Routes.SemesterInfo)
                    }
                }
            }
        ) {
            MainScaffold(navController, drawerState, scope, isLoggedInFlow, dataStoreManager)
        }
    } else {
        MainScaffold(navController, drawerState, scope, isLoggedInFlow, dataStoreManager)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    isLoggedInFlow: StateFlow<Boolean>,
    dataStoreManager: DataStoreManager
) {
    val isLoggedIn by isLoggedInFlow.collectAsState(initial = false)
    Scaffold(
        topBar = {
            if (isLoggedIn) {
                TopAppBar(
                    title = { Text("Artemis mobile legendary") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Navigation(
            navController = navController,
            innerPadding = innerPadding,
            isLoggedInFlow = isLoggedInFlow,
            dataStoreManager = dataStoreManager,
            scope = scope
        )
    }
}


@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    Text(
        text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    )
}

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    isLoggedInFlow: StateFlow<Boolean>,
    dataStoreManager: DataStoreManager,
    scope: CoroutineScope
) {
    val isLoggedIn by isLoggedInFlow.collectAsState(initial = false)
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.Dashboard else Routes.Login,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Routes.Login) {
            LoginScreen(
                onLoginSuccess = {
                    // Launch in a coroutine scope to call the suspend function
                    scope.launch {
                        dataStoreManager.setLoggedIn(true)
                        navController.navigate(Routes.Dashboard) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Routes.Dashboard) {
            if (isLoggedIn) {
                Dashboard(navController)
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.Login) {
                        popUpTo(0)
                    }
                }
            }
        }
        composable(Routes.MyStudents) {
            if (isLoggedIn) {
                MyStudents(navController)
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.Login) {
                        popUpTo(0)
                    }
                }
            }
        }
        composable(Routes.Events) {
            if (isLoggedIn) {
                EventScreen(navController)
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.Login) {
                        popUpTo(0)
                    }
                }
            }
        }
        composable(Routes.SemesterInfo) {
            if (isLoggedIn) {
                SemesterInfo(navController)
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.Login) {
                        popUpTo(0)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {

    val fakeFlow = MutableStateFlow(false)
    ArtemisMobileTheme {
        MainScreen(rememberNavController(), isLoggedInFlow = fakeFlow, DataStoreManager(LocalContext.current))
    }
}
