package androidx.renderscript;

import android.os.Build;
import android.util.Log;
import android.util.Pair;
import androidx.renderscript.Allocation;
import androidx.renderscript.Script;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ScriptGroup extends BaseObj {
    b[] a;
    b[] b;
    private boolean c = false;
    private ArrayList<c> d = new ArrayList<>();
    private String e;
    private List<Closure> f;
    private List<Input> g;
    private Future[] h;

    /* loaded from: classes.dex */
    static class b {
        Script.KernelID a;
        Allocation b;

        b(Script.KernelID kernelID) {
            this.a = kernelID;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        Script.FieldID a;
        Script.KernelID b;
        Script.KernelID c;
        Type d;
        Allocation e;

        a(Type type, Script.KernelID kernelID, Script.KernelID kernelID2) {
            this.c = kernelID;
            this.b = kernelID2;
            this.d = type;
        }

        a(Type type, Script.KernelID kernelID, Script.FieldID fieldID) {
            this.c = kernelID;
            this.a = fieldID;
            this.d = type;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class c {
        Script a;
        ArrayList<Script.KernelID> b = new ArrayList<>();
        ArrayList<a> c = new ArrayList<>();
        ArrayList<a> d = new ArrayList<>();
        int e;
        boolean f;
        int g;

        c(Script script) {
            this.a = script;
        }
    }

    /* loaded from: classes.dex */
    public static final class Closure extends BaseObj {
        private Object[] a;
        private Allocation b;
        private Map<Script.FieldID, Object> c;
        private Future d;
        private Map<Script.FieldID, Future> e;
        private FieldPacker f;

        Closure(RenderScript renderScript, Script.KernelID kernelID, Type type, Object[] objArr, Map<Script.FieldID, Object> map) {
            super(0L, renderScript);
            if (Build.VERSION.SDK_INT >= 23 || !renderScript.b()) {
                this.a = objArr;
                this.b = Allocation.createTyped(renderScript, type);
                this.c = map;
                this.e = new HashMap();
                int length = objArr.length + map.size();
                long[] jArr = new long[length];
                long[] jArr2 = new long[length];
                int[] iArr = new int[length];
                long[] jArr3 = new long[length];
                long[] jArr4 = new long[length];
                int i = 0;
                while (i < objArr.length) {
                    jArr[i] = 0;
                    a(renderScript, i, null, objArr[i], jArr2, iArr, jArr3, jArr4);
                    i++;
                    jArr2 = jArr2;
                    jArr3 = jArr3;
                    jArr4 = jArr4;
                    iArr = iArr;
                }
                int i2 = i;
                for (Map.Entry<Script.FieldID, Object> entry : map.entrySet()) {
                    Object value = entry.getValue();
                    Script.FieldID key = entry.getKey();
                    jArr[i2] = key.a(renderScript);
                    a(renderScript, i2, key, value, jArr2, iArr, jArr3, jArr4);
                    i2++;
                }
                a(renderScript.a(kernelID.a(renderScript), this.b.a(renderScript), jArr, jArr2, iArr, jArr3, jArr4));
                return;
            }
            throw new RSRuntimeException("ScriptGroup2 not supported in this API level");
        }

        Closure(RenderScript renderScript, Script.InvokeID invokeID, Object[] objArr, Map<Script.FieldID, Object> map) {
            super(0L, renderScript);
            if (Build.VERSION.SDK_INT >= 23 || !renderScript.b()) {
                this.f = FieldPacker.a(objArr);
                this.a = objArr;
                this.c = map;
                this.e = new HashMap();
                int size = map.size();
                long[] jArr = new long[size];
                long[] jArr2 = new long[size];
                int[] iArr = new int[size];
                long[] jArr3 = new long[size];
                long[] jArr4 = new long[size];
                int i = 0;
                for (Map.Entry<Script.FieldID, Object> entry : map.entrySet()) {
                    Object value = entry.getValue();
                    Script.FieldID key = entry.getKey();
                    jArr[i] = key.a(renderScript);
                    a(renderScript, i, key, value, jArr2, iArr, jArr3, jArr4);
                    i++;
                }
                a(renderScript.a(invokeID.a(renderScript), this.f.getData(), jArr, jArr2, iArr));
                return;
            }
            throw new RSRuntimeException("ScriptGroup2 not supported in this API level");
        }

        private void a(RenderScript renderScript, int i, Script.FieldID fieldID, Object obj, long[] jArr, int[] iArr, long[] jArr2, long[] jArr3) {
            if (obj instanceof Future) {
                Future future = (Future) obj;
                Object c = future.c();
                jArr2[i] = future.a().a(renderScript);
                Script.FieldID b = future.b();
                jArr3[i] = b != null ? b.a(renderScript) : 0L;
                obj = c;
            } else {
                jArr2[i] = 0;
                jArr3[i] = 0;
            }
            if (obj instanceof Input) {
                Input input = (Input) obj;
                if (i < this.a.length) {
                    input.a(this, i);
                } else {
                    input.a(this, fieldID);
                }
                jArr[i] = 0;
                iArr[i] = 0;
                return;
            }
            a aVar = new a(renderScript, obj);
            jArr[i] = aVar.a;
            iArr[i] = aVar.b;
        }

        public Future getReturn() {
            if (this.d == null) {
                this.d = new Future(this, null, this.b);
            }
            return this.d;
        }

        public Future getGlobal(Script.FieldID fieldID) {
            Future future = this.e.get(fieldID);
            if (future != null) {
                return future;
            }
            Object obj = this.c.get(fieldID);
            if (obj instanceof Future) {
                obj = ((Future) obj).c();
            }
            Future future2 = new Future(this, fieldID, obj);
            this.e.put(fieldID, future2);
            return future2;
        }

        void a(int i, Object obj) {
            if (obj instanceof Future) {
                obj = ((Future) obj).c();
            }
            this.a[i] = obj;
            a aVar = new a(this.t, obj);
            this.t.a(a(this.t), i, aVar.a, aVar.b);
        }

        void a(Script.FieldID fieldID, Object obj) {
            if (obj instanceof Future) {
                obj = ((Future) obj).c();
            }
            this.c.put(fieldID, obj);
            a aVar = new a(this.t, obj);
            this.t.a(a(this.t), fieldID.a(this.t), aVar.a, aVar.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static final class a {
            public long a;
            public int b;

            public a(RenderScript renderScript, Object obj) {
                if (obj instanceof Allocation) {
                    this.a = ((Allocation) obj).a(renderScript);
                    this.b = -1;
                } else if (obj instanceof Boolean) {
                    this.a = ((Boolean) obj).booleanValue() ? 1L : 0L;
                    this.b = 4;
                } else if (obj instanceof Integer) {
                    this.a = ((Integer) obj).longValue();
                    this.b = 4;
                } else if (obj instanceof Long) {
                    this.a = ((Long) obj).longValue();
                    this.b = 8;
                } else if (obj instanceof Float) {
                    this.a = Float.floatToRawIntBits(((Float) obj).floatValue());
                    this.b = 4;
                } else if (obj instanceof Double) {
                    this.a = Double.doubleToRawLongBits(((Double) obj).doubleValue());
                    this.b = 8;
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class Future {
        Closure a;
        Script.FieldID b;
        Object c;

        Future(Closure closure, Script.FieldID fieldID, Object obj) {
            this.a = closure;
            this.b = fieldID;
            this.c = obj;
        }

        Closure a() {
            return this.a;
        }

        Script.FieldID b() {
            return this.b;
        }

        Object c() {
            return this.c;
        }
    }

    /* loaded from: classes.dex */
    public static final class Input {
        List<Pair<Closure, Script.FieldID>> a = new ArrayList();
        List<Pair<Closure, Integer>> b = new ArrayList();
        Object c;

        Input() {
        }

        void a(Closure closure, int i) {
            this.b.add(Pair.create(closure, Integer.valueOf(i)));
        }

        void a(Closure closure, Script.FieldID fieldID) {
            this.a.add(Pair.create(closure, fieldID));
        }

        void a(Object obj) {
            this.c = obj;
            for (Pair<Closure, Integer> pair : this.b) {
                ((Closure) pair.first).a(((Integer) pair.second).intValue(), obj);
            }
            for (Pair<Closure, Script.FieldID> pair2 : this.a) {
                ((Closure) pair2.first).a((Script.FieldID) pair2.second, obj);
            }
        }

        Object a() {
            return this.c;
        }
    }

    ScriptGroup(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    ScriptGroup(RenderScript renderScript, String str, List<Closure> list, List<Input> list2, Future[] futureArr) {
        super(0L, renderScript);
        if (Build.VERSION.SDK_INT >= 23 || !renderScript.b()) {
            this.e = str;
            this.f = list;
            this.g = list2;
            this.h = futureArr;
            long[] jArr = new long[list.size()];
            for (int i = 0; i < jArr.length; i++) {
                jArr[i] = list.get(i).a(renderScript);
            }
            a(renderScript.a(str, renderScript.getApplicationContext().getCacheDir().toString(), jArr));
            return;
        }
        throw new RSRuntimeException("ScriptGroup2 not supported in this API level");
    }

    public Object[] execute(Object... objArr) {
        if (objArr.length < this.g.size()) {
            Log.e("ScriptGroup", toString() + " receives " + objArr.length + " inputs, less than expected " + this.g.size());
            return null;
        }
        if (objArr.length > this.g.size()) {
            Log.i("ScriptGroup", toString() + " receives " + objArr.length + " inputs, more than expected " + this.g.size());
        }
        for (int i = 0; i < this.g.size(); i++) {
            Object obj = objArr[i];
            if ((obj instanceof Future) || (obj instanceof Input)) {
                Log.e("ScriptGroup", toString() + ": input " + i + " is a future or unbound value");
                return null;
            }
            this.g.get(i).a(obj);
        }
        this.t.g(a(this.t));
        Future[] futureArr = this.h;
        Object[] objArr2 = new Object[futureArr.length];
        int i2 = 0;
        for (Future future : futureArr) {
            Object c2 = future.c();
            if (c2 instanceof Input) {
                c2 = ((Input) c2).a();
            }
            i2++;
            objArr2[i2] = c2;
        }
        return objArr2;
    }

    @Deprecated
    public void setInput(Script.KernelID kernelID, Allocation allocation) {
        int i = 0;
        while (true) {
            b[] bVarArr = this.b;
            if (i >= bVarArr.length) {
                throw new RSIllegalArgumentException("Script not found");
            } else if (bVarArr[i].a == kernelID) {
                this.b[i].b = allocation;
                if (!this.c) {
                    this.t.a(a(this.t), kernelID.a(this.t), this.t.b(allocation));
                    return;
                }
                return;
            } else {
                i++;
            }
        }
    }

    @Deprecated
    public void setOutput(Script.KernelID kernelID, Allocation allocation) {
        int i = 0;
        while (true) {
            b[] bVarArr = this.a;
            if (i >= bVarArr.length) {
                throw new RSIllegalArgumentException("Script not found");
            } else if (bVarArr[i].a == kernelID) {
                this.a[i].b = allocation;
                if (!this.c) {
                    this.t.b(a(this.t), kernelID.a(this.t), this.t.b(allocation));
                    return;
                }
                return;
            } else {
                i++;
            }
        }
    }

    @Deprecated
    public void execute() {
        if (!this.c) {
            this.t.f(a(this.t));
            return;
        }
        for (int i = 0; i < this.d.size(); i++) {
            c cVar = this.d.get(i);
            for (int i2 = 0; i2 < cVar.d.size(); i2++) {
                a aVar = cVar.d.get(i2);
                if (aVar.e == null) {
                    Allocation createTyped = Allocation.createTyped(this.t, aVar.d, Allocation.MipmapControl.MIPMAP_NONE, 1);
                    aVar.e = createTyped;
                    for (int i3 = i2 + 1; i3 < cVar.d.size(); i3++) {
                        if (cVar.d.get(i3).c == aVar.c) {
                            cVar.d.get(i3).e = createTyped;
                        }
                    }
                }
            }
        }
        Iterator<c> it = this.d.iterator();
        while (it.hasNext()) {
            c next = it.next();
            Iterator<Script.KernelID> it2 = next.b.iterator();
            while (it2.hasNext()) {
                Script.KernelID next2 = it2.next();
                Iterator<a> it3 = next.c.iterator();
                Allocation allocation = null;
                while (it3.hasNext()) {
                    a next3 = it3.next();
                    if (next3.b == next2) {
                        allocation = next3.e;
                    }
                }
                b[] bVarArr = this.b;
                Allocation allocation2 = allocation;
                for (b bVar : bVarArr) {
                    if (bVar.a == next2) {
                        allocation2 = bVar.b;
                    }
                }
                Iterator<a> it4 = next.d.iterator();
                Allocation allocation3 = null;
                while (it4.hasNext()) {
                    a next4 = it4.next();
                    if (next4.c == next2) {
                        allocation3 = next4.e;
                    }
                }
                b[] bVarArr2 = this.a;
                Allocation allocation4 = allocation3;
                for (b bVar2 : bVarArr2) {
                    if (bVar2.a == next2) {
                        allocation4 = bVar2.b;
                    }
                }
                next2.a.forEach(next2.b, allocation2, allocation4, (FieldPacker) null);
            }
        }
    }

    @Deprecated
    /* loaded from: classes.dex */
    public static final class Builder {
        private RenderScript a;
        private int d;
        private ArrayList<c> b = new ArrayList<>();
        private ArrayList<a> c = new ArrayList<>();
        private boolean e = false;

        public Builder(RenderScript renderScript) {
            this.a = renderScript;
        }

        private void a(c cVar, c cVar2) {
            for (int i = 0; i < cVar.d.size(); i++) {
                a aVar = cVar.d.get(i);
                if (aVar.b != null) {
                    c a = a(aVar.b.a);
                    if (!a.equals(cVar2)) {
                        a(a, cVar2);
                    } else {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                }
                if (aVar.a != null) {
                    c a2 = a(aVar.a.a);
                    if (!a2.equals(cVar2)) {
                        a(a2, cVar2);
                    } else {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                }
            }
        }

        private void a(int i, int i2) {
            for (int i3 = 0; i3 < this.b.size(); i3++) {
                if (this.b.get(i3).e == i2) {
                    this.b.get(i3).e = i;
                }
            }
        }

        private void a(c cVar, int i) {
            if (cVar.e == 0 || cVar.e == i) {
                cVar.e = i;
                for (int i2 = 0; i2 < cVar.d.size(); i2++) {
                    a aVar = cVar.d.get(i2);
                    if (aVar.b != null) {
                        a(a(aVar.b.a), i);
                    }
                    if (aVar.a != null) {
                        a(a(aVar.a.a), i);
                    }
                }
                return;
            }
            a(cVar.e, i);
        }

        private void a() {
            for (int i = 0; i < this.b.size(); i++) {
                c cVar = this.b.get(i);
                if (cVar.c.size() == 0) {
                    if (cVar.d.size() != 0 || this.b.size() <= 1) {
                        a(cVar, i + 1);
                    } else {
                        throw new RSInvalidStateException("Groups cannot contain unconnected scripts");
                    }
                }
            }
            int i2 = this.b.get(0).e;
            for (int i3 = 0; i3 < this.b.size(); i3++) {
                if (this.b.get(i3).e != i2) {
                    throw new RSInvalidStateException("Multiple DAGs in group not allowed.");
                }
            }
        }

        private c a(Script script) {
            for (int i = 0; i < this.b.size(); i++) {
                if (script == this.b.get(i).a) {
                    return this.b.get(i);
                }
            }
            return null;
        }

        private c a(Script.KernelID kernelID) {
            for (int i = 0; i < this.b.size(); i++) {
                c cVar = this.b.get(i);
                for (int i2 = 0; i2 < cVar.b.size(); i2++) {
                    if (kernelID == cVar.b.get(i2)) {
                        return cVar;
                    }
                }
            }
            return null;
        }

        public Builder addKernel(Script.KernelID kernelID) {
            if (this.c.size() == 0) {
                if (kernelID.a.isIncSupp()) {
                    this.e = true;
                }
                if (a(kernelID) != null) {
                    return this;
                }
                this.d++;
                c a = a(kernelID.a);
                if (a == null) {
                    a = new c(kernelID.a);
                    this.b.add(a);
                }
                a.b.add(kernelID);
                return this;
            }
            throw new RSInvalidStateException("Kernels may not be added once connections exist.");
        }

        public Builder addConnection(Type type, Script.KernelID kernelID, Script.FieldID fieldID) {
            c a = a(kernelID);
            if (a != null) {
                c a2 = a(fieldID.a);
                if (a2 != null) {
                    a aVar = new a(type, kernelID, fieldID);
                    this.c.add(new a(type, kernelID, fieldID));
                    a.d.add(aVar);
                    a2.c.add(aVar);
                    a(a, a);
                    return this;
                }
                throw new RSInvalidStateException("To script not found.");
            }
            throw new RSInvalidStateException("From script not found.");
        }

        public Builder addConnection(Type type, Script.KernelID kernelID, Script.KernelID kernelID2) {
            c a = a(kernelID);
            if (a != null) {
                c a2 = a(kernelID2);
                if (a2 != null) {
                    a aVar = new a(type, kernelID, kernelID2);
                    this.c.add(new a(type, kernelID, kernelID2));
                    a.d.add(aVar);
                    a2.c.add(aVar);
                    a(a, a);
                    return this;
                }
                throw new RSInvalidStateException("To script not found.");
            }
            throw new RSInvalidStateException("From script not found.");
        }

        private boolean b(c cVar, int i) {
            c cVar2;
            cVar.f = true;
            if (cVar.g < i) {
                cVar.g = i;
            }
            Iterator<a> it = cVar.d.iterator();
            boolean z = true;
            while (it.hasNext()) {
                a next = it.next();
                if (next.a != null) {
                    cVar2 = a(next.a.a);
                } else {
                    cVar2 = a(next.b.a);
                }
                if (cVar2.f) {
                    return false;
                }
                z &= b(cVar2, cVar.g + 1);
            }
            return z;
        }

        private boolean b() {
            Iterator<c> it = this.b.iterator();
            boolean z = true;
            while (it.hasNext()) {
                c next = it.next();
                if (next.c.size() == 0) {
                    Iterator<c> it2 = this.b.iterator();
                    while (it2.hasNext()) {
                        it2.next().f = false;
                    }
                    z &= b(next, 1);
                }
            }
            Collections.sort(this.b, new Comparator<c>() { // from class: androidx.renderscript.ScriptGroup.Builder.1
                /* renamed from: a */
                public int compare(c cVar, c cVar2) {
                    return cVar.g - cVar2.g;
                }
            });
            return z;
        }

        public ScriptGroup create() {
            long j;
            if (this.b.size() != 0) {
                for (int i = 0; i < this.b.size(); i++) {
                    this.b.get(i).e = 0;
                }
                a();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                long[] jArr = new long[this.d];
                int i2 = 0;
                int i3 = 0;
                while (i2 < this.b.size()) {
                    c cVar = this.b.get(i2);
                    int i4 = i3;
                    for (int i5 = 0; i5 < cVar.b.size(); i5++) {
                        Script.KernelID kernelID = cVar.b.get(i5);
                        i4++;
                        jArr[i4] = kernelID.a(this.a);
                        boolean z = false;
                        for (int i6 = 0; i6 < cVar.c.size(); i6++) {
                            if (cVar.c.get(i6).b == kernelID) {
                                z = true;
                            }
                        }
                        boolean z2 = false;
                        for (int i7 = 0; i7 < cVar.d.size(); i7++) {
                            if (cVar.d.get(i7).c == kernelID) {
                                z2 = true;
                            }
                        }
                        if (!z) {
                            arrayList.add(new b(kernelID));
                        }
                        if (!z2) {
                            arrayList2.add(new b(kernelID));
                        }
                    }
                    i2++;
                    i3 = i4;
                }
                if (i3 == this.d) {
                    if (!this.e) {
                        long[] jArr2 = new long[this.c.size()];
                        long[] jArr3 = new long[this.c.size()];
                        long[] jArr4 = new long[this.c.size()];
                        long[] jArr5 = new long[this.c.size()];
                        for (int i8 = 0; i8 < this.c.size(); i8++) {
                            a aVar = this.c.get(i8);
                            jArr2[i8] = aVar.c.a(this.a);
                            if (aVar.b != null) {
                                jArr3[i8] = aVar.b.a(this.a);
                            }
                            if (aVar.a != null) {
                                jArr4[i8] = aVar.a.a(this.a);
                            }
                            jArr5[i8] = aVar.d.a(this.a);
                        }
                        j = this.a.a(jArr, jArr2, jArr3, jArr4, jArr5);
                        if (j == 0) {
                            throw new RSRuntimeException("Object creation error, should not happen.");
                        }
                    } else {
                        b();
                        j = 0;
                    }
                    ScriptGroup scriptGroup = new ScriptGroup(j, this.a);
                    scriptGroup.a = new b[arrayList2.size()];
                    for (int i9 = 0; i9 < arrayList2.size(); i9++) {
                        scriptGroup.a[i9] = (b) arrayList2.get(i9);
                    }
                    scriptGroup.b = new b[arrayList.size()];
                    for (int i10 = 0; i10 < arrayList.size(); i10++) {
                        scriptGroup.b[i10] = (b) arrayList.get(i10);
                    }
                    scriptGroup.d = this.b;
                    scriptGroup.c = this.e;
                    return scriptGroup;
                }
                throw new RSRuntimeException("Count mismatch, should not happen.");
            }
            throw new RSInvalidStateException("Empty script groups are not allowed");
        }
    }

    /* loaded from: classes.dex */
    public static final class Binding {
        private final Script.FieldID a;
        private final Object b;

        public Binding(Script.FieldID fieldID, Object obj) {
            this.a = fieldID;
            this.b = obj;
        }

        public Script.FieldID getField() {
            return this.a;
        }

        public Object getValue() {
            return this.b;
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder2 {
        RenderScript a;
        List<Closure> b = new ArrayList();
        List<Input> c = new ArrayList();

        public Builder2(RenderScript renderScript) {
            this.a = renderScript;
        }

        private Closure a(Script.KernelID kernelID, Type type, Object[] objArr, Map<Script.FieldID, Object> map) {
            Closure closure = new Closure(this.a, kernelID, type, objArr, map);
            this.b.add(closure);
            return closure;
        }

        private Closure a(Script.InvokeID invokeID, Object[] objArr, Map<Script.FieldID, Object> map) {
            Closure closure = new Closure(this.a, invokeID, objArr, map);
            this.b.add(closure);
            return closure;
        }

        public Input addInput() {
            Input input = new Input();
            this.c.add(input);
            return input;
        }

        public Closure addKernel(Script.KernelID kernelID, Type type, Object... objArr) {
            ArrayList<Object> arrayList = new ArrayList<>();
            HashMap hashMap = new HashMap();
            if (!a(objArr, arrayList, hashMap)) {
                return null;
            }
            return a(kernelID, type, arrayList.toArray(), hashMap);
        }

        public Closure addInvoke(Script.InvokeID invokeID, Object... objArr) {
            ArrayList<Object> arrayList = new ArrayList<>();
            HashMap hashMap = new HashMap();
            if (!a(objArr, arrayList, hashMap)) {
                return null;
            }
            return a(invokeID, arrayList.toArray(), hashMap);
        }

        public ScriptGroup create(String str, Future... futureArr) {
            if (str != null && !str.isEmpty() && str.length() <= 100 && str.equals(str.replaceAll("[^a-zA-Z0-9-]", "_"))) {
                return new ScriptGroup(this.a, str, this.b, this.c, futureArr);
            }
            throw new RSIllegalArgumentException("invalid script group name");
        }

        private boolean a(Object[] objArr, ArrayList<Object> arrayList, Map<Script.FieldID, Object> map) {
            int i = 0;
            while (i < objArr.length && !(objArr[i] instanceof Binding)) {
                arrayList.add(objArr[i]);
                i++;
            }
            while (i < objArr.length) {
                if (!(objArr[i] instanceof Binding)) {
                    return false;
                }
                Binding binding = (Binding) objArr[i];
                map.put(binding.getField(), binding.getValue());
                i++;
            }
            return true;
        }
    }
}
