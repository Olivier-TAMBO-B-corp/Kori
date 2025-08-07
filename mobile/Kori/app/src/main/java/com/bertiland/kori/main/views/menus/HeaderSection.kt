package com.bertiland.kori.main.views.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import api.ttt.rahisi.template1.utils.media.ImageWithCoil

import com.bertiland.kori.R


@Composable
fun HeaderSection(
    name: String,
    key: String,
    photo: String?,
    objects: Int,
    reports: Int,
    models: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.primary)) // Couleur bleue
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = Color(0)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (photo!=null) {
                        ImageWithCoil(
                            imagePath = photo,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Black, CircleShape)
                        )
                    } else {
                        Text(
                            text = "- - -",
                            color = Color.White
                        )
                    }
                }
            }

            Text(text = name, color = Color.White, style = MaterialTheme.typography.h6)
            Text(text = key, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(label = "Objets", value = objects)
                StatItem(label = "Reportings", value = reports)
                StatItem(label = "Models", value = models)
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$value", style = MaterialTheme.typography.h6, color = Color.White)
        Text(text = label, color = Color.White)
    }
}