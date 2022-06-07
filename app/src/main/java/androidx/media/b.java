package androidx.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import androidx.annotation.RequiresApi;
import androidx.media.MediaSessionManager;
import androidx.media.c;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MediaSessionManagerImplApi28.java */
@RequiresApi(28)
/* loaded from: classes.dex */
public class b extends a {
    MediaSessionManager a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Context context) {
        super(context);
        this.a = (MediaSessionManager) context.getSystemService("media_session");
    }

    @Override // androidx.media.a, androidx.media.c, androidx.media.MediaSessionManager.a
    public boolean a(MediaSessionManager.b bVar) {
        return super.a(bVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MediaSessionManagerImplApi28.java */
    @RequiresApi(28)
    /* loaded from: classes.dex */
    public static final class a extends c.a {
        final MediaSessionManager.RemoteUserInfo a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(String str, int i, int i2) {
            super(str, i, i2);
            this.a = new MediaSessionManager.RemoteUserInfo(str, i, i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            super(remoteUserInfo.getPackageName(), remoteUserInfo.getPid(), remoteUserInfo.getUid());
            this.a = remoteUserInfo;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static String a(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            return remoteUserInfo.getPackageName();
        }
    }
}
