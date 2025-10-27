package com.example.mediscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.mediscan.ui.theme.MediScanTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediScanTheme {
                var userProfile by remember { mutableStateOf<UserProfile?>(null) }
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

                var medicationReminders by remember { mutableStateOf(listOf<MedicationReminder>()) }
                var symptomLogs by remember { mutableStateOf(listOf<SymptomLog>()) }
                var appointments by remember { mutableStateOf(listOf<Appointment>()) }

                // Scroll behavior used by LargeTopAppBar for collapse-on-scroll
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                // Top bar title depends on current screen
                val topBarTitle = when (currentScreen) {
                    Screen.Login -> "MediScan"
                    Screen.Dashboard -> "Dashboard"
                    Screen.MedicationReminders -> "Medication"
                    Screen.SymptomInput -> "Symptom Input"
                    Screen.SymptomChecker -> "Symptom Checker"
                    Screen.FindDoctor -> "Find Doctor"
                    Screen.Appointments -> "Appointments"
                    Screen.Emergency -> "Emergency"
                    Screen.Recommendations -> "Recommendations"
                    Screen.Settings -> "Settings"
                }

                // Simple navigation items shown in the overflow menu
                val navItems = listOf(
                    NavItem("Home", Screen.Dashboard),
                    NavItem("Symptom Input", Screen.SymptomInput),
                    NavItem("Medication", Screen.MedicationReminders),
                    NavItem("Appointments", Screen.Appointments),
                    NavItem("Find Doctor", Screen.FindDoctor),
                    NavItem("Symptom Checker", Screen.SymptomChecker),
                    NavItem("Recommendations", Screen.Recommendations),
                    NavItem("Emergency", Screen.Emergency),
                    NavItem("Settings", Screen.Settings)
                )

                Scaffold(
                    modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
                    topBar = {
                        // Show the large collapsing top bar on app screens, but hide it on Login
                        if (currentScreen != Screen.Login) {
                            // overflow menu state
                            var menuExpanded by remember { mutableStateOf(false) }

                            LargeTopAppBar(
                                title = {
                                    // Show title with a small subtitle (user name) to look more professional
                                    Column {
                                        Text(topBarTitle)
                                        userProfile?.let {
                                            Text(
                                                text = it.name,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                },
                                navigationIcon = {
                                    IconButton(onClick = { /* could open drawer or profile */ }) {
                                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                                    }
                                },
                                actions = {
                                    // Overflow menu for navigation and quick actions
                                    IconButton(onClick = { menuExpanded = true }) {
                                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                                    }
                                    DropdownMenu(
                                        expanded = menuExpanded,
                                        onDismissRequest = { menuExpanded = false }
                                    ) {
                                        navItems.forEach { item ->
                                            DropdownMenuItem(
                                                text = { Text(item.label) },
                                                onClick = {
                                                    currentScreen = item.screen
                                                    menuExpanded = false
                                                }
                                            )
                                        }
                                        // Optional separator and logout if logged in
                                        if (userProfile != null) {
                                            DropdownMenuItem(
                                                text = { Text("Logout") },
                                                onClick = {
                                                    userProfile = null
                                                    // reset app state
                                                    medicationReminders = listOf()
                                                    symptomLogs = listOf()
                                                    appointments = listOf()
                                                    currentScreen = Screen.Login
                                                    menuExpanded = false
                                                }
                                            )
                                        }
                                    }
                                },
                                scrollBehavior = topBarScrollBehavior
                            )
                        }
                    },
                    // Bottom bar removed for a cleaner, more professional layout
                ) { innerPadding ->
                    // create a base modifier that includes padding and the nestedScroll connection
                    val baseModifier = Modifier
                        .padding(innerPadding)
                        .nestedScroll(topBarScrollBehavior.nestedScrollConnection)

                    when (currentScreen) {
                        Screen.Login -> LoginScreen(
                            modifier = Modifier.padding(innerPadding),
                            onSubmit = { profile: UserProfile ->
                                userProfile = profile
                                currentScreen = Screen.Dashboard
                            }
                        )
                        Screen.Dashboard -> DashboardScreen(
                            name = userProfile?.name ?: "",
                            modifier = baseModifier,
                            onNavigate = { currentScreen = it },
                            onLogout = {
                                userProfile = null
                                currentScreen = Screen.Login
                                medicationReminders = listOf()
                                symptomLogs = listOf()
                                appointments = listOf()
                            }
                        )
                        Screen.MedicationReminders -> MedicationRemindersScreen(
                            reminders = medicationReminders,
                            onAddReminder = { reminder -> medicationReminders = medicationReminders + reminder },
                            onLogResponse = { index, response ->
                                medicationReminders = medicationReminders.mapIndexed { i, r ->
                                    if (i == index) r.copy(status = response) else r
                                }
                            },
                            onBack = { currentScreen = Screen.Dashboard },
                            modifier = baseModifier
                        )
                        Screen.SymptomInput -> SymptomInputScreen(
                            logs = symptomLogs,
                            onAddLog = { log -> symptomLogs = symptomLogs + log },
                            onBack = { currentScreen = Screen.Dashboard },
                            modifier = baseModifier
                        )
                        Screen.SymptomChecker -> SymptomCheckerScreen(
                            onSuggest = { suggestion ->
                                symptomLogs = symptomLogs + SymptomLog(suggestion.symptom, suggestion.severity, suggestion.notes)
                            },
                            onBack = { currentScreen = Screen.Dashboard },
                            profile = userProfile,
                            modifier = baseModifier
                        )
                        Screen.FindDoctor -> FindDoctorScreen(
                            onBook = { docName ->
                                // Use a backward-compatible date provider instead of java.time.LocalDate
                                val today = getTodayDateString()
                                appointments = appointments + Appointment(docName, today, "09:00", "Consultation", "Scheduled")
                                currentScreen = Screen.Appointments
                            },
                            onBack = { currentScreen = Screen.Dashboard },
                            modifier = baseModifier
                        )
                        Screen.Appointments -> AppointmentsScreen(
                            appointments = appointments,
                            onAddAppointment = { appt -> appointments = appointments + appt },
                            onBack = { currentScreen = Screen.Dashboard },
                            modifier = baseModifier
                        )
                        Screen.Emergency -> EmergencyScreen(onBack = { currentScreen = Screen.Dashboard }, modifier = Modifier.padding(innerPadding))
                        Screen.Recommendations -> RecommendationsScreen(
                            profile = userProfile,
                            onBack = { currentScreen = Screen.Dashboard },
                            modifier = baseModifier
                        )
                        Screen.Settings -> SettingsScreen(
                            onBack = { currentScreen = Screen.Dashboard },
                            onLogout = {
                                userProfile = null
                                currentScreen = Screen.Login
                                medicationReminders = listOf()
                                symptomLogs = listOf()
                                appointments = listOf()
                            },
                            modifier = baseModifier
                        )
                    }
                }
            }
        }
    }

    private fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private data class NavItem(val label: String, val screen: Screen)
}