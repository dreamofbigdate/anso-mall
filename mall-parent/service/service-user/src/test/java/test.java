import org.springframework.util.DigestUtils;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-10 9:39
 */
public class test {
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex("123456789".getBytes()));
    }
}
