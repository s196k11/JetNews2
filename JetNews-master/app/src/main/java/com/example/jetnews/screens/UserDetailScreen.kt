package com.example.jetnews.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UserDetail(navController: NavController,mainViewModel: MainViewModel){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        TopAppBar(modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,modifier = Modifier.clickable { navController.popBackStack() })
        }

        Text(text = mainViewModel.userData.value?.userName.toString(),fontSize = 20.sp, fontWeight = FontWeight.SemiBold,modifier= Modifier.padding(10.dp))
        Text(text = mainViewModel.userData.value?.email.toString(),fontSize = 20.sp, fontWeight = FontWeight.SemiBold,modifier= Modifier.padding(10.dp))
        Text(text = mainViewModel.userData.value?.country.toString(),fontSize = 20.sp, fontWeight = FontWeight.SemiBold,modifier= Modifier.padding(10.dp))

    }
}