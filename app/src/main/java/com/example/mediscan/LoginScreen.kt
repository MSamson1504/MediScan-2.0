package com.example.mediscan

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSubmit: (UserProfile) -> Unit
) {
    // form state
    var name by remember { mutableStateOf(TextFieldValue("")) }
    // Age as integer with slider + +/- controls (click & drag)
    var age by remember { mutableStateOf(30) } // default
    // Gender as two-button choice
    var gender by remember { mutableStateOf("") } // "Male" or "Female"
    var showSaved by remember { mutableStateOf(false) }
    var showValidationError by remember { mutableStateOf(false) }

    val canSubmit = name.text.isNotBlank()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .animateContentSize(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // header: logo + title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = "MediScan",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text(
                            "Welcome to MediScan",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "Create a quick profile to get started",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))

                // Name field
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (it.text.isNotBlank()) showValidationError = false
                    },
                    label = { Text("Full name") },
                    placeholder = { Text("e.g. Alex Morgan") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // Age selector (click & drag: Slider) with +/- controls for precision
                Text("Age", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // minus button
                    IconButton(
                        onClick = { if (age > 0) age -= 1 },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Text("-", style = MaterialTheme.typography.titleLarge)
                    }

                    Spacer(Modifier.width(8.dp))

                    // Age display
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 0.dp,
                        modifier = Modifier
                            .width(96.dp)
                            .height(48.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("$age", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    Spacer(Modifier.width(8.dp))

                    // plus button
                    IconButton(
                        onClick = { if (age < 100) age += 1 },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Text("+", style = MaterialTheme.typography.titleLarge)
                    }

                    Spacer(Modifier.width(12.dp))

                    // Slider expands to fill remaining space
                    Column(modifier = Modifier.weight(1f)) {
                        Slider(
                            value = age.toFloat(),
                            onValueChange = { newVal -> age = newVal.toInt() },
                            valueRange = 0f..100f,
                            steps = 119,
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { contentDescription = "Age slider" }
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("0", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("100", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Gender two buttons with icons
                Text("Gender", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Male button
                    val maleSelected = gender == "Male"
                    ElevatedCard(
                        onClick = { gender = "Male" },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (maleSelected) 8.dp else 2.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = Icons.Default.Male,
                                contentDescription = "Male",
                                tint = if (maleSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Male", color = if (maleSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    // Female button
                    val femaleSelected = gender == "Female"
                    ElevatedCard(
                        onClick = { gender = "Female" },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (femaleSelected) 8.dp else 2.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = Icons.Default.Female,
                                contentDescription = "Female",
                                tint = if (femaleSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Female", color = if (femaleSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                // Validation message
                if (showValidationError) {
                    Text(
                        text = "Please enter your name before submitting.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Action row
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (!canSubmit) {
                                showValidationError = true
                                return@Button
                            }
                            // healthHistory removed: send empty string for compatibility with UserProfile
                            onSubmit(UserProfile(name.text.trim(), age.toString(), gender, ""))
                            showSaved = true
                        },
                        modifier = Modifier.weight(1f),
                        enabled = true,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Get Started")
                    }

                    OutlinedButton(
                        onClick = {
                            // quick clear action
                            name = TextFieldValue("")
                            age = 30
                            gender = ""
                            showSaved = false
                            showValidationError = false
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text("Clear")
                    }
                }

                if (showSaved) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Profile saved locally. You can edit this later in Settings.",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(12.dp))

                // subtle privacy note
                Text(
                    "Your data stays on this device in this demo. For production apps, add secure storage & consent screens.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}