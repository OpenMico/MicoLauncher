package com.xiaomi.accountsdk.account.exception;

/* loaded from: classes2.dex */
public class InvalidPhoneOrTicketException extends Exception {
    public InvalidPhoneOrTicketException() {
        super("wrong ticket or unactivated phone");
    }

    public InvalidPhoneOrTicketException(String str) {
        super(str);
    }
}
