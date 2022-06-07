package androidx.core.app;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
/* loaded from: classes.dex */
public abstract class JobIntentService extends Service {
    static final Object h = new Object();
    static final HashMap<ComponentName, h> i = new HashMap<>();
    b a;
    h b;
    a c;
    boolean d = false;
    boolean e = false;
    boolean f = false;
    final ArrayList<d> g;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface b {
        IBinder a();

        e b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface e {
        Intent a();

        void b();
    }

    protected abstract void onHandleWork(@NonNull Intent intent);

    public boolean onStopCurrentWork() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static abstract class h {
        final ComponentName c;
        boolean d;
        int e;

        public void a() {
        }

        abstract void a(Intent intent);

        public void b() {
        }

        public void c() {
        }

        h(ComponentName componentName) {
            this.c = componentName;
        }

        void a(int i) {
            if (!this.d) {
                this.d = true;
                this.e = i;
            } else if (this.e != i) {
                throw new IllegalArgumentException("Given job ID " + i + " is different than previous " + this.e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class c extends h {
        boolean a;
        boolean b;
        private final Context f;
        private final PowerManager.WakeLock g;
        private final PowerManager.WakeLock h;

        c(Context context, ComponentName componentName) {
            super(componentName);
            this.f = context.getApplicationContext();
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            this.g = powerManager.newWakeLock(1, componentName.getClassName() + ":launch");
            this.g.setReferenceCounted(false);
            this.h = powerManager.newWakeLock(1, componentName.getClassName() + ":run");
            this.h.setReferenceCounted(false);
        }

        @Override // androidx.core.app.JobIntentService.h
        void a(Intent intent) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(this.c);
            if (this.f.startService(intent2) != null) {
                synchronized (this) {
                    if (!this.a) {
                        this.a = true;
                        if (!this.b) {
                            this.g.acquire(60000L);
                        }
                    }
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.h
        public void a() {
            synchronized (this) {
                this.a = false;
            }
        }

        @Override // androidx.core.app.JobIntentService.h
        public void b() {
            synchronized (this) {
                if (!this.b) {
                    this.b = true;
                    this.h.acquire(600000L);
                    this.g.release();
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.h
        public void c() {
            synchronized (this) {
                if (this.b) {
                    if (this.a) {
                        this.g.acquire(60000L);
                    }
                    this.b = false;
                    this.h.release();
                }
            }
        }
    }

    @RequiresApi(26)
    /* loaded from: classes.dex */
    static final class f extends JobServiceEngine implements b {
        final JobIntentService a;
        final Object b = new Object();
        JobParameters c;

        /* loaded from: classes.dex */
        final class a implements e {
            final JobWorkItem a;

            a(JobWorkItem jobWorkItem) {
                this.a = jobWorkItem;
            }

            @Override // androidx.core.app.JobIntentService.e
            public Intent a() {
                return this.a.getIntent();
            }

            @Override // androidx.core.app.JobIntentService.e
            public void b() {
                synchronized (f.this.b) {
                    if (f.this.c != null) {
                        f.this.c.completeWork(this.a);
                    }
                }
            }
        }

        f(JobIntentService jobIntentService) {
            super(jobIntentService);
            this.a = jobIntentService;
        }

        @Override // androidx.core.app.JobIntentService.b
        public IBinder a() {
            return getBinder();
        }

        @Override // android.app.job.JobServiceEngine
        public boolean onStartJob(JobParameters jobParameters) {
            this.c = jobParameters;
            this.a.a(false);
            return true;
        }

        @Override // android.app.job.JobServiceEngine
        public boolean onStopJob(JobParameters jobParameters) {
            boolean a2 = this.a.a();
            synchronized (this.b) {
                this.c = null;
            }
            return a2;
        }

        @Override // androidx.core.app.JobIntentService.b
        public e b() {
            synchronized (this.b) {
                if (this.c == null) {
                    return null;
                }
                JobWorkItem dequeueWork = this.c.dequeueWork();
                if (dequeueWork == null) {
                    return null;
                }
                dequeueWork.getIntent().setExtrasClassLoader(this.a.getClassLoader());
                return new a(dequeueWork);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(26)
    /* loaded from: classes.dex */
    public static final class g extends h {
        private final JobInfo a;
        private final JobScheduler b;

        g(Context context, ComponentName componentName, int i) {
            super(componentName);
            a(i);
            this.a = new JobInfo.Builder(i, this.c).setOverrideDeadline(0L).build();
            this.b = (JobScheduler) context.getApplicationContext().getSystemService("jobscheduler");
        }

        @Override // androidx.core.app.JobIntentService.h
        void a(Intent intent) {
            this.b.enqueue(this.a, new JobWorkItem(intent));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class d implements e {
        final Intent a;
        final int b;

        d(Intent intent, int i) {
            this.a = intent;
            this.b = i;
        }

        @Override // androidx.core.app.JobIntentService.e
        public Intent a() {
            return this.a;
        }

        @Override // androidx.core.app.JobIntentService.e
        public void b() {
            JobIntentService.this.stopSelf(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class a extends AsyncTask<Void, Void, Void> {
        a() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Void doInBackground(Void... voidArr) {
            while (true) {
                e c = JobIntentService.this.c();
                if (c == null) {
                    return null;
                }
                JobIntentService.this.onHandleWork(c.a());
                c.b();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public void onCancelled(Void r1) {
            JobIntentService.this.b();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public void onPostExecute(Void r1) {
            JobIntentService.this.b();
        }
    }

    public JobIntentService() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.g = null;
        } else {
            this.g = new ArrayList<>();
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            this.a = new f(this);
            this.b = null;
            return;
        }
        this.a = null;
        this.b = a(this, new ComponentName(this, getClass()), false, 0);
    }

    @Override // android.app.Service
    public int onStartCommand(@Nullable Intent intent, int i2, int i3) {
        if (this.g == null) {
            return 2;
        }
        this.b.a();
        synchronized (this.g) {
            ArrayList<d> arrayList = this.g;
            if (intent == null) {
                intent = new Intent();
            }
            arrayList.add(new d(intent, i3));
            a(true);
        }
        return 3;
    }

    @Override // android.app.Service
    public IBinder onBind(@NonNull Intent intent) {
        b bVar = this.a;
        if (bVar != null) {
            return bVar.a();
        }
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        ArrayList<d> arrayList = this.g;
        if (arrayList != null) {
            synchronized (arrayList) {
                this.f = true;
                this.b.c();
            }
        }
    }

    public static void enqueueWork(@NonNull Context context, @NonNull Class<?> cls, int i2, @NonNull Intent intent) {
        enqueueWork(context, new ComponentName(context, cls), i2, intent);
    }

    public static void enqueueWork(@NonNull Context context, @NonNull ComponentName componentName, int i2, @NonNull Intent intent) {
        if (intent != null) {
            synchronized (h) {
                h a2 = a(context, componentName, true, i2);
                a2.a(i2);
                a2.a(intent);
            }
            return;
        }
        throw new IllegalArgumentException("work must not be null");
    }

    static h a(Context context, ComponentName componentName, boolean z, int i2) {
        h hVar = i.get(componentName);
        if (hVar == null) {
            if (Build.VERSION.SDK_INT < 26) {
                hVar = new c(context, componentName);
            } else if (z) {
                hVar = new g(context, componentName, i2);
            } else {
                throw new IllegalArgumentException("Can't be here without a job id");
            }
            i.put(componentName, hVar);
        }
        return hVar;
    }

    public void setInterruptIfStopped(boolean z) {
        this.d = z;
    }

    public boolean isStopped() {
        return this.e;
    }

    boolean a() {
        a aVar = this.c;
        if (aVar != null) {
            aVar.cancel(this.d);
        }
        this.e = true;
        return onStopCurrentWork();
    }

    void a(boolean z) {
        if (this.c == null) {
            this.c = new a();
            h hVar = this.b;
            if (hVar != null && z) {
                hVar.b();
            }
            this.c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    void b() {
        ArrayList<d> arrayList = this.g;
        if (arrayList != null) {
            synchronized (arrayList) {
                this.c = null;
                if (this.g != null && this.g.size() > 0) {
                    a(false);
                } else if (!this.f) {
                    this.b.c();
                }
            }
        }
    }

    e c() {
        b bVar = this.a;
        if (bVar != null) {
            return bVar.b();
        }
        synchronized (this.g) {
            if (this.g.size() <= 0) {
                return null;
            }
            return this.g.remove(0);
        }
    }
}
