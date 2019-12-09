package de.tu.bs.guido.network.server;

import java.util.Comparator;

import de.tu.bs.guido.verification.system.IJob;
import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;

public class CodeComparator implements Comparator<IJob>{

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
	public int compare(IJob o1, IJob o2) {
		
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
