package org.fourthline.cling.support.model.container;

import java.net.URI;
import java.util.List;
import org.fourthline.cling.support.model.DIDLObject;

/* loaded from: classes5.dex */
public class MusicArtist extends PersonContainer {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.container.person.musicArtist");

    public MusicArtist() {
        setClazz(CLASS);
    }

    public MusicArtist(Container container) {
        super(container);
    }

    public MusicArtist(String str, Container container, String str2, String str3, Integer num) {
        this(str, container.getId(), str2, str3, num);
    }

    public MusicArtist(String str, String str2, String str3, String str4, Integer num) {
        super(str, str2, str3, str4, num);
        setClazz(CLASS);
    }

    public String getFirstGenre() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.GENRE.class);
    }

    public String[] getGenres() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.GENRE.class);
        return (String[]) propertyValues.toArray(new String[propertyValues.size()]);
    }

    public MusicArtist setGenres(String[] strArr) {
        removeProperties(DIDLObject.Property.UPNP.GENRE.class);
        for (String str : strArr) {
            addProperty(new DIDLObject.Property.UPNP.GENRE(str));
        }
        return this;
    }

    public URI getArtistDiscographyURI() {
        return (URI) getFirstPropertyValue(DIDLObject.Property.UPNP.ARTIST_DISCO_URI.class);
    }

    public MusicArtist setArtistDiscographyURI(URI uri) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.ARTIST_DISCO_URI(uri));
        return this;
    }
}
