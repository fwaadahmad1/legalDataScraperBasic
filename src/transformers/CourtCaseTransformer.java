/*
 * File: CourtCaseTransformer
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package transformers;

import model.CourtCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a transformer class that helps with transforming web data to required format
 */
public class CourtCaseTransformer {
    /**
     * This class should not be instantiated as all methods are supposed to be static
     */
    private CourtCaseTransformer() {
        throw new IllegalStateException();
    }

    /**
     * fetch and transform data from webElement to CourtCase Type Object for www.supremecourt.gov.
     *
     * @param table {@link WebElement} table element containing all the data to be used.
     * @return {@link List<CourtCase>} transformed data in meaningful object list format.
     */
    public static List<CourtCase> transformSupremeCourtGov(@Nullable WebElement table) {
        if (table == null) return new ArrayList<>();
        List<CourtCase> courtCaseList = new ArrayList<>();
        final List<WebElement> rows = table.findElements(By.tagName("tr"));

        rows.forEach(row -> {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6 && !cells.get(3).getText().isBlank())
                courtCaseList.add(new CourtCase(cells.get(3).getText(),
                                                cells.get(1).getText(),
                                                cells.get(2).getText(),
                                                "U.S. Supreme Court"));
        });

        return courtCaseList;
    }

    /**
     * fetch and transform data from webElement to CourtCase Type Object for caselaw.findlaw.com.
     *
     * @param table {@link WebElement} table element containing all the data to be used.
     * @param courtName {@link String} name of the court for which the table is provided.
     * @return {@link List<CourtCase>} transformed data in meaningful object list format
     */
    public static List<CourtCase> transformFindLaw(@Nullable WebElement table, String courtName) {
        if (table == null) return new ArrayList<>();
        List<CourtCase> courtCaseList = new ArrayList<>();
        final List<WebElement> rows = table.findElements(By.tagName("tr"));

        rows.forEach(row -> {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3 && !cells.get(0).getText().isBlank())
                courtCaseList.add(new CourtCase(cells.get(0).getText(),
                                                cells.get(1).getText(),
                                                cells.get(2).getText().replaceAll("No.", "").trim(),
                                                courtName));
        });

        return courtCaseList;
    }
}
