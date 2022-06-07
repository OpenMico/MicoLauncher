package com.fasterxml.jackson.databind.jsontype;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.Serializable;

/* loaded from: classes.dex */
public final class NamedType implements Serializable {
    private static final long serialVersionUID = 1;
    protected final Class<?> _class;
    protected final int _hashCode;
    protected String _name;

    public NamedType(Class<?> cls) {
        this(cls, null);
    }

    public NamedType(Class<?> cls, String str) {
        this._class = cls;
        this._hashCode = cls.getName().hashCode();
        setName(str);
    }

    public Class<?> getType() {
        return this._class;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String str) {
        if (str == null || str.length() == 0) {
            str = null;
        }
        this._name = str;
    }

    public boolean hasName() {
        return this._name != null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj != null && obj.getClass() == getClass() && this._class == ((NamedType) obj)._class;
    }

    public int hashCode() {
        return this._hashCode;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("[NamedType, class ");
        sb.append(this._class.getName());
        sb.append(", name: ");
        if (this._name == null) {
            str = "null";
        } else {
            str = LrcRow.SINGLE_QUOTE + this._name + LrcRow.SINGLE_QUOTE;
        }
        sb.append(str);
        sb.append("]");
        return sb.toString();
    }
}
