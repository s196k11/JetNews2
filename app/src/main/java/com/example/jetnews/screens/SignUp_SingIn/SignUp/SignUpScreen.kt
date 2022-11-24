package com.example.jetnews.screens.SignUp_SingIn.SignUp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.R
import com.example.jetnews.screens.MainViewModel
import com.example.jetnews.screens.SignUp_SingIn.SignIn.TextRow
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpScreen(navController: NavHostController,mainViewModel: MainViewModel,auth: FirebaseAuth){
    val passwordVisible = rememberSaveable { mutableStateOf(true) }

    var email = rememberSaveable {mutableStateOf("")}
    var password = rememberSaveable {mutableStateOf("")}


    val context = LocalContext.current
    val message = rememberSaveable {mutableStateOf("")}

    val focusRequest = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current



    Box(modifier = Modifier.fillMaxSize().background(color = Color.Transparent)){
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
            .height(200.dp)) {

            Image(painter = painterResource(id = R.drawable.signup), contentDescription = null, contentScale = ContentScale.Crop)

            Text(
                text = "Create An Account", fontWeight = FontWeight.Bold, fontSize = 35.sp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

        }



        Spacer(modifier = Modifier.height(10.dp))

        Text(text = message.value, fontSize = 15.sp,color = Color.Red)

        Spacer(modifier = Modifier.height(10.dp))

        TextRow(text1 = "Email",
            text2 = "*",
            t1c = Color.Black,
            t2c = Color.Red,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 4.dp, start = 18.dp))
        OutlinedTextField(value = email.value, onValueChange = {email.value = it},
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onSearch = {focusManager.moveFocus(focusDirection = FocusDirection.Down)})

        )

        TextRow(text1 = "Password",
            text2 = "*",
            t1c = Color.Black,
            t2c = Color.Red,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 4.dp, start = 18.dp))
        OutlinedTextField(value = password.value, onValueChange = {password.value = it},
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onDone = {
//                if (password.value.length > 8){
//
//                    auth.createUserWithEmailAndPassword(email.value,password.value)
//                        .addOnCompleteListener() {task->
//                            if (task.isSuccessful){
//                                Log.d("FireBase",task.result.toString())
//                                navController.navigate(JetScreens.SelectCountyScreen.name)
//                            }else{
//                                Log.d("FireBase else",task.exception?.message.toString())
//                                if (task.exception?.message.toString() == "The email address is badly formatted."){
//                                    message.value = "Invalid Email"
//                                }else{
//                                    if (task.exception?.message.toString() == "The email address is already in use by another account."){
//                                        message.value = "Email is Already Registered"
//                                    }
//                                }
//                            }
//                        }
//                }else{
//                    Toast.makeText(context,"Password Must Contain 8 Characters", Toast.LENGTH_SHORT).show()
//                }

                focusManager.clearFocus(force = true)
            })

        )

        OutlinedButton(onClick = {

            if (password.value.length > 8){

                auth.createUserWithEmailAndPassword(email.value,password.value)
                    .addOnCompleteListener() {task->
                        if (task.isSuccessful){
                            Log.d("FireBase",task.result.toString())
                            navController.navigate(JetScreens.SelectCountyScreen.name){
                                navController.popBackStack()
                            }
                        }else{
                            Log.d("FireBase else",task.exception?.message.toString())
                            if (task.exception?.message.toString() == "The email address is badly formatted."){
                                message.value = "Invalid Email"
                            }else{
                                if (task.exception?.message.toString() == "The email address is already in use by another account."){
                                    message.value = "Email is Already Registered"
                                }
                            }
                        }
                    }
            }else{
                Toast.makeText(context,"Password Must Contain 8 Characters", Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFFFF9A3A)),
            elevation = ButtonDefaults.elevation(4.dp),
        ) {
            Text(text = "Sign Up",fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(20.dp))


//        Text(text = "Forget Password?",
//            modifier = Modifier
//                .padding(bottom = 20.dp)
//                .clickable { },
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp,
//            color = Color.Red.copy(alpha = 0.5f))


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
//

        Spacer(modifier = Modifier.height(25.dp))


        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .align(Alignment.End)
            .padding(15.dp)
            .fillMaxWidth()
        ) {
            Text(text = "Already Have an Account? ",
                color = Color.Gray.copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold)
            Text(text = "Sign in",
                color = Color.Red.copy(alpha = 0.5f),
                modifier = Modifier.clickable {
                    navController.navigate(JetScreens.SingInScreen.name)
                },
                fontWeight = FontWeight.Bold)
        }
    }
    }
}