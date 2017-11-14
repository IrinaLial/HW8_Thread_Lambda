package library;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.Random;


public class Library {
    private static final int maxAmount = 12;
    private int peopleCount = 0;
    private Semaphore semaphore = new Semaphore(maxAmount);

    private ArrayList<User> users = new ArrayList<>();

    public void library(ArrayList<User> u) {

        Random random = new Random();
        for(User users : u) {
            final User tempUser = users;
            new Thread(() -> {
                try {
                   enter(tempUser);
                    System.out.println(tempUser.getName() + "\t" + tempUser.getIp() + " is reading a book");
                    Thread.sleep(5000);
                    leave(tempUser);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        this.users = u;
        this.peopleCount = users.size();

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