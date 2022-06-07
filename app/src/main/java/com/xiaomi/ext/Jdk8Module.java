package com.xiaomi.ext;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

/* loaded from: classes3.dex */
public class Jdk8Module extends Module {
    protected boolean _cfgHandleAbsentAsNull = false;

    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.fasterxml.jackson.databind.Module
    public String getModuleName() {
        return "Jdk8Module";
    }

    @Override // com.fasterxml.jackson.databind.Module
    public void setupModule(Module.SetupContext setupContext) {
        setupContext.addSerializers(new Jdk8Serializers());
        setupContext.addDeserializers(new Jdk8Deserializers());
        setupContext.addTypeModifier(new Jdk8TypeModifier());
        if (this._cfgHandleAbsentAsNull) {
            setupContext.addBeanSerializerModifier(new Jdk8BeanSerializerModifier());
        }
    }

    @Override // com.fasterxml.jackson.databind.Module, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return PackageVersion.VERSION;
    }

    public Jdk8Module configureAbsentsAsNulls(boolean z) {
        this._cfgHandleAbsentAsNull = z;
        return this;
    }

    public int hashCode() {
        return getClass().hashCode();
    }
}
