package androidx.work;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.work.WorkRequest;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class OneTimeWorkRequest extends WorkRequest {
    @NonNull
    public static OneTimeWorkRequest from(@NonNull Class<? extends ListenableWorker> cls) {
        return new Builder(cls).build();
    }

    @NonNull
    public static List<OneTimeWorkRequest> from(@NonNull List<Class<? extends ListenableWorker>> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (Class<? extends ListenableWorker> cls : list) {
            arrayList.add(new Builder(cls).build());
        }
        return arrayList;
    }

    OneTimeWorkRequest(Builder builder) {
        super(builder.b, builder.c, builder.d);
    }

    /* loaded from: classes.dex */
    public static final class Builder extends WorkRequest.Builder<Builder, OneTimeWorkRequest> {
        @NonNull
        /* renamed from: b */
        public Builder c() {
            return this;
        }

        public Builder(@NonNull Class<? extends ListenableWorker> cls) {
            super(cls);
            this.c.inputMergerClassName = OverwritingInputMerger.class.getName();
        }

        @NonNull
        public Builder setInputMerger(@NonNull Class<? extends InputMerger> cls) {
            this.c.inputMergerClassName = cls.getName();
            return this;
        }

        @NonNull
        /* renamed from: a */
        public OneTimeWorkRequest d() {
            if (this.a && Build.VERSION.SDK_INT >= 23 && this.c.constraints.requiresDeviceIdle()) {
                throw new IllegalArgumentException("Cannot set backoff criteria on an idle mode job");
            } else if (!this.c.runInForeground || Build.VERSION.SDK_INT < 23 || !this.c.constraints.requiresDeviceIdle()) {
                return new OneTimeWorkRequest(this);
            } else {
                throw new IllegalArgumentException("Cannot run in foreground with an idle mode constraint");
            }
        }
    }
}
