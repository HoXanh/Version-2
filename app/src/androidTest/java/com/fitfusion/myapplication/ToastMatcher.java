package com.fitfusion.myapplication;

import org.hamcrest.Matcher;

import android.view.WindowManager;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import android.view.View;
import org.hamcrest.Matcher;
import android.os.IBinder;
import androidx.test.espresso.Root;

public class ToastMatcher extends TypeSafeMatcher<Root> {

    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }

    @Override
    public boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams().get().type;
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            // Window token and app token will be the same in the case of a Toast
            if (windowToken == appToken) {
                // We have a Toast object!
                return true;
            }
        }
        return false;
    }

    /**
     * This is a shortcut method for creating an instance of the ToastMatcher.
     */
    public static Matcher<Root> isToast() {
        return new ToastMatcher();
    }
}
