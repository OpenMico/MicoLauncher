package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class Memo {

    @NamespaceName(name = "MultipleTurnStarted", namespace = AIApiConstants.Memo.NAME)
    /* loaded from: classes3.dex */
    public static class MultipleTurnStarted implements EventPayload {
    }

    @NamespaceName(name = "StartMultipleTurn", namespace = AIApiConstants.Memo.NAME)
    /* loaded from: classes3.dex */
    public static class StartMultipleTurn implements InstructionPayload {
    }

    @NamespaceName(name = "CreateResponse", namespace = AIApiConstants.Memo.NAME)
    /* loaded from: classes3.dex */
    public static class CreateResponse implements InstructionPayload {
        @Required
        private String memo_id;
        @Required
        private boolean result;

        public CreateResponse() {
        }

        public CreateResponse(boolean z, String str) {
            this.result = z;
            this.memo_id = str;
        }

        @Required
        public CreateResponse setResult(boolean z) {
            this.result = z;
            return this;
        }

        @Required
        public boolean isResult() {
            return this.result;
        }

        @Required
        public CreateResponse setMemoId(String str) {
            this.memo_id = str;
            return this;
        }

        @Required
        public String getMemoId() {
            return this.memo_id;
        }
    }

    @NamespaceName(name = "Delete", namespace = AIApiConstants.Memo.NAME)
    /* loaded from: classes3.dex */
    public static class Delete implements EventPayload {
        @Required
        private String memo_id;

        public Delete() {
        }

        public Delete(String str) {
            this.memo_id = str;
        }

        @Required
        public Delete setMemoId(String str) {
            this.memo_id = str;
            return this;
        }

        @Required
        public String getMemoId() {
            return this.memo_id;
        }
    }

    @NamespaceName(name = "DeleteResponse", namespace = AIApiConstants.Memo.NAME)
    /* loaded from: classes3.dex */
    public static class DeleteResponse implements InstructionPayload {
        @Required
        private String memo_id;
        @Required
        private boolean result;

        public DeleteResponse() {
        }

        public DeleteResponse(boolean z, String str) {
            this.result = z;
            this.memo_id = str;
        }

        @Required
        public DeleteResponse setResult(boolean z) {
            this.result = z;
            return this;
        }

        @Required
        public boolean isResult() {
            return this.result;
        }

        @Required
        public DeleteResponse setMemoId(String str) {
            this.memo_id = str;
            return this;
        }

        @Required
        public String getMemoId() {
            return this.memo_id;
        }
    }

    @NamespaceName(name = "GetMore", namespace = AIApiConstants.Memo.NAME)
    /* loaded from: classes3.dex */
    public static class GetMore implements EventPayload {
        @Required
        private String cursor;
        private Optional<Integer> size = Optional.empty();

        public GetMore() {
        }

        public GetMore(String str) {
            this.cursor = str;
        }

        @Required
        public GetMore setCursor(String str) {
            this.cursor = str;
            return this;
        }

        @Required
        public String getCursor() {
            return this.cursor;
        }

        public GetMore setSize(int i) {
            this.size = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getSize() {
            return this.size;
        }
    }

    @NamespaceName(name = "MultipleTurnFinished", namespace = AIApiConstants.Memo.NAME)
    /* loaded from: classes3.dex */
    public static class MultipleTurnFinished implements EventPayload {
        @Required
        private String content;
        @Required
        private String memo_id;

        public MultipleTurnFinished() {
        }

        public MultipleTurnFinished(String str, String str2) {
            this.content = str;
            this.memo_id = str2;
        }

        @Required
        public MultipleTurnFinished setContent(String str) {
            this.content = str;
            return this;
        }

        @Required
        public String getContent() {
            return this.content;
        }

        @Required
        public MultipleTurnFinished setMemoId(String str) {
            this.memo_id = str;
            return this;
        }

        @Required
        public String getMemoId() {
            return this.memo_id;
        }
    }
}
