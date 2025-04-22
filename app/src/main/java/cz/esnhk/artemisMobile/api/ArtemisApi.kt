package cz.esnhk.artemisMobile.api
import cz.esnhk.artemisMobile.entities.InternationalStudent
import retrofit2.http.GET
import retrofit2.Response


//interface nabízející funkce pro volání endpointů (Retrofit na základě tohoto rozhraní vytvoří implementaci)
interface ArtemisApi {
    @GET("exchange-students/")
    suspend fun getCryptoList(): Response<ArtemisResponse<List<InternationalStudent>>>
}