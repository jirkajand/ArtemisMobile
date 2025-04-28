package cz.esnhk.artemisMobile.services

import cz.esnhk.artemisMobile.entities.Semester
import retrofit2.Response
import retrofit2.http.GET

interface SemesterService {
    @GET("semesters/")
    suspend fun getSemesters(): Response<List<Semester>>
}