/*
 * File: Regulations
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package model;

import java.util.List;
import java.util.stream.Collectors;

public class Regulation {
    String title;
    String url;
    List<String> chapters;

    public Regulation(String title, String url, List<String> chapters) {
        this.title = title;
        this.url = url;
        this.chapters = chapters;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getChapters() {
        return chapters;
    }

    @Override
    public String toString() {
        return "{" + "\"title\": \"" + getTitle() + "\"," + "\"url\": \"" + getUrl() + "\"," + "\"chapters\": [" + getChapters().stream()
                                                                                                                               .map(chapter -> "\"" + chapter + "\"")
                                                                                                                               .collect(
                                                                                                                                       Collectors.joining(
                                                                                                                                               ",")) + "]}";
    }
}
