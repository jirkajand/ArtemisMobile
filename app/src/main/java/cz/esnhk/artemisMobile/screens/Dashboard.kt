package cz.esnhk.artemisMobile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.esnhk.artemisMobile.items.StudentItem
import cz.esnhk.artemisMobile.repository.StudentRepository


@Composable
fun Dashboard(
    navController: NavController
) {
    val studentList = StudentRepository.getCryptoList()
    Column(modifier = Modifier.padding(16.dp)){
        Text("Dashboard")
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {  // Use LazyColumn
            items(studentList) { student ->
                StudentItem(student, navController)
            }
        }

    }
}
