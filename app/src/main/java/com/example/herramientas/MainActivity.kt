package com.example.herramientas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.herramientas.ui.theme.HerramientasTheme
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.herramientas.data.db.AppDatabase
import com.example.herramientas.data.model.HerramientaEntity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 👇 Esto crea la DB apenas arranca la app
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(applicationContext)
            val dao = db.herramientaDao()

            // Solo insertar si no hay datos (para crear la DB y probar)
            if (dao.getAll().isEmpty()) {
                dao.insert(HerramientaEntity(nombre = "Martillo", cantidad_actual = 5, prestada = false))
            }
        }

        setContent {
            HerramientasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HerramientasTheme {
        Greeting("Android")
    }
}
