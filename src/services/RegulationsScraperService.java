/*
 * File: RegulationsScraperService
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package services;

import model.Regulation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import transformers.RegulationsTransformer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to scrape the web for Regulations and Statutes Data. This class inherits ChromeDriver and
 * ObjToJsonTransformer from ScraperService class.
 */
public class RegulationsScraperService extends ScraperService {

    public static final String URL = "https://codes.findlaw.com/{option}/";
    public static final List<String> URL_OPTIONS = List.of("cfr", "us");
    private final ChromeDriver driver = new ChromeDriver(chromeOptions);


    /**
     * This method starts the web scraping for Regulations and Statutes in the web
     */
    public void scrape() {
        // load the URL with different variants of webpages
        URL_OPTIONS.forEach(option -> {
            driver.get(URL.replace("{option}", option));

            // wait until element with className landingContent is loaded.
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("landingContent")));

            // extract the titleElements from the page
            List<WebElement> titleElements = element.findElement(By.tagName("ul")).findElements(By.tagName("li"));

            // transform and save the title element text and url they lead to in the list
            List<Regulation> regulationList = titleElements.stream()
                                                           .map(webElement -> new Regulation(webElement.getText()
                                                                                                       .substring(
                                                                                                               webElement.getText()
                                                                                                                         .indexOf(
                                                                                                                                 ".") + 1)
                                                                                                       .trim(),
                                                                                             RegulationsTransformer.extractUrlFromTitle(
                                                                                                     webElement),
                                                                                             new ArrayList<>()))
                                                           .toList();

            // loop over the list created to visit each url saved in each element of the list to scrape data
            regulationList.forEach(regulation -> {
                driver.get(regulation.getUrl());

                // scrape the data for all regulations for each chapter in list.
                WebElement content = driver.findElement(By.className("codesTocContent"));

                // transform and save it to the same list element.
                regulation.getChapters().addAll(RegulationsTransformer.extractChapterNames(content));
            });

            // transform the data into Json and save it in output folder
            objToJsonTransformer.transformAndWrite(option + "_regulations", regulationList);
        });
        // close the chrome driver
        driver.quit();
    }
}
