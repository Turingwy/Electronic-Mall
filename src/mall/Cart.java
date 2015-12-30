package mall;

import java.io.*;
import java.util.*;

public class Cart implements Serializable  {
    private class UserGoods  implements Serializable  {
        String name;
        int goodsId;
        int amount;

        public UserGoods(String name, int goodsId, int amount) {
            this.name = name;
            this.goodsId = goodsId;
            this.amount = amount;
        }
    }

    private Set<UserGoods> cartSet;
    private String fileName;
    private String split = "/";

    public Cart(String fileName) throws IOException {
        this.fileName = fileName;
        load();
    }

    private void load() throws IOException {
        try {
            cartSet = (Set<UserGoods>)DataIO.input(fileName);
        } catch (Exception e) {
            cartSet = new HashSet<UserGoods>();
            export();
        }
    }

    public void export() throws IOException {
        DataIO.output(fileName, cartSet);
    }

    public void add(String name, int id) {
        cartSet.add(new UserGoods(name, id, 1));
    }


    public Iterator<Map.Entry<Integer, Integer>> searchUserCart(String name) {
        Iterator<UserGoods> iter = cartSet.iterator();
        Map<Integer, Integer> list = new HashMap<Integer, Integer>();
        while (iter.hasNext()) {
            UserGoods us = iter.next();
            if (us.name.equals(name)) {
                if (list.containsKey(us.goodsId)) {
                    int amount = list.get(us.goodsId);
                    list.put(us.goodsId, amount + us.amount);
                } else {
                    list.put(us.goodsId, 1);
                }
            }
        }
        return list.entrySet().iterator();
    }

    public void removeUserCart(String name) {
        Iterator<UserGoods> iter = cartSet.iterator();
        while (iter.hasNext()) {
            UserGoods us = iter.next();
            if (us.name.equals(name)) {
                iter.remove();
            }
        }
    }

}
