package com.xiaomi.miplay.mylibrary.external.api;

import com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData;
import com.xiaomi.miplay.phoneclientsdk.info.PropertiesInfo;

/* loaded from: classes4.dex */
public interface IExternal {
    int onBufferStateChanged(String str, int i);

    int play(String str, MediaMetaData mediaMetaData);

    int playStateChange(String str, int i);

    int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo);
}
