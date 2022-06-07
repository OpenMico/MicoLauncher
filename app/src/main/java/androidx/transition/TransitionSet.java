package androidx.transition;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class TransitionSet extends Transition {
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    int a;
    private ArrayList<Transition> j = new ArrayList<>();
    private boolean k = true;
    boolean i = false;
    private int l = 0;

    public TransitionSet() {
    }

    @SuppressLint({"RestrictedApi"})
    public TransitionSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, o.i);
        setOrdering(TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionOrdering", 0, 0));
        obtainStyledAttributes.recycle();
    }

    @NonNull
    public TransitionSet setOrdering(int i) {
        switch (i) {
            case 0:
                this.k = true;
                break;
            case 1:
                this.k = false;
                break;
            default:
                throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + i);
        }
        return this;
    }

    public int getOrdering() {
        return !this.k ? 1 : 0;
    }

    @NonNull
    public TransitionSet addTransition(@NonNull Transition transition) {
        a(transition);
        if (this.b >= 0) {
            transition.setDuration(this.b);
        }
        if ((this.l & 1) != 0) {
            transition.setInterpolator(getInterpolator());
        }
        if ((this.l & 2) != 0) {
            transition.setPropagation(getPropagation());
        }
        if ((this.l & 4) != 0) {
            transition.setPathMotion(getPathMotion());
        }
        if ((this.l & 8) != 0) {
            transition.setEpicenterCallback(getEpicenterCallback());
        }
        return this;
    }

    private void a(@NonNull Transition transition) {
        this.j.add(transition);
        transition.e = this;
    }

    public int getTransitionCount() {
        return this.j.size();
    }

    @Nullable
    public Transition getTransitionAt(int i) {
        if (i < 0 || i >= this.j.size()) {
            return null;
        }
        return this.j.get(i);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet setDuration(long j) {
        ArrayList<Transition> arrayList;
        super.setDuration(j);
        if (this.b >= 0 && (arrayList = this.j) != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.j.get(i).setDuration(j);
            }
        }
        return this;
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet setStartDelay(long j) {
        return (TransitionSet) super.setStartDelay(j);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        this.l |= 1;
        ArrayList<Transition> arrayList = this.j;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.j.get(i).setInterpolator(timeInterpolator);
            }
        }
        return (TransitionSet) super.setInterpolator(timeInterpolator);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet addTarget(@NonNull View view) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).addTarget(view);
        }
        return (TransitionSet) super.addTarget(view);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet addTarget(@IdRes int i) {
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            this.j.get(i2).addTarget(i);
        }
        return (TransitionSet) super.addTarget(i);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet addTarget(@NonNull String str) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).addTarget(str);
        }
        return (TransitionSet) super.addTarget(str);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet addTarget(@NonNull Class<?> cls) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).addTarget(cls);
        }
        return (TransitionSet) super.addTarget(cls);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet addListener(@NonNull Transition.TransitionListener transitionListener) {
        return (TransitionSet) super.addListener(transitionListener);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet removeTarget(@IdRes int i) {
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            this.j.get(i2).removeTarget(i);
        }
        return (TransitionSet) super.removeTarget(i);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet removeTarget(@NonNull View view) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).removeTarget(view);
        }
        return (TransitionSet) super.removeTarget(view);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet removeTarget(@NonNull Class<?> cls) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).removeTarget(cls);
        }
        return (TransitionSet) super.removeTarget(cls);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet removeTarget(@NonNull String str) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).removeTarget(str);
        }
        return (TransitionSet) super.removeTarget(str);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public Transition excludeTarget(@NonNull View view, boolean z) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).excludeTarget(view, z);
        }
        return super.excludeTarget(view, z);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public Transition excludeTarget(@NonNull String str, boolean z) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).excludeTarget(str, z);
        }
        return super.excludeTarget(str, z);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public Transition excludeTarget(int i, boolean z) {
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            this.j.get(i2).excludeTarget(i, z);
        }
        return super.excludeTarget(i, z);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public Transition excludeTarget(@NonNull Class<?> cls, boolean z) {
        for (int i = 0; i < this.j.size(); i++) {
            this.j.get(i).excludeTarget(cls, z);
        }
        return super.excludeTarget(cls, z);
    }

    @Override // androidx.transition.Transition
    @NonNull
    public TransitionSet removeListener(@NonNull Transition.TransitionListener transitionListener) {
        return (TransitionSet) super.removeListener(transitionListener);
    }

    @Override // androidx.transition.Transition
    public void setPathMotion(PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        this.l |= 4;
        if (this.j != null) {
            for (int i = 0; i < this.j.size(); i++) {
                this.j.get(i).setPathMotion(pathMotion);
            }
        }
    }

    @NonNull
    public TransitionSet removeTransition(@NonNull Transition transition) {
        this.j.remove(transition);
        transition.e = null;
        return this;
    }

    private void a() {
        a aVar = new a(this);
        Iterator<Transition> it = this.j.iterator();
        while (it.hasNext()) {
            it.next().addListener(aVar);
        }
        this.a = this.j.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends TransitionListenerAdapter {
        TransitionSet a;

        a(TransitionSet transitionSet) {
            this.a = transitionSet;
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public void onTransitionStart(@NonNull Transition transition) {
            if (!this.a.i) {
                this.a.start();
                this.a.i = true;
            }
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public void onTransitionEnd(@NonNull Transition transition) {
            TransitionSet transitionSet = this.a;
            transitionSet.a--;
            if (this.a.a == 0) {
                TransitionSet transitionSet2 = this.a;
                transitionSet2.i = false;
                transitionSet2.end();
            }
            transition.removeListener(this);
        }
    }

    @Override // androidx.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void createAnimators(ViewGroup viewGroup, q qVar, q qVar2, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        long startDelay = getStartDelay();
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            Transition transition = this.j.get(i);
            if (startDelay > 0 && (this.k || i == 0)) {
                long startDelay2 = transition.getStartDelay();
                if (startDelay2 > 0) {
                    transition.setStartDelay(startDelay2 + startDelay);
                } else {
                    transition.setStartDelay(startDelay);
                }
            }
            transition.createAnimators(viewGroup, qVar, qVar2, arrayList, arrayList2);
        }
    }

    @Override // androidx.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void runAnimators() {
        if (this.j.isEmpty()) {
            start();
            end();
            return;
        }
        a();
        if (!this.k) {
            for (int i = 1; i < this.j.size(); i++) {
                final Transition transition = this.j.get(i);
                this.j.get(i - 1).addListener(new TransitionListenerAdapter() { // from class: androidx.transition.TransitionSet.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(@NonNull Transition transition2) {
                        transition.runAnimators();
                        transition2.removeListener(this);
                    }
                });
            }
            Transition transition2 = this.j.get(0);
            if (transition2 != null) {
                transition2.runAnimators();
                return;
            }
            return;
        }
        Iterator<Transition> it = this.j.iterator();
        while (it.hasNext()) {
            it.next().runAnimators();
        }
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        if (b(transitionValues.view)) {
            Iterator<Transition> it = this.j.iterator();
            while (it.hasNext()) {
                Transition next = it.next();
                if (next.b(transitionValues.view)) {
                    next.captureStartValues(transitionValues);
                    transitionValues.a.add(next);
                }
            }
        }
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        if (b(transitionValues.view)) {
            Iterator<Transition> it = this.j.iterator();
            while (it.hasNext()) {
                Transition next = it.next();
                if (next.b(transitionValues.view)) {
                    next.captureEndValues(transitionValues);
                    transitionValues.a.add(next);
                }
            }
        }
    }

    @Override // androidx.transition.Transition
    void a(TransitionValues transitionValues) {
        super.a(transitionValues);
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).a(transitionValues);
        }
    }

    @Override // androidx.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void pause(View view) {
        super.pause(view);
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).pause(view);
        }
    }

    @Override // androidx.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void resume(View view) {
        super.resume(view);
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).resume(view);
        }
    }

    @Override // androidx.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void cancel() {
        super.cancel();
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).cancel();
        }
    }

    @Override // androidx.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    void b(ViewGroup viewGroup) {
        super.b(viewGroup);
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).b(viewGroup);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: d */
    public TransitionSet c(ViewGroup viewGroup) {
        super.c(viewGroup);
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).c(viewGroup);
        }
        return this;
    }

    @Override // androidx.transition.Transition
    void b(boolean z) {
        super.b(z);
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).b(z);
        }
    }

    @Override // androidx.transition.Transition
    public void setPropagation(TransitionPropagation transitionPropagation) {
        super.setPropagation(transitionPropagation);
        this.l |= 2;
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).setPropagation(transitionPropagation);
        }
    }

    @Override // androidx.transition.Transition
    public void setEpicenterCallback(Transition.EpicenterCallback epicenterCallback) {
        super.setEpicenterCallback(epicenterCallback);
        this.l |= 8;
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            this.j.get(i).setEpicenterCallback(epicenterCallback);
        }
    }

    @Override // androidx.transition.Transition
    String a(String str) {
        String a2 = super.a(str);
        for (int i = 0; i < this.j.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(a2);
            sb.append("\n");
            sb.append(this.j.get(i).a(str + "  "));
            a2 = sb.toString();
        }
        return a2;
    }

    @Override // androidx.transition.Transition
    public Transition clone() {
        TransitionSet transitionSet = (TransitionSet) super.clone();
        transitionSet.j = new ArrayList<>();
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            transitionSet.a(this.j.get(i).clone());
        }
        return transitionSet;
    }
}
