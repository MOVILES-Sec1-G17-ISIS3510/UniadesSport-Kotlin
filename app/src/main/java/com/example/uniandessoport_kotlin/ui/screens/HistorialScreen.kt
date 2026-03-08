package com.example.uniandessoport_kotlin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HistorialPartido(
    val deporte: String,
    val fecha: String,
    val resultado: String,
    val equipo: String,
    val rival: String,
    val ubicacion: String
)

data class EstadisticaDeporte(
    val deporte: String,
    val jugados: Int,
    val victorias: Int,
    val derrotas: Int,
    val empates: Int
)

@Composable
fun HistorialScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    var vistaActual by remember { mutableStateOf("historial") }

    val partidos = listOf(
        HistorialPartido("5v5 Soccer", "Jan 28, 2026", "Victory", "Los Andes FC", "Rosario United", "UniAndes Courts"),
        HistorialPartido("Basketball", "Jan 25, 2026", "Defeat", "Warriors", "Javeriana", "UniAndes Gym"),
        HistorialPartido("Tennis", "Jan 22, 2026", "Victory", "Singles", "Laura M.", "El Nogal Club"),
        HistorialPartido("5v5 Soccer", "Jan 20, 2026", "Draw", "Los Andes FC", "Nacional", "Sports Complex"),
        HistorialPartido("Volleyball", "Jan 18, 2026", "Victory", "UniAndes Volley", "Sabana", "UniAndes Courts")
    )

    val estadisticas = listOf(
        EstadisticaDeporte("Soccer", 12, 7, 3, 2),
        EstadisticaDeporte("Basketball", 8, 5, 3, 0),
        EstadisticaDeporte("Tennis", 15, 10, 5, 0),
        EstadisticaDeporte("Volleyball", 6, 4, 2, 0)
    )

    @Composable
    fun getResultadoColor(resultado: String) = when (resultado) {
        "Victory" -> MaterialTheme.colorScheme.primary to Color.White
        "Defeat" -> Color(0xFFFEE2E2) to Color(0xFFDC2626)
        "Draw" -> Color(0xFFE5E7EB) to Color(0xFF4B5563)
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        // Hero Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.clickable { onNavigate("home") },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Back", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Your records \uD83D\uDCCA", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text("HISTORY", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                Text("Matches and statistics", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            listOf("historial" to "History", "estadisticas" to "Statistics").forEach { (id, label) ->
                val isSelected = vistaActual == id
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .clickable { vistaActual = id }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = if (isSelected) Color.White else Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            if (vistaActual == "historial") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    partidos.forEach { partido ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val colors = getResultadoColor(partido.resultado)
                                Surface(color = colors.first, shape = RoundedCornerShape(12.dp)) {
                                    Text(partido.resultado.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = colors.second, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(partido.deporte, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text(partido.fecha, fontSize = 11.sp, color = Color.Gray)
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Team", fontSize = 12.sp, color = Color.Gray)
                                        Text(partido.equipo, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Opponent", fontSize = 12.sp, color = Color.Gray)
                                        Text(partido.rival, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Location", fontSize = 12.sp, color = Color.Gray)
                                        Text(partido.ubicacion, fontSize = 12.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("41", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text("TOTAL MATCHES", fontSize = 10.sp, color = Color.Gray, letterSpacing = 1.sp)
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("63%", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text("WIN RATE", fontSize = 10.sp, color = Color.Gray, letterSpacing = 1.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("BY SPORT", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 12.dp))
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    estadisticas.forEach { est ->
                        val winRate = Math.round((est.victorias.toFloat() / est.jugados.toFloat()) * 100)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(est.deporte, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text("${est.jugados} matches", fontSize = 12.sp, color = Color.Gray)
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Win Rate", fontSize = 12.sp, color = Color.Gray)
                                    Text("${winRate}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                                Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape).padding(top = 4.dp)) {
                                    Box(modifier = Modifier.fillMaxWidth(winRate.toFloat() / 100f).height(8.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                                }
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Column(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f), RoundedCornerShape(12.dp)).padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(est.victorias.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        Text("Wins", fontSize = 10.sp, color = Color.Gray)
                                    }
                                    Column(modifier = Modifier.weight(1f).background(Color(0xFFFEF2F2), RoundedCornerShape(12.dp)).padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(est.derrotas.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
                                        Text("Losses", fontSize = 10.sp, color = Color.Gray)
                                    }
                                    Column(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)).padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(est.empates.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                        Text("Draws", fontSize = 10.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("MONTHLY TREND", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth().height(112.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                            val heights = listOf(0.65f, 0.8f, 0.55f, 0.9f)
                            heights.forEachIndexed { i, h ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                    Box(modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(h).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Wk ${i + 1}", fontSize = 10.sp, color = Color.Gray)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                        Text("Weekly activity for the last month", fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                            Icon(Icons.Default.CrisisAlert, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("PERSONAL RECORDS", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(
                                "Win streak" to "5 matches",
                                "Matches in a month" to "12 matches",
                                "Most played sport" to "Tennis"
                            ).forEach { (label, value) ->
                                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                                    Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(label, fontSize = 12.sp, color = Color.Gray)
                                        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
