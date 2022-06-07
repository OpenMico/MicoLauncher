package org.seamless.util.time;

import java.io.Serializable;
import org.seamless.util.time.DateRange;

/* loaded from: classes4.dex */
public enum DateRangeOption implements Serializable {
    ALL("All dates", DateRange.Preset.ALL.getDateRange()),
    MONTH_TO_DATE("Month to date", DateRange.Preset.MONTH_TO_DATE.getDateRange()),
    YEAR_TO_DATE("Year to date", DateRange.Preset.YEAR_TO_DATE.getDateRange()),
    LAST_MONTH("Last month", DateRange.Preset.LAST_MONTH.getDateRange()),
    LAST_YEAR("Last year", DateRange.Preset.LAST_YEAR.getDateRange()),
    CUSTOM("Custom dates", null);
    
    DateRange dateRange;
    String label;

    DateRangeOption(String str, DateRange dateRange) {
        this.label = str;
        this.dateRange = dateRange;
    }

    public String getLabel() {
        return this.label;
    }

    public DateRange getDateRange() {
        return this.dateRange;
    }
}
