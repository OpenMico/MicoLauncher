package miuix.animation.controller;

import android.graphics.Color;
import android.util.ArrayMap;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;
import miuix.animation.IAnimTarget;
import miuix.animation.ITouchStyle;
import miuix.animation.ViewTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.internal.AnimValueUtils;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.animation.property.ViewPropertyExt;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.LogUtils;
import miuix.folme.R;

/* loaded from: classes5.dex */
public class FolmeTouch extends FolmeBase implements ITouchStyle {
    private static WeakHashMap<View, b> b = new WeakHashMap<>();
    private FolmeFont c;
    private int d;
    private int e;
    private View.OnClickListener f;
    private View.OnLongClickListener g;
    private int h;
    private float i;
    private float j;
    private boolean k;
    private boolean l;
    private WeakReference<View> o;
    private WeakReference<View> p;
    private float q;
    private boolean t;
    private d w;
    private boolean x;
    private int[] m = new int[2];
    private Map<ITouchStyle.TouchType, Boolean> n = new ArrayMap();
    private AnimConfig r = new AnimConfig();
    private AnimConfig s = new AnimConfig();
    private boolean u = false;
    private TransitionListener v = new TransitionListener() { // from class: miuix.animation.controller.FolmeTouch.1
        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj, Collection<UpdateInfo> collection) {
            if (obj.equals(ITouchStyle.TouchType.DOWN)) {
                AnimState.alignState(FolmeTouch.this.a.getState(ITouchStyle.TouchType.UP), collection);
            }
        }
    };

    public FolmeTouch(IAnimTarget... iAnimTargetArr) {
        super(iAnimTargetArr);
        a(iAnimTargetArr.length > 0 ? iAnimTargetArr[0] : null);
        this.a.getState(ITouchStyle.TouchType.UP).add(ViewProperty.SCALE_X, 1.0d).add(ViewProperty.SCALE_Y, 1.0d);
        a();
        this.r.setEase(EaseManager.getStyle(-2, 0.99f, 0.15f));
        this.r.addListeners(this.v);
        this.s.setEase(-2, 0.99f, 0.3f).setSpecial(ViewProperty.ALPHA, -2L, 0.9f, 0.2f);
    }

    private void a() {
        if (!this.t && !this.u) {
            int argb = Color.argb(20, 0, 0, 0);
            Object targetObject = this.a.getTarget().getTargetObject();
            if (targetObject instanceof View) {
                argb = ((View) targetObject).getResources().getColor(R.color.miuix_folme_color_touch_tint);
            }
            ViewPropertyExt.ForegroundProperty foregroundProperty = ViewPropertyExt.FOREGROUND;
            this.a.getState(ITouchStyle.TouchType.DOWN).add(foregroundProperty, argb);
            this.a.getState(ITouchStyle.TouchType.UP).add(foregroundProperty, 0.0d);
        }
    }

    private void a(IAnimTarget iAnimTarget) {
        View targetObject = iAnimTarget instanceof ViewTarget ? ((ViewTarget) iAnimTarget).getTargetObject() : null;
        if (targetObject != null) {
            this.q = TypedValue.applyDimension(1, 10.0f, targetObject.getResources().getDisplayMetrics());
        }
    }

    @Override // miuix.animation.controller.FolmeBase, miuix.animation.IStateContainer
    public void clean() {
        super.clean();
        FolmeFont folmeFont = this.c;
        if (folmeFont != null) {
            folmeFont.clean();
        }
        this.n.clear();
        WeakReference<View> weakReference = this.o;
        if (weakReference != null) {
            a(weakReference);
            this.o = null;
        }
        WeakReference<View> weakReference2 = this.p;
        if (weakReference2 != null) {
            View a2 = a(weakReference2);
            if (a2 != null) {
                a2.setTag(R.id.miuix_animation_tag_touch_listener, null);
            }
            this.p = null;
        }
        b();
    }

    private View a(WeakReference<View> weakReference) {
        View view = weakReference.get();
        if (view != null) {
            view.setOnTouchListener(null);
        }
        return view;
    }

    public void setFontStyle(FolmeFont folmeFont) {
        this.c = folmeFont;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setTintMode(int i) {
        this.r.setTintMode(i);
        this.s.setTintMode(i);
        return this;
    }

    static boolean a(View view, int[] iArr, MotionEvent motionEvent) {
        if (view == null) {
            return true;
        }
        view.getLocationOnScreen(iArr);
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        return rawX >= iArr[0] && rawX <= iArr[0] + view.getWidth() && rawY >= iArr[1] && rawY <= iArr[1] + view.getHeight();
    }

    private boolean a(View view) {
        WeakReference<View> weakReference = this.o;
        if ((weakReference != null ? weakReference.get() : null) == view) {
            return false;
        }
        this.o = new WeakReference<>(view);
        return true;
    }

    @Override // miuix.animation.ITouchStyle
    public void bindViewOfListItem(final View view, final AnimConfig... animConfigArr) {
        if (a(view)) {
            CommonUtils.runOnPreDraw(view, new Runnable() { // from class: miuix.animation.controller.FolmeTouch.2
                @Override // java.lang.Runnable
                public void run() {
                    FolmeTouch.this.a(view, false, animConfigArr);
                }
            });
        }
    }

    @Override // miuix.animation.ITouchStyle
    public void ignoreTouchOf(View view) {
        b bVar = b.get(view);
        if (bVar != null && bVar.a(this)) {
            b.remove(view);
        }
    }

    @Override // miuix.animation.ITouchStyle
    public void handleTouchOf(View view, AnimConfig... animConfigArr) {
        handleTouchOf(view, false, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public void handleTouchOf(View view, View.OnClickListener onClickListener, AnimConfig... animConfigArr) {
        a(view, onClickListener, null, false, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public void handleTouchOf(View view, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener, AnimConfig... animConfigArr) {
        a(view, onClickListener, onLongClickListener, false, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public void handleTouchOf(View view, boolean z, AnimConfig... animConfigArr) {
        a(view, null, null, z, animConfigArr);
    }

    private void a(final View view, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener, final boolean z, final AnimConfig... animConfigArr) {
        a(onClickListener, onLongClickListener);
        a(view, animConfigArr);
        if (a(view)) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("handleViewTouch for " + view, new Object[0]);
            }
            final boolean isClickable = view.isClickable();
            view.setClickable(true);
            CommonUtils.runOnPreDraw(view, new CommonUtils.PreDrawTask() { // from class: miuix.animation.controller.FolmeTouch.3
                @Override // miuix.animation.utils.CommonUtils.PreDrawTask
                public boolean call() {
                    if (z) {
                        return true;
                    }
                    if (!view.isAttachedToWindow()) {
                        if (LogUtils.isLogEnabled()) {
                            LogUtils.debug("detached and retry bindListView later for " + view, new Object[0]);
                        }
                        return false;
                    }
                    if (FolmeTouch.this.a(view, true, animConfigArr)) {
                        FolmeTouch.this.a(view, isClickable);
                    }
                    return true;
                }
            });
        }
    }

    private void a(View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        IAnimTarget target = this.a.getTarget();
        View targetObject = target instanceof ViewTarget ? ((ViewTarget) target).getTargetObject() : null;
        if (targetObject != null) {
            if (this.f != null && onClickListener == null) {
                targetObject.setOnClickListener(null);
            } else if (onClickListener != null) {
                targetObject.setOnClickListener(new View.OnClickListener() { // from class: miuix.animation.controller.FolmeTouch.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        FolmeTouch.this.d(view);
                    }
                });
            }
            this.f = onClickListener;
            if (this.g != null && onLongClickListener == null) {
                targetObject.setOnLongClickListener(null);
            } else if (onLongClickListener != null) {
                targetObject.setOnLongClickListener(new View.OnLongClickListener() { // from class: miuix.animation.controller.FolmeTouch.5
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view) {
                        if (FolmeTouch.this.x) {
                            return false;
                        }
                        FolmeTouch.this.c(view);
                        return true;
                    }
                });
            }
            this.g = onLongClickListener;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(View view, boolean z, AnimConfig... animConfigArr) {
        c b2;
        if (this.a.getTarget() == null || (b2 = b(view)) == null || b2.a == null) {
            return false;
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("handleListViewTouch for " + view, new Object[0]);
        }
        a(b2.a, view, z, animConfigArr);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class c {
        ViewGroup a;
        View b;

        private c() {
        }
    }

    private c b(View view) {
        ViewGroup viewGroup = null;
        c cVar = new c();
        ViewParent parent = view.getParent();
        while (true) {
            if (parent == null) {
                break;
            } else if (parent instanceof AbsListView) {
                viewGroup = (AbsListView) parent;
                break;
            } else if (parent instanceof RecyclerView) {
                viewGroup = (RecyclerView) parent;
                break;
            } else {
                if (parent instanceof View) {
                    view = (View) parent;
                }
                parent = parent.getParent();
            }
        }
        if (viewGroup != null) {
            this.p = new WeakReference<>(cVar.a);
            cVar.a = viewGroup;
            cVar.b = view;
        }
        return cVar;
    }

    public static ListViewTouchListener getListViewTouchListener(ViewGroup viewGroup) {
        return (ListViewTouchListener) viewGroup.getTag(R.id.miuix_animation_tag_touch_listener);
    }

    private void a(ViewGroup viewGroup, View view, boolean z, AnimConfig... animConfigArr) {
        ListViewTouchListener listViewTouchListener = getListViewTouchListener(viewGroup);
        if (listViewTouchListener == null) {
            listViewTouchListener = new ListViewTouchListener(viewGroup);
            viewGroup.setTag(R.id.miuix_animation_tag_touch_listener, listViewTouchListener);
        }
        if (z) {
            viewGroup.setOnTouchListener(listViewTouchListener);
        }
        listViewTouchListener.putListener(view, new a(this, animConfigArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a implements View.OnTouchListener {
        private WeakReference<FolmeTouch> a;
        private AnimConfig[] b;

        a(FolmeTouch folmeTouch, AnimConfig... animConfigArr) {
            this.a = new WeakReference<>(folmeTouch);
            this.b = animConfigArr;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            WeakReference<FolmeTouch> weakReference = this.a;
            FolmeTouch folmeTouch = weakReference == null ? null : weakReference.get();
            if (folmeTouch == null) {
                return false;
            }
            if (motionEvent == null) {
                folmeTouch.b(this.b);
                return false;
            }
            folmeTouch.a(view, motionEvent, this.b);
            return false;
        }
    }

    private void a(View view, AnimConfig... animConfigArr) {
        b bVar = b.get(view);
        if (bVar == null) {
            bVar = new b();
            b.put(view, bVar);
        }
        view.setOnTouchListener(bVar);
        bVar.a(this, animConfigArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class b implements View.OnTouchListener {
        private WeakHashMap<FolmeTouch, AnimConfig[]> a;

        private b() {
            this.a = new WeakHashMap<>();
        }

        void a(FolmeTouch folmeTouch, AnimConfig... animConfigArr) {
            this.a.put(folmeTouch, animConfigArr);
        }

        boolean a(FolmeTouch folmeTouch) {
            this.a.remove(folmeTouch);
            return this.a.isEmpty();
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            for (Map.Entry<FolmeTouch, AnimConfig[]> entry : this.a.entrySet()) {
                entry.getKey().a(view, motionEvent, entry.getValue());
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(View view, boolean z) {
        view.setClickable(z);
        view.setOnTouchListener(null);
    }

    @Override // miuix.animation.ITouchStyle
    public void onMotionEventEx(View view, MotionEvent motionEvent, AnimConfig... animConfigArr) {
        a(view, motionEvent, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public void onMotionEvent(MotionEvent motionEvent) {
        a((View) null, motionEvent, new AnimConfig[0]);
    }

    private void a(AnimConfig... animConfigArr) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("onEventDown, touchDown", new Object[0]);
        }
        this.l = true;
        touchDown(animConfigArr);
    }

    private void a(MotionEvent motionEvent, View view, AnimConfig... animConfigArr) {
        if (!this.l) {
            return;
        }
        if (!a(view, this.m, motionEvent)) {
            touchUp(animConfigArr);
            b();
        } else if (this.w != null && !b(view, motionEvent)) {
            this.w.b(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(AnimConfig... animConfigArr) {
        if (this.l) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("onEventUp, touchUp", new Object[0]);
            }
            touchUp(animConfigArr);
            b();
        }
    }

    private void b() {
        d dVar = this.w;
        if (dVar != null) {
            dVar.b(this);
        }
        this.l = false;
        this.h = 0;
        this.i = 0.0f;
        this.j = 0.0f;
    }

    private void a(MotionEvent motionEvent) {
        if (this.f != null || this.g != null) {
            this.h = motionEvent.getActionIndex();
            this.i = motionEvent.getRawX();
            this.j = motionEvent.getRawY();
            this.k = false;
            this.x = false;
            c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class d implements Runnable {
        private WeakReference<FolmeTouch> a;

        private d() {
        }

        void a(FolmeTouch folmeTouch) {
            View targetObject;
            IAnimTarget target = folmeTouch.a.getTarget();
            if ((target instanceof ViewTarget) && (targetObject = ((ViewTarget) target).getTargetObject()) != null) {
                this.a = new WeakReference<>(folmeTouch);
                targetObject.postDelayed(this, ViewConfiguration.getLongPressTimeout());
            }
        }

        void b(FolmeTouch folmeTouch) {
            View targetObject;
            IAnimTarget target = folmeTouch.a.getTarget();
            if ((target instanceof ViewTarget) && (targetObject = ((ViewTarget) target).getTargetObject()) != null) {
                targetObject.removeCallbacks(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            View view;
            FolmeTouch folmeTouch = this.a.get();
            if (folmeTouch != null) {
                IAnimTarget target = folmeTouch.a.getTarget();
                if ((target instanceof ViewTarget) && (view = (View) target.getTargetObject()) != null && folmeTouch.g != null) {
                    view.performLongClick();
                    folmeTouch.c(view);
                }
            }
        }
    }

    private void c() {
        if (this.g != null) {
            if (this.w == null) {
                this.w = new d();
            }
            this.w.a(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(View view) {
        if (!this.x) {
            this.x = true;
            this.g.onLongClick(view);
        }
    }

    private void a(View view, MotionEvent motionEvent) {
        if (this.l && this.f != null && this.h == motionEvent.getActionIndex()) {
            IAnimTarget target = this.a.getTarget();
            if ((target instanceof ViewTarget) && b(view, motionEvent)) {
                View targetObject = ((ViewTarget) target).getTargetObject();
                targetObject.performClick();
                d(targetObject);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(View view) {
        if (!this.k && !this.x) {
            this.k = true;
            this.f.onClick(view);
        }
    }

    private boolean b(View view, MotionEvent motionEvent) {
        return CommonUtils.getDistance(this.i, this.j, motionEvent.getRawX(), motionEvent.getRawY()) < ((double) CommonUtils.getTouchSlop(view));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void a(View view, MotionEvent motionEvent, AnimConfig... animConfigArr) {
        switch (motionEvent.getActionMasked()) {
            case 0:
                a(motionEvent);
                a(animConfigArr);
                return;
            case 1:
                a(view, motionEvent);
                break;
            case 2:
                a(motionEvent, view, animConfigArr);
                return;
        }
        b(animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle useVarFont(TextView textView, int i, int i2, int i3) {
        FolmeFont folmeFont = this.c;
        if (folmeFont != null) {
            this.d = i2;
            this.e = i3;
            folmeFont.useAt(textView, i, i2);
        }
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setAlpha(float f, ITouchStyle.TouchType... touchTypeArr) {
        this.a.getState(a(touchTypeArr)).add(ViewProperty.ALPHA, f);
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setScale(float f, ITouchStyle.TouchType... touchTypeArr) {
        ITouchStyle.TouchType a2 = a(touchTypeArr);
        this.n.put(a2, true);
        double d2 = f;
        this.a.getState(a2).add(ViewProperty.SCALE_X, d2).add(ViewProperty.SCALE_Y, d2);
        return this;
    }

    private boolean a(ITouchStyle.TouchType touchType) {
        return Boolean.TRUE.equals(this.n.get(touchType));
    }

    private ITouchStyle.TouchType a(ITouchStyle.TouchType... touchTypeArr) {
        return touchTypeArr.length > 0 ? touchTypeArr[0] : ITouchStyle.TouchType.DOWN;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle clearTintColor() {
        this.u = true;
        ViewPropertyExt.ForegroundProperty foregroundProperty = ViewPropertyExt.FOREGROUND;
        this.a.getState(ITouchStyle.TouchType.DOWN).remove(foregroundProperty);
        this.a.getState(ITouchStyle.TouchType.UP).remove(foregroundProperty);
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setTint(int i) {
        boolean z = true;
        this.t = true;
        if (i != 0) {
            z = false;
        }
        this.u = z;
        this.a.getState(ITouchStyle.TouchType.DOWN).add(ViewPropertyExt.FOREGROUND, i);
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setTint(float f, float f2, float f3, float f4) {
        return setTint(Color.argb((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f)));
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setBackgroundColor(int i) {
        ViewPropertyExt.BackgroundProperty backgroundProperty = ViewPropertyExt.BACKGROUND;
        this.a.getState(ITouchStyle.TouchType.DOWN).add(backgroundProperty, i);
        this.a.getState(ITouchStyle.TouchType.UP).add(backgroundProperty, (int) AnimValueUtils.getValueOfTarget(this.a.getTarget(), backgroundProperty, 0.0d));
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setBackgroundColor(float f, float f2, float f3, float f4) {
        return setBackgroundColor(Color.argb((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f)));
    }

    @Override // miuix.animation.ITouchStyle
    public void touchDown(AnimConfig... animConfigArr) {
        a(0.0f);
        a();
        AnimConfig[] c2 = c(animConfigArr);
        FolmeFont folmeFont = this.c;
        if (folmeFont != null) {
            folmeFont.to(this.e, c2);
        }
        AnimState state = this.a.getState(ITouchStyle.TouchType.DOWN);
        if (!a(ITouchStyle.TouchType.DOWN)) {
            IAnimTarget target = this.a.getTarget();
            float max = Math.max(target.getValue(ViewProperty.WIDTH), target.getValue(ViewProperty.HEIGHT));
            double max2 = Math.max((max - this.q) / max, 0.9f);
            state.add(ViewProperty.SCALE_X, max2).add(ViewProperty.SCALE_Y, max2);
        }
        this.a.to(state, c2);
    }

    @Override // miuix.animation.ITouchStyle
    public void touchUp(AnimConfig... animConfigArr) {
        AnimConfig[] d2 = d(animConfigArr);
        FolmeFont folmeFont = this.c;
        if (folmeFont != null) {
            folmeFont.to(this.d, d2);
        }
        this.a.to(this.a.getState(ITouchStyle.TouchType.UP), d2);
    }

    @Override // miuix.animation.controller.FolmeBase, miuix.animation.ICancelableStyle
    public void cancel() {
        super.cancel();
        FolmeFont folmeFont = this.c;
        if (folmeFont != null) {
            folmeFont.cancel();
        }
    }

    @Override // miuix.animation.ITouchStyle
    public void setTouchDown() {
        a();
        this.a.setTo(ITouchStyle.TouchType.DOWN);
    }

    @Override // miuix.animation.ITouchStyle
    public void setTouchUp() {
        this.a.setTo(ITouchStyle.TouchType.UP);
    }

    private AnimConfig[] c(AnimConfig... animConfigArr) {
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.r);
    }

    private AnimConfig[] d(AnimConfig... animConfigArr) {
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.s);
    }

    private void a(float f) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            ((View) targetObject).setTag(miuix.animation.R.id.miuix_animation_tag_view_corner, Float.valueOf(f));
        }
    }
}
