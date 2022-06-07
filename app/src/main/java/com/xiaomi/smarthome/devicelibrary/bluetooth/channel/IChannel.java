package com.xiaomi.smarthome.devicelibrary.bluetooth.channel;

import java.util.List;

/* loaded from: classes4.dex */
public interface IChannel {
    void onRead(byte[] bArr);

    void onRecv(byte[] bArr, int i);

    void reset();

    void send(int i, byte[] bArr, int i2, ChannelCallback channelCallback);

    void send(byte[] bArr, int i, ChannelCallback channelCallback);

    void setCurrentState(int i);

    void write(byte[] bArr, ChannelCallback channelCallback, boolean z);

    void writeBatchData(List<byte[]> list, ChannelCallback channelCallback);
}
