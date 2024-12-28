package com.example.newsapp


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.screens.Article
import com.example.newsapp.screens.BottomNavigationItem
import com.example.newsapp.screens.DetailScreen
import com.example.newsapp.screens.HomeScreen
import com.example.newsapp.screens.ProfileScreen
import com.example.newsapp.screens.SearchScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.gson.Gson
import java.net.URLDecoder


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun SavedScreen() {
    // Placeholder for Saved Screen
    Text(text = "Saved Screen", modifier = Modifier.fillMaxSize())
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavigationItem(
            title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home
        ), BottomNavigationItem(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        ), BottomNavigationItem(
            title = "Saved",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite
        )
    )

    Scaffold(bottomBar = {
        NavigationBar(
            modifier = Modifier.height(80.dp), containerColor = Color.White
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(selected = selectedItemIndex == index, onClick = {
                    selectedItemIndex = index
                    when (index) {
                        0 -> navController.navigate("homeScreen") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }

                        1 -> navController.navigate("searchScreen") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }

                        2 -> navController.navigate("savedScreen") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                }, label = { Text(text = item.title) }, icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == index) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        }, contentDescription = item.title
                    )
                })
            }
        }
    }) { padding ->
        NavHost(
            navController = navController,
            startDestination = "homeScreen",
            modifier = Modifier.padding(padding)
        ) {
            composable("homeScreen") {
                HomeScreen(navController)
            }

            composable(
                "detailScreen/{article}",
                arguments = listOf(navArgument("article") { type = NavType.StringType })
            ) { backStackEntry ->
                val encodedArticleJson = backStackEntry.arguments?.getString("article")
                val articleJson = if (encodedArticleJson != null) {
                    URLDecoder.decode(encodedArticleJson, "UTF-8")  // Decode the URL-encoded string
                } else {
                    null
                }
                val article = if (articleJson != null) {
                    Gson().fromJson(articleJson, Article::class.java)
                } else {
                    null
                }
                if (article != null) {
                    DetailScreen(article = article, navController = navController)
                }
            }

            composable("profileScreen") {
                ProfileScreen(navController)
            }
            composable("searchScreen") {
                SearchScreen(navController)
            }
            composable("savedScreen") {
                SavedScreen()
            }
        }
    }
}
