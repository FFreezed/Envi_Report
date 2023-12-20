package id.ac.umn.fadhil.envi_report_2_test.screen

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.envi_report.ui.theme.EnviReportTheme
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.nav.Screens
import id.ac.umn.fadhil.envi_report_2_test.util_database.ReportData
import id.ac.umn.fadhil.envi_report_2_test.util_database.SharedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun GetDataScreen() {
    val navController = rememberNavController()
    val sharedViewModel = remember { SharedViewModel() }

    EnviReportTheme {
        // Call your Composable function here with the fake NavController and SharedViewModel
        GetDataScreen(navController = navController, sharedViewModel = sharedViewModel)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetDataScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    var ID: Int by remember { mutableStateOf(0) }
    var title: String by remember { mutableStateOf("") }
    var desc: String by remember { mutableStateOf("") }
    var Location: String by remember { mutableStateOf("") }
    var status: String by remember { mutableStateOf("") }
    var username: String by remember { mutableStateOf("") }
    var imageName: String by remember { mutableStateOf("") }

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
        reportTitle = title,
        reportDesc = desc,
//        reportLocation = Location,
        reportImageUrl = uri.toString(),
        reportStatus = status,
        reportUsername = username,
        imageName = imageName
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
                            title = data.reportTitle
                            desc = data.reportDesc
//                            Location = data.reportLocation
                            status = data.reportStatus
                            uri = Uri.parse(data.reportImageUrl)
                            username = data.reportUsername
                            imageName = data.imageName
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
                            value = username,
                            onValueChange = { username = it },
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
                            value = title,
                            onValueChange = { title = it },
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            readOnly = true,
                            label = {
                                Text(text = "Judul")
                            },
                            colors = textFieldColor
                        )
                    }

                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = desc,
                            onValueChange = { desc = it },
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            readOnly = true,
                            label = {
                                Text(text = "Deskripsi")
                            },
                            colors = textFieldColor
                        )
                    }

                    item {
                        val statusType = arrayOf("0", "1", "2")
                        var expanded by remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = !expanded
                                }
                            ) {
                                OutlinedTextField(
                                    value = status,
                                    onValueChange = { status = it },
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                    modifier = Modifier.menuAnchor(),
                                    colors = textFieldColor,
                                    label = {
                                        Text(text = "Status")
                                    }
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    statusType.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item) },
                                            onClick = {
                                                status = item
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }


                    item {
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
                        if (reportData.reportTitle.isNotEmpty() &&
                            reportData.reportDesc.isNotEmpty() &&
//                            reportData.reportLocation.isNotEmpty() &&
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox() {
    val context = LocalContext.current
    val statusType = arrayOf("0", "1", "2")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(statusType[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {  },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                statusType.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

