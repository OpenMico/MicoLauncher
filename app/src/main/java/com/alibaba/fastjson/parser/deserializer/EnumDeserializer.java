package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class EnumDeserializer implements ObjectDeserializer {
    protected final Class<?> enumClass;
    protected long[] enumNameHashCodes;
    protected final Enum[] enums;
    protected final Enum[] ordinalEnums;

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    public EnumDeserializer(Class<?> cls) {
        this.enumClass = cls;
        this.ordinalEnums = (Enum[]) cls.getEnumConstants();
        HashMap hashMap = new HashMap();
        int i = 0;
        while (true) {
            Enum[] enumArr = this.ordinalEnums;
            if (i >= enumArr.length) {
                break;
            }
            Enum r5 = enumArr[i];
            String name = r5.name();
            JSONField jSONField = null;
            try {
                jSONField = (JSONField) TypeUtils.getAnnotation(cls.getField(name), JSONField.class);
                if (jSONField != null) {
                    try {
                        String name2 = jSONField.name();
                        if (name2 != null && name2.length() > 0) {
                            name = name2;
                        }
                    } catch (Exception unused) {
                    }
                }
            } catch (Exception unused2) {
            }
            long j = -3750763034362895579L;
            long j2 = -3750763034362895579L;
            for (int i2 = 0; i2 < name.length(); i2++) {
                int charAt = name.charAt(i2);
                long j3 = charAt ^ j;
                if (charAt >= 65 && charAt <= 90) {
                    charAt += 32;
                }
                j = j3 * 1099511628211L;
                j2 = (charAt ^ j2) * 1099511628211L;
            }
            hashMap.put(Long.valueOf(j), r5);
            if (j != j2) {
                hashMap.put(Long.valueOf(j2), r5);
            }
            if (jSONField != null) {
                String[] alternateNames = jSONField.alternateNames();
                int length = alternateNames.length;
                int i3 = 0;
                while (i3 < length) {
                    String str = alternateNames[i3];
                    int i4 = 0;
                    long j4 = -3750763034362895579L;
                    while (i4 < str.length()) {
                        j4 = (j4 ^ str.charAt(i4)) * 1099511628211L;
                        i4++;
                        i = i;
                    }
                    if (!(j4 == j || j4 == j2)) {
                        hashMap.put(Long.valueOf(j4), r5);
                    }
                    i3++;
                    i = i;
                }
            }
            i++;
        }
        this.enumNameHashCodes = new long[hashMap.size()];
        int i5 = 0;
        for (Long l : hashMap.keySet()) {
            i5++;
            this.enumNameHashCodes[i5] = l.longValue();
        }
        Arrays.sort(this.enumNameHashCodes);
        this.enums = new Enum[this.enumNameHashCodes.length];
        int i6 = 0;
        while (true) {
            long[] jArr = this.enumNameHashCodes;
            if (i6 < jArr.length) {
                this.enums[i6] = (Enum) hashMap.get(Long.valueOf(jArr[i6]));
                i6++;
            } else {
                return;
            }
        }
    }

    public Enum getEnumByHashCode(long j) {
        int binarySearch;
        if (this.enums != null && (binarySearch = Arrays.binarySearch(this.enumNameHashCodes, j)) >= 0) {
            return this.enums[binarySearch];
        }
        return null;
    }

    public Enum<?> valueOf(int i) {
        return this.ordinalEnums[i];
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        try {
            JSONLexer jSONLexer = defaultJSONParser.lexer;
            int i = jSONLexer.token();
            if (i == 2) {
                int intValue = jSONLexer.intValue();
                jSONLexer.nextToken(16);
                if (intValue >= 0 && intValue <= this.ordinalEnums.length) {
                    return (T) this.ordinalEnums[intValue];
                }
                throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + intValue);
            } else if (i == 4) {
                String stringVal = jSONLexer.stringVal();
                jSONLexer.nextToken(16);
                if (stringVal.length() == 0) {
                    return null;
                }
                long j = -3750763034362895579L;
                long j2 = -3750763034362895579L;
                for (int i2 = 0; i2 < stringVal.length(); i2++) {
                    int charAt = stringVal.charAt(i2);
                    long j3 = j ^ charAt;
                    if (charAt >= 65 && charAt <= 90) {
                        charAt += 32;
                    }
                    j = j3 * 1099511628211L;
                    j2 = (j2 ^ charAt) * 1099511628211L;
                }
                T t = (T) getEnumByHashCode(j);
                if (t == null && j2 != j) {
                    t = (T) getEnumByHashCode(j2);
                }
                if (t == null && jSONLexer.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                    throw new JSONException("not match enum value, " + this.enumClass.getName() + " : " + stringVal);
                }
                return t;
            } else if (i == 8) {
                jSONLexer.nextToken(16);
                return null;
            } else {
                throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + defaultJSONParser.parse());
            }
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }
}
