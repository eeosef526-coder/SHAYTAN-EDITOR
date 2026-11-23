
package com.shaytan.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Black = Color(0xFF08080C)
private val Panel = Color(0xFF171720)
private val Gold = Color(0xFFD6AD45)
private val Purple = Color(0xFF9B5CFF)
private val White = Color(0xFFF7F5FA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ShaitanEditor()
            }
        }
    }
}

@Composable
fun ShaitanEditor() {
    var showTransitions by remember { mutableStateOf(false) }
    var showEffects by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("الرئيسية") }

    when {
        showEffects -> EffectLibraryScreen(
            onBack = { showEffects = false }
        )
        showTransitions -> TransitionsScreen(
            onBack = { showTransitions = false }
        )
        else -> HomeScreen(
            onOpenTransitions = { showTransitions = true },
            onOpenEffects = { showEffects = true },
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
    }
}

@Composable
fun HomeScreen(
    onOpenTransitions: () -> Unit,
    onOpenEffects: () -> Unit,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    var message by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Black,
        snackbarHost = {
            SnackbarHost(remember { SnackbarHostState() })
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF121218),
                contentColor = White
            ) {
                listOf("الرئيسية", "مشاريعي", "التأثيرات").forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = {
                            onTabSelected(tab)
                            if (tab == "التأثيرات") onOpenEffects()
                            if (tab == "مشاريعي") {
                                message = "قسم المشاريع قيد التطوير"
                            }
                        },
                        icon = {
                            Text(
                                when (tab) {
                                    "الرئيسية" -> "⌂"
                                    "مشاريعي" -> "▣"
                                    else -> "✦"
                                },
                                fontSize = 21.sp
                            )
                        },
                        label = { Text(tab, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Gold,
                            selectedTextColor = Gold,
                            indicatorColor = Color(0xFF302719),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
        ) {
            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "مـطـور شـيـطـآن",
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "SHAYTAN EDITOR",
                        color = Gold,
                        fontSize = 11.sp,
                        letterSpacing = 3.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    "♛",
                    color = Gold,
                    fontSize = 42.sp
                )
            }

            Spacer(Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF39204E),
                                Color(0xFF171322),
                                Color(0xFF101018)
                            )
                        ),
                        RoundedCornerShape(24.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFF70459A),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        "أطلق إبداعك",
                        color = White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "حوّل أفكارك إلى فيديوهات احترافية",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                    Spacer(Modifier.height(20.dp))

                    Surface(
                        onClick = {
                            message = "إنشاء المشروع قيد التطوير"
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = Gold
                    ) {
                        Text(
                            "＋  مشروع جديد",
                            color = Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                horizontal = 22.dp,
                                vertical = 12.dp
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            Text(
                "أدوات الإبداع",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(15.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ToolCard(
                    "🎬",
                    "محرر الفيديو",
                    "قص ودمج وتعديل",
                    Purple,
                    Modifier.weight(1f)
                ) {
                    message = "محرر الفيديو قيد التطوير"
                }

                ToolCard(
                    "🖼",
                    "محرر الصور",
                    "تعديل الصور",
                    Gold,
                    Modifier.weight(1f)
                ) {
                    message = "محرر الصور قيد التطوير"
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ToolCard(
                    "✨",
                    "التأثيرات",
                    "فلاتر وإضاءة",
                    Purple,
                    Modifier.weight(1f),
                    onOpenEffects
                )

                ToolCard(
                    "⚡",
                    "الانتقالات",
                    "حركات احترافية",
                    Gold,
                    Modifier.weight(1f),
                    onOpenTransitions
                )
            }

            Spacer(Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "مشاريعك الأخيرة",
                    color = White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    "عرض الكل",
                    color = Gold,
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Panel, RoundedCornerShape(18.dp))
                    .padding(25.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("▧", color = Purple, fontSize = 38.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "لا توجد مشاريع حتى الآن",
                        color = White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "ابدأ مشروعك الأول واكتشف أدوات التحرير",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(Modifier.height(25.dp))

            Text(
                "SHAYTAN EDITOR  •  CREATIVE STUDIO",
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(20.dp))
        }
    }

    if (message.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { message = "" },
            confirmButton = {
                TextButton(onClick = { message = "" }) {
                    Text("حسناً", color = Gold)
                }
            },
            title = { Text("مطور شيطان", color = White) },
            text = { Text(message, color = Color.LightGray) },
            containerColor = Panel
        )
    }
}

@Composable
fun ToolCard(
    emoji: String,
    title: String,
    subtitle: String,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(125.dp),
        shape = RoundedCornerShape(18.dp),
        color = Panel,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            Color(0xFF30303B)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 27.sp)
            Spacer(Modifier.height(7.dp))
            Text(
                title,
                color = accent,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                subtitle,
                color = Color.Gray,
                fontSize = 10.sp
            )
        }
    }
}

