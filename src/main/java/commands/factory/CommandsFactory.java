package commands.factory;

import commands.Command;
import commands.implementations.DefaultCommand;
import commands.implementations.admin.*;
import commands.implementations.client.*;
import commands.implementations.user.*;
import constants.Parameters;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandsFactory {
    private static final Logger logger = Logger.getLogger(CommandsFactory.class);
    private volatile static CommandsFactory instance;

    public CommandsFactory() {
    }

    public static CommandsFactory getInstance() {
        if (instance == null) {
            synchronized (CommandsFactory.class) {
                if (instance == null) {
                    instance = new CommandsFactory();
                }
            }
        }
        return instance;
    }

    public Command defineCommandFrom(HttpServletRequest request) {
        CommandType commandType = CommandType.valueOf(request.getParameter(Parameters.COMMAND).toUpperCase());
        return createCommandFromCommandType(commandType);
    }

    public Command createCommandFromCommandType(CommandType commandType) {
        switch (commandType) {
            case LOGIN:
                return new LoginCommand();
            case SETLANGUAGE:
                return new SetLanguageCommand();
            case LOGOUT:
                return new LogoutCommand();
            case REGISTRATION:
                return new RegistrationCommand();
            case GOTOREGISTRATION:
                return new GotoRegistrationCommand();
            case BACK:
                return new BackCommand();
            case CREATEACTIVITY:
                return new CreateActivityCommand();
            case OVERVIEWCLIENT:
                return new OverviewClientCommand();
            case BACKADMIN:
                return new BackAdminCommand();
            case REMOVEADMIN:
                return new RemoveAdminCommand();
            case ADDACTIVITY:
                return new AddActivityToUserCommand();
            case STARTTIME:
                return new StartTimeCommand();
            case STOPTIME:
                return new StopTimeCommand();
            case FINISH:
                return new FinishCommand();
            case CHOSEPAGE:
                return new PaginationCommand();
            case REMOVE:
                return new RemoveCommand();
            case ADD:
                return new AddCommand();
            default:
                return new DefaultCommand();
        }
    }
}