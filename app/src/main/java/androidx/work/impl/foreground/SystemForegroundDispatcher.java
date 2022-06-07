package androidx.work.impl.foreground;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.work.ForegroundInfo;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SystemForegroundDispatcher implements ExecutionListener, WorkConstraintsCallback {
    static final String a = Logger.tagWithPrefix("SystemFgDispatcher");
    final WorkConstraintsTracker g;
    private Context h;
    private WorkManagerImpl i;
    private final TaskExecutor j;
    @Nullable
    private a k;
    final Object b = new Object();
    String c = null;
    final Map<String, ForegroundInfo> d = new LinkedHashMap();
    final Set<WorkSpec> f = new HashSet();
    final Map<String, WorkSpec> e = new HashMap();

    /* loaded from: classes.dex */
    public interface a {
        void cancelNotification(int i);

        void notify(int i, @NonNull Notification notification);

        void startForeground(int i, int i2, @NonNull Notification notification);

        void stop();
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsMet(@NonNull List<String> list) {
    }

    public SystemForegroundDispatcher(@NonNull Context context) {
        this.h = context;
        this.i = WorkManagerImpl.getInstance(this.h);
        this.j = this.i.getWorkTaskExecutor();
        this.g = new WorkConstraintsTracker(this.h, this.j, this);
        this.i.getProcessor().addExecutionListener(this);
    }

    @Override // androidx.work.impl.ExecutionListener
    @MainThread
    public void onExecuted(@NonNull String str, boolean z) {
        Map.Entry<String, ForegroundInfo> entry;
        synchronized (this.b) {
            WorkSpec remove = this.e.remove(str);
            if (remove != null ? this.f.remove(remove) : false) {
                this.g.replace(this.f);
            }
        }
        ForegroundInfo remove2 = this.d.remove(str);
        if (str.equals(this.c) && this.d.size() > 0) {
            Iterator<Map.Entry<String, ForegroundInfo>> it = this.d.entrySet().iterator();
            Map.Entry<String, ForegroundInfo> next = it.next();
            while (true) {
                entry = next;
                if (!it.hasNext()) {
                    break;
                }
                next = it.next();
            }
            this.c = entry.getKey();
            if (this.k != null) {
                ForegroundInfo value = entry.getValue();
                this.k.startForeground(value.getNotificationId(), value.getForegroundServiceType(), value.getNotification());
                this.k.cancelNotification(value.getNotificationId());
            }
        }
        a aVar = this.k;
        if (remove2 != null && aVar != null) {
            Logger.get().debug(a, String.format("Removing Notification (id: %s, workSpecId: %s ,notificationType: %s)", Integer.valueOf(remove2.getNotificationId()), str, Integer.valueOf(remove2.getForegroundServiceType())), new Throwable[0]);
            aVar.cancelNotification(remove2.getNotificationId());
        }
    }

    @MainThread
    public void a(@NonNull a aVar) {
        if (this.k != null) {
            Logger.get().error(a, "A callback already exists.", new Throwable[0]);
        } else {
            this.k = aVar;
        }
    }

    public void a(@NonNull Intent intent) {
        String action = intent.getAction();
        if ("ACTION_START_FOREGROUND".equals(action)) {
            c(intent);
            d(intent);
        } else if ("ACTION_NOTIFY".equals(action)) {
            d(intent);
        } else if ("ACTION_CANCEL_WORK".equals(action)) {
            e(intent);
        } else if ("ACTION_STOP_FOREGROUND".equals(action)) {
            b(intent);
        }
    }

    @MainThread
    public void a() {
        this.k = null;
        synchronized (this.b) {
            this.g.reset();
        }
        this.i.getProcessor().removeExecutionListener(this);
    }

    @MainThread
    private void c(@NonNull Intent intent) {
        Logger.get().info(a, String.format("Started foreground service %s", intent), new Throwable[0]);
        final String stringExtra = intent.getStringExtra("KEY_WORKSPEC_ID");
        final WorkDatabase workDatabase = this.i.getWorkDatabase();
        this.j.executeOnBackgroundThread(new Runnable() { // from class: androidx.work.impl.foreground.SystemForegroundDispatcher.1
            @Override // java.lang.Runnable
            public void run() {
                WorkSpec workSpec = workDatabase.workSpecDao().getWorkSpec(stringExtra);
                if (workSpec != null && workSpec.hasConstraints()) {
                    synchronized (SystemForegroundDispatcher.this.b) {
                        SystemForegroundDispatcher.this.e.put(stringExtra, workSpec);
                        SystemForegroundDispatcher.this.f.add(workSpec);
                        SystemForegroundDispatcher.this.g.replace(SystemForegroundDispatcher.this.f);
                    }
                }
            }
        });
    }

    @MainThread
    private void d(@NonNull Intent intent) {
        int i = 0;
        int intExtra = intent.getIntExtra("KEY_NOTIFICATION_ID", 0);
        int intExtra2 = intent.getIntExtra("KEY_FOREGROUND_SERVICE_TYPE", 0);
        String stringExtra = intent.getStringExtra("KEY_WORKSPEC_ID");
        Notification notification = (Notification) intent.getParcelableExtra("KEY_NOTIFICATION");
        Logger.get().debug(a, String.format("Notifying with (id: %s, workSpecId: %s, notificationType: %s)", Integer.valueOf(intExtra), stringExtra, Integer.valueOf(intExtra2)), new Throwable[0]);
        if (!(notification == null || this.k == null)) {
            this.d.put(stringExtra, new ForegroundInfo(intExtra, notification, intExtra2));
            if (TextUtils.isEmpty(this.c)) {
                this.c = stringExtra;
                this.k.startForeground(intExtra, intExtra2, notification);
                return;
            }
            this.k.notify(intExtra, notification);
            if (intExtra2 != 0 && Build.VERSION.SDK_INT >= 29) {
                for (Map.Entry<String, ForegroundInfo> entry : this.d.entrySet()) {
                    i |= entry.getValue().getForegroundServiceType();
                }
                ForegroundInfo foregroundInfo = this.d.get(this.c);
                if (foregroundInfo != null) {
                    this.k.startForeground(foregroundInfo.getNotificationId(), i, foregroundInfo.getNotification());
                }
            }
        }
    }

    @MainThread
    void b(@NonNull Intent intent) {
        Logger.get().info(a, "Stopping foreground service", new Throwable[0]);
        a aVar = this.k;
        if (aVar != null) {
            aVar.stop();
        }
    }

    @MainThread
    private void e(@NonNull Intent intent) {
        Logger.get().info(a, String.format("Stopping foreground work for %s", intent), new Throwable[0]);
        String stringExtra = intent.getStringExtra("KEY_WORKSPEC_ID");
        if (stringExtra != null && !TextUtils.isEmpty(stringExtra)) {
            this.i.cancelWorkById(UUID.fromString(stringExtra));
        }
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsNotMet(@NonNull List<String> list) {
        if (!list.isEmpty()) {
            for (String str : list) {
                Logger.get().debug(a, String.format("Constraints unmet for WorkSpec %s", str), new Throwable[0]);
                this.i.stopForegroundWork(str);
            }
        }
    }

    @NonNull
    public static Intent createStartForegroundIntent(@NonNull Context context, @NonNull String str, @NonNull ForegroundInfo foregroundInfo) {
        Intent intent = new Intent(context, SystemForegroundService.class);
        intent.setAction("ACTION_START_FOREGROUND");
        intent.putExtra("KEY_WORKSPEC_ID", str);
        intent.putExtra("KEY_NOTIFICATION_ID", foregroundInfo.getNotificationId());
        intent.putExtra("KEY_FOREGROUND_SERVICE_TYPE", foregroundInfo.getForegroundServiceType());
        intent.putExtra("KEY_NOTIFICATION", foregroundInfo.getNotification());
        intent.putExtra("KEY_WORKSPEC_ID", str);
        return intent;
    }

    @NonNull
    public static Intent createCancelWorkIntent(@NonNull Context context, @NonNull String str) {
        Intent intent = new Intent(context, SystemForegroundService.class);
        intent.setAction("ACTION_CANCEL_WORK");
        intent.setData(Uri.parse(String.format("workspec://%s", str)));
        intent.putExtra("KEY_WORKSPEC_ID", str);
        return intent;
    }

    @NonNull
    public static Intent createNotifyIntent(@NonNull Context context, @NonNull String str, @NonNull ForegroundInfo foregroundInfo) {
        Intent intent = new Intent(context, SystemForegroundService.class);
        intent.setAction("ACTION_NOTIFY");
        intent.putExtra("KEY_NOTIFICATION_ID", foregroundInfo.getNotificationId());
        intent.putExtra("KEY_FOREGROUND_SERVICE_TYPE", foregroundInfo.getForegroundServiceType());
        intent.putExtra("KEY_NOTIFICATION", foregroundInfo.getNotification());
        intent.putExtra("KEY_WORKSPEC_ID", str);
        return intent;
    }

    @NonNull
    public static Intent createStopForegroundIntent(@NonNull Context context) {
        Intent intent = new Intent(context, SystemForegroundService.class);
        intent.setAction("ACTION_STOP_FOREGROUND");
        return intent;
    }
}
