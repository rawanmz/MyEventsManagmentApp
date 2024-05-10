package com.example.myeventsmanagmentapp.screens.auth


import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myeventsmanagmentapp.R
import com.example.myeventsmanagmentapp.navigation.Screens
import com.example.myeventsmanagmentapp.ui.theme.PrimaryColor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SplashScreen(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.intro_image), contentDescription = "")
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = "Dailoz",
            color = PrimaryColor,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
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
                .padding(12.dp),
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

@Composable
fun LoginWithGoogle(authViewModel: AuthViewModel) {
    //to do add it build config file
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
            authViewModel.isSignedIn.value = Screens.MainApp.route
        },
        onAuthError = {
            user = null
            authViewModel.isSignedIn.value = Screens.Authentication.route
        }
    )

    val token =
        "62354262835-nk2hem0okqad2jg6pbcoo4onprfou680.apps.googleusercontent.com"
    val context = LocalContext.current
    if (user == null) {
        authViewModel.isSignedIn.value = Screens.Authentication.route
        Image(
                modifier = Modifier.padding(4.dp).clickable {
                    val gso =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                },
                painter = painterResource(id = R.drawable.google_rounded),
                contentDescription = "google "
            )
    } else {
        authViewModel.isSignedIn.value = Screens.MainApp.route

//        Text("Welcome ${user?.displayName}")
//        AsyncImage(
//            model = user?.photoUrl,
//            contentDescription = null,
//            Modifier
//                .clip(CircleShape)
//                .size(45.dp)
//        )
//
//
//        Button(onClick = {
//            Firebase.auth.signOut()
//            user = null
//        }) {
//            Text("Sign out")
//        }


    }
}


@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            onAuthError(e)
        }
    }
}





