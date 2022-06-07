package com.fasterxml.jackson.databind.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;

@Deprecated
/* loaded from: classes.dex */
public class ISO8601DateFormat extends DateFormat {
    private static final long serialVersionUID = 1;

    @Override // java.text.DateFormat, java.text.Format
    public Object clone() {
        return this;
    }

    public ISO8601DateFormat() {
        this.numberFormat = new DecimalFormat();
        this.calendar = new GregorianCalendar();
    }

    @Override // java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        stringBuffer.append(ISO8601Utils.format(date));
        return stringBuffer;
    }

    @Override // java.text.DateFormat
    public Date parse(String str, ParsePosition parsePosition) {
        try {
            return ISO8601Utils.parse(str, parsePosition);
        } catch (ParseException unused) {
            return null;
        }
    }

    @Override // java.text.DateFormat
    public Date parse(String str) throws ParseException {
        return ISO8601Utils.parse(str, new ParsePosition(0));
    }
}
