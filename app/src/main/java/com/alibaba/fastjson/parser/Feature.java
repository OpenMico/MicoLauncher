package com.alibaba.fastjson.parser;

/* loaded from: classes.dex */
public enum Feature {
    AutoCloseSource,
    AllowComment,
    AllowUnQuotedFieldNames,
    AllowSingleQuotes,
    InternFieldNames,
    AllowISO8601DateFormat,
    AllowArbitraryCommas,
    UseBigDecimal,
    IgnoreNotMatch,
    SortFeidFastMatch,
    DisableASM,
    DisableCircularReferenceDetect,
    InitStringFieldAsEmpty,
    SupportArrayToBean,
    OrderedField,
    DisableSpecialKeyDetect,
    UseObjectArray,
    SupportNonPublicField,
    IgnoreAutoType,
    DisableFieldSmartMatch,
    SupportAutoType,
    NonStringKeyAsString,
    CustomMapDeserializer,
    ErrorOnEnumNotMatch,
    SafeMode;
    
    public final int mask = 1 << ordinal();

    Feature() {
    }

    public final int getMask() {
        return this.mask;
    }

    public static boolean isEnabled(int i, Feature feature) {
        return (i & feature.mask) != 0;
    }

    public static int config(int i, Feature feature, boolean z) {
        if (z) {
            return i | feature.mask;
        }
        return i & (~feature.mask);
    }

    public static int of(Feature[] featureArr) {
        if (featureArr == null) {
            return 0;
        }
        int i = 0;
        for (Feature feature : featureArr) {
            i |= feature.mask;
        }
        return i;
    }
}
