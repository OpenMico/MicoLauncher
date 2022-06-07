package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.bean.GrantBean;
import com.xiaomi.infra.galaxy.fds.bean.OwnerBean;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class AccessControlPolicy {
    private OwnerBean a;
    private List<GrantBean> b;

    public AccessControlPolicy() {
    }

    public AccessControlPolicy(OwnerBean ownerBean, List<GrantBean> list) {
        this.a = ownerBean;
        this.b = list;
    }

    public OwnerBean getOwner() {
        return this.a;
    }

    public void setOwner(OwnerBean ownerBean) {
        this.a = ownerBean;
    }

    public List<GrantBean> getAccessControlList() {
        return this.b;
    }

    public void setAccessControlList(List<GrantBean> list) {
        this.b = list;
    }
}
