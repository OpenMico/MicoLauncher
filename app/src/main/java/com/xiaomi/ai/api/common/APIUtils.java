package com.xiaomi.ai.api.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.umeng.analytics.pro.c;
import com.xiaomi.common.Optional;
import com.xiaomi.ext.Jdk8Module;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes3.dex */
public class APIUtils {
    private static ApiNameMapping mapping;
    private static ObjectMapper objectMapper = buildObjectMapper(false);
    private static ObjectMapper objectMapperAllowNull = buildObjectMapper(true);

    static {
        try {
            mapping = (ApiNameMapping) APIUtils.class.getClassLoader().loadClass("com.xiaomi.ai.api.AIApiNameMapping").newInstance();
        } catch (Throwable th) {
            throw new IllegalStateException(th);
        }
    }

    private static ObjectMapper buildObjectMapper(boolean z) {
        DefaultSerializerProvider.Impl impl = new DefaultSerializerProvider.Impl();
        if (!z) {
            impl.setNullValueSerializer(new NullValueSerializer());
        }
        ObjectMapper registerModule = new ObjectMapper().setSerializerProvider(impl).setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE).setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).setSerializationInclusion(JsonInclude.Include.ALWAYS).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE).registerModule(new Jdk8Module());
        registerModule.configOverride(Optional.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_ABSENT, JsonInclude.Include.NON_ABSENT));
        JsonInclude.Value construct = JsonInclude.Value.construct(JsonInclude.Include.NON_ABSENT, JsonInclude.Include.NON_ABSENT);
        registerModule.configOverride(List.class).setInclude(construct);
        registerModule.configOverride(Set.class).setInclude(construct);
        registerModule.configOverride(Map.class).setInclude(construct);
        return registerModule;
    }

    public static String randomRequestId(boolean z) {
        UUID randomUUID = UUID.randomUUID();
        if (z) {
            return randomUUID.toString();
        }
        return Long.toHexString(randomUUID.getMostSignificantBits()) + Long.toHexString(randomUUID.getLeastSignificantBits());
    }

    public static <T extends ContextPayload> Context<T> buildContext(T t) {
        NamespaceName namespaceName = getNamespaceName(t);
        return new Context<>(new ContextHeader(namespaceName.namespace(), namespaceName.name()), t);
    }

    public static <T extends EventPayload> Event<T> buildEvent(T t) {
        return buildEvent(t, null, randomRequestId(false));
    }

    public static <T extends EventPayload> Event<T> buildEvent(T t, List<Context> list) {
        return buildEvent(t, list, randomRequestId(false));
    }

    public static <T extends EventPayload> Event<T> buildEvent(T t, List<Context> list, String str) {
        NamespaceName namespaceName = getNamespaceName(t);
        return new Event<>(list, new EventHeader(namespaceName.namespace(), namespaceName.name()).setId(str), t);
    }

    public static <T extends InstructionPayload> Instruction<T> buildInstruction(T t) {
        return buildInstruction(t, randomRequestId(false));
    }

    public static <T extends InstructionPayload> Instruction<T> buildInstruction(T t, String str) {
        NamespaceName namespaceName = getNamespaceName(t);
        return new Instruction<>(new InstructionHeader(namespaceName.namespace(), namespaceName.name()).setId(str), t);
    }

    private static NamespaceName getNamespaceName(Object obj) {
        NamespaceName namespaceName = (NamespaceName) obj.getClass().getAnnotation(NamespaceName.class);
        if (namespaceName != null) {
            return namespaceName;
        }
        throw new UnsupportedOperationException("Cannot find NamespaceName");
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private static String toJsonStringImpl(ObjectMapper objectMapper2, Object obj, boolean z) throws JsonProcessingException {
        if (z) {
            return objectMapper2.writer(SerializationFeature.INDENT_OUTPUT).writeValueAsString(obj);
        }
        return objectMapper2.writeValueAsString(obj);
    }

    public static String toJsonString(Object obj, boolean z) throws JsonProcessingException {
        return toJsonStringImpl(objectMapper, obj, z);
    }

    public static String toJsonStringAllowNull(Object obj, boolean z) throws JsonProcessingException {
        return toJsonStringImpl(objectMapperAllowNull, obj, z);
    }

    public static String toJsonString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static String toJsonStringAllowNull(Object obj) throws JsonProcessingException {
        return objectMapperAllowNull.writeValueAsString(obj);
    }

    public static <T extends JsonNode> T toJsonNode(Object obj) {
        return (T) objectMapper.valueToTree(obj);
    }

    public static <T extends JsonNode> T toJsonNodeAllowNull(Object obj) {
        return (T) objectMapperAllowNull.valueToTree(obj);
    }

    public static <T> T fromJsonNode(JsonNode jsonNode, Class<T> cls) throws JsonProcessingException {
        return (T) objectMapper.treeToValue(jsonNode, cls);
    }

    public static <T> T fromJsonString(String str, Class<T> cls) throws IOException {
        return (T) objectMapper.readValue(str, cls);
    }

    public static <T> Instruction<T> findInstructionOrNull(ArrayNode arrayNode, String str, String str2) throws JsonProcessingException {
        Iterator<JsonNode> it = arrayNode.iterator();
        while (it.hasNext()) {
            JsonNode next = it.next();
            JsonNode jsonNode = next.get("header");
            if (jsonNode != null && jsonNode.has("namespace") && jsonNode.has("name")) {
                String asText = jsonNode.get("namespace").asText();
                String asText2 = jsonNode.get("name").asText();
                if (str.equals(asText) && str2.equals(asText2)) {
                    return readInstruction(next);
                }
            }
        }
        return null;
    }

    public static <T> Instruction<T> findInstructionOrNull(List<Instruction<?>> list, String str, String str2) {
        Iterator<Instruction<?>> it = list.iterator();
        while (it.hasNext()) {
            Instruction<T> instruction = (Instruction<T>) it.next();
            InstructionHeader header = instruction.getHeader();
            if (str.equals(header.getNamespace()) && str2.equals(header.getName())) {
                return instruction;
            }
        }
        return null;
    }

    public static List<Instruction<?>> readInstructions(ArrayNode arrayNode) {
        ArrayList arrayList = new ArrayList();
        Iterator<JsonNode> it = arrayNode.iterator();
        while (it.hasNext()) {
            try {
                Instruction readInstruction = readInstruction(it.next());
                if (readInstruction != null) {
                    arrayList.add(readInstruction);
                }
            } catch (JsonProcessingException unused) {
            }
        }
        return arrayList;
    }

    public static ArrayNode writeInstructions(List<Instruction<?>> list) {
        ArrayNode createArrayNode = objectMapper.createArrayNode();
        for (Instruction<?> instruction : list) {
            JsonNode jsonNode = toJsonNode(instruction);
            if (jsonNode != null) {
                createArrayNode.add(jsonNode);
            }
        }
        return createArrayNode;
    }

    public static <T> Instruction<T> readInstruction(String str) throws IOException {
        return readInstruction(getObjectMapper().readTree(str));
    }

    public static <T> Instruction<T> readInstruction(JsonNode jsonNode) throws JsonProcessingException {
        InstructionHeader instructionHeader = (InstructionHeader) fromJsonNode(jsonNode.get("header"), InstructionHeader.class);
        Object findPayload = findPayload(instructionHeader, jsonNode);
        if (findPayload != null) {
            return new Instruction<>(instructionHeader, findPayload);
        }
        return new Instruction<>(instructionHeader, jsonNode.get(SchemaActivity.KEY_PAYLOAD) == null ? objectMapper.createObjectNode() : jsonNode.get(SchemaActivity.KEY_PAYLOAD));
    }

    public static <T> Event<T> readEvent(String str) throws IOException {
        return readEvent(getObjectMapper().readTree(str));
    }

    public static <T> Event<T> readEvent(JsonNode jsonNode) throws JsonProcessingException {
        ArrayList arrayList;
        EventHeader eventHeader = (EventHeader) fromJsonNode(jsonNode.get("header"), EventHeader.class);
        JsonNode jsonNode2 = jsonNode.get(c.R);
        if (jsonNode2 == null || !jsonNode2.isArray()) {
            arrayList = null;
        } else {
            ArrayNode arrayNode = (ArrayNode) jsonNode2;
            arrayList = new ArrayList(arrayNode.size());
            Iterator<JsonNode> elements = arrayNode.elements();
            while (elements.hasNext()) {
                Context readContext = readContext(elements.next());
                if (readContext != null) {
                    arrayList.add(readContext);
                }
            }
        }
        Object findPayload = findPayload(eventHeader, jsonNode);
        if (findPayload == null) {
            return null;
        }
        return new Event<>(arrayList, eventHeader, findPayload);
    }

    public static <T> Context<T> readContext(String str) throws IOException {
        return readContext(getObjectMapper().readTree(str));
    }

    public static <T> Context<T> readContext(JsonNode jsonNode) throws JsonProcessingException {
        ContextHeader contextHeader = (ContextHeader) fromJsonNode(jsonNode.get("header"), ContextHeader.class);
        Object findPayload = findPayload(contextHeader, jsonNode);
        if (findPayload == null) {
            return null;
        }
        return new Context<>(contextHeader, findPayload);
    }

    private static <H extends MessageHeader<H>, P> P findPayload(H h, JsonNode jsonNode) throws JsonProcessingException {
        Class<?> findClass = mapping.findClass(h.getNamespace(), h.getName());
        if (findClass != null) {
            return (P) fromJsonNode(jsonNode.get(SchemaActivity.KEY_PAYLOAD), findClass);
        }
        return null;
    }
}
