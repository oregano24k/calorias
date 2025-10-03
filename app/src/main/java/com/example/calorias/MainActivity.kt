package com.example.calorias

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// --- SECCIÓN DE IMPORTS LIMPIADA ---
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
import coil.compose.AsyncImage
import com.example.calorias.ui.theme.CaloriasTheme
// ---------------------------------

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriasTheme {
                // Llamamos directamente a la nueva pantalla de la aplicación
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    // 1. Estado para saber si el usuario ha iniciado sesión.
    var isLoggedIn by remember { mutableStateOf(false) }

    // 2. El Box permite superponer elementos (el diálogo sobre la pantalla principal).
    Box(modifier = Modifier.fillMaxSize()) {
        // 3. Mostramos la pantalla principal, desenfocada si el usuario no ha iniciado sesión.
        MainScreen(
            // Aplicamos un desenfoque si el diálogo está visible.
            modifier = Modifier.blur(radius = if (isLoggedIn) 0.dp else 10.dp)
        )

        // 4. Si el usuario NO ha iniciado sesión, mostramos el diálogo flotante.
        if (!isLoggedIn) {
            LoginDialog(
                onLoginSuccess = {
                    isLoggedIn = true // Cuando el login es exitoso, actualizamos el estado.
                }
            )
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) { // Ya no necesita el NavController
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    Box(
        modifier = modifier // Usamos el modifier que nos pasan para aplicar el desenfoque
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background_shark),
                    contentDescription = "Logo de fondo",
                    modifier = Modifier
                        .size(250.dp)
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
            }
        } else {
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

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    CaloriasTheme {
        // En el preview, simulamos que el usuario no ha iniciado sesión.
        AppScreen()
    }
}
