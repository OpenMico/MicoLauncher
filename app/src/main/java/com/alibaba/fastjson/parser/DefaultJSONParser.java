package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses = new HashSet();
    private String[] autoTypeAccept;
    private boolean autoTypeEnable;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    protected transient BeanContext lastBeanContext;
    public final JSONLexer lexer;
    private int objectKeyLevel;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    static {
        for (Class<?> cls : new Class[]{Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class}) {
            primitiveClasses.add(cls);
        }
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DefaultJSONParser(String str) {
        this(str, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig) {
        this(str, new JSONScanner(str, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig, int i) {
        this(str, new JSONScanner(str, i), parserConfig);
    }

    public DefaultJSONParser(char[] cArr, int i, ParserConfig parserConfig, int i2) {
        this(cArr, new JSONScanner(cArr, i, i2), parserConfig);
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this((Object) null, jSONLexer, parserConfig);
    }

    public DefaultJSONParser(Object obj, JSONLexer jSONLexer, ParserConfig parserConfig) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.objectKeyLevel = 0;
        this.autoTypeAccept = null;
        this.lexer = jSONLexer;
        this.input = obj;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char current = jSONLexer.getCurrent();
        if (current == '{') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 12;
        } else if (current == '[') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 14;
        } else {
            jSONLexer.nextToken();
        }
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public String getInput() {
        Object obj = this.input;
        if (obj instanceof char[]) {
            return new String((char[]) obj);
        }
        return obj.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:135:0x0295, code lost:
        r3.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x02a0, code lost:
        if (r3.token() != 13) goto L_0x02f3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x02a2, code lost:
        r3.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x02ad, code lost:
        if ((r18.config.getDeserializer(r6) instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L_0x02b6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x02af, code lost:
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r19, (java.lang.Class<java.lang.Object>) r6, r18.config);
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x02b7, code lost:
        if (r0 != null) goto L_0x02e6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x02bb, code lost:
        if (r6 != java.lang.Cloneable.class) goto L_0x02c3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x02bd, code lost:
        r0 = new java.util.HashMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02c9, code lost:
        if ("java.util.Collections$EmptyMap".equals(r5) == false) goto L_0x02d0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02cb, code lost:
        r0 = java.util.Collections.emptyMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02d6, code lost:
        if ("java.util.Collections$UnmodifiableMap".equals(r5) == false) goto L_0x02e2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02d8, code lost:
        r0 = java.util.Collections.unmodifiableMap(new java.util.HashMap());
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02e2, code lost:
        r0 = r6.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02e6, code lost:
        setContext(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02e9, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02ea, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02f2, code lost:
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02f3, code lost:
        setResolveStatus(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02f9, code lost:
        if (r18.context == null) goto L_0x030c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02fb, code lost:
        if (r20 == null) goto L_0x030c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02ff, code lost:
        if ((r20 instanceof java.lang.Integer) != false) goto L_0x030c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0307, code lost:
        if ((r18.context.fieldName instanceof java.lang.Integer) != false) goto L_0x030c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0309, code lost:
        popContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0310, code lost:
        if (r19.size() <= 0) goto L_0x0323;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0312, code lost:
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r19, (java.lang.Class<java.lang.Object>) r6, r18.config);
        setResolveStatus(0);
        parseObject(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x031f, code lost:
        setContext(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0322, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0323, code lost:
        r0 = r18.config.getDeserializer(r6);
        r3 = r0.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0333, code lost:
        if (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class.isAssignableFrom(r3) == false) goto L_0x0342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0337, code lost:
        if (r3 == com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class) goto L_0x0342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x033b, code lost:
        if (r3 == com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class) goto L_0x0342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x033d, code lost:
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0344, code lost:
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer) == false) goto L_0x034a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0346, code lost:
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x034a, code lost:
        r0 = r0.deserialze(r18, r6, r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x034e, code lost:
        setContext(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0351, code lost:
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:235:0x040f A[Catch: all -> 0x06ab, TRY_LEAVE, TryCatch #2 {all -> 0x06ab, blocks: (B:28:0x007f, B:31:0x0092, B:34:0x00aa, B:37:0x00bc, B:38:0x00de, B:40:0x00e1, B:42:0x00ec, B:44:0x00f0, B:46:0x00f6, B:48:0x00fc, B:49:0x00ff, B:56:0x010e, B:58:0x0116, B:61:0x0128, B:62:0x0142, B:63:0x0143, B:64:0x014a, B:72:0x0159, B:73:0x015f, B:75:0x0166, B:76:0x016b, B:77:0x016f, B:82:0x017c, B:84:0x0181, B:87:0x018a, B:88:0x01a4, B:89:0x01a5, B:90:0x01bf, B:96:0x01c9, B:98:0x01d1, B:101:0x01e2, B:102:0x0204, B:103:0x0205, B:104:0x020c, B:105:0x020d, B:107:0x0217, B:109:0x0221, B:110:0x0227, B:112:0x0232, B:114:0x023a, B:118:0x0251, B:120:0x025f, B:122:0x0266, B:124:0x026c, B:128:0x0275, B:131:0x027b, B:134:0x028b, B:135:0x0295, B:137:0x02a2, B:138:0x02a5, B:140:0x02af, B:145:0x02bd, B:146:0x02c3, B:148:0x02cb, B:149:0x02d0, B:151:0x02d8, B:152:0x02e2, B:156:0x02eb, B:157:0x02f2, B:158:0x02f3, B:161:0x02fd, B:163:0x0301, B:165:0x0309, B:166:0x030c, B:168:0x0312, B:171:0x0323, B:177:0x033d, B:178:0x0342, B:180:0x0346, B:181:0x034a, B:190:0x035f, B:194:0x0369, B:196:0x0371, B:198:0x037b, B:200:0x038c, B:202:0x0397, B:204:0x039f, B:206:0x03a3, B:208:0x03ab, B:211:0x03b0, B:213:0x03b4, B:215:0x03bb, B:217:0x03c3, B:219:0x03c7, B:220:0x03ca, B:221:0x03d6, B:224:0x03df, B:226:0x03e3, B:227:0x03e6, B:229:0x03ea, B:230:0x03ee, B:231:0x03fb, B:233:0x0407, B:235:0x040f, B:238:0x0418, B:239:0x0432, B:240:0x0433, B:241:0x0451, B:245:0x0457, B:247:0x045b, B:249:0x0461, B:251:0x0467, B:252:0x046b, B:257:0x0475, B:263:0x0488, B:265:0x0497, B:267:0x04a2, B:268:0x04aa, B:269:0x04ad, B:274:0x04b9, B:276:0x04c3, B:277:0x04c8, B:278:0x04d2, B:279:0x04d5, B:281:0x04de, B:283:0x04e9, B:286:0x04f9, B:287:0x051b, B:290:0x0520, B:292:0x052a, B:294:0x0532, B:295:0x0535, B:297:0x0540, B:298:0x0544, B:300:0x054f, B:303:0x0556, B:306:0x0563, B:307:0x056a, B:310:0x056f, B:312:0x0574, B:316:0x0580, B:318:0x0588, B:320:0x059d, B:321:0x05a8, B:322:0x05af, B:324:0x05bc, B:326:0x05c2, B:329:0x05c8, B:331:0x05ce, B:333:0x05d6, B:336:0x05e8, B:339:0x05f0, B:341:0x05f4, B:342:0x05fb, B:344:0x0600, B:345:0x0603, B:347:0x060b, B:350:0x0615, B:353:0x061f, B:354:0x0627, B:355:0x062f, B:356:0x0649, B:357:0x064a, B:359:0x065c, B:362:0x0663, B:365:0x0670, B:366:0x0692, B:367:0x0693, B:368:0x069a, B:369:0x069b, B:370:0x06a2, B:371:0x06a3, B:372:0x06aa), top: B:381:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0418 A[Catch: all -> 0x06ab, TRY_ENTER, TryCatch #2 {all -> 0x06ab, blocks: (B:28:0x007f, B:31:0x0092, B:34:0x00aa, B:37:0x00bc, B:38:0x00de, B:40:0x00e1, B:42:0x00ec, B:44:0x00f0, B:46:0x00f6, B:48:0x00fc, B:49:0x00ff, B:56:0x010e, B:58:0x0116, B:61:0x0128, B:62:0x0142, B:63:0x0143, B:64:0x014a, B:72:0x0159, B:73:0x015f, B:75:0x0166, B:76:0x016b, B:77:0x016f, B:82:0x017c, B:84:0x0181, B:87:0x018a, B:88:0x01a4, B:89:0x01a5, B:90:0x01bf, B:96:0x01c9, B:98:0x01d1, B:101:0x01e2, B:102:0x0204, B:103:0x0205, B:104:0x020c, B:105:0x020d, B:107:0x0217, B:109:0x0221, B:110:0x0227, B:112:0x0232, B:114:0x023a, B:118:0x0251, B:120:0x025f, B:122:0x0266, B:124:0x026c, B:128:0x0275, B:131:0x027b, B:134:0x028b, B:135:0x0295, B:137:0x02a2, B:138:0x02a5, B:140:0x02af, B:145:0x02bd, B:146:0x02c3, B:148:0x02cb, B:149:0x02d0, B:151:0x02d8, B:152:0x02e2, B:156:0x02eb, B:157:0x02f2, B:158:0x02f3, B:161:0x02fd, B:163:0x0301, B:165:0x0309, B:166:0x030c, B:168:0x0312, B:171:0x0323, B:177:0x033d, B:178:0x0342, B:180:0x0346, B:181:0x034a, B:190:0x035f, B:194:0x0369, B:196:0x0371, B:198:0x037b, B:200:0x038c, B:202:0x0397, B:204:0x039f, B:206:0x03a3, B:208:0x03ab, B:211:0x03b0, B:213:0x03b4, B:215:0x03bb, B:217:0x03c3, B:219:0x03c7, B:220:0x03ca, B:221:0x03d6, B:224:0x03df, B:226:0x03e3, B:227:0x03e6, B:229:0x03ea, B:230:0x03ee, B:231:0x03fb, B:233:0x0407, B:235:0x040f, B:238:0x0418, B:239:0x0432, B:240:0x0433, B:241:0x0451, B:245:0x0457, B:247:0x045b, B:249:0x0461, B:251:0x0467, B:252:0x046b, B:257:0x0475, B:263:0x0488, B:265:0x0497, B:267:0x04a2, B:268:0x04aa, B:269:0x04ad, B:274:0x04b9, B:276:0x04c3, B:277:0x04c8, B:278:0x04d2, B:279:0x04d5, B:281:0x04de, B:283:0x04e9, B:286:0x04f9, B:287:0x051b, B:290:0x0520, B:292:0x052a, B:294:0x0532, B:295:0x0535, B:297:0x0540, B:298:0x0544, B:300:0x054f, B:303:0x0556, B:306:0x0563, B:307:0x056a, B:310:0x056f, B:312:0x0574, B:316:0x0580, B:318:0x0588, B:320:0x059d, B:321:0x05a8, B:322:0x05af, B:324:0x05bc, B:326:0x05c2, B:329:0x05c8, B:331:0x05ce, B:333:0x05d6, B:336:0x05e8, B:339:0x05f0, B:341:0x05f4, B:342:0x05fb, B:344:0x0600, B:345:0x0603, B:347:0x060b, B:350:0x0615, B:353:0x061f, B:354:0x0627, B:355:0x062f, B:356:0x0649, B:357:0x064a, B:359:0x065c, B:362:0x0663, B:365:0x0670, B:366:0x0692, B:367:0x0693, B:368:0x069a, B:369:0x069b, B:370:0x06a2, B:371:0x06a3, B:372:0x06aa), top: B:381:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:336:0x05e8 A[Catch: all -> 0x06ab, TryCatch #2 {all -> 0x06ab, blocks: (B:28:0x007f, B:31:0x0092, B:34:0x00aa, B:37:0x00bc, B:38:0x00de, B:40:0x00e1, B:42:0x00ec, B:44:0x00f0, B:46:0x00f6, B:48:0x00fc, B:49:0x00ff, B:56:0x010e, B:58:0x0116, B:61:0x0128, B:62:0x0142, B:63:0x0143, B:64:0x014a, B:72:0x0159, B:73:0x015f, B:75:0x0166, B:76:0x016b, B:77:0x016f, B:82:0x017c, B:84:0x0181, B:87:0x018a, B:88:0x01a4, B:89:0x01a5, B:90:0x01bf, B:96:0x01c9, B:98:0x01d1, B:101:0x01e2, B:102:0x0204, B:103:0x0205, B:104:0x020c, B:105:0x020d, B:107:0x0217, B:109:0x0221, B:110:0x0227, B:112:0x0232, B:114:0x023a, B:118:0x0251, B:120:0x025f, B:122:0x0266, B:124:0x026c, B:128:0x0275, B:131:0x027b, B:134:0x028b, B:135:0x0295, B:137:0x02a2, B:138:0x02a5, B:140:0x02af, B:145:0x02bd, B:146:0x02c3, B:148:0x02cb, B:149:0x02d0, B:151:0x02d8, B:152:0x02e2, B:156:0x02eb, B:157:0x02f2, B:158:0x02f3, B:161:0x02fd, B:163:0x0301, B:165:0x0309, B:166:0x030c, B:168:0x0312, B:171:0x0323, B:177:0x033d, B:178:0x0342, B:180:0x0346, B:181:0x034a, B:190:0x035f, B:194:0x0369, B:196:0x0371, B:198:0x037b, B:200:0x038c, B:202:0x0397, B:204:0x039f, B:206:0x03a3, B:208:0x03ab, B:211:0x03b0, B:213:0x03b4, B:215:0x03bb, B:217:0x03c3, B:219:0x03c7, B:220:0x03ca, B:221:0x03d6, B:224:0x03df, B:226:0x03e3, B:227:0x03e6, B:229:0x03ea, B:230:0x03ee, B:231:0x03fb, B:233:0x0407, B:235:0x040f, B:238:0x0418, B:239:0x0432, B:240:0x0433, B:241:0x0451, B:245:0x0457, B:247:0x045b, B:249:0x0461, B:251:0x0467, B:252:0x046b, B:257:0x0475, B:263:0x0488, B:265:0x0497, B:267:0x04a2, B:268:0x04aa, B:269:0x04ad, B:274:0x04b9, B:276:0x04c3, B:277:0x04c8, B:278:0x04d2, B:279:0x04d5, B:281:0x04de, B:283:0x04e9, B:286:0x04f9, B:287:0x051b, B:290:0x0520, B:292:0x052a, B:294:0x0532, B:295:0x0535, B:297:0x0540, B:298:0x0544, B:300:0x054f, B:303:0x0556, B:306:0x0563, B:307:0x056a, B:310:0x056f, B:312:0x0574, B:316:0x0580, B:318:0x0588, B:320:0x059d, B:321:0x05a8, B:322:0x05af, B:324:0x05bc, B:326:0x05c2, B:329:0x05c8, B:331:0x05ce, B:333:0x05d6, B:336:0x05e8, B:339:0x05f0, B:341:0x05f4, B:342:0x05fb, B:344:0x0600, B:345:0x0603, B:347:0x060b, B:350:0x0615, B:353:0x061f, B:354:0x0627, B:355:0x062f, B:356:0x0649, B:357:0x064a, B:359:0x065c, B:362:0x0663, B:365:0x0670, B:366:0x0692, B:367:0x0693, B:368:0x069a, B:369:0x069b, B:370:0x06a2, B:371:0x06a3, B:372:0x06aa), top: B:381:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:341:0x05f4 A[Catch: all -> 0x06ab, TryCatch #2 {all -> 0x06ab, blocks: (B:28:0x007f, B:31:0x0092, B:34:0x00aa, B:37:0x00bc, B:38:0x00de, B:40:0x00e1, B:42:0x00ec, B:44:0x00f0, B:46:0x00f6, B:48:0x00fc, B:49:0x00ff, B:56:0x010e, B:58:0x0116, B:61:0x0128, B:62:0x0142, B:63:0x0143, B:64:0x014a, B:72:0x0159, B:73:0x015f, B:75:0x0166, B:76:0x016b, B:77:0x016f, B:82:0x017c, B:84:0x0181, B:87:0x018a, B:88:0x01a4, B:89:0x01a5, B:90:0x01bf, B:96:0x01c9, B:98:0x01d1, B:101:0x01e2, B:102:0x0204, B:103:0x0205, B:104:0x020c, B:105:0x020d, B:107:0x0217, B:109:0x0221, B:110:0x0227, B:112:0x0232, B:114:0x023a, B:118:0x0251, B:120:0x025f, B:122:0x0266, B:124:0x026c, B:128:0x0275, B:131:0x027b, B:134:0x028b, B:135:0x0295, B:137:0x02a2, B:138:0x02a5, B:140:0x02af, B:145:0x02bd, B:146:0x02c3, B:148:0x02cb, B:149:0x02d0, B:151:0x02d8, B:152:0x02e2, B:156:0x02eb, B:157:0x02f2, B:158:0x02f3, B:161:0x02fd, B:163:0x0301, B:165:0x0309, B:166:0x030c, B:168:0x0312, B:171:0x0323, B:177:0x033d, B:178:0x0342, B:180:0x0346, B:181:0x034a, B:190:0x035f, B:194:0x0369, B:196:0x0371, B:198:0x037b, B:200:0x038c, B:202:0x0397, B:204:0x039f, B:206:0x03a3, B:208:0x03ab, B:211:0x03b0, B:213:0x03b4, B:215:0x03bb, B:217:0x03c3, B:219:0x03c7, B:220:0x03ca, B:221:0x03d6, B:224:0x03df, B:226:0x03e3, B:227:0x03e6, B:229:0x03ea, B:230:0x03ee, B:231:0x03fb, B:233:0x0407, B:235:0x040f, B:238:0x0418, B:239:0x0432, B:240:0x0433, B:241:0x0451, B:245:0x0457, B:247:0x045b, B:249:0x0461, B:251:0x0467, B:252:0x046b, B:257:0x0475, B:263:0x0488, B:265:0x0497, B:267:0x04a2, B:268:0x04aa, B:269:0x04ad, B:274:0x04b9, B:276:0x04c3, B:277:0x04c8, B:278:0x04d2, B:279:0x04d5, B:281:0x04de, B:283:0x04e9, B:286:0x04f9, B:287:0x051b, B:290:0x0520, B:292:0x052a, B:294:0x0532, B:295:0x0535, B:297:0x0540, B:298:0x0544, B:300:0x054f, B:303:0x0556, B:306:0x0563, B:307:0x056a, B:310:0x056f, B:312:0x0574, B:316:0x0580, B:318:0x0588, B:320:0x059d, B:321:0x05a8, B:322:0x05af, B:324:0x05bc, B:326:0x05c2, B:329:0x05c8, B:331:0x05ce, B:333:0x05d6, B:336:0x05e8, B:339:0x05f0, B:341:0x05f4, B:342:0x05fb, B:344:0x0600, B:345:0x0603, B:347:0x060b, B:350:0x0615, B:353:0x061f, B:354:0x0627, B:355:0x062f, B:356:0x0649, B:357:0x064a, B:359:0x065c, B:362:0x0663, B:365:0x0670, B:366:0x0692, B:367:0x0693, B:368:0x069a, B:369:0x069b, B:370:0x06a2, B:371:0x06a3, B:372:0x06aa), top: B:381:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0600 A[Catch: all -> 0x06ab, TryCatch #2 {all -> 0x06ab, blocks: (B:28:0x007f, B:31:0x0092, B:34:0x00aa, B:37:0x00bc, B:38:0x00de, B:40:0x00e1, B:42:0x00ec, B:44:0x00f0, B:46:0x00f6, B:48:0x00fc, B:49:0x00ff, B:56:0x010e, B:58:0x0116, B:61:0x0128, B:62:0x0142, B:63:0x0143, B:64:0x014a, B:72:0x0159, B:73:0x015f, B:75:0x0166, B:76:0x016b, B:77:0x016f, B:82:0x017c, B:84:0x0181, B:87:0x018a, B:88:0x01a4, B:89:0x01a5, B:90:0x01bf, B:96:0x01c9, B:98:0x01d1, B:101:0x01e2, B:102:0x0204, B:103:0x0205, B:104:0x020c, B:105:0x020d, B:107:0x0217, B:109:0x0221, B:110:0x0227, B:112:0x0232, B:114:0x023a, B:118:0x0251, B:120:0x025f, B:122:0x0266, B:124:0x026c, B:128:0x0275, B:131:0x027b, B:134:0x028b, B:135:0x0295, B:137:0x02a2, B:138:0x02a5, B:140:0x02af, B:145:0x02bd, B:146:0x02c3, B:148:0x02cb, B:149:0x02d0, B:151:0x02d8, B:152:0x02e2, B:156:0x02eb, B:157:0x02f2, B:158:0x02f3, B:161:0x02fd, B:163:0x0301, B:165:0x0309, B:166:0x030c, B:168:0x0312, B:171:0x0323, B:177:0x033d, B:178:0x0342, B:180:0x0346, B:181:0x034a, B:190:0x035f, B:194:0x0369, B:196:0x0371, B:198:0x037b, B:200:0x038c, B:202:0x0397, B:204:0x039f, B:206:0x03a3, B:208:0x03ab, B:211:0x03b0, B:213:0x03b4, B:215:0x03bb, B:217:0x03c3, B:219:0x03c7, B:220:0x03ca, B:221:0x03d6, B:224:0x03df, B:226:0x03e3, B:227:0x03e6, B:229:0x03ea, B:230:0x03ee, B:231:0x03fb, B:233:0x0407, B:235:0x040f, B:238:0x0418, B:239:0x0432, B:240:0x0433, B:241:0x0451, B:245:0x0457, B:247:0x045b, B:249:0x0461, B:251:0x0467, B:252:0x046b, B:257:0x0475, B:263:0x0488, B:265:0x0497, B:267:0x04a2, B:268:0x04aa, B:269:0x04ad, B:274:0x04b9, B:276:0x04c3, B:277:0x04c8, B:278:0x04d2, B:279:0x04d5, B:281:0x04de, B:283:0x04e9, B:286:0x04f9, B:287:0x051b, B:290:0x0520, B:292:0x052a, B:294:0x0532, B:295:0x0535, B:297:0x0540, B:298:0x0544, B:300:0x054f, B:303:0x0556, B:306:0x0563, B:307:0x056a, B:310:0x056f, B:312:0x0574, B:316:0x0580, B:318:0x0588, B:320:0x059d, B:321:0x05a8, B:322:0x05af, B:324:0x05bc, B:326:0x05c2, B:329:0x05c8, B:331:0x05ce, B:333:0x05d6, B:336:0x05e8, B:339:0x05f0, B:341:0x05f4, B:342:0x05fb, B:344:0x0600, B:345:0x0603, B:347:0x060b, B:350:0x0615, B:353:0x061f, B:354:0x0627, B:355:0x062f, B:356:0x0649, B:357:0x064a, B:359:0x065c, B:362:0x0663, B:365:0x0670, B:366:0x0692, B:367:0x0693, B:368:0x069a, B:369:0x069b, B:370:0x06a2, B:371:0x06a3, B:372:0x06aa), top: B:381:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0615 A[Catch: all -> 0x06ab, TRY_ENTER, TryCatch #2 {all -> 0x06ab, blocks: (B:28:0x007f, B:31:0x0092, B:34:0x00aa, B:37:0x00bc, B:38:0x00de, B:40:0x00e1, B:42:0x00ec, B:44:0x00f0, B:46:0x00f6, B:48:0x00fc, B:49:0x00ff, B:56:0x010e, B:58:0x0116, B:61:0x0128, B:62:0x0142, B:63:0x0143, B:64:0x014a, B:72:0x0159, B:73:0x015f, B:75:0x0166, B:76:0x016b, B:77:0x016f, B:82:0x017c, B:84:0x0181, B:87:0x018a, B:88:0x01a4, B:89:0x01a5, B:90:0x01bf, B:96:0x01c9, B:98:0x01d1, B:101:0x01e2, B:102:0x0204, B:103:0x0205, B:104:0x020c, B:105:0x020d, B:107:0x0217, B:109:0x0221, B:110:0x0227, B:112:0x0232, B:114:0x023a, B:118:0x0251, B:120:0x025f, B:122:0x0266, B:124:0x026c, B:128:0x0275, B:131:0x027b, B:134:0x028b, B:135:0x0295, B:137:0x02a2, B:138:0x02a5, B:140:0x02af, B:145:0x02bd, B:146:0x02c3, B:148:0x02cb, B:149:0x02d0, B:151:0x02d8, B:152:0x02e2, B:156:0x02eb, B:157:0x02f2, B:158:0x02f3, B:161:0x02fd, B:163:0x0301, B:165:0x0309, B:166:0x030c, B:168:0x0312, B:171:0x0323, B:177:0x033d, B:178:0x0342, B:180:0x0346, B:181:0x034a, B:190:0x035f, B:194:0x0369, B:196:0x0371, B:198:0x037b, B:200:0x038c, B:202:0x0397, B:204:0x039f, B:206:0x03a3, B:208:0x03ab, B:211:0x03b0, B:213:0x03b4, B:215:0x03bb, B:217:0x03c3, B:219:0x03c7, B:220:0x03ca, B:221:0x03d6, B:224:0x03df, B:226:0x03e3, B:227:0x03e6, B:229:0x03ea, B:230:0x03ee, B:231:0x03fb, B:233:0x0407, B:235:0x040f, B:238:0x0418, B:239:0x0432, B:240:0x0433, B:241:0x0451, B:245:0x0457, B:247:0x045b, B:249:0x0461, B:251:0x0467, B:252:0x046b, B:257:0x0475, B:263:0x0488, B:265:0x0497, B:267:0x04a2, B:268:0x04aa, B:269:0x04ad, B:274:0x04b9, B:276:0x04c3, B:277:0x04c8, B:278:0x04d2, B:279:0x04d5, B:281:0x04de, B:283:0x04e9, B:286:0x04f9, B:287:0x051b, B:290:0x0520, B:292:0x052a, B:294:0x0532, B:295:0x0535, B:297:0x0540, B:298:0x0544, B:300:0x054f, B:303:0x0556, B:306:0x0563, B:307:0x056a, B:310:0x056f, B:312:0x0574, B:316:0x0580, B:318:0x0588, B:320:0x059d, B:321:0x05a8, B:322:0x05af, B:324:0x05bc, B:326:0x05c2, B:329:0x05c8, B:331:0x05ce, B:333:0x05d6, B:336:0x05e8, B:339:0x05f0, B:341:0x05f4, B:342:0x05fb, B:344:0x0600, B:345:0x0603, B:347:0x060b, B:350:0x0615, B:353:0x061f, B:354:0x0627, B:355:0x062f, B:356:0x0649, B:357:0x064a, B:359:0x065c, B:362:0x0663, B:365:0x0670, B:366:0x0692, B:367:0x0693, B:368:0x069a, B:369:0x069b, B:370:0x06a2, B:371:0x06a3, B:372:0x06aa), top: B:381:0x007f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:399:0x018a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:406:0x060b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0187  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object parseObject(java.util.Map r19, java.lang.Object r20) {
        /*
            Method dump skipped, instructions count: 1715
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public void setConfig(ParserConfig parserConfig) {
        this.config = parserConfig;
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    public <T> T parseObject(Type type, Object obj) {
        int i = this.lexer.token();
        if (i == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (i == 4) {
            if (type == byte[].class) {
                T t = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t;
            } else if (type == char[].class) {
                String stringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) stringVal.toCharArray();
            }
        }
        ObjectDeserializer deserializer = this.config.getDeserializer(type);
        try {
            if (deserializer.getClass() != JavaBeanDeserializer.class) {
                return (T) deserializer.deserialze(this, type, obj);
            }
            if (!(this.lexer.token() == 12 || this.lexer.token() == 14)) {
                throw new JSONException("syntax error,except start with { or [,but actually start with " + this.lexer.tokenName());
            }
            return (T) ((JavaBeanDeserializer) deserializer).deserialze(this, type, obj, 0);
        } catch (JSONException e) {
            throw e;
        } catch (Throwable th) {
            throw new JSONException(th.getMessage(), th);
        }
    }

    public <T> List<T> parseArray(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        parseArray((Class<?>) cls, (Collection) arrayList);
        return arrayList;
    }

    public void parseArray(Class<?> cls, Collection collection) {
        parseArray((Type) cls, collection);
    }

    public void parseArray(Type type, Collection collection) {
        parseArray(type, collection, null);
    }

    /* JADX WARN: Finally extract failed */
    public void parseArray(Type type, Collection collection, Object obj) {
        ObjectDeserializer objectDeserializer;
        int i = this.lexer.token();
        if (i == 21 || i == 22) {
            this.lexer.nextToken();
            i = this.lexer.token();
        }
        if (i == 14) {
            if (Integer.TYPE == type) {
                objectDeserializer = IntegerCodec.instance;
                this.lexer.nextToken(2);
            } else if (String.class == type) {
                objectDeserializer = StringCodec.instance;
                this.lexer.nextToken(4);
            } else {
                objectDeserializer = this.config.getDeserializer(type);
                this.lexer.nextToken(objectDeserializer.getFastMatchToken());
            }
            ParseContext parseContext = this.context;
            setContext(collection, obj);
            int i2 = 0;
            while (true) {
                try {
                    if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (this.lexer.token() == 16) {
                            this.lexer.nextToken();
                        }
                    }
                    if (this.lexer.token() == 15) {
                        setContext(parseContext);
                        this.lexer.nextToken(16);
                        return;
                    }
                    Object obj2 = null;
                    if (Integer.TYPE == type) {
                        collection.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            obj2 = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object parse = parse();
                            if (parse != null) {
                                obj2 = parse.toString();
                            }
                        }
                        collection.add(obj2);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                        } else {
                            obj2 = objectDeserializer.deserialze(this, type, Integer.valueOf(i2));
                        }
                        collection.add(obj2);
                        checkListResolve(collection);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(objectDeserializer.getFastMatchToken());
                    }
                    i2++;
                } catch (Throwable th) {
                    setContext(parseContext);
                    throw th;
                }
            }
        } else {
            throw new JSONException("expect '[', but " + JSONToken.name(i) + ", " + this.lexer.info());
        }
    }

    public Object[] parseArray(Type[] typeArr) {
        Object obj;
        boolean z;
        Class<?> cls;
        Class cls2;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        } else if (this.lexer.token() == 14) {
            Object[] objArr = new Object[typeArr.length];
            if (typeArr.length == 0) {
                this.lexer.nextToken(15);
                if (this.lexer.token() == 15) {
                    this.lexer.nextToken(16);
                    return new Object[0];
                }
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(2);
            for (int i = 0; i < typeArr.length; i++) {
                if (this.lexer.token() == 8) {
                    this.lexer.nextToken(16);
                    obj = null;
                } else {
                    Type type = typeArr[i];
                    if (type == Integer.TYPE || type == Integer.class) {
                        if (this.lexer.token() == 2) {
                            obj = Integer.valueOf(this.lexer.intValue());
                            this.lexer.nextToken(16);
                        } else {
                            obj = TypeUtils.cast(parse(), type, this.config);
                        }
                    } else if (type != String.class) {
                        if (i != typeArr.length - 1 || !(type instanceof Class) || (((cls2 = (Class) type) == byte[].class || cls2 == char[].class) && this.lexer.token() == 4)) {
                            cls = null;
                            z = false;
                        } else {
                            z = cls2.isArray();
                            cls = cls2.getComponentType();
                        }
                        if (!z || this.lexer.token() == 14) {
                            obj = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i));
                        } else {
                            ArrayList arrayList = new ArrayList();
                            ObjectDeserializer deserializer = this.config.getDeserializer(cls);
                            int fastMatchToken = deserializer.getFastMatchToken();
                            if (this.lexer.token() != 15) {
                                while (true) {
                                    arrayList.add(deserializer.deserialze(this, type, null));
                                    if (this.lexer.token() != 16) {
                                        break;
                                    }
                                    this.lexer.nextToken(fastMatchToken);
                                }
                                if (this.lexer.token() != 15) {
                                    throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                                }
                            }
                            obj = TypeUtils.cast(arrayList, type, this.config);
                        }
                    } else if (this.lexer.token() == 4) {
                        obj = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                    } else {
                        obj = TypeUtils.cast(parse(), type, this.config);
                    }
                }
                objArr[i] = obj;
                if (this.lexer.token() == 15) {
                    break;
                } else if (this.lexer.token() == 16) {
                    if (i == typeArr.length - 1) {
                        this.lexer.nextToken(15);
                    } else {
                        this.lexer.nextToken(2);
                    }
                } else {
                    throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                }
            }
            if (this.lexer.token() == 15) {
                this.lexer.nextToken(16);
                return objArr;
            }
            throw new JSONException("syntax error");
        } else {
            throw new JSONException("syntax error : " + this.lexer.tokenName());
        }
    }

    public void parseObject(Object obj) {
        Object obj2;
        Class<?> cls = obj.getClass();
        ObjectDeserializer deserializer = this.config.getDeserializer(cls);
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (this.lexer.token() == 12 || this.lexer.token() == 16) {
            while (true) {
                String scanSymbol = this.lexer.scanSymbol(this.symbolTable);
                if (scanSymbol == null) {
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken(16);
                        return;
                    } else if (this.lexer.token() == 16 && this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    }
                }
                FieldDeserializer fieldDeserializer = javaBeanDeserializer != null ? javaBeanDeserializer.getFieldDeserializer(scanSymbol) : null;
                if (fieldDeserializer != null) {
                    Class<?> cls2 = fieldDeserializer.fieldInfo.fieldClass;
                    Type type = fieldDeserializer.fieldInfo.fieldType;
                    if (cls2 == Integer.TYPE) {
                        this.lexer.nextTokenWithColon(2);
                        obj2 = IntegerCodec.instance.deserialze(this, type, null);
                    } else if (cls2 == String.class) {
                        this.lexer.nextTokenWithColon(4);
                        obj2 = StringCodec.deserialze(this);
                    } else if (cls2 == Long.TYPE) {
                        this.lexer.nextTokenWithColon(2);
                        obj2 = LongCodec.instance.deserialze(this, type, null);
                    } else {
                        ObjectDeserializer deserializer2 = this.config.getDeserializer(cls2, type);
                        this.lexer.nextTokenWithColon(deserializer2.getFastMatchToken());
                        obj2 = deserializer2.deserialze(this, type, null);
                    }
                    fieldDeserializer.setValue(obj, obj2);
                    if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                        this.lexer.nextToken(16);
                        return;
                    }
                } else if (this.lexer.isEnabled(Feature.IgnoreNotMatch)) {
                    this.lexer.nextTokenWithColon();
                    parse();
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken();
                        return;
                    }
                } else {
                    throw new JSONException("setter not found, class " + cls.getName() + ", property " + scanSymbol);
                }
            }
        } else {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
    }

    public Object parseArrayWithType(Type type) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length == 1) {
            Type type2 = actualTypeArguments[0];
            if (type2 instanceof Class) {
                ArrayList arrayList = new ArrayList();
                parseArray((Class) type2, (Collection) arrayList);
                return arrayList;
            } else if (type2 instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type2;
                Type type3 = wildcardType.getUpperBounds()[0];
                if (!Object.class.equals(type3)) {
                    ArrayList arrayList2 = new ArrayList();
                    parseArray((Class) type3, (Collection) arrayList2);
                    return arrayList2;
                } else if (wildcardType.getLowerBounds().length == 0) {
                    return parse();
                } else {
                    throw new JSONException("not support type : " + type);
                }
            } else {
                if (type2 instanceof TypeVariable) {
                    TypeVariable typeVariable = (TypeVariable) type2;
                    Type[] bounds = typeVariable.getBounds();
                    if (bounds.length == 1) {
                        Type type4 = bounds[0];
                        if (type4 instanceof Class) {
                            ArrayList arrayList3 = new ArrayList();
                            parseArray((Class) type4, (Collection) arrayList3);
                            return arrayList3;
                        }
                    } else {
                        throw new JSONException("not support : " + typeVariable);
                    }
                }
                if (type2 instanceof ParameterizedType) {
                    ArrayList arrayList4 = new ArrayList();
                    parseArray((ParameterizedType) type2, arrayList4);
                    return arrayList4;
                }
                throw new JSONException("TODO : " + type);
            }
        } else {
            throw new JSONException("not support type " + type);
        }
    }

    public void acceptType(String str) {
        JSONLexer jSONLexer = this.lexer;
        jSONLexer.nextTokenWithColon();
        if (jSONLexer.token() != 4) {
            throw new JSONException("type not match error");
        } else if (str.equals(jSONLexer.stringVal())) {
            jSONLexer.nextToken();
            if (jSONLexer.token() == 16) {
                jSONLexer.nextToken();
            }
        } else {
            throw new JSONException("type not match error");
        }
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public void setResolveStatus(int i) {
        this.resolveStatus = i;
    }

    public Object getObject(String str) {
        for (int i = 0; i < this.contextArrayIndex; i++) {
            if (str.equals(this.contextArray[i].toString())) {
                return this.contextArray[i].object;
            }
        }
        return null;
    }

    public void checkListResolve(Collection collection) {
        if (this.resolveStatus != 1) {
            return;
        }
        if (collection instanceof List) {
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = new ResolveFieldDeserializer(this, (List) collection, collection.size() - 1);
            lastResolveTask.ownerContext = this.context;
            setResolveStatus(0);
            return;
        }
        ResolveTask lastResolveTask2 = getLastResolveTask();
        lastResolveTask2.fieldDeserializer = new ResolveFieldDeserializer(collection);
        lastResolveTask2.ownerContext = this.context;
        setResolveStatus(0);
    }

    public void checkMapResolve(Map map, Object obj) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer resolveFieldDeserializer = new ResolveFieldDeserializer(map, obj);
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = resolveFieldDeserializer;
            lastResolveTask.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public Object parseObject(Map map) {
        return parseObject(map, (Object) null);
    }

    public JSONObject parseObject() {
        Object parseObject = parseObject((Map) new JSONObject(this.lexer.isEnabled(Feature.OrderedField)));
        if (parseObject instanceof JSONObject) {
            return (JSONObject) parseObject;
        }
        if (parseObject == null) {
            return null;
        }
        return new JSONObject((Map) parseObject);
    }

    public final void parseArray(Collection collection) {
        parseArray(collection, (Object) null);
    }

    public final void parseArray(Collection collection, Object obj) {
        Number number;
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == 21 || jSONLexer.token() == 22) {
            jSONLexer.nextToken();
        }
        if (jSONLexer.token() == 14) {
            jSONLexer.nextToken(4);
            ParseContext parseContext = this.context;
            if (parseContext == null || parseContext.level <= 512) {
                ParseContext parseContext2 = this.context;
                setContext(collection, obj);
                int i = 0;
                while (true) {
                    try {
                        if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                            while (jSONLexer.token() == 16) {
                                jSONLexer.nextToken();
                            }
                        }
                        JSONArray jSONArray = null;
                        jSONArray = null;
                        switch (jSONLexer.token()) {
                            case 2:
                                Number integerValue = jSONLexer.integerValue();
                                jSONLexer.nextToken(16);
                                jSONArray = integerValue;
                                break;
                            case 3:
                                if (jSONLexer.isEnabled(Feature.UseBigDecimal)) {
                                    number = jSONLexer.decimalValue(true);
                                } else {
                                    number = jSONLexer.decimalValue(false);
                                }
                                jSONLexer.nextToken(16);
                                jSONArray = number;
                                break;
                            case 4:
                                String stringVal = jSONLexer.stringVal();
                                jSONLexer.nextToken(16);
                                jSONArray = stringVal;
                                if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                                    JSONScanner jSONScanner = new JSONScanner(stringVal);
                                    Date date = stringVal;
                                    if (jSONScanner.scanISO8601DateIfMatch()) {
                                        date = jSONScanner.getCalendar().getTime();
                                    }
                                    jSONScanner.close();
                                    jSONArray = date;
                                    break;
                                }
                                break;
                            case 6:
                                Boolean bool = Boolean.TRUE;
                                jSONLexer.nextToken(16);
                                jSONArray = bool;
                                break;
                            case 7:
                                Boolean bool2 = Boolean.FALSE;
                                jSONLexer.nextToken(16);
                                jSONArray = bool2;
                                break;
                            case 8:
                                jSONLexer.nextToken(4);
                                break;
                            case 12:
                                jSONArray = parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), Integer.valueOf(i));
                                break;
                            case 14:
                                JSONArray jSONArray2 = new JSONArray();
                                parseArray(jSONArray2, Integer.valueOf(i));
                                jSONArray = jSONArray2;
                                if (jSONLexer.isEnabled(Feature.UseObjectArray)) {
                                    jSONArray = jSONArray2.toArray();
                                    break;
                                }
                                break;
                            case 15:
                                jSONLexer.nextToken(16);
                                return;
                            case 20:
                                throw new JSONException("unclosed jsonArray");
                            case 23:
                                jSONLexer.nextToken(4);
                                break;
                            default:
                                jSONArray = parse();
                                break;
                        }
                        collection.add(jSONArray == 1 ? 1 : 0);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                            jSONLexer.nextToken(4);
                        }
                        i++;
                    } finally {
                        setContext(parseContext2);
                    }
                }
            } else {
                throw new JSONException("array level > 512");
            }
        } else {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(jSONLexer.token()) + ", pos " + jSONLexer.pos() + ", fieldName " + obj);
        }
    }

    public ParseContext getContext() {
        return this.context;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    public ResolveTask getLastResolveTask() {
        List<ResolveTask> list = this.resolveTaskList;
        return list.get(list.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setContext(ParseContext parseContext) {
        if (!this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            this.context = parseContext;
        }
    }

    public void popContext() {
        if (!this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            this.context = this.context.parent;
            int i = this.contextArrayIndex;
            if (i > 0) {
                this.contextArrayIndex = i - 1;
                this.contextArray[this.contextArrayIndex] = null;
            }
        }
    }

    public ParseContext setContext(Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, obj, obj2);
    }

    public ParseContext setContext(ParseContext parseContext, Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        this.context = new ParseContext(parseContext, obj, obj2);
        addContext(this.context);
        return this.context;
    }

    private void addContext(ParseContext parseContext) {
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        ParseContext[] parseContextArr = this.contextArray;
        if (parseContextArr == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= parseContextArr.length) {
            ParseContext[] parseContextArr2 = new ParseContext[(parseContextArr.length * 3) / 2];
            System.arraycopy(parseContextArr, 0, parseContextArr2, 0, parseContextArr.length);
            this.contextArray = parseContextArr2;
        }
        this.contextArray[i] = parseContext;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parseKey() {
        if (this.lexer.token() != 18) {
            return parse(null);
        }
        String stringVal = this.lexer.stringVal();
        this.lexer.nextToken(16);
        return stringVal;
    }

    public Object parse(Object obj) {
        JSONLexer jSONLexer = this.lexer;
        switch (jSONLexer.token()) {
            case 2:
                Number integerValue = jSONLexer.integerValue();
                jSONLexer.nextToken();
                return integerValue;
            case 3:
                Number decimalValue = jSONLexer.decimalValue(jSONLexer.isEnabled(Feature.UseBigDecimal));
                jSONLexer.nextToken();
                return decimalValue;
            case 4:
                String stringVal = jSONLexer.stringVal();
                jSONLexer.nextToken(16);
                if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                    JSONScanner jSONScanner = new JSONScanner(stringVal);
                    try {
                        if (jSONScanner.scanISO8601DateIfMatch()) {
                            return jSONScanner.getCalendar().getTime();
                        }
                    } finally {
                        jSONScanner.close();
                    }
                }
                return stringVal;
            case 5:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 24:
            case 25:
            default:
                throw new JSONException("syntax error, " + jSONLexer.info());
            case 6:
                jSONLexer.nextToken();
                return Boolean.TRUE;
            case 7:
                jSONLexer.nextToken();
                return Boolean.FALSE;
            case 8:
                jSONLexer.nextToken();
                return null;
            case 9:
                jSONLexer.nextToken(18);
                if (jSONLexer.token() == 18) {
                    jSONLexer.nextToken(10);
                    accept(10);
                    long longValue = jSONLexer.integerValue().longValue();
                    accept(2);
                    accept(11);
                    return new Date(longValue);
                }
                throw new JSONException("syntax error");
            case 12:
                return parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), obj);
            case 14:
                JSONArray jSONArray = new JSONArray();
                parseArray(jSONArray, obj);
                return jSONLexer.isEnabled(Feature.UseObjectArray) ? jSONArray.toArray() : jSONArray;
            case 18:
                if ("NaN".equals(jSONLexer.stringVal())) {
                    jSONLexer.nextToken();
                    return null;
                }
                throw new JSONException("syntax error, " + jSONLexer.info());
            case 20:
                if (jSONLexer.isBlankInput()) {
                    return null;
                }
                throw new JSONException("unterminated json string, " + jSONLexer.info());
            case 21:
                jSONLexer.nextToken();
                HashSet hashSet = new HashSet();
                parseArray(hashSet, obj);
                return hashSet;
            case 22:
                jSONLexer.nextToken();
                TreeSet treeSet = new TreeSet();
                parseArray(treeSet, obj);
                return treeSet;
            case 23:
                jSONLexer.nextToken();
                return null;
            case 26:
                byte[] bytesValue = jSONLexer.bytesValue();
                jSONLexer.nextToken();
                return bytesValue;
        }
    }

    public void config(Feature feature, boolean z) {
        this.lexer.config(feature, z);
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public final void accept(int i) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i) {
            jSONLexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(jSONLexer.token()));
    }

    public final void accept(int i, int i2) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i) {
            jSONLexer.nextToken(i2);
        } else {
            throwException(i);
        }
    }

    public void throwException(int i) {
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(this.lexer.token()));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer jSONLexer = this.lexer;
        try {
            if (jSONLexer.isEnabled(Feature.AutoCloseSource) && jSONLexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(jSONLexer.token()));
            }
        } finally {
            jSONLexer.close();
        }
    }

    public Object resolveReference(String str) {
        if (this.contextArray == null) {
            return null;
        }
        int i = 0;
        while (true) {
            ParseContext[] parseContextArr = this.contextArray;
            if (i >= parseContextArr.length || i >= this.contextArrayIndex) {
                break;
            }
            ParseContext parseContext = parseContextArr[i];
            if (parseContext.toString().equals(str)) {
                return parseContext.object;
            }
            i++;
        }
        return null;
    }

    public void handleResovleTask(Object obj) {
        Object obj2;
        List<ResolveTask> list = this.resolveTaskList;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ResolveTask resolveTask = this.resolveTaskList.get(i);
                String str = resolveTask.referenceValue;
                Object obj3 = null;
                if (resolveTask.ownerContext != null) {
                    obj3 = resolveTask.ownerContext.object;
                }
                if (str.startsWith("$")) {
                    obj2 = getObject(str);
                    if (obj2 == null) {
                        try {
                            JSONPath compile = JSONPath.compile(str);
                            if (compile.isRef()) {
                                obj2 = compile.eval(obj);
                            }
                        } catch (JSONPathException unused) {
                        }
                    }
                } else {
                    obj2 = resolveTask.context.object;
                }
                FieldDeserializer fieldDeserializer = resolveTask.fieldDeserializer;
                if (fieldDeserializer != null) {
                    if (obj2 != null && obj2.getClass() == JSONObject.class && fieldDeserializer.fieldInfo != null && !Map.class.isAssignableFrom(fieldDeserializer.fieldInfo.fieldClass)) {
                        Object obj4 = this.contextArray[0].object;
                        JSONPath compile2 = JSONPath.compile(str);
                        if (compile2.isRef()) {
                            obj2 = compile2.eval(obj4);
                        }
                    }
                    fieldDeserializer.setValue(obj3, obj2);
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext parseContext, String str) {
            this.context = parseContext;
            this.referenceValue = str;
        }
    }

    public void parseExtra(Object obj, String str) {
        Object obj2;
        this.lexer.nextTokenWithColon();
        List<ExtraTypeProvider> list = this.extraTypeProviders;
        Type type = null;
        if (list != null) {
            for (ExtraTypeProvider extraTypeProvider : list) {
                type = extraTypeProvider.getExtraType(obj, str);
            }
        }
        if (type == null) {
            obj2 = parse();
        } else {
            obj2 = parseObject(type);
        }
        if (obj instanceof ExtraProcessable) {
            ((ExtraProcessable) obj).processExtra(str, obj2);
            return;
        }
        List<ExtraProcessor> list2 = this.extraProcessors;
        if (list2 != null) {
            for (ExtraProcessor extraProcessor : list2) {
                extraProcessor.processExtra(obj, str, obj2);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x0238, code lost:
        return r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable r10, java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 619
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable, java.lang.Object):java.lang.Object");
    }
}
