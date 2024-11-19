package ru.mrksbgg.v1_18__1_21;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedLevelChunkData;
import ru.mrksbgg.AbstractLightUpdateWrapperProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class LightUpdateWrapperProxyImpl extends AbstractLightUpdateWrapperProxy {
    private static final String NOT_CONTAINS_MESSAGE = "Packet not contains Light data";

    private final StructureModifier<WrappedLevelChunkData.LightData> lightDataStructure;

    /**
     * Хорошо бы кэшировать это значение, чтобы не загружать его отдельно для блоков и неба
     */
    private WrappedLevelChunkData.LightData cacheLightData;

    public LightUpdateWrapperProxyImpl(PacketContainer container) {
        this.lightDataStructure = container.getLightUpdateData();
    }

    @Override
    public void skyLightArrays(List<byte[]> array) {
        final var light = getLightDataStructure();
        if (light == null) {
            throw new IllegalStateException(NOT_CONTAINS_MESSAGE);
        }
        final List<byte[]> sky = light.getSkyUpdates();
        sky.clear();
        sky.addAll(array);
    }

    @Override
    public void blockLightArrays(List<byte[]> array) {
        final var light = getLightDataStructure();
        if (light == null) {
            throw new IllegalStateException(NOT_CONTAINS_MESSAGE);
        }
        final List<byte[]> blocks = light.getBlockUpdates();
        blocks.clear();
        blocks.addAll(array);
    }

    @Override
    public boolean isContainsLightData() {
        return lightDataStructure != null && getLightDataStructure() != null;
    }

    @Override
    public void setLightLevel(byte level) {
        final byte[] bytes = new byte[2048];
        Arrays.fill(bytes, level);
        final List<byte[]> updates = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            updates.add(bytes);
        }

        blockLightArrays(updates);
        skyLightArrays(updates);
    }

    @Override
    public void fillMasks() {
        final var light = getLightDataStructure();
        if (light == null) {
            throw new IllegalStateException(NOT_CONTAINS_MESSAGE);
        }
        final BitSet blockMask = new BitSet();
        blockMask.set(0, 18);
        light.setBlockYMask(blockMask);

        final BitSet skyMask = new BitSet();
        skyMask.set(0, 18);
        light.setSkyYMask(skyMask);
    }

    @Override
    public void clearEmptyMasks() {
        final var light = getLightDataStructure();
        if (light == null) {
            throw new IllegalStateException(NOT_CONTAINS_MESSAGE);
        }
        light.setEmptyBlockYMask(new BitSet());
        light.setEmptySkyYMask(new BitSet());
    }

    protected WrappedLevelChunkData.LightData getLightDataStructure() {
        if (cacheLightData != null) return cacheLightData;
        if (lightDataStructure.size() < 1) {
            return null;
        }
        cacheLightData = lightDataStructure.read(0);
        return cacheLightData;
    }
}
