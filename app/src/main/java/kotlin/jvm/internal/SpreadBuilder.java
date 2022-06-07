package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SpreadBuilder {
    private final ArrayList<Object> a;

    public SpreadBuilder(int i) {
        this.a = new ArrayList<>(i);
    }

    public void addSpread(Object obj) {
        if (obj != null) {
            if (obj instanceof Object[]) {
                Object[] objArr = (Object[]) obj;
                if (objArr.length > 0) {
                    ArrayList<Object> arrayList = this.a;
                    arrayList.ensureCapacity(arrayList.size() + objArr.length);
                    Collections.addAll(this.a, objArr);
                }
            } else if (obj instanceof Collection) {
                this.a.addAll((Collection) obj);
            } else if (obj instanceof Iterable) {
                for (Object obj2 : (Iterable) obj) {
                    this.a.add(obj2);
                }
            } else if (obj instanceof Iterator) {
                Iterator it = (Iterator) obj;
                while (it.hasNext()) {
                    this.a.add(it.next());
                }
            } else {
                throw new UnsupportedOperationException("Don't know how to spread " + obj.getClass());
            }
        }
    }

    public int size() {
        return this.a.size();
    }

    public void add(Object obj) {
        this.a.add(obj);
    }

    public Object[] toArray(Object[] objArr) {
        return this.a.toArray(objArr);
    }
}
