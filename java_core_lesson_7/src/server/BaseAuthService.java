package server;

import java.util.ArrayList;

public class BaseAuthService implements AuthService{
    private class Entry{
        public String login;
        public String pass;
        public String nick;

        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }

    private ArrayList<Entry> entries;

    public BaseAuthService() {
        entries = new ArrayList<>(){};
        entries.add(new Entry("login1", "pass1", "Rick"));
        entries.add(new Entry("login2", "pass2", "Morty"));
        entries.add(new Entry("login3", "pass3", "Alex"));
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getNickByLoginAndPass(String login, String pass) {
        for (Entry entry : entries){
            if(entry.login.equals(login) && entry.pass.equals(pass))
                return entry.nick;
        }
        return null;
    }
}
