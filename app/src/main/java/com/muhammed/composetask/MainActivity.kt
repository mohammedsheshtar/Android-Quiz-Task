package com.muhammed.composetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammed.composetask.ui.theme.ComposeTaskTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Quiz(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

@Composable
fun Quiz(modifier: Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Question(question = "Android is an operating system.")
            TrueFalseButtons()
        }
    }
}

@Composable
fun Question(question: String, modifier: Modifier = Modifier) {
    Text(
        text = question,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun TrueFalseButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { } ,
            shape = CircleShape,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        ) {
            Text("True", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { },
            shape = CircleShape,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        ) {
            Text("False", fontSize = 20.sp)
        }
    }
}

@Composable
fun NextQuestionButton() {
    Button(
        onClick = { },
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text("Next Question", fontSize = 18.sp)
    }
}

@Composable
fun AnswerCircle(text: String, color: Color) {
    Box(
        modifier = Modifier
            .size(180.dp)
            .clip(CircleShape)
            .background(color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionTextPreview() {
    ComposeTaskTheme {
        Question("Is Kotlin used for Android development?")
    }
}

@Preview(showBackground = true)
@Composable
fun CorrectCirclePreview() {
    ComposeTaskTheme {
        AnswerCircle("Correct Answer", Color.Green)
    }
}

@Preview(showBackground = true)
@Composable
fun WrongCirclePreview() {
    ComposeTaskTheme {
        AnswerCircle("Wrong Answer", Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun TrueFalseButtonsPreview() {
    ComposeTaskTheme {
        TrueFalseButtons()
    }
}

@Preview(showBackground = true)
@Composable
fun NextQuestionButtonPreview() {
    ComposeTaskTheme {
        NextQuestionButton()
    }
}