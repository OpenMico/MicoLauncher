package org.fourthline.cling.support.model.item;

import java.util.List;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.StorageMedium;
import org.fourthline.cling.support.model.container.Container;

/* loaded from: classes5.dex */
public class Movie extends VideoItem {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.item.videoItem.movie");

    public Movie() {
        setClazz(CLASS);
    }

    public Movie(Item item) {
        super(item);
    }

    public Movie(String str, Container container, String str2, String str3, Res... resArr) {
        this(str, container.getId(), str2, str3, resArr);
    }

    public Movie(String str, String str2, String str3, String str4, Res... resArr) {
        super(str, str2, str3, str4, resArr);
        setClazz(CLASS);
    }

    public StorageMedium getStorageMedium() {
        return (StorageMedium) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_MEDIUM.class);
    }

    public Movie setStorageMedium(StorageMedium storageMedium) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_MEDIUM(storageMedium));
        return this;
    }

    public Integer getDVDRegionCode() {
        return (Integer) getFirstPropertyValue(DIDLObject.Property.UPNP.DVD_REGION_CODE.class);
    }

    public Movie setDVDRegionCode(Integer num) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.DVD_REGION_CODE(num));
        return this;
    }

    public String getChannelName() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.CHANNEL_NAME.class);
    }

    public Movie setChannelName(String str) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.CHANNEL_NAME(str));
        return this;
    }

    public String getFirstScheduledStartTime() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.SCHEDULED_START_TIME.class);
    }

    public String[] getScheduledStartTimes() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.SCHEDULED_START_TIME.class);
        return (String[]) propertyValues.toArray(new String[propertyValues.size()]);
    }

    public Movie setScheduledStartTimes(String[] strArr) {
        removeProperties(DIDLObject.Property.UPNP.SCHEDULED_START_TIME.class);
        for (String str : strArr) {
            addProperty(new DIDLObject.Property.UPNP.SCHEDULED_START_TIME(str));
        }
        return this;
    }

    public String getFirstScheduledEndTime() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.SCHEDULED_END_TIME.class);
    }

    public String[] getScheduledEndTimes() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.SCHEDULED_END_TIME.class);
        return (String[]) propertyValues.toArray(new String[propertyValues.size()]);
    }

    public Movie setScheduledEndTimes(String[] strArr) {
        removeProperties(DIDLObject.Property.UPNP.SCHEDULED_END_TIME.class);
        for (String str : strArr) {
            addProperty(new DIDLObject.Property.UPNP.SCHEDULED_END_TIME(str));
        }
        return this;
    }
}
