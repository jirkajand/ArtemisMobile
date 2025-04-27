package cz.esnhk.artemisMobile.services
import cz.esnhk.artemisMobile.api.ArtemisResponse
import cz.esnhk.artemisMobile.entities.InternationalStudent
import retrofit2.Response
import retrofit2.http.GET

interface StudentService {
    @GET("exchange-students/")
    suspend fun getInternationalStudents(): Response<List<InternationalStudent>>
}