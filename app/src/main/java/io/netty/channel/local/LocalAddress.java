package io.netty.channel.local;

import com.xiaomi.micolauncher.api.model.Video;
import io.netty.channel.Channel;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class LocalAddress extends SocketAddress implements Comparable<LocalAddress> {
    public static final LocalAddress ANY = new LocalAddress(Video.Category.ANY_CATEGORY_TYPE);
    private static final long serialVersionUID = 4644331421130916435L;
    private final String id;
    private final String strVal;

    public LocalAddress(Channel channel) {
        StringBuilder sb = new StringBuilder(16);
        sb.append("local:E");
        sb.append(Long.toHexString((channel.hashCode() & 4294967295L) | 4294967296L));
        sb.setCharAt(7, ':');
        this.id = sb.substring(6);
        this.strVal = sb.toString();
    }

    public LocalAddress(String str) {
        if (str != null) {
            String lowerCase = str.trim().toLowerCase();
            if (!lowerCase.isEmpty()) {
                this.id = lowerCase;
                this.strVal = "local:" + lowerCase;
                return;
            }
            throw new IllegalArgumentException("empty id");
        }
        throw new NullPointerException("id");
    }

    public String id() {
        return this.id;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LocalAddress)) {
            return false;
        }
        return this.id.equals(((LocalAddress) obj).id);
    }

    public int compareTo(LocalAddress localAddress) {
        return this.id.compareTo(localAddress.id);
    }

    public String toString() {
        return this.strVal;
    }
}
