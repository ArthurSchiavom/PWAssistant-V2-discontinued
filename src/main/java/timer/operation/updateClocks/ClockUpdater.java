package timer.operation.updateClocks;

import information.Bot;
import information.clocks.ClockRegister;
import timer.operation.TimerOperation;

public class ClockUpdater implements TimerOperation {
	@Override
	public void operate() {
		if (!Bot.isTestAccount())
			ClockRegister.getInstance().updateAllClocks();
	}
}
