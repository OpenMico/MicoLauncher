package org.eclipse.jetty.util.component;

import java.lang.ref.WeakReference;
import java.util.EventListener;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class Container {
    private static final Logger LOG = Log.getLogger(Container.class);
    private final CopyOnWriteArrayList<Listener> _listeners = new CopyOnWriteArrayList<>();

    /* loaded from: classes5.dex */
    public interface Listener extends EventListener {
        void add(Relationship relationship);

        void addBean(Object obj);

        void remove(Relationship relationship);

        void removeBean(Object obj);
    }

    public void addEventListener(Listener listener) {
        this._listeners.add(listener);
    }

    public void removeEventListener(Listener listener) {
        this._listeners.remove(listener);
    }

    public void update(Object obj, Object obj2, Object obj3, String str) {
        if (obj2 != null && !obj2.equals(obj3)) {
            remove(obj, obj2, str);
        }
        if (obj3 != null && !obj3.equals(obj2)) {
            add(obj, obj3, str);
        }
    }

    public void update(Object obj, Object obj2, Object obj3, String str, boolean z) {
        if (obj2 != null && !obj2.equals(obj3)) {
            remove(obj, obj2, str);
            if (z) {
                removeBean(obj2);
            }
        }
        if (obj3 != null && !obj3.equals(obj2)) {
            if (z) {
                addBean(obj3);
            }
            add(obj, obj3, str);
        }
    }

    public void update(Object obj, Object[] objArr, Object[] objArr2, String str) {
        update(obj, objArr, objArr2, str, false);
    }

    public void update(Object obj, Object[] objArr, Object[] objArr2, String str, boolean z) {
        Object[] objArr3 = null;
        if (objArr2 != null) {
            Object[] objArr4 = new Object[objArr2.length];
            int length = objArr2.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                boolean z2 = true;
                if (objArr != null) {
                    int length2 = objArr.length;
                    while (true) {
                        int i2 = length2 - 1;
                        if (length2 <= 0) {
                            break;
                        } else if (objArr2[i] == null || !objArr2[i].equals(objArr[i2])) {
                            length2 = i2;
                        } else {
                            objArr[i2] = null;
                            z2 = false;
                            length2 = i2;
                        }
                    }
                }
                if (z2) {
                    objArr4[i] = objArr2[i];
                }
                length = i;
            }
            objArr3 = objArr4;
        }
        if (objArr != null) {
            int length3 = objArr.length;
            while (true) {
                int i3 = length3 - 1;
                if (length3 <= 0) {
                    break;
                }
                if (objArr[i3] != null) {
                    remove(obj, objArr[i3], str);
                    if (z) {
                        removeBean(objArr[i3]);
                    }
                }
                length3 = i3;
            }
        }
        if (objArr3 != null) {
            for (int i4 = 0; i4 < objArr3.length; i4++) {
                if (objArr3[i4] != null) {
                    if (z) {
                        addBean(objArr3[i4]);
                    }
                    add(obj, objArr3[i4], str);
                }
            }
        }
    }

    public void addBean(Object obj) {
        if (this._listeners != null) {
            for (int i = 0; i < LazyList.size(this._listeners); i++) {
                ((Listener) LazyList.get(this._listeners, i)).addBean(obj);
            }
        }
    }

    public void removeBean(Object obj) {
        if (this._listeners != null) {
            for (int i = 0; i < LazyList.size(this._listeners); i++) {
                ((Listener) LazyList.get(this._listeners, i)).removeBean(obj);
            }
        }
    }

    private void add(Object obj, Object obj2, String str) {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("Container " + obj + " + " + obj2 + " as " + str, new Object[0]);
        }
        if (this._listeners != null) {
            Relationship relationship = new Relationship(obj, obj2, str);
            for (int i = 0; i < LazyList.size(this._listeners); i++) {
                ((Listener) LazyList.get(this._listeners, i)).add(relationship);
            }
        }
    }

    private void remove(Object obj, Object obj2, String str) {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("Container " + obj + " - " + obj2 + " as " + str, new Object[0]);
        }
        if (this._listeners != null) {
            Relationship relationship = new Relationship(obj, obj2, str);
            for (int i = 0; i < LazyList.size(this._listeners); i++) {
                ((Listener) LazyList.get(this._listeners, i)).remove(relationship);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class Relationship {
        private final WeakReference<Object> _child;
        private Container _container;
        private final WeakReference<Object> _parent;
        private String _relationship;

        private Relationship(Container container, Object obj, Object obj2, String str) {
            this._container = container;
            this._parent = new WeakReference<>(obj);
            this._child = new WeakReference<>(obj2);
            this._relationship = str;
        }

        public Container getContainer() {
            return this._container;
        }

        public Object getChild() {
            return this._child.get();
        }

        public Object getParent() {
            return this._parent.get();
        }

        public String getRelationship() {
            return this._relationship;
        }

        public String toString() {
            return this._parent + "---" + this._relationship + "-->" + this._child;
        }

        public int hashCode() {
            return this._parent.hashCode() + this._child.hashCode() + this._relationship.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Relationship)) {
                return false;
            }
            Relationship relationship = (Relationship) obj;
            return relationship._parent.get() == this._parent.get() && relationship._child.get() == this._child.get() && relationship._relationship.equals(this._relationship);
        }
    }
}
