package org.fourthline.cling.support.model.container;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.Person;
import org.fourthline.cling.support.model.PersonWithRole;
import org.fourthline.cling.support.model.item.Item;
import org.fourthline.cling.support.model.item.MusicTrack;

/* loaded from: classes5.dex */
public class MusicAlbum extends Album {
    public static final DIDLObject.Class CLASS = new DIDLObject.Class("object.container.album.musicAlbum");

    public MusicAlbum() {
        setClazz(CLASS);
    }

    public MusicAlbum(Container container) {
        super(container);
    }

    public MusicAlbum(String str, Container container, String str2, String str3, Integer num) {
        this(str, container.getId(), str2, str3, num, new ArrayList());
    }

    public MusicAlbum(String str, Container container, String str2, String str3, Integer num, List<MusicTrack> list) {
        this(str, container.getId(), str2, str3, num, list);
    }

    public MusicAlbum(String str, String str2, String str3, String str4, Integer num) {
        this(str, str2, str3, str4, num, new ArrayList());
    }

    public MusicAlbum(String str, String str2, String str3, String str4, Integer num, List<MusicTrack> list) {
        super(str, str2, str3, str4, num);
        setClazz(CLASS);
        addMusicTracks(list);
    }

    public PersonWithRole getFirstArtist() {
        return (PersonWithRole) getFirstPropertyValue(DIDLObject.Property.UPNP.ARTIST.class);
    }

    public PersonWithRole[] getArtists() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.ARTIST.class);
        return (PersonWithRole[]) propertyValues.toArray(new PersonWithRole[propertyValues.size()]);
    }

    public MusicAlbum setArtists(PersonWithRole[] personWithRoleArr) {
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

    public MusicAlbum setGenres(String[] strArr) {
        removeProperties(DIDLObject.Property.UPNP.GENRE.class);
        for (String str : strArr) {
            addProperty(new DIDLObject.Property.UPNP.GENRE(str));
        }
        return this;
    }

    public Person getFirstProducer() {
        return (Person) getFirstPropertyValue(DIDLObject.Property.UPNP.PRODUCER.class);
    }

    public Person[] getProducers() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.PRODUCER.class);
        return (Person[]) propertyValues.toArray(new Person[propertyValues.size()]);
    }

    public MusicAlbum setProducers(Person[] personArr) {
        removeProperties(DIDLObject.Property.UPNP.PRODUCER.class);
        for (Person person : personArr) {
            addProperty(new DIDLObject.Property.UPNP.PRODUCER(person));
        }
        return this;
    }

    public URI getFirstAlbumArtURI() {
        return (URI) getFirstPropertyValue(DIDLObject.Property.UPNP.ALBUM_ART_URI.class);
    }

    public URI[] getAlbumArtURIs() {
        List propertyValues = getPropertyValues(DIDLObject.Property.UPNP.ALBUM_ART_URI.class);
        return (URI[]) propertyValues.toArray(new URI[propertyValues.size()]);
    }

    public MusicAlbum setAlbumArtURIs(URI[] uriArr) {
        removeProperties(DIDLObject.Property.UPNP.ALBUM_ART_URI.class);
        for (URI uri : uriArr) {
            addProperty(new DIDLObject.Property.UPNP.ALBUM_ART_URI(uri));
        }
        return this;
    }

    public String getToc() {
        return (String) getFirstPropertyValue(DIDLObject.Property.UPNP.TOC.class);
    }

    public MusicAlbum setToc(String str) {
        replaceFirstProperty(new DIDLObject.Property.UPNP.TOC(str));
        return this;
    }

    public MusicTrack[] getMusicTracks() {
        ArrayList arrayList = new ArrayList();
        for (Item item : getItems()) {
            if (item instanceof MusicTrack) {
                arrayList.add((MusicTrack) item);
            }
        }
        return (MusicTrack[]) arrayList.toArray(new MusicTrack[arrayList.size()]);
    }

    public void addMusicTracks(List<MusicTrack> list) {
        addMusicTracks((MusicTrack[]) list.toArray(new MusicTrack[list.size()]));
    }

    public void addMusicTracks(MusicTrack[] musicTrackArr) {
        if (musicTrackArr != null) {
            for (MusicTrack musicTrack : musicTrackArr) {
                musicTrack.setAlbum(getTitle());
                addItem(musicTrack);
            }
        }
    }
}
