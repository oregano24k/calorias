package com.example.calorias

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// Lista de usuarios (no cambia)
val usuariosRegistrados = mapOf(
    "admin" to "1234",
    "user1" to "qwerty",
    "invitado" to "0000",
    "hola" to "hola"
)

@Composable
fun LoginDialog(
    onLoginSuccess: () -> Unit // Función que se llama cuando el login es exitoso
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Usamos Dialog para crear la ventana flotante.
    // onDismissRequest = {} significa que el diálogo no se puede cerrar pulsando fuera.
    Dialog(onDismissRequest = { /* No permitir cerrar el diálogo */ }) {
        // Card le da el fondo blanco y la sombra al diálogo.
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 24.dp), // Aumentamos el padding interno
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Iniciar Sesión", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val contraseñaGuardada = usuariosRegistrados[username]
                        if (contraseñaGuardada != null && contraseñaGuardada == password) {
                            Toast.makeText(context, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                            onLoginSuccess() // Llamamos a la función para cerrar el diálogo
                        } else {
                            Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Entrar")
                }
            }
        }
    }
}
