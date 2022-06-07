package androidx.room.util;

import android.database.Cursor;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public final class FtsTableInfo {
    private static final String[] a = {"tokenize=", "compress=", "content=", "languageid=", "matchinfo=", "notindexed=", "order=", "prefix=", "uncompress="};
    public final Set<String> columns;
    public final String name;
    public final Set<String> options;

    public FtsTableInfo(String str, Set<String> set, Set<String> set2) {
        this.name = str;
        this.columns = set;
        this.options = set2;
    }

    public FtsTableInfo(String str, Set<String> set, String str2) {
        this.name = str;
        this.columns = set;
        this.options = a(str2);
    }

    public static FtsTableInfo read(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
        return new FtsTableInfo(str, a(supportSQLiteDatabase, str), b(supportSQLiteDatabase, str));
    }

    private static Set<String> a(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
        Cursor query = supportSQLiteDatabase.query("PRAGMA table_info(`" + str + "`)");
        HashSet hashSet = new HashSet();
        try {
            if (query.getColumnCount() > 0) {
                int columnIndex = query.getColumnIndex("name");
                while (query.moveToNext()) {
                    hashSet.add(query.getString(columnIndex));
                }
            }
            return hashSet;
        } finally {
            query.close();
        }
    }

    /* JADX WARN: Finally extract failed */
    private static Set<String> b(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
        String str2 = "";
        Cursor query = supportSQLiteDatabase.query("SELECT * FROM sqlite_master WHERE `name` = '" + str + LrcRow.SINGLE_QUOTE);
        try {
            if (query.moveToFirst()) {
                str2 = query.getString(query.getColumnIndexOrThrow("sql"));
            }
            query.close();
            return a(str2);
        } catch (Throwable th) {
            query.close();
            throw th;
        }
    }

    @VisibleForTesting
    static Set<String> a(String str) {
        if (str.isEmpty()) {
            return new HashSet();
        }
        String substring = str.substring(str.indexOf(40) + 1, str.lastIndexOf(41));
        ArrayList<String> arrayList = new ArrayList();
        ArrayDeque arrayDeque = new ArrayDeque();
        int i = -1;
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (!(charAt == '\"' || charAt == '\'')) {
                if (charAt != ',') {
                    if (charAt != '[') {
                        if (charAt != ']') {
                            if (charAt != '`') {
                            }
                        } else if (!arrayDeque.isEmpty() && ((Character) arrayDeque.peek()).charValue() == '[') {
                            arrayDeque.pop();
                        }
                    } else if (arrayDeque.isEmpty()) {
                        arrayDeque.push(Character.valueOf(charAt));
                    }
                } else if (arrayDeque.isEmpty()) {
                    arrayList.add(substring.substring(i + 1, i2).trim());
                    i = i2;
                }
            }
            if (arrayDeque.isEmpty()) {
                arrayDeque.push(Character.valueOf(charAt));
            } else if (((Character) arrayDeque.peek()).charValue() == charAt) {
                arrayDeque.pop();
            }
        }
        arrayList.add(substring.substring(i + 1).trim());
        HashSet hashSet = new HashSet();
        for (String str2 : arrayList) {
            for (String str3 : a) {
                if (str2.startsWith(str3)) {
                    hashSet.add(str2);
                }
            }
        }
        return hashSet;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FtsTableInfo)) {
            return false;
        }
        FtsTableInfo ftsTableInfo = (FtsTableInfo) obj;
        String str = this.name;
        if (str == null ? ftsTableInfo.name != null : !str.equals(ftsTableInfo.name)) {
            return false;
        }
        Set<String> set = this.columns;
        if (set == null ? ftsTableInfo.columns != null : !set.equals(ftsTableInfo.columns)) {
            return false;
        }
        Set<String> set2 = this.options;
        return set2 != null ? set2.equals(ftsTableInfo.options) : ftsTableInfo.options == null;
    }

    public int hashCode() {
        String str = this.name;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        Set<String> set = this.columns;
        int hashCode2 = (hashCode + (set != null ? set.hashCode() : 0)) * 31;
        Set<String> set2 = this.options;
        if (set2 != null) {
            i = set2.hashCode();
        }
        return hashCode2 + i;
    }

    public String toString() {
        return "FtsTableInfo{name='" + this.name + "', columns=" + this.columns + ", options=" + this.options + '}';
    }
}
