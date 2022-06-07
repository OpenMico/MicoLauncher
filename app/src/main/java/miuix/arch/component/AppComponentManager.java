package miuix.arch.component;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.ArrayMap;
import android.util.Log;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.onetrack.a.a;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class AppComponentManager {
    private static final Map<String, AppComponentManager> a = new ArrayMap();
    private static int b;
    private static String c;
    private final Map<String, AppComponentDelegate<?>> d;
    private final Map<Integer, List<Task>> e;
    private final Map<Integer, Map<Integer, List<Task>>> f;
    private final Map<Integer, List<CountDownLatch>> g = new ConcurrentHashMap();
    private final Map<String, Task> h;
    private Executor i;
    private boolean j;

    static /* synthetic */ int b() {
        int i = b + 1;
        b = i;
        return i;
    }

    public static AppComponentManager getDefault() {
        synchronized (a) {
            if (c != null) {
                return a.get(c);
            }
            Set<String> keySet = a.keySet();
            if (!keySet.isEmpty()) {
                c = ((String[]) keySet.toArray(new String[0]))[0];
                return a.get(c);
            }
            throw new IllegalStateException("not installed any AppComponentManager.");
        }
    }

    @Nullable
    public static AppComponentManager get(String str) {
        AppComponentManager appComponentManager;
        synchronized (a) {
            appComponentManager = a.get(Objects.requireNonNull(str));
        }
        return appComponentManager;
    }

    static boolean a(@NonNull Context context) {
        return context.getPackageName().equals(b(context));
    }

    static String b(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Application.getProcessName();
        }
        String d = d();
        return d != null ? d : c(context);
    }

    public static AppComponentManager[] a() {
        AppComponentManager[] appComponentManagerArr;
        synchronized (a) {
            appComponentManagerArr = (AppComponentManager[]) a.values().toArray(new AppComponentManager[0]);
        }
        return appComponentManagerArr;
    }

    public static void a(@NonNull ComponentConfiguration componentConfiguration) {
        synchronized (a) {
            String componentManagerDomain = componentConfiguration.getComponentManagerDomain();
            if (!a.containsKey(Objects.requireNonNull(componentManagerDomain))) {
                a.put(componentManagerDomain, new AppComponentManager(componentConfiguration));
            } else {
                throw new IllegalStateException(String.format("install component manager %s, but already exist.", componentManagerDomain));
            }
        }
    }

    @NonNull
    private static ExecutorService c() {
        int max = Math.max((Runtime.getRuntime().availableProcessors() >> 1) + 1, 2);
        b.a("P-Size : %s", Integer.valueOf(max));
        return a(max);
    }

    private static ExecutorService a(int i) {
        final int i2 = i >> 1;
        return new ThreadPoolExecutor(0, i, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: miuix.arch.component.AppComponentManager.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(@NonNull Runnable runnable) {
                return new Thread(runnable) { // from class: miuix.arch.component.AppComponentManager.1.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        if (AppComponentManager.b() <= i2) {
                            Process.setThreadPriority(0);
                        } else {
                            Process.setThreadPriority(10);
                        }
                        super.run();
                    }
                };
            }
        });
    }

    private static void a(Runnable runnable, String str, Object... objArr) {
        if (runnable != null) {
            try {
                runnable.run();
            } catch (Throwable th) {
                Log.e("miuix-app-comp", String.format(str, objArr), th);
            }
        }
    }

    private static String c(@NonNull Context context) {
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) Objects.requireNonNull((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY))).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    @Nullable
    @SuppressLint({"PrivateApi", "DiscouragedPrivateApi"})
    private static String d() {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                return (String) invoke;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private AppComponentManager(@NonNull ComponentConfiguration componentConfiguration) {
        Objects.requireNonNull(componentConfiguration);
        this.e = componentConfiguration.createMainComponentInitMap();
        this.f = componentConfiguration.createBackgroundComponentInitMap();
        this.d = componentConfiguration.createAppComponentDelegates();
        this.h = componentConfiguration.createOnEventTaskMap();
    }

    public boolean hasActiveComponent(@NonNull String str) {
        return this.d.containsKey(Objects.requireNonNull(str));
    }

    public final void sendBroadcastEvent(@NonNull String str) {
        sendBroadcastEvent(str, null);
    }

    public final void sendBroadcastEvent(@NonNull String str, @Nullable Bundle bundle) {
        a((String) Objects.requireNonNull(str), bundle);
    }

    @Nullable
    public final Bundle callMethod(@NonNull String str, @NonNull String str2, @Nullable Bundle bundle) throws ComponentException {
        AppComponentDelegate<?> appComponentDelegate = this.d.get(Objects.requireNonNull(str));
        if (appComponentDelegate == null) {
            b.b("not found method: %s-%s", str, str2);
            return null;
        }
        try {
            Bundle bundle2 = (Bundle) appComponentDelegate.callTask(AppCallbackMediator.a, (String) Objects.requireNonNull(str2), bundle);
            b.a("call method %s:%s", str, str2);
            return bundle2;
        } catch (Exception e) {
            b.a(e, "Error when call method %s:%s", str, str2);
            throw new ComponentException(e);
        }
    }

    public final void a(Application application, @NonNull a aVar) {
        if (aVar instanceof c) {
            a(application, (c) aVar);
        } else {
            sendBroadcastEvent(aVar.a(), null);
        }
    }

    private void a(@NonNull final String str, Bundle bundle) {
        final Task task = this.h.get(Objects.requireNonNull(str));
        if (task != null) {
            final Bundle bundle2 = bundle != null ? (Bundle) bundle.clone() : null;
            g().execute(new Runnable() { // from class: miuix.arch.component.-$$Lambda$AppComponentManager$EsG42-OSDsobelbedEagZ2M5tIE
                @Override // java.lang.Runnable
                public final void run() {
                    AppComponentManager.this.a(str, task, bundle2);
                }
            });
        }
    }

    public /* synthetic */ void a(String str, Task task, Bundle bundle) {
        b.a("send event: %s ", str);
        while (task != null) {
            try {
                callMethod(task.a, String.valueOf(task.b), bundle);
            } catch (Exception e) {
                b.a(e, "error happen, send %s to %s.%s", str, task.a, task.c);
            }
            task = task.next;
        }
    }

    private void a(final Application application, @NonNull c cVar) {
        switch (cVar) {
            case APP_ATTACH_CONTEXT:
                b.a("LT");
                this.j = a((Context) application);
                a(application, 1);
                b.a("LT", "main %s", "@ APP_ATTACH_CONTEXT");
                b(application, 1);
                b.a("LT", "post-tasks %s", "@ APP_ATTACH_CONTEXT");
                a(1, "@ APP_ATTACH_CONTEXT");
                b.a("LT", "wait-done %s", "@ APP_ATTACH_CONTEXT");
                return;
            case BEFORE_APP_CREATED:
                a(application, 2, "@ BEFORE_APP_CREATED");
                return;
            case APP_CREATE:
                a(application, 3, "@ APP_CONTEXT");
                return;
            case AFTER_APP_CREATED:
                a(application, 4, "@ AFTER_APP_CREATED");
                return;
            case BEFORE_FIRST_ACT_CREATED:
                a(application, 5, "@ BEFORE_FIRST_ACT_CREATE");
                return;
            case FIRST_ACT_CREATED:
                a(application, 6, "@ FIRST_ACT_CREATED");
                return;
            case AFTER_FIRST_ACT_CREATED:
                a(application, 7, "@ AFTER_FIRST_ACT_CREATE");
                AppCallbackMediator.a();
                return;
            case MAIN_HANDLER_IDLE:
                new Thread(new Runnable() { // from class: miuix.arch.component.-$$Lambda$AppComponentManager$INsTAAEcSBWhIwX1BJ3qnpAsLMk
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppComponentManager.this.a(application);
                    }
                }).start();
                return;
            case CONFIG_CHANGE:
                a(cVar);
                return;
            case LOW_MEMORY:
                e();
                return;
            case TRIM_MEMORY:
                b(cVar);
                return;
            case ON_REMOVE:
                f();
                return;
            default:
                return;
        }
    }

    public /* synthetic */ void a(Application application) {
        a(application, 8, "@ MAIN_HANDLER_IDLE");
        b.a("LT", true);
    }

    @MainThread
    private void a(Application application, int i, String str) {
        b.a("LT", "start %s", str);
        b(application, i);
        b.a("LT", "post-tasks %s", str);
        a(application, i);
        b.a("LT", "main %s", str);
        a(i, str);
        b.a("LT", "wait-done %s", str);
    }

    @MainThread
    private void a(int i, String str) {
        List<CountDownLatch> list = this.g.get(Integer.valueOf(i));
        if (list != null && !list.isEmpty()) {
            Iterator<CountDownLatch> it = list.iterator();
            while (it.hasNext()) {
                CountDownLatch next = it.next();
                if (next == null) {
                    try {
                        it.remove();
                    } catch (InterruptedException e) {
                        throw new IllegalStateException(str, e);
                    }
                } else {
                    next.await();
                    it.remove();
                }
            }
        }
    }

    private void a(Application application, int i) {
        List<Task> list = this.e.get(Integer.valueOf(i));
        if (!(list == null || list.isEmpty())) {
            for (Task task : list) {
                for (; task != null; task = task.next) {
                    a(application, task);
                }
            }
        }
    }

    private void b(Application application, int i) {
        Map<Integer, List<Task>> map = this.f.get(Integer.valueOf(i));
        if (!(map == null || map.isEmpty())) {
            for (Map.Entry<Integer, List<Task>> entry : map.entrySet()) {
                List<Task> value = entry.getValue();
                if (value != null && !value.isEmpty()) {
                    Integer key = entry.getKey();
                    List<CountDownLatch> list = this.g.get(key);
                    if (list == null) {
                        list = new LinkedList<>();
                        this.g.put(key, list);
                    }
                    CountDownLatch countDownLatch = new CountDownLatch(value.size());
                    list.add(countDownLatch);
                    for (Task task : value) {
                        g().execute(a(application, task, countDownLatch));
                    }
                }
            }
        }
    }

    @NonNull
    private Runnable a(final Application application, final Task task, final CountDownLatch countDownLatch) {
        return new Runnable() { // from class: miuix.arch.component.-$$Lambda$AppComponentManager$wk5QLFEt4bZylQxBIFesdH9xZYE
            @Override // java.lang.Runnable
            public final void run() {
                AppComponentManager.this.a(task, application, countDownLatch);
            }
        };
    }

    public /* synthetic */ void a(Task task, Application application, CountDownLatch countDownLatch) {
        while (task != null) {
            try {
                a(application, task);
            } catch (Exception e) {
                Log.e("miuix-app-comp", String.format("perform task %s-%s", task.a, Integer.valueOf(task.b)), e);
            }
            task = task.next;
        }
        countDownLatch.countDown();
    }

    private void a(@NonNull c cVar) {
        for (Map.Entry<String, AppComponentDelegate<?>> entry : this.d.entrySet()) {
            final Object singleton = entry.getValue().getSingleton();
            if (singleton instanceof ComponentCallbacks2) {
                final Configuration configuration = (Configuration) Objects.requireNonNull((Configuration) ((Bundle) Objects.requireNonNull(cVar.b())).getParcelable("newConfig"));
                a(new Runnable() { // from class: miuix.arch.component.-$$Lambda$AppComponentManager$qB35C9apc1lxLgCzUtrWFewW7ts
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppComponentManager.a(singleton, configuration);
                    }
                }, "%s call configChange fail", entry.getKey());
            }
        }
    }

    public static /* synthetic */ void a(Object obj, Configuration configuration) {
        ((ComponentCallbacks2) obj).onConfigurationChanged(configuration);
    }

    private void e() {
        for (Map.Entry<String, AppComponentDelegate<?>> entry : this.d.entrySet()) {
            Object singleton = entry.getValue().getSingleton();
            if (singleton instanceof ComponentCallbacks) {
                final ComponentCallbacks componentCallbacks = (ComponentCallbacks) singleton;
                Objects.requireNonNull(componentCallbacks);
                a(new Runnable() { // from class: miuix.arch.component.-$$Lambda$GaenuRdO8mW8SS0qDdbUZrm4Hjg
                    @Override // java.lang.Runnable
                    public final void run() {
                        componentCallbacks.onLowMemory();
                    }
                }, "%s call lowMemory fail", entry.getKey());
            }
        }
    }

    private void b(@NonNull c cVar) {
        for (Map.Entry<String, AppComponentDelegate<?>> entry : this.d.entrySet()) {
            final Object singleton = entry.getValue().getSingleton();
            if (singleton instanceof ComponentCallbacks2) {
                final Bundle bundle = (Bundle) Objects.requireNonNull(cVar.b());
                a(new Runnable() { // from class: miuix.arch.component.-$$Lambda$AppComponentManager$L8zz1vg3unmtpqMKXekKaSodUMM
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppComponentManager.a(singleton, bundle);
                    }
                }, "%s call trimMemory fail", entry.getKey());
            }
        }
    }

    public static /* synthetic */ void a(Object obj, Bundle bundle) {
        ((ComponentCallbacks2) obj).onTrimMemory(bundle.getInt(a.d));
    }

    private void f() {
        String[] strArr = (String[]) this.d.keySet().toArray(new String[0]);
        for (String str : strArr) {
            AppComponentDelegate<?> remove = this.d.remove(str);
            if (remove != null) {
                Object singleton = remove.getSingleton();
                if (singleton instanceof OnRemoveCallback) {
                    final OnRemoveCallback onRemoveCallback = (OnRemoveCallback) singleton;
                    Objects.requireNonNull(onRemoveCallback);
                    a(new Runnable() { // from class: miuix.arch.component.-$$Lambda$VFSXee7Svyg3ttOA5YE8IAIB9oc
                        @Override // java.lang.Runnable
                        public final void run() {
                            OnRemoveCallback.this.onRemove();
                        }
                    }, "%s call onRemove fail", str);
                }
            }
        }
    }

    private void a(Application application, Task task) {
        AppComponentDelegate<?> appComponentDelegate = this.d.get(task.a);
        if (appComponentDelegate == null) {
            Log.w("miuix-app-comp", String.format("not found task %s-%s", task.a, Integer.valueOf(task.b)));
            return;
        }
        try {
            if (task.d || this.j) {
                appComponentDelegate.performTask(application, task.b);
            } else {
                Log.i("miuix-app-comp", String.format("not main process, skip task %s-%s", task.a, Integer.valueOf(task.b)));
            }
        } catch (Exception e) {
            Log.e("miuix-app-comp", String.format("perform task %s-%s fail", task.a, Integer.valueOf(task.b)), e);
        }
    }

    private Executor g() {
        if (this.i == null) {
            this.i = c();
        }
        return this.i;
    }
}
