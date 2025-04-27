package cz.esnhk.artemisMobile.screens

import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.Icons

import cz.esnhk.artemisMobile.R
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.entities.Event
import cz.esnhk.artemisMobile.viewmodels.EventViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventScreen(
    navController: NavController,
    viewModel: EventViewModel = koinViewModel()
) {
    val eventListState by viewModel.eventList.collectAsState()
    val upcomingEvents by viewModel.upcomingEvents.collectAsState()
    val pastEvents by viewModel.pastEvents.collectAsState()

    val tabs = listOf("Upcoming events", "My events", "Past events")
    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getEvents()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (eventListState) {
            is ApiResult.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ApiResult.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    val eventsToShow = when (selectedTabIndex) {
                        0 -> upcomingEvents
                        1 -> upcomingEvents // Placeholder for "My events" tab (for now we reuse)
                        2 -> pastEvents
                        else -> emptyList()
                    }
                    items(eventsToShow) { event ->
                        EventCard(event)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            is ApiResult.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val errorMessage = (eventListState as ApiResult.Error).message
                    Text("Error loading events: $errorMessage")
                }
            }

            is ApiResult.Idle -> {}
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun EventCard(event: Event) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = if (event.image != null)
                    rememberAsyncImagePainter(event.image)
                else
                    painterResource(id = R.drawable.calendar_month_24px),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(event.start, style = MaterialTheme.typography.bodySmall)
                Text(
                    event.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                event.categories.firstOrNull()?.let { category ->
                    Box(
                        modifier = Modifier
                            .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(category.name, style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))


                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    AndroidView(
                        factory = { context ->
                            TextView(context).apply {
                                text = Html.fromHtml(event.excerpt, Html.FROM_HTML_MODE_COMPACT)
                                textSize = 16f
                                setTextColor(android.graphics.Color.GRAY)
                                text = Html.fromHtml(text.toString(), Html.FROM_HTML_MODE_COMPACT)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.group_24px),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.Gray),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "${event.registeredParticipants} / ${event.maxParticipants}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    TextButton(onClick = { /* TODO: navigate to details */ }) {
                        Text("Details")
                    }
                }
            }
        }
    }
}
