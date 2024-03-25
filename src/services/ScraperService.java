/*
 * File: ScraperService
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package services;

import org.openqa.selenium.chrome.ChromeOptions;
import transformers.ObjToJsonTransformer;

/**
 * This class serves as a base class to all the Scraper classes giving them access to ChromeDriver and
 * ObjToJsonTransformer
 */
public abstract class ScraperService {
    final ChromeOptions chromeOptions;

    final ObjToJsonTransformer objToJsonTransformer;

    public ScraperService() {
        this.chromeOptions = new ChromeOptions();
        objToJsonTransformer = new ObjToJsonTransformer();
    }
}
