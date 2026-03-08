package com.example.uniandessoport_kotlin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uniandessoport_kotlin.ui.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 80.dp) // Leave space for bottom nav
    ) {
        // Stats Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatPill(
                icon = Icons.Default.LocalFireDepartment,
                label = "Streak",
                value = "7 DAYS",
                iconColor = Color(0xFFF97316),
                iconBg = Color(0xFFFFF7ED),
                modifier = Modifier.weight(1f)
            )
            StatPill(
                icon = Icons.Default.TrendingUp,
                label = "This week",
                value = "3 ACTS",
                iconColor = Color(0xFF10B981),
                iconBg = Color(0xFFECFDF5),
                modifier = Modifier.weight(1f)
            )
        }

        // Quick Access Horizontal Scroll
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickChip(icon = Icons.Default.Cloud, label = "24° Cloudy") { onNavigate(Screen.Clima.route) }
            QuickChip(icon = Icons.Default.MonitorHeart, label = "Strava") { onNavigate(Screen.Strava.route) }
            QuickChip(icon = Icons.Default.Schedule, label = "History") { onNavigate(Screen.Historial.route) }
            QuickChip(icon = Icons.Default.EmojiEvents, label = "Tournaments") { onNavigate(Screen.Torneos.route) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Activity
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SectionHeader(title = "Quick Activity")
            Text("Based on your profile and schedule", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))
            
            QuickActivityCard(
                icon = Icons.Default.DirectionsRun,
                title = "30-min Interval Run",
                subtitle = "Campus Track • 400m away",
                time = "30 min",
                tag = "Matches your running goal",
                color = Color(0xFFECFDF5),
                textColor = Color(0xFF047857),
                iconColor = Color(0xFF059669)
            )
            Spacer(modifier = Modifier.height(8.dp))
            QuickActivityCard(
                icon = Icons.Default.FitnessCenter,
                title = "Calisthenics Challenge",
                subtitle = "Trending in your community",
                time = "20 min",
                tag = "12 participants today",
                color = Color(0xFFFFFBEB),
                textColor = Color(0xFFB45309),
                iconColor = Color(0xFFD97706)
            )
            Spacer(modifier = Modifier.height(8.dp))
            QuickActivityCard(
                icon = Icons.Default.SportsSoccer,
                title = "5v5 Soccer – 1 spot left!",
                subtitle = "La Caneca • Starts in 10 min",
                time = "45 min",
                tag = "Join now",
                color = Color(0xFFEFF6FF),
                textColor = Color(0xFF1D4ED8),
                iconColor = Color(0xFF2563EB),
                highlight = true
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Active Challenges
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SectionHeader(title = "Active Challenges", action = "View all", onAction = { onNavigate(Screen.Retos.route) })
            Spacer(modifier = Modifier.height(12.dp))
            ChallengeCard(title = "100K Running Challenge", daysLeft = 15, progress = 35, participants = 45)
            Spacer(modifier = Modifier.height(12.dp))
            ChallengeCard(title = "30-Day Push-ups", daysLeft = 22, progress = 42, participants = 67)
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        // Recommended for You
        Column {
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                SectionHeader(title = "Recommended for You", action = "See more", onAction = { onNavigate(Screen.Comunidades.route) })
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RecommendedCard(
                    sport = "Tennis",
                    title = "Doubles Tournament",
                    community = "UniAndes Racquets",
                    spots = 6,
                    date = "Sat, Mar 1"
                )
                RecommendedCard(
                    sport = "Running",
                    title = "5K Night Run",
                    community = "Running UniAndes",
                    spots = 18,
                    date = "Fri, Feb 28"
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Upcoming Matches
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SectionHeader(title = "Upcoming Matches")
            Spacer(modifier = Modifier.height(12.dp))
            EventCard(title = "5v5 Soccer", time = "Today 3:00 PM", location = "UniAndes Courts")
            Spacer(modifier = Modifier.height(12.dp))
            EventCard(title = "Tennis Doubles", time = "Tomorrow 10:00 AM", location = "El Nogal Club")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Sub-components

@Composable
fun StatPill(icon: ImageVector, label: String, value: String, iconColor: Color, iconBg: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun QuickChip(icon: ImageVector, label: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun SectionHeader(title: String, action: String? = null, onAction: (() -> Unit)? = null) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        if (action != null && onAction != null) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onAction() }) {
                Text(action, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun QuickActivityCard(icon: ImageVector, title: String, subtitle: String, time: String, tag: String, color: Color, textColor: Color, iconColor: Color, highlight: Boolean = false) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (highlight) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = if (highlight) 4.dp else 1.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(subtitle, fontSize = 11.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(time, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = color,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(tag, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = textColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
            }
        }
    }
}

@Composable
fun ChallengeCard(title: String, daysLeft: Int, progress: Int, participants: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("$daysLeft days remaining", fontSize = 12.sp, color = Color.Gray)
                }
                Text("$progress%", fontSize = 24.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("+$participants participants", fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
fun RecommendedCard(sport: String, title: String, community: String, spots: Int, date: String) {
    Card(
        modifier = Modifier.width(200.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(sport, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(community, fontSize = 11.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(date, fontSize = 11.sp, color = Color.Gray)
                }
                Text("$spots spots", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun EventCard(title: String, time: String, location: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(time, fontSize = 12.sp, color = Color.Gray)
                Text(location, fontSize = 11.sp, color = Color.Gray)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}
