package io.realm;

/* loaded from: classes5.dex */
public interface ObjectChangeSet {
    String[] getChangedFields();

    boolean isDeleted();

    boolean isFieldChanged(String str);
}
