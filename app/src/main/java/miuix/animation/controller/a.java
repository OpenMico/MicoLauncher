package miuix.animation.controller;

import java.lang.reflect.Array;
import miuix.animation.IAnimTarget;
import miuix.animation.ValueTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.IntValueProperty;
import miuix.animation.property.ValueProperty;
import miuix.animation.utils.EaseManager;

/* compiled from: StateHelper.java */
/* loaded from: classes5.dex */
class a {
    static final ValueProperty a = new ValueProperty("defaultProperty");
    static final IntValueProperty b = new IntValueProperty("defaultIntProperty");

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v1, types: [int] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3, types: [int] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r9v0, types: [miuix.animation.controller.a] */
    /* JADX WARN: Unknown variable types count: 4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(miuix.animation.IAnimTarget r10, miuix.animation.controller.AnimState r11, miuix.animation.base.AnimConfigLink r12, java.lang.Object... r13) {
        /*
            r9 = this;
            int r0 = r13.length
            if (r0 != 0) goto L_0x0004
            return
        L_0x0004:
            r0 = 0
            r0 = r13[r0]
            java.lang.Object r1 = r11.getTag()
            boolean r0 = r0.equals(r1)
            r7 = r0
        L_0x0010:
            int r0 = r13.length
            if (r7 >= r0) goto L_0x0033
            r5 = r13[r7]
            int r0 = r7 + 1
            int r1 = r13.length
            if (r0 >= r1) goto L_0x001d
            r1 = r13[r0]
            goto L_0x001e
        L_0x001d:
            r1 = 0
        L_0x001e:
            r6 = r1
            boolean r1 = r5 instanceof java.lang.String
            if (r1 == 0) goto L_0x0029
            boolean r1 = r6 instanceof java.lang.String
            if (r1 == 0) goto L_0x0029
            r7 = r0
            goto L_0x0010
        L_0x0029:
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r8 = r13
            int r7 = r1.a(r2, r3, r4, r5, r6, r7, r8)
            goto L_0x0010
        L_0x0033:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: miuix.animation.controller.a.a(miuix.animation.IAnimTarget, miuix.animation.controller.AnimState, miuix.animation.base.AnimConfigLink, java.lang.Object[]):void");
    }

    private int a(IAnimTarget iAnimTarget, AnimState animState, AnimConfigLink animConfigLink, Object obj, Object obj2, int i, Object... objArr) {
        int i2;
        FloatProperty a2;
        if (a(animConfigLink, obj) || (a2 = a(iAnimTarget, obj, obj2)) == null) {
            i2 = 0;
        } else {
            if (!a(a2)) {
                i++;
            }
            i2 = a(iAnimTarget, animState, a2, i, objArr);
        }
        return i2 > 0 ? i + i2 : i + 1;
    }

    private boolean a(FloatProperty floatProperty) {
        return floatProperty == a || floatProperty == b;
    }

    private boolean a(AnimConfigLink animConfigLink, Object obj) {
        if ((obj instanceof TransitionListener) || (obj instanceof EaseManager.EaseStyle)) {
            a(animConfigLink.getHead(), obj);
            return true;
        } else if (!obj.getClass().isArray()) {
            return b(animConfigLink, obj);
        } else {
            int length = Array.getLength(obj);
            boolean z = false;
            for (int i = 0; i < length; i++) {
                z = b(animConfigLink, Array.get(obj, i)) || z;
            }
            return z;
        }
    }

    private void a(AnimConfig animConfig, Object obj) {
        if (obj instanceof TransitionListener) {
            animConfig.addListeners((TransitionListener) obj);
        } else if (obj instanceof EaseManager.EaseStyle) {
            animConfig.setEase((EaseManager.EaseStyle) obj);
        }
    }

    private boolean b(AnimConfigLink animConfigLink, Object obj) {
        if (obj instanceof AnimConfig) {
            animConfigLink.add((AnimConfig) obj, new boolean[0]);
            return true;
        }
        if (obj instanceof AnimConfigLink) {
            animConfigLink.add((AnimConfigLink) obj, new boolean[0]);
        }
        return false;
    }

    private FloatProperty a(IAnimTarget iAnimTarget, Object obj, Object obj2) {
        Class<?> cls = null;
        if (obj instanceof FloatProperty) {
            return (FloatProperty) obj;
        }
        if ((obj instanceof String) && (iAnimTarget instanceof ValueTarget)) {
            if (obj2 != null) {
                cls = obj2.getClass();
            }
            return ((ValueTarget) iAnimTarget).createProperty((String) obj, cls);
        } else if (obj instanceof Float) {
            return a;
        } else {
            return null;
        }
    }

    private int a(IAnimTarget iAnimTarget, AnimState animState, FloatProperty floatProperty, int i, Object... objArr) {
        Object a2;
        if (floatProperty == null || (a2 = a(i, objArr)) == null || !a(animState, floatProperty, a2)) {
            return 0;
        }
        return a(iAnimTarget, floatProperty, i + 1, objArr) ? 2 : 1;
    }

    private Object a(int i, Object... objArr) {
        if (i < objArr.length) {
            return objArr[i];
        }
        return null;
    }

    private boolean a(IAnimTarget iAnimTarget, FloatProperty floatProperty, int i, Object... objArr) {
        if (i >= objArr.length) {
            return false;
        }
        Object obj = objArr[i];
        if (!(obj instanceof Float)) {
            return false;
        }
        iAnimTarget.setVelocity(floatProperty, ((Float) obj).floatValue());
        return true;
    }

    private boolean a(AnimState animState, FloatProperty floatProperty, Object obj) {
        boolean z = obj instanceof Integer;
        if (!z && !(obj instanceof Float) && !(obj instanceof Double)) {
            return false;
        }
        if (floatProperty instanceof IIntValueProperty) {
            animState.add(floatProperty, a(obj, z));
            return true;
        }
        animState.add(floatProperty, b(obj, z));
        return true;
    }

    private int a(Object obj, boolean z) {
        return z ? ((Integer) obj).intValue() : (int) ((Float) obj).floatValue();
    }

    private float b(Object obj, boolean z) {
        return z ? ((Integer) obj).intValue() : ((Float) obj).floatValue();
    }
}
