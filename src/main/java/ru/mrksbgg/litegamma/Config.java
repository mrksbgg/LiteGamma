package ru.mrksbgg.litegamma;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class Config {
    public static void load(FileConfiguration file) {
        WORLDS.clear();
        WORLDS.addAll(file.getStringList("worlds"));
    }

    public static Set<String> WORLDS = new HashSet<>();
}
