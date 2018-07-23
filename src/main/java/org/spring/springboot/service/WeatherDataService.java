package org.spring.springboot.service;

import org.spring.springboot.domain.WeatherResponse;

public interface WeatherDataService {
    WeatherResponse getDataByCityId(String cityId);

    WeatherResponse getDataByCityName(String cityName);
    
    public void dotestredisbeen();
}
