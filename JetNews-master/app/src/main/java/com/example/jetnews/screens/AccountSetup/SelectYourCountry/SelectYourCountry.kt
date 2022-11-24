package com.example.jetnews.screens.AccountSetup.SelectYourCountry

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetnews.Model.UserInfoData
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.MainViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SelectYourCountry(navController:NavHostController,mainViewModel: MainViewModel,auth: FirebaseAuth){
    val text = rememberSaveable {
        mutableStateOf("")
    }

    val currentUser = auth.currentUser?.email.toString().split("@")[0]

    val mapOfCountries = mapOf("Argentina" to "ar","Australia" to "au","Austria" to "at","Belgium" to "be",
        "Brazil" to "br","Bulgaria" to "bg","Canada" to "ca","Chine" to "cn","Colombia" to "co","Cuba" to "cu","Czech Republic" to "cz",
        "Egypt" to "eg","France" to "fr","Germany" to "de","Greece" to "gr","Hong Kong" to "hk","Hungary" to "hu","India" to "in","Indonesia" to "id","Ireland" to "ie","Israel" to "il","Italy" to "it","Japan" to "jp",
        "Latvia" to "iv","Lithuania" to "lt","Malaysia" to "my","Mexico" to "mx","Morocco" to "ma","Netherlands" to "nl","New Zealand" to "nz","Nigeria" to "ng","Norway" to "no","Philippines" to "ph",
        "Poland" to "pl","Portugal" to "pt","Romania" to "ro","Russia" to "ru","Saudi Arabia" to "sa","Serbia" to "rs","Singapore" to "sg","Slovakia" to "sk","South Africa" to "za","South Korea" to "kr",
        "Sweden" to "se","Switzerland" to "ch","Taiwan" to "tw","Thailand" to "th","Turkey" to "tr","UAE" to "ae","Ukraine" to "ua","United Kingdom" to "uk","United States" to "us","Venuzuela" to "ve"
    )

    val listOfCountries = listOf<String>("Argentina","Australia","Austria","Belgium",
        "Brazil","Bulgaria","Canada" ,"Chine" ,"Colombia" ,"Cuba" ,"Czech Republic",
        "Egypt","France","Germany" ,"Greece" ,"Hong Kong","Hungary","India","Indonesia" ,"Ireland" ,"Israel","Italy","Japan",
        "Latvia","Lithuania" ,"Malaysia","Mexico" ,"Morocco","Netherlands" ,"New Zealand" ,"Nigeria","Norway","Philippines",
        "Poland","Portugal","Romania","Russia","Saudi Arabia","Serbia","Singapore" ,"Slovakia","South Africa","South Korea",
        "Sweden","Switzerland" ,"Taiwan" ,"Thailand","Turkey","UAE","Ukraine","United Kingdom","United States","Venuzuela")

    val selectedCountry = rememberSaveable {
        mutableStateOf("")
    }

    //selected country code
    val s = mapOfCountries[selectedCountry.value]

    mainViewModel.selectedCountry(countryCode = s.toString())
    Log.d("selectedC", "     ${s.toString()}")

//    mainViewModel.sendNewsFireB(user = currentUser, data = UserInfoData(country = s, article = listOf()))


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {

                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null,modifier =Modifier.clickable { navController.popBackStack() })

                OutlinedTextField(value = text.value,
                    onValueChange = { text.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text(text = "Find Your Country") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    maxLines = 1,
                    singleLine = true
                )
            }


            LazyColumn(modifier = Modifier.padding(4.dp)) {
                items(listOfCountries) { item ->
                    if (text.value != ""){
                        if (text.value.uppercase() in item.uppercase()) {
                            if (selectedCountry.value == item) {
                                Country(name = item, color = Color.Blue, borderWidth = 3) {
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                            } else {
                                Country(name = item) {
                                    selectedCountry.value = item
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }else{
                        if (selectedCountry.value == item) {
                            Country(name = item, color = Color.Blue, borderWidth = 3) {
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }else{
                            Country(name = item) {
                                selectedCountry.value = item
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }

        }
        OutlinedButton(onClick = {
            mainViewModel.sendNewsFireB(user = currentUser, data = UserInfoData(country = s))
            navController.navigate(JetScreens.FillProfileScreen.name){navController.popBackStack()}
        },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(60.dp)
                .padding(5.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFFFC0156))

        ) {
            Text(text = "Submit",color = Color.Yellow)
        }
    }
}



@Composable
fun Country(name:String,borderWidth:Int = 2,color: Color = Color(0xEEFF6876),onClick:() -> Unit){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clickable { onClick() }
        ,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = borderWidth.dp,color = color)
    ) {
        Text(text = name, textAlign = TextAlign.Start,modifier = Modifier.padding(10.dp, top = 10.dp))
    }
}