package miuix.arch.component;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Trace;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

/* loaded from: classes5.dex */
public abstract class AbsComponentManagerApplication extends Application {
    public AbsComponentManagerApplication() {
        try {
            Trace.beginSection("CA setupAppComponentManager");
            AppCallbackMediator.setupAppComponentManager(this);
        } finally {
            Trace.endSection();
        }
    }

    @Override // android.app.Application
    @CallSuper
    public void onCreate() {
        super.onCreate();
        try {
            Trace.beginSection("CA onCreate");
            AppCallbackMediator.onCreate();
        } finally {
            Trace.endSection();
        }
    }

    @Override // android.app.Application
    @CallSuper
    public void onTerminate() {
        super.onTerminate();
        AppCallbackMediator.onRemove();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    @CallSuper
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        AppCallbackMediator.onConfigurationChanged(configuration);
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    @CallSuper
    public void onLowMemory() {
        super.onLowMemory();
        AppCallbackMediator.onLowMemory();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    @CallSuper
    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        AppCallbackMediator.onTrimMemory(i);
    }

    @Override // android.content.ContextWrapper
    @CallSuper
    protected void attachBaseContext(Context context) {
        try {
            Trace.beginSection("CA attachBaseContext");
            super.attachBaseContext(context);
            AppCallbackMediator.attachBaseContext(this);
        } finally {
            Trace.endSection();
        }
    }
}
