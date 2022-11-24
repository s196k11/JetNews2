package com.example.jetnews.screens.HomeAction_search_home_profile.Account

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.MainViewModel
import java.time.format.TextStyle


@Composable
fun AccountScreen(navController:NavHostController = rememberNavController(),mainViewModel: MainViewModel){
    val conf = LocalConfiguration.current
    val screenH = conf.screenHeightDp.dp

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {mutableStateOf<Bitmap?>(null)}

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()){uri: Uri? ->
        imageUri = uri
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFFFFFFF)), horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(backgroundColor = Color.White.copy(alpha = 0.7f), elevation = 0.dp) {

            Text(text = "My Profile", fontSize = 20.sp,fontWeight = FontWeight.SemiBold,color = Color.Black,textAlign = TextAlign.Start,modifier = Modifier.padding(4.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            navController.navigate(JetScreens.EditProfileScreen.name)
                        }
                )
                Spacer(modifier = Modifier.width(19.dp))
                Icon(imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            navController.navigate(JetScreens.SettingScreen.name)
                        })
            }
        }

        Box(modifier = Modifier
            .size(screenH / 3 - 20.dp)
            .padding(15.dp)
            .background(color = Color.Transparent)
        ) {
            Box(modifier = Modifier
                .size(screenH / 3 - 20.dp)
                .shadow(elevation = 0.dp, shape = CircleShape)
            ){
                Icon(imageVector = Icons.Default.Person, contentDescription = null,modifier = Modifier.fillMaxSize())
            }

            FloatingActionButton(onClick = {launcher.launch("image/*") }, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 1.dp, bottom = 1.dp)
                .size(screenH / 12)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }

        }

        imageUri?.let{
            if(Build.VERSION.SDK_INT < 28){
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)
            }else{
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
                Log.d("BitmapValue",bitmap.value.toString())
            }

            bitmap.value?.let { btm ->
                Image(bitmap = btm.asImageBitmap(), contentDescription = null,modifier = Modifier.size(200.dp))
            }

        }

        Log.d("image Uri", imageUri.toString())

        Text(text = mainViewModel.userData.value?.userName.toString(), fontWeight = FontWeight.SemiBold, fontSize = 25.sp,modifier = Modifier.padding(start = 8.dp))

        TextBox(title = "About Me",text = mainViewModel.userData.value?.aboutMe.toString())

        Log.d("dataaaaaaaa",mainViewModel.userData.value.toString())

    }
}


@Composable
fun TextBox(title:String,text:String){
    Row(modifier = Modifier.fillMaxWidth().height(intrinsicSize = IntrinsicSize.Max).padding(10.dp)) {
        Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.Start) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = text)
            }
        }
    }
}

//570102304566-cpm3b74bn2cjun0ks1mvvodkqei231rm.apps.googleusercontent.com