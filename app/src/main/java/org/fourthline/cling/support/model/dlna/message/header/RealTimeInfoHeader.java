package org.fourthline.cling.support.model.dlna.message.header;

import org.fourthline.cling.model.message.header.InvalidHeaderException;
import org.fourthline.cling.support.model.dlna.types.NormalPlayTime;

/* loaded from: classes5.dex */
public class RealTimeInfoHeader extends DLNAHeader<NormalPlayTime> {
    public static final String PREFIX = "DLNA.ORG_TLAG=";

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (str.length() != 0 && str.startsWith(PREFIX)) {
            try {
                str = str.substring(14);
                setValue(str.equals("*") ? null : NormalPlayTime.valueOf(str));
                return;
            } catch (Exception unused) {
            }
        }
        throw new InvalidHeaderException("Invalid RealTimeInfo header value: " + str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        NormalPlayTime value = getValue();
        if (value == null) {
            return "DLNA.ORG_TLAG=*";
        }
        return PREFIX + value.getString();
    }
}
