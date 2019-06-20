package xx.test.lameda;

public class Jdk8InterImp implements Jdk8InterfaceTest{
    public static void main(String[] args) {
        Jdk8InterfaceTest interfaceTest = new Jdk8InterImp();
        interfaceTest.method1();
        Jdk8InterfaceTest.mtthod2();
    }

    @Override
    public void method1(){
        System.out.println("override default method:method1 in impl class");
    }
}
