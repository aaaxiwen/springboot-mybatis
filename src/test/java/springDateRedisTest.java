import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.spring.springboot.domain.City;
import org.spring.springboot.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import com.alibaba.fastjson.JSON;

import ch.qos.logback.core.net.SyslogOutputStream;



public class springDateRedisTest extends SpringTestCase  {
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	@Qualifier("redisTemplate")
	RedisTemplate redisTemplate;
	@Autowired
	@Qualifier("jsonRedisTemplate")
	RedisTemplate<Object, Object> jsonRedisTemplate;
	
	/*
	 * redisTemplate.opsForValuse  简单key-value
	 * 				 opsForHash    hash 
	 * */
	
	
	@Test
	public void test01() {
		Weather weather = new Weather();
		weather.setCity("中山");
		weather.setAqi("我是xiwen");
		jsonRedisTemplate.opsForValue().set("weatherjson", weather);
		redisTemplate.opsForValue().set("weatherjdk", weather);
		
		
	}
	
	
	
	
	
    
    public String test02() {
    	RedisAtomicLong redisAtomicLong = new RedisAtomicLong("somekey", redisTemplate.getConnectionFactory());
//    	redisAtomicLong.set(0l);  设置值
//    	System.out.println(redisAtomicLong.getExpire());
    	long incrementAndGet = redisAtomicLong.incrementAndGet();
    	System.out.println(incrementAndGet);
    	return "ok";
    	
    }
    private static int AAA = 0;
    
    public String test021() {
    	
    	
    	redisTemplate.opsForValue().set("AAA", AAA+1);
    	AAA = (int) redisTemplate.opsForValue().get("AAA");
    	System.out.println(AAA);
		return "ok";
    	
    }
    public static boolean tryGetDistributedLock(RedisTemplate jsonRedisTemplate,String lockKey, String requestId, int expireTime) {
    	if (jsonRedisTemplate.hasKey(lockKey)) {
			return false;
		}
    	jsonRedisTemplate.opsForValue().set(lockKey, requestId, expireTime,TimeUnit.SECONDS );
    	return true;
    }
    
    @Test
    public void test03() {
    	ExecutorService executorService = Executors.newCachedThreadPool();
    	for(int i=0;i<10;i++) {
    		CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(()->test021(), executorService);//多线程异步跑 结果 11111111
    		this.test021();//单线程跑结果 12345678910
    	}
    }
	@Test
    public void test04() {
		//jsonRedisTemplate使用了json格式解析 所以key值如果是字符串都是带有“”的 int就不带双引号   使用stringRedisTemplate是不带“”的  我感觉stringRedisTemplate这个好操作
		//如果使用了springboot 的cache 整合 jsonRedisTemplate  前面加了前缀 保存结果是  msg:"5,7"
		jsonRedisTemplate.opsForValue().set("msg:(5,6)", "测试");
		jsonRedisTemplate.opsForValue().set("msg:(6,8)", "测试");
		Set<Object> keys = jsonRedisTemplate.keys("msg:"+"*"+"6"+"*");
		for(Object key : keys){
			System.out.println(key.toString());
		}
	}
	@Test
	public void test05() {
		Set<String> keys = stringRedisTemplate.keys("msg:"+"*"+"5"+"*");
		stringRedisTemplate.opsForValue().set("msg:(5,6)", "测试");
		for(Object key : keys){
			System.out.println(key.toString());
		}
	}
	@Test
	public void test06() {
		City city = new City();
		city.setCityName("中山");
		city.setDescription("测试");
		stringRedisTemplate.opsForList().leftPush("msg:\"50,60\"", JSON.toJSONString(city));
		stringRedisTemplate.opsForList().leftPush("msg:\"50,60\"", JSON.toJSONString(city));
		List<String> range = stringRedisTemplate.opsForList().range("msg:\"50,60\"", 4, 6);
		range.stream().forEach(t->System.out.println(t));
	}
	@Test
	public void test07() {
		City city = new City();
		city.setCityName("中山");
		city.setDescription("测试");
		jsonRedisTemplate.opsForList().leftPush("msg:\"50,60\"", city);
		jsonRedisTemplate.opsForList().leftPush("msg:\"50,60\"", city);
	}
	// redis的  scan操作   迭代方式 取key    和keys语法有区别
	@Test
	public void test08() {
		RedisConnection connection = jsonRedisTemplate.getConnectionFactory().getConnection();
		ScanOptions options = ScanOptions.scanOptions().match("msg*").build();
		Cursor<byte[]> scan = connection.scan(options);
		while(scan.hasNext()) {
			byte[] next = scan.next();
			String string = new String(next,StandardCharsets.UTF_8);
			System.out.println(string);
		}
		scan.getPosition(); //光标位置
	}
    
    
    /**
     * 获取唯一Id
     * @param key
     * @param hashKey
     * @param delta 增加量（不传采用1）
     * @return
     * @throws BusinessException
     */
	
    public Long incrementHash(String key,String hashKey,Long delta){
        try {
            if (null == delta) {
                delta=1L;
            }
            return redisTemplate.opsForHash().increment(key, hashKey, delta);
        } catch (Exception e) {//redis宕机时采用uuid的方式生成唯一id
            int first = new Random(10).nextInt(8) + 1;
            int randNo=UUID.randomUUID().toString().hashCode();
            if (randNo < 0) {
                randNo=-randNo;
            }
            return Long.valueOf(first + String.format("%16d", randNo));
        }
    }
}
