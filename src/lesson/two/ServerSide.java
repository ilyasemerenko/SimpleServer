package lesson.two;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by SemerenkoIlya on 02.03.2016.
 */

public class ServerSide {
    private static final int PORT = 3333;
    public static void main(String[] args) throws Exception {
        new ServerSide().run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for a client on port: " + PORT);

            while (true) {
                Socket clientSocket = getConnection(serverSocket);

                InputStream sin =clientSocket.getInputStream();
                String request = new DataInputStream(sin).readLine();
                String path = getPath(request).trim();

                byte[] elements = null;

                if (path.contains("calculate")) {
                    String result = String.valueOf(new SimpleCalculator().operate(path));
                    System.out.println("result = " + result);
                    elements = result.getBytes();
                } else {
                    elements = new FileSupport().getFile(path);
                }
                sendResponse(clientSocket,prepareFullResponse(elements));
                clientSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private Socket getConnection(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();

        if (clientSocket != null) {
            System.out.println("Connected client-server");
        } else {
            System.out.println("No connection!");
            System.exit(0);
        }
        return clientSocket;
    }


    private byte[] prepareFullResponse(byte[] response){
        String headerStr = "HTTP/1.0 200 OK\r\n"
                + "Content-Length: " + response.length + "\r\n"
                + "\r\n";
        byte[] header = headerStr.getBytes();
        byte[] fullResponse = new byte[header.length + response.length];
        for(int i = 0; i < fullResponse.length; i++){
            fullResponse[i] = i < header.length ? header[i] : response[i-header.length];
        }
        return  fullResponse;
    }

    private void sendResponse(Socket socket,byte[] fullResponse) throws IOException{
        socket.getOutputStream().write(fullResponse, 0, fullResponse.length);
        socket.getOutputStream().flush();
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