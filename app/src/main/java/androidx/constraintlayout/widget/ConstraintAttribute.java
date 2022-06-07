package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ConstraintAttribute {
    String a;
    boolean b;
    private AttributeType c;
    private int d;
    private float e;
    private String f;
    private int g;

    /* loaded from: classes.dex */
    public enum AttributeType {
        INT_TYPE,
        FLOAT_TYPE,
        COLOR_TYPE,
        COLOR_DRAWABLE_TYPE,
        STRING_TYPE,
        BOOLEAN_TYPE,
        DIMENSION_TYPE
    }

    private static int a(int i) {
        int i2 = (i & (~(i >> 31))) - 255;
        return (i2 & (i2 >> 31)) + 255;
    }

    public AttributeType getType() {
        return this.c;
    }

    public void setFloatValue(float f) {
        this.e = f;
    }

    public void setColorValue(int i) {
        this.g = i;
    }

    public void setIntValue(int i) {
        this.d = i;
    }

    public void setStringValue(String str) {
        this.f = str;
    }

    public int noOfInterpValues() {
        switch (this.c) {
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                return 4;
            default:
                return 1;
        }
    }

    public float getValueToInterpolate() {
        switch (this.c) {
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case INT_TYPE:
                return this.d;
            case FLOAT_TYPE:
                return this.e;
            case STRING_TYPE:
                throw new RuntimeException("Cannot interpolate String");
            case BOOLEAN_TYPE:
                return this.b ? 1.0f : 0.0f;
            case DIMENSION_TYPE:
                return this.e;
            default:
                return Float.NaN;
        }
    }

    public void getValuesToInterpolate(float[] fArr) {
        switch (this.c) {
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                int i = this.g;
                float pow = (float) Math.pow(((i >> 16) & 255) / 255.0f, 2.2d);
                float pow2 = (float) Math.pow(((i >> 8) & 255) / 255.0f, 2.2d);
                fArr[0] = pow;
                fArr[1] = pow2;
                fArr[2] = (float) Math.pow((i & 255) / 255.0f, 2.2d);
                fArr[3] = ((i >> 24) & 255) / 255.0f;
                return;
            case INT_TYPE:
                fArr[0] = this.d;
                return;
            case FLOAT_TYPE:
                fArr[0] = this.e;
                return;
            case STRING_TYPE:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case BOOLEAN_TYPE:
                fArr[0] = this.b ? 1.0f : 0.0f;
                return;
            case DIMENSION_TYPE:
                fArr[0] = this.e;
                return;
            default:
                return;
        }
    }

    public void setValue(float[] fArr) {
        boolean z = false;
        switch (this.c) {
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                this.g = Color.HSVToColor(fArr);
                this.g = (a((int) (fArr[3] * 255.0f)) << 24) | (this.g & 16777215);
                return;
            case INT_TYPE:
                this.d = (int) fArr[0];
                return;
            case FLOAT_TYPE:
                this.e = fArr[0];
                return;
            case STRING_TYPE:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case BOOLEAN_TYPE:
                if (fArr[0] > 0.5d) {
                    z = true;
                }
                this.b = z;
                return;
            case DIMENSION_TYPE:
                this.e = fArr[0];
                return;
            default:
                return;
        }
    }

    public boolean diff(ConstraintAttribute constraintAttribute) {
        if (constraintAttribute == null || this.c != constraintAttribute.c) {
            return false;
        }
        switch (this.c) {
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                return this.g == constraintAttribute.g;
            case INT_TYPE:
                return this.d == constraintAttribute.d;
            case FLOAT_TYPE:
                return this.e == constraintAttribute.e;
            case STRING_TYPE:
                return this.d == constraintAttribute.d;
            case BOOLEAN_TYPE:
                return this.b == constraintAttribute.b;
            case DIMENSION_TYPE:
                return this.e == constraintAttribute.e;
            default:
                return false;
        }
    }

    public ConstraintAttribute(String str, AttributeType attributeType) {
        this.a = str;
        this.c = attributeType;
    }

    public ConstraintAttribute(String str, AttributeType attributeType, Object obj) {
        this.a = str;
        this.c = attributeType;
        setValue(obj);
    }

    public ConstraintAttribute(ConstraintAttribute constraintAttribute, Object obj) {
        this.a = constraintAttribute.a;
        this.c = constraintAttribute.c;
        setValue(obj);
    }

    public void setValue(Object obj) {
        switch (this.c) {
            case COLOR_TYPE:
            case COLOR_DRAWABLE_TYPE:
                this.g = ((Integer) obj).intValue();
                return;
            case INT_TYPE:
                this.d = ((Integer) obj).intValue();
                return;
            case FLOAT_TYPE:
                this.e = ((Float) obj).floatValue();
                return;
            case STRING_TYPE:
                this.f = (String) obj;
                return;
            case BOOLEAN_TYPE:
                this.b = ((Boolean) obj).booleanValue();
                return;
            case DIMENSION_TYPE:
                this.e = ((Float) obj).floatValue();
                return;
            default:
                return;
        }
    }

    public static HashMap<String, ConstraintAttribute> extractAttributes(HashMap<String, ConstraintAttribute> hashMap, View view) {
        HashMap<String, ConstraintAttribute> hashMap2 = new HashMap<>();
        Class<?> cls = view.getClass();
        for (String str : hashMap.keySet()) {
            ConstraintAttribute constraintAttribute = hashMap.get(str);
            try {
                if (str.equals("BackgroundColor")) {
                    hashMap2.put(str, new ConstraintAttribute(constraintAttribute, Integer.valueOf(((ColorDrawable) view.getBackground()).getColor())));
                } else {
                    hashMap2.put(str, new ConstraintAttribute(constraintAttribute, cls.getMethod("getMap" + str, new Class[0]).invoke(view, new Object[0])));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        return hashMap2;
    }

    public static void setAttributes(View view, HashMap<String, ConstraintAttribute> hashMap) {
        Class<?> cls = view.getClass();
        for (String str : hashMap.keySet()) {
            ConstraintAttribute constraintAttribute = hashMap.get(str);
            String str2 = BluetoothConstants.SET + str;
            try {
                switch (constraintAttribute.c) {
                    case COLOR_TYPE:
                        cls.getMethod(str2, Integer.TYPE).invoke(view, Integer.valueOf(constraintAttribute.g));
                        break;
                    case COLOR_DRAWABLE_TYPE:
                        Method method = cls.getMethod(str2, Drawable.class);
                        ColorDrawable colorDrawable = new ColorDrawable();
                        colorDrawable.setColor(constraintAttribute.g);
                        method.invoke(view, colorDrawable);
                        break;
                    case INT_TYPE:
                        cls.getMethod(str2, Integer.TYPE).invoke(view, Integer.valueOf(constraintAttribute.d));
                        break;
                    case FLOAT_TYPE:
                        cls.getMethod(str2, Float.TYPE).invoke(view, Float.valueOf(constraintAttribute.e));
                        break;
                    case STRING_TYPE:
                        cls.getMethod(str2, CharSequence.class).invoke(view, constraintAttribute.f);
                        break;
                    case BOOLEAN_TYPE:
                        cls.getMethod(str2, Boolean.TYPE).invoke(view, Boolean.valueOf(constraintAttribute.b));
                        break;
                    case DIMENSION_TYPE:
                        cls.getMethod(str2, Float.TYPE).invoke(view, Float.valueOf(constraintAttribute.e));
                        break;
                }
            } catch (IllegalAccessException e) {
                Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + cls.getName());
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                Log.e("TransitionLayout", e2.getMessage());
                Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + cls.getName());
                StringBuilder sb = new StringBuilder();
                sb.append(cls.getName());
                sb.append(" must have a method ");
                sb.append(str2);
                Log.e("TransitionLayout", sb.toString());
            } catch (InvocationTargetException e3) {
                Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + cls.getName());
                e3.printStackTrace();
            }
        }
    }

    public void setInterpolatedValue(View view, float[] fArr) {
        Class<?> cls = view.getClass();
        String str = BluetoothConstants.SET + this.a;
        try {
            boolean z = true;
            switch (this.c) {
                case COLOR_TYPE:
                    cls.getMethod(str, Integer.TYPE).invoke(view, Integer.valueOf((a((int) (fArr[3] * 255.0f)) << 24) | (a((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (a((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | a((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f))));
                    return;
                case COLOR_DRAWABLE_TYPE:
                    Method method = cls.getMethod(str, Drawable.class);
                    int a = a((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f));
                    int a2 = a((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f));
                    int a3 = a((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f));
                    ColorDrawable colorDrawable = new ColorDrawable();
                    colorDrawable.setColor((a((int) (fArr[3] * 255.0f)) << 24) | (a << 16) | (a2 << 8) | a3);
                    method.invoke(view, colorDrawable);
                    return;
                case INT_TYPE:
                    cls.getMethod(str, Integer.TYPE).invoke(view, Integer.valueOf((int) fArr[0]));
                    return;
                case FLOAT_TYPE:
                    cls.getMethod(str, Float.TYPE).invoke(view, Float.valueOf(fArr[0]));
                    return;
                case STRING_TYPE:
                    throw new RuntimeException("unable to interpolate strings " + this.a);
                case BOOLEAN_TYPE:
                    Method method2 = cls.getMethod(str, Boolean.TYPE);
                    Object[] objArr = new Object[1];
                    if (fArr[0] <= 0.5f) {
                        z = false;
                    }
                    objArr[0] = Boolean.valueOf(z);
                    method2.invoke(view, objArr);
                    return;
                case DIMENSION_TYPE:
                    cls.getMethod(str, Float.TYPE).invoke(view, Float.valueOf(fArr[0]));
                    return;
                default:
                    return;
            }
        } catch (IllegalAccessException e) {
            Log.e("TransitionLayout", "cannot access method " + str + "on View \"" + Debug.getName(view) + "\"");
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            Log.e("TransitionLayout", "no method " + str + "on View \"" + Debug.getName(view) + "\"");
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
    }

    public static void parse(Context context, XmlPullParser xmlPullParser, HashMap<String, ConstraintAttribute> hashMap) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.CustomAttribute);
        int indexCount = obtainStyledAttributes.getIndexCount();
        String str = null;
        Object obj = null;
        AttributeType attributeType = null;
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.CustomAttribute_attributeName) {
                str = obtainStyledAttributes.getString(index);
                if (str != null && str.length() > 0) {
                    str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
                }
            } else if (index == R.styleable.CustomAttribute_customBoolean) {
                obj = Boolean.valueOf(obtainStyledAttributes.getBoolean(index, false));
                attributeType = AttributeType.BOOLEAN_TYPE;
            } else if (index == R.styleable.CustomAttribute_customColorValue) {
                AttributeType attributeType2 = AttributeType.COLOR_TYPE;
                obj = Integer.valueOf(obtainStyledAttributes.getColor(index, 0));
                attributeType = attributeType2;
            } else if (index == R.styleable.CustomAttribute_customColorDrawableValue) {
                AttributeType attributeType3 = AttributeType.COLOR_DRAWABLE_TYPE;
                obj = Integer.valueOf(obtainStyledAttributes.getColor(index, 0));
                attributeType = attributeType3;
            } else if (index == R.styleable.CustomAttribute_customPixelDimension) {
                AttributeType attributeType4 = AttributeType.DIMENSION_TYPE;
                obj = Float.valueOf(TypedValue.applyDimension(1, obtainStyledAttributes.getDimension(index, 0.0f), context.getResources().getDisplayMetrics()));
                attributeType = attributeType4;
            } else if (index == R.styleable.CustomAttribute_customDimension) {
                AttributeType attributeType5 = AttributeType.DIMENSION_TYPE;
                obj = Float.valueOf(obtainStyledAttributes.getDimension(index, 0.0f));
                attributeType = attributeType5;
            } else if (index == R.styleable.CustomAttribute_customFloatValue) {
                AttributeType attributeType6 = AttributeType.FLOAT_TYPE;
                obj = Float.valueOf(obtainStyledAttributes.getFloat(index, Float.NaN));
                attributeType = attributeType6;
            } else if (index == R.styleable.CustomAttribute_customIntegerValue) {
                AttributeType attributeType7 = AttributeType.INT_TYPE;
                obj = Integer.valueOf(obtainStyledAttributes.getInteger(index, -1));
                attributeType = attributeType7;
            } else if (index == R.styleable.CustomAttribute_customStringValue) {
                attributeType = AttributeType.STRING_TYPE;
                obj = obtainStyledAttributes.getString(index);
            }
        }
        if (!(str == null || obj == null)) {
            hashMap.put(str, new ConstraintAttribute(str, attributeType, obj));
        }
        obtainStyledAttributes.recycle();
    }
}
