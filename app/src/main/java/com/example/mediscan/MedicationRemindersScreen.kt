package com.example.mediscan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.ui.Alignment

@Composable
fun MedicationRemindersScreen(
    reminders: List<MedicationReminder>,
    onAddReminder: (MedicationReminder) -> Unit,
    onLogResponse: (Int, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var medicineName by remember { mutableStateOf(TextFieldValue("")) }
    var dosage by remember { mutableStateOf(TextFieldValue("")) }
    var reminderTime by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Medication Reminders", fontSize = 26.sp, color = Color(0xFF673AB7))
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = medicineName,
            onValueChange = { medicineName = it },
            label = { Text("Medicine Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosage") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = reminderTime,
            onValueChange = { reminderTime = it },
            label = { Text("Reminder Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        Button(
            onClick = {
                if (medicineName.text.isNotBlank() && dosage.text.isNotBlank() && reminderTime.text.isNotBlank()) {
                    onAddReminder(
                        MedicationReminder(
                            name = medicineName.text,
                            dosage = dosage.text,
                            time = reminderTime.text
                        )
                    )
                    medicineName = TextFieldValue("")
                    dosage = TextFieldValue("")
                    reminderTime = TextFieldValue("")
                }
            },
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Add Reminder")
        }
        Spacer(Modifier.height(24.dp))
        Text("Reminders Log", fontSize = 18.sp, color = Color.Gray)
        Spacer(Modifier.height(8.dp))
        reminders.forEachIndexed { index, reminder ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(reminder.name, fontSize = 16.sp, color = Color(0xFF673AB7))
                        Text("Dosage: ${reminder.dosage}, Time: ${reminder.time}", fontSize = 13.sp, color = Color.DarkGray)
                        Text("Status: ${reminder.status.ifBlank { "Pending" }}", fontSize = 13.sp, color = Color.DarkGray)
                    }
                    if (reminder.status.isBlank()) {
                        Button(
                            onClick = { onLogResponse(index, "taken") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) { Text("Taken") }
                        Button(
                            onClick = { onLogResponse(index, "missed") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) { Text("Missed") }
                    }
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}