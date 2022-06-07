package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;

/* loaded from: classes3.dex */
public class NFC {

    /* loaded from: classes3.dex */
    public enum CardType {
        BANK(0),
        TRAFFIC(1),
        ACCESS(2),
        DEFAULT(3);
        
        private int id;

        CardType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "TurnOff", namespace = AIApiConstants.NFC.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOff implements InstructionPayload {
        @Required
        private CardType card;

        public TurnOff() {
        }

        public TurnOff(CardType cardType) {
            this.card = cardType;
        }

        @Required
        public TurnOff setCard(CardType cardType) {
            this.card = cardType;
            return this;
        }

        @Required
        public CardType getCard() {
            return this.card;
        }
    }

    @NamespaceName(name = "TurnOn", namespace = AIApiConstants.NFC.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOn implements InstructionPayload {
        @Required
        private CardType card;

        public TurnOn() {
        }

        public TurnOn(CardType cardType) {
            this.card = cardType;
        }

        @Required
        public TurnOn setCard(CardType cardType) {
            this.card = cardType;
            return this;
        }

        @Required
        public CardType getCard() {
            return this.card;
        }
    }
}
