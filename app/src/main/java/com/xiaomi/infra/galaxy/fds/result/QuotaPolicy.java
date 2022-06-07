package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.bean.Quota;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class QuotaPolicy {
    private List<Quota> a = new ArrayList();

    public List<Quota> getQuotas() {
        return this.a;
    }

    public void setQuotas(List<Quota> list) {
        this.a = list;
    }

    public void addQuota(Quota quota) {
        this.a.add(quota);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        QuotaPolicy quotaPolicy = (QuotaPolicy) obj;
        List<Quota> list = this.a;
        return list == null ? quotaPolicy.a == null : list.equals(quotaPolicy.a);
    }

    public int hashCode() {
        List<Quota> list = this.a;
        if (list != null) {
            return list.hashCode();
        }
        return 0;
    }
}
