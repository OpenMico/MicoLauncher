package org.eclipse.jetty.security;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes5.dex */
public class RoleInfo {
    private boolean _checked;
    private boolean _forbidden;
    private boolean _isAnyRole;
    private final Set<String> _roles = new CopyOnWriteArraySet();
    private UserDataConstraint _userDataConstraint;

    public boolean isChecked() {
        return this._checked;
    }

    public void setChecked(boolean z) {
        this._checked = z;
        if (!z) {
            this._forbidden = false;
            this._roles.clear();
            this._isAnyRole = false;
        }
    }

    public boolean isForbidden() {
        return this._forbidden;
    }

    public void setForbidden(boolean z) {
        this._forbidden = z;
        if (z) {
            this._checked = true;
            this._userDataConstraint = null;
            this._isAnyRole = false;
            this._roles.clear();
        }
    }

    public boolean isAnyRole() {
        return this._isAnyRole;
    }

    public void setAnyRole(boolean z) {
        this._isAnyRole = z;
        if (z) {
            this._checked = true;
            this._roles.clear();
        }
    }

    public UserDataConstraint getUserDataConstraint() {
        return this._userDataConstraint;
    }

    public void setUserDataConstraint(UserDataConstraint userDataConstraint) {
        if (userDataConstraint != null) {
            UserDataConstraint userDataConstraint2 = this._userDataConstraint;
            if (userDataConstraint2 == null) {
                this._userDataConstraint = userDataConstraint;
            } else {
                this._userDataConstraint = userDataConstraint2.combine(userDataConstraint);
            }
        } else {
            throw new NullPointerException("Null UserDataConstraint");
        }
    }

    public Set<String> getRoles() {
        return this._roles;
    }

    public void addRole(String str) {
        this._roles.add(str);
    }

    public void combine(RoleInfo roleInfo) {
        if (roleInfo._forbidden) {
            setForbidden(true);
        } else if (!roleInfo._checked) {
            setChecked(true);
        } else if (roleInfo._isAnyRole) {
            setAnyRole(true);
        } else if (!this._isAnyRole) {
            for (String str : roleInfo._roles) {
                this._roles.add(str);
            }
        }
        setUserDataConstraint(roleInfo._userDataConstraint);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{RoleInfo");
        sb.append(this._forbidden ? ",F" : "");
        sb.append(this._checked ? ",C" : "");
        sb.append(this._isAnyRole ? ",*" : this._roles);
        sb.append("}");
        return sb.toString();
    }
}
