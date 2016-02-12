package com.facebook.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class AutomatePosts 
{
	//log variables
	static final private Logger LOGGER = Logger.getLogger( AutomatePosts.class.getName() );
	static final private String APPENDER_MAIN = "AutomatePosts.main";
	
	//class variables
	static final private String CONFIGURATION_PROPERTIES = "config.properties";
	static final private String TARGET_URL = "https://www.facebook.com/";
	
	public static void main( String[] args ) {
    	
		Properties properties = loadConfigurationProperties();    	
    	
    	WebDriver driver = new FirefoxDriver();
    	
    	driver.manage().window().maximize(); 
    	LOGGER.info( APPENDER_MAIN + "[EVENT]: browser window is maximized" );
    	    	
        driver.get( TARGET_URL );
        LOGGER.info( APPENDER_MAIN + "[EVENT]: target url requested" );
        
        //look at file src/main/resources/config.properties
        //change email password in this file
        final String email = properties.getProperty( "email" );
        driver.findElement( By.id( "email" )).sendKeys( email );
        LOGGER.info( APPENDER_MAIN + "[EVENT]: username/email entered" );
        
        final String password = properties.getProperty( "password" );
        driver.findElement( By.id("pass") ).sendKeys( password );
        LOGGER.info( APPENDER_MAIN + "[EVENT]: password entered" );
                
        driver.findElement( By.id( "loginbutton" ) ).click();
        LOGGER.info( APPENDER_MAIN + "[EVENT]: login button clicked" );
        
        LOGGER.info( APPENDER_MAIN + "[SLEEP]: 4 seconds for the page to load" );
        try {
			Thread.sleep( 4000 );
		} catch ( InterruptedException e ) {
			LOGGER.severe( APPENDER_MAIN + "[InturreptedException]: thread pause failed" );
			e.printStackTrace();
		}
        LOGGER.info( APPENDER_MAIN + "[EVENT]: logged in, on facebook home page");
        
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[2]/div[1]/div/div/div/div/div[1]/ul/li[1]/a/div/span")).click();
        LOGGER.info( APPENDER_MAIN + "[EVENT]: link to profile clicked");
        
        LOGGER.info( APPENDER_MAIN + "[SLEEP]: 4 seconds for the page to load" );
        try {
			Thread.sleep( 4000 );
		} catch ( InterruptedException e ) {
			LOGGER.severe( APPENDER_MAIN + "[InturreptedException]: thread pause failed" );
			e.printStackTrace();
		}
        LOGGER.info( APPENDER_MAIN + "[EVENT]: profile page loaded" );
        
        //object to perform actions on the webpage, tab to the post text box on page load
        Actions actions = new Actions(driver);
        
        LOGGER.info( APPENDER_MAIN + "[EVENT]: tabbing begins");
        actions.sendKeys(Keys.TAB).perform();
        
        //search bar expands on this tab so let the code sleep for 2 sec
        //jvm<<ajax booyah
        actions.sendKeys(Keys.TAB).perform();
        
        LOGGER.info( APPENDER_MAIN + "[SLEEP]: 2 seconds for the search bar to load" );
        try {
			Thread.sleep( 2000 );
		} catch ( InterruptedException e ) {
			LOGGER.severe( APPENDER_MAIN + "[InturreptedException]: thread pause failed" );
			e.printStackTrace();
		}
        LOGGER.info( APPENDER_MAIN + "[EVENT]: drop down loaded" );
        
        //close drop down 
        actions.sendKeys(Keys.ESCAPE).perform();
        
        //continue tabbing through the page elements
        //takes 24 tabs to get to the text area and make a post
        for(int i = 1; i <= 24; i++) {
        	actions.sendKeys(Keys.TAB).perform();
        }
        
        //get your sample post from the properties file and set it in the text area
        final String post = properties.getProperty( "post" );
        actions.sendKeys(post).perform();
        
        LOGGER.info( APPENDER_MAIN + "[SLEEP]: 2 seconds for the 'Post' button to load" );
        try {
			Thread.sleep( 2000 );
		} catch ( InterruptedException e ) {
			LOGGER.severe( APPENDER_MAIN + "[InturreptedException]: thread pause failed" );
			e.printStackTrace();
		}
        LOGGER.info( APPENDER_MAIN + "[EVENT]: 'Post' button loaded" );
        
        //find the button using xpath and click
        driver.findElement( By.xpath( "/html/body/div[1]/div[2]/div[1]/div/div[2]/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/li/div/div/div/div/div/div[2]/div/div[4]/div[2]/div/div[2]/div/button" ) ).click();
        
        LOGGER.info( APPENDER_MAIN + "[EVENT] post completed");
        
    }

	private static Properties loadConfigurationProperties() {
		
		InputStream input = null;
		Properties properties = new Properties();

    	try {
    		
    		input = AutomatePosts.class.getClassLoader().getResourceAsStream( CONFIGURATION_PROPERTIES );    		
    		properties.load( input );
    		LOGGER.info( "AutomatePosts.loadConfigurationProperties:LOAD_COMPLETED" );
    	
    	} catch ( IOException e ) {
    		
    		LOGGER.severe( "App.loadConfigurationProperties[IOException]: properties file not loaded" );
    		e.printStackTrace();
    	
    	} finally {
    		
    		if ( input != null ) {
    			try {    				
    				input.close();    			
    			} catch ( IOException e ) {    				
    				LOGGER.severe( "App.loadConfigurationProperties[IOException]: inputstream not closed" );
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	return properties;
	}
}
