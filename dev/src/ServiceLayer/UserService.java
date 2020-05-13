package ServiceLayer;

import MessageTypes.Response;
import UserPackage.UserController;

public class UserService {

    private UserController userController;

    public UserService() {
        this.userController = UserController.UserController();
    }

    public Response<String> register(String pass) {
        int userId;
        try {
            userId=this.userController.register(pass);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Registered successfully. User id: "+userId);
    }

    public Response<String> login(int id, String pass){
        try {
            this.userController.login(id, pass);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Logged in successfully");
    }

}
