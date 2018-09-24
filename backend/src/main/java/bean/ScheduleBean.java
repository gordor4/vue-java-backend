package bean;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class ScheduleBean
{
	@Schedule(hour = "0")
	private void updatePeriodicalPayments()
	{
		//TODO: implement logic
	}
}
