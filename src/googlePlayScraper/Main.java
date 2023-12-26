package googlePlayScraper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import googlePlayScraper.entity.Application;
import googlePlayScraper.entity.Permission;
import googlePlayScraper.entity.Review;
import googlePlayScraper.util.Utils;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String driverPath = new File("Driver/chromedriver-win64/chromedriver.exe").getAbsolutePath();
		Utils utils = new Utils();

		String category = "";
		int option = -1;
		String saveToSQL = "";

		while (category.isEmpty()) {
			System.out.print("ENTER THE APPLICATIONS CATEGORY: ");
			category = scanner.nextLine().trim();

			if (category.isEmpty()) {
				System.err.println("The category can't be empty. Please enter a valid category!\n");
			}
		}

		while (option != 0 && option != 1 && option != 2) {
			System.out.println("IN WHAT TYPE OF FILE DO YOU WANT THE DATA TO BE SAVED?");
			System.out.println("0. Without save to file ");
			System.out.println("1. Text file (.txt)");
			System.out.println("2. Excel file (.xlsx)\n");
			System.out.print("ENTER THE NUMBER OF CHOSEN OPTION: ");
			try {
				option = scanner.nextInt();
			} catch (InputMismatchException e) {
				scanner.next();
			}

			if (option != 0 && option != 1 && option != 2) {
				System.err.println("The entered option not exists. Please enter a valid option!\n");
			}

		}

		while (saveToSQL.isEmpty() || (!saveToSQL.equals("1") && !saveToSQL.equals("2"))) {
			System.out.println("Do you want to save data in SQL database?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			System.out.print("ENTER THE NUMBER OF CHOSEN OPTION: ");
			saveToSQL = scanner.next().trim();

			if (saveToSQL.isEmpty()) {
				System.err.println("The option can't be empty. Please enter a valid option!\n");
			}

			if (!saveToSQL.equals("1") && !saveToSQL.equals("2")) {
				System.err.println("The entered option not exists. Please enter a valid option!\n");
			}

		}

		scanner.close();

		System.setProperty("webdriver.chrome.driver", driverPath);
		WebDriver chromeDriver = new ChromeDriver();
		ArrayList<Application> applications = new ArrayList<>();
//		ApplicationService applicationService = new ApplicationService();

		chromeDriver.navigate().to("https://play.google.com/store/");
		chromeDriver.manage().window();

		WebElement searchButton = chromeDriver.findElement(By.cssSelector(".google-material-icons.r9optf"));
		searchButton.click();

		WebElement searchBar = new WebDriverWait(chromeDriver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".HWAcU")));

		searchBar.sendKeys(category + Keys.ENTER);

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
				HashMap<String, LinkedList<String>> hashMap = new HashMap<>();
				List<Permission> permissionsList = new ArrayList<>();
				for (WebElement permissionName : permissionsNameList) {

					WebElement name = permissionName.findElement(By.className("B4wkv"))
							.findElement(By.className("aPeBBe"));

					List<WebElement> permDetails = permissionName.findElements(By.className("dnM39b"));
					LinkedList<String> arrayList = new LinkedList<>();
					Permission permis = new Permission();
					for (WebElement element : permDetails) {

						String[] permissionsArray = null;
						if (element.getText() != null)

							permissionsArray = element.getText().split("\n");
						for (String permission : permissionsArray) {
							if (!permission.isEmpty())
								arrayList.add(permission);
						}
						permis = new Permission(name.getText(), arrayList.stream().collect(Collectors.joining("; ")));
					}
					permissionsList.add(permis);
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
						String reviewOldDate = element.findElement(By.className("Jx4nYe"))
								.findElement(By.cssSelector(".bp9Aid")).getText();

						DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMMM d, yyyy");
						LocalDate date = LocalDate.parse(reviewOldDate, parser);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
						String reviewNewDate = date.format(formatter);

						String textReview = element.findElement(By.className("h3YV2d")).getText();

						Review review = new Review(author, starsNumber, reviewNewDate, textReview);
						reviews.add(review);
					}
				}

				chromeDriver.navigate().back();
				Thread.sleep(2000);

				String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

				Application application = new Application(currentDate, appName, appDeveloperName, appLink,
						appDescription, reviews.size() > 0 ? reviews : null,
						utils.parseRatingSection(array).getReviewsNumber(), utils.parseRatingSection(array).getRating(),
						utils.parseRatingSection(array).getDownloadsNumber(),
						utils.parseRatingSection(array).getAgeRating(), price, permissionsList);

				System.out.println(application + "\n" + "\n");

				applications.add(application);
//				applicationService.addApplication(application);

			}

		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			chromeDriver.quit();
		}

		if (option == 1)
			utils.saveInFolder(category, applications);
		else if (option == 2)
			utils.createExcel(category, applications);
		else {
			System.err.println("The data wasn't saved in file!");
		}

//		applicationService.addApplication(applications);

	}

}
