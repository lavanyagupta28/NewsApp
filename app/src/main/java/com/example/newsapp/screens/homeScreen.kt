package com.example.newsapp.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.google.gson.Gson
import java.net.URLEncoder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun parseMockJson(): NewsResponse {
    val gson = Gson()
    return gson.fromJson(mockJsonResponse, NewsResponse::class.java)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavHostController) {
    val newsResponse = parseMockJson()
    val articles: List<Article> = newsResponse.articles


    var enabledCategory by remember { mutableStateOf<Int?>(null) }
//    articles.forEach { article ->
//        Log.d("TAG", article.title)
//    }

    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
    ) {
        Row(modifier = Modifier) {
            Text(
                text = "NewsApp",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                modifier = Modifier
                    .padding(start = 10.dp, top = 20.dp, bottom = 20.dp)
                    .weight(1f)
            )
            ProfileButton {
                navController.navigate("profileScreen")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        ButtonUi(modifier = Modifier.padding(5.dp),
            enabledCategory = enabledCategory,
            onCategorySelected = { selectedCategory ->
                enabledCategory = selectedCategory
            })
        Spacer(modifier = Modifier.height(10.dp))
//    LazyColumn(modifier = Modifier) {
////        items(articles) { article ->
////            NewsItem(article = article, onClick = {
////                val articleJson = Gson().toJson(article)
////                navController.navigate("detailScreen/$articleJson")
////            })
////
////        }
//
//    }

        LazyColumn(modifier = Modifier) {
            items(articles) { article ->
                NewsItem(article = article, onClick = {
                    val articleJson = Gson().toJson(article)
                    val encodedArticleJson = URLEncoder.encode(articleJson, "UTF-8")
                    navController.navigate("detailScreen/$encodedArticleJson")
                })

            }
        }

    }

}


@Composable
fun ButtonUi(
    modifier: Modifier = Modifier, enabledCategory: Int?, onCategorySelected: (Int) -> Unit
) {
//    val buttonData = listOf(
//        Pair("Tech", false),
//        Pair("Sports", false),
//        Pair("Cinema", false)
//    )

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        buttonList.forEachIndexed { index, buttonItem ->
            CustomButton(label = buttonItem.lablel,
                isEnabled = (enabledCategory == index),
                modifier = modifier
                    .weight(1f)
                    .height(45.dp),
                onClick = { onCategorySelected(index) })
        }
    }
}

@Composable
fun CustomButton(
    label: String, isEnabled: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit = {}
) {

    Button(
        onClick = onClick,
        modifier = modifier.border(
                width = 2.dp, color = Color.Black, shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) Color.Black else Color.White,
            contentColor = if (isEnabled) Color.White else Color.Black
        ),

        ) {
        Text(text = label)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsItem(article: Article, onClick: () -> Unit) {
    val instant = Instant.parse(article.publishedAt)
    val publishedLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    val currentDate = LocalDate.now()
    val differenceDays = ChronoUnit.DAYS.between(publishedLocalDate, currentDate)
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        val imageUrl = if (LocalInspectionMode.current) {
            "https://via.placeholder.com/200" // Mock URL for preview
        } else {
            article.urlToImage
        }
        Box() {
            imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Article Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            article.source.name?.let {
                Text(
                    text = it, color = Color.White, modifier = Modifier.padding(15.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        Text(
            text = article.title,
            textAlign = TextAlign.Justify,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            article.author?.let { Text(text = it, color = Color.Gray) }

            Text(text = "$differenceDays Day Ago", color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun ProfileButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick, modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(50))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profile",
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified

        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // Use mock articles for the preview
//    HomeScreen(navController)
}