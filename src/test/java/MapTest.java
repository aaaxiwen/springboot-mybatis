import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapTest extends SpringTestCase {
	
	@Test
	public void test() {
		Map<String, Integer> map = new HashMap<>();
		
		map.put("测试", 1);
		map.put("测试", 2);
		
		
		Integer integer = map.get("测试");
		Integer integer1 = map.get("测试");
		
		System.out.println(integer+" "+integer1);
	}
	@Test
	public void test1() {
		System.out.println(new Date().getTime());//这个就是时间戳   
		long a = 1293072805l*1000;
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddhhmmss");
		System.out.println(sdf.format(new Date(a)));
	}
}
