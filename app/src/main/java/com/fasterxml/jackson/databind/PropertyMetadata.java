package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.Serializable;

/* loaded from: classes.dex */
public class PropertyMetadata implements Serializable {
    private static final long serialVersionUID = -1;
    protected Nulls _contentNulls;
    protected final String _defaultValue;
    protected final String _description;
    protected final Integer _index;
    protected final transient MergeInfo _mergeInfo;
    protected final Boolean _required;
    protected Nulls _valueNulls;
    public static final PropertyMetadata STD_REQUIRED = new PropertyMetadata(Boolean.TRUE, null, null, null, null, null, null);
    public static final PropertyMetadata STD_OPTIONAL = new PropertyMetadata(Boolean.FALSE, null, null, null, null, null, null);
    public static final PropertyMetadata STD_REQUIRED_OR_OPTIONAL = new PropertyMetadata(null, null, null, null, null, null, null);

    /* loaded from: classes.dex */
    public static final class MergeInfo {
        public final boolean fromDefaults;
        public final AnnotatedMember getter;

        protected MergeInfo(AnnotatedMember annotatedMember, boolean z) {
            this.getter = annotatedMember;
            this.fromDefaults = z;
        }

        public static MergeInfo createForDefaults(AnnotatedMember annotatedMember) {
            return new MergeInfo(annotatedMember, true);
        }

        public static MergeInfo createForTypeOverride(AnnotatedMember annotatedMember) {
            return new MergeInfo(annotatedMember, false);
        }

        public static MergeInfo createForPropertyOverride(AnnotatedMember annotatedMember) {
            return new MergeInfo(annotatedMember, false);
        }
    }

    protected PropertyMetadata(Boolean bool, String str, Integer num, String str2, MergeInfo mergeInfo, Nulls nulls, Nulls nulls2) {
        this._required = bool;
        this._description = str;
        this._index = num;
        this._defaultValue = (str2 == null || str2.isEmpty()) ? null : str2;
        this._mergeInfo = mergeInfo;
        this._valueNulls = nulls;
        this._contentNulls = nulls2;
    }

    public static PropertyMetadata construct(Boolean bool, String str, Integer num, String str2) {
        if (str != null || num != null || str2 != null) {
            return new PropertyMetadata(bool, str, num, str2, null, null, null);
        }
        if (bool == null) {
            return STD_REQUIRED_OR_OPTIONAL;
        }
        return bool.booleanValue() ? STD_REQUIRED : STD_OPTIONAL;
    }

    @Deprecated
    public static PropertyMetadata construct(boolean z, String str, Integer num, String str2) {
        if (str == null && num == null && str2 == null) {
            return z ? STD_REQUIRED : STD_OPTIONAL;
        }
        return new PropertyMetadata(Boolean.valueOf(z), str, num, str2, null, null, null);
    }

    protected Object readResolve() {
        if (this._description != null || this._index != null || this._defaultValue != null || this._mergeInfo != null || this._valueNulls != null || this._contentNulls != null) {
            return this;
        }
        Boolean bool = this._required;
        if (bool == null) {
            return STD_REQUIRED_OR_OPTIONAL;
        }
        return bool.booleanValue() ? STD_REQUIRED : STD_OPTIONAL;
    }

    public PropertyMetadata withDescription(String str) {
        return new PropertyMetadata(this._required, str, this._index, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
    }

    public PropertyMetadata withMergeInfo(MergeInfo mergeInfo) {
        return new PropertyMetadata(this._required, this._description, this._index, this._defaultValue, mergeInfo, this._valueNulls, this._contentNulls);
    }

    public PropertyMetadata withNulls(Nulls nulls, Nulls nulls2) {
        return new PropertyMetadata(this._required, this._description, this._index, this._defaultValue, this._mergeInfo, nulls, nulls2);
    }

    public PropertyMetadata withDefaultValue(String str) {
        String str2;
        if (str == null || str.isEmpty()) {
            if (this._defaultValue == null) {
                return this;
            }
            str2 = null;
        } else if (str.equals(this._defaultValue)) {
            return this;
        } else {
            str2 = str;
        }
        return new PropertyMetadata(this._required, this._description, this._index, str2, this._mergeInfo, this._valueNulls, this._contentNulls);
    }

    public PropertyMetadata withIndex(Integer num) {
        return new PropertyMetadata(this._required, this._description, num, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
    }

    public PropertyMetadata withRequired(Boolean bool) {
        if (bool == null) {
            if (this._required == null) {
                return this;
            }
        } else if (bool.equals(this._required)) {
            return this;
        }
        return new PropertyMetadata(bool, this._description, this._index, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
    }

    public String getDescription() {
        return this._description;
    }

    public String getDefaultValue() {
        return this._defaultValue;
    }

    public boolean hasDefaultValue() {
        return this._defaultValue != null;
    }

    public boolean isRequired() {
        Boolean bool = this._required;
        return bool != null && bool.booleanValue();
    }

    public Boolean getRequired() {
        return this._required;
    }

    public Integer getIndex() {
        return this._index;
    }

    public boolean hasIndex() {
        return this._index != null;
    }

    public MergeInfo getMergeInfo() {
        return this._mergeInfo;
    }

    public Nulls getValueNulls() {
        return this._valueNulls;
    }

    public Nulls getContentNulls() {
        return this._contentNulls;
    }
}
