package cn.globalcash.spring.cloud.event;

import cn.globalcash.spring.cloud.domain.User;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class UserRemoteApplicationEvent extends RemoteApplicationEvent {
    public UserRemoteApplicationEvent(){

    }
    public UserRemoteApplicationEvent(User user, String originService,
                                      String destinationService) {
        super(user, originService, destinationService);
    }

}
