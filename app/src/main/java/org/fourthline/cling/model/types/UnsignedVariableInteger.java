package org.fourthline.cling.model.types;

import com.xiaomi.mipush.sdk.Constants;
import java.util.logging.Logger;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes5.dex */
public abstract class UnsignedVariableInteger {
    private static final Logger log = Logger.getLogger(UnsignedVariableInteger.class.getName());
    protected long value;

    public abstract Bits getBits();

    public int getMinValue() {
        return 0;
    }

    /* loaded from: classes5.dex */
    public enum Bits {
        EIGHT(255),
        SIXTEEN(WebSocketProtocol.PAYLOAD_SHORT_MAX),
        TWENTYFOUR(16777215),
        THIRTYTWO(4294967295L);
        
        private long maxValue;

        Bits(long j) {
            this.maxValue = j;
        }

        public long getMaxValue() {
            return this.maxValue;
        }
    }

    protected UnsignedVariableInteger() {
    }

    public UnsignedVariableInteger(long j) throws NumberFormatException {
        setValue(j);
    }

    public UnsignedVariableInteger(String str) throws NumberFormatException {
        if (str.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) {
            Logger logger = log;
            logger.warning("Invalid negative integer value '" + str + "', assuming value 0!");
            str = "0";
        }
        setValue(Long.parseLong(str.trim()));
    }

    protected UnsignedVariableInteger setValue(long j) {
        isInRange(j);
        this.value = j;
        return this;
    }

    public Long getValue() {
        return Long.valueOf(this.value);
    }

    public void isInRange(long j) throws NumberFormatException {
        if (j < getMinValue() || j > getBits().getMaxValue()) {
            throw new NumberFormatException("Value must be between " + getMinValue() + " and " + getBits().getMaxValue() + ": " + j);
        }
    }

    public UnsignedVariableInteger increment(boolean z) {
        long j = 1;
        if (this.value + 1 > getBits().getMaxValue()) {
            if (!z) {
                j = 0;
            }
            this.value = j;
        } else {
            this.value++;
        }
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.value == ((UnsignedVariableInteger) obj).value;
    }

    public int hashCode() {
        long j = this.value;
        return (int) (j ^ (j >>> 32));
    }

    public String toString() {
        return Long.toString(this.value);
    }
}
