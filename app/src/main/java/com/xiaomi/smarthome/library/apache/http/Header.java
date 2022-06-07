package com.xiaomi.smarthome.library.apache.http;

/* loaded from: classes4.dex */
public interface Header {
    HeaderElement[] getElements() throws ParseException;

    String getName();

    String getValue();
}
