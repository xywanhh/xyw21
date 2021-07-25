package com.wh.p1;

import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public class B10 implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date start;
    private Date end;

    private B10(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    private static class B10Proxy implements Serializable {
        private static final long serialVersionUID = 2L;
        private Date start;
        private Date end;

        B10Proxy(B10 b) {
            this.start = b.start;
            this.end = b.end;
        }
        private Object readResolve() {
            return new B10(start, end);
        }
    }

    private Object writeReplace() {
        return new B10Proxy(this);
    }
    private void readObject(ObjectInputStream stream)
            throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
}