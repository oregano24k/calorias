package com.example.calorias

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.calorias.ui.theme.CaloriasTheme

// --- Definimos las rutas para la navegación ---
object AppRoutes {
    const val LOGIN_SCREEN = "login"
    const val MAIN_SCREEN = "main"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriasTheme {
                // Función principal que controla la navegación
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // 1. Creamos el controlador de navegación
    val navController = rememberNavController()

    // 2. NavHost es el contenedor que mostrará la pantalla actual
    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN_SCREEN // La primera pantalla será el login
    ) {
        // 3. Definimos cada pantalla
        composable(AppRoutes.LOGIN_SCREEN) {
            LoginScreen(
                // AJUSTE: Cambiamos onLoginClick por onLoginSuccess para que coincida
                onLoginSuccess = {
                    // Acción para navegar a la pantalla principal
                    navController.navigate(AppRoutes.MAIN_SCREEN)
                }
            )
        }

        composable(AppRoutes.MAIN_SCREEN) {
            // Pasamos el navController a MainScreen para poder volver atrás
            MainScreen(navController = navController)
        }
    }
}


// --- MainScreen ahora recibe un NavController ---
@Composable
fun MainScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            // --- PANTALLA INICIAL ---
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background_shark),
                    contentDescription = "Logo de fondo",
                    modifier = Modifier
                        .size(250.dp)
                        .blur(radius = 1.dp)
                        .alpha(0.2f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Version 1.0",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(48.dp))

                Button(onClick = { Toast.makeText(context, "Hola", Toast.LENGTH_SHORT).show() }) {
                    Text("Saludar")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { Toast.makeText(context, "Buenas Noches", Toast.LENGTH_SHORT).show() }) {
                    Text("Despedirse")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val resourceId = R.drawable.logo
                    imageUri = "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/$resourceId".toUri()
                }) {
                    Text("Cargar Logo")
                }

                // El botón de "Cerrar Sesión" y su Spacer han sido eliminados de aquí.
            }
        } else {
            // --- PANTALLA DE VISUALIZACIÓN DE IMAGEN ---
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Logo cargado",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { imageUri = null }) {
                    Text("Volver")
                }
            }
        }
    }
}

// El Preview sigue funcionando para MainScreen, pero sin navegación
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CaloriasTheme {
        // Como el preview no tiene un NavController real, lo creamos de forma simple
        val navController = rememberNavController()
        MainScreen(navController)
    }
}
