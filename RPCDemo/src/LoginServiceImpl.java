/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 8/28/17
 */
public class LoginServiceImpl implements LoginServiceInterface {
    @Override
    public String login(String username, String password) {
        return username + " logged in successfully!";
    }
}
