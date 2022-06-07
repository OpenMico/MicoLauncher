package io.realm.sync.permissions;

import io.realm.sync.permissions.Permission;

/* loaded from: classes5.dex */
public class Permission$Builder {
    private Role a;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private boolean e = false;
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;

    public Permission$Builder(Role role) {
        this.a = role;
    }

    public Permission$Builder allPrivileges() {
        this.b = true;
        this.c = true;
        this.d = true;
        this.e = true;
        this.f = true;
        this.g = true;
        this.h = true;
        return this;
    }

    public Permission$Builder noPrivileges() {
        this.b = false;
        this.c = false;
        this.d = false;
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = false;
        return this;
    }

    public Permission$Builder canRead(boolean z) {
        this.b = z;
        return this;
    }

    public Permission$Builder canUpdate(boolean z) {
        this.c = z;
        return this;
    }

    public Permission$Builder canDelete(boolean z) {
        this.d = z;
        return this;
    }

    public Permission$Builder canSetPermissions(boolean z) {
        this.e = z;
        return this;
    }

    public Permission$Builder canQuery(boolean z) {
        this.f = z;
        return this;
    }

    public Permission$Builder canCreate(boolean z) {
        this.g = z;
        return this;
    }

    public Permission$Builder canModifySchema(boolean z) {
        this.h = z;
        return this;
    }

    public Permission build() {
        return new Permission(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, (Permission.1) null);
    }
}
