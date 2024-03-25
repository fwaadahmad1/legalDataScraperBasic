/*
 * File: CourtCases
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package model;

public class CourtCase {
    String name;
    String decidedDate;
    String docket;
    String court;

    public CourtCase(String name, String decidedDate, String docket, String court) {
        this.name = name;
        this.decidedDate = decidedDate;
        this.docket = docket;
        this.court = court;
    }

    public String getName() {
        return name;
    }

    public String getDecidedDate() {
        return decidedDate;
    }

    public String getDocket() {
        return docket;
    }

    public String getCourt() {
        return court;
    }

    @Override
    public String toString() {
        return "{" + "\"name\": \"" + this.getName()
                                          .replaceAll("\n",
                                                      " ") + "\"," + "\"decidedDate\": \"" + this.getDecidedDate() + "\"," + "\"docket\": \"" + this.getDocket() + "\"," + "\"court\": \"" + this.getCourt() + "\"" + "}";
    }
}
