package org.apache.commons.text;

/* loaded from: classes5.dex */
public enum CharacterPredicates implements CharacterPredicate {
    LETTERS {
        @Override // org.apache.commons.text.CharacterPredicate
        public boolean test(int i) {
            return Character.isLetter(i);
        }
    },
    DIGITS {
        @Override // org.apache.commons.text.CharacterPredicate
        public boolean test(int i) {
            return Character.isDigit(i);
        }
    }
}
