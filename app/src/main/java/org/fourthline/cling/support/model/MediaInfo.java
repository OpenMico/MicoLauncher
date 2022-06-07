package org.fourthline.cling.support.model;

import java.util.Map;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

/* loaded from: classes5.dex */
public class MediaInfo {
    private String currentURI;
    private String currentURIMetaData;
    private String mediaDuration;
    private String nextURI;
    private String nextURIMetaData;
    private UnsignedIntegerFourBytes numberOfTracks;
    private StorageMedium playMedium;
    private StorageMedium recordMedium;
    private RecordMediumWriteStatus writeStatus;

    public MediaInfo() {
        this.currentURI = "";
        this.currentURIMetaData = "";
        this.nextURI = "NOT_IMPLEMENTED";
        this.nextURIMetaData = "NOT_IMPLEMENTED";
        this.numberOfTracks = new UnsignedIntegerFourBytes(0L);
        this.mediaDuration = "00:00:00";
        this.playMedium = StorageMedium.NONE;
        this.recordMedium = StorageMedium.NOT_IMPLEMENTED;
        this.writeStatus = RecordMediumWriteStatus.NOT_IMPLEMENTED;
    }

    public MediaInfo(Map<String, ActionArgumentValue> map) {
        this((String) map.get("CurrentURI").getValue(), (String) map.get("CurrentURIMetaData").getValue(), (String) map.get("NextURI").getValue(), (String) map.get("NextURIMetaData").getValue(), (UnsignedIntegerFourBytes) map.get("NrTracks").getValue(), (String) map.get("MediaDuration").getValue(), StorageMedium.valueOrVendorSpecificOf((String) map.get("PlayMedium").getValue()), StorageMedium.valueOrVendorSpecificOf((String) map.get("RecordMedium").getValue()), RecordMediumWriteStatus.valueOrUnknownOf((String) map.get("WriteStatus").getValue()));
    }

    public MediaInfo(String str, String str2) {
        this.currentURI = "";
        this.currentURIMetaData = "";
        this.nextURI = "NOT_IMPLEMENTED";
        this.nextURIMetaData = "NOT_IMPLEMENTED";
        this.numberOfTracks = new UnsignedIntegerFourBytes(0L);
        this.mediaDuration = "00:00:00";
        this.playMedium = StorageMedium.NONE;
        this.recordMedium = StorageMedium.NOT_IMPLEMENTED;
        this.writeStatus = RecordMediumWriteStatus.NOT_IMPLEMENTED;
        this.currentURI = str;
        this.currentURIMetaData = str2;
    }

    public MediaInfo(String str, String str2, UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str3, StorageMedium storageMedium) {
        this.currentURI = "";
        this.currentURIMetaData = "";
        this.nextURI = "NOT_IMPLEMENTED";
        this.nextURIMetaData = "NOT_IMPLEMENTED";
        this.numberOfTracks = new UnsignedIntegerFourBytes(0L);
        this.mediaDuration = "00:00:00";
        this.playMedium = StorageMedium.NONE;
        this.recordMedium = StorageMedium.NOT_IMPLEMENTED;
        this.writeStatus = RecordMediumWriteStatus.NOT_IMPLEMENTED;
        this.currentURI = str;
        this.currentURIMetaData = str2;
        this.numberOfTracks = unsignedIntegerFourBytes;
        this.mediaDuration = str3;
        this.playMedium = storageMedium;
    }

    public MediaInfo(String str, String str2, UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str3, StorageMedium storageMedium, StorageMedium storageMedium2, RecordMediumWriteStatus recordMediumWriteStatus) {
        this.currentURI = "";
        this.currentURIMetaData = "";
        this.nextURI = "NOT_IMPLEMENTED";
        this.nextURIMetaData = "NOT_IMPLEMENTED";
        this.numberOfTracks = new UnsignedIntegerFourBytes(0L);
        this.mediaDuration = "00:00:00";
        this.playMedium = StorageMedium.NONE;
        this.recordMedium = StorageMedium.NOT_IMPLEMENTED;
        this.writeStatus = RecordMediumWriteStatus.NOT_IMPLEMENTED;
        this.currentURI = str;
        this.currentURIMetaData = str2;
        this.numberOfTracks = unsignedIntegerFourBytes;
        this.mediaDuration = str3;
        this.playMedium = storageMedium;
        this.recordMedium = storageMedium2;
        this.writeStatus = recordMediumWriteStatus;
    }

    public MediaInfo(String str, String str2, String str3, String str4, UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str5, StorageMedium storageMedium) {
        this.currentURI = "";
        this.currentURIMetaData = "";
        this.nextURI = "NOT_IMPLEMENTED";
        this.nextURIMetaData = "NOT_IMPLEMENTED";
        this.numberOfTracks = new UnsignedIntegerFourBytes(0L);
        this.mediaDuration = "00:00:00";
        this.playMedium = StorageMedium.NONE;
        this.recordMedium = StorageMedium.NOT_IMPLEMENTED;
        this.writeStatus = RecordMediumWriteStatus.NOT_IMPLEMENTED;
        this.currentURI = str;
        this.currentURIMetaData = str2;
        this.nextURI = str3;
        this.nextURIMetaData = str4;
        this.numberOfTracks = unsignedIntegerFourBytes;
        this.mediaDuration = str5;
        this.playMedium = storageMedium;
    }

    public MediaInfo(String str, String str2, String str3, String str4, UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str5, StorageMedium storageMedium, StorageMedium storageMedium2, RecordMediumWriteStatus recordMediumWriteStatus) {
        this.currentURI = "";
        this.currentURIMetaData = "";
        this.nextURI = "NOT_IMPLEMENTED";
        this.nextURIMetaData = "NOT_IMPLEMENTED";
        this.numberOfTracks = new UnsignedIntegerFourBytes(0L);
        this.mediaDuration = "00:00:00";
        this.playMedium = StorageMedium.NONE;
        this.recordMedium = StorageMedium.NOT_IMPLEMENTED;
        this.writeStatus = RecordMediumWriteStatus.NOT_IMPLEMENTED;
        this.currentURI = str;
        this.currentURIMetaData = str2;
        this.nextURI = str3;
        this.nextURIMetaData = str4;
        this.numberOfTracks = unsignedIntegerFourBytes;
        this.mediaDuration = str5;
        this.playMedium = storageMedium;
        this.recordMedium = storageMedium2;
        this.writeStatus = recordMediumWriteStatus;
    }

    public String getCurrentURI() {
        return this.currentURI;
    }

    public String getCurrentURIMetaData() {
        return this.currentURIMetaData;
    }

    public String getNextURI() {
        return this.nextURI;
    }

    public String getNextURIMetaData() {
        return this.nextURIMetaData;
    }

    public UnsignedIntegerFourBytes getNumberOfTracks() {
        return this.numberOfTracks;
    }

    public String getMediaDuration() {
        return this.mediaDuration;
    }

    public StorageMedium getPlayMedium() {
        return this.playMedium;
    }

    public StorageMedium getRecordMedium() {
        return this.recordMedium;
    }

    public RecordMediumWriteStatus getWriteStatus() {
        return this.writeStatus;
    }
}
