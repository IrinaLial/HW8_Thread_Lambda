package library;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.Random;


public class Library {
    private int maxAmount = 12;
    private int peopleCount = 0;
    private Semaphore semaphore = new Semaphore(maxAmount);

    private ArrayList<User> users = new ArrayList<>();

    public void enterAll(ArrayList<User> users) {
        this.users = users;
        this.peopleCount = users.size();

        Random random = new Random();
        for(User user : users) {
            final User tempUser = user;
            new Thread(() -> {
                try {
                   enter(tempUser);
                    System.out.println(user.getName() + "\t" + user.getIp() + " is reading a book");
                    Thread.sleep(5000);
                    leave(tempUser);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void enter(User user) throws InterruptedException {
        System.out.println(user.getName() + "\t" + user.getIp() + " trying to enter the library\t\t Available slots: "
                + semaphore.availablePermits());
        semaphore.acquire();
        users.add(user);
        System.out.println(user.getName() + "\t" + user.getIp() + " has entered\t\t\t\t\t\t Available slots: "
                + semaphore.availablePermits());
    }

    public void leave(User user) throws InterruptedException {
        System.out.println(user.getName() + "\t" + user.getIp() + " trying to leave the library\t\t Available slots: "
                + semaphore.availablePermits());
        users.remove(user);
        semaphore.release();
        System.out.println(user.getName() + "\t" + user.getIp() + " has left the library\t\t\t\t Available slots: "
                + semaphore.availablePermits());
    }
}