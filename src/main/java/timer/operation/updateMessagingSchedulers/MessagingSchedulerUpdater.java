package timer.operation.updateMessagingSchedulers;

import information.Bot;
import information.scheduling.messageSchedule.MessagingSchedulerRegister;
import timer.operation.TimerOperation;

public class MessagingSchedulerUpdater implements TimerOperation {
	@Override
	public void operate() {
		if (!Bot.isTestAccount())
			MessagingSchedulerRegister.checkAllSchedules();
	}
}
