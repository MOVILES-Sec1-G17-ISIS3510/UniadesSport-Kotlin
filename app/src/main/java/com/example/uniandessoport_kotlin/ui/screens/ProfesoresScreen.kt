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

data class Profesor(
    val id: Int,
    val nombre: String,
    val deporte: Sport,
    val rating: Double,
    val totalReviews: Int,
    val precio: String,
    val experiencia: String,
    val whatsapp: String,
    val disponibilidad: String,
    val especialidad: String,
    val verified: Boolean,
    val sessionsDelivered: Int,
    val tournamentWins: Int,
    val rankInSport: Int,
    val totalCoachesInSport: Int
)

data class Review(val id: Int, val estudiante: String, val rating: Int, val comentario: String, val fecha: String)

val mockProfesores = listOf(
    Profesor(1, "Carlos Mendez", Sport.SOCCER, 4.8, 24, "$30/hour", "8 years", "+57 300 1234567", "Mon-Fri 4-8 PM", "Technical skills & tactics", true, 142, 5, 1, 8),
    Profesor(2, "Ana Rodriguez", Sport.TENNIS, 4.9, 31, "$35/hour", "10 years", "+57 310 7654321", "Tue-Sat 9 AM-6 PM", "Singles & doubles strategy", true, 210, 8, 1, 5),
    Profesor(3, "Miguel Torres", Sport.BASKETBALL, 4.7, 18, "$28/hour", "6 years", "+57 315 9876543", "Mon-Wed 5-9 PM", "Shooting & defense", false, 67, 2, 3, 4),
    Profesor(4, "Laura Gomez", Sport.SWIMMING, 5.0, 12, "$40/hour", "12 years", "+57 320 4561237", "Daily 6-10 AM", "All strokes & endurance", true, 320, 12, 1, 3),
    Profesor(5, "David Silva", Sport.RUNNING, 4.6, 15, "$25/hour", "5 years", "+57 318 7894561", "Mon-Sat 6-9 AM", "Marathon training", false, 45, 1, 4, 6)
)

val mockReviews = mapOf(
    1 to listOf(Review(1, "Student A", 5, "Excellent coach! Improved my technique significantly.", "Jan 20, 2026"), Review(2, "Student B", 5, "Very patient and knowledgeable. Highly recommend!", "Jan 15, 2026")),
    2 to listOf(Review(1, "Student D", 5, "Best tennis coach! My serve has improved dramatically.", "Jan 22, 2026"))
)

@Composable
fun ProfesoresScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    var filter by remember { mutableStateOf<Sport?>(null) }
    var selectedProfesor by remember { mutableStateOf<Profesor?>(null) }
    var showReviewModal by remember { mutableStateOf(false) }

    val filteredProfesores = if (filter == null) mockProfesores else mockProfesores.filter { it.deporte == filter }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
            .padding(top = 8.dp)
    ) {
        // Sport Filters
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
                label = { Text("All Coaches", fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(16.dp),
                border = null
            )
            Sport.entries.forEach { sport ->
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

        // Coach Cards
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            filteredProfesores.forEach { prof ->
                ProfesorCard(profesor = prof, onClick = { selectedProfesor = prof })
            }
            if (filteredProfesores.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No coaches found for this sport", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }

    selectedProfesor?.let { prof ->
        ProfesorDetailDialog(
            profesor = prof,
            onDismiss = { selectedProfesor = null },
            onAddReview = { showReviewModal = true }
        )
    }

    if (showReviewModal) {
        Dialog(onDismissRequest = { showReviewModal = false }) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                        Text("ADD REVIEW", fontSize = 18.sp, fontWeight = FontWeight.Black)
                        IconButton(onClick = { showReviewModal = false }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Rating", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        (1..5).forEach { _ ->
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(32.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Comment", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Share your experience...") },
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = { showReviewModal = false }, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(12.dp)) {
                            Text("Cancel")
                        }
                        Button(onClick = { showReviewModal = false }, modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(12.dp)) {
                            Text("Submit", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfesorCard(profesor: Profesor, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Row(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(profesor.nombre.split(" ").map { it.take(1) }.joinToString(""), fontSize = 20.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(profesor.nombre, fontSize = 16.sp, fontWeight = FontWeight.Black)
                            if (profesor.verified) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${profesor.deporte.label} • ${profesor.precio}", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = Color(0xFFFEF3C7), shape = RoundedCornerShape(4.dp)) {
                                Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(profesor.rating.toString(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("${profesor.totalReviews} reviews", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("${profesor.experiencia} exp.", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color(0xFFF97316), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("#${profesor.rankInSport} in ${profesor.deporte.label}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Specialty: ${profesor.especialidad}", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onClick,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("View Profile", fontWeight = FontWeight.Bold)
                }
                Surface(
                    onClick = { /* Handle WhatsApp */ },
                    color = Color(0xFF25D366).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF25D366), modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProfesorDetailDialog(profesor: Profesor, onDismiss: () -> Unit, onAddReview: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f).padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header Content
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(profesor.nombre, fontSize = 18.sp, fontWeight = FontWeight.Black, lineHeight = 22.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                    (1..5).forEach { _ ->
                                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(16.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(profesor.rating.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }
                            Text("${profesor.totalReviews} reviews", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                        }
                        IconButton(onClick = onDismiss, modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape).size(32.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(16.dp))
                        }
                    }
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))

                // Scrollable details
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    // Stats Grid
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatBoxSmall("Sport", profesor.deporte.label, modifier = Modifier.weight(1f))
                        StatBoxSmall("Price", profesor.precio, modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatBoxSmall("Experience", profesor.experiencia, modifier = Modifier.weight(1f))
                        StatBoxSmall("Availability", profesor.disponibilidad, modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("SPECIALTY", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.Gray, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(profesor.especialidad, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Performance
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("PERFORMANCE", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PerfBox(profesor.sessionsDelivered.toString(), "Sessions", modifier = Modifier.weight(1f))
                        PerfBox(profesor.tournamentWins.toString(), "Wins", modifier = Modifier.weight(1f))
                        PerfBox("#${profesor.rankInSport}", "Rank", modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ranked #${profesor.rankInSport} of ${profesor.totalCoachesInSport} ${profesor.deporte.label} coaches", fontSize = 11.sp, color = Color.Gray)
                        if (profesor.verified) {
                            Text(" • Verified Athlete", fontSize = 11.sp, color = Color.Gray)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Reviews
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("REVIEWS", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        TextButton(onClick = onAddReview) {
                            Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Review", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    val reviews = mockReviews[profesor.id] ?: emptyList()
                    if (reviews.isEmpty()) {
                        Text("No reviews yet.", fontSize = 12.sp, color = Color.Gray)
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            reviews.forEach { review ->
                                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(review.estudiante, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                                (1..5).forEach { i ->
                                                    Icon(Icons.Default.Star, contentDescription = null, tint = if (i <= review.rating) Color(0xFFFBBF24) else Color.LightGray, modifier = Modifier.size(10.dp))
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(review.fecha, fontSize = 10.sp, color = Color.Gray)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(review.comentario, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Bottom Actions
                Box(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(
                            onClick = { /* Handle WhatsApp */ },
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Contact", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Book Class", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBoxSmall(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun PerfBox(value: String, label: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(label.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.Medium, color = Color.Gray, letterSpacing = 1.sp)
        }
    }
}
