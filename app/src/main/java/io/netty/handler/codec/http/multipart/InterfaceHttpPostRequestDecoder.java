package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpContent;
import java.util.List;

/* loaded from: classes4.dex */
public interface InterfaceHttpPostRequestDecoder {
    void cleanFiles();

    InterfaceHttpData currentPartialHttpData();

    void destroy();

    InterfaceHttpData getBodyHttpData(String str);

    List<InterfaceHttpData> getBodyHttpDatas();

    List<InterfaceHttpData> getBodyHttpDatas(String str);

    int getDiscardThreshold();

    boolean hasNext();

    boolean isMultipart();

    InterfaceHttpData next();

    InterfaceHttpPostRequestDecoder offer(HttpContent httpContent);

    void removeHttpDataFromClean(InterfaceHttpData interfaceHttpData);

    void setDiscardThreshold(int i);
}
