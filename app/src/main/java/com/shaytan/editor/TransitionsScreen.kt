package com.shaytan.editor
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape

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

    var intensity by remember { mutableFloatStateOf(0.8f) }
    var spread by remember { mutableFloatStateOf(25f) }
    var transparency by remember { mutableFloatStateOf(1f) }
    var softness by remember { mutableFloatStateOf(20f) }
    var blinkSpeed by remember { mutableFloatStateOf(1f) }

    var glowEnabled by remember { mutableStateOf(true) }
    var blinkEnabled by remember { mutableStateOf(false) }

    var selectedShape by remember { mutableStateOf("نجمة") }
    var selectedDirection by remember { mutableStateOf("يمين") }
    var selectedEffect by remember { mutableStateOf("Glow") }

    val shapes = listOf(
        "مربع", "دائرة", "مثلث", "سداسي", "نجمة"
    )

    val directions = listOf(
        "أعلى", "أسفل", "يمين", "يسار"
    )

    val background = Color(0xFF080808)
    val card = Color(0xFF191919)
    val gold = Color(0xFFD6AD45)

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
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

            Spacer(Modifier.height(20.dp))

            Text(
                "معاينة الإضاءة",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

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

                Box(
                    modifier = Modifier
                        .size(115.dp)
                        .shadow(
                            elevation = if (glowEnabled) {
                                (spread / 3).dp
                            } else 0.dp,
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
                )
            ) {
                shapes.forEach { shape ->
                    SelectButton(
                        shape,
                        selectedShape == shape
                    ) {
                        selectedShape = shape
                    }
                }
            }

            Spacer(Modifier.height(25.dp))

            SectionTitle("② اتجاه الانتقال", gold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                directions.forEach { direction ->
                    SelectButton(
                        direction,
                        selectedDirection == direction
                    ) {
                        selectedDirection = direction
                    }
                }
            }

            Spacer(Modifier.height(25.dp))

            SectionTitle("③ نوع التأثير", gold)

            Row {
                listOf("Glow", "Blur").forEach { effect ->
                    SelectButton(
                        effect,
                        selectedEffect == effect
                    ) {
                        selectedEffect = effect
                    }
                }
            }

            Spacer(Modifier.height(25.dp))

            SectionTitle("④ التحكم بالإضاءة", gold)

            SettingSwitch(
                "تشغيل الإضاءة الخارجية",
                glowEnabled
            ) {
                glowEnabled = it
            }

            SettingSlider(
                "قوة الإضاءة",
                intensity,
                "100%"
            ) {
                intensity = it
            }

            SettingSlider(
                "انتشار الضوء",
                spread / 100f,
                "${spread.toInt()}"
            ) {
                spread = it * 100f
            }

            Setting
