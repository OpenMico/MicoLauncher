package android.support.v4.media;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import java.util.Set;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class MediaMetadataCompat implements Parcelable {
    public static final String METADATA_KEY_ADVERTISEMENT = "android.media.metadata.ADVERTISEMENT";
    public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DOWNLOAD_STATUS = "android.media.metadata.DOWNLOAD_STATUS";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    final Bundle b;
    private MediaMetadata f;
    private MediaDescriptionCompat g;
    static final ArrayMap<String, Integer> a = new ArrayMap<>();
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    private static final String[] c = {METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    private static final String[] d = {METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    private static final String[] e = {METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};
    public static final Parcelable.Creator<MediaMetadataCompat> CREATOR = new Parcelable.Creator<MediaMetadataCompat>() { // from class: android.support.v4.media.MediaMetadataCompat.1
        /* renamed from: a */
        public MediaMetadataCompat createFromParcel(Parcel parcel) {
            return new MediaMetadataCompat(parcel);
        }

        /* renamed from: a */
        public MediaMetadataCompat[] newArray(int i) {
            return new MediaMetadataCompat[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        a.put(METADATA_KEY_TITLE, 1);
        a.put(METADATA_KEY_ARTIST, 1);
        a.put(METADATA_KEY_DURATION, 0);
        a.put(METADATA_KEY_ALBUM, 1);
        a.put(METADATA_KEY_AUTHOR, 1);
        a.put(METADATA_KEY_WRITER, 1);
        a.put(METADATA_KEY_COMPOSER, 1);
        a.put(METADATA_KEY_COMPILATION, 1);
        a.put(METADATA_KEY_DATE, 1);
        a.put(METADATA_KEY_YEAR, 0);
        a.put(METADATA_KEY_GENRE, 1);
        a.put(METADATA_KEY_TRACK_NUMBER, 0);
        a.put(METADATA_KEY_NUM_TRACKS, 0);
        a.put(METADATA_KEY_DISC_NUMBER, 0);
        a.put(METADATA_KEY_ALBUM_ARTIST, 1);
        a.put(METADATA_KEY_ART, 2);
        a.put(METADATA_KEY_ART_URI, 1);
        a.put(METADATA_KEY_ALBUM_ART, 2);
        a.put(METADATA_KEY_ALBUM_ART_URI, 1);
        a.put(METADATA_KEY_USER_RATING, 3);
        a.put(METADATA_KEY_RATING, 3);
        a.put(METADATA_KEY_DISPLAY_TITLE, 1);
        a.put(METADATA_KEY_DISPLAY_SUBTITLE, 1);
        a.put(METADATA_KEY_DISPLAY_DESCRIPTION, 1);
        a.put(METADATA_KEY_DISPLAY_ICON, 2);
        a.put(METADATA_KEY_DISPLAY_ICON_URI, 1);
        a.put(METADATA_KEY_MEDIA_ID, 1);
        a.put(METADATA_KEY_BT_FOLDER_TYPE, 0);
        a.put(METADATA_KEY_MEDIA_URI, 1);
        a.put("android.media.metadata.ADVERTISEMENT", 0);
        a.put(METADATA_KEY_DOWNLOAD_STATUS, 0);
    }

    MediaMetadataCompat(Bundle bundle) {
        this.b = new Bundle(bundle);
        MediaSessionCompat.ensureClassLoader(this.b);
    }

    MediaMetadataCompat(Parcel parcel) {
        this.b = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
    }

    public boolean containsKey(String str) {
        return this.b.containsKey(str);
    }

    public CharSequence getText(String str) {
        return this.b.getCharSequence(str);
    }

    public String getString(String str) {
        CharSequence charSequence = this.b.getCharSequence(str);
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }

    public long getLong(String str) {
        return this.b.getLong(str, 0L);
    }

    public RatingCompat getRating(String str) {
        RatingCompat ratingCompat;
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                ratingCompat = RatingCompat.fromRating(this.b.getParcelable(str));
            } else {
                ratingCompat = (RatingCompat) this.b.getParcelable(str);
            }
            return ratingCompat;
        } catch (Exception e2) {
            Log.w("MediaMetadata", "Failed to retrieve a key as Rating.", e2);
            return null;
        }
    }

    public Bitmap getBitmap(String str) {
        try {
            return (Bitmap) this.b.getParcelable(str);
        } catch (Exception e2) {
            Log.w("MediaMetadata", "Failed to retrieve a key as Bitmap.", e2);
            return null;
        }
    }

    public MediaDescriptionCompat getDescription() {
        Uri uri;
        Bitmap bitmap;
        Uri uri2;
        MediaDescriptionCompat mediaDescriptionCompat = this.g;
        if (mediaDescriptionCompat != null) {
            return mediaDescriptionCompat;
        }
        String string = getString(METADATA_KEY_MEDIA_ID);
        CharSequence[] charSequenceArr = new CharSequence[3];
        CharSequence text = getText(METADATA_KEY_DISPLAY_TITLE);
        if (TextUtils.isEmpty(text)) {
            int i = 0;
            int i2 = 0;
            while (i < charSequenceArr.length) {
                String[] strArr = c;
                if (i2 >= strArr.length) {
                    break;
                }
                i2++;
                CharSequence text2 = getText(strArr[i2]);
                if (!TextUtils.isEmpty(text2)) {
                    i++;
                    charSequenceArr[i] = text2;
                }
            }
        } else {
            charSequenceArr[0] = text;
            charSequenceArr[1] = getText(METADATA_KEY_DISPLAY_SUBTITLE);
            charSequenceArr[2] = getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        }
        int i3 = 0;
        while (true) {
            String[] strArr2 = d;
            uri = null;
            if (i3 >= strArr2.length) {
                bitmap = null;
                break;
            }
            bitmap = getBitmap(strArr2[i3]);
            if (bitmap != null) {
                break;
            }
            i3++;
        }
        int i4 = 0;
        while (true) {
            String[] strArr3 = e;
            if (i4 >= strArr3.length) {
                uri2 = null;
                break;
            }
            String string2 = getString(strArr3[i4]);
            if (!TextUtils.isEmpty(string2)) {
                uri2 = Uri.parse(string2);
                break;
            }
            i4++;
        }
        String string3 = getString(METADATA_KEY_MEDIA_URI);
        if (!TextUtils.isEmpty(string3)) {
            uri = Uri.parse(string3);
        }
        MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
        builder.setMediaId(string);
        builder.setTitle(charSequenceArr[0]);
        builder.setSubtitle(charSequenceArr[1]);
        builder.setDescription(charSequenceArr[2]);
        builder.setIconBitmap(bitmap);
        builder.setIconUri(uri2);
        builder.setMediaUri(uri);
        Bundle bundle = new Bundle();
        if (this.b.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            bundle.putLong(MediaDescriptionCompat.EXTRA_BT_FOLDER_TYPE, getLong(METADATA_KEY_BT_FOLDER_TYPE));
        }
        if (this.b.containsKey(METADATA_KEY_DOWNLOAD_STATUS)) {
            bundle.putLong(MediaDescriptionCompat.EXTRA_DOWNLOAD_STATUS, getLong(METADATA_KEY_DOWNLOAD_STATUS));
        }
        if (!bundle.isEmpty()) {
            builder.setExtras(bundle);
        }
        this.g = builder.build();
        return this.g;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.b);
    }

    public int size() {
        return this.b.size();
    }

    public Set<String> keySet() {
        return this.b.keySet();
    }

    public Bundle getBundle() {
        return new Bundle(this.b);
    }

    public static MediaMetadataCompat fromMediaMetadata(Object obj) {
        if (obj == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        MediaMetadata mediaMetadata = (MediaMetadata) obj;
        mediaMetadata.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        MediaMetadataCompat createFromParcel = CREATOR.createFromParcel(obtain);
        obtain.recycle();
        createFromParcel.f = mediaMetadata;
        return createFromParcel;
    }

    public Object getMediaMetadata() {
        if (this.f == null && Build.VERSION.SDK_INT >= 21) {
            Parcel obtain = Parcel.obtain();
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            this.f = (MediaMetadata) MediaMetadata.CREATOR.createFromParcel(obtain);
            obtain.recycle();
        }
        return this.f;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final Bundle a;

        public Builder() {
            this.a = new Bundle();
        }

        public Builder(MediaMetadataCompat mediaMetadataCompat) {
            this.a = new Bundle(mediaMetadataCompat.b);
            MediaSessionCompat.ensureClassLoader(this.a);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public Builder(MediaMetadataCompat mediaMetadataCompat, int i) {
            this(mediaMetadataCompat);
            for (String str : this.a.keySet()) {
                Object obj = this.a.get(str);
                if (obj instanceof Bitmap) {
                    Bitmap bitmap = (Bitmap) obj;
                    if (bitmap.getHeight() > i || bitmap.getWidth() > i) {
                        putBitmap(str, a(bitmap, i));
                    }
                }
            }
        }

        public Builder putText(String str, CharSequence charSequence) {
            if (!MediaMetadataCompat.a.containsKey(str) || MediaMetadataCompat.a.get(str).intValue() == 1) {
                this.a.putCharSequence(str, charSequence);
                return this;
            }
            throw new IllegalArgumentException("The " + str + " key cannot be used to put a CharSequence");
        }

        public Builder putString(String str, String str2) {
            if (!MediaMetadataCompat.a.containsKey(str) || MediaMetadataCompat.a.get(str).intValue() == 1) {
                this.a.putCharSequence(str, str2);
                return this;
            }
            throw new IllegalArgumentException("The " + str + " key cannot be used to put a String");
        }

        public Builder putLong(String str, long j) {
            if (!MediaMetadataCompat.a.containsKey(str) || MediaMetadataCompat.a.get(str).intValue() == 0) {
                this.a.putLong(str, j);
                return this;
            }
            throw new IllegalArgumentException("The " + str + " key cannot be used to put a long");
        }

        public Builder putRating(String str, RatingCompat ratingCompat) {
            if (!MediaMetadataCompat.a.containsKey(str) || MediaMetadataCompat.a.get(str).intValue() == 3) {
                if (Build.VERSION.SDK_INT >= 19) {
                    this.a.putParcelable(str, (Parcelable) ratingCompat.getRating());
                } else {
                    this.a.putParcelable(str, ratingCompat);
                }
                return this;
            }
            throw new IllegalArgumentException("The " + str + " key cannot be used to put a Rating");
        }

        public Builder putBitmap(String str, Bitmap bitmap) {
            if (!MediaMetadataCompat.a.containsKey(str) || MediaMetadataCompat.a.get(str).intValue() == 2) {
                this.a.putParcelable(str, bitmap);
                return this;
            }
            throw new IllegalArgumentException("The " + str + " key cannot be used to put a Bitmap");
        }

        public MediaMetadataCompat build() {
            return new MediaMetadataCompat(this.a);
        }

        private Bitmap a(Bitmap bitmap, int i) {
            float f = i;
            float min = Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
            return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * min), (int) (bitmap.getHeight() * min), true);
        }
    }
}
