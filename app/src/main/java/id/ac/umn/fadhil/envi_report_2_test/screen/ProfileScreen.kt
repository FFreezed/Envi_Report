package id.ac.umn.fadhil.envi_report_2_test.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.nav.Screens
import id.ac.umn.fadhil.envi_report_2_test.signIn.UserData
import id.ac.umn.fadhil.envi_report_2_test.util_database.ReportData

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    navController: NavController
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
                text = "Profile",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.textColor),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(userData?.profilePictureUrl != null) {
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if(userData?.username != null) {
                Text(
                    text = userData.username,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            var showSignOutDialog by remember { mutableStateOf(false) }

            ElevatedButton(
                modifier = Modifier
                    .size(width = 240.dp, height = 70.dp)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.buttonColor)
                ),
//                onClick = onSignOut
                onClick = {
                    showSignOutDialog = true
                }
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
                    }
                    Text(
                        text = "Sign Out",
                        color = colorResource(id = R.color.textColor)
                    )
                }
            }

            if (showSignOutDialog) {
                SignOutDialog(
                    onConfirm = {
                        // User confirmed sign out
                        onSignOut()
                        showSignOutDialog = false
                    },
                    onDismiss = {
                        // User dismissed the dialog
                        showSignOutDialog = false
                    }
                )
            }

            ElevatedButton(
                modifier = Modifier
                    .size(width = 240.dp, height = 70.dp)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.buttonColor)
                ),
                onClick = { navController.navigate(route = Screens.MainScreen.route) }
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
                    }
                    Text(
                        text = "Home Page",
                        color = colorResource(id = R.color.textColor)
                    )
                }
            }
        }
    }
}

@Composable
fun SignOutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = colorResource(id = R.color.lightBg),
        textContentColor = colorResource(id = R.color.textColor),
        titleContentColor = colorResource(id = R.color.textColor),
        title = {
            Text(text = "Sign Out")
        },
        text = {
            Text(text = "Anda yakin ingin Sign Out?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(id = R.color.textColor),
                    containerColor = colorResource(id = R.color.darkGreen)
                )
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(id = R.color.textColor),
                    containerColor = colorResource(id = R.color.darkGreen)
                )
            ) {
                Text("Cancel")
            }
        }
    )
}
