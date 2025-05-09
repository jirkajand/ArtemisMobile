package cz.esnhk.artemisMobile.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.items.StudentItem
import cz.esnhk.artemisMobile.viewmodels.InternationalStudentViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun Dashboard(
    navController: NavController,
    viewModel: InternationalStudentViewModel = koinViewModel()
) {

    var selectedSemester by remember { mutableStateOf(0) }
    var selectedFaculty by remember { mutableStateOf(0) }
    var isSemesterDropdownExpanded by remember { mutableStateOf(false) }
    var isFacultyDropdownExpanded by remember { mutableStateOf(false) }

    val studentList by viewModel.internationalStudentList.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getStudentList()
    }

    // Sample data for semesters and faculties (replace with your actual data)
    val semesters = mapOf(
        1 to "WS2023",                 //TODO Import from API
        2 to "LS2024",
        3 to "WS2024",
        // ... other semesters
    )
    val faculties = mapOf(
        1 to "FPR",                 //TODO Import from API
        2 to "FCHT",
        3 to "FEKT",
        // ... other faculties
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Dashboard")
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Semester Dropdown
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { isSemesterDropdownExpanded = true }
                    .padding(16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(semesters[selectedSemester] ?: "Select Semester")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Open Semester Dropdown")
            }
            DropdownMenu(
                expanded = isSemesterDropdownExpanded,
                onDismissRequest = { isSemesterDropdownExpanded = false },
            ) {
                semesters.forEach { (semesterId, semesterName) ->
                    DropdownMenuItem(
                        text = { Text(semesterName) },
                        onClick = {
                            selectedSemester = semesterId
                            isSemesterDropdownExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Faculty Dropdown
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { isFacultyDropdownExpanded = true }
                    .padding(16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(faculties[selectedFaculty] ?: "Select Faculty")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Open Faculty Dropdown")
            }
            DropdownMenu(
                expanded = isFacultyDropdownExpanded,
                onDismissRequest = { isFacultyDropdownExpanded = false },
            ) {
                faculties.forEach { (facultyId, facultyName) ->
                    DropdownMenuItem(
                        text = { Text(facultyName) },
                        onClick = {
                            selectedFaculty = facultyId
                            isSemesterDropdownExpanded = false
                        }
                    )
                }
            }
        }
        /*LazyColumn {  // Use LazyColumn
            items(filteredStudentList) { student ->
                StudentItem(student, navController)
            }
        }*/


        when (studentList) {
            is ApiResult.Loading -> {
                // Zobraz indikátor načítání
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            is ApiResult.Success -> {
                val students = (studentList as ApiResult.Success).data
                // Apply filtering to the success data
                val filteredStudents = students.filter { student ->
                    (selectedSemester == 0 || student.semesters.contains(selectedSemester)) &&
                            (selectedFaculty == 0 || student.faculty == selectedFaculty) &&
                            (student.assignedBuddy == null)
                }

                LazyColumn {
                    items(filteredStudents) { student ->
                        StudentItem(student, navController)
                    }
                }
            }
            is ApiResult.Error -> {
                val errorMessage = (studentList as ApiResult.Error).message
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Error: $errorMessage",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            else -> {
                throw Exception("Invalid ApiResult state")
            }
        }
    }
}