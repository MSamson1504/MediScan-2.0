//package com.example.mediscan.app.src.main.java.com.example.mediscan
//
//import com.example.mediscan.SymptomSuggestion
//import com.example.mediscan.UserProfile
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//
//
//@Composable
//fun SymptomCheckerScreen(
//    onSuggest: (SymptomSuggestion) -> Unit,
//    onBack: () -> Unit,
//    profile: UserProfile?,
//    modifier: Modifier = Modifier
//) {
//    var input by remember { mutableStateOf(TextFieldValue("")) }
//    var result by remember { mutableStateOf<String?>(null) }
//    var showSave by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(24.dp)
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Symptom Checker", fontSize = 26.sp, color = Color(0xFF673AB7))
//        Spacer(Modifier.height(12.dp))
//        OutlinedTextField(
//            value = input,
//            onValueChange = { input = it },
//            label = { Text("Describe your symptoms (e.g., cough, chest pain)") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(12.dp))
//        Button(onClick = {
//            val text = input.text.lowercase()
//            val suggestion = when {
//                // 🩺 Chest / Respiratory symptoms
//                listOf("chest pain", "shortness", "breath", "palpitations", "bloody cough").any { text.contains(it, true) } ->
//                    "Possible cardiovascular or respiratory issue — if chest pain or shortness of breath is severe, seek emergency care immediately."
//
//                listOf("cough", "sore throat", "runny", "sputum", "hoarseness", "night cough").any { text.contains(it, true) } ->
//                    "Likely respiratory infection — stay hydrated, rest, and monitor for fever or worsening symptoms."
//
//                // 🤢 Digestive / Abdomen
//                listOf("abdominal pain", "stomach", "nausea", "vomiting", "diarrhea", "heartburn", "bloated", "appetite").any { text.contains(it, true) } ->
//                    "Common digestive issue — may be gastritis, indigestion, or food-related. Maintain hydration and avoid heavy meals."
//
//                listOf("blood in stool", "painful defecation", "constipation", "incomplete defecation").any { text.contains(it, true) } ->
//                    "Possible gastrointestinal problem — monitor your stool and seek medical advice if symptoms persist or worsen."
//
//                // 🧠 Head / Neurological
//                listOf("headache", "dizziness", "faint", "vision", "memory", "hallucination", "unconscious", "blackening of vision").any { text.contains(it, true) } ->
//                    "Neurological or vascular symptoms — rest and stay hydrated, but consult a doctor if persistent or severe."
//
//                listOf("fever", "chills", "tiredness", "drowsiness").any { text.contains(it, true) } ->
//                    "Possible infection or viral illness — monitor temperature, rest, and drink fluids."
//
//                // 👁 Face & Eyes
//                listOf("eye redness", "blurred", "light sensitivity", "itching eyes", "eye pain", "swelling", "burning eyes").any { text.contains(it, true) } ->
//                    "Possible eye irritation or infection — avoid rubbing your eyes and use clean water; consult an eye doctor if it worsens."
//
//                // 🦷 Mouth / Jaw
//                listOf("toothache", "mouth pain", "ulcer", "swallowing", "dry mouth", "lockjaw").any { text.contains(it, true) } ->
//                    "Possible oral or dental issue — maintain oral hygiene and visit a dentist if pain persists."
//
//                // 🦵 Musculoskeletal (Arms, Legs, Joints)
//                listOf("joint pain", "muscle pain", "cramps", "swelling", "stiffness", "bone", "fracture", "numbness").any { text.contains(it, true) } ->
//                    "Muscle or joint-related issue — may be strain, arthritis, or nerve compression. Rest affected area and consider light stretching."
//
//                // 👣 Feet / Hands
//                listOf("cold feet", "tingling", "numbness", "discoloration", "swelling", "cramps").any { text.contains(it, true) } ->
//                    "Circulatory or nerve-related issue — improve blood flow by gentle movement; consult a doctor if persistent."
//
//                // 🧍Skin / External
//                listOf("rash", "itching", "blister", "wound", "lesion", "mole", "yellow skin", "dry skin", "redness", "sweating").any { text.contains(it, true) } ->
//                    "Possible skin irritation, infection, or allergy — keep the area clean and dry; use mild soap and avoid scratching."
//
//                // 💧 Urinary / Genital
//                listOf("urine", "painful urination", "burning sensation", "dark urine", "frequent urination", "genital").any { text.contains(it, true) } ->
//                    "Possible urinary tract infection — drink plenty of water and consult a doctor if pain or fever is present."
//
//                listOf("testicular", "groin", "itching", "swelling of the testicles").any { text.contains(it, true) } ->
//                    "Genital or reproductive issue — avoid irritation and seek medical advice if swelling or pain continues."
//
//                // 🩸 Emergency indicators
//                listOf("severe", "unconscious", "bleeding", "trouble breathing", "chest pain", "vision loss").any { text.contains(it, true) } ->
//                    "Possible emergency — seek immediate medical attention."
//
//                // Default fallback
//                else -> "Unable to determine — please enter more specific symptoms or consult a healthcare provider for guidance."
//            }
//
//            result = suggestion
//            showSave = true
//        }) {
//            Text("Check")
//        }
//
//        Spacer(Modifier.height(16.dp))
//        result?.let {
//            Card(Modifier.fillMaxWidth()) {
//                Column(Modifier.padding(12.dp)) {
//                    Text("Suggestion", fontSize = 16.sp, color = Color(0xFF673AB7))
//                    Spacer(Modifier.height(8.dp))
//                    Text(it)
//                    Spacer(Modifier.height(8.dp))
//                    profile?.let { p ->
//                        Text("Profile: ${p.name}, Age: ${p.age}, Gender: ${p.gender}", fontSize = 12.sp, color = Color.Gray)
//                    }
//                }
//            }
//        }
//
//        if (showSave) {
//            Spacer(Modifier.height(12.dp))
//            Button(onClick = {
//                onSuggest(SymptomSuggestion(input.text, "Moderate", "", result ?: ""))
//                input = TextFieldValue("")
//                showSave = false
//            }) {
//                Text("Save to Symptom Log")
//            }
//        }
//
//        Spacer(Modifier.height(20.dp))
//        Button(onClick = onBack) { Text("Back") }
//    }
//}