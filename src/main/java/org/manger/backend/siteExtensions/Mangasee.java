package org.manger.backend.siteExtensions;

import java.io.File;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Mangasee implements MangaWebsite {
    String URL = "https://mangasee123.com/directory/";

    @Override
    public List<String> loadListOfAllMangas() throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "E:/Programming/manger/src/main/java/org/manger/backend/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            driver.get(URL);
            List<WebElement> elements = driver.findElements(By.className("ttip ng-binding"));
            for(WebElement element : elements) {
                System.out.println(element.getText());
            }
        } finally {
            driver.quit();
        }

        return null;
    }
}
