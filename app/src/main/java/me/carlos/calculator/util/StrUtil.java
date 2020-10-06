package me.carlos.calculator.util;

public class StrUtil {

    public static String formatScreenText(String str){
        StringBuilder formatted = new StringBuilder();
        int count = 0;
        int begin = str.length() - 1;
        if(isFloat(str)){
            while (str.charAt(begin) != '.'){
                begin--;
            }
        }
        for (int i = begin; i >= 0; i--) {

            formatted.insert(0, str.charAt(i));
            count++;
            if(count >= 3){
                formatted.insert(0, ",");
                count = 0;
            }
        }
        if(formatted.charAt(0) == ',') {
            formatted.delete(0, 1);
        }
        return formatted.toString();
    }

    public static String cleanScreenText(String str){
        StringBuilder cleaned = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char curChar = str.charAt(i);
            if (curChar == '.' && justZeros(i, str)) {
                break;
            }
            if(curChar != ','){
                cleaned.append(curChar);
            }
        }
        return cleaned.toString();
    }

    private static boolean justZeros(int begin, String str){
        for(int i = begin + 1; i < str.length(); i++){
            if(str.charAt(i) != '0'){
                return false;
            }
        }
        return true;
    }

    private static boolean hasNextCharacter(int i, String str){
        return i + 1 < str.length();
    }

    public static boolean isFloat(String str){
        for(char c : str.toCharArray()){
            if(c == '.'){
                return true;
            }
        }
        return false;
    }

}
