package nist.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a <i>Category</i> for a category in NIST data feed
 * 
 * @author mario
 *
 */
public class Category {

	private String ID;
	private List<Entry> entries;

	public Category(String ID) {
		this.entries = new LinkedList<Entry>();

		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public int getTotalEntries() {
		return entries.size();
	}

	public double getAverageScore() {
		double acumulado = entries.stream().mapToDouble(e -> e.getScore()).sum();
		return acumulado / (double) entries.size();
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
		Category other = (Category) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [ID=" + ID + ", entries=" + entries + "]";
	}

}
