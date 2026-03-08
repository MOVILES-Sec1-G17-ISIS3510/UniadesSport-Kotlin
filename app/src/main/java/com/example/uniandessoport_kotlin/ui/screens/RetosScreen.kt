package com.example.uniandessoport_kotlin.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// Reusing Sport enum from PlayScreen
// enum class Sport is already defined in PlayScreen.kt or we can redefine a similar scope here.
// For simplicity, defining locally what is needed, or we can use the same icons.

data class Reto(
    val id: Int,
    val nombre: String,
    val tipo: String, // Individual, Team
    val deporte: Sport,
    val dificultad: String, // Beginner, Intermediate, Advanced
    val objetivo: String,
    val diasRestantes: Int,
    val participantes: Int,
    val progreso: Int
)

val mockRetos = listOf(
    Reto(1, "100K Running Challenge", "Individual", Sport.RUNNING, "Advanced", "100 km", 15, 45, 35),
    Reto(2, "UniAndes Soccer Cup", "Team", Sport.SOCCER, "Intermediate", "Win tournament", 25, 8, 20),
    Reto(3, "30-Day Push-ups", "Individual", Sport.CALISTHENICS, "Beginner", "1000 reps", 22, 67, 42),
    Reto(4, "Tennis Marathon", "Individual", Sport.TENNIS, "Intermediate", "20 sets", 18, 23, 25),
    Reto(5, "5K Speed Challenge", "Individual", Sport.RUNNING, "Beginner", "Under 25 min", 10, 34, 60),
    Reto(6, "Dunk Contest", "Individual", Sport.BASKETBALL, "Advanced", "10 dunks", 30, 12, 10)
)

@Composable
fun RetosScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    var typeFilter by remember { mutableStateOf("all") }
    var sportFilter by remember { mutableStateOf<Sport?>(null) }
    var selectedReto by remember { mutableStateOf<Reto?>(null) }
    val verticalScrollState = rememberScrollState()

    val filteredRetos = mockRetos.filter { reto ->
        if (typeFilter != "all" && reto.tipo != typeFilter) return@filter false
        if (sportFilter != null && reto.deporte != sportFilter) return@filter false
        true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState)
            .padding(bottom = 80.dp)
            .padding(top = 8.dp)
    ) {
        // Type Filters
        val typeScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(typeScrollState)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("all", "Individual", "Team").forEach { type ->
                val isSelected = typeFilter == type
                FilterChip(
                    selected = isSelected,
                    onClick = { typeFilter = type },
                    label = { Text(if (type == "all") "All" else type, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = null
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sport Filters
        val sportScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(sportScrollState)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // "All Sports" button
            FilterChip(
                selected = sportFilter == null,
                onClick = { sportFilter = null },
                label = { Text("All Sports", fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp)) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = RoundedCornerShape(16.dp),
                border = null
            )

            Sport.entries.forEach { sport ->
                val isSelected = sportFilter == sport
                FilterChip(
                    selected = isSelected,
                    onClick = { sportFilter = sport },
                    label = { Text(sport.label, fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
                    leadingIcon = { Icon(sport.icon, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondary,
                        selectedLabelColor = MaterialTheme.colorScheme.onSecondary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = null
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Retos List
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (filteredRetos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No challenges found for this filter", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                filteredRetos.forEach { reto ->
                    RetoCard(reto = reto, onClick = { selectedReto = reto })
                }
            }
        }
    }

    // Reto Detail Dialog
    selectedReto?.let { reto ->
        RetoDetailDialog(reto = reto, onDismiss = { selectedReto = null })
    }
}

@Composable
fun RetoCard(reto: Reto, onClick: () -> Unit) {
    val difficultyColor = when (reto.dificultad) {
        "Beginner" -> Color(0xFF10B981) // Emerald
        "Intermediate" -> Color(0xFFF59E0B) // Amber
        else -> Color(0xFFEF4444) // Red
    }
    val difficultyBgColor = difficultyColor.copy(alpha = 0.1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(reto.deporte.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(reto.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp, lineHeight = 18.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(12.dp)) {
                                Text(reto.tipo, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                            Surface(color = difficultyBgColor, shape = RoundedCornerShape(12.dp)) {
                                Text(reto.dificultad, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = difficultyColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                    }
                }
                Text("${reto.progreso}%", fontSize = 24.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Flag, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(reto.objetivo, fontSize = 11.sp, color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${reto.diasRestantes}d left", fontSize = 11.sp, color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.People, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${reto.participantes}", fontSize = 11.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))

            LinearProgressIndicator(
                progress = { reto.progreso / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
fun RetoDetailDialog(reto: Reto, onDismiss: () -> Unit) {
    val difficultyColor = when (reto.dificultad) {
        "Beginner" -> Color(0xFF10B981)
        "Intermediate" -> Color(0xFFF59E0B)
        else -> Color(0xFFEF4444)
    }
    val difficultyBgColor = difficultyColor.copy(alpha = 0.1f)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(reto.nombre, fontSize = 20.sp, fontWeight = FontWeight.Black, lineHeight = 24.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(12.dp)) {
                                Text(reto.tipo, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                            Surface(color = difficultyBgColor, shape = RoundedCornerShape(12.dp)) {
                                Text(reto.dificultad, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = difficultyColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape).size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Surface(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(16.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("GOAL", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(reto.objetivo, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Surface(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(16.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("DAYS LEFT", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${reto.diasRestantes}d", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Progress", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                        Text("${reto.progreso}%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { reto.progreso / 100f },
                        modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("LEADERBOARD", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
                
                Spacer(modifier = Modifier.height(12.dp))

                val leaderboard = listOf(
                    Triple(1, "David Elías", "78 km"),
                    Triple(2, "Ana Rodriguez", "65 km"),
                    Triple(3, "You", "52 km"),
                    Triple(4, "Carlos Mendez", "45 km"),
                    Triple(5, "Student E", "38 km")
                )

                leaderboard.forEach { (pos, name, value) ->
                    val isUser = name == "You"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isUser) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant)
                            .border(if (isUser) 1.dp else 0.dp, if (isUser) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Transparent, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val posBg = when (pos) {
                                1 -> Color(0xFFFEF3C7) to Color(0xFFB45309) // Gold
                                2 -> Color(0xFFF3F4F6) to Color(0xFF4B5563) // Silver
                                3 -> Color(0xFFFFEDD5) to Color(0xFFC2410C) // Bronze
                                else -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.primary
                            }
                            Box(
                                modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(posBg.first),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("$pos", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = posBg.second)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(if (isUser) "$name ⭐" else name, fontSize = 14.sp, fontWeight = if (isUser) FontWeight.Bold else FontWeight.Medium, color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                        }
                        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Join Challenge", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
