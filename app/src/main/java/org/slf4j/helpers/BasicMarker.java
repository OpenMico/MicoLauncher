package org.slf4j.helpers;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class BasicMarker implements Marker {
    private static String a = "[ ";
    private static String b = " ]";
    private static String c = ", ";
    private static final long serialVersionUID = 1803952589649545191L;
    private final String name;
    private List<Marker> refereceList;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasicMarker(String str) {
        if (str != null) {
            this.name = str;
            return;
        }
        throw new IllegalArgumentException("A marker name cannot be null");
    }

    @Override // org.slf4j.Marker
    public String getName() {
        return this.name;
    }

    @Override // org.slf4j.Marker
    public synchronized void add(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("A null value cannot be added to a Marker as reference.");
        } else if (!contains(marker)) {
            if (!marker.contains(this)) {
                if (this.refereceList == null) {
                    this.refereceList = new Vector();
                }
                this.refereceList.add(marker);
            }
        }
    }

    @Override // org.slf4j.Marker
    public synchronized boolean hasReferences() {
        boolean z;
        if (this.refereceList != null) {
            if (this.refereceList.size() > 0) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    @Override // org.slf4j.Marker
    public boolean hasChildren() {
        return hasReferences();
    }

    @Override // org.slf4j.Marker
    public synchronized Iterator<Marker> iterator() {
        if (this.refereceList != null) {
            return this.refereceList.iterator();
        }
        return Collections.EMPTY_LIST.iterator();
    }

    @Override // org.slf4j.Marker
    public synchronized boolean remove(Marker marker) {
        if (this.refereceList == null) {
            return false;
        }
        int size = this.refereceList.size();
        for (int i = 0; i < size; i++) {
            if (marker.equals(this.refereceList.get(i))) {
                this.refereceList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override // org.slf4j.Marker
    public boolean contains(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("Other cannot be null");
        } else if (equals(marker)) {
            return true;
        } else {
            if (hasReferences()) {
                for (int i = 0; i < this.refereceList.size(); i++) {
                    if (this.refereceList.get(i).contains(marker)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override // org.slf4j.Marker
    public boolean contains(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Other cannot be null");
        } else if (this.name.equals(str)) {
            return true;
        } else {
            if (hasReferences()) {
                for (int i = 0; i < this.refereceList.size(); i++) {
                    if (this.refereceList.get(i).contains(str)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override // org.slf4j.Marker
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof Marker)) {
            return this.name.equals(((Marker) obj).getName());
        }
        return false;
    }

    @Override // org.slf4j.Marker
    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        if (!hasReferences()) {
            return getName();
        }
        Iterator<Marker> it = iterator();
        StringBuffer stringBuffer = new StringBuffer(getName());
        stringBuffer.append(' ');
        stringBuffer.append(a);
        while (it.hasNext()) {
            stringBuffer.append(it.next().getName());
            if (it.hasNext()) {
                stringBuffer.append(c);
            }
        }
        stringBuffer.append(b);
        return stringBuffer.toString();
    }
}
