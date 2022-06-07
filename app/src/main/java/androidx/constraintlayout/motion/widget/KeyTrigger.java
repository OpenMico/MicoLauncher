package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class KeyTrigger extends Key {
    public static final int KEY_TYPE = 5;
    private Method t;
    private Method u;
    private Method v;
    private float w;
    private int h = -1;
    private String i = null;
    private int j = UNSET;
    private String k = null;
    private String l = null;
    private int m = UNSET;
    private int n = UNSET;
    private View o = null;
    float e = 0.1f;
    private boolean p = true;
    private boolean q = true;
    private boolean r = true;
    private float s = Float.NaN;
    private boolean x = false;
    RectF f = new RectF();
    RectF g = new RectF();

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, SplineSet> hashMap) {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void getAttributeNames(HashSet<String> hashSet) {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String str, Object obj) {
    }

    public KeyTrigger() {
        this.mType = 5;
        this.d = new HashMap();
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        a.a(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyTrigger), context);
    }

    private void a(RectF rectF, View view, boolean z) {
        rectF.top = view.getTop();
        rectF.bottom = view.getBottom();
        rectF.left = view.getLeft();
        rectF.right = view.getRight();
        if (z) {
            view.getMatrix().mapRect(rectF);
        }
    }

    public void conditionallyFire(float f, View view) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (this.n != UNSET) {
            if (this.o == null) {
                this.o = ((ViewGroup) view.getParent()).findViewById(this.n);
            }
            a(this.f, this.o, this.x);
            a(this.g, view, this.x);
            if (this.f.intersect(this.g)) {
                if (this.p) {
                    this.p = false;
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (this.r) {
                    this.r = false;
                    z3 = true;
                } else {
                    z3 = false;
                }
                this.q = true;
                z = false;
            } else {
                if (!this.p) {
                    this.p = true;
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (this.q) {
                    this.q = false;
                    z = true;
                } else {
                    z = false;
                }
                this.r = true;
                z3 = false;
            }
        } else {
            if (this.p) {
                float f2 = this.s;
                if ((f - f2) * (this.w - f2) < 0.0f) {
                    this.p = false;
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                if (Math.abs(f - this.s) > this.e) {
                    this.p = true;
                }
                z2 = false;
            }
            if (this.q) {
                float f3 = this.s;
                float f4 = f - f3;
                if ((this.w - f3) * f4 >= 0.0f || f4 >= 0.0f) {
                    z = false;
                } else {
                    this.q = false;
                    z = true;
                }
            } else {
                if (Math.abs(f - this.s) > this.e) {
                    this.q = true;
                }
                z = false;
            }
            if (this.r) {
                float f5 = this.s;
                float f6 = f - f5;
                if ((this.w - f5) * f6 >= 0.0f || f6 <= 0.0f) {
                    z3 = false;
                } else {
                    this.r = false;
                }
            } else {
                if (Math.abs(f - this.s) > this.e) {
                    this.r = true;
                }
                z3 = false;
            }
        }
        this.w = f;
        if (z || z2 || z3) {
            ((MotionLayout) view.getParent()).fireTrigger(this.m, z3, f);
        }
        if (this.j != UNSET) {
            view = ((MotionLayout) view.getParent()).findViewById(this.j);
        }
        if (z && this.k != null) {
            if (this.u == null) {
                try {
                    this.u = view.getClass().getMethod(this.k, new Class[0]);
                } catch (NoSuchMethodException unused) {
                    Log.e("KeyTrigger", "Could not find method \"" + this.k + "\"on class " + view.getClass().getSimpleName() + StringUtils.SPACE + Debug.getName(view));
                }
            }
            try {
                this.u.invoke(view, new Object[0]);
            } catch (Exception unused2) {
                Log.e("KeyTrigger", "Exception in call \"" + this.k + "\"on class " + view.getClass().getSimpleName() + StringUtils.SPACE + Debug.getName(view));
            }
        }
        if (z3 && this.l != null) {
            if (this.v == null) {
                try {
                    this.v = view.getClass().getMethod(this.l, new Class[0]);
                } catch (NoSuchMethodException unused3) {
                    Log.e("KeyTrigger", "Could not find method \"" + this.l + "\"on class " + view.getClass().getSimpleName() + StringUtils.SPACE + Debug.getName(view));
                }
            }
            try {
                this.v.invoke(view, new Object[0]);
            } catch (Exception unused4) {
                Log.e("KeyTrigger", "Exception in call \"" + this.l + "\"on class " + view.getClass().getSimpleName() + StringUtils.SPACE + Debug.getName(view));
            }
        }
        if (z2 && this.i != null) {
            if (this.t == null) {
                try {
                    this.t = view.getClass().getMethod(this.i, new Class[0]);
                } catch (NoSuchMethodException unused5) {
                    Log.e("KeyTrigger", "Could not find method \"" + this.i + "\"on class " + view.getClass().getSimpleName() + StringUtils.SPACE + Debug.getName(view));
                }
            }
            try {
                this.t.invoke(view, new Object[0]);
            } catch (Exception unused6) {
                Log.e("KeyTrigger", "Exception in call \"" + this.i + "\"on class " + view.getClass().getSimpleName() + StringUtils.SPACE + Debug.getName(view));
            }
        }
    }

    /* loaded from: classes.dex */
    private static class a {
        private static SparseIntArray a = new SparseIntArray();

        static {
            a.append(R.styleable.KeyTrigger_framePosition, 8);
            a.append(R.styleable.KeyTrigger_onCross, 4);
            a.append(R.styleable.KeyTrigger_onNegativeCross, 1);
            a.append(R.styleable.KeyTrigger_onPositiveCross, 2);
            a.append(R.styleable.KeyTrigger_motionTarget, 7);
            a.append(R.styleable.KeyTrigger_triggerId, 6);
            a.append(R.styleable.KeyTrigger_triggerSlack, 5);
            a.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
            a.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
            a.append(R.styleable.KeyTrigger_triggerReceiver, 11);
        }

        public static void a(KeyTrigger keyTrigger, TypedArray typedArray, Context context) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (a.get(index)) {
                    case 1:
                        keyTrigger.k = typedArray.getString(index);
                        continue;
                    case 2:
                        keyTrigger.l = typedArray.getString(index);
                        continue;
                    case 4:
                        keyTrigger.i = typedArray.getString(index);
                        continue;
                    case 5:
                        keyTrigger.e = typedArray.getFloat(index, keyTrigger.e);
                        continue;
                    case 6:
                        keyTrigger.m = typedArray.getResourceId(index, keyTrigger.m);
                        continue;
                    case 7:
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            keyTrigger.b = typedArray.getResourceId(index, keyTrigger.b);
                            if (keyTrigger.b == -1) {
                                keyTrigger.c = typedArray.getString(index);
                            } else {
                                continue;
                            }
                        } else if (typedArray.peekValue(index).type == 3) {
                            keyTrigger.c = typedArray.getString(index);
                        } else {
                            keyTrigger.b = typedArray.getResourceId(index, keyTrigger.b);
                        }
                    case 8:
                        keyTrigger.a = typedArray.getInteger(index, keyTrigger.a);
                        keyTrigger.s = (keyTrigger.a + 0.5f) / 100.0f;
                        continue;
                    case 9:
                        keyTrigger.n = typedArray.getResourceId(index, keyTrigger.n);
                        continue;
                    case 10:
                        keyTrigger.x = typedArray.getBoolean(index, keyTrigger.x);
                        continue;
                    case 11:
                        keyTrigger.j = typedArray.getResourceId(index, keyTrigger.j);
                        break;
                }
                Log.e("KeyTrigger", "unused attribute 0x" + Integer.toHexString(index) + "   " + a.get(index));
            }
        }
    }
}
