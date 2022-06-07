package android.support.v4.media;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class MediaDescriptionCompat implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3;
    public static final long BT_FOLDER_TYPE_GENRES = 4;
    public static final long BT_FOLDER_TYPE_MIXED = 0;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5;
    public static final long BT_FOLDER_TYPE_TITLES = 1;
    public static final long BT_FOLDER_TYPE_YEARS = 6;
    public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new Parcelable.Creator<MediaDescriptionCompat>() { // from class: android.support.v4.media.MediaDescriptionCompat.1
        /* renamed from: a */
        public MediaDescriptionCompat createFromParcel(Parcel parcel) {
            if (Build.VERSION.SDK_INT < 21) {
                return new MediaDescriptionCompat(parcel);
            }
            return MediaDescriptionCompat.fromMediaDescription(MediaDescription.CREATOR.createFromParcel(parcel));
        }

        /* renamed from: a */
        public MediaDescriptionCompat[] newArray(int i) {
            return new MediaDescriptionCompat[i];
        }
    };
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    public static final String EXTRA_DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS";
    public static final long STATUS_DOWNLOADED = 2;
    public static final long STATUS_DOWNLOADING = 1;
    public static final long STATUS_NOT_DOWNLOADED = 0;
    private final String a;
    private final CharSequence b;
    private final CharSequence c;
    private final CharSequence d;
    private final Bitmap e;
    private final Uri f;
    private final Bundle g;
    private final Uri h;
    private MediaDescription i;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.a = str;
        this.b = charSequence;
        this.c = charSequence2;
        this.d = charSequence3;
        this.e = bitmap;
        this.f = uri;
        this.g = bundle;
        this.h = uri2;
    }

    MediaDescriptionCompat(Parcel parcel) {
        this.a = parcel.readString();
        this.b = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.c = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.d = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        ClassLoader classLoader = getClass().getClassLoader();
        this.e = (Bitmap) parcel.readParcelable(classLoader);
        this.f = (Uri) parcel.readParcelable(classLoader);
        this.g = parcel.readBundle(classLoader);
        this.h = (Uri) parcel.readParcelable(classLoader);
    }

    @Nullable
    public String getMediaId() {
        return this.a;
    }

    @Nullable
    public CharSequence getTitle() {
        return this.b;
    }

    @Nullable
    public CharSequence getSubtitle() {
        return this.c;
    }

    @Nullable
    public CharSequence getDescription() {
        return this.d;
    }

    @Nullable
    public Bitmap getIconBitmap() {
        return this.e;
    }

    @Nullable
    public Uri getIconUri() {
        return this.f;
    }

    @Nullable
    public Bundle getExtras() {
        return this.g;
    }

    @Nullable
    public Uri getMediaUri() {
        return this.h;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (Build.VERSION.SDK_INT < 21) {
            parcel.writeString(this.a);
            TextUtils.writeToParcel(this.b, parcel, i);
            TextUtils.writeToParcel(this.c, parcel, i);
            TextUtils.writeToParcel(this.d, parcel, i);
            parcel.writeParcelable(this.e, i);
            parcel.writeParcelable(this.f, i);
            parcel.writeBundle(this.g);
            parcel.writeParcelable(this.h, i);
            return;
        }
        ((MediaDescription) getMediaDescription()).writeToParcel(parcel, i);
    }

    public String toString() {
        return ((Object) this.b) + ", " + ((Object) this.c) + ", " + ((Object) this.d);
    }

    public Object getMediaDescription() {
        if (this.i != null || Build.VERSION.SDK_INT < 21) {
            return this.i;
        }
        MediaDescription.Builder a2 = a.a();
        a.a(a2, this.a);
        a.a(a2, this.b);
        a.b(a2, this.c);
        a.c(a2, this.d);
        a.a(a2, this.e);
        a.a(a2, this.f);
        Bundle bundle = this.g;
        if (Build.VERSION.SDK_INT < 23 && this.h != null) {
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true);
            }
            bundle.putParcelable(DESCRIPTION_KEY_MEDIA_URI, this.h);
        }
        a.a(a2, bundle);
        if (Build.VERSION.SDK_INT >= 23) {
            b.a(a2, this.h);
        }
        this.i = a.a(a2);
        return this.i;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.support.v4.media.MediaDescriptionCompat fromMediaDescription(java.lang.Object r6) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x008a
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 21
            if (r1 < r2) goto L_0x008a
            android.support.v4.media.MediaDescriptionCompat$Builder r1 = new android.support.v4.media.MediaDescriptionCompat$Builder
            r1.<init>()
            android.media.MediaDescription r6 = (android.media.MediaDescription) r6
            java.lang.String r2 = android.support.v4.media.MediaDescriptionCompat.a.a(r6)
            r1.setMediaId(r2)
            java.lang.CharSequence r2 = android.support.v4.media.MediaDescriptionCompat.a.b(r6)
            r1.setTitle(r2)
            java.lang.CharSequence r2 = android.support.v4.media.MediaDescriptionCompat.a.c(r6)
            r1.setSubtitle(r2)
            java.lang.CharSequence r2 = android.support.v4.media.MediaDescriptionCompat.a.d(r6)
            r1.setDescription(r2)
            android.graphics.Bitmap r2 = android.support.v4.media.MediaDescriptionCompat.a.e(r6)
            r1.setIconBitmap(r2)
            android.net.Uri r2 = android.support.v4.media.MediaDescriptionCompat.a.f(r6)
            r1.setIconUri(r2)
            android.os.Bundle r2 = android.support.v4.media.MediaDescriptionCompat.a.g(r6)
            if (r2 == 0) goto L_0x0044
            android.os.Bundle r2 = android.support.v4.media.session.MediaSessionCompat.unparcelWithClassLoader(r2)
        L_0x0044:
            if (r2 == 0) goto L_0x004f
            java.lang.String r3 = "android.support.v4.media.description.MEDIA_URI"
            android.os.Parcelable r3 = r2.getParcelable(r3)
            android.net.Uri r3 = (android.net.Uri) r3
            goto L_0x0050
        L_0x004f:
            r3 = r0
        L_0x0050:
            if (r3 == 0) goto L_0x006c
            java.lang.String r4 = "android.support.v4.media.description.NULL_BUNDLE_FLAG"
            boolean r4 = r2.containsKey(r4)
            if (r4 == 0) goto L_0x0062
            int r4 = r2.size()
            r5 = 2
            if (r4 != r5) goto L_0x0062
            goto L_0x006d
        L_0x0062:
            java.lang.String r0 = "android.support.v4.media.description.MEDIA_URI"
            r2.remove(r0)
            java.lang.String r0 = "android.support.v4.media.description.NULL_BUNDLE_FLAG"
            r2.remove(r0)
        L_0x006c:
            r0 = r2
        L_0x006d:
            r1.setExtras(r0)
            if (r3 == 0) goto L_0x0076
            r1.setMediaUri(r3)
            goto L_0x0083
        L_0x0076:
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 23
            if (r0 < r2) goto L_0x0083
            android.net.Uri r0 = android.support.v4.media.MediaDescriptionCompat.b.a(r6)
            r1.setMediaUri(r0)
        L_0x0083:
            android.support.v4.media.MediaDescriptionCompat r0 = r1.build()
            r0.i = r6
            return r0
        L_0x008a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaDescriptionCompat.fromMediaDescription(java.lang.Object):android.support.v4.media.MediaDescriptionCompat");
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private String a;
        private CharSequence b;
        private CharSequence c;
        private CharSequence d;
        private Bitmap e;
        private Uri f;
        private Bundle g;
        private Uri h;

        public Builder setMediaId(@Nullable String str) {
            this.a = str;
            return this;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            this.b = charSequence;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.c = charSequence;
            return this;
        }

        public Builder setDescription(@Nullable CharSequence charSequence) {
            this.d = charSequence;
            return this;
        }

        public Builder setIconBitmap(@Nullable Bitmap bitmap) {
            this.e = bitmap;
            return this;
        }

        public Builder setIconUri(@Nullable Uri uri) {
            this.f = uri;
            return this;
        }

        public Builder setExtras(@Nullable Bundle bundle) {
            this.g = bundle;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri uri) {
            this.h = uri;
            return this;
        }

        public MediaDescriptionCompat build() {
            return new MediaDescriptionCompat(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class a {
        @DoNotInline
        static MediaDescription.Builder a() {
            return new MediaDescription.Builder();
        }

        @DoNotInline
        static void a(MediaDescription.Builder builder, @Nullable String str) {
            builder.setMediaId(str);
        }

        @DoNotInline
        static void a(MediaDescription.Builder builder, @Nullable CharSequence charSequence) {
            builder.setTitle(charSequence);
        }

        @DoNotInline
        static void b(MediaDescription.Builder builder, @Nullable CharSequence charSequence) {
            builder.setSubtitle(charSequence);
        }

        @DoNotInline
        static void c(MediaDescription.Builder builder, @Nullable CharSequence charSequence) {
            builder.setDescription(charSequence);
        }

        @DoNotInline
        static void a(MediaDescription.Builder builder, @Nullable Bitmap bitmap) {
            builder.setIconBitmap(bitmap);
        }

        @DoNotInline
        static void a(MediaDescription.Builder builder, @Nullable Uri uri) {
            builder.setIconUri(uri);
        }

        @DoNotInline
        static void a(MediaDescription.Builder builder, @Nullable Bundle bundle) {
            builder.setExtras(bundle);
        }

        @DoNotInline
        static MediaDescription a(MediaDescription.Builder builder) {
            return builder.build();
        }

        @Nullable
        @DoNotInline
        static String a(MediaDescription mediaDescription) {
            return mediaDescription.getMediaId();
        }

        @Nullable
        @DoNotInline
        static CharSequence b(MediaDescription mediaDescription) {
            return mediaDescription.getTitle();
        }

        @Nullable
        @DoNotInline
        static CharSequence c(MediaDescription mediaDescription) {
            return mediaDescription.getSubtitle();
        }

        @Nullable
        @DoNotInline
        static CharSequence d(MediaDescription mediaDescription) {
            return mediaDescription.getDescription();
        }

        @Nullable
        @DoNotInline
        static Bitmap e(MediaDescription mediaDescription) {
            return mediaDescription.getIconBitmap();
        }

        @Nullable
        @DoNotInline
        static Uri f(MediaDescription mediaDescription) {
            return mediaDescription.getIconUri();
        }

        @Nullable
        @DoNotInline
        static Bundle g(MediaDescription mediaDescription) {
            return mediaDescription.getExtras();
        }
    }

    @RequiresApi(23)
    /* loaded from: classes.dex */
    public static class b {
        @DoNotInline
        static void a(MediaDescription.Builder builder, @Nullable Uri uri) {
            builder.setMediaUri(uri);
        }

        @Nullable
        @DoNotInline
        static Uri a(MediaDescription mediaDescription) {
            return mediaDescription.getMediaUri();
        }
    }
}
