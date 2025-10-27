package com.example.mediscan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun FindDoctorScreen(
    onBook: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    val sampleDoctors = listOf(
        Doctor("Dr. Aisha Khan", "General Physician", 4.7, 1.2),
        Doctor("Dr. Raj Patel", "Cardiologist", 4.9, 3.4),
        Doctor("Dr. Maria Gomez", "Pulmonologist", 4.6, 2.1),
        Doctor("Dr. John Smith", "Pediatrician", 4.5, 4.0)
    )

    val results = if (query.text.isBlank()) sampleDoctors else sampleDoctors.filter {
        it.name.lowercase().contains(query.text.lowercase()) || it.specialty.lowercase().contains(query.text.lowercase())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Find Doctor", fontSize = 26.sp, color = Color(0xFF673AB7))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search by name or specialty") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
        results.forEach { doc ->
            Card(Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(doc.name, fontSize = 16.sp, color = Color(0xFF673AB7))
                        Text("${doc.specialty} • ${doc.rating} ★ • ${doc.distanceKm} km", fontSize = 12.sp, color = Color.DarkGray)
                    }
                    Button(onClick = { onBook(doc.name) }) {
                        Text("Book")
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}