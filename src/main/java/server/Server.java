package server;

import helper.json.JsonHelper;
import protocol.request.RequisitionOperations;
import protocol.response.user.LogoutResponse;
import protocol.response.Response;
import server.commons.EuclidianDistance;
import server.commons.Posicao;
import server.controller.PDIController;
import server.controller.SegmentController;
import server.controller.UserController;
import server.dtobject.pdi.CreatePDI;
import server.dtobject.segment.CreateSegment;
import server.dtobject.user.CreateUser;
import server.exceptions.ServerResponseException;
import server.router.Router;
import server.services.map.*;
import server.services.user.*;

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
                    .addRoute(RequisitionOperations.ADMIN_DELETAR_USUARIO, new ServiceAdminDeleteUser())
                    .addRoute(RequisitionOperations.DELETAR_USUARIO, new ServiceDeleteUser())
                    .addRoute(RequisitionOperations.ADMIN_BUSCAR_USUARIO, new ServiceAdminSearchUser())
                    .addRoute(RequisitionOperations.ADMIN_BUSCAR_USUARIOS, new ServiceAdminSearchUsers())
                    .addRoute(RequisitionOperations.BUSCAR_USUARIO, new ServiceSearchUser())
                    .addRoute(RequisitionOperations.ADMIN_ATUALIZAR_USUARIO, new ServiceAdminUpdateUser())
                    .addRoute(RequisitionOperations.ATUALIZAR_USUARIO, new ServiceUpdateUser())
                    .addRoute(RequisitionOperations.CADASTRAR_PDI, new ServiceAdminCreatePDI())
                    .addRoute(RequisitionOperations.BUSCAR_PDIS, new ServiceAdminSearchPDIs())
                    .addRoute(RequisitionOperations.ATUALIZAR_PDI, new ServiceAdminUpdatePDI())
                    .addRoute(RequisitionOperations.DELETAR_PDI, new ServiceAdminDeletePDI())
                    .addRoute(RequisitionOperations.CADASTRAR_SEGMENTO, new ServiceAdminCreateSegment())
                    .addRoute(RequisitionOperations.BUSCAR_SEGMENTOS, new ServiceAdminSearchSegments())
                    .addRoute(RequisitionOperations.ATUALIZAR_SEGMENTO, new ServiceAdminUpdateSegment())
                    .addRoute(RequisitionOperations.DELETAR_SEGMENTO, new ServiceAdminDeleteSegment())
                    .build();
        }
        start();
    }

    public static void main(String[] args) throws ServerResponseException, IOException {

        UserController.getInstance();
        PDIController.getInstance();
        SegmentController.getInstance();

        PDIController.getInstance().createPDI(new CreatePDI("Portaria", 0.0,
                0.0, "Ponto Inicial", true));
        PDIController.getInstance().createPDI(new CreatePDI("Capela", 0.0,
                10.0, null, true));

        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("1"),Long.parseLong("2"),
                EuclidianDistance.CalculateDistance(new Posicao(0.0, 0.0),
                        new Posicao(0.0, 10.0)), "Escada no caminho", true));

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
