package id.ac.umn.fadhil.envi_report_2_test.screen

import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.envi_report.ui.theme.EnviReportTheme
import id.ac.umn.fadhil.envi_report_2_test.R
import id.ac.umn.fadhil.envi_report_2_test.signIn.SignInState
import id.ac.umn.fadhil.envi_report_2_test.util_database.SharedViewModel
import kotlinx.coroutines.launch

@Preview
@Composable
fun SignInScreen (){
    val signInState = SignInState(true)
    EnviReportTheme {
        SignInScreen(state = signInState, onSignInClick = {
            print("Yes")
        })
    }
}

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.enviro),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clip(shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selamat Datang",
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .wrapContentSize(Alignment.Center)
                .padding(bottom = 32.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ExtendedFloatingActionButton(
                onClick = { onSignInClick() },
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.google), // Replace with your image resource ID
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Adjust size as needed
                    )
                },
                text = { Text(text = "Sign In Google") },
                containerColor = colorResource(id = R.color.buttonColor)
            )
        }
    }

}