package com.example.mediscan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Account header (centered)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Surface(
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(
                        "Account",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "Manage your profile & preferences",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Settings groups
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    // Notifications row
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Notifications", modifier = Modifier.weight(1f))
                        var notificationsEnabled by remember { mutableStateOf(true) }
                        Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
                    }
                    Divider()
                    // Privacy / sharing
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Share anonymized data", modifier = Modifier.weight(1f))
                        var shareData by remember { mutableStateOf(false) }
                        Switch(checked = shareData, onCheckedChange = { shareData = it })
                    }
                    Divider()
                    // Theme or other quick toggles
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Compact layout", modifier = Modifier.weight(1f))
                        var compact by remember { mutableStateOf(false) }
                        Switch(checked = compact, onCheckedChange = { compact = it })
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Other settings actions
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FilledTonalButton(onClick = { /* edit profile */ }, modifier = Modifier.fillMaxWidth()) {
                    Text("Edit Profile")
                }
                OutlinedButton(onClick = { /* open data export */ }, modifier = Modifier.fillMaxWidth()) {
                    Text("Export Data (demo)")
                }
            }
        }

        // Bottom area with logout and back buttons
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout", color = MaterialTheme.colorScheme.onError)
            }
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
        }
    }
}