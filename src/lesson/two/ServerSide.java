package lesson.two;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by SemerenkoIlya on 02.03.2016.
 *//*

public class ServerSide {
    public static void main(String[] ar)    {
        int port = 54321; // случайный порт (может быть любое число от 1025 до 65535)
        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");
            while(true) {
            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
            System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            System.out.println();

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line = null;

                //line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                String messageBrowse = "Hello";
                int length = messageBrowse.getBytes().length;
                String lineOut = "HTTP/1.1 200 OK\r\nContent-Length: " + length +"\r\nContent-Type: text/html\r\n" + messageBrowse;

                System.out.println("The dumb client just sent me this line : " + line);
                System.out.println("I'm sending it back...");
                out.writeUTF(lineOut); // отсылаем клиенту обратно ту самую строку текста.
                out.flush(); // заставляем поток закончить передачу данных.
                System.out.println("Waiting for the next line...");
                System.out.println();
          */
public class ServerSide {
    public static void main(String[] args) throws IOException {
        ServerSide server = new ServerSide();
        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws Exception{

        ServerSocket serverSocket = null;
        serverSocket = startAndWait(serverSocket,55555);
        FileSupport fileSupport = null;
        Socket clientSocket = null;
        while (true) {
            clientSocket = serverSocket.accept();

            if (clientSocket != null)
                System.out.println("Connected client-server");

            InputStream sin = null;
            if (clientSocket != null) {
                sin = clientSocket.getInputStream();
            }
            OutputStream sout = clientSocket.getOutputStream();
            DataInputStream dataIn = new DataInputStream(sin);

            String request = dataIn.readLine();
            String path = getPath(request).trim();
            System.out.println(request);
            System.out.println("Path is " + path);
            System.out.println();

            fileSupport = new FileSupport();
            byte[] elements = fileSupport.getFile(path);

            /*String messageToSend = new String(elements);
            int length = messageToSend.getBytes().length;
            String response = "HTTP/1.1 200 OK\r\nContent-Length: " + length + "\r\nContent-Type: text/html\r\n\r\n" + messageToSend;*/

            sout.write(elements,0,elements.length);
        }
    }

    private ServerSocket startAndWait(ServerSocket serverSocket, int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for a client...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 55555.");
            System.exit(1);
        }
        return serverSocket;
    }

    private String getPath(String request) {
        String path = null;
        int pathStart = request.indexOf(" ");
        int pathEnd = request.lastIndexOf(" ");
        path = request.substring(pathStart,pathEnd);
        return path;
    }
}