package org.eclipse.jetty.util;

import com.xiaomi.mipush.sdk.Constants;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class StringMap extends AbstractMap implements Externalizable {
    public static final boolean CASE_INSENSTIVE = true;
    protected static final int __HASH_WIDTH = 17;
    protected HashSet _entrySet;
    protected boolean _ignoreCase;
    protected NullEntry _nullEntry;
    protected Object _nullValue;
    protected Node _root;
    protected Set _umEntrySet;
    protected int _width;

    public StringMap() {
        this._width = 17;
        this._root = new Node();
        this._ignoreCase = false;
        this._nullEntry = null;
        this._nullValue = null;
        this._entrySet = new HashSet(3);
        this._umEntrySet = Collections.unmodifiableSet(this._entrySet);
    }

    public StringMap(boolean z) {
        this();
        this._ignoreCase = z;
    }

    public StringMap(boolean z, int i) {
        this();
        this._ignoreCase = z;
        this._width = i;
    }

    public void setIgnoreCase(boolean z) {
        if (this._root._children == null) {
            this._ignoreCase = z;
            return;
        }
        throw new IllegalStateException("Must be set before first put");
    }

    public boolean isIgnoreCase() {
        return this._ignoreCase;
    }

    public void setWidth(int i) {
        this._width = i;
    }

    public int getWidth() {
        return this._width;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object put(Object obj, Object obj2) {
        if (obj == null) {
            return put((String) null, obj2);
        }
        return put(obj.toString(), obj2);
    }

    public Object put(String str, Object obj) {
        if (str == null) {
            Object obj2 = this._nullValue;
            this._nullValue = obj;
            if (this._nullEntry == null) {
                this._nullEntry = new NullEntry();
                this._entrySet.add(this._nullEntry);
            }
            return obj2;
        }
        Node node = null;
        Node node2 = null;
        Node node3 = this._root;
        int i = 0;
        int i2 = -1;
        while (true) {
            if (i >= str.length()) {
                break;
            }
            char charAt = str.charAt(i);
            if (i2 == -1) {
                node3 = node3._children == null ? null : node3._children[charAt % this._width];
                node = null;
                node2 = node3;
                i2 = 0;
            }
            while (node3 != null) {
                if (node3._char[i2] == charAt || (this._ignoreCase && node3._ochar[i2] == charAt)) {
                    i2++;
                    if (i2 == node3._char.length) {
                        node = null;
                        i2 = -1;
                    } else {
                        node = null;
                    }
                } else if (i2 == 0) {
                    node = node3;
                    node3 = node3._next;
                } else {
                    node3.split(this, i2);
                    i--;
                    i2 = -1;
                }
                i++;
            }
            node3 = new Node(this._ignoreCase, str, i);
            if (node != null) {
                node._next = node3;
            } else if (node2 != null) {
                if (node2._children == null) {
                    node2._children = new Node[this._width];
                }
                node2._children[charAt % this._width] = node3;
                int i3 = node3._ochar[0] % this._width;
                if (node3._ochar != null && node3._char[0] % this._width != i3) {
                    if (node2._children[i3] == null) {
                        node2._children[i3] = node3;
                    } else {
                        Node node4 = node2._children[i3];
                        while (node4._next != null) {
                            node4 = node4._next;
                        }
                        node4._next = node3;
                    }
                }
            } else {
                this._root = node3;
            }
        }
        if (node3 == null) {
            return null;
        }
        if (i2 > 0) {
            node3.split(this, i2);
        }
        Object obj3 = node3._value;
        node3._key = str;
        node3._value = obj;
        this._entrySet.add(node3);
        return obj3;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        if (obj == null) {
            return this._nullValue;
        }
        if (obj instanceof String) {
            return get((String) obj);
        }
        return get(obj.toString());
    }

    public Object get(String str) {
        if (str == null) {
            return this._nullValue;
        }
        Map.Entry entry = getEntry(str, 0, str.length());
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    public Map.Entry getEntry(String str, int i, int i2) {
        if (str == null) {
            return this._nullEntry;
        }
        Node node = this._root;
        int i3 = -1;
        for (int i4 = 0; i4 < i2; i4++) {
            char charAt = str.charAt(i + i4);
            if (i3 == -1) {
                node = node._children == null ? null : node._children[charAt % this._width];
                i3 = 0;
            }
            while (node != null) {
                if (node._char[i3] == charAt || (this._ignoreCase && node._ochar[i3] == charAt)) {
                    i3++;
                    if (i3 == node._char.length) {
                        i3 = -1;
                    }
                } else if (i3 > 0) {
                    return null;
                } else {
                    node = node._next;
                }
            }
            return null;
        }
        if (i3 > 0) {
            return null;
        }
        if (node == null || node._key != null) {
            return node;
        }
        return null;
    }

    public Map.Entry getEntry(char[] cArr, int i, int i2) {
        if (cArr == null) {
            return this._nullEntry;
        }
        Node node = this._root;
        int i3 = -1;
        for (int i4 = 0; i4 < i2; i4++) {
            char c = cArr[i + i4];
            if (i3 == -1) {
                node = node._children == null ? null : node._children[c % this._width];
                i3 = 0;
            }
            while (node != null) {
                if (node._char[i3] == c || (this._ignoreCase && node._ochar[i3] == c)) {
                    i3++;
                    if (i3 == node._char.length) {
                        i3 = -1;
                    }
                } else if (i3 > 0) {
                    return null;
                } else {
                    node = node._next;
                }
            }
            return null;
        }
        if (i3 > 0) {
            return null;
        }
        if (node == null || node._key != null) {
            return node;
        }
        return null;
    }

    public Map.Entry getBestEntry(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return this._nullEntry;
        }
        Node node = this._root;
        int i3 = -1;
        for (int i4 = 0; i4 < i2; i4++) {
            char c = (char) bArr[i + i4];
            if (i3 == -1) {
                Node node2 = node._children == null ? null : node._children[c % this._width];
                if (node2 == null && i4 > 0) {
                    return node;
                }
                node = node2;
                i3 = 0;
            }
            while (node != null) {
                if (node._char[i3] == c || (this._ignoreCase && node._ochar[i3] == c)) {
                    i3++;
                    if (i3 == node._char.length) {
                        i3 = -1;
                    }
                } else if (i3 > 0) {
                    return null;
                } else {
                    node = node._next;
                }
            }
            return null;
        }
        if (i3 > 0) {
            return null;
        }
        if (node == null || node._key != null) {
            return node;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        if (obj == null) {
            return remove((String) null);
        }
        return remove(obj.toString());
    }

    public Object remove(String str) {
        if (str == null) {
            Object obj = this._nullValue;
            NullEntry nullEntry = this._nullEntry;
            if (nullEntry != null) {
                this._entrySet.remove(nullEntry);
                this._nullEntry = null;
                this._nullValue = null;
            }
            return obj;
        }
        Node node = this._root;
        int i = -1;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (i == -1) {
                node = node._children == null ? null : node._children[charAt % this._width];
                i = 0;
            }
            while (node != null) {
                if (node._char[i] == charAt || (this._ignoreCase && node._ochar[i] == charAt)) {
                    i++;
                    if (i == node._char.length) {
                        i = -1;
                    }
                } else if (i > 0) {
                    return null;
                } else {
                    node = node._next;
                }
            }
            return null;
        }
        if (i > 0) {
            return null;
        }
        if (node != null && node._key == null) {
            return null;
        }
        Object obj2 = node._value;
        this._entrySet.remove(node);
        node._value = null;
        node._key = null;
        return obj2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set entrySet() {
        return this._umEntrySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this._entrySet.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this._entrySet.isEmpty();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        if (obj == null) {
            return this._nullEntry != null;
        }
        return getEntry(obj.toString(), 0, obj == null ? 0 : obj.toString().length()) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._root = new Node();
        this._nullEntry = null;
        this._nullValue = null;
        this._entrySet.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class Node implements Map.Entry {
        char[] _char;
        Node[] _children;
        String _key;
        Node _next;
        char[] _ochar;
        Object _value;

        Node() {
        }

        Node(boolean z, String str, int i) {
            int length = str.length() - i;
            this._char = new char[length];
            this._ochar = new char[length];
            for (int i2 = 0; i2 < length; i2++) {
                char charAt = str.charAt(i + i2);
                this._char[i2] = charAt;
                if (z) {
                    if (Character.isUpperCase(charAt)) {
                        charAt = Character.toLowerCase(charAt);
                    } else if (Character.isLowerCase(charAt)) {
                        charAt = Character.toUpperCase(charAt);
                    }
                    this._ochar[i2] = charAt;
                }
            }
        }

        Node split(StringMap stringMap, int i) {
            Node node = new Node();
            char[] cArr = this._char;
            int length = cArr.length - i;
            this._char = new char[i];
            node._char = new char[length];
            System.arraycopy(cArr, 0, this._char, 0, i);
            System.arraycopy(cArr, i, node._char, 0, length);
            char[] cArr2 = this._ochar;
            if (cArr2 != null) {
                this._ochar = new char[i];
                node._ochar = new char[length];
                System.arraycopy(cArr2, 0, this._ochar, 0, i);
                System.arraycopy(cArr2, i, node._ochar, 0, length);
            }
            node._key = this._key;
            node._value = this._value;
            this._key = null;
            this._value = null;
            if (stringMap._entrySet.remove(this)) {
                stringMap._entrySet.add(node);
            }
            node._children = this._children;
            this._children = new Node[stringMap._width];
            this._children[node._char[0] % stringMap._width] = node;
            char[] cArr3 = node._ochar;
            if (!(cArr3 == null || this._children[cArr3[0] % stringMap._width] == node)) {
                this._children[node._ochar[0] % stringMap._width] = node;
            }
            return node;
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this._key;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this._value;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            Object obj2 = this._value;
            this._value = obj;
            return obj2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            toString(sb);
            return sb.toString();
        }

        private void toString(StringBuilder sb) {
            sb.append("{[");
            if (this._char != null) {
                int i = 0;
                while (true) {
                    char[] cArr = this._char;
                    if (i >= cArr.length) {
                        break;
                    }
                    sb.append(cArr[i]);
                    i++;
                }
            } else {
                sb.append('-');
            }
            sb.append(':');
            sb.append(this._key);
            sb.append('=');
            sb.append(this._value);
            sb.append(']');
            if (this._children != null) {
                for (int i2 = 0; i2 < this._children.length; i2++) {
                    sb.append('|');
                    Node[] nodeArr = this._children;
                    if (nodeArr[i2] != null) {
                        nodeArr[i2].toString(sb);
                    } else {
                        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                    }
                }
            }
            sb.append('}');
            if (this._next != null) {
                sb.append(",\n");
                this._next.toString(sb);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class NullEntry implements Map.Entry {
        @Override // java.util.Map.Entry
        public Object getKey() {
            return null;
        }

        private NullEntry() {
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return StringMap.this._nullValue;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            Object obj2 = StringMap.this._nullValue;
            StringMap.this._nullValue = obj;
            return obj2;
        }

        public String toString() {
            return "[:null=" + StringMap.this._nullValue + "]";
        }
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        HashMap hashMap = new HashMap(this);
        objectOutput.writeBoolean(this._ignoreCase);
        objectOutput.writeObject(hashMap);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        setIgnoreCase(objectInput.readBoolean());
        putAll((HashMap) objectInput.readObject());
    }
}
