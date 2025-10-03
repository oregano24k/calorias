package com.example.calorias

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage // Import correcto para Coil
import com.example.calorias.ui.theme.CaloriasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FoodScannerScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FoodScannerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageUri = uri
        }
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Si no hay ninguna imagen seleccionada, mostramos los botones
        if (imageUri == null) {
            Text(
                text = "Calculadora de Calorías",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                Toast.makeText(context, "Abriendo cámara...", Toast.LENGTH_SHORT).show()
            }) {
                Text("Tomar Foto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                galleryLauncher.launch("image/*")
            }) {
                Text("Elegir de la Galería")
            }

            // --- CÓDIGO DEL BOTÓN "SALUDAR" ---
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                // La acción del nuevo botón: mostrar el mensaje "Hola"
                Toast.makeText(context, "Hola", Toast.LENGTH_SHORT).show()
            }) {
                Text("Saludar")
            }
            // ---------------------------------

            // --- INICIO DEL NUEVO BOTÓN "BUENAS NOCHES" ---
            Spacer(modifier = Modifier.height(16.dp)) // Espacio para separar del botón anterior

            Button(onClick = {
                // Acción: Mostrar el mensaje "Buenas Noches"
                Toast.makeText(context, "Buenas Noches", Toast.LENGTH_SHORT).show()
            }) {
                Text("Despedirse") // Texto del botón
            }
            // --- FIN DEL NUEVO BOTÓN ---


        } else {
            // Si SÍ hay una imagen, la mostramos y damos nuevas opciones
            Text(
                text = "Analizando Comida...",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = imageUri,
                contentDescription = "Imagen de comida seleccionada",
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                Toast.makeText(context, "Analizando...", Toast.LENGTH_SHORT).show()
            }) {
                Text("Calcular Calorías")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { imageUri = null }) {
                Text("Elegir otra imagen")
            }
        }
    }
}

// --- ESTA ES LA PARTE QUE FALTABA ---
@Preview(showBackground = true)
@Composable
fun FoodScannerScreenPreview() {
    CaloriasTheme {
        FoodScannerScreen()
    }
}
// ------------------------------------
