package androidx.media;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.ObjectsCompat;
import androidx.media.MediaSessionManager;
import com.xiaomi.mipush.sdk.Constants;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MediaSessionManagerImplBase.java */
/* loaded from: classes.dex */
public class c implements MediaSessionManager.a {
    private static final boolean a = MediaSessionManager.a;
    Context b;
    ContentResolver c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(Context context) {
        this.b = context;
        this.c = this.b.getContentResolver();
    }

    public Context a() {
        return this.b;
    }

    @Override // androidx.media.MediaSessionManager.a
    public boolean a(@NonNull MediaSessionManager.b bVar) {
        try {
            if (this.b.getPackageManager().getApplicationInfo(bVar.a(), 0) == null) {
                return false;
            }
            return a(bVar, "android.permission.STATUS_BAR_SERVICE") || a(bVar, "android.permission.MEDIA_CONTENT_CONTROL") || bVar.c() == 1000 || b(bVar);
        } catch (PackageManager.NameNotFoundException unused) {
            if (a) {
                Log.d("MediaSessionManager", "Package " + bVar.a() + " doesn't exist");
            }
            return false;
        }
    }

    private boolean a(MediaSessionManager.b bVar, String str) {
        return bVar.b() < 0 ? this.b.getPackageManager().checkPermission(str, bVar.a()) == 0 : this.b.checkPermission(str, bVar.b(), bVar.c()) == 0;
    }

    boolean b(@NonNull MediaSessionManager.b bVar) {
        String string = Settings.Secure.getString(this.c, "enabled_notification_listeners");
        if (string != null) {
            for (String str : string.split(Constants.COLON_SEPARATOR)) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (unflattenFromString != null && unflattenFromString.getPackageName().equals(bVar.a())) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MediaSessionManagerImplBase.java */
    /* loaded from: classes.dex */
    public static class a implements MediaSessionManager.b {
        private String a;
        private int b;
        private int c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(String str, int i, int i2) {
            this.a = str;
            this.b = i;
            this.c = i2;
        }

        @Override // androidx.media.MediaSessionManager.b
        public String a() {
            return this.a;
        }

        @Override // androidx.media.MediaSessionManager.b
        public int b() {
            return this.b;
        }

        @Override // androidx.media.MediaSessionManager.b
        public int c() {
            return this.c;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return (this.b < 0 || aVar.b < 0) ? TextUtils.equals(this.a, aVar.a) && this.c == aVar.c : TextUtils.equals(this.a, aVar.a) && this.b == aVar.b && this.c == aVar.c;
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.a, Integer.valueOf(this.c));
        }
    }
}
