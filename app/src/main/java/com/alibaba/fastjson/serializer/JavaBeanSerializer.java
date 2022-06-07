package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class JavaBeanSerializer extends SerializeFilterable implements ObjectSerializer {
    protected SerializeBeanInfo beanInfo;
    protected final FieldSerializer[] getters;
    private volatile transient long[] hashArray;
    private volatile transient short[] hashArrayMapping;
    protected final FieldSerializer[] sortedGetters;

    public JavaBeanSerializer(Class<?> cls) {
        this(cls, (Map<String, String>) null);
    }

    public JavaBeanSerializer(Class<?> cls, String... strArr) {
        this(cls, createAliasMap(strArr));
    }

    static Map<String, String> createAliasMap(String... strArr) {
        HashMap hashMap = new HashMap();
        for (String str : strArr) {
            hashMap.put(str, str);
        }
        return hashMap;
    }

    public Class<?> getType() {
        return this.beanInfo.beanType;
    }

    public JavaBeanSerializer(Class<?> cls, Map<String, String> map) {
        this(TypeUtils.buildBeanInfo(cls, map, null));
    }

    public JavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) {
        boolean z;
        this.beanInfo = serializeBeanInfo;
        this.sortedGetters = new FieldSerializer[serializeBeanInfo.sortedFields.length];
        int i = 0;
        while (true) {
            FieldSerializer[] fieldSerializerArr = this.sortedGetters;
            if (i >= fieldSerializerArr.length) {
                break;
            }
            fieldSerializerArr[i] = new FieldSerializer(serializeBeanInfo.beanType, serializeBeanInfo.sortedFields[i]);
            i++;
        }
        if (serializeBeanInfo.fields == serializeBeanInfo.sortedFields) {
            this.getters = this.sortedGetters;
        } else {
            this.getters = new FieldSerializer[serializeBeanInfo.fields.length];
            int i2 = 0;
            while (true) {
                if (i2 >= this.getters.length) {
                    z = false;
                    break;
                }
                FieldSerializer fieldSerializer = getFieldSerializer(serializeBeanInfo.fields[i2].name);
                if (fieldSerializer == null) {
                    z = true;
                    break;
                } else {
                    this.getters[i2] = fieldSerializer;
                    i2++;
                }
            }
            if (z) {
                FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                System.arraycopy(fieldSerializerArr2, 0, this.getters, 0, fieldSerializerArr2.length);
            }
        }
        if (serializeBeanInfo.jsonType != null) {
            for (Class<? extends SerializeFilter> cls : serializeBeanInfo.jsonType.serialzeFilters()) {
                try {
                    addFilter((SerializeFilter) cls.getConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (Exception unused) {
                }
            }
        }
    }

    public void writeDirectNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        write(jSONSerializer, obj, obj2, type, i);
    }

    public void writeAsArray(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        write(jSONSerializer, obj, obj2, type, i);
    }

    public void writeAsArrayNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        write(jSONSerializer, obj, obj2, type, i);
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        write(jSONSerializer, obj, obj2, type, i, false);
    }

    public void writeNoneASM(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        write(jSONSerializer, obj, obj2, type, i, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:297:0x0432, code lost:
        r5 = 0;
        r14.write(r13.name_chars, 0, r13.name_chars.length);
     */
    /* JADX WARN: Removed duplicated region for block: B:352:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0549  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x0572 A[Catch: Exception -> 0x0569, all -> 0x0563, TRY_LEAVE, TryCatch #3 {all -> 0x0563, blocks: (B:116:0x01bb, B:118:0x01c1, B:120:0x01cd, B:122:0x01dc, B:124:0x01e8, B:127:0x01f2, B:129:0x01fd, B:131:0x0201, B:134:0x020b, B:136:0x020f, B:137:0x0215, B:139:0x021a, B:141:0x0221, B:144:0x022b, B:146:0x0236, B:148:0x023a, B:151:0x0244, B:154:0x024c, B:156:0x0251, B:159:0x025c, B:161:0x0264, B:163:0x026f, B:165:0x0273, B:168:0x027d, B:170:0x0281, B:171:0x0287, B:173:0x028c, B:175:0x0293, B:176:0x0298, B:178:0x02a0, B:180:0x02ab, B:182:0x02af, B:185:0x02b9, B:187:0x02bd, B:188:0x02c2, B:190:0x02c7, B:192:0x02ce, B:194:0x02d5, B:196:0x02d9, B:198:0x02e3, B:202:0x02f1, B:204:0x02f5, B:206:0x02fe, B:208:0x0309, B:210:0x030f, B:212:0x0313, B:215:0x0321, B:217:0x0325, B:219:0x0329, B:222:0x0337, B:224:0x033b, B:226:0x033f, B:229:0x034d, B:231:0x0351, B:233:0x0355, B:236:0x0367, B:238:0x036b, B:240:0x036f, B:243:0x0380, B:245:0x0384, B:247:0x0388, B:250:0x039a, B:252:0x039e, B:254:0x03a2, B:258:0x03b2, B:260:0x03b6, B:262:0x03ba, B:265:0x03c8, B:267:0x03d3, B:272:0x03e2, B:274:0x03e8, B:278:0x03f6, B:279:0x03f9, B:281:0x0402, B:283:0x040e, B:290:0x0424, B:297:0x0432, B:298:0x043d, B:301:0x0447, B:304:0x0451, B:307:0x045b, B:309:0x0463, B:310:0x0470, B:312:0x0479, B:314:0x0480, B:315:0x0486, B:317:0x048d, B:318:0x0493, B:319:0x0497, B:321:0x049c, B:322:0x04a0, B:323:0x04a4, B:325:0x04a8, B:327:0x04ac, B:330:0x04bb, B:331:0x04bf, B:332:0x04c2, B:334:0x04c6, B:336:0x04ca, B:339:0x04d4, B:341:0x04dc, B:342:0x04e4, B:344:0x04ea, B:356:0x0507, B:376:0x0554, B:378:0x055c, B:384:0x0572), top: B:436:0x01bb }] */
    /* JADX WARN: Removed duplicated region for block: B:386:0x057a  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x05be  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x05c2 A[Catch: all -> 0x0647, TryCatch #5 {all -> 0x0647, blocks: (B:405:0x059f, B:408:0x05c2, B:410:0x05d9, B:412:0x05dd, B:414:0x05e3, B:415:0x05fe, B:416:0x0616, B:418:0x061c, B:419:0x0634, B:421:0x0638, B:425:0x0641, B:426:0x0646), top: B:439:0x059f }] */
    /* JADX WARN: Removed duplicated region for block: B:409:0x05d7  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x061c A[Catch: all -> 0x0647, TryCatch #5 {all -> 0x0647, blocks: (B:405:0x059f, B:408:0x05c2, B:410:0x05d9, B:412:0x05dd, B:414:0x05e3, B:415:0x05fe, B:416:0x0616, B:418:0x061c, B:419:0x0634, B:421:0x0638, B:425:0x0641, B:426:0x0646), top: B:439:0x059f }] */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0638 A[Catch: all -> 0x0647, TryCatch #5 {all -> 0x0647, blocks: (B:405:0x059f, B:408:0x05c2, B:410:0x05d9, B:412:0x05dd, B:414:0x05e3, B:415:0x05fe, B:416:0x0616, B:418:0x061c, B:419:0x0634, B:421:0x0638, B:425:0x0641, B:426:0x0646), top: B:439:0x059f }] */
    /* JADX WARN: Removed duplicated region for block: B:423:0x063e  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x063f  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x00d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:439:0x059f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:441:0x0554 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r34, java.lang.Object r35, java.lang.Object r36, java.lang.reflect.Type r37, int r38, boolean r39) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1613
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int, boolean):void");
    }

    protected void writeClassName(JSONSerializer jSONSerializer, String str, Object obj) {
        if (str == null) {
            str = jSONSerializer.config.typeKey;
        }
        jSONSerializer.out.writeFieldName(str, false);
        String str2 = this.beanInfo.typeName;
        if (str2 == null) {
            Class<?> cls = obj.getClass();
            if (TypeUtils.isProxy(cls)) {
                cls = cls.getSuperclass();
            }
            str2 = cls.getName();
        }
        jSONSerializer.write(str2);
    }

    public boolean writeReference(JSONSerializer jSONSerializer, Object obj, int i) {
        SerialContext serialContext = jSONSerializer.context;
        int i2 = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (serialContext == null || (serialContext.features & i2) != 0 || (i & i2) != 0 || jSONSerializer.references == null || !jSONSerializer.references.containsKey(obj)) {
            return false;
        }
        jSONSerializer.writeReference(obj);
        return true;
    }

    protected boolean isWriteAsArray(JSONSerializer jSONSerializer) {
        return isWriteAsArray(jSONSerializer, 0);
    }

    protected boolean isWriteAsArray(JSONSerializer jSONSerializer, int i) {
        int i2 = SerializerFeature.BeanToArray.mask;
        return ((this.beanInfo.features & i2) == 0 && !jSONSerializer.out.beanToArray && (i & i2) == 0) ? false : true;
    }

    public Object getFieldValue(Object obj, String str) {
        FieldSerializer fieldSerializer = getFieldSerializer(str);
        if (fieldSerializer != null) {
            try {
                return fieldSerializer.getPropertyValue(obj);
            } catch (IllegalAccessException e) {
                throw new JSONException("getFieldValue error." + str, e);
            } catch (InvocationTargetException e2) {
                throw new JSONException("getFieldValue error." + str, e2);
            }
        } else {
            throw new JSONException("field not found. " + str);
        }
    }

    public Object getFieldValue(Object obj, String str, long j, boolean z) {
        FieldSerializer fieldSerializer = getFieldSerializer(j);
        if (fieldSerializer != null) {
            try {
                return fieldSerializer.getPropertyValue(obj);
            } catch (IllegalAccessException e) {
                throw new JSONException("getFieldValue error." + str, e);
            } catch (InvocationTargetException e2) {
                throw new JSONException("getFieldValue error." + str, e2);
            }
        } else if (!z) {
            return null;
        } else {
            throw new JSONException("field not found. " + str);
        }
    }

    public FieldSerializer getFieldSerializer(String str) {
        if (str == null) {
            return null;
        }
        int i = 0;
        int length = this.sortedGetters.length - 1;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            int compareTo = this.sortedGetters[i2].fieldInfo.name.compareTo(str);
            if (compareTo < 0) {
                i = i2 + 1;
            } else if (compareTo <= 0) {
                return this.sortedGetters[i2];
            } else {
                length = i2 - 1;
            }
        }
        return null;
    }

    public FieldSerializer getFieldSerializer(long j) {
        PropertyNamingStrategy[] propertyNamingStrategyArr;
        int binarySearch;
        if (this.hashArray == null) {
            propertyNamingStrategyArr = PropertyNamingStrategy.values();
            long[] jArr = new long[this.sortedGetters.length * propertyNamingStrategyArr.length];
            int i = 0;
            int i2 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr = this.sortedGetters;
                if (i >= fieldSerializerArr.length) {
                    break;
                }
                String str = fieldSerializerArr[i].fieldInfo.name;
                int i3 = i2 + 1;
                jArr[i2] = TypeUtils.fnv1a_64(str);
                for (PropertyNamingStrategy propertyNamingStrategy : propertyNamingStrategyArr) {
                    String translate = propertyNamingStrategy.translate(str);
                    if (!str.equals(translate)) {
                        i3++;
                        jArr[i3] = TypeUtils.fnv1a_64(translate);
                    }
                }
                i++;
                i2 = i3;
            }
            Arrays.sort(jArr, 0, i2);
            this.hashArray = new long[i2];
            System.arraycopy(jArr, 0, this.hashArray, 0, i2);
        } else {
            propertyNamingStrategyArr = null;
        }
        int binarySearch2 = Arrays.binarySearch(this.hashArray, j);
        if (binarySearch2 < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            if (propertyNamingStrategyArr == null) {
                propertyNamingStrategyArr = PropertyNamingStrategy.values();
            }
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            int i4 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                if (i4 >= fieldSerializerArr2.length) {
                    break;
                }
                String str2 = fieldSerializerArr2[i4].fieldInfo.name;
                int binarySearch3 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(str2));
                if (binarySearch3 >= 0) {
                    sArr[binarySearch3] = (short) i4;
                }
                for (PropertyNamingStrategy propertyNamingStrategy2 : propertyNamingStrategyArr) {
                    String translate2 = propertyNamingStrategy2.translate(str2);
                    if (!str2.equals(translate2) && (binarySearch = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(translate2))) >= 0) {
                        sArr[binarySearch] = (short) i4;
                    }
                }
                i4++;
            }
            this.hashArrayMapping = sArr;
        }
        short s = this.hashArrayMapping[binarySearch2];
        if (s != -1) {
            return this.sortedGetters[s];
        }
        return null;
    }

    public List<Object> getFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            arrayList.add(fieldSerializer.getPropertyValue(obj));
        }
        return arrayList;
    }

    public List<Object> getObjectFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        FieldSerializer[] fieldSerializerArr = this.sortedGetters;
        for (FieldSerializer fieldSerializer : fieldSerializerArr) {
            Class<?> cls = fieldSerializer.fieldInfo.fieldClass;
            if (!cls.isPrimitive() && !cls.getName().startsWith("java.lang.")) {
                arrayList.add(fieldSerializer.getPropertyValue(obj));
            }
        }
        return arrayList;
    }

    public int getSize(Object obj) throws Exception {
        int i = 0;
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                i++;
            }
        }
        return i;
    }

    public Set<String> getFieldNames(Object obj) throws Exception {
        HashSet hashSet = new HashSet();
        FieldSerializer[] fieldSerializerArr = this.sortedGetters;
        for (FieldSerializer fieldSerializer : fieldSerializerArr) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                hashSet.add(fieldSerializer.fieldInfo.name);
            }
        }
        return hashSet;
    }

    public Map<String, Object> getFieldValuesMap(Object obj) throws Exception {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.sortedGetters.length);
        FieldSerializer[] fieldSerializerArr = this.sortedGetters;
        for (FieldSerializer fieldSerializer : fieldSerializerArr) {
            boolean isEnabled = SerializerFeature.isEnabled(fieldSerializer.features, SerializerFeature.SkipTransientField);
            FieldInfo fieldInfo = fieldSerializer.fieldInfo;
            if (!isEnabled || fieldInfo == null || !fieldInfo.fieldTransient) {
                if (fieldSerializer.fieldInfo.unwrapped) {
                    Object json = JSON.toJSON(fieldSerializer.getPropertyValue(obj));
                    if (json instanceof Map) {
                        linkedHashMap.putAll((Map) json);
                    } else {
                        linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(obj));
                    }
                } else {
                    linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(obj));
                }
            }
        }
        return linkedHashMap;
    }

    protected BeanContext getBeanContext(int i) {
        return this.sortedGetters[i].fieldContext;
    }

    protected Type getFieldType(int i) {
        return this.sortedGetters[i].fieldInfo.fieldType;
    }

    protected char writeBefore(JSONSerializer jSONSerializer, Object obj, char c) {
        if (jSONSerializer.beforeFilters != null) {
            for (BeforeFilter beforeFilter : jSONSerializer.beforeFilters) {
                c = beforeFilter.writeBefore(jSONSerializer, obj, c);
            }
        }
        if (this.beforeFilters != null) {
            for (BeforeFilter beforeFilter2 : this.beforeFilters) {
                c = beforeFilter2.writeBefore(jSONSerializer, obj, c);
            }
        }
        return c;
    }

    protected char writeAfter(JSONSerializer jSONSerializer, Object obj, char c) {
        if (jSONSerializer.afterFilters != null) {
            for (AfterFilter afterFilter : jSONSerializer.afterFilters) {
                c = afterFilter.writeAfter(jSONSerializer, obj, c);
            }
        }
        if (this.afterFilters != null) {
            for (AfterFilter afterFilter2 : this.afterFilters) {
                c = afterFilter2.writeAfter(jSONSerializer, obj, c);
            }
        }
        return c;
    }

    protected boolean applyLabel(JSONSerializer jSONSerializer, String str) {
        if (jSONSerializer.labelFilters != null) {
            for (LabelFilter labelFilter : jSONSerializer.labelFilters) {
                if (!labelFilter.apply(str)) {
                    return false;
                }
            }
        }
        if (this.labelFilters == null) {
            return true;
        }
        for (LabelFilter labelFilter2 : this.labelFilters) {
            if (!labelFilter2.apply(str)) {
                return false;
            }
        }
        return true;
    }
}
