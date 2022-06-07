package androidx.media;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.media.MediaSessionManager;

/* compiled from: MediaSessionManagerImplApi21.java */
@RequiresApi(21)
/* loaded from: classes.dex */
public class a extends c {
    public a(Context context) {
        super(context);
        this.b = context;
    }

    @Override // androidx.media.c, androidx.media.MediaSessionManager.a
    public boolean a(@NonNull MediaSessionManager.b bVar) {
        return c(bVar) || super.a(bVar);
    }

    private boolean c(@NonNull MediaSessionManager.b bVar) {
        return a().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", bVar.b(), bVar.c()) == 0;
    }
}
