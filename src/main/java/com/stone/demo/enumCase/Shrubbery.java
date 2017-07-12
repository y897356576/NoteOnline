package com.stone.demo.enumCase;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by 石头 on 2017/7/11.
 */
enum Shrubbery {
    GROUND, CRAWLING, HANGING
}


enum SpaceShip {
    SCOUT, CARGO, TRANSPORT, CRUISER, BATTLESHIP, MOTHERSHIP;

    public String toString() {
        String id = this.name();
        String lower = id.substring(1).toLowerCase();
        return id.charAt(0) + lower;
    }
}


enum OzWitch {
    EAST("Wicked Witch of the east, wearer of the ruby Slippers, crushed by Dorothy's house"),
    SOUTH("Good by inference, but missing"),
    WEST("Miss Gulch, aka the Wicked Witch of the West"),
    NORTH("Glinda, the Good Witch of the North");

    private String description;

    private OzWitch(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


/**
 * 类似于超类与子类
 */
enum EnumMethod {
    DATE_TIME {
        String getInfo() {
            return DateFormat.getDateInstance().format(new Date());
        }
    },
    CLASSPATH {
        String getInfo() {
            return System.getenv("PATH");
        }
    },
    VERSION {
        String getInfo() {
            return System.getProperty("java.version");
        }
    };

    abstract String getInfo();
}
