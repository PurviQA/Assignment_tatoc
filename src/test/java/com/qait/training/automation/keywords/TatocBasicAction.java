package com.qait.training.automation.keywords;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.training.automation.getpageobjects.GetPage;
import com.qait.training.automation.utils.YamlReader;

public class TatocBasicAction extends GetPage {

	public TatocBasicAction(WebDriver driver) {

		super(driver, "BasicAction");
		
	}
	public void loadBasicCourse()
	{
		element("basic_course").click();
	}
	

	public void performActions() throws InterruptedException {

		element("box_clck").click();
		

		switchToFrame("main");
		String first_color = element("box_01").getAttribute("class");
		switchToFrame("child");
		String second_color = element("box_02").getAttribute("class");

		while (!first_color.equals(second_color)) {
			switchToDefaultContent();
			switchToFrame("main");
			element("repaint_box").click();
			switchToFrame("child");
			second_color = element("box_02").getAttribute("class");
		}
		switchToDefaultContent();
		switchToFrame("main");
		element("proceed").click();
		WebElement drop = element("dropbox");
		WebElement drag = element("dragbox");

		dragAndDrop(drag, drop);
		element("proceed").click();
		element("popup_window").click();
		String Parent_Window = getwindowHandle();
		for (String Child_Window : getwindowHandles()) {
			switchToWindow(Child_Window);
		}
		element("txt_name").sendKeys(YamlReader.getData("name"));
		element("submit_clck").click();
		switchToWindow(Parent_Window);
		element("proceed").click();
		element("generate_token").click();
		// remove
		Thread.sleep(2000);
		String cookie = element("token_value").getText();
		String abc = cookie.split(":")[1];
		Cookie ck = new Cookie("Token", abc.trim());

		addCookie(ck);
		Thread.sleep(1000);
		element("proceed").click();

	}
}
