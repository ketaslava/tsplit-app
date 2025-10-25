package com.ktvincco.tsplit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ktvincco.tsplit.ColorPalette
import com.ktvincco.tsplit.DynamicText
import com.ktvincco.tsplit.Texts
import com.ktvincco.tsplit.presentation.ModelData


class HelpMenu (
    private val modelData: ModelData,
) {

    @Composable
    fun draw() {

        // Get parameter description
        val parameterId = modelData.helpMenuParameterId.collectAsState().value
        val parameterDescription = Texts.getParameterDescription(parameterId)

        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(ColorPalette.getBlockColor())
                    .padding(16.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DynamicText(
                    text = parameterDescription.getParameterName(),
                    modelData = modelData,
                    style = MaterialTheme.typography.h6,
                    color = ColorPalette.getTextColor()
                )

                Spacer(Modifier.height(42.dp))

                DynamicText(
                    text = parameterDescription.getDescription(),
                    modelData = modelData,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = ColorPalette.getTextColor()
                )

                Spacer(Modifier.height(42.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement =  Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(ColorPalette.getLightButtonColor())
                            .weight(1F)
                            .clickable { modelData.setHelpMenuState(false) }
                    ) {
                        DynamicText(
                            text = "Ok",
                            modelData = modelData,
                            color = ColorPalette.getTextColor()
                        )
                    }
                }
            }
        }
    }
}