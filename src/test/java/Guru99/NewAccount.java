package Guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewAccount {
	WebDriver driver;
	By customerID = By.name("cusid");
	By initialDeposit = By.name("inideposit");
	By submit = By.name("button2");
	By lnkNewAccount = By.xpath("//a[text()='New Account']");
	By successMes = By.xpath("//p[@class='heading3']");
	By accountID = By.xpath("//td[text()='Account ID']/parent::tr/td[2]");

	public NewAccount(WebDriver driver) {
		this.driver = driver;
	}

	public void setCustomerID(String strCustomerID) {
		driver.findElement(customerID).sendKeys(strCustomerID);
	}

	public void setInitialDeposit(String strInitialDeposit) {
		driver.findElement(initialDeposit).sendKeys(strInitialDeposit);
	}

	public void createNewAccount(String strCustomerID, String strInitialDeposit) {
		this.setCustomerID(strCustomerID);
		this.setInitialDeposit(strInitialDeposit);
		this.clickSubmit();
	}

	public void clickNewAccount() {
		driver.findElement(lnkNewAccount).click();
	}

	public String getSuccessMessage() {
		return driver.findElement(successMes).getText();
	}

	public void clickSubmit() {
		driver.findElement(submit).click();
	}

	public String getAccountID() {
		return driver.findElement(accountID).getText();
	}
}