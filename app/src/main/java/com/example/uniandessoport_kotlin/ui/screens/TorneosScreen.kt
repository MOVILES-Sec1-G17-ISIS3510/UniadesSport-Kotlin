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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

data class Torneo(
    val id: Int,
    val nombre: String,
    val deporte: Sport,
    val icon: ImageVector,
    val tipo: String,
    val fecha: String,
    val estado: String,
    val equipos: Int,
    val maxEquipos: Int,
    val ubicacion: String,
    val inscripcion: String,
    val organizador: String
)

data class TorneoMatch(
    val id: Int,
    val team1: String,
    val team2: String,
    val score1: Int,
    val score2: Int,
    val completed: Boolean,
    val time: String
)

data class BracketRound(
    val round: String,
    val matches: List<TorneoMatch>
)

val mockTorneos = listOf(
    Torneo(1, "Copa Turing 2026", Sport.SOCCER, Icons.Default.SportsSoccer, "5v5", "Mar 3-7, 2026", "Registration Open", 12, 16, "UniAndes Courts", "$15/person", "Sofia Castañeda"),
    Torneo(2, "Tennis Open Spring", Sport.TENNIS, Icons.Default.SportsTennis, "Singles", "Mar 10-12, 2026", "Registration Open", 20, 32, "UniAndes Tennis Courts", "$10/person", "UniAndes Racquets"),
    Torneo(3, "Calisthenics Championship", Sport.CALISTHENICS, Icons.Default.FitnessCenter, "Individual", "Feb 28, 2026", "Live", 24, 24, "Sports Center", "Free", "Calisthenics Crew")
)

val mockBracketRounds = listOf(
    BracketRound("Quarter-Finals", listOf(
        TorneoMatch(1, "Team Alpha", "Team Beta", 3, 1, true, "Completed"),
        TorneoMatch(2, "Team Gamma", "Team Delta", 2, 2, false, "In progress"),
        TorneoMatch(3, "Team Epsilon", "Team Zeta", 0, 0, false, "Today 4:00 PM"),
        TorneoMatch(4, "Team Eta", "Team Theta", 0, 0, false, "Today 5:00 PM")
    )),
    BracketRound("Semi-Finals", listOf(
        TorneoMatch(5, "Team Alpha", "TBD", 0, 0, false, "Mar 5"),
        TorneoMatch(6, "TBD", "TBD", 0, 0, false, "Mar 5")
    )),
    BracketRound("Final", listOf(
        TorneoMatch(7, "TBD", "TBD", 0, 0, false, "Mar 7")
    ))
)

val mockTeamPayments = listOf(
    Pair("You (Captain)", true),
    Pair("David Elías", true),
    Pair("Julián Contreras", true),
    Pair("Carlos Mendez", false),
    Pair("Miguel Torres", false)
)

