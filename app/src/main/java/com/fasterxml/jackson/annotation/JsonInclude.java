package com.fasterxml.jackson.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface JsonInclude {

    /* loaded from: classes.dex */
    public enum Include {
        ALWAYS,
        NON_NULL,
        NON_ABSENT,
        NON_EMPTY,
        NON_DEFAULT,
        CUSTOM,
        USE_DEFAULTS
    }

    Include content() default Include.ALWAYS;

    Class<?> contentFilter() default Void.class;

    Include value() default Include.ALWAYS;

    Class<?> valueFilter() default Void.class;

    /* loaded from: classes.dex */
    public static class Value implements JacksonAnnotationValue<JsonInclude>, Serializable {
        protected static final Value EMPTY = new Value(Include.USE_DEFAULTS, Include.USE_DEFAULTS, null, null);
        private static final long serialVersionUID = 1;
        protected final Class<?> _contentFilter;
        protected final Include _contentInclusion;
        protected final Class<?> _valueFilter;
        protected final Include _valueInclusion;

        public Value(JsonInclude jsonInclude) {
            this(jsonInclude.value(), jsonInclude.content(), jsonInclude.valueFilter(), jsonInclude.contentFilter());
        }

        protected Value(Include include, Include include2, Class<?> cls, Class<?> cls2) {
            this._valueInclusion = include == null ? Include.USE_DEFAULTS : include;
            this._contentInclusion = include2 == null ? Include.USE_DEFAULTS : include2;
            Class<?> cls3 = null;
            this._valueFilter = cls == Void.class ? null : cls;
            this._contentFilter = cls2 != Void.class ? cls2 : cls3;
        }

        public static Value empty() {
            return EMPTY;
        }

        public static Value merge(Value value, Value value2) {
            return value == null ? value2 : value.withOverrides(value2);
        }

        public static Value mergeAll(Value... valueArr) {
            int length = valueArr.length;
            Value value = null;
            for (int i = 0; i < length; i++) {
                value = valueArr[i];
                if (value != null) {
                    if (value != null) {
                        value = value.withOverrides(value);
                    }
                }
            }
            return value;
        }

        protected Object readResolve() {
            return (this._valueInclusion == Include.USE_DEFAULTS && this._contentInclusion == Include.USE_DEFAULTS && this._valueFilter == null && this._contentFilter == null) ? EMPTY : this;
        }

        public Value withOverrides(Value value) {
            if (value == null || value == EMPTY) {
                return this;
            }
            Include include = value._valueInclusion;
            Include include2 = value._contentInclusion;
            Class<?> cls = value._valueFilter;
            Class<?> cls2 = value._contentFilter;
            boolean z = true;
            boolean z2 = (include == this._valueInclusion || include == Include.USE_DEFAULTS) ? false : true;
            boolean z3 = (include2 == this._contentInclusion || include2 == Include.USE_DEFAULTS) ? false : true;
            Class<?> cls3 = this._valueFilter;
            if (cls == cls3 && cls2 == cls3) {
                z = false;
            }
            if (z2) {
                if (z3) {
                    return new Value(include, include2, cls, cls2);
                }
                return new Value(include, this._contentInclusion, cls, cls2);
            } else if (z3) {
                return new Value(this._valueInclusion, include2, cls, cls2);
            } else {
                return z ? new Value(this._valueInclusion, this._contentInclusion, cls, cls2) : this;
            }
        }

        public static Value construct(Include include, Include include2) {
            if ((include == Include.USE_DEFAULTS || include == null) && (include2 == Include.USE_DEFAULTS || include2 == null)) {
                return EMPTY;
            }
            return new Value(include, include2, null, null);
        }

        public static Value construct(Include include, Include include2, Class<?> cls, Class<?> cls2) {
            if (cls == Void.class) {
                cls = null;
            }
            if (cls2 == Void.class) {
                cls2 = null;
            }
            if ((include == Include.USE_DEFAULTS || include == null) && ((include2 == Include.USE_DEFAULTS || include2 == null) && cls == null && cls2 == null)) {
                return EMPTY;
            }
            return new Value(include, include2, cls, cls2);
        }

        public static Value from(JsonInclude jsonInclude) {
            if (jsonInclude == null) {
                return EMPTY;
            }
            Include value = jsonInclude.value();
            Include content = jsonInclude.content();
            if (value == Include.USE_DEFAULTS && content == Include.USE_DEFAULTS) {
                return EMPTY;
            }
            Class<?> valueFilter = jsonInclude.valueFilter();
            if (valueFilter == Void.class) {
                valueFilter = null;
            }
            Class<?> contentFilter = jsonInclude.contentFilter();
            if (contentFilter == Void.class) {
                contentFilter = null;
            }
            return new Value(value, content, valueFilter, contentFilter);
        }

        public Value withValueInclusion(Include include) {
            return include == this._valueInclusion ? this : new Value(include, this._contentInclusion, this._valueFilter, this._contentFilter);
        }

        public Value withValueFilter(Class<?> cls) {
            Include include;
            if (cls == null || cls == Void.class) {
                include = Include.USE_DEFAULTS;
                cls = null;
            } else {
                include = Include.CUSTOM;
            }
            return construct(include, this._contentInclusion, cls, this._contentFilter);
        }

        public Value withContentFilter(Class<?> cls) {
            Include include;
            if (cls == null || cls == Void.class) {
                include = Include.USE_DEFAULTS;
                cls = null;
            } else {
                include = Include.CUSTOM;
            }
            return construct(this._valueInclusion, include, this._valueFilter, cls);
        }

        public Value withContentInclusion(Include include) {
            return include == this._contentInclusion ? this : new Value(this._valueInclusion, include, this._valueFilter, this._contentFilter);
        }

        @Override // com.fasterxml.jackson.annotation.JacksonAnnotationValue
        public Class<JsonInclude> valueFor() {
            return JsonInclude.class;
        }

        public Include getValueInclusion() {
            return this._valueInclusion;
        }

        public Include getContentInclusion() {
            return this._contentInclusion;
        }

        public Class<?> getValueFilter() {
            return this._valueFilter;
        }

        public Class<?> getContentFilter() {
            return this._contentFilter;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(80);
            sb.append("JsonInclude.Value(value=");
            sb.append(this._valueInclusion);
            sb.append(",content=");
            sb.append(this._contentInclusion);
            if (this._valueFilter != null) {
                sb.append(",valueFilter=");
                sb.append(this._valueFilter.getName());
                sb.append(".class");
            }
            if (this._contentFilter != null) {
                sb.append(",contentFilter=");
                sb.append(this._contentFilter.getName());
                sb.append(".class");
            }
            sb.append(')');
            return sb.toString();
        }

        public int hashCode() {
            return (this._valueInclusion.hashCode() << 2) + this._contentInclusion.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            Value value = (Value) obj;
            return value._valueInclusion == this._valueInclusion && value._contentInclusion == this._contentInclusion && value._valueFilter == this._valueFilter && value._contentFilter == this._contentFilter;
        }
    }
}
