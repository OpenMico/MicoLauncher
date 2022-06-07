package com.zlw.main.recorderlib.recorder;

import android.os.Environment;
import java.io.Serializable;
import java.util.Locale;

/* loaded from: classes4.dex */
public class RecordConfig implements Serializable {
    private int channelConfig;
    private int configVersion;
    private int encodingConfig;
    private RecordFormat format;
    private String recordDir;
    private int sampleRate;

    public RecordConfig() {
        this.format = RecordFormat.WAV;
        this.channelConfig = 16;
        this.encodingConfig = 2;
        this.sampleRate = 16000;
        this.recordDir = String.format(Locale.getDefault(), "%s/Record/", Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public RecordConfig(RecordFormat recordFormat) {
        this.format = RecordFormat.WAV;
        this.channelConfig = 16;
        this.encodingConfig = 2;
        this.sampleRate = 16000;
        this.recordDir = String.format(Locale.getDefault(), "%s/Record/", Environment.getExternalStorageDirectory().getAbsolutePath());
        this.format = recordFormat;
    }

    public RecordConfig(RecordFormat recordFormat, int i, int i2, int i3) {
        this.format = RecordFormat.WAV;
        this.channelConfig = 16;
        this.encodingConfig = 2;
        this.sampleRate = 16000;
        this.recordDir = String.format(Locale.getDefault(), "%s/Record/", Environment.getExternalStorageDirectory().getAbsolutePath());
        this.format = recordFormat;
        this.channelConfig = i;
        this.encodingConfig = i2;
        this.sampleRate = i3;
    }

    public RecordConfig(RecordFormat recordFormat, int i, int i2, int i3, int i4, String str) {
        this.format = RecordFormat.WAV;
        this.channelConfig = 16;
        this.encodingConfig = 2;
        this.sampleRate = 16000;
        this.recordDir = String.format(Locale.getDefault(), "%s/Record/", Environment.getExternalStorageDirectory().getAbsolutePath());
        this.format = recordFormat;
        this.channelConfig = i;
        this.encodingConfig = i2;
        this.sampleRate = i3;
        this.configVersion = i4;
        this.recordDir = str;
    }

    public String getRecordDir() {
        return this.recordDir;
    }

    public void setRecordDir(String str) {
        this.recordDir = str;
    }

    public int getEncoding() {
        if (this.format == RecordFormat.MP3) {
            return 16;
        }
        int i = this.encodingConfig;
        if (i == 3) {
            return 8;
        }
        return i == 2 ? 16 : 0;
    }

    public int getRealEncoding() {
        int i = this.encodingConfig;
        if (i == 3) {
            return 8;
        }
        return i == 2 ? 16 : 0;
    }

    public int getChannelCount() {
        int i = this.channelConfig;
        if (i == 16) {
            return 1;
        }
        return i == 12 ? 2 : 0;
    }

    public RecordFormat getFormat() {
        return this.format;
    }

    public RecordConfig setFormat(RecordFormat recordFormat) {
        this.format = recordFormat;
        return this;
    }

    public int getChannelConfig() {
        return this.channelConfig;
    }

    public RecordConfig setChannelConfig(int i) {
        this.channelConfig = i;
        return this;
    }

    public int getEncodingConfig() {
        if (this.format == RecordFormat.MP3) {
            return 2;
        }
        return this.encodingConfig;
    }

    public RecordConfig setEncodingConfig(int i) {
        this.encodingConfig = i;
        return this;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public RecordConfig setSampleRate(int i) {
        this.sampleRate = i;
        return this;
    }

    public int getConfigVersion() {
        return this.configVersion;
    }

    public void setConfigVersion(int i) {
        this.configVersion = i;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "录制格式： %s,采样率：%sHz,位宽：%s bit,声道数：%s", this.format, Integer.valueOf(this.sampleRate), Integer.valueOf(getEncoding()), Integer.valueOf(getChannelCount()));
    }

    /* loaded from: classes4.dex */
    public enum RecordFormat {
        MP3(".mp3"),
        WAV(".wav"),
        PCM(".pcm");
        
        private String extension;

        public String getExtension() {
            return this.extension;
        }

        RecordFormat(String str) {
            this.extension = str;
        }
    }
}
