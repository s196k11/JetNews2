package com.example.jetnews.screens.HomeAction_search_home_profile.Home



import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.R
import com.example.jetnews.screens.MainViewModel
import android.util.Base64
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.example.jetnews.Model.Article
import com.example.jetnews.Model.News
import com.example.jetnews.Model.UserInfoData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay



@Composable
fun HomeScreen(navController: NavHostController, mainViewModel: MainViewModel,auth: FirebaseAuth) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val text = rememberSaveable { mutableStateOf("") }
    val searchedClicked = rememberSaveable {
        mutableStateOf(false)
    }
    val clicked = rememberSaveable { mutableStateOf("General") }
    val context = LocalContext.current

    val currentUser = auth.currentUser?.email.toString().split("@")[0]
    val focusRequest = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    val database = FirebaseDatabase.getInstance().getReference(currentUser).child("UserData")



    if (searchedClicked.value) {
        if (text.value.isNotEmpty()) {
            mainViewModel.getSearchedNews(language = "en", query = text.value)
        } else {
            Toast.makeText(context, "fill search box", Toast.LENGTH_LONG).show()
        }
    } else {
        mainViewModel.getTopHeadlines(country = mainViewModel.userData.value?.country.toString(), category = clicked.value)
    }

    mainViewModel.getRealtimeData(database)





    val news = mainViewModel.news.observeAsState().value

    Log.d("response", news.toString())



    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 7.dp, start = 1.dp, end = 1.dp)) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = text.value,
                onValueChange = { text.value = it },
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester = focusRequest),
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.LightGray.copy(
                    alpha = 0.5f),
                    unfocusedBorderColor = Color.DarkGray,
                    backgroundColor = Color.White.copy(alpha = 0.5f)),
                shape = CircleShape,
                textStyle = TextStyle(fontSize = 20.sp),
                singleLine = true,
                maxLines = 1,
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier.clickable {
                            focusManager.clearFocus(force = true)
                            mainViewModel.getSearchedNews(language = "en", text.value)
                            searchedClicked.value = true

                        })
                },
                trailingIcon = {
                    if (text.value.isNotEmpty()) {
                        Icon(imageVector = Icons.Default.Clear,
                            contentDescription = "Search Icon",
                            modifier = Modifier.clickable {
                                text.value = ""
                            })
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus(force = true)
                    mainViewModel.getSearchedNews(language = "en", text.value)
                    searchedClicked.value = true
                })
            )


        }

        Spacer(modifier = Modifier.height(10.dp))

        NewsTypes(clicked, searchedClicked)

//        Text(text = news.toString(),maxLines = 2)


        if (news != null) {
            LazyColumn {
                items(news.articles) { art ->
                    if (art?.title != null && art?.description != null && art?.content != null && art?.urlToImage != null){

                        NewsBox(imageURL = art.urlToImage,
                            title = art.title,
                            desc = art.description,
                            content = art.content) {

//                        navigation Call
//                            navController.navigate(JetScreens.DetailScreen.name + "/${listOf(art?.title.toString())}/${listOf(art?.description.toString())}/${listOf(art?.urlToImage.toString())}/${listOf(art?.content.toString())}")



                            mainViewModel.clickedNews.value = art
//
//                            mainViewModel.sendNewsFireB(user = currentUser,data = UserInfoData(userName = "null", email = auth.currentUser?.email.toString(), aboutMe = "null", country = "null", language = "en", article = listOf(art)))

//                            mainViewModel.pushListOfArticle(user = currentUser, art = art)

                            Log.d("currentUser",currentUser)
//                            mainViewModel.getSearchedNews(language = "en", art.title.slice(0..12))
//                            navController.navigate(JetScreens.DetailScreen.name + "/${Base64.encodeToString(art?.title.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.description.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.urlToImage.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.content.toString().toByteArray(),Base64.DEFAULT)}")

                            navController.navigate(JetScreens.DetailScreen.name){
                                popUpTo(JetScreens.DetailScreen.name) {
                                    inclusive = true
                                }
                            }

                            Log.d("NewsF",mainViewModel.userData.value.toString())

//                        navController.navigate(JetScreens.DetailScreen.name + "/title/desc/Imageurl/content")

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()

            }
        }
    }
}


@Composable
fun NewsTypes(clicked: MutableState<String>, searchedClicked: MutableState<Boolean>) {


    val typeOfNews = listOf("General",
        "Business",
        "Health",
        "politics",
        "Entertainment",
        "Science",
        "Sports",
        "Technology")


    LazyRow(modifier = Modifier.padding(bottom = 4.dp)) {
        items(typeOfNews) { typ ->

            if (typ == clicked.value) {
                CategoryBox(category = typ, color = Color(0xEEFF6822), textColor = Color.White) {
                    searchedClicked.value = false
                }

                Spacer(modifier = Modifier.width(5.dp))
            } else {
                CategoryBox(category = typ, color = Color.Transparent) {
                    clicked.value = typ
                    searchedClicked.value = false
                }
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }

    Log.d("clicked:-> ", clicked.value)
}


@Composable
fun CategoryBox(
    category: String,
    color: Color = Color.Transparent,
    textColor: Color = Color(0xEEFF6822),
    onClick: () -> Unit,
) {
    Surface(modifier = Modifier
        .clickable { onClick() }
        .wrapContentSize()
        .border(width = 2.dp, color = Color(0xEEFF6822), shape = CircleShape),
        elevation = 0.dp,
        color = color,
        shape = CircleShape) {
        Text(text = category,
            modifier = Modifier.padding(start = 17.dp, end = 17.dp, top = 4.dp, bottom = 4.dp),
            fontSize = 20.sp,
            color = textColor

        )
    }
}


@Preview
@Composable
fun NewsBox(
    imageURL: String = "",
    title: String = "",
    desc: String = "",
    modifier:Modifier = Modifier,
    onBookMarkClick: () -> Unit = {},
    content: String = "",
    onClick: () -> Unit = {},

    ) {
    val localCong = LocalConfiguration.current
    val screenH = localCong.screenHeightDp.dp
    val screenW = localCong.screenWidthDp.dp

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(screenH / 5)
        .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(9.dp))
        .clickable { onClick() }.then(modifier),
        shape = RoundedCornerShape(9.dp),
        color = Color.White
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            AsyncImage(model = imageURL, contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(screenW / 3)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Column() {
                Text(text = title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.h6
                )

                Text(text = desc,
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.DarkGray.copy(alpha = 0.8f),
                    modifier = Modifier.padding(5.dp),
                    maxLines = 3
                )
            }
        }
    }
}