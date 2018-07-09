package com.finance.recyclerviewdemo;

/**
 * Created by Jackie on 2018/7/9.
 */
public class TestTotalNum {

    static int[] arry = {-5,-1,0,5,9,11,12,15,20,35,46};

    public static void main(String[] args){

        calculate(arry,23);
    }

    public static int[] calculate(int[] arr,int num){
        int start = 0;
        int end = arr.length - 1;
        if (arr == null || arr.length < 2 || arr[start] > num){
            return null;
        }
        for (int i = 0;i<arr.length - 1;i++){
            if (arr[start] + arr[end] == num){
                System.out.println("-----"+start+"------"+end);
                return new int[]{start,end};
            }
            if (arr[start] + arr[end] > num){
                end--;
                continue;
            }else{
                start++;
                continue;
            }
        }
        return null;
    }


}
