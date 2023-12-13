package googlePlayScraper.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

	@Id
	@Column(name = "No")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	@Column(name = "Author")
	private String author;
	@Column(name = "StarsNumber")
	private String starsNumber;
	@Column(name = "Date")
	private String date;
	@Column(name = "Review", columnDefinition = "LONGTEXT")
	private String review;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Application application;

	/**
	 *
	 * Constructor
	 *
	 * @param author
	 * @param starsNumber
	 * @param date
	 * @param review
	 */
	public Review(String author, String starsNumber, String date, String review) {
		super();
		this.author = author;
		this.starsNumber = starsNumber;
		this.date = date;
		this.review = review;
	}

	@Override
	public String toString() {

		return String.format("Author: %s\nStars number: %s\nDate: %s\nReview: %s\n", author, starsNumber, date, review);
	}

}
