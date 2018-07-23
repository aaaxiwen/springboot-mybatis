package org.spring.springboot.service.impl;

import org.spring.springboot.dao.CityDao;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

/**
 * 城市业务逻辑实现类
 *
 * Created by bysocket on 07/02/2017.
 */
@Service
@CacheConfig(cacheManager="cityCacheManager")//指定使用cityCacheManager 也可以写在下面方法里  也 可以不写使用默认
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    /**
     * CacheManager管理多个Cache组件的，每个缓存组件都有一个唯一名字
     * 几个属性
     *         cacheName/value:指定缓存组件的名字 ，可以指定多个
     *         key:缓存数据使用的key:可以用他来指定。默认是方法的参数
     *         #root.args[0] #a0 可以写sqEL  #result
     *         cacheManager：指定缓存管理器，或者cacheResolver指定获取解析器（理解不透的）
     *         condition:指定符合条件的情况才缓存 支持sqEL  condition="#cityName != null"
     *         unless：否定缓存 跟上面相反 nuless = "#result == null"
     *         (@Cacheable的key不能用result，因为key的生成不会等方法跑完，不然缓存就没意义了，
     *         @CachePut 的key是可以用result的，因为跑完就缓存返回结果)
     * **/
    
    //http://localhost:8080/api/city?cityName=中山市 测试用
    @Cacheable(cacheNames="city",key="#cityName")
    @Override
    public City findCityByName(String cityName) {
    	System.out.println("查询数据库");
        return cityDao.findByName(cityName);
    }
    
	@Override
	public City addCity(City city) {
		 Long id =cityDao.addCity(city);
		 city.setId(id);
		return city;
	}
	//http://localhost:8080/api/updatecity?id=2&description=333&cityName=中山市 测试用
	@CachePut(cacheNames="city",key="#result.cityName")
	@Override
	public City updateCity(City city) {
		System.out.println("更新数据库");
		cityDao.updateCity(city);
		return city;
	}

	
	
	@Override
	public void deleteCity(String id) {
		cityDao.deleteCity(id);
		
	}
	//http://localhost:8080/api/deletecity?cityName=中山市 可以测试
	@CacheEvict(value = "city",key="#cityName")
	@Override
	public  void deleteCityByCityName(String cityName) {
		System.out.println("就当删除了吧");
	}

	
    
}
