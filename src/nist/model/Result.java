package nist.model;

import static nist.utils.DoubleUtils.doubleToCommaString;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import nist.functions.INistDataResult;

/**
 * A result for a Nist parser
 * 
 * @author mario
 *
 */
public class Result {

	/**
	 * Result vulnerability entries
	 */
	private List<Entry> entries;

	/**
	 * Result vulnerability categries
	 */
	private List<Category> categories;

	public Result(List<Entry> entries, List<Category> categories) {
		this.entries = entries;
		this.categories = categories;
	}

	public CategoriesResult categoriesResult() {
		return new CategoriesResult(this);
	}

	public EntriesResult entriesResult() {
		return new EntriesResult(this);
	}

	/**
	 * Class that express the results focused on categories
	 * 
	 * @author mario
	 *
	 */
	public class CategoriesResult implements INistDataResult {

		private Result result;

		public CategoriesResult(Result result) {
			this.result = result;
		}

		/**
		 * {@inheritDoc}
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();
			result.categories.sort(Comparator.comparingInt(Category::getTotalEntries).reversed());

			for (int i = 0; i < result.categories.size(); i++) {
				Category cat = result.categories.get(i);
				sb.append(cat.getID() + " -> " + cat.getTotalEntries() + " : score " + cat.getAverageScore() + " \n");
			}

			int entriesWithNoCategory = entries.stream().filter(ent -> !ent.hasCategory()).collect(Collectors.toList())
					.size();

			List<Entry> entNocatSiScore = entries.stream().filter(ent -> !ent.hasCategory() && ent.getScore() > 0)
					.collect(Collectors.toList());

			double noCatAvgScore = entNocatSiScore.stream().mapToDouble(e -> e.getScore()).sum()
					/ (double) entNocatSiScore.size();

			sb.append("Vulnerabilities without category assigned-> " + entriesWithNoCategory + " : score "
					+ doubleToCommaString(noCatAvgScore));

			return sb.toString();
		}

		/**
		 * {@inheritDoc}
		 */
		public void toCSV(String namefile, boolean removeFileIfExists) throws IOException {
			StringBuilder sb = new StringBuilder();
			sb.append("CATEGORY; NUMBER_OF_VULNERABILITIES; AVERAGE_SCORE \n");
			result.categories.sort(Comparator.comparingInt(Category::getTotalEntries).reversed());

			// CATEGORIES
			for (int i = 0; i < result.categories.size(); i++) {
				Category cat = result.categories.get(i);
				sb.append(cat.getID() + "; " + cat.getTotalEntries() + "; " + doubleToCommaString(cat.getAverageScore())
						+ " \n");
			}

			// ENTRIES
			int entriesWithNoCategory = entries.stream().filter(ent -> !ent.hasCategory()).collect(Collectors.toList())
					.size();

			List<Entry> entNocatSiScore = entries.stream().filter(ent -> !ent.hasCategory() && ent.getScore() > 0)
					.collect(Collectors.toList());

			double noCatAvgScore = entNocatSiScore.stream().mapToDouble(e -> e.getScore()).sum()
					/ (double) entNocatSiScore.size();

			sb.append("others; " + entriesWithNoCategory + "; " + doubleToCommaString(noCatAvgScore));

			String content = sb.toString();
			File file = new File(namefile);
			if (removeFileIfExists && file.exists())
				file.delete();

			file.createNewFile();
			FileUtils.writeStringToFile(file, content, Charset.forName("UTF-8"), true);
		}

	}

	/**
	 * Class that express the results focused on categories
	 * 
	 * @author mario
	 *
	 */
	public class EntriesResult implements INistDataResult {
		private Result result;

		public EntriesResult(Result result) {
			this.result = result;
		}

		/**
		 * {@inheritDoc}
		 */
		public String toString() {
			result.entries.sort(Comparator.comparing(Entry::getID));

			StringBuilder sb = new StringBuilder();
			for (Entry entry : result.entries) {
				sb.append(entry.getID());
				if (entry.hasCategory())
					sb.append(" (" + entry.getCategory().getID() + ")");

				sb.append(" -> " + entry.getVulnerableSoftware().length + " software products affected");
				if (entry.getVulnerableSoftware().length > 0) {
					String aux = "";
					for (int i = 0; i < entry.getVulnerableSoftware().length; i++) {
						aux += entry.getVulnerableSoftware()[i] + ";";
					}

					sb.append("[" + aux.substring(0, aux.length() - 1) + "]");
				}

				sb.append("\n");
			}

			return sb.toString();
		}

		/**
		 * {@inheritDoc}
		 */
		public void toCSV(String namefile, boolean removeFileIfExists) throws IOException {
			result.entries.sort(Comparator.comparing(Entry::countVulnerableSoftware).reversed());
			Set<String> uniqueProducts = new HashSet<String>();

			StringBuilder sb = new StringBuilder();
			sb.append("ENTRY; SCORE; PRODUCTS_AFFECTED \n");

			for (Entry entry : result.entries) {
				uniqueProducts.addAll(Arrays.asList(entry.getVulnerableSoftware()));

				// COL 1
				sb.append(entry.getID());
				sb.append(";");

				// COL 2
				sb.append(doubleToCommaString(entry.getScore()));
				sb.append(";");

				// COL 3
				sb.append(entry.getVulnerableSoftware().length);

				sb.append("\n");
			}

			sb.append("TOTAL PRODUCTS; " + uniqueProducts.size());

			String content = sb.toString();
			File file = new File(namefile);
			if (removeFileIfExists && file.exists())
				file.delete();

			file.createNewFile();
			FileUtils.writeStringToFile(file, content, Charset.forName("UTF-8"), true);
		}

	}

}
