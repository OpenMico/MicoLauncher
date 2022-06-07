package org.fourthline.cling.model.types.csv;

import org.fourthline.cling.model.types.InvalidValueException;

/* loaded from: classes5.dex */
public class CSVString extends CSV<String> {
    public CSVString() {
    }

    public CSVString(String str) throws InvalidValueException {
        super(str);
    }
}
