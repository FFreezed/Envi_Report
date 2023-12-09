package id.ac.umn.fadhil.envi_report_2_test.screen

import android.R.attr.maxLines
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.signIn.UserData
import id.ac.umn.fadhil.envi_report_2_test.util_database.ReportData
import id.ac.umn.fadhil.envi_report_2_test.util_database.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ListDataScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    userData: UserData?
) {
    val context = LocalContext.current

    // Get the list of all data
    var allDataList: List<ReportData> by remember { mutableStateOf(emptyList()) }

    sharedViewModel.getAllData { dataList ->
        allDataList = dataList
    }

    // main layout
    Image(
        painter = painterResource(id = R.drawable.bg1),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = 0.5f,
        modifier = Modifier
            .fillMaxSize()
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.darkGreen)
                )
                .padding(10.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back_button")
            }
            Text(
                text = "Daftar Laporan",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.textColor),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }

        // List of cards
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            val filteredDataList = allDataList.filter {
                if (userData?.username == context.getString(R.string.Admin)) {
                    true // Allow admin to see all data
                } else {
                    it.reportUsername == userData?.username
                }
            }.sortedByDescending { it.reportDate }

            items(filteredDataList) { data ->
                if (userData != null) {
                    CardItem(data = data, user = userData, onCardClick = {})
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            if (filteredDataList.isEmpty()) {
                // Add a message when there are no reports
                item {
                    Text(
                        text = "Belum ada laporan",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        style = TextStyle(color = Color.Gray)
                    )
                }
            }
        }
    }
}

@Composable
fun CardItem(data: ReportData, user: UserData, onCardClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(5.dp, colorResource(id = R.color.buttonColor), shape = RoundedCornerShape(8.dp))
        ,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.lightBg)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            if(user.username == stringResource(R.string.Admin)) {
                Text(
                    text = "ID : ${data.reportId}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.textColor),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Judul : ${data.reportTitle}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.textColor).copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pelapor : ${data.reportUsername}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.textColor).copy(alpha = 0.7f)
                )
            } else {
                Text(
                    text = "Judul : ${data.reportTitle}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.textColor).copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Lokasi : ${data.reportLocation}",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.textColor).copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = when (data.reportStatus) {
                    0.toString() -> "Status : Diterima"
                    1.toString() -> "Status : Di proses"
                    2.toString() -> "Status : Selesai"
                    else -> "Status Tidak Diketahui"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.textColor).copy(alpha = 0.7f)
            )

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(data.reportDate)

            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val formattedTime = timeFormat.format(data.reportDate)

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tanggal : ${formattedDate}, Jam : ${formattedTime}",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.textColor).copy(alpha = 0.7f)
            )
        }
    }
}


