package com.stone.demo.enumCase;

import java.util.Random;

/**
 * Created by 石头 on 2017/7/11.
 */
public class enumCase {

    public static void main(String[] args) {
//        enumPropertyInfo();
//        for (int i=0; i < 100; i++){
//            doSwitchCase();
//        }
//        getEnumToString();
//        getEnumDescription();
//        enumMenthodCase();
        System.out.println(Shrubbery.getShrubberyByCode(2).name());
    }

    private static void enumPropertyInfo(){
        for (Shrubbery shrubbery : Shrubbery.values()) {
            System.out.println(shrubbery + " ordinal : " + shrubbery.ordinal());  // 此处shrubbery调用的是toString()而非name();
            System.out.print(shrubbery.compareTo(Shrubbery.CRAWLING) + " ");
            System.out.print(shrubbery.equals(Shrubbery.CRAWLING) + " ");
            System.out.println(shrubbery == Shrubbery.CRAWLING);
            System.out.println(shrubbery.getDeclaringClass());
            System.out.println(shrubbery.name());
            System.out.println("------------------");
        }
        for (String str : "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrubbery = Enum.valueOf(Shrubbery.class, str);
            System.out.println(shrubbery);
        }
    }


    private static void doSwitchCase() {
        Shrubbery shrubbery = randomShrubberyAssist();
        switch (shrubbery) {
            case GROUND:
                System.out.println("Current Shrubbery is : " + shrubbery);
                shrubbery = randomShrubberyAssist();
                break;
            case CRAWLING:
                System.out.println("Current Shrubbery is : " + shrubbery);
                shrubbery = randomShrubberyAssist();
                break;
            case HANGING:
                System.out.println("Current Shrubbery is : " + shrubbery);
                shrubbery = randomShrubberyAssist();
                break;
        }
    }
    private static Shrubbery randomShrubberyAssist() {
        Random r = new Random();
        int i = r.nextInt(3);
        switch (i) {
            case 0 : return Shrubbery.GROUND;
            case 1 : return Shrubbery.CRAWLING;
            case 2 : return Shrubbery.HANGING;
            default: return Shrubbery.GROUND;
        }
    }


    /**
     * 重写toString()
     */
    private static void getEnumToString() {
        for (SpaceShip spaceShip : SpaceShip.values()) {
            System.out.println(spaceShip.name() + "'s alias : " +
                    spaceShip + " and toString : " + spaceShip.toString());  // spaceShip输出调用的是toString()
        }
    }


    /**
     * 添加默认描述
     */
    private static void getEnumDescription() {
        for (OzWitch witch : OzWitch.values()) {
            System.out.println(witch + "'s description : " + witch.getDescription());
        }
    }


    /**
     * 添加方法
     */
    private static void enumMenthodCase() {
        for(EnumMethod eMethod : EnumMethod.values()) {
            System.out.println(eMethod + "'s getInfo : " + eMethod.getInfo());
        }
    }

}
