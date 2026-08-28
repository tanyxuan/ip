package joebiden.task;

import joebiden.exception.JoeBidenException;
import java.util.ArrayList;

/**
 * Manages the collection of tasks in the chatbot.
 */
public class TaskList {

    private final ArrayList<Task> list;
    /**
     * Creates an empty task list.
     */
    public TaskList() {
        list = new ArrayList<>();
    }
    /**
     * Creates a task list containing the given tasks.
     *
     * @param tasks Tasks to initialize the list with.
     */
    public TaskList(ArrayList<Task> tasks) {
        list = tasks;
    }
    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        list.add(task);
    }
    /**
     * Deletes the task with the given task number.
     *
     * @param number Task number to delete.
     * @return The removed task.
     * @throws JoeBidenException If the task number does not exist.
     */
    public Task deleteTask(int number) throws JoeBidenException {
        validateTaskNumber(number);
        return list.remove(number - 1);
    }
    /**
     * Returns the task with the given task number.
     *
     * @param number Task number to retrieve.
     * @return The requested task.
     * @throws JoeBidenException If the task number does not exist.
     */
    public Task getTask(int number) throws JoeBidenException {
        validateTaskNumber(number);
        return list.get(number - 1);
    }
    /**
     * Marks the specified task as completed.
     *
     * @param number Task number to mark.
     * @return Message describing the updated task.
     * @throws JoeBidenException If the task number does not exist.
     */
    public String markTask(int number) throws JoeBidenException {
        Task task = getTask(number);
        return task.markDone();
    }
    /**
     * Marks the specified task as not completed.
     *
     * @param number Task number to unmark.
     * @return Message describing the updated task.
     * @throws JoeBidenException If the task number does not exist.
     */
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
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return list.size();
    }
    /**
     * Returns a formatted representation of all tasks in the list.
     *
     * @return Formatted task list.
     */
    public String listTasks() {
        String output = "Here are the tasks in your list:\n";

        for (int i = 0; i < list.size(); i++) {
            output += (i + 1) + ". " + list.get(i) + "\n";
        }

        return output;
    }
    /**
     * Returns the underlying list of tasks.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getList() {
        return list;
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

}