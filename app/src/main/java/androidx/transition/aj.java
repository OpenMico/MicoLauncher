package androidx.transition;

import android.view.View;
import android.view.WindowId;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: WindowIdApi18.java */
@RequiresApi(18)
/* loaded from: classes.dex */
public class aj implements ak {
    private final WindowId a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aj(@NonNull View view) {
        this.a = view.getWindowId();
    }

    public boolean equals(Object obj) {
        return (obj instanceof aj) && ((aj) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
