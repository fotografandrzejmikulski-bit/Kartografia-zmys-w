package pl.kartografiazmyslow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF0D0D0F)
private val Paper = Color(0xFFF2EEE8)
private val Copper = Color(0xFFC28B63)
private val Muted = Color(0xFFA6A09A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KartografiaApp() }
    }
}

@androidx.compose.runtime.Composable
private fun KartografiaApp() {
    var screen by remember { mutableStateOf("session") }
    var phase by remember { mutableStateOf("KALIBRACJA") }
    var tension by remember { mutableStateOf(1) }
    var score by remember { mutableStateOf(0) }
    var bank by remember { mutableStateOf(0) }
    var black by remember { mutableStateOf(0) }
    val threshold = (11 - tension).coerceIn(1, 10)

    MaterialTheme(colorScheme = darkColorScheme(background = Ink, surface = Color(0xFF171719), primary = Copper, onPrimary = Ink)) {
        Surface(modifier = Modifier.fillMaxSize(), color = Ink) {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp, vertical = 14.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("KARTOGRAFIA", color = Paper, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                        Text("Zmysłów", color = Copper, fontSize = 28.sp, fontWeight = FontWeight.Light)
                    }
                    Button(onClick = { phase = "PAUZA" }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF272528))) { Text("PAUZA") }
                }
                Spacer(Modifier.height(18.dp))

                if (phase == "PAUZA") {
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF201E21)), modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("SESJA WSTRZYMANA", color = Paper, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                            Text("Stan rozgrywki jest zamrożony. Możesz wrócić lub bezpiecznie zakończyć sesję.", color = Muted, fontSize = 14.sp)
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Button(onClick = { phase = "RYZYKO" }) { Text("WZNÓW") }
                                Button(onClick = { phase = "SAFE_EXIT" }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A2424))) { Text("STOP") }
                            }
                        }
                    }
                } else if (phase == "SAFE_EXIT") {
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF201E21)), modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("SESJA ZAKOŃCZONA", color = Paper, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                            Text("Stan został pozostawiony poza aktywną rozgrywką. Możesz rozpocząć nową sesję bez odzyskiwania poprzedniego kontekstu.", color = Muted, fontSize = 14.sp)
                            Button(onClick = { phase = "KALIBRACJA"; score = 0; bank = 0; black = 0; tension = 1 }) { Text("NOWA SESJA") }
                        }
                    }
                } else {
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF171719)), modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(18.dp)) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text(phase, color = Copper, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                                Text("2 GRACZY", color = Muted, fontSize = 12.sp)
                            }
                            Spacer(Modifier.height(12.dp))
                            Text("Napięcie", color = Muted, fontSize = 13.sp)
                            Text("$tension", color = Paper, fontSize = 40.sp, fontWeight = FontWeight.Light)
                            Text("Próg kontroli: $threshold", color = if (black >= threshold) Color(0xFFE88787) else Muted, fontSize = 12.sp)
                            Spacer(Modifier.height(10.dp))
                            Box(Modifier.fillMaxWidth().height(8.dp).background(Color(0xFF2A292B), RoundedCornerShape(8.dp))) {
                                Box(Modifier.fillMaxWidth(tension / 10f).height(8.dp).background(Copper, RoundedCornerShape(8.dp)))
                            }
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF171719)), modifier = Modifier.fillMaxWidth().weight(1f)) {
                        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.SpaceBetween) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("MAPA WĘZŁÓW", color = Muted, fontSize = 12.sp, letterSpacing = 2.sp)
                                Text("Sześć obszarów • stan sesji", color = Paper, fontSize = 20.sp)
                                NodeGrid()
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Stat("BANK", bank)
                                Stat("TURA", score)
                                Stat("CZARNE", black)
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(modifier = Modifier.weight(1f), onClick = {
                            phase = "RYZYKO"
                            score += 2
                            if (score % 5 == 0) { black += 1; tension = (tension + 1).coerceAtMost(10) }
                        }) { Text("DOCIĄGNIJ") }
                        Button(modifier = Modifier.weight(1f), onClick = { bank += score; score = 0; phase = "SILNIK" }) { Text("BANK") }
                    }
                }

                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    NavButton("SESJA", screen == "session") { screen = "session" }
                    NavButton("SILNIK", screen == "engine") { screen = "engine" }
                    NavButton("ZASADY", screen == "rules") { screen = "rules" }
                    NavButton("USTAWIENIA", screen == "settings") { screen = "settings" }
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun NodeGrid() {
    val nodes = listOf("KARK", "KLATKA", "BRZUCH", "UDa Z", "UDo W", "DŁONIE")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { nodes.take(3).forEach { Node(it) } }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { nodes.drop(3).forEach { Node(it) } }
    }
}

@androidx.compose.runtime.Composable
private fun Node(label: String) {
    Card(modifier = Modifier.size(98.dp, 74.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF232225))) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(label, color = Paper, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
    }
}

@androidx.compose.runtime.Composable
private fun Stat(label: String, value: Int) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(label, color = Muted, fontSize = 10.sp); Text(value.toString(), color = Paper, fontSize = 20.sp) } }

@androidx.compose.runtime.Composable
private fun NavButton(label: String, active: Boolean, onClick: () -> Unit) {
    Button(modifier = Modifier.weight(1f), onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = if (active) Color(0xFF2C2724) else Color(0xFF1B1A1C), contentColor = if (active) Copper else Muted)) { Text(label, fontSize = 9.sp) }
}
