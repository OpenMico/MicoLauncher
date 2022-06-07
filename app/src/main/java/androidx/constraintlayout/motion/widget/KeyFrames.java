package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class KeyFrames {
    public static final int UNSET = -1;
    static HashMap<String, Constructor<? extends Key>> a = new HashMap<>();
    private HashMap<Integer, ArrayList<Key>> b = new HashMap<>();

    static {
        try {
            a.put("KeyAttribute", KeyAttributes.class.getConstructor(new Class[0]));
            a.put("KeyPosition", KeyPosition.class.getConstructor(new Class[0]));
            a.put("KeyCycle", KeyCycle.class.getConstructor(new Class[0]));
            a.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(new Class[0]));
            a.put("KeyTrigger", KeyTrigger.class.getConstructor(new Class[0]));
        } catch (NoSuchMethodException e) {
            Log.e("KeyFrames", "unable to load", e);
        }
    }

    private void a(Key key) {
        if (!this.b.containsKey(Integer.valueOf(key.b))) {
            this.b.put(Integer.valueOf(key.b), new ArrayList<>());
        }
        this.b.get(Integer.valueOf(key.b)).add(key);
    }

    public KeyFrames(Context context, XmlPullParser xmlPullParser) {
        Exception e;
        Key key = null;
        try {
            int eventType = xmlPullParser.getEventType();
            while (eventType != 1) {
                if (eventType != 0) {
                    switch (eventType) {
                        case 2:
                            String name = xmlPullParser.getName();
                            if (a.containsKey(name)) {
                                try {
                                    Key key2 = (Key) a.get(name).newInstance(new Object[0]);
                                    try {
                                        key2.load(context, Xml.asAttributeSet(xmlPullParser));
                                        a(key2);
                                        key = key2;
                                    } catch (Exception e2) {
                                        e = e2;
                                        key = key2;
                                        Log.e("KeyFrames", "unable to create ", e);
                                        eventType = xmlPullParser.next();
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                }
                            } else if (!(!name.equalsIgnoreCase("CustomAttribute") || key == null || key.d == null)) {
                                ConstraintAttribute.parse(context, xmlPullParser, key.d);
                                continue;
                            }
                            break;
                        case 3:
                            if (!"KeyFrameSet".equals(xmlPullParser.getName())) {
                                continue;
                            } else {
                                return;
                            }
                        default:
                            continue;
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        } catch (XmlPullParserException e5) {
            e5.printStackTrace();
        }
    }

    public void addFrames(MotionController motionController) {
        ArrayList<Key> arrayList = this.b.get(Integer.valueOf(motionController.b));
        if (arrayList != null) {
            motionController.a(arrayList);
        }
        ArrayList<Key> arrayList2 = this.b.get(-1);
        if (arrayList2 != null) {
            Iterator<Key> it = arrayList2.iterator();
            while (it.hasNext()) {
                Key next = it.next();
                if (next.a(((ConstraintLayout.LayoutParams) motionController.a.getLayoutParams()).constraintTag)) {
                    motionController.a(next);
                }
            }
        }
    }

    public Set<Integer> getKeys() {
        return this.b.keySet();
    }

    public ArrayList<Key> getKeyFramesForView(int i) {
        return this.b.get(Integer.valueOf(i));
    }
}
