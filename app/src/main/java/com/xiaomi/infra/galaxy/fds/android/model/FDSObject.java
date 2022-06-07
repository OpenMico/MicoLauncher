package com.xiaomi.infra.galaxy.fds.android.model;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class FDSObject implements Closeable {
    private final String a;
    private final String b;
    private ObjectMetadata c;
    private InputStream d;

    public FDSObject(String str, String str2) {
        this.b = str;
        this.a = str2;
    }

    public String getObjectName() {
        return this.a;
    }

    public String getBucketName() {
        return this.b;
    }

    public ObjectMetadata getObjectMetadata() {
        return this.c;
    }

    public void setObjectMetadata(ObjectMetadata objectMetadata) {
        this.c = objectMetadata;
    }

    public InputStream getObjectContent() {
        return this.d;
    }

    public void setObjectContent(InputStream inputStream) {
        this.d = inputStream;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        InputStream inputStream = this.d;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }
}
