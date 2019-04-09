import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class GenforxcxTest {
	@Test
    public void generateCode() {
        String packageName = "changxiao.hejiahuan.xcx";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName, "hjh_news","hjh_paragraph");//表名
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://localhost:3306/hejiahuan";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("root")
                .setDriverName("com.mysql.jdbc.Driver")
                .setTypeConvert(new MySqlTypeConvert(){
                    // 自定义数据库表字段类型转换【可选】
                    @Override
                    public DbColumnType processTypeConvert(String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                        return super.processTypeConvert(fieldType);
                    }
                });
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)//全局大写命名 oracle注意
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)//表名生成策略 驼峰
                .setInclude(tableNames)//修改替换成你需要的表名，多个表名传数组
                .setSuperMapperClass("changxiao.hejiahuan.common.config.MyMapper")
                .setSuperServiceClass("changxiao.hejiahuan.common.service.IService")
                .setSuperServiceImplClass("changxiao.hejiahuan.common.service.impl.BaseService");
        config.setActiveRecord(false)
                .setAuthor("萧喜文")
                .setOutputDir("d:\\codeGen\\xcx-18V")
                .setFileOverride(true)
                .setActiveRecord(false)//不需要ActiveRecord特性的请改为false
                .setEnableCache(false)//xml 二级缓存
        		.setBaseResultMap(true)//xml ResultMap
        		.setBaseColumnList(false);
        		//xml columList
        		// .setKotlin(true) 是否生成 kotlin 代码 这都能生成这么牛逼
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                                .setMapper("dao")
                                
                ).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}
