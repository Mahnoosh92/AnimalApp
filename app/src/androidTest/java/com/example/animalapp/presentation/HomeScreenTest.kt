package com.example.animalapp.presentation

import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.animalapp.data.FakeServer
import com.example.animalapp.data.api.ApiService
import com.example.animalapp.data.common.UiConstants
import com.example.animalapp.presentation.detail.navigation.DETAIL_ROUTE
import com.example.animalapp.presentation.home.navigation.HOME_ROUTE
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private lateinit var navController: TestNavHostController

    private val fakeServer = FakeServer()
    private lateinit var api: ApiService

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @Before
    fun setUp() {
        fakeServer.start()

        hiltRule.inject()

        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(ApiService::class.java)

        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            val state = rememberAnimalAppState(
                navController = navController,
                coroutineScope = rememberCoroutineScope()
            )
            AnimalAppGraph(state = state)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verify_loadingAndThenItemsAreDisplayed() {
        composeTestRule.onNodeWithTag(UiConstants.UiTags.ProgressIndicator.customName)
            .assertIsDisplayed()

        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(UiConstants.UiTags.ProgressIndicator.customName))

        composeTestRule.onNodeWithTag(UiConstants.UiTags.BreedsLazyList.customName)
            .assertIsDisplayed()

        composeTestRule.onAllNodes(
            hasTestTag(UiConstants.UiTags.BreedsLazyListItem.customName)
        ).onFirst().assertIsDisplayed()
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        if (this::navController.isInitialized) {
            val route = navController.currentDestination?.route
            assertThat(route).isEqualTo(HOME_ROUTE)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun appNavHost_verifyNavigateToDetailsDestination() {
        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(UiConstants.UiTags.ProgressIndicator.customName))

        composeTestRule.onAllNodes(
            hasTestTag(UiConstants.UiTags.BreedsLazyListItem.customName)
        ).onFirst().performClick()

        val route = navController.currentDestination?.route
        assertThat(route).isEqualTo(DETAIL_ROUTE)
    }
}