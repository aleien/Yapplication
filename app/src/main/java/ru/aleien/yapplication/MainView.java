package ru.aleien.yapplication;

import android.support.v4.app.Fragment;

/**
 * Created by aleien on 18.04.16.
 */
public interface MainView {
    void changeFragmentTo(Fragment fragment, boolean hideBackButton);
}
