package androidx.activity.result;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/* loaded from: classes.dex */
public abstract class ActivityResultRegistry {
    private Random a = new Random();
    private final Map<Integer, String> g = new HashMap();
    final Map<String, Integer> b = new HashMap();
    private final Map<String, b> h = new HashMap();
    ArrayList<String> c = new ArrayList<>();
    final transient Map<String, a<?>> d = new HashMap();
    final Map<String, Object> e = new HashMap();
    final Bundle f = new Bundle();

    @MainThread
    public abstract <I, O> void onLaunch(int i, @NonNull ActivityResultContract<I, O> activityResultContract, @SuppressLint({"UnknownNullness"}) I i2, @Nullable ActivityOptionsCompat activityOptionsCompat);

    @NonNull
    public final <I, O> ActivityResultLauncher<I> register(@NonNull final String str, @NonNull LifecycleOwner lifecycleOwner, @NonNull final ActivityResultContract<I, O> activityResultContract, @NonNull final ActivityResultCallback<O> activityResultCallback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (!lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            final int b2 = b(str);
            b bVar = this.h.get(str);
            if (bVar == null) {
                bVar = new b(lifecycle);
            }
            bVar.a(new LifecycleEventObserver() { // from class: androidx.activity.result.ActivityResultRegistry.1
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner2, @NonNull Lifecycle.Event event) {
                    if (Lifecycle.Event.ON_START.equals(event)) {
                        ActivityResultRegistry.this.d.put(str, new a<>(activityResultCallback, activityResultContract));
                        if (ActivityResultRegistry.this.e.containsKey(str)) {
                            Object obj = ActivityResultRegistry.this.e.get(str);
                            ActivityResultRegistry.this.e.remove(str);
                            activityResultCallback.onActivityResult(obj);
                        }
                        ActivityResult activityResult = (ActivityResult) ActivityResultRegistry.this.f.getParcelable(str);
                        if (activityResult != null) {
                            ActivityResultRegistry.this.f.remove(str);
                            activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.getResultCode(), activityResult.getData()));
                        }
                    } else if (Lifecycle.Event.ON_STOP.equals(event)) {
                        ActivityResultRegistry.this.d.remove(str);
                    } else if (Lifecycle.Event.ON_DESTROY.equals(event)) {
                        ActivityResultRegistry.this.a(str);
                    }
                }
            });
            this.h.put(str, bVar);
            return new ActivityResultLauncher<I>() { // from class: androidx.activity.result.ActivityResultRegistry.2
                @Override // androidx.activity.result.ActivityResultLauncher
                public void launch(I i, @Nullable ActivityOptionsCompat activityOptionsCompat) {
                    ActivityResultRegistry.this.c.add(str);
                    Integer num = ActivityResultRegistry.this.b.get(str);
                    ActivityResultRegistry.this.onLaunch(num != null ? num.intValue() : b2, activityResultContract, i, activityOptionsCompat);
                }

                @Override // androidx.activity.result.ActivityResultLauncher
                public void unregister() {
                    ActivityResultRegistry.this.a(str);
                }

                @Override // androidx.activity.result.ActivityResultLauncher
                @NonNull
                public ActivityResultContract<I, ?> getContract() {
                    return activityResultContract;
                }
            };
        }
        throw new IllegalStateException("LifecycleOwner " + lifecycleOwner + " is attempting to register while current state is " + lifecycle.getCurrentState() + ". LifecycleOwners must call register before they are STARTED.");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public final <I, O> ActivityResultLauncher<I> register(@NonNull final String str, @NonNull final ActivityResultContract<I, O> activityResultContract, @NonNull ActivityResultCallback<O> activityResultCallback) {
        final int b2 = b(str);
        this.d.put(str, new a<>(activityResultCallback, activityResultContract));
        if (this.e.containsKey(str)) {
            Object obj = this.e.get(str);
            this.e.remove(str);
            activityResultCallback.onActivityResult(obj);
        }
        ActivityResult activityResult = (ActivityResult) this.f.getParcelable(str);
        if (activityResult != null) {
            this.f.remove(str);
            activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.getResultCode(), activityResult.getData()));
        }
        return new ActivityResultLauncher<I>() { // from class: androidx.activity.result.ActivityResultRegistry.3
            @Override // androidx.activity.result.ActivityResultLauncher
            public void launch(I i, @Nullable ActivityOptionsCompat activityOptionsCompat) {
                ActivityResultRegistry.this.c.add(str);
                Integer num = ActivityResultRegistry.this.b.get(str);
                ActivityResultRegistry.this.onLaunch(num != null ? num.intValue() : b2, activityResultContract, i, activityOptionsCompat);
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public void unregister() {
                ActivityResultRegistry.this.a(str);
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            @NonNull
            public ActivityResultContract<I, ?> getContract() {
                return activityResultContract;
            }
        };
    }

    @MainThread
    final void a(@NonNull String str) {
        Integer remove;
        if (!this.c.contains(str) && (remove = this.b.remove(str)) != null) {
            this.g.remove(remove);
        }
        this.d.remove(str);
        if (this.e.containsKey(str)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + this.e.get(str));
            this.e.remove(str);
        }
        if (this.f.containsKey(str)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + this.f.getParcelable(str));
            this.f.remove(str);
        }
        b bVar = this.h.get(str);
        if (bVar != null) {
            bVar.a();
            this.h.remove(str);
        }
    }

    public final void onSaveInstanceState(@NonNull Bundle bundle) {
        bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList<>(this.b.values()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList<>(this.b.keySet()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList<>(this.c));
        bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle) this.f.clone());
        bundle.putSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT", this.a);
    }

    public final void onRestoreInstanceState(@Nullable Bundle bundle) {
        if (bundle != null) {
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
            ArrayList<String> stringArrayList = bundle.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
            if (!(stringArrayList == null || integerArrayList == null)) {
                this.c = bundle.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
                this.a = (Random) bundle.getSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT");
                this.f.putAll(bundle.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT"));
                for (int i = 0; i < stringArrayList.size(); i++) {
                    String str = stringArrayList.get(i);
                    if (this.b.containsKey(str)) {
                        Integer remove = this.b.remove(str);
                        if (!this.f.containsKey(str)) {
                            this.g.remove(remove);
                        }
                    }
                    a(integerArrayList.get(i).intValue(), stringArrayList.get(i));
                }
            }
        }
    }

    @MainThread
    public final boolean dispatchResult(int i, int i2, @Nullable Intent intent) {
        String str = this.g.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        this.c.remove(str);
        a(str, i2, intent, this.d.get(str));
        return true;
    }

    @MainThread
    public final <O> boolean dispatchResult(int i, @SuppressLint({"UnknownNullness"}) O o) {
        String str = this.g.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        this.c.remove(str);
        a<?> aVar = this.d.get(str);
        if (aVar == null || aVar.a == null) {
            this.f.remove(str);
            this.e.put(str, o);
            return true;
        }
        aVar.a.onActivityResult(o);
        return true;
    }

    private <O> void a(String str, int i, @Nullable Intent intent, @Nullable a<O> aVar) {
        if (aVar == null || aVar.a == null) {
            this.e.remove(str);
            this.f.putParcelable(str, new ActivityResult(i, intent));
            return;
        }
        aVar.a.onActivityResult(aVar.b.parseResult(i, intent));
    }

    private int b(String str) {
        Integer num = this.b.get(str);
        if (num != null) {
            return num.intValue();
        }
        int a2 = a();
        a(a2, str);
        return a2;
    }

    private int a() {
        int nextInt = this.a.nextInt(2147418112);
        while (true) {
            int i = nextInt + 65536;
            if (!this.g.containsKey(Integer.valueOf(i))) {
                return i;
            }
            nextInt = this.a.nextInt(2147418112);
        }
    }

    private void a(int i, String str) {
        this.g.put(Integer.valueOf(i), str);
        this.b.put(str, Integer.valueOf(i));
    }

    /* loaded from: classes.dex */
    public static class a<O> {
        final ActivityResultCallback<O> a;
        final ActivityResultContract<?, O> b;

        a(ActivityResultCallback<O> activityResultCallback, ActivityResultContract<?, O> activityResultContract) {
            this.a = activityResultCallback;
            this.b = activityResultContract;
        }
    }

    /* loaded from: classes.dex */
    public static class b {
        final Lifecycle a;
        private final ArrayList<LifecycleEventObserver> b = new ArrayList<>();

        b(@NonNull Lifecycle lifecycle) {
            this.a = lifecycle;
        }

        void a(@NonNull LifecycleEventObserver lifecycleEventObserver) {
            this.a.addObserver(lifecycleEventObserver);
            this.b.add(lifecycleEventObserver);
        }

        void a() {
            Iterator<LifecycleEventObserver> it = this.b.iterator();
            while (it.hasNext()) {
                this.a.removeObserver(it.next());
            }
            this.b.clear();
        }
    }
}
