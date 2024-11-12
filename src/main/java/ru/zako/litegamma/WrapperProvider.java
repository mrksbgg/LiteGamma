package ru.zako.litegamma;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ru.zako.LightUpdateWrapperProxy;

import java.lang.reflect.Constructor;

@UtilityClass
public class WrapperProvider {
    public static final int PROTOCOL = MinecraftProtocolVersion.getCurrentVersion();

    private static final Class<LightUpdateWrapperProxy> proxy;
    private static final Constructor<LightUpdateWrapperProxy> proxyConstructor;
    public static PacketType[] PROTOCOL_LIGHT_PACKETS;

    static {
        String path = null;
        if (PROTOCOL >= 757 && PROTOCOL <= 766) {
            path = "v1_18__1_21";
            PROTOCOL_LIGHT_PACKETS = new PacketType[] {
                    PacketType.Play.Server.LIGHT_UPDATE,
                    PacketType.Play.Server.MAP_CHUNK
            };
        } else if (PROTOCOL >= 754) {
            path = "v1_16__1_17";
            PROTOCOL_LIGHT_PACKETS = new PacketType[] {
                    PacketType.Play.Server.LIGHT_UPDATE,
            };
        }
        if (path == null) {
            throw new IllegalStateException("Minecraft protocol version not supported: "+ PROTOCOL);
        }

        try {
            proxy = (Class<LightUpdateWrapperProxy>) Class.forName("ru.zako." + path + ".LightUpdateWrapperProxyImpl");
        } catch (Exception e) {
            throw new IllegalStateException("Unsupported Minecraft Protocol Version - " + PROTOCOL, e);
        }

        try {
            proxyConstructor = proxy.getConstructor(PacketContainer.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Not found constructor", e);
        }
    }

    @SneakyThrows
    public static LightUpdateWrapperProxy create(PacketContainer object) {
        return proxyConstructor.newInstance(object);
    }
}
