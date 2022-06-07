package io.netty.handler.codec.stomp;

import com.umeng.analytics.pro.c;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.rtsp.RtspHeaders;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public interface StompHeaders extends Headers<CharSequence, CharSequence, StompHeaders> {
    public static final AsciiString ACCEPT_VERSION = new AsciiString("accept-version");
    public static final AsciiString HOST = new AsciiString(b.E);
    public static final AsciiString LOGIN = new AsciiString(OneTrack.Event.LOGIN);
    public static final AsciiString PASSCODE = new AsciiString("passcode");
    public static final AsciiString HEART_BEAT = new AsciiString("heart-beat");
    public static final AsciiString VERSION = new AsciiString("version");
    public static final AsciiString SESSION = new AsciiString(c.aw);
    public static final AsciiString SERVER = new AsciiString("server");
    public static final AsciiString DESTINATION = new AsciiString(RtspHeaders.Values.DESTINATION);
    public static final AsciiString ID = new AsciiString("id");
    public static final AsciiString ACK = new AsciiString(Packet.ACK);
    public static final AsciiString TRANSACTION = new AsciiString("transaction");
    public static final AsciiString RECEIPT = new AsciiString("receipt");
    public static final AsciiString MESSAGE_ID = new AsciiString("message-id");
    public static final AsciiString SUBSCRIPTION = new AsciiString("subscription");
    public static final AsciiString RECEIPT_ID = new AsciiString("receipt-id");
    public static final AsciiString MESSAGE = new AsciiString("message");
    public static final AsciiString CONTENT_LENGTH = new AsciiString(Common.CONTENT_LENGTH);
    public static final AsciiString CONTENT_TYPE = new AsciiString(Common.CONTENT_TYPE);

    boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z);

    List<String> getAllAsString(CharSequence charSequence);

    String getAsString(CharSequence charSequence);

    Iterator<Map.Entry<String, String>> iteratorAsString();
}
