package cz.esnhk.artemisMobile.repository

import cz.esnhk.artemisMobile.api.ApiClient
import cz.esnhk.artemisMobile.entities.Event
import retrofit2.Response

class EventRepository(private val dataStoreManager: DataStoreManager) {
    private val eventService = ApiClient.createEventService(dataStoreManager) // You need EventService

    suspend fun getEvents(): Response<List<Event>> {
        return eventService.getEvents()
    }
}