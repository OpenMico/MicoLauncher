package okhttp3;

import com.xiaomi.onetrack.api.b;
import java.net.Socket;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Connection.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lokhttp3/Connection;", "", b.S, "Lokhttp3/Handshake;", "protocol", "Lokhttp3/Protocol;", "route", "Lokhttp3/Route;", "socket", "Ljava/net/Socket;", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public interface Connection {
    @Nullable
    Handshake handshake();

    @NotNull
    Protocol protocol();

    @NotNull
    Route route();

    @NotNull
    Socket socket();
}
