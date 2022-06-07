package io.realm;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.internal.CheckedRow;
import io.realm.internal.OsResults;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import io.realm.rx.CollectionChange;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class RealmResults<E> extends l<E> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ void add(int i, Object obj) {
        super.add(i, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean addAll(int i, Collection collection) {
        return super.addAll(i, collection);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean addAll(Collection collection) {
        return super.addAll(collection);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ double average(String str) {
        return super.average(str);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ boolean contains(@Nullable Object obj) {
        return super.contains(obj);
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ OrderedRealmCollectionSnapshot createSnapshot() {
        return super.createSnapshot();
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ boolean deleteAllFromRealm() {
        return super.deleteAllFromRealm();
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ boolean deleteFirstFromRealm() {
        return super.deleteFirstFromRealm();
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ void deleteFromRealm(int i) {
        super.deleteFromRealm(i);
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ boolean deleteLastFromRealm() {
        return super.deleteLastFromRealm();
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object first() {
        return super.first();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object first(@Nullable Object obj) {
        return super.first(obj);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Nullable
    public /* bridge */ /* synthetic */ Object get(int i) {
        return super.get(i);
    }

    @Override // io.realm.l
    public /* bridge */ /* synthetic */ Realm getRealm() {
        return super.getRealm();
    }

    @Override // io.realm.l, io.realm.RealmCollection, io.realm.internal.ManageableObject
    public /* bridge */ /* synthetic */ boolean isManaged() {
        return super.isManaged();
    }

    @Override // io.realm.l, io.realm.RealmCollection, io.realm.internal.ManageableObject
    public /* bridge */ /* synthetic */ boolean isValid() {
        return super.isValid();
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object last() {
        return super.last();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object last(@Nullable Object obj) {
        return super.last(obj);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator() {
        return super.listIterator();
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator(int i) {
        return super.listIterator(i);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number max(String str) {
        return super.max(str);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Date maxDate(String str) {
        return super.maxDate(str);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number min(String str) {
        return super.min(str);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Date minDate(String str) {
        return super.minDate(str);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ Object remove(int i) {
        return super.remove(i);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        return super.set(i, obj);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String str) {
        return super.sort(str);
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String str, Sort sort) {
        return super.sort(str, sort);
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String[] strArr, Sort[] sortArr) {
        return super.sort(strArr, sortArr);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number sum(String str) {
        return super.sum(str);
    }

    public static RealmResults<DynamicRealmObject> a(DynamicRealm dynamicRealm, CheckedRow checkedRow, Table table, String str) {
        return new RealmResults<>(dynamicRealm, OsResults.createForBacklinks(dynamicRealm.sharedRealm, checkedRow, table, str), Table.getClassNameForTable(table.getName()));
    }

    public RealmResults(BaseRealm baseRealm, OsResults osResults, Class<E> cls) {
        super(baseRealm, osResults, cls);
    }

    public RealmResults(BaseRealm baseRealm, OsResults osResults, String str) {
        super(baseRealm, osResults, str);
    }

    @Override // io.realm.RealmCollection
    public RealmQuery<E> where() {
        this.a.checkIfValid();
        return RealmQuery.a(this);
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // io.realm.RealmCollection
    public boolean isLoaded() {
        this.a.checkIfValid();
        return this.e.isLoaded();
    }

    @Override // io.realm.RealmCollection
    public boolean load() {
        this.a.checkIfValid();
        this.e.load();
        return true;
    }

    public void setValue(String str, @Nullable Object obj) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        boolean z = obj instanceof String;
        String str2 = z ? (String) obj : null;
        String className = this.e.getTable().getClassName();
        RealmObjectSchema realmObjectSchema = getRealm().getSchema().get(className);
        if (!realmObjectSchema.hasField(b)) {
            throw new IllegalArgumentException(String.format("Field '%s' could not be found in class '%s'", b, className));
        } else if (obj == null) {
            this.e.setNull(b);
        } else {
            RealmFieldType fieldType = realmObjectSchema.getFieldType(b);
            if (z && fieldType != RealmFieldType.STRING) {
                switch (fieldType) {
                    case BOOLEAN:
                        obj = Boolean.valueOf(Boolean.parseBoolean(str2));
                        break;
                    case INTEGER:
                        obj = Long.valueOf(Long.parseLong(str2));
                        break;
                    case FLOAT:
                        obj = Float.valueOf(Float.parseFloat(str2));
                        break;
                    case DOUBLE:
                        obj = Double.valueOf(Double.parseDouble(str2));
                        break;
                    case DATE:
                        obj = JsonUtils.stringToDate(str2);
                        break;
                    default:
                        throw new IllegalArgumentException(String.format(Locale.US, "Field %s is not a String field, and the provide value could not be automatically converted: %s. Use a typedsetter instead", b, obj));
                }
            }
            Class<?> cls = obj.getClass();
            if (cls == Boolean.class) {
                setBoolean(b, ((Boolean) obj).booleanValue());
            } else if (cls == Short.class) {
                setShort(b, ((Short) obj).shortValue());
            } else if (cls == Integer.class) {
                setInt(b, ((Integer) obj).intValue());
            } else if (cls == Long.class) {
                setLong(b, ((Long) obj).longValue());
            } else if (cls == Byte.class) {
                setByte(b, ((Byte) obj).byteValue());
            } else if (cls == Float.class) {
                setFloat(b, ((Float) obj).floatValue());
            } else if (cls == Double.class) {
                setDouble(b, ((Double) obj).doubleValue());
            } else if (cls == String.class) {
                setString(b, (String) obj);
            } else if (obj instanceof Date) {
                setDate(b, (Date) obj);
            } else if (obj instanceof byte[]) {
                setBlob(b, (byte[]) obj);
            } else if (obj instanceof RealmModel) {
                setObject(b, (RealmModel) obj);
            } else if (cls == RealmList.class) {
                setList(b, (RealmList) obj);
            } else {
                throw new IllegalArgumentException("Value is of a type not supported: " + obj.getClass());
            }
        }
    }

    public void setNull(String str) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        this.e.setNull(str);
    }

    public void setBoolean(String str, boolean z) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.BOOLEAN);
        this.e.setBoolean(b, z);
    }

    public void setByte(String str, byte b) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b2 = b(str);
        a(b2, RealmFieldType.INTEGER);
        this.e.setInt(b2, b);
    }

    public void setShort(String str, short s) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.INTEGER);
        this.e.setInt(b, s);
    }

    public void setInt(String str, int i) {
        a(str);
        String b = b(str);
        a(b, RealmFieldType.INTEGER);
        this.a.checkIfValidAndInTransaction();
        this.e.setInt(b, i);
    }

    public void setLong(String str, long j) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.INTEGER);
        this.e.setInt(b, j);
    }

    public void setFloat(String str, float f) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.FLOAT);
        this.e.setFloat(b, f);
    }

    public void setDouble(String str, double d) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.DOUBLE);
        this.e.setDouble(b, d);
    }

    public void setString(String str, @Nullable String str2) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.STRING);
        this.e.setString(b, str2);
    }

    public void setBlob(String str, @Nullable byte[] bArr) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.BINARY);
        this.e.setBlob(b, bArr);
    }

    public void setDate(String str, @Nullable Date date) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.DATE);
        this.e.setDate(b, date);
    }

    public void setObject(String str, @Nullable RealmModel realmModel) {
        a(str);
        this.a.checkIfValidAndInTransaction();
        String b = b(str);
        a(b, RealmFieldType.OBJECT);
        this.e.setObject(b, a(b, realmModel));
    }

    private Row a(String str, @Nullable RealmModel realmModel) {
        if (realmModel == null) {
            return null;
        }
        if (!RealmObject.isManaged(realmModel) || !RealmObject.isValid(realmModel)) {
            throw new IllegalArgumentException("'value' is not a valid, managed Realm object.");
        }
        ProxyState realmGet$proxyState = ((RealmObjectProxy) realmModel).realmGet$proxyState();
        if (realmGet$proxyState.getRealm$realm().getPath().equals(this.a.getPath())) {
            Table table = this.e.getTable();
            Table linkTarget = table.getLinkTarget(table.getColumnKey(str));
            Table table2 = realmGet$proxyState.getRow$realm().getTable();
            if (linkTarget.hasSameSchema(table2)) {
                return realmGet$proxyState.getRow$realm();
            }
            throw new IllegalArgumentException(String.format(Locale.US, "Type of object is wrong. Was '%s', expected '%s'", table2.getClassName(), linkTarget.getClassName()));
        }
        throw new IllegalArgumentException("'value' does not belong to the same Realm as the RealmResults.");
    }

    public <T> void setList(String str, RealmList<T> realmList) {
        a(str);
        String b = b(str);
        this.a.checkIfValidAndInTransaction();
        if (realmList != null) {
            RealmFieldType fieldType = this.a.getSchema().b(this.e.getTable().getClassName()).getFieldType(b);
            switch (fieldType) {
                case LIST:
                    a(realmList, RealmModel.class);
                    a(b, (RealmModel) realmList.first(null));
                    this.e.setModelList(b, realmList);
                    return;
                case INTEGER_LIST:
                    Class<?> a = a((RealmList) realmList);
                    if (a.equals(Integer.class)) {
                        this.e.setIntegerList(b, realmList);
                        return;
                    } else if (a.equals(Long.class)) {
                        this.e.setLongList(b, realmList);
                        return;
                    } else if (a.equals(Short.class)) {
                        this.e.setShortList(b, realmList);
                        return;
                    } else if (a.equals(Byte.class)) {
                        this.e.setByteList(b, realmList);
                        return;
                    } else {
                        throw new IllegalArgumentException(String.format("List contained the wrong type of elements. Elements that can be mapped to Integers was expected, but the actual type is '%s'", a));
                    }
                case BOOLEAN_LIST:
                    a(realmList, Boolean.class);
                    this.e.setBooleanList(b, realmList);
                    return;
                case STRING_LIST:
                    a(realmList, String.class);
                    this.e.setStringList(b, realmList);
                    return;
                case BINARY_LIST:
                    a(realmList, byte[].class);
                    this.e.setByteArrayList(b, realmList);
                    return;
                case DATE_LIST:
                    a(realmList, Date.class);
                    this.e.setDateList(b, realmList);
                    return;
                case FLOAT_LIST:
                    a(realmList, Float.class);
                    this.e.setFloatList(b, realmList);
                    return;
                case DOUBLE_LIST:
                    a(realmList, Double.class);
                    this.e.setDoubleList(b, realmList);
                    return;
                default:
                    throw new IllegalArgumentException(String.format("Field '%s' is not a list but a %s", b, fieldType));
            }
        } else {
            throw new IllegalArgumentException("Non-null 'list' required");
        }
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        return this.a != null && this.a.isFrozen();
    }

    @Override // io.realm.RealmCollection
    public RealmResults<E> freeze() {
        if (isValid()) {
            BaseRealm freeze = this.a.freeze();
            OsResults freeze2 = this.e.freeze(freeze.sharedRealm);
            if (this.c != null) {
                return new RealmResults<>(freeze, freeze2, this.c);
            }
            return new RealmResults<>(freeze, freeze2, this.b);
        }
        throw new IllegalStateException("Only valid, managed RealmResults can be frozen.");
    }

    private Class<?> a(RealmList realmList) {
        if (!realmList.isEmpty()) {
            return realmList.first().getClass();
        }
        return Long.class;
    }

    private <T> void a(RealmList<T> realmList, Class<?> cls) {
        if (!realmList.isEmpty()) {
            Class<?> cls2 = realmList.first().getClass();
            if (!cls.isAssignableFrom(cls2)) {
                throw new IllegalArgumentException(String.format("List contained the wrong type of elements. Elements of type '%s' was expected, but the actual type is '%s'", cls, cls2));
            }
        }
    }

    public void addChangeListener(RealmChangeListener<RealmResults<E>> realmChangeListener) {
        a(realmChangeListener);
        this.e.addListener((OsResults) this, (RealmChangeListener<OsResults>) realmChangeListener);
    }

    public void addChangeListener(OrderedRealmCollectionChangeListener<RealmResults<E>> orderedRealmCollectionChangeListener) {
        a(orderedRealmCollectionChangeListener);
        this.e.addListener((OsResults) this, (OrderedRealmCollectionChangeListener<OsResults>) orderedRealmCollectionChangeListener);
    }

    private void a(@Nullable Object obj) {
        if (obj != null) {
            this.a.checkIfValid();
            this.a.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
            return;
        }
        throw new IllegalArgumentException("Listener should not be null");
    }

    private void a(@Nullable Object obj, boolean z) {
        if (z && obj == null) {
            throw new IllegalArgumentException("Listener should not be null");
        } else if (this.a.isClosed()) {
            RealmLog.warn("Calling removeChangeListener on a closed Realm %s, make sure to close all listeners before closing the Realm.", this.a.configuration.getPath());
        }
    }

    public void removeAllChangeListeners() {
        a((Object) null, false);
        this.e.removeAllListeners();
    }

    public void removeChangeListener(RealmChangeListener<RealmResults<E>> realmChangeListener) {
        a((Object) realmChangeListener, true);
        this.e.removeListener((OsResults) this, (RealmChangeListener<OsResults>) realmChangeListener);
    }

    public void removeChangeListener(OrderedRealmCollectionChangeListener<RealmResults<E>> orderedRealmCollectionChangeListener) {
        a((Object) orderedRealmCollectionChangeListener, true);
        this.e.removeListener((OsResults) this, (OrderedRealmCollectionChangeListener<OsResults>) orderedRealmCollectionChangeListener);
    }

    public Flowable<RealmResults<E>> asFlowable() {
        if (this.a instanceof Realm) {
            return this.a.configuration.getRxFactory().from((Realm) this.a, this);
        }
        if (this.a instanceof DynamicRealm) {
            return this.a.configuration.getRxFactory().from((DynamicRealm) this.a, this);
        }
        throw new UnsupportedOperationException(this.a.getClass() + " does not support RxJava2.");
    }

    public Observable<CollectionChange<RealmResults<E>>> asChangesetObservable() {
        if (this.a instanceof Realm) {
            return this.a.configuration.getRxFactory().changesetsFrom((Realm) this.a, this);
        }
        if (this.a instanceof DynamicRealm) {
            return this.a.configuration.getRxFactory().changesetsFrom((DynamicRealm) this.a, this);
        }
        throw new UnsupportedOperationException(this.a.getClass() + " does not support RxJava2.");
    }

    public String asJSON() {
        return this.e.toJSON(-1);
    }

    private void a(String str) {
        if (Util.isEmptyString(str)) {
            throw new IllegalArgumentException("Non-empty 'fieldname' required.");
        }
    }

    private void a(String str, RealmFieldType realmFieldType) {
        String className = this.e.getTable().getClassName();
        RealmFieldType fieldType = this.a.getSchema().get(className).getFieldType(str);
        if (fieldType != realmFieldType) {
            throw new IllegalArgumentException(String.format("The field '%s.%s' is not of the expected type. Actual: %s, Expected: %s", className, str, fieldType, realmFieldType));
        }
    }

    private String b(String str) {
        if (!(this.a instanceof Realm)) {
            return str;
        }
        String internalFieldName = this.a.getSchema().getColumnInfo(this.e.getTable().getClassName()).getInternalFieldName(str);
        if (internalFieldName != null) {
            return internalFieldName;
        }
        throw new IllegalArgumentException(String.format("Field '%s' does not exists.", str));
    }
}
