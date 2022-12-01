package com.example.jetnews.screens.HomeAction_search_home_profile.Detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.jetnews.Navigation.JetScreens
import com.example.jetnews.screens.MainViewModel
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.jetnews.screens.HomeAction_search_home_profile.Home.NewsBox
import com.example.jetnews.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun DetailScreen(
//    title: String,
//    desc: String,
//    imageURL: String,
//    content: String,
//    navController: NavHostController,
//    mainViewModel: MainViewModel,
//) {
//
//    val c = rememberSaveable { mutableStateOf(0) }
//    val conf = LocalConfiguration.current
//    val screenH = conf.screenHeightDp.dp
//
////    mainViewModel.getSearchedNews(language = "en", query = String(Base64.decode(title,Base64.DEFAULT)).slice(0..12))
//    val news = mainViewModel.news.observeAsState().value
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        TopAppBar(backgroundColor = Color.White) {
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = null,
//                modifier = Modifier.clickable { navController.navigate(JetScreens.HomeScreen.name) }
//            )
//        }
//
//        Column(modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//        ) {
//
////            AsyncImage(model = String(Base64.decode(imageURL,Base64.DEFAULT)), contentDescription = null,
////                contentScale = ContentScale.Crop,
////                modifier = Modifier
////                    .padding(10.dp)
////                    .fillMaxWidth()
////                    .height(screenH / 3 + 45.dp)
////                    .clip(shape = RoundedCornerShape(12.dp))
////            )
////
////            Text(text = String(Base64.decode(title,Base64.DEFAULT)),
////                fontWeight = FontWeight.Bold,
////                fontSize = 34.sp,
////                color = Color.Black.copy(alpha = 0.8f),
////                modifier= Modifier.padding(10.dp)
////            )
////
////            Spacer(modifier = Modifier.height(5.dp))
////
////            Text(text = String(Base64.decode(content,Base64.DEFAULT)),
////                fontWeight = FontWeight.Normal,
////                fontSize = 20.sp,
////                color = Color.DarkGray.copy(0.8f),
////                modifier = Modifier.padding(10.dp)
////            )
//
//            AsyncImage(model = mainViewModel.clickedNews.value?.urlToImage, contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .fillMaxWidth()
//                    .height(screenH / 3 + 45.dp)
//                    .clip(shape = RoundedCornerShape(12.dp))
//            )
//
//            Text(text = mainViewModel.clickedNews.value?.title.toString(),
//                fontWeight = FontWeight.Bold,
//                fontSize = 34.sp,
//                color = Color.Black.copy(alpha = 0.8f),
//                modifier= Modifier.padding(10.dp)
//            )
//
//            Spacer(modifier = Modifier.height(5.dp))
//
//            Text(text = mainViewModel.clickedNews.value?.content.toString(),
//                fontWeight = FontWeight.Normal,
//                fontSize = 20.sp,
//                color = Color.DarkGray.copy(0.8f),
//                modifier = Modifier.padding(10.dp)
//            )
//
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//
//
//            if (news?.articles != null){
//
//                Text(text = "Related", fontSize = 25.sp, color = Color.Black.copy(alpha = 0.8f), fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
//
//                for (art in news.articles.subList(0,10)){
//                    if (art?.title != null && art?.description != null && art?.content != null && art?.urlToImage != null){
//
//                        NewsBox(imageURL = art.urlToImage,
//                            title = art.title,
//                            desc = art.description,
//                            content = art.content,
//                            modifier = Modifier.padding(10.dp)
//                        ) {
//
////                        navigation Call
////                            navController.navigate(JetScreens.DetailScreen.name + "/${listOf(art?.title.toString())}/${listOf(art?.description.toString())}/${listOf(art?.urlToImage.toString())}/${listOf(art?.content.toString())}")
//
//
//
//                            navController.navigate(JetScreens.DetailScreen.name + "/${Base64.encodeToString(art?.title.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.description.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.urlToImage.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.content.toString().toByteArray(),Base64.DEFAULT)}")
//
//
//
////                        navController.navigate(JetScreens.DetailScreen.name + "/title/desc/Imageurl/content")
//
//                        }
//                        Spacer(modifier = Modifier.height(5.dp))
//
//                    }
//                }
//            }
//    }
//}
//}


@Composable
fun DetailScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    auth:FirebaseAuth
) {

    val c = rememberSaveable { mutableStateOf(0) }
    val conf = LocalConfiguration.current
    val screenH = conf.screenHeightDp.dp

    mainViewModel.getSearchedNews(language = "en",
        query = mainViewModel.clickedNews.value?.title.toString().slice(1..17))

    val news = mainViewModel.news.observeAsState().value
    val added = remember {mutableStateOf(false)}

    val currentUser = auth.currentUser?.email.toString().split("@")[0]

    val fireDatabase = FirebaseDatabase.getInstance().getReference(currentUser)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TopAppBar(backgroundColor = Color.White) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navController.popBackStack() }
                )

                Log.d("Articles ","  ${mainViewModel.userData.value.toString()}")

