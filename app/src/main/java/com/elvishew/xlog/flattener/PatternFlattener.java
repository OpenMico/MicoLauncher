package com.elvishew.xlog.flattener;

import com.elvishew.xlog.LogLevel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/flattener/PatternFlattener.class */
public class PatternFlattener implements Flattener, Flattener2 {
    private static final String PARAM = "[^{}]*";
    private static final Pattern PARAM_REGEX = Pattern.compile("\\{([^{}]*)\\}");
    private static final String PARAMETER_DATE = "d";
    private static final String PARAMETER_LEVEL_SHORT = "l";
    private static final String PARAMETER_LEVEL_LONG = "L";
    private static final String PARAMETER_TAG = "t";
    private static final String PARAMETER_MESSAGE = "m";
    static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private String pattern;
    private List<ParameterFiller> parameterFillers;

    public PatternFlattener(String pattern) {
        if (pattern == null) {
            throw new NullPointerException("Pattern should not be null");
        }
        this.pattern = pattern;
        this.parameterFillers = parseParameters(parsePattern(pattern));
        if (this.parameterFillers.size() == 0) {
            throw new IllegalArgumentException("No recognizable parameter found in the pattern " + pattern);
        }
    }

    static List<String> parsePattern(String pattern) {
        List<String> parameters = new ArrayList<>(4);
        Matcher matcher = PARAM_REGEX.matcher(pattern);
        while (matcher.find()) {
            parameters.add(matcher.group(1));
        }
        return parameters;
    }

    private static List<ParameterFiller> parseParameters(List<String> parameters) {
        List<ParameterFiller> parameterFillers = new ArrayList<>(parameters.size());
        for (String parameter : parameters) {
            ParameterFiller parameterFiller = parseParameter(parameter);
            if (parameterFiller != null) {
                parameterFillers.add(parameterFiller);
            }
        }
        return parameterFillers;
    }

    private static ParameterFiller parseParameter(String parameter) {
        String wrappedParameter = "{" + parameter + "}";
        String trimmedParameter = parameter.trim();
        ParameterFiller parameterFiller = parseDateParameter(wrappedParameter, trimmedParameter);
        if (parameterFiller != null) {
            return parameterFiller;
        }
        ParameterFiller parameterFiller2 = parseLevelParameter(wrappedParameter, trimmedParameter);
        if (parameterFiller2 != null) {
            return parameterFiller2;
        }
        ParameterFiller parameterFiller3 = parseTagParameter(wrappedParameter, trimmedParameter);
        if (parameterFiller3 != null) {
            return parameterFiller3;
        }
        ParameterFiller parameterFiller4 = parseMessageParameter(wrappedParameter, trimmedParameter);
        if (parameterFiller4 != null) {
            return parameterFiller4;
        }
        return null;
    }

    static DateFiller parseDateParameter(String wrappedParameter, String trimmedParameter) {
        if (trimmedParameter.startsWith("d ") && trimmedParameter.length() > PARAMETER_DATE.length() + 1) {
            return new DateFiller(wrappedParameter, trimmedParameter, trimmedParameter.substring(PARAMETER_DATE.length() + 1));
        }
        if (trimmedParameter.equals(PARAMETER_DATE)) {
            return new DateFiller(wrappedParameter, trimmedParameter, DEFAULT_DATE_FORMAT);
        }
        return null;
    }

    static LevelFiller parseLevelParameter(String wrappedParameter, String trimmedParameter) {
        if (trimmedParameter.equals("l")) {
            return new LevelFiller(wrappedParameter, trimmedParameter, false);
        }
        if (trimmedParameter.equals(PARAMETER_LEVEL_LONG)) {
            return new LevelFiller(wrappedParameter, trimmedParameter, true);
        }
        return null;
    }

    static TagFiller parseTagParameter(String wrappedParameter, String trimmedParameter) {
        if (trimmedParameter.equals("t")) {
            return new TagFiller(wrappedParameter, trimmedParameter);
        }
        return null;
    }

    static MessageFiller parseMessageParameter(String wrappedParameter, String trimmedParameter) {
        if (trimmedParameter.equals("m")) {
            return new MessageFiller(wrappedParameter, trimmedParameter);
        }
        return null;
    }

    @Override // com.elvishew.xlog.flattener.Flattener
    public CharSequence flatten(int logLevel, String tag, String message) {
        return flatten(System.currentTimeMillis(), logLevel, tag, message);
    }

