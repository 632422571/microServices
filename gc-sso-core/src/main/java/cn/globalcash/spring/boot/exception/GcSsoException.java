package cn.globalcash.spring.boot.exception;

/**
 * @author: gh
 * @date:2019/6/9
 */
public class GcSsoException extends RuntimeException {
    private static final long serialVersionUID = 4538302636488275103L;

    public GcSsoException (String msg) {
        super(msg);
    }

    public GcSsoException (String msg,Throwable cause) {
        super(msg,cause);
    }

    public GcSsoException (Throwable cause) {
        super(cause);
    }
}
