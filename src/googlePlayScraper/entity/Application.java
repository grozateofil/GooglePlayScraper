package googlePlayScraper.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

	@Id
	@Column(name = "No")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	@Column(name = "AppScrappingDate")
	private String appScrappingDate;
	@Column(name = "Name")
	private String name;
	@Column(name = "Developer")
	private String developer;
	@Column(name = "Link")
	private String link;
	@Column(name = "Description", columnDefinition = "LONGTEXT")
	private String description;
	@OneToMany(cascade = { CascadeType.ALL, CascadeType.MERGE }, mappedBy = "application")
	private List<Review> reviews;
	@Column(name = "ReviewsNumber")
	private String reviewsNumber;
	@Column(name = "Rating")
	private String rating;
	@Column(name = "DownloadsNumber")
	private String downloadsNumber;
	@Column(name = "AgeRating")
	private String ageRating;
	@Column(name = "Price")
	private String price;
	@Column(name = "Permissions", columnDefinition = "LONGTEXT")
	private HashMap<String, ArrayList<String>> permissions;

	/**
	 *
	 * Constructor
	 *
	 * @param appScrappingDate
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
	public Application(String appScrappingDate, String name, String developer, String link, String description,
			List<Review> reviews, String reviewsNumber, String rating, String downloadsNumber, String ageRating,
			String price, HashMap<String, ArrayList<String>> permissions) {
		super();
		this.appScrappingDate = appScrappingDate;
		this.name = name;
		this.developer = developer;
		this.link = link;
		this.description = description;
		this.reviews = reviews;
		this.reviewsNumber = reviewsNumber;
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
	public Application(String reviewsNumber, String rating, String downloadsNumber, String ageRating) {
		super();
		this.reviewsNumber = reviewsNumber;
		this.rating = rating;
		this.downloadsNumber = downloadsNumber;
		this.ageRating = ageRating;
	}

	@Override
	public String toString() {
		return String.format(
				"Date: %s\nName: %s\nDeveloper: %s\nRating: %s\nDownloads number: %s\nReviews number: %s\nAge rating: %s\nLink: %s\nDescription: %s\nReviews: %s\nPrice: %s\nPermissions: %s",
				appScrappingDate, name, developer, rating, downloadsNumber, reviewsNumber, ageRating, link, description,
				reviews, price, permissions);
	}

}
