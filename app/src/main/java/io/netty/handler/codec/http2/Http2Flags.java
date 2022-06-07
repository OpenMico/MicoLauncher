package io.netty.handler.codec.http2;

/* loaded from: classes4.dex */
public final class Http2Flags {
    public static final short ACK = 1;
    public static final short END_HEADERS = 4;
    public static final short END_STREAM = 1;
    public static final short PADDED = 8;
    public static final short PRIORITY = 32;
    private short a;

    public Http2Flags() {
    }

    public Http2Flags(short s) {
        this.a = s;
    }

    public short value() {
        return this.a;
    }

    public boolean endOfStream() {
        return isFlagSet((short) 1);
    }

    public boolean endOfHeaders() {
        return isFlagSet((short) 4);
    }

    public boolean priorityPresent() {
        return isFlagSet((short) 32);
    }

    public boolean ack() {
        return isFlagSet((short) 1);
    }

    public boolean paddingPresent() {
        return isFlagSet((short) 8);
    }

    public int getNumPriorityBytes() {
        return priorityPresent() ? 5 : 0;
    }

    public int getPaddingPresenceFieldLength() {
        return paddingPresent() ? 1 : 0;
    }

    public Http2Flags endOfStream(boolean z) {
        return setFlag(z, (short) 1);
    }

    public Http2Flags endOfHeaders(boolean z) {
        return setFlag(z, (short) 4);
    }

    public Http2Flags priorityPresent(boolean z) {
        return setFlag(z, (short) 32);
    }

    public Http2Flags paddingPresent(boolean z) {
        return setFlag(z, (short) 8);
    }

    public Http2Flags ack(boolean z) {
        return setFlag(z, (short) 1);
    }

    public Http2Flags setFlag(boolean z, short s) {
        if (z) {
            this.a = (short) (this.a | s);
        } else {
            this.a = (short) (this.a & (~s));
        }
        return this;
    }

    public boolean isFlagSet(short s) {
        return (s & this.a) != 0;
    }

    public int hashCode() {
        return 31 + this.a;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.a == ((Http2Flags) obj).a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("value = ");
        sb.append((int) this.a);
        sb.append(" (");
        if (ack()) {
            sb.append("ACK,");
        }
        if (endOfHeaders()) {
            sb.append("END_OF_HEADERS,");
        }
        if (endOfStream()) {
            sb.append("END_OF_STREAM,");
        }
        if (priorityPresent()) {
            sb.append("PRIORITY_PRESENT,");
        }
        if (paddingPresent()) {
            sb.append("PADDING_PRESENT,");
        }
        sb.append(')');
        return sb.toString();
    }
}
