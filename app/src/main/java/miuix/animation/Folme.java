package miuix.animation;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import miuix.animation.controller.FolmeFont;
import miuix.animation.controller.FolmeHover;
import miuix.animation.controller.FolmeTouch;
import miuix.animation.controller.FolmeVisible;
import miuix.animation.controller.ListViewTouchListener;
import miuix.animation.controller.StateComposer;
import miuix.animation.internal.ThreadPoolUtil;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;
import miuix.folme.R;

/* loaded from: classes5.dex */
public class Folme {
    private static AtomicReference<Float> a = new AtomicReference<>(Float.valueOf(1.0f));
    private static final ConcurrentHashMap<IAnimTarget, a> b = new ConcurrentHashMap<>();
    private static float c = 12.5f;
    private static final Handler d = new Handler(Looper.getMainLooper()) { // from class: miuix.animation.Folme.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Folme.e();
                    Folme.b(true);
                    return;
                case 2:
                    Folme.b((List) message.obj);
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    };

    /* loaded from: classes5.dex */
    public interface FontType {
        public static final int MITYPE = 1;
        public static final int MITYPE_MONO = 2;
        public static final int MIUI = 0;
    }

    /* loaded from: classes5.dex */
    public interface FontWeight {
        public static final int BOLD = 8;
        public static final int DEMI_BOLD = 6;
        public static final int EXTRA_LIGHT = 1;
        public static final int HEAVY = 9;
        public static final int LIGHT = 2;
        public static final int MEDIUM = 5;
        public static final int NORMAL = 3;
        public static final int REGULAR = 4;
        public static final int SEMI_BOLD = 7;
        public static final int THIN = 0;
    }

    private static float a(float f, float f2) {
        return (-f) / (f2 * (-4.2f));
    }

    public static float perFromValue(float f, float f2, float f3) {
        if (f3 == f2) {
            return 0.0f;
        }
        return (f - f2) / (f3 - f2);
    }

    public static float valueFromPer(float f, float f2, float f3) {
        return f2 + ((f3 - f2) * f);
    }

    static {
        ThreadPoolUtil.post(new Runnable() { // from class: miuix.animation.Folme.1
            @Override // java.lang.Runnable
            public void run() {
                LogUtils.getLogEnableInfo();
            }
        });
    }

