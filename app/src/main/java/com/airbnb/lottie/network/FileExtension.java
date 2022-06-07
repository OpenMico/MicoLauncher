package com.airbnb.lottie.network;

import com.airbnb.lottie.utils.Logger;

/* loaded from: classes.dex */
public enum FileExtension {
    JSON(".json"),
    ZIP(".zip");
    
    public final String extension;

    FileExtension(String str) {
        this.extension = str;
    }

    public String tempExtension() {
        return ".temp" + this.extension;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.extension;
    }

    public static FileExtension forFile(String str) {
        FileExtension[] values = values();
        for (FileExtension fileExtension : values) {
            if (str.endsWith(fileExtension.extension)) {
                return fileExtension;
            }
        }
        Logger.warning("Unable to find correct extension for " + str);
        return JSON;
    }
}
