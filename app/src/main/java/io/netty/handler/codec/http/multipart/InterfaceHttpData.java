package io.netty.handler.codec.http.multipart;

import io.netty.util.ReferenceCounted;

/* loaded from: classes4.dex */
public interface InterfaceHttpData extends ReferenceCounted, Comparable<InterfaceHttpData> {

    /* loaded from: classes4.dex */
    public enum HttpDataType {
        Attribute,
        FileUpload,
        InternalAttribute
    }

    HttpDataType getHttpDataType();

    String getName();

    @Override // io.netty.util.ReferenceCounted
    InterfaceHttpData retain();

    @Override // io.netty.util.ReferenceCounted
    InterfaceHttpData retain(int i);

    @Override // io.netty.util.ReferenceCounted
    InterfaceHttpData touch();

    @Override // io.netty.util.ReferenceCounted
    InterfaceHttpData touch(Object obj);
}
