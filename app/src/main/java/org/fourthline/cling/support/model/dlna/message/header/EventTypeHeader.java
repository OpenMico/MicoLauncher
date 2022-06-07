package org.fourthline.cling.support.model.dlna.message.header;

import java.util.regex.Pattern;
import org.fourthline.cling.model.message.header.InvalidHeaderException;

/* loaded from: classes5.dex */
public class EventTypeHeader extends DLNAHeader<String> {
    static final Pattern pattern = Pattern.compile("^[0-9]{4}$", 2);

    public EventTypeHeader() {
        setValue("0000");
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (pattern.matcher(str).matches()) {
            setValue(str);
            return;
        }
        throw new InvalidHeaderException("Invalid EventType header value: " + str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().toString();
    }
}
