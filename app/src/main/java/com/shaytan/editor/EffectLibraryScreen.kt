package com.shaytan.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val EffectBlack = Color(0xFF08080C)
private val EffectPanel = Color(0xFF171720)
private val EffectGold = Color(0xFFD6AD45)
private val EffectPurple = Color(0xFF9B5CFF)
private val EffectWhite = Color(0xFFF7F5FA)

private data class EditorEffect(
    val name: String,
    val category: String,
    val description: String,
    val icon: String
)

@Composable
fun EffectLibraryScreen(onBack: () -> Unit = {}) {
    val effects = listOf(
        EditorEffect("Burning", "إضاءة وألوان", "تأثير اللهب", "🔥"),
        EditorEffect("Cells", "تشويه", "تأثير الخلايا", "▦"),
        EditorEffect("Dust", "جديد", "غبار وجزيئات", "✨"),
        EditorEffect("Electric", "إضاءة وألوان", "طاقة كهربائية", "⚡"),
        EditorEffect("Haze", "ضبابية", "ضباب وإضاءة ناعمة", "☁"),
        EditorEffect("Patronus", "إضاءة وألوان", "وهج ضوئي متحرك", "✧"),
        EditorEffect("Roughen Edges", "تشويه", "حواف خشنة", "◈"),
        EditorEffect("Saber", "إضاءة وألوان", "وهج ضوئي", "✦"),
        EditorEffect("Saber Electric", "إضاءة وألوان", "وهج كهربائي", "⚡"),
        EditorEffect("Saber Fusion", "إضاءة وألوان", "دمج الإضاءة", "💫"),
        EditorEffect("Saber Line", "إضاءة وألوان", "خط ضوئي", "➖"),
        EditorEffect("Saber Neon", "إضاءة وألوان", "نيون مضيء", "🌈")
    )

    val categories = listOf(
        "الكل", "جديد", "تشويه", "ضبابية", "إضاءة وألوان", "المفضلة"
    )

    var selectedCategory by remember { mutableStateOf("الكل") }
    var search by remember { mutableStateOf("") }
    val favorites = remember { mutableStateListOf<String>() }
    var selectedEffect by remember { mutableStateOf<EditorEffect?>(null) }
    var message by remember { mutableStateOf("") }

    val filteredEffects = effects.filter { effect ->
        val matchesSearch =
            effect.name.contains(search, ignoreCase = true) ||
            effect.description.contains(search, ignoreCase = true)

        val matchesCategory = when (selectedCategory) {
            "الكل" -> true
            "المفضلة" -> favorites.contains(effect.name)
            else -> effect.category == selectedCategory
        }

        matchesSearch && matchesCategory
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(EffectBlack)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "مكتبة التأثيرات",
                        color = EffectWhite,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "FUNIMATE SHAYTAN",
                        color = EffectGold,
                        fontSize = 10.sp,
                        letterSpacing = 2.sp
                    )
                }

                TextButton(onClick = onBack) {
                    Text("رجوع", color = EffectGold)
                }
            }

            Spacer(Modifier.height(18.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text("ابحث عن تأثير...", color = Color.Gray)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = EffectWhite,
                    unfocusedTextColor = EffectWhite,
                    focusedBorderColor = EffectGold,
                    unfocusedBorderColor = Color(0xFF393944),
                    cursorColor = EffectGold
                ),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(Modifier.height(18.dp))

            Text(
                "التصنيفات",
                color = EffectWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                categories.take(3).forEach { category ->
                    CategoryButton(
                        category = category,
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                categories.drop(3).forEach { category ->
                    CategoryButton(
                        category = category,
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "التأثيرات المتاحة",
                    color = EffectWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    "${filteredEffects.size} تأثير",
                    color = EffectGold,
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            if (filteredEffects.isEmpty()) {
                Text(
                    "لا توجد تأثيرات في هذا القسم",
                    color = Color.Gray,
                    modifier = Modifier.padding(20.dp)
                )
            }

            filteredEffects.forEach { effect ->
                EffectCard(
                    effect = effect,
                    favorite = favorites.contains(effect.name),
                    onFavorite = {
                        if (favorites.contains(effect.name)) {
                            favorites.remove(effect.name)
                        } else {
                            favorites.add(effect.name)
                        }
                    },
                    onClick = { selectedEffect = effect }
                )
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(25.dp))

            Text(
                "SHAYTAN EDITOR • EFFECT STUDIO",
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(20.dp))
        }
    }

    selectedEffect?.let { effect ->
        EffectSettingsDialog(
            effect = effect,
            onDismiss = { selectedEffect = null },
            onApply = {
                message = "تم تجهيز إعدادات ${effect.name}. ربط المعالجة الفعلية قيد التطوير."
                selectedEffect = null
            }
        )
    }

    if (message.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { message = "" },
            confirmButton = {
                TextButton(onClick = { message = "" }) {
                    Text("حسناً", color = EffectGold)
                }
            },
            title = { Text("مكتبة التأثيرات", color = EffectWhite) },
            text = { Text(message, color = Color.LightGray) },
            containerColor = EffectPanel
        )
    }
}

@Composable
private fun CategoryButton(
    category: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(category, fontSize = 10.sp)
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF382B19),
            selectedLabelColor = EffectGold,
            labelColor = Color.LightGray
        )
    )
}

