package com.wh.p1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        char[] chars = line.toCharArray();
        int[] nums = new int[26];
        for (char c : chars) {
            int index = c - 'a';
            nums[index] = nums[index]+1;
        }
        int min = 0;
        for (int i : nums) {
            if (i == 0) {
                continue;
            } else {
                min = i;
                min = Math.min(min, i);
            }
        }
        StringBuilder builder = new StringBuilder();
        for (char c : chars) {
            int index = c - 'a';
            if (nums[index] == min && nums[index] != 0) {
                continue;
            } else {
                builder.append((char)(nums[index]+'a'+1));
            }
        }
        System.out.println(builder.toString());
    }
}
