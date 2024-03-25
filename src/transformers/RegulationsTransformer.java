/*
 * File: RegulationsTransformer
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package transformers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RegulationsTransformer {

    /**
     * This class should not be instantiated as all methods are supposed to be static
     */
    private RegulationsTransformer() {
        throw new IllegalStateException();
    }

    /**
     * this method fetches and cleans the chapter Name from WebElement
     *
     * @param webElement {@link WebElement} WebElement from which to extract chapter Name.
     * @return {@link List<String>} list of chapter names extracted from the WebElement.
     */
    public static List<String> extractChapterNames(WebElement webElement) {
        List<WebElement> webElementList = webElement.findElements(By.tagName("a"));

        return webElementList.stream().map(RegulationsTransformer::extractTextFromContent).toList();
    }

    /**
     * this method extracts the url saved inside href attribute from WebElement
     *
     * @param li {@link WebElement} WebElement from which to extract url.
     * @return {@link String} Url extracted from the WebElement.
     */
    public static String extractUrlFromTitle(WebElement li) {
        WebElement a = li.findElement(By.tagName("a"));
        return a.getAttribute("href");
    }

    /**
     * this method extracts the text from Content WebElement
     *
     * @param element {@link WebElement} WebElement from which to extract text.
     * @return {@link String} Text extracted from the WebElement.
     */
    public static String extractTextFromContent(WebElement element) {
        return element.findElement(By.tagName("ul")).findElement(By.tagName("li")).getText();
    }


}
