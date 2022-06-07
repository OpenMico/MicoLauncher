package io.realm;

import io.realm.RealmModel;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public interface RealmObjectChangeListener<T extends RealmModel> {
    void onChange(T t, @Nullable ObjectChangeSet objectChangeSet);
}
