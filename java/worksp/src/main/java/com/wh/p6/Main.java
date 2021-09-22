package com.wh.p6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>();
        input.add("usr/a1/a2/a3");
        input.add("usr/a1/b2/b3/b4");
        input.add("usr/aa1/bb1/b3/b4");
        input.add("home/u1/u11");
        input.add("home/u2");
        input.add("a1/a2/a3");
        input.stream().forEach(System.out::println);

        Collections.sort(input, ((o1, o2) -> o1.compareTo(o2)));
        Node root = new Node("/", -1);
        for (String str : input) {
            String[] arr = str.split("/");
            for(int i=0;i<arr.length;i++) {
                Node temp = new Node(arr[i], i);
                Node pre = Node.getPreNode(i,temp,arr,root);
                temp.pre = pre;
                pre.next.add(temp);
            }
        }

    }


    static class Node {
        String val;
        int level;
        Node pre;
        List<Node> next;
        Node(String val, int level) {
            this.val = val;
            this.level = level;
        }
        static Node getPreNode(int level, Node n, String[] arr, Node root) {
            return null;
        }
        static void print(Node n) {
            System.out.println(n);
            n.next.stream().forEach(Node::print);
        }
    }
}
