package com.example.retrofit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofit.core.api.NetworkResponse
import com.example.retrofit.core.models.weather.current.WeatherCurrentResponse
import com.example.retrofit.core.viewmodels.WeatherViewModel


@Composable
fun WeatherPage(viewModel: WeatherViewModel) {
    val weatherResult by viewModel.weatherResult.collectAsState()

    var city by remember { mutableStateOf("") }
    var shouldLoad by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = city,
                onValueChange = { city = it },
                label = { Text("Search for any Location") }
            )
            IconButton(onClick = {
                shouldLoad = true
                viewModel.getData(city)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search for any Location"
                )
            }
        }

       if(shouldLoad){
           when (weatherResult) {
               is NetworkResponse.Error -> {
                   Text(text = "Error: ${(weatherResult as NetworkResponse.Error).message}")
               }

               is NetworkResponse.Loading -> {
                   CircularProgressIndicator()
               }

               is NetworkResponse.Success -> {
                   WeatherDetails((weatherResult as NetworkResponse.Success<WeatherCurrentResponse>).data)
               }
           }
       }

    }
}


@Composable
fun WeatherDetails(data: WeatherCurrentResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = " Location icon",
                modifier = Modifier.size(40.dp)
            )
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = data.location.country, fontSize = 18.sp, color = Gray)

        }

    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "${data.current.temp_c} ° c", fontSize = 56.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

}