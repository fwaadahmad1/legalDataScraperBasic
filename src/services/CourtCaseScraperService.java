/*
 * File: CourtCaseScraper
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package services;

import model.CourtCase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import transformers.CourtCaseTransformer;
import transformers.ObjToJsonTransformer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * This class is used to scrape the web for Court Cases Data. This class inherits ChromeDriver and ObjToJsonTransformer
 * from ScraperService class.
 */
public class CourtCaseScraperService extends ScraperService {
    public static final String URL = "https://www.supremecourt.gov/opinions/slipopinion/23";
    public static final String URL_2 = "https://caselaw.findlaw.com/court/{option}/recent";
    public static final List<String> URL_2_OPTIONS = List.of("us-1st-circuit",
                                                             "us-2nd-circuit",
                                                             "us-3rd-circuit",
                                                             "us-dc-circuit");

    private final ChromeDriver driver = new ChromeDriver(chromeOptions);

    /**
     * This method starts the web scraping for court cases in the web
     */
    public void scrape() {
        driver.get(URL);
        List<CourtCase> courtCaseList = new ArrayList<>();
        // only scrape data for 5 pages
        IntStream.range(0, 5).forEach(i -> {
            // get the pagination element
            List<WebElement> pagination = driver.findElement(By.id("ctl00_ctl00_MainEditable_mainContent_upButtons"))
                                                .findElements(By.tagName("a"));
            // click on the pagination link
            pagination.get(i).click();

            // wait until item with id cellTop10 is loaded.
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cellTop10 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cellTop10")));
            // scrape the data for top 10 recent court cases. transform and save it to the list
            courtCaseList.addAll(CourtCaseTransformer.transformSupremeCourtGov(cellTop10));

            // find the more button and click it to see more cases from apart from top 10
            WebElement button = driver.findElement(By.className("accordion-toggle"));
            button.click();

            // scrape the data for other recent court cases. transform and save it to the list
            WebElement cellMore = driver.findElement(By.id("cellMore"));
            courtCaseList.addAll(CourtCaseTransformer.transformSupremeCourtGov(cellMore));
        });


        // load the second URL with different variants of webpages
        URL_2_OPTIONS.forEach(option -> {
            driver.get(URL_2.replace("{option}", option));
            try {
                // scrape the data for all the court cases in table. transform and save it to the list
                WebElement casesTable = driver.findElement(By.className("responsive-card-table"));
                courtCaseList.addAll(CourtCaseTransformer.transformFindLaw(casesTable, option));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        // transform the data into Json and save it in output folder
        objToJsonTransformer.transformAndWrite("courtCases", courtCaseList);
        // close the chrome driver
        driver.quit();
    }
}
