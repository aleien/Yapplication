package ru.aleien.yapplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

/**
 * Created by aleien on 09.04.16.
 * Тесты!
 * Тестируем логику контроллера (интерактора).
 */
@RunWith(org.robolectric.RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ArtistsInteractorTest {
    private OneAndOnlyActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(OneAndOnlyActivity.class)
                .create()
                .get();
    }

    @Test
    public void testSetup() {
        assertTrue(activity.artistsInteractor != null);
        assertTrue(activity.artistsInteractor.artistsProvider != null);
    }
}
