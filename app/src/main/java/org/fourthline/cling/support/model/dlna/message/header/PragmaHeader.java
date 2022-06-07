package org.fourthline.cling.support.model.dlna.message.header;

import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.model.message.header.InvalidHeaderException;
import org.fourthline.cling.model.types.PragmaType;

/* loaded from: classes5.dex */
public class PragmaHeader extends DLNAHeader<List<PragmaType>> {
    public PragmaHeader() {
        setValue(new ArrayList());
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (str.length() != 0) {
            if (str.endsWith(";")) {
                str = str.substring(0, str.length() - 1);
            }
            String[] split = str.split("\\s*;\\s*");
            ArrayList arrayList = new ArrayList();
            for (String str2 : split) {
                arrayList.add(PragmaType.valueOf(str2));
            }
            return;
        }
        throw new InvalidHeaderException("Invalid Pragma header value: " + str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        String str = "";
        for (PragmaType pragmaType : getValue()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str.length() == 0 ? "" : Constants.ACCEPT_TIME_SEPARATOR_SP);
            sb.append(pragmaType.getString());
            str = sb.toString();
        }
        return str;
    }
}
