package com.org.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.opencsv.CSVReader;

public class APIWebMobileUtility {

	private static Map<String, List<String>> class2TestCasesMap = new LinkedHashMap<String, List<String>>();
	private static Map<String, List<String>> testCase2TestStepsMap = new LinkedHashMap<String, List<String>>();
	private static Map<String, String> class2StatusMap = new LinkedHashMap<String, String>();
	private static Map<String, String> testcase2StatusMap = new LinkedHashMap<String, String>();
	private static List<String> testCases = null;
	private static List<String> testSteps = null;
	private static List<String> testStepsForMetrics = null;
	private static List<String> testStepsTemp = new ArrayList<String>();
	private static String tempClassName = "", tempTestCaseName = "", startTime = "", endTime = "", environment = "",
			browser = "", toDayDate = "", projectName = "";
	private final String stepPassStatus = "It's PASS", stepFailStatus = "It's FAIL";
	private static int tempClassCount = 0, tempTCCount = 0;
	protected WebDriver driver = null;
	protected static boolean iterateAll = true, tempRun = false;
	private String reportPath = "/testreports";
	private String cssPath = "/src/test/resource/com/org/drivers";

	private static Properties prop = new Properties();
	private static final String dataSheetPath = "/src/test/resource/com/org/datasheets";
	private static final String qaFilePath = "/src/test/resource/com/org/config/qa.properties";
	public static String eachLineData[] = null, column[] = null, row[] = null;
	public List<String> columns = new LinkedList<String>();
	public static int columnCount = 0, tempRowCount = 0, temp = 0, iteration = 0;
	public static List<String[]> rowData = new ArrayList<String[]>();
	public static Map<String, String> data = new LinkedHashMap<String, String>();

	public static Connection connection = null;
	public static Statement statement = null;
	public static ResultSet rs = null;
	public static Random random = null;
	private Date date = new Date();
	private long totalTime = 0, t1 = 0, t2 = 0;

	private static final Logger log = Logger.getLogger(APIWebMobileUtility.class);
	private static int totalTestCount = 0, totalTestPassed = 0, totalTestFailed = 0;

