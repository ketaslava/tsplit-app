package com.ktvincco.tsplit

import androidx.compose.ui.graphics.Color


class Configuration {

    companion object {

        // Debug

        fun getIsAlwaysShowFirstStartScreen(): Boolean {
            return false // false
        }

        fun getIsEnableAbsentTranslationLogging(): Boolean {
            return false // false
        }

        fun getIsEnableTelemetryLogs(): Boolean {
            return true
        }

        // Audio processing

        fun getSampleRate(): Int {
            return 48000 // 48000
        }

        fun getAudioBufferSize(): Int {
            return 4096 // 4096
        }

        fun getProcessingSampleDurationSec(): Float {
            return 1 / 16F // 1/16 Sec
        }

        fun getProcessingSampleLength(): Int {
            return (getSampleRate().toFloat() * getProcessingSampleDurationSec()).toInt()
        }

        fun getAutoScrollXWindowSize(): Float {
            return 4F // Use as seconds
        }

        // Telemetry

        fun getIsEnableTelemetry(): Boolean {
            return true
        }

        fun getTelemetryApiEndpointUrl(): String {
            return "https://ktvincco.com/services/universal_telemetry/api.php"
        }

        // Legal info

        fun getTermsOfUseWebLink(): String {
            return "https://ktvincco.com/tsplit/termsofuse/"
        }

        fun getPrivacyPolicyWebLink(): String {
            return "https://ktvincco.com/tsplit/privacypolicy/"
        }

        fun getUserAgreementVersion(): Int {
            return 2 // If version will change, new user agreement screen will be shown
        }

        fun getSourceCodeWebLink(): String {
            return "https://github.com/ketaslava/open_audio_tools"
        }

        fun getLicenseWebLink(): String {
            return "https://www.gnu.org/licenses/gpl-3.0.en.html"
        }
    }
}


class ColorPalette {

    companion object {

        // Base

        fun getBackgroundColor(): Color {
            return Color.hsv(0F, 0F, 0.08F)
        }

        fun getBlockColor(): Color {
            return Color.hsv(0F, 0F, 0.2F)
        }

        fun getBlockHighlightColor(): Color {
            return Color.hsv(0F, 0F, 0.42F)
        }

        // Components

        fun getButtonColor(): Color {
            return Color.hsv(0F, 0F, 0.16F)
        }

        fun getLightButtonColor(): Color {
            return Color.hsv(0F, 0F, 0.3F)
        }

        fun getSelectionColor(): Color {
            return Color.hsv(0F, 0F, 0.5F).copy(alpha = 0.5f)
        }

        fun getMarkupColor(): Color {
            return Color.hsv(0F, 0F, 0.75F)
        }

        fun getTextColor(): Color {
            return Color.White
        }

        // Soft colors

        fun getSoftRedColor(): Color {
            return Color.hsv(0F, 0.75F, 0.75F)
        }

        fun getSoftYellowColor(): Color {
            return Color.hsv(60F, 0.75F, 0.75F)
        }

        fun getSoftGreenColor(): Color {
            return Color.hsv(120F, 0.5F, 0.75F)
        }

        fun getSoftCyanColor(): Color {
            return Color.hsv(180F, 0.75F, 0.75F)
        }

        fun getSoftBlueColor(): Color {
            return Color.hsv(240F, 0.75F, 0.75F)
        }

        fun getSoftMagentaColor(): Color {
            return Color.hsv(300F, 0.75F, 0.75F)
        }

        fun getSoftGrayColor(): Color {
            return Color.hsv(0F, 0F, 0.75F)
        }
    }
}


