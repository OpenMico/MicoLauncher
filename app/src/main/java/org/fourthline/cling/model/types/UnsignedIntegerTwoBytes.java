package org.fourthline.cling.model.types;

import org.fourthline.cling.model.types.UnsignedVariableInteger;

/* loaded from: classes5.dex */
public final class UnsignedIntegerTwoBytes extends UnsignedVariableInteger {
    public UnsignedIntegerTwoBytes(long j) throws NumberFormatException {
        super(j);
    }

    public UnsignedIntegerTwoBytes(String str) throws NumberFormatException {
        super(str);
    }

    @Override // org.fourthline.cling.model.types.UnsignedVariableInteger
    public UnsignedVariableInteger.Bits getBits() {
        return UnsignedVariableInteger.Bits.SIXTEEN;
    }
}
