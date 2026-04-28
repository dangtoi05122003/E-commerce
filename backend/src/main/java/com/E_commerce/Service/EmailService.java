package com.E_commerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Xác thực tài khoản E-commerce");
            helper.setText(buildEmailContent(otp), true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new AppException(ErrorCode.SEND_EMAIL_FAILED);
        }
    }
    private String buildEmailContent(String otp) {
        return """
            <div style="font-family: Arial; max-width:600px;margin:auto;padding:20px;">
                <h2 style="text-align:center;color:#0056b3;">Xác thực tài khoản</h2>
                <p>Mã OTP của bạn:</p>
                <div style="text-align:center;font-size:36px;font-weight:bold;background:#eee;padding:10px;border-radius:8px;">
                    %s
                </div>
                <p style="text-align:center;">Hiệu lực 5 phút</p>
            </div>
        """.formatted(otp);
    }
}