package com.bob.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MybatisPlusUtils {

    public static void main(String[] args) {
        String[] models = {"dao","model","api","web","service"};
      //  String moduleName = scanner("模块名");

        for (String model : models) {
            shell(model);
        }
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private static void shell(String model){
       // File file = new File(model);
      //  String path = file.getAbsolutePath();
        String projectPath = System.getProperty("user.dir")+"/"+model;
        //path = path.substring(0, path.lastIndexOf(File.separator));
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath+"/src/main/java");
        gc.setFileOverride(true);
      // gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
       // gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("bob");
        gc.setOpen(false);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
/*        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("I%sServiceImpl");
        gc.setControllerName("%sController");*/
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
     //   dsc.setDbType(DbType.MYSQL);
  /*      dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });*/
        dsc.setUrl("jdbc:mysql://192.168.8.94:3306/apstest?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("apstest");
        dsc.setPassword("!qazxsw@");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.bob");
/*        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("impl");*/
        pc.setModuleName("system");
        mpg.setPackageInfo(pc);



        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[] { "tlog_", "tsys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { "st_user" }); // 需要生成的表

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.example.mybatisplus.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("com.example.mybatisplus.common.BaseController");
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        //strategy.setSuperEntityClass("com.spf.model.Entity");
        // 自定义实体，公共字段
        //strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        //strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        //strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        //strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuliderModel(true);
        mpg.setStrategy(strategy);


        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
        /*        Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);*/
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
//    focList.add(new FileOutConfig("/template/list.jsp.vm") {
//       @Override
//       public String outputFile(TableInfo tableInfo) {
//          // 自定义输入文件名称
//          return "D://my_" + tableInfo.getEntityName() + ".jsp";
//       }
//    });
//    cfg.setFileOutConfigList(focList);
//    mpg.setCfg(cfg);

        // 调整 xml 生成目录演示
        if("dao".equals(model)){
            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                            + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });
            cfg.setFileOutConfigList(focList);
        }

        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录 {"dao","model","api","web","service"};
        TemplateConfig tc = new TemplateConfig();
        if ("dao".equals(model)) {
            tc.setController(null);
            tc.setEntity(null);
            tc.setService(null);
            tc.setServiceImpl(null);
        } else if ("model".equals(model)) {
            tc.setController(null);
            tc.setService(null);
            tc.setServiceImpl(null);
            tc.setMapper(null);
            tc.setXml(null);
        }  else if ("service".equals(model)) {
            tc.setController(null);
            tc.setMapper(null);
            tc.setXml(null);
            tc.setEntity(null);
            tc.setService(null);
        }  else if ("api".equals(model)) {
            tc.setController(null);
            tc.setMapper(null);
            tc.setXml(null);
            tc.setEntity(null);
            tc.setServiceImpl(null);
        }  else if ("web".equals(model)) {
            tc.setMapper(null);
            tc.setXml(null);
            tc.setService(null);
            tc.setServiceImpl(null);
            tc.setEntity(null);
        }
        mpg.setTemplate(tc);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
       // System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}