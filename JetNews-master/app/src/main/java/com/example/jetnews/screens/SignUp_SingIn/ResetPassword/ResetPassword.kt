package com.example.jetnews.screens.SignUp_SingIn.ResetPassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetnews.Navigation.JetScreens

@Composable
fun ResetPasswordScreen(navController:NavHostController){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Reset Password Screen", fontWeight = FontWeight.Bold, fontSize = 35.sp,
            modifier = Modifier.clickable { navController.navigate(JetScreens.ForgotPasswordScreen.name) }
        )
    }
}