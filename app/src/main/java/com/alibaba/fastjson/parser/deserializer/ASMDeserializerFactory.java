package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.core.JsonPointer;
import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.c;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class ASMDeserializerFactory implements Opcodes {
    static final String DefaultJSONParser = ASMUtils.type(DefaultJSONParser.class);
    static final String JSONLexerBase = ASMUtils.type(JSONLexerBase.class);
    public final ASMClassLoader classLoader;
    protected final AtomicLong seed = new AtomicLong();

    public ASMDeserializerFactory(ClassLoader classLoader) {
        this.classLoader = classLoader instanceof ASMClassLoader ? (ASMClassLoader) classLoader : new ASMClassLoader(classLoader);
    }

    public ObjectDeserializer createJavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) throws Exception {
        String str;
        Class<?> cls = javaBeanInfo.clazz;
        if (!cls.isPrimitive()) {
            String str2 = "FastjsonASMDeserializer_" + this.seed.incrementAndGet() + "_" + cls.getSimpleName();
            Package r1 = ASMDeserializerFactory.class.getPackage();
            if (r1 != null) {
                String name = r1.getName();
                str2 = name.replace('.', JsonPointer.SEPARATOR) + "/" + str2;
                str = name + "." + str2;
            } else {
                str = str2;
            }
            ClassWriter classWriter = new ClassWriter();
            classWriter.visit(49, 33, str2, ASMUtils.type(JavaBeanDeserializer.class), null);
            _init(classWriter, new Context(str2, parserConfig, javaBeanInfo, 3));
            _createInstance(classWriter, new Context(str2, parserConfig, javaBeanInfo, 3));
            _deserialze(classWriter, new Context(str2, parserConfig, javaBeanInfo, 5));
            _deserialzeArrayMapping(classWriter, new Context(str2, parserConfig, javaBeanInfo, 4));
            byte[] byteArray = classWriter.toByteArray();
            return (ObjectDeserializer) this.classLoader.defineClassPublic(str, byteArray, 0, byteArray.length).getConstructor(ParserConfig.class, JavaBeanInfo.class).newInstance(parserConfig, javaBeanInfo);
        }
        throw new IllegalArgumentException("not support type :" + cls.getName());
    }

    private void _setFlag(MethodVisitor methodVisitor, Context context, int i) {
        String str = "_asm_flag_" + (i / 32);
        methodVisitor.visitVarInsn(21, context.var(str));
        methodVisitor.visitLdcInsn(Integer.valueOf(1 << i));
        methodVisitor.visitInsn(128);
        methodVisitor.visitVarInsn(54, context.var(str));
    }

    private void _isFlag(MethodVisitor methodVisitor, Context context, int i, Label label) {
        methodVisitor.visitVarInsn(21, context.var("_asm_flag_" + (i / 32)));
        methodVisitor.visitLdcInsn(Integer.valueOf(1 << i));
        methodVisitor.visitInsn(126);
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
    }

    private void _deserialzeArrayMapping(ClassWriter classWriter, Context context) {
        int i;
        MethodWriter methodWriter = new MethodWriter(classWriter, 1, "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        defineVarLexer(context, methodWriter);
        int i2 = 25;
        methodWriter.visitVarInsn(25, context.var("lexer"));
        boolean z = true;
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc(SymbolTable.class));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanTypeName", "(" + ASMUtils.desc(SymbolTable.class) + ")Ljava/lang/String;");
        int i3 = 58;
        methodWriter.visitVarInsn(58, context.var("typeName"));
        Label label = new Label();
        methodWriter.visitVarInsn(25, context.var("typeName"));
        methodWriter.visitJumpInsn(Opcodes.IFNULL, label);
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc(ParserConfig.class));
        methodWriter.visitVarInsn(25, 0);
        methodWriter.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(JavaBeanDeserializer.class), "beanInfo", ASMUtils.desc(JavaBeanInfo.class));
        methodWriter.visitVarInsn(25, context.var("typeName"));
        int i4 = Opcodes.INVOKESTATIC;
        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(JavaBeanDeserializer.class), "getSeeAlso", "(" + ASMUtils.desc(ParserConfig.class) + ASMUtils.desc(JavaBeanInfo.class) + "Ljava/lang/String;)" + ASMUtils.desc(JavaBeanDeserializer.class));
        methodWriter.visitVarInsn(58, context.var("userTypeDeser"));
        methodWriter.visitVarInsn(25, context.var("userTypeDeser"));
        methodWriter.visitTypeInsn(Opcodes.INSTANCEOF, ASMUtils.type(JavaBeanDeserializer.class));
        methodWriter.visitJumpInsn(Opcodes.IFEQ, label);
        methodWriter.visitVarInsn(25, context.var("userTypeDeser"));
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitVarInsn(25, 2);
        methodWriter.visitVarInsn(25, 3);
        methodWriter.visitVarInsn(25, 4);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        methodWriter.visitInsn(Opcodes.ARETURN);
        methodWriter.visitLabel(label);
        _createInstance(context, methodWriter);
        FieldInfo[] fieldInfoArr = context.beanInfo.sortedFields;
        int length = fieldInfoArr.length;
        int i5 = 0;
        while (i5 < length) {
            if (i5 != length - 1) {
                z = false;
            }
            int i6 = z ? 93 : 44;
            FieldInfo fieldInfo = fieldInfoArr[i5];
            Class<?> cls = fieldInfo.fieldClass;
            Type type = fieldInfo.fieldType;
            if (cls == Byte.TYPE || cls == Short.TYPE || cls == Integer.TYPE) {
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                methodWriter.visitVarInsn(54, context.var(fieldInfo.name + "_asm"));
            } else if (cls == Byte.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                methodWriter.visitMethodInsn(i4, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                Label label2 = new Label();
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitLdcInsn(5);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label2);
                methodWriter.visitInsn(1);
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                methodWriter.visitLabel(label2);
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Short.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                methodWriter.visitMethodInsn(i4, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                Label label3 = new Label();
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitLdcInsn(5);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label3);
                methodWriter.visitInsn(1);
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                methodWriter.visitLabel(label3);
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Integer.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                methodWriter.visitMethodInsn(i4, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                Label label4 = new Label();
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitLdcInsn(5);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label4);
                methodWriter.visitInsn(1);
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                methodWriter.visitLabel(label4);
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Long.TYPE) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                methodWriter.visitVarInsn(55, context.var(fieldInfo.name + "_asm", 2));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Long.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                methodWriter.visitMethodInsn(i4, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                Label label5 = new Label();
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitLdcInsn(5);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label5);
                methodWriter.visitInsn(1);
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                methodWriter.visitLabel(label5);
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Boolean.TYPE) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanBoolean", "(C)Z");
                methodWriter.visitVarInsn(54, context.var(fieldInfo.name + "_asm"));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Float.TYPE) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFloat", "(C)F");
                methodWriter.visitVarInsn(56, context.var(fieldInfo.name + "_asm"));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Float.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFloat", "(C)F");
                methodWriter.visitMethodInsn(i4, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                Label label6 = new Label();
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitLdcInsn(5);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label6);
                methodWriter.visitInsn(1);
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                methodWriter.visitLabel(label6);
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Double.TYPE) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDouble", "(C)D");
                methodWriter.visitVarInsn(57, context.var(fieldInfo.name + "_asm", 2));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Double.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDouble", "(C)D");
                methodWriter.visitMethodInsn(i4, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                Label label7 = new Label();
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitLdcInsn(5);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label7);
                methodWriter.visitInsn(1);
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                methodWriter.visitLabel(label7);
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Character.TYPE) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                methodWriter.visitInsn(3);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C");
                methodWriter.visitVarInsn(54, context.var(fieldInfo.name + "_asm"));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == String.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == BigDecimal.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDecimal", "(C)Ljava/math/BigDecimal;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == Date.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDate", "(C)Ljava/util/Date;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls == UUID.class) {
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanUUID", "(C)Ljava/util/UUID;");
                methodWriter.visitVarInsn(i3, context.var(fieldInfo.name + "_asm"));
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                i3 = i3;
                z = true;
            } else if (cls.isEnum()) {
                Label label8 = new Label();
                Label label9 = new Label();
                Label label10 = new Label();
                Label label11 = new Label();
                methodWriter.visitVarInsn(i2, context.var("lexer"));
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                methodWriter.visitInsn(89);
                methodWriter.visitVarInsn(54, context.var("ch"));
                methodWriter.visitLdcInsn(110);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label11);
                methodWriter.visitVarInsn(21, context.var("ch"));
                methodWriter.visitLdcInsn(34);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label8);
                methodWriter.visitLabel(label11);
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls)));
                methodWriter.visitVarInsn(25, 1);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc(SymbolTable.class));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanEnum", "(Ljava/lang/Class;" + ASMUtils.desc(SymbolTable.class) + "C)Ljava/lang/Enum;");
                methodWriter.visitJumpInsn(Opcodes.GOTO, label10);
                methodWriter.visitLabel(label8);
                methodWriter.visitVarInsn(21, context.var("ch"));
                methodWriter.visitLdcInsn(48);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPLT, label9);
                methodWriter.visitVarInsn(21, context.var("ch"));
                methodWriter.visitLdcInsn(57);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPGT, label9);
                _getFieldDeser(context, methodWriter, fieldInfo);
                methodWriter.visitTypeInsn(192, ASMUtils.type(EnumDeserializer.class));
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(EnumDeserializer.class), "valueOf", "(I)Ljava/lang/Enum;");
                methodWriter.visitJumpInsn(Opcodes.GOTO, label10);
                methodWriter.visitLabel(label9);
                methodWriter.visitVarInsn(25, 0);
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitVarInsn(16, i6);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "scanEnum", "(L" + JSONLexerBase + ";C)Ljava/lang/Enum;");
                methodWriter.visitLabel(label10);
                methodWriter.visitTypeInsn(192, ASMUtils.type(cls));
                methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                i3 = 58;
                i = i5;
                fieldInfoArr = fieldInfoArr;
                length = length;
                z = true;
            } else if (Collection.class.isAssignableFrom(cls)) {
                Class<?> collectionItemClass = TypeUtils.getCollectionItemClass(type);
                if (collectionItemClass == String.class) {
                    if (cls == List.class || cls == Collections.class || cls == ArrayList.class) {
                        methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(ArrayList.class));
                        methodWriter.visitInsn(89);
                        methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(ArrayList.class), "<init>", "()V");
                    } else {
                        methodWriter.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls)));
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/Class;)Ljava/util/Collection;");
                    }
                    methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
                    methodWriter.visitVarInsn(16, i6);
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanStringArray", "(Ljava/util/Collection;C)V");
                    Label label12 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label12);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                    methodWriter.visitLabel(label12);
                } else {
                    Label label13 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
                    methodWriter.visitVarInsn(54, context.var("token"));
                    methodWriter.visitVarInsn(21, context.var("token"));
                    int i7 = i5 == 0 ? 14 : 16;
                    methodWriter.visitLdcInsn(Integer.valueOf(i7));
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label13);
                    methodWriter.visitVarInsn(25, 1);
                    methodWriter.visitLdcInsn(Integer.valueOf(i7));
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "throwException", "(I)V");
                    methodWriter.visitLabel(label13);
                    Label label14 = new Label();
                    Label label15 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                    methodWriter.visitVarInsn(16, 91);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label14);
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
                    methodWriter.visitInsn(87);
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitLdcInsn(14);
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
                    methodWriter.visitJumpInsn(Opcodes.GOTO, label15);
                    methodWriter.visitLabel(label14);
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitLdcInsn(14);
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
                    methodWriter.visitLabel(label15);
                    _newCollection(methodWriter, cls, i5, false);
                    methodWriter.visitInsn(89);
                    methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                    _getCollectionFieldItemDeser(context, methodWriter, fieldInfo, collectionItemClass);
                    methodWriter.visitVarInsn(25, 1);
                    methodWriter.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(collectionItemClass)));
                    methodWriter.visitVarInsn(25, 3);
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(JavaBeanDeserializer.class), "parseArray", "(Ljava/util/Collection;" + ASMUtils.desc(ObjectDeserializer.class) + "L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)V");
                }
                i = i5;
                fieldInfoArr = fieldInfoArr;
                length = length;
                i3 = 58;
                z = true;
            } else if (cls.isArray()) {
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitLdcInsn(14);
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
                z = true;
                methodWriter.visitVarInsn(25, 1);
                methodWriter.visitVarInsn(25, 0);
                methodWriter.visitLdcInsn(Integer.valueOf(i5));
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "parseObject", "(Ljava/lang/reflect/Type;)Ljava/lang/Object;");
                methodWriter.visitTypeInsn(192, ASMUtils.type(cls));
                methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                i3 = 58;
                i = i5;
                fieldInfoArr = fieldInfoArr;
                length = length;
            } else {
                z = true;
                Label label16 = new Label();
                Label label17 = new Label();
                if (cls == Date.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                    methodWriter.visitLdcInsn(49);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label16);
                    methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(Date.class));
                    methodWriter.visitInsn(89);
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i6);
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(Date.class), "<init>", "(J)V");
                    i3 = 58;
                    methodWriter.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
                    methodWriter.visitJumpInsn(Opcodes.GOTO, label17);
                } else {
                    i3 = 58;
                }
                methodWriter.visitLabel(label16);
                _quickNextToken(context, methodWriter, 14);
                i = i5;
                length = length;
                fieldInfoArr = fieldInfoArr;
                _deserObject(context, methodWriter, fieldInfo, cls, i);
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
                methodWriter.visitLdcInsn(15);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label17);
                methodWriter.visitVarInsn(25, 0);
                methodWriter.visitVarInsn(25, context.var("lexer"));
                if (!z) {
                    methodWriter.visitLdcInsn(16);
                } else {
                    methodWriter.visitLdcInsn(15);
                }
                methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "check", "(" + ASMUtils.desc(JSONLexer.class) + "I)V");
                methodWriter.visitLabel(label17);
            }
            i5 = i + 1;
            i4 = Opcodes.INVOKESTATIC;
            i2 = 25;
        }
        _batchSet(context, methodWriter, false);
        Label label18 = new Label();
        Label label19 = new Label();
        Label label20 = new Label();
        Label label21 = new Label();
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        methodWriter.visitInsn(89);
        methodWriter.visitVarInsn(54, context.var("ch"));
        methodWriter.visitVarInsn(16, 44);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label19);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(16);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodWriter.visitJumpInsn(Opcodes.GOTO, label21);
        methodWriter.visitLabel(label19);
        methodWriter.visitVarInsn(21, context.var("ch"));
        methodWriter.visitVarInsn(16, 93);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label20);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(15);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodWriter.visitJumpInsn(Opcodes.GOTO, label21);
        methodWriter.visitLabel(label20);
        methodWriter.visitVarInsn(21, context.var("ch"));
        methodWriter.visitVarInsn(16, 26);
        methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label18);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(20);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodWriter.visitJumpInsn(Opcodes.GOTO, label21);
        methodWriter.visitLabel(label18);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(16);
        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        methodWriter.visitLabel(label21);
        methodWriter.visitVarInsn(25, context.var("instance"));
        methodWriter.visitInsn(Opcodes.ARETURN);
        methodWriter.visitMaxs(5, context.variantIndex);
        methodWriter.visitEnd();
    }

    private void _deserialze(ClassWriter classWriter, Context context) {
        int i;
        int i2;
        char c;
        char c2;
        if (context.fieldInfoList.length != 0) {
            FieldInfo[] fieldInfoArr = context.fieldInfoList;
            for (FieldInfo fieldInfo : fieldInfoArr) {
                Class<?> cls = fieldInfo.fieldClass;
                Type type = fieldInfo.fieldType;
                if (cls == Character.TYPE) {
                    return;
                }
                if (Collection.class.isAssignableFrom(cls) && (!(type instanceof ParameterizedType) || !(((ParameterizedType) type).getActualTypeArguments()[0] instanceof Class))) {
                    return;
                }
            }
            JavaBeanInfo javaBeanInfo = context.beanInfo;
            context.fieldInfoList = javaBeanInfo.sortedFields;
            MethodWriter methodWriter = new MethodWriter(classWriter, 1, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
            Label label = new Label();
            Label label2 = new Label();
            Label label3 = new Label();
            Label label4 = new Label();
            defineVarLexer(context, methodWriter);
            Label label5 = new Label();
            int i3 = 25;
            methodWriter.visitVarInsn(25, context.var("lexer"));
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
            methodWriter.visitLdcInsn(14);
            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label5);
            if ((javaBeanInfo.parserFeatures & Feature.SupportArrayToBean.mask) == 0) {
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitVarInsn(21, 4);
                methodWriter.visitLdcInsn(Integer.valueOf(Feature.SupportArrayToBean.mask));
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(II)Z");
                methodWriter.visitJumpInsn(Opcodes.IFEQ, label5);
            }
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitVarInsn(25, 2);
            methodWriter.visitVarInsn(25, 3);
            methodWriter.visitInsn(1);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, context.className, "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitLabel(label5);
            methodWriter.visitVarInsn(25, context.var("lexer"));
            methodWriter.visitLdcInsn(Integer.valueOf(Feature.SortFeidFastMatch.mask));
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(I)Z");
            Label label6 = new Label();
            methodWriter.visitJumpInsn(Opcodes.IFNE, label6);
            methodWriter.visitJumpInsn(200, label2);
            methodWriter.visitLabel(label6);
            methodWriter.visitVarInsn(25, context.var("lexer"));
            methodWriter.visitLdcInsn(context.clazz.getName());
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanType", "(Ljava/lang/String;)I");
            methodWriter.visitLdcInsn(-1);
            Label label7 = new Label();
            methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label7);
            methodWriter.visitJumpInsn(200, label2);
            methodWriter.visitLabel(label7);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc(ParseContext.class));
            methodWriter.visitVarInsn(58, context.var("mark_context"));
            methodWriter.visitInsn(3);
            methodWriter.visitVarInsn(54, context.var("matchedCount"));
            _createInstance(context, methodWriter);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc(ParseContext.class));
            methodWriter.visitVarInsn(58, context.var(c.R));
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitVarInsn(25, context.var(c.R));
            methodWriter.visitVarInsn(25, context.var("instance"));
            methodWriter.visitVarInsn(25, 3);
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(" + ASMUtils.desc(ParseContext.class) + "Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc(ParseContext.class));
            methodWriter.visitVarInsn(58, context.var("childContext"));
            methodWriter.visitVarInsn(25, context.var("lexer"));
            methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
            methodWriter.visitLdcInsn(4);
            methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label3);
            int i4 = 3;
            methodWriter.visitInsn(3);
            methodWriter.visitIntInsn(54, context.var("matchStat"));
            int length = context.fieldInfoList.length;
            int i5 = 0;
            while (i5 < length) {
                methodWriter.visitInsn(i4);
                methodWriter.visitVarInsn(54, context.var("_asm_flag_" + (i5 / 32)));
                i5 += 32;
                i4 = 3;
            }
            methodWriter.visitVarInsn(25, context.var("lexer"));
            methodWriter.visitLdcInsn(Integer.valueOf(Feature.InitStringFieldAsEmpty.mask));
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(I)Z");
            methodWriter.visitIntInsn(54, context.var("initStringFieldAsEmpty"));
            int i6 = 0;
            while (i6 < length) {
                FieldInfo fieldInfo2 = context.fieldInfoList[i6];
                Class<?> cls2 = fieldInfo2.fieldClass;
                if (cls2 == Boolean.TYPE || cls2 == Byte.TYPE || cls2 == Short.TYPE || cls2 == Integer.TYPE) {
                    label2 = label2;
                    methodWriter.visitInsn(3);
                    methodWriter.visitVarInsn(54, context.var(fieldInfo2.name + "_asm"));
                } else if (cls2 == Long.TYPE) {
                    methodWriter.visitInsn(9);
                    methodWriter.visitVarInsn(55, context.var(fieldInfo2.name + "_asm", 2));
                    label2 = label2;
                } else if (cls2 == Float.TYPE) {
                    methodWriter.visitInsn(11);
                    methodWriter.visitVarInsn(56, context.var(fieldInfo2.name + "_asm"));
                    label2 = label2;
                } else if (cls2 == Double.TYPE) {
                    methodWriter.visitInsn(14);
                    methodWriter.visitVarInsn(57, context.var(fieldInfo2.name + "_asm", 2));
                    label2 = label2;
                } else {
                    if (cls2 == String.class) {
                        Label label8 = new Label();
                        Label label9 = new Label();
                        methodWriter.visitVarInsn(21, context.var("initStringFieldAsEmpty"));
                        methodWriter.visitJumpInsn(Opcodes.IFEQ, label9);
                        _setFlag(methodWriter, context, i6);
                        methodWriter.visitVarInsn(i3, context.var("lexer"));
                        label2 = label2;
                        methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "stringDefaultValue", "()Ljava/lang/String;");
                        methodWriter.visitJumpInsn(Opcodes.GOTO, label8);
                        methodWriter.visitLabel(label9);
                        methodWriter.visitInsn(1);
                        methodWriter.visitLabel(label8);
                    } else {
                        label2 = label2;
                        methodWriter.visitInsn(1);
                    }
                    methodWriter.visitTypeInsn(192, ASMUtils.type(cls2));
                    methodWriter.visitVarInsn(58, context.var(fieldInfo2.name + "_asm"));
                }
                i6++;
                i3 = 25;
            }
            for (int i7 = 0; i7 < length; i7++) {
                FieldInfo fieldInfo3 = context.fieldInfoList[i7];
                Class<?> cls3 = fieldInfo3.fieldClass;
                Type type2 = fieldInfo3.fieldType;
                Label label10 = new Label();
                if (cls3 == Boolean.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldBoolean", "([C)Z");
                    methodWriter.visitVarInsn(54, context.var(fieldInfo3.name + "_asm"));
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Byte.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                    methodWriter.visitVarInsn(54, context.var(fieldInfo3.name + "_asm"));
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Byte.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    Label label11 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label11);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    methodWriter.visitLabel(label11);
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Short.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                    methodWriter.visitVarInsn(54, context.var(fieldInfo3.name + "_asm"));
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Short.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    Label label12 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label12);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    methodWriter.visitLabel(label12);
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Integer.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                    methodWriter.visitVarInsn(54, context.var(fieldInfo3.name + "_asm"));
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Integer.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    Label label13 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label13);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    methodWriter.visitLabel(label13);
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Long.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldLong", "([C)J");
                    methodWriter.visitVarInsn(55, context.var(fieldInfo3.name + "_asm", 2));
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Long.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldLong", "([C)J");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    Label label14 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label14);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    methodWriter.visitLabel(label14);
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Float.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloat", "([C)F");
                    methodWriter.visitVarInsn(56, context.var(fieldInfo3.name + "_asm"));
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Float.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloat", "([C)F");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    Label label15 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label15);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    methodWriter.visitLabel(label15);
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Double.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDouble", "([C)D");
                    methodWriter.visitVarInsn(57, context.var(fieldInfo3.name + "_asm", 2));
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == Double.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDouble", "([C)D");
                    methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    Label label16 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label16);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    methodWriter.visitLabel(label16);
                    c2 = Typography.paragraph;
                    c = ':';
                } else if (cls3 == String.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldString", "([C)Ljava/lang/String;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    c = ':';
                    c2 = Typography.paragraph;
                } else if (cls3 == Date.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDate", "([C)Ljava/util/Date;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    c = ':';
                    c2 = Typography.paragraph;
                } else if (cls3 == UUID.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldUUID", "([C)Ljava/util/UUID;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    c = ':';
                    c2 = Typography.paragraph;
                } else if (cls3 == BigDecimal.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDecimal", "([C)Ljava/math/BigDecimal;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    c = ':';
                    c2 = Typography.paragraph;
                } else if (cls3 == BigInteger.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldBigInteger", "([C)Ljava/math/BigInteger;");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    c = ':';
                    c2 = Typography.paragraph;
                } else if (cls3 == int[].class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldIntArray", "([C)[I");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    c = ':';
                    c2 = Typography.paragraph;
                } else if (cls3 == float[].class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloatArray", "([C)[F");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    c = ':';
                    c2 = Typography.paragraph;
                } else if (cls3 == float[][].class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloatArray2", "([C)[[F");
                    methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                    c = ':';
                    c2 = Typography.paragraph;
                } else if (cls3.isEnum()) {
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(25, 0);
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                    _getFieldDeser(context, methodWriter, fieldInfo3);
                    methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "scanEnum", "(L" + JSONLexerBase + ";[C" + ASMUtils.desc(ObjectDeserializer.class) + ")Ljava/lang/Enum;");
                    methodWriter.visitTypeInsn(192, ASMUtils.type(cls3));
                    StringBuilder sb = new StringBuilder();
                    sb.append(fieldInfo3.name);
                    sb.append("_asm");
                    methodWriter.visitVarInsn(58, context.var(sb.toString()));
                    c = ':';
                    c2 = Typography.paragraph;
                } else {
                    if (Collection.class.isAssignableFrom(cls3)) {
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(25, 0);
                        methodWriter.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
                        Class<?> collectionItemClass = TypeUtils.getCollectionItemClass(type2);
                        if (collectionItemClass == String.class) {
                            methodWriter.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls3)));
                            c2 = Typography.paragraph;
                            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldStringArray", "([CLjava/lang/Class;)" + ASMUtils.desc(Collection.class));
                            c = ':';
                            methodWriter.visitVarInsn(58, context.var(fieldInfo3.name + "_asm"));
                        } else {
                            length = length;
                            methodWriter = methodWriter;
                            _deserialze_list_obj(context, methodWriter, label, fieldInfo3, cls3, collectionItemClass, i7);
                            if (i7 == length - 1) {
                                _deserialize_endCheck(context, methodWriter, label);
                            }
                        }
                    } else {
                        length = length;
                        methodWriter = methodWriter;
                        _deserialze_obj(context, methodWriter, label, fieldInfo3, cls3, i7);
                        if (i7 == length - 1) {
                            _deserialize_endCheck(context, methodWriter, label);
                        }
                    }
                }
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                Label label17 = new Label();
                methodWriter.visitJumpInsn(Opcodes.IFLE, label17);
                _setFlag(methodWriter, context, i7);
                methodWriter.visitLabel(label17);
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitInsn(89);
                methodWriter.visitVarInsn(54, context.var("matchStat"));
                methodWriter.visitLdcInsn(-1);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label);
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitJumpInsn(Opcodes.IFLE, label10);
                methodWriter.visitVarInsn(21, context.var("matchedCount"));
                methodWriter.visitInsn(4);
                methodWriter.visitInsn(96);
                methodWriter.visitVarInsn(54, context.var("matchedCount"));
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                methodWriter.visitLdcInsn(4);
                methodWriter.visitJumpInsn(Opcodes.IF_ICMPEQ, label4);
                methodWriter.visitLabel(label10);
                if (i7 == length - 1) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    methodWriter.visitLdcInsn(4);
                    methodWriter.visitJumpInsn(Opcodes.IF_ICMPNE, label);
                    length = length;
                    methodWriter = methodWriter;
                } else {
                    length = length;
                    methodWriter = methodWriter;
                }
            }
            methodWriter.visitLabel(label4);
            if (!context.clazz.isInterface() && !Modifier.isAbstract(context.clazz.getModifiers())) {
                _batchSet(context, methodWriter);
            }
            methodWriter.visitLabel(label3);
            _setContext(context, methodWriter);
            methodWriter.visitVarInsn(25, context.var("instance"));
            Method method = context.beanInfo.buildMethod;
            if (method != null) {
                methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(context.getInstClass()), method.getName(), "()" + ASMUtils.desc(method.getReturnType()));
                i = Opcodes.ARETURN;
            } else {
                i = Opcodes.ARETURN;
            }
            methodWriter.visitInsn(i);
            methodWriter.visitLabel(label);
            _batchSet(context, methodWriter);
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitVarInsn(25, 2);
            methodWriter.visitVarInsn(25, 3);
            methodWriter.visitVarInsn(25, context.var("instance"));
            methodWriter.visitVarInsn(21, 4);
            int i8 = length / 32;
            if (length == 0 || length % 32 == 0) {
                i2 = 1;
            } else {
                i8++;
                i2 = 1;
            }
            if (i8 == i2) {
                methodWriter.visitInsn(4);
            } else {
                methodWriter.visitIntInsn(16, i8);
            }
            methodWriter.visitIntInsn(188, 10);
            for (int i9 = 0; i9 < i8; i9++) {
                methodWriter.visitInsn(89);
                if (i9 == 0) {
                    methodWriter.visitInsn(3);
                } else if (i9 == 1) {
                    methodWriter.visitInsn(4);
                } else {
                    methodWriter.visitIntInsn(16, i9);
                }
                methodWriter.visitVarInsn(21, context.var("_asm_flag_" + i9));
                methodWriter.visitInsn(79);
            }
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "parseRest", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;I[I)Ljava/lang/Object;");
            methodWriter.visitTypeInsn(192, ASMUtils.type(context.clazz));
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitLabel(label2);
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitVarInsn(25, 2);
            methodWriter.visitVarInsn(25, 3);
            methodWriter.visitVarInsn(21, 4);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitMaxs(10, context.variantIndex);
            methodWriter.visitEnd();
        }
    }

    private void defineVarLexer(Context context, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, DefaultJSONParser, "lexer", ASMUtils.desc(JSONLexer.class));
        methodVisitor.visitTypeInsn(192, JSONLexerBase);
        methodVisitor.visitVarInsn(58, context.var("lexer"));
    }

    private void _createInstance(Context context, MethodVisitor methodVisitor) {
        Constructor<?> constructor = context.beanInfo.defaultConstructor;
        if (Modifier.isPublic(constructor.getModifiers())) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(context.getInstClass()));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(constructor.getDeclaringClass()), "<init>", "()V");
            methodVisitor.visitVarInsn(58, context.var("instance"));
            return;
        }
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(JavaBeanDeserializer.class), "clazz", "Ljava/lang/Class;");
        String type = ASMUtils.type(JavaBeanDeserializer.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, type, "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;");
        methodVisitor.visitTypeInsn(192, ASMUtils.type(context.getInstClass()));
        methodVisitor.visitVarInsn(58, context.var("instance"));
    }

    private void _batchSet(Context context, MethodVisitor methodVisitor) {
        _batchSet(context, methodVisitor, true);
    }

    private void _batchSet(Context context, MethodVisitor methodVisitor, boolean z) {
        int length = context.fieldInfoList.length;
        for (int i = 0; i < length; i++) {
            Label label = new Label();
            if (z) {
                _isFlag(methodVisitor, context, i, label);
            }
            _loadAndSet(context, methodVisitor, context.fieldInfoList[i]);
            if (z) {
                methodVisitor.visitLabel(label);
            }
        }
    }

    private void _loadAndSet(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Class<?> cls = fieldInfo.fieldClass;
        Type type = fieldInfo.fieldType;
        if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(21, context.var(fieldInfo.name + "_asm"));
            _set(context, methodVisitor, fieldInfo);
        } else if (cls == Byte.TYPE || cls == Short.TYPE || cls == Integer.TYPE || cls == Character.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(21, context.var(fieldInfo.name + "_asm"));
            _set(context, methodVisitor, fieldInfo);
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(22, context.var(fieldInfo.name + "_asm", 2));
            if (fieldInfo.method != null) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(context.getInstClass()), fieldInfo.method.getName(), ASMUtils.desc(fieldInfo.method));
                if (!fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                    methodVisitor.visitInsn(87);
                    return;
                }
                return;
            }
            methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(23, context.var(fieldInfo.name + "_asm"));
            _set(context, methodVisitor, fieldInfo);
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(24, context.var(fieldInfo.name + "_asm", 2));
            _set(context, methodVisitor, fieldInfo);
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            _set(context, methodVisitor, fieldInfo);
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            _set(context, methodVisitor, fieldInfo);
        } else if (Collection.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            if (TypeUtils.getCollectionItemClass(type) == String.class) {
                methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
                methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
            } else {
                methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            }
            _set(context, methodVisitor, fieldInfo);
        } else {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
            _set(context, methodVisitor, fieldInfo);
        }
    }

    private void _set(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            methodVisitor.visitMethodInsn(method.getDeclaringClass().isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(fieldInfo.declaringClass), method.getName(), ASMUtils.desc(method));
            if (!fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                methodVisitor.visitInsn(87);
                return;
            }
            return;
        }
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
    }

    private void _setContext(Context context, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(c.R));
        String str = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "setContext", "(" + ASMUtils.desc(ParseContext.class) + ")V");
        Label label = new Label();
        methodVisitor.visitVarInsn(25, context.var("childContext"));
        methodVisitor.visitJumpInsn(Opcodes.IFNULL, label);
        methodVisitor.visitVarInsn(25, context.var("childContext"));
        methodVisitor.visitVarInsn(25, context.var("instance"));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(ParseContext.class), "object", "Ljava/lang/Object;");
        methodVisitor.visitLabel(label);
    }

    private void _deserialize_endCheck(Context context, MethodVisitor methodVisitor, Label label) {
        methodVisitor.visitIntInsn(21, context.var("matchedCount"));
        methodVisitor.visitJumpInsn(Opcodes.IFLE, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        _quickNextTokenComma(context, methodVisitor);
    }

    private void _deserialze_list_obj(Context context, MethodVisitor methodVisitor, Label label, FieldInfo fieldInfo, Class<?> cls, Class<?> cls2, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        Label label2 = new Label();
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
        _setFlag(methodVisitor, context, i);
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(8);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label3);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label3);
        Label label4 = new Label();
        Label label5 = new Label();
        Label label6 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(21);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label5);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        _newCollection(methodVisitor, cls, i, true);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label5);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, label6);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(12);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        _newCollection(methodVisitor, cls, i, false);
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        _getCollectionFieldItemDeser(context, methodVisitor, fieldInfo, cls2);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls2)));
        methodVisitor.visitInsn(3);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        String type = ASMUtils.type(ObjectDeserializer.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, type, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, context.var("list_item_value"));
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitVarInsn(25, context.var("list_item_value"));
        if (cls.isInterface()) {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(cls), "add", "(Ljava/lang/Object;)Z");
        } else {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(cls), "add", "(Ljava/lang/Object;)Z");
        }
        methodVisitor.visitInsn(87);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label6);
        _newCollection(methodVisitor, cls, i, false);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        boolean isPrimitive2 = ParserConfig.isPrimitive2(fieldInfo.fieldClass);
        _getCollectionFieldItemDeser(context, methodVisitor, fieldInfo, cls2);
        if (isPrimitive2) {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(ObjectDeserializer.class), "getFastMatchToken", "()I");
            methodVisitor.visitVarInsn(54, context.var("fastMatchToken"));
            methodVisitor.visitVarInsn(25, context.var("lexer"));
            methodVisitor.visitVarInsn(21, context.var("fastMatchToken"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
            i3 = 25;
            i2 = 1;
        } else {
            methodVisitor.visitInsn(87);
            methodVisitor.visitLdcInsn(12);
            methodVisitor.visitVarInsn(54, context.var("fastMatchToken"));
            _quickNextToken(context, methodVisitor, 12);
            i3 = 25;
            i2 = 1;
        }
        methodVisitor.visitVarInsn(i3, i2);
        String str = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getContext", "()" + ASMUtils.desc(ParseContext.class));
        methodVisitor.visitVarInsn(58, context.var("listContext"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitLdcInsn(fieldInfo.name);
        String str2 = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc(ParseContext.class));
        methodVisitor.visitInsn(87);
        Label label7 = new Label();
        Label label8 = new Label();
        methodVisitor.visitInsn(3);
        methodVisitor.visitVarInsn(54, context.var(ai.aA));
        methodVisitor.visitLabel(label7);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, label8);
        methodVisitor.visitVarInsn(25, 0);
        String str3 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str3, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls2)));
        methodVisitor.visitVarInsn(21, context.var(ai.aA));
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        String type2 = ASMUtils.type(ObjectDeserializer.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, type2, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, context.var("list_item_value"));
        methodVisitor.visitIincInsn(context.var(ai.aA), 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitVarInsn(25, context.var("list_item_value"));
        if (cls.isInterface()) {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(cls), "add", "(Ljava/lang/Object;)Z");
            i4 = 87;
        } else {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(cls), "add", "(Ljava/lang/Object;)Z");
            i4 = 87;
        }
        methodVisitor.visitInsn(i4);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "checkListResolve", "(Ljava/util/Collection;)V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label7);
        if (isPrimitive2) {
            methodVisitor.visitVarInsn(25, context.var("lexer"));
            methodVisitor.visitVarInsn(21, context.var("fastMatchToken"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
            i5 = Opcodes.GOTO;
        } else {
            _quickNextToken(context, methodVisitor, 12);
            i5 = Opcodes.GOTO;
        }
        methodVisitor.visitJumpInsn(i5, label7);
        methodVisitor.visitLabel(label8);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var("listContext"));
        String str4 = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, "setContext", "(" + ASMUtils.desc(ParseContext.class) + ")V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        _quickNextTokenComma(context, methodVisitor);
        methodVisitor.visitLabel(label2);
    }

    private void _quickNextToken(Context context, MethodVisitor methodVisitor, int i) {
        Label label = new Label();
        Label label2 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        if (i == 12) {
            methodVisitor.visitVarInsn(16, 123);
        } else if (i == 14) {
            methodVisitor.visitVarInsn(16, 91);
        } else {
            throw new IllegalStateException();
        }
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(Integer.valueOf(i));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(Integer.valueOf(i));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        methodVisitor.visitLabel(label2);
    }

    private void _quickNextTokenComma(Context context, MethodVisitor methodVisitor) {
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        Label label5 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(54, context.var("ch"));
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label2);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 125);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label3);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 93);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label4);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 26);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(20);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "()V");
        methodVisitor.visitLabel(label5);
    }

    private void _getCollectionFieldItemDeser(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        String str2 = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "getConfig", "()" + ASMUtils.desc(ParserConfig.class));
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(cls)));
        String type = ASMUtils.type(ParserConfig.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type, "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc(ObjectDeserializer.class));
        String str3 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, str3, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        String str4 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str4, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class));
    }

    private void _newCollection(MethodVisitor methodVisitor, Class<?> cls, int i, boolean z) {
        if (cls.isAssignableFrom(ArrayList.class) && !z) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V");
        } else if (cls.isAssignableFrom(LinkedList.class) && !z) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(LinkedList.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(LinkedList.class), "<init>", "()V");
        } else if (cls.isAssignableFrom(HashSet.class)) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(HashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else if (cls.isAssignableFrom(TreeSet.class)) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(TreeSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(TreeSet.class), "<init>", "()V");
        } else if (cls.isAssignableFrom(LinkedHashSet.class)) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(LinkedHashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(LinkedHashSet.class), "<init>", "()V");
        } else if (z) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, ASMUtils.type(HashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitLdcInsn(Integer.valueOf(i));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/reflect/Type;)Ljava/util/Collection;");
        }
        methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
    }

    private void _deserialze_obj(Context context, MethodVisitor methodVisitor, Label label, FieldInfo fieldInfo, Class<?> cls, int i) {
        Label label2 = new Label();
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str, fieldInfo.name + "_asm_prefix__", "[C");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFNE, label2);
        methodVisitor.visitInsn(1);
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
        methodVisitor.visitLabel(label2);
        _setFlag(methodVisitor, context, i);
        methodVisitor.visitVarInsn(21, context.var("matchedCount"));
        methodVisitor.visitInsn(4);
        methodVisitor.visitInsn(96);
        methodVisitor.visitVarInsn(54, context.var("matchedCount"));
        _deserObject(context, methodVisitor, fieldInfo, cls, i);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getResolveStatus", "()I");
        methodVisitor.visitLdcInsn(1);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label3);
        methodVisitor.visitVarInsn(25, 1);
        String str2 = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "getLastResolveTask", "()" + ASMUtils.desc(DefaultJSONParser.ResolveTask.class));
        methodVisitor.visitVarInsn(58, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 1);
        String str3 = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, "getContext", "()" + ASMUtils.desc(ParseContext.class));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "ownerContext", ASMUtils.desc(ParseContext.class));
        methodVisitor.visitVarInsn(25, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(fieldInfo.name);
        String type = ASMUtils.type(JavaBeanDeserializer.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type, "getFieldDeserializer", "(Ljava/lang/String;)" + ASMUtils.desc(FieldDeserializer.class));
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "fieldDeserializer", ASMUtils.desc(FieldDeserializer.class));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(0);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setResolveStatus", "(I)V");
        methodVisitor.visitLabel(label3);
    }

    private void _deserObject(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls, int i) {
        _getFieldDeser(context, methodVisitor, fieldInfo);
        Label label = new Label();
        Label label2 = new Label();
        if ((fieldInfo.parserFeatures & Feature.SupportArrayToBean.mask) != 0) {
            methodVisitor.visitInsn(89);
            methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, ASMUtils.type(JavaBeanDeserializer.class));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            methodVisitor.visitTypeInsn(192, ASMUtils.type(JavaBeanDeserializer.class));
            methodVisitor.visitVarInsn(25, 1);
            if (fieldInfo.fieldType instanceof Class) {
                methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            } else {
                methodVisitor.visitVarInsn(25, 0);
                methodVisitor.visitLdcInsn(Integer.valueOf(i));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            }
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.parserFeatures));
            String type = ASMUtils.type(JavaBeanDeserializer.class);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
            methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
            methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            methodVisitor.visitLabel(label);
        }
        methodVisitor.visitVarInsn(25, 1);
        if (fieldInfo.fieldType instanceof Class) {
            methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitLdcInsn(Integer.valueOf(i));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
        }
        methodVisitor.visitLdcInsn(fieldInfo.name);
        String type2 = ASMUtils.type(ObjectDeserializer.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, type2, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
        methodVisitor.visitVarInsn(58, context.var(fieldInfo.name + "_asm"));
        methodVisitor.visitLabel(label2);
    }

    private void _getFieldDeser(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str, fieldInfo.name + "_asm_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        String str2 = DefaultJSONParser;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "getConfig", "()" + ASMUtils.desc(ParserConfig.class));
        methodVisitor.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        String type = ASMUtils.type(ParserConfig.class);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type, "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc(ObjectDeserializer.class));
        String str3 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, str3, fieldInfo.name + "_asm_deser__", ASMUtils.desc(ObjectDeserializer.class));
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        String str4 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str4, fieldInfo.name + "_asm_deser__", ASMUtils.desc(ObjectDeserializer.class));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Context {
        static final int fieldName = 3;
        static final int parser = 1;
        static final int type = 2;
        private final JavaBeanInfo beanInfo;
        private final String className;
        private final Class<?> clazz;
        private FieldInfo[] fieldInfoList;
        private int variantIndex;
        private final Map<String, Integer> variants = new HashMap();

        public Context(String str, ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, int i) {
            this.variantIndex = -1;
            this.className = str;
            this.clazz = javaBeanInfo.clazz;
            this.variantIndex = i;
            this.beanInfo = javaBeanInfo;
            this.fieldInfoList = javaBeanInfo.fields;
        }

        public Class<?> getInstClass() {
            Class<?> cls = this.beanInfo.builderClass;
            return cls == null ? this.clazz : cls;
        }

        public int var(String str, int i) {
            if (this.variants.get(str) == null) {
                this.variants.put(str, Integer.valueOf(this.variantIndex));
                this.variantIndex += i;
            }
            return this.variants.get(str).intValue();
        }

        public int var(String str) {
            if (this.variants.get(str) == null) {
                Map<String, Integer> map = this.variants;
                int i = this.variantIndex;
                this.variantIndex = i + 1;
                map.put(str, Integer.valueOf(i));
            }
            return this.variants.get(str).intValue();
        }
    }

    private void _init(ClassWriter classWriter, Context context) {
        int length = context.fieldInfoList.length;
        for (int i = 0; i < length; i++) {
            FieldInfo fieldInfo = context.fieldInfoList[i];
            new FieldWriter(classWriter, 1, fieldInfo.name + "_asm_prefix__", "[C").visitEnd();
        }
        int length2 = context.fieldInfoList.length;
        for (int i2 = 0; i2 < length2; i2++) {
            FieldInfo fieldInfo2 = context.fieldInfoList[i2];
            Class<?> cls = fieldInfo2.fieldClass;
            if (!cls.isPrimitive()) {
                if (Collection.class.isAssignableFrom(cls)) {
                    new FieldWriter(classWriter, 1, fieldInfo2.name + "_asm_list_item_deser__", ASMUtils.desc(ObjectDeserializer.class)).visitEnd();
                } else {
                    new FieldWriter(classWriter, 1, fieldInfo2.name + "_asm_deser__", ASMUtils.desc(ObjectDeserializer.class)).visitEnd();
                }
            }
        }
        MethodWriter methodWriter = new MethodWriter(classWriter, 1, "<init>", "(" + ASMUtils.desc(ParserConfig.class) + ASMUtils.desc(JavaBeanInfo.class) + ")V", null, null);
        methodWriter.visitVarInsn(25, 0);
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitVarInsn(25, 2);
        methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "<init>", "(" + ASMUtils.desc(ParserConfig.class) + ASMUtils.desc(JavaBeanInfo.class) + ")V");
        int length3 = context.fieldInfoList.length;
        for (int i3 = 0; i3 < length3; i3++) {
            FieldInfo fieldInfo3 = context.fieldInfoList[i3];
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitLdcInsn("\"" + fieldInfo3.name + "\":");
            methodWriter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C");
            methodWriter.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo3.name + "_asm_prefix__", "[C");
        }
        methodWriter.visitInsn(Opcodes.RETURN);
        methodWriter.visitMaxs(4, 4);
        methodWriter.visitEnd();
    }

    private void _createInstance(ClassWriter classWriter, Context context) {
        if (Modifier.isPublic(context.beanInfo.defaultConstructor.getModifiers())) {
            MethodWriter methodWriter = new MethodWriter(classWriter, 1, "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;", null, null);
            methodWriter.visitTypeInsn(Opcodes.NEW, ASMUtils.type(context.getInstClass()));
            methodWriter.visitInsn(89);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(context.getInstClass()), "<init>", "()V");
            methodWriter.visitInsn(Opcodes.ARETURN);
            methodWriter.visitMaxs(3, 3);
            methodWriter.visitEnd();
        }
    }
}
