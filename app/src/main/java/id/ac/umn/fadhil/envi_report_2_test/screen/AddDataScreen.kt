package id.ac.umn.fadhil.envi_report_2_test.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.nav.Screens
import id.ac.umn.fadhil.envi_report_2_test.signIn.UserData
import id.ac.umn.fadhil.envi_report_2_test.util_database.ReportData
import id.ac.umn.fadhil.envi_report_2_test.util_database.SharedViewModel

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDataScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    userData: UserData?
) {
//    var reportID: Int by remember { mutableStateOf(generateIDCounter()) }
    val id: Int = generateIDCounter()
    var judul: String by remember { mutableStateOf("") }
    var keluhan: String by remember { mutableStateOf("") }
    var lokasi: String by remember { mutableStateOf("") }
    val status: Int by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    var uri by remember{
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val reportData = ReportData(
        reportId = id,
        reportTitle = judul,
        reportDesc = keluhan,
        reportLocation = lokasi,
        reportImageUrl = uri.toString(),
        reportStatus = status.toString(),
        reportUsername = userData?.username.toString(),
    )

    var showSubmitDialog by remember { mutableStateOf(false) }

//    main layout
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
                text = "Buat Laporan",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.textColor),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }

        val textFieldColor = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.darkGreen),
            unfocusedBorderColor = colorResource(id = R.color.darkGreen) ,
            cursorColor = colorResource(id = R.color.textColor),
            textColor = colorResource(id = R.color.textColor),
            focusedLabelColor = colorResource(id = R.color.textColor),
            unfocusedLabelColor = colorResource(id = R.color.textColor)
        )

//        Add data layout
        Column(
            modifier = Modifier
                .padding(start = 50.dp, end = 50.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Formulir Laporan",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = judul,
                        onValueChange = { judul = it },
                        label = {
                            Text(text = "Judul")
                        },
                        colors = textFieldColor
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = keluhan,
                        onValueChange = { keluhan = it },
                        label = {
                            Text(text = "Deskripsi")
                        },
                        colors = textFieldColor
                    )
                }

                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = lokasi,
                        onValueChange = { lokasi = it },
                        label = {
                            Text(text = "Lokasi")
                        },
                        colors = textFieldColor
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(start = 40.dp, end = 40.dp)
                    ) {
                        ElevatedButton(
                            modifier = Modifier
                                .size(width = 180.dp, height = 70.dp)
                                .padding(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.buttonColor)
                            ),
                            onClick = { singlePhotoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            ) }
                        ){
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
                                        Icons.Default.Add,
                                        contentDescription = "Tambah",
                                        tint = colorResource(id = R.color.black)
                                    )
                                }

                                Text(
                                    text = "Foto Bukti",
                                    color = colorResource(id = R.color.textColor)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .padding(start = 30.dp, end = 30.dp)
                        ) {
                            AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(190.dp))
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp)
                    ) {
                        ElevatedButton(
                            modifier = Modifier
                                .size(width = 240.dp, height = 70.dp)
                                .padding(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.buttonColor)
                            ),
                            onClick = {
//                                val reportData = ReportData(
//                                    reportId = id,
//                                    reportTitle = judul,
//                                    reportDesc = keluhan,
//                                    reportLocation = lokasi,
//                                    reportImageUrl = uri.toString(),
//                                    reportStatus = status.toString(),
//                                    reportUsername = userData?.username.toString(),
//                                )

                                if (reportData.reportTitle.isNotEmpty() &&
                                    reportData.reportDesc.isNotEmpty() &&
                                    reportData.reportLocation.isNotEmpty() &&
                                    uri !== null
                                ) {
                                    showSubmitDialog = true

//                                    AlertDialog.Builder(context)
//                                        .setTitle("Konfirmasi")
//                                        .setMessage("Laporan yang dikirim tidak dapat diubah. "+"Apakah Anda yakin ingin mengirim laporan?")
//                                        .setPositiveButton("Ya") { dialog, _ ->
//                                            // User confirmed, proceed with submitting the report
//                                            sharedViewModel.uploadImagesAndSaveData(reportData = reportData, context = context, imageUri = uri )
//                                            generateIDCounter()
//                                            navController.navigate(route = Screens.MainScreen.route)
//                                            dialog.dismiss()
//                                        }
//                                        .setNegativeButton("Batal") { dialog, _ ->
//                                            // User canceled, dismiss the dialog
//                                            dialog.dismiss()
//                                        }
//                                        .show()


                                } else {
                                    // Display a message if any field is empty
                                    Toast.makeText(context, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
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
                                    text = "Laporkan",
                                    color = colorResource(id = R.color.textColor)
                                )
                            }
                        }
                    }
                }
            }
        }
        if (showSubmitDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Handle dismiss request, if needed
                    showSubmitDialog = false
                },
                containerColor = colorResource(id = R.color.lightBg),
                textContentColor = colorResource(id = R.color.textColor),
                titleContentColor = colorResource(id = R.color.textColor),
                title = {
                    Text("Konfirmasi")
                },
                text = {
                    Text("Laporan yang dikirim tidak dapat diubah. Apakah Anda yakin ingin mengirim laporan?")
                },
                confirmButton = {
                    TextButton(
                        modifier = Modifier
                            .size(75.dp, 50.dp),
                        onClick = {
                            // User confirmed, proceed with submitting the report
                            sharedViewModel.uploadImagesAndSaveData(reportData = reportData, context = context, imageUri = uri)
                            generateIDCounter()
                            navController.navigate(route = Screens.MainScreen.route)
                            showSubmitDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = colorResource(id = R.color.textColor),
                            containerColor = colorResource(id = R.color.darkGreen)
                        )
                    ) {
                        Text("Ya")
                    }
                },
                dismissButton = {
                    TextButton(
                        modifier = Modifier
                            .size(75.dp, 50.dp),
                        onClick = {
                            // User canceled, dismiss the dialog
                            showSubmitDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = colorResource(id = R.color.textColor),
                            containerColor = colorResource(id = R.color.darkGreen)
                        )
                    ) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}

var reportIDCounter by mutableIntStateOf(1)
fun generateIDCounter(): Int {
    return reportIDCounter++
}


