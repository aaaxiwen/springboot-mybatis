import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.springboot.Application;
import org.spring.springboot.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class springDateRedisTest  {
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	@Qualifier("redisTemplate")
	RedisTemplate redisTemplate;
	@Autowired
	@Qualifier("jsonRedisTemplate")
	RedisTemplate<Object, Object> jsonRedisTemplate;
	
	@Test
	public void test01() {
		Weather weather = new Weather();
		weather.setCity("中山");
		weather.setAqi("我是xiwen");
		jsonRedisTemplate.opsForValue().set("weatherjson", weather);
		redisTemplate.opsForValue().set("weatherjdk", weather);
	}
	
}
