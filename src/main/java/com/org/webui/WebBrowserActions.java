package com.org.webui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import com.org.common.APIWebMobileUtility;

/**
 * <b>WebBrowserActions</b> class has methods which helps to automate web
 * browser actions.</br>
 * This class should be extended by all <b>Test Classes</b> to automate UI test
 * scenarios.</br>
 * <b>WebBrowserActions</b> class is extending <b>APIWebMobileUtility</b> class
 * which loads all <b>properties</b>, has <b>DB</b> utility methods and has
 * methods to generate <b>HTML reports</b>.
 *
 */
public class WebBrowserActions extends APIWebMobileUtility {

	private static final String driverPath = "/src/test/resource/drivers";
	private Alert alert = null;
	private String elementLocatorValue = null;

	private static final Logger log = Logger.getLogger(WebBrowserActions.class);

	/**
	 * Launch application.
	 * 
	 * @param url
	 * 
	 */
	public void openURL(String url) {
		try {
			if (url != null) {
				driver.get(url);
				waitForPageToBeLoad();
				log.info("Application launched successfully : " + url);
				stepPass("Application launched successfully");
			}
		} catch (Exception e) {
			failWithSnap("Failed to launch application");
			log.error("openURL() : Failed to launch application : " + url);
			Assert.fail("openURL() : Failed to launch application : " + url);
		}
	}

