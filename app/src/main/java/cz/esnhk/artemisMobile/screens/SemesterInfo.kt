package cz.esnhk.artemisMobile.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import cz.esnhk.artemisMobile.items.SemesterItem
import cz.esnhk.artemisMobile.repository.SemesterRepository

@Composable
fun SemesterInfo(
    navController: NavController
){
    val semester = SemesterRepository.getCurrentSemester()
    val semesterInfo = semester?.semesterInfo ?: emptyList()
    LazyColumn {
        items(semesterInfo) { semesterInfo ->
            SemesterItem(semesterInfo, navController)
        }
    }
}