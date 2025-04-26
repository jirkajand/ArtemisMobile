package cz.esnhk.artemisMobile.consts

object Routes {
    const val Dashboard = "cryptoList"
    const val MyStudents = "myStudents"
    const val Events = "events"
    const val SemesterInfo = "semesterInfo"

    const val Login = "login"

    const val CryptoDetail = "cryptoDetail/{cryptoId}"
    const val FavouriteCrypto = "favouriteCrypto"
    const val Settings = "settings"

    //Funkce pro vytvoření routy s id
    fun cryptoDetail(cryptoId: String): String {
        return "cryptoDetail/$cryptoId"
    }
}