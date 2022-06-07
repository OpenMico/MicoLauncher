package org.apache.commons.text.diff;

/* loaded from: classes5.dex */
public class StringsComparator {
    private final String a;
    private final String b;
    private final int[] c;
    private final int[] d;

    public StringsComparator(String str, String str2) {
        this.a = str;
        this.b = str2;
        int length = str.length() + str2.length() + 2;
        this.c = new int[length];
        this.d = new int[length];
    }

    public EditScript<Character> getScript() {
        EditScript<Character> editScript = new EditScript<>();
        a(0, this.a.length(), 0, this.b.length(), editScript);
        return editScript;
    }

    private void a(int i, int i2, int i3, int i4, EditScript<Character> editScript) {
        a a2 = a(i, i2, i3, i4);
        if (a2 == null || ((a2.a() == i2 && a2.c() == i2 - i4) || (a2.b() == i && a2.c() == i - i3))) {
            int i5 = i;
            int i6 = i3;
            while (true) {
                if (i5 >= i2 && i6 >= i4) {
                    return;
                }
                if (i5 < i2 && i6 < i4 && this.a.charAt(i5) == this.b.charAt(i6)) {
                    editScript.append(new KeepCommand<>(Character.valueOf(this.a.charAt(i5))));
                    i5++;
                    i6++;
                } else if (i2 - i > i4 - i3) {
                    editScript.append(new DeleteCommand<>(Character.valueOf(this.a.charAt(i5))));
                    i5++;
                } else {
                    editScript.append(new InsertCommand<>(Character.valueOf(this.b.charAt(i6))));
                    i6++;
                }
            }
        } else {
            a(i, a2.a(), i3, a2.a() - a2.c(), editScript);
            for (int a3 = a2.a(); a3 < a2.b(); a3++) {
                editScript.append(new KeepCommand<>(Character.valueOf(this.a.charAt(a3))));
            }
            a(a2.b(), i2, a2.b() - a2.c(), i4, editScript);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0039, code lost:
        if (r6[r5 - 1] < r6[r5 + 1]) goto L_0x0047;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ae, code lost:
        if (r6[r7 + 1] <= r6[r7 - 1]) goto L_0x00ba;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x010a, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARN: Removed duplicated region for block: B:65:0x008a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00fe A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0093 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0107 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.apache.commons.text.diff.StringsComparator.a a(int r12, int r13, int r14, int r15) {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.text.diff.StringsComparator.a(int, int, int, int):org.apache.commons.text.diff.StringsComparator$a");
    }

    private a b(int i, int i2, int i3, int i4) {
        int i5 = i;
        while (true) {
            int i6 = i5 - i2;
            if (i6 >= i4 || i5 >= i3 || this.a.charAt(i5) != this.b.charAt(i6)) {
                break;
            }
            i5++;
        }
        return new a(i, i5, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a {
        private final int a;
        private final int b;
        private final int c;

        public a(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }

        public int c() {
            return this.c;
        }
    }
}
