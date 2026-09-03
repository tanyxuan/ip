package joebiden.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import joebiden.exception.JoeBidenException;

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

        assertEquals(true, tasks.getTask(1).isDone());
    }
}
