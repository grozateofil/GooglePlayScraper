package googlePlayScraper.entity;

public class Review {

	private String author;
	private String starsNumber;
	private String date;
	private String review;

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

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the starsNumber
	 */
	public String getStarsNumber() {
		return starsNumber;
	}

	/**
	 * @param starsNumber the starsNumber to set
	 */
	public void setStarsNumber(String starsNumber) {
		this.starsNumber = starsNumber;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public String toString() {

		return String.format("Author: %s\nStars number: %s\nDate: %s\nReview: %s\n", author, starsNumber, date, review);
	}
	
}
