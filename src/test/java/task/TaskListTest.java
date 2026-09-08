package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import joebiden.exception.JoeBidenException;
import joebiden.task.Deadline;
import joebiden.task.Event;
import joebiden.task.Task;
import joebiden.task.TaskList;
import joebiden.task.Todo;

public class TaskListTest {

    @Test
    public void deleteTask_validNumber_removesCorrectTask()
            throws JoeBidenException {
        TaskList tasks = new TaskList();

        tasks.addTask(new Todo("read book"));
        tasks.addTask(new Todo("sleep"));

        Task removed = tasks.deleteTask(1);

        assertEquals("read book", removed.getName());
        assertEquals(1, tasks.size());
        assertEquals("sleep", tasks.getTask(1).getName());
    }

    @Test
    public void deleteTask_invalidNumber_throwsException() {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("read book"));

        assertThrows(JoeBidenException.class, () -> tasks.deleteTask(5));
    }

    @Test
    public void markTask_validNumber_marksTaskDone()
            throws JoeBidenException {
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("read book"));

        tasks.markTask(1);

        assertTrue(tasks.getTask(1).isDone());
    }

    @Test
    public void getTomorrowReminders_deadlineTomorrow_returnsReminder() {
        TaskList tasks = new TaskList();

        LocalDateTime tomorrow = LocalDate.now()
                .plusDays(1)
                .atTime(23, 59);

        tasks.addTask(new Deadline("submit assignment", tomorrow));

        String reminders = tasks.getTomorrowReminders();

        assertTrue(reminders.contains("submit assignment"));
    }

    @Test
    public void getTomorrowReminders_eventTomorrow_returnsReminder() {
        TaskList tasks = new TaskList();

        LocalDateTime tomorrow = LocalDate.now()
                .plusDays(1)
                .atTime(14, 0);

        Event event = new Event(
                "project meeting",
                tomorrow,
                tomorrow.plusHours(1)
        );

        tasks.addTask(event);

        String reminders = tasks.getTomorrowReminders();

        assertTrue(reminders.contains("project meeting"));
    }

    @Test
    public void getTomorrowReminders_noTasksTomorrow_returnsEmptyString() {
        TaskList tasks = new TaskList();

        LocalDateTime futureDate = LocalDate.now()
                .plusDays(5)
                .atTime(23, 59);

        tasks.addTask(new Deadline("future assignment", futureDate));

        assertEquals("", tasks.getTomorrowReminders());
    }

    @Test
    public void getTomorrowReminders_completedTaskTomorrow_notIncluded()
            throws JoeBidenException {
        TaskList tasks = new TaskList();

        LocalDateTime tomorrow = LocalDate.now()
                .plusDays(1)
                .atTime(23, 59);

        tasks.addTask(new Deadline("submit assignment", tomorrow));

        tasks.markTask(1);

        assertEquals("", tasks.getTomorrowReminders());
    }
}
