package com.example.newsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
    ) {
        Row(modifier = Modifier) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate("homeScreen") },
                tint = Color.DarkGray
            )
            Text(
                text = "NewsApp",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color.DarkGray
            )

        }
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextInput()
    }


}

@Composable
fun OutlinedTextInput() {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            singleLine = true,
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = { Text("Search here") },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(40.dp) // Size of the circle
                        .background(
                            Color.Black,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center // Black background with a circular shape
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward, // Arrow icon
                        contentDescription = "Dropdown arrow",
                        tint = Color.White, // White arrow color
                        modifier = Modifier.size(20.dp) // Size of the arrow icon
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    //SearchScreen(navController)
}