package org.fourthline.cling.support.model.container;

import java.util.List;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.Person;
import org.fourthline.cling.support.model.PersonWithRole;
import org.fourthline.cling.support.model.StorageMedium;

/* loaded from: classes5.dex */
public class PlaylistContainer extends Container {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.container.playlistContainer");

    public PlaylistContainer() {
        setClazz(CLASS);
    }

    public PlaylistContainer(Container container) {
        super(container);
    }

    public PlaylistContainer(String str, Container container, String str2, String str3, Integer num) {
        this(str, container.getId(), str2, str3, num);
    }

    public PlaylistContainer(String str, String str2, String str3, String str4, Integer num) {
        super(str, str2, str3, str4, CLASS, num);
    }

    public PersonWithRole getFirstArtist() {
        return (PersonWithRole) getFirstPropertyValue(DIDLObject.Property.UPNP.ARTIST.class);
    }

    public PersonWithRole[] getArtists() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.ARTIST.class);
        return (PersonWithRole[]) propertyValues.toArray(new PersonWithRole[propertyValues.size()]);
    }

    public PlaylistContainer setArtists(PersonWithRole[] personWithRoleArr) {
        removeProperties(DIDLObject.Property.UPNP.ARTIST.class);
        for (PersonWithRole personWithRole : personWithRoleArr) {
            addProperty(new DIDLObject.Property.UPNP.ARTIST(personWithRole));
        }
        return this;
    }

    public String getFirstGenre() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.GENRE.class);
    }

    public String[] getGenres() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.GENRE.class);
        return (String[]) propertyValues.toArray(new String[propertyValues.size()]);
    }

    public PlaylistContainer setGenres(String[] strArr) {
        removeProperties(DIDLObject.Property.UPNP.GENRE.class);
        for (String str : strArr) {
            addProperty(new DIDLObject.Property.UPNP.GENRE(str));
        }
        return this;
    }

    public String getDescription() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.DESCRIPTION.class);
    }

    public PlaylistContainer setDescription(String str) {
        replaceFirstProperty(new DIDLObject.Property.DC.DESCRIPTION(str));
        return this;
    }

    public String getLongDescription() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.LONG_DESCRIPTION.class);
    }

    public PlaylistContainer setLongDescription(String str) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.LONG_DESCRIPTION(str));
        return this;
    }

    public Person getFirstProducer() {
        return (Person) getFirstPropertyValue(DIDLObject.Property.UPNP.PRODUCER.class);
    }

    public Person[] getProducers() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.PRODUCER.class);
        return (Person[]) propertyValues.toArray(new Person[propertyValues.size()]);
    }

    public PlaylistContainer setProducers(Person[] personArr) {
        removeProperties(DIDLObject.Property.UPNP.PRODUCER.class);
        for (Person person : personArr) {
            addProperty(new DIDLObject.Property.UPNP.PRODUCER(person));
        }
        return this;
    }

    public StorageMedium getStorageMedium() {
        return (StorageMedium) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_MEDIUM.class);
    }

    public PlaylistContainer setStorageMedium(StorageMedium storageMedium) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_MEDIUM(storageMedium));
        return this;
    }

    public String getDate() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.DATE.class);
    }

    public PlaylistContainer setDate(String str) {
        replaceFirstProperty(new DIDLObject.Property.DC.DATE(str));
        return this;
    }

    public String getFirstRights() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.RIGHTS.class);
    }

    public String[] getRights() {
        List propertyValues = getPropertyValues(DIDLObject.Property.DC.RIGHTS.class);
        return (String[]) propertyValues.toArray(new String[propertyValues.size()]);
    }

    public PlaylistContainer setRights(String[] strArr) {
        removeProperties(DIDLObject.Property.DC.RIGHTS.class);
        for (String str : strArr) {
            addProperty(new DIDLObject.Property.DC.RIGHTS(str));
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

    public PlaylistContainer setContributors(Person[] personArr) {
        removeProperties(DIDLObject.Property.DC.CONTRIBUTOR.class);
        for (Person person : personArr) {
            addProperty(new DIDLObject.Property.DC.CONTRIBUTOR(person));
        }
        return this;
    }

    public String getLanguage() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.LANGUAGE.class);
    }

    public PlaylistContainer setLanguage(String str) {
        replaceFirstProperty(new DIDLObject.Property.DC.LANGUAGE(str));
        return this;
    }
}
