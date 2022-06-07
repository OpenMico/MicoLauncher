package com.xiaomi.micolauncher.instruciton.base;

import android.util.ArrayMap;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class OperationsQueue {
    private final LinkedList<BaseOperation> a = new LinkedList<>();
    private final ConcurrentHashMap<String, BaseOperation> b = new ConcurrentHashMap<>();
    private final Object d = new Object();
    private final Object e = new Object();
    private final ArrayMap<String, List<Instruction>> c = new ArrayMap<>();

    public void cancel() {
    }

    public ConcurrentHashMap<String, BaseOperation> getOpConcurrentHashMap() {
        return this.b;
    }

    public synchronized void addInstructions(String str, List<Instruction> list) {
        if (ContainerUtil.hasData(list)) {
            synchronized (this.e) {
                this.c.put(str, new ArrayList(list));
            }
        }
    }

    public List<Instruction> getInstructions(String str) {
        List<Instruction> list;
        synchronized (this.e) {
            list = this.c.get(str);
        }
        return list;
    }

    public List<Instruction> removeInstructions(String str) {
        List<Instruction> remove;
        synchronized (this.e) {
            remove = this.c.remove(str);
        }
        return remove;
    }

    public void removeAllInstructions() {
        synchronized (this.e) {
            this.c.clear();
        }
    }

    public void removeOperations(String str) {
        if (ContainerUtil.hasData(this.a)) {
            synchronized (this.d) {
                Iterator<BaseOperation> it = this.a.iterator();
                while (it.hasNext()) {
                    BaseOperation next = it.next();
                    if (str.equals(next.getDialogId())) {
                        next.cancel();
                        it.remove();
                    }
                }
            }
        }
    }

    public void addAll(List<BaseOperation> list) {
        synchronized (this.d) {
            if (ContainerUtil.hasData(list)) {
                this.a.addAll(list);
            }
        }
    }

    public void addFirstAll(List<BaseOperation> list) {
        synchronized (this.d) {
            this.a.addAll(0, list);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseOperation a() {
        BaseOperation peek;
        synchronized (this.d) {
            peek = this.a.peek();
        }
        return peek;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseOperation b() {
        BaseOperation pop;
        synchronized (this.d) {
            pop = this.a.pop();
        }
        return pop;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        boolean isEmpty;
        synchronized (this.d) {
            isEmpty = this.a.isEmpty();
        }
        return isEmpty;
    }

    public void cancelProcessAll() {
        L.ope.i("OperationsQueue cancelProcessAll");
        synchronized (this.d) {
            if (ContainerUtil.hasData(this.a)) {
                Iterator<BaseOperation> it = this.a.iterator();
                while (it.hasNext()) {
                    it.next().cancel();
                }
            }
            this.a.clear();
        }
    }
}
