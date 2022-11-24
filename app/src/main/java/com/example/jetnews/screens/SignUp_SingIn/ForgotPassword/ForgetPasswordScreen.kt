package com.example.jetnews.screens.SignUp_SingIn.ForgotPassword

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgetPasswordScreen(navController: NavHostController,auth:FirebaseAuth){
    val email = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val message = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null,modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(7.dp))
            Text(text = "Forgot Password",color = Color.Black.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold,modifier = Modifier.padding(7.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            ImageBox(Image = R.drawable.forgot_password)

            Text(text = "Enter Your Email Address \n to get Password reset link",color = Color.Black.copy(alpha = 0.8f))
            Spacer(modifier = Modifier.height(10.dp))

            if (message.value == "Please check you spam section"){
                Text(text = message.value,color = Color.DarkGray)
            }else{

                Text(text = message.value,color = Color.Red)
            }


            TextField(value = email.value, onValueChange = {email.value = it},
                placeholder = {Text(text = "Email")},
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions(onDone = {
                    auth.sendPasswordResetEmail(email.value)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                Toast.makeText(context,"link is sended",Toast.LENGTH_LONG).show()
                                message.value = "Please check you spam section"
                            }else{
                                Log.d("FirebaseP",task.exception?.message.toString())
                                if (task.exception?.message.toString() == "There is no user record corresponding to this identifier. The user may have been deleted."){
                                    message.value = "Email is not registered"
                                }else{
                                    message.value = "Invalid Email"
                                }
                            }
                        }
                })
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(onClick = {
                auth.sendPasswordResetEmail(email.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Toast.makeText(context,"link is sended",Toast.LENGTH_LONG).show()
                            message.value = "Please check you spam section"
                        }else{
                            Log.d("FirebaseP",task.exception?.message.toString())
                            if (task.exception?.message.toString() == "There is no user record corresponding to this identifier. The user may have been deleted."){
                                message.value = "Email is not registered"
                            }else{
                                message.value = "Invalid Email"
                            }
                        }
                    }
            }, colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFFFF6754))) {
                Text("Reset Password")
            }
        }



    }
}


@Composable
fun ImageBox(Image:Int){
    val conf = LocalConfiguration.current
    val screenH = conf.screenHeightDp.dp
    Box(
        modifier = Modifier.size(screenH/3)
    ){
        Image(painter = painterResource(id = Image), modifier = Modifier.fillMaxSize(),contentDescription = null, contentScale = ContentScale.Fit)
    }
}