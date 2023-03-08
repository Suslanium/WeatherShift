package ru.cft.shift2023winter.presentation.compose

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.cft.shift2023winter.R
import ru.cft.shift2023winter.presentation.theme.AppTheme
import kotlin.random.Random

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun IntroScreen() {
    var index by remember { mutableStateOf(Random.nextInt(0, 9)) }
    LaunchedEffect(true) {
        while (true) {
            delay(3000)
            index = Random.nextInt(0, 9)
        }
    }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = RoundedCornerShape(size = 50.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            modifier = Modifier.padding(all = 20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            AnimatedContent(targetState = index, transitionSpec = {
                (slideInHorizontally { width -> width } + fadeIn() with slideOutHorizontally { width -> -width } + fadeOut())
            }) {
                Image(
                    painter = painterResource(
                        id = arrayOf(
                            R.drawable.snow,
                            R.drawable.partly_cloudy_night,
                            R.drawable.foggy,
                            R.drawable.thunderstorm,
                            R.drawable.clear_day,
                            R.drawable.rainy,
                            R.drawable.clear_night,
                            R.drawable.partly_cloudy_day,
                            R.drawable.cloudy
                        )[it]
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(30.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
            }
        }
        Text(
            text = "Welcome!",
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
fun IntroPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            IntroScreen()
        }
    }
}