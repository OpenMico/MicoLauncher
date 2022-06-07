package org.fourthline.cling.support.model;

import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import org.slf4j.Marker;

/* loaded from: classes5.dex */
public class SortCriterion {
    protected final boolean ascending;
    protected final String propertyName;

    public SortCriterion(boolean z, String str) {
        this.ascending = z;
        this.propertyName = str;
    }

    public SortCriterion(String str) {
        this(str.startsWith(Marker.ANY_NON_NULL_MARKER), str.substring(1));
        if (!str.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER) && !str.startsWith(Marker.ANY_NON_NULL_MARKER)) {
            throw new IllegalArgumentException("Missing sort prefix +/- on criterion: " + str);
        }
    }

    public boolean isAscending() {
        return this.ascending;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public static SortCriterion[] valueOf(String str) {
        if (str == null || str.length() == 0) {
            return new SortCriterion[0];
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
            arrayList.add(new SortCriterion(str2.trim()));
        }
        return (SortCriterion[]) arrayList.toArray(new SortCriterion[arrayList.size()]);
    }

    public static String toString(SortCriterion[] sortCriterionArr) {
        if (sortCriterionArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (SortCriterion sortCriterion : sortCriterionArr) {
            sb.append(sortCriterion.toString());
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        }
        if (sb.toString().endsWith(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ascending ? Marker.ANY_NON_NULL_MARKER : Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        sb.append(this.propertyName);
        return sb.toString();
    }
}
