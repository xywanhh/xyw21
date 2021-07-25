package com.wh.p1;

import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;

class SomeException extends Exception {
    SomeException(String msg) {
        super(msg);
    }
}
public class B1 {
    public static void main(String[] args) {
        try {
            f1();
        } catch (SomeException ex) {
            System.out.println(ex.getMessage()); // error
        }
    }
    static void f1() throws SomeException {
        throw new SomeException("error");
    }
}

class A {
    public void f1() throws IOException {
        throw new IOException("111");
    }

    public static void main(String[] args) {
        A[] arr = new A[2];
        arr[0] = new A();
        arr[1] = new B();
        for (A a : arr) {
            try {
                a.f1();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    static class B extends A {
//            @Override
//    public void f1() throws FileNotFoundException {
//                throw new FileNotFoundException("bb");
//            }; // allow

        @Override
        public void f1() throws NullPointerException, IOError {
            throw new NullPointerException("22");
        }; // not allow

//        @Override
//        public void f1() {
//            System.out.println("bb");
//        }; // allow
    }
}
