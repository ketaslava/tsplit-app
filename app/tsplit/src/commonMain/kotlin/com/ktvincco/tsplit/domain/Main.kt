package com.ktvincco.tsplit.domain

import com.ktvincco.tsplit.AppInfo
import com.ktvincco.tsplit.Configuration
import com.ktvincco.tsplit.data.Database
import com.ktvincco.tsplit.data.EnvironmentConnector
import com.ktvincco.tsplit.data.Logger
import com.ktvincco.tsplit.data.PermissionController
import com.ktvincco.tsplit.presentation.ModelData

class Main (private val modelData: ModelData,
            private val logger: Logger,
            private val permissionController: PermissionController,
            private val database: Database,
            private val environmentConnector: EnvironmentConnector
){

    companion object {
        const val LOG_TAG = "Main"
    }


    // Create components
    private val telemetry = Telemetry(database, environmentConnector)


    fun setup() {

        // Info
        modelData.setAppInfo("Name", AppInfo.NAME)
        modelData.setAppInfo("Version", AppInfo.VERSION)

        // Language
        // null or "" -> follow system
        // lang code -> specific lang
        // non lang code (any string) -> original
        var languageCode = database.loadString("languageCode")
        if ((languageCode == null) or (languageCode == "")) {
            languageCode = environmentConnector.getDefaultLanguageCode()
        }
        modelData.setLanguageCode(languageCode?: "original")

        // Translation
        modelData.assignReportAbsenceOfTranslationCallback{ originalText ->
            logger.logUniqueString(originalText, "to_translation")
        }

        // Language selection callback
        modelData.assignLanguageSelectedCallback { newLanguageCode ->
            database.saveString("languageCode", newLanguageCode)
            modelData.setLanguageCode(newLanguageCode)
        }

        // Callbacks
        modelData.assignOpenAppPermissionSettingsButtonCallback {
            environmentConnector.openAppPermissionSettings()
        }
        modelData.assignRestartAppButtonCallback {
            environmentConnector.restartTheApplication()
        }
        modelData.assignOpenWebLinkButtonCallback { url ->
            environmentConnector.openWebLink(url)
        }

        logger.log("Setup", "AAAAAAA")

        // Next
        requestPermissions()
    }


    private fun requestPermissions() {
        permissionController.requestPermissions { result ->

            // Process permissions request result
            if (result) {
                logger.log("Setup", "BBBBBB")
                logger.log(LOG_TAG, "Permissions granted")
                setup2()
            } else {
                logger.log("Setup", "BBBBB2222")
                logger.logW(LOG_TAG, "WARNING access denied")
                modelData.openAccessDeniedScreen()
                modelData.setIsShowUi(true)
            }
        }
    }


    private fun setup2() {

        // Setup components
        // recorder.setup()

        // Show UI
        modelData.setIsShowUi(true)

        // DEV
        modelData.openDashboardPage()

        logger.log("Setup", "CCCCCC")
        return

        // Get first start status
        val isComplete = database.loadString("IsFirstStartComplete") == "Yes" &&
                !Configuration.getIsAlwaysShowFirstStartScreen() &&
                database.loadString("AcceptedUserAgreementVersion") ==
                    Configuration.getUserAgreementVersion().toString()

        // Open dashboard or FirstStartScreen
        if (isComplete) {

            // Open page
            modelData.openDashboardPage()
            // DEV
            // modelData.openSettingsPage()

            // Setup next
            setup3()

            // Telemetry checkpoint
            telemetry.usageReportByCheckpoint("secondLaunch")
        } else {
            modelData.openFirstStartScreen {
                database.saveString("IsFirstStartComplete", "Yes")
                database.saveString("AcceptedUserAgreementVersion",
                    Configuration.getUserAgreementVersion().toString())

                // Open page
                modelData.openDashboardPage()

                // Setup next
                setup3()
            }
        }
    }


    private fun setup3() {

        // Enable telemetry
        telemetry.enable()

        // Telemetry events
        telemetry.newInstallationLaunchReport()
        telemetry.sixHoursActivityReport()
    }

}
