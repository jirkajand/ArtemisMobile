package cz.esnhk.artemisMobile.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.esnhk.artemisMobile.items.StudentItem
import cz.esnhk.artemisMobile.repository.StudentRepository

@Composable
fun MyStudents(
    navController: NavController
) {
    var selectedSemester by remember { mutableStateOf(0) }
    var isSemesterDropdownExpanded by remember { mutableStateOf(false) }

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


    val studentList = StudentRepository.getStudentByBuddyId(1) //TODO Get current user
    val filteredStudentList = remember(studentList, selectedSemester) {
        if (selectedSemester == 0) {
            studentList
        } else {
            studentList.filter { student ->
                (selectedSemester == 0 || student.semesters.contains(selectedSemester))
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("My Students")
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
        }


        LazyColumn {
            items(filteredStudentList) { student ->
                StudentItem(student, navController)
            }
        }
    }
}