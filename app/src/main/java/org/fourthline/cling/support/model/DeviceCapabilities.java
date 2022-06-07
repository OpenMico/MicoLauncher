package org.fourthline.cling.support.model;

import java.util.Map;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.action.ActionArgumentValue;

/* loaded from: classes5.dex */
public class DeviceCapabilities {
    private StorageMedium[] playMedia;
    private StorageMedium[] recMedia;
    private RecordQualityMode[] recQualityModes;

    public DeviceCapabilities(Map<String, ActionArgumentValue> map) {
        this(StorageMedium.valueOfCommaSeparatedList((String) map.get("PlayMedia").getValue()), StorageMedium.valueOfCommaSeparatedList((String) map.get("RecMedia").getValue()), RecordQualityMode.valueOfCommaSeparatedList((String) map.get("RecQualityModes").getValue()));
    }

    public DeviceCapabilities(StorageMedium[] storageMediumArr) {
        this.recMedia = new StorageMedium[]{StorageMedium.NOT_IMPLEMENTED};
        this.recQualityModes = new RecordQualityMode[]{RecordQualityMode.NOT_IMPLEMENTED};
        this.playMedia = storageMediumArr;
    }

    public DeviceCapabilities(StorageMedium[] storageMediumArr, StorageMedium[] storageMediumArr2, RecordQualityMode[] recordQualityModeArr) {
        this.recMedia = new StorageMedium[]{StorageMedium.NOT_IMPLEMENTED};
        this.recQualityModes = new RecordQualityMode[]{RecordQualityMode.NOT_IMPLEMENTED};
        this.playMedia = storageMediumArr;
        this.recMedia = storageMediumArr2;
        this.recQualityModes = recordQualityModeArr;
    }

    public StorageMedium[] getPlayMedia() {
        return this.playMedia;
    }

    public StorageMedium[] getRecMedia() {
        return this.recMedia;
    }

    public RecordQualityMode[] getRecQualityModes() {
        return this.recQualityModes;
    }

    public String getPlayMediaString() {
        return ModelUtil.toCommaSeparatedList(this.playMedia);
    }

    public String getRecMediaString() {
        return ModelUtil.toCommaSeparatedList(this.recMedia);
    }

    public String getRecQualityModesString() {
        return ModelUtil.toCommaSeparatedList(this.recQualityModes);
    }
}
