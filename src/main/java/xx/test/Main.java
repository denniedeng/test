package xx.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Main {
	private static int TOPNUM = 30000 * 10000;

	/**
	 * 分析转换分钟数
	 * 
	 * @param ts
	 * @return
	 */
	public static long getMinute(String ts) throws ParseException {
		long lt = new Long(ts);
		Date date = new Date(lt);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String res = simpleDateFormat.format(date);
		Date date2 = simpleDateFormat.parse(res);
		return date2.getTime();
	}

	public static boolean isNumber(String str) {
		boolean re = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				re = false;
				break;
			}
		}
		return re;
	}

	public static void main(String[] args) throws ParseException {
		Scanner in = new Scanner(System.in);
		String numberStr;
		int number = 0;
        if (in.hasNext()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
        	numberStr = in.next();
            if(!isNumber(numberStr)) {
            	System.out.println("the param is error");
            	in.close();
            	return ;
            }
            
            number = Integer.valueOf(numberStr);
            if(number<0 || number>TOPNUM) {
            	System.out.println("the number is over " + TOPNUM);
            	in.close();
            	return ;
            }
        }
        String[] array = new String[number];
        Map<Long,Long[]> result = new TreeMap<>();
    	for(int i=0;i<number;i++) {
    		if(in.hasNext())
    			array[i] = in.next();
    	}
    	in.close();
    	
    	for(String str : array) {
//    		System.out.println(str);
    		String[] arr = str.split(",");
    		String ts = arr[0];
    		String valueStr = arr[1];
    		if(!isNumber(valueStr)) {
    			System.out.println("error param!");
    			return ;
    		}
    		Long minute = getMinute(ts);
    		Long value = Long.valueOf(valueStr);
    		Long[] valueArray = result.get(minute);
    		if(valueArray==null) {
    			valueArray = new Long[3];
    			valueArray[0] = value;		//max
    			valueArray[1] = value;		//min
    			valueArray[2] = value;		//total
    		}
    		else {
    			if(valueArray[0]<value)
    				valueArray[0] = value;
    			
    			if(valueArray[1] > value)
    				valueArray[1] = value;
    			
    			valueArray[2] = valueArray[2] + value;
    		}
    		
    		result.put(minute, valueArray);		//回填数据
    	}
    	
    	System.out.println(result.size());
    	Set<Long> keyset = result.keySet();
    	for(long key : keyset) {
    		Long[] content = result.get(key);
    		System.out.println(key+","+content[0]+","+content[1]+","+content[2]);
    	}
	}

}
