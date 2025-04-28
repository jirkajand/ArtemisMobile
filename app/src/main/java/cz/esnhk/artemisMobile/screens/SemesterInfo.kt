package cz.esnhk.artemisMobile.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.entities.Semester
import cz.esnhk.artemisMobile.items.SemesterItem
import cz.esnhk.artemisMobile.viewmodels.SemesterViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun SemesterInfo(
    navController: NavController,
    viewModel: SemesterViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getSemesterList()
    }

    val semesterState by viewModel.semesterList.collectAsState()

    when (semesterState) {
        is ApiResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ApiResult.Success -> {
            val semesters = (semesterState as ApiResult.Success<List<Semester>>).data
            // You probably want to display the *current* semester here:
            val currentSemester = semesters.firstOrNull { it.isCurrent }
            val semesterInfoList = currentSemester?.semesterInfo ?: emptyList()

            LazyColumn {
                items(semesterInfoList) { semesterInfo ->
                    SemesterItem(semesterInfo, navController)
                }
            }
        }
        is ApiResult.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val errorMessage = (semesterState as ApiResult.Error).message
                Text("Error loading semester info: $errorMessage")
            }
        }
        is ApiResult.Idle -> {}
    }
}
