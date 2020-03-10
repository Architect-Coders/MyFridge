package com.pabji.myfridge.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.pabji.myfridge.R
import com.pabji.myfridge.model.network.api.RetrofitApiClient
import com.pabji.myfridge.ui.main.MainActivity
import com.pabji.myfridge.ui.rules.MockWebServerRule
import com.pabji.myfridge.utils.fromJson
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get

class UITest : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.CAMERA"
        )

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(MockResponse().fromJson("search_products.json"))
        mockWebServerRule.server.enqueue(MockResponse().fromJson("product_detail.json"))
        mockWebServerRule.server.enqueue(MockResponse().fromJson("product_detail.json"))
        val resource = OkHttp3IdlingResource.create("OkHttp", get<RetrofitApiClient>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun onSearchProducts() {
        activityTestRule.launchActivity(null)

        onView(
            allOf(
                withId(R.id.action_search),
                isDescendantOfA(withId(R.id.bottom_navigation))
            )
        ).perform(click())

        onView(withId(R.id.fab)).check(matches(not(isDisplayed())))

        onView(withId(R.id.rv_product_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )

        onView(withId(R.id.btn_add)).perform(click())

        onView(isRoot()).perform(pressBack())

        onView(
            allOf(
                withId(R.id.action_fridge),
                isDescendantOfA(withId(R.id.bottom_navigation))
            )
        ).perform(click())

        onView(withId(R.id.fab)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_product_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.fab)).perform(click())
    }
}
