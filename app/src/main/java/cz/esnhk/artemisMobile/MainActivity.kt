package cz.esnhk.artemisMobile

import android.os.Bundle
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import cz.esnhk.artemisMobile.consts.Routes
import cz.esnhk.artemisMobile.repository.DataStoreManager
import cz.esnhk.artemisMobile.screens.*
import cz.esnhk.artemisMobile.ui.theme.ArtemisMobileTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin { //inicializace DI
            androidContext(this@MainActivity)
            modules(
                viewModelModule
            ) //nastaveni modulu (ze souboru AppModules)
        }
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val dataStoreManager = remember { DataStoreManager(applicationContext) }
            val isLoggedIn by dataStoreManager.isLoggedInFlow.collectAsState(initial = false)

            MainScreen(navController, isLoggedIn)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, isLoggedIn: Boolean) {
    val drawerState = rememberDrawerState(DrawerValue.Closed) //Control whether the drawer (side menu) is open or closed.
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {           // The content of the drawer (side menu)
            ModalDrawerSheet {
                Text(
                    "Menu",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
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
        Scaffold(
            topBar = {
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
        ) { innerPadding ->
            // Your screen content
            Box(modifier = Modifier.padding(innerPadding)) {
                Text("Welcome to app!")
            }
            Navigation(navController = navController, innerPadding = innerPadding, isLoggedIn = isLoggedIn)
        }
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
fun Navigation(navController: NavHostController, innerPadding: PaddingValues, isLoggedIn: Boolean) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val isLoggedIn by dataStoreManager.isLoggedInFlow.collectAsState(initial = false)
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.Dashboard else Routes.Login,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Routes.Login) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Dashboard) { Dashboard(navController) }
        composable(Routes.MyStudents) { MyStudents(navController) }
        composable(Routes.Events) { EventScreen(navController) }
        composable(Routes.SemesterInfo) { SemesterInfo(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ArtemisMobileTheme {
        MainScreen(rememberNavController(), isLoggedIn = false)
    }
}
