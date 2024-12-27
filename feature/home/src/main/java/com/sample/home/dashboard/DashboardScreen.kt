@file:OptIn(ExperimentalMaterial3Api::class)

package com.sample.home.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sample.domain.dto.login.response.LoginResponse
import com.sample.home.R

@Composable
fun DashboardScreen(user: LoginResponse, onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    scrolledContainerColor = Color.Blue
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Image(
                painter = rememberAsyncImagePainter(
                    model = user.image,
                    error = painterResource(id = R.drawable.ic_launcher_background), // Fallback image
                    placeholder = painterResource(id = R.drawable.ic_launcher_background) // Placeholder image
                ),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop // Ensures the image is cropped properly in the circle
            )

            Spacer(modifier = Modifier.height(16.dp))

            // User Details
            Text(
                text = "Name: ${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Username: ${user.username}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Email: ${user.email}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Gender: ${user.gender}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "ID: ${user.id}",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = { onLogout.invoke() },
                modifier = Modifier.padding(50.dp)
            ) {
                Text(text = "Logout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val user = LoginResponse(
        email = "example@email.com",
        firstName = "John",
        gender = "Male",
        id = 123,
        image = "https://via.placeholder.com/150",
        lastName = "Doe",
        username = "johndoe",
        accessToken = "",
        refreshToken = ""
    )
    DashboardScreen(user = user, onLogout = {})
}