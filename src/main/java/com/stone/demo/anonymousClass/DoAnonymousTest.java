package com.stone.demo.anonymousClass;

/**
 * Created by 石头 on 2017/7/18.
 */
public class DoAnonymousTest {

    /**
     * 匿名类方法覆盖测试
     * @param args
     */
    public static void main(String[] args) {
        AnonymousModel model1 = new AnonymousModel();
        AnonymousModel model2 = new AnonymousModel(){
            @Override
            public String offerContent() {
                return "this is changed content";
            }
        };
        System.out.println(model1.offerContent());
        System.out.println(model2.offerContent());
    }

}
