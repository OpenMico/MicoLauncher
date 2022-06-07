package com.xiaomi.mitv.utils;

import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class BeanUtil {
    public static Map<String, Object> convert2map(Object obj, boolean z) throws InvocationTargetException, IllegalAccessException {
        Method[] methodArr;
        HashMap hashMap = new HashMap();
        if (z) {
            methodArr = obj.getClass().getDeclaredMethods();
        } else {
            methodArr = obj.getClass().getMethods();
        }
        for (Method method : methodArr) {
            String name = method.getName();
            if (name.contains(BluetoothConstants.GET)) {
                Object invoke = method.invoke(obj, new Object[0]);
                String substring = name.substring(name.indexOf(BluetoothConstants.GET) + 3);
                if (!"Class".equals(substring)) {
                    String lowerCase = substring.substring(0, 1).toString().toLowerCase();
                    hashMap.put(((Object) lowerCase) + substring.substring(1), invoke);
                }
            }
        }
        return hashMap;
    }
}
