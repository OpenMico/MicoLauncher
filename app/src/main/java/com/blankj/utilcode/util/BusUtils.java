package com.blankj.utilcode.util;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class BusUtils {
    private static final Object a = "nULl";
    private final Map<String, List<a>> b;
    private final Map<String, Set<Object>> c;
    private final Map<String, List<String>> d;
    private final Map<String, Map<String, Object>> e;

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    /* loaded from: classes.dex */
    public @interface Bus {
        int priority() default 0;

        boolean sticky() default false;

        String tag();

        ThreadMode threadMode() default ThreadMode.POSTING;
    }

    /* loaded from: classes.dex */
    public enum ThreadMode {
        MAIN,
        IO,
        CPU,
        CACHED,
        SINGLE,
        POSTING
    }

    private void a() {
    }

    private BusUtils() {
        this.b = new ConcurrentHashMap();
        this.c = new ConcurrentHashMap();
        this.d = new ConcurrentHashMap();
        this.e = new ConcurrentHashMap();
        a();
    }

    public static void register(@Nullable Object obj) {
        b().a(obj);
    }

    public static void unregister(@Nullable Object obj) {
        b().c(obj);
    }

    public static void post(@NonNull String str) {
        if (str != null) {
            post(str, a);
            return;
        }
        throw new NullPointerException("Argument 'tag' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void post(@NonNull String str, @NonNull Object obj) {
        if (str == null) {
            throw new NullPointerException("Argument 'tag' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (obj != null) {
            b().a(str, obj);
        } else {
            throw new NullPointerException("Argument 'arg' of type Object (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void postSticky(@NonNull String str) {
        if (str != null) {
            postSticky(str, a);
            return;
        }
        throw new NullPointerException("Argument 'tag' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void postSticky(@NonNull String str, Object obj) {
        if (str != null) {
            b().b(str, obj);
            return;
        }
        throw new NullPointerException("Argument 'tag' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void removeSticky(String str) {
        b().b(str);
    }

    public static String toString_() {
        return b().toString();
    }

    public String toString() {
        return "BusUtils: " + this.b;
    }

    private static BusUtils b() {
        return b.a;
    }

    private void a(@Nullable Object obj) {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            String name = cls.getName();
            boolean z = false;
            synchronized (this.c) {
                Set<Object> set = this.c.get(name);
                if (set == null) {
                    set = new CopyOnWriteArraySet<>();
                    this.c.put(name, set);
                    z = true;
                }
                if (set.contains(obj)) {
                    Log.w("BusUtils", "The bus of <" + obj + "> already registered.");
                    return;
                }
                set.add(obj);
                if (z) {
                    a(cls, name);
                }
                b(obj);
            }
        }
    }

    private void a(Class<?> cls, String str) {
        if (this.d.get(str) == null) {
            synchronized (this.d) {
                if (this.d.get(str) == null) {
                    CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
                    for (Map.Entry<String, List<a>> entry : this.b.entrySet()) {
                        for (a aVar : entry.getValue()) {
                            try {
                                if (Class.forName(aVar.b).isAssignableFrom(cls)) {
                                    copyOnWriteArrayList.add(entry.getKey());
                                    aVar.j.add(str);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    this.d.put(str, copyOnWriteArrayList);
                }
            }
        }
    }

    private void b(Object obj) {
        Map<String, Object> map = this.e.get(obj.getClass().getName());
        if (map != null) {
            synchronized (this.e) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    a(obj, entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void a(Object obj, String str, Object obj2) {
        List<a> list = this.b.get(str);
        if (list == null) {
            Log.e("BusUtils", "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (a aVar : list) {
            if (aVar.j.contains(obj.getClass().getName()) && aVar.f) {
                synchronized (this.e) {
                    Map<String, Object> map = this.e.get(aVar.b);
                    if (map != null && map.containsKey(str)) {
                        a(obj, obj2, aVar, true);
                    }
                }
            }
        }
    }

    private void c(Object obj) {
        if (obj != null) {
            String name = obj.getClass().getName();
            synchronized (this.c) {
                Set<Object> set = this.c.get(name);
                if (set != null && set.contains(obj)) {
                    set.remove(obj);
                    return;
                }
                Log.e("BusUtils", "The bus of <" + obj + "> was not registered before.");
            }
        }
    }

    private void a(String str, Object obj) {
        a(str, obj, false);
    }

    private void a(String str, Object obj, boolean z) {
        List<a> list = this.b.get(str);
        if (list == null) {
            Log.e("BusUtils", "The bus of tag <" + str + "> is not exists.");
            if (this.b.isEmpty()) {
                Log.e("BusUtils", "Please check whether the bus plugin is applied.");
                return;
            }
            return;
        }
        for (a aVar : list) {
            a(obj, aVar, z);
        }
    }

    private void a(Object obj, a aVar, boolean z) {
        a(null, obj, aVar, z);
    }

    private void a(Object obj, Object obj2, a aVar, boolean z) {
        if (aVar.i == null) {
            Method a2 = a(aVar);
            if (a2 != null) {
                aVar.i = a2;
            } else {
                return;
            }
        }
        b(obj, obj2, aVar, z);
    }

    private Method a(a aVar) {
        try {
            return "".equals(aVar.d) ? Class.forName(aVar.b).getDeclaredMethod(aVar.c, new Class[0]) : Class.forName(aVar.b).getDeclaredMethod(aVar.c, a(aVar.d));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Class a(String str) throws ClassNotFoundException {
        char c;
        switch (str.hashCode()) {
            case -1325958191:
                if (str.equals("double")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 104431:
                if (str.equals("int")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3039496:
                if (str.equals("byte")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3052374:
                if (str.equals("char")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 3327612:
                if (str.equals("long")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 64711720:
                if (str.equals("boolean")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 97526364:
                if (str.equals("float")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 109413500:
                if (str.equals("short")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return Boolean.TYPE;
            case 1:
                return Integer.TYPE;
            case 2:
                return Long.TYPE;
            case 3:
                return Short.TYPE;
            case 4:
                return Byte.TYPE;
            case 5:
                return Double.TYPE;
            case 6:
                return Float.TYPE;
            case 7:
                return Character.TYPE;
            default:
                return Class.forName(str);
        }
    }

    private void b(final Object obj, final Object obj2, final a aVar, final boolean z) {
        char c;
        Runnable runnable = new Runnable() { // from class: com.blankj.utilcode.util.BusUtils.1
            @Override // java.lang.Runnable
            public void run() {
                BusUtils.this.c(obj, obj2, aVar, z);
            }
        };
        String str = aVar.g;
        int hashCode = str.hashCode();
        if (hashCode == -1848936376) {
            if (str.equals("SINGLE")) {
                c = 4;
            }
            c = 65535;
        } else if (hashCode == 2342) {
            if (str.equals("IO")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode == 66952) {
            if (str.equals("CPU")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 2358713) {
            if (hashCode == 1980249378 && str.equals("CACHED")) {
                c = 3;
            }
            c = 65535;
        } else {
            if (str.equals("MAIN")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                ThreadUtils.runOnUiThread(runnable);
                return;
            case 1:
                ThreadUtils.getIoPool().execute(runnable);
                return;
            case 2:
                ThreadUtils.getCpuPool().execute(runnable);
                return;
            case 3:
                ThreadUtils.getCachedPool().execute(runnable);
                return;
            case 4:
                ThreadUtils.getSinglePool().execute(runnable);
                return;
            default:
                runnable.run();
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Object obj, Object obj2, a aVar, boolean z) {
        Set<Object> hashSet = new HashSet<>();
        if (obj == null) {
            for (String str : aVar.j) {
                Set<Object> set = this.c.get(str);
                if (set != null && !set.isEmpty()) {
                    hashSet.addAll(set);
                }
            }
            if (hashSet.size() == 0) {
                if (!z) {
                    Log.e("BusUtils", "The " + aVar + " was not registered before.");
                    return;
                }
                return;
            }
        } else {
            hashSet.add(obj);
        }
        a(obj2, aVar, hashSet);
    }

    private void a(Object obj, a aVar, Set<Object> set) {
        try {
            if (obj == a) {
                for (Object obj2 : set) {
                    aVar.i.invoke(obj2, new Object[0]);
                }
                return;
            }
            for (Object obj3 : set) {
                aVar.i.invoke(obj3, obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        }
    }

    private void b(String str, Object obj) {
        List<a> list = this.b.get(str);
        if (list == null) {
            Log.e("BusUtils", "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (a aVar : list) {
            if (!aVar.f) {
                a(obj, aVar, false);
            } else {
                synchronized (this.e) {
                    Map<String, Object> map = this.e.get(aVar.b);
                    if (map == null) {
                        map = new ConcurrentHashMap<>();
                        this.e.put(aVar.b, map);
                    }
                    map.put(str, obj);
                }
                a(obj, aVar, true);
            }
        }
    }

    private void b(String str) {
        List<a> list = this.b.get(str);
        if (list == null) {
            Log.e("BusUtils", "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (a aVar : list) {
            if (aVar.f) {
                synchronized (this.e) {
                    Map<String, Object> map = this.e.get(aVar.b);
                    if (map != null && map.containsKey(str)) {
                        map.remove(str);
                    }
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        String a;
        String b;
        String c;
        String d;
        String e;
        boolean f;
        String g;
        int h;
        Method i;
        List<String> j;

        public String toString() {
            return "BusInfo { tag : " + this.a + ", desc: " + a() + ", sticky: " + this.f + ", threadMode: " + this.g + ", method: " + this.i + ", priority: " + this.h + " }";
        }

        private String a() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append(this.b);
            sb.append("#");
            sb.append(this.c);
            if ("".equals(this.d)) {
                str = "()";
            } else {
                str = "(" + this.d + StringUtils.SPACE + this.e + ")";
            }
            sb.append(str);
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b {
        private static final BusUtils a = new BusUtils();
    }
}
