package org.fourthline.cling.support.model.container;

import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.StorageMedium;

/* loaded from: classes5.dex */
public class StorageSystem extends Container {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.container.storageSystem");

    public StorageSystem() {
        setClazz(CLASS);
    }

    public StorageSystem(Container container) {
        super(container);
    }

    public StorageSystem(String str, Container container, String str2, String str3, Integer num, Long l, Long l2, Long l3, Long l4, StorageMedium storageMedium) {
        this(str, container.getId(), str2, str3, num, l, l2, l3, l4, storageMedium);
    }

    public StorageSystem(String str, String str2, String str3, String str4, Integer num, Long l, Long l2, Long l3, Long l4, StorageMedium storageMedium) {
        super(str, str2, str3, str4, CLASS, num);
        if (l != null) {
            setStorageTotal(l);
        }
        if (l2 != null) {
            setStorageUsed(l2);
        }
        if (l3 != null) {
            setStorageFree(l3);
        }
        if (l4 != null) {
            setStorageMaxPartition(l4);
        }
        if (storageMedium != null) {
            setStorageMedium(storageMedium);
        }
    }

    public Long getStorageTotal() {
        return (Long) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_TOTAL.class);
    }

    public StorageSystem setStorageTotal(Long l) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_TOTAL(l));
        return this;
    }

    public Long getStorageUsed() {
        return (Long) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_USED.class);
    }

    public StorageSystem setStorageUsed(Long l) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_USED(l));
        return this;
    }

    public Long getStorageFree() {
        return (Long) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_FREE.class);
    }

    public StorageSystem setStorageFree(Long l) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_FREE(l));
        return this;
    }

    public Long getStorageMaxPartition() {
        return (Long) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_MAX_PARTITION.class);
    }

    public StorageSystem setStorageMaxPartition(Long l) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_MAX_PARTITION(l));
        return this;
    }

    public StorageMedium getStorageMedium() {
        return (StorageMedium) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_MEDIUM.class);
    }

    public StorageSystem setStorageMedium(StorageMedium storageMedium) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_MEDIUM(storageMedium));
        return this;
    }
}
