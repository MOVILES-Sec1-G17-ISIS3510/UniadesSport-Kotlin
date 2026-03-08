package com.example.uniandessoport_kotlin.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class Sport(val label: String, val icon: ImageVector, val color: Color, val emoji: String) {
    SOCCER("Fútbol", Icons.Default.SportsSoccer, Color(0xFF22C55E), "⚽"),
    BASKETBALL("Basketball", Icons.Default.SportsBasketball, Color(0xFFF97316), "🏀"),
    TENNIS("Tennis", Icons.Default.SportsTennis, Color(0xFFEAB308), "🎾"),
    CALISTHENICS("Calistenia", Icons.Default.FitnessCenter, Color(0xFFA855F7), "🏋️"),
    RUNNING("Running", Icons.Default.DirectionsRun, Color(0xFFEF4444), "🏃"),
    SWIMMING("Natación", Icons.Default.Pool, Color(0xFF3B82F6), "🏊")
}

enum class Modality(val label: String, val icon: ImageVector, val desc: String) {
    CASUAL("Casual", Icons.Default.Handshake, "Just for fun"),
    AMATEUR("Amateur", Icons.Default.Groups, "Competitive but relaxed"),
    TORNEO("Torneo", Icons.Default.EmojiEvents, "Competitive match"),
    ENTRENAMIENTO("Training", Icons.Default.TrackChanges, "Practice & improve")
}

data class Match(
    val id: Int, val sport: Sport, val title: String, val modality: String,
    val players: String, val time: String, val location: String, val level: String
)

val mockMatches = listOf(
    Match(1, Sport.SOCCER, "Fútbol 5v5", "Casual", "7/10", "Hoy 3:00 PM", "UniAndes Courts", "Amateur"),
    Match(2, Sport.BASKETBALL, "Basketball 3v3", "Torneo", "4/6", "Hoy 5:30 PM", "Main Gym", "Intermediate"),
    Match(3, Sport.TENNIS, "Tennis Doubles", "Casual", "3/4", "Mañana 10:00 AM", "El Nogal Club", "Beginner"),
    Match(4, Sport.SOCCER, "Fútbol 7v7", "Amateur", "10/14", "Mañana 4:00 PM", "North Complex", "Advanced"),
    Match(5, Sport.RUNNING, "Running Group 5K", "Training", "6/12", "Sábado 7:00 AM", "Parque Simón Bolívar", "Open")
)

