package com.milink.kit.lock;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.jackeywong.varhandle.WeakRefHolder;
import com.milink.base.contract.LockContract;
import com.milink.base.contract.MiLinkKeys;
import com.milink.base.utils.AndroidContextUtil;
import com.milink.base.utils.Logger;
import com.milink.base.utils.Sugar;
import com.milink.base.utils.Urn;
import com.milink.kit.exception.MiLinkRuntimeException;
import com.milink.kit.lock.c;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LockProviderImpl.java */
/* loaded from: classes2.dex */
public class c implements LockProvider {
    private final ExecutorService a;
    private final Set<MiLinkLock> b = new HashSet();

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(ExecutorService executorService) {
        this.a = (ExecutorService) Objects.requireNonNull(executorService);
    }

    private static d a(String str, String str2) {
        String[] a2 = a(str);
        if (TextUtils.isEmpty(str2) || str2.contains(StringUtils.SPACE)) {
            throw new IllegalStateException("tag can't be empty or have black space.");
        }
        d dVar = new d();
        dVar.a = str;
        dVar.b = a2[0];
        dVar.c = a2[1];
        dVar.d = str2;
        return dVar;
    }

    @NonNull
    private static String[] a(String str) {
        Urn parse = Urn.parse(str);
        if (parse.getNID().startsWith(LockContract.LOCK_NID)) {
            String nss = parse.getNSS();
            if (nss.indexOf(";") > -1) {
                nss = nss.split(";")[0];
            }
            if (!TextUtils.isEmpty(nss)) {
                String[] split = nss.split(Constants.COLON_SEPARATOR);
                if (split.length >= 2) {
                    return split;
                }
                throw new IllegalArgumentException("illegal lock : " + str);
            }
            throw new IllegalArgumentException("illegal lock : " + str);
        }
        throw new IllegalArgumentException("not support :" + str);
    }

