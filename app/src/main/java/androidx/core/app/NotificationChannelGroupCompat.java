package androidx.core.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class NotificationChannelGroupCompat {
    final String a;
    CharSequence b;
    String c;
    private boolean d;
    private List<NotificationChannelCompat> e;

    /* loaded from: classes.dex */
    public static class Builder {
        final NotificationChannelGroupCompat a;

        public Builder(@NonNull String str) {
            this.a = new NotificationChannelGroupCompat(str);
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.a.b = charSequence;
            return this;
        }

        @NonNull
        public Builder setDescription(@Nullable String str) {
            this.a.c = str;
            return this;
        }

        @NonNull
        public NotificationChannelGroupCompat build() {
            return this.a;
        }
    }

    NotificationChannelGroupCompat(@NonNull String str) {
        this.e = Collections.emptyList();
        this.a = (String) Preconditions.checkNotNull(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(28)
    public NotificationChannelGroupCompat(@NonNull NotificationChannelGroup notificationChannelGroup) {
        this(notificationChannelGroup, Collections.emptyList());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(26)
    public NotificationChannelGroupCompat(@NonNull NotificationChannelGroup notificationChannelGroup, @NonNull List<NotificationChannel> list) {
        this(notificationChannelGroup.getId());
        this.b = notificationChannelGroup.getName();
        if (Build.VERSION.SDK_INT >= 28) {
            this.c = notificationChannelGroup.getDescription();
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.d = notificationChannelGroup.isBlocked();
            this.e = a(notificationChannelGroup.getChannels());
            return;
        }
        this.e = a(list);
    }

    @RequiresApi(26)
    private List<NotificationChannelCompat> a(List<NotificationChannel> list) {
        ArrayList arrayList = new ArrayList();
        for (NotificationChannel notificationChannel : list) {
            if (this.a.equals(notificationChannel.getGroup())) {
                arrayList.add(new NotificationChannelCompat(notificationChannel));
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NotificationChannelGroup a() {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(this.a, this.b);
        if (Build.VERSION.SDK_INT >= 28) {
            notificationChannelGroup.setDescription(this.c);
        }
        return notificationChannelGroup;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this.a).setName(this.b).setDescription(this.c);
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
        return this.c;
    }

    public boolean isBlocked() {
        return this.d;
    }

    @NonNull
    public List<NotificationChannelCompat> getChannels() {
        return this.e;
    }
}
