package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class MqttSubAckPayload {
    private final List<Integer> a;

    public MqttSubAckPayload(int... iArr) {
        if (iArr != null) {
            ArrayList arrayList = new ArrayList(iArr.length);
            for (int i : iArr) {
                arrayList.add(Integer.valueOf(i));
            }
            this.a = Collections.unmodifiableList(arrayList);
            return;
        }
        throw new NullPointerException("grantedQoSLevels");
    }

    public MqttSubAckPayload(Iterable<Integer> iterable) {
        Integer next;
        if (iterable != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<Integer> it = iterable.iterator();
            while (it.hasNext() && (next = it.next()) != null) {
                arrayList.add(next);
            }
            this.a = Collections.unmodifiableList(arrayList);
            return;
        }
        throw new NullPointerException("grantedQoSLevels");
    }

    public List<Integer> grantedQoSLevels() {
        return this.a;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[grantedQoSLevels=" + this.a + ']';
    }
}
