package ru.cft.shift2023winter.presentation.compose

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.cft.shift2023winter.R
import ru.cft.shift2023winter.presentation.WeatherUiState
import ru.cft.shift2023winter.presentation.WeatherViewModel
import ru.cft.shift2023winter.presentation.adapter.FormattedForecast
import ru.cft.shift2023winter.presentation.adapter.FormattedWeather
import ru.cft.shift2023winter.presentation.adapter.WeatherParameter

@Composable
fun WeatherScreen(
    onNavigateToLocationScreen: () -> Unit,
    onNavigateToLocationScreenNoPop: () -> Unit,
    viewModel: WeatherViewModel = koinViewModel()
) {
    val state by viewModel.state.observeAsState(initial = WeatherUiState.Initial)

    LaunchedEffect(true) {
        viewModel.loadData()
    }
    Crossfade(targetState = state) { state ->
        when (state) {
            is WeatherUiState.Content -> WeatherContent(
                locationName = state.locationName,
                currentWeather = state.currentWeather,
                forecast = state.forecast,
                viewModel::updateData,
                onNavigateToLocationScreenNoPop
            )
            is WeatherUiState.Error -> if (state.message == "Current location not set") LaunchedEffect(
                true
            ) { onNavigateToLocationScreen() } else WeatherError(
                msg = state.message, viewModel::updateData
            )
            WeatherUiState.Initial -> Unit
            WeatherUiState.Loading -> WeatherLoading()
        }
    }
}

@Composable
fun WeatherLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(modifier = Modifier.size(150.dp))
    }
}

@Composable
fun WeatherError(msg: String, onUpdateClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(size = 50.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.refresh),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onUpdateClick() })
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = msg,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun WeatherContent(
    locationName: String,
    currentWeather: FormattedWeather,
    forecast: List<FormattedForecast>,
    onUpdateClick: () -> Unit,
    onNavigateToLocationScreen: () -> Unit
) {
    LazyColumn {
        item {
            ScreenHeader(locationName, onNavigateToLocationScreen)
            WeatherCard(
                currentWeather.id,
                currentWeather.description,
                currentWeather.temperature,
                currentWeather.parameters
            )
            ForecastHeader()
        }
        items(count = forecast.size) { index ->
            ForecastItem(
                dayOfWeek = forecast[index].dayOfWeek,
                dayNightAverageTemperature = forecast[index].dayNightAverageTemperature,
                hourlyItems = forecast[index].hourlyItems
            )
        }
        item {
            LastUpdated(currentWeather.time, onUpdateClick)
        }
    }
}

@Composable
fun ScreenHeader(locationName: String, onNavigateToLocationScreen: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .height(30.dp)
                .weight(0.5f)
        ) {
            Image(painter = painterResource(id = R.drawable.navigation),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onNavigateToLocationScreen() })
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(30.dp)
                .weight(3f)
        ) {
            Text(
                text = locationName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .height(30.dp)
                .weight(0.5f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
        }
    }
}

@Composable
fun LastUpdated(timeOfUpdate: String, onUpdateClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .height(30.dp)
                .weight(0.5f)
        ) {
            Image(painter = painterResource(id = R.drawable.refresh),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onUpdateClick() })
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(30.dp)
                .weight(3f)
        ) {
            Text(
                text = String.format(stringResource(id = R.string.updated),timeOfUpdate),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .height(30.dp)
                .weight(0.5f)
        ) {
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}

@Composable
fun ForecastHeader() {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(30.dp)
                .weight(3f)
        ) {
            Text(
                text = stringResource(id = R.string.forecast),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun WeatherCard(
    @DrawableRes id: Int,
    description: String,
    temperature: String,
    parameters: List<WeatherParameter>
) {
    Card(
        shape = RoundedCornerShape(size = 50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        var expanded by remember { mutableStateOf(false) }
        Column(Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() }, indication = null
        ) { expanded = !expanded }) {
            Row(
                modifier = Modifier
                    .padding(all = 20.dp)
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Image(
                    painter = painterResource(id = id),
                    contentDescription = "",
                    modifier = Modifier.size(150.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = temperature,
                        fontSize = 45.sp,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = description,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    for (parameter in parameters) {
                        ru.cft.shift2023winter.presentation.compose.WeatherParameter(
                            name = parameter.name, id = parameter.id, value = parameter.value
                        )
                    }
                    ExpandButton(expand = !expanded)
                }
            }
            AnimatedVisibility(visible = !expanded) {
                ExpandButton(expand = !expanded)
            }
        }
    }
}

@Composable
fun ForecastCard(
    @DrawableRes id: Int,
    description: String,
    temperature: String,
    time: String,
    parameters: List<WeatherParameter>
) {
    Card(
        shape = RoundedCornerShape(size = 50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        var expanded by remember { mutableStateOf(false) }
        Column(Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() }, indication = null
        ) { expanded = !expanded }) {
            Row(
                modifier = Modifier
                    .padding(all = 20.dp)
                    .fillMaxWidth()
                    .height(85.dp)
            ) {
                Image(
                    painter = painterResource(id = id),
                    contentDescription = "",
                    modifier = Modifier.size(85.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(85.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = temperature,
                        fontSize = 30.sp,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = description,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = time,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    for (parameter in parameters) {
                        ru.cft.shift2023winter.presentation.compose.WeatherParameter(
                            name = parameter.name, id = parameter.id, value = parameter.value
                        )
                    }
                    ExpandButton(expand = !expanded)
                }
            }
            AnimatedVisibility(visible = !expanded) {
                ExpandButton(expand = !expanded)
            }
        }
    }
}

@Composable
fun ForecastItem(
    dayOfWeek: String, dayNightAverageTemperature: String, hourlyItems: List<FormattedWeather>
) {
    var expanded by remember { mutableStateOf(false) }
    Column(Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() }, indication = null
    ) { expanded = !expanded }) {
        Card(
            shape = RoundedCornerShape(size = 50.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            if (expanded) {
                ru.cft.shift2023winter.presentation.compose.WeatherParameter(
                    name = dayOfWeek,
                    id = R.drawable.expand_less,
                    value = dayNightAverageTemperature
                )
            } else {
                ru.cft.shift2023winter.presentation.compose.WeatherParameter(
                    name = dayOfWeek, id = R.drawable.expand, value = dayNightAverageTemperature
                )
            }
        }
        AnimatedVisibility(visible = expanded) {
            Column {
                for (formattedWeather in hourlyItems) {
                    ForecastCard(
                        id = formattedWeather.id,
                        description = formattedWeather.description,
                        temperature = formattedWeather.temperature,
                        time = formattedWeather.time,
                        parameters = formattedWeather.parameters
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandButton(expand: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 10.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = if (expand) R.drawable.expand else R.drawable.expand_less),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun WeatherParameter(name: String, @DrawableRes id: Int, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            Image(
                painter = painterResource(id = id),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier.height(30.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .height(30.dp)
                .weight(1f)
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                maxLines = 1,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }

    }
}