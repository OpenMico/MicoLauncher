package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import java.util.UUID;

/* loaded from: classes.dex */
public class ObjectIdGenerators {

    /* loaded from: classes.dex */
    public static abstract class None extends ObjectIdGenerator<Object> {
    }

    /* loaded from: classes.dex */
    private static abstract class a<T> extends ObjectIdGenerator<T> {
        protected final Class<?> _scope;

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public abstract T generateId(Object obj);

        protected a(Class<?> cls) {
            this._scope = cls;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public final Class<?> getScope() {
            return this._scope;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
            return objectIdGenerator.getClass() == getClass() && objectIdGenerator.getScope() == this._scope;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class PropertyGenerator extends a<Object> {
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.a, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public /* bridge */ /* synthetic */ boolean canUseFor(ObjectIdGenerator objectIdGenerator) {
            return super.canUseFor(objectIdGenerator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public PropertyGenerator(Class<?> cls) {
            super(cls);
        }
    }

    /* loaded from: classes.dex */
    public static final class IntSequenceGenerator extends a<Integer> {
        private static final long serialVersionUID = 1;
        protected transient int _nextValue;

        protected int initialValue() {
            return 1;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.a, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public /* bridge */ /* synthetic */ boolean canUseFor(ObjectIdGenerator objectIdGenerator) {
            return super.canUseFor(objectIdGenerator);
        }

        public IntSequenceGenerator() {
            this(Object.class, -1);
        }

        public IntSequenceGenerator(Class<?> cls, int i) {
            super(cls);
            this._nextValue = i;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<Integer> forScope(Class<?> cls) {
            return this._scope == cls ? this : new IntSequenceGenerator(cls, this._nextValue);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<Integer> newForSerialization(Object obj) {
            return new IntSequenceGenerator(this._scope, initialValue());
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator.IdKey key(Object obj) {
            if (obj == null) {
                return null;
            }
            return new ObjectIdGenerator.IdKey(getClass(), this._scope, obj);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.a, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public Integer generateId(Object obj) {
            if (obj == null) {
                return null;
            }
            int i = this._nextValue;
            this._nextValue = i + 1;
            return Integer.valueOf(i);
        }
    }

    /* loaded from: classes.dex */
    public static final class UUIDGenerator extends a<UUID> {
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<UUID> forScope(Class<?> cls) {
            return this;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<UUID> newForSerialization(Object obj) {
            return this;
        }

        public UUIDGenerator() {
            this(Object.class);
        }

        private UUIDGenerator(Class<?> cls) {
            super(Object.class);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.a, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public UUID generateId(Object obj) {
            return UUID.randomUUID();
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator.IdKey key(Object obj) {
            if (obj == null) {
                return null;
            }
            return new ObjectIdGenerator.IdKey(getClass(), null, obj);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.a, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
            return objectIdGenerator.getClass() == getClass();
        }
    }

    /* loaded from: classes.dex */
    public static final class StringIdGenerator extends a<String> {
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<String> forScope(Class<?> cls) {
            return this;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<String> newForSerialization(Object obj) {
            return this;
        }

        public StringIdGenerator() {
            this(Object.class);
        }

        private StringIdGenerator(Class<?> cls) {
            super(Object.class);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.a, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public String generateId(Object obj) {
            return UUID.randomUUID().toString();
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator.IdKey key(Object obj) {
            if (obj == null) {
                return null;
            }
            return new ObjectIdGenerator.IdKey(getClass(), null, obj);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.a, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
            return objectIdGenerator instanceof StringIdGenerator;
        }
    }
}
