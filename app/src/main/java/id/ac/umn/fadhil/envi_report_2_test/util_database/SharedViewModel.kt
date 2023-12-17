package id.ac.umn.fadhil.envi_report_2_test.util_database

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.nav.Screens
import id.ac.umn.fadhil.envi_report_2_test.screen.generateIDCounter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SharedViewModel(): ViewModel() {

    fun saveData(
        reportData: ReportData,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {
        val reportIdString = reportData.reportId.toString()

        val fireStoreRef = Firebase.firestore
            .collection("report")
            .document(reportIdString)
//            .document()

        try {

            fireStoreRef.set(reportData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Selamat, laporan anda telah masuk!", Toast.LENGTH_SHORT).show()
                }

        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun updateData(
        reportData: ReportData,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {
        val reportIdString = reportData.reportId.toString()

        val fireStoreRef = Firebase.firestore
            .collection("report")
            .document(reportIdString)
//            .document()
        try {

            fireStoreRef.set(reportData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Laporan telah di update!", Toast.LENGTH_SHORT).show()
                }

        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun retrieveData(
        reportID: String,
        context: Context,
        data: (ReportData) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {
        val reportIdString = reportID

        val fireStoreRef = Firebase.firestore
            .collection("report")
            .document(reportIdString)

        try {

            fireStoreRef.get()
                .addOnSuccessListener {
                    if(it.exists()) {
                        val reportData = it.toObject<ReportData>()!!
                        data(reportData)
                    } else {
                        Toast.makeText(context, "Tidak ada laporan dengan ID itu", Toast.LENGTH_SHORT).show()
                    }
                }

        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

//    fun deleteData(
//        reportID: String,
//        context: Context,
//        navController: NavController,
//    ) = CoroutineScope(Dispatchers.IO).launch {
//        val reportIdString = reportID
//
//        val fireStoreRef = Firebase.firestore
//            .collection("report")
//            .document(reportIdString)
//
//        try {
//
//            fireStoreRef.delete()
//                .addOnSuccessListener {
//                    Toast.makeText(context, "Laporan telah di hapus!", Toast.LENGTH_SHORT).show()
//                    navController.navigate(route = Screens.MainScreen.route)
//                }
//
//        } catch (e: Exception) {
//            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
//        }
//    }

    fun deleteData(
        reportID: String,
        context: Context,
        navController: NavController,
    ) = CoroutineScope(Dispatchers.IO).launch {
        val reportIdString = reportID

        val fireStoreRef = Firebase.firestore
            .collection("report")
            .document(reportIdString)

        try {
            // Retrieve the report data from Firestore
            val documentSnapshot = fireStoreRef.get().await()
            val reportData = documentSnapshot.toObject(ReportData::class.java)

            if (reportData != null) {
                // If the report has an image URL, delete the corresponding image from Firebase Storage
                if (!reportData.reportImageUrl.isNullOrEmpty()) {
                    val storage = Firebase.storage
                    val storageRef = storage.reference
                    val imageRef = storageRef.child("images/${reportData.imageName}.jpg")

                    imageRef.delete().addOnSuccessListener {
                        // Image deleted successfully, now delete the document from Firestore
                        deleteFirestoreDocument(fireStoreRef, context, navController)
                    }.addOnFailureListener { e ->
                        // Handle the failure to delete the image
                        Toast.makeText(context, "Failed to delete image: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // No image associated with the report, delete the document from Firestore directly
                    deleteFirestoreDocument(fireStoreRef, context, navController)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteFirestoreDocument(
        fireStoreRef: DocumentReference,
        context: Context,
        navController: NavController
    ) {
        // Delete the document from Firestore
        fireStoreRef.delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Laporan telah di hapus!", Toast.LENGTH_SHORT).show()
                navController.navigate(route = Screens.MainScreen.route)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to delete document: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun getAllData(data: (List<ReportData>) -> Unit) = CoroutineScope(Dispatchers.IO).launch {
        val fireStoreRef = Firebase.firestore
            .collection("report")

        try {
            fireStoreRef.get()
                .addOnSuccessListener { result ->
                    val dataList = mutableListOf<ReportData>()

                    for (document in result) {
                        val reportData = document.toObject<ReportData>()
                        dataList.add(reportData)
                    }

                    data(dataList)
                }
                .addOnFailureListener { exception ->
                    // Handle failures
                    Log.e("getAllData", "Error getting documents: ", exception)
                }
        } catch (e: Exception) {
            Log.e("getAllData", "Exception: $e")
        }
    }

    fun uploadImagesAndSaveData(reportData: ReportData, imageUri: Uri?, context: Context) = CoroutineScope(Dispatchers.IO).launch {
        if (isAllFieldsFilled(reportData) && imageUri != null) {
            try {
                val firestoreRef = Firebase.firestore.collection("report").document(reportData.reportId.toString())

                val storage = Firebase.storage
                val storageRef = storage.reference

                val uniqueImageName = UUID.randomUUID().toString()
                val imageRef = storageRef.child("images/$uniqueImageName.jpg")

                val byteArray: ByteArray? = context.contentResolver
                    .openInputStream(imageUri)
                    ?.use { it.readBytes() }

                byteArray?.let {
                    val downloadUrl = uploadImage(imageRef, byteArray)

                    // Set the list of image URLs in the reportData
                    reportData.reportImageUrl = downloadUrl

                    reportData.imageName = uniqueImageName

                    // Save the data to Firestore
                    saveReportDataWithImages(firestoreRef, reportData, context)
                } ?: run {
                    // Handle the case where byteArray is null (image not loaded)
                    Toast.makeText(context, "Error: Image not selected", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            // Show a message indicating that all fields and images are required
            Toast.makeText(context, "All fields and images are required", Toast.LENGTH_SHORT).show()
        }
    }


    private suspend fun uploadImage(imageRef: StorageReference, byteArray: ByteArray): String = suspendCoroutine { continuation ->
        val uploadTask = imageRef.putBytes(byteArray)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result.toString()
                    continuation.resume(downloadUrl)
                } else {
                    continuation.resumeWithException(Exception("Error getting download URL"))
                }
            }
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }

    private fun saveReportDataWithImages(firestoreRef: DocumentReference, reportData: ReportData, context: Context) {
        // Save the data to Firestore
        firestoreRef.set(reportData).addOnSuccessListener {
            Toast.makeText(context, "Selamat, laporan anda telah masuk!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun isAllFieldsFilled(reportData: ReportData): Boolean {
        // Check if all fields in the ReportData are filled
        return reportData.reportTitle.isNotEmpty() &&
                reportData.reportDesc.isNotEmpty() &&
                reportData.reportLocation.isNotEmpty() &&
                reportData.reportImageUrl?.isNotEmpty() == true
    }
}