//

                added.value = mainViewModel.clickedNews?.value in mainViewModel.articleFire

                if (added.value == false){
                    Image(painter = painterResource(id = R.drawable.bookmark1), contentDescription = null,modifier = Modifier.size(34.dp).clickable {
                        mainViewModel.pushListOfArticle(user = currentUser,art = mainViewModel.clickedNews.value!!)
                    })
                }else{
                    Image(painter = painterResource(id = R.drawable.bookmark_added), contentDescription = null,modifier = Modifier.size(34.dp).clickable{
                        fireDatabase.child("UserData").child("article")
                    })
                }


            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {


            AsyncImage(model = mainViewModel.clickedNews.value?.urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(screenH / 3 + 45.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
            )

            Text(text = mainViewModel.clickedNews.value?.title.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                color = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier.padding(10.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = mainViewModel.clickedNews.value?.content.toString(),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = Color.DarkGray.copy(0.8f),
                maxLines = 50,
                modifier = Modifier.padding(10.dp)
            )


            Spacer(modifier = Modifier.height(10.dp))

            if (news?.articles != null) {
                Text(text = "Related",
                    fontSize = 25.sp,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp))

                if (news.articles.size in 1..9) {
                    for (art in news.articles.subList(0, news.articles.size)) {
                        if (art?.title != null && art?.description != null && art?.content != null && art?.urlToImage != null) {

                            NewsBox(imageURL = art.urlToImage,
                                title = art.title,
                                desc = art.description,
                                content = art.content) {

//                        navigation Call
//                            navController.navigate(JetScreens.DetailScreen.name + "/${listOf(art?.title.toString())}/${listOf(art?.description.toString())}/${listOf(art?.urlToImage.toString())}/${listOf(art?.content.toString())}")


                                mainViewModel.clickedNews.value = art
                                mainViewModel.getSearchedNews(language = "en",
                                    art.title.slice(0..12))
//                            navController.navigate(JetScreens.DetailScreen.name + "/${Base64.encodeToString(art?.title.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.description.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.urlToImage.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.content.toString().toByteArray(),Base64.DEFAULT)}")

                                navController.navigate(JetScreens.DetailScreen.name)


//                        navController.navigate(JetScreens.DetailScreen.name + "/title/desc/Imageurl/content")

                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                } else {
                    if (news.articles.size == 0) {

                    } else {
                        for (art in news.articles.subList(0, 10)) {
                            if (art?.title != null && art?.description != null && art?.content != null && art?.urlToImage != null) {

                                NewsBox(imageURL = art.urlToImage,
                                    title = art.title,
                                    desc = art.description,
                                    content = art.content) {

//                        navigation Call
//                            navController.navigate(JetScreens.DetailScreen.name + "/${listOf(art?.title.toString())}/${listOf(art?.description.toString())}/${listOf(art?.urlToImage.toString())}/${listOf(art?.content.toString())}")


                                    mainViewModel.clickedNews.value = art
                                    mainViewModel.getSearchedNews(language = "en",
                                        art.title.slice(0..12))
//                            navController.navigate(JetScreens.DetailScreen.name + "/${Base64.encodeToString(art?.title.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.description.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.urlToImage.toString().toByteArray(),Base64.DEFAULT)}/${Base64.encodeToString(art?.content.toString().toByteArray(),Base64.DEFAULT)}")

                                    navController.navigate(JetScreens.DetailScreen.name)


//                        navController.navigate(JetScreens.DetailScreen.name + "/title/desc/Imageurl/content")

                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}


