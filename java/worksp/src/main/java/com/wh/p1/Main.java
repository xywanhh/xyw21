package com.wh.p1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = Integer.valueOf(sc.nextLine());
        String numsString = sc.nextLine();
        String[] numTemp = numsString.split(" ");
        List<Integer> nums = Arrays.stream(numTemp)
                .map(Integer::valueOf).collect(Collectors.toList());
        // A = B + 2C
        Collections.sort(nums);
        int[] result = new int[3];
        boolean find = false;
        for (int i=0;i<nums.size()-2;i++) {
            int A = 2*nums.get(i)+nums.get(i+1);
            if (nums.contains(A) && A >= nums.get(i+2)) {
                result[0] = A;
                result[1] = nums.get(i+1);
                result[2] = nums.get(i);
                find = true;
                break;
            }
        }
        if (!find) {
            System.out.println(0);
        } else {
            System.out.println(result[0]+" " + result[1]+" " + result[2]);
        }
    }
}