	/**
	 * Opens given URL in new TAB.
	 * 
	 * @param url
	 */
	public void openURLInNewTab(String url) {
		try {
			if (url != null) {
				pressKeyboardKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_T);
				driver.get(url);
				waitForPageToBeLoad();
				stepPass("Application launched successfully in new tab");
				log.info("Application launched successfully in new tab : " + url);
			}
		} catch (Exception e) {
			failWithSnap("Failed to launch application in new tab");
			log.error("openURLInNewTab() : Failed to launch application in new tab : " + url);
			Assert.fail("openURLInNewTab() : Failed to launch application in new tab : " + url);
		}
	}

	/**
	 * Closes current window.
	 */
	public void closeBrowser() {
		try {
			if (null != driver) {
				driver.close();
				stepPass("Current window closed successfully");
				log.info("Current window closed successfully");
			} else {
				stepFail("Driver is not available to close current Window");
				log.error("Driver is not available to close current Window");
			}
		} catch (Exception e) {
			stepFail("Failed to close current Window");
			log.error("closeCurrentWindow() : Failed to close current Window");
			Assert.fail("closeCurrentWindow() : Failed to close current Window");
		}
	}

	/**
	 * Closes all associated windows of driver.
	 */
	public void quitBrowser() {
		try {
			if (null != driver) {
				driver.quit();
				stepPass("Current window closed successfully");
				log.info("Current window closed successfully");
			} else {
				stepFail("Driver is not available to quit");
				log.error("Driver is not available to quit");
			}
		} catch (Exception e) {
			stepFail("Failed to close current Window");
			log.error("quitBrowser() : Failed to quit driver");
			Assert.fail("quitBrowser() : Failed to quit driver");
		}
	}

	/**
	 * Verifies title of the page.
	 * 
	 * @param expectedTitle
	 * 
	 */
	public void verifyWindowTitle(String expectedTitle) {
		String actualTitle = null;
		try {
			waitForPageToBeLoad();
			actualTitle = driver.getTitle().trim();
			if (expectedTitle.equals(actualTitle)) {
				stepPass("Expected Title : '" + expectedTitle + "' and Actual Title '" + actualTitle + "' matched.'");
				log.info("Expected Title : '" + expectedTitle + "' and Actual Title '" + actualTitle + "' matched.'");
			} else {
				failWithSnap("Expected Title : '" + expectedTitle + "' and Actual Title '" + actualTitle
						+ "' not matched.'");
				log.error("Expected Title : '" + expectedTitle + "' and Actual Title '" + actualTitle
						+ "' not matched.'");
				Assert.fail("Expected Title : '" + expectedTitle + "' and Actual Title '" + actualTitle
						+ "' not matched.'");
			}
		} catch (Exception e) {
			failWithSnap("Failed to verify Title");
			log.error("verifyWindowTitle() : Failed to verify Title");
			Assert.fail("verifyWindowTitle() : Failed to verify Title");
		}
	}

	/**
	 * Enter text into textbox or textarea.
	 * 
	 * @param targetElement
	 * @param testData
	 * 
	 */
	public void enterText(WebElement targetElement, String testData) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				targetElement.clear();
				targetElement.sendKeys(testData);
				stepPass("Text entered : " + testData);
				log.info("Entered text into : " + elementLocatorValue);
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("enterText() : element not found on the page - " + elementLocatorValue);
			Assert.fail("enterText() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Clear text of the Textbox or Textarea.
	 * 
	 * @param targetElement
	 *
	 */
	public void clearText(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				targetElement.clear();
				stepPass("Cleared text");
				log.info("Cleared text of : " + elementLocatorValue);
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("clearText() : element not found on the page - " + elementLocatorValue);
			Assert.fail("clearText() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Get attribute value of any WebElement.
	 * 
	 * @param targetElement
	 * @param attributeType
	 * @return String
	 * 
	 */
	public String getAttributeValue(WebElement targetElement, String attributeType) {
		String value = null;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				value = targetElement.getAttribute(attributeType);
				stepPass("'" + value + "' is value of attribute type '" + attributeType + "' of : "
						+ elementLocatorValue);
				log.info("'" + value + "' is value of attribute type '" + attributeType + "' of : "
						+ elementLocatorValue);
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("getAttributeValue() : element not found on the page - " + elementLocatorValue);
			Assert.fail("getAttributeValue() : element not found on the page - " + elementLocatorValue);
		}
		return value;
	}

	/**
	 * Verify value of the Textbox and Textarea.
	 * 
	 * @param targetElement
	 * @param expectedText
	 * 
	 */
	public void verifyTextboxValue(WebElement targetElement, String expectedText) {
		String actualValue = null;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				actualValue = targetElement.getAttribute("value").trim();
				if (expectedText.equals(actualValue)) {
					stepPass("Expected : '" + expectedText + "' and Actual : '" + actualValue + "' values are matched: "
							+ elementLocatorValue);
					log.info("Expected : '" + expectedText + "' and Actual : '" + actualValue + "' values are matched: "
							+ elementLocatorValue);
				} else {
					failWithSnap("Expected : '" + expectedText + "' and Actual : '" + actualValue
							+ "' values are not matched: " + elementLocatorValue);
					log.info("Expected : '" + expectedText + "' and Actual : '" + actualValue
							+ "' values are not matched: " + elementLocatorValue);
					Assert.fail("Expected : '" + expectedText + "' and Actual : '" + actualValue
							+ "' values are not matched: " + elementLocatorValue);
				}
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("verifyTextboxValue() : element not found on the page - " + elementLocatorValue);
			Assert.fail("verifyTextboxValue() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Click on button or link.
	 * 
	 * @param targetElement
	 * 
	 */
	public void clickElement(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				targetElement.click();
				stepPass("Clicked on element");
				log.info("Clicked on : " + elementLocatorValue);
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("clickElement() : element not found on the page - " + elementLocatorValue);
			Assert.fail("clickElement() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Click on button or link and waits for page to be loaded completely.
	 * 
	 * @param targetElement
	 * 
	 */
	public void clicknWaitForPageToLoad(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				targetElement.click();
				waitForPageToBeLoad();
				stepPass("Clicked on element and waiting for page to load");
				log.info("Clicked on : " + elementLocatorValue);
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("clicknWaitForPageToLoad() : element not found on the page - " + elementLocatorValue);
			Assert.fail("clicknWaitForPageToLoad() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Click on button or link by java Script.
	 * 
	 * @param targetElement
	 * 
	 */
	public void clickElementByJS(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetElement);
			}
			stepPass("Clicked on using JavascriptExecutor");
			log.info("Clicked on : " + elementLocatorValue);
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("clickElementByJS() : element not found on the page - " + elementLocatorValue);
			Assert.fail("clickElementByJS() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Get WebElement text.
	 * 
	 * @param targetElement
	 * @return String
	 * 
	 */
	public String getElementText(WebElement targetElement) {
		String text = null;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				text = targetElement.getText().trim();
				stepPass("Text : " + text + " of the given element");
				log.info("Text : " + text + " of the given element : " + elementLocatorValue);
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("getElementText() : element not found on the page - " + elementLocatorValue);
			Assert.fail("getElementText() : element not found on the page - " + elementLocatorValue);
		}
		return text;
	}

	/**
	 * Selects radio button and if button is already selected, skips the action.
	 * 
	 * @param targetElement
	 * 
	 */
	public void selectRadioButton(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				if (targetElement.isSelected()) {
					stepPass("Radio button is selected by default");
					log.info("Radio button is selected by default : " + elementLocatorValue);
				} else {
					targetElement.click();
					stepPass("Radio button is selected : " + elementLocatorValue);
					log.info("Radio button is selected : " + elementLocatorValue);
				}
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("selectRadioButton() : element not found on the page - " + elementLocatorValue);
			Assert.fail("selectRadioButton() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * To check whether radio button is selected or not.
	 * 
	 * @param targetElement
	 * @return boolean value
	 * 
	 */
	public boolean isRadioButtonSelected(WebElement targetElement) {
		boolean flag = false;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				if (targetElement.isSelected()) {
					stepPass("Radio button is selected");
					log.info("Radio button is selected : " + elementLocatorValue);
					flag = true;
				} else {
					stepPass("Radio button is not selected");
					log.info("Radio button is not selected : " + elementLocatorValue);
					flag = false;
				}
			}
		} catch (Exception e) {
			stepPass("Radio button is not found and continuing execution");
			log.error("isRadioButtonSelected() : element not found on the page - " + elementLocatorValue);
		}
		return flag;
	}

	/**
	 * To select check box.
	 * 
	 * @param targetElement
	 * 
	 */
	public void selectCheckBox(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				if (targetElement.isSelected()) {
					stepPass("Check box is checked by default");
					log.info("Check box is checked by default : " + elementLocatorValue);
				} else {
					targetElement.click();
					stepPass("Check box is checked");
					log.info("Check box is checked : " + elementLocatorValue);
				}
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("selectCheckBox() : element not found on the page - " + elementLocatorValue);
			Assert.fail("selectCheckBox() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * To unCheck the check box.
	 * 
	 * @param targetElement
	 * 
	 */
	public void deSelectCheckBox(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				if (targetElement.isSelected()) {
					targetElement.click();
					stepPass("Check box is unChecked");
					log.info("Check box is unChecked : " + elementLocatorValue);
				} else {
					stepPass("Check box is unChecked by default");
					log.info("Check box is unChecked by default : " + elementLocatorValue);
				}
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("deSelectCheckBox() : element not found on the page - " + elementLocatorValue);
			Assert.fail("deSelectCheckBox() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * To check whether check box is checked or not.
	 * 
	 * @param targetElement
	 * @return boolean value
	 * 
	 */
	public boolean isCheckBoxSelected(WebElement targetElement) {
		boolean flag = false;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				if (targetElement.isSelected()) {
					stepPass("Check box is checked ");
					log.info("Check box is checked : " + elementLocatorValue);
					flag = true;
				} else {
					stepPass("Check box is not checked");
					log.info("Check box is not checked : " + elementLocatorValue);
					flag = false;
				}
			}
		} catch (Exception e) {
			stepPass("Check box is not found and continuing execution");
			log.error("isCheckBoxSelected() : element not found on the page - " + elementLocatorValue);
		}
		return flag;
	}

	/**
	 * Select one or more option(s) from the DropDown. To select multiple values
	 * pass options separated by ; (semicolon) symbol .
	 * 
	 * @param targetElement
	 * @param option
	 * @param selectBy
	 *            : index/text/value
	 * 
	 */
	public void selectOneOrMultipleOptions(WebElement targetElement, String optionValue, String selectBy) {
		String options[] = null;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				Select list = new Select(targetElement);
				if (list.isMultiple() && optionValue.contains(";")) {
					options = optionValue.split(";");
					for (String opt : options) {
						selectOption(opt, selectBy, list);
					}
				} else {
					selectOption(optionValue, selectBy, list);
				}
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("selectOneOrMultipleOptions() : element not found on the page - " + elementLocatorValue);
			Assert.fail("selectOneOrMultipleOptions() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Verifies the selected option with expected option.
	 * 
	 * @param targetElement
	 * @param expectedOption
	 * 
	 */
	public void verifySelectedOption(WebElement targetElement, String expectedOption) {
		String actualSelectedOption = null;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				Select list = new Select(targetElement);
				actualSelectedOption = list.getFirstSelectedOption().getText().trim();
				if (expectedOption.equals(actualSelectedOption)) {
					stepPass("Expected : '" + expectedOption + "' and Actual : '" + actualSelectedOption
							+ "' values are matched");
					log.info("Expected : '" + expectedOption + "' and Actual : '" + actualSelectedOption
							+ "' values are matched: " + elementLocatorValue);
				} else {
					failWithSnap("Expected : '" + expectedOption + "' and Actual : '" + actualSelectedOption
							+ "' values are not matched");
					log.info("Expected : '" + expectedOption + "' and Actual : '" + actualSelectedOption
							+ "' values are not matched: " + elementLocatorValue);
					Assert.fail("Expected : '" + expectedOption + "' and Actual : '" + actualSelectedOption
							+ "' values are not matched: " + elementLocatorValue);
				}
			}
		} catch (Exception e) {
			failWithSnap("Element not found on the page");
			log.error("verifySelectedOption() : element not found on the page - " + elementLocatorValue);
			Assert.fail("verifySelectedOption() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Used to select the given option from dropDown.
	 * 
	 * @param option
	 * @param type
	 * @param element
	 * 
	 */
	private void selectOption(String option, String type, Select element) {
		try {
			if (type.toUpperCase().equals("INDEX")) {
				element.selectByIndex(Integer.parseInt(option));
			} else if (type.toUpperCase().equals("TEXT")) {
				element.selectByVisibleText(option);
			} else {
				element.selectByValue(option);
			}

			stepPass("Given Option Selected : " + option);
			log.info("selectOneOrMultipleOptions() : Option Selected : " + option + " - " + element);

		} catch (Exception e) {
			failWithSnap("Unable to select Option : " + option);
			log.error("selectOneOrMultipleOptions() : Unable to select Option : " + option + " - " + element);
			Assert.fail("selectOneOrMultipleOptions() : Unable to select Option : " + option + " - " + element);
		}
	}

	/**
	 * Switches to Next TAB.
	 */
	public void switchToNextTab() {
		try {
			pressKeyboardKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_TAB);
			driver.switchTo().window(driver.getWindowHandle());
			waitForPageToBeLoad();
			stepPass("Switched to next TAB");
			log.info("Switched to next TAB");
		} catch (Exception e) {
			failWithSnap("Failed to Switch next TAB");
			log.error("switchToNextTab() : Failed to Switch next TAB");
			Assert.fail("switchToNextTab() : Failed to Switch next TAB");
		}
	}

	/**
	 * Switches to Previous TAB.
	 */
	public void switchToPreviousTab() {
		try {
			pressKeyboardKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_TAB);
			driver.switchTo().window(driver.getWindowHandle());
			waitForPageToBeLoad();
			stepPass("Switched to previous TAB");
			log.info("Switched to previous TAB");
		} catch (Exception e) {
			failWithSnap("Failed to Switch previous TAB");
			log.error("switchToPreviousTab() : Failed to Switch previous TAB");
			Assert.fail("switchToPreviousTab() : Failed to Switch previous TAB");
		}
	}

	/**
	 * Switches to the frame using Xpath.
	 * 
	 * @param targetElement.
	 * 
	 */
	public void switchToFrame(WebElement targetFrame) {
		try {
			if (targetFrame != null) {
				syncWithAction(targetFrame);
				driver.switchTo().frame(targetFrame);
				stepPass("Switched to expected frame by Xpath");
				log.info("Switched to expected frame by Xpath - " + printLocator(targetFrame));
			}
		} catch (Exception e) {
			failWithSnap("Unable to switch to expected frame by Xpath");
			log.error("switchToFrame() : Unable to switch to expected frame by Xpath - " + printLocator(targetFrame));
			Assert.fail("switchToFrame() : Unable to switch to expected frame by Xpath - " + printLocator(targetFrame));
		}
	}

	/**
	 * Switches to the frame using Id or Name.
	 * 
	 * @param idOrName.
	 * 
	 */
	public void switchToFrame(String idOrName) {
		try {
			driver.switchTo().frame(idOrName);
			stepPass("Switched to expected frame successfully by Id or Name");
			log.info("Switched to expected frame successfully by Id or Name: '" + idOrName + "'");
		} catch (Exception e) {
			failWithSnap("Unable to switch to expected frame by Id or Name");
			log.error("switchToFrame() : Unable to switch to expected frame by Id or Name: '" + idOrName + "'");
			Assert.fail("switchToFrame() : Unable to switch to expected frame by Id or Name: '" + idOrName + "'");
		}
	}

	/**
	 * Switches to the frame using index.
	 * 
	 * @param index.
	 * 
	 */
	public void switchToFrame(int index) {
		try {
			driver.switchTo().frame(index);
			stepPass("Switched to expected frame successfully by index");
			log.info("Switched to expected frame successfully by index '" + index + "'");
		} catch (Exception e) {
			failWithSnap("Unable to switch to expected frame by Index");
			log.error("switchToFrame() : Unable to switch to expected frame by Index '" + index + "'");
			Assert.fail("switchToFrame() : Unable to switch to expected frame by Index '" + index + "'");
		}
	}

	/**
	 * Switches to the window based on given window number.</br>
	 * Example : Pass 4 to switch to 4th window.
	 * 
	 * @param windowNumber
	 * 
	 */
	public void switchToWindow(int windowNumber) {
		int i = 0;
		try {
			Set<String> windowHandles = new LinkedHashSet<String>();
			windowHandles = driver.getWindowHandles();
			for (String handle : windowHandles) {
				if (windowNumber == i) {
					driver.switchTo().window(handle);
					break;
				} else {
					i++;
					continue;
				}
			}
			stepPass("Switched to expected window successfully by index");
			log.info("Switched to expected window successfully by index '" + windowNumber + "'");
		} catch (Exception e) {
			failWithSnap("Unable to switch to expected window by Index");
			log.error("switchToWindow() : Unable to switch to expected window by Index '" + windowNumber + "'");
			Assert.fail("switchToWindow() : Unable to switch to expected window by Index '" + windowNumber + "'");
		}
	}

	/**
	 * Switches to the ' default content ' from frame or window.
	 */
	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			stepPass("Switched to default content successfully");
			log.info("Switched to default content successfully");
		} catch (Exception e) {
			failWithSnap("Unable to switch to default content");
			log.error("switchToDefaultContent() : Unable to switch to default content");
		}
	}

	/**
	 * Switches to alert if exists.
	 */
	public void switchToAlert() {
		try {
			alertCheck();
			stepPass("Alert is present and switched successfully");
			log.info("Alert is present and switched successfully");
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("switchToAlert() : Alert is not present on the page");
			Assert.fail("switchToAlert() : Alert is not present on the page");
		}
	}

	/**
	 * Accepts the alert present on page.</br>
	 * Usage : switchToAlert() should be called before using this.
	 */
	public void acceptAlert() {
		try {
			alert.accept();
			stepPass("Alert accepted successfully");
			log.info("Alert accepted successfully");
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("acceptAlert() : Alert is not present on the page");
			Assert.fail("acceptAlert() : Alert is not present on the page");
		}
	}

	/**
	 * Dismiss the alert present on page.</br>
	 * Usage : switchToAlert() should be called before using this.
	 */
	public void dismissAlert() {
		try {
			alert.dismiss();
			stepPass("Alert dismissed successfully");
			log.info("Alert dismissed successfully");
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("dismissAlert() : Alert is not present on the page");
			Assert.fail("dismissAlert() : Alert is not present on the page");
		}
	}

	/**
	 * Accepts if alert present.
	 */

	public void acceptIfAlertPresent() {
		try {
			alertCheck().accept();
			stepPass("Alert is present and accepted successfully");
			log.info("Alert is present and accepted successfully");
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("acceptIfAlertPresent() : Alert is not present on the page");
		}
	}

	/**
	 * Dismiss if alert present.
	 */
	public void dismissIfAlertPresent() {
		try {
			alertCheck().dismiss();
			stepPass("Alert is present and dismissed successfully");
			log.info("Alert is present and dismissed successfully");
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("dismissIfAlertPresent() : Alert is not present on the page");
		}
	}

	/**
	 * Gets alert message.</br>
	 * Usage : switchToAlert() should be called before using this.
	 * 
	 * @return String
	 * 
	 * 
	 */
	public String getAlertMessage() {
		String msg = null;
		try {
			msg = alert.getText().trim();
			stepPass("Alert text is : " + msg);
			log.info("Alert text is : " + msg);
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("getAlertMessage() : Alert is not present on the page");
			Assert.fail("getAlertMessage() : Alert is not present on the page");
		}
		return msg;
	}

	/**
	 * Verifies alert message.</br>
	 * Usage : switchToAlert() should be called before using this.
	 * 
	 * @param expectedMessage
	 * 
	 */
	public void verifyAlertMessage(String expectedMessage) {
		String actualMessage = null;
		try {

			actualMessage = alert.getText().trim();
			if (actualMessage.equals(expectedMessage)) {
				stepPass("Expected : '" + expectedMessage + "' and Actual : '" + actualMessage
						+ "' alert messages are matchd.");
				log.info("Expected : '" + expectedMessage + "' and Actual : '" + actualMessage
						+ "' alert messages are matchd.");
			} else {
				failWithSnap("Expected : '" + expectedMessage + "' and Actual : '" + actualMessage
						+ "' alert messages are not matchd.");
				log.error("Expected : '" + expectedMessage + "' and Actual : '" + actualMessage
						+ "' alert messages are not matchd.");
			}
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("verifyAlertMessage() : Alert is not present on the page");
			Assert.fail("verifyAlertMessage() : Alert is not present on the page");
		}
	}

	/**
	 * Verifies alert message contains text.</br>
	 * Usage : switchToAlert() should be called before using this.
	 * 
	 * @param expectedMessage
	 */
	public void verifyAlertMessageContainsText(String expectedMessage) {
		String actualMessage = null;
		try {
			actualMessage = alert.getText();
			if (actualMessage.contains(expectedMessage)) {
				stepPass("Actual : '" + actualMessage + "' message contains Expected : '" + expectedMessage
						+ "' message");
				log.info("Actual : '" + actualMessage + "' message contains Expected : '" + expectedMessage
						+ "' message");
			} else {
				failWithSnap("Actual : '" + actualMessage + "' message not contains Expected : '" + expectedMessage
						+ "' message");
				log.error("Actual : '" + actualMessage + "' message not contains Expected : '" + expectedMessage
						+ "' message");
			}
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("verifyAlertMessageContainsText() : Alert is not present on the page");
			Assert.fail("verifyAlertMessageContainsText() : Alert is not present on the page");
		}
	}

	/**
	 * Enters text to prompt alert type.</br>
	 * Usage : switchToAlert() should be called before using this.
	 * 
	 * @param text
	 *
	 */
	public void sendTextToAlert(String text) {
		try {
			alert.sendKeys(text);
			stepPass("Text entered : " + text);
			log.info("Text entered : " + text);
		} catch (Exception e) {
			failWithSnap("Alert is not present on the page");
			log.error("sendTextToAlert() : Alert is not present on the page");
			Assert.fail("sendTextToAlert() : Alert is not present on the page");
		}
	}

	/**
	 * Verify whether alert is present or not.
	 * 
	 * @return boolean
	 * 
	 */
	public boolean isAlertPresent() {
		boolean flag = false;
		try {
			alertCheck();
			flag = true;
			stepPass("Alert is present on the page.");
			log.info("Alert is present on the page.");
		} catch (Exception e) {
			stepPass("Alert is not present and continuing execution");
			log.error("isAlertPresent() : Alert is not present on the page.");
		}
		return flag;
	}

	/**
	 * Scrolls element into view.
	 * 
	 * @param targetElement
	 * 
	 */
	public void scrollToElement(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetElement);
			}
			stepPass("Scrolled element into view : " + elementLocatorValue);
			log.info("Scrolled element into view : " + elementLocatorValue);
		} catch (Exception e) {
			failWithSnap("Element not found on the page - " + elementLocatorValue);
			log.error("scrollToElement() : element not found on the page - " + elementLocatorValue);
			Assert.fail("scrollToElement() : element not found on the page - " + elementLocatorValue);
		}
	}

	/**
	 * Scrolls page up or down with the given x and y coordinates. </br>
	 * Example : (0,150) - scroll page down and (0, -150) - scroll page up.
	 * 
	 * @param xCoordinates
	 * @param yCoordinates
	 */
	public void scrollPageUpAndDown(int xCoordinates, int yCoordinates) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(" + xCoordinates + "," + yCoordinates + ")");
			stepPass("Page scrolled : " + xCoordinates + " and " + yCoordinates);
			log.info("Page scrolled : " + xCoordinates + " and " + yCoordinates);
		} catch (Exception e) {
			failWithSnap("Unaable to scroll page up or down");
			log.error("scrollPageUpAndDown() : Unaable to scroll page up or down");
			Assert.fail("scrollPageUpAndDown() : Unaable to scroll page up or down");
		}
	}

	/**
	 * Checks whether element is displayed or not.
	 * 
	 * @param targetElement
	 * @return boolean
	 * 
	 */
	public boolean isElementDisplayed(WebElement targetElement) {
		boolean flag = false;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				if (targetElement.isDisplayed()) {
					stepPass("Element is displayed");
					log.info("Element is displayed : " + elementLocatorValue);
					flag = true;
				} else {
					stepPass("Element not is displayed");
					log.info("Element not is displayed : " + elementLocatorValue);
					flag = false;
				}
			}
		} catch (Exception e) {
			stepPass("Element not displayed and continuing with execution");
			log.error("isElementDisplayed() : element not found on the page - " + elementLocatorValue);
		}
		return flag;
	}

	/**
	 * Checks whether element is enabled or not.
	 * 
	 * @param targetElement
	 * @return boolean
	 * 
	 */
	public boolean isElementEnabled(WebElement targetElement) {
		boolean flag = false;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				if (targetElement.isEnabled()) {
					stepPass("Element is enabled");
					log.info("Element is enabled : " + elementLocatorValue);
					flag = true;
				} else {
					stepPass("Element not is enabled");
					log.info("Element not is enabled : " + elementLocatorValue);
					flag = false;
				}
			}
		} catch (Exception e) {
			stepPass("Element not Enabled and continuing with execution");
			log.error("isElementEnabled() : element not found on the page - " + elementLocatorValue);
		}
		return flag;
	}

	/**
	 * Checks whether element is present or not.
	 * 
	 * @param targetElement
	 * @return boolean
	 * 
	 */
	public boolean isElementPresent(WebElement targetElement) {
		boolean flag = false;
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				stepPass("Element is present");
				log.info("Element is present: " + elementLocatorValue);
				flag = true;
			}
		} catch (Exception e) {
			stepPass("Element not present and continuing with execution");
			log.error("isElementPresent() : element not found on the page - " + elementLocatorValue);
		}
		return flag;
	}

	/**
	 * Wait for page to load completely. This method waits for maximum 120 seconds
	 * by polling for every 5 seconds .
	 */
	public void waitForPageToBeLoad() {
		int pageLoadTimeOut = 0;
		try {
			pageLoadTimeOut = Integer.parseInt(getValue("pageLoadTimeOutInSec"));

			if (null != getValue("pageLoadTimeOutInSec")) {
				int time = 0;
				while (time <= pageLoadTimeOut) {
					Thread.sleep(2000);
					if (!((JavascriptExecutor) driver).executeScript("return document.readyState")
							.equals("complete")) {
						time = time + 5;
					} else {
						break;
					}
				}
				stepPass("Page loaded successfully in : " + time + 5 + " secs");
				log.info("Page loaded successfully in : " + time + 5 + " secs");
			} else {
				Assert.fail("'pageLoadTimeOutInSec' value is null in property file");
			}

		} catch (Exception e) {
			e.printStackTrace();
			failWithSnap("Unable to load page in given time in " + pageLoadTimeOut + " mins");
			log.error("waitForPageToBeLoad() : Unable to load page in given time in " + pageLoadTimeOut + " mins", e);
		}
	}

	/**
	 * Wait for element to be visible on the page. This method waits for given
	 * maximum seconds by polling for every 1 seconds .
	 * 
	 * @param targetElement
	 * @param timeOutInSeconds
	 * 
	 */
	public void waitForElementToBeVisible(WebElement targetElement, int timeOutInSeconds) {
		try {
			if (targetElement != null) {

				elementLocatorValue = printLocator(targetElement);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Duration.ofSeconds(timeOutInSeconds)).pollingEvery(Duration.ofSeconds(1))
						.ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.visibilityOf(targetElement));
				stepPass("Expected element is found on the page");
				log.info("Expected element is found on the page : " + elementLocatorValue);
			}
		} catch (Exception e) {
			failWithSnap("Expected element is not found on the page");
			log.error(
					"waitForElementToBeVisible() : Expected element is not found on the page : " + elementLocatorValue);
			Assert.fail(
					"waitForElementToBeVisible() : Expected element is not found on the page : " + elementLocatorValue);
		}
	}

	/**
	 * Highlights the element.
	 * 
	 * @param targetElement
	 * 
	 */
	public void highlightElement(WebElement targetElement) {
		try {
			if (targetElement != null) {
				elementLocatorValue = printLocator(targetElement);
				syncWithAction(targetElement);
				((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1]);",
						targetElement, "color: red; border: 3px solid red;");
				stepPass("Element highlighted");
				log.info("Element highlighted : " + elementLocatorValue);
			}
		} catch (Exception e) {
			failWithSnap("Expected element is not found on the page");
			log.error("highlightElement() : Expected element is not found on the page : " + elementLocatorValue);
		}
	}

	/**
	 * Used to print element locator.
	 * 
	 * @param targetElement
	 * @return String
	 *
	 */
	private String printLocator(WebElement targetElement) {
		String result = null;
		try {
			if (null != targetElement && targetElement.toString().contains("->")) {
				String target[] = targetElement.toString().split("->");
				result = target[1].trim().substring(0, target[1].length() - 2);
			}
		} catch (Exception e) {
			log.error("printLocator is failed : " + e.getMessage());
		}
		return result;
	}

	@AfterClass
	public void closeSetUp() {
		data.clear();
		log.info("End of the " + super.getClass().getName().toUpperCase() + " Class TestCases\n");
	}

	public void openBrowser(String browser) {
		try {
			if (null != browser) {

				Browsers browserName = Browsers.valueOf(browser.toUpperCase());
				switch (browserName) {
				case FF:
					startFirefox();
					break;
				case CHROME:
					startChrome();
					break;
				case IE:
					startIE();
					break;
				case SAFARI:
					startSafari();
					break;
				}
				driver.manage().window().maximize();
			}
		} catch (IllegalArgumentException e) {
			stepFail("Oops !!!! Invalid or UnSupported browser type");
			log.error("Oops !!!! Invalid or UnSupported browser type");
		}
	}

	private void startIE() {

		if (Platform.getCurrent().equals(Platform.WIN8) || Platform.getCurrent().equals(Platform.WIN8_1)
				|| Platform.getCurrent().equals(Platform.WINDOWS) || Platform.getCurrent().equals(Platform.WIN10)) {

			log.info("Opening IE Browser On : " + Platform.getCurrent());
			System.setProperty("webdriver.ie.driver",
					System.getProperty("baseDir") + driverPath + "/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if (Platform.getCurrent().equals(Platform.MAC)) {

			log.info("Opening IE Browser On : " + Platform.getCurrent());
			System.setProperty("webdriver.ie.driver", System.getProperty("baseDir") + driverPath + "/IEDriverServer");
			driver = new InternetExplorerDriver();
		} else if (Platform.getCurrent().equals(Platform.LINUX)) {

			log.info("Opening IE Browser On : " + Platform.getCurrent());
			System.setProperty("webdriver.ie.driver", System.getProperty("baseDir") + driverPath + "/IEDriverServer");
			driver = new InternetExplorerDriver();
		} else {
			stepFail("Unsupported Operating System");
			log.error("Unsupported Operating System");
		}
	}

	private void startChrome() {

		if (Platform.getCurrent().equals(Platform.WIN8) || Platform.getCurrent().equals(Platform.WIN8)
				|| Platform.getCurrent().equals(Platform.XP) || Platform.getCurrent().equals(Platform.WIN10)) {

			log.info("Opening Chrome Browser On : " + Platform.getCurrent());
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("baseDir") + driverPath + "/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (Platform.getCurrent().equals(Platform.MAC)) {

			log.info("Opening Chrome Browser On : " + Platform.getCurrent());
			System.setProperty("webdriver.chrome.driver", System.getProperty("baseDir") + driverPath + "/chromedriver");
			driver = new ChromeDriver();
		} else if (Platform.getCurrent().equals(Platform.LINUX)) {

			log.info("Opening Chrome Browser On : " + Platform.getCurrent());
			System.setProperty("webdriver.chrome.driver", System.getProperty("baseDir") + driverPath + "/chromedriver");
			driver = new ChromeDriver();
		} else {
			stepFail("Unsupported Operating System");
			log.error("Unsupported Operating System");
		}

	}

	private void startFirefox() {
		log.info("Opening Firefox Browser On : " + Platform.getCurrent());
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
	}

	private void startSafari() {

		if (Platform.getCurrent().equals(Platform.WIN8) || Platform.getCurrent().equals(Platform.WIN8)
				|| Platform.getCurrent().equals(Platform.XP) || Platform.getCurrent().equals(Platform.WIN10)) {

			log.info("Opening Safari Browser On : " + Platform.getCurrent());
			System.setProperty("webdriver.safari.driver",
					System.getProperty("baseDir") + driverPath + "/safaridriver.exe");
			driver = new SafariDriver();
		} else if (Platform.getCurrent().equals(Platform.MAC)) {

			log.info("Opening Safari Browser On : " + Platform.getCurrent());
			driver = new SafariDriver();
		} else if (Platform.getCurrent().equals(Platform.LINUX)) {

			log.info("Opening Safari Browser On : " + Platform.getCurrent());
			System.setProperty("webdriver.safari.driver", System.getProperty("baseDir") + driverPath + "/safaridriver");
			driver = new SafariDriver();
		} else {
			stepFail("Unsupported Operating System");
			log.error("Unsupported Operating System");
		}
	}

	private enum Browsers {
		FF, IE, CHROME, SAFARI;
	}

	/**
	 * Used to initiate driver for page objects in a class.
	 * 
	 * @param className
	 * @return driverForClass
	 * 
	 */
	protected <T> T initializeDriverForPage(Class<T> className) {
		return PageFactory.initElements(driver, className);
	}

	/**
	 * This method waits for given maximum seconds by polling for every 1 seconds.
	 * 
	 * @param targetElement
	 * @param timeOutInSeconds
	 * @throws Exception
	 * 
	 */
	public void syncWithAction(WebElement targetElement) throws Exception {
		try {
			if (null != getValue("elementTimeOutInSec")) {

				int elementTimeOut = Integer.parseInt(getValue("elementTimeOutInSec"));
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(elementTimeOut))
						.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.visibilityOf(targetElement));
			} else {
				failWithSnap("elementTimeOutInSec Value is Null in property File");
			}
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * Checks alert is present or not.
	 * 
	 * @return boolean
	 * 
	 */
	private Alert alertCheck() {
		Alert tempalert = null;
		try {
			int alertTimeOut = Integer.parseInt(getValue("alertTimeOutInSec"));
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(alertTimeOut))
					.pollingEvery(Duration.ofSeconds(1)).ignoring(NoAlertPresentException.class);
			wait.until(ExpectedConditions.alertIsPresent());
			tempalert = driver.switchTo().alert();
			if (tempalert != null) {
				alert = tempalert;
			}
		} catch (Exception e) {
			throw new NoAlertPresentException();
		}
		return alert;
	}

	/**
	 * Performs the keyboard actions.</br>
	 * Usage : Keys should be passed in sequence to be executed separated by
	 * ,(comma).</br>
	 * Example : To press Ctrl+t pass KeyEvent.VK_CONTROL,KeyEvent.VK_T.
	 * 
	 * @param keys
	 * @throws AWTException
	 * 
	 */
	public void pressKeyboardKeys(int... keys) throws AWTException {
		Robot r = new Robot();
		for (int key : keys) {
			r.keyPress(key);
		}
		for (int key : keys) {
			r.keyRelease(key);
		}
	}

	public int getElementCount(List<WebElement> targetElement) {
		int elementCount = 0;
		try {
			if (targetElement != null) {
				elementCount = targetElement.size();
				stepPass("Given element count : " + elementCount);
				log.info("Given element count : " + elementCount);
			}
		} catch (Exception e) {
			failWithSnap("getElementCount () : Element not found on the page");
			log.error("getElementCount() : element not found on the page ");
			Assert.fail("getElementCount() : element not found on the pag");
		}
		return elementCount;
	}
}