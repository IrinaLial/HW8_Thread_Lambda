package library;

public class User {
   private String name;
   private final String ip;

    public User(String name) {
        this(name, "32.0.2.1");
    }
    public User(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }
    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString ( ) {
        return "User{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
