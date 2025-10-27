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
fun AppointmentsScreen(
    appointments: List<Appointment>,
    onAddAppointment: (Appointment) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var doctor by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var time by remember { mutableStateOf(TextFieldValue("")) }
    var reason by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Appointments", fontSize = 26.sp, color = Color(0xFF673AB7))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = doctor, onValueChange = { doctor = it }, label = { Text("Doctor Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Time (HH:MM)") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        OutlinedTextField(value = reason, onValueChange = { reason = it }, label = { Text("Reason") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        Button(onClick = {
            if (doctor.text.isNotBlank() && date.text.isNotBlank() && time.text.isNotBlank()) {
                onAddAppointment(Appointment(doctor.text, date.text, time.text, reason.text, "Scheduled"))
                doctor = TextFieldValue("")
                date = TextFieldValue("")
                time = TextFieldValue("")
                reason = TextFieldValue("")
            }
        }, modifier = Modifier.padding(top = 12.dp)) {
            Text("Schedule Appointment")
        }

        Spacer(Modifier.height(16.dp))
        Text("Your Appointments", fontSize = 18.sp, color = Color.Gray)
        Spacer(Modifier.height(8.dp))
        appointments.forEach { appt ->
            Card(Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text("${appt.doctorName} — ${appt.date} ${appt.time}", fontSize = 16.sp, color = Color(0xFF673AB7))
                    Text("Reason: ${appt.reason}", fontSize = 13.sp, color = Color.DarkGray)
                    Text("Status: ${appt.status}", fontSize = 13.sp, color = Color.DarkGray)
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}