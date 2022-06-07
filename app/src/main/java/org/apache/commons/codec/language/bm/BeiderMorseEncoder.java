package org.apache.commons.codec.language.bm;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
public class BeiderMorseEncoder implements StringEncoder {
    private PhoneticEngine a = new PhoneticEngine(NameType.GENERIC, RuleType.APPROX, true);

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("BeiderMorseEncoder encode parameter is not of type String");
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) throws EncoderException {
        if (str == null) {
            return null;
        }
        return this.a.encode(str);
    }

    public NameType getNameType() {
        return this.a.getNameType();
    }

    public RuleType getRuleType() {
        return this.a.getRuleType();
    }

    public boolean isConcat() {
        return this.a.isConcat();
    }

    public void setConcat(boolean z) {
        this.a = new PhoneticEngine(this.a.getNameType(), this.a.getRuleType(), z, this.a.getMaxPhonemes());
    }

    public void setNameType(NameType nameType) {
        this.a = new PhoneticEngine(nameType, this.a.getRuleType(), this.a.isConcat(), this.a.getMaxPhonemes());
    }

    public void setRuleType(RuleType ruleType) {
        this.a = new PhoneticEngine(this.a.getNameType(), ruleType, this.a.isConcat(), this.a.getMaxPhonemes());
    }

    public void setMaxPhonemes(int i) {
        this.a = new PhoneticEngine(this.a.getNameType(), this.a.getRuleType(), this.a.isConcat(), i);
    }
}
