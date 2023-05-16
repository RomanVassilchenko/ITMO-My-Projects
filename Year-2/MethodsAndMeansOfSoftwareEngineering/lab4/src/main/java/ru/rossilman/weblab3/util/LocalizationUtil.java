package ru.rossilman.weblab3.util;

import lombok.experimental.UtilityClass;

import java.util.ResourceBundle;

/**
 * Created by Rossilman on 01/04/2023
 */
@UtilityClass
public class LocalizationUtil {

    private final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");

    public String getMessage(String name) {
        return RESOURCE_BUNDLE.getString(name);
    }
}
