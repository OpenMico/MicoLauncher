package org.fourthline.cling.support.model;

import java.util.HashMap;
import java.util.Map;
import org.fourthline.cling.model.ModelUtil;

/* loaded from: classes5.dex */
public enum StorageMedium {
    UNKNOWN,
    DV,
    MINI_DV("MINI-DV"),
    VHS,
    W_VHS("W-VHS"),
    S_VHS("S-VHS"),
    D_VHS("D-VHS"),
    VHSC,
    VIDEO8,
    HI8,
    CD_ROM("CD-ROM"),
    CD_DA("CD-DA"),
    CD_R("CD-R"),
    CD_RW("CD-RW"),
    VIDEO_CD("VIDEO-CD"),
    SACD,
    MD_AUDIO("M-AUDIO"),
    MD_PICTURE("MD-PICTURE"),
    DVD_ROM("DVD-ROM"),
    DVD_VIDEO("DVD-VIDEO"),
    DVD_R("DVD-R"),
    DVD_PLUS_RW("DVD+RW"),
    DVD_MINUS_RW("DVD-RW"),
    DVD_RAM("DVD-RAM"),
    DVD_AUDIO("DVD-AUDIO"),
    DAT,
    LD,
    HDD,
    MICRO_MV("MICRO_MV"),
    NETWORK,
    NONE,
    NOT_IMPLEMENTED,
    VENDOR_SPECIFIC;
    
    private static Map<String, StorageMedium> byProtocolString = new HashMap<String, StorageMedium>() { // from class: org.fourthline.cling.support.model.StorageMedium.1
        {
            StorageMedium[] values = StorageMedium.values();
            for (StorageMedium storageMedium : values) {
                put(storageMedium.protocolString, storageMedium);
            }
        }
    };
    private String protocolString;

    StorageMedium() {
        this(null);
    }

    StorageMedium(String str) {
        this.protocolString = str == null ? name() : str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.protocolString;
    }

    public static StorageMedium valueOrExceptionOf(String str) {
        StorageMedium storageMedium = byProtocolString.get(str);
        if (storageMedium != null) {
            return storageMedium;
        }
        throw new IllegalArgumentException("Invalid storage medium string: " + str);
    }

    public static StorageMedium valueOrVendorSpecificOf(String str) {
        StorageMedium storageMedium = byProtocolString.get(str);
        return storageMedium != null ? storageMedium : VENDOR_SPECIFIC;
    }

    public static StorageMedium[] valueOfCommaSeparatedList(String str) {
        String[] fromCommaSeparatedList = ModelUtil.fromCommaSeparatedList(str);
        if (fromCommaSeparatedList == null) {
            return new StorageMedium[0];
        }
        StorageMedium[] storageMediumArr = new StorageMedium[fromCommaSeparatedList.length];
        for (int i = 0; i < fromCommaSeparatedList.length; i++) {
            storageMediumArr[i] = valueOrVendorSpecificOf(fromCommaSeparatedList[i]);
        }
        return storageMediumArr;
    }
}
