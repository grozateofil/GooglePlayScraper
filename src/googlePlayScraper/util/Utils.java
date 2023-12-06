package googlePlayScraper.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

	public Application parseRatingSection(String[] array) {
//		String rating = array[0] + " " + array[1];
//		String reviewsNumber = array[2];
//		String downloadsNumber = array[4] + " " + array[5];
//		String ageRating = array[6].split(" ")[1];
//
//		return new Application(reviewsNumber, rating, downloadsNumber, ageRating);
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
					reviewsNumber = array[i];
				if (line.contains("Downloads"))
					downloadsNumber = array[i];
				if (line.contains("PEGI"))
					ageRating = array[i].split(" ")[1];

			}
		}
		return new Application(reviewsNumber, rating, downloadsNumber, ageRating);
	}

}
