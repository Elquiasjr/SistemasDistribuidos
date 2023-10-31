package server;

import helper.json.JsonHelper;
import protocol.request.RequisitionOperations;
import protocol.response.LogoutResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.CreateUser;
import server.exceptions.ServerResponseException;
import server.router.Router;
import server.services.ServiceAdminCreateUser;
import server.services.ServiceCreateUser;
import server.services.ServiceLogin;
import server.services.ServiceLogout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final Socket clientSocket;
    private Router routes = null;

    private Server(Socket clientSetSocket){
        this.clientSocket = clientSetSocket;
        if(routes == null){
            routes = Router.builder()
                    .addRoute(RequisitionOperations.LOGIN, new ServiceLogin())
                    .addRoute(RequisitionOperations.LOGOUT, new ServiceLogout())
                    .addRoute(RequisitionOperations.ADMIN_CADASTRAR_USUARIO, new ServiceAdminCreateUser())
                    .addRoute(RequisitionOperations.CADASTRAR_USUARIO, new ServiceCreateUser())
                    .build();
        }
        start();
    }

    public static void main(String[] args) throws ServerResponseException, IOException {

        UserController.getInstance()
                .createUser(new CreateUser("email@email.com", "123456", "jose", true));

        UserController.getInstance()
                .createUser(new CreateUser("fire@outlook.com", "333221", "Elquiasj", false));

        InetAddress ipAddress = InetAddress.getByName("0.0.0.0");

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Port: ");
        final int port = Integer.parseInt(stdIn.readLine());

        try(ServerSocket serverSocket = new ServerSocket(port, 0, ipAddress)) {
            System.out.println("Connection Socket Created");
            while (true) {
                try {
                    System.out.println("Waiting for Connection");
                    new Server(serverSocket.accept());
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.err.printf("Could not listen on port: %d.%n", port);
            System.exit(1);
        }
    }
    public void run()
    {
        System.out.println ("New Communication Thread Started");

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println("IP do cliente: " + clientSocket.getInetAddress().getHostAddress());
                System.out.println ("Recebido: " + inputLine);
                Response<?> response;

                try{
                    response = routes.server(inputLine);
                } catch(ServerResponseException e){
                    response = e.intoResponse();
                }

                String jsonResponse = JsonHelper.toJson(response);
                System.out.println("Enviado: " + jsonResponse);
                out.println(jsonResponse);

                if(response instanceof  LogoutResponse)
                    break;
            }
        }
        catch(IOException e){
            System.err.println("Problem with Communication Server");
        }

        assert(clientSocket.isClosed());
    }
}
