package de.tubs.isf.guido.key.logging.network.order;

import de.tubs.isf.guido.key.logging.Logger;

public class LogOrderLoggerMapper {

	public static void map(String prefix, LogOrder order, Logger logTo){
		switch (order.getLevel()) {
		case ERROR:
			logTo.logError(prefix);
			if(order.getE() == null){
				logTo.logError(order.getText());
			} else {
				logTo.logError(order.getE());
			}
			break;
		case STATUS:
			logTo.logStatus(prefix);
			logTo.logStatus(order.getText(), order.getStes());
			break;
		case VERBOSE:
			logTo.logVerbose(prefix);
			logTo.logVerbose(order.getText(), order.getStes());
			break;
		case WARN:
			logTo.logWarning(prefix);
			logTo.logWarning(order.getText(), order.getStes());
			break;
		}
	}
}
