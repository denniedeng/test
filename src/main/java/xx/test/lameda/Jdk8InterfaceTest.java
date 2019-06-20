package xx.test.lameda;

public interface Jdk8InterfaceTest {
    default void method1(){
        System.out.println("this is a default method content!");
    }

    static void mtthod2(){
        System.out.println("this is a static method in interface. oh, My god!!!");
    }
}
