package Guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Deposit {
	WebDriver driver;
	By accountNo = By.name("accountno");
	By ammount = By.name("ammount");
	By description = By.name("desc");
	By submit = By.name("AccSubmit");
	By successMes = By.xpath("//p[@class='heading3']");
	By lnkDeposit = By.xpath("//a[text()='Deposit']");

	public Deposit(WebDriver driver) {
		this.driver = driver;
	}

	public void clickDeposit() {
		driver.findElement(lnkDeposit).click();
	}

	public void setAccountNo(String strAccountNo) {
		driver.findElement(accountNo).sendKeys(strAccountNo);
	}

	public void setAmount(String strAmount) {
		driver.findElement(ammount).sendKeys(strAmount);
	}

	public void setDescription(String strDescription) {
		driver.findElement(description).sendKeys(strDescription);
	}

	public void clickSubmit() {
		driver.findElement(submit).click();
	}

	public void createDeposit(String strAccountNo, String strAmount, String strDescription) {
		this.setAccountNo(strAccountNo);
		this.setAmount(strAmount);
		this.setDescription(strDescription);
		this.clickSubmit();
	}

	public String getSuccessMessage() {
		return driver.findElement(successMes).getText();
	}
}
