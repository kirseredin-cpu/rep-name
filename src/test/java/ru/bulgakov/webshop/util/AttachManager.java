package ru.bulgakov.webshop.util;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.sessionId;


public class AttachManager {



    @Attachment(value = "last-screenshot", type = "image/png")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "page-source", type = "text/html")
    public static byte[] getPageSource() {
        return WebDriverRunner.getWebDriver()
                .getPageSource()
                .getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "browser-console-logs", type = "text/plain")
    public static String getBrowserConsoleLogs() {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            return "Браузер ещё не запущен";
        }
        try {
            LogEntries logs = WebDriverRunner.getWebDriver()
                    .manage().logs().get(LogType.BROWSER);
            if (logs.getAll().isEmpty()) {
                return "Логи пустые";
            }
            return logs.getAll().stream()
                    .map(LogEntry::toString)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "Не удалось получить логи: " + e.getMessage();
        }
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                +  getVideoUrl() + "' type='video/mp4'></video></body></html>";
    }

    private static URL getVideoUrl() {
        String videoUrl = "https://selenoid.autotests.cloud/video/" + sessionId() + ".mp4";

        try {
            return new URL(videoUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}