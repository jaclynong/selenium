import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestRetailUserDxpInsurance {

	@Before
	public void setUp() {
		System.setProperty(
			"webdriver.chrome.driver",
			"/home/jaclynong/dev/chromedriver_linux64/chromedriver");
	}

	JavascriptExecutor js;

	@After
	public void tearDown() {
	}

	@Test
	public void test() {
		List<String> listUsers = new ArrayList<>();

		listUsers.add("test@speedwell.com");
		listUsers.add("charles.ford@dieselinstallers.com");
		listUsers.add("espy.translater@speedwell.com");
		listUsers.add("fran.tran@speedwell.com");
		listUsers.add("phil.marcketer@speedwell.com");
		listUsers.add("james.shepard@speedwell.com");

		_visitSupportSite(listUsers);

		_visitEventSiteAsGuest();
		_visitEventSite(listUsers);
	}

	private void _enterSupportTicket() {
		_navigateToUrl(_BASE_URL + "group/support/support-ticket");

		List<WebElement> formFields = driver.findElements(
			By.className("ddm-field"));

		_selectDropDownItem(formFields.get(0), 0);
		_selectDropDownItem(formFields.get(1), 1);

		List<WebElement> textFields = driver.findElements(
			By.className("ddm-field-text"));

		for (WebElement textField : textFields) {
			textField.click();
			textField.sendKeys("test");
		}

		driver.findElement(
			By.className("btn-primary")
		).click();
	}

	private void _exploreEventSite() {
		driver.findElement(
			By.xpath("//span[contains(text()," + "'Overview')]")
		).click();
		driver.findElement(
			By.xpath("//span[contains(text(),'Location')]")
		).click();
		driver.findElement(
			By.xpath("//span[contains(text(),'Speakers')]")
		).click();
		driver.findElement(
			By.xpath("//span[contains(text(),'Sessions')]")
		).click();

		driver.findElement(
			By.id("full-name")
		).sendKeys(
			"Test Test"
		);
		driver.findElement(
			By.id("email")
		).sendKeys(
			"test@speedwell" + ".com"
		);
		driver.findElement(
			By.id("company")
		).sendKeys(
			"Test Company"
		);

		driver.findElement(
			By.xpath("//a//div[contains(@class, 'form-button-text')]")
		).click();

		WebElement featuredCoverButton = driver.findElement(
			By.xpath("//a[contains(@class, 'featured-cover-button')]"));

		_scrollToClick(featuredCoverButton);

		driver.findElement(
			By.xpath("//a" + "[contains(@class, 'learn-more-button')]")
		).click();

		driver.findElement(
			By.xpath("//div[contains(text(),'BE " + "INSPIRED TODAY')]"));

		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		_sleep(4);
	}

	private WebElement _getElementWhenPresent(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		return wait.until(presenceOfElementLocated(locator));
	}

	private void _liveChat() {
		_getElementWhenPresent(
			By.xpath("//div[contains(@class, 'LPMcontainer')]")
		).click();

		_getElementWhenPresent(
			By.xpath("//textarea[@placeholder='Enter text here']")
		).sendKeys(
			"Hello, I need some help finding the " +
				"Marketing Expense Report.",
			Keys.ENTER
		);

		driver.findElement(
			By.xpath("//button[contains(@class,'lp_minimize')" + "]")
		).click();
	}

	private void _login(String listUser) {
		driver.findElement(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_login")
		).sendKeys(
			Keys.chord(Keys.CONTROL, "a")
		);
		driver.findElement(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_login")
		).sendKeys(
			Keys.DELETE
		);
		driver.findElement(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_login")
		).sendKeys(
			listUser
		);
		driver.findElement(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_password")
		).sendKeys(
			"test"
		);
		driver.findElement(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_login")
		).sendKeys(
			Keys.ENTER
		);
	}

	private void _navigateToUrl(String url) {
		driver.navigate(
		).to(
			url
		);

		_sleep(3);
	}

	private void _scrollToClick(WebElement webElement) {
		try {
			webElement.click();
		}
		catch (ElementClickInterceptedException ec) {
			_scrollToElement(webElement);

			webElement.click();
		}
	}

	private void _scrollToElement(WebElement webElement) {
		Point point = webElement.getLocation();

		int x = point.getX();

		int y = point.getY();

		js.executeScript("window.scrollBy(" + x + ", " + y + ");");
	}

	private void _searchInKnowledgeBase() {
		WebElement searchInput = driver.findElement(
			By.className("search-bar-keywords-input"));

		searchInput.click();

		searchInput.sendKeys("top contributors", Keys.ENTER);

		_getElementWhenPresent(
			By.xpath("//li[1]//div[2]//section[1]//div[1]//a[1" + "]")
		).click();
	}

	private void _selectDropDownItem(
		WebElement formField, int ddmSelectDropdownIndex) {

		WebElement selectArrowDown = formField.findElement(
			By.className("select-arrow-down-container"));

		selectArrowDown.click();

		driver.findElements(
			By.className("ddm-select-dropdown")
		).get(
			ddmSelectDropdownIndex
		).findElements(
			By.className("dropdown-item")
		).get(
			1
		).click();
	}

	private void _sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		}
		catch (Exception e) {
		}
	}

	private void _startNewBrowser(String url) {
		driver = new ChromeDriver();
		driver.manage(
		).deleteAllCookies();
		js = (JavascriptExecutor)driver;
		driver.get(url);
	}

	private void _visitEventSite(List<String> listUsers) {
		for (int i = 0; i < _ITERATIONS; i++) {
			for (int f = 0; f < listUsers.size(); f++) {
				_startNewBrowser(_BASE_URL + "web/apac-auto-parts-con");

				WebElement accountToggle = driver.findElement(
					By.className("js-toggle-account"));

				accountToggle.click();

				driver.findElement(
					By.className("main-link")
				).click();

				_login(listUsers.get(f));

				_sleep(2);

				_exploreEventSite();

				driver.close();
			}
		}
	}

	private void _visitEventSiteAsGuest() {
		for (int i = 0; i < _ITERATIONS; i++) {
			_startNewBrowser(_BASE_URL + "web/apac-auto-parts-con");

			_exploreEventSite();

			_liveChat();

			driver.close();
		}
	}

	private void _visitManageSupportTicketPage() {
		_navigateToUrl(_BASE_URL + "group/support/manage-support-tickets");

		driver.findElement(
			By.xpath("//tr[1]//td[1]//p[1]//a[1]//span[1]")
		).click();

		_getElementWhenPresent(
			By.xpath(
				"//div[contains(@class,'speedwell-frame " +
					"speedwell-frame__content')]//button[2]")
		).click();
	}

	private void _visitSupportSite(List<String> listUsers) {
		for (int i = 0; i < _ITERATIONS; i++) {
			for (int f = 0; f < listUsers.size(); f++) {
				_startNewBrowser(_BASE_URL + "group/support");

				_login(listUsers.get(f));

				_searchInKnowledgeBase();

				if (listUsers.get(
					f).equalsIgnoreCase(
					"charles.ford@dieselinstallers.com")) {

					_liveChat();
				}

				_enterSupportTicket();

				if (listUsers.get(f).equalsIgnoreCase("test@speedwell.com") ||
					listUsers.get(
						f).equalsIgnoreCase(
						"james" + ".shepard@speedwell.com")) {

					_visitManageSupportTicketPage();
				}

				driver.close();
			}
		}
	}

	private static final String _BASE_URL =
		"http://gartner-demo-lb1-132791781.us-east-1.elb.amazonaws.com/";

	private static final int _ITERATIONS = 10;

	private WebDriver driver;

}