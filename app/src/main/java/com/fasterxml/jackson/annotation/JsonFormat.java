package com.fasterxml.jackson.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;
import java.util.TimeZone;

@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface JsonFormat {
    public static final String DEFAULT_LOCALE = "##default";
    public static final String DEFAULT_TIMEZONE = "##default";

    /* loaded from: classes.dex */
    public enum Feature {
        ACCEPT_SINGLE_VALUE_AS_ARRAY,
        ACCEPT_CASE_INSENSITIVE_PROPERTIES,
        WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
        WRITE_DATES_WITH_ZONE_ID,
        WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED,
        WRITE_SORTED_MAP_ENTRIES,
        ADJUST_DATES_TO_CONTEXT_TIME_ZONE
    }

    OptBoolean lenient() default OptBoolean.DEFAULT;

    String locale() default "##default";

    String pattern() default "";

    Shape shape() default Shape.ANY;

    String timezone() default "##default";

    Feature[] with() default {};

    Feature[] without() default {};

    /* loaded from: classes.dex */
    public enum Shape {
        ANY,
        NATURAL,
        SCALAR,
        ARRAY,
        OBJECT,
        NUMBER,
        NUMBER_FLOAT,
        NUMBER_INT,
        STRING,
        BOOLEAN;

        public boolean isNumeric() {
            return this == NUMBER || this == NUMBER_INT || this == NUMBER_FLOAT;
        }

        public boolean isStructured() {
            return this == OBJECT || this == ARRAY;
        }
    }

    /* loaded from: classes.dex */
    public static class Features {
        private static final Features c = new Features(0, 0);
        private final int a;
        private final int b;

        private Features(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public static Features empty() {
            return c;
        }

        public static Features construct(JsonFormat jsonFormat) {
            return construct(jsonFormat.with(), jsonFormat.without());
        }

        public static Features construct(Feature[] featureArr, Feature[] featureArr2) {
            int i = 0;
            for (Feature feature : featureArr) {
                i |= 1 << feature.ordinal();
            }
            int i2 = 0;
            for (Feature feature2 : featureArr2) {
                i2 |= 1 << feature2.ordinal();
            }
            return new Features(i, i2);
        }

        public Features withOverrides(Features features) {
            if (features == null) {
                return this;
            }
            int i = features.b;
            int i2 = features.a;
            if (i == 0 && i2 == 0) {
                return this;
            }
            if (this.a == 0 && this.b == 0) {
                return features;
            }
            int i3 = this.a;
            int i4 = ((~i) & i3) | i2;
            int i5 = this.b;
            int i6 = i | ((~i2) & i5);
            return (i4 == i3 && i6 == i5) ? this : new Features(i4, i6);
        }

        public Features with(Feature... featureArr) {
            int i = this.a;
            for (Feature feature : featureArr) {
                i |= 1 << feature.ordinal();
            }
            return i == this.a ? this : new Features(i, this.b);
        }

        public Features without(Feature... featureArr) {
            int i = this.b;
            for (Feature feature : featureArr) {
                i |= 1 << feature.ordinal();
            }
            return i == this.b ? this : new Features(this.a, i);
        }

        public Boolean get(Feature feature) {
            int ordinal = 1 << feature.ordinal();
            if ((this.b & ordinal) != 0) {
                return Boolean.FALSE;
            }
            if ((ordinal & this.a) != 0) {
                return Boolean.TRUE;
            }
            return null;
        }

        public int hashCode() {
            return this.b + this.a;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            Features features = (Features) obj;
            return features.a == this.a && features.b == this.b;
        }
    }

    /* loaded from: classes.dex */
    public static class Value implements JacksonAnnotationValue<JsonFormat>, Serializable {
        private static final Value a = new Value();
        private static final long serialVersionUID = 1;
        private final Features _features;
        private final Boolean _lenient;
        private final Locale _locale;
        private final String _pattern;
        private final Shape _shape;
        private final String _timezoneStr;
        private transient TimeZone b;

        public Value() {
            this("", Shape.ANY, "", "", Features.empty(), (Boolean) null);
        }

        public Value(JsonFormat jsonFormat) {
            this(jsonFormat.pattern(), jsonFormat.shape(), jsonFormat.locale(), jsonFormat.timezone(), Features.construct(jsonFormat), jsonFormat.lenient().asBoolean());
        }

        public Value(String str, Shape shape, String str2, String str3, Features features, Boolean bool) {
            this(str, shape, (str2 == null || str2.length() == 0 || "##default".equals(str2)) ? null : new Locale(str2), (str3 == null || str3.length() == 0 || "##default".equals(str3)) ? null : str3, null, features, bool);
        }

        public Value(String str, Shape shape, Locale locale, TimeZone timeZone, Features features, Boolean bool) {
            this._pattern = str;
            this._shape = shape == null ? Shape.ANY : shape;
            this._locale = locale;
            this.b = timeZone;
            this._timezoneStr = null;
            this._features = features == null ? Features.empty() : features;
            this._lenient = bool;
        }

        public Value(String str, Shape shape, Locale locale, String str2, TimeZone timeZone, Features features, Boolean bool) {
            this._pattern = str;
            this._shape = shape == null ? Shape.ANY : shape;
            this._locale = locale;
            this.b = timeZone;
            this._timezoneStr = str2;
            this._features = features == null ? Features.empty() : features;
            this._lenient = bool;
        }

        @Deprecated
        public Value(String str, Shape shape, Locale locale, String str2, TimeZone timeZone, Features features) {
            this(str, shape, locale, str2, timeZone, features, null);
        }

        @Deprecated
        public Value(String str, Shape shape, String str2, String str3, Features features) {
            this(str, shape, str2, str3, features, (Boolean) null);
        }

        @Deprecated
        public Value(String str, Shape shape, Locale locale, TimeZone timeZone, Features features) {
            this(str, shape, locale, timeZone, features, (Boolean) null);
        }

        public static final Value empty() {
            return a;
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

        public static final Value from(JsonFormat jsonFormat) {
            return jsonFormat == null ? a : new Value(jsonFormat);
        }

        public final Value withOverrides(Value value) {
            Value value2;
            Features features;
            TimeZone timeZone;
            String str;
            if (value == null || value == (value2 = a) || value == this) {
                return this;
            }
            if (this == value2) {
                return value;
            }
            String str2 = value._pattern;
            String str3 = (str2 == null || str2.isEmpty()) ? this._pattern : str2;
            Shape shape = value._shape;
            Shape shape2 = shape == Shape.ANY ? this._shape : shape;
            Locale locale = value._locale;
            Locale locale2 = locale == null ? this._locale : locale;
            Features features2 = this._features;
            if (features2 == null) {
                features = value._features;
            } else {
                features = features2.withOverrides(value._features);
            }
            Boolean bool = value._lenient;
            Boolean bool2 = bool == null ? this._lenient : bool;
            String str4 = value._timezoneStr;
            if (str4 == null || str4.isEmpty()) {
                str = this._timezoneStr;
                timeZone = this.b;
            } else {
                timeZone = value.b;
                str = str4;
            }
            return new Value(str3, shape2, locale2, str, timeZone, features, bool2);
        }

        public static Value forPattern(String str) {
            return new Value(str, null, null, null, null, Features.empty(), null);
        }

        public static Value forShape(Shape shape) {
            return new Value(null, shape, null, null, null, Features.empty(), null);
        }

        public static Value forLeniency(boolean z) {
            return new Value(null, null, null, null, null, Features.empty(), Boolean.valueOf(z));
        }

        public Value withPattern(String str) {
            return new Value(str, this._shape, this._locale, this._timezoneStr, this.b, this._features, this._lenient);
        }

        public Value withShape(Shape shape) {
            return shape == this._shape ? this : new Value(this._pattern, shape, this._locale, this._timezoneStr, this.b, this._features, this._lenient);
        }

        public Value withLocale(Locale locale) {
            return new Value(this._pattern, this._shape, locale, this._timezoneStr, this.b, this._features, this._lenient);
        }

        public Value withTimeZone(TimeZone timeZone) {
            return new Value(this._pattern, this._shape, this._locale, null, timeZone, this._features, this._lenient);
        }

        public Value withLenient(Boolean bool) {
            return bool == this._lenient ? this : new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this.b, this._features, bool);
        }

        public Value withFeature(Feature feature) {
            Features with = this._features.with(feature);
            return with == this._features ? this : new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this.b, with, this._lenient);
        }

        public Value withoutFeature(Feature feature) {
            Features without = this._features.without(feature);
            return without == this._features ? this : new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this.b, without, this._lenient);
        }

        @Override // com.fasterxml.jackson.annotation.JacksonAnnotationValue
        public Class<JsonFormat> valueFor() {
            return JsonFormat.class;
        }

        public String getPattern() {
            return this._pattern;
        }

        public Shape getShape() {
            return this._shape;
        }

        public Locale getLocale() {
            return this._locale;
        }

        public Boolean getLenient() {
            return this._lenient;
        }

        public boolean isLenient() {
            return Boolean.TRUE.equals(this._lenient);
        }

        public String timeZoneAsString() {
            TimeZone timeZone = this.b;
            if (timeZone != null) {
                return timeZone.getID();
            }
            return this._timezoneStr;
        }

        public TimeZone getTimeZone() {
            TimeZone timeZone = this.b;
            if (timeZone != null) {
                return timeZone;
            }
            String str = this._timezoneStr;
            if (str == null) {
                return null;
            }
            TimeZone timeZone2 = TimeZone.getTimeZone(str);
            this.b = timeZone2;
            return timeZone2;
        }

        public boolean hasShape() {
            return this._shape != Shape.ANY;
        }

        public boolean hasPattern() {
            String str = this._pattern;
            return str != null && str.length() > 0;
        }

        public boolean hasLocale() {
            return this._locale != null;
        }

        public boolean hasTimeZone() {
            String str;
            return this.b != null || ((str = this._timezoneStr) != null && !str.isEmpty());
        }

        public boolean hasLenient() {
            return this._lenient != null;
        }

        public Boolean getFeature(Feature feature) {
            return this._features.get(feature);
        }

        public Features getFeatures() {
            return this._features;
        }

        public String toString() {
            return String.format("JsonFormat.Value(pattern=%s,shape=%s,lenient=%s,locale=%s,timezone=%s)", this._pattern, this._shape, this._lenient, this._locale, this._timezoneStr);
        }

        public int hashCode() {
            String str = this._timezoneStr;
            int hashCode = str == null ? 1 : str.hashCode();
            String str2 = this._pattern;
            if (str2 != null) {
                hashCode ^= str2.hashCode();
            }
            int hashCode2 = hashCode + this._shape.hashCode();
            Boolean bool = this._lenient;
            if (bool != null) {
                hashCode2 ^= bool.hashCode();
            }
            Locale locale = this._locale;
            if (locale != null) {
                hashCode2 += locale.hashCode();
            }
            return hashCode2 ^ this._features.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            Value value = (Value) obj;
            if (this._shape != value._shape || !this._features.equals(value._features)) {
                return false;
            }
            return a(this._lenient, value._lenient) && a(this._timezoneStr, value._timezoneStr) && a(this._pattern, value._pattern) && a(this.b, value.b) && a(this._locale, value._locale);
        }

        private static <T> boolean a(T t, T t2) {
            if (t == null) {
                return t2 == null;
            }
            if (t2 == null) {
                return false;
            }
            return t.equals(t2);
        }
    }
}
