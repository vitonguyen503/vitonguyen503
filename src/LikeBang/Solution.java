package LikeBang;

import java.sql.SQLOutput;

public class Solution {
    public static void main(String[] args) {
        int arr[] = {12, 17, 70, 15, 22, 65, 21, 90};
        arr = seperateArray(arr);
        for(int i : arr)
            System.out.print(i + " ");
    }

    private static int[] seperateArray(int arr[]){
        int l = 0, r = arr.length - 1;
        while(l <= r){
            while(l <= r && arr[l] % 2 == 0)
                l++;
            while(r >= l && arr[r] % 2 == 1)
                r--;
            if(l <= r){
                int tmp = arr[l];
                arr[l] = arr[r];
                arr[r] = tmp;
            }
        }
        return arr;
    }
}
