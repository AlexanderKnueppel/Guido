package de.tubs.isf.guido.key.pooling.distributed;

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
public interface ResultRunnable extends Runnable {

	Object getResult();

	void setIPAdress(String ip);
}
