package miuix.animation.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import miuix.animation.IAnimTarget;
import miuix.animation.ValueTarget;
import miuix.animation.property.FloatProperty;

/* compiled from: AnimOperationInfo.java */
/* loaded from: classes5.dex */
class a {
    public final IAnimTarget a;
    public final byte b;
    public final List<FloatProperty> c;
    public volatile long d;
    public int e = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(IAnimTarget iAnimTarget, byte b, String[] strArr, FloatProperty[] floatPropertyArr) {
        this.b = b;
        this.a = iAnimTarget;
        if (strArr != null && (iAnimTarget instanceof ValueTarget)) {
            ValueTarget valueTarget = (ValueTarget) iAnimTarget;
            this.c = new ArrayList();
            for (String str : strArr) {
                this.c.add(valueTarget.getFloatProperty(str));
            }
        } else if (floatPropertyArr != null) {
            this.c = Arrays.asList(floatPropertyArr);
        } else {
            this.c = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        List<FloatProperty> list = this.c;
        int size = list == null ? 0 : list.size();
        if (size == 0) {
            if (this.e <= 0) {
                return false;
            }
        } else if (this.e != size) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AnimOperationInfo{target=");
        sb.append(this.a);
        sb.append(", op=");
        sb.append((int) this.b);
        sb.append(", propList=");
        List<FloatProperty> list = this.c;
        sb.append(list != null ? Arrays.toString(list.toArray()) : null);
        sb.append('}');
        return sb.toString();
    }
}
