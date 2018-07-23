import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FastjsonTest extends SpringTestCase {
	
	
	
	
	
	
	
	@Test
	public void test() {
		Map<String, Object> map = new HashMap<>();
		map.put("时间", new Date());
		String jsonString = JSON.toJSONString(map);
		System.out.println(jsonString);//{"时间":1528678386952} 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		map.put("时间sdf", sdf.format(new Date()));
//		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
		String jsonString2 = JSON.toJSONString(map);
		
		System.out.println(jsonString2);//{"时间":1528678722060,"时间sdf":"2018-06-11 08:58:42"}
		
		JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd hh:mm:ss");//json有提供的api
		
		
		
		
		
	}
}
