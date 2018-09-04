package lesson3;

import lesson2.Array;

public class Methods {

    public static boolean checkIsValidBrackets(String s){
        Stack stack = new Stack(s.length());
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '<' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                stack.push(s.charAt(i));
            } else {
                boolean isEmpty = stack.isEmpty();
                switch (s.charAt(i)){
                    case ')':
                        if(!isEmpty && stack.peek() == '('){
                            stack.pop();
                            break;
                        }
                        else return false;
                    case '>':
                        if(!isEmpty && stack.peek() == '<'){
                            stack.pop();
                            break;
                        }
                        else return false;
                    case '}':
                        if(!isEmpty && stack.peek() == '{'){
                            stack.pop();
                            break;
                        }
                        else return false;
                    case ']':
                        if(!isEmpty && stack.peek() == '['){
                            stack.pop();
                            break;
                        }
                        else return false;
                }
            }

        }
        return stack.isEmpty();
    }

    public static String reversWrite(String s){
        Stack stack = new Stack(s.length());
        for (int i = 0; i < s.length(); i++) {
           stack.push(s.charAt(i));
        }
        String out = "";
        while (!stack.isEmpty()){
            out += (char)stack.pop();
        }
        return out;
    }
}
