package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.provider.FontsContractCompat;
import androidx.core.provider.d;

/* compiled from: CallbackWithHandler.java */
/* loaded from: classes.dex */
public class a {
    @NonNull
    private final FontsContractCompat.FontRequestCallback a;
    @NonNull
    private final Handler b;

    public a(@NonNull FontsContractCompat.FontRequestCallback fontRequestCallback, @NonNull Handler handler) {
        this.a = fontRequestCallback;
        this.b = handler;
    }

    public a(@NonNull FontsContractCompat.FontRequestCallback fontRequestCallback) {
        this.a = fontRequestCallback;
        this.b = b.a();
    }

    private void a(@NonNull final Typeface typeface) {
        final FontsContractCompat.FontRequestCallback fontRequestCallback = this.a;
        this.b.post(new Runnable() { // from class: androidx.core.provider.a.1
            @Override // java.lang.Runnable
            public void run() {
                fontRequestCallback.onTypefaceRetrieved(typeface);
            }
        });
    }

    private void a(final int i) {
        final FontsContractCompat.FontRequestCallback fontRequestCallback = this.a;
        this.b.post(new Runnable() { // from class: androidx.core.provider.a.2
            @Override // java.lang.Runnable
            public void run() {
                fontRequestCallback.onTypefaceRequestFailed(i);
            }
        });
    }

    public void a(@NonNull d.a aVar) {
        if (aVar.a()) {
            a(aVar.a);
        } else {
            a(aVar.b);
        }
    }
}
