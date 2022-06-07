package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.d;
import androidx.fragment.app.r;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: DefaultSpecialEffectsController.java */
/* loaded from: classes.dex */
class c extends r {
    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@NonNull ViewGroup viewGroup) {
        super(viewGroup);
    }

    @Override // androidx.fragment.app.r
    void a(@NonNull List<r.b> list, boolean z) {
        r.b bVar = null;
        r.b bVar2 = null;
        for (r.b bVar3 : list) {
            r.b.EnumC0018b a2 = r.b.EnumC0018b.a(bVar3.e().mView);
            switch (bVar3.c()) {
                case GONE:
                case INVISIBLE:
                case REMOVED:
                    if (a2 == r.b.EnumC0018b.VISIBLE && bVar == null) {
                        bVar = bVar3;
                        break;
                    }
                    break;
                case VISIBLE:
                    if (a2 != r.b.EnumC0018b.VISIBLE) {
                        bVar2 = bVar3;
                        break;
                    } else {
                        break;
                    }
            }
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList<r.b> arrayList3 = new ArrayList(list);
        Iterator<r.b> it = list.iterator();
        while (true) {
            boolean z2 = true;
            if (it.hasNext()) {
                final r.b next = it.next();
                CancellationSignal cancellationSignal = new CancellationSignal();
                next.a(cancellationSignal);
                arrayList.add(new a(next, cancellationSignal, z));
                CancellationSignal cancellationSignal2 = new CancellationSignal();
                next.a(cancellationSignal2);
                z2 = false;
                if (z) {
                    if (next == bVar) {
                        arrayList2.add(new C0017c(next, cancellationSignal2, z, z2));
                        next.a(new Runnable() { // from class: androidx.fragment.app.c.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (arrayList3.contains(next)) {
                                    arrayList3.remove(next);
                                    c.this.a(next);
                                }
                            }
                        });
                    }
                    arrayList2.add(new C0017c(next, cancellationSignal2, z, z2));
                    next.a(new Runnable() { // from class: androidx.fragment.app.c.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (arrayList3.contains(next)) {
                                arrayList3.remove(next);
                                c.this.a(next);
                            }
                        }
                    });
                } else {
                    if (next == bVar2) {
                        arrayList2.add(new C0017c(next, cancellationSignal2, z, z2));
                        next.a(new Runnable() { // from class: androidx.fragment.app.c.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (arrayList3.contains(next)) {
                                    arrayList3.remove(next);
                                    c.this.a(next);
                                }
                            }
                        });
                    }
                    arrayList2.add(new C0017c(next, cancellationSignal2, z, z2));
                    next.a(new Runnable() { // from class: androidx.fragment.app.c.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (arrayList3.contains(next)) {
                                arrayList3.remove(next);
                                c.this.a(next);
                            }
                        }
                    });
                }
            } else {
                Map<r.b, Boolean> a3 = a(arrayList2, arrayList3, z, bVar, bVar2);
                a(arrayList, arrayList3, a3.containsValue(true), a3);
                for (r.b bVar4 : arrayList3) {
                    a(bVar4);
                }
                arrayList3.clear();
                return;
            }
        }
    }

    private void a(@NonNull List<a> list, @NonNull List<r.b> list2, boolean z, @NonNull Map<r.b, Boolean> map) {
        final ViewGroup a2 = a();
        Context context = a2.getContext();
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        for (final a aVar : list) {
            if (aVar.c()) {
                aVar.d();
            } else {
                d.a a3 = aVar.a(context);
                if (a3 == null) {
                    aVar.d();
                } else {
                    final Animator animator = a3.b;
                    if (animator == null) {
                        arrayList.add(aVar);
                    } else {
                        final r.b a4 = aVar.a();
                        Fragment e = a4.e();
                        if (Boolean.TRUE.equals(map.get(a4))) {
                            if (FragmentManager.a(2)) {
                                Log.v("FragmentManager", "Ignoring Animator set on " + e + " as this Fragment was involved in a Transition.");
                            }
                            aVar.d();
                        } else {
                            final boolean z3 = a4.c() == r.b.EnumC0018b.GONE;
                            if (z3) {
                                list2.remove(a4);
                            }
                            final View view = e.mView;
                            a2.startViewTransition(view);
                            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.c.3
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    a2.endViewTransition(view);
                                    if (z3) {
                                        a4.c().b(view);
                                    }
                                    aVar.d();
                                }
                            });
                            animator.setTarget(view);
                            animator.start();
                            aVar.b().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.c.4
                                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                                public void onCancel() {
                                    animator.end();
                                }
                            });
                            z2 = true;
                        }
                    }
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            final a aVar2 = (a) it.next();
            r.b a5 = aVar2.a();
            Fragment e2 = a5.e();
            if (z) {
                if (FragmentManager.a(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + e2 + " as Animations cannot run alongside Transitions.");
                }
                aVar2.d();
            } else if (z2) {
                if (FragmentManager.a(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + e2 + " as Animations cannot run alongside Animators.");
                }
                aVar2.d();
            } else {
                final View view2 = e2.mView;
                Animation animation = (Animation) Preconditions.checkNotNull(((d.a) Preconditions.checkNotNull(aVar2.a(context))).a);
                if (a5.c() != r.b.EnumC0018b.REMOVED) {
                    view2.startAnimation(animation);
                    aVar2.d();
                } else {
                    a2.startViewTransition(view2);
                    d.b bVar = new d.b(animation, a2, view2);
                    bVar.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.c.5
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            a2.post(new Runnable() { // from class: androidx.fragment.app.c.5.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    a2.endViewTransition(view2);
                                    aVar2.d();
                                }
                            });
                        }
                    });
                    view2.startAnimation(bVar);
                }
                aVar2.b().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.c.6
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        view2.clearAnimation();
                        a2.endViewTransition(view2);
                        aVar2.d();
                    }
                });
            }
        }
    }

    @NonNull
    private Map<r.b, Boolean> a(@NonNull List<C0017c> list, @NonNull List<r.b> list2, final boolean z, @Nullable final r.b bVar, @Nullable final r.b bVar2) {
        r.b bVar3;
        Object obj;
        boolean z2;
        FragmentTransitionImpl fragmentTransitionImpl;
        ArrayMap arrayMap;
        c cVar;
        SharedElementCallback sharedElementCallback;
        SharedElementCallback sharedElementCallback2;
        ArrayList<String> arrayList;
        View view;
        c cVar2;
        String a2;
        c cVar3 = this;
        boolean z3 = z;
        r.b bVar4 = bVar;
        r.b bVar5 = bVar2;
        HashMap hashMap = new HashMap();
        FragmentTransitionImpl fragmentTransitionImpl2 = null;
        for (C0017c cVar4 : list) {
            if (!cVar4.c()) {
                FragmentTransitionImpl i = cVar4.i();
                if (fragmentTransitionImpl2 == null) {
                    fragmentTransitionImpl2 = i;
                } else if (!(i == null || fragmentTransitionImpl2 == i)) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + cVar4.a().e() + " returned Transition " + cVar4.e() + " which uses a different Transition  type than other Fragments.");
                }
            }
        }
        boolean z4 = false;
        if (fragmentTransitionImpl2 == null) {
            for (C0017c cVar5 : list) {
                hashMap.put(cVar5.a(), false);
                cVar5.d();
            }
            return hashMap;
        }
        View view2 = new View(a().getContext());
        final Rect rect = new Rect();
        ArrayList<View> arrayList2 = new ArrayList<>();
        ArrayList<View> arrayList3 = new ArrayList<>();
        ArrayMap arrayMap2 = new ArrayMap();
        boolean z5 = false;
        Object obj2 = null;
        View view3 = null;
        ArrayMap arrayMap3 = arrayMap2;
        final FragmentTransitionImpl fragmentTransitionImpl3 = fragmentTransitionImpl2;
        for (C0017c cVar6 : list) {
            if (!cVar6.g() || bVar4 == null || bVar5 == null) {
                arrayMap = arrayMap3;
                bVar5 = bVar5;
                z4 = z4;
                fragmentTransitionImpl = fragmentTransitionImpl3;
                arrayList3 = arrayList3;
                hashMap = hashMap;
                rect = rect;
                bVar4 = bVar4;
                view2 = view2;
                arrayList2 = arrayList2;
                cVar = cVar3;
                view3 = view3;
            } else {
                Object wrapTransitionInSet = fragmentTransitionImpl3.wrapTransitionInSet(fragmentTransitionImpl3.cloneTransition(cVar6.h()));
                ArrayList<String> sharedElementSourceNames = bVar2.e().getSharedElementSourceNames();
                ArrayList<String> sharedElementSourceNames2 = bVar.e().getSharedElementSourceNames();
                int i2 = 0;
                for (ArrayList<String> sharedElementTargetNames = bVar.e().getSharedElementTargetNames(); i2 < sharedElementTargetNames.size(); sharedElementTargetNames = sharedElementTargetNames) {
                    int indexOf = sharedElementSourceNames.indexOf(sharedElementTargetNames.get(i2));
                    if (indexOf != -1) {
                        sharedElementSourceNames.set(indexOf, sharedElementSourceNames2.get(i2));
                    }
                    i2++;
                }
                ArrayList<String> sharedElementTargetNames2 = bVar2.e().getSharedElementTargetNames();
                if (!z3) {
                    sharedElementCallback2 = bVar.e().getExitTransitionCallback();
                    sharedElementCallback = bVar2.e().getEnterTransitionCallback();
                } else {
                    sharedElementCallback2 = bVar.e().getEnterTransitionCallback();
                    sharedElementCallback = bVar2.e().getExitTransitionCallback();
                }
                int i3 = 0;
                for (int size = sharedElementSourceNames.size(); i3 < size; size = size) {
                    arrayMap3.put(sharedElementSourceNames.get(i3), sharedElementTargetNames2.get(i3));
                    i3++;
                }
                ArrayMap<String, View> arrayMap4 = new ArrayMap<>();
                cVar3.a(arrayMap4, bVar.e().mView);
                arrayMap4.retainAll(sharedElementSourceNames);
                if (sharedElementCallback2 != null) {
                    sharedElementCallback2.onMapSharedElements(sharedElementSourceNames, arrayMap4);
                    for (int size2 = sharedElementSourceNames.size() - 1; size2 >= 0; size2--) {
                        String str = sharedElementSourceNames.get(size2);
                        View view4 = arrayMap4.get(str);
                        if (view4 == null) {
                            arrayMap3.remove(str);
                            sharedElementSourceNames = sharedElementSourceNames;
                        } else {
                            sharedElementSourceNames = sharedElementSourceNames;
                            if (!str.equals(ViewCompat.getTransitionName(view4))) {
                                arrayMap3.put(ViewCompat.getTransitionName(view4), (String) arrayMap3.remove(str));
                            }
                        }
                    }
                    arrayList = sharedElementSourceNames;
                } else {
                    arrayList = sharedElementSourceNames;
                    arrayMap3.retainAll(arrayMap4.keySet());
                }
                final ArrayMap<String, View> arrayMap5 = new ArrayMap<>();
                cVar3.a(arrayMap5, bVar2.e().mView);
                arrayMap5.retainAll(sharedElementTargetNames2);
                arrayMap5.retainAll(arrayMap3.values());
                if (sharedElementCallback != null) {
                    sharedElementCallback.onMapSharedElements(sharedElementTargetNames2, arrayMap5);
                    for (int size3 = sharedElementTargetNames2.size() - 1; size3 >= 0; size3--) {
                        String str2 = sharedElementTargetNames2.get(size3);
                        View view5 = arrayMap5.get(str2);
                        if (view5 == null) {
                            String a3 = n.a(arrayMap3, str2);
                            if (a3 != null) {
                                arrayMap3.remove(a3);
                            }
                        } else if (!str2.equals(ViewCompat.getTransitionName(view5)) && (a2 = n.a(arrayMap3, str2)) != null) {
                            arrayMap3.put(a2, ViewCompat.getTransitionName(view5));
                        }
                    }
                } else {
                    n.a(arrayMap3, arrayMap5);
                }
                cVar3.a(arrayMap4, arrayMap3.keySet());
                cVar3.a(arrayMap5, arrayMap3.values());
                if (arrayMap3.isEmpty()) {
                    arrayList2.clear();
                    arrayList3.clear();
                    arrayMap = arrayMap3;
                    arrayList3 = arrayList3;
                    rect = rect;
                    view2 = view2;
                    fragmentTransitionImpl = fragmentTransitionImpl3;
                    view3 = view3;
                    obj2 = null;
                    z4 = false;
                    bVar5 = bVar2;
                    hashMap = hashMap;
                    bVar4 = bVar;
                    arrayList2 = arrayList2;
                    cVar = cVar3;
                } else {
                    n.a(bVar2.e(), bVar.e(), z3, arrayMap4, true);
                    arrayMap = arrayMap3;
                    arrayList2 = arrayList2;
                    OneShotPreDrawListener.add(a(), new Runnable() { // from class: androidx.fragment.app.c.7
                        @Override // java.lang.Runnable
                        public void run() {
                            n.a(bVar2.e(), bVar.e(), z, (ArrayMap<String, View>) arrayMap5, false);
                        }
                    });
                    arrayList2.addAll(arrayMap4.values());
                    if (!arrayList.isEmpty()) {
                        z4 = false;
                        view3 = arrayMap4.get(arrayList.get(0));
                        fragmentTransitionImpl3.setEpicenter(wrapTransitionInSet, view3);
                    } else {
                        z4 = false;
                        view3 = view3;
                    }
                    arrayList3.addAll(arrayMap5.values());
                    if (!sharedElementTargetNames2.isEmpty()) {
                        int i4 = z4 ? 1 : 0;
                        int i5 = z4 ? 1 : 0;
                        int i6 = z4 ? 1 : 0;
                        int i7 = z4 ? 1 : 0;
                        int i8 = z4 ? 1 : 0;
                        final View view6 = arrayMap5.get(sharedElementTargetNames2.get(i4));
                        if (view6 != null) {
                            c cVar7 = this;
                            OneShotPreDrawListener.add(a(), new Runnable() { // from class: androidx.fragment.app.c.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    fragmentTransitionImpl3.getBoundsOnScreen(view6, rect);
                                }
                            });
                            view = view2;
                            z5 = true;
                            cVar = cVar7;
                            fragmentTransitionImpl3.setSharedElementTargets(wrapTransitionInSet, view, arrayList2);
                            rect = rect;
                            view2 = view;
                            arrayList3 = arrayList3;
                            fragmentTransitionImpl = fragmentTransitionImpl3;
                            fragmentTransitionImpl3.scheduleRemoveTargets(wrapTransitionInSet, null, null, null, null, wrapTransitionInSet, arrayList3);
                            hashMap = hashMap;
                            bVar4 = bVar;
                            hashMap.put(bVar4, true);
                            bVar5 = bVar2;
                            hashMap.put(bVar5, true);
                            obj2 = wrapTransitionInSet;
                        } else {
                            cVar2 = this;
                        }
                    } else {
                        cVar2 = this;
                    }
                    view = view2;
                    cVar = cVar2;
                    fragmentTransitionImpl3.setSharedElementTargets(wrapTransitionInSet, view, arrayList2);
                    rect = rect;
                    view2 = view;
                    arrayList3 = arrayList3;
                    fragmentTransitionImpl = fragmentTransitionImpl3;
                    fragmentTransitionImpl3.scheduleRemoveTargets(wrapTransitionInSet, null, null, null, null, wrapTransitionInSet, arrayList3);
                    hashMap = hashMap;
                    bVar4 = bVar;
                    hashMap.put(bVar4, true);
                    bVar5 = bVar2;
                    hashMap.put(bVar5, true);
                    obj2 = wrapTransitionInSet;
                }
            }
            z3 = z;
            arrayMap3 = arrayMap;
            cVar3 = cVar;
            fragmentTransitionImpl3 = fragmentTransitionImpl;
        }
        View view7 = view3;
        boolean z6 = z4 ? 1 : 0;
        boolean z7 = z4 ? 1 : 0;
        boolean z8 = z4 ? 1 : 0;
        boolean z9 = z4 ? 1 : 0;
        boolean z10 = z4 ? 1 : 0;
        boolean z11 = z4 ? 1 : 0;
        boolean z12 = z6;
        ArrayList<View> arrayList4 = arrayList3;
        HashMap hashMap2 = hashMap;
        View view8 = view2;
        ArrayList<View> arrayList5 = arrayList2;
        ArrayList arrayList6 = new ArrayList();
        Iterator<C0017c> it = list.iterator();
        Object obj3 = null;
        Object obj4 = null;
        while (it.hasNext()) {
            C0017c next = it.next();
            if (next.c()) {
                hashMap2.put(next.a(), Boolean.valueOf(z12));
                next.d();
                it = it;
            } else {
                Object cloneTransition = fragmentTransitionImpl3.cloneTransition(next.e());
                r.b a4 = next.a();
                boolean z13 = (obj2 == null || !(a4 == bVar4 || a4 == bVar5)) ? z12 : true;
                if (cloneTransition == null) {
                    if (!z13) {
                        hashMap2.put(a4, Boolean.valueOf(z12));
                        next.d();
                    }
                    arrayList5 = arrayList5;
                    view8 = view8;
                    arrayList4 = arrayList4;
                    obj3 = obj3;
                    obj4 = obj4;
                    hashMap2 = hashMap2;
                    view7 = view7;
                } else {
                    final ArrayList<View> arrayList7 = new ArrayList<>();
                    cVar3.a(arrayList7, a4.e().mView);
                    if (z13) {
                        if (a4 == bVar4) {
                            arrayList7.removeAll(arrayList5);
                        } else {
                            arrayList7.removeAll(arrayList4);
                        }
                    }
                    if (arrayList7.isEmpty()) {
                        fragmentTransitionImpl3.addTarget(cloneTransition, view8);
                        arrayList5 = arrayList5;
                        view8 = view8;
                        arrayList4 = arrayList4;
                        bVar3 = a4;
                        obj4 = obj4;
                        hashMap2 = hashMap2;
                        obj = obj3;
                    } else {
                        fragmentTransitionImpl3.addTargets(cloneTransition, arrayList7);
                        view8 = view8;
                        obj = obj3;
                        arrayList5 = arrayList5;
                        obj4 = obj4;
                        arrayList4 = arrayList4;
                        hashMap2 = hashMap2;
                        fragmentTransitionImpl3.scheduleRemoveTargets(cloneTransition, cloneTransition, arrayList7, null, null, null, null);
                        if (a4.c() == r.b.EnumC0018b.GONE) {
                            bVar3 = a4;
                            list2.remove(bVar3);
                            ArrayList<View> arrayList8 = new ArrayList<>(arrayList7);
                            arrayList8.remove(bVar3.e().mView);
                            fragmentTransitionImpl3.scheduleHideFragmentView(cloneTransition, bVar3.e().mView, arrayList8);
                            OneShotPreDrawListener.add(a(), new Runnable() { // from class: androidx.fragment.app.c.9
                                @Override // java.lang.Runnable
                                public void run() {
                                    n.a(arrayList7, 4);
                                }
                            });
                        } else {
                            bVar3 = a4;
                        }
                    }
                    if (bVar3.c() == r.b.EnumC0018b.VISIBLE) {
                        arrayList6.addAll(arrayList7);
                        if (z5) {
                            fragmentTransitionImpl3.setEpicenter(cloneTransition, rect);
                            view7 = view7;
                            z2 = true;
                        } else {
                            view7 = view7;
                            z2 = true;
                        }
                    } else {
                        view7 = view7;
                        fragmentTransitionImpl3.setEpicenter(cloneTransition, view7);
                        z2 = true;
                    }
                    hashMap2.put(bVar3, Boolean.valueOf(z2));
                    if (next.f()) {
                        obj4 = fragmentTransitionImpl3.mergeTransitionsTogether(obj4, cloneTransition, null);
                        obj3 = obj;
                    } else {
                        obj3 = fragmentTransitionImpl3.mergeTransitionsTogether(obj, cloneTransition, null);
                    }
                }
                z12 = false;
                it = it;
            }
        }
        boolean z14 = true;
        Object mergeTransitionsInSequence = fragmentTransitionImpl3.mergeTransitionsInSequence(obj4, obj3, obj2);
        for (final C0017c cVar8 : list) {
            if (!cVar8.c()) {
                Object e = cVar8.e();
                r.b a5 = cVar8.a();
                if (obj2 == null || !(a5 == bVar4 || a5 == bVar5)) {
                    z14 = false;
                }
                if (e != null || z14) {
                    if (!ViewCompat.isLaidOut(a())) {
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Container " + a() + " has not been laid out. Completing operation " + a5);
                        }
                        cVar8.d();
                    } else {
                        fragmentTransitionImpl3.setListenerForTransitionEnd(cVar8.a().e(), mergeTransitionsInSequence, cVar8.b(), new Runnable() { // from class: androidx.fragment.app.c.10
                            @Override // java.lang.Runnable
                            public void run() {
                                cVar8.d();
                            }
                        });
                    }
                }
            }
        }
        if (!ViewCompat.isLaidOut(a())) {
            return hashMap2;
        }
        n.a(arrayList6, 4);
        ArrayList<String> a6 = fragmentTransitionImpl3.a(arrayList4);
        fragmentTransitionImpl3.beginDelayedTransition(a(), mergeTransitionsInSequence);
        fragmentTransitionImpl3.a(a(), arrayList5, arrayList4, a6, arrayMap3);
        n.a(arrayList6, 0);
        fragmentTransitionImpl3.swapSharedElementTargets(obj2, arrayList5, arrayList4);
        return hashMap2;
    }

    void a(@NonNull ArrayMap<String, View> arrayMap, @NonNull Collection<String> collection) {
        Iterator<Map.Entry<String, View>> it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(ViewCompat.getTransitionName(it.next().getValue()))) {
                it.remove();
            }
        }
    }

    void a(ArrayList<View> arrayList, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (!ViewGroupCompat.isTransitionGroup(viewGroup)) {
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (childAt.getVisibility() == 0) {
                        a(arrayList, childAt);
                    }
                }
            } else if (!arrayList.contains(view)) {
                arrayList.add(viewGroup);
            }
        } else if (!arrayList.contains(view)) {
            arrayList.add(view);
        }
    }

    void a(Map<String, View> map, @NonNull View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            map.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    a(map, childAt);
                }
            }
        }
    }

    void a(@NonNull r.b bVar) {
        bVar.c().b(bVar.e().mView);
    }

    /* compiled from: DefaultSpecialEffectsController.java */
    /* loaded from: classes.dex */
    private static class b {
        @NonNull
        private final r.b a;
        @NonNull
        private final CancellationSignal b;

        b(@NonNull r.b bVar, @NonNull CancellationSignal cancellationSignal) {
            this.a = bVar;
            this.b = cancellationSignal;
        }

        @NonNull
        r.b a() {
            return this.a;
        }

        @NonNull
        CancellationSignal b() {
            return this.b;
        }

        boolean c() {
            r.b.EnumC0018b a = r.b.EnumC0018b.a(this.a.e().mView);
            r.b.EnumC0018b c = this.a.c();
            return a == c || !(a == r.b.EnumC0018b.VISIBLE || c == r.b.EnumC0018b.VISIBLE);
        }

        void d() {
            this.a.b(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DefaultSpecialEffectsController.java */
    /* loaded from: classes.dex */
    public static class a extends b {
        private boolean a;
        private boolean b = false;
        @Nullable
        private d.a c;

        a(@NonNull r.b bVar, @NonNull CancellationSignal cancellationSignal, boolean z) {
            super(bVar, cancellationSignal);
            this.a = z;
        }

        @Nullable
        d.a a(@NonNull Context context) {
            if (this.b) {
                return this.c;
            }
            this.c = d.a(context, a().e(), a().c() == r.b.EnumC0018b.VISIBLE, this.a);
            this.b = true;
            return this.c;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DefaultSpecialEffectsController.java */
    /* renamed from: androidx.fragment.app.c$c  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static class C0017c extends b {
        @Nullable
        private final Object a;
        private final boolean b;
        @Nullable
        private final Object c;

        C0017c(@NonNull r.b bVar, @NonNull CancellationSignal cancellationSignal, boolean z, boolean z2) {
            super(bVar, cancellationSignal);
            Object obj;
            Object obj2;
            boolean z3;
            if (bVar.c() == r.b.EnumC0018b.VISIBLE) {
                if (z) {
                    obj2 = bVar.e().getReenterTransition();
                } else {
                    obj2 = bVar.e().getEnterTransition();
                }
                this.a = obj2;
                if (z) {
                    z3 = bVar.e().getAllowReturnTransitionOverlap();
                } else {
                    z3 = bVar.e().getAllowEnterTransitionOverlap();
                }
                this.b = z3;
            } else {
                if (z) {
                    obj = bVar.e().getReturnTransition();
                } else {
                    obj = bVar.e().getExitTransition();
                }
                this.a = obj;
                this.b = true;
            }
            if (!z2) {
                this.c = null;
            } else if (z) {
                this.c = bVar.e().getSharedElementReturnTransition();
            } else {
                this.c = bVar.e().getSharedElementEnterTransition();
            }
        }

        @Nullable
        Object e() {
            return this.a;
        }

        boolean f() {
            return this.b;
        }

        public boolean g() {
            return this.c != null;
        }

        @Nullable
        public Object h() {
            return this.c;
        }

        @Nullable
        FragmentTransitionImpl i() {
            FragmentTransitionImpl a = a(this.a);
            FragmentTransitionImpl a2 = a(this.c);
            if (a == null || a2 == null || a == a2) {
                return a != null ? a : a2;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + a().e() + " returned Transition " + this.a + " which uses a different Transition  type than its shared element transition " + this.c);
        }

        @Nullable
        private FragmentTransitionImpl a(Object obj) {
            if (obj == null) {
                return null;
            }
            if (n.a != null && n.a.canHandle(obj)) {
                return n.a;
            }
            if (n.b != null && n.b.canHandle(obj)) {
                return n.b;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + a().e() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
