package BatDongSan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBatDongSan {
	WebDriver driver;
	String jdbc = "jdbc:sqlserver://103.101.163.224:1433;databaseName=bodb_Source";
	String userName = "Dot_Crawler";
	String passWord = "$serv1c3cr4wl3r%";
	String chromeLocal = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
	String chromeServer = "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe";
	Connection conn;

	private String strTitleList = "//h3[@class='product-title']//a";
	//	private String strTransactionCode = "(//span[@class='normalblue']/parent::div)[1]/div";
	private String strTransactionCode = "//span[@class='sp1' and (.)='Mã tin:']/following-sibling::span[@class='sp3']";
	//private String strTitle = "//div[@class='pm-title']/h1";
	private String strTitle = "//h1[@class='tile-product']";
	//	private String strTransactionDescription = "//div[contains(@class, 'pm-content')]/div[@class='pm-desc']";
	private String strTransactionDescription = "//div[@class='des-product']";
	//	private String strHiddenMobileDes = "//div[@class='pm-desc']//span[contains(@class, 'hidden-phone hidden-mobile detail')]";
	private String strHiddenMobileDes = "//div[@class='des-product']//span[contains(@class, 'hidden-phone hidden-mobile des')]";
	//	private String strPhoneContact = "//div[@id='LeftMainContent__productDetail_contactMobile']//span[contains(@class, 'border-phone')]";
	private String strPhoneContact = "//div[contains(@class,'phone')]/span[contains(@class,'phoneEvent')]";
	//	private String strDiaChi = "(//div[@class='div-table']//div[@class='right'])[2]";
	private String strDiaChi = "//span[text()='Địa chỉ:']/following-sibling::span";
	//	private String strPrice = "(//span[contains(@class, 'gia-title')]/strong)[1]";
	private String strPrice = "//span[text()='Mức giá:']/following-sibling::span";
	//	private String strTransactionDate = "(//span[@class='normalblue']/parent::div)[3]";
	private String strTransactionDate = "//span[text()='Ngày đăng:']/following-sibling::span";
	//	private String contactPersonXpath = "//div[contains(text(),'Tên liên lạc')]/following-sibling::div";
	private String contactPersonXpath = "//div[@class='user']//div[@class='name']";
	//	private String emailXpath = "//div[@id='contactEmail']//a[@rel='nofollow']";
	private String emailXpath = "//a[@id='email']";

	@Before
	public void setup() throws Exception {
		conn = getConnection();
		ChromeOptions chromeOptions= new ChromeOptions();
		chromeOptions.setBinary(chromeServer);
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\driver\\chromedriver.exe");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() {
//		driver.quit();
	}

	// ==============================================================================================================================	
	@Test
	public void getAllPage() throws Exception {
		String transtype = "transtype";
		String bds = "bds";

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
		int iStop = 0;
		try {
			List<WebElement> record;
			for (int iUrl = 0; iUrl <= 10000; iUrl++) {
				if(iStop==2) {
					break;
				}
				iStop = 0;
				if(iStop > 0) {
					break;
				} else {
					System.out.println(url + "/p" + iUrl);
					driver.get(url + "/p" + iUrl);
					for (int i = 0; i < 20; i++) {
						if(iStop > 0) {
							break;
						} else {
							List<WebElement> titleEles = driver.findElements(By.xpath(strTitleList));
							if(titleEles.size()!=0) {
								record = driver.findElements(By.xpath(strTitleList));
								if(record.size() > 0) {
									record.get(i).click();
									new WebDriverWait(driver, 50).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
									System.out.println(driver.getCurrentUrl());
									iStop = writeToDatabase(transtype);
									System.out.println("=======" + iStop);
									if(iStop==0) {
										driver.navigate().back();
										new WebDriverWait(driver, 50).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
									} else {
										break;
									}
								} else {
									iStop = 1;
								}
							} else {
								List<WebElement> notFoundEles = driver.findElements(By.xpath("//div[@id='LeftMainContent__productSearchResult_pnlNotFound']"));
								if(notFoundEles.size()==1) {
									CallableStatement cs = conn.prepareCall("{call BDS_TaskProcessUpd(?,?)}");
									cs.setEscapeProcessing(true);
									cs.setQueryTimeout(5);
									cs.setInt(1, transtype);
									cs.setString(2, "bds");
									cs.executeUpdate();
									System.out.println("!End reading !");
									iStop = 2;
								} else {
									Thread.sleep(5000);	
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("e.getMessage()");
			System.out.println(e.getMessage());
			CallableStatement cs = conn.prepareCall("{call BDS_TaskProcessUpd(?,?)}");
			cs.setEscapeProcessing(true);
			cs.setQueryTimeout(5);
			cs.setInt(1, transtype);
			cs.setString(2, "bds");
			cs.executeUpdate();
			System.out.println("End reading !");
			iStop = 1;
		}
		
	}

	// Get detail information
	private int writeToDatabase(int transtype) throws Exception {
		String transCode = getTransactionCode(transtype);
		if(transCode!=null) {
			String title = getTitle();
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
			Date importDate = null;
			String url = getURL();		
			
			try {
				CallableStatement cs = conn.prepareCall("{call BDS_TransImport_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cs.setEscapeProcessing(true);
				cs.setQueryTimeout(5);
				
				cs.setInt(1, transtype);
				cs.setString(2, title);
//				cs.setString(3, getAddress());
				cs.setString(3, getAddressContact());

				if(!getPrice().get(0).contains("Th")) {
					cs.setFloat(4, Float.parseFloat(getPrice().get(0)));
					cs.setString(5, getPrice().get(1));
				} else {
					cs.setFloat(4, 0);
					cs.setString(5, null);
				}
				
				cs.setFloat(6, area);
				cs.setString(7, transDesc);
				cs.setString(8, direction);
				cs.setString(9, balcony);
				cs.setInt(10, bedRoom);
				cs.setInt(11, toilet);
				cs.setString(12, furniture);
				cs.setString(13, contactPerson);
				cs.setString(14, phoneContact);
				cs.setString(15, email);
				cs.setString(16, addressContact);
				cs.setTimestamp(17, getTransactionDate());
				cs.setDate(18, (java.sql.Date) importDate);
				cs.setString(19, transCode);
				cs.setString(20, url);
				cs.registerOutParameter(21, java.sql.Types.INTEGER);
				
				cs.executeUpdate();
				int iStop = Integer.parseInt(cs.getString(21));
	
				return iStop;
			} catch (SQLException e) {
				System.out.println("Test");
				e.printStackTrace();
				return 100;
			}
		} else {
			return 100;
		}
	}

	// ==============================================================================================================================	
	// Getting data unit
	// Getting connection
	private Connection getConnection() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection(jdbc, userName, passWord);
		return conn;
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
		String price = driver.findElement(By.xpath(strPrice)).getText().trim();
		List<String> priceList = Arrays.asList(price.split(" "));
		System.out.println("priceList = " + priceList);
		return priceList;
	}
	
	// Getting title
	private String getTitle() throws Exception {
		String title = driver.findElement(By.xpath(strTitle)).getText();
		return title;
	}

	// Getting address detail
	private String getAddress() {
		String address = driver.findElement(By.xpath("(//div[@class='table-detail']/div[2]/div[@class='right'])[1]")).getText();
		return address;
	}

	// Getting email
	private String getEmail() {
		String email;
		try {
			email = driver.findElement(By.xpath(emailXpath)).getAttribute("data-email");
			System.out.println(email);
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
		List<WebElement> eles = null;

		if (driver.findElements(By.xpath(strHiddenMobileDes)).size() > 0) {
			eles = driver.findElements(By.xpath(strHiddenMobileDes));
			for (int i=0; i < eles.size(); i++) {
				eles.get(i).click();
			}
		}
//		System.out.println("=====================" + eles.size());
//		if(iHiddenMobile > 0) {
//			for (int i=0; i < iHiddenMobile; i++) {
//				driver.findElements(By.xpath("//span[@class='hidden-phone hidden-mobile detail']")).get(i+1).click();
//			}
//		}
		String transactionDescription = driver.findElement(By.xpath(strTransactionDescription)).getAttribute("innerText");
//		System.out.println("transactionDescription = " + transactionDescription);
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
		if(driver.findElements(By.xpath(contactPersonXpath)).size()>0) {
			contactPerson = driver.findElement(By.xpath(contactPersonXpath)).getText();
		}
		return contactPerson;
	}

	// Getting Phone Contact
	private String getPhoneContact() {
//		String phoneContact = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_contactMobile']/div[@class='right']")).getText();
		String phoneContact = driver.findElement(By.xpath(strPhoneContact)).getAttribute("raw");
		return phoneContact;
	}

	// Getting Address Contact
	private String getAddressContact() {
		String addressContact = driver.findElement(By.xpath(strDiaChi)).getText();
		return addressContact;
	}

	// Getting Transaction Code
	private String getTransactionCode(int transtype) throws Exception {
		try {
			String transactionCodeXpath = strTransactionCode;
			WebDriverWait wait = new WebDriverWait(driver, 180);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(transactionCodeXpath)));
			String transCode = driver.findElement(By.xpath(transactionCodeXpath)).getText();
			CallableStatement cs = conn.prepareCall("{call BDS_CheckTranscodeExists(?,?,?)}");
			cs.setEscapeProcessing(true);
			cs.setQueryTimeout(5);
			cs.setInt(1, transtype);
			cs.setString(2, transCode);
			cs.registerOutParameter(3, java.sql.Types.BOOLEAN);
			cs.executeUpdate();
			Boolean iVerify = cs.getBoolean(3);
			System.out.println(transCode);
			System.out.println(iVerify);
			if(iVerify)
				return null;
			else 
				return transCode;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("======= Waiting too long !");
			CallableStatement cs = conn.prepareCall("{call BDS_TaskProcessUpd(?,?)}");
			cs.setEscapeProcessing(true);
			cs.setQueryTimeout(5);
			cs.setInt(1, transtype);
			cs.setString(2, "bds");
			cs.executeUpdate();
			System.out.println("End reading !");
//			iStop = 1;
			return null;
		}
	}

	// Getting URL
	private String getURL() {
		String url = driver.getCurrentUrl();
		return url;
	}

	// Getting Transaction Date
	private Timestamp getTransactionDate() throws Exception {
		String transDate = driver.findElement(By.xpath(strTransactionDate)).getText();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp ts = new Timestamp(((java.util.Date)df.parse(transDate)).getTime());
		return ts;
	}
}