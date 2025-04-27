package cz.esnhk.artemisMobile.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.entities.Event
import cz.esnhk.artemisMobile.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _eventList = MutableStateFlow<ApiResult<List<Event>>>(ApiResult.Loading)
    val eventList: StateFlow<ApiResult<List<Event>>> = _eventList.asStateFlow()

    fun getEvents() {
        viewModelScope.launch {
            _eventList.value = ApiResult.Loading
            try {
                val response = eventRepository.getEvents()
                if (response.isSuccessful) {
                    val events = response.body()
                    if (events != null) {
                        _eventList.value = ApiResult.Success(events)
                        Log.d("EventViewModel", "getEvents: $events")
                    } else {
                        _eventList.value = ApiResult.Error("Data is null")
                        Log.e("EventViewModel", "Data is null")
                    }
                } else {
                    _eventList.value = ApiResult.Error("Error fetching Event list: ${response.message()}")
                    Log.e("EventViewModel", "Error fetching Event list: ${response.message()}")
                }
            } catch (e: Exception) {
                _eventList.value = ApiResult.Error("Exception fetching Events: ${e.message}")
                Log.e("EventViewModel", "Exception fetching Events: ${e.message}")
            }
        }
    }

    class Factory(private val eventRepository: EventRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
                return EventViewModel(eventRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
