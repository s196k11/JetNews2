package com.example.jetnews.screens.SignUp_SingIn.SignIn

import android.graphics.Paint.Align
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import com.example.jetnews.R
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.MainViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SignInScreen(navController: NavController,mainViewModel: MainViewModel,auth: FirebaseAuth) {


    val passwordVisible = rememberSaveable { mutableStateOf(true) }

    val email = rememberSaveable {mutableStateOf("")}
    val password = rememberSaveable {mutableStateOf("")}
    val context = LocalContext.current
    val message = rememberSaveable {
        mutableStateOf("")
    }

    val focusRequest = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Transparent)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                .verticalScroll(rememberScrollState())
                .background(color = Color(0xffEFF7F6))
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)) {

                Image(painter = painterResource(id = R.drawable.login), contentDescription = null, contentScale = ContentScale.Crop)
                Text(
                    text = "Lets Sign In",fontWeight = FontWeight.Bold, fontSize = 35.sp,modifier = Modifier.align(Alignment.TopCenter)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = message.value,
                color = Color.Red,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp)

            Spacer(modifier = Modifier.height(10.dp))

            TextRow(text1 = "Email",
                text2 = "*",
                t1c = Color.Black,
                t2c = Color.Red,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 4.dp, start = 18.dp))
            OutlinedTextField(value = email.value, onValueChange = { email.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = CircleShape,
                placeholder = { Text(text = "Email") },
                singleLine = true,
                maxLines = 1,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email,
                        contentDescription = "Email Icons")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.moveFocus(focusDirection = FocusDirection.Down)
                })

            )

            TextRow(text1 = "Password",
                text2 = "*",
                t1c = Color.Black,
                t2c = Color.Red,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 4.dp, start = 18.dp))
            OutlinedTextField(value = password.value, onValueChange = { password.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp).focusRequester(focusRequester = focusRequest),
                shape = CircleShape,
                placeholder = { Text(text = "Password") },
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    if (passwordVisible.value) {
                        Icon(painter = painterResource(id = R.drawable.eye),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { passwordVisible.value = false })
                    } else {
                        Icon(painter = painterResource(id = R.drawable.close_eye),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { passwordVisible.value = true })
                    }
                },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                visualTransformation = if (passwordVisible.value) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus(force = true)
                })

            )

            OutlinedButton(
                onClick = {
                    auth.signInWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener() { task ->
                            if (task.isSuccessful) {
                                Log.d("FireBase", task.result.toString())
                                message.value = ""
                                navController.navigate(JetScreens.MainScreen.name) {
                                    popUpTo(JetScreens.SingInScreen.name){
                                        inclusive = true
                                    }
                                }
                            } else {
                                Log.d("FireBaseElse", task.exception?.message.toString())
                                message.value = "Invalid UserId and Password"

                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(59.dp),
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFFFF9A3A)),
                elevation = ButtonDefaults.elevation(4.dp),
            ) {
                Text(text = "Sign In", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Forget Password?",
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .clickable {
                        navController.navigate(JetScreens.ForgotPasswordScreen.name)
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Red.copy(alpha = 0.5f))


            //********Implement later
//        Text(text = "or Continue with", modifier = Modifier.padding(bottom = 45.dp))
//
//        Row() {
//            Icon(painter = painterResource(id = R.drawable.google),
//                contentDescription = null,
//                tint = Color.Unspecified,
//                modifier = Modifier
//                    .size(65.dp)
//                    .clickable { }
//            )
//
//
//            Spacer(modifier = Modifier.width(85.dp))
//
//            Icon(painter = painterResource(id = R.drawable.facebook),
//                contentDescription = null,
//                tint = Color.Unspecified,
//                modifier = Modifier
//                    .size(65.dp)
//                    .clickable { }
//            )
//        }

            Spacer(modifier = Modifier.height(25.dp))


            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .align(Alignment.End)
                .padding(15.dp)
                .fillMaxWidth()
            ) {
                Text(text = "Don't Have an Account? ",
                    color = Color.Gray.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold)
                Text(text = "Sign up",
                    color = Color.Red.copy(alpha = 0.5f),
                    modifier = Modifier.clickable {
                        navController.navigate(JetScreens.SingUpScreen.name)
                    },
                    fontWeight = FontWeight.Bold)
            }
        }

    }
}


@Composable
fun TextRow(text1: String, text2: String, t1c: Color, t2c: Color, t1FontW:FontWeight = FontWeight.Normal, modifier: Modifier) {
    Row(modifier = modifier) {
        Text(text = text1, color = t1c, fontWeight = t1FontW)
        Text(text = text2, color = t2c)
    }
}