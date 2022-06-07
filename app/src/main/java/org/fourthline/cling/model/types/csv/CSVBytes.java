package org.fourthline.cling.model.types.csv;

import org.fourthline.cling.model.types.InvalidValueException;

/* loaded from: classes5.dex */
public class CSVBytes extends CSV<byte[]> {
    public CSVBytes() {
    }

    public CSVBytes(String str) throws InvalidValueException {
        super(str);
    }
}
