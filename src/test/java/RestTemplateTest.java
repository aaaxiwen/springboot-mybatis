import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.spring.springboot.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sun.xml.internal.stream.Entity;

public class RestTemplateTest extends SpringTestCase {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RedisTemplate redisTemplate;
	
	@Test
	public void test() {
		System.out.println("天气预报");
		Map<String, String> map = new HashMap<>();
		map.put("city", "中山");
		String result=restTemplate.getForObject("https://www.sojson.com/open/api/weather/json.shtml", String.class, map);
		System.out.println(result.toString());
		JSONObject jsonObject =JSON.parseObject(result);
		Iterator<Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, Object> next = iterator.next();
			System.out.println(next.toString());
		}
	}
	
	@Test
	public void test1() {
		System.out.println("万年历");
		Map<String, String> map = new HashMap<>();
		map.put("date", "2018-06-03");
		String result=restTemplate.getForObject("https://www.sojson.com/open/api/lunar/json.shtml", String.class, map);
		System.out.println(result.toString());
		JSONObject jsonObject =JSON.parseObject(result);
//		Weather weather = JSON.parseObject(result,new TypeReference<Weather>() {});
		Iterator<Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, Object> next = iterator.next();
			System.out.println(next.toString());
		}
	}
	
	@Test
    public void test02() {
    	RedisAtomicLong redisAtomicLong = new RedisAtomicLong("somekey", redisTemplate.getConnectionFactory());
    	System.out.println(redisAtomicLong.getExpire());
    	long incrementAndGet = redisAtomicLong.incrementAndGet();
    	System.out.println(incrementAndGet);
    	
    	
    }
}
