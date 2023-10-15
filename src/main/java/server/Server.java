package server;

import helper.json.JsonHelper;
import protocol.request.RequisitionOperations;
import protocol.response.LogoutResponse;
import protocol.response.Response;
import server.exception.ServerResponseException;
import server.layer.inicialLayer.*;
import server.router.Router;

import java.net.*;
import java.io.*;

public class Server extends Thread {
    private final Socket clientSocket;

    private Router routes = null;


    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10008);
            System.out.println ("Connection Socket Created");
            try {
                while (true)
                {
                    System.out.println ("Waiting for Connection");
                    new Server (serverSocket.accept());
                }
            }
            catch (IOException e)
            {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        }
        finally
        {
            try {
                serverSocket.close();
            }
            catch (IOException e)
            {
                System.err.println("Could not close port: 10008.");
                System.exit(1);
            }
        }
    }

    private Server (Socket clientSoc)
    {
        clientSocket = clientSoc;
        start();
    }

    public void run()
    {
        System.out.println ("New Communication Thread Started");

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println ("Server: " + inputLine);
                out.println(inputLine);

                if (inputLine.equals("Bye."))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }
}
