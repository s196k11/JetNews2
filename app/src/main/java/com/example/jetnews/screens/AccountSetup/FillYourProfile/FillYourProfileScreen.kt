package com.example.jetnews.screens.AccountSetup.FillYourProfile

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.Model.Article
import com.example.jetnews.Model.Source
import com.example.jetnews.Model.UserInfoData
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.MainViewModel
import com.google.firebase.auth.FirebaseAuth

//@Preview(showSystemUi = true)
@Composable
fun FillYourProfileScreen(navController: NavHostController = rememberNavController(),mainViewModel: MainViewModel,auth:FirebaseAuth) {
    val focusManager = LocalFocusManager.current
    val focusRequest = remember { FocusRequester() }
    val conf = LocalConfiguration.current
    val screenH = conf.screenHeightDp.dp
    val userName = rememberSaveable {mutableStateOf("")}
    val aboutMe = rememberSaveable {mutableStateOf("")}
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember{
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){uri:Uri? ->
        imageUri.value = uri
    }

    val currentUser = auth.currentUser?.email.toString().split("@")[0]




    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFF3F3F3))) {

        Text(text = "Skip",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    navController.navigate(JetScreens.MainScreen.name){
                        popUpTo(JetScreens.MainScreen.name)
                    }
                }
                .align(Alignment.TopEnd)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
        ) {
            Box(modifier = Modifier
                .size(screenH / 3 - 20.dp)
                .padding(15.dp)
                .background(color = Color.Transparent)
            ) {
                Box(modifier = Modifier
                    .size(screenH / 3 - 20.dp)
                    .shadow(elevation = 1.dp, shape = CircleShape)
                ){
                    Icon(imageVector = Icons.Default.Person, contentDescription = null,modifier = Modifier.fillMaxSize())
                }

                FloatingActionButton(onClick = { launcher.launch("image/*") }, modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = 10.dp)) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))




            OutlinedTextField(value = userName.value, onValueChange = {userName.value = it}, modifier = Modifier
                .fillMaxWidth(),
                placeholder = { Text(text = "UserName") },
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White),
                singleLine = true,
                maxLines = 1,
                keyboardOptions= KeyboardOptions(imeAction = ImeAction.Next,keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(focusDirection = FocusDirection.Down)})
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(value = aboutMe.value, onValueChange = {aboutMe.value = it}, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp).focusRequester(focusRequester = focusRequest),
                placeholder = { Text(text = "About Me") },
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = {
                    if (userName.value.trim().isNotEmpty() && aboutMe.value.trim().isNotEmpty()){
                        mainViewModel.sendNewsFireB(user = currentUser,data = UserInfoData(
                            userName = userName.value.trim(), email = auth.currentUser?.email, country = mainViewModel.country.value.trim() ,
                            aboutMe = aboutMe.value.toString(), language = "en"))
                        focusManager.clearFocus(force = true)
                        navController.navigate(JetScreens.MainScreen.name){
                            popUpTo(JetScreens.FillProfileScreen.name){
                                inclusive = true
                            }
                        }
                    }else{
                        Toast.makeText(context,"Fill All Detail", Toast.LENGTH_SHORT).show()
                    }
                })
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(onClick = {
                                     if (userName.value.trim().isNotEmpty() && aboutMe.value.trim().isNotEmpty()){
                                         mainViewModel.sendNewsFireB(user = currentUser,data = UserInfoData(
                                             userName = userName.value.trim(), email = auth.currentUser?.email, country = mainViewModel.country.value.trim() ,
                                             aboutMe = aboutMe.value.toString(), language = "en"))
                                         navController.navigate(JetScreens.MainScreen.name){
                                             popUpTo(JetScreens.FillProfileScreen.name){
                                                 inclusive = true
                                             }
                                         }
                                     }else{
                                         Toast.makeText(context,"Fill All Detail", Toast.LENGTH_SHORT).show()
                                     }
            }, modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            ) {
                Text(text = "Next", fontSize = 22.sp)
            }


        }
    }
}