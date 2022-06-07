package androidx.core.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

/* loaded from: classes.dex */
public class NotificationChannelCompat {
    public static final String DEFAULT_CHANNEL_ID = "miscellaneous";
    @NonNull
    final String a;
    CharSequence b;
    int c;
    String d;
    String e;
    boolean f;
    Uri g;
    AudioAttributes h;
    boolean i;
    int j;
    boolean k;
    long[] l;
    String m;
    String n;
    private boolean o;
    private int p;
    private boolean q;
    private boolean r;

    /* loaded from: classes.dex */
    public static class Builder {
        private final NotificationChannelCompat a;

        public Builder(@NonNull String str, int i) {
            this.a = new NotificationChannelCompat(str, i);
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.a.b = charSequence;
            return this;
        }

        @NonNull
        public Builder setImportance(int i) {
            this.a.c = i;
            return this;
        }

        @NonNull
        public Builder setDescription(@Nullable String str) {
            this.a.d = str;
            return this;
        }

        @NonNull
        public Builder setGroup(@Nullable String str) {
            this.a.e = str;
            return this;
        }

        @NonNull
        public Builder setShowBadge(boolean z) {
            this.a.f = z;
            return this;
        }

        @NonNull
        public Builder setSound(@Nullable Uri uri, @Nullable AudioAttributes audioAttributes) {
            NotificationChannelCompat notificationChannelCompat = this.a;
            notificationChannelCompat.g = uri;
            notificationChannelCompat.h = audioAttributes;
            return this;
        }

        @NonNull
        public Builder setLightsEnabled(boolean z) {
            this.a.i = z;
            return this;
        }

        @NonNull
        public Builder setLightColor(int i) {
            this.a.j = i;
            return this;
        }

        @NonNull
        public Builder setVibrationEnabled(boolean z) {
            this.a.k = z;
            return this;
        }

        @NonNull
        public Builder setVibrationPattern(@Nullable long[] jArr) {
            this.a.k = jArr != null && jArr.length > 0;
            this.a.l = jArr;
            return this;
        }

        @NonNull
        public Builder setConversationId(@NonNull String str, @NonNull String str2) {
            if (Build.VERSION.SDK_INT >= 30) {
                NotificationChannelCompat notificationChannelCompat = this.a;
                notificationChannelCompat.m = str;
                notificationChannelCompat.n = str2;
            }
            return this;
        }

        @NonNull
        public NotificationChannelCompat build() {
            return this.a;
        }
    }

    NotificationChannelCompat(@NonNull String str, int i) {
        this.f = true;
        this.g = Settings.System.DEFAULT_NOTIFICATION_URI;
        this.j = 0;
        this.a = (String) Preconditions.checkNotNull(str);
        this.c = i;
        if (Build.VERSION.SDK_INT >= 21) {
            this.h = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(26)
    public NotificationChannelCompat(@NonNull NotificationChannel notificationChannel) {
        this(notificationChannel.getId(), notificationChannel.getImportance());
        this.b = notificationChannel.getName();
        this.d = notificationChannel.getDescription();
        this.e = notificationChannel.getGroup();
        this.f = notificationChannel.canShowBadge();
        this.g = notificationChannel.getSound();
        this.h = notificationChannel.getAudioAttributes();
        this.i = notificationChannel.shouldShowLights();
        this.j = notificationChannel.getLightColor();
        this.k = notificationChannel.shouldVibrate();
        this.l = notificationChannel.getVibrationPattern();
        if (Build.VERSION.SDK_INT >= 30) {
            this.m = notificationChannel.getParentChannelId();
            this.n = notificationChannel.getConversationId();
        }
        this.o = notificationChannel.canBypassDnd();
        this.p = notificationChannel.getLockscreenVisibility();
        if (Build.VERSION.SDK_INT >= 29) {
            this.q = notificationChannel.canBubble();
        }
        if (Build.VERSION.SDK_INT >= 30) {
            this.r = notificationChannel.isImportantConversation();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NotificationChannel a() {
        String str;
        String str2;
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannel notificationChannel = new NotificationChannel(this.a, this.b, this.c);
        notificationChannel.setDescription(this.d);
        notificationChannel.setGroup(this.e);
        notificationChannel.setShowBadge(this.f);
        notificationChannel.setSound(this.g, this.h);
        notificationChannel.enableLights(this.i);
        notificationChannel.setLightColor(this.j);
        notificationChannel.setVibrationPattern(this.l);
        notificationChannel.enableVibration(this.k);
        if (!(Build.VERSION.SDK_INT < 30 || (str = this.m) == null || (str2 = this.n) == null)) {
            notificationChannel.setConversationId(str, str2);
        }
        return notificationChannel;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this.a, this.c).setName(this.b).setDescription(this.d).setGroup(this.e).setShowBadge(this.f).setSound(this.g, this.h).setLightsEnabled(this.i).setLightColor(this.j).setVibrationEnabled(this.k).setVibrationPattern(this.l).setConversationId(this.m, this.n);
    }

    @NonNull
    public String getId() {
        return this.a;
    }

    @Nullable
    public CharSequence getName() {
        return this.b;
    }

    @Nullable
    public String getDescription() {
        return this.d;
    }

    public int getImportance() {
        return this.c;
    }

    @Nullable
    public Uri getSound() {
        return this.g;
    }

    @Nullable
    public AudioAttributes getAudioAttributes() {
        return this.h;
    }

    public boolean shouldShowLights() {
        return this.i;
    }

    public int getLightColor() {
        return this.j;
    }

    public boolean shouldVibrate() {
        return this.k;
    }

    @Nullable
    public long[] getVibrationPattern() {
        return this.l;
    }

    public boolean canShowBadge() {
        return this.f;
    }

    @Nullable
    public String getGroup() {
        return this.e;
    }

    @Nullable
    public String getParentChannelId() {
        return this.m;
    }

    @Nullable
    public String getConversationId() {
        return this.n;
    }

    public boolean canBypassDnd() {
        return this.o;
    }

    public int getLockscreenVisibility() {
        return this.p;
    }

    public boolean canBubble() {
        return this.q;
    }

    public boolean isImportantConversation() {
        return this.r;
    }
}
