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
fun SymptomInputScreen(
    logs: List<SymptomLog>,
    onAddLog: (SymptomLog) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var symptomName by remember { mutableStateOf(TextFieldValue("")) }
    var severity by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf(TextFieldValue("")) }
    val severityOptions = listOf("Mild", "Moderate", "Severe")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Symptom Input", fontSize = 26.sp, color = Color(0xFF673AB7))
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = symptomName,
            onValueChange = { symptomName = it },
            label = { Text("Symptom Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Box(Modifier.fillMaxWidth().padding(top = 8.dp)) {
            OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(if (severity.isNotEmpty()) severity else "Select Severity")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                severityOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            severity = option
                            expanded = false
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            maxLines = 3
        )
        Button(
            onClick = {
                if (symptomName.text.isNotBlank() && severity.isNotBlank()) {
                    onAddLog(SymptomLog(symptomName.text, severity, notes.text))
                    symptomName = TextFieldValue("")
                    severity = ""
                    notes = TextFieldValue("")
                }
            },
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Add Symptom Log")
        }
        Spacer(Modifier.height(24.dp))
        Text("Symptom History", fontSize = 18.sp, color = Color.Gray)
        Spacer(Modifier.height(8.dp))
        logs.forEach { log ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(log.name, fontSize = 16.sp, color = Color(0xFF673AB7))
                    Text("Severity: ${log.severity}", fontSize = 13.sp, color = Color.DarkGray)
                    Text("Notes: ${log.notes}", fontSize = 13.sp, color = Color.DarkGray)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}