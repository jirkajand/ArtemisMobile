package cz.esnhk.artemisMobile.items

import android.content.Intent
import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import cz.esnhk.artemisMobile.R
import cz.esnhk.artemisMobile.entities.SemesterInfo

@RequiresApi(Build.VERSION_CODES.N) //TODO unifi project api version
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
            Image(
                painter = painterResource(id = R.drawable.coin), // your QR image
                contentDescription = "Image",
                modifier = Modifier.size(40.dp)
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