package ru.zako.litegamma;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import ru.zako.LightUpdateWrapperProxy;

@Getter
public class ProtocolPacketListener extends PacketAdapter {

    private final ProtocolManager protocolManager;

    public ProtocolPacketListener(final Plugin plugin, ProtocolManager protocolManager) {
        super(plugin, ListenerPriority.HIGH, WrapperProvider.PROTOCOL_LIGHT_PACKETS);
        this.plugin = plugin;
        this.protocolManager = protocolManager;
    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {
        if (!Config.WORLDS.contains(packetEvent.getPlayer().getWorld().getName()))
            return;

        final var packet = packetEvent.getPacket();
        final LightUpdateWrapperProxy proxy = WrapperProvider.create(packet);
        if (!proxy.isContainsLightData()) return;
        proxy.fillMasks();
        proxy.clearEmptyMasks();
        proxy.setLightLevel((byte) 0xFF);
    }
}
