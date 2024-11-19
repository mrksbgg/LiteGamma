package ru.mrksbgg.litegamma.command.list;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import ru.mrksbgg.litegamma.Config;
import ru.mrksbgg.litegamma.command.SubCommand;

import java.util.List;

@RequiredArgsConstructor
public class ReloadCommand implements SubCommand {
    private final Plugin plugin;

    @Getter
    private final List<String> aliases = List.of("reload");

    @Getter
    private final Permission permission = new Permission("litegamma.reload");

    @Override
    public void onCommand(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        plugin.reloadConfig();
        Config.load(plugin.getConfig());

        sender.sendMessage(
                ChatColor.GRAY + "[" + ChatColor.GREEN + "LiteGamma" + ChatColor.GRAY + "]" + ChatColor.WHITE + " Конфиг перезагружен!");
    }
}
