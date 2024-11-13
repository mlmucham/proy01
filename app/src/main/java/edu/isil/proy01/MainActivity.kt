package edu.isil.proy01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import edu.isil.proy01.ui.theme.Proy01Theme
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Firebase
        Firebase.initialize(this)
        var firebaseAnalytics = Firebase.analytics

        setContent {
            Proy01Theme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController, firebaseAnalytics) }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                firebaseAnalytics = firebaseAnalytics
                            )
                        }
                        composable("data") {
                            DataScreen(
                                modifier = Modifier.padding(innerPadding),
                                firebaseAnalytics = firebaseAnalytics
                            )
                        }
                        composable("photos") {
                            PhotosScreen(
                                modifier = Modifier.padding(innerPadding),
                                firebaseAnalytics = firebaseAnalytics
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, firebaseAnalytics: FirebaseAnalytics) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Inicio") },
            selected = false,
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
                val bundle = Bundle().apply {
                    putString("valor1", "Ingresaste al Home")
                    putString("valor2", "Bienvenido")
                }
                firebaseAnalytics.logEvent("navigation_home", bundle)
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = null) },
            label = { Text("Datos") },
            selected = false,
            onClick = {
                navController.navigate("data") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
                val bundle = Bundle().apply {
                    putString("valor1", "Información importante")
                    putString("valor2", "Detalle de datos")
                }
                firebaseAnalytics.logEvent("navigation_data", bundle)
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Fotos") },
            selected = false,
            onClick = {
                navController.navigate("photos") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
                val bundle = Bundle().apply {
                    putString("valor1", "Galería de fotos")
                    putString("valor2", "Visualización de imágenes")
                }
                firebaseAnalytics.logEvent("navigation_photos", bundle)
            }
        )
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController, firebaseAnalytics: FirebaseAnalytics) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                navController.navigate("data")
                val bundle = Bundle().apply {
                    putString("valor1", "Botón clicado")
                    putString("valor2", "Ir a la pantalla de datos")
                }
                firebaseAnalytics.logEvent("button_click", bundle)
            }) {
                Text(text = "Ir a la siguiente pantalla")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Hola Clase 01!")
        }
    }
    // Log event for Home screen
    val bundle = Bundle().apply {
        putString("valor1", "Visualización de pantalla")
        putString("valor2", "HomeScreen")
    }
    firebaseAnalytics.logEvent("screen_view", bundle)
}

@Composable
fun DataScreen(modifier: Modifier = Modifier, firebaseAnalytics: FirebaseAnalytics) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Bienvenido a la pantalla de datos!",
            modifier = modifier
        )
    }
    // Log event for Data screen
    val bundle = Bundle().apply {
        putString("valor1", "Visualización de pantalla")
        putString("valor2", "DataScreen")
    }
    firebaseAnalytics.logEvent("screen_view", bundle)
}

@Composable
fun PhotosScreen(modifier: Modifier = Modifier, firebaseAnalytics: FirebaseAnalytics) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Bienvenido a la pantalla de fotos!",
            modifier = modifier
        )
    }
    // Log event for Photos screen
    val bundle = Bundle().apply {
        putString("valor1", "Visualización de pantalla")
        putString("valor2", "PhotosScreen")
    }
    firebaseAnalytics.logEvent("screen_view", bundle)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    val firebaseAnalytics = Firebase.analytics // Use a dummy instance for preview
    Proy01Theme {
        HomeScreen(navController = navController, firebaseAnalytics = firebaseAnalytics)
    }
}