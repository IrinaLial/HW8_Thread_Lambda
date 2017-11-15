package library;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.Random;


public class Library {
    private static final int MAX_AMOUNT = 12;
    private int peopleCount = 0;
    private Semaphore semaphore = new Semaphore(MAX_AMOUNT);
    private Semaphore door = new Semaphore(1);

    private ArrayList<User> users = new ArrayList<>();

    public void library(ArrayList<User> u) {

        Random random = new Random();
        for(User user : u) {
            final User tempUser = user;
            new Thread(() -> {
                try {
                   enter(tempUser);
                    System.out.println(tempUser.getName() + "\t" + tempUser.getIp() + " reading a book");
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
        System.out.println(user.getName() + "\t" + user.getIp() + " trying to enter\t\t\t\t\t Available slots: "
                + semaphore.availablePermits());
        semaphore.acquire();
        pathThroughTheDoor(user);
        users.add(user);
        System.out.println(user.getName() + "\t" + user.getIp() + " has entered\t\t\t\t\t\t Available slots: "
                + semaphore.availablePermits());
    }

    public void leave(User user) throws InterruptedException {
        System.out.println(user.getName() + "\t" + user.getIp() + " try to leave\t\t\t\t\t Available slots: "
                + semaphore.availablePermits()+ "time:" + System.currentTimeMillis());
        users.remove(user);
        pathOutTheDoor(user);
        semaphore.release();
        System.out.println(user.getName() + "\t" + user.getIp() + " has left the library\t\t\t Available slots: "
                + semaphore.availablePermits());
    }
    public void pathThroughTheDoor(User user) throws InterruptedException {
        System.out.println(user.getName() + "\t" + user.getIp () + " near the door(from the street)\t Available slots: "
                + door.availablePermits() + "\ttime: " + System.currentTimeMillis());
        door.acquire();
        System.out.println(user.getName() + "\t" + user.getIp() + " passing to inside\t\t\t\t Available slots: "
                + door.availablePermits() + "\ttime: " + System.currentTimeMillis());
        Thread.sleep(500);
        door.release();
        System.out.println(user.getName() + "\t" + user.getIp() + " came to inside\t\t\t\t\t Available slots: "
                + door.availablePermits() + "\ttime: " + System.currentTimeMillis());
    }

    public void pathOutTheDoor(User user) throws InterruptedException {
        System.out.println(user.getName() + "\t" + user.getIp() + " near the door(inside)\t\t\t Available slots: "
                + door.availablePermits() + "\ttime: " + System.currentTimeMillis());
        door.acquire();
        System.out.println(user.getName() + "\t" + user.getIp() + " passing to outside\t\t\t\t Available slots: "
                + door.availablePermits() + "\ttime: " + System.currentTimeMillis());
        Thread.sleep(500);
        door.release();
        System.out.println(user.getName() + "\t" + user.getIp() + " came outside\t\t\t\t\t Available slots: "
                + door.availablePermits() + "\ttime: " + System.currentTimeMillis());
    }
}