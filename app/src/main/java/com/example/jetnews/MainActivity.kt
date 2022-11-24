package com.example.jetnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetnews.Model.News
import com.example.jetnews.Navigation.NewsNavigation
import com.example.jetnews.screens.MainViewModel
import com.example.jetnews.ui.theme.JetNewsTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNewsTheme {

                val mainViewModel:MainViewModel = hiltViewModel()
                val auth = Firebase.auth
                NewsNavigation(mainViewModel = mainViewModel,auth)
//                val mainViewModel:MainViewModel = hiltViewModel()
//                val response =remember {mutableStateOf(listOf<News>())}
//                mainViewModel.news.observe(this){
//                    response.value = listOf(it)
//                }
//
//                Text(text = response.value.toString())

            }
        }
    }
}

//API Key = 2e5c50e075d44b61acfe63aad6d25997





@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetNewsTheme {
        Greeting("Android")
    }
}