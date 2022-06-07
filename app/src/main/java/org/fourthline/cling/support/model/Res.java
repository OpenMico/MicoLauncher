package org.fourthline.cling.support.model;

import java.net.URI;
import org.seamless.util.MimeType;

/* loaded from: classes5.dex */
public class Res {
    protected Long bitrate;
    protected Long bitsPerSample;
    protected Long colorDepth;
    protected String duration;
    protected URI importUri;
    protected Long nrAudioChannels;
    protected String protection;
    protected ProtocolInfo protocolInfo;
    protected String resolution;
    protected Long sampleFrequency;
    protected Long size;
    protected String value;

    public Res() {
    }

    public Res(String str, Long l, String str2, Long l2, String str3) {
        this(new ProtocolInfo(Protocol.HTTP_GET, "*", str, "*"), l, str2, l2, str3);
    }

    public Res(MimeType mimeType, Long l, String str, Long l2, String str2) {
        this(new ProtocolInfo(mimeType), l, str, l2, str2);
    }

    public Res(MimeType mimeType, Long l, String str) {
        this(new ProtocolInfo(mimeType), l, str);
    }

    public Res(ProtocolInfo protocolInfo, Long l, String str) {
        this.protocolInfo = protocolInfo;
        this.size = l;
        this.value = str;
    }

    public Res(ProtocolInfo protocolInfo, Long l, String str, Long l2, String str2) {
        this.protocolInfo = protocolInfo;
        this.size = l;
        this.duration = str;
        this.bitrate = l2;
        this.value = str2;
    }

    public Res(URI uri, ProtocolInfo protocolInfo, Long l, String str, Long l2, Long l3, Long l4, Long l5, Long l6, String str2, String str3, String str4) {
        this.importUri = uri;
        this.protocolInfo = protocolInfo;
        this.size = l;
        this.duration = str;
        this.bitrate = l2;
        this.sampleFrequency = l3;
        this.bitsPerSample = l4;
        this.nrAudioChannels = l5;
        this.colorDepth = l6;
        this.protection = str2;
        this.resolution = str3;
        this.value = str4;
    }

    public URI getImportUri() {
        return this.importUri;
    }

    public void setImportUri(URI uri) {
        this.importUri = uri;
    }

    public ProtocolInfo getProtocolInfo() {
        return this.protocolInfo;
    }

    public void setProtocolInfo(ProtocolInfo protocolInfo) {
        this.protocolInfo = protocolInfo;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long l) {
        this.size = l;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public Long getBitrate() {
        return this.bitrate;
    }

    public void setBitrate(Long l) {
        this.bitrate = l;
    }

    public Long getSampleFrequency() {
        return this.sampleFrequency;
    }

    public void setSampleFrequency(Long l) {
        this.sampleFrequency = l;
    }

    public Long getBitsPerSample() {
        return this.bitsPerSample;
    }

    public void setBitsPerSample(Long l) {
        this.bitsPerSample = l;
    }

    public Long getNrAudioChannels() {
        return this.nrAudioChannels;
    }

    public void setNrAudioChannels(Long l) {
        this.nrAudioChannels = l;
    }

    public Long getColorDepth() {
        return this.colorDepth;
    }

    public void setColorDepth(Long l) {
        this.colorDepth = l;
    }

    public String getProtection() {
        return this.protection;
    }

    public void setProtection(String str) {
        this.protection = str;
    }

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String str) {
        this.resolution = str;
    }

    public void setResolution(int i, int i2) {
        this.resolution = i + "x" + i2;
    }

    public int getResolutionX() {
        if (getResolution() == null || getResolution().split("x").length != 2) {
            return 0;
        }
        return Integer.valueOf(getResolution().split("x")[0]).intValue();
    }

    public int getResolutionY() {
        if (getResolution() == null || getResolution().split("x").length != 2) {
            return 0;
        }
        return Integer.valueOf(getResolution().split("x")[1]).intValue();
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
