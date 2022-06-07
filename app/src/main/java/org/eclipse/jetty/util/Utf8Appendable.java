package org.eclipse.jetty.util;

import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.io.IOException;

/* loaded from: classes5.dex */
public abstract class Utf8Appendable {
    public static final char REPLACEMENT = 65533;
    private static final int UTF8_ACCEPT = 0;
    private static final int UTF8_REJECT = 12;
    protected final Appendable _appendable;
    private int _codep;
    protected int _state = 0;
    private static final byte[] BYTE_TABLE = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 11, 6, 6, 6, 5, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
    private static final byte[] TRANS_TABLE = {0, 12, 24, BinaryMemcacheOpcodes.GATKQ, 60, 96, 84, 12, 12, 12, 48, 72, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0, 12, 12, 12, 12, 12, 0, 12, 0, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, BinaryMemcacheOpcodes.GATKQ, 12, BinaryMemcacheOpcodes.GATKQ, 12, 12, 12, BinaryMemcacheOpcodes.GATKQ, 12, 12, 12, 12, 12, BinaryMemcacheOpcodes.GATKQ, 12, BinaryMemcacheOpcodes.GATKQ, 12, 12, 12, BinaryMemcacheOpcodes.GATKQ, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    public abstract int length();

    public Utf8Appendable(Appendable appendable) {
        this._appendable = appendable;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void reset() {
        this._state = 0;
    }

    public void append(byte b) {
        try {
            appendByte(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void append(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            try {
                appendByte(bArr[i]);
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean append(byte[] bArr, int i, int i2, int i3) {
        int i4 = i2 + i;
        while (i < i4) {
            try {
                if (length() > i3) {
                    return false;
                }
                appendByte(bArr[i]);
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    protected void appendByte(byte b) throws IOException {
        if (b <= 0 || this._state != 0) {
            int i = b & 255;
            byte b2 = BYTE_TABLE[i];
            this._codep = this._state == 0 ? (255 >> b2) & i : (i & 63) | (this._codep << 6);
            byte b3 = TRANS_TABLE[this._state + b2];
            if (b3 == 0) {
                this._state = b3;
                int i2 = this._codep;
                if (i2 < 55296) {
                    this._appendable.append((char) i2);
                    return;
                }
                for (char c : Character.toChars(i2)) {
                    this._appendable.append(c);
                }
            } else if (b3 != 12) {
                this._state = b3;
            } else {
                String str = "byte " + TypeUtil.toHexString(b) + " in state " + (this._state / 12);
                this._codep = 0;
                this._state = 0;
                this._appendable.append((char) 65533);
                throw new NotUtf8Exception(str);
            }
        } else {
            this._appendable.append((char) (b & 255));
        }
    }

    public boolean isUtf8SequenceComplete() {
        return this._state == 0;
    }

    /* loaded from: classes5.dex */
    public static class NotUtf8Exception extends IllegalArgumentException {
        public NotUtf8Exception(String str) {
            super("Not valid UTF8! " + str);
        }
    }
}