@Composable
private fun EffectCard(
    effect: EditorEffect,
    favorite: Boolean,
    onFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = EffectPanel,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            Color(0xFF30303B)
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        Color(0xFF292033),
                        RoundedCornerShape(13.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(effect.icon, fontSize = 25.sp)
            }

            Spacer(Modifier.width(13.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    effect.name,
                    color = EffectWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    effect.description,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
                Text(
                    effect.category,
                    color = EffectPurple,
                    fontSize = 10.sp
                )
            }

            TextButton(onClick = onFavorite) {
                Text(
                    if (favorite) "★" else "☆",
                    color = if (favorite) EffectGold else Color.Gray,
                    fontSize = 23.sp
                )
            }

            Text(
                "›",
                color = EffectGold,
                fontSize = 25.sp
            )
        }
    }
}

@Composable
private fun EffectSettingsDialog(
    effect: EditorEffect,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    var strength by remember { mutableFloatStateOf(0.7f) }
    var size by remember { mutableFloatStateOf(0.5f) }
    var speed by remember { mutableFloatStateOf(0.5f) }
    var opacity by remember { mutableFloatStateOf(1f) }
    var glow by remember { mutableFloatStateOf(0.6f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = EffectPanel,
        title = {
            Text(effect.name, color = EffectGold, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 430.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    "إعدادات التأثير",
                    color = EffectWhite,
                    fontWeight = FontWeight.Bold
                )

                EffectSlider("القوة", strength) { strength = it }
                EffectSlider("الحجم", size) { size = it }
                EffectSlider("السرعة", speed) { speed = it }
                EffectSlider("الشفافية", opacity) { opacity = it }
                EffectSlider("التوهج", glow) { glow = it }

                Text(
                    "يمكن تعديل هذه القيم هنا. المعالجة على الصور والفيديو ستُربط بمحرك التأثيرات لاحقاً.",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onApply,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EffectGold,
                    contentColor = EffectBlack
                )
            ) {
                Text("تطبيق")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء", color = Color.LightGray)
            }
        }
    )
}

@Composable
private fun EffectSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Text(
        "$label  ${(value * 100).toInt()}%",
        color = EffectWhite,
        fontSize = 12.sp
    )
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = 0f..1f,
        colors = SliderDefaults.colors(
            thumbColor = EffectGold,
            activeTrackColor = EffectGold,
            inactiveTrackColor = Color(0xFF45404D)
        )
    )
}
