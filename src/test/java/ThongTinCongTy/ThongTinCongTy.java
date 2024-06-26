package ThongTinCongTy;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ThongTinCongTy {
	WebDriver driver;
//	String jdbc = "jdbc:sqlserver://103.101.163.224:1433;databaseName=DBCRAWLER";
//	String userName = "Dot_Crawler";
//	String passWord = "$serv1c3cr4wl3r%";
	String chromeLocal = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
//	String chromeServer = "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe";
//	Connection conn;
	
//	@Before
	public void setup() throws Exception {
//		conn = getConnection();
		ChromeOptions chromeOptions= new ChromeOptions();
		chromeOptions.setBinary(chromeLocal);
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\driver\\chromedriver2.exe");
		driver = new ChromeDriver(chromeOptions);
//		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\driver\\geckodriver.exe");
//		 WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

//	@After
	public void tearDown() {
		driver.quit();
	}

//	@Test
	public void getThongTinCongTy() throws Exception {
		for (int iPage=1; iPage<10000; iPage++) {
			String hochiminhDoanhNghiep = "https://ho-chi-minh.congtydoanhnghiep.com/trang-";
			String quangngaiDoanhNghiep = "https://quang-ngai.congtydoanhnghiep.com/trang-";
			
			driver.get(quangngaiDoanhNghiep + iPage);
			for(int i = 0; i < 20; i ++) {
				driver.findElements(By.xpath("//article[@class='even']/h2/a|//article[@class='odd']/h2/a")).get(i).click();
				getThongTinChiTietCongTy(i);
				driver.navigate().back();
			}
		}
	}
	
	private void getThongTinChiTietCongTy(int iPage) {
		System.out.println(iPage + " ============ " + driver.getCurrentUrl());
		System.out.println("TEN CONG TY: " + driver.findElement(By.xpath("//th[text()='Tên công ty: ']/following-sibling::td")).getText());
		List<WebElement> giamdoc = driver.findElements(By.xpath("//th[text()='Giám đốc:']/following-sibling::td"));
		if(giamdoc.size()==1 ) {
			System.out.println("GIAM DOC: " + giamdoc.get(0).getText());
		}
		System.out.println("CHU SO HUU: " + driver.findElement(By.xpath("//th[text()='Chủ sở hữu:']/following-sibling::td")).getText());
		System.out.println("MA SO THUE: " + driver.findElement(By.xpath("//th[text()='Mã số thuế: ']/following-sibling::td")).getText());
		
		System.out.println("==========");
		System.out.println("DIA CHI: " + driver.findElement(By.xpath("//th[text()='Địa Chỉ:']/following-sibling::td")).getText());
		List<WebElement> dienthoais = driver.findElements(By.xpath("//th[text()='Điện thoại:']/following-sibling::td"));
		if(dienthoais.size()==1 ) {
			System.out.println("DIEN THOAI: " + driver.findElement(By.xpath("//th[text()='Điện thoại:']/following-sibling::td")).getText());
		}
		
		System.out.println("==========");
		System.out.println("TINH TRANG HOAT DONG: " + driver.findElement(By.xpath("//th[text()='Tình trạng hoạt động: ']/following-sibling::td")).getText());
		System.out.println("NOI DANG KY: " + driver.findElement(By.xpath("//th[text()='Nơi đăng ký quản lý:']/following-sibling::td")).getText());
		System.out.println("NGAY CAP GIAY PHEP: " + driver.findElement(By.xpath("//th[text()='Ngày cấp giấy phép:']/following-sibling::td")).getText());
		
		System.out.println("==========");
		List<WebElement> nganhnghechinh = driver.findElements(By.xpath("//th[text()='Ngành nghề kinh doanh:']/following-sibling::td"));
		if(nganhnghechinh.size()==1 ) {
			List<WebElement> nganhNghes = driver.findElements(By.xpath("//th[text()='Ngành nghề kinh doanh:']/following-sibling::td//li"));
			for(int iNganh=0; iNganh<nganhNghes.size(); iNganh++) {
				System.out.println("=====" + nganhNghes.get(iNganh).getText());
			}
		}
		
		System.out.println("END ===================================================== END");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	// ==============================================================================================================================	
////	@Test
//	public void getAllPage() throws Exception {
//		String transtype = "transtype";
//		String bds = "bds";
//
//		String SPUrl = "EXEC [dbo].[Transtype_Sel] ?,?";
//		PreparedStatement ps = conn.prepareStatement(SPUrl);
//		ps.setEscapeProcessing(true);
//		ps.setQueryTimeout(5);
//		ps.setString(1, transtype);
//		ps.setString(2, bds);
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next()) {
//			int itranstype = Integer.parseInt(rs.getString("transtype"));
//			String iurl = rs.getString("url");
//			System.out.println(itranstype + " : " + iurl);
//			getPage(itranstype, iurl);
//		}
//	}
//
//	// Getting page
//	private void getPage(int transtype, String url) throws Exception {
//		List<WebElement> record;
//		int iStop = 0;
//		
//		for (int iUrl = 0; iUrl <= 10000; iUrl++) {
//			if(iStop > 0) {
//				break;
//			} else {
//				driver.get(url + "/p" + iUrl);
//				for (int i = 0; i < 20; i++) {
//					if(iStop > 0) {
//						break;
//					} else {
//						List<WebElement> titleEles = driver.findElements(By.xpath("//div[@class='p-title']//a"));
//						if(titleEles.size()!=0) {
//							record = driver.findElements(By.xpath("//div[@class='p-title']//a"));
//							if(record.size() > 0) {
//								record.get(i).click();
//								new WebDriverWait(driver, 50).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
//								System.out.println(driver.getCurrentUrl());
//								iStop = writeToDatabase(transtype);
//								driver.navigate().back();
//								new WebDriverWait(driver, 50).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
//							} else {
//								iStop = 1;
//							}
//						} else {
//							List<WebElement> notFoundEles = driver.findElements(By.xpath("//div[@id='LeftMainContent__productSearchResult_pnlNotFound']"));
//							if(notFoundEles.size()==1) {
//								CallableStatement cs = conn.prepareCall("{call BDS_TaskProcessUpd(?,?)}");
//								cs.setEscapeProcessing(true);
//								cs.setQueryTimeout(5);
//								cs.setInt(1, transtype);
//								cs.setString(2, "bds");
//								cs.executeUpdate();
//								System.out.println("End reading !");
//								iStop = 1;
//							} else {
//								Thread.sleep(5000);	
//							}
//						}
//					}
//				}
//			}
//		}
//
//	}
//
//	// Get detail information
//	private int writeToDatabase(int transtype) throws Exception {
//		String transCode = getTransactionCode(transtype);
//		if(transCode!=null) {
//			String title = getTitle();
//			float area = getArea();
//			String transDesc = getTransactionDescription();
//			String direction = getDirection();
//			String balcony = getBalcony();
//			int bedRoom = getBedRoom();
//			int toilet = getToilet();
//			String furniture = getFurniture();
//			String contactPerson = getContactPerson();
//			String phoneContact = getPhoneContact();
//			String email = getEmail();
//			String addressContact = getAddressContact();
//			Date importDate = null;
//			String url = getURL();		
//			
//			try {
//				CallableStatement cs = conn.prepareCall("{call BDS_TransImport_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//				cs.setEscapeProcessing(true);
//				cs.setQueryTimeout(5);
//				
//				cs.setInt(1, transtype);
//				cs.setString(2, title);
//				cs.setString(3, getAddress());
//				
//				if(!getPrice().get(0).contains("Th")) {
//					cs.setFloat(4, Float.parseFloat(getPrice().get(0)));
//					cs.setString(5, getPrice().get(1));
//				} else {
//					cs.setFloat(4, 0);
//					cs.setString(5, null);
//				}
//				
//				cs.setFloat(6, area);
//				cs.setString(7, transDesc);
//				cs.setString(8, direction);
//				cs.setString(9, balcony);
//				cs.setInt(10, bedRoom);
//				cs.setInt(11, toilet);
//				cs.setString(12, furniture);
//				cs.setString(13, contactPerson);
//				cs.setString(14, phoneContact);
//				cs.setString(15, email);
//				cs.setString(16, addressContact);
//				cs.setTimestamp(17, getTransactionDate());
//				cs.setDate(18, (java.sql.Date) importDate);
//				cs.setString(19, transCode);
//				cs.setString(20, url);
//				cs.registerOutParameter(21, java.sql.Types.INTEGER);
//				
//				cs.executeUpdate();
//				int iStop = Integer.parseInt(cs.getString(21));
//	
//				return iStop;
//			} catch (SQLException e) {
//				e.printStackTrace();
//				return 100;
//			}
//		} else {
//			return 0;
//		}
//	}
//
//	// ==============================================================================================================================	
//	// Getting data unit
//	// Getting connection
//	private Connection getConnection() throws Exception {
//		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		Connection conn = DriverManager.getConnection(jdbc, userName, passWord);
//		return conn;
//	}
//		
//	// 	Getting direction
//	private String getDirection() {
//		String direction = null;
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
//		if(driver.findElements(By.xpath("//div[@id='LeftMainContent__productDetail_direction']/div[2]")).size()==1) {
//			direction = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_direction']/div[2]")).getText();
//		}
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		return direction;
//	}
//	
//	// 	Getting balcony
//	private String getBalcony() {
//		String balcony = null;
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		if(driver.findElements(By.xpath("//div[@id='LeftMainContent__productDetail_balcony']/div[2]")).size()==1) {
//			balcony = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_balcony']/div[2]")).getText();
//		}
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		return balcony;
//	}
//	
//	// 	Getting furniture
//	private String getFurniture() {
//		String funiture;
//		try {
//			funiture = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_interior']/div[2]")).getText();
//		} catch (NoSuchElementException e) {
//			funiture = null;
//		}
//		return funiture;
//	}
//	
//	//	Getting price
//	private List<String> getPrice() {
//		String price = driver.findElement(By.xpath("(//span[contains(@class, 'gia-title')]/strong)[1]")).getText().trim();
//		List<String> priceList = Arrays.asList(price.split(" "));
//		return priceList;
//	}
//	
//	// Getting title
//	private String getTitle() throws Exception {
//		String title = driver.findElement(By.xpath("//div[@class='pm-title']/h1")).getText();
//		return title;
//	}
//
//	// Getting address detail
//	private String getAddress() {
//		String address = driver.findElement(By.xpath("(//div[@class='table-detail']/div[2]/div[@class='right'])[1]")).getText();
//		return address;
//	}
//
//	// Getting email
//	private String getEmail() {
//		String email;
//		try {
//			email = driver.findElement(By.xpath("//div[@id='contactEmail']//a[@rel='nofollow']")).getText();
//		} catch (NoSuchElementException e) {
//			email = null;
//		}
//		return email;
//	}
//
//	// Getting area
//	private float getArea() {
//		float area;
//		try {
//			String strArea = driver.findElement(By.xpath("//span[@class='gia-title']/strong")).getText();
//			if(strArea.contains("Kh")) {
//				area = -1;
//			} else {
//				area = Float.parseFloat(strArea.replaceAll("m²", ""));
//			}
//		} catch (NoSuchElementException e) {
//			area = -1;
//		}
//		return area;
//	}
//
//	// Getting Transaction Description
//	private String getTransactionDescription() {
//		String transactionDescription = driver.findElement(By.xpath("//div[contains(@class, 'pm-content')]/div[@class='pm-desc']")).getText();
//		return transactionDescription;
//	}
//
//	// Getting Bed Room
//	private int getBedRoom() {
//		int bedRoom;
//		try {
//			bedRoom = Integer.parseInt(driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_roomNumber']/div[2]")).getText().replaceAll("[^0-9]", ""));
//		} catch (NoSuchElementException e) {
//			bedRoom = 0;
//		}
//		return bedRoom;
//	}
//
//	// Getting Toilet
//	private int getToilet() {
//		int toilet;
//		try {
//			toilet = Integer.parseInt(driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_toilet']/div[2]")).getText().replaceAll("[^0-9]", ""));
//		} catch (NoSuchElementException e) {
//			toilet = 0;
//		}
//		return toilet;
//	}
//
//	// Getting Contact Person
//	private String getContactPerson() {
//		String contactPerson = null;
//		if(driver.findElements(By.xpath("//div[@id='LeftMainContent__productDetail_contactName']/div[@class='right']")).size()==1) {
//			contactPerson = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_contactName']/div[@class='right']")).getText();
//		}
//		return contactPerson;
//	}
//
//	// Getting Phone Contact
//	private String getPhoneContact() {
//		String phoneContact = driver.findElement(By.xpath("//div[@id='LeftMainContent__productDetail_contactMobile']/div[@class='right']")).getText();
//		return phoneContact;
//	}
//
//	// Getting Address Contact
//	private String getAddressContact() {
//		String addressContact = driver.findElement(By.xpath("(//div[@class='div-table']//div[@class='right'])[2]")).getText();
//		return addressContact;
//	}
//
//	// Getting Transaction Code
//	private String getTransactionCode(int transtype) throws Exception {
//		String transCode = driver.findElement(By.xpath("(//span[@class='normalblue']/parent::div)[1]/div")).getText();
//		CallableStatement cs = conn.prepareCall("{call BDS_CheckTranscodeExists(?,?,?)}");
//		cs.setEscapeProcessing(true);
//		cs.setQueryTimeout(5);
//		cs.setInt(1, transtype);
//		cs.setString(2, transCode);
//		cs.registerOutParameter(3, java.sql.Types.BOOLEAN);
//		cs.executeUpdate();
//		Boolean iVerify = cs.getBoolean(3);
//		System.out.println(transCode);
//		System.out.println(iVerify);
//		if(iVerify)
//			return null;
//		else 
//			return transCode;
//	}
//
//	// Getting URL
//	private String getURL() {
//		String url = driver.getCurrentUrl();
//		return url;
//	}
//
//	// Getting Transaction Date
//	private Timestamp getTransactionDate() throws Exception {
//		String transDate = driver.findElement(By.xpath("(//span[@class='normalblue']/parent::div)[3]")).getText().substring(11);
//		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//		Timestamp ts = new Timestamp(((java.util.Date)df.parse(transDate)).getTime());
//		return ts;
//	}
}