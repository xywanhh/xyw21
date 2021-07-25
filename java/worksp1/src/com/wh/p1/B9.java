package com.wh.p1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

public abstract class B9 {
    private int x, y;

    // this enum and field are used to track initialization
    private enum State {NEW, INITIALIZING, INITIALIZED}
    private final AtomicReference<State> init =
            new AtomicReference<>(State.NEW);

    public B9(int x, int y) {initialize(x, y);}

    // this constructor and the following method allow
    // subclass's readObject method to initialize our state
    protected B9() {}
    protected final void initialize(int x, int y) {
        if (!init.compareAndSet(State.NEW, State.INITIALIZING)) {
            throw new IllegalStateException("Already initialized");
        }
        this.x = x;
        this.y = y;
        // do anything else the original constructor did
        init.set(State.INITIALIZED);
    }
    // these methods provide access to internal state to it can
    // be manually serialized by subclass's writeobject method
    protected final int getX() {checkInit(); return x;}
    protected final int getY() {checkInit(); return y;}
    // must call from all public and protected instance methods
    private void checkInit() {
        if (init.get() != State.INITIALIZED) {
            throw new IllegalStateException("Uninitialized");
        }
    }
}

// 实现一个可序列化的子类
class B99 extends B9 implements Serializable {
    private static final long serialVersionUID = 1856835860954L;
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        // manually deserialize and initialize superclass state
        int x = s.readInt();
        int y = s.readInt();
        initialize(x, y);
    }
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        // manually serialize superclass state
        s.writeInt(getX());
        s.writeInt(getY());
    }

    // constructor does not use the fancy mechanism
    public B99(int x, int y) {super(x, y);}
}
