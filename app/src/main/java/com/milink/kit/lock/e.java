package com.milink.kit.lock;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.milink.base.utils.Logger;
import com.milink.base.utils.Sugar;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: MiLinkLockDefault.java */
/* loaded from: classes2.dex */
class e implements MiLinkLock {
    private final d a;
    private final MiLinkLockCallback b;
    private final Executor c;
    private final Set<MiLinkLock> d;
    private final Context e;
    private boolean f;
    private boolean g;
    private WeakReference<LockStatusListener> h = new WeakReference<>(null);

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Context context, d dVar, MiLinkLockCallback miLinkLockCallback, Executor executor, Set<MiLinkLock> set) {
        this.e = context;
        this.a = dVar;
        this.b = miLinkLockCallback;
        this.c = executor;
        this.d = set;
    }

    @Override // com.milink.kit.lock.MiLinkLock
    @NonNull
    public String uri() {
        return this.a.a;
    }

    @Override // com.milink.kit.lock.MiLinkLock
    public int requestLock(long j) {
        Logger.d("MiLinkLockDefault", "request lock = %s", this.a.a);
        final MiLinkLockCallback miLinkLockCallback = this.b;
        if (miLinkLockCallback == null) {
            return -1;
        }
        this.c.execute(new Runnable() { // from class: com.milink.kit.lock.-$$Lambda$e$Z9b6uxiHtSLcZKZV5ykhvgPUolg
            @Override // java.lang.Runnable
            public final void run() {
                e.this.d(miLinkLockCallback);
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(MiLinkLockCallback miLinkLockCallback) {
        Sugar.eat(miLinkLockCallback, new Sugar.FuncV1() { // from class: com.milink.kit.lock.-$$Lambda$e$7_cgTnnEAtiiiVrF_L2gKTtdPW0
            @Override // com.milink.base.utils.Sugar.FuncV1
            public final void apply(Object obj) {
                e.this.e((MiLinkLockCallback) obj);
            }
        });
        LockStatusListener lockStatusListener = this.h.get();
        LockHolder currentLockHolder = getCurrentLockHolder();
        if (lockStatusListener != null && !Objects.equals(currentLockHolder.tag(), this.a.d)) {
            Sugar.eat(lockStatusListener, new Sugar.FuncV1() { // from class: com.milink.kit.lock.-$$Lambda$e$QW2_Vaf-58RPjbYE6l1NaU2C8-s
                @Override // com.milink.base.utils.Sugar.FuncV1
                public final void apply(Object obj) {
                    e.this.b((LockStatusListener) obj);
                }
            });
        }
        this.g = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(MiLinkLockCallback miLinkLockCallback) throws Exception {
        miLinkLockCallback.onLockGranted(this.a.a, this.a.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(LockStatusListener lockStatusListener) throws Exception {
        lockStatusListener.onLockGranted(this.a.b, this.a.c, this.e.getPackageName(), this.a.d);
    }

    @Override // com.milink.kit.lock.MiLinkLock
    public int requestUnlock() {
        Logger.d("MiLinkLockDefault", "request unlock = %s", this.a.a);
        final MiLinkLockCallback miLinkLockCallback = this.b;
        if (miLinkLockCallback == null) {
            return -1;
        }
        this.c.execute(new Runnable() { // from class: com.milink.kit.lock.-$$Lambda$e$uVAC84ZwJ3LJ_VBuTM5fZYf_540
            @Override // java.lang.Runnable
            public final void run() {
                e.this.a(miLinkLockCallback);
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MiLinkLockCallback miLinkLockCallback) {
        Sugar.eat(miLinkLockCallback, new Sugar.FuncV1() { // from class: com.milink.kit.lock.-$$Lambda$e$4H6_fggW_V9wUEU2fYLGyM2b55I
            @Override // com.milink.base.utils.Sugar.FuncV1
            public final void apply(Object obj) {
                e.this.c((MiLinkLockCallback) obj);
            }
        });
        Sugar.eat(miLinkLockCallback, new Sugar.FuncV1() { // from class: com.milink.kit.lock.-$$Lambda$e$gttnGZo4u1mRaBb3psumRRag0Jk
            @Override // com.milink.base.utils.Sugar.FuncV1
            public final void apply(Object obj) {
                e.this.b((MiLinkLockCallback) obj);
            }
        });
        LockStatusListener lockStatusListener = this.h.get();
        LockHolder currentLockHolder = getCurrentLockHolder();
        if (lockStatusListener != null && currentLockHolder.isNoneHolder()) {
            Sugar.eat(lockStatusListener, new Sugar.FuncV1() { // from class: com.milink.kit.lock.-$$Lambda$e$zh5zmXdj3eHV2BdI-HZiAagAi2k
                @Override // com.milink.base.utils.Sugar.FuncV1
                public final void apply(Object obj) {
                    e.this.a((LockStatusListener) obj);
                }
            });
        }
        this.g = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(MiLinkLockCallback miLinkLockCallback) throws Exception {
        miLinkLockCallback.onBeforeLockRevoke(this.a.a, this.a.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(MiLinkLockCallback miLinkLockCallback) throws Exception {
        miLinkLockCallback.onLockRevoked(this.a.a, this.a.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(LockStatusListener lockStatusListener) throws Exception {
        lockStatusListener.onLockGranted(this.a.b, this.a.c, "", "");
    }

    @Override // com.milink.kit.lock.MiLinkLock
    public void release() {
        Logger.d("MiLinkLockDefault", "release lock = %s", this.a.a);
        requestUnlock();
        synchronized (this.d) {
            this.d.remove(this);
            this.f = true;
        }
    }

    @Override // com.milink.kit.lock.MiLinkLock
    @NonNull
    public LockHolder getCurrentLockHolder() {
        Logger.d("MiLinkLockDefault", "getCurrentLockHolder = %s", this.a.a);
        if (this.g) {
            return new a(this.e.getPackageName(), (String) Objects.requireNonNull(this.a.d));
        }
        return new a("", "");
    }

    @Override // com.milink.kit.lock.MiLinkLock
    public void setWeakLockStatusListener(@Nullable LockStatusListener lockStatusListener) {
        boolean z = true;
        Object[] objArr = new Object[1];
        if (lockStatusListener == null) {
            z = false;
        }
        objArr[0] = Boolean.valueOf(z);
        Logger.d("MiLinkLockDefault", "set lock status listener: %s", objArr);
        this.h = new WeakReference<>(lockStatusListener);
    }

    @Override // com.milink.kit.lock.MiLinkLock
    public boolean isReleased() {
        return this.f;
    }

    @Override // com.milink.kit.lock.MiLinkLock
    @NonNull
    public String tag() {
        return this.a.d;
    }
}
