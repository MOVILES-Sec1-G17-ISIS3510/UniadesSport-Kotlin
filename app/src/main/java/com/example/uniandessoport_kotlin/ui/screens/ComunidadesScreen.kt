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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

data class Comunidad(
    val id: Int,
    val nombre: String,
    val tipo: String, // Clan, Community
    val deporte: String,
    val miembros: Int,
    val descripcion: String,
    val canales: Int
)

val mockComunidades = listOf(
    Comunidad(1, "UniAndes Football Club", "Clan", "Soccer", 48, "Official soccer clan. Weekly training sessions.", 3),
    Comunidad(2, "UniAndes Racquets", "Community", "Tennis", 32, "Tennis community for all skill levels.", 2),
    Comunidad(3, "Basketball Warriors", "Clan", "Basketball", 24, "Competitive basketball clan 3x3 and 5x5.", 3),
    Comunidad(4, "Running UniAndes", "Community", "Running", 67, "Runners group for all levels.", 2)
)

data class FeedPost(val id: Int, val author: String, val role: String, val content: String, val time: String, val pinned: Boolean, val likes: Int)

val mockFeed = listOf(
    FeedPost(1, "Daniel Torres", "Instructor", "⚡ Schedule change: tomorrow's training session moved to 5 PM at La Caneca. See you there!", "1h ago", true, 12),
    FeedPost(2, "Sofia Castañeda", "Organizer", "🏆 Copa Turing 2026 registrations are OPEN! 16 team slots — register your team before Mar 1.", "3h ago", true, 34),
    FeedPost(3, "Julián Martínez", "Member", "Great match today! Our team won 4-2 against Team Beta. Thanks for the coordination 🙌", "6h ago", false, 8)
)

@Composable
fun ComunidadesScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    var filter by remember { mutableStateOf("all") }
    var selectedComunidad by remember { mutableStateOf<Comunidad?>(null) }
    
    val filteredComunidades = if (filter == "all") mockComunidades else mockComunidades.filter { it.tipo == filter }
    val trending = mockComunidades.take(2)
    val others = if (filter == "all") mockComunidades.drop(2) else filteredComunidades

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
            .padding(top = 8.dp)
    ) {
        // Filters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("all", "Community", "Clan").forEach { f ->
                val isSelected = filter == f
                val label = if (f == "all") "All" else if (f == "Clan") "Clans" else "Communities"
                FilterChip(
                    selected = isSelected,
                    onClick = { filter = f },
                    label = { Text(label, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = null
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trending Section
        if (filter == "all") {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color(0xFFF97316), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("TRENDING NOW", fontSize = 14.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                trending.forEach { com ->
                    TrendingCard(comunidad = com, onClick = { selectedComunidad = com })
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Discover More / Filtered List
        Text(
            if (filter == "all") "DISCOVER MORE" else "FILTERED ${if (filter == "Clan") "CLANS" else "COMMUNITIES"}",
            fontSize = 14.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            others.forEach { com ->
                ComunidadCard(comunidad = com, onClick = { selectedComunidad = com })
            }
            if (others.isEmpty()) {
                Text("No communities found.", color = Color.Gray, modifier = Modifier.padding(16.dp))
            }
        }
    }

    selectedComunidad?.let { com ->
        ComunidadDetailDialog(comunidad = com, onDismiss = { selectedComunidad = null })
    }
}

@Composable
fun TrendingCard(comunidad: Comunidad, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(240.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))))
            .clickable(onClick = onClick)
    ) {
        // Decorative background letter
        Text(
            text = comunidad.nombre.take(1),
            fontSize = 100.sp,
            fontWeight = FontWeight.Black,
            color = Color.White.copy(alpha = 0.05f),
            modifier = Modifier.align(Alignment.BottomEnd).offset(x = 16.dp, y = 24.dp)
        )

        Column(modifier = Modifier.padding(20.dp).fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Surface(color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)) {
                    Text(comunidad.tipo, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
                Box(modifier = Modifier.size(24.dp).background(Color.White.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(comunidad.nombre, fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(comunidad.deporte, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White.copy(alpha = 0.8f))
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(6.dp)).padding(horizontal = 6.dp, vertical = 4.dp)) {
                    Icon(Icons.Default.Group, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${comunidad.miembros}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(6.dp)).padding(horizontal = 6.dp, vertical = 4.dp)) {
                    Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${comunidad.canales}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ComunidadCard(comunidad: Comunidad, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(comunidad.nombre.take(1), fontSize = 24.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(comunidad.nombre, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(comunidad.descripcion, fontSize = 12.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(8.dp)) {
                        Text(comunidad.tipo, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.size(4.dp).background(Color.LightGray, CircleShape))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(comunidad.deporte, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Group, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${comunidad.miembros}", fontSize = 12.sp, color = Color.Gray)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${comunidad.canales}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun ComunidadDetailDialog(comunidad: Comunidad, onDismiss: () -> Unit) {
    var selectedTab by remember { mutableStateOf("feed") }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f).padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(comunidad.nombre, fontSize = 18.sp, fontWeight = FontWeight.Black, lineHeight = 22.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(comunidad.descripcion, fontSize = 12.sp, color = Color.Gray)
                        }
                        IconButton(onClick = onDismiss, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape).size(32.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatBox(label = "Type", value = comunidad.tipo, modifier = Modifier.weight(1f))
                        StatBox(label = "Sport", value = comunidad.deporte, modifier = Modifier.weight(1f))
                        StatBox(label = "Members", value = "${comunidad.miembros}", modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tab Selector
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    ) {
                        listOf("feed" to "Feed", "channels" to "Channels", "members" to "Members").forEach { (id, label) ->
                            val isSelected = selectedTab == id
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent)
                                    .clickable { selectedTab = id }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                            }
                        }
                    }
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))

                // Scrollable Content area
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    when (selectedTab) {
                        "feed" -> FeedTabContent()
                        "channels" -> ChannelsTabContent(comunidad.canales)
                        "members" -> MembersTabContent(comunidad.miembros)
                    }
                }

                // Bottom Action
                Box(modifier = Modifier.padding(20.dp)) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Join Community", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun StatBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun FeedTabContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedButton(
            onClick = { /* New Announce */ },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
        ) {
            Icon(Icons.Default.Campaign, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("New Announcement", color = Color.Gray, fontWeight = FontWeight.SemiBold)
        }

        mockFeed.forEach { post ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (post.pinned) MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
                ),
                border = if (post.pinned) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer) else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.Top) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(post.author.split(" ").map { it.take(1) }.joinToString(""), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(post.author, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp)) {
                                    Text(post.role, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.Gray, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                                Text(post.time, fontSize = 10.sp, color = Color.Gray)
                                if (post.pinned) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.Default.PushPin, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(10.dp))
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text("Pinned", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(post.content, fontSize = 12.sp, lineHeight = 18.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${post.likes}", fontSize = 11.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reply", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChannelsTabContent(total: Int) {
    val mockChannels = listOf("general" to false, "matches" to false, "strategy" to true)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        mockChannels.forEach { (name, isPrivate) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (isPrivate) Icons.Default.Lock else Icons.Default.Tag,
                        contentDescription = null,
                        tint = if (isPrivate) Color.Gray else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }
                Text("${(10..200).random()} msgs", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun MembersTabContent(total: Int) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        (1..minOf(total, 5)).forEach { i ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("S$i", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Student $i", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    Text(if (i <= 2) "Admin" else "Member", fontSize = 10.sp, color = Color.Gray)
                }
                if (i <= 2) {
                    Icon(Icons.Default.Stars, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
