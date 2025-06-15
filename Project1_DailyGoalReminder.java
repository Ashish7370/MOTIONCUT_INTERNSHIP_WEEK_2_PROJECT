import java.util.*;
import java.time.*;
import java.util.Timer;
import java.util.TimerTask;

public class GoalReminder {
    static class ReminderTask extends TimerTask {
        String goal;
        public ReminderTask(String goal) {
            this.goal = goal;
        }
        public void run() {
            System.out.println("⏰ Reminder: " + goal);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> goals = new ArrayList<>();
        List<LocalTime> goalTimes = new ArrayList<>();

        System.out.print("How many goals do you want to set today? ");
        int count = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.print("Enter goal " + (i + 1) + ": ");
            String goal = scanner.nextLine();
            goals.add(goal);

            System.out.print("Enter time for this goal (HH:mm): ");
            String timeInput = scanner.nextLine();

            try {
                LocalTime time = LocalTime.parse(timeInput);
                goalTimes.add(time);
            } catch (Exception e) {
                System.out.println("❌ Invalid time format. Skipping this goal.");
                goals.remove(goals.size() - 1);
            }
        }

        Timer timer = new Timer();

        for (int i = 0; i < goals.size(); i++) {
            LocalTime now = LocalTime.now();
            LocalTime scheduledTime = goalTimes.get(i);

            long delay = Duration.between(now, scheduledTime).toMillis();
            if (delay < 0) {
                System.out.println("⚠️ Time for goal '" + goals.get(i) + "' is already past. Skipping.");
                continue;
            }

            timer.schedule(new ReminderTask(goals.get(i)), delay);
        }

        System.out.println("✅ All reminders are set! Keep this application running.");
    }
}
