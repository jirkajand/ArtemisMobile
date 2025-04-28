package cz.esnhk.artemisMobile.repository

import cz.esnhk.artemisMobile.api.ApiClient
import cz.esnhk.artemisMobile.entities.Semester
import retrofit2.Response

class SemesterRepository(private val dataStoreManager: DataStoreManager) {

    private val semesterService = ApiClient.createSemesterService(dataStoreManager) // You need EventService
    suspend fun getSemesterList(): Response<List<Semester>> {
        return semesterService.getSemesters()
    }
}