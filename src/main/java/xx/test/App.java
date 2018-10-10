package xx.test;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//    	int[] a = new int[]{4,5,6,7,5,6,8}; 
//    	int[] b = new int[]{4,5,6}; 
//    	int res1 = sub(a,b);
//    	System.out.println(res1);
    	
    	String strA = "123456789123456789123456789";
    	String strB = "123456789123456789123456";
//    	String strA = "12345";
//    	String strB = "12345";
    	String res2 = add(strA, strB);
    	System.out.println(res2);
    	
//    	String numberA = "123";
//    	char[] arrA = numberA.toCharArray();
//    	char[] newArrA = revertArr(arrA);
    }
    
    //method one
    public static int sub(int[] a,int[] b) {
    	boolean[] resultBoolean = new boolean[b.length];
    	for(int i=0;i<b.length;i++) {
    		int bCell = b[i];
    		for(int k=0;k<a.length;k++) {
    			int aCell = a[k];
    			if(aCell==bCell) {
    				resultBoolean[i]=true;
    				break;
    			}
    		}
    	}
    	
    	for(int i=0;i<resultBoolean.length;i++) {
    		if(!resultBoolean[i])
    			return -1;
    	}
    	
    	
    	StringBuffer bString = new StringBuffer();
    	StringBuffer aString = new StringBuffer();
    	for(int i=0;i<a.length;i++)
    		aString.append(a[i]);
    	
    	for(int i=0;i<b.length;i++)
    		bString.append(b[i]);
    	
    	
    	return aString.toString().lastIndexOf(bString.toString());
    }
    
    //method 2
    public static String add(String numberA,String numberB) {
    	if(!isNumber(numberA) || !isNumber(numberB))
    		return "ERROR";
    	
    	char[] arrA = numberA.toCharArray();
    	char[] arrB = numberB.toCharArray();
    	
    	char[] newArrA = revertArr(arrA);
    	char[] newArrB = revertArr(arrB);
    	int maxlength = arrA.length>arrB.length?arrA.length:arrB.length;
    	int minlength = arrA.length>arrB.length?arrB.length:arrA.length; 
    	int[] result = new int[maxlength];
    	for(int i=0;i<minlength;i++) {
    		int a = Integer.valueOf(String.valueOf(newArrA[i]));
    		int b = Integer.valueOf(String.valueOf(newArrB[i]));
    		result[i] = result[i]+((a+b)%10);
    		if((a+b)>=10)
    			result[i+1]=1;
    	}
    	
    	result = revertArr(result);
    	
    	if(maxlength!=minlength) {
    		for(int i=0;i<(maxlength-minlength);i++)
        		if(arrA.length>arrB.length)
        			result[i]=Integer.valueOf(String.valueOf(arrA[i]));
        		else
        			result[i]=Integer.valueOf(String.valueOf(arrB[i]));
    	}
    	
    	StringBuffer resultStr = new StringBuffer();
    	for(int i=0;i<result.length;i++)
    		resultStr.append(result[i]);
    	
    	return resultStr.toString();
    	
    }
    
    public static char[] revertArr(char[] str) {
    	char[] newChar = new char[str.length];
    	for(int i=str.length-1;i>=0;i--) {
    		newChar[i]=str[str.length-i-1];
    	}
    	return newChar;
    }
    
    public static int[] revertArr(int[] str) {
    	int[] newChar = new int[str.length];
    	if(str.length>1) {
    		for(int i=str.length-1;i>=0;i--) {
        		newChar[i]=str[str.length-i-1];
        	}
    		return newChar;
    	}
    	else
    		return str;
    	
    }
    
    public static boolean isNumber(String str) {
    	char[] arr = str.toCharArray();
    	for(int i=0;i<str.length();i++) {
    		String s = String.valueOf(arr[i]);
    		if(s.equals("0")||s.equals("1")||s.equals("2")||s.equals("3")||s.equals("4")||s.equals("5")||s.equals("6")||s.equals("7")||s.equals("8")||s.equals("9"))
    			return true;
    		else 
    			return false;
    	}
    	
    	return true;
    }
}
