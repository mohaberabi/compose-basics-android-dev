package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.ui.navigation.CupcakeScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AppNavTest {


    @get:Rule

    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    private lateinit var navController: TestNavHostController


    @Before

    fun setup() {

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)

        }
    }

    @Test
    fun verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    val backText = "Back"


    @Test
    fun verifyHomeScreenHasNoBackButton() {
        //composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()

    }

    @Test
    fun verifyNavigationBackNotShown() {
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun onCupCakeClickdShouldGoToSelectFalvor() {
        clickOnText("One Cupcake")
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }


    @Test
    fun clickNextOnFlavorsGoToPickup() {
        navigateToFlavors()

        clickOnText("Next")

        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)

    }

    @Test
    fun clickBackFromFlavorsGoesBackAgain() {
        navigateToFlavors()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test

    fun clickCancelOnFalvorsGoesToStartOrder() {
        navigateToFlavors()
        clickOnText("Cancel")
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)

    }


    @Test

    fun clickOnNextOnPickUpScreenGoesToSummary() {
        goToPickupScreen()
        clickOnText(getFormattedDate())
        clickOnText("Next")
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }


    @Test
    fun clickBackOnPickupScreenGoesToFlavors() {
        goToPickupScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    @Test
    fun cancelOnPickGoesToStartOrder() {
        goToPickupScreen()
        clickOnText("Cancel")
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cancelOnSummaryGoesToStartOrder() {
        navigateToSummaryScreen()
        clickOnText("Cancel")
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    private fun clickOnText(text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }

    private fun performNavigateUp() {
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(java.util.Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun goToPickupScreen() {
        navigateToFlavors()
        clickOnText("Next")

    }

    private fun navigateToSummaryScreen() {
        goToPickupScreen()
        clickOnText(getFormattedDate())
        clickOnText("Next")

    }


    private fun navigateToFlavors() {
        clickOnText("One Cupcake")
        clickOnText("Chocolate")

    }
}