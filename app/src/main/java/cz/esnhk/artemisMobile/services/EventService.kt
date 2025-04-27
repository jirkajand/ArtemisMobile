package cz.esnhk.artemisMobile.services

import cz.esnhk.artemisMobile.entities.Event
import retrofit2.Response
import retrofit2.http.GET

interface EventService {
    @GET("events/")
    suspend fun getEvents(): Response<List<Event>>
}