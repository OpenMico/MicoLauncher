package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.view.ViewCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class TransitionManager {
    private ArrayMap<Scene, Transition> c = new ArrayMap<>();
    private ArrayMap<Scene, ArrayMap<Scene, Transition>> d = new ArrayMap<>();
    private static Transition b = new AutoTransition();
    private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> e = new ThreadLocal<>();
    static ArrayList<ViewGroup> a = new ArrayList<>();

    public void setTransition(@NonNull Scene scene, @Nullable Transition transition) {
        this.c.put(scene, transition);
    }

    public void setTransition(@NonNull Scene scene, @NonNull Scene scene2, @Nullable Transition transition) {
        ArrayMap<Scene, Transition> arrayMap = this.d.get(scene2);
        if (arrayMap == null) {
            arrayMap = new ArrayMap<>();
            this.d.put(scene2, arrayMap);
        }
        arrayMap.put(scene, transition);
    }

    private Transition a(Scene scene) {
        Scene currentScene;
        ArrayMap<Scene, Transition> arrayMap;
        Transition transition;
        ViewGroup sceneRoot = scene.getSceneRoot();
        if (sceneRoot != null && (currentScene = Scene.getCurrentScene(sceneRoot)) != null && (arrayMap = this.d.get(scene)) != null && (transition = arrayMap.get(currentScene)) != null) {
            return transition;
        }
        Transition transition2 = this.c.get(scene);
        return transition2 != null ? transition2 : b;
    }

    private static void a(Scene scene, Transition transition) {
        ViewGroup sceneRoot = scene.getSceneRoot();
        if (!a.contains(sceneRoot)) {
            Scene currentScene = Scene.getCurrentScene(sceneRoot);
            if (transition == null) {
                if (currentScene != null) {
                    currentScene.exit();
                }
                scene.enter();
                return;
            }
            a.add(sceneRoot);
            Transition clone = transition.clone();
            clone.c(sceneRoot);
            if (currentScene != null && currentScene.a()) {
                clone.b(true);
            }
            b(sceneRoot, clone);
            scene.enter();
            a(sceneRoot, clone);
        }
    }

    static ArrayMap<ViewGroup, ArrayList<Transition>> a() {
        ArrayMap<ViewGroup, ArrayList<Transition>> arrayMap;
        WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>> weakReference = e.get();
        if (weakReference != null && (arrayMap = weakReference.get()) != null) {
            return arrayMap;
        }
        ArrayMap<ViewGroup, ArrayList<Transition>> arrayMap2 = new ArrayMap<>();
        e.set(new WeakReference<>(arrayMap2));
        return arrayMap2;
    }

    private static void a(ViewGroup viewGroup, Transition transition) {
        if (transition != null && viewGroup != null) {
            a aVar = new a(transition, viewGroup);
            viewGroup.addOnAttachStateChangeListener(aVar);
            viewGroup.getViewTreeObserver().addOnPreDrawListener(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a implements View.OnAttachStateChangeListener, ViewTreeObserver.OnPreDrawListener {
        Transition a;
        ViewGroup b;

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        a(Transition transition, ViewGroup viewGroup) {
            this.a = transition;
            this.b = viewGroup;
        }

        private void a() {
            this.b.getViewTreeObserver().removeOnPreDrawListener(this);
            this.b.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            a();
            TransitionManager.a.remove(this.b);
            ArrayList<Transition> arrayList = TransitionManager.a().get(this.b);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator<Transition> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().resume(this.b);
                }
            }
            this.a.a(true);
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            a();
            if (!TransitionManager.a.remove(this.b)) {
                return true;
            }
            final ArrayMap<ViewGroup, ArrayList<Transition>> a = TransitionManager.a();
            ArrayList<Transition> arrayList = a.get(this.b);
            ArrayList arrayList2 = null;
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                a.put(this.b, arrayList);
            } else if (arrayList.size() > 0) {
                arrayList2 = new ArrayList(arrayList);
            }
            arrayList.add(this.a);
            this.a.addListener(new TransitionListenerAdapter() { // from class: androidx.transition.TransitionManager.a.1
                @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                public void onTransitionEnd(@NonNull Transition transition) {
                    ((ArrayList) a.get(a.this.b)).remove(transition);
                    transition.removeListener(this);
                }
            });
            this.a.a(this.b, false);
            if (arrayList2 != null) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((Transition) it.next()).resume(this.b);
                }
            }
            this.a.a(this.b);
            return true;
        }
    }

    private static void b(ViewGroup viewGroup, Transition transition) {
        ArrayList<Transition> arrayList = a().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<Transition> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().pause(viewGroup);
            }
        }
        if (transition != null) {
            transition.a(viewGroup, true);
        }
        Scene currentScene = Scene.getCurrentScene(viewGroup);
        if (currentScene != null) {
            currentScene.exit();
        }
    }

    public void transitionTo(@NonNull Scene scene) {
        a(scene, a(scene));
    }

    public static void go(@NonNull Scene scene) {
        a(scene, b);
    }

    public static void go(@NonNull Scene scene, @Nullable Transition transition) {
        a(scene, transition);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup) {
        beginDelayedTransition(viewGroup, null);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup, @Nullable Transition transition) {
        if (!a.contains(viewGroup) && ViewCompat.isLaidOut(viewGroup)) {
            a.add(viewGroup);
            if (transition == null) {
                transition = b;
            }
            Transition clone = transition.clone();
            b(viewGroup, clone);
            Scene.a(viewGroup, null);
            a(viewGroup, clone);
        }
    }

    public static void endTransitions(ViewGroup viewGroup) {
        a.remove(viewGroup);
        ArrayList<Transition> arrayList = a().get(viewGroup);
        if (!(arrayList == null || arrayList.isEmpty())) {
            ArrayList arrayList2 = new ArrayList(arrayList);
            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                ((Transition) arrayList2.get(size)).b(viewGroup);
            }
        }
    }
}
