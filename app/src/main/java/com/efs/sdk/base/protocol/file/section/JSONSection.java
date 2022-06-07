package com.efs.sdk.base.protocol.file.section;

/* loaded from: classes.dex */
public class JSONSection extends AbsSection {
    private String body;

    public JSONSection(String str) {
        super("json");
        this.name = str;
    }

    public void setBody(String str) {
        this.body = str;
    }

    @Override // com.efs.sdk.base.protocol.file.section.AbsSection
    public String changeToStr() {
        return getDeclarationLine() + "\n" + this.body + "\n";
    }
}
