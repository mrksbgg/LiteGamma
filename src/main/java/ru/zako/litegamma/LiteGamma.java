package ru.zako.litegamma;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.zako.litegamma.command.LiteGammaCommand;

public final class LiteGamma extends JavaPlugin {

    private ProtocolManager protocolManager;


    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Config.load(getConfig());

        protocolManager.addPacketListener(new ProtocolPacketListener(this, protocolManager));

        getCommand("litegamma").setExecutor(new LiteGammaCommand(this));
    }

    @Override
    public void onDisable() {
        protocolManager.removePacketListeners(this);
    }
}
