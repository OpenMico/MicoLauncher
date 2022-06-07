package org.fourthline.cling.support.model.dlna;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class DLNAAttribute<T> {
    private static final Logger log = Logger.getLogger(DLNAAttribute.class.getName());
    private T value;

    public abstract String getString();

    public abstract void setString(String str, String str2) throws InvalidDLNAProtocolAttributeException;

    /* loaded from: classes5.dex */
    public enum Type {
        DLNA_ORG_PN("DLNA.ORG_PN", DLNAProfileAttribute.class),
        DLNA_ORG_OP("DLNA.ORG_OP", DLNAOperationsAttribute.class),
        DLNA_ORG_PS("DLNA.ORG_PS", DLNAPlaySpeedAttribute.class),
        DLNA_ORG_CI("DLNA.ORG_CI", DLNAConversionIndicatorAttribute.class),
        DLNA_ORG_FLAGS("DLNA.ORG_FLAGS", DLNAFlagsAttribute.class);
        
        private static Map<String, Type> byName = new HashMap<String, Type>() { // from class: org.fourthline.cling.support.model.dlna.DLNAAttribute.Type.1
            {
                Type[] values = Type.values();
                for (Type type : values) {
                    put(type.getAttributeName().toUpperCase(Locale.ROOT), type);
                }
            }
        };
        private String attributeName;
        private Class<? extends DLNAAttribute>[] attributeTypes;

        @SafeVarargs
        Type(String str, Class... clsArr) {
            this.attributeName = str;
            this.attributeTypes = clsArr;
        }

        public String getAttributeName() {
            return this.attributeName;
        }

        public Class<? extends DLNAAttribute>[] getAttributeTypes() {
            return this.attributeTypes;
        }

        public static Type valueOfAttributeName(String str) {
            if (str == null) {
                return null;
            }
            return byName.get(str.toUpperCase(Locale.ROOT));
        }
    }

    public void setValue(T t) {
        this.value = t;
    }

    public T getValue() {
        return this.value;
    }

    public static DLNAAttribute newInstance(Type type, String str, String str2) {
        Exception e;
        DLNAAttribute dLNAAttribute = null;
        for (int i = 0; i < type.getAttributeTypes().length && dLNAAttribute == null; i++) {
            Class<? extends DLNAAttribute> cls = type.getAttributeTypes()[i];
            try {
                try {
                    log.finest("Trying to parse DLNA '" + type + "' with class: " + cls.getSimpleName());
                    DLNAAttribute dLNAAttribute2 = (DLNAAttribute) cls.newInstance();
                    if (str != null) {
                        try {
                            dLNAAttribute2.setString(str, str2);
                        } catch (Exception e2) {
                            e = e2;
                            dLNAAttribute = dLNAAttribute2;
                            log.severe("Error instantiating DLNA attribute of type '" + type + "' with value: " + str);
                            log.log(Level.SEVERE, "Exception root cause: ", Exceptions.unwrap(e));
                        }
                    }
                    dLNAAttribute = dLNAAttribute2;
                } catch (InvalidDLNAProtocolAttributeException e3) {
                    log.finest("Invalid DLNA attribute value for tested type: " + cls.getSimpleName() + " - " + e3.getMessage());
                    dLNAAttribute = null;
                }
            } catch (Exception e4) {
                e = e4;
            }
        }
        return dLNAAttribute;
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ") '" + getValue() + LrcRow.SINGLE_QUOTE;
    }
}
