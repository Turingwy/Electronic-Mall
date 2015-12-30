package mall;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserInfo {
    private Map<String, User> userMap;
    private String split = "/";
    private String fileName;
    public UserInfo(String fileName) throws IOException {
        this.fileName = fileName;
        load();
    }

    private void load() throws IOException {
        try {
            userMap = (Map<String, User>)DataIO.input(fileName);
        } catch (Exception e) {
            userMap = new HashMap<String, User>();
            export();
        }
    }

    public void export() throws IOException {
        DataIO.output(fileName, userMap);
    }

    public boolean login(String name, String password) {
        User user = userMap.get(name);
        if (user == null)
            return false;

        if (user.getPassword().equals(password)) {
            user.setLogin(true);
            return true;
        } else
            return false;

    }

    public void logout(String name) {
        User user = userMap.get(name);
        user.setLogin(false);
    }

    public boolean addUser(String name, String password) {
        if (!verifyExist(name)) {
            userMap.put(name, new User(name, password));
            return true;
        } else
            return false;
    }

    public boolean delUser(String name) {
        if (verifyExist(name)) {
            userMap.remove(name);
            return true;
        } else
            return false;
    }

    public void listUser() {
        int i = 1;
        for(String str :userMap.keySet()) {
            System.out.println(i + ". " + str);
            i++;
        }
    }

    public boolean verifyExist(String name) {
        return userMap.containsKey(name);
    }




}