package cz.esnhk.artemisMobile.repository

import cz.esnhk.artemisMobile.api.ApiClient
import cz.esnhk.artemisMobile.api.ArtemisResponse
import cz.esnhk.artemisMobile.entities.InternationalStudent
import retrofit2.Response

class StudentRepository(private val dataStoreManager: DataStoreManager) {
    private val studentService = ApiClient.createStudentService(dataStoreManager)

    suspend fun getStudentList(): Response<List<InternationalStudent>> {
        return studentService.getInternationalStudents()
    }

    suspend fun getStudentByBuddyId(id: Int): List<InternationalStudent> {
        val response = getStudentList()
        val studentList = response.body().orEmpty()
        return studentList.filter { it.assignedBuddy == id }
    }
}