package com.example.contact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale

data class Contact(
    val name: String,
    val surname: String? = null,
    val familyName: String,
    val imageRes: Int? = null,
    val isFavorite: Boolean = false,
    val phone: String,
    val address: String,
    val email: String? = null,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactScreen()
                }
            }
        }
    }
}

@Composable
fun ContactScreen() {
    var showFavorite by remember { mutableStateOf(true) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { showFavorite = true }
            ) {
                Text("Избранный")
            }

            Button(
                onClick = { showFavorite = false }
            ) {
                Text("Не избранный")
            }
        }

        if (showFavorite) {
            ContactDetails(
                contact = Contact(
                    name = "Анна",
                    surname = "Андреевна",
                    familyName = "Иванова",
                    imageRes = null,
                    isFavorite = true,
                    phone = "+7 495 123 45 67",
                    address = "г. Москва, ул. Тверская, д. 1",
                    email = "anna.ivanova@example.com"
                )
            )
        } else {
            ContactDetails(
                contact = Contact(
                    name = "Петр",
                    surname = null,
                    familyName = "Сидоров",
                    imageRes = R.drawable.person_photo,
                    isFavorite = false,
                    phone = "+7 916 789 12 34",
                    address = "г. Санкт-Петербург, Невский пр., д. 25",
                    email = null
                )
            )
        }
    }
}

@Composable
fun ContactDetails(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        if (contact.imageRes != null) {
            Image(
                painter = painterResource(id = contact.imageRes),
                contentDescription = "Фото контакта",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFF9E9E9E), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                val initials = "${contact.name.take(1)}${contact.familyName.take(1)}"
                Text(
                    text = initials,
                    fontSize = 40.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val fullName = buildString {
                append(contact.name)
                if (contact.surname != null) {
                    append(" ${contact.surname}")
                }
                append(" ${contact.familyName}")
            }

            Text(
                text = fullName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            if (contact.isFavorite) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "★",
                    fontSize = 28.sp,
                    color = Color(0xFFFFD700)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        InfoRow(label = "Телефон", value = contact.phone)
        InfoRow(label = "Адрес", value = contact.address)

        if (contact.email != null) {
            InfoRow(label = "E-mail", value = contact.email)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.3f),
            color = Color(0xFF666666)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.7f),
            color = Color.Black
        )
    }
}

@Preview(name = "Избранный", showBackground = true)
@Composable
fun PreviewFavoriteWithoutPhoto() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContactDetails(
                contact = Contact(
                    name = "Анна",
                    surname = "Андреевна",
                    familyName = "Иванова",
                    imageRes = null,
                    isFavorite = true,
                    phone = "+7 495 123 45 67",
                    address = "г. Москва, ул. Тверская, д. 1",
                    email = "anna.ivanova@example.com"
                )
            )
        }
    }
}

@Preview(name = "Не избранный", showBackground = true)
@Composable
fun PreviewNonFavoriteWithPhoto() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContactDetails(
                contact = Contact(
                    name = "Петр",
                    surname = null,
                    familyName = "Сидоров",
                    imageRes = R.drawable.person_photo,
                    isFavorite = false,
                    phone = "+7 916 789 12 34",
                    address = "г. Санкт-Петербург, Невский пр., д. 25",
                    email = null
                )
            )
        }
    }
}