@Composable
fun PlayScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedSport by remember { mutableStateOf<Sport?>(null) }
    var selectedModality by remember { mutableStateOf<Modality?>(null) }
    var isSearching by remember { mutableStateOf(false) }
    var searchProgress by remember { mutableFloatStateOf(0f) }
    var showResults by remember { mutableStateOf(false) }

    LaunchedEffect(isSearching) {
        if (isSearching) {
            searchProgress = 0f
            while (searchProgress < 100f) {
                delay(350)
                searchProgress += (8..25).random().toFloat()
                if (searchProgress >= 100f) {
                    searchProgress = 100f
                    delay(300)
                    isSearching = false
                    showResults = true
                }
            }
        }
    }

    val filteredMatches = mockMatches.filter { match ->
        if (selectedSport != null && match.sport != selectedSport) return@filter false
        if (selectedModality != null && !match.modality.equals(selectedModality!!.label, ignoreCase = true)) return@filter false
        true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 80.dp)
    ) {
        // Stats Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatPill(
                icon = Icons.Default.Bolt,
                label = "Active Now",
                value = "24 PLAYERS",
                iconColor = Color(0xFFF59E0B),
                iconBg = Color(0xFFFEF3C7),
                modifier = Modifier.weight(1f)
            )
            StatPill(
                icon = Icons.Default.EmojiEvents,
                label = "Open Matches",
                value = "${mockMatches.size} NEARBY",
                iconColor = MaterialTheme.colorScheme.primary,
                iconBg = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSearching) {
            // Searching Animation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Searching...", fontSize = 18.sp, fontWeight = FontWeight.Black)
                        Text("Finding ${selectedSport?.label ?: ""} matches near you", fontSize = 12.sp, color = Color.Gray)
                    }
                    
                    Column(modifier = Modifier.fillMaxWidth(0.8f), horizontalAlignment = Alignment.CenterHorizontally) {
                        LinearProgressIndicator(
                            progress = { searchProgress / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                        )
                        Text("${searchProgress.toInt()}%", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
                    }

                    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth(0.8f)) {
                        SearchResultStep("Scanning nearby players...", searchProgress > 20)
                        Spacer(modifier = Modifier.height(8.dp))
                        SearchResultStep("Matching preferences...", searchProgress > 50)
                        Spacer(modifier = Modifier.height(8.dp))
                        SearchResultStep("Finding open matches...", searchProgress > 80)
                    }
                }
            }
        } else if (showResults) {
            // Results View
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Matches Found", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("${filteredMatches.size} available matches", fontSize = 12.sp, color = Color.Gray)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            selectedSport = null
                            selectedModality = null
                            isSearching = false
                            showResults = false
                            searchProgress = 0f
                        }
                    ) {
                        Text("New Search", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Active Filters
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    selectedSport?.let {
                        Surface(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)) {
                            Text("${it.emoji} ${it.label}", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
                        }
                    }
                    selectedModality?.let {
                        Surface(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(16.dp)) {
                            Text(it.label, color = MaterialTheme.colorScheme.onSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Results list
                filteredMatches.forEach { match ->
                    MatchResultCard(match)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        } else {
            // Selection Flow
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = if (selectedSport != null) "1. Sport ✓" else "1. Choose your sport",
                    fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Sport Grid
                val chunkedSports = Sport.entries.chunked(3)
                chunkedSports.forEach { rowSports ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        rowSports.forEach { sport ->
                            val isSelected = selectedSport == sport
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        selectedSport = if (isSelected) null else sport
                                        if (isSelected) selectedModality = null
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                                ),
                                border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(12.dp).fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(if (isSelected) Color.White.copy(alpha = 0.2f) else sport.color),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(sport.icon, contentDescription = null, tint = Color.White)
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        sport.label,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                        // Add empty spacers if row is not full
                        if (rowSports.size < 3) {
                            repeat(3 - rowSports.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (selectedSport != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (selectedModality != null) "2. Mode ✓" else "2. Choose mode",
                        fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    val chunkedMods = Modality.entries.chunked(2)
                    chunkedMods.forEach { rowMods ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            rowMods.forEach { mod ->
                                val isSelected = selectedModality == mod
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedModality = if (isSelected) null else mod },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                                    ),
                                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(12.dp).fillMaxWidth()
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(if (isSelected) Color.White.copy(alpha = 0.2f) else MaterialTheme.colorScheme.secondaryContainer),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(mod.icon, contentDescription = null, tint = if (isSelected) Color.White else MaterialTheme.colorScheme.primary)
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(mod.label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface)
                                            Text(mod.desc, fontSize = 10.sp, color = if (isSelected) Color.White.copy(alpha = 0.7f) else Color.Gray)
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                if (selectedSport != null && selectedModality != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = { isSearching = true },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f).height(56.dp)
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Search", fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = { /* TODO Create Match */ },
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            modifier = Modifier.weight(1f).height(56.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Create", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                
                // Open Matches Preview
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Open Matches", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(16.dp)) {
                        Text("${mockMatches.size} available", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                mockMatches.take(3).forEach { match ->
                    MatchPreviewCard(match = match)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun SearchResultStep(text: String, completed: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            if (completed) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = if (completed) MaterialTheme.colorScheme.primary else Color.LightGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 12.sp, color = if (completed) MaterialTheme.colorScheme.onSurface else Color.Gray)
    }
}

@Composable
fun MatchPreviewCard(match: Match) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(match.sport.color),
                contentAlignment = Alignment.Center
            ) {
                Icon(match.sport.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(match.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("${match.time} • ${match.players}", fontSize = 12.sp, color = Color.Gray)
            }
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
            }
        }
    }
}

@Composable
fun MatchResultCard(match: Match) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(match.sport.color),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(match.sport.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(match.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("${match.modality} • ${match.level}", fontSize = 12.sp, color = Color.Gray)
                }
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(match.players, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(match.time, fontSize = 12.sp, color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(match.location, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { /* TODO Join Match */ },
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Join Match", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(14.dp))
            }
        }
    }
}
