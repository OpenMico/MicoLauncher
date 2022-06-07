package com.xiaomi.infra.galaxy.fds.bean;

import com.xiaomi.infra.galaxy.fds.model.AccessControlList;

/* loaded from: classes3.dex */
public class GrantBean {
    private GranteeBean a;
    private AccessControlList.Permission b;
    private AccessControlList.GrantType c;

    public GrantBean() {
    }

    public GrantBean(GranteeBean granteeBean, AccessControlList.Permission permission) {
        this(granteeBean, permission, AccessControlList.GrantType.USER);
    }

    public GrantBean(GranteeBean granteeBean, AccessControlList.Permission permission, AccessControlList.GrantType grantType) {
        this.a = granteeBean;
        this.b = permission;
        this.c = grantType;
    }

    public GranteeBean getGrantee() {
        return this.a;
    }

    public void setGrantee(GranteeBean granteeBean) {
        this.a = granteeBean;
    }

    public AccessControlList.Permission getPermission() {
        return this.b;
    }

    public void setPermission(AccessControlList.Permission permission) {
        this.b = permission;
    }

    public AccessControlList.GrantType getType() {
        return this.c;
    }

    public void setType(AccessControlList.GrantType grantType) {
        this.c = grantType;
    }
}
