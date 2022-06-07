package io.netty.handler.codec.string;

import io.netty.buffer.ByteBufUtil;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public final class LineSeparator {
    public static final LineSeparator DEFAULT = new LineSeparator(StringUtil.NEWLINE);
    public static final LineSeparator UNIX = new LineSeparator("\n");
    public static final LineSeparator WINDOWS = new LineSeparator("\r\n");
    private final String a;

    public LineSeparator(String str) {
        this.a = (String) ObjectUtil.checkNotNull(str, "lineSeparator");
    }

    public String value() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineSeparator)) {
            return false;
        }
        LineSeparator lineSeparator = (LineSeparator) obj;
        String str = this.a;
        return str != null ? str.equals(lineSeparator.a) : lineSeparator.a == null;
    }

    public int hashCode() {
        String str = this.a;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        return ByteBufUtil.hexDump(this.a.getBytes(CharsetUtil.UTF_8));
    }
}
