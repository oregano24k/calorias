package com.example.calorias

// --- ERROR CORREGIDO AQUÍ ---
import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import com.example.calorias.ui.theme.CaloriasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriasTheme {
                MainScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    // 1. Reintroducimos el estado para guardar la URI de la imagen
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Usamos un Box para mantener el fondo blanco consistente
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // 2. Reintroducimos la lógica condicional
        if (imageUri == null) {
            // --- PANTALLA INICIAL ---
            // Columna para organizar los elementos de la pantalla principal
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

                // 3. Reintroducimos el botón "Cargar Logo"
                Button(onClick = {
                    val resourceId = R.drawable.logo // Esta línea causa un 'crash' si 'logo.jpg' no existe en res/drawable
                    imageUri = Uri.parse(
                        "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/$resourceId"
                    )
                }) {
                    Text("Cargar Logo")
                }
            }
        } else {
            // --- PANTALLA DE VISUALIZACIÓN DE IMAGEN ---
            // Columna para mostrar la imagen cargada
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
fun MainScreenPreview() {
    CaloriasTheme {
        MainScreen()
    }
}
