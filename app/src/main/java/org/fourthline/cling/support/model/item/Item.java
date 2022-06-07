package org.fourthline.cling.support.model.item;

import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.DescMeta;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.WriteStatus;
import org.fourthline.cling.support.model.container.Container;

/* loaded from: classes5.dex */
public class Item extends DIDLObject {
    protected String refID;

    public Item() {
    }

    public Item(Item item) {
        super(item);
        setRefID(item.getRefID());
    }

    public Item(String str, String str2, String str3, String str4, boolean z, WriteStatus writeStatus, DIDLObject.Class r7, List<Res> list, List<DIDLObject.Property> list2, List<DescMeta> list3) {
        super(str, str2, str3, str4, z, writeStatus, r7, list, list2, list3);
    }

    public Item(String str, String str2, String str3, String str4, boolean z, WriteStatus writeStatus, DIDLObject.Class r7, List<Res> list, List<DIDLObject.Property> list2, List<DescMeta> list3, String str5) {
        super(str, str2, str3, str4, z, writeStatus, r7, list, list2, list3);
        this.refID = str5;
    }

    public Item(String str, Container container, String str2, String str3, DIDLObject.Class r16) {
        this(str, container.getId(), str2, str3, false, null, r16, new ArrayList(), new ArrayList(), new ArrayList());
    }

    public Item(String str, Container container, String str2, String str3, DIDLObject.Class r17, String str4) {
        this(str, container.getId(), str2, str3, false, null, r17, new ArrayList(), new ArrayList(), new ArrayList(), str4);
    }

    public Item(String str, String str2, String str3, String str4, DIDLObject.Class r16) {
        this(str, str2, str3, str4, false, null, r16, new ArrayList(), new ArrayList(), new ArrayList());
    }

    public Item(String str, String str2, String str3, String str4, DIDLObject.Class r17, String str5) {
        this(str, str2, str3, str4, false, null, r17, new ArrayList(), new ArrayList(), new ArrayList(), str5);
    }

    public String getRefID() {
        return this.refID;
    }

    public void setRefID(String str) {
        this.refID = str;
    }
}
