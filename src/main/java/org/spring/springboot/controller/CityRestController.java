package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        return cityService.findCityByName(cityName);
    }
    @GetMapping("/api/addcity")
    public City addCity(City city) {
    	return cityService.addCity(city);
    }
    @GetMapping("/api/updatecity")
    public City updateCity(City city) {
    	return cityService.updateCity(city);
    }
    @Async
    @GetMapping("/api/deletecity")
    public String delete(String cityName) {
    	cityService.deleteCityByCityName(cityName);
		return "success";
    	
    }

}
