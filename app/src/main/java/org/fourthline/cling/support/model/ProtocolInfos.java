package org.fourthline.cling.support.model;

import java.util.ArrayList;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.types.InvalidValueException;

/* loaded from: classes5.dex */
public class ProtocolInfos extends ArrayList<ProtocolInfo> {
    public ProtocolInfos(ProtocolInfo... protocolInfoArr) {
        for (ProtocolInfo protocolInfo : protocolInfoArr) {
            add(protocolInfo);
        }
    }

    public ProtocolInfos(String str) throws InvalidValueException {
        String[] fromCommaSeparatedList = ModelUtil.fromCommaSeparatedList(str);
        if (fromCommaSeparatedList != null) {
            for (String str2 : fromCommaSeparatedList) {
                add(new ProtocolInfo(str2));
            }
        }
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return ModelUtil.toCommaSeparatedList(toArray(new ProtocolInfo[size()]));
    }
}
