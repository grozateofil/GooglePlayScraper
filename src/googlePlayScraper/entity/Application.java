package googlePlayScraper.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Application {

	private String name;
	private String developer;
	private String link;
	private String description;
	private ArrayList<Review> reviews;
	private String reviewsNumeber;
	private String rating;
	private String downloadsNumber;
	private String ageRating;
	private String price;
	private HashMap<String, ArrayList<String>> permissions;

	/**
	 *
	 * Constructor
	 *
	 * @param name
	 * @param developer
	 * @param link
	 * @param description
	 * @param reviews
	 * @param reviewsNumeber
	 * @param rating
	 * @param downloadsNumber
	 * @param ageRating
	 * @param price
	 * @param permissions
	 */
	public Application(String name, String developer, String link, String description, ArrayList<Review> reviews,
			String reviewsNumeber, String rating, String downloadsNumber, String ageRating, String price,
			HashMap<String, ArrayList<String>> permissions) {
		super();
		this.name = name;
		this.developer = developer;
		this.link = link;
		this.description = description;
		this.reviews = reviews;
		this.reviewsNumeber = reviewsNumeber;
		this.rating = rating;
		this.downloadsNumber = downloadsNumber;
		this.ageRating = ageRating;
		this.price = price;
		this.permissions = permissions;
	}

	/**
	 *
	 * Constructor
	 *
	 * @param reviewsNumeber
	 * @param rating
	 * @param downloadsNumber
	 * @param ageRating
	 */
	public Application(String reviewsNumeber, String rating, String downloadsNumber, String ageRating) {
		super();
		this.reviewsNumeber = reviewsNumeber;
		this.rating = rating;
		this.downloadsNumber = downloadsNumber;
		this.ageRating = ageRating;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the developer
	 */
	public String getDeveloper() {
		return developer;
	}

	/**
	 * @param developer the developer to set
	 */
	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * @return the reviewsNumeber
	 */
	public String getReviewsNumeber() {
		return reviewsNumeber;
	}

	/**
	 * @param reviewsNumeber the reviewsNumeber to set
	 */
	public void setReviewsNumeber(String reviewsNumeber) {
		this.reviewsNumeber = reviewsNumeber;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the downloadsNumber
	 */
	public String getDownloadsNumber() {
		return downloadsNumber;
	}

	/**
	 * @param downloadsNumber the downloadsNumber to set
	 */
	public void setDownloadsNumber(String downloadsNumber) {
		this.downloadsNumber = downloadsNumber;
	}

	/**
	 * @return the ageRating
	 */
	public String getAgeRating() {
		return ageRating;
	}

	/**
	 * @param ageRating the ageRating to set
	 */
	public void setAgeRating(String ageRating) {
		this.ageRating = ageRating;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the permissions
	 */
	public HashMap<String, ArrayList<String>> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(HashMap<String, ArrayList<String>> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return String.format(
				"Name: %s\nDeveloper: %s\nRating: %s\nDownloads number: %s\nReviews number: %s\nAge rating: %s\nLink: %s\nDescription: %s\nReviews: %s\nPrice: %s\nPermissions: %s",
				name, developer, rating, downloadsNumber, reviewsNumeber, ageRating, link, description, reviews, price,
				permissions);
	}

}
