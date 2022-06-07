package androidx.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.media.b;
import androidx.media.c;

/* loaded from: classes.dex */
public final class MediaSessionManager {
    static final boolean a = Log.isLoggable("MediaSessionManager", 3);
    private static final Object c = new Object();
    private static volatile MediaSessionManager d;
    a b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface a {
        boolean a(b bVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface b {
        String a();

        int b();

        int c();
    }

    @NonNull
    public static MediaSessionManager getSessionManager(@NonNull Context context) {
        MediaSessionManager mediaSessionManager;
        if (context != null) {
            synchronized (c) {
                if (d == null) {
                    d = new MediaSessionManager(context.getApplicationContext());
                }
                mediaSessionManager = d;
            }
            return mediaSessionManager;
        }
        throw new IllegalArgumentException("context cannot be null");
    }

    private MediaSessionManager(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.b = new b(context);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.b = new a(context);
        } else {
            this.b = new c(context);
        }
    }

    public boolean isTrustedForMediaControl(@NonNull RemoteUserInfo remoteUserInfo) {
        if (remoteUserInfo != null) {
            return this.b.a(remoteUserInfo.a);
        }
        throw new IllegalArgumentException("userInfo should not be null");
    }

    /* loaded from: classes.dex */
    public static final class RemoteUserInfo {
        public static final String LEGACY_CONTROLLER = "android.media.session.MediaController";
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static final int UNKNOWN_PID = -1;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static final int UNKNOWN_UID = -1;
        b a;

        public RemoteUserInfo(@NonNull String str, int i, int i2) {
            if (str == null) {
                throw new NullPointerException("package shouldn't be null");
            } else if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("packageName should be nonempty");
            } else if (Build.VERSION.SDK_INT >= 28) {
                this.a = new b.a(str, i, i2);
            } else {
                this.a = new c.a(str, i, i2);
            }
        }

        @RequiresApi(28)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public RemoteUserInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            String a = b.a.a(remoteUserInfo);
            if (a == null) {
                throw new NullPointerException("package shouldn't be null");
            } else if (!TextUtils.isEmpty(a)) {
                this.a = new b.a(remoteUserInfo);
            } else {
                throw new IllegalArgumentException("packageName should be nonempty");
            }
        }

        @NonNull
        public String getPackageName() {
            return this.a.a();
        }

        public int getPid() {
            return this.a.b();
        }

        public int getUid() {
            return this.a.c();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RemoteUserInfo)) {
                return false;
            }
            return this.a.equals(((RemoteUserInfo) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }
}
