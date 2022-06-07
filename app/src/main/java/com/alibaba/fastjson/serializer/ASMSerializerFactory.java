package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.core.JsonPointer;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class ASMSerializerFactory implements Opcodes {
    protected final ASMClassLoader classLoader = new ASMClassLoader();
    private final AtomicLong seed = new AtomicLong();
    static final String JSONSerializer = ASMUtils.type(JSONSerializer.class);
    static final String ObjectSerializer = ASMUtils.type(ObjectSerializer.class);
    static final String ObjectSerializer_desc = "L" + ObjectSerializer + ";";
    static final String SerializeWriter = ASMUtils.type(SerializeWriter.class);
    static final String SerializeWriter_desc = "L" + SerializeWriter + ";";
    static final String JavaBeanSerializer = ASMUtils.type(JavaBeanSerializer.class);
    static final String JavaBeanSerializer_desc = "L" + ASMUtils.type(JavaBeanSerializer.class) + ";";
    static final String SerialContext_desc = ASMUtils.desc(SerialContext.class);
    static final String SerializeFilterable_desc = ASMUtils.desc(SerializeFilterable.class);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Context {
        static final int features = 5;
        static int fieldName = 6;
        static final int obj = 2;
        static int original = 7;
        static final int paramFieldName = 3;
        static final int paramFieldType = 4;
        static int processValue = 8;
        static final int serializer = 1;
        private final SerializeBeanInfo beanInfo;
        private final String className;
        private final FieldInfo[] getters;
        private final boolean nonContext;
        private final boolean writeDirect;
        private Map<String, Integer> variants = new HashMap();
        private int variantIndex = 9;

        public Context(FieldInfo[] fieldInfoArr, SerializeBeanInfo serializeBeanInfo, String str, boolean z, boolean z2) {
            this.getters = fieldInfoArr;
            this.className = str;
            this.beanInfo = serializeBeanInfo;
            this.writeDirect = z;
            this.nonContext = z2 || serializeBeanInfo.beanType.isEnum();
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

        public int var(String str, int i) {
            if (this.variants.get(str) == null) {
                this.variants.put(str, Integer.valueOf(this.variantIndex));
                this.variantIndex += i;
            }
            return this.variants.get(str).intValue();
        }

        public int getFieldOrinal(String str) {
            int length = this.getters.length;
            for (int i = 0; i < length; i++) {
                if (this.getters[i].name.equals(str)) {
                    return i;
                }
            }
            return -1;
        }
    }

    public JavaBeanSerializer createJavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) throws Exception {
        String str;
        String str2;
        boolean z;
        int i;
        String str3;
        boolean z2;
        boolean z3;
        boolean z4;
        String str4;
        boolean z5;
        String str5;
        char c;
        Class<?> cls = serializeBeanInfo.beanType;
        if (!cls.isPrimitive()) {
            JSONType jSONType = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class);
            FieldInfo[] fieldInfoArr = serializeBeanInfo.fields;
            for (FieldInfo fieldInfo : fieldInfoArr) {
                if (fieldInfo.field == null && fieldInfo.method != null && fieldInfo.method.getDeclaringClass().isInterface()) {
                    return new JavaBeanSerializer(serializeBeanInfo);
                }
            }
            FieldInfo[] fieldInfoArr2 = serializeBeanInfo.sortedFields;
            boolean z6 = serializeBeanInfo.sortedFields == serializeBeanInfo.fields;
            if (fieldInfoArr2.length > 256) {
                return new JavaBeanSerializer(serializeBeanInfo);
            }
            for (FieldInfo fieldInfo2 : fieldInfoArr2) {
                if (!ASMUtils.checkName(fieldInfo2.getMember().getName())) {
                    return new JavaBeanSerializer(serializeBeanInfo);
                }
            }
            String str6 = "ASMSerializer_" + this.seed.incrementAndGet() + "_" + cls.getSimpleName();
            Package r2 = ASMSerializerFactory.class.getPackage();
            if (r2 != null) {
                String name = r2.getName();
                str = name.replace('.', JsonPointer.SEPARATOR) + "/" + str6;
                str2 = name + "." + str6;
            } else {
                str2 = str6;
                str = str2;
            }
            ASMSerializerFactory.class.getPackage().getName();
            ClassWriter classWriter = new ClassWriter();
            classWriter.visit(49, 33, str, JavaBeanSerializer, new String[]{ObjectSerializer});
            int length = fieldInfoArr2.length;
            for (int i2 = 0; i2 < length; i2++) {
                FieldInfo fieldInfo3 = fieldInfoArr2[i2];
                if (fieldInfo3.fieldClass.isPrimitive()) {
                    length = length;
                } else if (fieldInfo3.fieldClass == String.class) {
                    length = length;
                } else {
                    length = length;
                    new FieldWriter(classWriter, 1, fieldInfo3.name + "_asm_fieldType", "Ljava/lang/reflect/Type;").visitEnd();
                    if (List.class.isAssignableFrom(fieldInfo3.fieldClass)) {
                        new FieldWriter(classWriter, 1, fieldInfo3.name + "_asm_list_item_ser_", ObjectSerializer_desc).visitEnd();
                    }
                    new FieldWriter(classWriter, 1, fieldInfo3.name + "_asm_ser_", ObjectSerializer_desc).visitEnd();
                }
            }
            MethodWriter methodWriter = new MethodWriter(classWriter, 1, "<init>", "(" + ASMUtils.desc(SerializeBeanInfo.class) + ")V", null, null);
            int i3 = 25;
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitVarInsn(25, 1);
            methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "<init>", "(" + ASMUtils.desc(SerializeBeanInfo.class) + ")V");
            int i4 = 0;
            while (i4 < fieldInfoArr2.length) {
                FieldInfo fieldInfo4 = fieldInfoArr2[i4];
                if (fieldInfo4.fieldClass.isPrimitive()) {
                    classWriter = classWriter;
                } else if (fieldInfo4.fieldClass == String.class) {
                    classWriter = classWriter;
                } else {
                    methodWriter.visitVarInsn(i3, 0);
                    if (fieldInfo4.method != null) {
                        methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo4.declaringClass)));
                        methodWriter.visitLdcInsn(fieldInfo4.method.getName());
                        classWriter = classWriter;
                        methodWriter.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(ASMUtils.class), "getMethodType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
                    } else {
                        classWriter = classWriter;
                        methodWriter.visitVarInsn(i3, 0);
                        methodWriter.visitLdcInsn(Integer.valueOf(i4));
                        methodWriter.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "getFieldType", "(I)Ljava/lang/reflect/Type;");
                    }
                    methodWriter.visitFieldInsn(Opcodes.PUTFIELD, str, fieldInfo4.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                i4++;
                i3 = 25;
            }
            ClassWriter classWriter2 = classWriter;
            int i5 = Opcodes.RETURN;
            methodWriter.visitInsn(Opcodes.RETURN);
            methodWriter.visitMaxs(4, 4);
            methodWriter.visitEnd();
            if (jSONType != null) {
                for (SerializerFeature serializerFeature : jSONType.serialzeFeatures()) {
                    if (serializerFeature == SerializerFeature.DisableCircularReferenceDetect) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            int i6 = 0;
            while (i6 < 3) {
                if (i6 == 0) {
                    str4 = "write";
                    z4 = z;
                    z5 = true;
                } else if (i6 == 1) {
                    str4 = "writeNormal";
                    z4 = z;
                    z5 = false;
                } else {
                    str4 = "writeDirectNonContext";
                    z5 = true;
                    z4 = true;
                }
                Context context = new Context(fieldInfoArr2, serializeBeanInfo, str, z5, z4);
                MethodWriter methodWriter2 = new MethodWriter(classWriter2, 1, str4, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
                Label label = new Label();
                methodWriter2.visitVarInsn(25, 2);
                methodWriter2.visitJumpInsn(Opcodes.IFNONNULL, label);
                methodWriter2.visitVarInsn(25, 1);
                methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeNull", "()V");
                methodWriter2.visitInsn(i5);
                methodWriter2.visitLabel(label);
                methodWriter2.visitVarInsn(25, 1);
                methodWriter2.visitFieldInsn(Opcodes.GETFIELD, JSONSerializer, "out", SerializeWriter_desc);
                methodWriter2.visitVarInsn(58, context.var("out"));
                if (z6) {
                    str5 = str;
                } else if (context.writeDirect) {
                    str5 = str;
                } else if (jSONType == null || jSONType.alphabetic()) {
                    Label label2 = new Label();
                    methodWriter2.visitVarInsn(25, context.var("out"));
                    methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isSortField", "()Z");
                    methodWriter2.visitJumpInsn(Opcodes.IFNE, label2);
                    methodWriter2.visitVarInsn(25, 0);
                    methodWriter2.visitVarInsn(25, 1);
                    methodWriter2.visitVarInsn(25, 2);
                    methodWriter2.visitVarInsn(25, 3);
                    methodWriter2.visitVarInsn(25, 4);
                    methodWriter2.visitVarInsn(21, 5);
                    str5 = str;
                    methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodWriter2.visitInsn(Opcodes.RETURN);
                    methodWriter2.visitLabel(label2);
                } else {
                    str5 = str;
                }
                if (!context.writeDirect || z4) {
                    jSONType = jSONType;
                    c = 4;
                } else {
                    Label label3 = new Label();
                    Label label4 = new Label();
                    methodWriter2.visitVarInsn(25, 0);
                    methodWriter2.visitVarInsn(25, 1);
                    String str7 = JavaBeanSerializer;
                    StringBuilder sb = new StringBuilder();
                    jSONType = jSONType;
                    sb.append("(L");
                    sb.append(JSONSerializer);
                    sb.append(";)Z");
                    methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str7, "writeDirect", sb.toString());
                    methodWriter2.visitJumpInsn(Opcodes.IFNE, label4);
                    methodWriter2.visitVarInsn(25, 0);
                    methodWriter2.visitVarInsn(25, 1);
                    methodWriter2.visitVarInsn(25, 2);
                    methodWriter2.visitVarInsn(25, 3);
                    methodWriter2.visitVarInsn(25, 4);
                    methodWriter2.visitVarInsn(21, 5);
                    methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "writeNormal", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodWriter2.visitInsn(Opcodes.RETURN);
                    methodWriter2.visitLabel(label4);
                    methodWriter2.visitVarInsn(25, context.var("out"));
                    methodWriter2.visitLdcInsn(Integer.valueOf(SerializerFeature.DisableCircularReferenceDetect.mask));
                    methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                    methodWriter2.visitJumpInsn(Opcodes.IFEQ, label3);
                    methodWriter2.visitVarInsn(25, 0);
                    methodWriter2.visitVarInsn(25, 1);
                    methodWriter2.visitVarInsn(25, 2);
                    methodWriter2.visitVarInsn(25, 3);
                    c = 4;
                    methodWriter2.visitVarInsn(25, 4);
                    methodWriter2.visitVarInsn(21, 5);
                    methodWriter2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "writeDirectNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodWriter2.visitInsn(Opcodes.RETURN);
                    methodWriter2.visitLabel(label3);
                }
                methodWriter2.visitVarInsn(25, 2);
                methodWriter2.visitTypeInsn(192, ASMUtils.type(cls));
                methodWriter2.visitVarInsn(58, context.var("entity"));
                generateWriteMethod(cls, methodWriter2, fieldInfoArr2, context);
                methodWriter2.visitInsn(Opcodes.RETURN);
                methodWriter2.visitMaxs(7, context.variantIndex + 2);
                methodWriter2.visitEnd();
                i6++;
                str = str5;
                classWriter2 = classWriter2;
                str2 = str2;
                i5 = Opcodes.RETURN;
            }
            if (!z6) {
                Context context2 = new Context(fieldInfoArr2, serializeBeanInfo, str, false, z);
                i = 3;
                MethodWriter methodWriter3 = new MethodWriter(classWriter2, 1, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
                methodWriter3.visitVarInsn(25, 1);
                methodWriter3.visitFieldInsn(Opcodes.GETFIELD, JSONSerializer, "out", SerializeWriter_desc);
                methodWriter3.visitVarInsn(58, context2.var("out"));
                methodWriter3.visitVarInsn(25, 2);
                methodWriter3.visitTypeInsn(192, ASMUtils.type(cls));
                methodWriter3.visitVarInsn(58, context2.var("entity"));
                generateWriteMethod(cls, methodWriter3, fieldInfoArr, context2);
                methodWriter3.visitInsn(Opcodes.RETURN);
                methodWriter3.visitMaxs(7, context2.variantIndex + 2);
                methodWriter3.visitEnd();
            } else {
                i = 3;
            }
            int i7 = 0;
            while (i7 < i) {
                if (i7 == 0) {
                    str3 = "writeAsArray";
                    z2 = z;
                    z3 = true;
                } else if (i7 == 1) {
                    str3 = "writeAsArrayNormal";
                    z2 = z;
                    z3 = false;
                } else {
                    str3 = "writeAsArrayNonContext";
                    z3 = true;
                    z2 = true;
                }
                Context context3 = new Context(fieldInfoArr2, serializeBeanInfo, str, z3, z2);
                MethodWriter methodWriter4 = new MethodWriter(classWriter2, 1, str3, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
                methodWriter4.visitVarInsn(25, 1);
                methodWriter4.visitFieldInsn(Opcodes.GETFIELD, JSONSerializer, "out", SerializeWriter_desc);
                methodWriter4.visitVarInsn(58, context3.var("out"));
                methodWriter4.visitVarInsn(25, 2);
                methodWriter4.visitTypeInsn(192, ASMUtils.type(cls));
                methodWriter4.visitVarInsn(58, context3.var("entity"));
                generateWriteAsArray(cls, methodWriter4, fieldInfoArr2, context3);
                methodWriter4.visitInsn(Opcodes.RETURN);
                methodWriter4.visitMaxs(7, context3.variantIndex + 2);
                methodWriter4.visitEnd();
                i7++;
                i = i;
            }
            byte[] byteArray = classWriter2.toByteArray();
            return (JavaBeanSerializer) this.classLoader.defineClassPublic(str2, byteArray, 0, byteArray.length).getConstructor(SerializeBeanInfo.class).newInstance(serializeBeanInfo);
        }
        throw new JSONException("unsupportd class " + cls.getName());
    }

    private void generateWriteAsArray(Class<?> cls, MethodVisitor methodVisitor, FieldInfo[] fieldInfoArr, Context context) throws Exception {
        int i;
        boolean z;
        char c;
        int i2;
        int i3;
        java.lang.reflect.Type type;
        Class<?> cls2;
        int i4;
        int i5;
        int i6;
        int i7;
        Label label;
        ASMSerializerFactory aSMSerializerFactory = this;
        FieldInfo[] fieldInfoArr2 = fieldInfoArr;
        Label label2 = new Label();
        int i8 = 25;
        methodVisitor.visitVarInsn(25, 1);
        int i9 = 0;
        methodVisitor.visitVarInsn(25, 0);
        int i10 = Opcodes.INVOKEVIRTUAL;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "hasPropertyFilters", "(" + SerializeFilterable_desc + ")Z");
        methodVisitor.visitJumpInsn(Opcodes.IFNE, label2);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, 3);
        int i11 = 4;
        methodVisitor.visitVarInsn(25, 4);
        methodVisitor.visitVarInsn(21, 5);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "writeNoneASM", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("out"));
        int i12 = 16;
        methodVisitor.visitVarInsn(16, 91);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        int length = fieldInfoArr2.length;
        int i13 = 93;
        if (length == 0) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 93);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            return;
        }
        int i14 = 0;
        while (i14 < length) {
            if (i14 != length - 1) {
                i13 = 44;
            }
            FieldInfo fieldInfo = fieldInfoArr2[i14];
            Class<?> cls3 = fieldInfo.fieldClass;
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (cls3 == Byte.TYPE || cls3 == Short.TYPE || cls3 == Integer.TYPE) {
                aSMSerializerFactory = aSMSerializerFactory;
                i8 = i8;
                i9 = i9;
                length = length;
                i = i14;
                c = 21;
                z = true;
                methodVisitor.visitVarInsn(i8, context.var("out"));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                String str = SerializeWriter;
                i10 = Opcodes.INVOKEVIRTUAL;
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeInt", "(I)V");
                i12 = 16;
                methodVisitor.visitVarInsn(16, i13);
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            } else if (cls3 == Long.TYPE) {
                methodVisitor.visitVarInsn(i8, context.var("out"));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "writeLong", "(J)V");
                methodVisitor.visitVarInsn(i12, i13);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "write", "(I)V");
                aSMSerializerFactory = aSMSerializerFactory;
                i12 = i12;
                i8 = i8;
                i9 = i9;
                length = length;
                i10 = i10;
                i = i14;
                c = 21;
                z = true;
            } else if (cls3 == Float.TYPE) {
                methodVisitor.visitVarInsn(i8, context.var("out"));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitInsn(i11);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "writeFloat", "(FZ)V");
                methodVisitor.visitVarInsn(i12, i13);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "write", "(I)V");
                aSMSerializerFactory = aSMSerializerFactory;
                i12 = i12;
                i8 = i8;
                i9 = i9;
                length = length;
                i10 = i10;
                i = i14;
                c = 21;
                z = true;
            } else if (cls3 == Double.TYPE) {
                methodVisitor.visitVarInsn(i8, context.var("out"));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitInsn(i11);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "writeDouble", "(DZ)V");
                methodVisitor.visitVarInsn(i12, i13);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "write", "(I)V");
                aSMSerializerFactory = aSMSerializerFactory;
                i12 = i12;
                i8 = i8;
                i9 = i9;
                length = length;
                i10 = i10;
                i = i14;
                c = 21;
                z = true;
            } else if (cls3 == Boolean.TYPE) {
                methodVisitor.visitVarInsn(i8, context.var("out"));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "write", "(Z)V");
                methodVisitor.visitVarInsn(i12, i13);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "write", "(I)V");
                aSMSerializerFactory = aSMSerializerFactory;
                i12 = i12;
                i8 = i8;
                i9 = i9;
                length = length;
                i10 = i10;
                i = i14;
                c = 21;
                z = true;
            } else if (cls3 == Character.TYPE) {
                methodVisitor.visitVarInsn(i8, context.var("out"));
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "toString", "(C)Ljava/lang/String;");
                methodVisitor.visitVarInsn(i12, i13);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
                aSMSerializerFactory = aSMSerializerFactory;
                i12 = i12;
                i8 = i8;
                i9 = i9;
                length = length;
                i10 = i10;
                i = i14;
                c = 21;
                z = true;
            } else if (cls3 == String.class) {
                methodVisitor.visitVarInsn(i8, context.var("out"));
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitVarInsn(i12, i13);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
                aSMSerializerFactory = aSMSerializerFactory;
                i12 = i12;
                i8 = i8;
                i9 = i9;
                length = length;
                i10 = i10;
                i = i14;
                c = 21;
                z = true;
            } else if (cls3.isEnum()) {
                methodVisitor.visitVarInsn(i8, context.var("out"));
                methodVisitor.visitInsn(89);
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "writeEnum", "(Ljava/lang/Enum;)V");
                methodVisitor.visitVarInsn(i12, i13);
                methodVisitor.visitMethodInsn(i10, SerializeWriter, "write", "(I)V");
                aSMSerializerFactory = aSMSerializerFactory;
                i12 = i12;
                i8 = i8;
                i9 = i9;
                length = length;
                i10 = i10;
                i = i14;
                c = 21;
                z = true;
            } else if (List.class.isAssignableFrom(cls3)) {
                java.lang.reflect.Type type2 = fieldInfo.fieldType;
                if (type2 instanceof Class) {
                    type = Object.class;
                } else {
                    type = ((ParameterizedType) type2).getActualTypeArguments()[i9];
                }
                if (type instanceof Class) {
                    cls2 = (Class) type;
                    if (cls2 == Object.class) {
                        cls2 = null;
                    }
                } else {
                    cls2 = null;
                }
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitTypeInsn(192, "java/util/List");
                methodVisitor.visitVarInsn(58, context.var("list"));
                if (cls2 != String.class || !context.writeDirect) {
                    Label label3 = new Label();
                    Label label4 = new Label();
                    methodVisitor.visitVarInsn(i8, context.var("list"));
                    methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label4);
                    methodVisitor.visitVarInsn(i8, context.var("out"));
                    methodVisitor.visitMethodInsn(i10, SerializeWriter, "writeNull", "()V");
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
                    methodVisitor.visitLabel(label4);
                    methodVisitor.visitVarInsn(25, context.var("list"));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
                    methodVisitor.visitVarInsn(54, context.var("size"));
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitVarInsn(16, 91);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
                    Label label5 = new Label();
                    Label label6 = new Label();
                    Label label7 = new Label();
                    methodVisitor.visitInsn(3);
                    methodVisitor.visitVarInsn(54, context.var(ai.aA));
                    methodVisitor.visitLabel(label5);
                    methodVisitor.visitVarInsn(21, context.var(ai.aA));
                    methodVisitor.visitVarInsn(21, context.var("size"));
                    methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label7);
                    methodVisitor.visitVarInsn(21, context.var(ai.aA));
                    methodVisitor.visitJumpInsn(Opcodes.IFEQ, label6);
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    methodVisitor.visitVarInsn(16, 44);
                    length = length;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
                    methodVisitor.visitLabel(label6);
                    methodVisitor.visitVarInsn(25, context.var("list"));
                    methodVisitor.visitVarInsn(21, context.var(ai.aA));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", BluetoothConstants.GET, "(I)Ljava/lang/Object;");
                    methodVisitor.visitVarInsn(58, context.var("list_item"));
                    Label label8 = new Label();
                    Label label9 = new Label();
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label9);
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    i = i14;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label8);
                    methodVisitor.visitLabel(label9);
                    Label label10 = new Label();
                    Label label11 = new Label();
                    if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                        label = label3;
                        i4 = i13;
                    } else {
                        methodVisitor.visitVarInsn(25, context.var("list_item"));
                        i4 = i13;
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                        methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label11);
                        aSMSerializerFactory._getListFieldItemSer(context, methodVisitor, fieldInfo, cls2);
                        methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                        Label label12 = new Label();
                        Label label13 = new Label();
                        if (context.writeDirect) {
                            methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                            methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label12);
                            methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                            methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                            methodVisitor.visitVarInsn(25, 1);
                            methodVisitor.visitVarInsn(25, context.var("list_item"));
                            if (context.nonContext) {
                                methodVisitor.visitInsn(1);
                                label = label3;
                            } else {
                                methodVisitor.visitVarInsn(21, context.var(ai.aA));
                                label = label3;
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                            }
                            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            methodVisitor.visitJumpInsn(Opcodes.GOTO, label13);
                            methodVisitor.visitLabel(label12);
                        } else {
                            label = label3;
                        }
                        methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                        methodVisitor.visitVarInsn(25, 1);
                        methodVisitor.visitVarInsn(25, context.var("list_item"));
                        if (context.nonContext) {
                            methodVisitor.visitInsn(1);
                        } else {
                            methodVisitor.visitVarInsn(21, context.var(ai.aA));
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                        }
                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                        methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                        methodVisitor.visitLabel(label13);
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label10);
                    }
                    methodVisitor.visitLabel(label11);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        methodVisitor.visitInsn(1);
                        c = 21;
                    } else {
                        c = 21;
                        methodVisitor.visitVarInsn(21, context.var(ai.aA));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                    } else {
                        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class) type)));
                        methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    }
                    methodVisitor.visitLabel(label10);
                    methodVisitor.visitLabel(label8);
                    methodVisitor.visitIincInsn(context.var(ai.aA), 1);
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label5);
                    methodVisitor.visitLabel(label7);
                    i6 = 25;
                    methodVisitor.visitVarInsn(25, context.var("out"));
                    i7 = 16;
                    methodVisitor.visitVarInsn(16, 93);
                    String str2 = SerializeWriter;
                    i5 = Opcodes.INVOKEVIRTUAL;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "write", "(I)V");
                    methodVisitor.visitLabel(label);
                } else {
                    methodVisitor.visitVarInsn(i8, context.var("out"));
                    methodVisitor.visitVarInsn(i8, context.var("list"));
                    methodVisitor.visitMethodInsn(i10, SerializeWriter, "write", "(Ljava/util/List;)V");
                    i6 = i8;
                    length = length;
                    i5 = i10;
                    i = i14;
                    i4 = i13;
                    i7 = 16;
                    c = 21;
                }
                methodVisitor.visitVarInsn(i6, context.var("out"));
                methodVisitor.visitVarInsn(i7, i4);
                methodVisitor.visitMethodInsn(i5, SerializeWriter, "write", "(I)V");
                i10 = i5;
                i12 = 16;
                i8 = 25;
                aSMSerializerFactory = this;
                i9 = 0;
                z = true;
            } else {
                length = length;
                i = i14;
                c = 21;
                Label label14 = new Label();
                Label label15 = new Label();
                aSMSerializerFactory = this;
                aSMSerializerFactory._get(methodVisitor, context, fieldInfo);
                methodVisitor.visitInsn(89);
                methodVisitor.visitVarInsn(58, context.var("field_" + fieldInfo.fieldClass.getName()));
                methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label15);
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label14);
                methodVisitor.visitLabel(label15);
                Label label16 = new Label();
                Label label17 = new Label();
                methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label17);
                aSMSerializerFactory._getFieldSer(context, methodVisitor, fieldInfo);
                methodVisitor.visitVarInsn(58, context.var("fied_ser"));
                Label label18 = new Label();
                Label label19 = new Label();
                if (context.writeDirect && Modifier.isPublic(cls3.getModifiers())) {
                    methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                    methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                    methodVisitor.visitJumpInsn(Opcodes.IFEQ, label18);
                    methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                    methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                    methodVisitor.visitVarInsn(25, Context.fieldName);
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label19);
                    methodVisitor.visitLabel(label18);
                }
                methodVisitor.visitVarInsn(25, context.var("fied_ser"));
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                methodVisitor.visitVarInsn(25, Context.fieldName);
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodVisitor.visitLabel(label19);
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label16);
                methodVisitor.visitLabel(label17);
                String format = fieldInfo.getFormat();
                z = true;
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                if (format != null) {
                    methodVisitor.visitLdcInsn(format);
                    String str3 = JSONSerializer;
                    i2 = Opcodes.INVOKEVIRTUAL;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
                    i9 = 0;
                } else {
                    methodVisitor.visitVarInsn(25, Context.fieldName);
                    if (!(fieldInfo.fieldType instanceof Class)) {
                        i3 = 25;
                        i9 = 0;
                    } else if (((Class) fieldInfo.fieldType).isPrimitive()) {
                        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                        i2 = 182;
                        i9 = 0;
                    } else {
                        i3 = 25;
                        i9 = 0;
                    }
                    methodVisitor.visitVarInsn(i3, i9);
                    methodVisitor.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                    String str4 = JSONSerializer;
                    i2 = Opcodes.INVOKEVIRTUAL;
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                }
                methodVisitor.visitLabel(label16);
                methodVisitor.visitLabel(label14);
                i8 = 25;
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitVarInsn(16, i13);
                methodVisitor.visitMethodInsn(i2, SerializeWriter, "write", "(I)V");
                i10 = i2;
                i12 = 16;
            }
            i14 = i + 1;
            i11 = 4;
            i13 = 93;
            fieldInfoArr2 = fieldInfoArr;
        }
    }

    private void generateWriteMethod(Class<?> cls, MethodVisitor methodVisitor, FieldInfo[] fieldInfoArr, Context context) throws Exception {
        String str;
        int i;
        int i2;
        int i3;
        Label label = new Label();
        int length = fieldInfoArr.length;
        int i4 = 25;
        if (!context.writeDirect) {
            Label label2 = new Label();
            Label label3 = new Label();
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.PrettyFormat.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label3);
            boolean z = false;
            for (FieldInfo fieldInfo : fieldInfoArr) {
                if (fieldInfo.method != null) {
                    z = true;
                }
            }
            if (z) {
                methodVisitor.visitVarInsn(25, context.var("out"));
                methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreErrorGetter.mask));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
            } else {
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            }
            methodVisitor.visitLabel(label3);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(label2);
        }
        if (!context.nonContext) {
            Label label4 = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeReference", "(L" + JSONSerializer + ";Ljava/lang/Object;I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label4);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(label4);
        }
        if (context.writeDirect) {
            str = context.nonContext ? "writeAsArrayNonContext" : "writeAsArray";
        } else {
            str = "writeAsArrayNormal";
        }
        if ((context.beanInfo.features & SerializerFeature.BeanToArray.mask) == 0) {
            Label label5 = new Label();
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.BeanToArray.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label5);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitLabel(label5);
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(21, 5);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitInsn(Opcodes.RETURN);
        }
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getContext", "()" + SerialContext_desc);
            methodVisitor.visitVarInsn(58, context.var("parent"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("parent"));
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, 3);
            methodVisitor.visitLdcInsn(Integer.valueOf(context.beanInfo.features));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(" + SerialContext_desc + "Ljava/lang/Object;Ljava/lang/Object;I)V");
        }
        boolean z2 = (context.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0;
        int i5 = 16;
        if (z2 || !context.writeDirect) {
            Label label6 = new Label();
            Label label7 = new Label();
            Label label8 = new Label();
            if (!z2) {
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, 4);
                methodVisitor.visitVarInsn(25, 2);
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label7);
                i2 = 4;
            } else {
                i2 = 4;
            }
            methodVisitor.visitVarInsn(25, i2);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitJumpInsn(Opcodes.IF_ACMPEQ, label7);
            methodVisitor.visitLabel(label8);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 123);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            if (context.beanInfo.typeKey != null) {
                methodVisitor.visitLdcInsn(context.beanInfo.typeKey);
                i3 = 2;
            } else {
                methodVisitor.visitInsn(1);
                i3 = 2;
            }
            methodVisitor.visitVarInsn(25, i3);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeClassName", "(L" + JSONSerializer + ";Ljava/lang/String;Ljava/lang/Object;)V");
            i5 = 16;
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label6);
            methodVisitor.visitLabel(label7);
            methodVisitor.visitVarInsn(16, 123);
            methodVisitor.visitLabel(label6);
        } else {
            methodVisitor.visitVarInsn(16, 123);
        }
        methodVisitor.visitVarInsn(54, context.var("seperator"));
        if (!context.writeDirect) {
            _before(methodVisitor, context);
        }
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isNotWriteDefaultValue", "()Z");
            methodVisitor.visitVarInsn(54, context.var("notWriteDefaultValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "checkValue", "(" + SerializeFilterable_desc + ")Z");
            methodVisitor.visitVarInsn(54, context.var("checkValue"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "hasNameFilters", "(" + SerializeFilterable_desc + ")Z");
            methodVisitor.visitVarInsn(54, context.var("hasNameFilters"));
        }
        int i6 = 0;
        while (i6 < length) {
            FieldInfo fieldInfo2 = fieldInfoArr[i6];
            Class<?> cls2 = fieldInfo2.fieldClass;
            methodVisitor.visitLdcInsn(fieldInfo2.name);
            methodVisitor.visitVarInsn(58, Context.fieldName);
            if (cls2 == Byte.TYPE || cls2 == Short.TYPE || cls2 == Integer.TYPE) {
                i = i6;
                i5 = i5;
                i4 = i4;
                _int(cls, methodVisitor, fieldInfo2, context, context.var(cls2.getName()), 'I');
            } else if (cls2 == Long.TYPE) {
                _long(cls, methodVisitor, fieldInfo2, context);
                i = i6;
                i5 = i5;
                i4 = i4;
            } else if (cls2 == Float.TYPE) {
                _float(cls, methodVisitor, fieldInfo2, context);
                i = i6;
                i5 = i5;
                i4 = i4;
            } else if (cls2 == Double.TYPE) {
                _double(cls, methodVisitor, fieldInfo2, context);
                i = i6;
                i5 = i5;
                i4 = i4;
            } else if (cls2 == Boolean.TYPE) {
                i = i6;
                i5 = i5;
                i4 = i4;
                _int(cls, methodVisitor, fieldInfo2, context, context.var("boolean"), 'Z');
            } else {
                i = i6;
                i5 = i5;
                i4 = i4;
                if (cls2 == Character.TYPE) {
                    _int(cls, methodVisitor, fieldInfo2, context, context.var("char"), 'C');
                } else if (cls2 == String.class) {
                    _string(cls, methodVisitor, fieldInfo2, context);
                } else if (cls2 == BigDecimal.class) {
                    _decimal(cls, methodVisitor, fieldInfo2, context);
                } else if (List.class.isAssignableFrom(cls2)) {
                    _list(cls, methodVisitor, fieldInfo2, context);
                } else if (cls2.isEnum()) {
                    _enum(cls, methodVisitor, fieldInfo2, context);
                } else {
                    _object(cls, methodVisitor, fieldInfo2, context);
                }
            }
            i6 = i + 1;
        }
        if (!context.writeDirect) {
            _after(methodVisitor, context);
        }
        Label label9 = new Label();
        Label label10 = new Label();
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitIntInsn(i5, 123);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label9);
        methodVisitor.visitVarInsn(i4, context.var("out"));
        methodVisitor.visitVarInsn(i5, 123);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        methodVisitor.visitLabel(label9);
        methodVisitor.visitVarInsn(i4, context.var("out"));
        methodVisitor.visitVarInsn(i5, 125);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        methodVisitor.visitLabel(label10);
        methodVisitor.visitLabel(label);
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(i4, 1);
            methodVisitor.visitVarInsn(i4, context.var("parent"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(" + SerialContext_desc + ")V");
        }
    }

    private void _object(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("object"));
        _filters(methodVisitor, fieldInfo, context, label);
        _writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitLabel(label);
    }

    private void _enum(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label3);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(192, "java/lang/Enum");
        methodVisitor.visitVarInsn(58, context.var("enum"));
        _filters(methodVisitor, fieldInfo, context, label3);
        methodVisitor.visitVarInsn(25, context.var("enum"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
        methodVisitor.visitLabel(label);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Enum", "name", "()Ljava/lang/String;");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValueStringWithDoubleQuote", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitInsn(3);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        }
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label3);
    }

    private void _int(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, int i, char c) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(54, i);
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(21, i);
        String str = SerializeWriter;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeFieldValue", "(CLjava/lang/String;" + c + ")V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _long(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(55, context.var("long", 2));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(22, context.var("long", 2));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;J)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _float(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(56, context.var("float"));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(23, context.var("float"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;F)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _double(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(57, context.var("double", 2));
        _filters(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(24, context.var("double", 2));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;D)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
    }

    private void _get(MethodVisitor methodVisitor, Context context, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            methodVisitor.visitVarInsn(25, context.var("entity"));
            Class<?> declaringClass = method.getDeclaringClass();
            methodVisitor.visitMethodInsn(declaringClass.isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(declaringClass), method.getName(), ASMUtils.desc(method));
            if (!method.getReturnType().equals(fieldInfo.fieldClass)) {
                methodVisitor.visitTypeInsn(192, ASMUtils.type(fieldInfo.fieldClass));
                return;
            }
            return;
        }
        methodVisitor.visitVarInsn(25, context.var("entity"));
        Field field = fieldInfo.field;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(fieldInfo.declaringClass), field.getName(), ASMUtils.desc(field.getType()));
        if (!field.getType().equals(fieldInfo.fieldClass)) {
            methodVisitor.visitTypeInsn(192, ASMUtils.type(fieldInfo.fieldClass));
        }
    }

    private void _decimal(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("decimal"));
        _filters(methodVisitor, fieldInfo, context, label);
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label3);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitVarInsn(25, context.var("decimal"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitLabel(label);
    }

    private void _string(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        if (fieldInfo.name.equals(context.beanInfo.typeKey)) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 4);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
        _nameApply(methodVisitor, fieldInfo, context, label);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitVarInsn(58, context.var("string"));
        _filters(methodVisitor, fieldInfo, context, label);
        Label label2 = new Label();
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var("string"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label2);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
        methodVisitor.visitLabel(label2);
        if ("trim".equals(fieldInfo.format)) {
            methodVisitor.visitVarInsn(25, context.var("string"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "trim", "()Ljava/lang/String;");
            methodVisitor.visitVarInsn(58, context.var("string"));
        }
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("string"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValueStringWithDoubleQuoteCheck", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(21, context.var("seperator"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, context.var("string"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
        }
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitLabel(label);
    }

    private void _list(Class<?> cls, MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label;
        Label label2;
        Label label3;
        int i;
        int i2;
        int i3;
        Label label4;
        java.lang.reflect.Type collectionItemType = TypeUtils.getCollectionItemType(fieldInfo.fieldType);
        Class<?> cls2 = collectionItemType instanceof Class ? (Class) collectionItemType : null;
        if (cls2 == Object.class || cls2 == Serializable.class) {
            cls2 = null;
        }
        Label label5 = new Label();
        Label label6 = new Label();
        Label label7 = new Label();
        _nameApply(methodVisitor, fieldInfo, context, label5);
        _get(methodVisitor, context, fieldInfo);
        methodVisitor.visitTypeInsn(192, "java/util/List");
        methodVisitor.visitVarInsn(58, context.var("list"));
        _filters(methodVisitor, fieldInfo, context, label5);
        methodVisitor.visitVarInsn(25, context.var("list"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label6);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label7);
        methodVisitor.visitLabel(label6);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        methodVisitor.visitVarInsn(25, context.var("list"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
        methodVisitor.visitVarInsn(54, context.var("size"));
        Label label8 = new Label();
        Label label9 = new Label();
        methodVisitor.visitVarInsn(21, context.var("size"));
        methodVisitor.visitInsn(3);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label8);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitLdcInsn(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Ljava/lang/String;)V");
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label9);
        methodVisitor.visitLabel(label8);
        if (!context.nonContext) {
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        }
        if (collectionItemType != String.class || !context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 91);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            Label label10 = new Label();
            Label label11 = new Label();
            Label label12 = new Label();
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, context.var(ai.aA));
            methodVisitor.visitLabel(label10);
            methodVisitor.visitVarInsn(21, context.var(ai.aA));
            methodVisitor.visitVarInsn(21, context.var("size"));
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label12);
            methodVisitor.visitVarInsn(21, context.var(ai.aA));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label11);
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 44);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            methodVisitor.visitLabel(label11);
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(21, context.var(ai.aA));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", BluetoothConstants.GET, "(I)Ljava/lang/Object;");
            methodVisitor.visitVarInsn(58, context.var("list_item"));
            Label label13 = new Label();
            Label label14 = new Label();
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label14);
            methodVisitor.visitVarInsn(25, context.var("out"));
            label3 = label5;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label13);
            methodVisitor.visitLabel(label14);
            Label label15 = new Label();
            Label label16 = new Label();
            if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                label = label7;
                label4 = label12;
                label2 = label9;
            } else {
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                label = label7;
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label16);
                _getListFieldItemSer(context, methodVisitor, fieldInfo, cls2);
                methodVisitor.visitVarInsn(58, context.var("list_item_desc"));
                Label label17 = new Label();
                Label label18 = new Label();
                if (context.writeDirect) {
                    String str = (!context.nonContext || !context.writeDirect) ? "write" : "writeDirectNonContext";
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                    methodVisitor.visitJumpInsn(Opcodes.IFEQ, label17);
                    methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                    label2 = label9;
                    methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
                    methodVisitor.visitVarInsn(25, 1);
                    methodVisitor.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        methodVisitor.visitInsn(1);
                        label4 = label12;
                    } else {
                        methodVisitor.visitVarInsn(21, context.var(ai.aA));
                        label4 = label12;
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                    methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    methodVisitor.visitJumpInsn(Opcodes.GOTO, label18);
                    methodVisitor.visitLabel(label17);
                } else {
                    label4 = label12;
                    label2 = label9;
                }
                methodVisitor.visitVarInsn(25, context.var("list_item_desc"));
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitVarInsn(25, context.var("list_item"));
                if (context.nonContext) {
                    methodVisitor.visitInsn(1);
                } else {
                    methodVisitor.visitVarInsn(21, context.var(ai.aA));
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                }
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                methodVisitor.visitLabel(label18);
                methodVisitor.visitJumpInsn(Opcodes.GOTO, label15);
            }
            methodVisitor.visitLabel(label16);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("list_item"));
            if (context.nonContext) {
                methodVisitor.visitInsn(1);
            } else {
                methodVisitor.visitVarInsn(21, context.var(ai.aA));
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            }
            if (cls2 == null || !Modifier.isPublic(cls2.getModifiers())) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            } else {
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc((Class) collectionItemType)));
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            }
            methodVisitor.visitLabel(label15);
            methodVisitor.visitLabel(label13);
            methodVisitor.visitIincInsn(context.var(ai.aA), 1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label10);
            methodVisitor.visitLabel(label4);
            i2 = 25;
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(16, 93);
            String str2 = SerializeWriter;
            i = Opcodes.INVOKEVIRTUAL;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "write", "(I)V");
            i3 = 1;
        } else {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Ljava/util/List;)V");
            label3 = label5;
            label = label7;
            i = 182;
            i3 = 1;
            i2 = 25;
            label2 = label9;
        }
        methodVisitor.visitVarInsn(i2, i3);
        methodVisitor.visitMethodInsn(i, JSONSerializer, "popContext", "()V");
        methodVisitor.visitLabel(label2);
        _seperator(methodVisitor, context);
        methodVisitor.visitLabel(label);
        methodVisitor.visitLabel(label3);
    }

    private void _filters(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (fieldInfo.fieldTransient) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.SkipTransientField.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
        _notWriteDefault(methodVisitor, fieldInfo, context, label);
        if (!context.writeDirect) {
            _apply(methodVisitor, fieldInfo, context);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            _processKey(methodVisitor, fieldInfo, context);
            _processValue(methodVisitor, fieldInfo, context, label);
        }
    }

    private void _nameApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (!context.writeDirect) {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(25, Context.fieldName);
            String str = JavaBeanSerializer;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "applyName", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            _labelApply(methodVisitor, fieldInfo, context, label);
        }
        if (fieldInfo.field == null) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreNonFieldGetter.mask));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        }
    }

    private void _labelApply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(fieldInfo.label);
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "applyLabel", "(L" + JSONSerializer + ";Ljava/lang/String;)Z");
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
    }

    private void _writeObject(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        String format = fieldInfo.getFormat();
        Class<?> cls = fieldInfo.fieldClass;
        Label label2 = new Label();
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(58, context.var("object"));
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label2);
        _if_write_null(methodVisitor, fieldInfo, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        Label label3 = new Label();
        Label label4 = new Label();
        if (Modifier.isPublic(cls.getModifiers()) && !ParserConfig.isPrimitive2(cls)) {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
            methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, label4);
            _getFieldSer(context, methodVisitor, fieldInfo);
            methodVisitor.visitVarInsn(58, context.var("fied_ser"));
            Label label5 = new Label();
            Label label6 = new Label();
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label5);
            boolean z = (fieldInfo.serialzeFeatures & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
            boolean z2 = (fieldInfo.serialzeFeatures & SerializerFeature.BeanToArray.mask) != 0;
            String str = (z || (context.nonContext && context.writeDirect)) ? z2 ? "writeAsArrayNonContext" : "writeDirectNonContext" : z2 ? "writeAsArray" : "write";
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitTypeInsn(192, JavaBeanSerializer);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            String str2 = context.className;
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str2, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            String str3 = JavaBeanSerializer;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label6);
            methodVisitor.visitLabel(label5);
            methodVisitor.visitVarInsn(25, context.var("fied_ser"));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitVarInsn(25, 0);
            String str4 = context.className;
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str4, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            String str5 = ObjectSerializer;
            methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, str5, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            methodVisitor.visitLabel(label6);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label3);
        }
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(25, 1);
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("object"));
        } else {
            methodVisitor.visitVarInsn(25, Context.processValue);
        }
        if (format != null) {
            methodVisitor.visitLdcInsn(format);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
        } else {
            methodVisitor.visitVarInsn(25, Context.fieldName);
            if (!(fieldInfo.fieldType instanceof Class) || !((Class) fieldInfo.fieldType).isPrimitive()) {
                if (fieldInfo.fieldClass == String.class) {
                    methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(String.class)));
                } else {
                    methodVisitor.visitVarInsn(25, 0);
                    String str6 = context.className;
                    methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str6, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            } else {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            }
        }
        methodVisitor.visitLabel(label3);
        _seperator(methodVisitor, context);
    }

    private void _before(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeBefore", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _after(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "writeAfter", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _notWriteDefault(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        if (!context.writeDirect) {
            Label label2 = new Label();
            methodVisitor.visitVarInsn(21, context.var("notWriteDefaultValue"));
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
            Class<?> cls = fieldInfo.fieldClass;
            if (cls == Boolean.TYPE) {
                methodVisitor.visitVarInsn(21, context.var("boolean"));
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            } else if (cls == Byte.TYPE) {
                methodVisitor.visitVarInsn(21, context.var("byte"));
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            } else if (cls == Short.TYPE) {
                methodVisitor.visitVarInsn(21, context.var("short"));
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            } else if (cls == Integer.TYPE) {
                methodVisitor.visitVarInsn(21, context.var("int"));
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            } else if (cls == Long.TYPE) {
                methodVisitor.visitVarInsn(22, context.var("long"));
                methodVisitor.visitInsn(9);
                methodVisitor.visitInsn(Opcodes.LCMP);
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            } else if (cls == Float.TYPE) {
                methodVisitor.visitVarInsn(23, context.var("float"));
                methodVisitor.visitInsn(11);
                methodVisitor.visitInsn(Opcodes.FCMPL);
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            } else if (cls == Double.TYPE) {
                methodVisitor.visitVarInsn(24, context.var("double"));
                methodVisitor.visitInsn(14);
                methodVisitor.visitInsn(Opcodes.DCMPL);
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
            }
            methodVisitor.visitLabel(label2);
        }
    }

    private void _apply(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Class<?> cls = fieldInfo.fieldClass;
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var("string"));
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
        }
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "apply", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
    }

    private void _processValue(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context, Label label) {
        Label label2 = new Label();
        Class<?> cls = fieldInfo.fieldClass;
        if (cls.isPrimitive()) {
            Label label3 = new Label();
            methodVisitor.visitVarInsn(21, context.var("checkValue"));
            methodVisitor.visitJumpInsn(Opcodes.IFNE, label3);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(58, Context.processValue);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, label2);
            methodVisitor.visitLabel(label3);
        }
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(Integer.valueOf(context.getFieldOrinal(fieldInfo.name)));
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "getBeanContext", "(I)" + ASMUtils.desc(BeanContext.class));
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(58, Context.original);
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var("string"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
            methodVisitor.visitVarInsn(58, Context.original);
            methodVisitor.visitVarInsn(25, Context.original);
        }
        String str2 = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "processValue", "(L" + JSONSerializer + ";" + ASMUtils.desc(BeanContext.class) + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitVarInsn(58, Context.processValue);
        methodVisitor.visitVarInsn(25, Context.original);
        methodVisitor.visitVarInsn(25, Context.processValue);
        methodVisitor.visitJumpInsn(Opcodes.IF_ACMPEQ, label2);
        _writeObject(methodVisitor, fieldInfo, context, label);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label);
        methodVisitor.visitLabel(label2);
    }

    private void _processKey(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        Label label = new Label();
        methodVisitor.visitVarInsn(21, context.var("hasNameFilters"));
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        Class<?> cls = fieldInfo.fieldClass;
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, 2);
        methodVisitor.visitVarInsn(25, Context.fieldName);
        if (cls == Byte.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("byte"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (cls == Short.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("short"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (cls == Integer.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("int"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (cls == Character.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("char"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(22, context.var("long", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(23, context.var("float"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(24, context.var("double", 2));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(21, context.var("boolean"));
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (cls == BigDecimal.class) {
            methodVisitor.visitVarInsn(25, context.var("decimal"));
        } else if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var("string"));
        } else if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var("list"));
        } else {
            methodVisitor.visitVarInsn(25, context.var("object"));
        }
        String str = JavaBeanSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str, "processKey", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
        methodVisitor.visitVarInsn(58, Context.fieldName);
        methodVisitor.visitLabel(label);
    }

    private void _if_write_null(MethodVisitor methodVisitor, FieldInfo fieldInfo, Context context) {
        int i;
        Class<?> cls = fieldInfo.fieldClass;
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        methodVisitor.visitLabel(label);
        JSONField annotation = fieldInfo.getAnnotation();
        int of = annotation != null ? SerializerFeature.of(annotation.serialzeFeatures()) : 0;
        JSONType jSONType = context.beanInfo.jsonType;
        if (jSONType != null) {
            of |= SerializerFeature.of(jSONType.serialzeFeatures());
        }
        if (cls == String.class) {
            i = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullStringAsEmpty.getMask();
        } else if (Number.class.isAssignableFrom(cls)) {
            i = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullNumberAsZero.getMask();
        } else if (Collection.class.isAssignableFrom(cls)) {
            i = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullListAsEmpty.getMask();
        } else if (Boolean.class == cls) {
            i = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullBooleanAsFalse.getMask();
        } else {
            i = SerializerFeature.WRITE_MAP_NULL_FEATURES;
        }
        if ((of & i) == 0) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitLdcInsn(Integer.valueOf(i));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, label2);
        }
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(21, context.var("seperator"));
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(methodVisitor, context);
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitLdcInsn(Integer.valueOf(of));
        if (cls == String.class || cls == Character.class) {
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullStringAsEmpty.mask));
        } else if (Number.class.isAssignableFrom(cls)) {
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullNumberAsZero.mask));
        } else if (cls == Boolean.class) {
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullBooleanAsFalse.mask));
        } else if (Collection.class.isAssignableFrom(cls) || cls.isArray()) {
            methodVisitor.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullListAsEmpty.mask));
        } else {
            methodVisitor.visitLdcInsn(0);
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "(II)V");
        _seperator(methodVisitor, context);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, label4);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLabel(label4);
    }

    private void _writeFieldName(MethodVisitor methodVisitor, Context context) {
        if (context.writeDirect) {
            methodVisitor.visitVarInsn(25, context.var("out"));
            methodVisitor.visitVarInsn(25, Context.fieldName);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldNameDirect", "(Ljava/lang/String;)V");
            return;
        }
        methodVisitor.visitVarInsn(25, context.var("out"));
        methodVisitor.visitVarInsn(25, Context.fieldName);
        methodVisitor.visitInsn(3);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
    }

    private void _seperator(MethodVisitor methodVisitor, Context context) {
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitVarInsn(54, context.var("seperator"));
    }

    private void _getListFieldItemSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
        String str2 = JSONSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        String str3 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, str3, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        String str4 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str4, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
    }

    private void _getFieldSer(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        String str = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        methodVisitor.visitJumpInsn(Opcodes.IFNONNULL, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        String str2 = JSONSerializer;
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        String str3 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, str3, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        String str4 = context.className;
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str4, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
    }
}
