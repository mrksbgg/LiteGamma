package ru.mrksbgg.litegamma.command;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.Collections;
import java.util.List;

public interface SubCommand {
    List<String> getAliases();
    Permission getPermission();
    void onCommand(@NonNull CommandSender sender, @NonNull String[] args);
    default List<String> onTabComplete(@NonNull CommandSender sender, @NonNull String[] args) {
        return Collections.emptyList();
    }
}
