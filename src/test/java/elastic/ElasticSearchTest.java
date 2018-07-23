package elastic;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.springboot.Application;
import org.spring.springboot.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ElasticSearchTest {
	
	@Autowired
	JestClient jestClient;
	
	//构建
	@Test
	public void test() {
		//需要在实体类中标识JestId 或者构建时候标识
		City city = new City();
		city.setCityName("中山市");
		city.setDescription("诚信中山，幸福中山");
		city.setId((long) 1);
		city.setProvinceId((long) 1);
		
		Index index = new Index.Builder(city).index("地图").type("市级").build();
		try {
			//执行
			jestClient.execute(index);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//查询
	@Test
	public void test1() {
		String json = "{\r\n" + 
				"  \"query\": { \r\n" + 
				"    \"bool\": { \r\n" + 
				"      \"must\": [\r\n" + 
				"        { \"match\": { \"cityName\":   \"中山市\"        }}, \r\n" + 
				"        { \"match\": { \"description\": \"中山\" }}  \r\n" + 
				"      ],\r\n" + 
				"      \"filter\": [ \r\n" + 
//				"        { \"term\":  { \"status\": \"published\" }}, \r\n" + 
//				"        { \"range\": { \"publish_date\": { \"gte\": \"2015-01-01\" }}} \r\n" + 
				"      ]\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}\r\n" + 
				"\r\n"; 
		System.out.println(json);
		Search search = new Search.Builder(json).addIndex("地图").addType("市级").build();
		try {
			SearchResult searchResult = jestClient.execute(search);
			System.out.println(searchResult.getJsonString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
