package org.fourthline.cling.model.types.csv;

import org.fourthline.cling.model.types.InvalidValueException;

/* loaded from: classes5.dex */
public class CSVInteger extends CSV<Integer> {
    public CSVInteger() {
    }

    public CSVInteger(String str) throws InvalidValueException {
        super(str);
    }
}
