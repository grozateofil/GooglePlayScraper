package googlePlayScraper.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import googlePlayScraper.entity.Application;

public class Utils {

	private String parentFolder = "GooglePlay";

	public void saveInFolder(String searchedWord, ArrayList<Application> applications) {

		String folderName = searchedWord;
		String dayFolder = new SimpleDateFormat("ddMMyyyy").format(new Date());
		String message = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
		File folder = new File(parentFolder + "/" + dayFolder + "/" + folderName);
		folder.mkdirs();
		String fileName = message + ".txt";
		PrintStream scriereFisier = null;

		File fisier = new File(folder, fileName);

		if (applications != null) {
			try {
				scriereFisier = new PrintStream(fisier);

				for (Application application : applications) {

					scriereFisier.println(application + "\n"
							+ "__________________________________________________________________________________________"
							+ "\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();

			}
		}
	}

	public void createExcel(ArrayList<Application> applications, String searchedWord) {

		String currentDate = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
		File folder = new File(
				parentFolder + "/" + "ExcelFiles" + "/" + new SimpleDateFormat("MM_yyyy").format(new Date()));
		folder.mkdirs();

		String fileName = currentDate + ".xlsx";
		try {
			File file = new File(folder, fileName);
			file.createNewFile();
			String excelPath = file.getAbsolutePath();

			XSSFWorkbook workbook = new XSSFWorkbook();

			if (workbook.getNumberOfSheets() == 0) {
				workbook.createSheet(searchedWord);

			}

			CellStyle cellStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			cellStyle.setFont(font);
			cellStyle.setWrapText(false);

			cellStyle.setAlignment(HorizontalAlignment.CENTER);

			XSSFSheet spreadsheet = workbook.getSheetAt(0);

			int rowid = 1;
			XSSFRow headerRow = spreadsheet.createRow(0);

			Cell headerDateCell = headerRow.createCell(0);
			headerDateCell.setCellValue("Date");
			headerDateCell.setCellStyle(cellStyle);
			Cell headerNameCell = headerRow.createCell(1);
			headerNameCell.setCellValue("Name");
			headerNameCell.setCellStyle(cellStyle);
			Cell headerDeveloperCell = headerRow.createCell(2);
			headerDeveloperCell.setCellValue("Developer");
			headerDeveloperCell.setCellStyle(cellStyle);
			Cell headerLinkCell = headerRow.createCell(3);
			headerLinkCell.setCellValue("Link");
			headerLinkCell.setCellStyle(cellStyle);
			Cell headerDescriptionCell = headerRow.createCell(4);
			headerDescriptionCell.setCellValue("Description");
			headerDescriptionCell.setCellStyle(cellStyle);
			Cell headerReviewsCell = headerRow.createCell(5);
			headerReviewsCell.setCellValue("Reviews");
			headerReviewsCell.setCellStyle(cellStyle);
			Cell headerReviewsNumberCell = headerRow.createCell(6);
			headerReviewsNumberCell.setCellValue("Reviews number");
			headerReviewsNumberCell.setCellStyle(cellStyle);
			Cell headerRatingCell = headerRow.createCell(7);
			headerRatingCell.setCellValue("Rating");
			headerRatingCell.setCellStyle(cellStyle);
			Cell headerDownloadsNumberCell = headerRow.createCell(8);
			headerDownloadsNumberCell.setCellValue("Downloads number");
			headerDownloadsNumberCell.setCellStyle(cellStyle);
			Cell headerAgeRatingCell = headerRow.createCell(9);
			headerAgeRatingCell.setCellValue("Age rating");
			headerAgeRatingCell.setCellStyle(cellStyle);
			Cell headerPriceCell = headerRow.createCell(10);
			headerPriceCell.setCellValue("Price");
			headerPriceCell.setCellStyle(cellStyle);
			Cell headerPermissionsCell = headerRow.createCell(11);
			headerPermissionsCell.setCellValue("Permissions");
			headerPermissionsCell.setCellStyle(cellStyle);

			
			XSSFRow row = null;
			for (Application application : applications) {
				row = spreadsheet.createRow(rowid++);

				Cell date = row.createCell(0);
				date.setCellValue(application.getAppScrappingDate());

				Cell appName = row.createCell(1);
				appName.setCellValue(application.getName());

				Cell appDeveloper = row.createCell(2);
				appDeveloper.setCellValue(application.getDeveloper());

				Cell appLink = row.createCell(3);
				appLink.setCellValue(application.getLink());

				Cell appDescription = row.createCell(4);
				appDescription.setCellValue(application.getDescription());

				Cell appReviews = row.createCell(5);
				appReviews.setCellValue(application.getReviews() != null ? application.getReviews().stream()
						.map(review -> review.toString() + "\n").collect(Collectors.joining(",")) : "");

				Cell appReviewsNumber = row.createCell(6);
				appReviewsNumber.setCellValue(application.getReviewsNumber());

				Cell appRating = row.createCell(7);
				appRating.setCellValue(application.getRating());

				Cell appDownloadsNumber = row.createCell(8);
				appDownloadsNumber.setCellValue(application.getDownloadsNumber());

				Cell appAgeRating = row.createCell(9);
				appAgeRating.setCellValue(application.getAgeRating());

				Cell appPrice = row.createCell(10);
				appPrice.setCellValue(application.getPrice());

				Cell appPermissions = row.createCell(11);
				appPermissions.setCellValue(application.getPermissions() != null ? application.getPermissions()
						.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue())
						.collect(Collectors.joining(", \n")) : "");

			}

			if (row != null) {
				for (int colNum = 0; colNum < row.getLastCellNum(); colNum++)
					workbook.getSheetAt(0).autoSizeColumn(colNum);
			}
			FileOutputStream fileOutputStream = new FileOutputStream(excelPath);
			workbook.write(fileOutputStream);

			fileOutputStream.close();
			workbook.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public Application parseRatingSection(String[] array) {
		String line = null;
		String rating = null;
		String reviewsNumber = null;
		String downloadsNumber = null;
		String ageRating = null;
		for (int i = 0; i < array.length; i += 2) {
			if (i + 1 < array.length) {
				line = array[i] + " " + (array[i + 1].equals("info") ? "" : array[i + 1]);
				if (line.toString().contains("star"))
					rating = array[i];
				if (line.contains("reviews"))
					reviewsNumber = array[i].split(" ")[0];
				if (line.contains("Downloads"))
					downloadsNumber = array[i];
				if (line.contains("PEGI")) {

					String[] ln = line.split(" ");

					ageRating = (IntStream.range(0, ln.length - 1).filter(j -> ln[j].equals("PEGI"))
							.mapToObj(j -> ln[j + 1]).findFirst().orElse(""));

				}
			}
		}
		return new Application(reviewsNumber, rating, downloadsNumber, ageRating);
	}

}
