package com.alibaba.fastjson.serializer;

/* loaded from: classes.dex */
public class PascalNameFilter implements NameFilter {
    @Override // com.alibaba.fastjson.serializer.NameFilter
    public String process(Object obj, String str, Object obj2) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        return new String(charArray);
    }
}
