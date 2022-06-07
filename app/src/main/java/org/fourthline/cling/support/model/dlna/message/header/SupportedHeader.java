package org.fourthline.cling.support.model.dlna.message.header;

import com.xiaomi.mipush.sdk.Constants;
import org.fourthline.cling.model.message.header.InvalidHeaderException;

/* loaded from: classes5.dex */
public class SupportedHeader extends DLNAHeader<String[]> {
    public SupportedHeader() {
        setValue(new String[0]);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (str.length() != 0) {
            if (str.endsWith(";")) {
                str = str.substring(0, str.length() - 1);
            }
            setValue(str.split("\\s*,\\s*"));
            return;
        }
        throw new InvalidHeaderException("Invalid Supported header value: " + str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        String[] value = getValue();
        String str = value.length > 0 ? value[0] : "";
        for (int i = 1; i < value.length; i++) {
            str = str + Constants.ACCEPT_TIME_SEPARATOR_SP + value[i];
        }
        return str;
    }
}
