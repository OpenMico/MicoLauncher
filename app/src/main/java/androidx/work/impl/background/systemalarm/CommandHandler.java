package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.background.systemalarm.SystemAlarmDispatcher;
import androidx.work.impl.model.WorkSpec;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class CommandHandler implements ExecutionListener {
    private static final String a = Logger.tagWithPrefix("CommandHandler");
    private final Context b;
    private final Map<String, ExecutionListener> c = new HashMap();
    private final Object d = new Object();

    public static Intent a(@NonNull Context context, @NonNull String str) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction("ACTION_SCHEDULE_WORK");
        intent.putExtra("KEY_WORKSPEC_ID", str);
        return intent;
    }

    public static Intent b(@NonNull Context context, @NonNull String str) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction("ACTION_DELAY_MET");
        intent.putExtra("KEY_WORKSPEC_ID", str);
        return intent;
    }

    public static Intent c(@NonNull Context context, @NonNull String str) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction("ACTION_STOP_WORK");
        intent.putExtra("KEY_WORKSPEC_ID", str);
        return intent;
    }

    public static Intent a(@NonNull Context context) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction("ACTION_CONSTRAINTS_CHANGED");
        return intent;
    }

    public static Intent b(@NonNull Context context) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction("ACTION_RESCHEDULE");
        return intent;
    }

    public static Intent a(@NonNull Context context, @NonNull String str, boolean z) {
        Intent intent = new Intent(context, SystemAlarmService.class);
        intent.setAction("ACTION_EXECUTION_COMPLETED");
        intent.putExtra("KEY_WORKSPEC_ID", str);
        intent.putExtra("KEY_NEEDS_RESCHEDULE", z);
        return intent;
    }

    public CommandHandler(@NonNull Context context) {
        this.b = context;
    }

    @Override // androidx.work.impl.ExecutionListener
    public void onExecuted(@NonNull String str, boolean z) {
        synchronized (this.d) {
            ExecutionListener remove = this.c.remove(str);
            if (remove != null) {
                remove.onExecuted(str, z);
            }
        }
    }

    public boolean a() {
        boolean z;
        synchronized (this.d) {
            z = !this.c.isEmpty();
        }
        return z;
    }

    @WorkerThread
    public void a(@NonNull Intent intent, int i, @NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
        String action = intent.getAction();
        if ("ACTION_CONSTRAINTS_CHANGED".equals(action)) {
            d(intent, i, systemAlarmDispatcher);
        } else if ("ACTION_RESCHEDULE".equals(action)) {
            e(intent, i, systemAlarmDispatcher);
        } else if (!a(intent.getExtras(), "KEY_WORKSPEC_ID")) {
            Logger.get().error(a, String.format("Invalid request for %s, requires %s.", action, "KEY_WORKSPEC_ID"), new Throwable[0]);
        } else if ("ACTION_SCHEDULE_WORK".equals(action)) {
            b(intent, i, systemAlarmDispatcher);
        } else if ("ACTION_DELAY_MET".equals(action)) {
            c(intent, i, systemAlarmDispatcher);
        } else if ("ACTION_STOP_WORK".equals(action)) {
            a(intent, systemAlarmDispatcher);
        } else if ("ACTION_EXECUTION_COMPLETED".equals(action)) {
            a(intent, i);
        } else {
            Logger.get().warning(a, String.format("Ignoring intent %s", intent), new Throwable[0]);
        }
    }

    private void b(@NonNull Intent intent, int i, @NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
        String string = intent.getExtras().getString("KEY_WORKSPEC_ID");
        Logger.get().debug(a, String.format("Handling schedule work for %s", string), new Throwable[0]);
        WorkDatabase workDatabase = systemAlarmDispatcher.d().getWorkDatabase();
        workDatabase.beginTransaction();
        try {
            WorkSpec workSpec = workDatabase.workSpecDao().getWorkSpec(string);
            if (workSpec == null) {
                Logger logger = Logger.get();
                String str = a;
                logger.warning(str, "Skipping scheduling " + string + " because it's no longer in the DB", new Throwable[0]);
            } else if (workSpec.state.isFinished()) {
                Logger logger2 = Logger.get();
                String str2 = a;
                logger2.warning(str2, "Skipping scheduling " + string + "because it is finished.", new Throwable[0]);
            } else {
                long calculateNextRunTime = workSpec.calculateNextRunTime();
                if (!workSpec.hasConstraints()) {
                    Logger.get().debug(a, String.format("Setting up Alarms for %s at %s", string, Long.valueOf(calculateNextRunTime)), new Throwable[0]);
                    a.a(this.b, systemAlarmDispatcher.d(), string, calculateNextRunTime);
                } else {
                    Logger.get().debug(a, String.format("Opportunistically setting an alarm for %s at %s", string, Long.valueOf(calculateNextRunTime)), new Throwable[0]);
                    a.a(this.b, systemAlarmDispatcher.d(), string, calculateNextRunTime);
                    systemAlarmDispatcher.a(new SystemAlarmDispatcher.a(systemAlarmDispatcher, a(this.b), i));
                }
                workDatabase.setTransactionSuccessful();
            }
        } finally {
            workDatabase.endTransaction();
        }
    }

    private void c(@NonNull Intent intent, int i, @NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
        Bundle extras = intent.getExtras();
        synchronized (this.d) {
            String string = extras.getString("KEY_WORKSPEC_ID");
            Logger.get().debug(a, String.format("Handing delay met for %s", string), new Throwable[0]);
            if (!this.c.containsKey(string)) {
                DelayMetCommandHandler delayMetCommandHandler = new DelayMetCommandHandler(this.b, i, string, systemAlarmDispatcher);
                this.c.put(string, delayMetCommandHandler);
                delayMetCommandHandler.a();
            } else {
                Logger.get().debug(a, String.format("WorkSpec %s is already being handled for ACTION_DELAY_MET", string), new Throwable[0]);
            }
        }
    }

    private void a(@NonNull Intent intent, @NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
        String string = intent.getExtras().getString("KEY_WORKSPEC_ID");
        Logger.get().debug(a, String.format("Handing stopWork work for %s", string), new Throwable[0]);
        systemAlarmDispatcher.d().stopWork(string);
        a.a(this.b, systemAlarmDispatcher.d(), string);
        systemAlarmDispatcher.onExecuted(string, false);
    }

    private void d(@NonNull Intent intent, int i, @NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
        Logger.get().debug(a, String.format("Handling constraints changed %s", intent), new Throwable[0]);
        new b(this.b, i, systemAlarmDispatcher).a();
    }

    private void e(@NonNull Intent intent, int i, @NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
        Logger.get().debug(a, String.format("Handling reschedule %s, %s", intent, Integer.valueOf(i)), new Throwable[0]);
        systemAlarmDispatcher.d().rescheduleEligibleWork();
    }

    private void a(@NonNull Intent intent, int i) {
        Bundle extras = intent.getExtras();
        String string = extras.getString("KEY_WORKSPEC_ID");
        boolean z = extras.getBoolean("KEY_NEEDS_RESCHEDULE");
        Logger.get().debug(a, String.format("Handling onExecutionCompleted %s, %s", intent, Integer.valueOf(i)), new Throwable[0]);
        onExecuted(string, z);
    }

    private static boolean a(@Nullable Bundle bundle, @NonNull String... strArr) {
        if (bundle == null || bundle.isEmpty()) {
            return false;
        }
        for (String str : strArr) {
            if (bundle.get(str) == null) {
                return false;
            }
        }
        return true;
    }
}
