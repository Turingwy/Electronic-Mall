package mall;

import java.io.Console;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private UserInfo uinfo;
    private UserInfo ainfo;
    private GoodsInfo ginfo;
    private String currentName;
    private Cart cart;
    private Scanner in;
    private boolean hasLogin;

    public void visitModel() {
        System.out.println("*********************");
        System.out.println("    �����̳�-�ο�");
        System.out.println("*********************");
        while (true) {
            System.out.println("\n1.ע��\n2.�˿͵�½\n3.�鿴������Ʒ\n4.����Ա��½\n5.�˳�����");
            int opt = in.nextInt();
            switch (opt) {
                case 1:
                    register(uinfo);
                    break;
                case 2:
                    login(true);
                    if (hasLogin)
                        userModel();
                    break;
                case 3:
                    lookupAllGoods();
                    break;
                case 4:
                    login(false);
                    if (hasLogin) {
                        adminModel();
                    }
                    break;
                case 5:
                    quit();
                    break;
                case 100:
                    register(ainfo);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.visitModel();
    }

    public Main() {
        try {
            uinfo = new UserInfo("userinfo.dat");
            ainfo = new UserInfo("admininfo.dat");
            ginfo = new GoodsInfo("goodsinfo.dat");
            cart = new Cart("cartinfo.dat");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("�޷���ȡ�����е���Ϣ���������");
            quit();
        }
        in = new Scanner(System.in);
    }

    public void userModel() {
        System.out.println("\n\n*********************");
        System.out.println("    �����̳�-�˿�");
        System.out.println("*********************");
        while (hasLogin) {
            System.out.println("\n1.�鿴������Ʒ\n2.���ؼ�������\n3.������Ʒ\n4.�鿴������Ϣ\n5.�˳���½");
            int opt = in.nextInt();
            switch (opt) {
                case 1:
                    lookupAllGoods();
                    break;
                case 2:
                    System.out.print("�ؼ���Ϊ��");
                    String key = in.next();
                    ginfo.displayByKeyWord(key);
                    break;
                case 3:
                    buy();
                    break;
                case 4:
                    showCart();
                    break;
                case 5:
                    uinfo.logout(currentName);
                    hasLogin = false;
                    currentName = null;
                    System.out.println("���˳�!");
                    break;
            }
        }
    }


    public void adminModel() {
        System.out.println("\n\n*********************");
        System.out.println("    �����̳�-����Ա");
        System.out.println("*********************");
        while (hasLogin) {
            System.out.println("\n1.�鿴������Ʒ\n2.������Ʒ\n3.������Ʒ��Ϣ\n4.�鿴��Ʒ\n5.ɾ���û�\n6.�˳���½");
            int opt = in.nextInt();
            switch (opt) {
                case 1:
                    lookupAllGoods();
                    break;
                case 2:
                    addGoodsInfo(true);
                    break;
                case 3:
                    addGoodsInfo(false);
                    break;
                case 4:
                    searchOneGoods();
                    break;
                case 5:
                    delUser();
                    break;
                case 6:
                    ainfo.logout(currentName);
                    hasLogin = false;
                    currentName = null;
                    break;
            }
        }
    }


    public void delUser() {
        System.out.println("�û��б�");
        uinfo.listUser();
        System.out.println("�����û���");
        String name = in.next();
        if (uinfo.delUser(name)) {
            System.out.println("ɾ���ɹ�");
            cart.removeUserCart(name);
            System.out.println("��ɾ�����û����й�����Ϣ");
        } else {
            System.out.println("�û�������");
        }
    }

    public void register(UserInfo info) {
        System.out.println("�����û���");
        String name = in.next();
        System.out.println("��������");
        String password = in.next();
        while (info.verifyExist(name)) {
            System.out.println("�û��Ѵ��ڣ������������û���");
            name = in.next();
            System.out.println("��������");
            password = in.next();
        }
        info.addUser(name, password);
        System.out.println("ע��ɹ�");
    }

    public void login(boolean user_or_admin) {
        Console cs = System.console();
        System.out.println("�����û���");
        String name = in.next();
        System.out.println("��������");
        String password;
        if(cs == null)
            password = in.next();
        else {
            char[] passwordChar = cs.readPassword();
            password = new String(passwordChar);
        }
        if (user_or_admin) {
            if (uinfo.login(name, password)) {
                System.out.println("��½�ɹ�");
                hasLogin = true;
                this.currentName = name;
            } else {
                System.out.println("�û��������벻��ȷ");
            }
        } else {
            if (ainfo.login(name, password)) {
                System.out.println("��½�ɹ�");
                hasLogin = true;
                this.currentName = name;
            } else {
                System.out.println("�û��������벻��ȷ");
            }
        }
    }


    public void lookupAllGoods() {
        ginfo.displayAll();
    }

    public void searchOneGoods() {
        System.out.println("����Ҫ��ѯ����Ʒid:");
        int id = in.nextInt();
        if (ginfo.verityExist(id)) {
            ginfo.displayGood(id);
        } else {
            System.out.println("û�и���Ʒ.");
        }
    }

    public void addGoodsInfo(boolean add) {
        System.out.print("������Ʒid:");
        int id = in.nextInt();
        System.out.print("������Ʒ����:");
        String name = in.next();
        System.out.print("������Ʒ�۸�:");
        double price = in.nextDouble();
        System.out.print("������Ʒ���:");
        int storageSize = in.nextInt();
        if (add && ginfo.verityExist(id)) {
            System.out.println("id�Ѿ����ڣ��޷����");
        } else {
            ginfo.addGoods(id, name, price, storageSize);
        }
    }


    void quit() {
        System.out.println("���ڱ�����Ϣ......");
        try {
            uinfo.export();
            ginfo.export();
            ainfo.export();
            cart.export();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("������Ϣʧ�ܣ��������!");
            System.exit(0);
        }

        System.out.println("������Ϣ�ɹ�.");
        System.out.println("����ر�.");
        System.exit(0);
    }

    public void buy() {
        System.out.print("����Ҫ�������Ʒid:");
        int id = in.nextInt();
        if (!ginfo.verityExist(id)) {
            System.out.println("��Ʒ������!");
            return;
        }

        if (ginfo.isShortage(id)) {
            System.out.println("��Ʒȱ��!���޷�����!");
            return;
        }
        System.out.println("���ҵ�����Ʒ��Ϣ��");
        ginfo.displayGood(id);
        System.out.println("\n�Ƿ���?(y/n)");
        String answer = in.next();
        if (answer.charAt(0) == 'y' || answer.charAt(0) == 'Y') {
            ginfo.buyGood(id);
            cart.add(currentName, id);
            System.out.println("���׳ɹ�.");
        } else {
            System.out.println("��ȡ������.");
        }
    }

    public void showCart() {
        System.out.println("����ǰ������Ϣ��");
        Iterator<Map.Entry<Integer, Integer>> iter = cart.searchUserCart(currentName);
        if (!iter.hasNext()) {
            System.out.println("��Ǹû����Ʒ.");
            return;
        }
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> e = iter.next();
            int id = e.getKey();
            int amount = e.getValue();
            ginfo.displayGood(id);
            System.out.println("���������" + amount);
        }
    }
}
