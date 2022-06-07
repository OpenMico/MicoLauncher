package org.fourthline.cling.model.types;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;

/* loaded from: classes5.dex */
public class CustomDatatype extends AbstractDatatype<String> {
    private String name;

    public CustomDatatype(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public String valueOf(String str) throws InvalidValueException {
        if (str.equals("")) {
            return null;
        }
        return str;
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype
    public String toString() {
        return "(" + getClass().getSimpleName() + ") '" + getName() + LrcRow.SINGLE_QUOTE;
    }
}
