package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ExternalTypeHandler {
    private final JavaType a;
    private final a[] b;
    private final Map<String, Object> c;
    private final String[] d;
    private final TokenBuffer[] e;

    protected ExternalTypeHandler(JavaType javaType, a[] aVarArr, Map<String, Object> map, String[] strArr, TokenBuffer[] tokenBufferArr) {
        this.a = javaType;
        this.b = aVarArr;
        this.c = map;
        this.d = strArr;
        this.e = tokenBufferArr;
    }

    protected ExternalTypeHandler(ExternalTypeHandler externalTypeHandler) {
        this.a = externalTypeHandler.a;
        this.b = externalTypeHandler.b;
        this.c = externalTypeHandler.c;
        int length = this.b.length;
        this.d = new String[length];
        this.e = new TokenBuffer[length];
    }

    public static Builder builder(JavaType javaType) {
        return new Builder(javaType);
    }

    public ExternalTypeHandler start() {
        return new ExternalTypeHandler(this);
    }

    public boolean handleTypePropertyValue(JsonParser jsonParser, DeserializationContext deserializationContext, String str, Object obj) throws IOException {
        Object obj2 = this.c.get(str);
        boolean z = false;
        if (obj2 == null) {
            return false;
        }
        String text = jsonParser.getText();
        if (!(obj2 instanceof List)) {
            return a(jsonParser, deserializationContext, str, obj, text, ((Integer) obj2).intValue());
        }
        for (Integer num : (List) obj2) {
            if (a(jsonParser, deserializationContext, str, obj, text, num.intValue())) {
                z = true;
            }
        }
        return z;
    }

    private final boolean a(JsonParser jsonParser, DeserializationContext deserializationContext, String str, Object obj, String str2, int i) throws IOException {
        boolean z = false;
        if (!this.b[i].a(str)) {
            return false;
        }
        if (!(obj == null || this.e[i] == null)) {
            z = true;
        }
        if (z) {
            _deserializeAndSet(jsonParser, deserializationContext, obj, i, str2);
            this.e[i] = null;
        } else {
            this.d[i] = str2;
        }
        return true;
    }

    public boolean handlePropertyValue(JsonParser jsonParser, DeserializationContext deserializationContext, String str, Object obj) throws IOException {
        Object obj2 = this.c.get(str);
        boolean z = false;
        if (obj2 == null) {
            return false;
        }
        if (obj2 instanceof List) {
            Iterator it = ((List) obj2).iterator();
            Integer num = (Integer) it.next();
            if (this.b[num.intValue()].a(str)) {
                String text = jsonParser.getText();
                jsonParser.skipChildren();
                this.d[num.intValue()] = text;
                while (it.hasNext()) {
                    this.d[((Integer) it.next()).intValue()] = text;
                }
            } else {
                TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
                tokenBuffer.copyCurrentStructure(jsonParser);
                this.e[num.intValue()] = tokenBuffer;
                while (it.hasNext()) {
                    this.e[((Integer) it.next()).intValue()] = tokenBuffer;
                }
            }
            return true;
        }
        int intValue = ((Integer) obj2).intValue();
        if (this.b[intValue].a(str)) {
            this.d[intValue] = jsonParser.getText();
            jsonParser.skipChildren();
            if (!(obj == null || this.e[intValue] == null)) {
                z = true;
            }
        } else {
            TokenBuffer tokenBuffer2 = new TokenBuffer(jsonParser, deserializationContext);
            tokenBuffer2.copyCurrentStructure(jsonParser);
            this.e[intValue] = tokenBuffer2;
            if (!(obj == null || this.d[intValue] == null)) {
                z = true;
            }
        }
        if (z) {
            String[] strArr = this.d;
            String str2 = strArr[intValue];
            strArr[intValue] = null;
            _deserializeAndSet(jsonParser, deserializationContext, obj, intValue, str2);
            this.e[intValue] = null;
        }
        return true;
    }

    public Object complete(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        int length = this.b.length;
        for (int i = 0; i < length; i++) {
            String str = this.d[i];
            if (str == null) {
                TokenBuffer tokenBuffer = this.e[i];
                if (tokenBuffer != null) {
                    if (tokenBuffer.firstToken().isScalarValue()) {
                        JsonParser asParser = tokenBuffer.asParser(jsonParser);
                        asParser.nextToken();
                        SettableBeanProperty d = this.b[i].d();
                        Object deserializeIfNatural = TypeDeserializer.deserializeIfNatural(asParser, deserializationContext, d.getType());
                        if (deserializeIfNatural != null) {
                            d.set(obj, deserializeIfNatural);
                        } else if (!this.b[i].a()) {
                            deserializationContext.reportInputMismatch(obj.getClass(), "Missing external type id property '%s'", this.b[i].c());
                        } else {
                            str = this.b[i].b();
                        }
                    }
                }
            } else if (this.e[i] == null) {
                SettableBeanProperty d2 = this.b[i].d();
                if (d2.isRequired() || deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)) {
                    deserializationContext.reportInputMismatch(obj.getClass(), "Missing property '%s' for external type id '%s'", d2.getName(), this.b[i].c());
                }
                return obj;
            }
            _deserializeAndSet(jsonParser, deserializationContext, obj, i, str);
        }
        return obj;
    }

    public Object complete(JsonParser jsonParser, DeserializationContext deserializationContext, PropertyValueBuffer propertyValueBuffer, PropertyBasedCreator propertyBasedCreator) throws IOException {
        String str;
        int length = this.b.length;
        Object[] objArr = new Object[length];
        for (int i = 0; i < length; i++) {
            String str2 = this.d[i];
            a aVar = this.b[i];
            if (str2 != null) {
                str = str2;
                if (this.e[i] == null) {
                    deserializationContext.reportInputMismatch(this.a, "Missing property '%s' for external type id '%s'", aVar.d().getName(), this.b[i].c());
                    str = str2;
                }
            } else if (this.e[i] != null) {
                if (!aVar.a()) {
                    deserializationContext.reportInputMismatch(this.a, "Missing external type id property '%s'", aVar.c());
                    str = str2;
                } else {
                    str = aVar.b();
                }
            }
            objArr[i] = _deserialize(jsonParser, deserializationContext, i, str);
            SettableBeanProperty d = aVar.d();
            if (d.getCreatorIndex() >= 0) {
                propertyValueBuffer.assignParameter(d, objArr[i]);
                SettableBeanProperty e = aVar.e();
                if (e != null && e.getCreatorIndex() >= 0) {
                    Object obj = str;
                    if (!e.getType().hasRawClass(String.class)) {
                        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
                        tokenBuffer.writeString(str);
                        Object deserialize = e.getValueDeserializer().deserialize(tokenBuffer.asParserOnFirstToken(), deserializationContext);
                        tokenBuffer.close();
                        obj = deserialize;
                    }
                    propertyValueBuffer.assignParameter(e, obj);
                }
            }
        }
        Object build = propertyBasedCreator.build(deserializationContext, propertyValueBuffer);
        for (int i2 = 0; i2 < length; i2++) {
            SettableBeanProperty d2 = this.b[i2].d();
            if (d2.getCreatorIndex() < 0) {
                d2.set(build, objArr[i2]);
            }
        }
        return build;
    }

    protected final Object _deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, int i, String str) throws IOException {
        JsonParser asParser = this.e[i].asParser(jsonParser);
        if (asParser.nextToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartArray();
        tokenBuffer.writeString(str);
        tokenBuffer.copyCurrentStructure(asParser);
        tokenBuffer.writeEndArray();
        JsonParser asParser2 = tokenBuffer.asParser(jsonParser);
        asParser2.nextToken();
        return this.b[i].d().deserialize(asParser2, deserializationContext);
    }

    protected final void _deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, int i, String str) throws IOException {
        JsonParser asParser = this.e[i].asParser(jsonParser);
        if (asParser.nextToken() == JsonToken.VALUE_NULL) {
            this.b[i].d().set(obj, null);
            return;
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartArray();
        tokenBuffer.writeString(str);
        tokenBuffer.copyCurrentStructure(asParser);
        tokenBuffer.writeEndArray();
        JsonParser asParser2 = tokenBuffer.asParser(jsonParser);
        asParser2.nextToken();
        this.b[i].d().deserializeAndSet(asParser2, deserializationContext, obj);
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private final JavaType a;
        private final List<a> b = new ArrayList();
        private final Map<String, Object> c = new HashMap();

        protected Builder(JavaType javaType) {
            this.a = javaType;
        }

        public void addExternal(SettableBeanProperty settableBeanProperty, TypeDeserializer typeDeserializer) {
            Integer valueOf = Integer.valueOf(this.b.size());
            this.b.add(new a(settableBeanProperty, typeDeserializer));
            a(settableBeanProperty.getName(), valueOf);
            a(typeDeserializer.getPropertyName(), valueOf);
        }

        private void a(String str, Integer num) {
            Object obj = this.c.get(str);
            if (obj == null) {
                this.c.put(str, num);
            } else if (obj instanceof List) {
                ((List) obj).add(num);
            } else {
                LinkedList linkedList = new LinkedList();
                linkedList.add(obj);
                linkedList.add(num);
                this.c.put(str, linkedList);
            }
        }

        public ExternalTypeHandler build(BeanPropertyMap beanPropertyMap) {
            int size = this.b.size();
            a[] aVarArr = new a[size];
            for (int i = 0; i < size; i++) {
                a aVar = this.b.get(i);
                SettableBeanProperty find = beanPropertyMap.find(aVar.c());
                if (find != null) {
                    aVar.a(find);
                }
                aVarArr[i] = aVar;
            }
            return new ExternalTypeHandler(this.a, aVarArr, this.c, null, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        private final SettableBeanProperty a;
        private final TypeDeserializer b;
        private final String c;
        private SettableBeanProperty d;

        public a(SettableBeanProperty settableBeanProperty, TypeDeserializer typeDeserializer) {
            this.a = settableBeanProperty;
            this.b = typeDeserializer;
            this.c = typeDeserializer.getPropertyName();
        }

        public void a(SettableBeanProperty settableBeanProperty) {
            this.d = settableBeanProperty;
        }

        public boolean a(String str) {
            return str.equals(this.c);
        }

        public boolean a() {
            return this.b.getDefaultImpl() != null;
        }

        public String b() {
            Class<?> defaultImpl = this.b.getDefaultImpl();
            if (defaultImpl == null) {
                return null;
            }
            return this.b.getTypeIdResolver().idFromValueAndType(null, defaultImpl);
        }

        public String c() {
            return this.c;
        }

        public SettableBeanProperty d() {
            return this.a;
        }

        public SettableBeanProperty e() {
            return this.d;
        }
    }
}
