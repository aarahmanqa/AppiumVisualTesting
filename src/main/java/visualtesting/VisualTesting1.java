package visualtesting;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.imagecomparison.OccurrenceMatchingOptions;
import io.appium.java_client.imagecomparison.OccurrenceMatchingResult;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class VisualTesting1 {	

	AppiumDriver iDriver  = null;
	
	
	public static IOSDriver<IOSElement> initIOSDriverForMobile()
	{
		IOSDriver<IOSElement> iDriver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();		
		capabilities.setCapability("udid", "cc6080c50dad4605d8e5511e89410792f0100026");
		capabilities.setCapability("bundleId", "com.tekion.sales");
		capabilities.setCapability("wdaLocalPort", "8101");
		capabilities.setCapability("platformVersion","12.1");
		capabilities.setCapability("noReset", false);		
		capabilities.setCapability("useNewWDA", true); //This will remove and install the WebDriverAgent.
		capabilities.setCapability("automationName", "XCUITest");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("deviceName", "iPhone 7 Plus");		
		capabilities.setCapability("newCommandTimeout",300); //It specifies how much time appium needs to wait			
		capabilities.setCapability("waitForQuiescence", false);
		capabilities.setCapability("useJSONSource", true);		
		try {			
			System.out.println("Launching IOSDriver...");
			iDriver = new IOSDriver<IOSElement>(new URL("http://0.0.0.0:4723/wd/hub"),capabilities);			
			iDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		return iDriver;
	}
	
	public static AppiumDriver initIOSDriverForSimulator()
	{
		AppiumDriver iDriver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();		
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("deviceName", "iPhone 7 Plus");
		capabilities.setCapability("bundleId", "com.tekion.sales");
		capabilities.setCapability("automationName", "XCUITest");		
		capabilities.setCapability("platformVersion","11.4");
		try {			
			System.out.println("Launching IOSDriver...");
			iDriver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"),capabilities);			
			iDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		return iDriver;
	}
	
	@BeforeTest
	public void beforeTest() {
		iDriver = initIOSDriverForMobile();
	}


	@Test(enabled=true)
	public void wholeImageComparison() {

		try {			
			File image1 = new File("src/test/resources/image1.jpg");
			File image2 = new File("src/test/resources/image2.jpg");			
			SimilarityMatchingResult res = iDriver.getImagesSimilarity(image1, image2, new SimilarityMatchingOptions().withEnabledVisualization());					
			System.out.println("Get Score for image 1 - "+res.getScore());
			res.storeVisualization(new File("src/test/resources/result.jpg"));			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Test(enabled=true)
	public void partialImageComparison() {
		try {			
			File image1 = new File("src/test/resources/image1.jpg");
			File partialImage = new File("src/test/resources/image1_jpg.png");
			
			OccurrenceMatchingResult res1 = iDriver.findImageOccurrence(image1, partialImage, new OccurrenceMatchingOptions().withEnabledVisualization());
			res1.storeVisualization(new File("src/test/resources/occurenceResult.jpg"));
			System.out.println("Height - " + res1.getRect().getHeight());
			System.out.println("Wdith - " + res1.getRect().getWidth());
		}catch(Throwable t) {
			t.printStackTrace();
		}
	}
}
