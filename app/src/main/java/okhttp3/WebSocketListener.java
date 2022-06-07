package okhttp3;

import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.ByteString;
import org.hapjs.features.channel.IChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WebSocketListener.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\"\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u0018\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0016"}, d2 = {"Lokhttp3/WebSocketListener;", "", "()V", "onClosed", "", "webSocket", "Lokhttp3/WebSocket;", "code", "", IChannel.EXTRA_CLOSE_REASON, "", "onClosing", "onFailure", ai.aF, "", b.I, "Lokhttp3/Response;", "onMessage", "text", "bytes", "Lokio/ByteString;", "onOpen", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public abstract class WebSocketListener {
    public void onClosed(@NotNull WebSocket webSocket, int i, @NotNull String reason) {
        Intrinsics.checkParameterIsNotNull(webSocket, "webSocket");
        Intrinsics.checkParameterIsNotNull(reason, "reason");
    }

    public void onClosing(@NotNull WebSocket webSocket, int i, @NotNull String reason) {
        Intrinsics.checkParameterIsNotNull(webSocket, "webSocket");
        Intrinsics.checkParameterIsNotNull(reason, "reason");
    }

    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        Intrinsics.checkParameterIsNotNull(webSocket, "webSocket");
        Intrinsics.checkParameterIsNotNull(t, "t");
    }

    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull(webSocket, "webSocket");
        Intrinsics.checkParameterIsNotNull(text, "text");
    }

    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        Intrinsics.checkParameterIsNotNull(webSocket, "webSocket");
        Intrinsics.checkParameterIsNotNull(bytes, "bytes");
    }

    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        Intrinsics.checkParameterIsNotNull(webSocket, "webSocket");
        Intrinsics.checkParameterIsNotNull(response, "response");
    }
}
