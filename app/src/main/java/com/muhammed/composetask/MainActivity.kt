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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
                    QuizPage(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

data class QuizQuestion(val text: String, val answer: Boolean)

    val questionsList = listOf(
        QuizQuestion("Android is an operating system.", true),
        QuizQuestion("Android is made by Apple", false),
        QuizQuestion("Kotlin is made by a Kuwaiti", false),
        QuizQuestion("Kotlin is supported in Intellij", true)
    )

@Composable
fun QuizPage(modifier: Modifier = Modifier) {
    var questionIndex by rememberSaveable { mutableIntStateOf(0) }
    var isAnswered by rememberSaveable { mutableStateOf(false) }
    var isCorrect by rememberSaveable { mutableStateOf(false) }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var gameOver by rememberSaveable { mutableStateOf(false) }

    if (gameOver) {
        GameOverScreen(score, questionsList.size){
            score = 0
            isAnswered = false
            isCorrect = false
            gameOver = false
            questionIndex = 0
        }
        return
    }

    val currentQuestion = questionsList[questionIndex]

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
            Question(question = currentQuestion.text)
            if (!isAnswered) {
                TrueFalseButtons(
                    onTrueClick = {
                        isAnswered = true
                        isCorrect = currentQuestion.answer == true
                        if (isCorrect) score++
                    },
                    onFalseClick = {
                        isAnswered = true
                        isCorrect = currentQuestion.answer == false
                        if (isCorrect) score++
                    }
                )
            } else {
                if (isCorrect) {
                    AnswerCircle(text = stringResource(R.string.correct_answer), color = Color.Green)
                } else {
                    AnswerCircle(text = stringResource(R.string.wrong_answer), color = Color.Red)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (questionIndex < questionsList.lastIndex) {
                    NextQuestionButton(
                        onClick = {
                                questionIndex++
                                isAnswered = false
                                isCorrect = false
                        }
                    )
                }
                else {
                    Button(
                        onClick = {
                            gameOver = true
                        },
                        shape = CircleShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(stringResource(R.string.results), fontSize = 19.sp)
                    }
                }
            }
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
        fontSize = 30.sp,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun TrueFalseButtons(
    onTrueClick: () -> Unit = {},
    onFalseClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onTrueClick ,
            shape = CircleShape,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        ) {
            Text(stringResource(R.string.True), fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = onFalseClick,
            shape = CircleShape,
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        ) {
            Text(stringResource(R.string.False), fontSize = 20.sp)
        }
    }
}

@Composable
fun NextQuestionButton(
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(stringResource(R.string.next_question), fontSize = 18.sp)
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

@Composable
fun GameOverScreen(score: Int, total: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.quiz_complete), fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.score, score, total), fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRestart,
            shape = CircleShape
        ) {
            Text(stringResource(R.string.restart_quiz), fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionTextPreview() {
    ComposeTaskTheme {
        Question("Android is an operating system.")
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