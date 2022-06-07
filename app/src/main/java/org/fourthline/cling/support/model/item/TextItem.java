package org.fourthline.cling.support.model.item;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.Person;
import org.fourthline.cling.support.model.PersonWithRole;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.StorageMedium;
import org.fourthline.cling.support.model.container.Container;

/* loaded from: classes5.dex */
public class TextItem extends Item {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.item.textItem");

    public TextItem() {
        setClazz(CLASS);
    }

    public TextItem(Item item) {
        super(item);
    }

    public TextItem(String str, Container container, String str2, String str3, Res... resArr) {
        this(str, container.getId(), str2, str3, resArr);
    }

    public TextItem(String str, String str2, String str3, String str4, Res... resArr) {
        super(str, str2, str3, str4, CLASS);
        if (resArr != null) {
            getResources().addAll(Arrays.asList(resArr));
        }
    }

    public PersonWithRole getFirstAuthor() {
        return (PersonWithRole) getFirstPropertyValue(DIDLObject.Property.UPNP.AUTHOR.class);
    }

    public PersonWithRole[] getAuthors() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.AUTHOR.class);
        return (PersonWithRole[]) propertyValues.toArray(new PersonWithRole[propertyValues.size()]);
    }

    public TextItem setAuthors(PersonWithRole[] personWithRoleArr) {
        removeProperties(DIDLObject.Property.UPNP.AUTHOR.class);
        for (PersonWithRole personWithRole : personWithRoleArr) {
            addProperty(new DIDLObject.Property.UPNP.AUTHOR(personWithRole));
        }
        return this;
    }

    public String getDescription() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.DESCRIPTION.class);
    }

    public TextItem setDescription(String str) {
        replaceFirstProperty(new DIDLObject.Property.DC.DESCRIPTION(str));
        return this;
    }

    public String getLongDescription() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.LONG_DESCRIPTION.class);
    }

    public TextItem setLongDescription(String str) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.LONG_DESCRIPTION(str));
        return this;
    }

    public String getLanguage() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.LANGUAGE.class);
    }

    public TextItem setLanguage(String str) {
        replaceFirstProperty(new DIDLObject.Property.DC.LANGUAGE(str));
        return this;
    }

    public StorageMedium getStorageMedium() {
        return (StorageMedium) getFirstPropertyValue(DIDLObject.Property.UPNP.STORAGE_MEDIUM.class);
    }

    public TextItem setStorageMedium(StorageMedium storageMedium) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.STORAGE_MEDIUM(storageMedium));
        return this;
    }

    public String getDate() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.DATE.class);
    }

    public TextItem setDate(String str) {
        replaceFirstProperty(new DIDLObject.Property.DC.DATE(str));
        return this;
    }

    public URI getFirstRelation() {
        return (URI) getFirstPropertyValue(DIDLObject.Property.DC.RELATION.class);
    }

    public URI[] getRelations() {
        List propertyValues = getPropertyValues(DIDLObject.Property.DC.RELATION.class);
        return (URI[]) propertyValues.toArray(new URI[propertyValues.size()]);
    }

    public TextItem setRelations(URI[] uriArr) {
        removeProperties(DIDLObject.Property.DC.RELATION.class);
        for (URI uri : uriArr) {
            addProperty(new DIDLObject.Property.DC.RELATION(uri));
        }
        return this;
    }

    public String getFirstRights() {
        return (String) getFirstPropertyValue(DIDLObject.Property.DC.RIGHTS.class);
    }

    public String[] getRights() {
        List propertyValues = getPropertyValues(DIDLObject.Property.DC.RIGHTS.class);
        return (String[]) propertyValues.toArray(new String[propertyValues.size()]);
    }

    public TextItem setRights(String[] strArr) {
        removeProperties(DIDLObject.Property.DC.RIGHTS.class);
        for (String str : strArr) {
            addProperty(new DIDLObject.Property.DC.RIGHTS(str));
        }
        return this;
    }

    public String getRating() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.RATING.class);
    }

    public TextItem setRating(String str) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.RATING(str));
        return this;
    }

    public Person getFirstContributor() {
        return (Person) getFirstPropertyValue(DIDLObject.Property.DC.CONTRIBUTOR.class);
    }

    public Person[] getContributors() {
        List propertyValues = getPropertyValues(DIDLObject.Property.DC.CONTRIBUTOR.class);
        return (Person[]) propertyValues.toArray(new Person[propertyValues.size()]);
    }

    public TextItem setContributors(Person[] personArr) {
        removeProperties(DIDLObject.Property.DC.CONTRIBUTOR.class);
        for (Person person : personArr) {
            addProperty(new DIDLObject.Property.DC.CONTRIBUTOR(person));
        }
        return this;
    }

    public Person getFirstPublisher() {
        return (Person) getFirstPropertyValue(DIDLObject.Property.DC.PUBLISHER.class);
    }

    public Person[] getPublishers() {
        List propertyValues = getPropertyValues(DIDLObject.Property.DC.PUBLISHER.class);
        return (Person[]) propertyValues.toArray(new Person[propertyValues.size()]);
    }

    public TextItem setPublishers(Person[] personArr) {
        removeProperties(DIDLObject.Property.DC.PUBLISHER.class);
        for (Person person : personArr) {
            addProperty(new DIDLObject.Property.DC.PUBLISHER(person));
        }
        return this;
    }
}
