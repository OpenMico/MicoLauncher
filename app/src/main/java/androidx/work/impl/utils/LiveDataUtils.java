package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class LiveDataUtils {
    public static <In, Out> LiveData<Out> dedupedMappedLiveDataFor(@NonNull LiveData<In> liveData, @NonNull Function<In, Out> function, @NonNull TaskExecutor taskExecutor) {
        Object obj = new Object();
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new AnonymousClass1(taskExecutor, obj, function, mediatorLiveData));
        return mediatorLiveData;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: androidx.work.impl.utils.LiveDataUtils$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements Observer<In> {
        Out a = null;
        final /* synthetic */ TaskExecutor b;
        final /* synthetic */ Object c;
        final /* synthetic */ Function d;
        final /* synthetic */ MediatorLiveData e;

        AnonymousClass1(TaskExecutor taskExecutor, Object obj, Function function, MediatorLiveData mediatorLiveData) {
            this.b = taskExecutor;
            this.c = obj;
            this.d = function;
            this.e = mediatorLiveData;
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(@Nullable final In in) {
            this.b.executeOnBackgroundThread(new Runnable() { // from class: androidx.work.impl.utils.LiveDataUtils.1.1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Object, Out] */
                /* JADX WARN: Unknown variable types count: 1 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void run() {
                    /*
                        r3 = this;
                        androidx.work.impl.utils.LiveDataUtils$1 r0 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this
                        java.lang.Object r0 = r0.c
                        monitor-enter(r0)
                        androidx.work.impl.utils.LiveDataUtils$1 r1 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this     // Catch: all -> 0x0040
                        androidx.arch.core.util.Function r1 = r1.d     // Catch: all -> 0x0040
                        java.lang.Object r2 = r2     // Catch: all -> 0x0040
                        java.lang.Object r1 = r1.apply(r2)     // Catch: all -> 0x0040
                        androidx.work.impl.utils.LiveDataUtils$1 r2 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this     // Catch: all -> 0x0040
                        Out r2 = r2.a     // Catch: all -> 0x0040
                        if (r2 != 0) goto L_0x0023
                        if (r1 == 0) goto L_0x0023
                        androidx.work.impl.utils.LiveDataUtils$1 r2 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this     // Catch: all -> 0x0040
                        r2.a = r1     // Catch: all -> 0x0040
                        androidx.work.impl.utils.LiveDataUtils$1 r2 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this     // Catch: all -> 0x0040
                        androidx.lifecycle.MediatorLiveData r2 = r2.e     // Catch: all -> 0x0040
                        r2.postValue(r1)     // Catch: all -> 0x0040
                        goto L_0x003e
                    L_0x0023:
                        androidx.work.impl.utils.LiveDataUtils$1 r2 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this     // Catch: all -> 0x0040
                        Out r2 = r2.a     // Catch: all -> 0x0040
                        if (r2 == 0) goto L_0x003e
                        androidx.work.impl.utils.LiveDataUtils$1 r2 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this     // Catch: all -> 0x0040
                        Out r2 = r2.a     // Catch: all -> 0x0040
                        boolean r2 = r2.equals(r1)     // Catch: all -> 0x0040
                        if (r2 != 0) goto L_0x003e
                        androidx.work.impl.utils.LiveDataUtils$1 r2 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this     // Catch: all -> 0x0040
                        r2.a = r1     // Catch: all -> 0x0040
                        androidx.work.impl.utils.LiveDataUtils$1 r2 = androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.this     // Catch: all -> 0x0040
                        androidx.lifecycle.MediatorLiveData r2 = r2.e     // Catch: all -> 0x0040
                        r2.postValue(r1)     // Catch: all -> 0x0040
                    L_0x003e:
                        monitor-exit(r0)     // Catch: all -> 0x0040
                        return
                    L_0x0040:
                        r1 = move-exception
                        monitor-exit(r0)     // Catch: all -> 0x0040
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.utils.LiveDataUtils.AnonymousClass1.RunnableC00311.run():void");
                }
            });
        }
    }

    private LiveDataUtils() {
    }
}
