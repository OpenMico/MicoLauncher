package org.fourthline.cling.support.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.w3c.dom.Element;

/* loaded from: classes5.dex */
public abstract class DIDLObject {
    protected Class clazz;
    protected String creator;
    protected List<DescMeta> descMetadata;
    protected String id;
    protected String parentID;
    protected List<Property> properties;
    protected List<Res> resources;
    protected boolean restricted;
    protected String title;
    protected WriteStatus writeStatus;

    /* loaded from: classes5.dex */
    public static abstract class Property<V> {
        private final List<Property<DIDLAttribute>> attributes;
        private final String descriptorName;
        private V value;

        /* loaded from: classes5.dex */
        public interface NAMESPACE {
        }

        protected Property() {
            this(null, null);
        }

        protected Property(String str) {
            this(null, str);
        }

        protected Property(V v, String str) {
            this.attributes = new ArrayList();
            this.value = v;
            this.descriptorName = str == null ? getClass().getSimpleName().toLowerCase(Locale.ROOT).replace("didlobject$property$upnp$", "") : str;
        }

        protected Property(V v, String str, List<Property<DIDLAttribute>> list) {
            this.attributes = new ArrayList();
            this.value = v;
            this.descriptorName = str == null ? getClass().getSimpleName().toLowerCase(Locale.ROOT).replace("didlobject$property$upnp$", "") : str;
            this.attributes.addAll(list);
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(V v) {
            this.value = v;
        }

        public String getDescriptorName() {
            return this.descriptorName;
        }

        public void setOnElement(Element element) {
            element.setTextContent(toString());
            for (Property<DIDLAttribute> property : this.attributes) {
                String namespaceURI = property.getValue().getNamespaceURI();
                element.setAttributeNS(namespaceURI, property.getValue().getPrefix() + ':' + property.getDescriptorName(), property.getValue().getValue());
            }
        }

        public void addAttribute(Property<DIDLAttribute> property) {
            this.attributes.add(property);
        }

        public void removeAttribute(Property<DIDLAttribute> property) {
            this.attributes.remove(property);
        }

        public void removeAttribute(String str) {
            for (Property<DIDLAttribute> property : this.attributes) {
                if (property.getDescriptorName().equals(str)) {
                    removeAttribute(property);
                    return;
                }
            }
        }

        public Property<DIDLAttribute> getAttribute(String str) {
            for (Property<DIDLAttribute> property : this.attributes) {
                if (property.getDescriptorName().equals(str)) {
                    return property;
                }
            }
            return null;
        }

        public String toString() {
            return getValue() != null ? getValue().toString() : "";
        }

        /* loaded from: classes5.dex */
        public static class PropertyPersonWithRole extends Property<PersonWithRole> {
            public PropertyPersonWithRole() {
            }

            public PropertyPersonWithRole(String str) {
                super(str);
            }

            public PropertyPersonWithRole(PersonWithRole personWithRole, String str) {
                super(personWithRole, str);
            }

            @Override // org.fourthline.cling.support.model.DIDLObject.Property
            public void setOnElement(Element element) {
                if (getValue() != null) {
                    getValue().setOnElement(element);
                }
            }
        }

        /* loaded from: classes5.dex */
        public static class DC {

            /* loaded from: classes5.dex */
            public interface NAMESPACE extends NAMESPACE {
                public static final String URI = "http://purl.org/dc/elements/1.1/";
            }

            /* loaded from: classes5.dex */
            public static class DESCRIPTION extends Property<String> implements NAMESPACE {
                public DESCRIPTION() {
                }

                public DESCRIPTION(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class PUBLISHER extends Property<Person> implements NAMESPACE {
                public PUBLISHER() {
                }

                public PUBLISHER(Person person) {
                    super(person, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class CONTRIBUTOR extends Property<Person> implements NAMESPACE {
                public CONTRIBUTOR() {
                }

                public CONTRIBUTOR(Person person) {
                    super(person, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class DATE extends Property<String> implements NAMESPACE {
                public DATE() {
                }

                public DATE(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class LANGUAGE extends Property<String> implements NAMESPACE {
                public LANGUAGE() {
                }

                public LANGUAGE(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class RELATION extends Property<URI> implements NAMESPACE {
                public RELATION() {
                }

                public RELATION(URI uri) {
                    super(uri, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class RIGHTS extends Property<String> implements NAMESPACE {
                public RIGHTS() {
                }

                public RIGHTS(String str) {
                    super(str, null);
                }
            }
        }

        /* loaded from: classes5.dex */
        public static abstract class SEC {

            /* loaded from: classes5.dex */
            public interface NAMESPACE extends NAMESPACE {
                public static final String URI = "http://www.sec.co.kr/";
            }

            /* loaded from: classes5.dex */
            public static class CAPTIONINFOEX extends Property<URI> implements NAMESPACE {
                public CAPTIONINFOEX() {
                    this(null);
                }

                public CAPTIONINFOEX(URI uri) {
                    super(uri, "CaptionInfoEx");
                }

                public CAPTIONINFOEX(URI uri, List<Property<DIDLAttribute>> list) {
                    super(uri, "CaptionInfoEx", list);
                }
            }

            /* loaded from: classes5.dex */
            public static class CAPTIONINFO extends Property<URI> implements NAMESPACE {
                public CAPTIONINFO() {
                    this(null);
                }

                public CAPTIONINFO(URI uri) {
                    super(uri, "CaptionInfo");
                }

                public CAPTIONINFO(URI uri, List<Property<DIDLAttribute>> list) {
                    super(uri, "CaptionInfo", list);
                }
            }

            /* loaded from: classes5.dex */
            public static class TYPE extends Property<DIDLAttribute> implements NAMESPACE {
                public TYPE() {
                    this(null);
                }

                public TYPE(DIDLAttribute dIDLAttribute) {
                    super(dIDLAttribute, "type");
                }
            }
        }

        /* loaded from: classes5.dex */
        public static abstract class UPNP {

            /* loaded from: classes5.dex */
            public interface NAMESPACE extends NAMESPACE {
                public static final String URI = "urn:schemas-upnp-org:metadata-1-0/upnp/";
            }

            /* loaded from: classes5.dex */
            public static class ARTIST extends PropertyPersonWithRole implements NAMESPACE {
                public ARTIST() {
                }

                public ARTIST(PersonWithRole personWithRole) {
                    super(personWithRole, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class ACTOR extends PropertyPersonWithRole implements NAMESPACE {
                public ACTOR() {
                }

                public ACTOR(PersonWithRole personWithRole) {
                    super(personWithRole, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class AUTHOR extends PropertyPersonWithRole implements NAMESPACE {
                public AUTHOR() {
                }

                public AUTHOR(PersonWithRole personWithRole) {
                    super(personWithRole, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class PRODUCER extends Property<Person> implements NAMESPACE {
                public PRODUCER() {
                }

                public PRODUCER(Person person) {
                    super(person, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class DIRECTOR extends Property<Person> implements NAMESPACE {
                public DIRECTOR() {
                }

                public DIRECTOR(Person person) {
                    super(person, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class GENRE extends Property<String> implements NAMESPACE {
                public GENRE() {
                }

                public GENRE(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class ALBUM extends Property<String> implements NAMESPACE {
                public ALBUM() {
                }

                public ALBUM(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class PLAYLIST extends Property<String> implements NAMESPACE {
                public PLAYLIST() {
                }

                public PLAYLIST(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class REGION extends Property<String> implements NAMESPACE {
                public REGION() {
                }

                public REGION(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class RATING extends Property<String> implements NAMESPACE {
                public RATING() {
                }

                public RATING(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class TOC extends Property<String> implements NAMESPACE {
                public TOC() {
                }

                public TOC(String str) {
                    super(str, null);
                }
            }

            /* loaded from: classes5.dex */
            public static class ALBUM_ART_URI extends Property<URI> implements NAMESPACE {
                public ALBUM_ART_URI() {
                    this(null);
                }

                public ALBUM_ART_URI(URI uri) {
                    super(uri, "albumArtURI");
                }

                public ALBUM_ART_URI(URI uri, List<Property<DIDLAttribute>> list) {
                    super(uri, "albumArtURI", list);
                }
            }

            /* loaded from: classes5.dex */
            public static class ARTIST_DISCO_URI extends Property<URI> implements NAMESPACE {
                public ARTIST_DISCO_URI() {
                    this(null);
                }

                public ARTIST_DISCO_URI(URI uri) {
                    super(uri, "artistDiscographyURI");
                }
            }

            /* loaded from: classes5.dex */
            public static class LYRICS_URI extends Property<URI> implements NAMESPACE {
                public LYRICS_URI() {
                    this(null);
                }

                public LYRICS_URI(URI uri) {
                    super(uri, "lyricsURI");
                }
            }

            /* loaded from: classes5.dex */
            public static class STORAGE_TOTAL extends Property<Long> implements NAMESPACE {
                public STORAGE_TOTAL() {
                    this(null);
                }

                public STORAGE_TOTAL(Long l) {
                    super(l, "storageTotal");
                }
            }

            /* loaded from: classes5.dex */
            public static class STORAGE_USED extends Property<Long> implements NAMESPACE {
                public STORAGE_USED() {
                    this(null);
                }

                public STORAGE_USED(Long l) {
                    super(l, "storageUsed");
                }
            }

            /* loaded from: classes5.dex */
            public static class STORAGE_FREE extends Property<Long> implements NAMESPACE {
                public STORAGE_FREE() {
                    this(null);
                }

                public STORAGE_FREE(Long l) {
                    super(l, "storageFree");
                }
            }

            /* loaded from: classes5.dex */
            public static class STORAGE_MAX_PARTITION extends Property<Long> implements NAMESPACE {
                public STORAGE_MAX_PARTITION() {
                    this(null);
                }

                public STORAGE_MAX_PARTITION(Long l) {
                    super(l, "storageMaxPartition");
                }
            }

            /* loaded from: classes5.dex */
            public static class STORAGE_MEDIUM extends Property<StorageMedium> implements NAMESPACE {
                public STORAGE_MEDIUM() {
                    this(null);
                }

                public STORAGE_MEDIUM(StorageMedium storageMedium) {
                    super(storageMedium, "storageMedium");
                }
            }

            /* loaded from: classes5.dex */
            public static class LONG_DESCRIPTION extends Property<String> implements NAMESPACE {
                public LONG_DESCRIPTION() {
                    this(null);
                }

                public LONG_DESCRIPTION(String str) {
                    super(str, "longDescription");
                }
            }

            /* loaded from: classes5.dex */
            public static class ICON extends Property<URI> implements NAMESPACE {
                public ICON() {
                    this(null);
                }

                public ICON(URI uri) {
                    super(uri, "icon");
                }
            }

            /* loaded from: classes5.dex */
            public static class RADIO_CALL_SIGN extends Property<String> implements NAMESPACE {
                public RADIO_CALL_SIGN() {
                    this(null);
                }

                public RADIO_CALL_SIGN(String str) {
                    super(str, "radioCallSign");
                }
            }

            /* loaded from: classes5.dex */
            public static class RADIO_STATION_ID extends Property<String> implements NAMESPACE {
                public RADIO_STATION_ID() {
                    this(null);
                }

                public RADIO_STATION_ID(String str) {
                    super(str, "radioStationID");
                }
            }

            /* loaded from: classes5.dex */
            public static class RADIO_BAND extends Property<String> implements NAMESPACE {
                public RADIO_BAND() {
                    this(null);
                }

                public RADIO_BAND(String str) {
                    super(str, "radioBand");
                }
            }

            /* loaded from: classes5.dex */
            public static class CHANNEL_NR extends Property<Integer> implements NAMESPACE {
                public CHANNEL_NR() {
                    this(null);
                }

                public CHANNEL_NR(Integer num) {
                    super(num, "channelNr");
                }
            }

            /* loaded from: classes5.dex */
            public static class CHANNEL_NAME extends Property<String> implements NAMESPACE {
                public CHANNEL_NAME() {
                    this(null);
                }

                public CHANNEL_NAME(String str) {
                    super(str, "channelName");
                }
            }

            /* loaded from: classes5.dex */
            public static class SCHEDULED_START_TIME extends Property<String> implements NAMESPACE {
                public SCHEDULED_START_TIME() {
                    this(null);
                }

                public SCHEDULED_START_TIME(String str) {
                    super(str, "scheduledStartTime");
                }
            }

            /* loaded from: classes5.dex */
            public static class SCHEDULED_END_TIME extends Property<String> implements NAMESPACE {
                public SCHEDULED_END_TIME() {
                    this(null);
                }

                public SCHEDULED_END_TIME(String str) {
                    super(str, "scheduledEndTime");
                }
            }

            /* loaded from: classes5.dex */
            public static class DVD_REGION_CODE extends Property<Integer> implements NAMESPACE {
                public DVD_REGION_CODE() {
                    this(null);
                }

                public DVD_REGION_CODE(Integer num) {
                    super(num, "DVDRegionCode");
                }
            }

            /* loaded from: classes5.dex */
            public static class ORIGINAL_TRACK_NUMBER extends Property<Integer> implements NAMESPACE {
                public ORIGINAL_TRACK_NUMBER() {
                    this(null);
                }

                public ORIGINAL_TRACK_NUMBER(Integer num) {
                    super(num, "originalTrackNumber");
                }
            }

            /* loaded from: classes5.dex */
            public static class USER_ANNOTATION extends Property<String> implements NAMESPACE {
                public USER_ANNOTATION() {
                    this(null);
                }

                public USER_ANNOTATION(String str) {
                    super(str, "userAnnotation");
                }
            }
        }

        /* loaded from: classes5.dex */
        public static abstract class DLNA {

            /* loaded from: classes5.dex */
            public interface NAMESPACE extends NAMESPACE {
                public static final String URI = "urn:schemas-dlna-org:metadata-1-0/";
            }

            /* loaded from: classes5.dex */
            public static class PROFILE_ID extends Property<DIDLAttribute> implements NAMESPACE {
                public PROFILE_ID() {
                    this(null);
                }

                public PROFILE_ID(DIDLAttribute dIDLAttribute) {
                    super(dIDLAttribute, "profileID");
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class Class {
        protected String friendlyName;
        protected boolean includeDerived;
        protected String value;

        public Class() {
        }

        public Class(String str) {
            this.value = str;
        }

        public Class(String str, String str2) {
            this.value = str;
            this.friendlyName = str2;
        }

        public Class(String str, String str2, boolean z) {
            this.value = str;
            this.friendlyName = str2;
            this.includeDerived = z;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String str) {
            this.value = str;
        }

        public String getFriendlyName() {
            return this.friendlyName;
        }

        public void setFriendlyName(String str) {
            this.friendlyName = str;
        }

        public boolean isIncludeDerived() {
            return this.includeDerived;
        }

        public void setIncludeDerived(boolean z) {
            this.includeDerived = z;
        }

        public boolean equals(DIDLObject dIDLObject) {
            return getValue().equals(dIDLObject.getClazz().getValue());
        }
    }

    public DIDLObject() {
        this.restricted = true;
        this.resources = new ArrayList();
        this.properties = new ArrayList();
        this.descMetadata = new ArrayList();
    }

    public DIDLObject(DIDLObject dIDLObject) {
        this(dIDLObject.getId(), dIDLObject.getParentID(), dIDLObject.getTitle(), dIDLObject.getCreator(), dIDLObject.isRestricted(), dIDLObject.getWriteStatus(), dIDLObject.getClazz(), dIDLObject.getResources(), dIDLObject.getProperties(), dIDLObject.getDescMetadata());
    }

    public DIDLObject(String str, String str2, String str3, String str4, boolean z, WriteStatus writeStatus, Class r8, List<Res> list, List<Property> list2, List<DescMeta> list3) {
        this.restricted = true;
        this.resources = new ArrayList();
        this.properties = new ArrayList();
        this.descMetadata = new ArrayList();
        this.id = str;
        this.parentID = str2;
        this.title = str3;
        this.creator = str4;
        this.restricted = z;
        this.writeStatus = writeStatus;
        this.clazz = r8;
        this.resources = list;
        this.properties = list2;
        this.descMetadata = list3;
    }

    public String getId() {
        return this.id;
    }

    public DIDLObject setId(String str) {
        this.id = str;
        return this;
    }

    public String getParentID() {
        return this.parentID;
    }

    public DIDLObject setParentID(String str) {
        this.parentID = str;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public DIDLObject setTitle(String str) {
        this.title = str;
        return this;
    }

    public String getCreator() {
        return this.creator;
    }

    public DIDLObject setCreator(String str) {
        this.creator = str;
        return this;
    }

    public boolean isRestricted() {
        return this.restricted;
    }

    public DIDLObject setRestricted(boolean z) {
        this.restricted = z;
        return this;
    }

    public WriteStatus getWriteStatus() {
        return this.writeStatus;
    }

    public DIDLObject setWriteStatus(WriteStatus writeStatus) {
        this.writeStatus = writeStatus;
        return this;
    }

    public Res getFirstResource() {
        if (getResources().size() > 0) {
            return getResources().get(0);
        }
        return null;
    }

    public List<Res> getResources() {
        return this.resources;
    }

    public DIDLObject setResources(List<Res> list) {
        this.resources = list;
        return this;
    }

    public DIDLObject addResource(Res res) {
        getResources().add(res);
        return this;
    }

    public Class getClazz() {
        return this.clazz;
    }

    public DIDLObject setClazz(Class r1) {
        this.clazz = r1;
        return this;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public DIDLObject setProperties(List<Property> list) {
        this.properties = list;
        return this;
    }

    public DIDLObject addProperty(Property property) {
        if (property == null) {
            return this;
        }
        getProperties().add(property);
        return this;
    }

    public DIDLObject replaceFirstProperty(Property property) {
        if (property == null) {
            return this;
        }
        Iterator<Property> it = getProperties().iterator();
        while (it.hasNext()) {
            if (it.next().getClass().isAssignableFrom(property.getClass())) {
                it.remove();
            }
        }
        addProperty(property);
        return this;
    }

    public DIDLObject replaceProperties(Class<? extends Property> cls, Property[] propertyArr) {
        if (propertyArr.length == 0) {
            return this;
        }
        removeProperties(cls);
        return addProperties(propertyArr);
    }

    public DIDLObject addProperties(Property[] propertyArr) {
        if (propertyArr == null) {
            return this;
        }
        for (Property property : propertyArr) {
            addProperty(property);
        }
        return this;
    }

    public DIDLObject removeProperties(Class<? extends Property> cls) {
        Iterator<Property> it = getProperties().iterator();
        while (it.hasNext()) {
            if (cls.isInstance(it.next())) {
                it.remove();
            }
        }
        return this;
    }

    public boolean hasProperty(Class<? extends Property> cls) {
        for (Property property : getProperties()) {
            if (cls.isInstance(property)) {
                return true;
            }
        }
        return false;
    }

    public <V> Property<V> getFirstProperty(Class<? extends Property<V>> cls) {
        for (Property<V> property : getProperties()) {
            if (cls.isInstance(property)) {
                return property;
            }
        }
        return null;
    }

    public <V> Property<V> getLastProperty(Class<? extends Property<V>> cls) {
        Property<V> property = null;
        for (Property<V> property2 : getProperties()) {
            if (cls.isInstance(property2)) {
                property = property2;
            }
        }
        return property;
    }

    public <V> Property<V>[] getProperties(Class<? extends Property<V>> cls) {
        ArrayList arrayList = new ArrayList();
        for (Property property : getProperties()) {
            if (cls.isInstance(property)) {
                arrayList.add(property);
            }
        }
        return (Property[]) arrayList.toArray(new Property[arrayList.size()]);
    }

    public <V> Property<V>[] getPropertiesByNamespace(Class<? extends Property.NAMESPACE> cls) {
        ArrayList arrayList = new ArrayList();
        for (Property property : getProperties()) {
            if (cls.isInstance(property)) {
                arrayList.add(property);
            }
        }
        return (Property[]) arrayList.toArray(new Property[arrayList.size()]);
    }

    public <V> V getFirstPropertyValue(Class<? extends Property<V>> cls) {
        Property<V> firstProperty = getFirstProperty(cls);
        if (firstProperty == null) {
            return null;
        }
        return firstProperty.getValue();
    }

    public <V> List<V> getPropertyValues(Class<? extends Property<V>> cls) {
        ArrayList arrayList = new ArrayList();
        for (Property<V> property : getProperties(cls)) {
            arrayList.add(property.getValue());
        }
        return arrayList;
    }

    public List<DescMeta> getDescMetadata() {
        return this.descMetadata;
    }

    public void setDescMetadata(List<DescMeta> list) {
        this.descMetadata = list;
    }

    public DIDLObject addDescMetadata(DescMeta descMeta) {
        getDescMetadata().add(descMeta);
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.id.equals(((DIDLObject) obj).id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }
}
