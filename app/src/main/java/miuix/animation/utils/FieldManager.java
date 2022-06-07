package miuix.animation.utils;

import android.util.ArrayMap;
import android.util.Log;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes5.dex */
public class FieldManager {
    Map<String, b> a = new ArrayMap();
    Map<String, a> b = new ArrayMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class b {
        Method a;

        b() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class a {
        Field a;

        a() {
        }
    }

    public synchronized <T> T getField(Object obj, String str, Class<T> cls) {
        if (!(obj == null || str == null)) {
            if (str.length() != 0) {
                b bVar = this.a.get(str);
                if (bVar == null) {
                    bVar = a(obj, a(str, BluetoothConstants.GET), this.a, new Class[0]);
                }
                if (bVar.a != null) {
                    return (T) a(a(obj, bVar.a, new Object[0]), cls);
                }
                a aVar = this.b.get(str);
                if (aVar == null) {
                    aVar = a(obj, str, (Class<?>) cls, this.b);
                }
                if (aVar.a == null) {
                    return null;
                }
                return (T) a(obj, aVar.a);
            }
        }
        return null;
    }

    public synchronized <T> boolean setField(Object obj, String str, Class<T> cls, T t) {
        if (!(obj == null || str == null)) {
            if (str.length() != 0) {
                b bVar = this.a.get(str);
                if (bVar == null) {
                    bVar = a(obj, a(str, BluetoothConstants.SET), this.a, cls);
                }
                if (bVar.a != null) {
                    a(obj, bVar.a, t);
                    return true;
                }
                a aVar = this.b.get(str);
                if (aVar == null) {
                    aVar = a(obj, str, (Class<?>) cls, this.b);
                }
                if (aVar.a == null) {
                    return false;
                }
                a(obj, aVar.a, t);
                return true;
            }
        }
        return false;
    }

    static <T> T a(Object obj, Class<T> cls) {
        if (!(obj instanceof Number)) {
            return null;
        }
        Number number = (Number) obj;
        if (cls == Float.class || cls == Float.TYPE) {
            return (T) Float.valueOf(number.floatValue());
        }
        if (cls == Integer.class || cls == Integer.TYPE) {
            return (T) Integer.valueOf(number.intValue());
        }
        throw new IllegalArgumentException("getPropertyValue, clz must be float or int instead of " + cls);
    }

    static String a(String str, String str2) {
        return str2 + Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    static <T> T a(Object obj, Field field) {
        try {
            return (T) field.get(obj);
        } catch (Exception unused) {
            return null;
        }
    }

    static <T> void a(Object obj, Field field, T t) {
        try {
            field.set(obj, t);
        } catch (Exception unused) {
        }
    }

    static b a(Object obj, String str, Map<String, b> map, Class<?>... clsArr) {
        b bVar = map.get(str);
        if (bVar != null) {
            return bVar;
        }
        b bVar2 = new b();
        bVar2.a = a(obj, str, clsArr);
        map.put(str, bVar2);
        return bVar2;
    }

    static Method a(Object obj, String str, Class<?>... clsArr) {
        try {
            try {
                Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
                declaredMethod.setAccessible(true);
                return declaredMethod;
            } catch (NoSuchMethodException unused) {
                return obj.getClass().getMethod(str, clsArr);
            }
        } catch (NoSuchMethodException unused2) {
            return null;
        }
    }

    static a a(Object obj, String str, Class<?> cls, Map<String, a> map) {
        a aVar = map.get(str);
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a();
        aVar2.a = a(obj, str, cls);
        map.put(str, aVar2);
        return aVar2;
    }

    static Field a(Object obj, String str, Class<?> cls) {
        Field field;
        try {
            field = obj.getClass().getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            field = null;
        }
        try {
            field.setAccessible(true);
        } catch (NoSuchFieldException unused2) {
            try {
                field = obj.getClass().getField(str);
            } catch (NoSuchFieldException unused3) {
            }
            if (field != null) {
            }
            return field;
        }
        if (field != null || field.getType() == cls) {
            return field;
        }
        return null;
    }

    static <T> T a(Object obj, Method method, Object... objArr) {
        if (method == null) {
            return null;
        }
        try {
            return (T) method.invoke(obj, objArr);
        } catch (Exception e) {
            Log.d(CommonUtils.TAG, "ValueProperty.invokeMethod failed, " + method.getName(), e);
            return null;
        }
    }
}
