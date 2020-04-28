package main.java.lucia.client.content.employee;


/**
 * Throw this inside a runnable to stop the repeating task.
 * Use with AsynchronousTaskService.scheduleEndingTask
 * @author Matt Kwiatkowski
 */
public class TaskComplete extends RuntimeException{

    public TaskComplete(){
        super("This Exception was thrown to incdicate the end of a running repeating process.");
    }
    @Override
    public void printStackTrace(){
        //No stack trace to print.
    }

}
