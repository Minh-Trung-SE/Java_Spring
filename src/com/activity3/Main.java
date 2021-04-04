package com.activity3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Main {
    public static HashMap<String, String> map = new HashMap<String, String>();
    public static String key;
    public static boolean flag = false;

    public static void main(String[] args) {
        Main.map.put("Monday", "Thứ 2");
        Main.map.put("Tuesday", "Thứ 3");
        Main.map.put("Wednesday", "Thứ 4");
        Main.map.put("Thursday", "Thứ 5");
        Main.map.put("Friday", "Thứ 6");
        Main.map.put("Saturday", "Hôm nay là thứ 7");
        Main.map.put("Sunday", "Chủ nhật");
        genDays genDays = new genDays();
        getDays getDays = new getDays();
        genDays.start();
        getDays.start();
    }

}

class genDays extends Thread{
    @Override
    public void run() {
        Set<String> keySet = Main.map.keySet();
        ArrayList<String> key = new ArrayList<String>(keySet);
        while (true){
            if(!Main.flag){
                Random r = new Random();
                Main.key = key.get(r.nextInt(6));
                System.out.print(Main.key);
                Main.flag = true;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

class getDays extends Thread{
    @Override
    public void run() {
        while (true){
            if(Main.flag){
                System.out.println(": " + Main.map.get(Main.key));
                Main.flag = false;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
