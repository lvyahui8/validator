package org.lyh.validate.example;

import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Properties properties = System.getProperties();
        for (String name : properties.stringPropertyNames()){
            System.out.println(name + " \t\t " + properties.getProperty(name));
        }
    }
}
