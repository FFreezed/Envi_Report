package id.ac.umn.fadhil.envi_report_2_test.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.nav.Screens
import id.ac.umn.fadhil.envi_report_2_test.signIn.UserData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//import id.ac.umn.fadhil.envi_report_2_test.util_database.SharedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController,
    userData: UserData?
//    sharedViewModel: SharedViewModel

) {

    Image(
        painter = painterResource(id = R.drawable.bg2),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = 0.5f,
        modifier = Modifier
            .fillMaxSize()
    )

    Column(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(400.dp, 300.dp)
                .padding(start = 20.dp, end = 20.dp, top = 50.dp, bottom = 60.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(5.dp, colorResource(id = R.color.buttonColor), shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        colorResource(id = R.color.white)
                    )
                    .border(3.dp, colorResource(id = R.color.buttonColor))
                    .padding(15.dp)
            ) {
                AsyncImage(
                    model = userData?.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp, 80.dp)
                        .clip(CircleShape)
                        .padding(15.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {
                    Text(
                        text = "Halo ${userData?.username}!",
                        color = colorResource(id = R.color.textColor),
                        fontSize = 24.sp
                    )

                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy")
                    val formattedDateTime = currentDateTime.format(formatter)

                    Text(
                        text = formattedDateTime,
                        color = colorResource(id = R.color.textColor),
                        fontSize = 12.sp
                    )
                }
            }
        }
//        Image(
//            painter = painterResource(id = R.drawable.enviro),
//            contentDescription = "Logo",
//            modifier = Modifier
//                .size(250.dp)
//                .padding(top = 16.dp)
//        )

        Text(
            text = "Laporkan Keluhanmu!",
            color = colorResource(id = R.color.textColor),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )

        ElevatedButton(
            modifier = Modifier
                .size(width = 240.dp, height = 70.dp)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.buttonColor)
            ),
            onClick = { navController.navigate(Screens.ListDataScreen.route) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment =  Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                ) {
                    Icon(
                        Icons.Default.List,
                        contentDescription = "List",
                        tint = colorResource(id = R.color.black)
                    )
                }

                Text(
                    text = "Daftar Laporan",
                    color = colorResource(id = R.color.textColor)
                )
            }
        }

//        Add user data button
        ElevatedButton(
            modifier = Modifier
                .size(width = 240.dp, height = 70.dp)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.buttonColor)
            ),
            onClick = { navController.navigate(route = Screens.AddDataScreen.route ) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment =  Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                ) {
                    Icon(
                        Icons.Default.Create,
                        contentDescription = "Tambah",
                        tint = colorResource(id = R.color.black)
                    )
                }

                Text(
                    text = "Buat Laporan",
                    color = colorResource(id = R.color.textColor)
                )
            }
        }

        if(userData?.username == stringResource(R.string.Admin)){
            ElevatedButton(
                modifier = Modifier
                    .size(width = 240.dp, height = 70.dp)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.buttonColor)
                ),
                onClick = { navController.navigate(route = Screens.GetDataScreen.route)}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment =  Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart),
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Cari",
                            tint = colorResource(id = R.color.black)
                        )
                    }

                    Text(
                        text = "Cari Laporan",
                        color = colorResource(id = R.color.textColor)
                    )
                }
            }
        }

        ElevatedButton(
            modifier = Modifier
                .size(width = 240.dp, height = 70.dp)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.buttonColor)
            ),
            onClick = { navController.navigate(route = Screens.ProfileScreen.route)}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment =  Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = colorResource(id = R.color.black)
                    )
                }

                Text(
                    text = "Profile",
                    color = colorResource(id = R.color.textColor)
                )
            }
        }
    }
}
