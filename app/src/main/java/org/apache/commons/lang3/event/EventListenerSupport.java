package org.apache.commons.lang3.event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public class EventListenerSupport<L> implements Serializable {
    private static final long serialVersionUID = 3593265990380473632L;
    private transient L a;
    private transient L[] b;
    private List<L> listeners;

    public static <T> EventListenerSupport<T> create(Class<T> cls) {
        return new EventListenerSupport<>(cls);
    }

    public EventListenerSupport(Class<L> cls) {
        this(cls, Thread.currentThread().getContextClassLoader());
    }

    public EventListenerSupport(Class<L> cls, ClassLoader classLoader) {
        this();
        Validate.notNull(cls, "Listener interface cannot be null.", new Object[0]);
        Validate.notNull(classLoader, "ClassLoader cannot be null.", new Object[0]);
        Validate.isTrue(cls.isInterface(), "Class {0} is not an interface", cls.getName());
        a(cls, classLoader);
    }

    private EventListenerSupport() {
        this.listeners = new CopyOnWriteArrayList();
    }

    public L fire() {
        return this.a;
    }

    public void addListener(L l) {
        addListener(l, true);
    }

    public void addListener(L l, boolean z) {
        Validate.notNull(l, "Listener object cannot be null.", new Object[0]);
        if (z) {
            this.listeners.add(l);
        } else if (!this.listeners.contains(l)) {
            this.listeners.add(l);
        }
    }

    public void removeListener(L l) {
        Validate.notNull(l, "Listener object cannot be null.", new Object[0]);
        this.listeners.remove(l);
    }

    public L[] getListeners() {
        return (L[]) this.listeners.toArray(this.b);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ArrayList arrayList = new ArrayList();
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
        for (L l : this.listeners) {
            try {
                objectOutputStream2.writeObject(l);
                arrayList.add(l);
            } catch (IOException unused) {
                objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
            }
        }
        objectOutputStream.writeObject(arrayList.toArray(this.b));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Object[] objArr = (Object[]) objectInputStream.readObject();
        this.listeners = new CopyOnWriteArrayList(objArr);
        a(objArr.getClass().getComponentType(), Thread.currentThread().getContextClassLoader());
    }

    private void a(Class<L> cls, ClassLoader classLoader) {
        this.b = (L[]) ((Object[]) Array.newInstance((Class<?>) cls, 0));
        b(cls, classLoader);
    }

    private void b(Class<L> cls, ClassLoader classLoader) {
        this.a = cls.cast(Proxy.newProxyInstance(classLoader, new Class[]{cls}, createInvocationHandler()));
    }

    protected InvocationHandler createInvocationHandler() {
        return new ProxyInvocationHandler();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes5.dex */
    public class ProxyInvocationHandler implements InvocationHandler {
        protected ProxyInvocationHandler() {
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            for (Object obj2 : EventListenerSupport.this.listeners) {
                method.invoke(obj2, objArr);
            }
            return null;
        }
    }
}
