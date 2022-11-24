package com.example.jetnews.screens.SplashScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetnews.Model.Article
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController,mainViewModel: MainViewModel,auth: FirebaseAuth){




    val currentUser = auth.currentUser
    val user = auth.currentUser?.email.toString().split("@")[0]
    val database = FirebaseDatabase.getInstance().getReference(user).child("UserData")


//    auth.signOut()



    LaunchedEffect(key1 = true, block = {
        delay(2000)

        if (currentUser == null) {
            navController.navigate(JetScreens.SingInScreen.name){
                popUpTo(JetScreens.SplashScreen.name){
                    inclusive = true
                }
            }
        }else{
            mainViewModel.getRealtimeData(database)
            navController.navigate(JetScreens.MainScreen.name) {
                popUpTo(JetScreens.SplashScreen.name) {
                    inclusive = true
                }
            }
        }
    }
    )

//    val database: DatabaseReference = Firebase.database.reference
//
//    database.child("users").child("md123").setValue("Saif")
//
//    val valueEvent = object : ValueEventListener{
//        override fun onDataChange(snapshot: DataSnapshot) {
//            val data = snapshot.getValue()
//            Log.d("ValueR",data.toString())
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            Log.d("onCancelled",error.toException().toString())
//        }
//    }
//
//    database.addValueEventListener(valueEvent)





    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Jet News", fontWeight = FontWeight.Bold, fontSize = 40.sp
        )
    }

    Log.d("FirebaseUsr", currentUser.toString())





//    val NewsData = produceState<DataOrException<News, Boolean, Exception>>(
//        initialValue = DataOrException(loading = true)) {
//        value = mainViewModel.getNews(country = "in", language = "hi", category = "sports")
//    }.value
//
//    Log.d("Response",NewsData.data.toString())
}