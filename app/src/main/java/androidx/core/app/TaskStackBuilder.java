package androidx.core.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class TaskStackBuilder implements Iterable<Intent> {
    private final ArrayList<Intent> a = new ArrayList<>();
    private final Context b;

    /* loaded from: classes.dex */
    public interface SupportParentable {
        @Nullable
        Intent getSupportParentActivityIntent();
    }

    private TaskStackBuilder(Context context) {
        this.b = context;
    }

    @NonNull
    public static TaskStackBuilder create(@NonNull Context context) {
        return new TaskStackBuilder(context);
    }

    @Deprecated
    public static TaskStackBuilder from(Context context) {
        return create(context);
    }

    @NonNull
    public TaskStackBuilder addNextIntent(@NonNull Intent intent) {
        this.a.add(intent);
        return this;
    }

    @NonNull
    public TaskStackBuilder addNextIntentWithParentStack(@NonNull Intent intent) {
        ComponentName component = intent.getComponent();
        if (component == null) {
            component = intent.resolveActivity(this.b.getPackageManager());
        }
        if (component != null) {
            addParentStack(component);
        }
        addNextIntent(intent);
        return this;
    }

    @NonNull
    public TaskStackBuilder addParentStack(@NonNull Activity activity) {
        Intent supportParentActivityIntent = activity instanceof SupportParentable ? ((SupportParentable) activity).getSupportParentActivityIntent() : null;
        if (supportParentActivityIntent == null) {
            supportParentActivityIntent = NavUtils.getParentActivityIntent(activity);
        }
        if (supportParentActivityIntent != null) {
            ComponentName component = supportParentActivityIntent.getComponent();
            if (component == null) {
                component = supportParentActivityIntent.resolveActivity(this.b.getPackageManager());
            }
            addParentStack(component);
            addNextIntent(supportParentActivityIntent);
        }
        return this;
    }

    @NonNull
    public TaskStackBuilder addParentStack(@NonNull Class<?> cls) {
        return addParentStack(new ComponentName(this.b, cls));
    }

    public TaskStackBuilder addParentStack(ComponentName componentName) {
        int size = this.a.size();
        try {
            Intent parentActivityIntent = NavUtils.getParentActivityIntent(this.b, componentName);
            while (parentActivityIntent != null) {
                this.a.add(size, parentActivityIntent);
                parentActivityIntent = NavUtils.getParentActivityIntent(this.b, parentActivityIntent.getComponent());
            }
            return this;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e);
        }
    }

    public int getIntentCount() {
        return this.a.size();
    }

    @Deprecated
    public Intent getIntent(int i) {
        return editIntentAt(i);
    }

    @Nullable
    public Intent editIntentAt(int i) {
        return this.a.get(i);
    }

    @Override // java.lang.Iterable
    @Deprecated
    public Iterator<Intent> iterator() {
        return this.a.iterator();
    }

    public void startActivities() {
        startActivities(null);
    }

    public void startActivities(@Nullable Bundle bundle) {
        if (!this.a.isEmpty()) {
            ArrayList<Intent> arrayList = this.a;
            Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[arrayList.size()]);
            intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
            if (!ContextCompat.startActivities(this.b, intentArr, bundle)) {
                Intent intent = new Intent(intentArr[intentArr.length - 1]);
                intent.addFlags(268435456);
                this.b.startActivity(intent);
                return;
            }
            return;
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
    }

    @Nullable
    public PendingIntent getPendingIntent(int i, int i2) {
        return getPendingIntent(i, i2, null);
    }

    @Nullable
    public PendingIntent getPendingIntent(int i, int i2, @Nullable Bundle bundle) {
        if (!this.a.isEmpty()) {
            ArrayList<Intent> arrayList = this.a;
            Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[arrayList.size()]);
            intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
            if (Build.VERSION.SDK_INT >= 16) {
                return PendingIntent.getActivities(this.b, i, intentArr, i2, bundle);
            }
            return PendingIntent.getActivities(this.b, i, intentArr, i2);
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
    }

    @NonNull
    public Intent[] getIntents() {
        Intent[] intentArr = new Intent[this.a.size()];
        if (intentArr.length == 0) {
            return intentArr;
        }
        intentArr[0] = new Intent(this.a.get(0)).addFlags(268484608);
        for (int i = 1; i < intentArr.length; i++) {
            intentArr[i] = new Intent(this.a.get(i));
        }
        return intentArr;
    }
}
