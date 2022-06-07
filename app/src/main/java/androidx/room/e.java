package androidx.room;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.Callable;

/* compiled from: InvalidationLiveDataContainer.java */
/* loaded from: classes.dex */
public class e {
    @VisibleForTesting
    final Set<LiveData> a = Collections.newSetFromMap(new IdentityHashMap());
    private final RoomDatabase b;

    public e(RoomDatabase roomDatabase) {
        this.b = roomDatabase;
    }

    public <T> LiveData<T> a(String[] strArr, boolean z, Callable<T> callable) {
        return new l(this.b, this, z, callable, strArr);
    }

    public void a(LiveData liveData) {
        this.a.add(liveData);
    }

    public void b(LiveData liveData) {
        this.a.remove(liveData);
    }
}
