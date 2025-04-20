package cz.esnhk.artemisMobile

import com.google.gson.annotations.SerializedName

data class InternationalStudent(
    @SerializedName("id") val id: Int,
    @SerializedName("last_login") val lastLogin: String,
    @SerializedName("is_superuser") val isSuperuser: Boolean,
    @SerializedName("object_class") val objectClass: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("number") val number: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("date_joined") val dateJoined: String,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("email_subscription") val emailSubscription: Boolean,
    @SerializedName("json_state") val jsonState: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("staff") val staff: Boolean,
    @SerializedName("profile_picture") val profilePicture: String,
    @SerializedName("country") val country: String,
    @SerializedName("home_university") val homeUniversity: String,
    @SerializedName("description") val description: String,
    @SerializedName("arrival_date") val arrivalDate: String,
    @SerializedName("arrival_note") val arrivalNote: String,
    @SerializedName("arrival_mates") val arrivalMates: String,
    @SerializedName("faculty") val faculty: Int,
    @SerializedName("assigned_buddy") val assignedBuddy: Int,
    @SerializedName("arrival_place") val arrivalPlace: Int,
    @SerializedName("accommodation") val accommodation: Int,
    @SerializedName("student_group") val studentGroup: Int,
    @SerializedName("groups") val groups: List<Int>,
    @SerializedName("user_permissions") val userPermissions: List<Int>,
    @SerializedName("semesters") val semesters: List<Int>
)

