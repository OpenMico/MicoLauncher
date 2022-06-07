package androidx.appcompat.app;

import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: LayoutIncludeDetector.java */
/* loaded from: classes.dex */
class b {
    @NonNull
    private final Deque<WeakReference<XmlPullParser>> a = new ArrayDeque();

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(@NonNull AttributeSet attributeSet) {
        if (!(attributeSet instanceof XmlPullParser)) {
            return false;
        }
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        if (xmlPullParser.getDepth() != 1) {
            return false;
        }
        XmlPullParser a = a(this.a);
        this.a.push(new WeakReference<>(xmlPullParser));
        return a(xmlPullParser, a);
    }

    private static boolean a(@NonNull XmlPullParser xmlPullParser, @Nullable XmlPullParser xmlPullParser2) {
        if (xmlPullParser2 == null || xmlPullParser == xmlPullParser2) {
            return false;
        }
        try {
            if (xmlPullParser2.getEventType() == 2) {
                return "include".equals(xmlPullParser2.getName());
            }
            return false;
        } catch (XmlPullParserException unused) {
            return false;
        }
    }

    @Nullable
    private static XmlPullParser a(@NonNull Deque<WeakReference<XmlPullParser>> deque) {
        while (!deque.isEmpty()) {
            XmlPullParser xmlPullParser = deque.peek().get();
            if (!a(xmlPullParser)) {
                return xmlPullParser;
            }
            deque.pop();
        }
        return null;
    }

    private static boolean a(@Nullable XmlPullParser xmlPullParser) {
        if (xmlPullParser == null) {
            return true;
        }
        try {
            if (xmlPullParser.getEventType() != 3) {
                return xmlPullParser.getEventType() == 1;
            }
            return true;
        } catch (XmlPullParserException unused) {
            return true;
        }
    }
}
