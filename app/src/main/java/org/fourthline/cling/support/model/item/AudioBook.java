package org.fourthline.cling.support.model.item;

import java.util.List;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.Person;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.StorageMedium;
import org.fourthline.cling.support.model.container.Container;

/* loaded from: classes5.dex */
public class AudioBook extends AudioItem {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.item.audioItem.audioBook");

    public AudioBook() {
        setClazz(CLASS);
    }

    public AudioBook(Item item) {
        super(item);
    }

    public AudioBook(String str, Container container, String str2, String str3, Res... resArr) {
        this(str, container.getId(), str2, str3, (Person) null, (Person) null, (String) null, resArr);
    }

    public AudioBook(String str, Container container, String str2, String str3, String str4, String str5, String str6, Res... resArr) {
        this(str, container.getId(), str2, str3, new Person(str4), new Person(str5), str6, resArr);
    }

    public AudioBook(String str, String str2, String str3, String str4, Person person, Person person2, String str5, Res... resArr) {
        super(str, str2, str3, str4, resArr);
        setClazz(CLASS);
        if (person != null) {
            addProperty(new DIDLObject.Property.UPNP.PRODUCER(person));
        }
        if (person2 != null) {
            addProperty(new DIDLObject.Property.DC.CONTRIBUTOR(person2));
        }
        if (str5 != null) {
            setDate(str5);
        }
    }

    public StorageMedium getStorageMedium() {
        return (StorageMedium) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_MEDIUM.class);
    }

    public AudioBook setStorageMedium(StorageMedium storageMedium) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_MEDIUM(storageMedium));
        return this;
    }

    public Person getFirstProducer() {
        return (Person) getFirstPropertyValue(DIDLObject.Property.UPNP.PRODUCER.class);
    }

    public Person[] getProducers() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.PRODUCER.class);
        return (Person[]) propertyValues.toArray(new Person[propertyValues.size()]);
    }

    public AudioBook setProducers(Person[] personArr) {
        removeProperties(DIDLObject.Property.UPNP.PRODUCER.class);
        for (Person person : personArr) {
            addProperty(new DIDLObject.Property.UPNP.PRODUCER(person));
        }
        return this;
    }

    public Person getFirstContributor() {
        return (Person) getFirstPropertyValue(DIDLObject.Property.DC.CONTRIBUTOR.class);
    }

    public Person[] getContributors() {
        List propertyValues = getPropertyValues(DIDLObject.Property.DC.CONTRIBUTOR.class);
        return (Person[]) propertyValues.toArray(new Person[propertyValues.size()]);
    }

    public AudioBook setContributors(Person[] personArr) {
        removeProperties(DIDLObject.Property.DC.CONTRIBUTOR.class);
        for (Person person : personArr) {
            addProperty(new DIDLObject.Property.DC.CONTRIBUTOR(person));
        }
        return this;
    }

    public String getDate() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.DATE.class);
    }

    public AudioBook setDate(String str) {
        replaceFirstProperty(new DIDLObject.Property.DC.DATE(str));
        return this;
    }
}
