package com.example.mediscan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmergencyScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Center the emergency actions and make the primary CTA prominent
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // big attention card (use Card elevation)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.LocalHospital,
                    contentDescription = "Emergency",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "Emergency Help",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "If you are experiencing a life-threatening emergency, call your local emergency number immediately.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        // prominent call button (demo)
        Button(
            onClick = { /* placeholder - demo only */ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            shape = CircleShape,
            modifier = Modifier
                .sizeIn(minWidth = 200.dp)
                .height(56.dp)
        ) {
            Icon(Icons.Default.Call, contentDescription = "Call", tint = MaterialTheme.colorScheme.onError)
            Spacer(Modifier.width(8.dp))
            Text("Call Emergency", color = MaterialTheme.colorScheme.onError)
        }

        Spacer(Modifier.height(16.dp))

        // quick contact cards — centered
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = MaterialTheme.shapes.small,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                // Use the updated ListItem API names: headlineContent / supportingContent / trailingContent
                ListItem(
                    headlineContent = { Text("Ambulance") },
                    supportingContent = { Text("112 (demo)") },
                    trailingContent = {
                        TextButton(onClick = { /* demo */ }) { Text("Details") }
                    }
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = MaterialTheme.shapes.small,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Poison Control") },
                    supportingContent = { Text("800-222-1222 (demo)") },
                    trailingContent = {
                        TextButton(onClick = { /* demo */ }) { Text("Details") }
                    }
                )
            }
        }

        Spacer(Modifier.height(22.dp))

        OutlinedButton(onClick = onBack) { Text("Back") }
    }
}