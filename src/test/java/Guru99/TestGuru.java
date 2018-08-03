package Guru99;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestGuru {
	public WebDriver driver;
	public Login objLogin;
	public NewCustomer objNewCustomer;
	public String customerID;
	public NewAccount objNewAccount;
	public String accountID;
	public Deposit objNewDeposit;
	public String strTime; 
	@BeforeMethod
	public void setup() {
		Date d = new Date();
	    SimpleDateFormat form = new SimpleDateFormat("ddhhmmss");
	    strTime = form.format(d);
	    
	    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\Driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://demo.guru99.com/V4/");
	}

	@Test(priority = 0)
	public void test_New_Customer_Can_Be_Created() {
		objLogin = new Login(driver);
		objNewCustomer = new NewCustomer(driver);

		objLogin.loginToGuru("mngr146189", "hUtUbUp");
		objNewCustomer.clickNewCustomer();
		objNewCustomer.createNewCustomer("abc", "20011998", "abc", "abc", "abc", "123456", "123456",
				"vunguyen" + strTime + "@gmail.com", "123456");
		customerID = objNewCustomer.getCustomerID();

		Assert.assertTrue(objNewCustomer.getSuccessMessage().contains("Customer Registered Successfully!!!"));
	}

	@Test(priority = 1)
	public void test_New_Account_Can_Be_Created_Based_On_New_Customer() {
		objLogin = new Login(driver);
		objNewCustomer = new NewCustomer(driver);
		objNewAccount = new NewAccount(driver);

		objLogin.loginToGuru("mngr146189", "hUtUbUp");
		objNewAccount.clickNewAccount();
		objNewAccount.createNewAccount(customerID, "30000");
		accountID = objNewAccount.getAccountID();
		Assert.assertTrue(objNewAccount.getSuccessMessage().contains("Account Generated Successfully!!!"));
	}

	@Test(priority = 2)
	public void test_Deposit_Work_Fine_With_New_Account() {
		objLogin = new Login(driver);
		objNewCustomer = new NewCustomer(driver);
		objNewAccount = new NewAccount(driver);
		objNewDeposit = new Deposit(driver);

		objLogin.loginToGuru("mngr146189", "hUtUbUp");
		objNewDeposit.clickDeposit();
		objNewDeposit.createDeposit(accountID, "2000", "abc");
		Assert.assertTrue(
				objNewAccount.getSuccessMessage().contains("Transaction details of Deposit for Account " + accountID));
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}