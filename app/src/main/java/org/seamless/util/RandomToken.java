package org.seamless.util;

import java.security.SecureRandom;
import java.util.Random;

/* loaded from: classes4.dex */
public class RandomToken {
    protected final Random random;

    public RandomToken() {
        try {
            this.random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            this.random.nextBytes(new byte[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generate() {
        String str = null;
        while (true) {
            if (str != null && str.length() != 0) {
                return str;
            }
            long nextLong = this.random.nextLong();
            if (nextLong < 0) {
                nextLong = -nextLong;
            }
            long nextLong2 = this.random.nextLong();
            if (nextLong2 < 0) {
                nextLong2 = -nextLong2;
            }
            str = Long.toString(nextLong, 36) + Long.toString(nextLong2, 36);
        }
    }
}
