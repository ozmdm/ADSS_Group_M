package test;


import UserPackage.User;
import UserPackage.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
public class UserControllerTest {
    private UserController userController= UserController.UserController();
    private User user1;
    private User user2;


    @BeforeEach
    public void setUp(){
        user1 = new User(1, "123");
        user2 = new User(2, "456");

    }


    @Test
    void testRegister() {
        Exception exception1 = new Exception();
        Exception exception2 = new Exception();

        try {
            userController.register("");
        }
        catch (Exception e){
            exception1 = e;
        }
        assertTrue (exception1.getMessage().equals("Cannot register with empty password"));
        try {
            userController.register("123");
        }
        catch (Exception e){
            exception2 = e;
        }
        assertTrue (exception2.getMessage()==null);
        assertTrue(userController.getIdCounter()==1);
        assertTrue(userController.getUsers().get(1).getPassword().equals("123"));

    }


}