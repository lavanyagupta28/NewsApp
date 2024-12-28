package com.example.newsapp.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(article: Article, navController: NavHostController) {
    val instant = Instant.parse(article.publishedAt)
    val publishedLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    val currentDate = LocalDate.now()
    val differenceDays = ChronoUnit.DAYS.between(publishedLocalDate, currentDate)
    val imageUrl = if (LocalInspectionMode.current) {
        "https://via.placeholder.com/200" // Mock URL for preview
    } else {
        article.urlToImage
    }
    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier) {
            Text(
                text = "NewsApp",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.popBackStack() },
                tint = Color.DarkGray
            )

        }
        Spacer(modifier = Modifier.height(40.dp))
        imageUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
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
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = article.description.toString(),
            textAlign = TextAlign.Justify,
            letterSpacing = 0.5.sp,
            fontSize = 15.sp,
        )
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = article.author.toString(),
                textAlign = TextAlign.Justify,
                modifier = Modifier.weight(1f),
                fontSize = 15.sp
            )
            Text(
                text = "$differenceDays day ago",
                textAlign = TextAlign.Justify,
                fontSize = 15.sp
            )

        }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    //DetailScreen(article = articles[1])
}
