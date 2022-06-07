package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class DateDeserializers {
    private static final HashSet<String> a = new HashSet<>();

    static {
        for (Class cls : new Class[]{Calendar.class, GregorianCalendar.class, Date.class, java.util.Date.class, Timestamp.class}) {
            a.add(cls.getName());
        }
    }

    public static JsonDeserializer<?> find(Class<?> cls, String str) {
        if (!a.contains(str)) {
            return null;
        }
        if (cls == Calendar.class) {
            return new CalendarDeserializer();
        }
        if (cls == java.util.Date.class) {
            return DateDeserializer.instance;
        }
        if (cls == Date.class) {
            return new SqlDateDeserializer();
        }
        if (cls == Timestamp.class) {
            return new TimestampDeserializer();
        }
        if (cls == GregorianCalendar.class) {
            return new CalendarDeserializer(GregorianCalendar.class);
        }
        return null;
    }

    /* loaded from: classes.dex */
    protected static abstract class DateBasedDeserializer<T> extends StdScalarDeserializer<T> implements ContextualDeserializer {
        protected final DateFormat _customFormat;
        protected final String _formatString;

        protected abstract DateBasedDeserializer<T> withDateFormat(DateFormat dateFormat, String str);

        protected DateBasedDeserializer(Class<?> cls) {
            super(cls);
            this._customFormat = null;
            this._formatString = null;
        }

        protected DateBasedDeserializer(DateBasedDeserializer<T> dateBasedDeserializer, DateFormat dateFormat, String str) {
            super(dateBasedDeserializer._valueClass);
            this._customFormat = dateFormat;
            this._formatString = str;
        }

        public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
            StdDateFormat stdDateFormat;
            StdDateFormat stdDateFormat2;
            JsonFormat.Value findFormatOverrides = findFormatOverrides(deserializationContext, beanProperty, handledType());
            if (findFormatOverrides != null) {
                TimeZone timeZone = findFormatOverrides.getTimeZone();
                Boolean lenient = findFormatOverrides.getLenient();
                if (findFormatOverrides.hasPattern()) {
                    String pattern = findFormatOverrides.getPattern();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, findFormatOverrides.hasLocale() ? findFormatOverrides.getLocale() : deserializationContext.getLocale());
                    if (timeZone == null) {
                        timeZone = deserializationContext.getTimeZone();
                    }
                    simpleDateFormat.setTimeZone(timeZone);
                    if (lenient != null) {
                        simpleDateFormat.setLenient(lenient.booleanValue());
                    }
                    return withDateFormat(simpleDateFormat, pattern);
                } else if (timeZone != null) {
                    DateFormat dateFormat = deserializationContext.getConfig().getDateFormat();
                    if (dateFormat.getClass() == StdDateFormat.class) {
                        StdDateFormat withLocale = ((StdDateFormat) dateFormat).withTimeZone(timeZone).withLocale(findFormatOverrides.hasLocale() ? findFormatOverrides.getLocale() : deserializationContext.getLocale());
                        stdDateFormat2 = withLocale;
                        if (lenient != null) {
                            stdDateFormat2 = withLocale.withLenient(lenient);
                        }
                    } else {
                        DateFormat dateFormat2 = (DateFormat) dateFormat.clone();
                        dateFormat2.setTimeZone(timeZone);
                        stdDateFormat2 = dateFormat2;
                        if (lenient != null) {
                            dateFormat2.setLenient(lenient.booleanValue());
                            stdDateFormat2 = dateFormat2;
                        }
                    }
                    return withDateFormat(stdDateFormat2, this._formatString);
                } else if (lenient != null) {
                    DateFormat dateFormat3 = deserializationContext.getConfig().getDateFormat();
                    String str = this._formatString;
                    if (dateFormat3.getClass() == StdDateFormat.class) {
                        StdDateFormat withLenient = ((StdDateFormat) dateFormat3).withLenient(lenient);
                        str = withLenient.toPattern();
                        stdDateFormat = withLenient;
                    } else {
                        DateFormat dateFormat4 = (DateFormat) dateFormat3.clone();
                        dateFormat4.setLenient(lenient.booleanValue());
                        boolean z = dateFormat4 instanceof SimpleDateFormat;
                        stdDateFormat = dateFormat4;
                        if (z) {
                            ((SimpleDateFormat) dateFormat4).toPattern();
                            stdDateFormat = dateFormat4;
                        }
                    }
                    if (str == null) {
                        str = "[unknown]";
                    }
                    return withDateFormat(stdDateFormat, str);
                }
            }
            return this;
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer
        protected java.util.Date _parseDate(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            java.util.Date parse;
            if (this._customFormat == null || !jsonParser.hasToken(JsonToken.VALUE_STRING)) {
                return super._parseDate(jsonParser, deserializationContext);
            }
            String trim = jsonParser.getText().trim();
            if (trim.length() == 0) {
                return (java.util.Date) getEmptyValue(deserializationContext);
            }
            try {
                synchronized (this._customFormat) {
                    try {
                        parse = this._customFormat.parse(trim);
                    } catch (ParseException unused) {
                        return (java.util.Date) deserializationContext.handleWeirdStringValue(handledType(), trim, "expected format \"%s\"", this._formatString);
                    }
                }
                return parse;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class CalendarDeserializer extends DateBasedDeserializer<Calendar> {
        protected final Constructor<Calendar> _defaultCtor;

        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer, com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public /* bridge */ /* synthetic */ JsonDeserializer createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(deserializationContext, beanProperty);
        }

        public CalendarDeserializer() {
            super(Calendar.class);
            this._defaultCtor = null;
        }

        public CalendarDeserializer(Class<? extends Calendar> cls) {
            super(cls);
            this._defaultCtor = ClassUtil.findConstructor(cls, false);
        }

        public CalendarDeserializer(CalendarDeserializer calendarDeserializer, DateFormat dateFormat, String str) {
            super(calendarDeserializer, dateFormat, str);
            this._defaultCtor = calendarDeserializer._defaultCtor;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer
        /* renamed from: withDateFormat */
        public DateBasedDeserializer<Calendar> withDateFormat2(DateFormat dateFormat, String str) {
            return new CalendarDeserializer(this, dateFormat, str);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Calendar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            java.util.Date _parseDate = _parseDate(jsonParser, deserializationContext);
            if (_parseDate == null) {
                return null;
            }
            Constructor<Calendar> constructor = this._defaultCtor;
            if (constructor == null) {
                return deserializationContext.constructCalendar(_parseDate);
            }
            try {
                Calendar newInstance = constructor.newInstance(new Object[0]);
                newInstance.setTimeInMillis(_parseDate.getTime());
                TimeZone timeZone = deserializationContext.getTimeZone();
                if (timeZone != null) {
                    newInstance.setTimeZone(timeZone);
                }
                return newInstance;
            } catch (Exception e) {
                return (Calendar) deserializationContext.handleInstantiationProblem(handledType(), _parseDate, e);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class DateDeserializer extends DateBasedDeserializer<java.util.Date> {
        public static final DateDeserializer instance = new DateDeserializer();

        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer, com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public /* bridge */ /* synthetic */ JsonDeserializer createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(deserializationContext, beanProperty);
        }

        public DateDeserializer() {
            super(java.util.Date.class);
        }

        public DateDeserializer(DateDeserializer dateDeserializer, DateFormat dateFormat, String str) {
            super(dateDeserializer, dateFormat, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer
        public DateBasedDeserializer<java.util.Date> withDateFormat(DateFormat dateFormat, String str) {
            return new DateDeserializer(this, dateFormat, str);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public java.util.Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return _parseDate(jsonParser, deserializationContext);
        }
    }

    /* loaded from: classes.dex */
    public static class SqlDateDeserializer extends DateBasedDeserializer<Date> {
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer, com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public /* bridge */ /* synthetic */ JsonDeserializer createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(deserializationContext, beanProperty);
        }

        public SqlDateDeserializer() {
            super(Date.class);
        }

        public SqlDateDeserializer(SqlDateDeserializer sqlDateDeserializer, DateFormat dateFormat, String str) {
            super(sqlDateDeserializer, dateFormat, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer
        public DateBasedDeserializer<Date> withDateFormat(DateFormat dateFormat, String str) {
            return new SqlDateDeserializer(this, dateFormat, str);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            java.util.Date _parseDate = _parseDate(jsonParser, deserializationContext);
            if (_parseDate == null) {
                return null;
            }
            return new Date(_parseDate.getTime());
        }
    }

    /* loaded from: classes.dex */
    public static class TimestampDeserializer extends DateBasedDeserializer<Timestamp> {
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer, com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public /* bridge */ /* synthetic */ JsonDeserializer createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(deserializationContext, beanProperty);
        }

        public TimestampDeserializer() {
            super(Timestamp.class);
        }

        public TimestampDeserializer(TimestampDeserializer timestampDeserializer, DateFormat dateFormat, String str) {
            super(timestampDeserializer, dateFormat, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateBasedDeserializer
        public DateBasedDeserializer<Timestamp> withDateFormat(DateFormat dateFormat, String str) {
            return new TimestampDeserializer(this, dateFormat, str);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            java.util.Date _parseDate = _parseDate(jsonParser, deserializationContext);
            if (_parseDate == null) {
                return null;
            }
            return new Timestamp(_parseDate.getTime());
        }
    }
}
