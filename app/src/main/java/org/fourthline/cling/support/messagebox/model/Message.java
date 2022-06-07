package org.fourthline.cling.support.messagebox.model;

import com.blankj.utilcode.constant.PermissionConstants;
import java.util.Random;
import org.fourthline.cling.support.messagebox.parser.MessageDOM;
import org.fourthline.cling.support.messagebox.parser.MessageDOMParser;
import org.fourthline.cling.support.messagebox.parser.MessageElement;
import org.seamless.xml.DOM;
import org.seamless.xml.ParserException;

/* loaded from: classes5.dex */
public abstract class Message implements ElementAppender {
    private final Category category;
    private DisplayType displayType;
    private final int id;
    protected final Random randomGenerator;

    /* loaded from: classes5.dex */
    public enum Category {
        SMS(PermissionConstants.SMS),
        INCOMING_CALL("Incoming Call"),
        SCHEDULE_REMINDER("Schedule Reminder");
        
        public String text;

        Category(String str) {
            this.text = str;
        }
    }

    /* loaded from: classes5.dex */
    public enum DisplayType {
        MINIMUM("Minimum"),
        MAXIMUM("Maximum");
        
        public String text;

        DisplayType(String str) {
            this.text = str;
        }
    }

    public Message(Category category, DisplayType displayType) {
        this(0, category, displayType);
    }

    public Message(int i, Category category, DisplayType displayType) {
        this.randomGenerator = new Random();
        this.id = i == 0 ? this.randomGenerator.nextInt(Integer.MAX_VALUE) : i;
        this.category = category;
        this.displayType = displayType;
    }

    public int getId() {
        return this.id;
    }

    public Category getCategory() {
        return this.category;
    }

    public DisplayType getDisplayType() {
        return this.displayType;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.id == ((Message) obj).id;
    }

    public int hashCode() {
        return this.id;
    }

    public String toString() {
        try {
            MessageDOMParser messageDOMParser = new MessageDOMParser();
            MessageDOM messageDOM = (MessageDOM) messageDOMParser.createDocument();
            MessageElement createRoot = messageDOM.createRoot(messageDOMParser.createXPath(), "Message");
            createRoot.createChild("Category").setContent(getCategory().text);
            createRoot.createChild("DisplayType").setContent(getDisplayType().text);
            appendMessageElements(createRoot);
            return messageDOMParser.print((DOM) messageDOM, 0, false).replaceAll("<Message xmlns=\"urn:samsung-com:messagebox-1-0\">", "").replaceAll("</Message>", "");
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
    }
}
