package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;

/* loaded from: classes.dex */
public final class PackageVersion implements Versioned {
    public static final Version VERSION = VersionUtil.parseVersion("2.9.9", "com.fasterxml.jackson.core", "jackson-databind");

    @Override // com.fasterxml.jackson.core.Versioned
    public Version version() {
        return VERSION;
    }
}
