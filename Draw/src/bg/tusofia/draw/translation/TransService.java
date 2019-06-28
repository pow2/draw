package bg.tusofia.draw.translation;

import javax.annotation.ManagedBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@ManagedBean
@Stateless(name="TransService")
public class TransService {
	
	@Schedule(hour="2", minute="0", second="0", persistent=false)
	public void executeService() {
	    Translation.cacheRefresh();
	}
	
}
