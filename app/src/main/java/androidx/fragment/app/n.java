package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentTransition.java */
/* loaded from: classes.dex */
public class n {
    static final FragmentTransitionImpl a;
    static final FragmentTransitionImpl b;
    private static final int[] c = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FragmentTransition.java */
    /* loaded from: classes.dex */
    public interface a {
        void a(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal);

        void b(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal);
    }

    static {
        a = Build.VERSION.SDK_INT >= 21 ? new o() : null;
        b = b();
    }

    private static FragmentTransitionImpl b() {
        try {
            return (FragmentTransitionImpl) Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull Context context, @NonNull FragmentContainer fragmentContainer, ArrayList<a> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z, a aVar) {
        ViewGroup viewGroup;
        SparseArray sparseArray = new SparseArray();
        for (int i3 = i; i3 < i2; i3++) {
            a aVar2 = arrayList.get(i3);
            if (arrayList2.get(i3).booleanValue()) {
                b(aVar2, sparseArray, z);
            } else {
                a(aVar2, sparseArray, z);
            }
        }
        if (sparseArray.size() != 0) {
            View view = new View(context);
            int size = sparseArray.size();
            for (int i4 = 0; i4 < size; i4++) {
                int keyAt = sparseArray.keyAt(i4);
                ArrayMap<String, String> a2 = a(keyAt, arrayList, arrayList2, i, i2);
                b bVar = (b) sparseArray.valueAt(i4);
                if (fragmentContainer.onHasView() && (viewGroup = (ViewGroup) fragmentContainer.onFindViewById(keyAt)) != null) {
                    if (z) {
                        a(viewGroup, bVar, view, a2, aVar);
                    } else {
                        b(viewGroup, bVar, view, a2, aVar);
                    }
                }
            }
        }
    }

    private static ArrayMap<String, String> a(int i, ArrayList<a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            a aVar = arrayList.get(i4);
            if (aVar.b(i)) {
                boolean booleanValue = arrayList2.get(i4).booleanValue();
                if (aVar.q != null) {
                    int size = aVar.q.size();
                    if (booleanValue) {
                        arrayList3 = aVar.q;
                        arrayList4 = aVar.r;
                    } else {
                        ArrayList arrayList5 = aVar.q;
                        arrayList3 = aVar.r;
                        arrayList4 = arrayList5;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = (String) arrayList4.get(i5);
                        String str2 = (String) arrayList3.get(i5);
                        String remove = arrayMap.remove(str2);
                        if (remove != null) {
                            arrayMap.put(str, remove);
                        } else {
                            arrayMap.put(str, str2);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    private static void a(@NonNull ViewGroup viewGroup, b bVar, View view, ArrayMap<String, String> arrayMap, final a aVar) {
        Object obj;
        Fragment fragment = bVar.a;
        final Fragment fragment2 = bVar.d;
        FragmentTransitionImpl a2 = a(fragment2, fragment);
        if (a2 != null) {
            boolean z = bVar.b;
            boolean z2 = bVar.e;
            ArrayList<View> arrayList = new ArrayList<>();
            ArrayList<View> arrayList2 = new ArrayList<>();
            Object a3 = a(a2, fragment, z);
            Object b2 = b(a2, fragment2, z2);
            Object a4 = a(a2, viewGroup, view, arrayMap, bVar, arrayList2, arrayList, a3, b2);
            if (a3 == null && a4 == null) {
                obj = b2;
                if (obj == null) {
                    return;
                }
            } else {
                obj = b2;
            }
            ArrayList<View> a5 = a(a2, obj, fragment2, arrayList2, view);
            ArrayList<View> a6 = a(a2, a3, fragment, arrayList, view);
            a(a6, 4);
            Object a7 = a(a2, a3, obj, a4, fragment, z);
            if (!(fragment2 == null || a5 == null || (a5.size() <= 0 && arrayList2.size() <= 0))) {
                final CancellationSignal cancellationSignal = new CancellationSignal();
                aVar.a(fragment2, cancellationSignal);
                a2.setListenerForTransitionEnd(fragment2, a7, cancellationSignal, new Runnable() { // from class: androidx.fragment.app.n.1
                    @Override // java.lang.Runnable
                    public void run() {
                        a.this.b(fragment2, cancellationSignal);
                    }
                });
            }
            if (a7 != null) {
                a(a2, obj, fragment2, a5);
                ArrayList<String> a8 = a2.a(arrayList);
                a2.scheduleRemoveTargets(a7, a3, a6, obj, a5, a4, arrayList);
                a2.beginDelayedTransition(viewGroup, a7);
                a2.a(viewGroup, arrayList2, arrayList, a8, arrayMap);
                a(a6, 0);
                a2.swapSharedElementTargets(a4, arrayList2, arrayList);
            }
        }
    }

    private static void a(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment != null && obj != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            fragmentTransitionImpl.scheduleHideFragmentView(obj, fragment.getView(), arrayList);
            OneShotPreDrawListener.add(fragment.mContainer, new Runnable() { // from class: androidx.fragment.app.n.2
                @Override // java.lang.Runnable
                public void run() {
                    n.a(arrayList, 4);
                }
            });
        }
    }

    private static void b(@NonNull ViewGroup viewGroup, b bVar, View view, ArrayMap<String, String> arrayMap, final a aVar) {
        Object obj;
        Fragment fragment = bVar.a;
        final Fragment fragment2 = bVar.d;
        FragmentTransitionImpl a2 = a(fragment2, fragment);
        if (a2 != null) {
            boolean z = bVar.b;
            boolean z2 = bVar.e;
            Object a3 = a(a2, fragment, z);
            Object b2 = b(a2, fragment2, z2);
            ArrayList arrayList = new ArrayList();
            ArrayList<View> arrayList2 = new ArrayList<>();
            Object b3 = b(a2, viewGroup, view, arrayMap, bVar, arrayList, arrayList2, a3, b2);
            if (a3 == null && b3 == null) {
                obj = b2;
                if (obj == null) {
                    return;
                }
            } else {
                obj = b2;
            }
            ArrayList<View> a4 = a(a2, obj, fragment2, arrayList, view);
            Object obj2 = (a4 == null || a4.isEmpty()) ? null : obj;
            a2.addTarget(a3, view);
            Object a5 = a(a2, a3, obj2, b3, fragment, bVar.b);
            if (!(fragment2 == null || a4 == null || (a4.size() <= 0 && arrayList.size() <= 0))) {
                final CancellationSignal cancellationSignal = new CancellationSignal();
                aVar.a(fragment2, cancellationSignal);
                a2.setListenerForTransitionEnd(fragment2, a5, cancellationSignal, new Runnable() { // from class: androidx.fragment.app.n.3
                    @Override // java.lang.Runnable
                    public void run() {
                        a.this.b(fragment2, cancellationSignal);
                    }
                });
            }
            if (a5 != null) {
                ArrayList<View> arrayList3 = new ArrayList<>();
                a2.scheduleRemoveTargets(a5, a3, arrayList3, obj2, a4, b3, arrayList2);
                a(a2, viewGroup, fragment, view, arrayList2, a3, arrayList3, obj2, a4);
                a2.a((View) viewGroup, arrayList2, (Map<String, String>) arrayMap);
                a2.beginDelayedTransition(viewGroup, a5);
                a2.a(viewGroup, arrayList2, (Map<String, String>) arrayMap);
            }
        }
    }

    private static void a(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final Fragment fragment, final View view, final ArrayList<View> arrayList, final Object obj, final ArrayList<View> arrayList2, final Object obj2, final ArrayList<View> arrayList3) {
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.n.4
            @Override // java.lang.Runnable
            public void run() {
                Object obj3 = obj;
                if (obj3 != null) {
                    fragmentTransitionImpl.removeTarget(obj3, view);
                    arrayList2.addAll(n.a(fragmentTransitionImpl, obj, fragment, arrayList, view));
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList<View> arrayList4 = new ArrayList<>();
                        arrayList4.add(view);
                        fragmentTransitionImpl.replaceTargets(obj2, arrayList3, arrayList4);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    private static FragmentTransitionImpl a(Fragment fragment, Fragment fragment2) {
        ArrayList arrayList = new ArrayList();
        if (fragment != null) {
            Object exitTransition = fragment.getExitTransition();
            if (exitTransition != null) {
                arrayList.add(exitTransition);
            }
            Object returnTransition = fragment.getReturnTransition();
            if (returnTransition != null) {
                arrayList.add(returnTransition);
            }
            Object sharedElementReturnTransition = fragment.getSharedElementReturnTransition();
            if (sharedElementReturnTransition != null) {
                arrayList.add(sharedElementReturnTransition);
            }
        }
        if (fragment2 != null) {
            Object enterTransition = fragment2.getEnterTransition();
            if (enterTransition != null) {
                arrayList.add(enterTransition);
            }
            Object reenterTransition = fragment2.getReenterTransition();
            if (reenterTransition != null) {
                arrayList.add(reenterTransition);
            }
            Object sharedElementEnterTransition = fragment2.getSharedElementEnterTransition();
            if (sharedElementEnterTransition != null) {
                arrayList.add(sharedElementEnterTransition);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        FragmentTransitionImpl fragmentTransitionImpl = a;
        if (fragmentTransitionImpl != null && a(fragmentTransitionImpl, arrayList)) {
            return a;
        }
        FragmentTransitionImpl fragmentTransitionImpl2 = b;
        if (fragmentTransitionImpl2 != null && a(fragmentTransitionImpl2, arrayList)) {
            return b;
        }
        if (a == null && b == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static boolean a(FragmentTransitionImpl fragmentTransitionImpl, List<Object> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!fragmentTransitionImpl.canHandle(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static Object a(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, Fragment fragment2, boolean z) {
        Object obj;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        if (z) {
            obj = fragment2.getSharedElementReturnTransition();
        } else {
            obj = fragment.getSharedElementEnterTransition();
        }
        return fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(obj));
    }

    private static Object a(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, boolean z) {
        Object obj;
        if (fragment == null) {
            return null;
        }
        if (z) {
            obj = fragment.getReenterTransition();
        } else {
            obj = fragment.getEnterTransition();
        }
        return fragmentTransitionImpl.cloneTransition(obj);
    }

    private static Object b(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, boolean z) {
        Object obj;
        if (fragment == null) {
            return null;
        }
        if (z) {
            obj = fragment.getReturnTransition();
        } else {
            obj = fragment.getExitTransition();
        }
        return fragmentTransitionImpl.cloneTransition(obj);
    }

    private static Object a(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, b bVar, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        final Rect rect;
        final View view2;
        final Fragment fragment = bVar.a;
        final Fragment fragment2 = bVar.d;
        if (fragment != null) {
            fragment.requireView().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = bVar.b;
        Object a2 = arrayMap.isEmpty() ? null : a(fragmentTransitionImpl, fragment, fragment2, z);
        ArrayMap<String, View> b2 = b(fragmentTransitionImpl, arrayMap, a2, bVar);
        final ArrayMap<String, View> a3 = a(fragmentTransitionImpl, arrayMap, a2, bVar);
        if (arrayMap.isEmpty()) {
            if (b2 != null) {
                b2.clear();
            }
            if (a3 != null) {
                a3.clear();
            }
            obj3 = null;
        } else {
            a(arrayList, b2, arrayMap.keySet());
            a(arrayList2, a3, arrayMap.values());
            obj3 = a2;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        a(fragment, fragment2, z, b2, true);
        if (obj3 != null) {
            arrayList2.add(view);
            fragmentTransitionImpl.setSharedElementTargets(obj3, view, arrayList);
            a(fragmentTransitionImpl, obj3, obj2, b2, bVar.e, bVar.f);
            Rect rect2 = new Rect();
            View a4 = a(a3, bVar, obj, z);
            if (a4 != null) {
                fragmentTransitionImpl.setEpicenter(obj, rect2);
            }
            rect = rect2;
            view2 = a4;
        } else {
            view2 = null;
            rect = null;
        }
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.n.5
            @Override // java.lang.Runnable
            public void run() {
                n.a(Fragment.this, fragment2, z, (ArrayMap<String, View>) a3, false);
                View view3 = view2;
                if (view3 != null) {
                    fragmentTransitionImpl.getBoundsOnScreen(view3, rect);
                }
            }
        });
        return obj3;
    }

    private static void a(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View valueAt = arrayMap.valueAt(size);
            if (collection.contains(ViewCompat.getTransitionName(valueAt))) {
                arrayList.add(valueAt);
            }
        }
    }

    private static Object b(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final View view, final ArrayMap<String, String> arrayMap, final b bVar, final ArrayList<View> arrayList, final ArrayList<View> arrayList2, final Object obj, Object obj2) {
        ArrayMap<String, String> arrayMap2;
        Object obj3;
        final Object obj4;
        final Rect rect;
        final Fragment fragment = bVar.a;
        final Fragment fragment2 = bVar.d;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = bVar.b;
        if (arrayMap.isEmpty()) {
            arrayMap2 = arrayMap;
            obj3 = null;
        } else {
            obj3 = a(fragmentTransitionImpl, fragment, fragment2, z);
            arrayMap2 = arrayMap;
        }
        ArrayMap<String, View> b2 = b(fragmentTransitionImpl, arrayMap2, obj3, bVar);
        if (arrayMap.isEmpty()) {
            obj4 = null;
        } else {
            arrayList.addAll(b2.values());
            obj4 = obj3;
        }
        if (obj == null && obj2 == null && obj4 == null) {
            return null;
        }
        a(fragment, fragment2, z, b2, true);
        if (obj4 != null) {
            rect = new Rect();
            fragmentTransitionImpl.setSharedElementTargets(obj4, view, arrayList);
            a(fragmentTransitionImpl, obj4, obj2, b2, bVar.e, bVar.f);
            if (obj != null) {
                fragmentTransitionImpl.setEpicenter(obj, rect);
            }
        } else {
            rect = null;
        }
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.n.6
            @Override // java.lang.Runnable
            public void run() {
                ArrayMap<String, View> a2 = n.a(FragmentTransitionImpl.this, arrayMap, obj4, bVar);
                if (a2 != null) {
                    arrayList2.addAll(a2.values());
                    arrayList2.add(view);
                }
                n.a(fragment, fragment2, z, a2, false);
                Object obj5 = obj4;
                if (obj5 != null) {
                    FragmentTransitionImpl.this.swapSharedElementTargets(obj5, arrayList, arrayList2);
                    View a3 = n.a(a2, bVar, obj, z);
                    if (a3 != null) {
                        FragmentTransitionImpl.this.getBoundsOnScreen(a3, rect);
                    }
                }
            }
        });
        return obj4;
    }

    private static ArrayMap<String, View> b(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap<String, String> arrayMap, Object obj, b bVar) {
        SharedElementCallback sharedElementCallback;
        ArrayList arrayList;
        if (arrayMap.isEmpty() || obj == null) {
            arrayMap.clear();
            return null;
        }
        Fragment fragment = bVar.d;
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragmentTransitionImpl.a(arrayMap2, fragment.requireView());
        a aVar = bVar.f;
        if (bVar.e) {
            sharedElementCallback = fragment.getEnterTransitionCallback();
            arrayList = aVar.r;
        } else {
            sharedElementCallback = fragment.getExitTransitionCallback();
            arrayList = aVar.q;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = (String) arrayList.get(size);
                View view = arrayMap2.get(str);
                if (view == null) {
                    arrayMap.remove(str);
                } else if (!str.equals(ViewCompat.getTransitionName(view))) {
                    arrayMap.put(ViewCompat.getTransitionName(view), arrayMap.remove(str));
                }
            }
        } else {
            arrayMap.retainAll(arrayMap2.keySet());
        }
        return arrayMap2;
    }

    static ArrayMap<String, View> a(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap<String, String> arrayMap, Object obj, b bVar) {
        SharedElementCallback sharedElementCallback;
        ArrayList arrayList;
        String a2;
        Fragment fragment = bVar.a;
        View view = fragment.getView();
        if (arrayMap.isEmpty() || obj == null || view == null) {
            arrayMap.clear();
            return null;
        }
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragmentTransitionImpl.a(arrayMap2, view);
        a aVar = bVar.c;
        if (bVar.b) {
            sharedElementCallback = fragment.getExitTransitionCallback();
            arrayList = aVar.q;
        } else {
            sharedElementCallback = fragment.getEnterTransitionCallback();
            arrayList = aVar.r;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
            arrayMap2.retainAll(arrayMap.values());
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = (String) arrayList.get(size);
                View view2 = arrayMap2.get(str);
                if (view2 == null) {
                    String a3 = a(arrayMap, str);
                    if (a3 != null) {
                        arrayMap.remove(a3);
                    }
                } else if (!str.equals(ViewCompat.getTransitionName(view2)) && (a2 = a(arrayMap, str)) != null) {
                    arrayMap.put(a2, ViewCompat.getTransitionName(view2));
                }
            }
        } else {
            a(arrayMap, arrayMap2);
        }
        return arrayMap2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(ArrayMap<String, String> arrayMap, String str) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(arrayMap.valueAt(i))) {
                return arrayMap.keyAt(i);
            }
        }
        return null;
    }

    static View a(ArrayMap<String, View> arrayMap, b bVar, Object obj, boolean z) {
        String str;
        a aVar = bVar.c;
        if (obj == null || arrayMap == null || aVar.q == null || aVar.q.isEmpty()) {
            return null;
        }
        if (z) {
            str = (String) aVar.q.get(0);
        } else {
            str = (String) aVar.r.get(0);
        }
        return arrayMap.get(str);
    }

    private static void a(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, ArrayMap<String, View> arrayMap, boolean z, a aVar) {
        String str;
        if (aVar.q != null && !aVar.q.isEmpty()) {
            if (z) {
                str = (String) aVar.r.get(0);
            } else {
                str = (String) aVar.q.get(0);
            }
            View view = arrayMap.get(str);
            fragmentTransitionImpl.setEpicenter(obj, view);
            if (obj2 != null) {
                fragmentTransitionImpl.setEpicenter(obj2, view);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull ArrayMap<String, String> arrayMap, @NonNull ArrayMap<String, View> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey(arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Fragment fragment, Fragment fragment2, boolean z, ArrayMap<String, View> arrayMap, boolean z2) {
        SharedElementCallback sharedElementCallback;
        if (z) {
            sharedElementCallback = fragment2.getEnterTransitionCallback();
        } else {
            sharedElementCallback = fragment.getEnterTransitionCallback();
        }
        if (sharedElementCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(arrayMap.keyAt(i));
                arrayList.add(arrayMap.valueAt(i));
            }
            if (z2) {
                sharedElementCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                sharedElementCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    static ArrayList<View> a(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList<View> arrayList2 = new ArrayList<>();
        View view2 = fragment.getView();
        if (view2 != null) {
            fragmentTransitionImpl.a(arrayList2, view2);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        arrayList2.add(view);
        fragmentTransitionImpl.addTargets(obj, arrayList2);
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(ArrayList<View> arrayList, int i) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).setVisibility(i);
            }
        }
    }

    private static Object a(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        boolean z2;
        if (obj == null || obj2 == null || fragment == null) {
            z2 = true;
        } else if (z) {
            z2 = fragment.getAllowReturnTransitionOverlap();
        } else {
            z2 = fragment.getAllowEnterTransitionOverlap();
        }
        if (z2) {
            return fragmentTransitionImpl.mergeTransitionsTogether(obj2, obj, obj3);
        }
        return fragmentTransitionImpl.mergeTransitionsInSequence(obj2, obj, obj3);
    }

    public static void a(a aVar, SparseArray<b> sparseArray, boolean z) {
        int size = aVar.d.size();
        for (int i = 0; i < size; i++) {
            a(aVar, (FragmentTransaction.a) aVar.d.get(i), sparseArray, false, z);
        }
    }

    public static void b(a aVar, SparseArray<b> sparseArray, boolean z) {
        if (aVar.a.j().onHasView()) {
            for (int size = aVar.d.size() - 1; size >= 0; size--) {
                a(aVar, (FragmentTransaction.a) aVar.d.get(size), sparseArray, true, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        return (a == null && b == null) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(androidx.fragment.app.a r8, androidx.fragment.app.FragmentTransaction.a r9, android.util.SparseArray<androidx.fragment.app.n.b> r10, boolean r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.n.a(androidx.fragment.app.a, androidx.fragment.app.FragmentTransaction$a, android.util.SparseArray, boolean, boolean):void");
    }

    private static b a(b bVar, SparseArray<b> sparseArray, int i) {
        if (bVar != null) {
            return bVar;
        }
        b bVar2 = new b();
        sparseArray.put(i, bVar2);
        return bVar2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FragmentTransition.java */
    /* loaded from: classes.dex */
    public static class b {
        public Fragment a;
        public boolean b;
        public a c;
        public Fragment d;
        public boolean e;
        public a f;

        b() {
        }
    }
}
