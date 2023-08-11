import java.util.HashMap;
import java.util.Map;

class Solution {
    public int romanToInt(String s) {
        Map<Character, Integer> dict = new HashMap<>();
        dict.put('I', 1); dict.put('V', 5); dict.put('X', 10); dict.put('L', 50); dict.put('C', 100); dict.put('D', 500); dict.put('M', 1000);
        int res = 0;
        for(int i = 0; i < s.length(); i++){
            int val = dict.get(s.charAt(i));
            res += val;
            if(i > 0){
                int prevVal = dict.get(s.charAt(i - 1));
                res = (prevVal < val) ? res - prevVal * 2 : res;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.romanToInt("MCMXCIV"));
    }
}