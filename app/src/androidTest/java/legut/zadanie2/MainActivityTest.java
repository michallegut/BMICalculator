package legut.zadanie2;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void author() {
        Intents.init();
        onView(withId(R.id.itemMenu)).perform(click());
        onView(withText(R.string.author)).perform(click());
        intended(hasComponent(AuthorActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void toastSave() {
        SystemClock.sleep(1000);
        onView(withId(R.id.itemMenu)).perform(click());
        onView(withText(R.string.save)).perform(click());
        onView(withText(R.string.data_saved)).inRoot(withDecorView(not(mainActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void zShare() {
        Intents.init();
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(100f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.itemMenu)).perform(click());
        onView(withText(R.string.share)).perform(click());
        intended(allOf(hasAction(Intent.ACTION_CHOOSER),hasExtra(is(Intent.EXTRA_INTENT),allOf(hasAction(Intent.ACTION_SEND),hasExtra(Intent.EXTRA_TEXT, String.format("%s%n%.2f%n%s%n%s", mainActivityTestRule.getActivity().getResources().getString(R.string.share_text1), 25f, mainActivityTestRule.getActivity().getResources().getString(R.string.share_text2), mainActivityTestRule.getActivity().getResources().getString(R.string.healthy_weight)))))));
        Intents.release();
    }

    @Test
    public void toastShare() {
        SystemClock.sleep(1000);
        onView(withId(R.id.itemMenu)).perform(click());
        onView(withText(R.string.share)).perform(click());
        onView(withText(R.string.calculate_first)).inRoot(withDecorView(not(mainActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void changingModes() {
        onView(withId(R.id.buttonKgM)).perform(click());
        onView(withId(R.id.textActiveMode)).check(matches(withText(R.string.kg_m)));
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.textActiveMode)).check(matches(withText(R.string.lb_in)));
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.textActiveMode)).check(matches(withText(R.string.lb_in)));
        onView(withId(R.id.buttonKgM)).perform(click());
        onView(withId(R.id.textActiveMode)).check(matches(withText(R.string.kg_m)));
    }

    @Test
    public void toastCalculateKgM() {
        SystemClock.sleep(1000);
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(0f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(0f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withText(R.string.wrong_input)).inRoot(withDecorView(not(mainActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void toastDoubleClickKgM() {
        SystemClock.sleep(1000);
        onView(withId(R.id.buttonKgM)).perform(click());
        onView(withText(R.string.already_set)).inRoot(withDecorView(not(mainActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void toastDoubleClickLbIn() {
        SystemClock.sleep(1000);
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withText(R.string.already_set)).inRoot(withDecorView(not(mainActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void toastCalculateLbIn() {
        SystemClock.sleep(1000);
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(0f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(0f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withText(R.string.wrong_input)).inRoot(withDecorView(not(mainActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void verySeverelyUnderweightKgM() {
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(60f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("15,00")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.very_severely_underweight))));
    }

    @Test
    public void severelyUnderweightKgM() {
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(64f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("16,00")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.severely_underweight))));
    }

    @Test
    public void underweightKgM() {
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(72f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("18,00")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.underweight))));
    }

    @Test
    public void healthyWeightKgM() {
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(100f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("25,00")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.healthy_weight))));
    }

    @Test
    public void overweightKgM() {
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(120f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("30,00")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.overweight))));
    }

    @Test
    public void moderatelyObeseKgM() {
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(140f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("35,00")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.moderately_obese))));
    }

    @Test
    public void severelyObeseKgM() {
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(160f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("40,00")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.severely_obese))));
    }

    @Test
    public void verySeverelyObeseKgM() {
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(200f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(2f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("50,00")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.very_severely_obese))));
    }

    @Test
    public void verySeverelyUnderweightLbIn() {
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(130f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(80f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("14,28")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.very_severely_underweight))));
    }

    @Test
    public void severelyUnderweightLbIn() {
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(140f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(80f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("15,38")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.severely_underweight))));
    }

    @Test
    public void underweightLbIn() {
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(160f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(80f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("17,58")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.underweight))));
    }

    @Test
    public void healthyWeightLbIn() {
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(220f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(80f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("24,17")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.healthy_weight))));
    }

    @Test
    public void overweightLbIn() {
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(270f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(80f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("29,66")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.overweight))));
    }

    @Test
    public void moderatelyObeseLbIn() {
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(310f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(80f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("34,05")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.moderately_obese))));
    }

    @Test
    public void severelyObeseLbIn() {
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(360f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(80f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("39,54")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.severely_obese))));
    }

    @Test
    public void verySeverelyObeseLbIn() {
        onView(withId(R.id.buttonLbIn)).perform(click());
        onView(withId(R.id.editMass)).perform(typeText(Float.toString(370f)));
        onView(withId(R.id.editHeight)).perform(typeText(Float.toString(80f)));
        onView(withId(R.id.buttonCalculate)).perform(click());
        onView(withId(R.id.textBMICalculated)).check(matches(withText("40,64")));
        onView(withId(R.id.textCategory)).check(matches((withText(R.string.very_severely_obese))));
    }
}