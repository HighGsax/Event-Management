import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class EventManagerUI extends JFrame {

    private final EventManager eventManager = new EventManager();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final JTextArea eventListTextArea;
    private final JTextField eventNameTextField;
    private final JTextArea eventDescriptionTextField;

    private final JTextField eventVenueTextField;

    private final JTextField eventStartTimeTextField;
    private final JTextField eventEndTimeTextField;

    public EventManagerUI() {
        setTitle("Event Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 800);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Event input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 4, 12));

        inputPanel.add(new JLabel("Event Name: "));
        eventNameTextField = new JTextField();
        inputPanel.add(eventNameTextField);

        inputPanel.add(new JLabel("Event Description: "));
        eventDescriptionTextField = new JTextArea();
        eventDescriptionTextField.setRows(3);
        inputPanel.add(eventDescriptionTextField);

        inputPanel.add(new JLabel("Event Venue: "));
        eventVenueTextField = new JTextField();
        inputPanel.add(eventVenueTextField);

        inputPanel.add(new JLabel("Start Time (yyyy-MM-dd HH:mm): "));
        eventStartTimeTextField = new JTextField();
        inputPanel.add(eventStartTimeTextField);

        inputPanel.add(new JLabel("End Time (yyyy-MM-dd HH:mm): "));
        eventEndTimeTextField = new JTextField();
        inputPanel.add(eventEndTimeTextField);

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEvent();
            }
        });
        inputPanel.add(addButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Event list panel
        JPanel eventListPanel = new JPanel(new BorderLayout());

        eventListTextArea = new JTextArea();
        eventListTextArea.setEditable(false);
        JScrollPane eventListScrollPane = new JScrollPane(eventListTextArea);
        eventListPanel.add(eventListScrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshEventList();
            }
        });
        eventListPanel.add(refreshButton, BorderLayout.SOUTH);

        mainPanel.add(eventListPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void addEvent() {
        String eventName = eventNameTextField.getText().trim();
        String eventDescription = eventDescriptionTextField.getText().trim();
        String eventVenue = eventVenueTextField.getText().trim();
        String startTimeString = eventStartTimeTextField.getText().trim();
        String endTimeString = eventEndTimeTextField.getText().trim();

        if (eventName.isEmpty() || startTimeString.isEmpty() || endTimeString.isEmpty() || eventVenue.isEmpty() || eventDescription.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.");
            return;
        }

        try {
            LocalDateTime startTime = LocalDateTime.parse(startTimeString, dateTimeFormatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeString, dateTimeFormatter);

            if (endTime.isBefore(startTime)) {
                JOptionPane.showMessageDialog(this, "End time must be after start time.");
                return;
            }

            Event event = new Event(eventName, startTime, endTime, eventDescription, eventVenue);
            eventManager.addEvent(event);

            refreshEventList();

            eventNameTextField.setText("");
            eventDescriptionTextField.setText("");
            eventVenueTextField.setText("");
            eventStartTimeTextField.setText("");
            eventEndTimeTextField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please enter dates in the format yyyy-MM-dd HH:mm.");
        }
    }

    private void refreshEventList() {
        List<Event> events = eventManager.getEvents();
        events.sort(Comparator.comparing(Event::getStartTime));

        eventListTextArea.setText("");

        if (events.isEmpty()) {
            eventListTextArea.append("No events found.");
            return;
        }

        for (Event event : events) {
            String eventName = event.getName();
            String eventDescription = event.getDescription();
            String eventVenue = event.getVenue();
            String startTime = dateTimeFormatter.format(event.getStartTime());
            String endTime = dateTimeFormatter.format(event.getEndTime());

            eventListTextArea.append("Event Name: " + eventName + "\n");
            eventListTextArea.append("Description: " + eventDescription + "\n");
            eventListTextArea.append("Venue: " + eventVenue + "\n");
            eventListTextArea.append("Start Time: " + startTime + "\n");
            eventListTextArea.append("End Time: " + endTime + "\n");
            eventListTextArea.append("\n");
        }
    }
}
