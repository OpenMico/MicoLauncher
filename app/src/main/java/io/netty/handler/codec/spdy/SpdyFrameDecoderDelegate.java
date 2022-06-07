package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public interface SpdyFrameDecoderDelegate {
    void readDataFrame(int i, boolean z, ByteBuf byteBuf);

    void readFrameError(String str);

    void readGoAwayFrame(int i, int i2);

    void readHeaderBlock(ByteBuf byteBuf);

    void readHeaderBlockEnd();

    void readHeadersFrame(int i, boolean z);

    void readPingFrame(int i);

    void readRstStreamFrame(int i, int i2);

    void readSetting(int i, int i2, boolean z, boolean z2);

    void readSettingsEnd();

    void readSettingsFrame(boolean z);

    void readSynReplyFrame(int i, boolean z);

    void readSynStreamFrame(int i, int i2, byte b, boolean z, boolean z2);

    void readWindowUpdateFrame(int i, int i2);
}
