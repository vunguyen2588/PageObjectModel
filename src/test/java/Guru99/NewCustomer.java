package Guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewCustomer {
	WebDriver driver;
	By customerName = By.name("name");
	By dateOfBirth = By.name("dob");
	By address = By.name("addr");
	By city = By.name("city");
	By state = By.name("state");
	By mobilePhone = By.name("telephoneno");
	By pin = By.name("pinno");
	By email = By.name("emailid");
	By password = By.name("password");
	By submit = By.name("sub");
	By successMes = By.xpath("//p[@class='heading3']");
	By lnkNewCustomer = By.xpath("//a[text()='New Customer']");
	By customerID = By.xpath("//td[text()='Customer ID']/parent::tr/td[2]");

	public NewCustomer(WebDriver driver) {
		this.driver = driver;
	}

	public void setCustomerName(String strCustomerName) {
		driver.findElement(customerName).sendKeys(strCustomerName);
	}

	public void setDateOfBirth(String strDateOfBirth) {
		driver.findElement(dateOfBirth).sendKeys(strDateOfBirth);
	}

	public void setAddress(String strAddress) {
		driver.findElement(address).sendKeys(strAddress);
	}

	public void setCity(String strCity) {
		driver.findElement(city).sendKeys(strCity);
	}

	public void setPIN(String strPin) {
		driver.findElement(pin).sendKeys(strPin);
	}

	public void setState(String strState) {
		driver.findElement(state).sendKeys(strState);
	}

	public void setMobilePhone(String strMobilePhone) {
		driver.findElement(mobilePhone).sendKeys(strMobilePhone);
	}

	public void setEmail(String strEmail) {
		driver.findElement(email).sendKeys(strEmail);
	}

	public void setPassword(String strPassword) {
		driver.findElement(password).sendKeys(strPassword);
	}

	public void clickNewCustomer() {
		driver.findElement(lnkNewCustomer).click();
	}

	public void clickSubmit() {
		driver.findElement(submit).click();
	}

	public void createNewCustomer(String strCustomerName, String strDateOfBirth, String strAddress, String strCity,
			String strState, String strPin, String strMobilePhone, String strEmail, String strPassword) {
		this.setCustomerName(strCustomerName);
		this.setDateOfBirth(strDateOfBirth);
		this.setAddress(strAddress);
		this.setCity(strCity);
		this.setState(strState);
		this.setPIN(strPin);
		this.setMobilePhone(strMobilePhone);
		this.setEmail(strEmail);
		this.setPassword(strPassword);
		this.clickSubmit();
	}

	public String getSuccessMessage() {
		return driver.findElement(successMes).getText();
	}

	public String getCustomerID() {
		return driver.findElement(customerID).getText();
	}
}