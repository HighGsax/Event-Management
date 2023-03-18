import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String description;
    private String venue;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Event(String name, LocalDateTime startTime, LocalDateTime endTime, String description, String venue) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.venue = venue;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getVenue() {
        return venue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", description=" + description + '\'' +
                ", venue=" + venue + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

class EventManager {
    private List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Event> getEventsBetween(LocalDateTime start, LocalDateTime end) {
        List<Event> eventsBetween = new ArrayList<>();
        for (Event event : events) {
            if (event.getStartTime().isAfter(start) && event.getEndTime().isBefore(end)) {
                eventsBetween.add(event);
            }
        }
        return eventsBetween;
    }
}
