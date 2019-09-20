package de.tu.bs.guido.key.network.server;

import java.util.Comparator;

import de.tu.bs.guido.key.network.Job;
import keYHandler.options.OptionableContainer;

public class CodeComparator implements Comparator<Job>{

	private String[] arr;
	
	public CodeComparator(OptionableContainer... oc){
		arr = new String[oc.length];
		for (int i = 0; i < oc.length; i++) {
			String[] splitted = oc[i].getValue().split(" ");
			StringBuilder sb = new StringBuilder();
			for (String string : splitted) {
				char[] cs = string.toCharArray();
				cs[0] = Character.toUpperCase(cs[0]);
				sb.append(cs);
			}
			arr[i] = sb.toString();
		}
	}
	
	@Override
	public int compare(Job o1, Job o2) {
		
		for (String s : arr) {
			boolean o1c = o1.getCode().contains(s);
			boolean o2c = o2.getCode().contains(s);
			
			if (o1c && !o2c){
				return -1;
			} else if (!o1c && o2c){
				return 1;
			}
		}
		return 0;
	}

}
