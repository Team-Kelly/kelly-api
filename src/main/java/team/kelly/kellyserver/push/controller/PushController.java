package team.kelly.kellyserver.push.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.push.service.PushService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class PushController {

    private final PushService pushService;

    @PostMapping("/push/send")
    public String sendFCMPush(String appToken) throws FirebaseMessagingException {

        return pushService.sendFCMPush(appToken);

    }
}
