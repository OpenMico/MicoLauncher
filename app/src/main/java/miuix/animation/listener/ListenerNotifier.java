package miuix.animation.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.ObjectPool;

/* loaded from: classes5.dex */
public class ListenerNotifier {
    static final a a = new a();
    static final f b = new f();
    static final e c = new e();
    static final h d = new h();
    static final g e = new g();
    static final b f = new b();
    static final c g = new c();
    final Map<Object, List<TransitionListener>> h = new ConcurrentHashMap();
    final IAnimTarget i;

    /* loaded from: classes5.dex */
    public interface d {
        void a(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo);
    }

    /* loaded from: classes5.dex */
    public static class a implements d {
        a() {
        }

        @Override // miuix.animation.listener.ListenerNotifier.d
        public void a(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onBegin(obj);
        }
    }

    /* loaded from: classes5.dex */
    public static class f implements d {
        f() {
        }

        @Override // miuix.animation.listener.ListenerNotifier.d
        public void a(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onBegin(obj, collection);
        }
    }

    /* loaded from: classes5.dex */
    public static class e implements d {
        static final List<UpdateInfo> a = new ArrayList();

        e() {
        }

        @Override // miuix.animation.listener.ListenerNotifier.d
        public void a(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onUpdate(obj, a);
        }
    }

    /* loaded from: classes5.dex */
    public static class h implements d {
        h() {
        }

        @Override // miuix.animation.listener.ListenerNotifier.d
        public void a(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            if (collection != null && collection.size() <= 4000) {
                for (UpdateInfo updateInfo2 : collection) {
                    a(obj, transitionListener, updateInfo2);
                }
            }
            transitionListener.onUpdate(obj, collection);
        }

        private void a(Object obj, TransitionListener transitionListener, UpdateInfo updateInfo) {
            transitionListener.onUpdate(obj, updateInfo.property, updateInfo.getFloatValue(), updateInfo.isCompleted);
            if (updateInfo.useInt) {
                transitionListener.onUpdate(obj, (IIntValueProperty) updateInfo.property, updateInfo.getIntValue(), (float) updateInfo.velocity, updateInfo.isCompleted);
            } else {
                transitionListener.onUpdate(obj, updateInfo.property, updateInfo.getFloatValue(), (float) updateInfo.velocity, updateInfo.isCompleted);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class g implements d {
        g() {
        }

        @Override // miuix.animation.listener.ListenerNotifier.d
        public void a(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            for (UpdateInfo updateInfo2 : collection) {
                if (updateInfo2.isCompleted && updateInfo2.animInfo.justEnd) {
                    updateInfo2.animInfo.justEnd = false;
                    if (updateInfo2.animInfo.op == 3) {
                        transitionListener.onComplete(obj, updateInfo2);
                    } else {
                        transitionListener.onCancel(obj, updateInfo2);
                    }
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class b implements d {
        b() {
        }

        @Override // miuix.animation.listener.ListenerNotifier.d
        public void a(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onCancel(obj);
        }
    }

    /* loaded from: classes5.dex */
    public static class c implements d {
        c() {
        }

        @Override // miuix.animation.listener.ListenerNotifier.d
        public void a(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onComplete(obj);
        }
    }

    public ListenerNotifier(IAnimTarget iAnimTarget) {
        this.i = iAnimTarget;
    }

    public boolean addListeners(Object obj, AnimConfig animConfig) {
        if (animConfig.listeners.isEmpty()) {
            return false;
        }
        CommonUtils.addTo(animConfig.listeners, a(obj));
        return true;
    }

    public void removeListeners(Object obj) {
        ObjectPool.release(this.h.remove(obj));
    }

    private List<TransitionListener> a(Object obj) {
        List<TransitionListener> list = this.h.get(obj);
        if (list != null) {
            return list;
        }
        List<TransitionListener> list2 = (List) ObjectPool.acquire(ArrayList.class, new Object[0]);
        this.h.put(obj, list2);
        return list2;
    }

    public void notifyBegin(Object obj, Object obj2) {
        a(obj, obj2, a, (Collection<UpdateInfo>) null, (UpdateInfo) null);
    }

    public void notifyPropertyBegin(Object obj, Object obj2, Collection<UpdateInfo> collection) {
        a(obj, obj2, b, collection, (UpdateInfo) null);
    }

    public void notifyMassUpdate(Object obj, Object obj2) {
        a(obj, obj2, c, (Collection<UpdateInfo>) null, (UpdateInfo) null);
    }

    public void notifyUpdate(Object obj, Object obj2, Collection<UpdateInfo> collection) {
        a(obj, obj2, d, collection, (UpdateInfo) null);
    }

    public void notifyPropertyEnd(Object obj, Object obj2, Collection<UpdateInfo> collection) {
        a(obj, obj2, e, collection, (UpdateInfo) null);
    }

    public void notifyCancelAll(Object obj, Object obj2) {
        a(obj, obj2, f, (Collection<UpdateInfo>) null, (UpdateInfo) null);
    }

    public void notifyEndAll(Object obj, Object obj2) {
        a(obj, obj2, g, (Collection<UpdateInfo>) null, (UpdateInfo) null);
    }

    private void a(Object obj, Object obj2, d dVar, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
        List<TransitionListener> list = this.h.get(obj);
        if (list != null && !list.isEmpty()) {
            a(obj2, list, dVar, collection, updateInfo);
        }
    }

    private static void a(Object obj, List<TransitionListener> list, d dVar, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
        Set set = (Set) ObjectPool.acquire(HashSet.class, new Object[0]);
        for (TransitionListener transitionListener : list) {
            if (set.add(transitionListener)) {
                dVar.a(obj, transitionListener, collection, updateInfo);
            }
        }
        ObjectPool.release(set);
    }
}
