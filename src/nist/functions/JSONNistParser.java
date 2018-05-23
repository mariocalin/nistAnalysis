package nist.functions;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import nist.model.Category;
import nist.model.Entry;
import nist.model.Result;

/**
 * Class that implements INistDataAnalysis for an JSON NIST data feed This class
 * is yet to be completed.
 * 
 * @author mario
 *
 */
public class JSONNistParser implements INistDataAnalysis {

	private String JSON_SOURCE = JSonFiles.FULL_YEAR_2016;

	@Override
	public Result createResult() throws NistException {
		LinkedList<Entry> entries = new LinkedList<Entry>();
		LinkedList<Category> categories = new LinkedList<Category>();

		try {
			JSONArray array = jsonEntries();
			for (int i = 0; i < array.length(); i++)
				addEntryFromJson(array.getJSONObject(i), entries, categories);

		} catch (IOException e) {
			e.printStackTrace();
			throw new NistException(e);
		}

		return new Result(entries, categories);
	}

	private void addEntryFromJson(JSONObject jsonObject, LinkedList<Entry> entries, LinkedList<Category> categories) {
		Entry entry = new Entry();
		entry.setID(obtainId(jsonObject));

		Category category = obtainCategory(jsonObject, categories);
		entry.setCategory(category);
		if (category != null)
			category.getEntries().add(entry);

		entries.add(entry);
	}

	private String obtainId(JSONObject jsonObject) {
		return jsonObject.getJSONObject("cve").getJSONObject("CVE_data_meta").getString("ID");
	}

	private Category obtainCategory(JSONObject jsonObject, LinkedList<Category> categories) {

		try {
			Category temporalCategory = new Category(
					jsonObject.getJSONObject("cve").getJSONObject("problemtype").getJSONArray("problemtype_data")
							.getJSONObject(0).getJSONArray("description").getJSONObject(0).getString("value"));

			if (categories.contains(temporalCategory))
				return categories.stream().filter(c -> c.equals(temporalCategory)).findFirst().get();
			else {
				categories.add(temporalCategory);
				return temporalCategory;
			}

		} catch (Exception ex) {

		}

		return null;

	}

	private JSONArray jsonEntries() throws IOException {
		File file = new File(JSON_SOURCE);
		String content = FileUtils.readFileToString(file, "utf-8");
		JSONObject jsonObject = new JSONObject(content);
		return jsonObject.getJSONArray("CVE_Items");
	}

	@SuppressWarnings("unused")
	private static class JSonFiles {
		private static final String FULL_YEAR_2016 = "jsonData/nvdcve-1.0-2016.json";
		private static final String FULL_YEAR_2017 = "jsonData/nvdcve-1.0-2017.json";
		private static final String ACTUAL_YEAR_2018 = "jsonData/nvdcve-1.0-recent.json";

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSource(String sourceFile) {
		this.JSON_SOURCE = sourceFile;

	}

}
