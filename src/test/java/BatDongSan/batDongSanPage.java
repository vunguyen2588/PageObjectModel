//package BatDongSan;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//public class batDongSanPage {
//	
//	public static void getDetailsInformationPage() {
//		System.out.println("CHU DE: " + driver.findElement(By.xpath("//div[@class='pm-title']/h1")).getText());
//		System.out.println("KHU VUC: " + driver.findElement(By.xpath("//span[@class='diadiem-title mar-right-15']")).getText());
//		System.out.println("GIA: " + driver.findElement(By.xpath("//span[@class='gia-title mar-right-15']/strong")).getText());
//		System.out.println("DIEN TICH: " + driver.findElement(By.xpath("//span[@class='gia-title']/strong")).getText());
//		System.out.println("Mo ta: " + driver.findElement(By.xpath("//div[contains(@class, 'pm-content')]")).getText());
//	}
//
//	public static void main(String[] args) {
//		public static WebDriver driver = new ChromeDriver();
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\driver\\chromedriver.exe");
//		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//		driver.get("https://batdongsan.com.vn/ban-nha-rieng/p1");
//		List<WebElement> titles = driver.findElements(By.xpath("//div[@class='p-title']//a"));
//		for (int i=0; i<titles.size(); i++) {
//			titles.get(i).click();
//			getDetailsInformationPage();
//			driver.navigate().back();
//		}
//		driver.quit();
//	}
//}