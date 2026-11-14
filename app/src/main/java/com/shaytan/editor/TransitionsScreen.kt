package com.shaytan.editor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun TransitionsScreen(onBack: () -> Unit = {}) {

    val colors = listOf(
        Color.White,
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Magenta,
        Color.Yellow,
        Color.Cyan,
        Color(0xFFFF69B4),
        Color(0xFFFF9800),
        Color(0xFF9C27B0),
        Color(0xFF00BCD4),
        Color(0xFFFFD700)
    )

    var selectedColor by remember { mutableStateOf(Color.White) }
    var secondColor by remember { mutableStateOf(Color.Blue) }
    var useSecondColor by remember { mutableStateOf(false) }

    var intensity by remember { mutableStateOf(0.8f) }
    var spread by remember { mutableStateOf(25f) }
    var transparency by remember { mutableStateOf(1f) }
    var softness by remember { mutableStateOf(20f) }

    var glowEnabled by remember { mutableStateOf(true) }
    var blinkEnabled by remember { mutableStateOf(false) }

    var selectedShape by remember { mutableStateOf("نجمة") }
    var selectedDirection by remember { mutableStateOf("يمين") }
    var selectedEffect by remember { mutableStateOf("Glow") }

    val shapes = listOf("مربع", "دائرة", "مثلث", "سداسي", "نجمة")
    val directions = listOf("أعلى", "أسفل", "يمين", "يسار")

    val background = Color(0xFF080808)
    val gold = Color(0xFFD6AD45)

    val previewColor = if (useSecondColor) {
        Color(
            red = (selectedColor.red + secondColor.red) / 2,
            green = (selectedColor.green + secondColor.green) / 2,
            blue = (selectedColor.blue + secondColor.blue) / 2
        )
    } else selectedColor

    val previewShape: Shape = when (selectedShape) {
        "دائرة" -> CircleShape
        "مربع" -> RoundedCornerShape(5.dp)
        "مثلث" -> RoundedCornerShape(5.dp)
        "سداسي" -> RoundedCornerShape(18.dp)
        else -> RoundedCornerShape(12.dp)
    }

    CompositionLocalProvider(
        androidx.compose.ui.platform.LocalLayoutDirection provides
            androidx.compose.ui.unit.LayoutDirection.Rtl
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "انتقالات SHAYTAN",
                    color = gold,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                TextButton(onClick = onBack) {
                    Text("رجوع", color = Color.White)
                }
            }

            Spacer(Modifier.height(22.dp))

            SectionTitle("معاينة الإضاءة", gold)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(
                        Color(0xFF222222),
                        RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(115.dp)
                        .shadow(
                            elevation = if (glowEnabled) spread.dp else 0.dp,
                            shape = previewShape,
                            ambientColor = previewColor.copy(
                                alpha = intensity * transparency
                            ),
                            spotColor = previewColor.copy(
                                alpha = intensity * transparency
                            )
                        )
                        .border(
                            width = 3.dp,
                            color = previewColor.copy(
                                alpha = intensity * transparency
                            ),
                            shape = previewShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        selectedShape,
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }

            Spacer(Modifier.height(25.dp))

            SectionTitle("① اختيار شكل الانتقال", gold)

            Row(
                modifier = Modifier.horizontalScroll(
                    rememberScrollState()
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                shapes.forEach { shape ->
                    SelectButton(
                        title = shape,
                        selected = selectedShape == shape
                    ) {
                        selectedShape = shape
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            SectionTitle("② اتجاه الانتقال", gold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                directions.forEach { direction ->
                    SelectButton(
                        title = direction,
                        selected = selectedDirection == direction
                    ) {
                        selectedDirection = direction
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            SectionTitle("③ نوع التأثير", gold)

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("Glow", "Blur").forEach { effect ->
                    SelectButton(
                        title = effect,
                        selected = selectedEffect == effect
                    ) {
                        selectedEffect = effect
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            SectionTitle("④ اختيار لون الإضاءة", gold)

            Text("اللون الأول", color = Color.LightGray)

            ColorPicker(colors, selectedColor) {
                selectedColor = it
            }

            SettingSwitch(
                "استخدام لون ثانٍ",
                useSecondColor
            ) {
                useSecondColor = it
            }

            if (useSecondColor) {
                Text("اللون الثاني", color = Color.LightGray)
                ColorPicker(colors, secondColor) {
                    secondColor = it
                }
            }

            Spacer(Modifier.height(20.dp))

            SectionTitle("⑤ التحكم بالإضاءة", gold)

            SettingSwitch(
                "تشغيل الإضاءة الخارجية",
                glowEnabled
            ) {
                glowEnabled = it
            }

            SettingSlider(
                "قوة الإضاءة",
                intensity,
                0f,
                1f
            ) {
                intensity = it
            }

            SettingSlider(
                "انتشار الضوء",
                spread,
                0f,
                60f
            ) {
                spread = it
            }

            SettingSlider(
                "شفافية الضوء",
                transparency,
                0f,
                1f
            ) {
                transparency = it
            }

            SettingSlider(
                "نعومة الضوء",
                softness,
                0f,
                100f
            ) {
                softness = it
            }

            SettingSwitch(
                "تأثير الوميض",
                blinkEnabled
            ) {
                blinkEnabled = it
            }

            Spacer(Modifier.height(25.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF191919),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "إعدادات الانتقال",
                        color = gold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "الشكل: $selectedShape",
                        color = Color.White
                    )

                    Text(
                        "الاتجاه: $selectedDirection",
                        color = Color.White
                    )

                    Text(
                        "التأثير: $selectedEffect",
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
fun SectionTitle(
    title: String,
    color: Color
) {
    Text(
        text = title,
        color = color,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun SelectButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = if (selected) Color(0xFFD6AD45)
        else Color(0xFF222222),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            if (selected) Color.White else Color.DarkGray
        )
    ) {
        Text(
            text = title,
            color = if (selected) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = 15.dp,
                vertical = 12.dp
            )
        )
    }
}

@Composable
fun SettingSwitch(
    title: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = checked,
            onCheckedChange = onChange
        )
    }
}

@Composable
fun SettingSlider(
    title: String,
    value: Float,
    min: Float,
    max: Float,
    onChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            "$title: ${value.toInt()}",
            color = Color.White
        )

        Slider(
            value = value,
            onValueChange = onChange,
            valueRange = min..max
        )
    }
}

@Composable
fun ColorPicker(
    colors: List<Color>,
    selected: Color,
    onSelect: (Color) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = if (selected == color) 3.dp else 1.dp,
                        color = if (selected == color)
                            Color.White else Color.Gray,
                        shape = CircleShape
                    )
                    .clickable {
                        onSelect(color)
                    }
            )
        }
    }
}
