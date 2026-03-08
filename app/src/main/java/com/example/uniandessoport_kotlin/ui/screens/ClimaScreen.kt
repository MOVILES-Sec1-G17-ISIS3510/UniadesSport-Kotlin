package com.example.uniandessoport_kotlin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Pronostico(
    val dia: String,
    val hora: String,
    val temp: String,
    val lluvia: String,
    val icono: ImageVector
)

@Composable
fun ClimaScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val pronostico = listOf(
        Pronostico("Today", "3:00 PM", "18°", "10%", Icons.Default.WbSunny),
        Pronostico("Today", "6:00 PM", "16°", "20%", Icons.Default.Cloud),
        Pronostico("Tomorrow", "10:00 AM", "17°", "45%", Icons.Default.Cloud),
        Pronostico("Tomorrow", "3:00 PM", "19°", "65%", Icons.Default.Umbrella),
        Pronostico("Day after", "10:00 AM", "15°", "80%", Icons.Default.Umbrella),
        Pronostico("Day after", "3:00 PM", "16°", "55%", Icons.Default.Umbrella)
    )

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
                Text("Plan your activities ☀️", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text("WEATHER", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Now in Bogotá", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
                        Text("17°C", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFFDE047).copy(alpha = 0.8f), modifier = Modifier.size(64.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    WeatherStatBox(Icons.Default.WaterDrop, "Rain", "15%", Color(0xFF93C5FD), Modifier.weight(1f))
                    WeatherStatBox(Icons.Default.Air, "Wind", "12 km/h", Color.White.copy(alpha = 0.7f), Modifier.weight(1f))
                    WeatherStatBox(Icons.Default.CloudQueue, "Clouds", "40%", Color.White.copy(alpha = 0.7f), Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Warning
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Surface(
                color = Color(0xFFFEF3C7),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(20.dp).padding(top = 2.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Weather alert", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
                        Text("Rain probability increasing in the next hours. Consider indoor activities.", fontSize = 12.sp, color = Color(0xFFB45309))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("HOURLY FORECAST", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                pronostico.forEach { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                                    val iconTint = if (item.icono == Icons.Default.WbSunny) Color(0xFFFBBF24) else if (item.icono == Icons.Default.Umbrella) Color(0xFF60A5FA) else Color.Gray
                                    Icon(item.icono, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(item.dia, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text(item.hora, fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Text(item.temp, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                val probabilityInt = item.lluvia.removeSuffix("%").toInt()
                                Column(horizontalAlignment = Alignment.End, modifier = Modifier.width(40.dp)) {
                                    Text(item.lluvia, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (probabilityInt > 50) Color(0xFF3B82F6) else Color.Gray)
                                    Box(modifier = Modifier.fillMaxWidth().height(6.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape).padding(top = 4.dp)) {
                                        Box(modifier = Modifier.fillMaxWidth(probabilityInt.toFloat() / 100f).height(6.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("BEST TIMES TO PLAY", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("Today 3:00 PM - 5:00 PM", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Low chance of rain", fontSize = 11.sp, color = Color.Gray)
                        }
                        Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(22.dp))
                    }
                }
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("Tomorrow 9:00 AM - 11:00 AM", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Clear weather", fontSize = 11.sp, color = Color.Gray)
                        }
                        Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(22.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Updated 15 minutes ago", fontSize = 11.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun WeatherStatBox(icon: ImageVector, label: String, value: String, tint: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(label, fontSize = 10.sp, color = Color.White.copy(alpha = 0.6f))
                Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
