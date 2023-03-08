package ru.cft.shift2023winter.presentation.compose

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.cft.shift2023winter.R
import ru.cft.shift2023winter.presentation.LocationUiState
import ru.cft.shift2023winter.presentation.LocationViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LocationScreen(
    onNavigateToWeatherScreen: () -> Unit, viewModel: LocationViewModel = koinViewModel()
) {
    val text by viewModel.inputFlow.collectAsState()
    val state by viewModel.state.observeAsState(initial = LocationUiState.Initial)
    when (state) {
        LocationUiState.Finished -> LaunchedEffect(true) { onNavigateToWeatherScreen() }
        else -> Unit
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(size = 50.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.size(10.dp))
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = stringResource(id = R.string.enter_location),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                BasicTextField(
                    value = text,
                    onValueChange = viewModel::setInput,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        AnimatedContent(targetState = state, transitionSpec = {
            (slideInHorizontally { width -> width } + fadeIn() with slideOutHorizontally { width -> -width } + fadeOut()).using(
                SizeTransform(clip = false)
            )
        }) { state ->
            if (state is LocationUiState.Content) {
                LazyColumn {
                    items(count = state.names.size) {
                        LocationCard(
                            name = state.names[it], index = it, onClick = (viewModel::setLocation)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LocationCard(name: String, index: Int, onClick: (Int) -> Unit) {
    Card(shape = RoundedCornerShape(size = 50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .clickable { onClick(index) }) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = name,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}