    private static MiLinkLock a(@NonNull String str, String str2, @NonNull Set<MiLinkLock> set) {
        for (MiLinkLock miLinkLock : set) {
            if (Objects.equals(miLinkLock.uri(), Objects.requireNonNull(str)) && Objects.equals(miLinkLock.tag(), Objects.requireNonNull(str2))) {
                return miLinkLock;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(@NonNull Context context) {
        return AndroidContextUtil.isProviderExist(context, LockContract.AUTHORITY);
    }

    @Override // com.milink.kit.lock.LockProvider
    @NonNull
    public MiLinkLock requireLock(@NonNull Context context, @NonNull String str, @NonNull String str2, @NonNull MiLinkLockCallback miLinkLockCallback) {
        MiLinkLock miLinkLock;
        if (getLock(str, str2) == null) {
            boolean b = b(context);
            d a2 = a(str, str2);
            MiLinkLockCallback miLinkLockCallback2 = (MiLinkLockCallback) Objects.requireNonNull(miLinkLockCallback);
            if (b) {
                miLinkLock = new a((Context) Objects.requireNonNull(context), a2, miLinkLockCallback2, this.a);
            } else {
                Logger.w("MiLinkLockClient", "not exist lock-server, use default lock", new Object[0]);
                miLinkLock = new e(context, a2, miLinkLockCallback2, this.a, this.b);
            }
            synchronized (this.b) {
                if (!this.b.add(miLinkLock)) {
                    throw new IllegalStateException("already exist lock " + str);
                }
            }
            return miLinkLock;
        }
        throw new IllegalStateException("already exist lock " + str);
    }

    @Override // com.milink.kit.lock.LockProvider
    @Nullable
    public MiLinkLock getLock(@NonNull String str, @NonNull String str2) {
        MiLinkLock a2;
        synchronized (this.b) {
            a2 = a(str, str2, this.b);
        }
        return a2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LockProviderImpl.java */
    /* loaded from: classes2.dex */
    public class a implements MiLinkLock {
        private final d b;
        private final ContentResolver c;
        private final MiLinkLockCallback d;
        private final ExecutorService f;
        private final Handler g;
        private final Context h;
        private final ContentObserver j;
        private Runnable k;
        private volatile boolean l;
        @Nullable
        private ContentObserver m;
        private volatile boolean n;
        private volatile boolean o;
        private Runnable p;
        private final Object e = new Object();
        private final WeakRefHolder<LockStatusListener> i = new WeakRefHolder<>();

        a(Context context, @NonNull d dVar, @NonNull MiLinkLockCallback miLinkLockCallback, @NonNull ExecutorService executorService) {
            this.h = context;
            this.b = dVar;
            this.c = context.getContentResolver();
            this.d = (MiLinkLockCallback) Objects.requireNonNull(miLinkLockCallback);
            this.f = executorService;
            HandlerThread handlerThread = new HandlerThread("MiLinkLockClient");
            handlerThread.start();
            this.g = new Handler(handlerThread.getLooper(), new C0130a());
            this.j = new C0131c(this.g);
        }

        @Override // com.milink.kit.lock.MiLinkLock
        @NonNull
        public String uri() {
            return this.b.a;
        }

        @Override // com.milink.kit.lock.MiLinkLock
        @WorkerThread
        public int requestLock(long j) {
            int i;
            Logger.d("MiLinkLockClient", "request lock = %s", this.b.a);
            if (!c.b(this.h)) {
                Logger.w("MiLinkLockClient", "request lock but lock server not found", new Object[0]);
                return -2;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(LockContract.COL_LOCK_NAME, this.b.c);
            contentValues.put(LockContract.COL_LOCK_SCOPE, this.b.b);
            contentValues.put(LockContract.COL_TAG, this.b.d);
            Uri lockUri = LockContract.Matcher.getLockUri(this.b.b, this.b.c);
            synchronized (this.e) {
                if (this.n) {
                    Logger.w("MiLinkLockClient", "request lock but lock already released", new Object[0]);
                    return 1;
                }
                f();
                int i2 = 0;
                do {
                    Uri insert = this.c.insert(lockUri, contentValues);
                    i = -1;
                    if (insert != null) {
                        if (i2 > 0) {
                            SystemClock.sleep((i2 * i2 * 30) + 80);
                        }
                        try {
                            i = Integer.parseInt(insert.getQueryParameter("code"));
                        } catch (NumberFormatException unused) {
                            Logger.e("MiLinkLockClient", "acquire lock not return result code: %s", insert);
                        }
                        i2++;
                        if (i != 3) {
                            break;
                        }
                    } else {
                        return -1;
                    }
                } while (i2 < 3);
                if (i == 0) {
                    this.o = true;
                    h();
                }
                if (j > 0) {
                    this.g.removeMessages(5);
                    this.g.sendEmptyMessageDelayed(5, j);
                }
                Logger.w("MiLinkLockClient", "request lock result = %s", Integer.valueOf(i));
                return i;
            }
        }

        @Override // com.milink.kit.lock.MiLinkLock
        @WorkerThread
        public int requestUnlock() {
            Logger.d("MiLinkLockClient", "request unlock = %s", this.b.a);
            if (!c.b(this.h)) {
                Logger.w("MiLinkLockClient", "request unlock but lock server not found", new Object[0]);
                return -2;
            }
            synchronized (this.e) {
                if (this.n) {
                    Logger.w("MiLinkLockClient", "request unlock but lock already released", new Object[0]);
                    return 1;
                }
                return j();
            }
        }

        @Override // com.milink.kit.lock.MiLinkLock
        public void release() {
            if (c.b(this.h)) {
                requestUnlock();
                synchronized (c.this.b) {
                    c.this.b.remove(this);
                    this.n = true;
                    g();
                    this.g.removeCallbacksAndMessages(null);
                }
            }
        }

        @Override // com.milink.kit.lock.MiLinkLock
        @NonNull
        @WorkerThread
        public LockHolder getCurrentLockHolder() {
            Bundle call = this.c.call(LockContract.Matcher.ROOT_URI, LockContract.Action.LOCK_STATUS, this.b.a, (Bundle) null);
            if (call == null) {
                throw new IllegalStateException("get current lock holder, but result is null");
            } else if (!call.isEmpty()) {
                String string = call.getString(MiLinkKeys.PARAM_IDENTIFY);
                String string2 = call.getString(MiLinkKeys.PARAM_TAG);
                if (string != null && string2 != null) {
                    return new a(string, string2);
                }
                throw new IllegalStateException("get current lock holder, but identify or tag is null");
            } else {
                throw new MiLinkRuntimeException(-2, "not support");
            }
        }

        @Override // com.milink.kit.lock.MiLinkLock
        public void setWeakLockStatusListener(@Nullable LockStatusListener lockStatusListener) {
            if (lockStatusListener != this.i.get()) {
                synchronized (this.i) {
                    if (lockStatusListener != null) {
                        try {
                            if (this.i.get() == null) {
                                this.c.registerContentObserver(LockContract.Matcher.getLockStatusChangeUri(this.b.b, this.b.c), false, this.j);
                                this.p = new Runnable() { // from class: com.milink.kit.lock.c.a.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (a.this.p != null) {
                                            if (!a.this.l) {
                                                boolean z = true;
                                                Object[] objArr = new Object[1];
                                                if (a.this.c.getType(LockContract.Matcher.ROOT_URI) == null) {
                                                    z = false;
                                                }
                                                objArr[0] = Boolean.valueOf(z);
                                                Logger.d("MiLinkLockClient", "wakeup lock server: %s", objArr);
                                            }
                                            a.this.g.postDelayed(this, 30000L);
                                        }
                                    }
                                };
                                this.g.postDelayed(this.p, 30000L);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (lockStatusListener == null && this.i.get() != null) {
                        this.g.removeCallbacks(this.p);
                        this.p = null;
                        this.c.unregisterContentObserver(this.j);
                    }
                    this.i.set(lockStatusListener);
                }
            }
        }

        @Override // com.milink.kit.lock.MiLinkLock
        public boolean isReleased() {
            return this.n;
        }

        @Override // com.milink.kit.lock.MiLinkLock
        @NonNull
        public String tag() {
            return this.b.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final Uri uri) {
            final String queryParameter = uri.getQueryParameter(MiLinkKeys.PARAM_IDENTIFY);
            final String queryParameter2 = uri.getQueryParameter(MiLinkKeys.PARAM_TAG);
            if (queryParameter == null || queryParameter2 == null) {
                Logger.w("MiLinkLockClient", "ask identify or tag is null, %s", uri);
                return;
            }
            final MiLinkLockCallback miLinkLockCallback = this.d;
            if (miLinkLockCallback != null) {
                this.f.execute(new Runnable() { // from class: com.milink.kit.lock.-$$Lambda$c$a$9BhyaLiYpdsZMRaljhFamn4i7AY
                    @Override // java.lang.Runnable
                    public final void run() {
                        c.a.this.a(miLinkLockCallback, queryParameter2, queryParameter, uri);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(final MiLinkLockCallback miLinkLockCallback, final String str, final String str2, Uri uri) {
            Boolean bool = (Boolean) a(new Callable() { // from class: com.milink.kit.lock.-$$Lambda$c$a$H41N0fgySMFsdfy9m8KWlWYaIHw
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Boolean a;
                    a = c.a.this.a(miLinkLockCallback, str, str2);
                    return a;
                }
            }, 60, "ask for lock");
            if (!this.o || this.n) {
                Logger.d("MiLinkLockClient", "not acquire lock, ask for lock callback interrupt!", new Object[0]);
            } else if (bool == null || bool.booleanValue()) {
                a(str2, str);
            } else {
                a(uri, str2, str);
                Logger.d("MiLinkLockClient", "%s not accept unlock to %s - %s", this.b.d, str2, str);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Boolean a(final MiLinkLockCallback miLinkLockCallback, final String str, final String str2) throws Exception {
            return (Boolean) Sugar.eat(new Sugar.Func0() { // from class: com.milink.kit.lock.-$$Lambda$c$a$4Bea-jGaDeZREZFLQBbFy2LoVgI
                @Override // com.milink.base.utils.Sugar.Func0
                public final Object apply() {
                    Boolean b2;
                    b2 = c.a.this.b(miLinkLockCallback, str, str2);
                    return b2;
                }
            }, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Boolean b(MiLinkLockCallback miLinkLockCallback, String str, String str2) throws Exception {
            return Boolean.valueOf(miLinkLockCallback.onAcceptUnlock(this.b.a, str, str2));
        }

        private void a(Uri uri, String str, String str2) {
            this.c.update(uri.buildUpon().clearQuery().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.ASK_FOR_LOCK_REJECT).appendQueryParameter(MiLinkKeys.PARAM_TAG, this.b.d).appendQueryParameter(MiLinkKeys.PARAM_TO_IDENTIFY, str).appendQueryParameter(MiLinkKeys.PARAM_TO_TAG, str2).build(), new ContentValues(), null, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(Uri uri) {
            a(new Callable() { // from class: com.milink.kit.lock.-$$Lambda$c$a$Iod6mhUgqvVYWVY5VSaLISBtvZY
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Object q;
                    q = c.a.this.q();
                    return q;
                }
            }, 6, "before revoke lock");
            if (!a(uri, (ContentValues) null)) {
                Logger.w("MiLinkLockClient", "before revoke lock callback reply fail, ignore it", new Object[0]);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(MiLinkLockCallback miLinkLockCallback) throws Exception {
            miLinkLockCallback.onBeforeLockRevoke(this.b.a, this.b.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Object q() throws Exception {
            Sugar.eat(this.d, new Sugar.FuncV1() { // from class: com.milink.kit.lock.-$$Lambda$c$a$PnEWj2xYZwOoc0QyV05er59L1og
                @Override // com.milink.base.utils.Sugar.FuncV1
                public final void apply(Object obj) {
                    c.a.this.a((MiLinkLockCallback) obj);
                }
            });
            return null;
        }

        private boolean a(@NonNull Uri uri, @Nullable ContentValues contentValues) {
            String queryParameter = uri.getQueryParameter(MiLinkKeys.PARAM_REPLAY_ID);
            if (TextUtils.isEmpty(queryParameter)) {
                Logger.d("MiLinkLockClient", "not have reply id, do nothing for %s", uri);
                return false;
            }
            Uri build = uri.buildUpon().clearQuery().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.CLIENT_REPLY).appendQueryParameter(MiLinkKeys.PARAM_REPLAY_ID, queryParameter).build();
            ContentResolver contentResolver = this.c;
            if (contentValues == null) {
                contentValues = new ContentValues();
            }
            boolean z = contentResolver.update(build, contentValues, null, null) == 0;
            if (!z) {
                Logger.w("MiLinkLockClient", "reply not succ for %s, %s", queryParameter, uri);
            }
            return z;
        }

        @Nullable
        private <T> T a(@NonNull Callable<T> callable, int i, @NonNull String str) {
            try {
                return this.f.submit(callable).get(i, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                Logger.e("MiLinkLockClient", "perform %s interrupted. %s", str, this.b.a);
                return null;
            } catch (ExecutionException e) {
                Logger.e("MiLinkLockClient", "perform %s exception happen. %s, %s", str, this.b.a, e.getCause());
                return null;
            } catch (TimeoutException unused2) {
                Logger.w("MiLinkLockClient", "perform %s timeout. %s", str, this.b.a);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            this.f.execute(new Runnable() { // from class: com.milink.kit.lock.-$$Lambda$c$a$85nYjXyKTW_SLnDM7fJ4Mqw1R3Y
                @Override // java.lang.Runnable
                public final void run() {
                    c.a.this.o();
                }
            });
            synchronized (this.e) {
                i();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void o() {
            Sugar.eat(new Sugar.FuncV() { // from class: com.milink.kit.lock.-$$Lambda$c$a$kzPPuErEWn7O3E5-3qn8Nntnv1Q
                @Override // com.milink.base.utils.Sugar.FuncV
                public final void apply() {
                    c.a.this.p();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void p() throws Exception {
            this.d.onLockRevoked(this.b.a, this.b.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            this.g.removeMessages(5);
            this.f.execute(new Runnable() { // from class: com.milink.kit.lock.-$$Lambda$c$a$ohAXb4AakmYR14bbIZY7kPxYduk
                @Override // java.lang.Runnable
                public final void run() {
                    c.a.this.m();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void m() {
            Sugar.eat(new Sugar.FuncV() { // from class: com.milink.kit.lock.-$$Lambda$c$a$6CzXuklApph8oaRs8pkTPS7Ttes
                @Override // com.milink.base.utils.Sugar.FuncV
                public final void apply() {
                    c.a.this.n();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void n() throws Exception {
            this.d.onLockGranted(this.b.a, this.b.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c() {
            if (this.g.hasMessages(1)) {
                Logger.i("MiLinkLockClient", "already granted lock, so reject timeout task.", new Object[0]);
                return;
            }
            int j = j();
            if (j != 0) {
                Logger.w("MiLinkLockClient", "revoke request lock cause request denied, but cancel lock request fail(%s): %s - %s", Integer.valueOf(j), this.b.a, this.b.d);
            } else {
                d();
            }
        }

        private void d() {
            this.f.execute(new Runnable() { // from class: com.milink.kit.lock.-$$Lambda$c$a$kyavWu2ZBIYwSF5ZE-lcDEwLPPQ
                @Override // java.lang.Runnable
                public final void run() {
                    c.a.this.k();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void k() {
            Sugar.eat(new Sugar.FuncV() { // from class: com.milink.kit.lock.-$$Lambda$c$a$NAHLnGk9TshiYZKXeJm72vVxxfQ
                @Override // com.milink.base.utils.Sugar.FuncV
                public final void apply() {
                    c.a.this.l();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void l() throws Exception {
            this.d.onRequestLockDenied(this.b.a, this.b.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            if (this.g.hasMessages(5)) {
                this.g.removeMessages(5);
                Handler handler = this.g;
                handler.sendMessageAtFrontOfQueue(Message.obtain(handler, 5));
            }
        }

        private void f() {
            if (this.m == null) {
                Uri lockUriWithIdentify = LockContract.Matcher.getLockUriWithIdentify(this.b.b, this.b.c, this.h.getPackageName(), this.b.d);
                this.m = new b(this.g, this.h.getPackageName());
                this.c.registerContentObserver(lockUriWithIdentify, false, this.m);
                this.c.registerContentObserver(LockContract.Matcher.getTickUri(this.b.b, this.b.c), false, this.m);
                Logger.d("MiLinkLockClient", "start observer event", new Object[0]);
            }
        }

        private void g() {
            ContentObserver contentObserver = this.m;
            if (contentObserver != null) {
                this.c.unregisterContentObserver(contentObserver);
                this.m = null;
                Logger.d("MiLinkLockClient", "stop observer event", new Object[0]);
            }
        }

        private void h() {
            if (this.k == null) {
                Bundle call = this.c.call(LockContract.Matcher.ROOT_URI, LockContract.Action.TICK_INFO, (String) null, (Bundle) null);
                long j = 12000;
                if (call != null) {
                    j = call.getLong(MiLinkKeys.PARAM_INTERVAL, 12000L) + 5000;
                } else {
                    Logger.w("MiLinkLockClient", "get tick_info fail, interval default: %s", 12000L);
                }
                this.k = new d(j);
                this.g.postDelayed(this.k, j);
                Logger.d("MiLinkLockClient", "start interval request lock", new Object[0]);
            }
        }

        private void i() {
            Runnable runnable = this.k;
            if (runnable != null) {
                this.g.removeCallbacks(runnable);
                this.k = null;
                Logger.d("MiLinkLockClient", "stop interval request lock", new Object[0]);
            }
        }

        private int j() {
            int delete;
            synchronized (this.e) {
                delete = this.c.delete(LockContract.Matcher.getLockUri(this.b.b, this.b.c).buildUpon().appendQueryParameter(MiLinkKeys.PARAM_TAG, this.b.d).build(), null, null);
                if (delete == 0) {
                    this.o = false;
                    i();
                }
                Logger.d("MiLinkLockClient", "revoke lock result = %s", Integer.valueOf(delete));
            }
            return delete;
        }

        private void a(String str, String str2) {
            int delete = this.c.delete(LockContract.Matcher.getLockUri(this.b.b, this.b.c).buildUpon().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.LOCK_TRANSFER).appendQueryParameter(MiLinkKeys.PARAM_TAG, this.b.d).appendQueryParameter(MiLinkKeys.PARAM_TO_IDENTIFY, str).appendQueryParameter(MiLinkKeys.PARAM_TO_TAG, str2).build(), null, null);
            if (delete == 0) {
                this.o = false;
            } else {
                Logger.w("MiLinkLockClient", "ask for release fail, code : %s", Integer.valueOf(delete));
            }
        }

        /* compiled from: LockProviderImpl.java */
        /* renamed from: com.milink.kit.lock.c$a$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        class C0130a implements Handler.Callback {
            C0130a() {
            }

            @Override // android.os.Handler.Callback
            public boolean handleMessage(@NonNull Message message) {
                Uri uri = (Uri) message.obj;
                switch (message.what) {
                    case 1:
                        a.this.b();
                        return true;
                    case 2:
                        a.this.b(uri);
                        return true;
                    case 3:
                        a.this.a(uri);
                        return true;
                    case 4:
                        a.this.a();
                        return true;
                    case 5:
                        a.this.c();
                        return true;
                    case 6:
                        a.this.e();
                        return true;
                    default:
                        return false;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: LockProviderImpl.java */
        /* loaded from: classes2.dex */
        public class b extends ContentObserver {
            private final String b;

            b(Handler handler, String str) {
                super(handler);
                this.b = str;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z, @Nullable Uri uri) {
                if (uri == null) {
                    Logger.w("MiLinkLockClient", "onchange uri is null, %s : %s", a.this.b.d, a.this.b.a);
                } else {
                    a(uri);
                }
            }

            private void a(@NonNull Uri uri) {
                Uri convertToRequestUri = LockContract.Matcher.convertToRequestUri(uri, this.b, a.this.b.d);
                Logger.d("MiLinkLockClient", "receive event: %s", uri);
                switch (LockContract.Matcher.match(convertToRequestUri)) {
                    case 1:
                        a.this.l = true;
                        if (a.this.o) {
                            a.this.c.update(convertToRequestUri.buildUpon().appendQueryParameter(MiLinkKeys.PARAM_TAG, a.this.b.d).build(), new ContentValues(), null, null);
                            return;
                        } else {
                            a.this.requestUnlock();
                            return;
                        }
                    case 2:
                        String queryParameter = convertToRequestUri.getQueryParameter(MiLinkKeys.PARAM_EVENT);
                        if (queryParameter != null) {
                            a(queryParameter, convertToRequestUri);
                            return;
                        } else {
                            Logger.w("MiLinkLockClient", "not found lock action for: %s", convertToRequestUri);
                            return;
                        }
                    default:
                        Logger.w("MiLinkLockClient", "unknown change uri: %s", uri);
                        return;
                }
            }

            private void a(@NonNull String str, Uri uri) {
                synchronized (a.this.e) {
                    char c = 65535;
                    switch (str.hashCode()) {
                        case 145028583:
                            if (str.equals(LockContract.Event.ASK_FOR_LOCK)) {
                                c = 3;
                                break;
                            }
                            break;
                        case 280295099:
                            if (str.equals(LockContract.Event.LOCK_GRANTED)) {
                                c = 0;
                                break;
                            }
                            break;
                        case 1100137118:
                            if (str.equals(LockContract.Event.LOCK_REVOKED)) {
                                c = 2;
                                break;
                            }
                            break;
                        case 1367403140:
                            if (str.equals(LockContract.Event.LOCK_REVOKE_BEFORE)) {
                                c = 1;
                                break;
                            }
                            break;
                        case 1555454199:
                            if (str.equals(LockContract.Event.ASK_FOR_LOCK_REJECT)) {
                                c = 4;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            a.this.g.obtainMessage(1, uri).sendToTarget();
                            break;
                        case 1:
                            a.this.g.obtainMessage(2, uri).sendToTarget();
                            break;
                        case 2:
                            a.this.g.obtainMessage(4, uri).sendToTarget();
                            break;
                        case 3:
                            a.this.g.obtainMessage(3, uri).sendToTarget();
                            break;
                        case 4:
                            a.this.g.obtainMessage(6, uri).sendToTarget();
                            break;
                        default:
                            Logger.w("MiLinkLockClient", "unknown action, %s : %s", a.this.b.d, uri);
                            break;
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: LockProviderImpl.java */
        /* loaded from: classes2.dex */
        public class d implements Runnable {
            private final long b;

            public d(long j) {
                this.b = j;
            }

            @Override // java.lang.Runnable
            public void run() {
                boolean z = a.this.l;
                a.this.l = false;
                if (z || a.this.k == null || a.this.requestLock() != 0) {
                    a.this.g.postDelayed(this, this.b);
                }
            }
        }

        /* compiled from: LockProviderImpl.java */
        /* renamed from: com.milink.kit.lock.c$a$c  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        private class C0131c extends ContentObserver {
            public C0131c(Handler handler) {
                super(handler);
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z, @Nullable Uri uri) {
                super.onChange(z);
                if (uri == null) {
                    Logger.e("MiLinkLockClient", "Can't reach!!!, lock status change uri is null", new Object[0]);
                    return;
                }
                LockStatusListener lockStatusListener = (LockStatusListener) a.this.i.get();
                if (lockStatusListener != null) {
                    final String queryParameter = uri.getQueryParameter(MiLinkKeys.PARAM_TAG);
                    final String queryParameter2 = uri.getQueryParameter(MiLinkKeys.PARAM_IDENTIFY);
                    Sugar.eat(lockStatusListener, new Sugar.FuncV1() { // from class: com.milink.kit.lock.-$$Lambda$c$a$c$-82-jEX-qN5gBIiwy2dupNUJdfA
                        @Override // com.milink.base.utils.Sugar.FuncV1
                        public final void apply(Object obj) {
                            c.a.C0131c.this.a(queryParameter2, queryParameter, (LockStatusListener) obj);
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void a(String str, String str2, LockStatusListener lockStatusListener) throws Exception {
                String str3 = a.this.b.b;
                String str4 = a.this.b.c;
                if (str == null) {
                    str = "";
                }
                if (str2 == null) {
                    str2 = "";
                }
                lockStatusListener.onLockGranted(str3, str4, str, str2);
            }
        }
    }
}
