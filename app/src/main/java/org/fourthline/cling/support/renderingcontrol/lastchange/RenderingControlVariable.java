package org.fourthline.cling.support.renderingcontrol.lastchange;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.fourthline.cling.model.types.UnsignedIntegerTwoBytes;
import org.fourthline.cling.support.lastchange.EventedValue;
import org.fourthline.cling.support.lastchange.EventedValueShort;
import org.fourthline.cling.support.lastchange.EventedValueString;
import org.fourthline.cling.support.lastchange.EventedValueUnsignedIntegerTwoBytes;

/* loaded from: classes5.dex */
public class RenderingControlVariable {
    public static Set<Class<? extends EventedValue>> ALL = new HashSet<Class<? extends EventedValue>>() { // from class: org.fourthline.cling.support.renderingcontrol.lastchange.RenderingControlVariable.1
        {
            add(PresetNameList.class);
            add(Brightness.class);
            add(Contrast.class);
            add(Sharpness.class);
            add(RedVideoGain.class);
            add(BlueVideoGain.class);
            add(GreenVideoGain.class);
            add(RedVideoBlackLevel.class);
            add(BlueVideoBlackLevel.class);
            add(GreenVideoBlackLevel.class);
            add(ColorTemperature.class);
            add(HorizontalKeystone.class);
            add(VerticalKeystone.class);
            add(Mute.class);
            add(VolumeDB.class);
            add(Volume.class);
            add(Loudness.class);
        }
    };

    /* loaded from: classes5.dex */
    public static class PresetNameList extends EventedValueString {
        public PresetNameList(String str) {
            super(str);
        }

        public PresetNameList(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class Brightness extends EventedValueUnsignedIntegerTwoBytes {
        public Brightness(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public Brightness(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class Contrast extends EventedValueUnsignedIntegerTwoBytes {
        public Contrast(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public Contrast(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class Sharpness extends EventedValueUnsignedIntegerTwoBytes {
        public Sharpness(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public Sharpness(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class RedVideoGain extends EventedValueUnsignedIntegerTwoBytes {
        public RedVideoGain(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public RedVideoGain(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class BlueVideoGain extends EventedValueUnsignedIntegerTwoBytes {
        public BlueVideoGain(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public BlueVideoGain(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class GreenVideoGain extends EventedValueUnsignedIntegerTwoBytes {
        public GreenVideoGain(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public GreenVideoGain(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class RedVideoBlackLevel extends EventedValueUnsignedIntegerTwoBytes {
        public RedVideoBlackLevel(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public RedVideoBlackLevel(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class BlueVideoBlackLevel extends EventedValueUnsignedIntegerTwoBytes {
        public BlueVideoBlackLevel(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public BlueVideoBlackLevel(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class GreenVideoBlackLevel extends EventedValueUnsignedIntegerTwoBytes {
        public GreenVideoBlackLevel(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public GreenVideoBlackLevel(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class ColorTemperature extends EventedValueUnsignedIntegerTwoBytes {
        public ColorTemperature(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
            super(unsignedIntegerTwoBytes);
        }

        public ColorTemperature(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class HorizontalKeystone extends EventedValueShort {
        public HorizontalKeystone(Short sh) {
            super(sh);
        }

        public HorizontalKeystone(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class VerticalKeystone extends EventedValueShort {
        public VerticalKeystone(Short sh) {
            super(sh);
        }

        public VerticalKeystone(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class Mute extends EventedValueChannelMute {
        public Mute(ChannelMute channelMute) {
            super(channelMute);
        }

        public Mute(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class VolumeDB extends EventedValueChannelVolumeDB {
        public VolumeDB(ChannelVolumeDB channelVolumeDB) {
            super(channelVolumeDB);
        }

        public VolumeDB(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class Volume extends EventedValueChannelVolume {
        public Volume(ChannelVolume channelVolume) {
            super(channelVolume);
        }

        public Volume(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class Loudness extends EventedValueChannelLoudness {
        public Loudness(ChannelLoudness channelLoudness) {
            super(channelLoudness);
        }

        public Loudness(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }
}
