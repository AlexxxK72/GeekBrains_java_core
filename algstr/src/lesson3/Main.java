package lesson3;

public class Main {
    public static void main(String[] args) {

        String s = "((a+1)*(b-2))";
        System.out.println(s + " - isValid :" + Methods.checkIsValidBrackets(s));
        s = "[((a+1)*(b-2))]";
        System.out.println(s + " - isValid :" + Methods.checkIsValidBrackets(s));
        s = "]((a+1)*(b-2))]";
        System.out.println(s + " - isValid :" + Methods.checkIsValidBrackets(s));
        s = "<d{{f(()f)[[f]f]}}>";
        System.out.println(s + " - isValid :" + Methods.checkIsValidBrackets(s));
        System.out.println();
        System.out.println("" + " - reverseWrite :" + Methods.reversWrite(""));
        System.out.println("Мама мыла раму" + " - reverseWrite :" + Methods.reversWrite("Мама мыла раму"));
        System.out.println("Java" + " - reverseWrite :" + Methods.reversWrite("Java"));
    }
}
