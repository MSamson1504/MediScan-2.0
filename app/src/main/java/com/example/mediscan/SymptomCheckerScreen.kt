package com.example.mediscan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.Alignment

@Composable
fun SymptomCheckerScreen(
    onSuggest: (SymptomSuggestion) -> Unit,
    onBack: () -> Unit,
    profile: UserProfile?,
    modifier: Modifier = Modifier
) {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf<String?>(null) }
    var showSave by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Symptom Checker", fontSize = 26.sp, color = Color(0xFF673AB7))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Describe your symptoms (e.g., cough, chest pain)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            val text = input.text.lowercase()
            val suggestion = when {
                listOf("chest pain", "shortness", "breath").any { text.contains(it) } ->
                    "Possible emergency — consider seeking immediate care."
                listOf("fever", "temperature", "chills").any { text.contains(it) } ->
                    "Common sign of infection — monitor fever, stay hydrated, consider seeing a provider."
                listOf("cough", "sore throat", "runny").any { text.contains(it) } ->
                    "Likely respiratory issue — rest and monitor. If worsening, consult a physician."
                else -> "Unable to determine — consider entering specific symptoms or consult a provider."
            }
            result = suggestion
            showSave = true
        }) {
            Text("Check")
        }

        Spacer(Modifier.height(16.dp))
        result?.let {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text("Suggestion", fontSize = 16.sp, color = Color(0xFF673AB7))
                    Spacer(Modifier.height(8.dp))
                    Text(it)
                    Spacer(Modifier.height(8.dp))
                    profile?.let { p ->
                        Text("Profile: ${p.name}, Age: ${p.age}, Gender: ${p.gender}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        if (showSave) {
            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                onSuggest(SymptomSuggestion(input.text, "Moderate", "", result ?: ""))
                input = TextFieldValue("")
                showSave = false
            }) {
                Text("Save to Symptom Log")
            }
        }

        Spacer(Modifier.height(20.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}