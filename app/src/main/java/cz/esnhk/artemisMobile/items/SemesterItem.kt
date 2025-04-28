package cz.esnhk.artemisMobile.items

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import cz.esnhk.artemisMobile.entities.SemesterInfo

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SemesterItem(semesterInfo: SemesterInfo, navController: NavController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            AsyncImage(
                model = semesterInfo.image,
                contentDescription = "Semester Info Image",
                modifier = Modifier.size(80.dp) // Bigger to better see QR codes, adjust if needed
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                AndroidView(
                    factory = { context ->
                        TextView(context).apply {
                            text = Html.fromHtml(semesterInfo.description, Html.FROM_HTML_MODE_COMPACT)
                            textSize = 12f
                            setTextColor(android.graphics.Color.GRAY)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
