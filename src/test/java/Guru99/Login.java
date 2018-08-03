package Guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {
	WebDriver driver;
	By userID = By.name("uid");
	By password = By.name("password");
	By login = By.name("btnLogin");

	public Login(WebDriver driver) {
		this.driver = driver;
	}

	public void setUserName(String strUserName) {
		driver.findElement(userID).sendKeys(strUserName);
	}

	public void setPassword(String strPassword) {
		driver.findElement(password).sendKeys(strPassword);
	}

	public void clickLogin() {
		driver.findElement(login).click();
	}

	public void loginToGuru(String strUserName, String strPasword) {
		this.setUserName(strUserName);
		this.setPassword(strPasword);
		this.clickLogin();
	}
}