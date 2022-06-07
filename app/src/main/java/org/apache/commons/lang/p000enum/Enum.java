package org.apache.commons.lang.p000enum;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

/* renamed from: org.apache.commons.lang.enum.Enum */
/* loaded from: classes5.dex */
public abstract class Enum implements Serializable, Comparable {
    static Class a = null;
    static Class b = null;
    private static final Map c = Collections.unmodifiableMap(new HashMap(0));
    private static Map d = new WeakHashMap();
    private static final long serialVersionUID = -487045951170455942L;
    private final transient int e;
    private final String iName;
    protected transient String iToString = null;

    /* renamed from: org.apache.commons.lang.enum.Enum$a */
    /* loaded from: classes5.dex */
    public static class a {
        final Map a = new HashMap();
        final Map b = Collections.unmodifiableMap(this.a);
        final List c = new ArrayList(25);
        final List d = Collections.unmodifiableList(this.c);

        protected a() {
        }
    }

    public Enum(String str) {
        b(str);
        this.iName = str;
        this.e = getEnumClass().hashCode() + 7 + (str.hashCode() * 3);
    }

    private void b(String str) {
        a aVar;
        if (!StringUtils.isEmpty(str)) {
            Class<?> enumClass = getEnumClass();
            if (enumClass != null) {
                Class<?> cls = getClass();
                boolean z = false;
                while (true) {
                    if (cls == null) {
                        break;
                    }
                    Class<?> cls2 = a;
                    if (cls2 == null) {
                        cls2 = a("org.apache.commons.lang.enum.Enum");
                        a = cls2;
                    }
                    if (cls == cls2) {
                        break;
                    }
                    Class<?> cls3 = b;
                    if (cls3 == null) {
                        cls3 = a("org.apache.commons.lang.enum.ValuedEnum");
                        b = cls3;
                    }
                    if (cls == cls3) {
                        break;
                    } else if (cls == enumClass) {
                        z = true;
                        break;
                    } else {
                        cls = cls.getSuperclass();
                    }
                }
                if (z) {
                    Class cls4 = a;
                    if (cls4 == null) {
                        cls4 = a("org.apache.commons.lang.enum.Enum");
                        a = cls4;
                    }
                    synchronized (cls4) {
                        aVar = (a) d.get(enumClass);
                        if (aVar == null) {
                            aVar = b(enumClass);
                            WeakHashMap weakHashMap = new WeakHashMap();
                            weakHashMap.putAll(d);
                            weakHashMap.put(enumClass, aVar);
                            d = weakHashMap;
                        }
                    }
                    if (!aVar.a.containsKey(str)) {
                        aVar.a.put(str, this);
                        aVar.c.add(this);
                        return;
                    }
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("The Enum name must be unique, '");
                    stringBuffer.append(str);
                    stringBuffer.append("' has already been added");
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
                throw new IllegalArgumentException("getEnumClass() must return a superclass of this class");
            }
            throw new IllegalArgumentException("getEnumClass() must not be null");
        }
        throw new IllegalArgumentException("The Enum name must not be empty or null");
    }

    static Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    protected Object readResolve() {
        a aVar = (a) d.get(getEnumClass());
        if (aVar == null) {
            return null;
        }
        return aVar.a.get(getName());
    }

    public static Enum getEnum(Class cls, String str) {
        a a2 = a(cls);
        if (a2 == null) {
            return null;
        }
        return (Enum) a2.a.get(str);
    }

    public static Map getEnumMap(Class cls) {
        a a2 = a(cls);
        if (a2 == null) {
            return c;
        }
        return a2.b;
    }

    public static List getEnumList(Class cls) {
        a a2 = a(cls);
        if (a2 == null) {
            return Collections.EMPTY_LIST;
        }
        return a2.d;
    }

    protected static Iterator iterator(Class cls) {
        return getEnumList(cls).iterator();
    }

    private static a a(Class cls) {
        if (cls != null) {
            Class cls2 = a;
            if (cls2 == null) {
                cls2 = a("org.apache.commons.lang.enum.Enum");
                a = cls2;
            }
            if (cls2.isAssignableFrom(cls)) {
                a aVar = (a) d.get(cls);
                if (aVar == null) {
                    try {
                        Class.forName(cls.getName(), true, cls.getClassLoader());
                        return (a) d.get(cls);
                    } catch (Exception unused) {
                    }
                }
                return aVar;
            }
            throw new IllegalArgumentException("The Class must be a subclass of Enum");
        }
        throw new IllegalArgumentException("The Enum Class must not be null");
    }

    private static a b(Class cls) {
        a aVar = new a();
        Class superclass = cls.getSuperclass();
        while (true) {
            if (superclass == null) {
                break;
            }
            Class cls2 = a;
            if (cls2 == null) {
                cls2 = a("org.apache.commons.lang.enum.Enum");
                a = cls2;
            }
            if (superclass == cls2) {
                break;
            }
            Class cls3 = b;
            if (cls3 == null) {
                cls3 = a("org.apache.commons.lang.enum.ValuedEnum");
                b = cls3;
            }
            if (superclass == cls3) {
                break;
            }
            a aVar2 = (a) d.get(superclass);
            if (aVar2 != null) {
                aVar.c.addAll(aVar2.c);
                aVar.a.putAll(aVar2.a);
                break;
            }
            superclass = superclass.getSuperclass();
        }
        return aVar;
    }

    public final String getName() {
        return this.iName;
    }

    public Class getEnumClass() {
        return getClass();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == getClass()) {
            return this.iName.equals(((Enum) obj).iName);
        }
        if (!obj.getClass().getName().equals(getClass().getName())) {
            return false;
        }
        return this.iName.equals(a(obj));
    }

    public final int hashCode() {
        return this.e;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == this) {
            return 0;
        }
        if (obj.getClass() == getClass()) {
            return this.iName.compareTo(((Enum) obj).iName);
        }
        if (obj.getClass().getName().equals(getClass().getName())) {
            return this.iName.compareTo(a(obj));
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Different enum class '");
        stringBuffer.append(ClassUtils.getShortClassName(obj.getClass()));
        stringBuffer.append(LrcRow.SINGLE_QUOTE);
        throw new ClassCastException(stringBuffer.toString());
    }

    private String a(Object obj) {
        try {
            return (String) obj.getClass().getMethod("getName", null).invoke(obj, null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            throw new IllegalStateException("This should not happen");
        }
    }

    public String toString() {
        if (this.iToString == null) {
            String shortClassName = ClassUtils.getShortClassName(getEnumClass());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(shortClassName);
            stringBuffer.append("[");
            stringBuffer.append(getName());
            stringBuffer.append("]");
            this.iToString = stringBuffer.toString();
        }
        return this.iToString;
    }
}
