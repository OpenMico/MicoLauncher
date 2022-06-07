package androidx.work;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.work.WorkInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public final class WorkQuery {
    private final List<UUID> a;
    private final List<String> b;
    private final List<String> c;
    private final List<WorkInfo.State> d;

    WorkQuery(@NonNull Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
    }

    @NonNull
    public List<UUID> getIds() {
        return this.a;
    }

    @NonNull
    public List<String> getUniqueWorkNames() {
        return this.b;
    }

    @NonNull
    public List<String> getTags() {
        return this.c;
    }

    @NonNull
    public List<WorkInfo.State> getStates() {
        return this.d;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        List<UUID> a = new ArrayList();
        List<String> b = new ArrayList();
        List<String> c = new ArrayList();
        List<WorkInfo.State> d = new ArrayList();

        private Builder() {
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public static Builder fromIds(@NonNull List<UUID> list) {
            Builder builder = new Builder();
            builder.addIds(list);
            return builder;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public static Builder fromUniqueWorkNames(@NonNull List<String> list) {
            Builder builder = new Builder();
            builder.addUniqueWorkNames(list);
            return builder;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public static Builder fromTags(@NonNull List<String> list) {
            Builder builder = new Builder();
            builder.addTags(list);
            return builder;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public static Builder fromStates(@NonNull List<WorkInfo.State> list) {
            Builder builder = new Builder();
            builder.addStates(list);
            return builder;
        }

        @NonNull
        public Builder addIds(@NonNull List<UUID> list) {
            this.a.addAll(list);
            return this;
        }

        @NonNull
        public Builder addUniqueWorkNames(@NonNull List<String> list) {
            this.b.addAll(list);
            return this;
        }

        @NonNull
        public Builder addTags(@NonNull List<String> list) {
            this.c.addAll(list);
            return this;
        }

        @NonNull
        public Builder addStates(@NonNull List<WorkInfo.State> list) {
            this.d.addAll(list);
            return this;
        }

        @NonNull
        public WorkQuery build() {
            if (!this.a.isEmpty() || !this.b.isEmpty() || !this.c.isEmpty() || !this.d.isEmpty()) {
                return new WorkQuery(this);
            }
            throw new IllegalArgumentException("Must specify ids, uniqueNames, tags or states when building a WorkQuery");
        }
    }
}
