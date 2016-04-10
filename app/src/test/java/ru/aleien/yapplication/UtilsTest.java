package ru.aleien.yapplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import ru.aleien.yapplication.utils.Utils;

import static junit.framework.Assert.assertEquals;

/**
 * Created by aleien on 10.04.16.
 */
@RunWith(org.robolectric.RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class UtilsTest {
    @Test
    public void listToStringNull() {
        assertEquals("", Utils.convertToString(new ArrayList<>(), null));
    }

    @Test
    public void listToStringSeveral() {
        assertEquals("jazz, funk", Utils.convertToString(Arrays.asList("jazz", "funk"), ','));
    }

    @Test
    public void listToStringOne() {
        assertEquals("jazz", Utils.convertToString(Collections.singletonList("jazz"), ','));
    }


}


