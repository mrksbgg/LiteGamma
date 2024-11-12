package ru.zako.litegamma.command;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import org.bukkit.plugin.Plugin;
import ru.zako.litegamma.command.list.ReloadCommand;

import javax.annotation.Nullable;
import java.util.Collection;

public class SubCommands {
    private final ClassToInstanceMap<SubCommand> commands;

    public SubCommands(Plugin plugin) {
        commands = new ImmutableClassToInstanceMap.Builder<SubCommand>()
                .put(ReloadCommand.class, new ReloadCommand(plugin))
                .build();
    }

    @Nullable
    public SubCommand getCommand(Class<? extends SubCommand> clazz) {
        return commands.get(clazz);
    }

    public Collection<SubCommand> getCommands() {
        return commands.values();
    }
}
