/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 8/28/17
 */
public interface LoginServiceInterface {
    long versionID = 1L;
    String login(String username, String password);
}
