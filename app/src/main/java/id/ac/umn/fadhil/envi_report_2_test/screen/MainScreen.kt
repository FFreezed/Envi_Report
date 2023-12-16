package id.ac.umn.fadhil.envi_report_2_test.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.nav.Screens
import id.ac.umn.fadhil.envi_report_2_test.signIn.UserData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    val userData = UserData(
        username = "EnviAdmin",
        userId = "1",
        profilePictureUrl = "https://picsum.photos/200/300"
    )

    MainScreen(navController = navController, userData = userData)
}

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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
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
                    .padding(5.dp),
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

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = "Laporkan Keluhanmu!",
                color = colorResource(id = R.color.textColor),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MenuIconButton(
                    buttonText = "Daftar Laporan",
                    contentDesc = "List",
                    icon = Icons.Default.List,
                    onClickAction = {
                        navController.navigate(Screens.ListDataScreen.route)
                    }
                )

                MenuIconButton(
                    buttonText = "Buat Laporan",
                    contentDesc = "Create",
                    icon = Icons.Default.Create,
                    onClickAction = {
                        navController.navigate(Screens.AddDataScreen.route)
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MenuIconButton(
                    buttonText = "Profile Anda",
                    contentDesc = "Profile",
                    icon = Icons.Default.AccountCircle,
                    onClickAction = {
                        navController.navigate(Screens.ProfileScreen.route)
                    }
                )

                if(userData?.username == stringResource(R.string.Admin)){
                    MenuIconButton(
                        buttonText = "Cari Laporan",
                        contentDesc = "Search",
                        icon = Icons.Default.Search,
                        onClickAction = {
                            navController.navigate(Screens.GetDataScreen.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MenuIconButton(
    buttonText: String,
    contentDesc: String,
    icon: ImageVector,
    onClickAction: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            modifier = Modifier
                .size(120.dp)
                .padding(10.dp)
                .background(colorResource(id = R.color.buttonColor)),
            onClick = { onClickAction() }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(60.dp),
                    imageVector = icon,
                    contentDescription = contentDesc,
                )
            }
        }

        Text(
            modifier = Modifier.padding(10.dp),
            text = buttonText,
            fontSize = 15.sp
        )
    }
}

//        ElevatedButton(
//            modifier = Modifier
//                .size(width = 240.dp, height = 70.dp)
//                .padding(8.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = colorResource(id = R.color.buttonColor)
//            ),
//            onClick = { navController.navigate(Screens.ListDataScreen.route) }
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                contentAlignment =  Alignment.Center
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.CenterStart),
//                ) {
//                    Icon(
//                        Icons.Default.List,
//                        contentDescription = "List",
//                        tint = colorResource(id = R.color.black)
//                    )
//                }
//
//                Text(
//                    text = "Daftar Laporan",
//                    color = colorResource(id = R.color.textColor)
//                )
//            }
//        }

//        Add user data button
//        ElevatedButton(
//            modifier = Modifier
//                .size(width = 240.dp, height = 70.dp)
//                .padding(8.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = colorResource(id = R.color.buttonColor)
//            ),
//            onClick = { navController.navigate(route = Screens.AddDataScreen.route) }
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                contentAlignment =  Alignment.Center
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.CenterStart),
//                ) {
//                    Icon(
//                        Icons.Default.Create,
//                        contentDescription = "Tambah",
//                        tint = colorResource(id = R.color.black)
//                    )
//                }
//
//                Text(
//                    text = "Buat Laporan",
//                    color = colorResource(id = R.color.textColor)
//                )
//            }
//        }
//
//        if(userData?.username == stringResource(R.string.Admin)){
//            ElevatedButton(
//                modifier = Modifier
//                    .size(width = 240.dp, height = 70.dp)
//                    .padding(8.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = colorResource(id = R.color.buttonColor)
//                ),
//                onClick = { navController.navigate(route = Screens.GetDataScreen.route)}
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    contentAlignment =  Alignment.Center
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .align(Alignment.CenterStart),
//                    ) {
//                        Icon(
//                            Icons.Default.Search,
//                            contentDescription = "Cari",
//                            tint = colorResource(id = R.color.black)
//                        )
//                    }
//
//                    Text(
//                        text = "Cari Laporan",
//                        color = colorResource(id = R.color.textColor)
//                    )
//                }
//            }
//        }
//
//        ElevatedButton(
//            modifier = Modifier
//                .size(width = 240.dp, height = 70.dp)
//                .padding(8.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = colorResource(id = R.color.buttonColor)
//            ),
//            onClick = { navController.navigate(route = Screens.ProfileScreen.route)}
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                contentAlignment =  Alignment.Center
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.CenterStart),
//                ) {
//                    Icon(
//                        Icons.Default.Person,
//                        contentDescription = "Profile",
//                        tint = colorResource(id = R.color.black)
//                    )
//                }
//
//                Text(
//                    text = "Profile",
//                    color = colorResource(id = R.color.textColor)
//                )
//            }
//        }



