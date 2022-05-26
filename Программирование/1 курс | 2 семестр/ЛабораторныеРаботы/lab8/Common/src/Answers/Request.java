package Answers;

import Commands.Command;
import Data.User;

import java.io.Serializable;

public class Request implements Serializable {
    private Command command;
    private Object[] args;
    private User user;

    public Request(User user,Command command, Object[] args) {
        this.command = command;
        this.args = args;
        this.user=user;
    }

    public Command getCommand() {
        return command;
    }

    public Object[] getArgs() {
        return args;
    }

    public User getUser() {
        return user;
    }
}