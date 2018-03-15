import AnnoTest.AfterSuite;
import AnnoTest.BeforeSuite;
import AnnoTest.Test;

public class Calculator {

    @BeforeSuite
    public static void before(){
        System.out.println("Старт теста!");
    }

    @Test(priority = 10)
    public static void add(int a, int b){
        System.out.println(a + b);
    }

    @Test
    public static void sub(int a, int b){
        System.out.println(a - b);
    }

    @Test(priority = 7)
    public static void mul(int a, int b){
        System.out.println(a * b);
    }

    @Test(priority = 7)
    public static void div(int a, int b){
        System.out.println(a / b);
    }

    @AfterSuite
    public static void after(){
        System.out.println("Конец теста!");
    }

}
