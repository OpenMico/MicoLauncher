package io.netty.channel;

/* loaded from: classes4.dex */
public final class WriteBufferWaterMark {
    public static final WriteBufferWaterMark DEFAULT = new WriteBufferWaterMark(32768, 65536, false);
    private final int a;
    private final int b;

    public WriteBufferWaterMark(int i, int i2) {
        this(i, i2, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WriteBufferWaterMark(int i, int i2, boolean z) {
        if (z) {
            if (i < 0) {
                throw new IllegalArgumentException("write buffer's low water mark must be >= 0");
            } else if (i2 < i) {
                throw new IllegalArgumentException("write buffer's high water mark cannot be less than  low water mark (" + i + "): " + i2);
            }
        }
        this.a = i;
        this.b = i2;
    }

    public int low() {
        return this.a;
    }

    public int high() {
        return this.b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(55);
        sb.append("WriteBufferWaterMark(low: ");
        sb.append(this.a);
        sb.append(", high: ");
        sb.append(this.b);
        sb.append(")");
        return sb.toString();
    }
}
