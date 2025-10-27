package com.example.mediscan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(
    name: String,
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            // allow collapsing top app bar to react to scroll
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome / info card (use Card elevation instead of tonalElevation)
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Row(
                Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        "Welcome${if (name.isNotBlank()) ", $name" else ""}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "Quick access to your health tools",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onLogout) {
                    Text("Logout", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        // Dashboard grid
        DashboardMenuGrid(onNavigate = onNavigate)

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            "© 2025 MediScan",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = 12.sp
        )
    }
}

@Composable
fun DashboardMenuGrid(onNavigate: (Screen) -> Unit) {
    val menuItems = listOf(
        MenuItem(androidx.compose.material.icons.Icons.Default.HealthAndSafety, "Symptom Checker", Screen.SymptomChecker),
        MenuItem(androidx.compose.material.icons.Icons.Default.Search, "Find Doctor", Screen.FindDoctor),
        MenuItem(androidx.compose.material.icons.Icons.Default.DateRange, "Appointments", Screen.Appointments),
        MenuItem(androidx.compose.material.icons.Icons.Default.Warning, "Emergency", Screen.Emergency),
        MenuItem(androidx.compose.material.icons.Icons.Default.Info, "Recommendations", Screen.Recommendations),
        MenuItem(androidx.compose.material.icons.Icons.Default.Settings, "Settings", Screen.Settings),
        MenuItem(androidx.compose.material.icons.Icons.Default.Medication, "Medication Reminders", Screen.MedicationReminders),
        MenuItem(androidx.compose.material.icons.Icons.Default.Sick, "Symptom Input", Screen.SymptomInput)
    )

    Column {
        for (row in menuItems.chunked(2)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (item in row) {
                    ElevatedCard(
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                            .clickable { onNavigate(item.screen) },
                        shape = RoundedCornerShape(12.dp),
                        elevation = elevatedCardElevation(defaultElevation = 10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                modifier = Modifier.size(44.dp),
                                tonalElevation = 2.dp
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(item.label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

private data class MenuItem(val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String, val screen: Screen)