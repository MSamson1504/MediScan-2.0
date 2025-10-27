package com.example.mediscan

// Models and navigation enum

data class UserProfile(
    val name: String,
    val age: String,
    val gender: String,
    val healthHistory: String
)

enum class Screen {
    Login, Dashboard,
    MedicationReminders, SymptomInput,
    SymptomChecker, FindDoctor, Appointments,
    Emergency, Recommendations, Settings
}

data class MedicationReminder(
    val name: String,
    val dosage: String,
    val time: String,
    val status: String = "" // "taken", "missed", ""
)

data class SymptomLog(
    val name: String,
    val severity: String,
    val notes: String
)

data class SymptomSuggestion(val symptom: String, val severity: String, val notes: String, val suggestionText: String)

data class Doctor(
    val name: String,
    val specialty: String,
    val rating: Double,
    val distanceKm: Double
)

data class Appointment(
    val doctorName: String,
    val date: String,
    val time: String,
    val reason: String,
    val status: String
)