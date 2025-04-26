package cz.esnhk.artemisMobile

import cz.esnhk.artemisMobile.api.ArtemisApi
import cz.esnhk.artemisMobile.viewmodels.AuthViewModel
import cz.esnhk.artemisMobile.viewmodels.StudentViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

val viewModelModule = module {

    //zde se pomocí get() odkáži na potřebnou instanci repozitáře,
    // mohu mít vytvořených více různých singletonů,
    // koin však vždy dosadí požadovanou instanci (zde FavouriteCryptoRepository)
    viewModel { StudentViewModel(get()) } //samostatná práce
    //viewModel { AnotherViewModel(get()) } //takto bych vytvořil další viewmodel, který by mohl požadovat např. AnotherRepository ;)
    viewModel { AuthViewModel(get()) }
}

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideCryptoApi(get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply { //tvorba interceptoru pro logování
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) //přidání interceptoru
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.coincap.io/v2/")
        .client(okHttpClient) //clienta je nutné definovat, jen v případě, že si vytváříme vlastního, např. my jsme si vytvořili vlastního, abychom přidali interceptor na logování
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideCryptoApi(retrofit: Retrofit): ArtemisApi {
    return retrofit.create(ArtemisApi::class.java) //zde jse Retrofitu předali naše rozhraní pro API, které Retrofit bude implementovat
}