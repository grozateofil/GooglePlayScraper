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
@Table(name = "Permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "PermissionName")
	private String permissionName;

	@Column(name = "PermissionDescription", columnDefinition = "LONGTEXT")
	private String permissionDescription;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Application application;

	/**
	 *
	 * Constructor
	 *
	 * @param permissionName
	 * @param permissionDescription
	 */
	public Permission(String permissionName, String permissionDescription) {
		super();
		this.permissionName = permissionName;
		this.permissionDescription = permissionDescription;
	}

	@Override
	public String toString() {
		return String.format("Permission name: %s \nPermission description: %s \n", permissionName,
				permissionDescription);
	}

}
