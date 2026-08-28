import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> list;

    public TaskList() {
        list = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        list = tasks;
    }

    public void addTask(Task task) {
        list.add(task);
    }

    public Task deleteTask(int number) throws JoeBidenException {
        validateTaskNumber(number);
        return list.remove(number - 1);
    }

    public Task getTask(int number) throws JoeBidenException {
        validateTaskNumber(number);
        return list.get(number - 1);
    }

    private void validateTaskNumber(int number) throws JoeBidenException {
        if (number < 1 || number > list.size()) {
            throw new JoeBidenException(
                    "That task number does not exist."
            );
        }
    }

    public int size() {
        return list.size();
    }

    public String listTasks() {
        String output = "Here are the tasks in your list:\n";

        for (int i = 0; i < list.size(); i++) {
            output += (i + 1) + ". " + list.get(i) + "\n";
        }

        return output;
    }

    public ArrayList<Task> getList() {
        return list;
    }
}