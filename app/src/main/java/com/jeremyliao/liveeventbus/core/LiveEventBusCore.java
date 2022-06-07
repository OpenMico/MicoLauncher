package com.jeremyliao.liveeventbus.core;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ExternalLiveData;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.jeremyliao.liveeventbus.ipc.consts.IpcConst;
import com.jeremyliao.liveeventbus.ipc.core.ProcessorManager;
import com.jeremyliao.liveeventbus.ipc.receiver.LebIpcReceiver;
import com.jeremyliao.liveeventbus.logger.DefaultLogger;
import com.jeremyliao.liveeventbus.logger.Logger;
import com.jeremyliao.liveeventbus.logger.LoggerManager;
import com.jeremyliao.liveeventbus.utils.AppUtils;
import com.jeremyliao.liveeventbus.utils.ThreadUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/* loaded from: classes2.dex */
public final class LiveEventBusCore {
    private boolean autoClear;
    private final Map<String, LiveEvent<Object>> bus;
    private final Config config;
    final InnerConsole console;
    private boolean isRegisterReceiver;
    private boolean lifecycleObserverAlwaysActive;
    private LoggerManager logger;
    private final Map<String, ObservableConfig> observableConfigs;
    private LebIpcReceiver receiver;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        private static final LiveEventBusCore DEFAULT_BUS = new LiveEventBusCore();

