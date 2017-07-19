package junit4.rule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 石头 on 2017/7/19.
 */
public class TemporaryFolderTest {

    //TemporaryFolder可以创建一些临时目录或者文件，在一个测试方法结束之后，系统会自动清空他们。
    @ClassRule
    public static TemporaryFolder folderCreater = new TemporaryFolder();

    @Before
    public void before() throws IOException {
        folderCreater.create();
    }

    @Test
    public void createFolder() throws IOException {
        //在系统的临时目录下创建目录或者文件，当测试方法执行完毕自动删除
        File folder = folderCreater.newFolder();
        File file = folderCreater.newFile("fileName.txt");
        System.out.println(folder.getAbsolutePath());
        System.out.println(file.getAbsolutePath());
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //do something...
    }

}
