package config;

import constants.ConfigConstant;

import java.util.ResourceBundle;

public class ConfigManagerPages {
    private volatile static ConfigManagerPages instance;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(ConfigConstant.PATHPAGES_PROPERTIES_SOURCE);

    public static ConfigManagerPages getInstance() {
        if (instance == null) {
            synchronized (ConfigManagerPages.class) {
                if (instance == null) {
                    instance = new ConfigManagerPages();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
