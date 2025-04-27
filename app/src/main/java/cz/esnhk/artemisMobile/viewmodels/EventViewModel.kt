package cz.esnhk.artemisMobile.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.entities.Event
import cz.esnhk.artemisMobile.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _eventList = MutableStateFlow<ApiResult<List<Event>>>(ApiResult.Loading)
    val eventList: StateFlow<ApiResult<List<Event>>> = _eventList.asStateFlow()

    private val _upcomingEvents = MutableStateFlow<List<Event>>(emptyList())
    val upcomingEvents: StateFlow<List<Event>> = _upcomingEvents.asStateFlow()

    private val _pastEvents = MutableStateFlow<List<Event>>(emptyList())
    val pastEvents: StateFlow<List<Event>> = _pastEvents.asStateFlow()

    fun getEvents() {
        viewModelScope.launch {
            _eventList.value = ApiResult.Loading
            try {
                val response = eventRepository.getEvents()
                if (response.isSuccessful) {
                    val events = response.body()
                    if (events != null) {
                        _eventList.value = ApiResult.Success(events)

                        val now = OffsetDateTime.now()

                        val upcoming = mutableListOf<Event>()
                        val past = mutableListOf<Event>()

                        for (event in events) {
                            val eventDate = try {
                                OffsetDateTime.parse(event.start)
                            } catch (e: Exception) {
                                Log.e("EventViewModel", "Error parsing date '${event.start}': ${e.message}")
                                null
                            }

                            if (eventDate != null) {
                                if (eventDate.isAfter(now)) {
                                    upcoming.add(event)
                                } else {
                                    past.add(event)
                                }
                            } else {
                                Log.w("EventViewModel", "Skipping event with invalid date: ${event.start}")
                            }
                        }

                        _upcomingEvents.value = upcoming
                        _pastEvents.value = past

                    } else {
                        _eventList.value = ApiResult.Error("Data is null")
                    }
                } else {
                    _eventList.value = ApiResult.Error("Error fetching Event list: ${response.message()}")
                }
            } catch (e: Exception) {
                _eventList.value = ApiResult.Error("Exception fetching Events: ${e.message}")
            }
        }
    }
}
