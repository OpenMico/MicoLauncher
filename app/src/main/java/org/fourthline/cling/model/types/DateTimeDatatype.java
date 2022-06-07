package org.fourthline.cling.model.types;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* loaded from: classes5.dex */
public class DateTimeDatatype extends AbstractDatatype<Calendar> {
    protected String[] readFormats;
    protected String writeFormat;

    public DateTimeDatatype(String[] strArr, String str) {
        this.readFormats = strArr;
        this.writeFormat = str;
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public Calendar valueOf(String str) throws InvalidValueException {
        if (str.equals("")) {
            return null;
        }
        Date dateValue = getDateValue(str, this.readFormats);
        if (dateValue != null) {
            Calendar instance = Calendar.getInstance(getTimeZone());
            instance.setTime(dateValue);
            return instance;
        }
        throw new InvalidValueException("Can't parse date/time from: " + str);
    }

    public String getString(Calendar calendar) throws InvalidValueException {
        if (calendar == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.writeFormat);
        simpleDateFormat.setTimeZone(getTimeZone());
        return simpleDateFormat.format(calendar.getTime());
    }

    protected String normalizeTimeZone(String str) {
        if (str.endsWith("Z")) {
            return str.substring(0, str.length() - 1) + "+0000";
        } else if (str.length() <= 7 || str.charAt(str.length() - 3) != ':') {
            return str;
        } else {
            if (str.charAt(str.length() - 6) != '-' && str.charAt(str.length() - 6) != '+') {
                return str;
            }
            return str.substring(0, str.length() - 3) + str.substring(str.length() - 2);
        }
    }

    protected Date getDateValue(String str, String[] strArr) {
        String normalizeTimeZone = normalizeTimeZone(str);
        Date date = null;
        for (String str2 : strArr) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
            simpleDateFormat.setTimeZone(getTimeZone());
            try {
                date = simpleDateFormat.parse(normalizeTimeZone);
            } catch (ParseException unused) {
            }
        }
        return date;
    }

    protected TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }
}
