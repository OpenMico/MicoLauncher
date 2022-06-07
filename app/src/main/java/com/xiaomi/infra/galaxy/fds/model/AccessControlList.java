package com.xiaomi.infra.galaxy.fds.model;

import com.google.common.base.Preconditions;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class AccessControlList {
    private final Map<GrantKey, Integer> a = new HashMap();

    /* loaded from: classes3.dex */
    public enum GrantType {
        USER,
        GROUP
    }

    /* loaded from: classes3.dex */
    public enum UserGroups {
        ALL_USERS,
        AUTHENTICATED_USERS
    }

    /* loaded from: classes3.dex */
    public enum Permission {
        READ(1),
        WRITE(2),
        READ_OBJECTS(4),
        SSO_WRITE(8),
        FULL_CONTROL(255);
        
        private final int value;

        Permission(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    /* loaded from: classes3.dex */
    public static class GrantKey implements Comparable<GrantKey> {
        protected final String granteeId;
        protected final GrantType type;

        public GrantKey(String str, GrantType grantType) {
            this.granteeId = str;
            this.type = grantType;
        }

        public int compareTo(GrantKey grantKey) {
            int compareTo = this.granteeId.compareTo(grantKey.granteeId);
            return compareTo == 0 ? this.type.compareTo(grantKey.type) : compareTo;
        }

        public String toString() {
            return this.granteeId + Constants.COLON_SEPARATOR + this.type.name();
        }

        public static GrantKey fromString(String str) {
            int lastIndexOf = str.lastIndexOf(Constants.COLON_SEPARATOR);
            Preconditions.checkState(lastIndexOf > 0);
            return new GrantKey(str.substring(0, lastIndexOf), GrantType.valueOf(str.substring(lastIndexOf + 1)));
        }

        public int hashCode() {
            return (this.granteeId.hashCode() ^ this.type.hashCode()) + this.granteeId.length();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof GrantKey)) {
                return false;
            }
            GrantKey grantKey = (GrantKey) obj;
            return this.granteeId.equals(grantKey.granteeId) && this.type.equals(grantKey.type);
        }
    }

    /* loaded from: classes3.dex */
    public static class Grant {
        private String a;
        private Permission b;
        private GrantType c;

        public Grant(GrantKey grantKey, Permission permission) {
            this(grantKey.granteeId, permission, grantKey.type);
        }

        public Grant(String str, Permission permission) {
            this(str, permission, GrantType.USER);
        }

        public Grant(String str, Permission permission, GrantType grantType) {
            this.a = str;
            this.b = permission;
            this.c = grantType;
        }

        public String getGranteeId() {
            return this.a;
        }

        public void setGranteeId(String str) {
            this.a = str;
        }

        public Permission getPermission() {
            return this.b;
        }

        public void setPermission(Permission permission) {
            this.b = permission;
        }

        public GrantType getType() {
            return this.c;
        }

        public void setType(GrantType grantType) {
            this.c = grantType;
        }

        public String toString() {
            return getGrantKey().toString() + Constants.COLON_SEPARATOR + this.b.name();
        }

        public static Grant fromString(String str) {
            int lastIndexOf = str.lastIndexOf(Constants.COLON_SEPARATOR);
            Preconditions.checkState(lastIndexOf > 0);
            return new Grant(GrantKey.fromString(str.substring(0, lastIndexOf)), Permission.valueOf(str.substring(lastIndexOf + 1)));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Grant grant = (Grant) obj;
            String str = this.a;
            if (str == null ? grant.a == null : str.equals(grant.a)) {
                return this.b == grant.b && this.c == grant.c;
            }
            return false;
        }

        public int hashCode() {
            String str = this.a;
            int i = 0;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            Permission permission = this.b;
            int hashCode2 = (hashCode + (permission != null ? permission.hashCode() : 0)) * 31;
            GrantType grantType = this.c;
            if (grantType != null) {
                i = grantType.hashCode();
            }
            return hashCode2 + i;
        }

        protected GrantKey getGrantKey() {
            return new GrantKey(this.a, this.c);
        }
    }

    public void addGrant(Grant grant) {
        GrantKey grantKey = grant.getGrantKey();
        Integer num = this.a.get(grantKey);
        if (num == null) {
            this.a.put(grantKey, Integer.valueOf(grant.getPermission().getValue()));
            return;
        }
        this.a.put(grantKey, Integer.valueOf(grant.getPermission().getValue() | num.intValue()));
    }

    public void addGrants(List<Grant> list) {
        for (Grant grant : list) {
            addGrant(grant);
        }
    }

    public boolean checkPermission(String str, GrantType grantType, Permission permission) {
        Integer num = this.a.get(new GrantKey(str, grantType));
        if (num == null) {
            return false;
        }
        return (num.intValue() & permission.getValue()) > 0;
    }

    public boolean checkUserReadPermission(String str) {
        return checkPermission(str, GrantType.USER, Permission.READ);
    }

    public boolean checkUserWritePermission(String str) {
        return checkPermission(str, GrantType.USER, Permission.WRITE);
    }

    public boolean checkGroupReadPermission(String str) {
        return checkPermission(str, GrantType.GROUP, Permission.READ);
    }

    public boolean checkGroupWritePermission(String str) {
        return checkPermission(str, GrantType.GROUP, Permission.WRITE);
    }

    public List<Grant> getGrantList() {
        LinkedList linkedList = new LinkedList();
        for (Map.Entry<GrantKey, Integer> entry : this.a.entrySet()) {
            GrantKey key = entry.getKey();
            if (entry.getValue().intValue() == Permission.FULL_CONTROL.getValue()) {
                linkedList.add(new Grant(key, Permission.FULL_CONTROL));
            } else {
                Permission[] values = Permission.values();
                for (Permission permission : values) {
                    if (permission.getValue() != Permission.FULL_CONTROL.getValue() && (permission.getValue() & entry.getValue().intValue()) > 0) {
                        linkedList.add(new Grant(key, permission));
                    }
                }
            }
        }
        return linkedList;
    }
}
