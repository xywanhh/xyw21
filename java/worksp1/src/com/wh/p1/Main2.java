package com.wh.p1;

import java.util.Scanner;

public class Main2 {
    private static String[][] data = null;
    private static int[][] mark = null;
    private static String result = null;
    private static String work = null;
    private static int n;
    private static int m;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            result="NO";
            n = sc.nextInt();
            m = sc.nextInt();
            work = sc.next();
            mark = new int[n][m];
            data = new String[n][m];
            for (int i = 0; i < n; i++) {
                // 这里直接将单词分开存入二位数组中
                data[i] = sc.next().split("");
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (!data[i][j].equals(String.valueOf(work.charAt(0)))) {
                        continue;
                    } else {
                        find(i, j, 0);
                    }
                }
            }
            System.out.println(result);
        }
    }

    public static void find(int i, int j, int f) {
        if (i < 0 || i >= n || j < 0 || j >= m || f >= work.length()
                || mark[i][j] == 1 || !data[i][j].equals(String.valueOf(work.charAt(f)))) {
            return;
        } else if (f == work.length() - 1 && String.valueOf(work.charAt(f)).equals(data[i][j])) {
            result = "YES";
            return;
        }
        mark[i][j] = 1;
        find(i-1,j,f+1);
        find(i+1,j,f+1);
        find(i,j-1,f+1);
        find(i,j+1,f+1);
        mark[i][j] = 0;
    }
}