package org.hapjs.features.channel;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ChannelMessage {
    public static final String KEY_CODE = "code";
    public static final String KEY_DATA = "data";
    public static final String KEY_STREAMS = "streams";
    private Object a;
    public int code;
    public List<ParcelFileDescriptor> streams;

    public static ChannelMessage parse(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        ChannelMessage channelMessage = new ChannelMessage();
        channelMessage.code = bundle.getInt("code");
        channelMessage.a = bundle.get("data");
        channelMessage.streams = bundle.getParcelableArrayList(KEY_STREAMS);
        return channelMessage;
    }

    public ChannelMessage() {
    }

    public ChannelMessage(ChannelMessage channelMessage) {
        this.code = channelMessage.code;
        this.a = channelMessage.a;
        this.streams = channelMessage.streams;
    }

    public Object getData() {
        return this.a;
    }

    public void setData(String str) {
        this.a = str;
    }

    public void setData(byte[] bArr) {
        this.a = bArr;
    }

    public int dataSize() {
        Object obj = this.a;
        if (obj instanceof String) {
            return ((String) obj).length() * 2;
        }
        if (obj instanceof byte[]) {
            return ((byte[]) obj).length;
        }
        return 0;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("code", this.code);
        Object obj = this.a;
        if (obj instanceof byte[]) {
            bundle.putByteArray("data", (byte[]) obj);
        } else if (obj instanceof String) {
            bundle.putString("data", String.valueOf(obj));
        }
        List<ParcelFileDescriptor> list = this.streams;
        if (list != null) {
            bundle.putParcelableArrayList(KEY_STREAMS, new ArrayList<>(list));
        }
        return bundle;
    }
}
