package io.realm;

import io.realm.internal.ManageableObject;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public interface RealmCollection<E> extends ManageableObject, Collection<E> {
    double average(String str);

    boolean contains(@Nullable Object obj);

    boolean deleteAllFromRealm();

    RealmCollection<E> freeze();

    boolean isLoaded();

    @Override // io.realm.internal.ManageableObject
    boolean isManaged();

    @Override // io.realm.internal.ManageableObject
    boolean isValid();

    boolean load();

    @Nullable
    Number max(String str);

    @Nullable
    Date maxDate(String str);

    @Nullable
    Number min(String str);

    @Nullable
    Date minDate(String str);

    Number sum(String str);

    RealmQuery<E> where();
}
