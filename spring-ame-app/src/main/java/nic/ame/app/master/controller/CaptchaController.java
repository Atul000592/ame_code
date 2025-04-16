package nic.ame.app.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nic.ame.app.esign.StringConstants;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.Random;


@Controller
public class CaptchaController {
	

    private static final String CAPTCHA_SESSION_ATTR = "captcha";

    @CrossOrigin(originPatterns = { StringConstants.CROSS_ORIGIN_BASE_URL}, allowCredentials = "true")
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 150;
        int height = 50;
        int fontSize = 24;
        int charsToPrint = 6;

        char[] chars = "ABCDEFGHJKMNOPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz0123456789".toCharArray();

        // Create a buffered image with grey background
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Set background color to grey
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, width, height);

        // Generate random circles
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int circleRadius = random.nextInt(10) + 5;
            int circleX = random.nextInt(width - circleRadius);
            int circleY = random.nextInt(height - circleRadius);
            g2d.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g2d.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
        }

        // Generate random text with black color
        StringBuilder captchaText = new StringBuilder();
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
        g2d.setColor(Color.BLACK);  // Set text color to black
        for (int i = 0; i < charsToPrint; i++) {
            char c = chars[random.nextInt(chars.length)];
            captchaText.append(c);
            g2d.drawString(String.valueOf(c), 20 + i * 20, 30 + random.nextInt(15));
        }

        g2d.dispose();

        // Store captcha text in session
        request.getSession().setAttribute(CAPTCHA_SESSION_ATTR, captchaText.toString());

        // Write the image as PNG
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        outputStream.close();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/refresh-captcha")
    @ResponseBody
    public String refreshCaptcha(HttpServletRequest request) {
        // Invalidate existing captcha in session
        request.getSession().removeAttribute(CAPTCHA_SESSION_ATTR);
        return "success";
    }
    
}
