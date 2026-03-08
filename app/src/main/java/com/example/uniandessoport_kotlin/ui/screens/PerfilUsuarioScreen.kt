package com.example.uniandessoport_kotlin.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class UserProfile(val nombre: String, val universidad: String, val semestre: String, val streak: Int, val totalMatches: Int, val winRate: Int, val avgPace: String, val challengesCompleted: Int)
data class CommunityRanking(val clan: String, val position: Int, val total: Int, val sport: String, val icon: ImageVector)
data class MatchHistoryItem(val title: String, val result: String, val score: String, val opponent: String, val date: String, val icon: ImageVector)
data class Badge(val name: String, val icon: ImageVector, val earned: Boolean, val desc: String)

@Composable
fun PerfilUsuarioScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    var activeTab by remember { mutableStateOf("stats") }

    val user = UserProfile(
        nombre = "Julián Martínez",
        universidad = "Universidad de los Andes",
        semestre = "7th Semester — Systems Engineering",
        streak = 7,
        totalMatches = 34,
        winRate = 68,
        avgPace = "5:23 min/km",
        challengesCompleted = 8
    )

    val rankings = listOf(
        CommunityRanking("Running UniAndes", 12, 67, "Running", Icons.Default.DirectionsRun),
        CommunityRanking("UniAndes Football Club", 5, 48, "Soccer", Icons.Default.SportsSoccer),
        CommunityRanking("Calisthenics Crew", 3, 29, "Calisthenics", Icons.Default.FitnessCenter)
    )

    val matchHistory = listOf(
        MatchHistoryItem("5v5 Soccer", "Won", "4-2", "Team Alpha", "Feb 24, 2026", Icons.Default.SportsSoccer),
        MatchHistoryItem("5K Campus Run", "Completed", "24:15", "-", "Feb 22, 2026", Icons.Default.DirectionsRun),
        MatchHistoryItem("5v5 Casual", "Lost", "1-3", "Team Beta", "Feb 20, 2026", Icons.Default.SportsSoccer),
        MatchHistoryItem("Push-up Challenge", "Completed", "150 reps", "-", "Feb 18, 2026", Icons.Default.FitnessCenter),
        MatchHistoryItem("Copa Turing R1", "Won", "3-1", "Team Gamma", "Feb 15, 2026", Icons.Default.SportsSoccer)
    )

    val badges = listOf(
        Badge("First Match", Icons.Default.SportsEsports, true, "Complete your first match"),
        Badge("7-Day Streak", Icons.Default.LocalFireDepartment, true, "Stay active for 7 days straight"),
        Badge("Speed Demon", Icons.Default.FlashOn, true, "Run 5K under 25 minutes"),
        Badge("Team Player", Icons.Default.Group, true, "Join 3 communities"),
        Badge("100K Runner", Icons.Default.DirectionsRun, false, "Run 100 km total"),
        Badge("Tournament Champ", Icons.Default.EmojiEvents, false, "Win a tournament"),
        Badge("Coach Rated", Icons.Default.Star, true, "Rate a coaching session"),
        Badge("Challenge Creator", Icons.Default.AdsClick, false, "Create a community challenge"),
        Badge("Top 10", Icons.Default.WorkspacePremium, true, "Reach Top 10 in any community")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.clickable { onNavigate("home") },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Back", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Back", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                            .clickable { /* Toggle Theme */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.DarkMode, contentDescription = "Theme", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                            .border(2.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(user.nombre.split(" ").map { it.take(1) }.joinToString(""), fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(user.nombre, fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color.White)
                        Text(user.universidad, fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                        Text(user.semestre, fontSize = 11.sp, color = Color.White.copy(alpha = 0.5f))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MiniStat(label = "Matches", value = "${user.totalMatches}", modifier = Modifier.weight(1f))
                    MiniStat(label = "Win Rate", value = "${user.winRate}%", modifier = Modifier.weight(1f))
                    MiniStat(label = "Avg Pace", value = user.avgPace, modifier = Modifier.weight(1f))
                    MiniStat(label = "Streak", value = "${user.streak}d", modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            listOf("stats" to "Rankings", "history" to "History", "badges" to "Badges").forEach { (id, label) ->
                val isSelected = activeTab == id
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent)
                        .clickable { activeTab = id }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            when (activeTab) {
                "stats" -> {
                    Text("COMMUNITY RANKINGS", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        rankings.forEach { rank ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(rank.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                            }
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(rank.clan, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                                Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(12.dp)) {
                                                    Text(rank.sport, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                                                }
                                            }
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text("#${rank.position}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                            Text("of ${rank.total}", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Box {
                                        Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape))
                                        val fraction = ((rank.total - rank.position + 1).toFloat() / rank.total.toFloat()).coerceIn(0f, 1f)
                                        Box(modifier = Modifier.fillMaxWidth(fraction).height(8.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Top ${Math.round((rank.position.toFloat() / rank.total.toFloat()) * 100)}%", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
                "history" -> {
                    Text("MATCH HISTORY", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        matchHistory.forEach { match ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Row(modifier = Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(match.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(match.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                                                val resultColor = when (match.result) {
                                                    "Won" -> Color(0xFF059669)
                                                    "Lost" -> Color(0xFFDC2626)
                                                    else -> Color(0xFF2563EB)
                                                }
                                                val resultBgColor = resultColor.copy(alpha = 0.1f)
                                                Surface(color = resultBgColor, shape = RoundedCornerShape(12.dp)) {
                                                    Text(match.result, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = resultColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                                                }
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(match.score, fontSize = 11.sp, color = Color.Gray)
                                            }
                                        }
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(match.date, fontSize = 11.sp, color = Color.Gray)
                                        if (match.opponent != "-") {
                                            Text("vs ${match.opponent}", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                "badges" -> {
                    val earnedCount = badges.count { it.earned }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("EARNED BADGES", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text("$earnedCount/${badges.size}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Displaying grid of badges
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            badges.filterIndexed { index, _ -> index % 3 == 0 }.forEach { badge ->
                                BadgeItem(name = badge.name, icon = badge.icon, desc = badge.desc, earned = badge.earned)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            badges.filterIndexed { index, _ -> index % 3 == 1 }.forEach { badge ->
                                BadgeItem(name = badge.name, icon = badge.icon, desc = badge.desc, earned = badge.earned)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            badges.filterIndexed { index, _ -> index % 3 == 2 }.forEach { badge ->
                                BadgeItem(name = badge.name, icon = badge.icon, desc = badge.desc, earned = badge.earned)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MiniStat(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.Medium, color = Color.White.copy(alpha = 0.6f), letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun BadgeItem(name: String, icon: ImageVector, desc: String, earned: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (earned) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .border(1.dp, if (earned) Color.LightGray.copy(alpha = 0.3f) else Color.Transparent, RoundedCornerShape(16.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(if (earned) MaterialTheme.colorScheme.secondaryContainer else Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = if (earned) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f), modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(name, fontSize = 11.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, lineHeight = 14.sp, color = if (earned) Color.Black else Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
            Text(desc, fontSize = 9.sp, color = Color.Gray, textAlign = TextAlign.Center, lineHeight = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}
