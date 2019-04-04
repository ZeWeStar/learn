package com.shanxin.utils.httpClientUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyUtil {	
	
	public static String getPropertyByName(String name) {
		return getPropertyByName(propertyPath, name);
	} 
	
	private static final String propertyPath = "config/shanxin_custom.properties";
	private static String getPropertyByName(String path, String name) 
	{  
        String result = "";          
        InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream(path);  
        
        Properties prop = new Properties();  
        try 
        {  
            prop.load(in);  
            result = prop.getProperty(name).trim();  
        }
        catch (IOException e) 
        {              
            e.printStackTrace();  
        }  
        
        return result;  
    }  
}
