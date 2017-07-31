package com.qait.training.automation.tests;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qait.training.automation.TestSessionInitiator;

public class TatocTest {
	  private TestSessionInitiator testSessionInitiator;
	    
	    @BeforeTest
	    public void initializeVariable(){
	        testSessionInitiator = new TestSessionInitiator(this.getClass().getName());
	    }
	    @Test
		public void Test01_verifyBasicCourse() throws InterruptedException
		{
	    	 testSessionInitiator.launchApplication();
	    	 testSessionInitiator.tatocBasicAction.loadBasicCourse();
	    	 testSessionInitiator.tatocBasicAction.performActions();
	    	
	}
	    
	    @Test
	  		public void Test02_verifyAdvancedCourse() throws SQLException, IOException 
	  		{
	    	testSessionInitiator.launchApplication();
	    	testSessionInitiator.tatocAdvancedAction.loadAdvancedCourse();
	    	testSessionInitiator.tatocAdvancedAction.performAction_clickOnGoNext();
	    	testSessionInitiator.tatocAdvancedAction.databaseConnectionToGetNameandPasskey();
	  	    	 testSessionInitiator.openUrl("http://10.0.1.86/tatoc/advanced/rest");
	  	    	testSessionInitiator.tatocAdvancedAction.restServices();
	  	    	testSessionInitiator.tatocAdvancedAction.fileHandle();

	  	    	
	  	}   
}
