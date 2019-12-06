package de.tubs.isf.guido.core.databasis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class DataBasis<T extends DefaultDataBasisElement> {
	private List<T> entries;
	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public DataBasis() {
		this.entries = new ArrayList<>();
		//this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.clazz = (Class<T>) DefaultDataBasisElement.class;
	}

	public Class<T> clazz() {
		return clazz;
	}

	public List<T> getEntries() {
		return entries;
	}

	public T getEntry(int i) {
		if (i < 0 || i >= entries.size()) {
			throw new IllegalArgumentException("Entry '" + i + "' does not exist in data basis...");
		}
		return entries.get(i);
	}

	public void addEntries(T... entries) {
		for (T entry : entries)
			this.entries.add(entry);
	}

	public void addEntry(T entry) {
		this.entries.add(entry);
	}
	
	public boolean removeEntry(T entry) {
		return this.entries.remove(entry);
	}

	public int size() {
		return entries.size();
	}

	public DataBasis<T> filter(Function<T, Boolean> keep) {
		DataBasis<T> db = new DataBasis<T>();
		for (T elem : getEntries()) {
			if (keep.apply(elem))
				db.addEntry(elem);
		}
		return db;
	}

	public static<T extends DefaultDataBasisElement> void filter(DataBasis<T> db, Function<T, Boolean> keep) {
		List<T> toRemove = new ArrayList<T>();
		
		for(T elem : db.getEntries()) {
			if(!keep.apply(elem))
				toRemove.add(elem);
		}

		for(T elem : toRemove) {
			db.removeEntry(elem);
		}
	}

	public static <T extends DefaultDataBasisElement> DataBasis<T> readFromFile(File f) {
		DataBasis<T> result = new DataBasis<T>();

		f.getParentFile().mkdirs();
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null) {
				result.addEntry(gson.fromJson(line, result.clazz));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void writeToFile(File f) {
		try {
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			boolean first = true;
			for (T entry : entries) {
				Writer writer = new FileWriter(f, !first);
				gson.toJson(entry, writer);
				writer.append("\n");
				writer.flush();
				writer.close();
				first = false;
			}
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataBasis<T> clone() {
		DataBasis<T> db = new DataBasis<T>();
		for(T entry : getEntries()) {
			db.addEntry((T)entry.clone());
		}
		return db;
	}
}
