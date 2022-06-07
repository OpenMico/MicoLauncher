package org.fourthline.cling.model.types.csv;

import java.net.URI;
import org.fourthline.cling.model.types.InvalidValueException;

/* loaded from: classes5.dex */
public class CSVURI extends CSV<URI> {
    public CSVURI() {
    }

    public CSVURI(String str) throws InvalidValueException {
        super(str);
    }
}
