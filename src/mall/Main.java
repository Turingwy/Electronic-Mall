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
        System.out.println("    电子商城-游客");
        System.out.println("*********************");
        while (true) {
            System.out.println("\n1.注册\n2.顾客登陆\n3.查看所有商品\n4.管理员登陆\n5.退出程序");
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
            System.out.println("无法读取磁盘中的信息，请检查磁盘");
            quit();
        }
        in = new Scanner(System.in);
    }

    public void userModel() {
        System.out.println("\n\n*********************");
        System.out.println("    电子商城-顾客");
        System.out.println("*********************");
        while (hasLogin) {
            System.out.println("\n1.查看所有商品\n2.按关键字搜索\n3.购买商品\n4.查看购物信息\n5.退出登陆");
            int opt = in.nextInt();
            switch (opt) {
                case 1:
                    lookupAllGoods();
                    break;
                case 2:
                    System.out.print("关键字为：");
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
                    System.out.println("已退出!");
                    break;
            }
        }
    }


    public void adminModel() {
        System.out.println("\n\n*********************");
        System.out.println("    电子商城-管理员");
        System.out.println("*********************");
        while (hasLogin) {
            System.out.println("\n1.查看所有商品\n2.增加商品\n3.更改商品信息\n4.查看商品\n5.删除用户\n6.退出登陆");
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
        System.out.println("用户列表：");
        uinfo.listUser();
        System.out.println("输入用户名");
        String name = in.next();
        if (uinfo.delUser(name)) {
            System.out.println("删除成功");
            cart.removeUserCart(name);
            System.out.println("已删除该用户所有购物信息");
        } else {
            System.out.println("用户不存在");
        }
    }

    public void register(UserInfo info) {
        System.out.println("输入用户名");
        String name = in.next();
        System.out.println("输入密码");
        String password = in.next();
        while (info.verifyExist(name)) {
            System.out.println("用户已存在，请重新输入用户名");
            name = in.next();
            System.out.println("输入密码");
            password = in.next();
        }
        info.addUser(name, password);
        System.out.println("注册成功");
    }

    public void login(boolean user_or_admin) {
        Console cs = System.console();
        System.out.println("输入用户名");
        String name = in.next();
        System.out.println("输入密码");
        String password;
        if(cs == null)
            password = in.next();
        else {
            char[] passwordChar = cs.readPassword();
            password = new String(passwordChar);
        }
        if (user_or_admin) {
            if (uinfo.login(name, password)) {
                System.out.println("登陆成功");
                hasLogin = true;
                this.currentName = name;
            } else {
                System.out.println("用户名或密码不正确");
            }
        } else {
            if (ainfo.login(name, password)) {
                System.out.println("登陆成功");
                hasLogin = true;
                this.currentName = name;
            } else {
                System.out.println("用户名或密码不正确");
            }
        }
    }


    public void lookupAllGoods() {
        ginfo.displayAll();
    }

    public void searchOneGoods() {
        System.out.println("输入要查询的商品id:");
        int id = in.nextInt();
        if (ginfo.verityExist(id)) {
            ginfo.displayGood(id);
        } else {
            System.out.println("没有该商品.");
        }
    }

    public void addGoodsInfo(boolean add) {
        System.out.print("输入商品id:");
        int id = in.nextInt();
        System.out.print("输入商品名称:");
        String name = in.next();
        System.out.print("输入商品价格:");
        double price = in.nextDouble();
        System.out.print("输入商品库存:");
        int storageSize = in.nextInt();
        if (add && ginfo.verityExist(id)) {
            System.out.println("id已经存在！无法添加");
        } else {
            ginfo.addGoods(id, name, price, storageSize);
        }
    }


    void quit() {
        System.out.println("正在保存信息......");
        try {
            uinfo.export();
            ginfo.export();
            ainfo.export();
            cart.export();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保存信息失败，请检查磁盘!");
            System.exit(0);
        }

        System.out.println("保存信息成功.");
        System.out.println("程序关闭.");
        System.exit(0);
    }

    public void buy() {
        System.out.print("输入要购买的物品id:");
        int id = in.nextInt();
        if (!ginfo.verityExist(id)) {
            System.out.println("商品不存在!");
            return;
        }

        if (ginfo.isShortage(id)) {
            System.out.println("商品缺货!暂无法购买!");
            return;
        }
        System.out.println("查找到该商品信息：");
        ginfo.displayGood(id);
        System.out.println("\n是否购买?(y/n)");
        String answer = in.next();
        if (answer.charAt(0) == 'y' || answer.charAt(0) == 'Y') {
            ginfo.buyGood(id);
            cart.add(currentName, id);
            System.out.println("交易成功.");
        } else {
            System.out.println("已取消交易.");
        }
    }

    public void showCart() {
        System.out.println("您当前购物信息：");
        Iterator<Map.Entry<Integer, Integer>> iter = cart.searchUserCart(currentName);
        if (!iter.hasNext()) {
            System.out.println("抱歉没有物品.");
            return;
        }
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> e = iter.next();
            int id = e.getKey();
            int amount = e.getValue();
            ginfo.displayGood(id);
            System.out.println("购买件数：" + amount);
        }
    }
}
