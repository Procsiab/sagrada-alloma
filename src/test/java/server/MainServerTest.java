package server;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainServerTest {

    @Test
    void main() {
        String[] test = new String[3];
        try {
            MainServer.main(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}