package junit.display;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

import junit.TestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.StudentPO;

import businesslogic.displaybl.StudentInfoDisplay;
import businesslogicservice.displayblservice.StudentInfoDisplayService;

import dataservice.DatabaseService;
import dataservice.Table;

public class StudentInfoDisplayTest {
	
	DatabaseService studentData;
	StudentInfoDisplayService studentDisplay;
	
	@Before
	public void setUp() throws Exception {
		studentData = TestHelper.getDatabaseService(Table.student);
		studentDisplay = new StudentInfoDisplay(studentData);
	}

	@After
	public void tearDown() throws Exception {
		studentData =null;
		studentDisplay = null;
	}

	@Test
	public void testGetStudent() throws RemoteException {
		StudentPO student = studentDisplay.getStudent(121250151);
		assertTrue(student!=null);
	}

	@Test
	public void testGetStudentList() throws RemoteException {
		ArrayList<StudentPO> list = studentDisplay.getStudentList(25, 1);
		assertTrue(list!=null);
	}

}
