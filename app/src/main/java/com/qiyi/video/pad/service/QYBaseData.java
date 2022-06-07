package com.qiyi.video.pad.service;

/* loaded from: classes2.dex */
public abstract class QYBaseData {
    public int code;
    public String msg;

    abstract QYBaseData parse(String str);

    abstract String toJsonStr();
}
