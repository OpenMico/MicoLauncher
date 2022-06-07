package com.xiaomi.micolauncher.skills.openplatform.model;

/* loaded from: classes3.dex */
public class SkillChatItem {
    private Type a;
    public String avatar;
    private String b;

    /* loaded from: classes3.dex */
    public enum Type {
        RECV,
        SEND
    }

    public SkillChatItem(Type type, String str) {
        this.a = type;
        this.b = str;
    }

    public SkillChatItem(Type type, String str, String str2) {
        this.a = type;
        this.b = str;
        this.avatar = str2;
    }

    public Type getType() {
        return this.a;
    }

    public String getContent() {
        return this.b;
    }
}
