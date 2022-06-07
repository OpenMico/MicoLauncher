package miuix.arch.component;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xiaomi.onetrack.a.a;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class AppCallbackMediator {
    static Application a;
    private static final long b = SystemClock.elapsedRealtime();
    private static boolean c;

    public static void attachBaseContext(Application application) {
        a(application);
        b(c.APP_ATTACH_CONTEXT);
    }

    public static void onCreate() {
        c();
        b(c.APP_CREATE);
        d();
    }

    public static void onRemove() {
        b(c.ON_REMOVE);
    }

    public static void onConfigurationChanged(@NonNull Configuration configuration) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("newConfig", configuration);
        b(c.CONFIG_CHANGE.a(bundle));
    }

    public static void onLowMemory() {
        b(c.LOW_MEMORY);
    }

    public static void onTrimMemory(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(a.d, i);
        b(c.TRIM_MEMORY.a(bundle));
    }

    public static void setupAppComponentManager(@NonNull Application application) {
        Exception e;
        ComponentConfiguration componentConfiguration;
        Class<?> cls = application.getClass();
        try {
            AppComponentManager.a((ComponentConfiguration) Class.forName(cls.getName() + "$ComponentConfiguration").newInstance());
        } catch (ClassNotFoundException unused) {
            b.a("setup app component with default configuration, but not found, skip it.", new Object[0]);
        } catch (Exception e2) {
            throw new IllegalStateException("init app-component fail", e2);
        }
        AppComponentManagerConfig appComponentManagerConfig = (AppComponentManagerConfig) cls.getAnnotation(AppComponentManagerConfig.class);
        if (appComponentManagerConfig != null) {
            Class<? extends ComponentConfiguration>[] subComponentManagerConfig = appComponentManagerConfig.subComponentManagerConfig();
            for (Class<? extends ComponentConfiguration> cls2 : subComponentManagerConfig) {
                ComponentConfiguration componentConfiguration2 = null;
                try {
                    componentConfiguration = (ComponentConfiguration) cls2.newInstance();
                } catch (Exception e3) {
                    e = e3;
                }
                try {
                    AppComponentManager.a(componentConfiguration);
                } catch (Exception e4) {
                    e = e4;
                    componentConfiguration2 = componentConfiguration;
                    StringBuilder sb = new StringBuilder();
                    sb.append("load sub component fail: ");
                    String str = cls2;
                    if (componentConfiguration2 != null) {
                        str = componentConfiguration2.getComponentManagerDomain();
                    }
                    sb.append(str);
                    throw new IllegalStateException(sb.toString(), e);
                }
            }
        }
    }

    public static void a() {
        b.a("comp-init cost : %sms", Long.valueOf(SystemClock.elapsedRealtime() - b));
    }

    private static void c() {
        b(c.BEFORE_APP_CREATED);
    }

    private static void d() {
        b(c.AFTER_APP_CREATED);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new AnonymousClass1(handler), SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    /* renamed from: miuix.arch.component.AppCallbackMediator$1 */
    /* loaded from: classes5.dex */
    public class AnonymousClass1 implements Runnable {
        int a;
        final /* synthetic */ Handler b;

        AnonymousClass1(Handler handler) {
            this.b = handler;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!AppCallbackMediator.c && this.a < 2) {
                this.b.postDelayed(this, 500L);
                this.a++;
            } else if (Build.VERSION.SDK_INT >= 23) {
                Looper.getMainLooper().getQueue().addIdleHandler($$Lambda$AppCallbackMediator$1$zg1xWVERkngA3u_hAe3S5PooMJY.INSTANCE);
            } else {
                this.b.postDelayed($$Lambda$AppCallbackMediator$1$PGDCMfDQuAlLxyX_JtXZ9Rbx0U.INSTANCE, 5000L);
            }
        }

        public static /* synthetic */ boolean b() {
            AppCallbackMediator.b(c.MAIN_HANDLER_IDLE);
            return false;
        }

        public static /* synthetic */ void a() {
            AppCallbackMediator.b(c.MAIN_HANDLER_IDLE);
        }
    }

    private static void a(@NonNull Application application) {
        a = application;
        ((Application) Objects.requireNonNull(application)).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: miuix.arch.component.AppCallbackMediator.2
            private boolean a;
            private boolean b;

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(@NonNull Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(@NonNull Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(@NonNull Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(@NonNull Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(@NonNull Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                if (this.b) {
                    AppCallbackMediator.b(c.FIRST_ACT_CREATED);
                    return;
                }
                AppCallbackMediator.b(c.BEFORE_FIRST_ACT_CREATED);
                AppCallbackMediator.b(c.FIRST_ACT_CREATED);
                a();
            }

            private void a() {
                if (!this.a) {
                    AppCallbackMediator.b(c.AFTER_FIRST_ACT_CREATED);
                    AppCallbackMediator.a.unregisterActivityLifecycleCallbacks(this);
                    boolean unused = AppCallbackMediator.c = true;
                    this.a = true;
                }
            }
        });
    }

    public static void b(@NonNull a aVar) {
        AppComponentManager[] a2 = AppComponentManager.a();
        for (AppComponentManager appComponentManager : a2) {
            Objects.requireNonNull(appComponentManager, "app-component impl init");
            try {
                appComponentManager.a(a, aVar);
            } catch (Exception e) {
                Log.e("miuix-app-comp", "handle event fail: " + aVar.a(), e);
            }
        }
    }

    private AppCallbackMediator() {
    }
}
