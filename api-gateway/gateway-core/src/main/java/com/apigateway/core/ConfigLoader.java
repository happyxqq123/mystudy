package com.apigateway.core;

import com.apigateway.common.util.PropertiesUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final String CONFIG_FILE = "gateway.properties";

    private static final String ENV_PREFIX = "GATEWAY_";

    private static final String JVM_PREFIX = "gateway.";

    private static final ConfigLoader INSTANCE = new ConfigLoader();

    private ConfigLoader(){
    }

    public static ConfigLoader getInstance(){
        return INSTANCE;
    }

    private Config config;

    public static Config getConfig(){
        return INSTANCE.config;
    }

    /**
     * 运行参数 -> jvm参数 -> 环境变量 -->配置文件 --> 配置对象默认值
     * @param args
     * @return
     */
    public Config load(String args[]){
        config = new Config();

        //配置文件
        loadFromConfigFile();

        //环境变量
        loadFromEnv();

        //jvm参数
        loadFromJvm();

        //运行参数
        loadFromArgs();

        return config;
    }

    private void loadFromArgs() {
    }

    private void loadFromJvm() {
    }

    private void loadFromEnv() {
    }

    /**
     * 从文件中加载
     * @return
     */
    private void loadFromConfigFile() {
        InputStream iStream  = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        if(iStream != null){
            Properties properties = new Properties();
            try{
                properties.load(iStream);
                PropertiesUtils.properties2Object(properties,config);
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(iStream != null){
                    try {
                        iStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
