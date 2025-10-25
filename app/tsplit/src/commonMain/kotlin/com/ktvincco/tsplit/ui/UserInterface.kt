package com.ktvincco.tsplit.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ktvincco.tsplit.ColorPalette
import com.ktvincco.tsplit.MainApplicationTheme
import com.ktvincco.tsplit.ui.basics.Popup
import com.ktvincco.tsplit.ui.pages.Dashboard
import com.ktvincco.tsplit.ui.screens.AccessDeniedScreen
import com.ktvincco.tsplit.ui.screens.FirstStartScreen
import com.ktvincco.tsplit.ui.screens.LegalInfoScreen
import com.ktvincco.tsplit.ui.screens.LoadingScreenOverlay
import com.ktvincco.tsplit.presentation.ModelData
import com.ktvincco.tsplit.ui.components.HelpMenu
import com.ktvincco.tsplit.ui.components.MainMenu
import com.ktvincco.tsplit.ui.pages.SettingsPage


class UserInterface (
    private val modelData: ModelData,
) {


    @Composable
    fun draw() {
        // UI refresh
        val refreshKey = modelData.refreshKey.collectAsState().value
        key(refreshKey) {
            MainApplicationTheme {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(ColorPalette.getBackgroundColor())
                ) {
                    // UI switch
                    val isShowUi = modelData.isShowUi.collectAsState().value
                    if (!isShowUi) {
                        print("1111111111111")
                        return@Box
                    }

                    // Front screens

                    if (modelData.legalInfoScreenState.collectAsState().value) {
                        print("22222222222222")
                        LegalInfoScreen(modelData).draw()
                        return@Box
                    }

                    print("333333333333")

                    // Draw pages
                    val currentPage = modelData.currentPage.collectAsState().value

                    when (currentPage) {
                        "FirstStartScreen" -> FirstStartScreen(modelData).draw()
                        "AccessDeniedScreen" -> AccessDeniedScreen(modelData).draw()

                        else -> mainScreen()
                    }

                    // Overlays

                    // Loading screen overlay
                    AnimatedVisibility(
                        modelData.isShowLoadingScreenOverlay.collectAsState().value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        LoadingScreenOverlay(modelData).draw()
                    }

                    // Help Menu overlay
                    AnimatedVisibility(
                        modelData.helpMenuState.collectAsState().value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        HelpMenu(modelData).draw()
                    }
                }
            }
        }
    }


    @Composable
    fun mainScreen() {
        Box (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {

                        // Page
                        val currentPage = modelData.currentPage.collectAsState().value

                        if (currentPage == "Dashboard") {
                            Dashboard(modelData).draw()
                        }
                        if (currentPage == "Settings") {
                            SettingsPage(modelData).draw()
                        }

                        // Main Menu
                        Column(
                            Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                modelData.mainMenuState.collectAsState().value,
                                enter = slideInVertically(initialOffsetY = {it * 2}) + fadeIn(),
                                exit = slideOutVertically(targetOffsetY = {it}) + fadeOut()
                            ) {
                                MainMenu(modelData).draw()
                            }
                        }
                    }
                }
                // Main Menu Bottom Bar
                MainMenu(modelData).openMainMenuButton()
            }

            Popup(modelData).Popup()
            Popup(modelData).PopupWithTextInput()
        }
    }
}
