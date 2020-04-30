import com.sbt.school.terminalserver.TerminalServerImpl;

public class Main {

    public static void main(String[] args) {
        TerminalServerImpl server = new TerminalServerImpl();
        TerminalApp app = new TerminalApp(server);

        new Thread(app).start();
    }

}
