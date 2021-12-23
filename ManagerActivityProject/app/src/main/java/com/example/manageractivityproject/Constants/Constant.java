package com.example.manageractivityproject.Constants;

public class Constant {
    public static final String IPV4 = "192.168.1.9";
    public static final String PORT = ":8080";
    public static final String URL = "http://"+  IPV4 + PORT;
    public static final String HOME = URL+"/AndroidWebServices";
    public static final String LOGIN = HOME+"/login.php";
    public static final String REGISTER = HOME+"/register.php";
    public static final String SAVE_USER_INFO = HOME+"/update_user_info.php";
    public static final String CHANGER_PASSWORD = HOME+"/changer_pass_word.php";
    public static final String CREATE_ACTIVITY = HOME+"/createactivity.php";
    public static final String SELECT_FULL_ACTIVITY = HOME + "/showactivitys.php";
    public static final String DELETE_ACTIVITY = HOME + "/deleteactivity.php";
    public static final String UPDATE_ACTIVITY = HOME + "/updateactivity.php";
    public static final String SHOW_ACTIVITY_REGISTRATIONS = HOME + "/activeregistrations.php";
}