        private SingletonHolder() {
        }
    }

    public static LiveEventBusCore get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    private LiveEventBusCore() {
        this.config = new Config();
        this.isRegisterReceiver = false;
        this.console = new InnerConsole();
        this.bus = new HashMap();
        this.observableConfigs = new HashMap();
        this.lifecycleObserverAlwaysActive = true;
        this.autoClear = false;
        this.logger = new LoggerManager(new DefaultLogger());
        this.receiver = new LebIpcReceiver();
        registerReceiver();
    }

    public synchronized <T> Observable<T> with(String str, Class<T> cls) {
        if (!this.bus.containsKey(str)) {
            this.bus.put(str, new LiveEvent<>(str));
        }
        return this.bus.get(str);
    }

    public Config config() {
        return this.config;
    }

    public ObservableConfig config(String str) {
        if (!this.observableConfigs.containsKey(str)) {
            this.observableConfigs.put(str, new ObservableConfig());
        }
        return this.observableConfigs.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setLogger(@NonNull Logger logger) {
        this.logger.setLogger(logger);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void enableLogger(boolean z) {
        this.logger.setEnable(z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void registerReceiver() {
        Application app2;
        if (!this.isRegisterReceiver && (app2 = AppUtils.getApp()) != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(IpcConst.ACTION);
            app2.registerReceiver(this.receiver, intentFilter);
            this.isRegisterReceiver = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setLifecycleObserverAlwaysActive(boolean z) {
        this.lifecycleObserverAlwaysActive = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setAutoClear(boolean z) {
        this.autoClear = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class LiveEvent<T> implements Observable<T> {
        @NonNull
        private final String key;
        private final LiveEvent<T>.LifecycleLiveData liveData;
        private final Map<Observer, ObserverWrapper<T>> observerMap = new HashMap();
        private final Handler mainHandler = new Handler(Looper.getMainLooper());

        LiveEvent(String str) {
            this.key = str;
            this.liveData = new LifecycleLiveData<>(str);
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void post(T t) {
            if (ThreadUtils.isMainThread()) {
                postInternal(t);
            } else {
                this.mainHandler.post(new PostValueTask(t));
            }
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void postAcrossProcess(T t) {
            broadcast(t, false, true);
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void postAcrossApp(T t) {
            broadcast(t, false, false);
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void postDelay(T t, long j) {
            this.mainHandler.postDelayed(new PostValueTask(t), j);
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void postDelay(LifecycleOwner lifecycleOwner, T t, long j) {
            this.mainHandler.postDelayed(new PostLifeValueTask(t, lifecycleOwner), j);
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void postOrderly(T t) {
            this.mainHandler.post(new PostValueTask(t));
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        @Deprecated
        public void broadcast(T t) {
            broadcast(t, false, false);
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void broadcast(final T t, final boolean z, final boolean z2) {
            if (AppUtils.getApp() == null) {
                post(t);
            } else if (ThreadUtils.isMainThread()) {
                broadcastInternal(t, z, z2);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.jeremyliao.liveeventbus.core.LiveEventBusCore.LiveEvent.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.broadcastInternal(t, z, z2);
                    }
                });
            }
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void observe(@NonNull final LifecycleOwner lifecycleOwner, @NonNull final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                observeInternal(lifecycleOwner, observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.jeremyliao.liveeventbus.core.LiveEventBusCore.LiveEvent.2
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.observeInternal(lifecycleOwner, observer);
                    }
                });
            }
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void observeSticky(@NonNull final LifecycleOwner lifecycleOwner, @NonNull final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                observeStickyInternal(lifecycleOwner, observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.jeremyliao.liveeventbus.core.LiveEventBusCore.LiveEvent.3
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.observeStickyInternal(lifecycleOwner, observer);
                    }
                });
            }
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void observeForever(@NonNull final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                observeForeverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.jeremyliao.liveeventbus.core.LiveEventBusCore.LiveEvent.4
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.observeForeverInternal(observer);
                    }
                });
            }
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void observeStickyForever(@NonNull final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                observeStickyForeverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.jeremyliao.liveeventbus.core.LiveEventBusCore.LiveEvent.5
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.observeStickyForeverInternal(observer);
                    }
                });
            }
        }

        @Override // com.jeremyliao.liveeventbus.core.Observable
        public void removeObserver(@NonNull final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                removeObserverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.jeremyliao.liveeventbus.core.LiveEventBusCore.LiveEvent.6
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.removeObserverInternal(observer);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @MainThread
        public void postInternal(T t) {
            LoggerManager loggerManager = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            loggerManager.log(level, "post: " + t + " with key: " + this.key);
            this.liveData.setValue(t);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @MainThread
        public void broadcastInternal(T t, boolean z, boolean z2) {
            LoggerManager loggerManager = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            loggerManager.log(level, "broadcast: " + t + " foreground: " + z + " with key: " + this.key);
            Application app2 = AppUtils.getApp();
            if (app2 == null) {
                LiveEventBusCore.this.logger.log(Level.WARNING, "application is null, you can try setContext() when config");
                return;
            }
            Intent intent = new Intent(IpcConst.ACTION);
            if (z && Build.VERSION.SDK_INT >= 16) {
                intent.addFlags(268435456);
            }
            if (z2) {
                intent.setPackage(app2.getPackageName());
            }
            intent.putExtra(IpcConst.KEY, this.key);
            if (ProcessorManager.getManager().writeTo(intent, t)) {
                try {
                    app2.sendBroadcast(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @MainThread
        public void observeInternal(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer) {
            ObserverWrapper observerWrapper = new ObserverWrapper(observer);
            observerWrapper.preventNextEvent = this.liveData.getVersion() > -1;
            this.liveData.observe(lifecycleOwner, observerWrapper);
            LoggerManager loggerManager = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            loggerManager.log(level, "observe observer: " + observerWrapper + "(" + observer + ") on owner: " + lifecycleOwner + " with key: " + this.key);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @MainThread
        public void observeStickyInternal(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer) {
            ObserverWrapper observerWrapper = new ObserverWrapper(observer);
            this.liveData.observe(lifecycleOwner, observerWrapper);
            LoggerManager loggerManager = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            loggerManager.log(level, "observe sticky observer: " + observerWrapper + "(" + observer + ") on owner: " + lifecycleOwner + " with key: " + this.key);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @MainThread
        public void observeForeverInternal(@NonNull Observer<T> observer) {
            ObserverWrapper<T> observerWrapper = new ObserverWrapper<>(observer);
            ((ObserverWrapper) observerWrapper).preventNextEvent = this.liveData.getVersion() > -1;
            this.observerMap.put(observer, observerWrapper);
            this.liveData.observeForever(observerWrapper);
            LoggerManager loggerManager = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            loggerManager.log(level, "observe forever observer: " + observerWrapper + "(" + observer + ") with key: " + this.key);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @MainThread
        public void observeStickyForeverInternal(@NonNull Observer<T> observer) {
            ObserverWrapper<T> observerWrapper = new ObserverWrapper<>(observer);
            this.observerMap.put(observer, observerWrapper);
            this.liveData.observeForever(observerWrapper);
            LoggerManager loggerManager = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            loggerManager.log(level, "observe sticky forever observer: " + observerWrapper + "(" + observer + ") with key: " + this.key);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @MainThread
        public void removeObserverInternal(@NonNull Observer<T> observer) {
            if (this.observerMap.containsKey(observer)) {
                observer = this.observerMap.remove(observer);
            }
            this.liveData.removeObserver(observer);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public class LifecycleLiveData<T> extends ExternalLiveData<T> {
            private final String key;

            public LifecycleLiveData(String str) {
                this.key = str;
            }

            @Override // androidx.lifecycle.ExternalLiveData
            protected Lifecycle.State observerActiveLevel() {
                return lifecycleObserverAlwaysActive() ? Lifecycle.State.CREATED : Lifecycle.State.STARTED;
            }

            @Override // androidx.lifecycle.LiveData
            public void removeObserver(@NonNull Observer<? super T> observer) {
                super.removeObserver(observer);
                if (autoClear() && !LiveEvent.this.liveData.hasObservers()) {
                    LiveEventBusCore.get().bus.remove(this.key);
                }
                LoggerManager loggerManager = LiveEventBusCore.this.logger;
                Level level = Level.INFO;
                loggerManager.log(level, "observer removed: " + observer);
            }

            private boolean lifecycleObserverAlwaysActive() {
                if (LiveEventBusCore.this.observableConfigs.containsKey(this.key)) {
                    ObservableConfig observableConfig = (ObservableConfig) LiveEventBusCore.this.observableConfigs.get(this.key);
                    if (observableConfig.lifecycleObserverAlwaysActive != null) {
                        return observableConfig.lifecycleObserverAlwaysActive.booleanValue();
                    }
                }
                return LiveEventBusCore.this.lifecycleObserverAlwaysActive;
            }

            private boolean autoClear() {
                if (LiveEventBusCore.this.observableConfigs.containsKey(this.key)) {
                    ObservableConfig observableConfig = (ObservableConfig) LiveEventBusCore.this.observableConfigs.get(this.key);
                    if (observableConfig.autoClear != null) {
                        return observableConfig.autoClear.booleanValue();
                    }
                }
                return LiveEventBusCore.this.autoClear;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public class PostValueTask implements Runnable {
            private Object newValue;

            public PostValueTask(Object obj) {
                this.newValue = obj;
            }

            @Override // java.lang.Runnable
            public void run() {
                LiveEvent.this.postInternal(this.newValue);
            }
        }

        /* loaded from: classes2.dex */
        private class PostLifeValueTask implements Runnable {
            private Object newValue;
            private LifecycleOwner owner;

            public PostLifeValueTask(Object obj, @NonNull LifecycleOwner lifecycleOwner) {
                this.newValue = obj;
                this.owner = lifecycleOwner;
            }

            @Override // java.lang.Runnable
            public void run() {
                LifecycleOwner lifecycleOwner = this.owner;
                if (lifecycleOwner != null && lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    LiveEvent.this.postInternal(this.newValue);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ObserverWrapper<T> implements Observer<T> {
        @NonNull
        private final Observer<T> observer;
        private boolean preventNextEvent = false;

        ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(@Nullable T t) {
            if (this.preventNextEvent) {
                this.preventNextEvent = false;
                return;
            }
            LoggerManager loggerManager = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            loggerManager.log(level, "message received: " + t);
            try {
                this.observer.onChanged(t);
            } catch (ClassCastException e) {
                LoggerManager loggerManager2 = LiveEventBusCore.this.logger;
                Level level2 = Level.WARNING;
                loggerManager2.log(level2, "class cast error on message received: " + t, e);
            } catch (Exception e2) {
                LoggerManager loggerManager3 = LiveEventBusCore.this.logger;
                Level level3 = Level.WARNING;
                loggerManager3.log(level3, "error on message received: " + t, e2);
            }
        }
    }

    /* loaded from: classes2.dex */
    class InnerConsole {
        InnerConsole() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public String getConsoleInfo() {
            return "*********Base info*********\n" + getBaseInfo() + "*********Event info*********\n" + getBusInfo();
        }

        String getBaseInfo() {
            return "lifecycleObserverAlwaysActive: " + LiveEventBusCore.this.lifecycleObserverAlwaysActive + "\nautoClear: " + LiveEventBusCore.this.autoClear + "\nlogger enable: " + LiveEventBusCore.this.logger.isEnable() + "\nlogger: " + LiveEventBusCore.this.logger.getLogger() + "\nReceiver register: " + LiveEventBusCore.this.isRegisterReceiver + "\nApplication: " + AppUtils.getApp() + "\n";
        }

        String getBusInfo() {
            StringBuilder sb = new StringBuilder();
            for (String str : LiveEventBusCore.this.bus.keySet()) {
                sb.append("Event name: " + str);
                sb.append("\n");
                LiveEvent.LifecycleLiveData lifecycleLiveData = ((LiveEvent) LiveEventBusCore.this.bus.get(str)).liveData;
                sb.append("\tversion: " + lifecycleLiveData.getVersion());
                sb.append("\n");
                sb.append("\thasActiveObservers: " + lifecycleLiveData.hasActiveObservers());
                sb.append("\n");
                sb.append("\thasObservers: " + lifecycleLiveData.hasObservers());
                sb.append("\n");
                sb.append("\tActiveCount: " + getActiveCount(lifecycleLiveData));
                sb.append("\n");
                sb.append("\tObserverCount: " + getObserverCount(lifecycleLiveData));
                sb.append("\n");
                sb.append("\tObservers: ");
                sb.append("\n");
                sb.append("\t\t" + getObserverInfo(lifecycleLiveData));
                sb.append("\n");
            }
            return sb.toString();
        }

        private int getActiveCount(LiveData liveData) {
            try {
                Field declaredField = LiveData.class.getDeclaredField("mActiveCount");
                declaredField.setAccessible(true);
                return ((Integer) declaredField.get(liveData)).intValue();
            } catch (Exception unused) {
                return -1;
            }
        }

        private int getObserverCount(LiveData liveData) {
            try {
                Field declaredField = LiveData.class.getDeclaredField("mObservers");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(liveData);
                Method declaredMethod = obj.getClass().getDeclaredMethod("size", new Class[0]);
                declaredMethod.setAccessible(true);
                return ((Integer) declaredMethod.invoke(obj, new Object[0])).intValue();
            } catch (Exception unused) {
                return -1;
            }
        }

        private String getObserverInfo(LiveData liveData) {
            try {
                Field declaredField = LiveData.class.getDeclaredField("mObservers");
                declaredField.setAccessible(true);
                return declaredField.get(liveData).toString();
            } catch (Exception unused) {
                return "";
            }
        }
    }
}