    @Override // com.elvishew.xlog.flattener.Flattener2
    public CharSequence flatten(long timeMillis, int logLevel, String tag, String message) {
        String flattenedLog = this.pattern;
        for (ParameterFiller parameterFiller : this.parameterFillers) {
            flattenedLog = parameterFiller.fill(flattenedLog, timeMillis, logLevel, tag, message);
        }
        return flattenedLog;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/flattener/PatternFlattener$DateFiller.class */
    public static class DateFiller extends ParameterFiller {
        String dateFormat;
        private ThreadLocal<SimpleDateFormat> threadLocalDateFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.elvishew.xlog.flattener.PatternFlattener.DateFiller.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public SimpleDateFormat initialValue() {
                return new SimpleDateFormat(DateFiller.this.dateFormat, Locale.US);
            }
        };

        DateFiller(String wrappedParameter, String trimmedParameter, String dateFormat) {
            super(wrappedParameter, trimmedParameter);
            this.dateFormat = dateFormat;
            try {
                this.threadLocalDateFormat.get().format(new Date());
            } catch (Exception e) {
                throw new IllegalArgumentException("Bad date pattern: " + dateFormat, e);
            }
        }

        @Override // com.elvishew.xlog.flattener.PatternFlattener.ParameterFiller
        protected String fill(String pattern, long timeMillis, int logLevel, String tag, String message) {
            return pattern.replace(this.wrappedParameter, this.threadLocalDateFormat.get().format(new Date(timeMillis)));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends d {
        String a;
        private ThreadLocal<SimpleDateFormat> d = new ThreadLocal<SimpleDateFormat>() { // from class: com.elvishew.xlog.flattener.PatternFlattener.a.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public SimpleDateFormat initialValue() {
                return new SimpleDateFormat(a.this.a, Locale.US);
            }
        };

        a(String str, String str2, String str3) {
            super(str, str2);
            this.a = str3;
            try {
                this.d.get().format(new Date());
            } catch (Exception e) {
                throw new IllegalArgumentException("Bad date pattern: " + str3, e);
            }
        }

        @Override // com.elvishew.xlog.flattener.PatternFlattener.d
        protected String a(String str, long j, int i, String str2, String str3) {
            return str.replace(this.b, this.d.get().format(new Date(j)));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/flattener/PatternFlattener$LevelFiller.class */
    public static class LevelFiller extends ParameterFiller {
        boolean useLongName;

        LevelFiller(String wrappedParameter, String trimmedParameter, boolean useLongName) {
            super(wrappedParameter, trimmedParameter);
            this.useLongName = useLongName;
        }

        @Override // com.elvishew.xlog.flattener.PatternFlattener.ParameterFiller
        protected String fill(String pattern, long timeMillis, int logLevel, String tag, String message) {
            if (this.useLongName) {
                return pattern.replace(this.wrappedParameter, LogLevel.getLevelName(logLevel));
            }
            return pattern.replace(this.wrappedParameter, LogLevel.getShortLevelName(logLevel));
        }
    }

    /* loaded from: classes.dex */
    static class b extends d {
        boolean a;

        b(String str, String str2, boolean z) {
            super(str, str2);
            this.a = z;
        }

        @Override // com.elvishew.xlog.flattener.PatternFlattener.d
        protected String a(String str, long j, int i, String str2, String str3) {
            if (this.a) {
                return str.replace(this.b, LogLevel.getLevelName(i));
            }
            return str.replace(this.b, LogLevel.getShortLevelName(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/flattener/PatternFlattener$TagFiller.class */
    public static class TagFiller extends ParameterFiller {
        TagFiller(String wrappedParameter, String trimmedParameter) {
            super(wrappedParameter, trimmedParameter);
        }

        @Override // com.elvishew.xlog.flattener.PatternFlattener.ParameterFiller
        protected String fill(String pattern, long timeMillis, int logLevel, String tag, String message) {
            return pattern.replace(this.wrappedParameter, tag);
        }
    }

    /* loaded from: classes.dex */
    static class e extends d {
        e(String str, String str2) {
            super(str, str2);
        }

        @Override // com.elvishew.xlog.flattener.PatternFlattener.d
        protected String a(String str, long j, int i, String str2, String str3) {
            return str.replace(this.b, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/flattener/PatternFlattener$MessageFiller.class */
    public static class MessageFiller extends ParameterFiller {
        MessageFiller(String wrappedParameter, String trimmedParameter) {
            super(wrappedParameter, trimmedParameter);
        }

        @Override // com.elvishew.xlog.flattener.PatternFlattener.ParameterFiller
        protected String fill(String pattern, long timeMillis, int logLevel, String tag, String message) {
            return pattern.replace(this.wrappedParameter, message);
        }
    }

    /* loaded from: classes.dex */
    static class c extends d {
        c(String str, String str2) {
            super(str, str2);
        }

        @Override // com.elvishew.xlog.flattener.PatternFlattener.d
        protected String a(String str, long j, int i, String str2, String str3) {
            return str.replace(this.b, str3);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/flattener/PatternFlattener$ParameterFiller.class */
    public static abstract class ParameterFiller {
        String wrappedParameter;
        String trimmedParameter;

        protected abstract String fill(String str, long j, int i, String str2, String str3);

        ParameterFiller(String wrappedParameter, String trimmedParameter) {
            this.wrappedParameter = wrappedParameter;
            this.trimmedParameter = trimmedParameter;
        }
    }

    /* loaded from: classes.dex */
    static abstract class d {
        String b;
        String c;

        protected abstract String a(String str, long j, int i, String str2, String str3);

        d(String str, String str2) {
            this.b = str;
            this.c = str2;
        }
    }
}
