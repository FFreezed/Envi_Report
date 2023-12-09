package id.ac.umn.fadhil.envi_report_2_test

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import id.ac.umn.fadhil.envi_report_2_test.nav.NavGraph
import id.ac.umn.fadhil.envi_report_2_test.signIn.GoogleAuthUiClient
import com.example.envi_report.ui.theme.EnviReportTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import id.ac.umn.fadhil.envi_report_2_test.util_database.SharedViewModel
import com.google.android.gms.auth.api.identity.Identity

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnviReportTheme {
                val systemUIController = rememberSystemUiController()
                systemUIController.setSystemBarsColor(
                    color = Color.White,
                )
//
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xfff4f4f4)
                ) {
                    navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        googleAuthUiClient = googleAuthUiClient,
                    )
                }
            }
        }
    }
}