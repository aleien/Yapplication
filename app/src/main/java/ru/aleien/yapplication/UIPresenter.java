package ru.aleien.yapplication;

import android.support.v4.app.Fragment;

/**
 * Created by aleien on 10.04.16.
 */
public interface UIPresenter {
    void changeFragment(Fragment fragment);

    void popBackStack();
}
