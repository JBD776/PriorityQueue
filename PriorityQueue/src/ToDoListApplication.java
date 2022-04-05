import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;


enum Priority {
    URGENT,
    HIGH,
    NORMAL,
    LOW}

enum Status{
    NOT_STARTED,
    IN_PROGRESS,
    WAITING,
    DEFERRED
}

class Task implements Comparable<Task> {
    int taskId;
    String subject;
    Priority priority;
    Status status;
    LocalDate startDate;
    LocalDate dueDate;

    public Task(int taskId, String subject, Priority priority, Status status, LocalDate startDate, LocalDate dueDate) {
        this.taskId = taskId;
        this.subject = subject;
        this.priority = priority;
        this.status = status;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public Task() {

    }

    public int getTaskId(){
        return taskId;
    }
    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", subject='" + subject + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                '}';
    }
    public Priority getPriority() {
        return priority;
    }
    @Override
    public int compareTo(Task task) {
        return this.getPriority().compareTo(task.getPriority());        }

    public String toCommaDelimitedString(){
        return  taskId + "," + subject + "," +
                priority + "," + status + "," +
                startDate.toString() +
                "," +
                dueDate.toString() + "\n";

    }
}

public class ToDoListApplication extends PriorityQueue<Task> {
        Scanner scanner = new Scanner(System.in);
        java.util.PriorityQueue<Task> taskPriorityQueue = new java.util.PriorityQueue<>();

        public static void main(String[] args) {
            ToDoListApplication app = new ToDoListApplication();
            app.testPriorityQueue();
        }
        public Task getTaskById(int taskId)  {
            Iterator it = taskPriorityQueue.iterator();
            while (((Iterator) it).hasNext()) {
                Task task = (Task) it.next();
                if (task.getTaskId() == taskId)
                    return task;
                System.out.println("TaskId not found...");
            }
            return null;
        }
        public void showTaskDetail(int taskId)
        {
            Task task = getTaskById(taskId);
            System.out.println(task);
            // add your code here to print the task.
        }
        public void removeTask(int taskId)  {
            Task task = getTaskById(taskId);


        }
        public void showTaskList() {
            Task[] tasks = this.toArray(new Task[this.size()]);
            Arrays.sort(tasks, this.comparator());
            Iterator it = this.iterator();
            if (!it.hasNext())
                System.out.println("No items in list.");
            for (Task t : tasks) {
                System.out.println(t);
            }
        }

        public  void displayNextTask() {
            if (this.isEmpty()) {
                System.out.println("Queue is empty.");
            }
            Task currentTask = (Task) this.peek();
            System.out.println(currentTask.toString());
            System.out.println("Is this task complete? Y/n:");
            String answer = scanner.nextLine();

            if (answer.equals("y")) {
                this.poll();
            }
            //add code
        }



        public void testPriorityQueue() {
            System.out.println("Welcome to My Task List\n");
            do {
                System.out.println("Choose action (Add(a), Next(n), List(l), Detail(d), Edit(e), Remove(r), Quit(q):");
                String menuItem = scanner.nextLine();
                switch (menuItem) {
                    case "a":
                        addTask();
                        break;
                    case "n":
                        displayNextTask();
                        break;
                    case "l":
                        showTaskSummaryList();
                        break;
                    case "d":
                        System.out.println("Enter taskId: ");
                        int taskId = Integer.parseInt(scanner.nextLine());
                        showTaskDetail(taskId);
                        break;
                    case "r":
                        System.out.println("Enter taskId: ");
                        taskId = Integer.parseInt(scanner.nextLine());
                        removeTask(taskId);
                        break;
                    case "q":
                        break;
                }
            } while (true);
        }
        void showTaskSummaryList() {
            for (Task task: taskPriorityQueue)
                System.out.println(task);
        }
        private Priority scanForPriority() {
            Priority priority = Priority.NORMAL;
            System.out.println("Enter priority abbreviation Normal=n,Low=l,High=h,Urgent=u ");String abbrev = scanner.nextLine();
            switch (abbrev) {
                case "n":
                    priority = Priority.NORMAL;
                    break;
                case "l":
                    priority = Priority.LOW;
                    break;
                case "h":
                    priority = Priority.HIGH;
                    break;
                case "u":
                    priority = Priority.URGENT;
                    break;
            }
            return priority;
        }
        private Status scanForStatus(){
            Status status = Status.WAITING;
            System.out.println("Enter the status abbreviation of the task: Not started = 'ns'; In progress = 'ip'; waiting = 'w'; Deferred = 'd'. ");
            String abbrev = scanner.nextLine();
            switch (abbrev){
                case "ns":
                    status = Status.NOT_STARTED;
                    break;
                case "ip":
                    status = Status.IN_PROGRESS;
                    break;
                case "w":
                    status = Status.WAITING;
                    break;
                case "d":
                    status = Status.DEFERRED;
                    break;
            }
            return status;

        }
        void addTask() {
            System.out.println("Adding new Task...");
            System.out.println("Enter subject:");
            Scanner scanner = new Scanner(System.in);
            String subject = scanner.nextLine();
            System.out.println("Enter due date (yyyy-MM-dd):");
            String input = scanner.nextLine();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dueDate = LocalDate.parse(input);
            Priority priority = scanForPriority();
            Status status = scanForStatus();
            LocalDate date = LocalDate.now();
            LocalDate startDate = LocalDate.now();
            Task task = new Task(taskPriorityQueue.size() + 1, subject, priority,
                    status, startDate, dueDate);
            taskPriorityQueue.add(task);
        }
    }
