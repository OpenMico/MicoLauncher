package io.realm.rx;

import io.realm.ObjectChangeSet;
import io.realm.RealmModel;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class ObjectChange<E extends RealmModel> {
    private final E a;
    private final ObjectChangeSet b;

    public ObjectChange(E e, @Nullable ObjectChangeSet objectChangeSet) {
        this.a = e;
        this.b = objectChangeSet;
    }

    public E getObject() {
        return this.a;
    }

    @Nullable
    public ObjectChangeSet getChangeset() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ObjectChange objectChange = (ObjectChange) obj;
        if (!this.a.equals(objectChange.a)) {
            return false;
        }
        ObjectChangeSet objectChangeSet = this.b;
        return objectChangeSet != null ? objectChangeSet.equals(objectChange.b) : objectChange.b == null;
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        ObjectChangeSet objectChangeSet = this.b;
        return hashCode + (objectChangeSet != null ? objectChangeSet.hashCode() : 0);
    }

    public String toString() {
        return "ObjectChange{object=" + this.a + ", changeset=" + this.b + '}';
    }
}
