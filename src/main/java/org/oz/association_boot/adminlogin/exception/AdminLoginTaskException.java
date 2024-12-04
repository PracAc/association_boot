package org.oz.association_boot.adminlogin.exception;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginTaskException extends RuntimeException {

    private int status;
    private String msg;

    public AdminLoginTaskException(final int status, final String msg) {
        super(status +"_" + msg);
        this.status = status;
        this.msg = msg;
    }

}