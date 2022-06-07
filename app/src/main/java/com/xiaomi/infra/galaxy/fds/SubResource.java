package com.xiaomi.infra.galaxy.fds;

import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;

/* loaded from: classes3.dex */
public enum SubResource {
    ACL("acl"),
    QUOTA("quota"),
    UPLOADS("uploads"),
    PART_NUMBER("partNumber"),
    UPLOAD_ID("uploadId"),
    STORAGE_ACCESS_TOKEN("storageAccessToken"),
    METADATA(PlayerEvent.METADATA);
    
    private final String name;

    SubResource(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }
}
