package org.fourthline.cling.support.contentdirectory;

import com.xiaomi.mico.base.utils.BitmapCache;

/* loaded from: classes5.dex */
public enum ContentDirectoryErrorCode {
    NO_SUCH_OBJECT(701, "The specified ObjectID is invalid"),
    UNSUPPORTED_SORT_CRITERIA(709, "Unsupported or invalid sort criteria"),
    CANNOT_PROCESS(BitmapCache.MAX_WIDTH, "Cannot process the request");
    
    private int code;
    private String description;

    ContentDirectoryErrorCode(int i, String str) {
        this.code = i;
        this.description = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static ContentDirectoryErrorCode getByCode(int i) {
        ContentDirectoryErrorCode[] values = values();
        for (ContentDirectoryErrorCode contentDirectoryErrorCode : values) {
            if (contentDirectoryErrorCode.getCode() == i) {
                return contentDirectoryErrorCode;
            }
        }
        return null;
    }
}
