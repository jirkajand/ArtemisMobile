package cz.esnhk.artemisMobile.api
import com.google.gson.annotations.SerializedName

/*
Tato třída bude sloužit pro získávání response s API (všechny endpointy vracejí data ve stejně strukturovaném JSON),
tj. data a timestamp, pro tyto účely se tedy hodí použít genericitu
* */
data class ArtemisResponse<T> (
    @SerializedName("data") val data: T
)
