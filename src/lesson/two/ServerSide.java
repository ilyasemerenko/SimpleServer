package lesson.two;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by SemerenkoIlya on 02.03.2016.
 */

public class ServerSide {
    private static final int PORT = 12345;
    public static void main(String[] args) throws Exception {
        new ServerSide().run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for a client on port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                if (clientSocket != null) {
                    System.out.println("Connected client-server");
                } else {
                    System.out.println("No connection!");
                    System.exit(0);
                }

                InputStream sin =clientSocket.getInputStream();
                String request = new DataInputStream(sin).readLine();
                String path = getPath(request).trim();

                if (path.contains("calculate")) {
                    String result = String.valueOf(new SimpleCalculator().operate(path));
                    System.out.println("result = " + result);
                    byte[] elements = result.getBytes();
                    clientSocket.getOutputStream().write(elements, 0, elements.length);
                } else {
                    byte[] elements = new FileSupport().getFile(path);
                    clientSocket.getOutputStream().write(elements, 0, elements.length);
                }
                clientSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String getPath(String request) {
        int pathStart = request.indexOf(" ");
        int pathEnd = request.lastIndexOf(" ");
        String path = request.substring(pathStart,pathEnd);
        System.out.println("Request is: " + request);
        System.out.println("Path is: " + path);
        return path;
    }
}