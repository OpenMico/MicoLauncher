package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import io.netty.util.internal.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

/* loaded from: classes.dex */
public class MiscCodec implements ObjectDeserializer, ObjectSerializer {
    private static Method method_paths_get;
    public static final MiscCodec instance = new MiscCodec();
    private static boolean method_paths_get_error = false;
    private static boolean FILE_RELATIVE_PATH_SUPPORT = "true".equals(IOUtils.getStringProperty("fastjson.deserializer.fileRelativePathSupport"));

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        String str;
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        Class<?> cls = obj.getClass();
        if (cls == SimpleDateFormat.class) {
            str = ((SimpleDateFormat) obj).toPattern();
            if (serializeWriter.isEnabled(SerializerFeature.WriteClassName) && obj.getClass() != type) {
                serializeWriter.write(123);
                serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                jSONSerializer.write(obj.getClass().getName());
                serializeWriter.writeFieldValue(StringUtil.COMMA, "val", str);
                serializeWriter.write(125);
                return;
            }
        } else if (cls == Class.class) {
            str = ((Class) obj).getName();
        } else if (cls == InetSocketAddress.class) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) obj;
            InetAddress address = inetSocketAddress.getAddress();
            serializeWriter.write(123);
            if (address != null) {
                serializeWriter.writeFieldName("address");
                jSONSerializer.write(address);
                serializeWriter.write(44);
            }
            serializeWriter.writeFieldName("port");
            serializeWriter.writeInt(inetSocketAddress.getPort());
            serializeWriter.write(125);
            return;
        } else if (obj instanceof File) {
            str = ((File) obj).getPath();
        } else if (obj instanceof InetAddress) {
            str = ((InetAddress) obj).getHostAddress();
        } else if (obj instanceof TimeZone) {
            str = ((TimeZone) obj).getID();
        } else if (obj instanceof Currency) {
            str = ((Currency) obj).getCurrencyCode();
        } else if (obj instanceof JSONStreamAware) {
            ((JSONStreamAware) obj).writeJSONString(serializeWriter);
            return;
        } else if (obj instanceof Iterator) {
            writeIterator(jSONSerializer, serializeWriter, (Iterator) obj);
            return;
        } else if (obj instanceof Iterable) {
            writeIterator(jSONSerializer, serializeWriter, ((Iterable) obj).iterator());
            return;
        } else if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key instanceof String) {
                String str2 = (String) key;
                if (value instanceof String) {
                    serializeWriter.writeFieldValueStringWithDoubleQuoteCheck('{', str2, (String) value);
                } else {
                    serializeWriter.write(123);
                    serializeWriter.writeFieldName(str2);
                    jSONSerializer.write(value);
                }
            } else {
                serializeWriter.write(123);
                jSONSerializer.write(key);
                serializeWriter.write(58);
                jSONSerializer.write(value);
            }
            serializeWriter.write(125);
            return;
        } else if (obj.getClass().getName().equals("net.sf.json.JSONNull")) {
            serializeWriter.writeNull();
            return;
        } else if (obj instanceof Node) {
            str = toString((Node) obj);
        } else {
            throw new JSONException("not support class : " + cls);
        }
        serializeWriter.writeString(str);
    }

    private static String toString(Node node) {
        try {
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            DOMSource dOMSource = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            newTransformer.transform(dOMSource, new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (TransformerException e) {
            throw new JSONException("xml node to string error", e);
        }
    }

    protected void writeIterator(JSONSerializer jSONSerializer, SerializeWriter serializeWriter, Iterator<?> it) {
        serializeWriter.write(91);
        int i = 0;
        while (it.hasNext()) {
            if (i != 0) {
                serializeWriter.write(44);
            }
            jSONSerializer.write(it.next());
            i++;
        }
        serializeWriter.write(93);
    }

    /* JADX WARN: Type inference failed for: r8v27, types: [T, java.text.SimpleDateFormat] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r8, java.lang.reflect.Type r9, java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 615
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.MiscCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
