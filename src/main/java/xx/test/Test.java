package xx.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static long getMinute(String ts) throws ParseException{
		long lt = new Long(ts);
		lt = System.currentTimeMillis();
		Date date = new Date(lt);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String res = simpleDateFormat.format(date);
		Date date2 = simpleDateFormat.parse(res);
		return date2.getTime();
    }
	
	public static void main(String[] args) throws ParseException {
		String ts = "1490942090132";
		long str = getMinute(ts);
		System.out.println(str);
	}
}
