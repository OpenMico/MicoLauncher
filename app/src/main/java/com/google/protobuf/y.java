package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.api.b;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MessageLiteToString.java */
/* loaded from: classes2.dex */
public final class y {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(MessageLite messageLite, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        a(messageLite, sb, 0);
        return sb.toString();
    }

    private static void a(MessageLite messageLite, StringBuilder sb, int i) {
        boolean z;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        TreeSet<String> treeSet = new TreeSet();
        Method[] declaredMethods = messageLite.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            hashMap2.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                hashMap.put(method.getName(), method);
                if (method.getName().startsWith(BluetoothConstants.GET)) {
                    treeSet.add(method.getName());
                }
            }
        }
        for (String str : treeSet) {
            String substring = str.startsWith(BluetoothConstants.GET) ? str.substring(3) : str;
            if (substring.endsWith("List") && !substring.endsWith("OrBuilderList") && !substring.equals("List")) {
                String str2 = substring.substring(0, 1).toLowerCase() + substring.substring(1, substring.length() - 4);
                Method method2 = (Method) hashMap.get(str);
                if (method2 != null && method2.getReturnType().equals(List.class)) {
                    a(sb, i, a(str2), GeneratedMessageLite.a(method2, messageLite, new Object[0]));
                }
            }
            if (substring.endsWith(AIApiConstants.Map.NAME) && !substring.equals(AIApiConstants.Map.NAME)) {
                String str3 = substring.substring(0, 1).toLowerCase() + substring.substring(1, substring.length() - 3);
                Method method3 = (Method) hashMap.get(str);
                if (method3 != null && method3.getReturnType().equals(Map.class) && !method3.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method3.getModifiers())) {
                    a(sb, i, a(str3), GeneratedMessageLite.a(method3, messageLite, new Object[0]));
                }
            }
            if (((Method) hashMap2.get(BluetoothConstants.SET + substring)) != null) {
                if (substring.endsWith("Bytes")) {
                    if (hashMap.containsKey(BluetoothConstants.GET + substring.substring(0, substring.length() - 5))) {
                    }
                }
                String str4 = substring.substring(0, 1).toLowerCase() + substring.substring(1);
                Method method4 = (Method) hashMap.get(BluetoothConstants.GET + substring);
                Method method5 = (Method) hashMap.get("has" + substring);
                if (method4 != null) {
                    Object a = GeneratedMessageLite.a(method4, messageLite, new Object[0]);
                    if (method5 == null) {
                        z = !a(a);
                    } else {
                        z = ((Boolean) GeneratedMessageLite.a(method5, messageLite, new Object[0])).booleanValue();
                    }
                    if (z) {
                        a(sb, i, a(str4), a);
                    }
                }
            }
        }
        if (messageLite instanceof GeneratedMessageLite.ExtendableMessage) {
            Iterator<Map.Entry<GeneratedMessageLite.a, Object>> g = ((GeneratedMessageLite.ExtendableMessage) messageLite).extensions.g();
            while (g.hasNext()) {
                Map.Entry<GeneratedMessageLite.a, Object> next = g.next();
                a(sb, i, "[" + next.getKey().getNumber() + "]", next.getValue());
            }
        }
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) messageLite;
        if (generatedMessageLite.unknownFields != null) {
            generatedMessageLite.unknownFields.a(sb, i);
        }
    }

    private static boolean a(Object obj) {
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue();
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() == 0;
        }
        if (obj instanceof Float) {
            return ((Float) obj).floatValue() == 0.0f;
        }
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue() == 0.0d;
        }
        if (obj instanceof String) {
            return obj.equals("");
        }
        if (obj instanceof ByteString) {
            return obj.equals(ByteString.EMPTY);
        }
        return obj instanceof MessageLite ? obj == ((MessageLite) obj).getDefaultInstanceForType() : (obj instanceof Enum) && ((Enum) obj).ordinal() == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void a(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            for (Object obj2 : (List) obj) {
                a(sb, i, str, obj2);
            }
        } else if (obj instanceof Map) {
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                a(sb, i, str, entry);
            }
        } else {
            sb.append('\n');
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                sb.append(' ');
            }
            sb.append(str);
            if (obj instanceof String) {
                sb.append(": \"");
                sb.append(aq.a((String) obj));
                sb.append('\"');
            } else if (obj instanceof ByteString) {
                sb.append(": \"");
                sb.append(aq.a((ByteString) obj));
                sb.append('\"');
            } else if (obj instanceof GeneratedMessageLite) {
                sb.append(" {");
                a((GeneratedMessageLite) obj, sb, i + 2);
                sb.append("\n");
                while (i2 < i) {
                    sb.append(' ');
                    i2++;
                }
                sb.append("}");
            } else if (obj instanceof Map.Entry) {
                sb.append(" {");
                Map.Entry entry2 = (Map.Entry) obj;
                int i4 = i + 2;
                a(sb, i4, "key", entry2.getKey());
                a(sb, i4, b.p, entry2.getValue());
                sb.append("\n");
                while (i2 < i) {
                    sb.append(' ');
                    i2++;
                }
                sb.append("}");
            } else {
                sb.append(": ");
                sb.append(obj.toString());
            }
        }
    }

    private static final String a(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(charAt));
        }
        return sb.toString();
    }
}
