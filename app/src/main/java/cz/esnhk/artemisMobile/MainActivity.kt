package cz.esnhk.artemisMobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import cz.esnhk.artemisMobile.consts.Routes
import cz.esnhk.artemisMobile.ui.theme.ArtemisMobileTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.esnhk.artemisMobile.screens.Dashboard
import cz.esnhk.artemisMobile.screens.MyStudents
import cz.esnhk.artemisMobile.screens.SemesterInfo

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
            MainScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val drawerState =
        rememberDrawerState(DrawerValue.Closed) //Control whether the drawer (side menu) is open or closed.
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
            Navigation(navController = navController, innerPadding = innerPadding)
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
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Routes.Dashboard,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Routes.Dashboard) { Dashboard(navController) }
        composable(Routes.MyStudents) { MyStudents(navController) }
        composable(Routes.Events) { Dashboard(navController) }
        composable(Routes.SemesterInfo) { SemesterInfo(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ArtemisMobileTheme {
        MainScreen(rememberNavController())
    }
}