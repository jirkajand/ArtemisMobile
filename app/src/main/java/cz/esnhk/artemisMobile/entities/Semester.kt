package cz.esnhk.artemisMobile.entities

import com.google.gson.annotations.SerializedName

data class Semester(
    @SerializedName("id") val id: Int,
    @SerializedName("semester_info") val semesterInfo: List<SemesterInfo>,
    @SerializedName("is_current") val isCurrent: Boolean,
    @SerializedName("label") val label: String,
    @SerializedName("year") val year: Int,
    @SerializedName("semester") val semester: String
)

data class SemesterInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("description") val description: String
)