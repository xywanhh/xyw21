package com.wh.p1;

import com.sun.prism.RenderTarget;
import jdk.nashorn.internal.ir.annotations.Reference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.List;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface ANN {}

public class A10 {
    public static void main(String[] args) {
        Date start = new Date();
        Date end = new Date();
        In1 n1 = new In1(start,end);
        end.setYear(2011); // n1的值也跟着变

    }
    static class In1 {
        private final Date start;
        private final Date end;
        public In1(Date start, Date end) {
            this.start = start;
            this.end = end;
        }
        public Date start() {return start;}
        public Date end() {return end;}
    }
    static class In2 {
        private final Date start;
        private final Date end;
        public In2(Date start, Date end) {
            this.start = new Date(start.getTime()); // 保护性拷贝
            this.end = new Date(end.getTime()); // 保护性拷贝
            if (this.start.compareTo(this.end) > 0) {
                throw new IllegalArgumentException(start+" after " + end);
            }
        }
        public Date start() {
            return new Date(start.getTime()); // 保护性拷贝
        }
        public Date end() {
            return new Date(end.getTime()); // 保护性拷贝
        }
    }
    void f1(int a, List l1) {
        assert a != 0;
        assert l1 != null;
    }
}
