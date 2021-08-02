package team.kelly.kellyserver.push.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class PushService {

    public String sendFCMPush(String appToken) throws FirebaseMessagingException {

        // This registration token comes from the client FCM SDKs.
        String registrationToken = appToken;

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("title", "테스트 타이틀")
                .putData("content", "테스트 콘텐츠입니다.")
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);

        return response;
    }

}
