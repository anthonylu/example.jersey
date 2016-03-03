package cclu.example.jersey.data;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStore {
    public static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, User> idToUser = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, ArrayList<Message>> messages = new ConcurrentHashMap<>();
    public static final AtomicInteger userSeq = new AtomicInteger();
    public static final AtomicInteger msgSeq = new AtomicInteger();

    static {
        User root = new User();
        root.setId(userSeq.getAndIncrement());
        root.setEmail("root");
        root.setPassword("root123");
        root.setRole(Role.Admin);
        users.put("root", root);
        idToUser.put(root.getId(), root);
    }
}
