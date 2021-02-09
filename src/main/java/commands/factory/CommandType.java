package commands.factory;

public enum CommandType {

    /*user commands*/
    LOGIN, LOGOUT, REGISTRATION, GOTOREGISTRATION, BACK, DEFAULT, SETLANGUAGE,

    /*admin commands*/
    CREATEACTIVITY, OVERVIEWCLIENT, BACKADMIN, ADDACTIVITY, REMOVEADMIN, CHOSEPAGE,

    /*client commands*/
    STARTTIME, STOPTIME, FINISH, REMOVE, ADD;


}