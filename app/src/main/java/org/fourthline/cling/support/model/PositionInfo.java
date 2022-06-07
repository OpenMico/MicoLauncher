package org.fourthline.cling.support.model;

import java.util.Map;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

/* loaded from: classes5.dex */
public class PositionInfo {
    private int absCount;
    private String absTime;
    private int relCount;
    private String relTime;
    private UnsignedIntegerFourBytes track;
    private String trackDuration;
    private String trackMetaData;
    private String trackURI;

    public PositionInfo() {
        this.track = new UnsignedIntegerFourBytes(0L);
        this.trackDuration = "00:00:00";
        this.trackMetaData = "NOT_IMPLEMENTED";
        this.trackURI = "";
        this.relTime = "00:00:00";
        this.absTime = "00:00:00";
        this.relCount = Integer.MAX_VALUE;
        this.absCount = Integer.MAX_VALUE;
    }

    public PositionInfo(Map<String, ActionArgumentValue> map) {
        this(((UnsignedIntegerFourBytes) map.get("Track").getValue()).getValue().longValue(), (String) map.get("TrackDuration").getValue(), (String) map.get("TrackMetaData").getValue(), (String) map.get("TrackURI").getValue(), (String) map.get("RelTime").getValue(), (String) map.get("AbsTime").getValue(), ((Integer) map.get("RelCount").getValue()).intValue(), ((Integer) map.get("AbsCount").getValue()).intValue());
    }

    public PositionInfo(PositionInfo positionInfo, String str, String str2) {
        this.track = new UnsignedIntegerFourBytes(0L);
        this.trackDuration = "00:00:00";
        this.trackMetaData = "NOT_IMPLEMENTED";
        this.trackURI = "";
        this.relTime = "00:00:00";
        this.absTime = "00:00:00";
        this.relCount = Integer.MAX_VALUE;
        this.absCount = Integer.MAX_VALUE;
        this.track = positionInfo.track;
        this.trackDuration = positionInfo.trackDuration;
        this.trackMetaData = positionInfo.trackMetaData;
        this.trackURI = positionInfo.trackURI;
        this.relTime = str;
        this.absTime = str2;
        this.relCount = positionInfo.relCount;
        this.absCount = positionInfo.absCount;
    }

    public PositionInfo(PositionInfo positionInfo, long j, long j2) {
        this.track = new UnsignedIntegerFourBytes(0L);
        this.trackDuration = "00:00:00";
        this.trackMetaData = "NOT_IMPLEMENTED";
        this.trackURI = "";
        this.relTime = "00:00:00";
        this.absTime = "00:00:00";
        this.relCount = Integer.MAX_VALUE;
        this.absCount = Integer.MAX_VALUE;
        this.track = positionInfo.track;
        this.trackDuration = positionInfo.trackDuration;
        this.trackMetaData = positionInfo.trackMetaData;
        this.trackURI = positionInfo.trackURI;
        this.relTime = ModelUtil.toTimeString(j);
        this.absTime = ModelUtil.toTimeString(j2);
        this.relCount = positionInfo.relCount;
        this.absCount = positionInfo.absCount;
    }

    public PositionInfo(long j, String str, String str2, String str3, String str4) {
        this.track = new UnsignedIntegerFourBytes(0L);
        this.trackDuration = "00:00:00";
        this.trackMetaData = "NOT_IMPLEMENTED";
        this.trackURI = "";
        this.relTime = "00:00:00";
        this.absTime = "00:00:00";
        this.relCount = Integer.MAX_VALUE;
        this.absCount = Integer.MAX_VALUE;
        this.track = new UnsignedIntegerFourBytes(j);
        this.trackDuration = str;
        this.trackURI = str2;
        this.relTime = str3;
        this.absTime = str4;
    }

    public PositionInfo(long j, String str, String str2, String str3, String str4, String str5, int i, int i2) {
        this.track = new UnsignedIntegerFourBytes(0L);
        this.trackDuration = "00:00:00";
        this.trackMetaData = "NOT_IMPLEMENTED";
        this.trackURI = "";
        this.relTime = "00:00:00";
        this.absTime = "00:00:00";
        this.relCount = Integer.MAX_VALUE;
        this.absCount = Integer.MAX_VALUE;
        this.track = new UnsignedIntegerFourBytes(j);
        this.trackDuration = str;
        this.trackMetaData = str2;
        this.trackURI = str3;
        this.relTime = str4;
        this.absTime = str5;
        this.relCount = i;
        this.absCount = i2;
    }

    public PositionInfo(long j, String str, String str2) {
        this.track = new UnsignedIntegerFourBytes(0L);
        this.trackDuration = "00:00:00";
        this.trackMetaData = "NOT_IMPLEMENTED";
        this.trackURI = "";
        this.relTime = "00:00:00";
        this.absTime = "00:00:00";
        this.relCount = Integer.MAX_VALUE;
        this.absCount = Integer.MAX_VALUE;
        this.track = new UnsignedIntegerFourBytes(j);
        this.trackMetaData = str;
        this.trackURI = str2;
    }

    public UnsignedIntegerFourBytes getTrack() {
        return this.track;
    }

    public String getTrackDuration() {
        return this.trackDuration;
    }

    public String getTrackMetaData() {
        return this.trackMetaData;
    }

    public String getTrackURI() {
        return this.trackURI;
    }

    public String getRelTime() {
        return this.relTime;
    }

    public String getAbsTime() {
        return this.absTime;
    }

    public int getRelCount() {
        return this.relCount;
    }

    public int getAbsCount() {
        return this.absCount;
    }

    public void setTrackDuration(String str) {
        this.trackDuration = str;
    }

    public void setRelTime(String str) {
        this.relTime = str;
    }

    public long getTrackDurationSeconds() {
        if (getTrackDuration() == null) {
            return 0L;
        }
        return ModelUtil.fromTimeString(getTrackDuration());
    }

    public long getTrackElapsedSeconds() {
        if (getRelTime() == null || getRelTime().equals("NOT_IMPLEMENTED")) {
            return 0L;
        }
        return ModelUtil.fromTimeString(getRelTime());
    }

    public long getTrackRemainingSeconds() {
        return getTrackDurationSeconds() - getTrackElapsedSeconds();
    }

    public int getElapsedPercent() {
        long trackElapsedSeconds = getTrackElapsedSeconds();
        long trackDurationSeconds = getTrackDurationSeconds();
        if (trackElapsedSeconds == 0 || trackDurationSeconds == 0) {
            return 0;
        }
        return new Double(trackElapsedSeconds / (trackDurationSeconds / 100.0d)).intValue();
    }

    public String toString() {
        return "(PositionInfo) Track: " + getTrack() + " RelTime: " + getRelTime() + " Duration: " + getTrackDuration() + " Percent: " + getElapsedPercent();
    }
}
