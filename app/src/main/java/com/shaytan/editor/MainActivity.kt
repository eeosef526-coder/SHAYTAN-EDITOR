package com.shaytan.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShaitanEditor()
        }
    }
}

@Composable
fun ShaitanEditor() {
    val background = Color(0xFF080808)
    val card = Color(0xFF191919)
    val gold = Color(0xFFD6AD45)

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "♛",
                color = gold,
                fontSize = 65.sp
            )

            Text(
                text = "مـطـور شـيـطـآن",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "SHAYTAN EDITOR",
                color = gold,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "اصنع فيديوهاتك باحتراف",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(25.dp))

            EditorButton("＋  مشروع جديد", card, gold)

            Spacer(modifier = Modifier.height(15.dp))

            EditorButton("🎬  تعديل فيديو", card, Color.White)

            Spacer(modifier = Modifier.height(15.dp))

            EditorButton("🖼  تعديل صورة", card, Color.White)

            Spacer(modifier = Modifier.height(15.dp))

            EditorButton("📁  مشاريعي", card, Color.White)

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "صُنع بإبداع • SHAYTAN EDITOR",
                color = Color.Gray,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun EditorButton(
    title: String,
    cardColor: Color,
    textColor: Color
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(15.dp),
        color = cardColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = title,
                color = textColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
