package org.fourthline.cling.support.messagebox.model;

import org.fourthline.cling.support.messagebox.model.Message;
import org.fourthline.cling.support.messagebox.parser.MessageElement;

/* loaded from: classes5.dex */
public class MessageSMS extends Message {
    private final String body;
    private final DateTime receiveTime;
    private final NumberName receiver;
    private final NumberName sender;

    public MessageSMS(NumberName numberName, NumberName numberName2, String str) {
        this(new DateTime(), numberName, numberName2, str);
    }

    public MessageSMS(DateTime dateTime, NumberName numberName, NumberName numberName2, String str) {
        this(Message.DisplayType.MAXIMUM, dateTime, numberName, numberName2, str);
    }

    public MessageSMS(Message.DisplayType displayType, DateTime dateTime, NumberName numberName, NumberName numberName2, String str) {
        super(Message.Category.SMS, displayType);
        this.receiveTime = dateTime;
        this.receiver = numberName;
        this.sender = numberName2;
        this.body = str;
    }

    public DateTime getReceiveTime() {
        return this.receiveTime;
    }

    public NumberName getReceiver() {
        return this.receiver;
    }

    public NumberName getSender() {
        return this.sender;
    }

    public String getBody() {
        return this.body;
    }

    @Override // org.fourthline.cling.support.messagebox.model.ElementAppender
    public void appendMessageElements(MessageElement messageElement) {
        getReceiveTime().appendMessageElements(messageElement.createChild("ReceiveTime"));
        getReceiver().appendMessageElements(messageElement.createChild("Receiver"));
        getSender().appendMessageElements(messageElement.createChild("Sender"));
        messageElement.createChild("Body").setContent(getBody());
    }
}
