package org.fourthline.cling.support.model.dlna.message.header;

import java.util.EnumMap;
import org.fourthline.cling.model.message.header.InvalidHeaderException;
import org.fourthline.cling.support.model.dlna.DLNAAttribute;

/* loaded from: classes5.dex */
public class ContentFeaturesHeader extends DLNAHeader<EnumMap<DLNAAttribute.Type, DLNAAttribute>> {
    public ContentFeaturesHeader() {
        setValue(new EnumMap(DLNAAttribute.Type.class));
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        DLNAAttribute.Type valueOfAttributeName;
        if (str.length() != 0) {
            for (String str2 : str.split(";")) {
                String[] split = str2.split("=");
                if (split.length == 2 && (valueOfAttributeName = DLNAAttribute.Type.valueOfAttributeName(split[0])) != null) {
                    getValue().put((EnumMap<DLNAAttribute.Type, DLNAAttribute>) valueOfAttributeName, (DLNAAttribute.Type) DLNAAttribute.newInstance(valueOfAttributeName, split[1], ""));
                }
            }
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        String str = "";
        DLNAAttribute.Type[] values = DLNAAttribute.Type.values();
        for (DLNAAttribute.Type type : values) {
            String string = getValue().containsKey(type) ? getValue().get(type).getString() : null;
            if (!(string == null || string.length() == 0)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(str.length() == 0 ? "" : ";");
                sb.append(type.getAttributeName());
                sb.append("=");
                sb.append(string);
                str = sb.toString();
            }
        }
        return str;
    }
}
