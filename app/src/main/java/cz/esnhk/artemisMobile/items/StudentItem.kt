package cz.esnhk.artemisMobile.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import cz.esnhk.artemisMobile.entities.InternationalStudent
import cz.esnhk.artemisMobile.R
import java.util.Locale

// Helper function to safely handle nullable Strings
private fun safeText(text: String?, fallback: String = "N/A") = text ?: fallback

@Composable
fun StudentItem(internationalStudent: InternationalStudent, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = getGenderIcon(internationalStudent.sex),
                        contentDescription = "Gender icon",
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                            getGenderColor(
                                internationalStudent.sex
                            )
                        ),
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = safeText(internationalStudent.homeUniversity))
                }

                Text(text = "Faculty: " + internationalStudent.faculty)

                Column(
                    modifier = Modifier
                        .weight(1f) // fill the remaining vertical space
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "About me: " + safeText(internationalStudent.description),
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (internationalStudent.assignedBuddy == null) {
                    Button(
                        onClick = { /* TODO Handle take action */ },
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Text("Take student")
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(80.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(getFlagUrl(internationalStudent.country)),
                    contentDescription = "Flag of ${internationalStudent.country}",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(getCountryName(internationalStudent.country))
            }
        }
    }
}

private fun getFlagUrl(countryCode: String?): String {
    if (countryCode.isNullOrBlank()) return "https://upload.wikimedia.org/wikipedia/commons/8/89/HD_transparent_picture.png" // fallback transparent image
    return "https://flagcdn.com/w320/${countryCode.lowercase()}.png"
}

private fun getCountryName(countryCode: String?): String {
    if (countryCode.isNullOrBlank()) {
        return "Unknown"
    }
    val locale = Locale("en", countryCode)
    return locale.getDisplayCountry(Locale.ENGLISH).ifBlank { "Unknown" }
}

@DrawableRes
private fun getGenderIcon(sex: String?): Int {
    return when (sex?.lowercase()) {
        "male" -> R.drawable.male_24px
        "female" -> R.drawable.female_24px
        else -> R.drawable.transgender_24px
    }
}

private fun getGenderColor(sex: String?): Color {
    return when (sex?.lowercase()) {
        "male" -> Color(0xFF2196F3) // Blue for male
        "female" -> Color(0xFFE91E63) // Pink for female
        else -> Color.Gray // Default gray if unknown
    }
}