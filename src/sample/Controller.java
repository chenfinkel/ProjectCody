package sample;

public class Controller {

    private Model model;
    private View view;

    public Controller(){
        model=new Model();

    }

    public void setView(View v){
        view=v;
    }

    public void login(String userName, String password){
        model.login(userName,password);
        if(model.isLogin()){
            try {
                view.login();

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else
            view.incorrect();
    }

    public String getDetails(String label){
        return model.getDetails(label,"");
    }

    public void update(String password, String fName, String lName, String bDate, String city){
        model.update(password,fName,lName,bDate,city);
    }

    public void delete(){
        model.delete();
        try {
            view.returnMain();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void search(String user){
        try {
            view.showSearch(user, model.getDetails("firstName", user), model.getDetails("lastName", user),
                    model.getDetails("birthDate", user), model.getDetails("city", user));
        } catch (Exception e){}
    }
}
