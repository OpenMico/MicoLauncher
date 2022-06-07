package com.xiaomi.miplay.mylibrary.external.api;

import com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData;
import com.xiaomi.miplay.phoneclientsdk.info.PropertiesInfo;

/* loaded from: classes4.dex */
public interface IMediaControl {
    int cancelCirculate(String str, int i);

    int getCirculateMode();

    int getPosition(String str);

    String getSourceName(String str);

    int getVolume(String str);

    int onBufferState(String str, int i);

    int pause(String str);

    int play(String str, MediaMetaData mediaMetaData);

    int playState(String str, int i);

    int resume(String str);

    int seek(String str, long j);

    int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo);

    int setVolume(String str, double d);

    int stop(String str);
}
