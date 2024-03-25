import services.CourtCaseScraperService;
import services.RegulationsScraperService;

public class Main {

    public static void main(String[] args) {
        CourtCaseScraperService courtCaseScraperService = new CourtCaseScraperService();

        RegulationsScraperService regulationsScraperService = new RegulationsScraperService();


        courtCaseScraperService.scrape();
        regulationsScraperService.scrape();
    }
}