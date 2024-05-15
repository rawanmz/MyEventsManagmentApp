package com.example.myeventsmanagmentapp.screens.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myeventsmanagmentapp.R
import com.example.myeventsmanagmentapp.navigation.Screens
import com.example.myeventsmanagmentapp.ui.theme.PrimaryColor


@Composable
fun SplashScreen(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .semantics {
                testTag = "SplashScreen"
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(modifier = Modifier.semantics {
            testTag = "intro image"
        }, painter = painterResource(id = R.drawable.intro_image), contentDescription = null)
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .semantics {
                    contentDescription = "title text"
                },
            text = "Dailoz",
            color = PrimaryColor,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 22.dp)
                .semantics {
                    contentDescription = "description text"
                },
            textAlign = TextAlign.Center,
            text = "Plan what you will do to be more organized for today, tomorrow and beyond",
        )

        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                navController.navigate(Screens.Authentication.Login.route)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(12.dp)
                .semantics {
                    testTag = "Login Button"
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp), text = "Login",
                fontSize = 16.sp,
                color = Color.White
            )

        }
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    navController.navigate(Screens.Authentication.SignUp.route)

                },
            text = "Sign Up",
            color = PrimaryColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}



