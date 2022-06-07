package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ExternalLiveData<T> extends MutableLiveData<T> {
    public static final int START_VERSION = -1;

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002b, code lost:
        return;
     */
    @Override // androidx.lifecycle.LiveData
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void observe(@androidx.annotation.NonNull androidx.lifecycle.LifecycleOwner r3, @androidx.annotation.NonNull androidx.lifecycle.Observer<? super T> r4) {
        /*
            r2 = this;
            androidx.lifecycle.Lifecycle r0 = r3.getLifecycle()
            androidx.lifecycle.Lifecycle$State r0 = r0.getCurrentState()
            androidx.lifecycle.Lifecycle$State r1 = androidx.lifecycle.Lifecycle.State.DESTROYED
            if (r0 != r1) goto L_0x000d
            return
        L_0x000d:
            androidx.lifecycle.ExternalLiveData$ExternalLifecycleBoundObserver r0 = new androidx.lifecycle.ExternalLiveData$ExternalLifecycleBoundObserver     // Catch: Exception -> 0x0034
            r0.<init>(r3, r4)     // Catch: Exception -> 0x0034
            java.lang.Object r4 = r2.callMethodPutIfAbsent(r4, r0)     // Catch: Exception -> 0x0034
            androidx.lifecycle.LiveData$LifecycleBoundObserver r4 = (androidx.lifecycle.LiveData.LifecycleBoundObserver) r4     // Catch: Exception -> 0x0034
            if (r4 == 0) goto L_0x0029
            boolean r1 = r4.isAttachedTo(r3)     // Catch: Exception -> 0x0034
            if (r1 == 0) goto L_0x0021
            goto L_0x0029
        L_0x0021:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch: Exception -> 0x0034
            java.lang.String r4 = "Cannot add the same observer with different lifecycles"
            r3.<init>(r4)     // Catch: Exception -> 0x0034
            throw r3     // Catch: Exception -> 0x0034
        L_0x0029:
            if (r4 == 0) goto L_0x002c
            return
        L_0x002c:
            androidx.lifecycle.Lifecycle r3 = r3.getLifecycle()     // Catch: Exception -> 0x0034
            r3.addObserver(r0)     // Catch: Exception -> 0x0034
            goto L_0x0038
        L_0x0034:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0038:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ExternalLiveData.observe(androidx.lifecycle.LifecycleOwner, androidx.lifecycle.Observer):void");
    }

    @Override // androidx.lifecycle.LiveData
    public int getVersion() {
        return super.getVersion();
    }

    protected Lifecycle.State observerActiveLevel() {
        return Lifecycle.State.CREATED;
    }

    /* loaded from: classes.dex */
    class ExternalLifecycleBoundObserver extends LiveData<T>.LifecycleBoundObserver {
        ExternalLifecycleBoundObserver(LifecycleOwner lifecycleOwner, @NonNull Observer<? super T> observer) {
            super(lifecycleOwner, observer);
        }

        @Override // androidx.lifecycle.LiveData.LifecycleBoundObserver, androidx.lifecycle.LiveData.ObserverWrapper
        boolean shouldBeActive() {
            return this.mOwner.getLifecycle().getCurrentState().isAtLeast(ExternalLiveData.this.observerActiveLevel());
        }
    }

    private Object getFieldObservers() throws Exception {
        Field declaredField = LiveData.class.getDeclaredField("mObservers");
        declaredField.setAccessible(true);
        return declaredField.get(this);
    }

    private Object callMethodPutIfAbsent(Object obj, Object obj2) throws Exception {
        Object fieldObservers = getFieldObservers();
        Method declaredMethod = fieldObservers.getClass().getDeclaredMethod("putIfAbsent", Object.class, Object.class);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(fieldObservers, obj, obj2);
    }
}
