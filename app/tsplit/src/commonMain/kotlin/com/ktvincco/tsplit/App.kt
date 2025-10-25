package com.ktvincco.tsplit

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.ktvincco.tsplit.data.Database
import com.ktvincco.tsplit.data.EnvironmentConnector
import com.ktvincco.tsplit.data.Logger
import com.ktvincco.tsplit.data.PermissionController
import com.ktvincco.tsplit.domain.Main
import com.ktvincco.tsplit.presentation.ModelData
import com.ktvincco.tsplit.ui.UserInterface
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(
    logger: Logger,
    permissionController: PermissionController,
    database: Database,
    environmentConnector: EnvironmentConnector
) {

    // Create components
    val modelData = ModelData()
    val domainMain = Main(modelData, logger, permissionController, database, environmentConnector)
    val userInterface = UserInterface(modelData)

    // Run actions
    domainMain.setup()
    userInterface.draw()
}


expect fun epochMillis(): Long


@Composable
expect fun getScreenSizeInDp(): Pair<Dp, Dp>
@Composable
expect fun getScreenSizeInPx(): Pair<Int, Int>