package com.wh.p1;

import java.util.EnumMap;
import java.util.Map;

public class A9 {
    static final int A = 1 << 0; // 1
    static final int B = 1 << 0; // 2
    static final int C = 1 << 0; // 4
    static final int D = 1 << 0; // 8

    void apply(int a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
        new A9().apply(D | A); // 1
    }

    enum Phase {
        SOLID,LIQUID,GAS;
        enum Transition {
            MELT(SOLID, LIQUID),
            FREEZE(LIQUID, SOLID);
            private final Phase src;
            private final Phase dest;
            Transition(Phase src, Phase dest) {
                this.src = src;
                this.dest = dest;
            }
            private static final Map<Phase, Map<Phase, Transition>> m =
                    new EnumMap<Phase, Map<Phase, Transition>>(Phase.class);
            static {
                for (Phase p : Phase.values()) {
                    m.put(p, new EnumMap<Phase, Transition>(Phase.class));
                }
                for (Transition t : Transition.values()) {
                    m.get(t.src).put(t.dest, t);
                }
            }
            static Transition from(Phase src, Phase dest) {
                return m.get(src).get(dest);
            }
        }
    }
}
