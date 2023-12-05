package googlePlayScraper;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import googlePlayScraper.entity.Application;
import googlePlayScraper.entity.Review;
import googlePlayScraper.util.Utils;

public class Main {

	public static void main(String[] args) {

		String driverPath = new File("Driver/chromedriver-win64/chromedriver.exe").getAbsolutePath();

		System.setProperty("webdriver.chrome.driver", driverPath);
		WebDriver chromeDriver = new ChromeDriver();

		Utils utils = new Utils();

		String searchedWord = "fishing game";

		ArrayList<Application> applications = new ArrayList<>();

		chromeDriver.navigate().to("https://play.google.com/store/");
		chromeDriver.manage().window();

		WebElement searchButton = chromeDriver.findElement(By.cssSelector(".google-material-icons.r9optf"));
		searchButton.click();

		WebElement searchBar = new WebDriverWait(chromeDriver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".HWAcU")));

		searchBar.sendKeys(searchedWord + Keys.ENTER);

		try {

			WebElement listOfApplicationsWebElement = new WebDriverWait(chromeDriver, Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(By.className("fUEl2e")));

			List<WebElement> listOfApplications = listOfApplicationsWebElement.findElements(By.className("ULeU3b"));

			for (WebElement applicationWebElement : listOfApplications) {
				String appName = null;
				String appDescription = null;
				String appDeveloperName = null;

				String price = null;
				List<WebElement> appCardWebElements = applicationWebElement.findElements(By.className("cXFu1"));
				for (WebElement appCardWebElement : appCardWebElements) {

					List<WebElement> appCardDataList = appCardWebElement.findElements(By.className("ubGTjb"));

					int index = 0;
					for (WebElement appCardData : appCardDataList) {
						if (index == appCardDataList.size() - 1) {
							if (appCardData.getText().replace("\n", " ").contains("star")) {

								price = "free";
							} else if (appCardData.getText().replace("\n", " ").contains("RON")) {
								price = appCardData.getText().replace("\n", " ");
							}
						} else {

							appName = appCardDataList.get(0).getText();
							appDeveloperName = appCardDataList.get(1).getText();
						}
						index++;
					}
				}

				WebElement appButtonWebElement = applicationWebElement.findElement(By.cssSelector("a.Si6A0c.Gy4nib"));
				String appLink = appButtonWebElement.getAttribute("href");

				appButtonWebElement.sendKeys(Keys.ENTER);

				WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(30));

				WebElement ratingSection = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".w7Iutd")));

				String[] array = ratingSection.getText().split("\n");

				WebElement descriptionSection = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bARER")));

				appDescription = descriptionSection.getText();

				Thread.sleep(500);

				WebElement aboutThisAppButton = new WebDriverWait(chromeDriver, Duration.ofSeconds(10))
						.until(ExpectedConditions.elementToBeClickable(
								By.cssSelector(".VfPpkd-Bz112c-LgbsSe.yHy1rc.eT1oJ.QDwDD.mN1ivc.VxpoF")));

				aboutThisAppButton.click();

				WebElement permissionsDetails = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".TCqkTe.Vvn1K")));

				permissionsDetails.click();

				WebElement permissions = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".xNUmN")));

				List<WebElement> permissionsNameList = permissions.findElements(By.className("BlLrjc"));
				HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
				for (WebElement permissionName : permissionsNameList) {

					WebElement name = permissionName.findElement(By.className("B4wkv"))
							.findElement(By.className("aPeBBe"));

					List<WebElement> permDetails = permissionName.findElements(By.className("dnM39b"));
					ArrayList<String> arrayList = new ArrayList<>();
					for (WebElement element : permDetails) {

						if (element.getText() != null)

							arrayList.add(element.getText());
					}

					hashMap.put(name.getText(), arrayList);
				}

				chromeDriver.navigate().back();
				Thread.sleep(2000);

				ArrayList<Review> reviews = new ArrayList<>();

				if (chromeDriver.findElements(By.cssSelector(".Jwxk6d")).size() > 0) {
					WebElement reviewSection = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Jwxk6d")));

					List<WebElement> reviewsList = reviewSection.findElements(By.className("EGFGHd"));

					for (WebElement element : reviewsList) {

						String author = element.findElement(By.className("YNR7H")).findElement(By.className("gSGphe"))
								.findElement(By.className("X5PpBb")).getText();
						String starsNumber = element.findElement(By.className("Jx4nYe"))
								.findElement(By.className("iXRFPc")).getAttribute("aria-label").split(" ")[1];
						String reviewDate = element.findElement(By.className("Jx4nYe"))
								.findElement(By.cssSelector(".bp9Aid")).getText();
						String textReview = element.findElement(By.className("h3YV2d")).getText();

						Review review = new Review(author, starsNumber, reviewDate, textReview);
						reviews.add(review);
					}
				}

				chromeDriver.navigate().back();
				Thread.sleep(2000);

				Application application = new Application(appName, appDeveloperName, appLink, null,
						reviews.size() > 0 ? reviews : null, utils.parseRatingSection(array).getReviewsNumeber(),
						utils.parseRatingSection(array).getRating(),
						utils.parseRatingSection(array).getDownloadsNumber(),
						utils.parseRatingSection(array).getAgeRating(), price, hashMap);

				System.out.println(application + "\n\n");

				applications.add(application);

			}

		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
//			chromeDriver.quit();
		}
		utils.saveInFolder(searchedWord, applications);

	}

}
