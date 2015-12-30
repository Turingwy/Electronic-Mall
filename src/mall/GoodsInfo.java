package mall;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class  GoodsInfo {
    private Map<Integer, Goods> goodsMap;   //goods_id, goods
    private String split = "/";
    private String fileName;

    public GoodsInfo(String fileName) throws IOException {
        this.fileName = fileName;
        load();
    }

    private void load() throws IOException {
        try {
            goodsMap = (Map<Integer, Goods>)DataIO.input(fileName);
        } catch (Exception e) {
            goodsMap = new HashMap<Integer, Goods>();
            export();
        }
    }

    public void export() throws IOException {
        DataIO.output(fileName, goodsMap);
    }

    public void addGoods(int id, String name, double price, int size) {
        goodsMap.put(id, new Goods(id, name, price, size));
    }

    public void delGoods(int id) {
        goodsMap.remove(id);
    }

    public boolean isShortage(int id) {
        Goods g = goodsMap.get(id);
        if (g.getStorageSize() <= 0)
            return true;
        else
            return false;
    }

    public boolean verityExist(int id) {
        return goodsMap.containsKey(id);
    }

    public void buyGood(int id) {
        Goods g = goodsMap.get(id);
        g.setStorageSize(g.getStorageSize() - 1);
    }

    public void displayGood(int id) {
        goodsMap.get(id).display();
    }

    public void displayAll() {
        Iterator<Map.Entry<Integer, Goods>> iter = goodsMap.entrySet().iterator();
        while (iter.hasNext()) {
            Goods g = iter.next().getValue();
            g.display();
        }
    }

    public void displayByKeyWord(String key) {
        Iterator<Map.Entry<Integer, Goods>> iter = goodsMap.entrySet().iterator();
        while(iter.hasNext()) {
            Goods g = iter.next().getValue();
            if(g.getName().contains(key)) {
                g.display();
            }
        }
    }
}
