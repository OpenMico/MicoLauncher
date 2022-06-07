package miuix.animation.controller;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;
import miuix.animation.Folme;
import miuix.animation.IAnimTarget;
import miuix.animation.IHoverStyle;
import miuix.animation.ViewTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.internal.AnimValueUtils;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.property.ViewPropertyExt;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.LogUtils;
import miuix.folme.R;
import org.eclipse.jetty.http.HttpMethods;

/* loaded from: classes5.dex */
public class FolmeHover extends FolmeBase implements IHoverStyle {
    private static WeakHashMap<View, a> b = new WeakHashMap<>();
    private boolean k;
    private boolean m;
    private WeakReference<View> r;
    private WeakReference<View> s;
    private WeakReference<View> t;
    private float c = Float.MAX_VALUE;
    private AnimConfig d = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.4f));
    private AnimConfig e = new AnimConfig();
    private AnimConfig f = new AnimConfig();
    private Map<IHoverStyle.HoverType, Boolean> g = new ArrayMap();
    private Map<IHoverStyle.HoverType, Boolean> h = new ArrayMap();
    private IHoverStyle.HoverEffect i = IHoverStyle.HoverEffect.NORMAL;
    private boolean j = false;
    private boolean l = false;
    private int[] n = new int[2];
    private float o = 0.0f;
    private int p = 0;
    private int q = 0;
    private String u = HttpMethods.MOVE;
    private TransitionListener v = new TransitionListener() { // from class: miuix.animation.controller.FolmeHover.1
        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj, Collection<UpdateInfo> collection) {
            if (obj.equals(IHoverStyle.HoverType.ENTER)) {
                AnimState.alignState(FolmeHover.this.a.getState(IHoverStyle.HoverType.EXIT), collection);
            }
        }
    };

    private float a(float f, float f2, float f3) {
        return (f - f2) / (f3 - f2);
    }

    private float b(float f, float f2, float f3) {
        return f2 + ((f3 - f2) * f);
    }

    private void c() {
    }

    private void g() {
    }

    public FolmeHover(IAnimTarget... iAnimTargetArr) {
        super(iAnimTargetArr);
        a(iAnimTargetArr.length > 0 ? iAnimTargetArr[0] : null);
        a(this.i);
        this.e.setEase(EaseManager.getStyle(-2, 0.99f, 0.6f));
        this.e.addListeners(this.v);
        this.f.setEase(-2, 0.99f, 0.4f).setSpecial(ViewProperty.ALPHA, -2L, 0.9f, 0.2f);
    }

    private void a() {
        this.g.put(IHoverStyle.HoverType.ENTER, true);
        this.g.put(IHoverStyle.HoverType.EXIT, true);
        this.a.getState(IHoverStyle.HoverType.EXIT).add(ViewProperty.SCALE_X, 1.0d).add(ViewProperty.SCALE_Y, 1.0d);
    }

    private void b() {
        this.j = true;
        this.h.put(IHoverStyle.HoverType.ENTER, true);
        this.h.put(IHoverStyle.HoverType.EXIT, true);
        this.a.getState(IHoverStyle.HoverType.EXIT).add(ViewProperty.TRANSLATION_X, 0.0d).add(ViewProperty.TRANSLATION_Y, 0.0d);
    }

    private void d() {
        if (!this.k && !this.l) {
            int argb = Color.argb(20, 0, 0, 0);
            Object targetObject = this.a.getTarget().getTargetObject();
            if (targetObject instanceof View) {
                argb = ((View) targetObject).getResources().getColor(R.color.miuix_folme_color_touch_tint);
            }
            ViewPropertyExt.ForegroundProperty foregroundProperty = ViewPropertyExt.FOREGROUND;
            this.a.getState(IHoverStyle.HoverType.ENTER).add(foregroundProperty, argb);
            this.a.getState(IHoverStyle.HoverType.EXIT).add(foregroundProperty, 0.0d);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setParentView(View view) {
        WeakReference<View> weakReference = this.t;
        if (view == (weakReference != null ? weakReference.get() : null)) {
            return this;
        }
        this.t = new WeakReference<>(view);
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setAlpha(float f, IHoverStyle.HoverType... hoverTypeArr) {
        this.a.getState(a(hoverTypeArr)).add(ViewProperty.ALPHA, f);
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setScale(float f, IHoverStyle.HoverType... hoverTypeArr) {
        IHoverStyle.HoverType a2 = a(hoverTypeArr);
        this.g.put(a2, true);
        double d = f;
        this.a.getState(a2).add(ViewProperty.SCALE_X, d).add(ViewProperty.SCALE_Y, d);
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setTranslate(float f, IHoverStyle.HoverType... hoverTypeArr) {
        this.j = false;
        IHoverStyle.HoverType a2 = a(hoverTypeArr);
        this.h.put(a2, true);
        double d = f;
        this.a.getState(a2).add(ViewProperty.TRANSLATION_X, d).add(ViewProperty.TRANSLATION_Y, d);
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setCorner(float f) {
        this.o = f;
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            ((View) targetObject).setTag(miuix.animation.R.id.miuix_animation_tag_view_corner, Float.valueOf(f));
        }
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setTint(int i) {
        boolean z = true;
        this.k = true;
        if (i != 0) {
            z = false;
        }
        this.l = z;
        this.a.getState(IHoverStyle.HoverType.ENTER).add(ViewPropertyExt.FOREGROUND, i);
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setTint(float f, float f2, float f3, float f4) {
        return setTint(Color.argb((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f)));
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setBackgroundColor(float f, float f2, float f3, float f4) {
        return setBackgroundColor(Color.argb((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f)));
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setBackgroundColor(int i) {
        ViewPropertyExt.BackgroundProperty backgroundProperty = ViewPropertyExt.BACKGROUND;
        this.a.getState(IHoverStyle.HoverType.ENTER).add(backgroundProperty, i);
        this.a.getState(IHoverStyle.HoverType.EXIT).add(backgroundProperty, (int) AnimValueUtils.getValueOfTarget(this.a.getTarget(), backgroundProperty, 0.0d));
        return this;
    }

    private void e() {
        if (a(IHoverStyle.HoverType.ENTER)) {
            this.a.getState(IHoverStyle.HoverType.ENTER).remove(ViewProperty.SCALE_X);
            this.a.getState(IHoverStyle.HoverType.ENTER).remove(ViewProperty.SCALE_Y);
        }
        if (a(IHoverStyle.HoverType.EXIT)) {
            this.a.getState(IHoverStyle.HoverType.EXIT).remove(ViewProperty.SCALE_X);
            this.a.getState(IHoverStyle.HoverType.EXIT).remove(ViewProperty.SCALE_Y);
        }
        this.g.clear();
    }

    private void f() {
        this.j = false;
        if (b(IHoverStyle.HoverType.ENTER)) {
            this.a.getState(IHoverStyle.HoverType.ENTER).remove(ViewProperty.TRANSLATION_X);
            this.a.getState(IHoverStyle.HoverType.ENTER).remove(ViewProperty.TRANSLATION_Y);
        }
        if (b(IHoverStyle.HoverType.EXIT)) {
            this.a.getState(IHoverStyle.HoverType.EXIT).remove(ViewProperty.TRANSLATION_X);
            this.a.getState(IHoverStyle.HoverType.EXIT).remove(ViewProperty.TRANSLATION_Y);
        }
        this.h.clear();
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle clearTintColor() {
        this.l = true;
        ViewPropertyExt.ForegroundProperty foregroundProperty = ViewPropertyExt.FOREGROUND;
        this.a.getState(IHoverStyle.HoverType.ENTER).remove(foregroundProperty);
        this.a.getState(IHoverStyle.HoverType.EXIT).remove(foregroundProperty);
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setTintMode(int i) {
        this.e.setTintMode(i);
        this.f.setTintMode(i);
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public IHoverStyle setEffect(IHoverStyle.HoverEffect hoverEffect) {
        a(hoverEffect);
        return this;
    }

    @Override // miuix.animation.IHoverStyle
    public void handleHoverOf(View view, AnimConfig... animConfigArr) {
        a(view, animConfigArr);
    }

    private void a(View view, AnimConfig... animConfigArr) {
        b(view, animConfigArr);
        if (a(view) && LogUtils.isLogEnabled()) {
            LogUtils.debug("handleViewHover for " + view, new Object[0]);
        }
    }

    private void a(IHoverStyle.HoverEffect hoverEffect) {
        switch (hoverEffect) {
            case NORMAL:
                if (this.i == IHoverStyle.HoverEffect.FLOATED) {
                    e();
                    f();
                } else if (this.i == IHoverStyle.HoverEffect.FLOATED_WRAPPED) {
                    e();
                    f();
                    g();
                }
                d();
                this.i = hoverEffect;
                return;
            case FLOATED:
                if (this.i == IHoverStyle.HoverEffect.FLOATED_WRAPPED) {
                    g();
                }
                d();
                a();
                b();
                this.i = hoverEffect;
                return;
            case FLOATED_WRAPPED:
                if (this.i == IHoverStyle.HoverEffect.NORMAL || this.i == IHoverStyle.HoverEffect.FLOATED) {
                    clearTintColor();
                }
                a();
                b();
                c();
                this.i = hoverEffect;
                return;
            default:
                return;
        }
    }

    private boolean a(View view) {
        WeakReference<View> weakReference = this.r;
        if ((weakReference != null ? weakReference.get() : null) == view) {
            return false;
        }
        this.r = new WeakReference<>(view);
        return true;
    }

    private void b(View view, AnimConfig... animConfigArr) {
        a aVar = b.get(view);
        if (aVar == null) {
            aVar = new a();
            b.put(view, aVar);
        }
        view.setOnHoverListener(aVar);
        aVar.a(this, animConfigArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a implements View.OnHoverListener {
        private WeakHashMap<FolmeHover, AnimConfig[]> a;

        private a() {
            this.a = new WeakHashMap<>();
        }

        void a(FolmeHover folmeHover, AnimConfig... animConfigArr) {
            this.a.put(folmeHover, animConfigArr);
        }

        boolean a(FolmeHover folmeHover) {
            this.a.remove(folmeHover);
            return this.a.isEmpty();
        }

        @Override // android.view.View.OnHoverListener
        public boolean onHover(View view, MotionEvent motionEvent) {
            for (Map.Entry<FolmeHover, AnimConfig[]> entry : this.a.entrySet()) {
                entry.getKey().a(view, motionEvent, entry.getValue());
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(View view, MotionEvent motionEvent, AnimConfig... animConfigArr) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 7) {
            switch (actionMasked) {
                case 9:
                    a(motionEvent, animConfigArr);
                    return;
                case 10:
                    b(motionEvent, animConfigArr);
                    return;
                default:
                    return;
            }
        } else {
            b(view, motionEvent, animConfigArr);
        }
    }

    private void a(MotionEvent motionEvent, AnimConfig... animConfigArr) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("onEventEnter, touchEnter", new Object[0]);
        }
        hoverEnter(motionEvent, animConfigArr);
    }

    private void b(View view, MotionEvent motionEvent, AnimConfig... animConfigArr) {
        if (this.m && view != null && b(IHoverStyle.HoverType.ENTER) && this.j) {
            a(view, motionEvent);
        }
    }

    private void b(MotionEvent motionEvent, AnimConfig... animConfigArr) {
        if (this.m) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("onEventExit, touchExit", new Object[0]);
            }
            hoverExit(motionEvent, animConfigArr);
            h();
        }
    }

    private void h() {
        this.m = false;
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

    @Override // miuix.animation.IHoverStyle
    public void ignoreHoverOf(View view) {
        a aVar = b.get(view);
        if (aVar != null && aVar.a(this)) {
            b.remove(view);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public void onMotionEventEx(View view, MotionEvent motionEvent, AnimConfig... animConfigArr) {
        a(view, motionEvent, animConfigArr);
    }

    @Override // miuix.animation.IHoverStyle
    public void onMotionEvent(MotionEvent motionEvent) {
        a((View) null, motionEvent, new AnimConfig[0]);
    }

    @Override // miuix.animation.IHoverStyle
    public void hoverEnter(AnimConfig... animConfigArr) {
        a(true, animConfigArr);
    }

    private void a(boolean z, AnimConfig... animConfigArr) {
        this.j = z;
        this.m = true;
        if (this.i == IHoverStyle.HoverEffect.FLOATED_WRAPPED) {
            WeakReference<View> weakReference = this.r;
            View view = weakReference != null ? weakReference.get() : null;
            if (view != null) {
                a(view, true);
                b(view, true);
            }
        }
        if (isHideHover()) {
            setMagicView(true);
            setPointerHide(true);
        }
        setCorner(this.o);
        d();
        AnimConfig[] a2 = a(animConfigArr);
        AnimState state = this.a.getState(IHoverStyle.HoverType.ENTER);
        if (a(IHoverStyle.HoverType.ENTER)) {
            IAnimTarget target = this.a.getTarget();
            float max = Math.max(target.getValue(ViewProperty.WIDTH), target.getValue(ViewProperty.HEIGHT));
            double min = Math.min((12.0f + max) / max, 1.15f);
            state.add(ViewProperty.SCALE_X, min).add(ViewProperty.SCALE_Y, min);
        }
        WeakReference<View> weakReference2 = this.t;
        if (weakReference2 != null) {
            Folme.useAt(weakReference2.get()).state().add((FloatProperty) ViewProperty.SCALE_X, 1.0f).add((FloatProperty) ViewProperty.SCALE_Y, 1.0f).to(a2);
        }
        this.a.to(state, a2);
    }

    private void a(int i, AnimConfig... animConfigArr) {
        if (i == 1 || i == 3 || i == 0) {
            hoverEnter(animConfigArr);
        } else if (i == 4 || i == 2) {
            a(false, animConfigArr);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public void hoverEnter(MotionEvent motionEvent, AnimConfig... animConfigArr) {
        a(motionEvent.getToolType(0), animConfigArr);
    }

    @Override // miuix.animation.IHoverStyle
    public void hoverMove(View view, MotionEvent motionEvent, AnimConfig... animConfigArr) {
        b(view, motionEvent, animConfigArr);
    }

    @Override // miuix.animation.IHoverStyle
    public void hoverExit(MotionEvent motionEvent, AnimConfig... animConfigArr) {
        if (this.t != null && !a(this.r.get(), this.n, motionEvent)) {
            Folme.useAt(this.t.get()).hover().hoverEnter(this.e);
        }
        if (b(IHoverStyle.HoverType.EXIT) && this.j) {
            this.a.getState(IHoverStyle.HoverType.EXIT).add(ViewProperty.TRANSLATION_X, 0.0d).add(ViewProperty.TRANSLATION_Y, 0.0d);
        }
        hoverExit(animConfigArr);
    }

    @Override // miuix.animation.IHoverStyle
    public void hoverExit(AnimConfig... animConfigArr) {
        this.a.to(this.a.getState(IHoverStyle.HoverType.EXIT), b(animConfigArr));
    }

    @Override // miuix.animation.IHoverStyle
    public void setHoverEnter() {
        d();
        this.a.setTo(IHoverStyle.HoverType.ENTER);
    }

    @Override // miuix.animation.IHoverStyle
    public void setHoverExit() {
        this.a.setTo(IHoverStyle.HoverType.EXIT);
    }

    @Override // miuix.animation.IHoverStyle
    public boolean isMagicView() {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            return b((View) targetObject);
        }
        return false;
    }

    @Override // miuix.animation.IHoverStyle
    public void setMagicView(boolean z) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            a((View) targetObject, z);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public void setWrapped(boolean z) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            b((View) targetObject, z);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public boolean isWrapped() {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            return c((View) targetObject);
        }
        return false;
    }

    @Override // miuix.animation.IHoverStyle
    public void setPointerHide(boolean z) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            c((View) targetObject, z);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public void setPointerShape(Bitmap bitmap) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            a((View) targetObject, bitmap);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public void setPointerShapeType(int i) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            a((View) targetObject, i);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public int getPointerShapeType() {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            return d((View) targetObject);
        }
        return -1;
    }

    @Override // miuix.animation.IHoverStyle
    public void setHotXOffset(int i) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            b((View) targetObject, i);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public void setHotYOffset(int i) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            c((View) targetObject, i);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public void addMagicPoint(Point point) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            a((View) targetObject, point);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public void clearMagicPoint() {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            e((View) targetObject);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public int getFeedbackColor() {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            return f((View) targetObject);
        }
        return -1;
    }

    @Override // miuix.animation.IHoverStyle
    public void setFeedbackColor(int i) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            d((View) targetObject, i);
        }
    }

    @Override // miuix.animation.IHoverStyle
    public float getFeedbackRadius() {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            return g((View) targetObject);
        }
        return -1.0f;
    }

    @Override // miuix.animation.IHoverStyle
    public void setFeedbackRadius(float f) {
        Object targetObject = this.a.getTarget().getTargetObject();
        if (targetObject instanceof View) {
            a((View) targetObject, f);
        }
    }

    private boolean a(IHoverStyle.HoverType hoverType) {
        return Boolean.TRUE.equals(this.g.get(hoverType));
    }

    private boolean b(IHoverStyle.HoverType hoverType) {
        return Boolean.TRUE.equals(this.h.get(hoverType));
    }

    private IHoverStyle.HoverType a(IHoverStyle.HoverType... hoverTypeArr) {
        return hoverTypeArr.length > 0 ? hoverTypeArr[0] : IHoverStyle.HoverType.ENTER;
    }

    @Override // miuix.animation.controller.FolmeBase, miuix.animation.IStateContainer
    public void clean() {
        super.clean();
        this.g.clear();
        WeakReference<View> weakReference = this.r;
        if (weakReference != null) {
            a(weakReference);
            this.r = null;
        }
        WeakReference<View> weakReference2 = this.s;
        if (weakReference2 != null) {
            a(weakReference2);
            this.s = null;
        }
        WeakReference<View> weakReference3 = this.t;
        if (weakReference3 != null) {
            a(weakReference3);
            this.t = null;
        }
    }

    private void a(IAnimTarget iAnimTarget) {
        View targetObject = iAnimTarget instanceof ViewTarget ? ((ViewTarget) iAnimTarget).getTargetObject() : null;
        if (targetObject != null) {
            float max = Math.max(iAnimTarget.getValue(ViewProperty.WIDTH), iAnimTarget.getValue(ViewProperty.HEIGHT));
            float min = Math.min((12.0f + max) / max, 1.15f);
            this.p = targetObject.getWidth();
            this.q = targetObject.getHeight();
            float f = 0.0f;
            float min2 = Math.min(15.0f, b(Math.max(0.0f, Math.min(1.0f, a(this.p - 40, 0.0f, 360.0f))), 15.0f, 0.0f));
            float min3 = Math.min(15.0f, b(Math.max(0.0f, Math.min(1.0f, a(this.q - 40, 0.0f, 360.0f))), 15.0f, 0.0f));
            if (min != 1.0f) {
                f = Math.min(min2, min3);
            }
            this.c = f;
            int i = this.p;
            int i2 = this.q;
            if (i != i2 || i >= 100 || i2 >= 100) {
                setCorner(36.0f);
            } else {
                setCorner((int) (i * 0.5f));
            }
        }
    }

    private void a(View view, MotionEvent motionEvent) {
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        view.getLocationOnScreen(this.n);
        float width = this.n[0] + (view.getWidth() * 0.5f);
        float height = (rawY - (this.n[1] + (view.getHeight() * 0.5f))) / view.getHeight();
        float f = 1.0f;
        float max = Math.max(-1.0f, Math.min(1.0f, (rawX - width) / view.getWidth()));
        float max2 = Math.max(-1.0f, Math.min(1.0f, height));
        float f2 = this.c;
        if (f2 == Float.MAX_VALUE) {
            f2 = 1.0f;
        }
        float f3 = max * f2;
        float f4 = this.c;
        if (f4 != Float.MAX_VALUE) {
            f = f4;
        }
        this.a.to(this.a.getState(this.u).add(ViewProperty.TRANSLATION_X, f3).add(ViewProperty.TRANSLATION_Y, max2 * f), this.d);
    }

    private View a(WeakReference<View> weakReference) {
        View view = weakReference.get();
        if (view != null) {
            view.setOnHoverListener(null);
        }
        return view;
    }

    private AnimConfig[] a(AnimConfig... animConfigArr) {
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.e);
    }

    private AnimConfig[] b(AnimConfig... animConfigArr) {
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.f);
    }

    public boolean isHideHover() {
        boolean z;
        return this.p < 100 && this.q < 100 && (!(z = this.j) || (z && (this.i == IHoverStyle.HoverEffect.FLOATED || this.i == IHoverStyle.HoverEffect.FLOATED_WRAPPED)));
    }

    private static boolean b(View view) {
        try {
            return ((Boolean) Class.forName("android.view.View").getMethod("isMagicView", new Class[0]).invoke(view, new Object[0])).booleanValue();
        } catch (Exception e) {
            Log.e("", "isMagicView failed , e:" + e.toString());
            return false;
        }
    }

    private static void a(View view, boolean z) {
        try {
            Class.forName("android.view.View").getMethod("setMagicView", Boolean.TYPE).invoke(view, Boolean.valueOf(z));
        } catch (Exception e) {
            Log.e("", "setMagicView failed , e:" + e.toString());
        }
    }

    private static void b(View view, boolean z) {
        try {
            Class.forName("android.view.View").getMethod("setWrapped", Boolean.TYPE).invoke(view, Boolean.valueOf(z));
        } catch (Exception e) {
            Log.e("", "setWrapped failed , e:" + e.toString());
        }
    }

    private static boolean c(View view) {
        try {
            return ((Boolean) Class.forName("android.view.View").getMethod("isWrapped", new Class[0]).invoke(view, new Object[0])).booleanValue();
        } catch (Exception e) {
            Log.e("", " isWrapped failed , e:" + e.toString());
            return false;
        }
    }

    private static void c(View view, boolean z) {
        try {
            Class.forName("android.view.View").getMethod("setPointerHide", Boolean.TYPE).invoke(view, Boolean.valueOf(z));
        } catch (Exception e) {
            Log.e("", "setPointerHide failed , e:" + e.toString());
        }
    }

    private static void a(View view, Bitmap bitmap) {
        try {
            Class.forName("android.view.View").getMethod("setPointerShape", Bitmap.class).invoke(view, bitmap);
        } catch (Exception e) {
            Log.e("", "setPointerShape failed , e:" + e.toString());
        }
    }

    private static void a(View view, int i) {
        try {
            Class.forName("android.view.View").getMethod("setPointerShapeType", Integer.TYPE).invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("", "setPointerShapeType failed , e:" + e.toString());
        }
    }

    private static int d(View view) {
        try {
            return ((Integer) Class.forName("android.view.View").getMethod("getPointerShapeType", new Class[0]).invoke(view, new Object[0])).intValue();
        } catch (Exception e) {
            Log.e("", "getPointerShapeType failed , e:" + e.toString());
            return -1;
        }
    }

    private static void b(View view, int i) {
        try {
            Class.forName("android.view.View").getMethod("setHotXOffset", Integer.TYPE).invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("", "setHotXOffset failed , e:" + e.toString());
        }
    }

    private static void c(View view, int i) {
        try {
            Class.forName("android.view.View").getMethod("setHotYOffset", Integer.TYPE).invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("", "setHotYOffset failed , e:" + e.toString());
        }
    }

    private static void a(View view, Point point) {
        try {
            Class.forName("android.view.View").getMethod("addMagicPoint", Point.class).invoke(view, point);
        } catch (Exception e) {
            Log.e("", "addMagicPoint failed , e:" + e.toString());
        }
    }

    private static void e(View view) {
        try {
            Class.forName("android.view.View").getMethod("clearMagicPoint", new Class[0]).invoke(view, new Object[0]);
        } catch (Exception e) {
            Log.e("", "clearMagicPoint failed , e:" + e.toString());
        }
    }

    private static int f(View view) {
        try {
            return ((Integer) Class.forName("android.view.View").getMethod("getFeedbackColor", new Class[0]).invoke(view, new Object[0])).intValue();
        } catch (Exception e) {
            Log.e("", "getFeedbackColor failed , e:" + e.toString());
            return -1;
        }
    }

    private static void d(View view, int i) {
        try {
            Class.forName("android.view.View").getMethod("setFeedbackColor", Integer.TYPE).invoke(view, Integer.valueOf(i));
        } catch (Exception e) {
            Log.e("", "setFeedbackColor failed , e:" + e.toString());
        }
    }

    private static float g(View view) {
        try {
            return ((Float) Class.forName("android.view.View").getMethod("getFeedbackRadius", new Class[0]).invoke(view, new Object[0])).floatValue();
        } catch (Exception e) {
            Log.e("", "getFeedbackRadius failed , e:" + e.toString());
            return -1.0f;
        }
    }

    private static void a(View view, float f) {
        try {
            Class.forName("android.view.View").getMethod("setFeedbackRadius", Float.TYPE).invoke(view, Float.valueOf(f));
        } catch (Exception e) {
            Log.e("", "setFeedbackRadius failed , e:" + e.toString());
        }
    }
}
