package com.fasterxml.jackson.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface JsonSetter {
    Nulls contentNulls() default Nulls.DEFAULT;

    Nulls nulls() default Nulls.DEFAULT;

    String value() default "";

    /* loaded from: classes.dex */
    public static class Value implements JacksonAnnotationValue<JsonSetter>, Serializable {
        protected static final Value EMPTY = new Value(Nulls.DEFAULT, Nulls.DEFAULT);
        private static final long serialVersionUID = 1;
        private final Nulls _contentNulls;
        private final Nulls _nulls;

        protected Value(Nulls nulls, Nulls nulls2) {
            this._nulls = nulls;
            this._contentNulls = nulls2;
        }

        @Override // com.fasterxml.jackson.annotation.JacksonAnnotationValue
        public Class<JsonSetter> valueFor() {
            return JsonSetter.class;
        }

        protected Object readResolve() {
            return a(this._nulls, this._contentNulls) ? EMPTY : this;
        }

        public static Value from(JsonSetter jsonSetter) {
            if (jsonSetter == null) {
                return EMPTY;
            }
            return construct(jsonSetter.nulls(), jsonSetter.contentNulls());
        }

        public static Value construct(Nulls nulls, Nulls nulls2) {
            if (nulls == null) {
                nulls = Nulls.DEFAULT;
            }
            if (nulls2 == null) {
                nulls2 = Nulls.DEFAULT;
            }
            if (a(nulls, nulls2)) {
                return EMPTY;
            }
            return new Value(nulls, nulls2);
        }

        public static Value empty() {
            return EMPTY;
        }

        public static Value merge(Value value, Value value2) {
            return value == null ? value2 : value.withOverrides(value2);
        }

        public static Value forValueNulls(Nulls nulls) {
            return construct(nulls, Nulls.DEFAULT);
        }

        public static Value forValueNulls(Nulls nulls, Nulls nulls2) {
            return construct(nulls, nulls2);
        }

        public static Value forContentNulls(Nulls nulls) {
            return construct(Nulls.DEFAULT, nulls);
        }

        public Value withOverrides(Value value) {
            if (value == null || value == EMPTY) {
                return this;
            }
            Nulls nulls = value._nulls;
            Nulls nulls2 = value._contentNulls;
            if (nulls == Nulls.DEFAULT) {
                nulls = this._nulls;
            }
            if (nulls2 == Nulls.DEFAULT) {
                nulls2 = this._contentNulls;
            }
            return (nulls == this._nulls && nulls2 == this._contentNulls) ? this : construct(nulls, nulls2);
        }

        public Value withValueNulls(Nulls nulls) {
            if (nulls == null) {
                nulls = Nulls.DEFAULT;
            }
            return nulls == this._nulls ? this : construct(nulls, this._contentNulls);
        }

        public Value withValueNulls(Nulls nulls, Nulls nulls2) {
            if (nulls == null) {
                nulls = Nulls.DEFAULT;
            }
            if (nulls2 == null) {
                nulls2 = Nulls.DEFAULT;
            }
            return (nulls == this._nulls && nulls2 == this._contentNulls) ? this : construct(nulls, nulls2);
        }

        public Value withContentNulls(Nulls nulls) {
            if (nulls == null) {
                nulls = Nulls.DEFAULT;
            }
            return nulls == this._contentNulls ? this : construct(this._nulls, nulls);
        }

        public Nulls getValueNulls() {
            return this._nulls;
        }

        public Nulls getContentNulls() {
            return this._contentNulls;
        }

        public Nulls nonDefaultValueNulls() {
            if (this._nulls == Nulls.DEFAULT) {
                return null;
            }
            return this._nulls;
        }

        public Nulls nonDefaultContentNulls() {
            if (this._contentNulls == Nulls.DEFAULT) {
                return null;
            }
            return this._contentNulls;
        }

        public String toString() {
            return String.format("JsonSetter.Value(valueNulls=%s,contentNulls=%s)", this._nulls, this._contentNulls);
        }

        public int hashCode() {
            return this._nulls.ordinal() + (this._contentNulls.ordinal() << 2);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            Value value = (Value) obj;
            return value._nulls == this._nulls && value._contentNulls == this._contentNulls;
        }

        private static boolean a(Nulls nulls, Nulls nulls2) {
            return nulls == Nulls.DEFAULT && nulls2 == Nulls.DEFAULT;
        }
    }
}
