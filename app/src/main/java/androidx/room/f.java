package androidx.room;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.IMultiInstanceInvalidationCallback;
import androidx.room.IMultiInstanceInvalidationService;
import androidx.room.InvalidationTracker;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MultiInstanceInvalidationClient.java */
/* loaded from: classes.dex */
public class f {
    final Context a;
    final String b;
    int c;
    final InvalidationTracker d;
    final InvalidationTracker.Observer e;
    @Nullable
    IMultiInstanceInvalidationService f;
    final Executor g;
    final IMultiInstanceInvalidationCallback h = new IMultiInstanceInvalidationCallback.Stub() { // from class: androidx.room.f.1
        @Override // androidx.room.IMultiInstanceInvalidationCallback
        public void onInvalidation(final String[] strArr) {
            f.this.g.execute(new Runnable() { // from class: androidx.room.f.1.1
                @Override // java.lang.Runnable
                public void run() {
                    f.this.d.notifyObserversByTableNames(strArr);
                }
            });
        }
    };
    final AtomicBoolean i = new AtomicBoolean(false);
    final ServiceConnection j = new ServiceConnection() { // from class: androidx.room.f.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            f.this.f = IMultiInstanceInvalidationService.Stub.asInterface(iBinder);
            f.this.g.execute(f.this.k);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            f.this.g.execute(f.this.l);
            f.this.f = null;
        }
    };
    final Runnable k = new Runnable() { // from class: androidx.room.f.3
        @Override // java.lang.Runnable
        public void run() {
            try {
                IMultiInstanceInvalidationService iMultiInstanceInvalidationService = f.this.f;
                if (iMultiInstanceInvalidationService != null) {
                    f.this.c = iMultiInstanceInvalidationService.registerCallback(f.this.h, f.this.b);
                    f.this.d.addObserver(f.this.e);
                }
            } catch (RemoteException e) {
                Log.w("ROOM", "Cannot register multi-instance invalidation callback", e);
            }
        }
    };
    final Runnable l = new Runnable() { // from class: androidx.room.f.4
        @Override // java.lang.Runnable
        public void run() {
            f.this.d.removeObserver(f.this.e);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(Context context, String str, InvalidationTracker invalidationTracker, Executor executor) {
        this.a = context.getApplicationContext();
        this.b = str;
        this.d = invalidationTracker;
        this.g = executor;
        this.e = new InvalidationTracker.Observer((String[]) invalidationTracker.a.keySet().toArray(new String[0])) { // from class: androidx.room.f.5
            @Override // androidx.room.InvalidationTracker.Observer
            boolean a() {
                return true;
            }

            @Override // androidx.room.InvalidationTracker.Observer
            public void onInvalidated(@NonNull Set<String> set) {
                if (!f.this.i.get()) {
                    try {
                        IMultiInstanceInvalidationService iMultiInstanceInvalidationService = f.this.f;
                        if (iMultiInstanceInvalidationService != null) {
                            iMultiInstanceInvalidationService.broadcastInvalidation(f.this.c, (String[]) set.toArray(new String[0]));
                        }
                    } catch (RemoteException e) {
                        Log.w("ROOM", "Cannot broadcast invalidation", e);
                    }
                }
            }
        };
        this.a.bindService(new Intent(this.a, MultiInstanceInvalidationService.class), this.j, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        if (this.i.compareAndSet(false, true)) {
            this.d.removeObserver(this.e);
            try {
                IMultiInstanceInvalidationService iMultiInstanceInvalidationService = this.f;
                if (iMultiInstanceInvalidationService != null) {
                    iMultiInstanceInvalidationService.unregisterCallback(this.h, this.c);
                }
            } catch (RemoteException e) {
                Log.w("ROOM", "Cannot unregister multi-instance invalidation callback", e);
            }
            this.a.unbindService(this.j);
        }
    }
}
