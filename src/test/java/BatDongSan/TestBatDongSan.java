package BatDongSan;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBatDongSan {
	public WebDriver driver;

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void getAllPage() throws Exception {
		String transtype = "transtype";
		String bds = "bds";

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String dbURL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433";
		Connection conn = DriverManager.getConnection(dbURL, "Dot_Crawler",
				"$serv1c3cr4wl3r%");

		String SPUrl = "EXEC [dbo].[Transtype_Sel] ?,?";
		PreparedStatement ps = conn.prepareStatement(SPUrl);
		ps.setEscapeProcessing(true);
		ps.setQueryTimeout(5);
		ps.setString(1, transtype);
		ps.setString(2, bds);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int itranstype = Integer.parseInt(rs.getString("transtype"));
			String iurl = rs.getString("url");
			 System.out.println(itranstype + " : " + iurl);

			getPage(itranstype, iurl);
		}
	}

	// Getting page
	private void getPage(int transtype, String url) throws Exception {
		List<WebElement> record;
		int iStop = 0;
		
		for (int iUrl = 1; iUrl <= 100; iUrl++) {
			if(iStop > 0) {
				break;
			} else {
				driver.get(url + "/p" + iUrl);
				for (int i = 0; i < 20; i++) {
					if(iStop > 0) {
						break;
					} else {
						record = driver.findElements(By.xpath("//div[@class='p-title']//a"));
						record.get(i).click();
						new WebDriverWait(driver, 50).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
						System.out.println(driver.getCurrentUrl());
						iStop = writeToDatabase(transtype);
						driver.navigate().back();
						new WebDriverWait(driver, 50).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
					}
				}
			}
		}

	}

	// Get detail information
	private int writeToDatabase(int transtype) throws Exception {
		String title = getTitle();
//		String city = null;
//		String district = null;
//		String addressDetails = null;
//		float price = 0;
//		String unitPrice = null;
		float area = getArea();
		String transDesc = getTransactionDescription();
		String direction = getDirection();
		String balcony = getBalcony();
		int bedRoom = getBedRoom();
		int toilet = getToilet();
		String furniture = getFurniture();
		String contactPerson = getContactPerson();
		String phoneContact = getPhoneContact();
		String email = getEmail();
		String addressContact = getAddressContact();
//		String transDate = getTransactionDate();
		Date importDate = null;
		String transCode = getTransactionCode();
		String url = getURL();
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String dbURL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433";
		Connection conn = DriverManager.getConnection(dbURL, "Dot_Crawler",
				"$serv1c3cr4wl3r%");

		try {
			CallableStatement cs = conn
					.prepareCall("{call BDS_TransImport_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setEscapeProcessing(true);
			cs.setQueryTimeout(5);
			cs.setInt(1, transtype);
			cs.setString(2, title);
			cs.setString(3, getAddress().get(0));
			cs.setString(4, getAddress().get(1));
			cs.setString(5, getAddress().get(2));
			if(!getPrice().get(0).contains("Th")) {
				cs.setFloat(6, Float.parseFloat(getPrice().get(0)));
				cs.setString(7, getPrice().get(1));
			} else {
				cs.setFloat(6, 0);
				cs.setString(7, null);
			}
			
			cs.setFloat(8, area);
			cs.setString(9, transDesc);
			cs.setString(10, direction);
			cs.setString(11, balcony);
			cs.setInt(12, bedRoom);
			cs.setInt(13, toilet);
			cs.setString(14, furniture);
			cs.setString(15, contactPerson);
			cs.setString(16, phoneContact);
			cs.setString(17, email);
			cs.setString(18, addressContact);
			cs.setTimestamp(19, getTransactionDate());
			cs.setDate(20, (java.sql.Date) importDate);
			cs.setString(21, transCode);
			cs.setString(22, url);
			cs.registerOutParameter(23, java.sql.Types.INTEGER);
			cs.executeUpdate();
			int iStop = Integer.parseInt(cs.getString(23));
//			 System.out.println("==============" + cs.getString(23));
			return iStop;
		} catch (SQLException e) {
			e.printStackTrace();
			return 100;
		}
	}

	// 	Getting direction
	private String getDirection() {
		String direction = null;
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		if(driver.findElements(By.xpath("//div[@id='LeftMainContent__productDetail_direction']/div[2]")).size()==1) {
			direction = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_direction']/div[2]")).getText();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return direction;
	}
	
	// 	Getting balcony
	private String getBalcony() {
		String balcony = null;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		if(driver.findElements(By.xpath("//div[@id='LeftMainContent__productDetail_balcony']/div[2]")).size()==1) {
			balcony = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_balcony']/div[2]")).getText();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return balcony;
	}
	
	// 	Getting furniture
	private String getFurniture() {
		String funiture;
		try {
			funiture = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_interior']/div[2]")).getText();
		} catch (NoSuchElementException e) {
			funiture = null;
		}
		return funiture;
	}
	
	//	Getting price
	private List<String> getPrice() {
		String price = driver.findElement(By.xpath("(//span[contains(@class, 'gia-title')]/strong)[1]")).getText().trim();
		List<String> priceList = Arrays.asList(price.split(" "));
		return priceList;
	}
	
	// Getting title
	private String getTitle() throws Exception {
		String title = driver.findElement(By.xpath("//div[@class='pm-title']/h1")).getText();
		return title;
	}

	// Getting city, district and address detail
	private List<String> getAddress() {
		String allAddress = driver.findElement(By.xpath("(//div[@class='table-detail']/div[2]/div[@class='right'])[1]")).getText();
		List<String> addressList = Arrays.asList(allAddress.split(", "));
		String city = addressList.get(addressList.size()-1);
		String district = addressList.get(addressList.size()-2);
		String addressDetail = allAddress.replace(city,"").replace(district,"").replace(", ,","").trim();
		List<String> addresses = Arrays.asList(city, district, addressDetail) ;
		return addresses;
	}

	// Getting email
	private String getEmail() {
		String email;
		try {
			email = driver.findElement(By.xpath("//div[@id='contactEmail']//a[@rel='nofollow']")).getText();
		} catch (NoSuchElementException e) {
			email = null;
		}
		return email;
	}

	// Getting area
	private float getArea() {
		float area;
		try {
			String strArea = driver.findElement(By.xpath("//span[@class='gia-title']/strong")).getText();
			if(strArea.contains("Kh")) {
				area = -1;
			} else {
				area = Float.parseFloat(strArea.replaceAll("m²", ""));
			}
		} catch (NoSuchElementException e) {
			area = -1;
		}
		return area;
	}

	// Getting Transaction Description
	private String getTransactionDescription() {
		String transactionDescription = driver
				.findElement(
						By.xpath("//div[contains(@class, 'pm-content')]/div[@class='pm-desc']"))
				.getText();
		// System.out.println(transactionDescription);
		return transactionDescription;
	}

	// Getting Bed Room
	private int getBedRoom() {
		int bedRoom;
		try {
			bedRoom = Integer.parseInt(driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_roomNumber']/div[2]")).getText().replaceAll("[^0-9]", ""));
		} catch (NoSuchElementException e) {
			bedRoom = 0;
		}
		return bedRoom;
	}

	// Getting Toilet
	private int getToilet() {
		int toilet;
		try {
			toilet = Integer.parseInt(driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_toilet']/div[2]")).getText().replaceAll("[^0-9]", ""));
		} catch (NoSuchElementException e) {
			toilet = 0;
		}
		return toilet;
	}

	// Getting Contact Person
	private String getContactPerson() {
		String contactPerson = null;
		if(driver.findElements(By.xpath("//div[@id='LeftMainContent__productDetail_contactName']/div[@class='right']")).size()==1) {
			contactPerson = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_contactName']/div[@class='right']")).getText();
		}
//		// System.out.println(contactPerson);
		return contactPerson;
	}

	// Getting Phone Contact
	private String getPhoneContact() {
		String phoneContact = driver
				.findElement(
						By.xpath("//div[@id='LeftMainContent__productDetail_contactMobile']/div[@class='right']"))
				.getText();
		// System.out.println(phoneContact);
		return phoneContact;
	}

	// Getting Address Contact
	private String getAddressContact() {
		String addressContact = driver
				.findElement(
						By.xpath("(//div[@class='div-table']//div[@class='right'])[2]"))
				.getText();
		// System.out.println(addressContact);
		return addressContact;
	}

	// Getting Transaction Code
	private String getTransactionCode() {
		String transCode = driver.findElement(
				By.xpath("(//span[@class='normalblue']/parent::div)[1]/div"))
				.getText();
		// System.out.println(transCode);
		return transCode;
	}

	// Getting URL
	private String getURL() {
		String url = driver.getCurrentUrl();
		// System.out.println(url);
		return url;
	}

	// Getting Transaction Date
	private Timestamp getTransactionDate() throws Exception {
		String transDate = driver.findElement(By.xpath("(//span[@class='normalblue']/parent::div)[3]")).getText().substring(11);
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Timestamp ts = new Timestamp(((java.util.Date)df.parse(transDate)).getTime());
		return ts;
	}
}