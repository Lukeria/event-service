package com.eventmanager.eventservice.service.observer;

import com.eventmanager.eventservice.model.Notification;
import com.eventmanager.eventservice.resources.ApplicationProperties;
import com.eventmanager.eventservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final ApplicationProperties applicationProperties;
    private final JavaMailSender emailSender;
    private final UserInfoService userInfoService;

    @Override
    public void sendMessage(SimpleMailMessage message) {
        sendEmailMessage(message);
    }

    @Override
    public void update(Notification notification) {

        List<String> emailList = userInfoService.getEmailList(notification.getUsers());

        for (String email : emailList) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(applicationProperties.getEmail());
            message.setTo(email);
            message.setSubject(notification.getSubject());
            message.setText("Date: " + notification.getDate()+
                    "\nNotification: "+ notification.getMessage());

            sendEmailMessage(message);
        }
    }

    /**
     * Создает email сообщение и отправляет его гостю.
     *
     * @param message Сообщение для отправки.
     * @throws ResponseStatusException если превышено количество попыток отправки email.
     */
    private void sendEmailMessage(SimpleMailMessage message) {
        // Получаем количество возможных попыток отправки email из настроек приложения
        int attemptsCount = applicationProperties.getAttemptsCount();
        // Инициализируем счетчик попыток
        int counter = 0;

        // Пытаемся отправить email сообщение, делая несколько попыток в случае неудачи
        do {
            try {
                // Пытаемся отправить email сообщение
                emailSender.send(message);
                break;
            } catch (MailException e) {
                // В случае возникновения ошибки и наличия попыток, пытаемся отправить сообщение повторно
                if (counter < attemptsCount) {
                    // Логируем ошибку и увеличиваем счетчик попыток
                    log.error("An error occurred while sending email. Attempt " + counter++ + "Details:\n" + e);
                } else {
                    // Выбрасываем исключение с сообщением об ошибке
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
            }
        } while (counter < attemptsCount);
    }
}
