package de.tubs.isf.guido.core.experiments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AExperiment {
	
	public interface Label {
	    String getLabel();
	    String toString();
	}

	static protected class DataPoint {
		private Label label;
		private Object value;
		private Function<DataPoint,String> tostring;
		
		public Label getLabel() {
			return label;
		}

		public void setLabel(Label label) {
			this.label = label;
		}
		
		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
		
		public void setStringRepresentation(Function<DataPoint,String> tostring) {
			this.tostring = tostring;
		}

		DataPoint(Object value, Label label) {
			this(value,label,null);
		}
		
		DataPoint(Object value, Label label, Function<DataPoint,String> tostring) {
			this.label = label;
			this.value = value;
			this.tostring = tostring;
		}

		public String toString() {
			if(tostring == null)
				return this.value.toString();
			return tostring.apply(this);
		}
	}
	
	static protected class DataColumn<T> {
		protected List<DataPoint> data;
		protected Label label;
		private Function<DataPoint,String> tostring = null;
		private Function<String, T> toT = null;
		
		public Label getLabel() {
			return label;
		}

		public void setLabel(Label label) {
			this.label = label;
		}
		
		@SuppressWarnings("unchecked")
		public DataColumn(Label label) {
			this.label = label;
			this.data = new ArrayList<DataPoint>();
			toT = (str) -> {return (T)str;};
		}
		
		public DataColumn(Label label, Function<DataPoint,String> tostring, Function<String,T> toT) {
			this(label);
			this.tostring = tostring;
			this.toT  = toT;
		}
		
		List<DataPoint> getData() {
			return data;
		}
		
		public void add(DataPoint... dp) {
			for(DataPoint point:dp) {
				point.setStringRepresentation(tostring);
				this.data.add(point);
			}
		}
		
		public T cast(String representation) {
			return toT.apply(representation);
		}
		
		@SuppressWarnings("unchecked")
		public T getValue(int i) {
			if(i < 0 || i >= data.size()) {
				throw new IllegalArgumentException("Date '" + i + "' does not exist...");
			}
			return (T)data.get(i).getValue();
		}
	}

	protected List<DataColumn<?>> data;
	protected String name;
	
	AExperiment(String name) {
		data = new ArrayList<DataColumn<?>>();
		this.name = name;
		addColumns();
		sort();
	}
	
	public String getName() {
		return name;
	}
	
	public void addColumn(DataColumn<?> column) {
		data.add(column);
	}
	
	public void addRow(DataPoint... data) {
		for(DataPoint dp : data) {
			DataColumn<?> column = this.data.stream().filter(c -> c.getLabel().equals(dp.getLabel())).findFirst().get();
			dp.setStringRepresentation(column.tostring);
			column.add(dp);
		}
	}
	
	public void addRow(Object... data) {
		if(data.length != this.data.size()) {
			throw new IllegalArgumentException("Number of columns (="+this.data.size()+") and number of input objects (="+data.length+") don't match!");
		}
		
		int i = 0;
		for(Object o : data) {
			DataColumn<?> c = this.data.get(i++);
			c.add(new DataPoint(o, c.getLabel(), c.tostring));
		}
	}
	
	public static <T> void addToColumn(DataColumn<T> column, T value) {
		column.add(new DataPoint(value, column.getLabel(), column.tostring));
	}
	
	protected abstract void addColumns();
	protected void sort() {}
	
	public void writeToFile(File file) throws IOException {
		if(!file.exists()) {
			//file.mkdirs();
			file.createNewFile();
		}
		StringBuffer result=new StringBuffer();
		result.append(getHeader().stream().collect(Collectors.joining("|")) + "\n");
		
		getRows().stream().forEach(row -> {
			result.append(row.stream().map(r -> r.toString()).collect(Collectors.joining("|")) + "\n");
		});
		
		FileWriter writer = new FileWriter(file);
	    writer.write(result.toString());
	    writer.close();
	}

	public static AExperiment readFromFile(File file) throws IOException {
		AExperiment ae = null;
		if(!file.exists() || file.isDirectory()) {
			throw new IllegalArgumentException("'" + file.getPath() + "' is not a valid (experiment) file...");
		}
		
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		if(!lines.isEmpty()) { //hope for the best and only test for the right number of columns
			String line = lines.get(0);
			if(line.split("\\|").length == SingleExperiment.BaseLabel.values().length) {
				ae = new SingleExperiment(file.getName());
			} else if(line.split("\\|").length == PairExperiment.BaseLabel.values().length) {
				ae = new PairExperiment(file.getName());
			} else {
				throw new IOException("File seems to be corrupted... not a valid experiment file!");
			}
			lines.remove(0); //remove header...
		}
		for(String line : lines) {
			Object[] objects = line.replace(" ", "").split("\\|");
			List<Object> l = new ArrayList<Object>();
			for(int i = 0; i < objects.length; ++i) {
				l.add(ae.getColumn(i).cast((String)objects[i]));
			}
			ae.addRow(l.toArray());
		}
		return ae;
	}
	
	public List<String> getHeader() {
		return data.stream().map(c -> c.getLabel().toString()).collect(Collectors.toList());
	}
	
	public List<DataColumn<?>> getColumns() {
		 return data;
	}
	
	public DataColumn<?> getColumn(int i) {
		if(i < 0 || data.isEmpty() || i >= data.size()) {
			throw new IllegalArgumentException("Data column '" + i + "' does not exist...");
		}
		return data.get(i);
	}
	
	public DataColumn<?> getColumn(Label l) {
		if(data.isEmpty() || !data.stream().anyMatch(d -> d.getLabel().equals(l))) {
			throw new IllegalArgumentException("Data column '" + l.toString() + "' does not exist...");
		}
		return data.stream().filter(d -> d.getLabel().equals(l)).collect(Collectors.toList()).get(0);
	}
	
	public int getNumberOfRows() {
		if(data.isEmpty()) {
			return 0;
		}
		return data.get(0).getData().size();
	}
	
	public List<DataPoint> getRow(int i) {		
		if(i < 0 || i >= getNumberOfRows()) {
			throw new IllegalArgumentException("Data row '" + i + "' does not exist...");
		}
		
		List<DataPoint> result = new ArrayList<DataPoint>();
		
		for(DataColumn<?> column : data) {
			result.add(new DataPoint(column.getValue(i), column.getLabel(), column.tostring));
		}
		return result;
	}
	
	public List<List<DataPoint>> getRows() {
		List<List<DataPoint>> result = new ArrayList<List<DataPoint>>();
		for(int i = 0; i < getNumberOfRows(); ++i) {
			result.add(getRow(i));
		}
		return result;
	}

}
