package com.fasterxml.jackson.databind.cfg;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class ContextAttributes {
    public abstract Object getAttribute(Object obj);

    public abstract ContextAttributes withPerCallAttribute(Object obj, Object obj2);

    public abstract ContextAttributes withSharedAttribute(Object obj, Object obj2);

    public abstract ContextAttributes withSharedAttributes(Map<?, ?> map);

    public abstract ContextAttributes withoutSharedAttribute(Object obj);

    public static ContextAttributes getEmpty() {
        return Impl.getEmpty();
    }

    /* loaded from: classes.dex */
    public static class Impl extends ContextAttributes implements Serializable {
        protected static final Impl EMPTY = new Impl(Collections.emptyMap());
        protected static final Object NULL_SURROGATE = new Object();
        private static final long serialVersionUID = 1;
        protected transient Map<Object, Object> _nonShared;
        protected final Map<?, ?> _shared;

        protected Impl(Map<?, ?> map) {
            this._shared = map;
            this._nonShared = null;
        }

        protected Impl(Map<?, ?> map, Map<Object, Object> map2) {
            this._shared = map;
            this._nonShared = map2;
        }

        public static ContextAttributes getEmpty() {
            return EMPTY;
        }

        @Override // com.fasterxml.jackson.databind.cfg.ContextAttributes
        public ContextAttributes withSharedAttribute(Object obj, Object obj2) {
            Map<Object, Object> map;
            if (this == EMPTY) {
                map = new HashMap<>(8);
            } else {
                map = a(this._shared);
            }
            map.put(obj, obj2);
            return new Impl(map);
        }

        @Override // com.fasterxml.jackson.databind.cfg.ContextAttributes
        public ContextAttributes withSharedAttributes(Map<?, ?> map) {
            return new Impl(map);
        }

        @Override // com.fasterxml.jackson.databind.cfg.ContextAttributes
        public ContextAttributes withoutSharedAttribute(Object obj) {
            if (this._shared.isEmpty() || !this._shared.containsKey(obj)) {
                return this;
            }
            if (this._shared.size() == 1) {
                return EMPTY;
            }
            Map<Object, Object> a = a(this._shared);
            a.remove(obj);
            return new Impl(a);
        }

        @Override // com.fasterxml.jackson.databind.cfg.ContextAttributes
        public Object getAttribute(Object obj) {
            Object obj2;
            Map<Object, Object> map = this._nonShared;
            if (map == null || (obj2 = map.get(obj)) == null) {
                return this._shared.get(obj);
            }
            if (obj2 == NULL_SURROGATE) {
                return null;
            }
            return obj2;
        }

        @Override // com.fasterxml.jackson.databind.cfg.ContextAttributes
        public ContextAttributes withPerCallAttribute(Object obj, Object obj2) {
            if (obj2 == null) {
                if (this._shared.containsKey(obj)) {
                    obj2 = NULL_SURROGATE;
                } else {
                    Map<Object, Object> map = this._nonShared;
                    if (map == null || !map.containsKey(obj)) {
                        return this;
                    }
                    this._nonShared.remove(obj);
                    return this;
                }
            }
            Map<Object, Object> map2 = this._nonShared;
            if (map2 == null) {
                return nonSharedInstance(obj, obj2);
            }
            map2.put(obj, obj2);
            return this;
        }

        protected ContextAttributes nonSharedInstance(Object obj, Object obj2) {
            HashMap hashMap = new HashMap();
            if (obj2 == null) {
                obj2 = NULL_SURROGATE;
            }
            hashMap.put(obj, obj2);
            return new Impl(this._shared, hashMap);
        }

        private Map<Object, Object> a(Map<?, ?> map) {
            return new HashMap(map);
        }
    }
}
