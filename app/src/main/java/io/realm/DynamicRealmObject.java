package io.realm;

import com.xiaomi.mipush.sdk.Constants;
import io.realm.internal.CheckedRow;
import io.realm.internal.OsList;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.android.JsonUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class DynamicRealmObject extends RealmObject implements RealmObjectProxy {
    private final ProxyState<DynamicRealmObject> a = new ProxyState<>(this);

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
    }

    public DynamicRealmObject(RealmModel realmModel) {
        if (realmModel == null) {
            throw new IllegalArgumentException("A non-null object must be provided.");
        } else if (realmModel instanceof DynamicRealmObject) {
            throw new IllegalArgumentException("The object is already a DynamicRealmObject: " + realmModel);
        } else if (!RealmObject.isManaged(realmModel)) {
            throw new IllegalArgumentException("An object managed by Realm must be provided. This is an unmanaged object.");
        } else if (RealmObject.isValid(realmModel)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
            Row row$realm = realmObjectProxy.realmGet$proxyState().getRow$realm();
            this.a.setRealm$realm(realmObjectProxy.realmGet$proxyState().getRealm$realm());
            this.a.setRow$realm(((UncheckedRow) row$realm).convertToChecked());
            this.a.setConstructionFinished();
        } else {
            throw new IllegalArgumentException("A valid object managed by Realm must be provided. This object was deleted.");
        }
    }

    public DynamicRealmObject(BaseRealm baseRealm, Row row) {
        this.a.setRealm$realm(baseRealm);
        this.a.setRow$realm(row);
        this.a.setConstructionFinished();
    }

    public <E> E get(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        RealmFieldType columnType = this.a.getRow$realm().getColumnType(columnKey);
        switch (columnType) {
            case BOOLEAN:
                return (E) Boolean.valueOf(this.a.getRow$realm().getBoolean(columnKey));
            case INTEGER:
                return (E) Long.valueOf(this.a.getRow$realm().getLong(columnKey));
            case FLOAT:
                return (E) Float.valueOf(this.a.getRow$realm().getFloat(columnKey));
            case DOUBLE:
                return (E) Double.valueOf(this.a.getRow$realm().getDouble(columnKey));
            case STRING:
                return (E) this.a.getRow$realm().getString(columnKey);
            case BINARY:
                return (E) this.a.getRow$realm().getBinaryByteArray(columnKey);
            case DATE:
                return (E) this.a.getRow$realm().getDate(columnKey);
            case OBJECT:
                return (E) getObject(str);
            case LIST:
                return (E) getList(str);
            default:
                throw new IllegalStateException("Field type not supported: " + columnType);
        }
    }

    public boolean getBoolean(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        try {
            return this.a.getRow$realm().getBoolean(columnKey);
        } catch (IllegalArgumentException e) {
            a(str, columnKey, RealmFieldType.BOOLEAN);
            throw e;
        }
    }

    public int getInt(String str) {
        return (int) getLong(str);
    }

    public short getShort(String str) {
        return (short) getLong(str);
    }

    public long getLong(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        try {
            return this.a.getRow$realm().getLong(columnKey);
        } catch (IllegalArgumentException e) {
            a(str, columnKey, RealmFieldType.INTEGER);
            throw e;
        }
    }

    public byte getByte(String str) {
        return (byte) getLong(str);
    }

    public float getFloat(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        try {
            return this.a.getRow$realm().getFloat(columnKey);
        } catch (IllegalArgumentException e) {
            a(str, columnKey, RealmFieldType.FLOAT);
            throw e;
        }
    }

    public double getDouble(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        try {
            return this.a.getRow$realm().getDouble(columnKey);
        } catch (IllegalArgumentException e) {
            a(str, columnKey, RealmFieldType.DOUBLE);
            throw e;
        }
    }

    public byte[] getBlob(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        try {
            return this.a.getRow$realm().getBinaryByteArray(columnKey);
        } catch (IllegalArgumentException e) {
            a(str, columnKey, RealmFieldType.BINARY);
            throw e;
        }
    }

    public String getString(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        try {
            return this.a.getRow$realm().getString(columnKey);
        } catch (IllegalArgumentException e) {
            a(str, columnKey, RealmFieldType.STRING);
            throw e;
        }
    }

    public Date getDate(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        a(str, columnKey, RealmFieldType.DATE);
        if (this.a.getRow$realm().isNull(columnKey)) {
            return null;
        }
        return this.a.getRow$realm().getDate(columnKey);
    }

    @Nullable
    public DynamicRealmObject getObject(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        a(str, columnKey, RealmFieldType.OBJECT);
        if (this.a.getRow$realm().isNullLink(columnKey)) {
            return null;
        }
        return new DynamicRealmObject(this.a.getRealm$realm(), this.a.getRow$realm().getTable().getLinkTarget(columnKey).getCheckedRow(this.a.getRow$realm().getLink(columnKey)));
    }

    public RealmList<DynamicRealmObject> getList(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        try {
            OsList modelList = this.a.getRow$realm().getModelList(columnKey);
            return new RealmList<>(modelList.getTargetTable().getClassName(), modelList, this.a.getRealm$realm());
        } catch (IllegalArgumentException e) {
            a(str, columnKey, RealmFieldType.LIST);
            throw e;
        }
    }

    public <E> RealmList<E> getList(String str, Class<E> cls) {
        this.a.getRealm$realm().checkIfValid();
        if (cls != null) {
            long columnKey = this.a.getRow$realm().getColumnKey(str);
            RealmFieldType a = a(cls);
            try {
                return new RealmList<>(cls, this.a.getRow$realm().getValueList(columnKey, a), this.a.getRealm$realm());
            } catch (IllegalArgumentException e) {
                a(str, columnKey, a);
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Non-null 'primitiveType' required.");
        }
    }

    private <E> RealmFieldType a(Class<E> cls) {
        if (cls.equals(Integer.class) || cls.equals(Long.class) || cls.equals(Short.class) || cls.equals(Byte.class)) {
            return RealmFieldType.INTEGER_LIST;
        }
        if (cls.equals(Boolean.class)) {
            return RealmFieldType.BOOLEAN_LIST;
        }
        if (cls.equals(String.class)) {
            return RealmFieldType.STRING_LIST;
        }
        if (cls.equals(byte[].class)) {
            return RealmFieldType.BINARY_LIST;
        }
        if (cls.equals(Date.class)) {
            return RealmFieldType.DATE_LIST;
        }
        if (cls.equals(Float.class)) {
            return RealmFieldType.FLOAT_LIST;
        }
        if (cls.equals(Double.class)) {
            return RealmFieldType.DOUBLE_LIST;
        }
        throw new IllegalArgumentException("Unsupported element type. Only primitive types supported. Yours was: " + cls);
    }

    public boolean isNull(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        switch (this.a.getRow$realm().getColumnType(columnKey)) {
            case BOOLEAN:
            case INTEGER:
            case FLOAT:
            case DOUBLE:
            case STRING:
            case BINARY:
            case DATE:
                return this.a.getRow$realm().isNull(columnKey);
            case OBJECT:
                return this.a.getRow$realm().isNullLink(columnKey);
            default:
                return false;
        }
    }

    public boolean hasField(String str) {
        this.a.getRealm$realm().checkIfValid();
        if (str == null || str.isEmpty()) {
            return false;
        }
        return this.a.getRow$realm().hasColumn(str);
    }

    public String[] getFieldNames() {
        this.a.getRealm$realm().checkIfValid();
        return this.a.getRow$realm().getColumnNames();
    }

    public void set(String str, Object obj) {
        this.a.getRealm$realm().checkIfValid();
        boolean z = obj instanceof String;
        String str2 = z ? (String) obj : null;
        RealmFieldType columnType = this.a.getRow$realm().getColumnType(this.a.getRow$realm().getColumnKey(str));
        if (z && columnType != RealmFieldType.STRING) {
            int i = AnonymousClass1.a[columnType.ordinal()];
            if (i != 7) {
                switch (i) {
                    case 1:
                        obj = Boolean.valueOf(Boolean.parseBoolean(str2));
                        break;
                    case 2:
                        obj = Long.valueOf(Long.parseLong(str2));
                        break;
                    case 3:
                        obj = Float.valueOf(Float.parseFloat(str2));
                        break;
                    case 4:
                        obj = Double.valueOf(Double.parseDouble(str2));
                        break;
                    default:
                        throw new IllegalArgumentException(String.format(Locale.US, "Field %s is not a String field, and the provide value could not be automatically converted: %s. Use a typedsetter instead", str, obj));
                }
            } else {
                obj = JsonUtils.stringToDate(str2);
            }
        }
        if (obj == null) {
            setNull(str);
        } else {
            a(str, obj);
        }
    }

    private void a(String str, Object obj) {
        Class<?> cls = obj.getClass();
        if (cls == Boolean.class) {
            setBoolean(str, ((Boolean) obj).booleanValue());
        } else if (cls == Short.class) {
            setShort(str, ((Short) obj).shortValue());
        } else if (cls == Integer.class) {
            setInt(str, ((Integer) obj).intValue());
        } else if (cls == Long.class) {
            setLong(str, ((Long) obj).longValue());
        } else if (cls == Byte.class) {
            setByte(str, ((Byte) obj).byteValue());
        } else if (cls == Float.class) {
            setFloat(str, ((Float) obj).floatValue());
        } else if (cls == Double.class) {
            setDouble(str, ((Double) obj).doubleValue());
        } else if (cls == String.class) {
            setString(str, (String) obj);
        } else if (obj instanceof Date) {
            setDate(str, (Date) obj);
        } else if (obj instanceof byte[]) {
            setBlob(str, (byte[]) obj);
        } else if (cls == DynamicRealmObject.class) {
            setObject(str, (DynamicRealmObject) obj);
        } else if (cls == RealmList.class) {
            setList(str, (RealmList) obj);
        } else {
            throw new IllegalArgumentException("Value is of an type not supported: " + obj.getClass());
        }
    }

    public void setBoolean(String str, boolean z) {
        this.a.getRealm$realm().checkIfValid();
        this.a.getRow$realm().setBoolean(this.a.getRow$realm().getColumnKey(str), z);
    }

    public void setShort(String str, short s) {
        this.a.getRealm$realm().checkIfValid();
        a(str);
        this.a.getRow$realm().setLong(this.a.getRow$realm().getColumnKey(str), s);
    }

    public void setInt(String str, int i) {
        this.a.getRealm$realm().checkIfValid();
        a(str);
        this.a.getRow$realm().setLong(this.a.getRow$realm().getColumnKey(str), i);
    }

    public void setLong(String str, long j) {
        this.a.getRealm$realm().checkIfValid();
        a(str);
        this.a.getRow$realm().setLong(this.a.getRow$realm().getColumnKey(str), j);
    }

    public void setByte(String str, byte b) {
        this.a.getRealm$realm().checkIfValid();
        a(str);
        this.a.getRow$realm().setLong(this.a.getRow$realm().getColumnKey(str), b);
    }

    public void setFloat(String str, float f) {
        this.a.getRealm$realm().checkIfValid();
        this.a.getRow$realm().setFloat(this.a.getRow$realm().getColumnKey(str), f);
    }

    public void setDouble(String str, double d) {
        this.a.getRealm$realm().checkIfValid();
        this.a.getRow$realm().setDouble(this.a.getRow$realm().getColumnKey(str), d);
    }

    public void setString(String str, @Nullable String str2) {
        this.a.getRealm$realm().checkIfValid();
        a(str);
        this.a.getRow$realm().setString(this.a.getRow$realm().getColumnKey(str), str2);
    }

    public void setBlob(String str, @Nullable byte[] bArr) {
        this.a.getRealm$realm().checkIfValid();
        this.a.getRow$realm().setBinaryByteArray(this.a.getRow$realm().getColumnKey(str), bArr);
    }

    public void setDate(String str, @Nullable Date date) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        if (date == null) {
            this.a.getRow$realm().setNull(columnKey);
        } else {
            this.a.getRow$realm().setDate(columnKey, date);
        }
    }

    public void setObject(String str, @Nullable DynamicRealmObject dynamicRealmObject) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        if (dynamicRealmObject == null) {
            this.a.getRow$realm().nullifyLink(columnKey);
        } else if (dynamicRealmObject.a.getRealm$realm() == null || dynamicRealmObject.a.getRow$realm() == null) {
            throw new IllegalArgumentException("Cannot link to objects that are not part of the Realm.");
        } else if (this.a.getRealm$realm() == dynamicRealmObject.a.getRealm$realm()) {
            Table linkTarget = this.a.getRow$realm().getTable().getLinkTarget(columnKey);
            Table table = dynamicRealmObject.a.getRow$realm().getTable();
            if (linkTarget.hasSameSchema(table)) {
                this.a.getRow$realm().setLink(columnKey, dynamicRealmObject.a.getRow$realm().getObjectKey());
                return;
            }
            throw new IllegalArgumentException(String.format(Locale.US, "Type of object is wrong. Was %s, expected %s", table.getName(), linkTarget.getName()));
        } else {
            throw new IllegalArgumentException("Cannot add an object from another Realm instance.");
        }
    }

    public <E> void setList(String str, RealmList<E> realmList) {
        this.a.getRealm$realm().checkIfValid();
        if (realmList != null) {
            RealmFieldType columnType = this.a.getRow$realm().getColumnType(this.a.getRow$realm().getColumnKey(str));
            switch (columnType) {
                case LIST:
                    if (!realmList.isEmpty()) {
                        E first = realmList.first();
                        if (!(first instanceof DynamicRealmObject) && RealmModel.class.isAssignableFrom(first.getClass())) {
                            throw new IllegalArgumentException("RealmList must contain `DynamicRealmObject's, not Java model classes.");
                        }
                    }
                    a(str, (RealmList<DynamicRealmObject>) realmList);
                    return;
                case LINKING_OBJECTS:
                default:
                    throw new IllegalArgumentException(String.format("Field '%s' is not a list but a %s", str, columnType));
                case INTEGER_LIST:
                case BOOLEAN_LIST:
                case STRING_LIST:
                case BINARY_LIST:
                case DATE_LIST:
                case FLOAT_LIST:
                case DOUBLE_LIST:
                    a(str, realmList, columnType);
                    return;
            }
        } else {
            throw new IllegalArgumentException("Non-null 'list' required");
        }
    }

    private void a(String str, RealmList<DynamicRealmObject> realmList) {
        boolean z;
        OsList modelList = this.a.getRow$realm().getModelList(this.a.getRow$realm().getColumnKey(str));
        Table targetTable = modelList.getTargetTable();
        String className = targetTable.getClassName();
        if (realmList.className == null && realmList.clazz == null) {
            z = false;
        } else {
            String className2 = realmList.className != null ? realmList.className : this.a.getRealm$realm().getSchema().a(realmList.clazz).getClassName();
            if (className.equals(className2)) {
                z = true;
            } else {
                throw new IllegalArgumentException(String.format(Locale.US, "The elements in the list are not the proper type. Was %s expected %s.", className2, className));
            }
        }
        int size = realmList.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            DynamicRealmObject dynamicRealmObject = realmList.get(i);
            if (dynamicRealmObject.realmGet$proxyState().getRealm$realm() != this.a.getRealm$realm()) {
                throw new IllegalArgumentException("Each element in 'list' must belong to the same Realm instance.");
            } else if (z || targetTable.hasSameSchema(dynamicRealmObject.realmGet$proxyState().getRow$realm().getTable())) {
                jArr[i] = dynamicRealmObject.realmGet$proxyState().getRow$realm().getObjectKey();
            } else {
                throw new IllegalArgumentException(String.format(Locale.US, "Element at index %d is not the proper type. Was '%s' expected '%s'.", Integer.valueOf(i), dynamicRealmObject.realmGet$proxyState().getRow$realm().getTable().getClassName(), className));
            }
        }
        modelList.removeAll();
        for (int i2 = 0; i2 < size; i2++) {
            modelList.addRow(jArr[i2]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <E> void a(String str, RealmList<E> realmList, RealmFieldType realmFieldType) {
        Object obj;
        OsList valueList = this.a.getRow$realm().getValueList(this.a.getRow$realm().getColumnKey(str), realmFieldType);
        switch (realmFieldType) {
            case INTEGER_LIST:
                obj = Long.class;
                break;
            case BOOLEAN_LIST:
                obj = Boolean.class;
                break;
            case STRING_LIST:
                obj = String.class;
                break;
            case BINARY_LIST:
                obj = byte[].class;
                break;
            case DATE_LIST:
                obj = Date.class;
                break;
            case FLOAT_LIST:
                obj = Float.class;
                break;
            case DOUBLE_LIST:
                obj = Double.class;
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + realmFieldType);
        }
        i a = a(this.a.getRealm$realm(), valueList, realmFieldType, obj);
        if (!realmList.isManaged() || valueList.size() != realmList.size()) {
            valueList.removeAll();
            Iterator<E> it = realmList.iterator();
            while (it.hasNext()) {
                a.c(it.next());
            }
            return;
        }
        int size = realmList.size();
        Iterator<E> it2 = realmList.iterator();
        for (int i = 0; i < size; i++) {
            a.d(i, it2.next());
        }
    }

    private <E> i<E> a(BaseRealm baseRealm, OsList osList, RealmFieldType realmFieldType, Class<E> cls) {
        if (realmFieldType == RealmFieldType.STRING_LIST) {
            return new p(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.INTEGER_LIST) {
            return new h(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.BOOLEAN_LIST) {
            return new b(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.BINARY_LIST) {
            return new a(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.DOUBLE_LIST) {
            return new d(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.FLOAT_LIST) {
            return new e(baseRealm, osList, cls);
        }
        if (realmFieldType == RealmFieldType.DATE_LIST) {
            return new c(baseRealm, osList, cls);
        }
        throw new IllegalArgumentException("Unexpected list type: " + realmFieldType.name());
    }

    public void setNull(String str) {
        this.a.getRealm$realm().checkIfValid();
        long columnKey = this.a.getRow$realm().getColumnKey(str);
        if (this.a.getRow$realm().getColumnType(columnKey) == RealmFieldType.OBJECT) {
            this.a.getRow$realm().nullifyLink(columnKey);
            return;
        }
        a(str);
        this.a.getRow$realm().setNull(columnKey);
    }

    public String getType() {
        this.a.getRealm$realm().checkIfValid();
        return this.a.getRow$realm().getTable().getClassName();
    }

    public RealmFieldType getFieldType(String str) {
        this.a.getRealm$realm().checkIfValid();
        return this.a.getRow$realm().getColumnType(this.a.getRow$realm().getColumnKey(str));
    }

    private void a(String str, long j, RealmFieldType realmFieldType) {
        RealmFieldType columnType = this.a.getRow$realm().getColumnType(j);
        if (columnType != realmFieldType) {
            String str2 = "";
            if (realmFieldType == RealmFieldType.INTEGER || realmFieldType == RealmFieldType.OBJECT) {
                str2 = "n";
            }
            throw new IllegalArgumentException(String.format(Locale.US, "'%s' is not a%s '%s', but a%s '%s'.", str, str2, realmFieldType, (columnType == RealmFieldType.INTEGER || columnType == RealmFieldType.OBJECT) ? "n" : "", columnType));
        }
    }

    public int hashCode() {
        this.a.getRealm$realm().checkIfValid();
        String path = this.a.getRealm$realm().getPath();
        String name = this.a.getRow$realm().getTable().getName();
        long objectKey = this.a.getRow$realm().getObjectKey();
        int i = 0;
        int hashCode = (527 + (path != null ? path.hashCode() : 0)) * 31;
        if (name != null) {
            i = name.hashCode();
        }
        return ((hashCode + i) * 31) + ((int) ((objectKey >>> 32) ^ objectKey));
    }

    public boolean equals(Object obj) {
        this.a.getRealm$realm().checkIfValid();
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DynamicRealmObject dynamicRealmObject = (DynamicRealmObject) obj;
        String path = this.a.getRealm$realm().getPath();
        String path2 = dynamicRealmObject.a.getRealm$realm().getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        String name = this.a.getRow$realm().getTable().getName();
        String name2 = dynamicRealmObject.a.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.a.getRow$realm().getObjectKey() == dynamicRealmObject.a.getRow$realm().getObjectKey();
        }
        return false;
    }

    public String toString() {
        this.a.getRealm$realm().checkIfValid();
        if (!this.a.getRow$realm().isValid()) {
            return "Invalid object";
        }
        String className = this.a.getRow$realm().getTable().getClassName();
        StringBuilder sb = new StringBuilder(className + " = dynamic[");
        String[] fieldNames = getFieldNames();
        int length = fieldNames.length;
        for (int i = 0; i < length; i++) {
            String str = fieldNames[i];
            long columnKey = this.a.getRow$realm().getColumnKey(str);
            RealmFieldType columnType = this.a.getRow$realm().getColumnType(columnKey);
            sb.append("{");
            sb.append(str);
            sb.append(Constants.COLON_SEPARATOR);
            switch (columnType) {
                case BOOLEAN:
                    sb.append(this.a.getRow$realm().isNull(columnKey) ? "null" : Boolean.valueOf(this.a.getRow$realm().getBoolean(columnKey)));
                    break;
                case INTEGER:
                    sb.append(this.a.getRow$realm().isNull(columnKey) ? "null" : Long.valueOf(this.a.getRow$realm().getLong(columnKey)));
                    break;
                case FLOAT:
                    sb.append(this.a.getRow$realm().isNull(columnKey) ? "null" : Float.valueOf(this.a.getRow$realm().getFloat(columnKey)));
                    break;
                case DOUBLE:
                    sb.append(this.a.getRow$realm().isNull(columnKey) ? "null" : Double.valueOf(this.a.getRow$realm().getDouble(columnKey)));
                    break;
                case STRING:
                    sb.append(this.a.getRow$realm().getString(columnKey));
                    break;
                case BINARY:
                    sb.append(Arrays.toString(this.a.getRow$realm().getBinaryByteArray(columnKey)));
                    break;
                case DATE:
                    sb.append(this.a.getRow$realm().isNull(columnKey) ? "null" : this.a.getRow$realm().getDate(columnKey));
                    break;
                case OBJECT:
                    sb.append(this.a.getRow$realm().isNullLink(columnKey) ? "null" : this.a.getRow$realm().getTable().getLinkTarget(columnKey).getClassName());
                    break;
                case LIST:
                    sb.append(String.format(Locale.US, "RealmList<%s>[%s]", this.a.getRow$realm().getTable().getLinkTarget(columnKey).getClassName(), Long.valueOf(this.a.getRow$realm().getModelList(columnKey).size())));
                    break;
                case LINKING_OBJECTS:
                default:
                    sb.append("?");
                    break;
                case INTEGER_LIST:
                    sb.append(String.format(Locale.US, "RealmList<Long>[%s]", Long.valueOf(this.a.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case BOOLEAN_LIST:
                    sb.append(String.format(Locale.US, "RealmList<Boolean>[%s]", Long.valueOf(this.a.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case STRING_LIST:
                    sb.append(String.format(Locale.US, "RealmList<String>[%s]", Long.valueOf(this.a.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case BINARY_LIST:
                    sb.append(String.format(Locale.US, "RealmList<byte[]>[%s]", Long.valueOf(this.a.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case DATE_LIST:
                    sb.append(String.format(Locale.US, "RealmList<Date>[%s]", Long.valueOf(this.a.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case FLOAT_LIST:
                    sb.append(String.format(Locale.US, "RealmList<Float>[%s]", Long.valueOf(this.a.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
                case DOUBLE_LIST:
                    sb.append(String.format(Locale.US, "RealmList<Double>[%s]", Long.valueOf(this.a.getRow$realm().getValueList(columnKey, columnType).size())));
                    break;
            }
            sb.append("},");
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        sb.append("]");
        return sb.toString();
    }

    public RealmResults<DynamicRealmObject> linkingObjects(String str, String str2) {
        DynamicRealm dynamicRealm = (DynamicRealm) this.a.getRealm$realm();
        dynamicRealm.checkIfValid();
        this.a.getRow$realm().checkIfAttached();
        RealmObjectSchema realmObjectSchema = dynamicRealm.getSchema().get(str);
        if (realmObjectSchema == null) {
            throw new IllegalArgumentException("Class not found: " + str);
        } else if (str2 == null) {
            throw new IllegalArgumentException("Non-null 'srcFieldName' required.");
        } else if (!str2.contains(".")) {
            RealmFieldType fieldType = realmObjectSchema.getFieldType(str2);
            if (fieldType == RealmFieldType.OBJECT || fieldType == RealmFieldType.LIST) {
                return RealmResults.a(dynamicRealm, (CheckedRow) this.a.getRow$realm(), realmObjectSchema.a(), str2);
            }
            throw new IllegalArgumentException(String.format(Locale.US, "Unexpected field type: %1$s. Field type should be either %2$s.%3$s or %2$s.%4$s.", fieldType.name(), RealmFieldType.class.getSimpleName(), RealmFieldType.OBJECT.name(), RealmFieldType.LIST.name()));
        } else {
            throw new IllegalArgumentException("Queries across relationships are not supported");
        }
    }

    public DynamicRealm getDynamicRealm() {
        BaseRealm realm$realm = realmGet$proxyState().getRealm$realm();
        realm$realm.checkIfValid();
        if (isValid()) {
            return (DynamicRealm) realm$realm;
        }
        throw new IllegalStateException("the object is already deleted.");
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState realmGet$proxyState() {
        return this.a;
    }

    private void a(String str) {
        RealmObjectSchema b = this.a.getRealm$realm().getSchema().b(getType());
        if (b.hasPrimaryKey() && b.getPrimaryKey().equals(str)) {
            throw new IllegalArgumentException(String.format(Locale.US, "Primary key field '%s' cannot be changed after object was created.", str));
        }
    }
}
