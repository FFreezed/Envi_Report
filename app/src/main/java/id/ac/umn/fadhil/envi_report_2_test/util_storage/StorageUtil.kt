package id.ac.umn.fadhil.envi_report_2_test.util_storage

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class StorageUtil{
    companion object {

        fun uploadToStorage(uri: Uri, context: Context, type: String) {
            val storage = Firebase.storage
            val firestore = FirebaseFirestore.getInstance()


            // Create a storage reference from our app
            val storageRef = storage.reference

            val unique_image_name = UUID.randomUUID()
            val spaceRef: StorageReference

            if (type == "image"){
                spaceRef = storageRef.child("images/$unique_image_name.jpg")
            }else{
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let{

                val uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...

                    spaceRef.downloadUrl.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUrl = task.result.toString()

                            // Save the download URL to Firestore
                            saveUrlToFirestore(downloadUrl, type)
                        } else {
                            Toast.makeText(
                                context,
                                "Error getting download URL",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    Toast.makeText(
                        context,
                        "upload successed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        private fun saveUrlToFirestore(downloadUrl: String, type: String) {
            // You can customize this part based on your Firestore structure
            val firestore = FirebaseFirestore.getInstance()
            val collectionName = if (type == "image") "imageCollection" else "videoCollection"

            // Create a new document in the specified collection with the download URL
            firestore.collection(collectionName)
                .add(mapOf("url" to downloadUrl))
//                .addOnSuccessListener {
//                    // Handle successful Firestore document creation
//                }
//                .addOnFailureListener {
//                    // Handle unsuccessful Firestore document creation
//                }
        }
    }
}