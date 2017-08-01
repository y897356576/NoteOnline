/**
 * Created by 石头 on 2017/7/22.
 */
public class Test_B {

    public static void main(String[] args)  {
        new Circle_B();
    }
}

class Draw_B {

    public Draw_B(String type) {
        System.out.println(type+" draw constructor");
    }
}

class Shape_B {
    private Draw_B draw = new Draw_B("shape");

    public Shape_B(){
        System.out.println("shape constructor");
    }
}

class Circle_B extends Shape_B {
    private Draw_B draw = new Draw_B("circle");
    public Circle_B() {
        System.out.println("circle constructor");
    }
}
