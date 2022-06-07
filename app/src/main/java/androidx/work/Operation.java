package androidx.work;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.LiveData;
import com.google.common.util.concurrent.ListenableFuture;

/* loaded from: classes.dex */
public interface Operation {
    @SuppressLint({"SyntheticAccessor"})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final State.SUCCESS SUCCESS = new State.SUCCESS();
    @SuppressLint({"SyntheticAccessor"})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final State.IN_PROGRESS IN_PROGRESS = new State.IN_PROGRESS();

    @NonNull
    ListenableFuture<State.SUCCESS> getResult();

    @NonNull
    LiveData<State> getState();

    /* loaded from: classes.dex */
    public static abstract class State {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        State() {
        }

        /* loaded from: classes.dex */
        public static final class SUCCESS extends State {
            @NonNull
            public String toString() {
                return "SUCCESS";
            }

            private SUCCESS() {
            }
        }

        /* loaded from: classes.dex */
        public static final class IN_PROGRESS extends State {
            @NonNull
            public String toString() {
                return "IN_PROGRESS";
            }

            private IN_PROGRESS() {
            }
        }

        /* loaded from: classes.dex */
        public static final class FAILURE extends State {
            private final Throwable a;

            public FAILURE(@NonNull Throwable th) {
                this.a = th;
            }

            @NonNull
            public Throwable getThrowable() {
                return this.a;
            }

            @NonNull
            public String toString() {
                return String.format("FAILURE (%s)", this.a.getMessage());
            }
        }
    }
}
