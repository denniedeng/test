package xx.test;

import java.util.Arrays;

public class PrintTest {
	public static void main(String[] args) {
		String[] str = new String[] {"a","b","c"};
//		String str2 = new String(str);
		test("a");
	}
	
	public static void test(String... str) {
		System.out.println(Arrays.toString(str));
	}
}
