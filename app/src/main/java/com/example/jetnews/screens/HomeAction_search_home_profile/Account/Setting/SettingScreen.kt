package com.example.jetnews.screens.HomeAction_search_home_profile.Account.Setting

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetnews.Model.UserInfoData
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

@Composable
fun SettingScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    auth: FirebaseAuth,
) {
    val localConf = LocalConfiguration.current
    val screenW = localConf.screenWidthDp.dp


    val l = listOf("Edit Profile", "Select Country","Change Password", "Detail", "Help")

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Transparent)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(backgroundColor = Color.White.copy(alpha = 0.7f)) {

                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable { navController.navigate(JetScreens.AccountScreen.name) })


                Text(text = "Settings",
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(5.dp))
            }



            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(l) { item ->
                    Divider(modifier = Modifier.width(screenW - 20.dp), color = Color.LightGray)
                    Spacer(modifier = Modifier.height(5.dp))
                    SettingBox(name = item) {
                        if (item == "Edit Profile") {
                            navController.navigate(JetScreens.EditProfileScreen.name)
                        }
                        if (item == "Change Password"){
                            navController.navigate(JetScreens.ForgotPasswordScreen.name)
                        }
                        if (item == "Help"){
                            navController.navigate(JetScreens.HelpScreen.name)
                        }
                        if (item == "Detail"){
                            navController.navigate(JetScreens.UserDetailScreen.name)
                        }
                        if (item == "Select Country"){
                            navController.navigate(JetScreens.ChangeCountryScreen.name)
                        }


                    }
                    Spacer(modifier = Modifier.height(5.dp))


                }
            }


        }
        Text(text = "Log Out",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    auth.signOut()
                    navController.navigate(JetScreens.SingInScreen.name) {
                        popUpTo(JetScreens.SettingScreen.name) {
                            inclusive = true
                        }
                    }
                })

    }

}


@Composable
fun SettingBox(name: String, onClick: () -> Unit) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clickable { onClick() },
        color = Color.Transparent
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.padding(start = 10.dp))

            Text(text = name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 15.dp))
        }
    }
}