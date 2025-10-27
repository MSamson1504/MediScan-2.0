package com.example.mediscan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

@Composable
fun RecommendationsScreen(profile: UserProfile?, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val recs = remember(profile) {
        val list = mutableListOf<String>()
        profile?.age?.toIntOrNull()?.let { age ->
            if (age >= 50) {
                list.add("Annual cardiovascular screening recommended.")
                list.add("Colonoscopy screening recommended every 10 years (age ≥50).")
            } else if (age in 18..49) {
                list.add("Routine check-up every 1–2 years.")
                list.add("Maintain regular physical activity (150 mins/week).")
            }
        } ?: run {
            list.add("Keep regular health check-ups and a balanced lifestyle.")
        }
        if (profile?.gender == "Female") {
            list.add("Consider routine mammogram screening as per local guidelines.")
        }
        if (profile?.healthHistory?.contains("asthma", ignoreCase = true) == true) {
            list.add("Carry a rescue inhaler and schedule periodic pulmonary reviews.")
        }
        list
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Recommendations", fontSize = 26.sp, color = Color(0xFF673AB7))
        Spacer(Modifier.height(12.dp))
        recs.forEach { r ->
            Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text(r, fontSize = 14.sp)
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}