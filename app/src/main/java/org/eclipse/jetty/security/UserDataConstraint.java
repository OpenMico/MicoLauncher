package org.eclipse.jetty.security;

/* loaded from: classes5.dex */
public enum UserDataConstraint {
    None,
    Integral,
    Confidential;

    public static UserDataConstraint get(int i) {
        if (i < -1 || i > 2) {
            throw new IllegalArgumentException("Expected -1, 0, 1, or 2, not: " + i);
        } else if (i == -1) {
            return None;
        } else {
            return values()[i];
        }
    }

    public UserDataConstraint combine(UserDataConstraint userDataConstraint) {
        return compareTo(userDataConstraint) < 0 ? this : userDataConstraint;
    }
}
