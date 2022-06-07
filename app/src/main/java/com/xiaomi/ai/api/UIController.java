package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class UIController {

    @NamespaceName(name = "Bookmark", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class Bookmark implements InstructionPayload {
    }

    @NamespaceName(name = "EndScreenProjection", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class EndScreenProjection implements InstructionPayload {
    }

    @NamespaceName(name = "StartScreenProjection", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class StartScreenProjection implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum AuthorizationOperation {
        AUTH(0),
        CANCEL_AUTH(1);
        
        private int id;

        AuthorizationOperation(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum AuthorizationScope {
        POSITION(0),
        VOICE(1);
        
        private int id;

        AuthorizationScope(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum CommonEditCmd {
        PRINT(0),
        SELECT_ALL(1),
        COPY(2),
        PASTE(3),
        CUT(4),
        DELETE(5),
        COMPLETE_DELETE(6),
        SAVE(7),
        SAVE_AS(8),
        FIND(9),
        REPLACE(10),
        UNDO(11),
        REDO(12),
        CLOSE(13),
        ZOOM_IN(14),
        ZOOM_OUT(15),
        RESUME_ZOOM(16),
        MINIMIZE(17),
        MAXIMIZE(18),
        MINIMIZE_ALL(19),
        MAXIMIZE_ALL(20);
        
        private int id;

        CommonEditCmd(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum FileSizeCompare {
        LG(0),
        EQ(1),
        LE(2);
        
        private int id;

        FileSizeCompare(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum InteractionControlTextMatchMethod {
        DEFAULT(0),
        EXACT(1);
        
        private int id;

        InteractionControlTextMatchMethod(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum InteractionIntentType {
        MUSIC_SELECT(0),
        STATION_SELECT(1),
        VIDEO_SELECT(2),
        COMMON(3);
        
        private int id;

        InteractionIntentType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum InteractionOp {
        CLICK(0),
        SWIPE_UP(1),
        SWIPE_DOWN(2),
        SWIPE_LEFT(3),
        SWIPE_RIGHT(4),
        BACK(5),
        EDIT(6);
        
        private int id;

        InteractionOp(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum NavigateOp {
        UNKNOWN(-1),
        HOME(0),
        BACK(1),
        CONFIRM(2),
        CANCEL(3),
        PREV_PAGE(4),
        NEXT_PAGE(5),
        EXIT(6),
        FULL_SCREEN(7),
        QUIT_FULL_SCREEN(8),
        EXIT_XIAOAI(9),
        EXIT_BOTTOM_CAPTURE(10);
        
        private int id;

        NavigateOp(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ScreenshotType {
        FULL_SCREEN(0),
        CURREN_WINDOW(1),
        SELECT_WINDOW(2);
        
        private int id;

        ScreenshotType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SearchFileType {
        PICTURE(0),
        MUSIC(1),
        DOC(2),
        ARCHIVE(3),
        LARGE_DOC(4),
        ZIP(5),
        WORD(6),
        PPT(7),
        VIDEO(8),
        EXCEL(9),
        PDF(10),
        FOLDER(11),
        ALL(100);
        
        private int id;

        SearchFileType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "AuthorizationUpdated", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class AuthorizationUpdated implements EventPayload {
        @Required
        private AuthorizationOperation operation;
        @Required
        private List<AuthorizationScope> scope;

        public AuthorizationUpdated() {
        }

        public AuthorizationUpdated(AuthorizationOperation authorizationOperation, List<AuthorizationScope> list) {
            this.operation = authorizationOperation;
            this.scope = list;
        }

        @Required
        public AuthorizationUpdated setOperation(AuthorizationOperation authorizationOperation) {
            this.operation = authorizationOperation;
            return this;
        }

        @Required
        public AuthorizationOperation getOperation() {
            return this.operation;
        }

        @Required
        public AuthorizationUpdated setScope(List<AuthorizationScope> list) {
            this.scope = list;
            return this;
        }

        @Required
        public List<AuthorizationScope> getScope() {
            return this.scope;
        }
    }

    @NamespaceName(name = "CommonEdit", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class CommonEdit implements InstructionPayload {
        @Required
        private CommonEditCmd cmd;

        public CommonEdit() {
        }

        public CommonEdit(CommonEditCmd commonEditCmd) {
            this.cmd = commonEditCmd;
        }

        @Required
        public CommonEdit setCmd(CommonEditCmd commonEditCmd) {
            this.cmd = commonEditCmd;
            return this;
        }

        @Required
        public CommonEditCmd getCmd() {
            return this.cmd;
        }
    }

    @NamespaceName(name = "InputImages", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class InputImages implements InstructionPayload {
        @Required
        private List<Template.Image> images;
        @Required
        private Template.Title title;

        public InputImages() {
        }

        public InputImages(Template.Title title, List<Template.Image> list) {
            this.title = title;
            this.images = list;
        }

        @Required
        public InputImages setTitle(Template.Title title) {
            this.title = title;
            return this;
        }

        @Required
        public Template.Title getTitle() {
            return this.title;
        }

        @Required
        public InputImages setImages(List<Template.Image> list) {
            this.images = list;
            return this;
        }

        @Required
        public List<Template.Image> getImages() {
            return this.images;
        }
    }

    @NamespaceName(name = "InputText", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class InputText implements InstructionPayload {
        @Required
        private String text;
        private Optional<Template.Title> title = Optional.empty();

        public InputText() {
        }

        public InputText(String str) {
            this.text = str;
        }

        @Required
        public InputText setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public InputText setTitle(Template.Title title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Optional<Template.Title> getTitle() {
            return this.title;
        }
    }

    @NamespaceName(name = "Interaction", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class Interaction implements InstructionPayload {
        @Required
        private List<String> ids;
        @Required
        private String page_id;
        private Optional<InteractionOp> operation = Optional.empty();
        private Optional<String> edit_content = Optional.empty();

        public Interaction() {
        }

        public Interaction(String str, List<String> list) {
            this.page_id = str;
            this.ids = list;
        }

        @Required
        public Interaction setPageId(String str) {
            this.page_id = str;
            return this;
        }

        @Required
        public String getPageId() {
            return this.page_id;
        }

        @Required
        public Interaction setIds(List<String> list) {
            this.ids = list;
            return this;
        }

        @Required
        public List<String> getIds() {
            return this.ids;
        }

        public Interaction setOperation(InteractionOp interactionOp) {
            this.operation = Optional.ofNullable(interactionOp);
            return this;
        }

        public Optional<InteractionOp> getOperation() {
            return this.operation;
        }

        public Interaction setEditContent(String str) {
            this.edit_content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEditContent() {
            return this.edit_content;
        }
    }

    /* loaded from: classes3.dex */
    public static class InteractionControl {
        @Required
        private String id;
        @Required
        private List<InteractionOp> operation;
        private Optional<List<String>> texts = Optional.empty();
        private Optional<Integer> number = Optional.empty();
        private Optional<String> entity_id = Optional.empty();
        private Optional<InteractionControlTextMatchMethod> text_match_method = Optional.empty();

        public InteractionControl() {
        }

        public InteractionControl(String str, List<InteractionOp> list) {
            this.id = str;
            this.operation = list;
        }

        @Required
        public InteractionControl setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public InteractionControl setOperation(List<InteractionOp> list) {
            this.operation = list;
            return this;
        }

        @Required
        public List<InteractionOp> getOperation() {
            return this.operation;
        }

        public InteractionControl setTexts(List<String> list) {
            this.texts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getTexts() {
            return this.texts;
        }

        public InteractionControl setNumber(int i) {
            this.number = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getNumber() {
            return this.number;
        }

        public InteractionControl setEntityId(String str) {
            this.entity_id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEntityId() {
            return this.entity_id;
        }

        public InteractionControl setTextMatchMethod(InteractionControlTextMatchMethod interactionControlTextMatchMethod) {
            this.text_match_method = Optional.ofNullable(interactionControlTextMatchMethod);
            return this;
        }

        public Optional<InteractionControlTextMatchMethod> getTextMatchMethod() {
            return this.text_match_method;
        }
    }

    /* loaded from: classes3.dex */
    public static class InteractionEntity {
        @Required
        private String name;
        @Required
        private List<InteractionEntityWord> words;

        public InteractionEntity() {
        }

        public InteractionEntity(String str, List<InteractionEntityWord> list) {
            this.name = str;
            this.words = list;
        }

        @Required
        public InteractionEntity setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public InteractionEntity setWords(List<InteractionEntityWord> list) {
            this.words = list;
            return this;
        }

        @Required
        public List<InteractionEntityWord> getWords() {
            return this.words;
        }
    }

    @NamespaceName(name = "InteractionEntityInfo", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class InteractionEntityInfo implements ContextPayload {
        @Required
        private List<InteractionEntity> entities;
        @Required
        private List<InteractionIntentType> intents;

        public InteractionEntityInfo() {
        }

        public InteractionEntityInfo(List<InteractionIntentType> list, List<InteractionEntity> list2) {
            this.intents = list;
            this.entities = list2;
        }

        @Required
        public InteractionEntityInfo setIntents(List<InteractionIntentType> list) {
            this.intents = list;
            return this;
        }

        @Required
        public List<InteractionIntentType> getIntents() {
            return this.intents;
        }

        @Required
        public InteractionEntityInfo setEntities(List<InteractionEntity> list) {
            this.entities = list;
            return this;
        }

        @Required
        public List<InteractionEntity> getEntities() {
            return this.entities;
        }
    }

    /* loaded from: classes3.dex */
    public static class InteractionEntityWord {
        @Required
        private String id;
        @Required
        private String text;

        public InteractionEntityWord() {
        }

        public InteractionEntityWord(String str, String str2) {
            this.text = str;
            this.id = str2;
        }

        @Required
        public InteractionEntityWord setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public InteractionEntityWord setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "InteractionInfo", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class InteractionInfo implements ContextPayload {
        @Required
        private List<InteractionControl> controls;
        @Required
        private String page_id;
        private Optional<String> category = Optional.empty();
        private Optional<String> entity_index_key = Optional.empty();

        public InteractionInfo() {
        }

        public InteractionInfo(String str, List<InteractionControl> list) {
            this.page_id = str;
            this.controls = list;
        }

        @Required
        public InteractionInfo setPageId(String str) {
            this.page_id = str;
            return this;
        }

        @Required
        public String getPageId() {
            return this.page_id;
        }

        @Required
        public InteractionInfo setControls(List<InteractionControl> list) {
            this.controls = list;
            return this;
        }

        @Required
        public List<InteractionControl> getControls() {
            return this.controls;
        }

        public InteractionInfo setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public InteractionInfo setEntityIndexKey(String str) {
            this.entity_index_key = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEntityIndexKey() {
            return this.entity_index_key;
        }
    }

    @NamespaceName(name = "Navigate", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class Navigate implements InstructionPayload {
        @Required
        private NavigateOp operation;

        public Navigate() {
        }

        public Navigate(NavigateOp navigateOp) {
            this.operation = navigateOp;
        }

        @Required
        public Navigate setOperation(NavigateOp navigateOp) {
            this.operation = navigateOp;
            return this;
        }

        @Required
        public NavigateOp getOperation() {
            return this.operation;
        }
    }

    @NamespaceName(name = "ReplyEmail", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class ReplyEmail implements InstructionPayload {
        private Optional<String> name = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<String> receiver = Optional.empty();
        private Optional<Boolean> reply_to_all = Optional.empty();

        public ReplyEmail setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public ReplyEmail setContent(String str) {
            this.content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContent() {
            return this.content;
        }

        public ReplyEmail setReceiver(String str) {
            this.receiver = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getReceiver() {
            return this.receiver;
        }

        public ReplyEmail setReplyToAll(boolean z) {
            this.reply_to_all = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isReplyToAll() {
            return this.reply_to_all;
        }
    }

    @NamespaceName(name = "Screenshot", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class Screenshot implements InstructionPayload {

        /* renamed from: app  reason: collision with root package name */
        private Optional<String> f200app = Optional.empty();
        @Required
        private ScreenshotType type;

        public Screenshot() {
        }

        public Screenshot(ScreenshotType screenshotType) {
            this.type = screenshotType;
        }

        @Required
        public Screenshot setType(ScreenshotType screenshotType) {
            this.type = screenshotType;
            return this;
        }

        @Required
        public ScreenshotType getType() {
            return this.type;
        }

        public Screenshot setApp(String str) {
            this.f200app = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getApp() {
            return this.f200app;
        }
    }

    @NamespaceName(name = "SearchEmail", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class SearchEmail implements InstructionPayload {
        private Optional<String> name = Optional.empty();
        private Optional<String> sender = Optional.empty();
        private Optional<String> receiver = Optional.empty();
        private Optional<String> start_time = Optional.empty();
        private Optional<String> end_time = Optional.empty();

        public SearchEmail setName(String str) {
            this.name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getName() {
            return this.name;
        }

        public SearchEmail setSender(String str) {
            this.sender = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSender() {
            return this.sender;
        }

        public SearchEmail setReceiver(String str) {
            this.receiver = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getReceiver() {
            return this.receiver;
        }

        public SearchEmail setStartTime(String str) {
            this.start_time = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStartTime() {
            return this.start_time;
        }

        public SearchEmail setEndTime(String str) {
            this.end_time = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEndTime() {
            return this.end_time;
        }
    }

    @NamespaceName(name = "SearchFile", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class SearchFile implements InstructionPayload {
        @Required
        private String name;
        private Optional<SearchFileType> type = Optional.empty();
        private Optional<String> start_time = Optional.empty();
        private Optional<String> end_time = Optional.empty();
        private Optional<Long> size = Optional.empty();
        private Optional<FileSizeCompare> size_compare = Optional.empty();
        private Optional<String> path = Optional.empty();

        public SearchFile() {
        }

        public SearchFile(String str) {
            this.name = str;
        }

        @Required
        public SearchFile setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public SearchFile setType(SearchFileType searchFileType) {
            this.type = Optional.ofNullable(searchFileType);
            return this;
        }

        public Optional<SearchFileType> getType() {
            return this.type;
        }

        public SearchFile setStartTime(String str) {
            this.start_time = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getStartTime() {
            return this.start_time;
        }

        public SearchFile setEndTime(String str) {
            this.end_time = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEndTime() {
            return this.end_time;
        }

        public SearchFile setSize(long j) {
            this.size = Optional.ofNullable(Long.valueOf(j));
            return this;
        }

        public Optional<Long> getSize() {
            return this.size;
        }

        public SearchFile setSizeCompare(FileSizeCompare fileSizeCompare) {
            this.size_compare = Optional.ofNullable(fileSizeCompare);
            return this;
        }

        public Optional<FileSizeCompare> getSizeCompare() {
            return this.size_compare;
        }

        public SearchFile setPath(String str) {
            this.path = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPath() {
            return this.path;
        }
    }

    @NamespaceName(name = "SendEmail", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class SendEmail implements InstructionPayload {
        private Optional<String> receiver = Optional.empty();
        private Optional<String> content = Optional.empty();

        public SendEmail setReceiver(String str) {
            this.receiver = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getReceiver() {
            return this.receiver;
        }

        public SendEmail setContent(String str) {
            this.content = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContent() {
            return this.content;
        }
    }

    @NamespaceName(name = "SetUIProperties", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class SetUIProperties implements InstructionPayload {
        private Optional<Boolean> nav_back_from_outside = Optional.empty();
        private Optional<Boolean> show_interaction_number = Optional.empty();

        public SetUIProperties setNavBackFromOutside(boolean z) {
            this.nav_back_from_outside = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNavBackFromOutside() {
            return this.nav_back_from_outside;
        }

        public SetUIProperties setShowInteractionNumber(boolean z) {
            this.show_interaction_number = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isShowInteractionNumber() {
            return this.show_interaction_number;
        }
    }

    @NamespaceName(name = "State", namespace = AIApiConstants.UIController.NAME)
    /* loaded from: classes3.dex */
    public static class State implements ContextPayload {
        @Required
        private String page_id;

        public State() {
        }

        public State(String str) {
            this.page_id = str;
        }

        @Required
        public State setPageId(String str) {
            this.page_id = str;
            return this;
        }

        @Required
        public String getPageId() {
            return this.page_id;
        }
    }
}
