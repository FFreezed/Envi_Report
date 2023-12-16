package id.ac.umn.fadhil.envi_report_2_test.nav

import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.ac.umn.fadhil.envi_report_2_test.screen.AddDataScreen
import id.ac.umn.fadhil.envi_report_2_test.screen.GetDataScreen
import id.ac.umn.fadhil.envi_report_2_test.screen.ListDataScreen
import id.ac.umn.fadhil.envi_report_2_test.screen.MainScreen
import id.ac.umn.fadhil.envi_report_2_test.screen.ProfileScreen
import id.ac.umn.fadhil.envi_report_2_test.screen.SignInScreen
import id.ac.umn.fadhil.envi_report_2_test.signIn.GoogleAuthUiClient
import id.ac.umn.fadhil.envi_report_2_test.signIn.SignInViewModel
import id.ac.umn.fadhil.envi_report_2_test.util_database.SharedViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    googleAuthUiClient: GoogleAuthUiClient
) {
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route
    ) {
        //Main Screen
        composable(
            route = Screens.MainScreen.route
        ) {
            MainScreen(
                navController = navController,
//                sharedViewModel = sharedViewModel
                userData = googleAuthUiClient.getSignedInUser()
            )
        }
        //get data Screen
        composable(
            route = Screens.GetDataScreen.route
        ) {
            GetDataScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
        //add data Screen
        composable(
            route = Screens.AddDataScreen.route
        ) {
            AddDataScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                userData = googleAuthUiClient.getSignedInUser()
            )
        }
//        List data screen
        composable(
            route = Screens.ListDataScreen.route
        ) {
            ListDataScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                userData = googleAuthUiClient.getSignedInUser()
            )
        }

        composable(
            route = Screens.ProfileScreen.route
        ) {
            val coroutineScope = rememberCoroutineScope()

            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            navController.context,
                            "Signed Out",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate(route = Screens.SignInScreen.route)
                    }
                }, navController = navController

            )
        }

        composable(
            route = Screens.SignInScreen.route
        ) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(key1 = Unit) {
                if(googleAuthUiClient.getSignedInUser() != null) {
//                    navController.navigate(Screens.ProfileScreen.route)
                    navController.navigate(Screens.MainScreen.route)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == ComponentActivity.RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )

                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful, ) {
                if(state.isSignInSuccessful) {
                    Toast.makeText(
                        navController.context,
                        "Sign in success",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate(Screens.ProfileScreen.route)
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }
    }
}