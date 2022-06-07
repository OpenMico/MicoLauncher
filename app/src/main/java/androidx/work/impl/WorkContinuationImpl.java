package androidx.work.impl;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.LiveData;
import androidx.work.ArrayCreatingInputMerger;
import androidx.work.ExistingWorkPolicy;
import androidx.work.Logger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkRequest;
import androidx.work.impl.utils.EnqueueRunnable;
import androidx.work.impl.utils.StatusRunnable;
import androidx.work.impl.workers.CombineContinuationsWorker;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkContinuationImpl extends WorkContinuation {
    private static final String a = Logger.tagWithPrefix("WorkContinuationImpl");
    private final WorkManagerImpl b;
    private final String c;
    private final ExistingWorkPolicy d;
    private final List<? extends WorkRequest> e;
    private final List<String> f;
    private final List<String> g;
    private final List<WorkContinuationImpl> h;
    private boolean i;
    private Operation j;

    @NonNull
    public WorkManagerImpl getWorkManagerImpl() {
        return this.b;
    }

    @Nullable
    public String getName() {
        return this.c;
    }

    public ExistingWorkPolicy getExistingWorkPolicy() {
        return this.d;
    }

    @NonNull
    public List<? extends WorkRequest> getWork() {
        return this.e;
    }

    @NonNull
    public List<String> getIds() {
        return this.f;
    }

    public List<String> getAllIds() {
        return this.g;
    }

    public boolean isEnqueued() {
        return this.i;
    }

    public void markEnqueued() {
        this.i = true;
    }

    public List<WorkContinuationImpl> getParents() {
        return this.h;
    }

    public WorkContinuationImpl(@NonNull WorkManagerImpl workManagerImpl, @NonNull List<? extends WorkRequest> list) {
        this(workManagerImpl, null, ExistingWorkPolicy.KEEP, list, null);
    }

    public WorkContinuationImpl(@NonNull WorkManagerImpl workManagerImpl, @Nullable String str, @NonNull ExistingWorkPolicy existingWorkPolicy, @NonNull List<? extends WorkRequest> list) {
        this(workManagerImpl, str, existingWorkPolicy, list, null);
    }

    public WorkContinuationImpl(@NonNull WorkManagerImpl workManagerImpl, @Nullable String str, @NonNull ExistingWorkPolicy existingWorkPolicy, @NonNull List<? extends WorkRequest> list, @Nullable List<WorkContinuationImpl> list2) {
        this.b = workManagerImpl;
        this.c = str;
        this.d = existingWorkPolicy;
        this.e = list;
        this.h = list2;
        this.f = new ArrayList(this.e.size());
        this.g = new ArrayList();
        if (list2 != null) {
            for (WorkContinuationImpl workContinuationImpl : list2) {
                this.g.addAll(workContinuationImpl.g);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            String stringId = ((WorkRequest) list.get(i)).getStringId();
            this.f.add(stringId);
            this.g.add(stringId);
        }
    }

    @Override // androidx.work.WorkContinuation
    @NonNull
    public WorkContinuation then(@NonNull List<OneTimeWorkRequest> list) {
        return list.isEmpty() ? this : new WorkContinuationImpl(this.b, this.c, ExistingWorkPolicy.KEEP, list, Collections.singletonList(this));
    }

    @Override // androidx.work.WorkContinuation
    @NonNull
    public LiveData<List<WorkInfo>> getWorkInfosLiveData() {
        return this.b.a(this.g);
    }

    @Override // androidx.work.WorkContinuation
    @NonNull
    public ListenableFuture<List<WorkInfo>> getWorkInfos() {
        StatusRunnable<List<WorkInfo>> forStringIds = StatusRunnable.forStringIds(this.b, this.g);
        this.b.getWorkTaskExecutor().executeOnBackgroundThread(forStringIds);
        return forStringIds.getFuture();
    }

    @Override // androidx.work.WorkContinuation
    @NonNull
    public Operation enqueue() {
        if (!this.i) {
            EnqueueRunnable enqueueRunnable = new EnqueueRunnable(this);
            this.b.getWorkTaskExecutor().executeOnBackgroundThread(enqueueRunnable);
            this.j = enqueueRunnable.getOperation();
        } else {
            Logger.get().warning(a, String.format("Already enqueued work ids (%s)", TextUtils.join(", ", this.f)), new Throwable[0]);
        }
        return this.j;
    }

    @Override // androidx.work.WorkContinuation
    @NonNull
    protected WorkContinuation combineInternal(@NonNull List<WorkContinuation> list) {
        OneTimeWorkRequest build = new OneTimeWorkRequest.Builder(CombineContinuationsWorker.class).setInputMerger(ArrayCreatingInputMerger.class).build();
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<WorkContinuation> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((WorkContinuationImpl) it.next());
        }
        return new WorkContinuationImpl(this.b, null, ExistingWorkPolicy.KEEP, Collections.singletonList(build), arrayList);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean hasCycles() {
        return a(this, new HashSet());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    private static boolean a(@NonNull WorkContinuationImpl workContinuationImpl, @NonNull Set<String> set) {
        set.addAll(workContinuationImpl.getIds());
        Set<String> prerequisitesFor = prerequisitesFor(workContinuationImpl);
        for (String str : set) {
            if (prerequisitesFor.contains(str)) {
                return true;
            }
        }
        List<WorkContinuationImpl> parents = workContinuationImpl.getParents();
        if (parents != null && !parents.isEmpty()) {
            for (WorkContinuationImpl workContinuationImpl2 : parents) {
                if (a(workContinuationImpl2, set)) {
                    return true;
                }
            }
        }
        set.removeAll(workContinuationImpl.getIds());
        return false;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Set<String> prerequisitesFor(WorkContinuationImpl workContinuationImpl) {
        HashSet hashSet = new HashSet();
        List<WorkContinuationImpl> parents = workContinuationImpl.getParents();
        if (parents != null && !parents.isEmpty()) {
            for (WorkContinuationImpl workContinuationImpl2 : parents) {
                hashSet.addAll(workContinuationImpl2.getIds());
            }
        }
        return hashSet;
    }
}
