package com.trello.rxlifecycle2.android;

import android.view.View;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.OutsideLifecycleException;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/* loaded from: classes2.dex */
public class RxLifecycleAndroid {
    private static final Function<ActivityEvent, ActivityEvent> a = new Function<ActivityEvent, ActivityEvent>() { // from class: com.trello.rxlifecycle2.android.RxLifecycleAndroid.1
        /* renamed from: a */
        public ActivityEvent apply(ActivityEvent activityEvent) throws Exception {
            switch (AnonymousClass3.a[activityEvent.ordinal()]) {
                case 1:
                    return ActivityEvent.DESTROY;
                case 2:
                    return ActivityEvent.STOP;
                case 3:
                    return ActivityEvent.PAUSE;
                case 4:
                    return ActivityEvent.STOP;
                case 5:
                    return ActivityEvent.DESTROY;
                case 6:
                    throw new OutsideLifecycleException("Cannot bind to Activity lifecycle when outside of it.");
                default:
                    throw new UnsupportedOperationException("Binding to " + activityEvent + " not yet implemented");
            }
        }
    };
    private static final Function<FragmentEvent, FragmentEvent> b = new Function<FragmentEvent, FragmentEvent>() { // from class: com.trello.rxlifecycle2.android.RxLifecycleAndroid.2
        /* renamed from: a */
        public FragmentEvent apply(FragmentEvent fragmentEvent) throws Exception {
            switch (AnonymousClass3.b[fragmentEvent.ordinal()]) {
                case 1:
                    return FragmentEvent.DETACH;
                case 2:
                    return FragmentEvent.DESTROY;
                case 3:
                    return FragmentEvent.DESTROY_VIEW;
                case 4:
                    return FragmentEvent.STOP;
                case 5:
                    return FragmentEvent.PAUSE;
                case 6:
                    return FragmentEvent.STOP;
                case 7:
                    return FragmentEvent.DESTROY_VIEW;
                case 8:
                    return FragmentEvent.DESTROY;
                case 9:
                    return FragmentEvent.DETACH;
                case 10:
                    throw new OutsideLifecycleException("Cannot bind to Fragment lifecycle when outside of it.");
                default:
                    throw new UnsupportedOperationException("Binding to " + fragmentEvent + " not yet implemented");
            }
        }
    };

    private RxLifecycleAndroid() {
        throw new AssertionError("No instances");
    }

    @NonNull
    @CheckResult
    public static <T> LifecycleTransformer<T> bindActivity(@NonNull Observable<ActivityEvent> observable) {
        return RxLifecycle.bind(observable, a);
    }

    @NonNull
    @CheckResult
    public static <T> LifecycleTransformer<T> bindFragment(@NonNull Observable<FragmentEvent> observable) {
        return RxLifecycle.bind(observable, b);
    }

    @NonNull
    @CheckResult
    public static <T> LifecycleTransformer<T> bindView(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return RxLifecycle.bind(Observable.create(new a(view)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.trello.rxlifecycle2.android.RxLifecycleAndroid$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b = new int[FragmentEvent.values().length];

        static {
            try {
                b[FragmentEvent.ATTACH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[FragmentEvent.CREATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[FragmentEvent.CREATE_VIEW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[FragmentEvent.START.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                b[FragmentEvent.RESUME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                b[FragmentEvent.PAUSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                b[FragmentEvent.STOP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                b[FragmentEvent.DESTROY_VIEW.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                b[FragmentEvent.DESTROY.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                b[FragmentEvent.DETACH.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            a = new int[ActivityEvent.values().length];
            try {
                a[ActivityEvent.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[ActivityEvent.START.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                a[ActivityEvent.RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                a[ActivityEvent.PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                a[ActivityEvent.STOP.ordinal()] = 5;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                a[ActivityEvent.DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }
}
