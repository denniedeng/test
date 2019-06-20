package xx.test.lameda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LamedaMain {
    public static void main(String[] args) {

        List<String> list= Arrays.asList("danny","fancial","join","jack1");

        Predicate<String> predicate = (n)->n.startsWith("j");
        Predicate<String> predicate1 = (n)->n.length()==5;

        System.out.println("====print start with j");
        list.stream().filter(predicate).forEach(System.out::println);

        System.out.println("=====print length is 5");
        list.stream().filter(predicate1).forEach(System.out::println);

        System.out.println("=======print start with j and length is 5");
        list.stream().filter(predicate.and(predicate1)).forEach(System.out::println);

        System.out.println("=======print start with j or length is 5");
        list.stream().filter(predicate.or(predicate1)).forEach(System.out::println);

        System.out.println("=======show may operation");
        List<Double> doubleList = Arrays.asList(100.0,200.0,300.0,400.0);
        doubleList.stream().map(n->n*0.2).forEach(System.out::println);

        System.out.println("========show reduce operation");
        System.out.println("sum is "+doubleList.stream().map(n->n*0.2).reduce((sum,n)->sum+n).get());

        List<Integer> integerList = Arrays.asList(100,200,300,400);
        System.out.println("max is "+integerList.stream().reduce(Integer::max).get());
        System.out.println("min is "+integerList.stream().reduce(Integer::min).get());

        System.out.println("sum is "+integerList.stream().reduce(Integer::sum).get());

        List<Integer> intList2 = Arrays.asList(1,2,2,3,3,3,4,5,6,6,6,6);
        int sum = intList2.stream().distinct().reduce(0,(x,y)->x+y);
        int max = intList2.parallelStream().distinct().reduce(Integer::max).get();
        List<Integer> distinctIntList = intList2.stream().parallel().distinct().collect(Collectors.toList());
        System.out.println("distinct list is " + distinctIntList);
        System.out.println("distinct max is " + max);
        System.out.println("distinct sum is "+ sum);
    }
}
