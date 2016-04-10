package ru.aleien.yapplication.utils;

import java.util.List;

/**
 * Created by aleien on 10.04.16.
 */
public class Utils {
    public static String convertToString(List list, Character separator) {
        if (list == null) return "";

        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            resultString.append(list.get(i).toString());
            if (i < list.size() - 1) {
                resultString.append(separator).append(' ');
            }
        }
        return resultString.toString();
    }


}