@Composable
fun TorneosScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    var filter by remember { mutableStateOf<Sport?>(null) }
    var selectedTorneo by remember { mutableStateOf<Torneo?>(null) }
    var showRegister by remember { mutableStateOf(false) }

    val filteredTorneos = if (filter == null) mockTorneos else mockTorneos.filter { it.deporte == filter }

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
                    modifier = Modifier.clickable { onNavigate("home") },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Back", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Back", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Compete & Win \uD83C\uDFC6", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text("TOURNAMENTS", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                Text("Register, compete, and track live brackets", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Filters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = filter == null,
                onClick = { filter = null },
                label = { Text("All Sports", fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(16.dp),
                border = null
            )
            listOf(Sport.SOCCER, Sport.TENNIS, Sport.CALISTHENICS).forEach { sport ->
                FilterChip(
                    selected = filter == sport,
                    onClick = { filter = sport },
                    label = { Text(sport.label, fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
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

        // Cards
        Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            filteredTorneos.forEach { torneo ->
                TorneoCard(torneo = torneo, onClick = { selectedTorneo = torneo })
            }
        }
    }

    selectedTorneo?.let { torneo ->
        TorneoDetailDialog(
            torneo = torneo,
            onDismiss = { selectedTorneo = null },
            onRegister = {
                selectedTorneo = null
                showRegister = true
            }
        )
    }

    if (showRegister) {
        RegisterTeamDialog(onDismiss = { showRegister = false })
    }
}

@Composable
fun TorneoCard(torneo: Torneo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Row(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(torneo.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(torneo.nombre, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val isLive = torneo.estado == "Live"
                            Surface(
                                color = if (isLive) Color(0xFFFEF2F2) else MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    (if (isLive) "● " else "") + torneo.estado,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isLive) Color(0xFFDC2626) else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(torneo.tipo, fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color.LightGray.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(torneo.fecha, fontSize = 11.sp, color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Group, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${torneo.equipos}/${torneo.maxEquipos}", fontSize = 11.sp, color = Color.Gray)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(torneo.ubicacion, fontSize = 11.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.widthIn(max = 100.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box {
                Box(modifier = Modifier.fillMaxWidth().height(6.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape))
                val fraction = (torneo.equipos.toFloat() / torneo.maxEquipos.toFloat()).coerceIn(0f, 1f)
                val barColor = if (fraction >= 1f) Color(0xFFF87171) else MaterialTheme.colorScheme.primary
                Box(modifier = Modifier.fillMaxWidth(fraction).height(6.dp).background(barColor, CircleShape))
            }
        }
    }
}

@Composable
fun TorneoDetailDialog(torneo: Torneo, onDismiss: () -> Unit, onRegister: () -> Unit) {
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
                            Text(torneo.nombre, fontSize = 18.sp, fontWeight = FontWeight.Black, lineHeight = 22.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val isLive = torneo.estado == "Live"
                                Surface(
                                    color = if (isLive) Color(0xFFFEF2F2) else MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        (if (isLive) "● " else "") + torneo.estado,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isLive) Color(0xFFDC2626) else MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(torneo.tipo, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                        IconButton(onClick = onDismiss, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape).size(32.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(16.dp))
                        }
                    }
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))

                // Scrollable Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    // Stats
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatBoxSmall("Date", torneo.fecha, modifier = Modifier.weight(1f))
                        StatBoxSmall("Location", torneo.ubicacion, modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatBoxSmall("Teams", "${torneo.equipos}/${torneo.maxEquipos}", modifier = Modifier.weight(1f))
                        StatBoxSmall("Fee", torneo.inscripcion, modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("LIVE BRACKET", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        mockBracketRounds.forEach { round ->
                            Column {
                                Text(round.round.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, letterSpacing = 1.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    round.matches.forEach { match ->
                                        val bgColor = if (match.completed) Color(0xFFF0FDF4) else if (match.time == "In progress") Color(0xFFFFFBEB) else MaterialTheme.colorScheme.surfaceVariant
                                        val borderColor = if (match.completed) Color(0xFFA7F3D0) else if (match.time == "In progress") Color(0xFFFDE68A) else Color.Transparent
                                        
                                        Surface(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = bgColor,
                                            border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                            Text(match.team1, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = if (match.completed && match.score1 > match.score2) MaterialTheme.colorScheme.primary else Color.Gray)
                                                            Text(if (match.completed || match.time == "In progress") match.score1.toString() else "-", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                                        }
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                            Text(match.team2, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = if (match.completed && match.score2 > match.score1) MaterialTheme.colorScheme.primary else Color.Gray)
                                                            Text(if (match.completed || match.time == "In progress") match.score2.toString() else "-", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                                        }
                                                    }
                                                }
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    if (match.completed) {
                                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(12.dp))
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Text("Completed", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFF059669))
                                                    } else if (match.time == "In progress") {
                                                        Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(12.dp))
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Text("In progress", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFFD97706))
                                                    } else {
                                                        Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Text(match.time, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
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

                // Actions
                Box(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(12.dp)) {
                            Text("Close")
                        }
                        if (torneo.estado == "Registration Open") {
                            Button(onClick = onRegister, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(12.dp)) {
                                Text("Register Team", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBoxSmall(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(label.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun RegisterTeamDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f).padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                        Text("REGISTER TEAM", fontSize = 18.sp, fontWeight = FontWeight.Black)
                        IconButton(onClick = onDismiss, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape).size(32.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(16.dp))
                        }
                    }
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))

                // Scrollable Form
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    Text("Team Name", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("e.g. Team Thunder") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Team Members", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Payment Progress", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                        Text("3/5 paid", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Box {
                        Box(modifier = Modifier.fillMaxWidth().height(12.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape))
                        Box(modifier = Modifier.fillMaxWidth(0.6f).height(12.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        mockTeamPayments.forEach { (name, paid) ->
                            Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                                Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(name.take(1), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                    }
                                    if (paid) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Paid", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF059669))
                                        }
                                    } else {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Pending", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFD97706))
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
                    ) {
                        Text("+ Add Team Member", color = Color.Gray, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CreditCard, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("$15 per person", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text("Each member pays individually via Nequi/DaviPlata", fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                    }
                }

                // Actions
                Box(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(12.dp)) {
                            Text("Cancel")
                        }
                        Button(onClick = onDismiss, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(12.dp)) {
                            Text("Confirm", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
