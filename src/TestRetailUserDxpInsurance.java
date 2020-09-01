import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
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

		List<WebElement> formFields = _getElementsWhenPresent(
			By.className("ddm-field"));

		_selectDropDownItem(formFields.get(0), 0);
		_selectDropDownItem(formFields.get(1), 1);

		List<WebElement> textFields = _getElementsWhenPresent(
			By.className("ddm-field-text"));

		for (WebElement textField : textFields) {
			textField.click();
			textField.sendKeys("test");
		}

		_getElementWhenPresent(By.className("btn-primary")).click();
	}

	private void _exploreEventSite() {
		_getElementWhenPresent(By.xpath("//span[contains(text(),'Overview')]")).click();
		_getElementWhenPresent(By.xpath("//span[contains(text(),'Location')]")).click();
		_getElementWhenPresent(By.xpath("//span[contains(text(),'Speakers')]")).click();
		_getElementWhenPresent(By.xpath("//span[contains(text(),'Sessions')]")).click();

		_getElementWhenPresent(
			By.id("full-name")
		).sendKeys(
			"Test Test"
		);

		_getElementWhenPresent(
			By.id("email")
		).sendKeys(
			"test@speedwell" + ".com"
		);

		_getElementWhenPresent(
			By.id("company")
		).sendKeys(
			"Test Company"
		);

		_getElementWhenPresent(
			By.xpath("//a//div[contains(@class, 'form-button-text')]")
		).click();

		WebElement featuredCoverButton = _getElementWhenPresent(
			By.xpath("//a[contains(@class, 'featured-cover-button')]"));

		_scrollToClick(featuredCoverButton);

		_getElementWhenPresent(
			By.xpath("//a" + "[contains(@class, 'learn-more-button')]")
		).click();

		_getElementWhenPresent(
			By.xpath("//div[contains(text(),'BE " + "INSPIRED TODAY')]"));

		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		_sleep(4);
	}

	private WebElement _getElementWhenPresent(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		return wait.until(presenceOfElementLocated(locator));
	}

	private List<WebElement> _getElementsWhenPresent(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		return wait.until(presenceOfAllElementsLocatedBy(locator));
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

		_getElementWhenPresent(
			By.xpath("//button[contains(@class,'lp_minimize')" + "]")
		).click();
	}

	private void _login(String listUser) {
		_getElementWhenPresent(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_login")
		).sendKeys(
			Keys.chord(Keys.CONTROL, "a")
		);
		_getElementWhenPresent(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_login")
		).sendKeys(
			Keys.DELETE
		);
		_getElementWhenPresent(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_login")
		).sendKeys(
			listUser
		);
		_getElementWhenPresent(
			By.id("_com_liferay_login_web_portlet_LoginPortlet_password")
		).sendKeys(
			"test"
		);
		_getElementWhenPresent(
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
		WebElement searchInput = _getElementWhenPresent(
			By.className("search-bar-keywords-input"));

		searchInput.click();

		searchInput.sendKeys(_getRandomSearchQuery(), Keys.ENTER);

		try {
			_getElementWhenPresent(
					By.xpath("//li[1]//div[2]//section[1]//div[1]//a[1" + "]")
			).click();
		}
		catch (Exception e) {
		}

	}

	private void _selectDropDownItem(
		WebElement formField, int ddmSelectDropdownIndex) {

		WebElement selectArrowDown = formField.findElement(
			By.className("select-arrow-down-container"));

		selectArrowDown.click();

		_getElementsWhenPresent(
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

				WebElement accountToggle =
					_getElementWhenPresent(By.className("js-toggle-account"));

				accountToggle.click();

				_getElementWhenPresent(By.className("main-link")).click();

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

			driver.close();
		}
	}

	private void _visitManageSupportTicketPage() {
		_navigateToUrl(_BASE_URL + "group/support/manage-support-tickets");

		_getElementWhenPresent(By.xpath("//tr[1]//td[1]//p[1]//a[1]//span[1]")).click();

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

	private String _getRandomSearchQuery() {
		int randomIndex = new Random().nextInt(_SEARCH_QUERIES.length);
		return _SEARCH_QUERIES[randomIndex];
	}

	private static final String[] _SEARCH_QUERIES = new String[] {
		"tires","rims","engine","supercharger","tools","seatbelts","windshield","oil change","convention","timing belt"};

	private static final String _BASE_URL =
		"http://gartner-demo-lb1-132791781.us-east-1.elb.amazonaws.com/";

	private static final int _ITERATIONS = 10;

	private WebDriver driver;

}