package org.spring.springboot.service;

import org.spring.springboot.domain.City;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;



/**
 * 城市业务逻辑接口类
 *
 * Created by bysocket on 07/02/2017.
 */
public interface CityService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName
     */
	
    City findCityByName(String cityName);
    
    public City addCity(City city);
    
   
    public City updateCity(City city);
    
    public void deleteCity(String id);
    
    
    public  void deleteCityByCityName(String cityName);
}
