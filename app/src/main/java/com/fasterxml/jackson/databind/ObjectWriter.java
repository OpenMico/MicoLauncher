package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.Instantiatable;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.TypeWrappedSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Closeable;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class ObjectWriter implements Versioned, Serializable {
    protected static final PrettyPrinter NULL_PRETTY_PRINTER = new MinimalPrettyPrinter();
    private static final long serialVersionUID = 1;
    protected final SerializationConfig _config;
    protected final JsonFactory _generatorFactory;
    protected final GeneratorSettings _generatorSettings;
    protected final Prefetch _prefetch;
    protected final SerializerFactory _serializerFactory;
    protected final DefaultSerializerProvider _serializerProvider;

    public ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig, JavaType javaType, PrettyPrinter prettyPrinter) {
        this._config = serializationConfig;
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._generatorSettings = prettyPrinter == null ? GeneratorSettings.empty : new GeneratorSettings(prettyPrinter, null, null, null);
        if (javaType == null || javaType.hasRawClass(Object.class)) {
            this._prefetch = Prefetch.empty;
        } else {
            this._prefetch = Prefetch.empty.forRootType(this, javaType.withStaticTyping());
        }
    }

    public ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig) {
        this._config = serializationConfig;
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._generatorSettings = GeneratorSettings.empty;
        this._prefetch = Prefetch.empty;
    }

    public ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig, FormatSchema formatSchema) {
        this._config = serializationConfig;
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._generatorSettings = formatSchema == null ? GeneratorSettings.empty : new GeneratorSettings(null, formatSchema, null, null);
        this._prefetch = Prefetch.empty;
    }

    protected ObjectWriter(ObjectWriter objectWriter, SerializationConfig serializationConfig, GeneratorSettings generatorSettings, Prefetch prefetch) {
        this._config = serializationConfig;
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = objectWriter._generatorFactory;
        this._generatorSettings = generatorSettings;
        this._prefetch = prefetch;
    }

    protected ObjectWriter(ObjectWriter objectWriter, SerializationConfig serializationConfig) {
        this._config = serializationConfig;
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = objectWriter._generatorFactory;
        this._generatorSettings = objectWriter._generatorSettings;
        this._prefetch = objectWriter._prefetch;
    }

    protected ObjectWriter(ObjectWriter objectWriter, JsonFactory jsonFactory) {
        this._config = (SerializationConfig) objectWriter._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, jsonFactory.requiresPropertyOrdering());
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = jsonFactory;
        this._generatorSettings = objectWriter._generatorSettings;
        this._prefetch = objectWriter._prefetch;
    }

    @Override // com.fasterxml.jackson.core.Versioned
    public Version version() {
        return PackageVersion.VERSION;
    }

    protected ObjectWriter _new(ObjectWriter objectWriter, JsonFactory jsonFactory) {
        return new ObjectWriter(objectWriter, jsonFactory);
    }

    protected ObjectWriter _new(ObjectWriter objectWriter, SerializationConfig serializationConfig) {
        return serializationConfig == this._config ? this : new ObjectWriter(objectWriter, serializationConfig);
    }

    protected ObjectWriter _new(GeneratorSettings generatorSettings, Prefetch prefetch) {
        return (this._generatorSettings == generatorSettings && this._prefetch == prefetch) ? this : new ObjectWriter(this, this._config, generatorSettings, prefetch);
    }

    protected SequenceWriter _newSequenceWriter(boolean z, JsonGenerator jsonGenerator, boolean z2) throws IOException {
        _configureGenerator(jsonGenerator);
        return new SequenceWriter(_serializerProvider(), jsonGenerator, z2, this._prefetch).init(z);
    }

    public ObjectWriter with(SerializationFeature serializationFeature) {
        return _new(this, this._config.with(serializationFeature));
    }

    public ObjectWriter with(SerializationFeature serializationFeature, SerializationFeature... serializationFeatureArr) {
        return _new(this, this._config.with(serializationFeature, serializationFeatureArr));
    }

    public ObjectWriter withFeatures(SerializationFeature... serializationFeatureArr) {
        return _new(this, this._config.withFeatures(serializationFeatureArr));
    }

    public ObjectWriter without(SerializationFeature serializationFeature) {
        return _new(this, this._config.without(serializationFeature));
    }

    public ObjectWriter without(SerializationFeature serializationFeature, SerializationFeature... serializationFeatureArr) {
        return _new(this, this._config.without(serializationFeature, serializationFeatureArr));
    }

    public ObjectWriter withoutFeatures(SerializationFeature... serializationFeatureArr) {
        return _new(this, this._config.withoutFeatures(serializationFeatureArr));
    }

    public ObjectWriter with(JsonGenerator.Feature feature) {
        return _new(this, this._config.with(feature));
    }

    public ObjectWriter withFeatures(JsonGenerator.Feature... featureArr) {
        return _new(this, this._config.withFeatures(featureArr));
    }

    public ObjectWriter without(JsonGenerator.Feature feature) {
        return _new(this, this._config.without(feature));
    }

    public ObjectWriter withoutFeatures(JsonGenerator.Feature... featureArr) {
        return _new(this, this._config.withoutFeatures(featureArr));
    }

    public ObjectWriter with(FormatFeature formatFeature) {
        return _new(this, this._config.with(formatFeature));
    }

    public ObjectWriter withFeatures(FormatFeature... formatFeatureArr) {
        return _new(this, this._config.withFeatures(formatFeatureArr));
    }

    public ObjectWriter without(FormatFeature formatFeature) {
        return _new(this, this._config.without(formatFeature));
    }

    public ObjectWriter withoutFeatures(FormatFeature... formatFeatureArr) {
        return _new(this, this._config.withoutFeatures(formatFeatureArr));
    }

    public ObjectWriter forType(JavaType javaType) {
        return _new(this._generatorSettings, this._prefetch.forRootType(this, javaType));
    }

    public ObjectWriter forType(Class<?> cls) {
        if (cls == Object.class) {
            return forType((JavaType) null);
        }
        return forType(this._config.constructType(cls));
    }

    public ObjectWriter forType(TypeReference<?> typeReference) {
        return forType(this._config.getTypeFactory().constructType(typeReference.getType()));
    }

    @Deprecated
    public ObjectWriter withType(JavaType javaType) {
        return forType(javaType);
    }

    @Deprecated
    public ObjectWriter withType(Class<?> cls) {
        return forType(cls);
    }

    @Deprecated
    public ObjectWriter withType(TypeReference<?> typeReference) {
        return forType(typeReference);
    }

    public ObjectWriter with(DateFormat dateFormat) {
        return _new(this, this._config.with(dateFormat));
    }

    public ObjectWriter withDefaultPrettyPrinter() {
        return with(this._config.getDefaultPrettyPrinter());
    }

    public ObjectWriter with(FilterProvider filterProvider) {
        return filterProvider == this._config.getFilterProvider() ? this : _new(this, this._config.withFilters(filterProvider));
    }

    public ObjectWriter with(PrettyPrinter prettyPrinter) {
        return _new(this._generatorSettings.with(prettyPrinter), this._prefetch);
    }

    public ObjectWriter withRootName(String str) {
        return _new(this, this._config.withRootName(str));
    }

    public ObjectWriter withRootName(PropertyName propertyName) {
        return _new(this, this._config.withRootName(propertyName));
    }

    public ObjectWriter withoutRootName() {
        return _new(this, this._config.withRootName(PropertyName.NO_NAME));
    }

    public ObjectWriter with(FormatSchema formatSchema) {
        _verifySchemaType(formatSchema);
        return _new(this._generatorSettings.with(formatSchema), this._prefetch);
    }

    @Deprecated
    public ObjectWriter withSchema(FormatSchema formatSchema) {
        return with(formatSchema);
    }

    public ObjectWriter withView(Class<?> cls) {
        return _new(this, this._config.withView(cls));
    }

    public ObjectWriter with(Locale locale) {
        return _new(this, this._config.with(locale));
    }

    public ObjectWriter with(TimeZone timeZone) {
        return _new(this, this._config.with(timeZone));
    }

    public ObjectWriter with(Base64Variant base64Variant) {
        return _new(this, this._config.with(base64Variant));
    }

    public ObjectWriter with(CharacterEscapes characterEscapes) {
        return _new(this._generatorSettings.with(characterEscapes), this._prefetch);
    }

    public ObjectWriter with(JsonFactory jsonFactory) {
        return jsonFactory == this._generatorFactory ? this : _new(this, jsonFactory);
    }

    public ObjectWriter with(ContextAttributes contextAttributes) {
        return _new(this, this._config.with(contextAttributes));
    }

    public ObjectWriter withAttributes(Map<?, ?> map) {
        return _new(this, this._config.withAttributes(map));
    }

    public ObjectWriter withAttribute(Object obj, Object obj2) {
        return _new(this, this._config.withAttribute(obj, obj2));
    }

    public ObjectWriter withoutAttribute(Object obj) {
        return _new(this, this._config.withoutAttribute(obj));
    }

    public ObjectWriter withRootValueSeparator(String str) {
        return _new(this._generatorSettings.withRootValueSeparator(str), this._prefetch);
    }

    public ObjectWriter withRootValueSeparator(SerializableString serializableString) {
        return _new(this._generatorSettings.withRootValueSeparator(serializableString), this._prefetch);
    }

    public SequenceWriter writeValues(File file) throws IOException {
        return _newSequenceWriter(false, this._generatorFactory.createGenerator(file, JsonEncoding.UTF8), true);
    }

    public SequenceWriter writeValues(JsonGenerator jsonGenerator) throws IOException {
        _configureGenerator(jsonGenerator);
        return _newSequenceWriter(false, jsonGenerator, false);
    }

    public SequenceWriter writeValues(Writer writer) throws IOException {
        return _newSequenceWriter(false, this._generatorFactory.createGenerator(writer), true);
    }

    public SequenceWriter writeValues(OutputStream outputStream) throws IOException {
        return _newSequenceWriter(false, this._generatorFactory.createGenerator(outputStream, JsonEncoding.UTF8), true);
    }

    public SequenceWriter writeValues(DataOutput dataOutput) throws IOException {
        return _newSequenceWriter(false, this._generatorFactory.createGenerator(dataOutput), true);
    }

    public SequenceWriter writeValuesAsArray(File file) throws IOException {
        return _newSequenceWriter(true, this._generatorFactory.createGenerator(file, JsonEncoding.UTF8), true);
    }

    public SequenceWriter writeValuesAsArray(JsonGenerator jsonGenerator) throws IOException {
        return _newSequenceWriter(true, jsonGenerator, false);
    }

    public SequenceWriter writeValuesAsArray(Writer writer) throws IOException {
        return _newSequenceWriter(true, this._generatorFactory.createGenerator(writer), true);
    }

    public SequenceWriter writeValuesAsArray(OutputStream outputStream) throws IOException {
        return _newSequenceWriter(true, this._generatorFactory.createGenerator(outputStream, JsonEncoding.UTF8), true);
    }

    public SequenceWriter writeValuesAsArray(DataOutput dataOutput) throws IOException {
        return _newSequenceWriter(true, this._generatorFactory.createGenerator(dataOutput), true);
    }

    public boolean isEnabled(SerializationFeature serializationFeature) {
        return this._config.isEnabled(serializationFeature);
    }

    public boolean isEnabled(MapperFeature mapperFeature) {
        return this._config.isEnabled(mapperFeature);
    }

    @Deprecated
    public boolean isEnabled(JsonParser.Feature feature) {
        return this._generatorFactory.isEnabled(feature);
    }

    public boolean isEnabled(JsonGenerator.Feature feature) {
        return this._generatorFactory.isEnabled(feature);
    }

    public SerializationConfig getConfig() {
        return this._config;
    }

    public JsonFactory getFactory() {
        return this._generatorFactory;
    }

    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public boolean hasPrefetchedSerializer() {
        return this._prefetch.hasSerializer();
    }

    public ContextAttributes getAttributes() {
        return this._config.getAttributes();
    }

    public void writeValue(JsonGenerator jsonGenerator, Object obj) throws IOException {
        _configureGenerator(jsonGenerator);
        if (!this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) || !(obj instanceof Closeable)) {
            this._prefetch.serialize(jsonGenerator, obj, _serializerProvider());
            if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                jsonGenerator.flush();
                return;
            }
            return;
        }
        Closeable closeable = (Closeable) obj;
        try {
            this._prefetch.serialize(jsonGenerator, obj, _serializerProvider());
            if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                jsonGenerator.flush();
            }
            closeable.close();
        } catch (Exception e) {
            ClassUtil.closeOnFailAndThrowAsIOE(null, closeable, e);
        }
    }

    public void writeValue(File file, Object obj) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._generatorFactory.createGenerator(file, JsonEncoding.UTF8), obj);
    }

    public void writeValue(OutputStream outputStream, Object obj) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._generatorFactory.createGenerator(outputStream, JsonEncoding.UTF8), obj);
    }

    public void writeValue(Writer writer, Object obj) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._generatorFactory.createGenerator(writer), obj);
    }

    public void writeValue(DataOutput dataOutput, Object obj) throws IOException {
        _configAndWriteValue(this._generatorFactory.createGenerator(dataOutput), obj);
    }

    public String writeValueAsString(Object obj) throws JsonProcessingException {
        SegmentedStringWriter segmentedStringWriter = new SegmentedStringWriter(this._generatorFactory._getBufferRecycler());
        try {
            _configAndWriteValue(this._generatorFactory.createGenerator(segmentedStringWriter), obj);
            return segmentedStringWriter.getAndClear();
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e2) {
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }

    public byte[] writeValueAsBytes(Object obj) throws JsonProcessingException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(this._generatorFactory._getBufferRecycler());
        try {
            _configAndWriteValue(this._generatorFactory.createGenerator(byteArrayBuilder, JsonEncoding.UTF8), obj);
            byte[] byteArray = byteArrayBuilder.toByteArray();
            byteArrayBuilder.release();
            return byteArray;
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e2) {
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }

    public void acceptJsonFormatVisitor(JavaType javaType, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        if (javaType != null) {
            _serializerProvider().acceptJsonFormatVisitor(javaType, jsonFormatVisitorWrapper);
            return;
        }
        throw new IllegalArgumentException("type must be provided");
    }

    public void acceptJsonFormatVisitor(Class<?> cls, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        acceptJsonFormatVisitor(this._config.constructType(cls), jsonFormatVisitorWrapper);
    }

    public boolean canSerialize(Class<?> cls) {
        return _serializerProvider().hasSerializerFor(cls, null);
    }

    public boolean canSerialize(Class<?> cls, AtomicReference<Throwable> atomicReference) {
        return _serializerProvider().hasSerializerFor(cls, atomicReference);
    }

    protected DefaultSerializerProvider _serializerProvider() {
        return this._serializerProvider.createInstance(this._config, this._serializerFactory);
    }

    protected void _verifySchemaType(FormatSchema formatSchema) {
        if (formatSchema != null && !this._generatorFactory.canUseSchema(formatSchema)) {
            throw new IllegalArgumentException("Cannot use FormatSchema of type " + formatSchema.getClass().getName() + " for format " + this._generatorFactory.getFormatName());
        }
    }

    protected final void _configAndWriteValue(JsonGenerator jsonGenerator, Object obj) throws IOException {
        _configureGenerator(jsonGenerator);
        if (!this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) || !(obj instanceof Closeable)) {
            try {
                this._prefetch.serialize(jsonGenerator, obj, _serializerProvider());
                jsonGenerator.close();
            } catch (Exception e) {
                ClassUtil.closeOnFailAndThrowAsIOE(jsonGenerator, e);
            }
        } else {
            a(jsonGenerator, obj);
        }
    }

    private final void a(JsonGenerator jsonGenerator, Object obj) throws IOException {
        Exception e;
        Closeable closeable = (Closeable) obj;
        try {
            this._prefetch.serialize(jsonGenerator, obj, _serializerProvider());
            closeable = null;
        } catch (Exception e2) {
            e = e2;
        }
        try {
            closeable.close();
            jsonGenerator.close();
        } catch (Exception e3) {
            e = e3;
            ClassUtil.closeOnFailAndThrowAsIOE(jsonGenerator, closeable, e);
        }
    }

    protected final void _configureGenerator(JsonGenerator jsonGenerator) {
        this._config.initialize(jsonGenerator);
        this._generatorSettings.initialize(jsonGenerator);
    }

    /* loaded from: classes.dex */
    public static final class GeneratorSettings implements Serializable {
        public static final GeneratorSettings empty = new GeneratorSettings(null, null, null, null);
        private static final long serialVersionUID = 1;
        public final CharacterEscapes characterEscapes;
        public final PrettyPrinter prettyPrinter;
        public final SerializableString rootValueSeparator;
        public final FormatSchema schema;

        public GeneratorSettings(PrettyPrinter prettyPrinter, FormatSchema formatSchema, CharacterEscapes characterEscapes, SerializableString serializableString) {
            this.prettyPrinter = prettyPrinter;
            this.schema = formatSchema;
            this.characterEscapes = characterEscapes;
            this.rootValueSeparator = serializableString;
        }

        public GeneratorSettings with(PrettyPrinter prettyPrinter) {
            if (prettyPrinter == null) {
                prettyPrinter = ObjectWriter.NULL_PRETTY_PRINTER;
            }
            return prettyPrinter == this.prettyPrinter ? this : new GeneratorSettings(prettyPrinter, this.schema, this.characterEscapes, this.rootValueSeparator);
        }

        public GeneratorSettings with(FormatSchema formatSchema) {
            return this.schema == formatSchema ? this : new GeneratorSettings(this.prettyPrinter, formatSchema, this.characterEscapes, this.rootValueSeparator);
        }

        public GeneratorSettings with(CharacterEscapes characterEscapes) {
            return this.characterEscapes == characterEscapes ? this : new GeneratorSettings(this.prettyPrinter, this.schema, characterEscapes, this.rootValueSeparator);
        }

        public GeneratorSettings withRootValueSeparator(String str) {
            return str == null ? this.rootValueSeparator == null ? this : new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, null) : str.equals(a()) ? this : new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, new SerializedString(str));
        }

        public GeneratorSettings withRootValueSeparator(SerializableString serializableString) {
            return serializableString == null ? this.rootValueSeparator == null ? this : new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, null) : serializableString.equals(this.rootValueSeparator) ? this : new GeneratorSettings(this.prettyPrinter, this.schema, this.characterEscapes, serializableString);
        }

        private final String a() {
            SerializableString serializableString = this.rootValueSeparator;
            if (serializableString == null) {
                return null;
            }
            return serializableString.getValue();
        }

        public void initialize(JsonGenerator jsonGenerator) {
            PrettyPrinter prettyPrinter = this.prettyPrinter;
            if (prettyPrinter != null) {
                if (prettyPrinter == ObjectWriter.NULL_PRETTY_PRINTER) {
                    jsonGenerator.setPrettyPrinter(null);
                } else {
                    if (prettyPrinter instanceof Instantiatable) {
                        prettyPrinter = (PrettyPrinter) ((Instantiatable) prettyPrinter).createInstance();
                    }
                    jsonGenerator.setPrettyPrinter(prettyPrinter);
                }
            }
            CharacterEscapes characterEscapes = this.characterEscapes;
            if (characterEscapes != null) {
                jsonGenerator.setCharacterEscapes(characterEscapes);
            }
            FormatSchema formatSchema = this.schema;
            if (formatSchema != null) {
                jsonGenerator.setSchema(formatSchema);
            }
            SerializableString serializableString = this.rootValueSeparator;
            if (serializableString != null) {
                jsonGenerator.setRootValueSeparator(serializableString);
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class Prefetch implements Serializable {
        public static final Prefetch empty = new Prefetch(null, null, null);
        private static final long serialVersionUID = 1;
        private final JavaType rootType;
        private final TypeSerializer typeSerializer;
        private final JsonSerializer<Object> valueSerializer;

        private Prefetch(JavaType javaType, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer) {
            this.rootType = javaType;
            this.valueSerializer = jsonSerializer;
            this.typeSerializer = typeSerializer;
        }

        public Prefetch forRootType(ObjectWriter objectWriter, JavaType javaType) {
            if (javaType == null || javaType.isJavaLangObject()) {
                return (this.rootType == null || this.valueSerializer == null) ? this : new Prefetch(null, null, this.typeSerializer);
            }
            if (javaType.equals(this.rootType)) {
                return this;
            }
            if (objectWriter.isEnabled(SerializationFeature.EAGER_SERIALIZER_FETCH)) {
                try {
                    JsonSerializer<Object> findTypedValueSerializer = objectWriter._serializerProvider().findTypedValueSerializer(javaType, true, (BeanProperty) null);
                    if (findTypedValueSerializer instanceof TypeWrappedSerializer) {
                        return new Prefetch(javaType, null, ((TypeWrappedSerializer) findTypedValueSerializer).typeSerializer());
                    }
                    return new Prefetch(javaType, findTypedValueSerializer, null);
                } catch (JsonProcessingException unused) {
                }
            }
            return new Prefetch(javaType, null, this.typeSerializer);
        }

        public final JsonSerializer<Object> getValueSerializer() {
            return this.valueSerializer;
        }

        public final TypeSerializer getTypeSerializer() {
            return this.typeSerializer;
        }

        public boolean hasSerializer() {
            return (this.valueSerializer == null && this.typeSerializer == null) ? false : true;
        }

        public void serialize(JsonGenerator jsonGenerator, Object obj, DefaultSerializerProvider defaultSerializerProvider) throws IOException {
            TypeSerializer typeSerializer = this.typeSerializer;
            if (typeSerializer != null) {
                defaultSerializerProvider.serializePolymorphic(jsonGenerator, obj, this.rootType, this.valueSerializer, typeSerializer);
                return;
            }
            JsonSerializer<Object> jsonSerializer = this.valueSerializer;
            if (jsonSerializer != null) {
                defaultSerializerProvider.serializeValue(jsonGenerator, obj, this.rootType, jsonSerializer);
                return;
            }
            JavaType javaType = this.rootType;
            if (javaType != null) {
                defaultSerializerProvider.serializeValue(jsonGenerator, obj, javaType);
            } else {
                defaultSerializerProvider.serializeValue(jsonGenerator, obj);
            }
        }
    }
}
