import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.springboot.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class redisCacheTest {
	@Qualifier("jsonCacheManager")
	@Autowired
	RedisCacheManager jsonCacheManager;
	
	@Test
	public void test() {
		org.springframework.cache.Cache city = jsonCacheManager.getCache("city");
		 city.put("key","测试2");
		 org.springframework.cache.Cache city1 = jsonCacheManager.getCache("city1");
		 city1.put("key","测试21");
		
	}
}
