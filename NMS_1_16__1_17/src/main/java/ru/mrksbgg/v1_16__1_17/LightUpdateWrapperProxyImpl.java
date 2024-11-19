package ru.mrksbgg.v1_16__1_17;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import ru.mrksbgg.LightUpdateWrapperProxy;

import java.util.Arrays;
import java.util.List;


public class LightUpdateWrapperProxyImpl implements LightUpdateWrapperProxy {
    private PacketContainer container;

    private List<byte[]> skyLight;
    private List<byte[]> blockLight;

    public LightUpdateWrapperProxyImpl(PacketContainer container) {
        this.container = container;
        if (container.getType() != PacketType.Play.Server.LIGHT_UPDATE) {
            throw new IllegalArgumentException("Packet type is not Play.Server.LIGHT_UPDATE");
        }
        final var lists = container.getSpecificModifier(List.class);
        skyLight = lists.read(0);
        blockLight = lists.read(1);
//        System.out.println("Sky: "+ skyLight.size()+", blocks: "+ blockLight.size());
//        System.out.println("Patch "+ container.getIntegers().read(0) +" "+ container.getIntegers().read(1));
    }

    @Override
    public void skyLightArrays(List<byte[]> array) {
        if (skyLight == null) {
            throw new IllegalStateException("skyLight arrays not initialized");
        }

        skyLight.clear();
        skyLight.addAll(array);
    }

    @Override
    public void blockLightArrays(List<byte[]> array) {
        if (skyLight == null) {
            throw new IllegalStateException("blockLight arrays not initialized");
        }

        blockLight.clear();
        blockLight.addAll(array);
    }

    @Override
    public boolean isContainsLightData() {
        return skyLight != null && blockLight != null;
    }

    @Override
    public void setLightLevel(byte level) {
        for (int i = 0; i < 16; i++) {
            if (skyLight.size() > i) {
                final byte[] block = skyLight.get(i);
                Arrays.fill(block, level);
            } else {
                final byte[] bytes = new byte[2048];
                Arrays.fill(bytes, level);
                skyLight.add(bytes);
            }

            if (blockLight.size() > i) {
                final byte[] block = blockLight.get(i);
                Arrays.fill(block, level);
            } else {
                final byte[] bytes = new byte[2048];
                Arrays.fill(bytes, level);
                blockLight.add(bytes);
            }
        }
    }

    @Override
    public void fillMasks() {
        container.getIntegers().write(2, 0xFFFF );
        container.getIntegers().write(3, 0xFFFF );

    }

    @Override
    public void clearEmptyMasks() {
        container.getIntegers().write(4, 0);
        container.getIntegers().write(5, 0);
    }
}
