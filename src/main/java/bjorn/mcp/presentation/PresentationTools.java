package bjorn.mcp.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class PresentationTools {

    private final List<Presentation> presentations = new ArrayList<>();
    private final List<ToInterpretFile> toInterpretFiles = new ArrayList<>();

    public PresentationTools() {
        var jave24Launch = new Presentation("Java 24 launch", "https://www.youtube.com/watch?v=1gX2v4j0k8E", 2021);
        var jave23Launch = new Presentation("Java 23 launch", "https://www.youtube.com/watch?v=1gX2v4j0k8E", 2021);
        var jave22Launch = new Presentation("Java 22 launch", "https://www.youtube.com/watch?v=1gX2v4j0k8E", 2021);
        var jave21Launch = new Presentation("Java 21 launch", "https://www.youtube.com/watch?v=1gX2v4j0k8E", 2021);
        var jave20Launch = new Presentation("Java 20 launch", "https://www.youtube.com/watch?v=1gX2v4j0k8E", 2021);
        var jave19Launch = new Presentation("Java 29 launch", "https://www.youtube.com/watch?v=1gX2v4j0k8E", 2022);
        var jave18Launch = new Presentation("Java 18 launch", "https://www.youtube.com/watch?v=1gX2v4j0k8E", 2023);
        presentations.addAll(List.of(jave18Launch,jave19Launch,jave20Launch,jave21Launch,jave22Launch,jave23Launch,jave24Launch));
    }

    public List<Presentation> getPresentations() {
        return presentations;
    }

    public List<Presentation> getPresentationsByYear(int year) {
        return presentations.stream()
                .filter(presentation -> presentation.year() == year)
                .toList();
    }

    public List<Map<String,Object>> getPresentationsAsMapList() {
        return presentations.stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("title", p.title());
                    map.put("url", p.url());
                    map.put("year", p.year());
                    return map;
                })
                .collect(toList());
    }
}
