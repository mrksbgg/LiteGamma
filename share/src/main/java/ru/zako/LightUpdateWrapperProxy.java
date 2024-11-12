package ru.zako;

import java.util.List;

public interface LightUpdateWrapperProxy {
    void skyLightArrays(List<byte[]> array);
    void blockLightArrays(List<byte[]> array);
    boolean isContainsLightData();
    void setLightLevel(byte level);

    /**
     * Устанавливает значение всех <a href="https://wiki.vg/Protocol#Update_Light:~:text=16%2C%20rounded%20down)-,Sky%20Light%20Mask,-BitSet">Подобных</a>  масок
     */
    void fillMasks();
    void clearEmptyMasks();
}
