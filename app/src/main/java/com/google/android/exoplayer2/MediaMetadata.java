package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Objects;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class MediaMetadata implements Bundleable {
    public static final int FOLDER_TYPE_ALBUMS = 2;
    public static final int FOLDER_TYPE_ARTISTS = 3;
    public static final int FOLDER_TYPE_GENRES = 4;
    public static final int FOLDER_TYPE_MIXED = 0;
    public static final int FOLDER_TYPE_NONE = -1;
    public static final int FOLDER_TYPE_PLAYLISTS = 5;
    public static final int FOLDER_TYPE_TITLES = 1;
    public static final int FOLDER_TYPE_YEARS = 6;
    public static final int PICTURE_TYPE_ARTIST_PERFORMER = 8;
    public static final int PICTURE_TYPE_A_BRIGHT_COLORED_FISH = 17;
    public static final int PICTURE_TYPE_BACK_COVER = 4;
    public static final int PICTURE_TYPE_BAND_ARTIST_LOGO = 19;
    public static final int PICTURE_TYPE_BAND_ORCHESTRA = 10;
    public static final int PICTURE_TYPE_COMPOSER = 11;
    public static final int PICTURE_TYPE_CONDUCTOR = 9;
    public static final int PICTURE_TYPE_DURING_PERFORMANCE = 15;
    public static final int PICTURE_TYPE_DURING_RECORDING = 14;
    public static final int PICTURE_TYPE_FILE_ICON = 1;
    public static final int PICTURE_TYPE_FILE_ICON_OTHER = 2;
    public static final int PICTURE_TYPE_FRONT_COVER = 3;
    public static final int PICTURE_TYPE_ILLUSTRATION = 18;
    public static final int PICTURE_TYPE_LEAD_ARTIST_PERFORMER = 7;
    public static final int PICTURE_TYPE_LEAFLET_PAGE = 5;
    public static final int PICTURE_TYPE_LYRICIST = 12;
    public static final int PICTURE_TYPE_MEDIA = 6;
    public static final int PICTURE_TYPE_MOVIE_VIDEO_SCREEN_CAPTURE = 16;
    public static final int PICTURE_TYPE_OTHER = 0;
    public static final int PICTURE_TYPE_PUBLISHER_STUDIO_LOGO = 20;
    public static final int PICTURE_TYPE_RECORDING_LOCATION = 13;
    @Nullable
    public final CharSequence albumArtist;
    @Nullable
    public final CharSequence albumTitle;
    @Nullable
    public final CharSequence artist;
    @Nullable
    public final byte[] artworkData;
    @Nullable
    public final Integer artworkDataType;
    @Nullable
    public final Uri artworkUri;
    @Nullable
    public final CharSequence compilation;
    @Nullable
    public final CharSequence composer;
    @Nullable
    public final CharSequence conductor;
    @Nullable
    public final CharSequence description;
    @Nullable
    public final Integer discNumber;
    @Nullable
    public final CharSequence displayTitle;
    @Nullable
    public final Bundle extras;
    @Nullable
    public final Integer folderType;
    @Nullable
    public final CharSequence genre;
    @Nullable
    public final Boolean isPlayable;
    @Nullable
    public final Uri mediaUri;
    @Nullable
    public final Rating overallRating;
    @Nullable
    public final Integer recordingDay;
    @Nullable
    public final Integer recordingMonth;
    @Nullable
    public final Integer recordingYear;
    @Nullable
    public final Integer releaseDay;
    @Nullable
    public final Integer releaseMonth;
    @Nullable
    public final Integer releaseYear;
    @Nullable
    public final CharSequence subtitle;
    @Nullable
    public final CharSequence title;
    @Nullable
    public final Integer totalDiscCount;
    @Nullable
    public final Integer totalTrackCount;
    @Nullable
    public final Integer trackNumber;
    @Nullable
    public final Rating userRating;
    @Nullable
    public final CharSequence writer;
    @Nullable
    @Deprecated
    public final Integer year;
    public static final MediaMetadata EMPTY = new Builder().build();
    public static final Bundleable.Creator<MediaMetadata> CREATOR = $$Lambda$MediaMetadata$LmmDVvU6mnLd6OwzcC6B5vJOUQc.INSTANCE;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface FolderType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface PictureType {
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        @Nullable
        private Integer A;
        @Nullable
        private Integer B;
        @Nullable
        private CharSequence C;
        @Nullable
        private CharSequence D;
        @Nullable
        private Bundle E;
        @Nullable
        private CharSequence a;
        @Nullable
        private CharSequence b;
        @Nullable
        private CharSequence c;
        @Nullable
        private CharSequence d;
        @Nullable
        private CharSequence e;
        @Nullable
        private CharSequence f;
        @Nullable
        private CharSequence g;
        @Nullable
        private Uri h;
        @Nullable
        private Rating i;
        @Nullable
        private Rating j;
        @Nullable
        private byte[] k;
        @Nullable
        private Integer l;
        @Nullable
        private Uri m;
        @Nullable
        private Integer n;
        @Nullable
        private Integer o;
        @Nullable
        private Integer p;
        @Nullable
        private Boolean q;
        @Nullable
        private Integer r;
        @Nullable
        private Integer s;
        @Nullable
        private Integer t;
        @Nullable
        private Integer u;
        @Nullable
        private Integer v;
        @Nullable
        private Integer w;
        @Nullable
        private CharSequence x;
        @Nullable
        private CharSequence y;
        @Nullable
        private CharSequence z;

        public Builder() {
        }

        private Builder(MediaMetadata mediaMetadata) {
            this.a = mediaMetadata.title;
            this.b = mediaMetadata.artist;
            this.c = mediaMetadata.albumTitle;
            this.d = mediaMetadata.albumArtist;
            this.e = mediaMetadata.displayTitle;
            this.f = mediaMetadata.subtitle;
            this.g = mediaMetadata.description;
            this.h = mediaMetadata.mediaUri;
            this.i = mediaMetadata.userRating;
            this.j = mediaMetadata.overallRating;
            this.k = mediaMetadata.artworkData;
            this.l = mediaMetadata.artworkDataType;
            this.m = mediaMetadata.artworkUri;
            this.n = mediaMetadata.trackNumber;
            this.o = mediaMetadata.totalTrackCount;
            this.p = mediaMetadata.folderType;
            this.q = mediaMetadata.isPlayable;
            this.r = mediaMetadata.recordingYear;
            this.s = mediaMetadata.recordingMonth;
            this.t = mediaMetadata.recordingDay;
            this.u = mediaMetadata.releaseYear;
            this.v = mediaMetadata.releaseMonth;
            this.w = mediaMetadata.releaseDay;
            this.x = mediaMetadata.writer;
            this.y = mediaMetadata.composer;
            this.z = mediaMetadata.conductor;
            this.A = mediaMetadata.discNumber;
            this.B = mediaMetadata.totalDiscCount;
            this.C = mediaMetadata.genre;
            this.D = mediaMetadata.compilation;
            this.E = mediaMetadata.extras;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            this.a = charSequence;
            return this;
        }

        public Builder setArtist(@Nullable CharSequence charSequence) {
            this.b = charSequence;
            return this;
        }

        public Builder setAlbumTitle(@Nullable CharSequence charSequence) {
            this.c = charSequence;
            return this;
        }

        public Builder setAlbumArtist(@Nullable CharSequence charSequence) {
            this.d = charSequence;
            return this;
        }

        public Builder setDisplayTitle(@Nullable CharSequence charSequence) {
            this.e = charSequence;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.f = charSequence;
            return this;
        }

        public Builder setDescription(@Nullable CharSequence charSequence) {
            this.g = charSequence;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri uri) {
            this.h = uri;
            return this;
        }

        public Builder setUserRating(@Nullable Rating rating) {
            this.i = rating;
            return this;
        }

        public Builder setOverallRating(@Nullable Rating rating) {
            this.j = rating;
            return this;
        }

        @Deprecated
        public Builder setArtworkData(@Nullable byte[] bArr) {
            return setArtworkData(bArr, null);
        }

        public Builder setArtworkData(@Nullable byte[] bArr, @Nullable Integer num) {
            this.k = bArr == null ? null : (byte[]) bArr.clone();
            this.l = num;
            return this;
        }

        public Builder maybeSetArtworkData(byte[] bArr, int i) {
            if (this.k == null || Util.areEqual(Integer.valueOf(i), 3) || !Util.areEqual(this.l, 3)) {
                this.k = (byte[]) bArr.clone();
                this.l = Integer.valueOf(i);
            }
            return this;
        }

        public Builder setArtworkUri(@Nullable Uri uri) {
            this.m = uri;
            return this;
        }

        public Builder setTrackNumber(@Nullable Integer num) {
            this.n = num;
            return this;
        }

        public Builder setTotalTrackCount(@Nullable Integer num) {
            this.o = num;
            return this;
        }

        public Builder setFolderType(@Nullable Integer num) {
            this.p = num;
            return this;
        }

        public Builder setIsPlayable(@Nullable Boolean bool) {
            this.q = bool;
            return this;
        }

        @Deprecated
        public Builder setYear(@Nullable Integer num) {
            return setRecordingYear(num);
        }

        public Builder setRecordingYear(@Nullable Integer num) {
            this.r = num;
            return this;
        }

        public Builder setRecordingMonth(@IntRange(from = 1, to = 12) @Nullable Integer num) {
            this.s = num;
            return this;
        }

        public Builder setRecordingDay(@IntRange(from = 1, to = 31) @Nullable Integer num) {
            this.t = num;
            return this;
        }

        public Builder setReleaseYear(@Nullable Integer num) {
            this.u = num;
            return this;
        }

        public Builder setReleaseMonth(@IntRange(from = 1, to = 12) @Nullable Integer num) {
            this.v = num;
            return this;
        }

        public Builder setReleaseDay(@IntRange(from = 1, to = 31) @Nullable Integer num) {
            this.w = num;
            return this;
        }

        public Builder setWriter(@Nullable CharSequence charSequence) {
            this.x = charSequence;
            return this;
        }

        public Builder setComposer(@Nullable CharSequence charSequence) {
            this.y = charSequence;
            return this;
        }

        public Builder setConductor(@Nullable CharSequence charSequence) {
            this.z = charSequence;
            return this;
        }

        public Builder setDiscNumber(@Nullable Integer num) {
            this.A = num;
            return this;
        }

        public Builder setTotalDiscCount(@Nullable Integer num) {
            this.B = num;
            return this;
        }

        public Builder setGenre(@Nullable CharSequence charSequence) {
            this.C = charSequence;
            return this;
        }

        public Builder setCompilation(@Nullable CharSequence charSequence) {
            this.D = charSequence;
            return this;
        }

        public Builder setExtras(@Nullable Bundle bundle) {
            this.E = bundle;
            return this;
        }

        public Builder populateFromMetadata(Metadata metadata) {
            for (int i = 0; i < metadata.length(); i++) {
                metadata.get(i).populateMediaMetadata(this);
            }
            return this;
        }

        public Builder populateFromMetadata(List<Metadata> list) {
            for (int i = 0; i < list.size(); i++) {
                Metadata metadata = list.get(i);
                for (int i2 = 0; i2 < metadata.length(); i2++) {
                    metadata.get(i2).populateMediaMetadata(this);
                }
            }
            return this;
        }

        public MediaMetadata build() {
            return new MediaMetadata(this);
        }
    }

    private MediaMetadata(Builder builder) {
        this.title = builder.a;
        this.artist = builder.b;
        this.albumTitle = builder.c;
        this.albumArtist = builder.d;
        this.displayTitle = builder.e;
        this.subtitle = builder.f;
        this.description = builder.g;
        this.mediaUri = builder.h;
        this.userRating = builder.i;
        this.overallRating = builder.j;
        this.artworkData = builder.k;
        this.artworkDataType = builder.l;
        this.artworkUri = builder.m;
        this.trackNumber = builder.n;
        this.totalTrackCount = builder.o;
        this.folderType = builder.p;
        this.isPlayable = builder.q;
        this.year = builder.r;
        this.recordingYear = builder.r;
        this.recordingMonth = builder.s;
        this.recordingDay = builder.t;
        this.releaseYear = builder.u;
        this.releaseMonth = builder.v;
        this.releaseDay = builder.w;
        this.writer = builder.x;
        this.composer = builder.y;
        this.conductor = builder.z;
        this.discNumber = builder.A;
        this.totalDiscCount = builder.B;
        this.genre = builder.C;
        this.compilation = builder.D;
        this.extras = builder.E;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) obj;
        return Util.areEqual(this.title, mediaMetadata.title) && Util.areEqual(this.artist, mediaMetadata.artist) && Util.areEqual(this.albumTitle, mediaMetadata.albumTitle) && Util.areEqual(this.albumArtist, mediaMetadata.albumArtist) && Util.areEqual(this.displayTitle, mediaMetadata.displayTitle) && Util.areEqual(this.subtitle, mediaMetadata.subtitle) && Util.areEqual(this.description, mediaMetadata.description) && Util.areEqual(this.mediaUri, mediaMetadata.mediaUri) && Util.areEqual(this.userRating, mediaMetadata.userRating) && Util.areEqual(this.overallRating, mediaMetadata.overallRating) && Arrays.equals(this.artworkData, mediaMetadata.artworkData) && Util.areEqual(this.artworkDataType, mediaMetadata.artworkDataType) && Util.areEqual(this.artworkUri, mediaMetadata.artworkUri) && Util.areEqual(this.trackNumber, mediaMetadata.trackNumber) && Util.areEqual(this.totalTrackCount, mediaMetadata.totalTrackCount) && Util.areEqual(this.folderType, mediaMetadata.folderType) && Util.areEqual(this.isPlayable, mediaMetadata.isPlayable) && Util.areEqual(this.recordingYear, mediaMetadata.recordingYear) && Util.areEqual(this.recordingMonth, mediaMetadata.recordingMonth) && Util.areEqual(this.recordingDay, mediaMetadata.recordingDay) && Util.areEqual(this.releaseYear, mediaMetadata.releaseYear) && Util.areEqual(this.releaseMonth, mediaMetadata.releaseMonth) && Util.areEqual(this.releaseDay, mediaMetadata.releaseDay) && Util.areEqual(this.writer, mediaMetadata.writer) && Util.areEqual(this.composer, mediaMetadata.composer) && Util.areEqual(this.conductor, mediaMetadata.conductor) && Util.areEqual(this.discNumber, mediaMetadata.discNumber) && Util.areEqual(this.totalDiscCount, mediaMetadata.totalDiscCount) && Util.areEqual(this.genre, mediaMetadata.genre) && Util.areEqual(this.compilation, mediaMetadata.compilation);
    }

    public int hashCode() {
        return Objects.hashCode(this.title, this.artist, this.albumTitle, this.albumArtist, this.displayTitle, this.subtitle, this.description, this.mediaUri, this.userRating, this.overallRating, Integer.valueOf(Arrays.hashCode(this.artworkData)), this.artworkDataType, this.artworkUri, this.trackNumber, this.totalTrackCount, this.folderType, this.isPlayable, this.recordingYear, this.recordingMonth, this.recordingDay, this.releaseYear, this.releaseMonth, this.releaseDay, this.writer, this.composer, this.conductor, this.discNumber, this.totalDiscCount, this.genre, this.compilation);
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(a(0), this.title);
        bundle.putCharSequence(a(1), this.artist);
        bundle.putCharSequence(a(2), this.albumTitle);
        bundle.putCharSequence(a(3), this.albumArtist);
        bundle.putCharSequence(a(4), this.displayTitle);
        bundle.putCharSequence(a(5), this.subtitle);
        bundle.putCharSequence(a(6), this.description);
        bundle.putParcelable(a(7), this.mediaUri);
        bundle.putByteArray(a(10), this.artworkData);
        bundle.putParcelable(a(11), this.artworkUri);
        bundle.putCharSequence(a(22), this.writer);
        bundle.putCharSequence(a(23), this.composer);
        bundle.putCharSequence(a(24), this.conductor);
        bundle.putCharSequence(a(27), this.genre);
        bundle.putCharSequence(a(28), this.compilation);
        if (this.userRating != null) {
            bundle.putBundle(a(8), this.userRating.toBundle());
        }
        if (this.overallRating != null) {
            bundle.putBundle(a(9), this.overallRating.toBundle());
        }
        if (this.trackNumber != null) {
            bundle.putInt(a(12), this.trackNumber.intValue());
        }
        if (this.totalTrackCount != null) {
            bundle.putInt(a(13), this.totalTrackCount.intValue());
        }
        if (this.folderType != null) {
            bundle.putInt(a(14), this.folderType.intValue());
        }
        if (this.isPlayable != null) {
            bundle.putBoolean(a(15), this.isPlayable.booleanValue());
        }
        if (this.recordingYear != null) {
            bundle.putInt(a(16), this.recordingYear.intValue());
        }
        if (this.recordingMonth != null) {
            bundle.putInt(a(17), this.recordingMonth.intValue());
        }
        if (this.recordingDay != null) {
            bundle.putInt(a(18), this.recordingDay.intValue());
        }
        if (this.releaseYear != null) {
            bundle.putInt(a(19), this.releaseYear.intValue());
        }
        if (this.releaseMonth != null) {
            bundle.putInt(a(20), this.releaseMonth.intValue());
        }
        if (this.releaseDay != null) {
            bundle.putInt(a(21), this.releaseDay.intValue());
        }
        if (this.discNumber != null) {
            bundle.putInt(a(25), this.discNumber.intValue());
        }
        if (this.totalDiscCount != null) {
            bundle.putInt(a(26), this.totalDiscCount.intValue());
        }
        if (this.artworkDataType != null) {
            bundle.putInt(a(29), this.artworkDataType.intValue());
        }
        if (this.extras != null) {
            bundle.putBundle(a(1000), this.extras);
        }
        return bundle;
    }

    public static MediaMetadata a(Bundle bundle) {
        Bundle bundle2;
        Bundle bundle3;
        Builder builder = new Builder();
        builder.setTitle(bundle.getCharSequence(a(0))).setArtist(bundle.getCharSequence(a(1))).setAlbumTitle(bundle.getCharSequence(a(2))).setAlbumArtist(bundle.getCharSequence(a(3))).setDisplayTitle(bundle.getCharSequence(a(4))).setSubtitle(bundle.getCharSequence(a(5))).setDescription(bundle.getCharSequence(a(6))).setMediaUri((Uri) bundle.getParcelable(a(7))).setArtworkData(bundle.getByteArray(a(10)), bundle.containsKey(a(29)) ? Integer.valueOf(bundle.getInt(a(29))) : null).setArtworkUri((Uri) bundle.getParcelable(a(11))).setWriter(bundle.getCharSequence(a(22))).setComposer(bundle.getCharSequence(a(23))).setConductor(bundle.getCharSequence(a(24))).setGenre(bundle.getCharSequence(a(27))).setCompilation(bundle.getCharSequence(a(28))).setExtras(bundle.getBundle(a(1000)));
        if (bundle.containsKey(a(8)) && (bundle3 = bundle.getBundle(a(8))) != null) {
            builder.setUserRating(Rating.CREATOR.fromBundle(bundle3));
        }
        if (bundle.containsKey(a(9)) && (bundle2 = bundle.getBundle(a(9))) != null) {
            builder.setOverallRating(Rating.CREATOR.fromBundle(bundle2));
        }
        if (bundle.containsKey(a(12))) {
            builder.setTrackNumber(Integer.valueOf(bundle.getInt(a(12))));
        }
        if (bundle.containsKey(a(13))) {
            builder.setTotalTrackCount(Integer.valueOf(bundle.getInt(a(13))));
        }
        if (bundle.containsKey(a(14))) {
            builder.setFolderType(Integer.valueOf(bundle.getInt(a(14))));
        }
        if (bundle.containsKey(a(15))) {
            builder.setIsPlayable(Boolean.valueOf(bundle.getBoolean(a(15))));
        }
        if (bundle.containsKey(a(16))) {
            builder.setRecordingYear(Integer.valueOf(bundle.getInt(a(16))));
        }
        if (bundle.containsKey(a(17))) {
            builder.setRecordingMonth(Integer.valueOf(bundle.getInt(a(17))));
        }
        if (bundle.containsKey(a(18))) {
            builder.setRecordingDay(Integer.valueOf(bundle.getInt(a(18))));
        }
        if (bundle.containsKey(a(19))) {
            builder.setReleaseYear(Integer.valueOf(bundle.getInt(a(19))));
        }
        if (bundle.containsKey(a(20))) {
            builder.setReleaseMonth(Integer.valueOf(bundle.getInt(a(20))));
        }
        if (bundle.containsKey(a(21))) {
            builder.setReleaseDay(Integer.valueOf(bundle.getInt(a(21))));
        }
        if (bundle.containsKey(a(25))) {
            builder.setDiscNumber(Integer.valueOf(bundle.getInt(a(25))));
        }
        if (bundle.containsKey(a(26))) {
            builder.setTotalDiscCount(Integer.valueOf(bundle.getInt(a(26))));
        }
        return builder.build();
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
