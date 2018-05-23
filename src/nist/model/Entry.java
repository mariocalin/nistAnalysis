package nist.model;

import java.util.Arrays;

/**
 * Represents a <i>Entry</i> for a vulnerability in NIST data feed
 * 
 * @author mario
 *
 */
public class Entry {

	private String ID;
	private Category category;
	private double score;
	private String[] vulnerableSoftware;

	public Entry() {

	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean hasCategory() {
		return category != null;
	}

	public Category getCategory() {
		return category;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String[] getVulnerableSoftware() {
		return vulnerableSoftware;
	}

	public void setVulnerableSoftware(String[] vulnerableSoftware) {
		this.vulnerableSoftware = vulnerableSoftware;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entry other = (Entry) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Entry [ID=" + ID + ", category=" + (category != null ? category.getID() : "ninguna") + ", score="
				+ score + ", vulnerale products=" + Arrays.toString(vulnerableSoftware) + "]";
	}

	public int countVulnerableSoftware() {
		return vulnerableSoftware.length;
	}

}
