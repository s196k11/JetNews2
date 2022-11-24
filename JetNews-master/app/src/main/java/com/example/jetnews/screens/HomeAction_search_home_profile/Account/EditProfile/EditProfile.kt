package com.example.jetnews.screens.HomeAction_search_home_profile.Account.EditProfile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.MainViewModel
import com.example.jetnews.screens.SignUp_SingIn.SignIn.TextRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//Apearence
//logout


@Composable
fun EditProfile(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = hiltViewModel(),
    auth: FirebaseAuth
) {
    val conf = LocalConfiguration.current
    val screenH = conf.screenHeightDp.dp
    val userName = remember { mutableStateOf(mainViewModel.userData.value?.userName.toString()) }
    val aboutMe = remember { mutableStateOf(mainViewModel.userData.value?.aboutMe.toString()) }
    val email = remember { mutableStateOf(mainViewModel.userData.value?.email.toString()) }

    val focusRequest = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val currentUser = auth.currentUser?.email.toString().split("@")[0]

    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current
    val bitmap = remember {mutableStateOf<Bitmap?>(null)}

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()){ uri: Uri? ->
        imageUri = uri
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(backgroundColor = Color.White.copy(alpha = 0.7f)) {

            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { navController.navigate(JetScreens.AccountScreen.name) })


            Text(text = "Edit Profile",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(5.dp))

        }

        Box(modifier = Modifier
            .size(screenH / 3 - 20.dp)
            .padding(15.dp)
            .background(color = Color.Transparent)
        ) {
            Box(modifier = Modifier
                .size(screenH / 3 - 20.dp)
            ) {

                if (bitmap.value != null){
                    imageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images
                                .Media.getBitmap(context.contentResolver,it)

                        } else {
                            val source = ImageDecoder
                                .createSource(context.contentResolver,it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }

                        bitmap.value?.let { btm ->
                            Image(bitmap = btm.asImageBitmap(), contentDescription = null)
                            Log.d("bitmapImage", btm.asImageBitmap().toString())
                            Log.d("ImageURI",imageUri.toString())
                        }

                    }
                }else {
                    Icon(imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize())
                }

            }

            FloatingActionButton(onClick = { launcher.launch("image/*") }, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 15.dp, bottom = 15.dp)) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }



        Spacer(modifier = Modifier.heightIn(10.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp), color = Color.Gray)
        Spacer(modifier = Modifier.height(10.dp))

        TextRow(text1 = "UserName",
            text2 = "*",
            t1c = Color.Gray,
            t2c = Color.Red,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 9.dp))
        OutlinedTextField(value = userName.value,
            onValueChange = {userName.value = it},
            placeholder = { Text(text = "UserName") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 9.dp, end = 12.dp)
                .focusRequester(focusRequester = focusRequest),
            shape = CircleShape,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(
                0xFFFF683A), focusedBorderColor = Color(0xFFFF683A)),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(focusDirection = FocusDirection.Down)
            })
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextRow(text1 = "About Me",
            text2 = "*",
            t1c = Color.Gray,
            t2c = Color.Red,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 9.dp))
        OutlinedTextField(value = aboutMe.value,
            onValueChange = {aboutMe.value = it},
            placeholder = { Text(text = "About Me") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 9.dp, end = 12.dp)
                .focusRequester(focusRequester = focusRequest),
            shape = CircleShape,
            colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color(
                0xFFFF683A), focusedBorderColor = Color(0xFFFF683A)),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(force = true)
                mainViewModel.editProfile(user = currentUser,userName.value,aboutMe.value)
                navController.navigate(JetScreens.AccountScreen.name)
            })
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(onClick = {
                                 mainViewModel.editProfile(user = currentUser,userName.value,aboutMe.value)
            navController.navigate(JetScreens.AccountScreen.name)
        },
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(
                0xFFFF683A))) {
            Text(text = "Save Changes")
        }
    }
}