	/**
	 * loads CSVFile once for testCase
	 * 
	 * @param fileName
	 */
	public void loadCSVFile(String fileName) {
		try {
			tempRun = true;
			tempRowCount = iteration;
			temp = tempRowCount;

			if (tempRowCount == 0) {
				CSVReader reader = new CSVReader(
						new FileReader(System.getProperty("baseDir") + dataSheetPath + "/" + fileName + ".csv"));
				String line[] = null;

				line = reader.readNext();
				column = line;
				columnCount = line.length - 1;
				while ((line = reader.readNext()) != null) {

					if ("yes".equalsIgnoreCase(line[columnCount])) {
						rowData.add(line);
					}
				}
				reader.close();
				success("Loaded CSV File successfully");
			}
			iteration++;
			if (rowData.size() == iteration) {
				iterateAll = false;
			}
		} catch (Exception e) {
			failure("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
			log.error("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
			Assert.fail("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
		}
	}

	/**
	 * loads CSVFile once for testCase and given rowNumber
	 * 
	 * @param fileName
	 */
	public void loadCSVFile(String fileName, int rowNumber) {
		try {
			iteration = 0;
			tempRun = true;
			tempRowCount = iteration;
			temp = tempRowCount;
			int rowCount = 1;
			if (!(rowData == null || rowData.isEmpty())) {
				rowData.clear();
			}
			if (tempRowCount == 0) {
				CSVReader reader = new CSVReader(
						new FileReader(System.getProperty("baseDir") + dataSheetPath + "/" + fileName + ".csv"));
				String line[] = null;

				line = reader.readNext();
				column = line;
				columnCount = line.length - 1;
				while ((line = reader.readNext()) != null) {
					if (rowCount == rowNumber) {
						rowData.add(line);
					}
					rowCount++;
				}
				reader.close();
				success("Loaded CSV File successfully");
			}
			iteration++;
			if (rowData.size() == iteration) {
				iterateAll = false;
			}
		} catch (Exception e) {
			failure("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
			log.error("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
			Assert.fail("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
		}
	}

	/**
	 * returns the cell value for a given column name
	 * 
	 * @param columnName
	 * @return String
	 */
	public String getCellData(String columnName) {
		try {
			if (tempRun) {

				if (temp == tempRowCount) {
					row = rowData.get(tempRowCount);
					for (int i = 0; i <= columnCount; i++) {
						data.put(column[i], row[i]);
					}
					success("Data set_" + iteration + " : " + data);
					System.out.println("\nData set_" + iteration + " : " + data + "\n");
				}
				temp++;
			} else {
				log.error("Please load CSV file before reading cellData ");
				Assert.fail("Please load CSV file before reading cellData ");
			}
		} catch (Exception e) {
			failure("Getting Cell Data is failed");
			log.error("Getting Cell Data is failed");
			Assert.fail("Getting Cell Data is failed");
		}
		return data.get(columnName);
	}

	/**
	 * loads CSVFile once for testCase and returns CSVData
	 * 
	 * @param fileName
	 */
	public synchronized CSVData loadCSVFileOnce(String fileName) {
		Map<String, Integer> columnToIndex = new LinkedHashMap<>();
		List<String[]> rowData = new ArrayList<>();
		CSVData data = new CSVData();
		try {
			CSVReader reader = new CSVReader(
					new FileReader(System.getProperty("baseDir") + dataSheetPath + "/" + fileName + ".csv"));
			String[] line = null;
			String[] column = null;
			int columnCount = 0;
			int rowCount = 0;

			line = reader.readNext();
			column = line;
			columnCount = line.length - 1;

			for (int i = 0; i < columnCount; i++) {
				columnToIndex.put(column[i], i);
			}

			while ((line = reader.readNext()) != null) {
				if ("yes".equalsIgnoreCase(line[columnCount])) {
					rowData.add(line);
					rowCount++;
				}
			}

			data.setColumnNameToIndex(columnToIndex);
			data.setColumnCount(columnCount);
			data.setRowCount(rowCount);
			data.setRowData(rowData);

			reader.close();
			success("Loaded CSV File successfully");
		} catch (Exception e) {
			failure("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
			log.error("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
			Assert.fail("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
		}
		return data;
	}

	/**
	 * returns the cell value for a given column name, row
	 * 
	 * @param columnName
	 * @return String
	 */
	public String getData(String columnName, String[] row, Map<String, Integer> columnToIndex) {

		if(null!=columnToIndex.get(columnName)) {
			return row[columnToIndex.get(columnName)];
		}else return null;
	}

	/**
	 * returns the cell value for a given row and index and starts from Zero
	 * 
	 * @param columnName
	 * @return String
	 */
	public String getData(String[] row, int columnNo) throws ArrayIndexOutOfBoundsException {

		return row[columnNo];
	}

	/**
	 * loads CSVFileData For given Row Numbers in increasing orger and returns
	 * CSVData
	 * 
	 * @param fileName
	 */
	public synchronized CSVData loadCSVFileDataByRowNumber(String fileName, int... rowNo)
			throws ArrayIndexOutOfBoundsException {
		Map<String, Integer> columnToIndex = new LinkedHashMap<>();
		List<String[]> rowData = new ArrayList<>();
		CSVData data = new CSVData();
		try {
			CSVReader reader = new CSVReader(
					new FileReader(System.getProperty("baseDir") + dataSheetPath + "/" + fileName + ".csv"));
			String[] line = null;
			String[] column = null;
			int columnCount = 0;
			int rowCount = 1;
			int actualRowCount = 0;

			line = reader.readNext();
			column = line;
			columnCount = line.length - 1;

			for (int i = 0; i < columnCount; i++) {
				columnToIndex.put(column[i], i);
			}
			int j = 0;

			while ((line = reader.readNext()) != null) {
				rowCount++;
				if (rowNo[j] == rowCount) {
					rowData.add(line);
					j++;
					actualRowCount++;
				}
				if (rowNo.length == j)
					break;

			}
			rowCount--;
			data.setColumnNameToIndex(columnToIndex);
			data.setColumnCount(columnCount);
			data.setRowCount(actualRowCount);
			data.setRowData(rowData);

			reader.close();
			success("Loaded CSV File successfully");
		} catch (Exception e) {
			e.printStackTrace();
			failure("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
			log.error("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
			Assert.fail("Loading CSV File is failed : " + System.getProperty("baseDir") + dataSheetPath + "/" + fileName
					+ ".csv");
		}
		return data;
	}

	/**
	 * opens the DB connection for the given DBUrl and credentials.
	 * 
	 * @param dbURL
	 * @param userNamenPasswd
	 */
	public void openDBConnection(String dbURL, String userNamenPasswd) {
		try {

			String dburl[] = dbURL.split("/");
			String uNamenPasswd[] = userNamenPasswd.split("/");
			String url = "jdbc:mysql://" + dburl[0] + ":3306/" + dburl[1];
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, uNamenPasswd[0], uNamenPasswd[1]);
			statement = connection.createStatement();
			log.info("Database connection established susseccfully : " + dbURL);
			success("Database connection established susseccfully : " + dbURL);

		} catch (SQLException e) {
			failure("Unable to connect to given database : " + dbURL);
			log.error("Unable to connect to given database : " + dbURL);
		} catch (Exception e) {
			failure("Unable to connect to given database : " + dbURL);
			log.error("Unable to connect to given database : " + dbURL);
		}
	}

	/**
	 * closes the opened DB connection.
	 */
	public void closeDBConnection() {
		try {
			if (rs != null) {
				rs.close();
				statement.close();
			}
			connection.close();
			success("Database connection closed susseccfully");
			log.info("Database connection closed susseccfully");
		} catch (SQLException e) {
			failure("Unable to close Database connection");
			log.error("Unable to close Database connection");
		}
	}

	/**
	 * this returns the Result Set
	 * 
	 * @param query
	 * @return result set
	 */
	public ResultSet getDBData(String query) {
		try {

			rs = statement.executeQuery(query);
			success("SELECT operation performed successfully.");
			return rs;

		} catch (Exception e) {
			failure("getDBData has failed. " + e.getMessage());
			log.error("getDBData has failed. " + e.getMessage());
		}
		return rs;
	}

	/**
	 * returns single Integer column data
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public int getDBIntegerData(String selectQuery) {

		int result = 0;
		try {
			rs = statement.executeQuery(selectQuery);
			while (rs.next()) {
				result = rs.getInt(1);
			}
			log.info("SELECT operation performed successfully.");
			success("SELECT operation performed successfully.");
		} catch (SQLException e) {
			failure("getDBIntegerData has failed. " + e.getMessage());
			log.error("getDBIntegerData has failed. " + e.getMessage());
		} catch (Exception e) {
			failure("getDBIntegerData has failed. " + e.getMessage());
			log.error("getDBIntegerData has failed. " + e.getMessage());
		}
		return result;
	}

	/**
	 * returns single String column data
	 * 
	 * @throws Exception
	 * @throws SQLException
	 */
	public String getDBStringData(String selectQuery) {

		String result = null;
		try {
			rs = statement.executeQuery(selectQuery);
			while (rs.next()) {
				result = rs.getString(1);
			}
			log.info("SELECT operation performed successfully.");
			success("SELECT operation performed successfully.");
		} catch (SQLException e) {
			failure("getDBStringData has failed. " + e.getMessage());
			log.error("getDBStringData has failed. " + e.getMessage());
		} catch (Exception e) {
			failure("getDBStringData has failed. " + e.getMessage());
			log.error("getDBStringData has failed. " + e.getMessage());
		}
		return result;
	}

	/**
	 * this method will insert specified values in DB not tested for the all cases
	 */
	public int insertDataInDB(String insertQuery) {
		int result = 0;
		try {
			result = statement.executeUpdate(insertQuery);
			log.info("INSERT operation performed successfully.");
			success("INSERT operation performed successfully.");
		} catch (SQLException e) {
			failure("performInsert has failed. " + e.getMessage());
			log.error("performInsert() has failed. " + e.getMessage());
		}
		return result;
	}

	/**
	 * this method will update DB with specified values not tested for the all cases
	 */
	public int updateDataInDB(String updateQuery) {
		int result = 0;
		try {
			result = statement.executeUpdate(updateQuery);
			log.info("UPDATE operation performed successfully.");
			success("UPDATE operation performed successfully.");
		} catch (SQLException e) {
			failure("performUpdate has failed. " + e.getMessage());
			log.error("performUpdate() has failed. " + e.getMessage());
		}
		return result;
	}

	/**
	 * this method will delete specified values from DB not tested for the all cases
	 */
	public int deleteDataInDB(String deleteQuery) {
		int result = 0;
		try {
			result = statement.executeUpdate(deleteQuery);
			log.info("DELETE operation performed successfully.");
			success("DELETE operation performed successfully.");
		} catch (SQLException e) {
			failure("performDelete has failed. " + e.getMessage());
			log.error("performDelete() has failed. " + e.getMessage());
		}
		return result;
	}

	/**
	 * used to load Property File by file path
	 * 
	 * @param filepath
	 */
	protected void loadPropertyFile(String filepath) {
		try {
			InputStream is = new FileInputStream(filepath);
			if (is != null) {
				prop.load(is);
			}
		} catch (Exception e) {
			Assert.fail("File not found : " + filepath);
		}
	}

	/**
	 * using to get property value from the .properties file using key
	 * 
	 * @param key
	 * @return property value in String format
	 */
	protected String getValue(String key) {
		return prop.getProperty(key);
	}

	@BeforeSuite
	public void startSuite() {

		startTime = date.toString();
		t1 = System.currentTimeMillis();
		toDayDate = "Results" + todayDatenTime();
	}

	@BeforeClass
	public void loadProperties() {

		String baseDir = Matcher.quoteReplacement(new File("").getAbsolutePath());
		System.setProperty("baseDir", baseDir.replace("\\\\", "/"));
		loadPropertyFile(System.getProperty("baseDir") + qaFilePath);
		PropertyConfigurator.configure(System.getProperty("baseDir") + "/log4j.properties");
		testCases = new ArrayList<String>();
		tempClassCount = 1;
		environment = getValue("environment");
		browser = getValue("browser");
		projectName = getValue("project");
	}

	@BeforeMethod
	public void setUpTestCount() {

		testSteps = new ArrayList<String>();
		tempTCCount = 1;
	}

	public void updateTestMatrics() {
		totalTestCount = totalTestCount + 1;
		testStepsForMetrics = new ArrayList<String>(testSteps);
		for (String element : testStepsTemp) {
			testStepsForMetrics.remove(element);
		}
		boolean isTestCasePass = true;
		for (String str : testStepsForMetrics) {
			if (str.contains("It's PASS")) {
				isTestCasePass = true;
			} else {
				isTestCasePass = false;
				totalTestFailed = totalTestFailed + 1;
				break;
			}
		}
		if (isTestCasePass) {
			totalTestPassed = totalTestPassed + 1;
		}
		testStepsTemp = new ArrayList<String>(testSteps);
	}

	@AfterMethod
	public void testCase2TestStepsMap() {

		testCase2TestStepsMap.put(tempTestCaseName, testSteps);
		tempTCCount = 0;
		tempRowCount = 0;
		iteration = 0;
		iterateAll = true;
		tempRun = false;
		rowData.clear();
	}

	@AfterClass
	public void clearAllProperties() {
		try {
//			prop.clear();
			class2TestCasesMap.put(tempClassName, testCases);
			tempClassCount = 0;
			log.info("Cleared Properties Successfully");
		} catch (Exception e) {
			Assert.fail("ClearAllProperties is failed - " + e.getMessage());
		}
	}

	/**
	 * to add test steps which are successful
	 * 
	 * @param stepName
	 */
	protected void stepPass(String stepName) {
		if (null != testSteps) {
			collectTests();
			testSteps.add(stepName + stepPassStatus);
		}
	}

	/**
	 * to add test steps which are not successful
	 * 
	 * @param stepName
	 */
	protected void stepFail(String stepName) {
		if (null != testSteps) {
			collectTests();
			testSteps.add(stepName + stepFailStatus);
		}
	}

	/**
	 * to add test steps which are successful with Screenshot
	 * 
	 * @param stepName
	 */
	protected void stepPassScreenShot(String stepName) {
		if (null != testSteps) {
			String snapShotName = takeScreenshot();
			collectTests();
			testSteps.add(stepName + stepPassStatus + "_" + snapShotName);
		}
	}

	/**
	 * to add test steps which are not successful with Screenshot
	 * 
	 * @param stepName
	 */
	protected void stepFailScreenShot(String stepName) {
		if (null != testSteps) {
			String snapShotName = takeScreenshot();
			collectTests();
			testSteps.add(stepName + stepFailStatus + "_" + snapShotName);

		}
	}

	/**
	 * To add test steps which are not successful with ScreenShot usage : this
	 * method to be called by only subclasses which are directly extending
	 * APIWebMobileUtility class
	 * 
	 * @param stepName
	 */
	protected void failWithSnap(String stepName) {
		if (null != testSteps) {
			String snapShotName = takeScreenshot();
			collectTests();
			testSteps.add(stepName + stepFailStatus + "_" + snapShotName);
		}
	}

	/**
	 * to add test steps which are not successful
	 * 
	 * @param stepName
	 */
	private void success(String stepName) {
		if (null != testSteps) {
			collectTests();
			testSteps.add(stepName + stepPassStatus);
		}
	}

	/**
	 * to add test steps which are successful
	 * 
	 * @param stepName
	 */
	private void failure(String stepName) {
		if (null != testSteps) {
			collectTests();
			testSteps.add(stepName + stepFailStatus);
		}
	}

	/**
	 * To collect and map all test classes to test cases and test cases to test
	 * steps.
	 * 
	 * @param index
	 */
	private void collectTests() {

		if (tempClassCount == 1) {
			tempClassCount = 2;
			tempClassName = getTestClassName();
			class2TestCasesMap.put(tempClassName, null);
		}
		if (tempTCCount == 1) {
			tempTCCount = 2;
			tempTestCaseName = getTestCaseName();
			testCase2TestStepsMap.put(tempTestCaseName, null);
			if (null != testCases) {
				testCases.add(tempTestCaseName);
			}
		}
		/*
		 * if(tcCount && tempTCCount>=2){ tcCount = false; tempTCCount++;
		 * testCase2TestStepsMap.put(tempTestCaseName+iteration, null);
		 * testCases.add(tempTestCaseName+iteration); }
		 */
	}

	@AfterSuite
	public void generateReports() {
		try {
			endTime = date.toString();
			t2 = System.currentTimeMillis();
			totalTime = ((t2 - t1) / 1000) / 60;

			if (totalTime == 0) {
				totalTime = 1;
			}
			log.info("****** Total:" + totalTestCount + " Passed:" + totalTestPassed + " Failed:" + totalTestFailed);
			mapTestCasesToStatus();
			mapTestClassesToStatus();
			generateHTMLIndexFile();
			generateHTMLFileForTestSteps();

		} catch (Exception e) {
			failure("Reports Generation Failed");
			log.error("Reports Generation Failed : " + e.getMessage());
		}
	}

	/**
	 * mapping test cases to status
	 */
	private void mapTestCasesToStatus() {
		for (String tcName : testCase2TestStepsMap.keySet()) {
			List<String> steps = testCase2TestStepsMap.get(tcName);

			for (String step : steps) {
				if (step.contains("It's PASS")) {
					testcase2StatusMap.put(tcName, "PASS");
				} else {
					testcase2StatusMap.put(tcName, "FAIL");
					break;
				}
			}
		}
	}

	/**
	 * mapping test classes to status
	 */
	private void mapTestClassesToStatus() {
		for (String className : class2TestCasesMap.keySet()) {
			List<String> tcs = class2TestCasesMap.get(className);

			for (String tc : tcs) {
				String tcStatus = testcase2StatusMap.get(tc);
				if (tcStatus.equals("PASS")) {
					class2StatusMap.put(className, "PASS");
				} else {
					class2StatusMap.put(className, "FAIL");
					break;
				}
			}
		}
	}

	/**
	 * to generate HTML Report index File.
	 * 
	 * @throws IOException
	 */
	private void generateHTMLIndexFile() throws Exception {

		String tempSnapPath = System.getProperty("baseDir") + reportPath + "/" + toDayDate + "/snapshots";
		FileUtils.copyFile(new File(System.getProperty("baseDir") + cssPath + "/pass.ico"),
				new File(tempSnapPath + "/pass.ico"));
		FileUtils.copyFile(new File(System.getProperty("baseDir") + cssPath + "/fail.ico"),
				new File(tempSnapPath + "/fail.ico"));
		FileUtils.copyFile(new File(System.getProperty("baseDir") + cssPath + "/org.png"),
				new File(tempSnapPath + "/org.png"));
		FileUtils.copyFile(new File(System.getProperty("baseDir") + cssPath + "/report.css"),
				new File(tempSnapPath + "/report.css"));
		FileUtils.copyFile(new File(System.getProperty("baseDir") + cssPath + "/reporttc.css"),
				new File(tempSnapPath + "/reporttc.css"));

		File file = new File(System.getProperty("baseDir") + reportPath + "/" + toDayDate + "/index.html");
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(file));

			bw.write("<!DOCTYPE html>");
			bw.write("<html> ");
			bw.write("<head> ");
			bw.write("<meta charset='UTF-8'> ");
			bw.write("<title>org automation results</title>");

			bw.write("<link rel='stylesheet' href='./snapshots/report.css' type='text/css'>");

			bw.write(
					"<script> function showDiv(testClassName) {document.getElementById(testClassName).style.display = 'block';"
							+ " var lastClickedTestClass = document.getElementById('lastClickedClass');"
							+ " if(lastClickedTestClass.value != '' && lastClickedTestClass.value!=testClassName) { document.getElementById(lastClickedTestClass.value).style.display = 'none'; } "
							+ " lastClickedTestClass.value = testClassName;" + "}" + "</script>");

			bw.write("</head> ");
			bw.write("<body> ");
			bw.write("</br>");
			bw.write("<input type='hidden' value='' id='lastClickedClass' />");

			bw.write("<table id='Logos'>");
			bw.write("<colgroup>");
			bw.write("<col style='width: 25%' />");
			bw.write("<col style='width: 25%' />");
			bw.write("<col style='width: 25%' />");
			bw.write("<col style='width: 25%' />");
			bw.write("</colgroup> ");
			bw.write("</table> ");

			bw.write("<table id='header'> ");
			bw.write("<thead> ");
			bw.write("<tr>");
			bw.write("<th class ='Logos' colspan='10'>");
			bw.write("<img align ='center' src= './snapshots/org.png' height=90 width=1000></img>");
			bw.write("</th> ");
			bw.write("</tr> ");

			bw.write("<tr class='subheading'> ");
			bw.write("<th>&nbsp;AUTOMATION RESULTS :</th> ");
			bw.write("<th>" + projectName.toUpperCase() + "</th> ");
			bw.write("<th>&nbsp;Total Execution Time&nbsp;:" + "" + "</th> ");
			bw.write("<th>&nbsp;" + totalTime + "&nbsp;mins</th>");
			bw.write("</tr> ");

			bw.write("<tr class='subheading'> ");
			bw.write("<th>&nbsp;Environment : </th> ");
			bw.write("<th>" + environment + "</th> ");
			bw.write("<th>&nbsp;Suite :</th> ");
			bw.write("<th>&nbsp;Regression</th> ");
			bw.write("</tr> ");

			bw.write("<tr class='subheading'>");
			bw.write("<th>&nbsp;ExecutionStartTime&nbsp;:</th>");
			bw.write("<th>" + startTime + "</th>");
			bw.write("<th>&nbsp;ExecutionEndTime&nbsp;:</th>");
			bw.write("<th>" + endTime + "</th>");
			bw.write("</tr> ");

			bw.write("<tr class='subheading'> ");
			bw.write("<th>&nbsp;Browser&nbsp;:</th> ");
			bw.write("<th>" + browser.toUpperCase() + "</th>");
			bw.write("<th>&nbsp;Host Name&nbsp;:</th> ");
			bw.write("<th>" + InetAddress.getLocalHost().getHostName() + "</th> ");
			bw.write("</tr> ");

			bw.write("</th>");
			bw.write("</tr>");
			bw.write("</thead>");
			bw.write("</table>");

			bw.write("<div id='wrapper1'>");
			bw.write("<div id ='nav1'>");
			bw.write(
					"<div id='colheader'>Test Class Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</div>");

			int tempCount = 1;
			for (String className : class2TestCasesMap.keySet()) {

				String tempClassStatus = class2StatusMap.get(className);
				String temptcList = "testcaselist" + tempCount;

				bw.write("<div id='scenarios'>");
				bw.write("<a href='#" + temptcList + "' onclick=showDiv('" + temptcList + "');>" + className);
				if ("PASS".equals(tempClassStatus))
					bw.write(
							"<img id='statusimage' align='right' src= './snapshots/pass.ico' width='25' height='25'/></a>");
				else
					bw.write(
							"<img id='statusimage' align='right' src= './snapshots/fail.ico' width='25' height='25'/></a>");
				bw.write("</div>");
				tempCount++;
			}
			bw.write("</div>");
			bw.write("<div id ='nav2'>");
			bw.write(
					"<div id='colheader'>Tests&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</div>");

			int tempCount1 = 1;
			for (String className : class2TestCasesMap.keySet()) {

				bw.write("<div class ='tc' id='testcaselist" + tempCount1 + "'>");
				for (String tcName : class2TestCasesMap.get(className)) {
					String temptcName = testcase2StatusMap.get(tcName);

					bw.write("<div id='scenariostc'>");
					bw.write("<a href=" + className + ".html>" + tcName);
					if ("PASS".equals(temptcName))
						bw.write(
								"<img id='statusimage' align='right' src= './snapshots/pass.ico' width='25' height='25'/></a>");
					else
						bw.write(
								"<img id='statusimage' align='right' src= './snapshots/fail.ico' width='25' height='25'/></a>");
					bw.write("</div>");
				}
				bw.write("</div>");
				tempCount1++;
			}
			bw.write("</div>");
			bw.write("</div>");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * to Generate HTML report for each class Test Steps.
	 */
	private void generateHTMLFileForTestSteps() {
		try {

			File file = null;
			BufferedWriter bw = null;

			int i = 1;
			for (Map.Entry<String, List<String>> itrSteps : class2TestCasesMap.entrySet()) {

				String key = itrSteps.getKey();

				file = new File(System.getProperty("baseDir") + reportPath + "/" + toDayDate + "/" + key + ".html");
				bw = new BufferedWriter(new FileWriter(file));

				bw.write("<!DOCTYPE html>");
				bw.write("<html> ");
				bw.write("<head> ");
				bw.write("<meta charset='UTF-8'> ");
				bw.write("<title>" + key + " Results </title>");
				bw.write("<link rel='stylesheet' href='./snapshots/reporttc.css' type='text/css'>");
				bw.write("</head> ");

				bw.write("<body> ");
				bw.write("</br>");
				bw.write("<table id='Logos'>");
				bw.write("<colgroup>");
				bw.write("<col style='width: 25%' />");
				bw.write("<col style='width: 25%' />");
				bw.write("<col style='width: 25%' />");
				bw.write("<col style='width: 25%' />");
				bw.write("</colgroup> ");
				bw.write("</table> ");

				bw.write("<table id='header'> ");
				bw.write("<thead>");
				bw.write("<tr>");
				bw.write("<th class ='Logos' colspan='10'>");
				bw.write("<img align ='center' src= './snapshots/org.png' height=90 width=1000 />");
				bw.write("</th>");
				bw.write("</tr>");
				bw.write("</thead>");
				bw.write("</table>");

				for (int k = 0; k < itrSteps.getValue().size(); k++) {

					bw.write("<table id='main'>");
					bw.write("<thead>");
					bw.write("<tr class='subheading'>");
					bw.write("<th width='80%' align='left'>" + itrSteps.getValue().get(k) + " : TEST STEPS</th>");
					bw.write("<th>STATUS</th>");
					bw.write("<th>SNAPSHOT</th>");
					bw.write("</tr>");
					bw.write("</thead>");

					for (String testStep : testCase2TestStepsMap.get(itrSteps.getValue().get(k))) {

						if (testStep.contains("It's PASS")) {
							String tempStepName = testStep.substring(0, testStep.indexOf("It's PASS"));
							bw.write("<tbody>");
							bw.write("<tr class='content2'>");
							bw.write("<td class='justified'>" + tempStepName + "</td>");

							String stanSnap = testStep.substring(testStep.indexOf("It's PASS"));

							if (stanSnap.contains(".png")) {

								String statuses[] = stanSnap.split("_");

								bw.write(
										"<td align='center'> <img src='./snapshots/pass.ico' width='25' height='25'> </td>");
								bw.write("<td align='center'><a href='" + "./snapshots/" + statuses[1]
										+ "'>View</a></td>");
								bw.write("</tr>");
								bw.write("</tbody> ");
							} else {
								bw.write(
										"<td align='center'> <img src='./snapshots/pass.ico' width='25' height='25'> </td>");
								bw.write("<td align='center'>NA</td>");
								bw.write("</tr>");
								bw.write("</tbody> ");
							}

						} else {

							String tempStepName = testStep.substring(0, testStep.indexOf("It's FAIL"));
							bw.write("<tbody>");
							bw.write("<tr class='content2'>");
							bw.write("<td class='justified'>" + tempStepName + "</td>");
							String stanSnap = testStep.substring(testStep.indexOf("It's FAIL"));

							if (stanSnap.contains(".png")) {

								String statuses[] = stanSnap.split("_");
								bw.write(
										"<td align='center'> <img src='./snapshots/fail.ico' width='25' height='25'> </td>");
								bw.write("<td align='center'><a href='" + "./snapshots/" + statuses[1]
										+ "'>View</a></td>");
								bw.write("</tr>");
								bw.write("</tbody> ");
							} else {
								bw.write(
										"<td align='center'> <img src='./snapshots/fail.ico' width='25' height='25'> </td>");
								bw.write("<td align='center'>NA</td>");
								bw.write("</tr>");
								bw.write("</tbody> ");
							}
						}
						i = i + 1;
					}
				}
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Helper Method to get Test Case Name dynamically while execution */
	private String getTestCaseName() {

		StackTraceElement e[] = Thread.currentThread().getStackTrace();

		for (int i = 0; i < e.length; i++) {
			if (e[i].getMethodName().equals("invoke0")) {
				return e[i - 1].getMethodName();
			}
		}
		return "";
	}

	/* Helper Method to get Test Class Name dynamically while execution */
	private String getTestClassName() {

		StackTraceElement e[] = Thread.currentThread().getStackTrace();

		for (int i = 0; i < e.length; i++) {
			if (e[i].getMethodName().equals("invoke0")) {

				String exactClassName[] = e[i - 1].getClassName().split("\\.");
				return exactClassName[exactClassName.length - 1];
			}
		}
		return "";
	}

	/**
	 * @Description takes Screenshot
	 */
	public String takeScreenshot() {

		String screenshotName = "";
		try {

			screenshotName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new GregorianCalendar().getTime())
					+ ".png";
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String file = System.getProperty("baseDir") + reportPath + "/" + toDayDate + "/snapshots/" + screenshotName;
			FileUtils.copyFile(scrFile, new File(file));
			log.info("Captured Screenshot successfully");
		} catch (Exception e) {
			log.error("takeScreenshot() : Unable to capture Screenshot");
		}
		return screenshotName;
	}

	public String randomString(int size) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuffer sb = new StringBuffer();
		random = new Random();
		for (int i = 0; i <= size; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		return sb.toString();
	}

	protected String todayDatenTime() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy'T'HH-mm-ss");
		String tdate = sdf.format(date);
		return tdate;
	}

	public String randomEmailid(int size) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuffer sb = new StringBuffer();
		random = new Random();
		for (int i = 0; i <= size; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		sb.append("@gmail.com");
		return sb.toString();
	}

	public String randomMobileNumber(double size) {
		StringBuffer sb = new StringBuffer();
		random = new Random();
		for (int i = 1; i <= size; i++) {
			sb.append(random.nextInt(9));
		}
		String s = sb.toString();
		return s;
	}

	public int randomAge() {
		random = new Random();
		int age = random.nextInt(99);
		if (age < 18) {
			age = age + 10;
		}
		return age;
	}

}
