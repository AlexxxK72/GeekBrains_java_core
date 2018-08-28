import java.io.Serializable;

public class CommandMessage implements Serializable {
    private Commands cmd;
    private Object[] args;

    public Commands getCmd() {
        return cmd;
    }
    public Object[] getArgs() {
        return args;
    }


    public CommandMessage(Commands cmd, Object[] args) {
        this.cmd = cmd;
        this.args = args;
    }

    enum Commands{
        Auth,
        LogOut,
        Auth_Result,
        List,
        IsDirectory,
        Upload,
        Download,
        Rename,
        Delete
    }
}


