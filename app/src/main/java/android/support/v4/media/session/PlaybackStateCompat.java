package android.support.v4.media.session;

import android.annotation.SuppressLint;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class PlaybackStateCompat implements Parcelable {
    public static final long ACTION_FAST_FORWARD = 64;
    public static final long ACTION_PAUSE = 2;
    public static final long ACTION_PLAY = 4;
    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024;
    public static final long ACTION_PLAY_FROM_SEARCH = 2048;
    public static final long ACTION_PLAY_FROM_URI = 8192;
    public static final long ACTION_PLAY_PAUSE = 512;
    public static final long ACTION_PREPARE = 16384;
    public static final long ACTION_PREPARE_FROM_MEDIA_ID = 32768;
    public static final long ACTION_PREPARE_FROM_SEARCH = 65536;
    public static final long ACTION_PREPARE_FROM_URI = 131072;
    public static final long ACTION_REWIND = 8;
    public static final long ACTION_SEEK_TO = 256;
    public static final long ACTION_SET_CAPTIONING_ENABLED = 1048576;
    public static final long ACTION_SET_PLAYBACK_SPEED = 4194304;
    public static final long ACTION_SET_RATING = 128;
    public static final long ACTION_SET_REPEAT_MODE = 262144;
    public static final long ACTION_SET_SHUFFLE_MODE = 2097152;
    @Deprecated
    public static final long ACTION_SET_SHUFFLE_MODE_ENABLED = 524288;
    public static final long ACTION_SKIP_TO_NEXT = 32;
    public static final long ACTION_SKIP_TO_PREVIOUS = 16;
    public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096;
    public static final long ACTION_STOP = 1;
    public static final Parcelable.Creator<PlaybackStateCompat> CREATOR = new Parcelable.Creator<PlaybackStateCompat>() { // from class: android.support.v4.media.session.PlaybackStateCompat.1
        /* renamed from: a */
        public PlaybackStateCompat createFromParcel(Parcel parcel) {
            return new PlaybackStateCompat(parcel);
        }

        /* renamed from: a */
        public PlaybackStateCompat[] newArray(int i) {
            return new PlaybackStateCompat[i];
        }
    };
    public static final int ERROR_CODE_ACTION_ABORTED = 10;
    public static final int ERROR_CODE_APP_ERROR = 1;
    public static final int ERROR_CODE_AUTHENTICATION_EXPIRED = 3;
    public static final int ERROR_CODE_CONCURRENT_STREAM_LIMIT = 5;
    public static final int ERROR_CODE_CONTENT_ALREADY_PLAYING = 8;
    public static final int ERROR_CODE_END_OF_QUEUE = 11;
    public static final int ERROR_CODE_NOT_AVAILABLE_IN_REGION = 7;
    public static final int ERROR_CODE_NOT_SUPPORTED = 2;
    public static final int ERROR_CODE_PARENTAL_CONTROL_RESTRICTED = 6;
    public static final int ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED = 4;
    public static final int ERROR_CODE_SKIP_LIMIT_REACHED = 9;
    public static final int ERROR_CODE_UNKNOWN_ERROR = 0;
    public static final long PLAYBACK_POSITION_UNKNOWN = -1;
    public static final int REPEAT_MODE_ALL = 2;
    public static final int REPEAT_MODE_GROUP = 3;
    public static final int REPEAT_MODE_INVALID = -1;
    public static final int REPEAT_MODE_NONE = 0;
    public static final int REPEAT_MODE_ONE = 1;
    public static final int SHUFFLE_MODE_ALL = 1;
    public static final int SHUFFLE_MODE_GROUP = 2;
    public static final int SHUFFLE_MODE_INVALID = -1;
    public static final int SHUFFLE_MODE_NONE = 0;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;
    final int a;
    final long b;
    final long c;
    final float d;
    final long e;
    final int f;
    final CharSequence g;
    final long h;
    List<CustomAction> i;
    final long j;
    final Bundle k;
    private PlaybackState l;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface Actions {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public @interface MediaKeyAction {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface RepeatMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface ShuffleMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface State {
    }

    public static int toKeyCode(long j) {
        if (j == 4) {
            return 126;
        }
        if (j == 2) {
            return 127;
        }
        if (j == 32) {
            return 87;
        }
        if (j == 16) {
            return 88;
        }
        if (j == 1) {
            return 86;
        }
        if (j == 64) {
            return 90;
        }
        if (j == 8) {
            return 89;
        }
        return j == 512 ? 85 : 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    PlaybackStateCompat(int i, long j, long j2, float f, long j3, int i2, CharSequence charSequence, long j4, List<CustomAction> list, long j5, Bundle bundle) {
        this.a = i;
        this.b = j;
        this.c = j2;
        this.d = f;
        this.e = j3;
        this.f = i2;
        this.g = charSequence;
        this.h = j4;
        this.i = new ArrayList(list);
        this.j = j5;
        this.k = bundle;
    }

    PlaybackStateCompat(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readLong();
        this.d = parcel.readFloat();
        this.h = parcel.readLong();
        this.c = parcel.readLong();
        this.e = parcel.readLong();
        this.g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.i = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.j = parcel.readLong();
        this.k = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
        this.f = parcel.readInt();
    }

    public String toString() {
        return "PlaybackState {state=" + this.a + ", position=" + this.b + ", buffered position=" + this.c + ", speed=" + this.d + ", updated=" + this.h + ", actions=" + this.e + ", error code=" + this.f + ", error message=" + this.g + ", custom actions=" + this.i + ", active item id=" + this.j + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeLong(this.b);
        parcel.writeFloat(this.d);
        parcel.writeLong(this.h);
        parcel.writeLong(this.c);
        parcel.writeLong(this.e);
        TextUtils.writeToParcel(this.g, parcel, i);
        parcel.writeTypedList(this.i);
        parcel.writeLong(this.j);
        parcel.writeBundle(this.k);
        parcel.writeInt(this.f);
    }

    public int getState() {
        return this.a;
    }

    public long getPosition() {
        return this.b;
    }

    public long getLastPositionUpdateTime() {
        return this.h;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public long getCurrentPosition(Long l) {
        return Math.max(0L, this.b + (this.d * ((float) (l != null ? l.longValue() : SystemClock.elapsedRealtime() - this.h))));
    }

    public long getBufferedPosition() {
        return this.c;
    }

    public float getPlaybackSpeed() {
        return this.d;
    }

    public long getActions() {
        return this.e;
    }

    public List<CustomAction> getCustomActions() {
        return this.i;
    }

    public int getErrorCode() {
        return this.f;
    }

    public CharSequence getErrorMessage() {
        return this.g;
    }

    public long getActiveQueueItemId() {
        return this.j;
    }

    @Nullable
    public Bundle getExtras() {
        return this.k;
    }

    public static PlaybackStateCompat fromPlaybackState(Object obj) {
        ArrayList arrayList;
        Bundle bundle;
        if (obj == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        PlaybackState playbackState = (PlaybackState) obj;
        List<PlaybackState.CustomAction> a2 = a.a(playbackState);
        if (a2 != null) {
            ArrayList arrayList2 = new ArrayList(a2.size());
            for (PlaybackState.CustomAction customAction : a2) {
                arrayList2.add(CustomAction.fromCustomAction(customAction));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        if (Build.VERSION.SDK_INT >= 22) {
            Bundle a3 = b.a(playbackState);
            MediaSessionCompat.ensureClassLoader(a3);
            bundle = a3;
        } else {
            bundle = null;
        }
        PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(a.b(playbackState), a.c(playbackState), a.d(playbackState), a.e(playbackState), a.f(playbackState), 0, a.g(playbackState), a.h(playbackState), arrayList, a.i(playbackState), bundle);
        playbackStateCompat.l = playbackState;
        return playbackStateCompat;
    }

    public Object getPlaybackState() {
        if (this.l == null && Build.VERSION.SDK_INT >= 21) {
            PlaybackState.Builder a2 = a.a();
            a.a(a2, this.a, this.b, this.d, this.h);
            a.a(a2, this.c);
            a.b(a2, this.e);
            a.a(a2, this.g);
            for (CustomAction customAction : this.i) {
                a.a(a2, (PlaybackState.CustomAction) customAction.getCustomAction());
            }
            a.c(a2, this.j);
            if (Build.VERSION.SDK_INT >= 22) {
                b.a(a2, this.k);
            }
            this.l = a.a(a2);
        }
        return this.l;
    }

    /* loaded from: classes.dex */
    public static final class CustomAction implements Parcelable {
        public static final Parcelable.Creator<CustomAction> CREATOR = new Parcelable.Creator<CustomAction>() { // from class: android.support.v4.media.session.PlaybackStateCompat.CustomAction.1
            /* renamed from: a */
            public CustomAction createFromParcel(Parcel parcel) {
                return new CustomAction(parcel);
            }

            /* renamed from: a */
            public CustomAction[] newArray(int i) {
                return new CustomAction[i];
            }
        };
        private final String a;
        private final CharSequence b;
        private final int c;
        private final Bundle d;
        private PlaybackState.CustomAction e;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        CustomAction(String str, CharSequence charSequence, int i, Bundle bundle) {
            this.a = str;
            this.b = charSequence;
            this.c = i;
            this.d = bundle;
        }

        CustomAction(Parcel parcel) {
            this.a = parcel.readString();
            this.b = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.c = parcel.readInt();
            this.d = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            TextUtils.writeToParcel(this.b, parcel, i);
            parcel.writeInt(this.c);
            parcel.writeBundle(this.d);
        }

        public static CustomAction fromCustomAction(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) obj;
            Bundle a = a.a(customAction);
            MediaSessionCompat.ensureClassLoader(a);
            CustomAction customAction2 = new CustomAction(a.b(customAction), a.c(customAction), a.d(customAction), a);
            customAction2.e = customAction;
            return customAction2;
        }

        public Object getCustomAction() {
            if (this.e != null || Build.VERSION.SDK_INT < 21) {
                return this.e;
            }
            PlaybackState.CustomAction.Builder a = a.a(this.a, this.b, this.c);
            a.a(a, this.d);
            return a.a(a);
        }

        public String getAction() {
            return this.a;
        }

        public CharSequence getName() {
            return this.b;
        }

        public int getIcon() {
            return this.c;
        }

        public Bundle getExtras() {
            return this.d;
        }

        public String toString() {
            return "Action:mName='" + ((Object) this.b) + ", mIcon=" + this.c + ", mExtras=" + this.d;
        }

        /* loaded from: classes.dex */
        public static final class Builder {
            private final String a;
            private final CharSequence b;
            private final int c;
            private Bundle d;

            public Builder(String str, CharSequence charSequence, int i) {
                if (TextUtils.isEmpty(str)) {
                    throw new IllegalArgumentException("You must specify an action to build a CustomAction");
                } else if (TextUtils.isEmpty(charSequence)) {
                    throw new IllegalArgumentException("You must specify a name to build a CustomAction");
                } else if (i != 0) {
                    this.a = str;
                    this.b = charSequence;
                    this.c = i;
                } else {
                    throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction");
                }
            }

            public Builder setExtras(Bundle bundle) {
                this.d = bundle;
                return this;
            }

            public CustomAction build() {
                return new CustomAction(this.a, this.b, this.c, this.d);
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final List<CustomAction> a;
        private int b;
        private long c;
        private long d;
        private float e;
        private long f;
        private int g;
        private CharSequence h;
        private long i;
        private long j;
        private Bundle k;

        public Builder() {
            this.a = new ArrayList();
            this.j = -1L;
        }

        public Builder(PlaybackStateCompat playbackStateCompat) {
            this.a = new ArrayList();
            this.j = -1L;
            this.b = playbackStateCompat.a;
            this.c = playbackStateCompat.b;
            this.e = playbackStateCompat.d;
            this.i = playbackStateCompat.h;
            this.d = playbackStateCompat.c;
            this.f = playbackStateCompat.e;
            this.g = playbackStateCompat.f;
            this.h = playbackStateCompat.g;
            if (playbackStateCompat.i != null) {
                this.a.addAll(playbackStateCompat.i);
            }
            this.j = playbackStateCompat.j;
            this.k = playbackStateCompat.k;
        }

        public Builder setState(int i, long j, float f) {
            return setState(i, j, f, SystemClock.elapsedRealtime());
        }

        public Builder setState(int i, long j, float f, long j2) {
            this.b = i;
            this.c = j;
            this.i = j2;
            this.e = f;
            return this;
        }

        public Builder setBufferedPosition(long j) {
            this.d = j;
            return this;
        }

        public Builder setActions(long j) {
            this.f = j;
            return this;
        }

        public Builder addCustomAction(String str, String str2, int i) {
            return addCustomAction(new CustomAction(str, str2, i, null));
        }

        public Builder addCustomAction(CustomAction customAction) {
            if (customAction != null) {
                this.a.add(customAction);
                return this;
            }
            throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackStateCompat");
        }

        public Builder setActiveQueueItemId(long j) {
            this.j = j;
            return this;
        }

        @Deprecated
        public Builder setErrorMessage(CharSequence charSequence) {
            this.h = charSequence;
            return this;
        }

        public Builder setErrorMessage(int i, CharSequence charSequence) {
            this.g = i;
            this.h = charSequence;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.k = bundle;
            return this;
        }

        public PlaybackStateCompat build() {
            return new PlaybackStateCompat(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.a, this.j, this.k);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class a {
        @DoNotInline
        static PlaybackState.Builder a() {
            return new PlaybackState.Builder();
        }

        @DoNotInline
        static void a(PlaybackState.Builder builder, int i, long j, float f, long j2) {
            builder.setState(i, j, f, j2);
        }

        @DoNotInline
        static void a(PlaybackState.Builder builder, long j) {
            builder.setBufferedPosition(j);
        }

        @DoNotInline
        static void b(PlaybackState.Builder builder, long j) {
            builder.setActions(j);
        }

        @DoNotInline
        static void a(PlaybackState.Builder builder, CharSequence charSequence) {
            builder.setErrorMessage(charSequence);
        }

        @DoNotInline
        static void a(PlaybackState.Builder builder, PlaybackState.CustomAction customAction) {
            builder.addCustomAction(customAction);
        }

        @DoNotInline
        static void c(PlaybackState.Builder builder, long j) {
            builder.setActiveQueueItemId(j);
        }

        @DoNotInline
        static List<PlaybackState.CustomAction> a(PlaybackState playbackState) {
            return playbackState.getCustomActions();
        }

        @DoNotInline
        static PlaybackState a(PlaybackState.Builder builder) {
            return builder.build();
        }

        @DoNotInline
        static int b(PlaybackState playbackState) {
            return playbackState.getState();
        }

        @DoNotInline
        static long c(PlaybackState playbackState) {
            return playbackState.getPosition();
        }

        @DoNotInline
        static long d(PlaybackState playbackState) {
            return playbackState.getBufferedPosition();
        }

        @DoNotInline
        static float e(PlaybackState playbackState) {
            return playbackState.getPlaybackSpeed();
        }

        @DoNotInline
        static long f(PlaybackState playbackState) {
            return playbackState.getActions();
        }

        @DoNotInline
        static CharSequence g(PlaybackState playbackState) {
            return playbackState.getErrorMessage();
        }

        @DoNotInline
        static long h(PlaybackState playbackState) {
            return playbackState.getLastPositionUpdateTime();
        }

        @DoNotInline
        static long i(PlaybackState playbackState) {
            return playbackState.getActiveQueueItemId();
        }

        @DoNotInline
        static PlaybackState.CustomAction.Builder a(String str, CharSequence charSequence, int i) {
            return new PlaybackState.CustomAction.Builder(str, charSequence, i);
        }

        @DoNotInline
        static void a(PlaybackState.CustomAction.Builder builder, Bundle bundle) {
            builder.setExtras(bundle);
        }

        @DoNotInline
        static PlaybackState.CustomAction a(PlaybackState.CustomAction.Builder builder) {
            return builder.build();
        }

        @DoNotInline
        static Bundle a(PlaybackState.CustomAction customAction) {
            return customAction.getExtras();
        }

        @DoNotInline
        static String b(PlaybackState.CustomAction customAction) {
            return customAction.getAction();
        }

        @DoNotInline
        static CharSequence c(PlaybackState.CustomAction customAction) {
            return customAction.getName();
        }

        @DoNotInline
        static int d(PlaybackState.CustomAction customAction) {
            return customAction.getIcon();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(22)
    /* loaded from: classes.dex */
    public static class b {
        @DoNotInline
        static void a(PlaybackState.Builder builder, Bundle bundle) {
            builder.setExtras(bundle);
        }

        @DoNotInline
        static Bundle a(PlaybackState playbackState) {
            return playbackState.getExtras();
        }
    }
}
