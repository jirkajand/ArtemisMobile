package cz.esnhk.artemisMobile.entities

data class Event(
    val id: Int,
    val name: String,
    val image: String?, // Can be null
    val start: String,
    val excerpt: String,
    val maxParticipants: Int,
    val registeredParticipants: Int,
    val categories: List<Category>
) {
    data class Category(
        val name: String
    )
}