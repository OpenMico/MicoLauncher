package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private Map<String, FieldDeserializer> fieldDeserializerMap;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] hashArray;
    private transient short[] hashArrayMapping;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls) {
        this(parserConfig, cls, cls);
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type) {
        this(parserConfig, JavaBeanInfo.build(cls, type, parserConfig.propertyNamingStrategy, parserConfig.fieldBased, parserConfig.compatibleWithJavaBean, parserConfig.isJacksonCompatible()));
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) {
        this.clazz = javaBeanInfo.clazz;
        this.beanInfo = javaBeanInfo;
        this.sortedFieldDeserializers = new FieldDeserializer[javaBeanInfo.sortedFields.length];
        int length = javaBeanInfo.sortedFields.length;
        HashMap hashMap = null;
        int i = 0;
        while (i < length) {
            FieldInfo fieldInfo = javaBeanInfo.sortedFields[i];
            FieldDeserializer createFieldDeserializer = parserConfig.createFieldDeserializer(parserConfig, javaBeanInfo, fieldInfo);
            this.sortedFieldDeserializers[i] = createFieldDeserializer;
            if (length > 128) {
                if (this.fieldDeserializerMap == null) {
                    this.fieldDeserializerMap = new HashMap();
                }
                this.fieldDeserializerMap.put(fieldInfo.name, createFieldDeserializer);
            }
            String[] strArr = fieldInfo.alternateNames;
            HashMap hashMap2 = hashMap;
            for (String str : strArr) {
                if (hashMap2 == null) {
                    hashMap2 = new HashMap();
                }
                hashMap2.put(str, createFieldDeserializer);
            }
            i++;
            hashMap = hashMap2;
        }
        this.alterNameFieldDeserializers = hashMap;
        this.fieldDeserializers = new FieldDeserializer[javaBeanInfo.fields.length];
        int length2 = javaBeanInfo.fields.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(javaBeanInfo.fields[i2].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(String str) {
        return getFieldDeserializer(str, null);
    }

    public FieldDeserializer getFieldDeserializer(String str, int[] iArr) {
        FieldDeserializer fieldDeserializer;
        if (str == null) {
            return null;
        }
        Map<String, FieldDeserializer> map = this.fieldDeserializerMap;
        if (map != null && (fieldDeserializer = map.get(str)) != null) {
            return fieldDeserializer;
        }
        int i = 0;
        int length = this.sortedFieldDeserializers.length - 1;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            int compareTo = this.sortedFieldDeserializers[i2].fieldInfo.name.compareTo(str);
            if (compareTo < 0) {
                i = i2 + 1;
            } else if (compareTo > 0) {
                length = i2 - 1;
            } else if (isSetFlag(i2, iArr)) {
                return null;
            } else {
                return this.sortedFieldDeserializers[i2];
            }
        }
        Map<String, FieldDeserializer> map2 = this.alterNameFieldDeserializers;
        if (map2 != null) {
            return map2.get(str);
        }
        return null;
    }

    public FieldDeserializer getFieldDeserializer(long j) {
        int i = 0;
        if (this.hashArray == null) {
            long[] jArr = new long[this.sortedFieldDeserializers.length];
            int i2 = 0;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                if (i2 >= fieldDeserializerArr.length) {
                    break;
                }
                jArr[i2] = TypeUtils.fnv1a_64(fieldDeserializerArr[i2].fieldInfo.name);
                i2++;
            }
            Arrays.sort(jArr);
            this.hashArray = jArr;
        }
        int binarySearch = Arrays.binarySearch(this.hashArray, j);
        if (binarySearch < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            while (true) {
                FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                if (i >= fieldDeserializerArr2.length) {
                    break;
                }
                int binarySearch2 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(fieldDeserializerArr2[i].fieldInfo.name));
                if (binarySearch2 >= 0) {
                    sArr[binarySearch2] = (short) i;
                }
                i++;
            }
            this.hashArrayMapping = sArr;
        }
        short s = this.hashArrayMapping[binarySearch];
        if (s != -1) {
            return this.sortedFieldDeserializers[s];
        }
        return null;
    }

    static boolean isSetFlag(int i, int[] iArr) {
        if (iArr == null) {
            return false;
        }
        int i2 = i / 32;
        int i3 = i % 32;
        if (i2 < iArr.length) {
            if (((1 << i3) & iArr[i2]) != 0) {
                return true;
            }
        }
        return false;
    }

    public Object createInstance(DefaultJSONParser defaultJSONParser, Type type) {
        Object obj;
        ParseContext parseContext;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject());
        }
        if (this.beanInfo.defaultConstructor == null && this.beanInfo.factoryMethod == null) {
            return null;
        }
        if (this.beanInfo.factoryMethod != null && this.beanInfo.defaultConstructorParameterSize > 0) {
            return null;
        }
        try {
            Constructor<?> constructor = this.beanInfo.defaultConstructor;
            if (this.beanInfo.defaultConstructorParameterSize != 0) {
                ParseContext context = defaultJSONParser.getContext();
                if (context == null || context.object == null) {
                    throw new JSONException("can't create non-static inner class instance.");
                } else if (type instanceof Class) {
                    String name = ((Class) type).getName();
                    String substring = name.substring(0, name.lastIndexOf(36));
                    Object obj2 = context.object;
                    String name2 = obj2.getClass().getName();
                    if (!name2.equals(substring) && (parseContext = context.parent) != null && parseContext.object != null && ("java.util.ArrayList".equals(name2) || "java.util.List".equals(name2) || "java.util.Collection".equals(name2) || "java.util.Map".equals(name2) || "java.util.HashMap".equals(name2))) {
                        obj2 = parseContext.object.getClass().getName().equals(substring) ? parseContext.object : null;
                    }
                    if (obj2 == null || ((obj2 instanceof Collection) && ((Collection) obj2).isEmpty())) {
                        throw new JSONException("can't create non-static inner class instance.");
                    }
                    obj = constructor.newInstance(obj2);
                } else {
                    throw new JSONException("can't create non-static inner class instance.");
                }
            } else if (constructor != null) {
                obj = constructor.newInstance(new Object[0]);
            } else {
                obj = this.beanInfo.factoryMethod.invoke(null, new Object[0]);
            }
            if (defaultJSONParser != null && defaultJSONParser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                FieldInfo[] fieldInfoArr = this.beanInfo.fields;
                for (FieldInfo fieldInfo : fieldInfoArr) {
                    if (fieldInfo.fieldClass == String.class) {
                        try {
                            fieldInfo.set(obj, "");
                        } catch (Exception e) {
                            throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                        }
                    }
                }
            }
            return obj;
        } catch (JSONException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e3);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, 0);
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, int i) {
        return (T) deserialze(defaultJSONParser, type, obj, null, i, null);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        Enum<?> r1;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 14) {
            String scanTypeName = jSONLexer.scanTypeName(defaultJSONParser.symbolTable);
            if (scanTypeName != null) {
                ObjectDeserializer seeAlso = getSeeAlso(defaultJSONParser.getConfig(), this.beanInfo, scanTypeName);
                if (seeAlso == null) {
                    seeAlso = defaultJSONParser.getConfig().getDeserializer(defaultJSONParser.getConfig().checkAutoType(scanTypeName, TypeUtils.getClass(type), jSONLexer.getFeatures()));
                }
                if (seeAlso instanceof JavaBeanDeserializer) {
                    return (T) ((JavaBeanDeserializer) seeAlso).deserialzeArrayMapping(defaultJSONParser, type, obj, obj2);
                }
            }
            T t = (T) createInstance(defaultJSONParser, type);
            int i = 0;
            int length = this.sortedFieldDeserializers.length;
            while (true) {
                int i2 = 16;
                if (i >= length) {
                    break;
                }
                char c = i == length + (-1) ? ']' : StringUtil.COMMA;
                FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
                Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
                if (cls == Integer.TYPE) {
                    fieldDeserializer.setValue((Object) t, jSONLexer.scanInt(c));
                } else if (cls == String.class) {
                    fieldDeserializer.setValue((Object) t, jSONLexer.scanString(c));
                } else if (cls == Long.TYPE) {
                    fieldDeserializer.setValue(t, jSONLexer.scanLong(c));
                } else if (cls.isEnum()) {
                    char current = jSONLexer.getCurrent();
                    if (current == '\"' || current == 'n') {
                        r1 = jSONLexer.scanEnum(cls, defaultJSONParser.getSymbolTable(), c);
                    } else if (current < '0' || current > '9') {
                        r1 = scanEnum(jSONLexer, c);
                    } else {
                        r1 = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(jSONLexer.scanInt(c));
                    }
                    fieldDeserializer.setValue(t, r1);
                } else if (cls == Boolean.TYPE) {
                    fieldDeserializer.setValue(t, jSONLexer.scanBoolean(c));
                } else if (cls == Float.TYPE) {
                    fieldDeserializer.setValue(t, Float.valueOf(jSONLexer.scanFloat(c)));
                } else if (cls == Double.TYPE) {
                    fieldDeserializer.setValue(t, Double.valueOf(jSONLexer.scanDouble(c)));
                } else if (cls == Date.class && jSONLexer.getCurrent() == '1') {
                    fieldDeserializer.setValue(t, new Date(jSONLexer.scanLong(c)));
                } else if (cls == BigDecimal.class) {
                    fieldDeserializer.setValue(t, jSONLexer.scanDecimal(c));
                } else {
                    jSONLexer.nextToken(14);
                    fieldDeserializer.setValue(t, defaultJSONParser.parseObject(fieldDeserializer.fieldInfo.fieldType, fieldDeserializer.fieldInfo.name));
                    if (jSONLexer.token() == 15) {
                        break;
                    }
                    if (c == ']') {
                        i2 = 15;
                    }
                    check(jSONLexer, i2);
                }
                i++;
            }
            jSONLexer.nextToken(16);
            return t;
        }
        throw new JSONException("error");
    }

    protected void check(JSONLexer jSONLexer, int i) {
        if (jSONLexer.token() != i) {
            throw new JSONException("syntax error");
        }
    }

    protected Enum<?> scanEnum(JSONLexer jSONLexer, char c) {
        throw new JSONException("illegal enum. " + jSONLexer.info());
    }

    /* JADX WARN: Code restructure failed: missing block: B:388:0x068e, code lost:
        r2 = getSeeAlso(r13, r31.beanInfo, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x0694, code lost:
        if (r2 != null) goto L_0x06ab;
     */
    /* JADX WARN: Code restructure failed: missing block: B:390:0x0696, code lost:
        r15 = r13.checkAutoType(r1, com.alibaba.fastjson.util.TypeUtils.getClass(r33), r12.getFeatures());
        r2 = r32.getConfig().getDeserializer(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:391:0x06ab, code lost:
        r15 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:392:0x06ac, code lost:
        r3 = (T) r2.deserialze(r32, r15, r34);
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x06b2, code lost:
        if ((r2 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L_0x06c1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:394:0x06b4, code lost:
        r2 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x06b6, code lost:
        if (r6 == null) goto L_0x06c1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x06b8, code lost:
        r2 = r2.getFieldDeserializer(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:397:0x06bc, code lost:
        if (r2 == null) goto L_0x06c1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:398:0x06be, code lost:
        r2.setValue((java.lang.Object) r3, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x06c1, code lost:
        if (r5 == null) goto L_0x06c7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x06c3, code lost:
        r5.object = r37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x06c7, code lost:
        r32.setContext(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x06ca, code lost:
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x06d8, code lost:
        r15 = r5;
        r30 = r7;
        r2 = r20;
        r35 = 0;
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:477:0x0826, code lost:
        r12.nextToken();
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:488:0x085c, code lost:
        r12.nextToken(16);
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:672:0x0b2c, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r12.token()));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0417  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x041b A[Catch: all -> 0x0574, TryCatch #16 {all -> 0x0574, blocks: (B:133:0x01e5, B:140:0x01f3, B:143:0x0202, B:145:0x0206, B:149:0x022a, B:151:0x022e, B:154:0x023d, B:156:0x0241, B:160:0x0265, B:163:0x0274, B:165:0x0278, B:169:0x029c, B:172:0x02ab, B:174:0x02af, B:176:0x02cf, B:181:0x02d9, B:186:0x02e3, B:191:0x02ed, B:193:0x02f3, B:196:0x0301, B:198:0x0309, B:200:0x030d, B:203:0x031d, B:205:0x0322, B:211:0x0349, B:214:0x0357, B:216:0x035c, B:220:0x037f, B:223:0x038d, B:225:0x0392, B:229:0x03b5, B:232:0x03c3, B:234:0x03c8, B:236:0x03e7, B:240:0x03fc, B:242:0x0404, B:246:0x040f, B:247:0x0413, B:250:0x041b, B:252:0x0420, B:254:0x043d, B:256:0x0447, B:259:0x044e, B:260:0x0452, B:263:0x045a, B:265:0x045f, B:267:0x047c, B:270:0x0489, B:271:0x048d, B:274:0x0495, B:276:0x049a, B:278:0x04b7, B:280:0x04c1, B:283:0x04c8, B:284:0x04cc, B:287:0x04d4, B:289:0x04d9, B:291:0x04f5, B:293:0x04fd, B:296:0x0504, B:297:0x0508, B:300:0x050f, B:314:0x054f, B:316:0x0559, B:319:0x0566, B:328:0x0581, B:330:0x058a, B:332:0x0596, B:333:0x059a, B:335:0x05a2, B:337:0x05a8, B:338:0x05ac, B:339:0x05ba, B:342:0x05c3, B:344:0x05c7, B:345:0x05ca, B:347:0x05ce, B:348:0x05d1, B:349:0x05df, B:351:0x05e7, B:352:0x05ed, B:354:0x05f3, B:356:0x05f9, B:357:0x05ff, B:358:0x0605, B:359:0x0609, B:362:0x0611, B:374:0x0641, B:375:0x065b, B:377:0x065e, B:390:0x0696, B:394:0x06b4, B:396:0x06b8, B:398:0x06be), top: B:720:0x01e5 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0073 A[Catch: all -> 0x004b, TRY_LEAVE, TryCatch #7 {all -> 0x004b, blocks: (B:17:0x003b, B:19:0x0040, B:27:0x0056, B:29:0x0061, B:31:0x0069, B:37:0x0073, B:43:0x0082, B:48:0x008e, B:50:0x0098, B:53:0x009f, B:55:0x00a5, B:57:0x00b1, B:59:0x00bb, B:64:0x00c8, B:66:0x00d0, B:69:0x00da, B:71:0x00e0, B:74:0x00e8, B:78:0x00f8, B:81:0x010b, B:85:0x0114, B:90:0x0125, B:91:0x012e, B:92:0x012f, B:94:0x0150, B:95:0x0158, B:96:0x016b, B:101:0x0172), top: B:704:0x0039, inners: #20 }] */
    /* JADX WARN: Removed duplicated region for block: B:414:0x070b  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x0768  */
    /* JADX WARN: Removed duplicated region for block: B:464:0x07e1  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x084e  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x0856 A[Catch: all -> 0x0b39, TryCatch #19 {all -> 0x0b39, blocks: (B:473:0x081a, B:481:0x083e, B:483:0x0846, B:486:0x0856, B:488:0x085c), top: B:724:0x081a }] */
    /* JADX WARN: Removed duplicated region for block: B:625:0x0a2d A[Catch: all -> 0x0ae3, TRY_ENTER, TryCatch #2 {all -> 0x0ae3, blocks: (B:495:0x0871, B:504:0x0887, B:506:0x088d, B:507:0x0892, B:509:0x0895, B:511:0x089d, B:513:0x08ad, B:514:0x08b2, B:516:0x08b6, B:517:0x08bb, B:519:0x08bf, B:520:0x08c4, B:522:0x08c8, B:523:0x08cd, B:525:0x08d1, B:526:0x08d6, B:528:0x08da, B:529:0x08df, B:531:0x08e3, B:534:0x08ea, B:538:0x08f8, B:540:0x08fe, B:542:0x0905, B:544:0x090f, B:546:0x0917, B:548:0x091b, B:550:0x0925, B:552:0x0931, B:558:0x0944, B:559:0x094c, B:561:0x0958, B:563:0x0962, B:565:0x0968, B:566:0x096e, B:568:0x0972, B:569:0x0978, B:571:0x097c, B:572:0x0982, B:574:0x0986, B:575:0x098b, B:577:0x098f, B:578:0x0994, B:580:0x0998, B:581:0x099d, B:583:0x09a1, B:586:0x09a8, B:589:0x09b3, B:591:0x09ba, B:593:0x09c0, B:596:0x09c7, B:598:0x09ca, B:600:0x09ce, B:602:0x09d4, B:604:0x09db, B:610:0x09f1, B:612:0x09f7, B:613:0x0a03, B:615:0x0a06, B:617:0x0a0a, B:619:0x0a10, B:621:0x0a17, B:622:0x0a20, B:623:0x0a23, B:625:0x0a2d, B:626:0x0a35, B:628:0x0a3b, B:630:0x0a4d, B:637:0x0a84, B:639:0x0a8a, B:642:0x0a96, B:643:0x0ab4, B:645:0x0ab7, B:647:0x0abc, B:654:0x0acb, B:661:0x0adb, B:662:0x0ae2), top: B:696:0x0865, inners: #8, #18 }] */
    /* JADX WARN: Removed duplicated region for block: B:686:0x0b51  */
    /* JADX WARN: Removed duplicated region for block: B:699:0x0545 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:703:0x09f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r32, java.lang.reflect.Type r33, java.lang.Object r34, java.lang.Object r35, int r36, int[] r37) {
        /*
            Method dump skipped, instructions count: 2908
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object, int, int[]):java.lang.Object");
    }

    protected Enum scanEnum(JSONLexerBase jSONLexerBase, char[] cArr, ObjectDeserializer objectDeserializer) {
        EnumDeserializer enumDeserializer = objectDeserializer instanceof EnumDeserializer ? (EnumDeserializer) objectDeserializer : null;
        if (enumDeserializer == null) {
            jSONLexerBase.matchStat = -1;
            return null;
        }
        long scanEnumSymbol = jSONLexerBase.scanEnumSymbol(cArr);
        if (jSONLexerBase.matchStat <= 0) {
            return null;
        }
        Enum enumByHashCode = enumDeserializer.getEnumByHashCode(scanEnumSymbol);
        if (enumByHashCode == null) {
            if (scanEnumSymbol == -3750763034362895579L) {
                return null;
            }
            if (jSONLexerBase.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                throw new JSONException("not match enum value, " + enumDeserializer.enumClass);
            }
        }
        return enumByHashCode;
    }

    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map) {
        return parseField(defaultJSONParser, str, obj, type, map, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0123  */
    /* JADX WARN: Type inference failed for: r17v0, types: [int, boolean] */
    /* JADX WARN: Type inference failed for: r17v22 */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r17v6 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean parseField(com.alibaba.fastjson.parser.DefaultJSONParser r22, java.lang.String r23, java.lang.Object r24, java.lang.reflect.Type r25, java.util.Map<java.lang.String, java.lang.Object> r26, int[] r27) {
        /*
            Method dump skipped, instructions count: 600
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.parseField(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.String, java.lang.Object, java.lang.reflect.Type, java.util.Map, int[]):boolean");
    }

    public FieldDeserializer smartMatch(String str) {
        return smartMatch(str, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.alibaba.fastjson.parser.deserializer.FieldDeserializer smartMatch(java.lang.String r11, int[] r12) {
        /*
            r10 = this;
            r0 = 0
            if (r11 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r1 = r10.getFieldDeserializer(r11, r12)
            if (r1 != 0) goto L_0x00af
            long r2 = com.alibaba.fastjson.util.TypeUtils.fnv1a_64_lower(r11)
            long[] r4 = r10.smartMatchHashArray
            r5 = 0
            if (r4 != 0) goto L_0x0032
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r4 = r10.sortedFieldDeserializers
            int r4 = r4.length
            long[] r4 = new long[r4]
            r6 = r5
        L_0x0019:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r7 = r10.sortedFieldDeserializers
            int r8 = r7.length
            if (r6 >= r8) goto L_0x002d
            r7 = r7[r6]
            com.alibaba.fastjson.util.FieldInfo r7 = r7.fieldInfo
            java.lang.String r7 = r7.name
            long r7 = com.alibaba.fastjson.util.TypeUtils.fnv1a_64_lower(r7)
            r4[r6] = r7
            int r6 = r6 + 1
            goto L_0x0019
        L_0x002d:
            java.util.Arrays.sort(r4)
            r10.smartMatchHashArray = r4
        L_0x0032:
            long[] r4 = r10.smartMatchHashArray
            int r2 = java.util.Arrays.binarySearch(r4, r2)
            if (r2 >= 0) goto L_0x0052
            java.lang.String r3 = "is"
            boolean r3 = r11.startsWith(r3)
            if (r3 == 0) goto L_0x0053
            r2 = 2
            java.lang.String r11 = r11.substring(r2)
            long r6 = com.alibaba.fastjson.util.TypeUtils.fnv1a_64_lower(r11)
            long[] r11 = r10.smartMatchHashArray
            int r2 = java.util.Arrays.binarySearch(r11, r6)
            goto L_0x0053
        L_0x0052:
            r3 = r5
        L_0x0053:
            if (r2 < 0) goto L_0x0092
            short[] r11 = r10.smartMatchHashArrayMapping
            r4 = -1
            if (r11 != 0) goto L_0x0081
            long[] r11 = r10.smartMatchHashArray
            int r11 = r11.length
            short[] r11 = new short[r11]
            java.util.Arrays.fill(r11, r4)
        L_0x0062:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r6 = r10.sortedFieldDeserializers
            int r7 = r6.length
            if (r5 >= r7) goto L_0x007f
            long[] r7 = r10.smartMatchHashArray
            r6 = r6[r5]
            com.alibaba.fastjson.util.FieldInfo r6 = r6.fieldInfo
            java.lang.String r6 = r6.name
            long r8 = com.alibaba.fastjson.util.TypeUtils.fnv1a_64_lower(r6)
            int r6 = java.util.Arrays.binarySearch(r7, r8)
            if (r6 < 0) goto L_0x007c
            short r7 = (short) r5
            r11[r6] = r7
        L_0x007c:
            int r5 = r5 + 1
            goto L_0x0062
        L_0x007f:
            r10.smartMatchHashArrayMapping = r11
        L_0x0081:
            short[] r11 = r10.smartMatchHashArrayMapping
            short r11 = r11[r2]
            if (r11 == r4) goto L_0x0092
            boolean r12 = isSetFlag(r11, r12)
            if (r12 != 0) goto L_0x0092
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r12 = r10.sortedFieldDeserializers
            r11 = r12[r11]
            goto L_0x0093
        L_0x0092:
            r11 = r1
        L_0x0093:
            if (r11 == 0) goto L_0x00b0
            com.alibaba.fastjson.util.FieldInfo r12 = r11.fieldInfo
            int r1 = r12.parserFeatures
            com.alibaba.fastjson.parser.Feature r2 = com.alibaba.fastjson.parser.Feature.DisableFieldSmartMatch
            int r2 = r2.mask
            r1 = r1 & r2
            if (r1 == 0) goto L_0x00a1
            return r0
        L_0x00a1:
            java.lang.Class<?> r12 = r12.fieldClass
            if (r3 == 0) goto L_0x00b0
            java.lang.Class r1 = java.lang.Boolean.TYPE
            if (r12 == r1) goto L_0x00b0
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            if (r12 == r1) goto L_0x00b0
            r11 = r0
            goto L_0x00b0
        L_0x00af:
            r11 = r1
        L_0x00b0:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.smartMatch(java.lang.String, int[]):com.alibaba.fastjson.parser.deserializer.FieldDeserializer");
    }

    private Object createFactoryInstance(ParserConfig parserConfig, Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return this.beanInfo.factoryMethod.invoke(null, obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x0249, code lost:
        if (r12.beanInfo.fields[r13].fieldClass == java.lang.String.class) goto L_0x0250;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006f, code lost:
        r9 = r7.getType();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0075, code lost:
        if (r9 != java.lang.Boolean.TYPE) goto L_0x0087;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0079, code lost:
        if (r4 != java.lang.Boolean.FALSE) goto L_0x007f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007b, code lost:
        r7.setBoolean(r0, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0081, code lost:
        if (r4 != java.lang.Boolean.TRUE) goto L_0x0115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0083, code lost:
        r7.setBoolean(r0, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0089, code lost:
        if (r9 != java.lang.Integer.TYPE) goto L_0x0099;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008d, code lost:
        if ((r4 instanceof java.lang.Number) == false) goto L_0x0115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008f, code lost:
        r7.setInt(r0, ((java.lang.Number) r4).intValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009b, code lost:
        if (r9 != java.lang.Long.TYPE) goto L_0x00ac;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
        if ((r4 instanceof java.lang.Number) == false) goto L_0x0115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a1, code lost:
        r7.setLong(r0, ((java.lang.Number) r4).longValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b0, code lost:
        if (r9 != java.lang.Float.TYPE) goto L_0x00db;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00b4, code lost:
        if ((r4 instanceof java.lang.Number) == false) goto L_0x00c1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b6, code lost:
        r7.setFloat(r0, ((java.lang.Number) r4).floatValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00c3, code lost:
        if ((r4 instanceof java.lang.String) == false) goto L_0x0115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00c5, code lost:
        r4 = (java.lang.String) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00cb, code lost:
        if (r4.length() > 10) goto L_0x00d2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00cd, code lost:
        r4 = com.alibaba.fastjson.util.TypeUtils.parseFloat(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00d2, code lost:
        r4 = java.lang.Float.parseFloat(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00d6, code lost:
        r7.setFloat(r0, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00dd, code lost:
        if (r9 != java.lang.Double.TYPE) goto L_0x0108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00e1, code lost:
        if ((r4 instanceof java.lang.Number) == false) goto L_0x00ee;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00e3, code lost:
        r7.setDouble(r0, ((java.lang.Number) r4).doubleValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00f0, code lost:
        if ((r4 instanceof java.lang.String) == false) goto L_0x0115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00f2, code lost:
        r4 = (java.lang.String) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00f8, code lost:
        if (r4.length() > 10) goto L_0x00ff;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00fa, code lost:
        r4 = com.alibaba.fastjson.util.TypeUtils.parseDouble(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00ff, code lost:
        r4 = java.lang.Double.parseDouble(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0103, code lost:
        r7.setDouble(r0, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0108, code lost:
        if (r4 == null) goto L_0x0115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x010e, code lost:
        if (r8 != r4.getClass()) goto L_0x0115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0110, code lost:
        r7.set(r0, r4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object createInstance(java.util.Map<java.lang.String, java.lang.Object> r13, com.alibaba.fastjson.parser.ParserConfig r14) throws java.lang.IllegalArgumentException, java.lang.IllegalAccessException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 767
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.createInstance(java.util.Map, com.alibaba.fastjson.parser.ParserConfig):java.lang.Object");
    }

    public Type getFieldType(int i) {
        return this.sortedFieldDeserializers[i].fieldInfo.fieldType;
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i) {
        return parseRest(defaultJSONParser, type, obj, obj2, i, new int[0]);
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i, int[] iArr) {
        return deserialze(defaultJSONParser, type, obj, obj2, i, iArr);
    }

    protected static JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, String str) {
        if (javaBeanInfo.jsonType == null) {
            return null;
        }
        for (Class<?> cls : javaBeanInfo.jsonType.seeAlso()) {
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                JavaBeanInfo javaBeanInfo2 = javaBeanDeserializer.beanInfo;
                if (javaBeanInfo2.typeName.equals(str)) {
                    return javaBeanDeserializer;
                }
                JavaBeanDeserializer seeAlso = getSeeAlso(parserConfig, javaBeanInfo2, str);
                if (seeAlso != null) {
                    return seeAlso;
                }
            }
        }
        return null;
    }

    protected static void parseArray(Collection collection, ObjectDeserializer objectDeserializer, DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
        int i = jSONLexerBase.token();
        if (i == 8) {
            jSONLexerBase.nextToken(16);
            jSONLexerBase.token();
            return;
        }
        if (i != 14) {
            defaultJSONParser.throwException(i);
        }
        if (jSONLexerBase.getCurrent() == '[') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(14);
        } else {
            jSONLexerBase.nextToken(14);
        }
        if (jSONLexerBase.token() == 15) {
            jSONLexerBase.nextToken();
            return;
        }
        int i2 = 0;
        while (true) {
            collection.add(objectDeserializer.deserialze(defaultJSONParser, type, Integer.valueOf(i2)));
            i2++;
            if (jSONLexerBase.token() != 16) {
                break;
            } else if (jSONLexerBase.getCurrent() == '[') {
                jSONLexerBase.next();
                jSONLexerBase.setToken(14);
            } else {
                jSONLexerBase.nextToken(14);
            }
        }
        int i3 = jSONLexerBase.token();
        if (i3 != 15) {
            defaultJSONParser.throwException(i3);
        }
        if (jSONLexerBase.getCurrent() == ',') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(16);
            return;
        }
        jSONLexerBase.nextToken(16);
    }
}
