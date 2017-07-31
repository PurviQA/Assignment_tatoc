package com.qait.training.automation.keywords;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;

import com.jayway.restassured.RestAssured;
import com.qait.training.automation.getpageobjects.GetPage;
import com.qait.training.automation.utils.DataBaseConnector;
import com.qait.training.automation.utils.YamlReader;

public class TatocAdvancedAction extends GetPage {
	public TatocAdvancedAction(WebDriver driver) {

		super(driver, "AdvanceAction");

	}

	public void loadAdvancedCourse() {
		element("advanced_course").click();
	}

	public void performAction_clickOnGoNext() {

		hoverClick(element("menu_title"));
		//wait.getWhenVisible("go_next");
		wait.hardWait(1);
		element("go_next").click();

	}

	public void databaseConnectionToGetNameandPasskey() throws SQLException {
		DataBaseConnector data = new DataBaseConnector();
		String i = null, name = null, pass = null;

		String id = element("dislayed_id").getText();

		data.connectToDataBase(YamlReader.getData("host"), YamlReader.getData("databaseName"),
				YamlReader.getData("username"), YamlReader.getData("password"));

		ResultSet resultSet = data.getResultSetOnExecutingASelectQuery("select * from identity");

		while (resultSet.next()) {
			if (resultSet.getString(2).equalsIgnoreCase(id)) {
				i = resultSet.getString(1);
				break;
			}
		}
		ResultSet resultSet1 = data.getResultSetOnExecutingASelectQuery("select * from credentials");
		while (resultSet1.next()) {
			if (resultSet1.getString(1).equalsIgnoreCase(i)) {
				name = resultSet1.getString(2);
				pass = resultSet1.getString(3);

				break;
			}
		}
		element("txt_name").sendKeys(name);
		element("txt_pass").sendKeys(pass);
		element("clck_submit").click();

	}

	public void restServices() {
		RestAssured.baseURI = YamlReader.getData("rest_baseUrl");
		String Session_id = element("session_id").getText();
		String ses_id = Session_id.split(":")[1];
		System.out.println("ses_id: " + ses_id);

		String resp = given().get("/token/" + ses_id.trim()).then().extract().path("token");
		System.out.println(resp);

		given().header("Content-Type", "application/x-www-form-urlencoded").formParam("id", ses_id.trim())
				.formParam("signature", resp).formParam("allow_access", "1").request().post("/register");

		element("proceed").click();
	}

	public void fileHandle() throws IOException {
		element("download").click();
		wait.hardWait(2);
		File file = new File(YamlReader.getData("FilePath"));
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String line;
		String sig = null;

		while ((line = bufferedReader.readLine()) != null) {
			if (line.contains("Signature")) {
				System.out.println(line);
				sig = line.split(":")[1];
				System.out.println(sig);

				break;
			}
		}
		if(file.exists())
			file.delete();
		bufferedReader.close();
		element("txt_signature").sendKeys(sig.trim());
		element("clck_proceed").click();
	}
}