    public static void useSystemAnimatorDurationScale(Context context) {
        a.set(Float.valueOf(Settings.Global.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f)));
    }

    public static void setAnimPlayRatio(float f) {
        a.set(Float.valueOf(f));
    }

    public static float getTimeRatio() {
        return a.get().floatValue();
    }

    public static <T> void post(T t, Runnable runnable) {
        IAnimTarget target = getTarget(t, null);
        if (target != null) {
            target.post(runnable);
        }
    }

    public static Collection<IAnimTarget> getTargets() {
        if (LogUtils.isLogEnabled()) {
            int i = 0;
            for (IAnimTarget iAnimTarget : b.keySet()) {
                if (!iAnimTarget.isValid()) {
                    i++;
                }
            }
            LogUtils.debug("current sImplMap total : " + b.size() + "  , target invalid count :  " + i, new Object[0]);
        }
        return b.keySet();
    }

    public static IVarFontStyle useVarFontAt(TextView textView, int i, int i2) {
        return new FolmeFont().useAt(textView, i, i2);
    }

    /* loaded from: classes5.dex */
    public static class a implements IFolme {
        private IStateStyle a;
        private ITouchStyle b;
        private IVisibleStyle c;
        private IHoverStyle d;
        private IAnimTarget[] e;

        private a(IAnimTarget... iAnimTargetArr) {
            this.e = iAnimTargetArr;
            Folme.b(false);
            Folme.f();
        }

        void a() {
            ITouchStyle iTouchStyle = this.b;
            if (iTouchStyle != null) {
                iTouchStyle.clean();
            }
            IVisibleStyle iVisibleStyle = this.c;
            if (iVisibleStyle != null) {
                iVisibleStyle.clean();
            }
            IStateStyle iStateStyle = this.a;
            if (iStateStyle != null) {
                iStateStyle.clean();
            }
            IHoverStyle iHoverStyle = this.d;
            if (iHoverStyle != null) {
                iHoverStyle.clean();
            }
        }

        void b() {
            ITouchStyle iTouchStyle = this.b;
            if (iTouchStyle != null) {
                iTouchStyle.end(new Object[0]);
            }
            IVisibleStyle iVisibleStyle = this.c;
            if (iVisibleStyle != null) {
                iVisibleStyle.end(new Object[0]);
            }
            IStateStyle iStateStyle = this.a;
            if (iStateStyle != null) {
                iStateStyle.end(new Object[0]);
            }
            IHoverStyle iHoverStyle = this.d;
            if (iHoverStyle != null) {
                iHoverStyle.end(new Object[0]);
            }
        }

        @Override // miuix.animation.IFolme
        public IHoverStyle hover() {
            if (this.d == null) {
                this.d = new FolmeHover(this.e);
            }
            return this.d;
        }

        @Override // miuix.animation.IFolme
        public ITouchStyle touch() {
            if (this.b == null) {
                FolmeTouch folmeTouch = new FolmeTouch(this.e);
                folmeTouch.setFontStyle(new FolmeFont());
                this.b = folmeTouch;
            }
            return this.b;
        }

        @Override // miuix.animation.IFolme
        public IVisibleStyle visible() {
            if (this.c == null) {
                this.c = new FolmeVisible(this.e);
            }
            return this.c;
        }

        @Override // miuix.animation.IFolme
        public IStateStyle state() {
            if (this.a == null) {
                this.a = StateComposer.composeStyle(this.e);
            }
            return this.a;
        }
    }

    public static IStateStyle useValue(Object... objArr) {
        IFolme iFolme;
        if (objArr.length > 0) {
            iFolme = useAt(getTarget(objArr[0], ValueTarget.h));
        } else {
            ValueTarget valueTarget = new ValueTarget();
            valueTarget.setFlags(1L);
            iFolme = useAt(valueTarget);
        }
        return iFolme.state();
    }

    public static IFolme useAt(IAnimTarget iAnimTarget) {
        a aVar = b.get(iAnimTarget);
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a(new IAnimTarget[]{iAnimTarget});
        a putIfAbsent = b.putIfAbsent(iAnimTarget, aVar2);
        return putIfAbsent != null ? putIfAbsent : aVar2;
    }

    public static IFolme useAt(View... viewArr) {
        if (viewArr.length != 0) {
            if (viewArr.length == 1) {
                return useAt(getTarget(viewArr[0], ViewTarget.sCreator));
            }
            IAnimTarget[] iAnimTargetArr = new IAnimTarget[viewArr.length];
            a a2 = a(viewArr, iAnimTargetArr);
            if (a2 == null) {
                a2 = new a(iAnimTargetArr);
                for (IAnimTarget iAnimTarget : iAnimTargetArr) {
                    a put = b.put(iAnimTarget, a2);
                    if (put != null) {
                        put.a();
                    }
                }
            }
            return a2;
        }
        throw new IllegalArgumentException("useAt can not be applied to empty views array");
    }

    private static a a(View[] viewArr, IAnimTarget[] iAnimTargetArr) {
        a aVar = null;
        boolean z = false;
        for (int i = 0; i < viewArr.length; i++) {
            iAnimTargetArr[i] = getTarget(viewArr[i], ViewTarget.sCreator);
            a aVar2 = b.get(iAnimTargetArr[i]);
            if (aVar == null) {
                aVar = aVar2;
            } else if (aVar != aVar2) {
                z = true;
            }
        }
        if (z) {
            return null;
        }
        return aVar;
    }

    @SafeVarargs
    public static <T> void clean(T... tArr) {
        if (CommonUtils.isArrayEmpty(tArr)) {
            for (IAnimTarget iAnimTarget : b.keySet()) {
                a(iAnimTarget);
            }
            return;
        }
        for (T t : tArr) {
            a(t);
        }
    }

    public static <T> void end(T... tArr) {
        a aVar;
        for (T t : tArr) {
            IAnimTarget target = getTarget(t, null);
            if (!(target == null || (aVar = b.get(target)) == null)) {
                aVar.b();
            }
        }
    }

    public static void onListViewTouchEvent(AbsListView absListView, MotionEvent motionEvent) {
        ListViewTouchListener listViewTouchListener = FolmeTouch.getListViewTouchListener(absListView);
        if (listViewTouchListener != null) {
            listViewTouchListener.onTouch(absListView, motionEvent);
        }
    }

    private static <T> void a(T t) {
        a(getTarget(t, null));
    }

    private static void a(IAnimTarget iAnimTarget) {
        if (iAnimTarget != null) {
            iAnimTarget.clean();
            a remove = b.remove(iAnimTarget);
            iAnimTarget.animManager.clear();
            if (remove != null) {
                remove.a();
            }
        }
    }

    public static <T> ValueTarget getValueTarget(T t) {
        return (ValueTarget) getTarget(t, ValueTarget.h);
    }

    public static <T> IAnimTarget getTarget(T t) {
        return getTarget(t, null);
    }

    public static <T> IAnimTarget getTarget(T t, ITargetCreator<T> iTargetCreator) {
        IAnimTarget createTarget;
        if (t == null) {
            return null;
        }
        if (t instanceof IAnimTarget) {
            return (IAnimTarget) t;
        }
        for (IAnimTarget iAnimTarget : b.keySet()) {
            Object targetObject = iAnimTarget.getTargetObject();
            if (targetObject != null && targetObject.equals(t)) {
                return iAnimTarget;
            }
        }
        if (iTargetCreator == null || (createTarget = iTargetCreator.createTarget(t)) == null) {
            return null;
        }
        useAt(createTarget);
        return createTarget;
    }

    public static void setDraggingState(View view, boolean z) {
        if (z) {
            view.setTag(R.id.miuix_animation_tag_is_dragging, true);
        } else {
            view.setTag(R.id.miuix_animation_tag_is_dragging, null);
        }
    }

    public static boolean isInDraggingState(View view) {
        return view.getTag(R.id.miuix_animation_tag_is_dragging) != null;
    }

    public static void getTargets(Collection<IAnimTarget> collection) {
        for (IAnimTarget iAnimTarget : b.keySet()) {
            if (!iAnimTarget.isValid() || (iAnimTarget.hasFlags(1L) && !iAnimTarget.animManager.isAnimRunning(new FloatProperty[0]))) {
                clean(iAnimTarget);
            } else {
                collection.add(iAnimTarget);
            }
        }
    }

    public static IAnimTarget getTargetById(int i) {
        for (IAnimTarget iAnimTarget : b.keySet()) {
            if (iAnimTarget.id == i) {
                return iAnimTarget;
            }
        }
        return null;
    }

    public static void e() {
        for (IAnimTarget iAnimTarget : b.keySet()) {
            if (!iAnimTarget.isValid() || (iAnimTarget.hasFlags(1L) && !iAnimTarget.animManager.isAnimRunning(new FloatProperty[0]) && !iAnimTarget.animManager.isAnimSetup() && iAnimTarget.isValidFlag())) {
                clean(iAnimTarget);
            }
        }
    }

    public static void b(List<IAnimTarget> list) {
        for (IAnimTarget iAnimTarget : list) {
            if (!iAnimTarget.isValid() && !iAnimTarget.animManager.isAnimRunning(new FloatProperty[0]) && !iAnimTarget.animManager.isAnimSetup() && iAnimTarget.isValidFlag()) {
                clean(iAnimTarget);
            }
        }
    }

    public static void b(boolean z) {
        if (z) {
            a(1);
        }
        if (z && LogUtils.isLogEnabled()) {
            for (IAnimTarget iAnimTarget : b.keySet()) {
                LogUtils.debug("exist target:" + iAnimTarget.getTargetObject() + " , target isValid : " + iAnimTarget.isValid(), new Object[0]);
            }
        }
        if (b.size() <= 0) {
            a(1);
        } else if (!d.hasMessages(1)) {
            d.sendEmptyMessageDelayed(1, 20000L);
        }
    }

    public static void f() {
        if (b.size() > 0 && b.size() % PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID == 0) {
            ThreadPoolUtil.post(new Runnable() { // from class: miuix.animation.Folme.3
                @Override // java.lang.Runnable
                public void run() {
                    ArrayList arrayList = new ArrayList();
                    for (IAnimTarget iAnimTarget : Folme.b.keySet()) {
                        if (!iAnimTarget.isValid()) {
                            arrayList.add(iAnimTarget);
                        }
                    }
                    if (arrayList.size() > 0) {
                        Message obtain = Message.obtain();
                        obtain.obj = arrayList;
                        obtain.what = 2;
                        Folme.d.sendMessageDelayed(obtain, 1000L);
                    }
                }
            });
        }
    }

    private static void a(int i) {
        if (d.hasMessages(i)) {
            d.removeMessages(i);
        }
    }

    public static float afterFrictionValue(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        float f3 = f >= 0.0f ? 1.0f : -1.0f;
        float min = Math.min(Math.abs(f) / f2, 1.0f);
        float f4 = min * min;
        return f3 * ((((f4 * min) / 3.0f) - f4) + min) * f2;
    }

    public static float getDefalutThresholdVelocity() {
        return c;
    }

    public static float getPredictDistance(float f) {
        return a(f, 0.4761905f);
    }

    public static float getPredictDistanceWithFriction(float f, float f2, float... fArr) {
        if (fArr == null || fArr.length <= 0) {
            return a(f, f2);
        }
        return a(f, f2, fArr[0]);
    }

    public static float getPredictDistance(float f, float... fArr) {
        if (fArr == null || fArr.length <= 0) {
            return a(f, 0.4761905f);
        }
        return a(f, 0.4761905f, fArr[0]);
    }

    public static float getPredictFriction(float f, float f2, float f3, float... fArr) {
        float f4 = f2 - f;
        if (f3 * f4 <= 0.0f) {
            return -1.0f;
        }
        float signum = Math.signum(f3) * Math.abs(getDefalutThresholdVelocity());
        if (fArr != null && fArr.length > 0) {
            signum = Math.signum(f3) * Math.abs(fArr[0]);
        }
        return (f3 - signum) / (f4 * 4.2f);
    }

    private static float a(float f, float f2, float f3) {
        return a(f, f2) - a(f3, f2);
    }
}
