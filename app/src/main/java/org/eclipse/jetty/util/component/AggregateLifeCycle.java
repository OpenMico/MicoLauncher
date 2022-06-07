package org.eclipse.jetty.util.component;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class AggregateLifeCycle extends AbstractLifeCycle implements Destroyable, Dumpable {
    private static final Logger LOG = Log.getLogger(AggregateLifeCycle.class);
    private final List<Bean> _beans = new CopyOnWriteArrayList();
    private boolean _started = false;

    /* loaded from: classes5.dex */
    public class Bean {
        final Object _bean;
        volatile boolean _managed = true;

        Bean(Object obj) {
            AggregateLifeCycle.this = r1;
            this._bean = obj;
        }

        public String toString() {
            return "{" + this._bean + Constants.ACCEPT_TIME_SEPARATOR_SP + this._managed + "}";
        }
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        for (Bean bean : this._beans) {
            if (bean._managed && (bean._bean instanceof LifeCycle)) {
                LifeCycle lifeCycle = (LifeCycle) bean._bean;
                if (!lifeCycle.isRunning()) {
                    lifeCycle.start();
                }
            }
        }
        this._started = true;
        super.doStart();
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        this._started = false;
        super.doStop();
        ArrayList<Bean> arrayList = new ArrayList(this._beans);
        Collections.reverse(arrayList);
        for (Bean bean : arrayList) {
            if (bean._managed && (bean._bean instanceof LifeCycle)) {
                LifeCycle lifeCycle = (LifeCycle) bean._bean;
                if (lifeCycle.isRunning()) {
                    lifeCycle.stop();
                }
            }
        }
    }

    public void destroy() {
        ArrayList<Bean> arrayList = new ArrayList(this._beans);
        Collections.reverse(arrayList);
        for (Bean bean : arrayList) {
            if ((bean._bean instanceof Destroyable) && bean._managed) {
                ((Destroyable) bean._bean).destroy();
            }
        }
        this._beans.clear();
    }

    public boolean contains(Object obj) {
        for (Bean bean : this._beans) {
            if (bean._bean == obj) {
                return true;
            }
        }
        return false;
    }

    public boolean isManaged(Object obj) {
        for (Bean bean : this._beans) {
            if (bean._bean == obj) {
                return bean._managed;
            }
        }
        return false;
    }

    public boolean addBean(Object obj) {
        return addBean(obj, !(obj instanceof LifeCycle) || !((LifeCycle) obj).isStarted());
    }

    public boolean addBean(Object obj, boolean z) {
        if (contains(obj)) {
            return false;
        }
        Bean bean = new Bean(obj);
        bean._managed = z;
        this._beans.add(bean);
        if (!(obj instanceof LifeCycle)) {
            return true;
        }
        LifeCycle lifeCycle = (LifeCycle) obj;
        if (!z || !this._started) {
            return true;
        }
        try {
            lifeCycle.start();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void manage(Object obj) {
        for (Bean bean : this._beans) {
            if (bean._bean == obj) {
                bean._managed = true;
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public void unmanage(Object obj) {
        for (Bean bean : this._beans) {
            if (bean._bean == obj) {
                bean._managed = false;
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public Collection<Object> getBeans() {
        return getBeans(Object.class);
    }

    public <T> List<T> getBeans(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        for (Bean bean : this._beans) {
            if (cls.isInstance(bean._bean)) {
                arrayList.add(bean._bean);
            }
        }
        return arrayList;
    }

    public <T> T getBean(Class<T> cls) {
        for (Bean bean : this._beans) {
            if (cls.isInstance(bean._bean)) {
                return (T) bean._bean;
            }
        }
        return null;
    }

    public void removeBeans() {
        this._beans.clear();
    }

    public boolean removeBean(Object obj) {
        for (Bean bean : this._beans) {
            if (bean._bean == obj) {
                this._beans.remove(bean);
                return true;
            }
        }
        return false;
    }

    public void dumpStdErr() {
        try {
            dump(System.err, "");
        } catch (IOException e) {
            LOG.warn(e);
        }
    }

    public String dump() {
        return dump(this);
    }

    public static String dump(Dumpable dumpable) {
        StringBuilder sb = new StringBuilder();
        try {
            dumpable.dump(sb, "");
        } catch (IOException e) {
            LOG.warn(e);
        }
        return sb.toString();
    }

    public void dump(Appendable appendable) throws IOException {
        dump(appendable, "");
    }

    protected void dumpThis(Appendable appendable) throws IOException {
        appendable.append(String.valueOf(this)).append(" - ").append(getState()).append("\n");
    }

    public static void dumpObject(Appendable appendable, Object obj) throws IOException {
        try {
            if (obj instanceof LifeCycle) {
                appendable.append(String.valueOf(obj)).append(" - ").append(AbstractLifeCycle.getState((LifeCycle) obj)).append("\n");
            } else {
                appendable.append(String.valueOf(obj)).append("\n");
            }
        } catch (Throwable th) {
            appendable.append(" => ").append(th.toString()).append('\n');
        }
    }

    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        int size = this._beans.size();
        if (size != 0) {
            int i = 0;
            for (Bean bean : this._beans) {
                i++;
                appendable.append(str).append(" +- ");
                if (!bean._managed) {
                    dumpObject(appendable, bean._bean);
                } else if (bean._bean instanceof Dumpable) {
                    Dumpable dumpable = (Dumpable) bean._bean;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(i == size ? "    " : " |  ");
                    dumpable.dump(appendable, sb.toString());
                } else {
                    dumpObject(appendable, bean._bean);
                }
            }
            if (i != size) {
                appendable.append(str).append(" |\n");
            }
        }
    }

    public static void dump(Appendable appendable, String str, Collection<?>... collectionArr) throws IOException {
        if (collectionArr.length != 0) {
            int i = 0;
            for (Collection<?> collection : collectionArr) {
                i += collection.size();
            }
            if (i != 0) {
                int i2 = 0;
                for (Collection<?> collection2 : collectionArr) {
                    for (Object obj : collection2) {
                        i2++;
                        appendable.append(str).append(" +- ");
                        if (obj instanceof Dumpable) {
                            Dumpable dumpable = (Dumpable) obj;
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append(i2 == i ? "    " : " |  ");
                            dumpable.dump(appendable, sb.toString());
                        } else {
                            dumpObject(appendable, obj);
                        }
                    }
                    if (i2 != i) {
                        appendable.append(str).append(" |\n");
                    }
                }
            }
        }
    }
}
