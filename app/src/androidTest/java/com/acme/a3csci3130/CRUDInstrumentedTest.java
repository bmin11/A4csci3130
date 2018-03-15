package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;


/**
 * For testing CRUD functionality
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CRUDInstrumentedTest {

    private int[] counts = new int[1];
    private int[] compareCounts = new int[1];

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * For testing on creating new business
     * Check the initial length of the listview and the final length of the listview
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {
        String name = "Test Create";
        String number = "123456789";
        String address = "address";
        String province = "AB";

        //Wait for the app to retrieve all data entries from Firebase
        Thread.sleep(2000);

        //Get the initial length of the list view
        onView(withId(R.id.lvList)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                counts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onView(withId(R.id.btSubmit))
                .perform(click());

        intended(hasComponent(CreateBusinessActivity.class.getName()));

        onView(withId(R.id.etName))
                .perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.etNumber))
                .perform(typeText(number), closeSoftKeyboard());
        onView(withId(R.id.etAddress))
                .perform(typeText(address), closeSoftKeyboard());
        onView(withId(R.id.etProvince))
                .perform(typeText(province), closeSoftKeyboard());

        onView(withId(R.id.btSubmit))
                .perform(click());


        //get the new length of the list view
        onView(withId(R.id.lvList)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                compareCounts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));


        //Test if the number of items for the list view has increased by 1 due to new business
        assertEquals(counts[0]+1, compareCounts[0]);
    }

    /**
     * For testing on updating the last business from Test Create to Test Update, which was made during the testCreate()
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        String name = "Test Update";

        //get the length of the list view
        onView(withId(R.id.lvList)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                counts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        //click on the last item, which would be the one created during testCreate()
        onData(anything()).inAdapterView(withId(R.id.lvList)).atPosition(counts[0]-1).perform(click());

        intended(hasComponent(DetailViewActivity.class.getName()));

        onView(withId(R.id.etName))
                .perform(clearText(), typeText(name), closeSoftKeyboard());

        onView(withId(R.id.btUpdate))
                .perform(click());

        intended(hasComponent(MainActivity.class.getName()));
    }

    /**
     * For testing on deleting the last business, which was updated during testUpdate()
     * Check the initial length of the listview and the final length of the listview
     * @throws Exception
     */
    @Test
    public void testzDelete() throws Exception {

        //get the initial length of the list view
        onView(withId(R.id.lvList)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                counts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        //click on the last item, which would be the one created during testCreate()
        onData(anything()).inAdapterView(withId(R.id.lvList)).atPosition(counts[0]-1).perform(click());

        intended(hasComponent(DetailViewActivity.class.getName()));

        onView(withId(R.id.btDelete))
                .perform(click());

        //get the new length of the list view
        onView(withId(R.id.lvList)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                compareCounts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));


        //Test if the number of items for the list view has increased by 1 due to new business
        assertEquals(counts[0]-1, compareCounts[0]);
    }
}
