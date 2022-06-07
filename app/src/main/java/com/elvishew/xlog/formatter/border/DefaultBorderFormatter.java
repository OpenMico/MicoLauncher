package com.elvishew.xlog.formatter.border;

import com.elvishew.xlog.internal.SystemCompat;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/formatter/border/DefaultBorderFormatter.class */
public class DefaultBorderFormatter implements BorderFormatter {
    private static final char VERTICAL_BORDER_CHAR = 9553;
    private static final String TOP_HORIZONTAL_BORDER = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════";
    private static final String DIVIDER_HORIZONTAL_BORDER = "╟───────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final String BOTTOM_HORIZONTAL_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════════════════════";

    public String format(String[] segments) {
        if (segments == null || segments.length == 0) {
            return "";
        }
        String[] nonNullSegments = new String[segments.length];
        int nonNullCount = 0;
        for (String segment : segments) {
            if (segment != null) {
                nonNullCount++;
                nonNullSegments[nonNullCount] = segment;
            }
        }
        if (nonNullCount == 0) {
            return "";
        }
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append(TOP_HORIZONTAL_BORDER).append(SystemCompat.lineSeparator);
        for (int i = 0; i < nonNullCount; i++) {
            msgBuilder.append(appendVerticalBorder(nonNullSegments[i]));
            if (i != nonNullCount - 1) {
                msgBuilder.append(SystemCompat.lineSeparator).append(DIVIDER_HORIZONTAL_BORDER).append(SystemCompat.lineSeparator);
            } else {
                msgBuilder.append(SystemCompat.lineSeparator).append(BOTTOM_HORIZONTAL_BORDER);
            }
        }
        return msgBuilder.toString();
    }

    private static String appendVerticalBorder(String msg) {
        StringBuilder borderedMsgBuilder = new StringBuilder(msg.length() + 10);
        String[] lines = msg.split(SystemCompat.lineSeparator);
        int N = lines.length;
        for (int i = 0; i < N; i++) {
            if (i != 0) {
                borderedMsgBuilder.append(SystemCompat.lineSeparator);
            }
            borderedMsgBuilder.append((char) 9553).append(lines[i]);
        }
        return borderedMsgBuilder.toString();
    }
}
