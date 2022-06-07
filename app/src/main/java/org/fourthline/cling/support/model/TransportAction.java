package org.fourthline.cling.support.model;

import java.util.ArrayList;
import org.fourthline.cling.model.ModelUtil;

/* loaded from: classes5.dex */
public enum TransportAction {
    Play,
    Stop,
    Pause,
    Seek,
    Next,
    Previous,
    Record;

    public static TransportAction[] valueOfCommaSeparatedList(String str) {
        String[] fromCommaSeparatedList = ModelUtil.fromCommaSeparatedList(str);
        if (fromCommaSeparatedList == null) {
            return new TransportAction[0];
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : fromCommaSeparatedList) {
            TransportAction[] values = values();
            for (TransportAction transportAction : values) {
                if (transportAction.name().equals(str2)) {
                    arrayList.add(transportAction);
                }
            }
        }
        return (TransportAction[]) arrayList.toArray(new TransportAction[arrayList.size()]);
    }
}
