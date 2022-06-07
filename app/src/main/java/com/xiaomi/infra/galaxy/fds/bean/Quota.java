package com.xiaomi.infra.galaxy.fds.bean;

import com.xiaomi.infra.galaxy.fds.Action;

/* loaded from: classes3.dex */
public class Quota {
    private QuotaType a;
    private Action b;
    private long c;

    /* loaded from: classes3.dex */
    public enum QuotaType {
        QPS,
        Throughput
    }

    public Quota() {
    }

    public Quota(QuotaType quotaType, Action action, long j) {
        this.a = quotaType;
        this.b = action;
        this.c = j;
    }

    public QuotaType getType() {
        return this.a;
    }

    public void setType(QuotaType quotaType) {
        this.a = quotaType;
    }

    public Action getAction() {
        return this.b;
    }

    public void setAction(Action action) {
        this.b = action;
    }

    public long getValue() {
        return this.c;
    }

    public void setValue(long j) {
        this.c = j;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Quota quota = (Quota) obj;
        return this.c == quota.c && this.b == quota.b && this.a == quota.a;
    }

    public int hashCode() {
        QuotaType quotaType = this.a;
        int i = 0;
        int hashCode = (quotaType != null ? quotaType.hashCode() : 0) * 31;
        Action action = this.b;
        if (action != null) {
            i = action.hashCode();
        }
        long j = this.c;
        return ((hashCode + i) * 31) + ((int) (j ^ (j >>> 32)));
    }
}
