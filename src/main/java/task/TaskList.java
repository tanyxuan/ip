package task;

import exception.JoeBidenException;
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

    public String markTask(int number) throws JoeBidenException {
        Task task = getTask(number);
        return task.markDone();
    }

    public String unmarkTask(int number) throws JoeBidenException {
        Task task = getTask(number);
        return task.unmark();
    }
    private void validateTaskNumber(int number) throws JoeBidenException {
        if (number < 1 || number > list.size()) {
            throw new JoeBidenException(
                    "That task number does not exist."
            );
        }
    }
    /**
     * Finds tasks whose descriptions contain the given keyword.
     *
     * @param keyword Keyword to search for in task descriptions.
     * @return Formatted list of matching tasks.
     */
    public String findTasks(String keyword) {
        String output = "Here are the matching tasks in your list:\n";
        int count = 1;

        for (Task task : list) {
            if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
                output += count + ". " + task + "\n";
                count++;
            }
        }

        return output;
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