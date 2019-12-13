package de.tubs.isf.guido.key.pooling.distributed;

import java.util.Observable;

/**
*
* @author  Carsten Padylla
* @author  Maren Süwer
* @author  Alexander Knüppel
* 
* @version 1.0
* 
* @since   2018-01-01 
*/
public class NewResultNotifier extends Observable {

	public void notifyAboutResult(Object o) {
		setChanged();
		notifyObservers(o);
	}
}
