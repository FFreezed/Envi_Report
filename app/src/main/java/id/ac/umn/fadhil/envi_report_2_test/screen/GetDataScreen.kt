package id.ac.umn.fadhil.envi_report_2_test.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.nav.Screens
import id.ac.umn.fadhil.envi_report_2_test.util_database.ReportData
import id.ac.umn.fadhil.envi_report_2_test.util_database.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetDataScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    var ID: Int by remember { mutableStateOf(0) }
    var Title: String by remember { mutableStateOf("") }
    var Desc: String by remember { mutableStateOf("") }
    var Location: String by remember { mutableStateOf("") }
    var Status: String by remember { mutableStateOf("") }
    var Username: String by remember { mutableStateOf("") }
    var ImageName: String by remember { mutableStateOf("") }

    val context = LocalContext.current

    var uri by remember{
        mutableStateOf<Uri?>(null)
    }

    val textFieldColor = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = colorResource(id = R.color.darkGreen),
        unfocusedBorderColor = colorResource(id = R.color.darkGreen) ,
        cursorColor = colorResource(id = R.color.textColor),
        textColor = colorResource(id = R.color.textColor),
        focusedLabelColor = colorResource(id = R.color.textColor),
        unfocusedLabelColor = colorResource(id = R.color.textColor),
        containerColor = Color.White
    )

    var showUpdateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val reportData = ReportData(
        reportId = ID,
        reportTitle = Title,
        reportDesc = Desc,
        reportLocation = Location,
        reportImageUrl = uri.toString(),
        reportStatus = Status,
        reportUsername = Username,
        imageName = ImageName
    )

//    main layout
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
                text = "Update Laporan",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.textColor),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }

//        Get data layout
        Column(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, bottom = 25.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            report ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(top = 10.dp),
                    value = ID.toString(),
                    onValueChange = { ID = (it.toIntOrNull() ?: 0) },
                    label = {
                        Text(text = "ID Laporan")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = textFieldColor
                )
                
                Spacer(modifier = Modifier.padding(8.dp))

                IconButton(
                    modifier = Modifier
                        .size(width = 60.dp, height = 40.dp)
                        .background(
                            colorResource(id = R.color.buttonColor),
                            shape = RoundedCornerShape(8.dp)
                        ),
//                    colors = IconButtonDefaults.filledIconButtonColors(
//                        containerColor = colorResource(id = R.color.buttonColor)
//                    ),
                    onClick = {
                        sharedViewModel.retrieveData(
                            reportID = ID.toString(),
//                            reportTitle = Title,
//                            reportDesc = Desc,
                            context = context
                        ) {data ->
                            Title = data.reportTitle
                            Desc = data.reportDesc
                            Location = data.reportLocation
                            Status = data.reportStatus
                            uri = Uri.parse(data.reportImageUrl)
                            Username = data.reportUsername
                            ImageName = data.imageName
                        }
                    }
                ){
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Cari",
                        tint = colorResource(id = R.color.black)
                    )
                }
            }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = Username,
                            onValueChange = { Username = it },
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            readOnly = true,
                            label = {
                                Text(text = "Username")
                            },
                            colors = textFieldColor
                        )
                    }

                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = Title,
                            onValueChange = { Title = it },
                            label = {
                                Text(text = "Judul")
                            },
                            colors = textFieldColor
                        )
                    }

                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = Desc,
                            onValueChange = { Desc = it },
                            label = {
                                Text(text = "Deskripsi")
                            },
                            colors = textFieldColor
                        )
                    }

                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = Location,
                            onValueChange = { Location = it },
                            label = {
                                Text(text = "Lokasi")
                            },
                            colors = textFieldColor
                        )
                    }

                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = Status,
                            onValueChange = { Status = it },
                            label = {
                                Text(text = "Status(0-2)")
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            colors = textFieldColor
                        )
                    }

                    item {
//                        Image(
//                            painter = rememberAsyncImagePainter(uri),
//                            contentDescription = "Report Image",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 16.dp)
//                                .height(200.dp)
//                        )

                        val singlePhotoPicker = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.PickVisualMedia(),
                            onResult = {
                                uri = it
                            }
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 40.dp, end = 40.dp)
                        ){
                            Text(
                                text = "Foto Bukti : ",
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp)
                            )
                        }
                    }
                }

            Row {


                ElevatedButton(
                    modifier = Modifier
                        .size(width = 120.dp, height = 70.dp)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.redDelete)
                    ),
                    onClick = {
                        showDeleteDialog = true
//                        sharedViewModel.deleteData(
//                            reportID = ID.toString(),
//                            context = context,
//                            navController = navController
//                        )
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
                            text = "Hapus",
                            color = colorResource(id = R.color.textColor)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                ElevatedButton(
                    modifier = Modifier
                        .size(width = 120.dp, height = 70.dp)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.buttonColor)
                    ),
                    onClick = {
//                        val reportData = ReportData(
//                            reportId = ID,
//                            reportTitle = Title,
//                            reportDesc = Desc,
//                            reportLocation = Location,
//                            reportImageUrl = uri.toString(),
//                            reportStatus = Status,
//                            reportUsername = Username,
//                            imageName = ImageName
//                        )
                        if (reportData.reportTitle.isNotEmpty() &&
                            reportData.reportDesc.isNotEmpty() &&
                            reportData.reportLocation.isNotEmpty() &&
                            uri !== null
                        ){
                            showUpdateDialog = true
//                            sharedViewModel.updateData(reportData = reportData, context = context)
//                            navController.navigate(route = Screens.MainScreen.route)
                        }else{
                            Toast.makeText(context, "Masukkan ID", Toast.LENGTH_SHORT).show()
                        }
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
                            text = "Update",
                            color = colorResource(id = R.color.textColor)
                        )
                    }
                }
            }
        }

        if (showUpdateDialog) {
            UpdateDialog(
                onConfirm = {
                    // User confirmed sign out
                    sharedViewModel.updateData(reportData = reportData, context = context)
                    navController.navigate(route = Screens.MainScreen.route)
                    showUpdateDialog = false
                },
                onDismiss = {
                    // User dismissed the dialog
                    showUpdateDialog = false
                }
            )
        }

        if (showDeleteDialog) {
            DeleteDialog(
                onConfirm = {
                    // User confirmed sign out
                    sharedViewModel.deleteData(
                        reportID = ID.toString(),
                        context = context,
                        navController = navController
                    )
                    showDeleteDialog = false
                },
                onDismiss = {
                    // User dismissed the dialog
                    showDeleteDialog = false

                }
            )
        }
    }
}

@Composable
fun UpdateDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = colorResource(id = R.color.lightBg),
        textContentColor = colorResource(id = R.color.textColor),
        titleContentColor = colorResource(id = R.color.textColor),
        title = {
            Text(text = "Update")
        },
        text = {
            Text(text = "Anda yakin ingin update data laporan?")
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

@Composable
fun DeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = colorResource(id = R.color.lightBg),
        textContentColor = colorResource(id = R.color.textColor),
        titleContentColor = colorResource(id = R.color.textColor),
        title = {
            Text(text = "Delete")
        },
        text = {
            Text(text = "Anda yakin ingin hapus data laporan?")
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

