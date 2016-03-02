package cclu.example.jersey.data;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {
    public static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, ArrayList<Message>> messages = new ConcurrentHashMap<>();

    static {
        User root = new User();
        root.setEmail("root");
        root.setPassword("root123");
        root.setRole(Role.Admin);
        users.put("root", root);
    }
}
