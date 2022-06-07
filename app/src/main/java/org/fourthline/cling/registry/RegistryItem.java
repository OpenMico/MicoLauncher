package org.fourthline.cling.registry;

import org.fourthline.cling.model.ExpirationDetails;

/* loaded from: classes5.dex */
class RegistryItem<K, I> {
    private ExpirationDetails expirationDetails;
    private I item;
    private K key;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RegistryItem(K k) {
        this.expirationDetails = new ExpirationDetails();
        this.key = k;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RegistryItem(K k, I i, int i2) {
        this.expirationDetails = new ExpirationDetails();
        this.key = k;
        this.item = i;
        this.expirationDetails = new ExpirationDetails(i2);
    }

    public K getKey() {
        return this.key;
    }

    public I getItem() {
        return this.item;
    }

    public ExpirationDetails getExpirationDetails() {
        return this.expirationDetails;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.key.equals(((RegistryItem) obj).key);
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ") " + getExpirationDetails() + " KEY: " + getKey() + " ITEM: " + getItem();
    }
}
