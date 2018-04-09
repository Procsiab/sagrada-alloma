package ServerP2P.Concurrency;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class SerializedSocket extends GeneralTask {
    private Socket s;

    public SerializedSocket(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            // Initialize input and output streams
            // Revise communication method, more flexibility than an Array can be required
            ObjectOutputStream objectOut = new ObjectOutputStream(this.s.getOutputStream());
            objectOut.flush();
            ObjectInputStream objectIn = new ObjectInputStream(this.s.getInputStream());

            // Receive player data
            String[] answer;
            answer = (String[]) objectIn.readObject();
            System.out.println(Arrays.toString(answer));

            // Send back answer client
            objectOut.writeObject(new String[]{"Il server ha ricevuto i dati!"});

            // Finalize connection
            objectIn.close();
            objectOut.close();
            s.close(); // Should start a session instead of closing...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
