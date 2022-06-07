package androidx.transition;

import android.os.IBinder;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: WindowIdApi14.java */
/* loaded from: classes.dex */
public class ai implements ak {
    private final IBinder a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ai(IBinder iBinder) {
        this.a = iBinder;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ai) && ((ai) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
