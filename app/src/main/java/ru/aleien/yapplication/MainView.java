package ru.aleien.yapplication;

import android.support.v4.app.Fragment;


interface MainView {
    void changeFragmentTo(Fragment fragment, boolean hideBackButton);
}
