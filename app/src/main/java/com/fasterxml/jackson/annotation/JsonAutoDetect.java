package com.fasterxml.jackson.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface JsonAutoDetect {
    Visibility creatorVisibility() default Visibility.DEFAULT;

    Visibility fieldVisibility() default Visibility.DEFAULT;

    Visibility getterVisibility() default Visibility.DEFAULT;

    Visibility isGetterVisibility() default Visibility.DEFAULT;

    Visibility setterVisibility() default Visibility.DEFAULT;

    /* loaded from: classes.dex */
    public enum Visibility {
        ANY,
        NON_PRIVATE,
        PROTECTED_AND_PUBLIC,
        PUBLIC_ONLY,
        NONE,
        DEFAULT;

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public boolean isVisible(Member member) {
            switch (this) {
                case ANY:
                    return true;
                case NONE:
                    return false;
                case NON_PRIVATE:
                    return !Modifier.isPrivate(member.getModifiers());
                case PROTECTED_AND_PUBLIC:
                    if (Modifier.isProtected(member.getModifiers())) {
                        return true;
                    }
                    break;
                case PUBLIC_ONLY:
                    break;
                default:
                    return false;
            }
            return Modifier.isPublic(member.getModifiers());
        }
    }

    /* loaded from: classes.dex */
    public static class Value implements JacksonAnnotationValue<JsonAutoDetect>, Serializable {
        private static final long serialVersionUID = 1;
        protected final Visibility _creatorVisibility;
        protected final Visibility _fieldVisibility;
        protected final Visibility _getterVisibility;
        protected final Visibility _isGetterVisibility;
        protected final Visibility _setterVisibility;
        private static final Visibility a = Visibility.PUBLIC_ONLY;
        protected static final Value DEFAULT = new Value(a, Visibility.PUBLIC_ONLY, Visibility.PUBLIC_ONLY, Visibility.ANY, Visibility.PUBLIC_ONLY);
        protected static final Value NO_OVERRIDES = new Value(Visibility.DEFAULT, Visibility.DEFAULT, Visibility.DEFAULT, Visibility.DEFAULT, Visibility.DEFAULT);

        private Value(Visibility visibility, Visibility visibility2, Visibility visibility3, Visibility visibility4, Visibility visibility5) {
            this._fieldVisibility = visibility;
            this._getterVisibility = visibility2;
            this._isGetterVisibility = visibility3;
            this._setterVisibility = visibility4;
            this._creatorVisibility = visibility5;
        }

        public static Value defaultVisibility() {
            return DEFAULT;
        }

        public static Value noOverrides() {
            return NO_OVERRIDES;
        }

        public static Value from(JsonAutoDetect jsonAutoDetect) {
            return construct(jsonAutoDetect.fieldVisibility(), jsonAutoDetect.getterVisibility(), jsonAutoDetect.isGetterVisibility(), jsonAutoDetect.setterVisibility(), jsonAutoDetect.creatorVisibility());
        }

        public static Value construct(PropertyAccessor propertyAccessor, Visibility visibility) {
            Visibility visibility2 = Visibility.DEFAULT;
            Visibility visibility3 = Visibility.DEFAULT;
            Visibility visibility4 = Visibility.DEFAULT;
            Visibility visibility5 = Visibility.DEFAULT;
            Visibility visibility6 = Visibility.DEFAULT;
            switch (propertyAccessor) {
                case CREATOR:
                    visibility6 = visibility;
                    break;
                case FIELD:
                    visibility2 = visibility;
                    break;
                case GETTER:
                    visibility3 = visibility;
                    break;
                case IS_GETTER:
                    visibility4 = visibility;
                    break;
                case SETTER:
                    visibility5 = visibility;
                    break;
                case ALL:
                    visibility2 = visibility;
                    visibility3 = visibility2;
                    visibility4 = visibility3;
                    visibility5 = visibility4;
                    visibility6 = visibility5;
                    break;
            }
            return construct(visibility2, visibility3, visibility4, visibility5, visibility6);
        }

        public static Value construct(Visibility visibility, Visibility visibility2, Visibility visibility3, Visibility visibility4, Visibility visibility5) {
            Value a2 = a(visibility, visibility2, visibility3, visibility4, visibility5);
            return a2 == null ? new Value(visibility, visibility2, visibility3, visibility4, visibility5) : a2;
        }

        public Value withFieldVisibility(Visibility visibility) {
            return construct(visibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }

        public Value withGetterVisibility(Visibility visibility) {
            return construct(this._fieldVisibility, visibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }

        public Value withIsGetterVisibility(Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, visibility, this._setterVisibility, this._creatorVisibility);
        }

        public Value withSetterVisibility(Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, visibility, this._creatorVisibility);
        }

        public Value withCreatorVisibility(Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, visibility);
        }

        public static Value merge(Value value, Value value2) {
            return value == null ? value2 : value.withOverrides(value2);
        }

        public Value withOverrides(Value value) {
            if (value == null || value == NO_OVERRIDES || value == this || a(this, value)) {
                return this;
            }
            Visibility visibility = value._fieldVisibility;
            if (visibility == Visibility.DEFAULT) {
                visibility = this._fieldVisibility;
            }
            Visibility visibility2 = value._getterVisibility;
            if (visibility2 == Visibility.DEFAULT) {
                visibility2 = this._getterVisibility;
            }
            Visibility visibility3 = value._isGetterVisibility;
            if (visibility3 == Visibility.DEFAULT) {
                visibility3 = this._isGetterVisibility;
            }
            Visibility visibility4 = value._setterVisibility;
            if (visibility4 == Visibility.DEFAULT) {
                visibility4 = this._setterVisibility;
            }
            Visibility visibility5 = value._creatorVisibility;
            if (visibility5 == Visibility.DEFAULT) {
                visibility5 = this._creatorVisibility;
            }
            return construct(visibility, visibility2, visibility3, visibility4, visibility5);
        }

        @Override // com.fasterxml.jackson.annotation.JacksonAnnotationValue
        public Class<JsonAutoDetect> valueFor() {
            return JsonAutoDetect.class;
        }

        public Visibility getFieldVisibility() {
            return this._fieldVisibility;
        }

        public Visibility getGetterVisibility() {
            return this._getterVisibility;
        }

        public Visibility getIsGetterVisibility() {
            return this._isGetterVisibility;
        }

        public Visibility getSetterVisibility() {
            return this._setterVisibility;
        }

        public Visibility getCreatorVisibility() {
            return this._creatorVisibility;
        }

        protected Object readResolve() {
            Value a2 = a(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
            return a2 == null ? this : a2;
        }

        public String toString() {
            return String.format("JsonAutoDetect.Value(fields=%s,getters=%s,isGetters=%s,setters=%s,creators=%s)", this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }

        public int hashCode() {
            return ((this._fieldVisibility.ordinal() + 1) ^ (((this._getterVisibility.ordinal() * 3) - (this._isGetterVisibility.ordinal() * 7)) + (this._setterVisibility.ordinal() * 11))) ^ (this._creatorVisibility.ordinal() * 13);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            return obj.getClass() == getClass() && a(this, (Value) obj);
        }

        private static Value a(Visibility visibility, Visibility visibility2, Visibility visibility3, Visibility visibility4, Visibility visibility5) {
            if (visibility == a) {
                Value value = DEFAULT;
                if (visibility2 == value._getterVisibility && visibility3 == value._isGetterVisibility && visibility4 == value._setterVisibility && visibility5 == value._creatorVisibility) {
                    return value;
                }
                return null;
            } else if (visibility == Visibility.DEFAULT && visibility2 == Visibility.DEFAULT && visibility3 == Visibility.DEFAULT && visibility4 == Visibility.DEFAULT && visibility5 == Visibility.DEFAULT) {
                return NO_OVERRIDES;
            } else {
                return null;
            }
        }

        private static boolean a(Value value, Value value2) {
            return value._fieldVisibility == value2._fieldVisibility && value._getterVisibility == value2._getterVisibility && value._isGetterVisibility == value2._isGetterVisibility && value._setterVisibility == value2._setterVisibility && value._creatorVisibility == value2._creatorVisibility;
        }
    }
}
