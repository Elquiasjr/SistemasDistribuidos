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
import server.interfaces.PortPage;
import server.router.Router;
import server.services.map.*;
import server.services.user.*;

import javax.sound.sampled.Port;
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
        criarMapa();

        UserController.getInstance()
                .createUser(new CreateUser("email@email.com", "123456", "jose", true));

        UserController.getInstance()
                .createUser(new CreateUser("fire@outlook.com", "333221", "Elquiasj", false));

        InetAddress ipAddress = InetAddress.getByName("0.0.0.0");

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        PortPage portPage = new PortPage(null);
        final int port = portPage.getPort();

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

    public static void criarMapa() throws ServerResponseException {
        PDIController.getInstance().createPDI(new CreatePDI("Portaria", 0.0,
                0.0, "Ponto Inicial", true)); //1
        PDIController.getInstance().createPDI(new CreatePDI("Escada Portaria-Capela", 0.0,
                100.0, "Escala", true)); //2
        PDIController.getInstance().createPDI(new CreatePDI("Capela", 0.0,
                105.0, "", true)); //3
        PDIController.getInstance().createPDI(new CreatePDI("Auditorio", -20.0,
                105.0, "", true)); //4
        PDIController.getInstance().createPDI(new CreatePDI("Escada Inicio 1.o andar", 20.0,
                105.0, "", true)); //5
        PDIController.getInstance().createPDI(new CreatePDI("Escada Baixo-Meio 1.o andar", 20.0,
                115.0, "", true)); //6
        PDIController.getInstance().createPDI(new CreatePDI("Lab. 6", 22.0,
                105.0, "", true)); //7
        PDIController.getInstance().createPDI(new CreatePDI("Lab. 7", 22.0,
                125.0, "", true)); //8
        PDIController.getInstance().createPDI(new CreatePDI("Lab. 8", 22.0,
                135.0, "", true)); //9
        PDIController.getInstance().createPDI(new CreatePDI("Escada Bebedouro", 22.0,
                140.0, "", true)); //10
        PDIController.getInstance().createPDI(new CreatePDI("LaCa", 20.0,
                140.0, "", true)); //11
        PDIController.getInstance().createPDI(new CreatePDI("Rampa", -20.0,
                140.0, "", true)); //12
        PDIController.getInstance().createPDI(new CreatePDI("Escada Inicio Audiotorio 1.o andar",
                -20.0, 103.0, "", true)); //13
        PDIController.getInstance().createPDI(new CreatePDI("Escada Meio Audiotorio 1.o andar",
                -20.0, 93.0, "", true)); //14
        PDIController.getInstance().createPDI(new CreatePDI("Escada Cima Audiotorio 1.o andar",
                -20.0, 105.0, "", true)); //15
        PDIController.getInstance().createPDI(new CreatePDI("Lab. 4", 10.0,
                105.0, "", true)); //16
        PDIController.getInstance().createPDI(new CreatePDI("Lab. 2", 15.0,
                105.0, "", true)); //17
        PDIController.getInstance().createPDI(new CreatePDI("Escada Cima 1.o andar", 18.0,
                105.0, "", true)); //18
        PDIController.getInstance().createPDI(new CreatePDI("Escada Cima-Meio 1.o andar", 18.0,
                115.0, "", true)); //19
        PDIController.getInstance().createPDI(new CreatePDI("Lab. 1", 20.0,
                105.0, "", true)); //20
        PDIController.getInstance().createPDI(new CreatePDI("Lab. 3", 22.0,
                105.0, "", true)); //21
        PDIController.getInstance().createPDI(new CreatePDI("Lab. 5", 22.0,
                115.0, "", true)); //22
        PDIController.getInstance().createPDI(new CreatePDI("DAINF", 22.0,
                130.0, "", true)); //23

        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("1"),Long.parseLong("2"),
                EuclidianDistance.CalculateDistance(new Posicao(0.0, 0.0),
                        new Posicao(0.0, 100.0)), "", true)); //1
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("2"),Long.parseLong("3"),
                EuclidianDistance.CalculateDistance(new Posicao(0.0, 100.0),
                        new Posicao(0.0, 105.0)), "", true));//2
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("3"),Long.parseLong("4"),
                EuclidianDistance.CalculateDistance(new Posicao(0.0, 105.0),
                        new Posicao(-20.0, 105.0)), "", true));//3
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("3"),Long.parseLong("5"),
                EuclidianDistance.CalculateDistance(new Posicao(0.0, 105.0),
                        new Posicao(20.0, 105.0)), "", true));//4
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("5"),Long.parseLong("6"),
                EuclidianDistance.CalculateDistance(new Posicao(20.0, 105.0),
                        new Posicao(20.0, 115.0)), "", true));//5
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("5"),Long.parseLong("7"),
                EuclidianDistance.CalculateDistance(new Posicao(20.0, 105.0),
                        new Posicao(22.0, 105.0)), "", true));//6
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("7"),Long.parseLong("8"),
                EuclidianDistance.CalculateDistance(new Posicao(22.0, 105.0),
                        new Posicao(22.0, 125.0)), "", true));//7
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("8"),Long.parseLong("9"),
                EuclidianDistance.CalculateDistance(new Posicao(22.0, 125.0),
                        new Posicao(22.0, 135.0)), "", true));//8
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("9"),Long.parseLong("10"),
                EuclidianDistance.CalculateDistance(new Posicao(22.0, 135.0),
                        new Posicao(22.0, 140.0)), "", true));//9
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("10"),Long.parseLong("11"),
                EuclidianDistance.CalculateDistance(new Posicao(22.0, 140.0),
                        new Posicao(20.0, 140.0)), "", true));//10
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("11"),Long.parseLong("12"),
                EuclidianDistance.CalculateDistance(new Posicao(20.0, 140.0),
                        new Posicao(-20.0, 140.0)), "", true));//11
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("12"),Long.parseLong("4"),
                EuclidianDistance.CalculateDistance(new Posicao(-20.0, 140.0),
                        new Posicao(-20.0, 105.0)), "", true));//12
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("4"),Long.parseLong("13"),
                EuclidianDistance.CalculateDistance(new Posicao(-20.0, 105.0),
                        new Posicao(-20.0, 103.0)), "", true));//13
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("13"),Long.parseLong("14"),
                EuclidianDistance.CalculateDistance(new Posicao(-20.0, 103.0),
                        new Posicao(-20.0, 93.0)), "", true));//14
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("14"),Long.parseLong("15"),
                EuclidianDistance.CalculateDistance(new Posicao(-20.0, 93.0),
                        new Posicao(-20.0, 105.0)), "", true));//15
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("15"),Long.parseLong("16"),
                EuclidianDistance.CalculateDistance(new Posicao(-20.0, 105.0),
                        new Posicao(10.0, 105.0)), "", true));//16
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("16"),Long.parseLong("17"),
                EuclidianDistance.CalculateDistance(new Posicao(10.0, 105.0),
                        new Posicao(15.0, 105.0)), "", true));//17
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("17"),Long.parseLong("18"),
                EuclidianDistance.CalculateDistance(new Posicao(15.0, 105.0),
                        new Posicao(18.0, 105.0)), "", true));//18
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("18"),Long.parseLong("19"),
                EuclidianDistance.CalculateDistance(new Posicao(18.0, 105.0),
                        new Posicao(18.0, 95.0)), "", true));//19
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("19"),Long.parseLong("6"),
                EuclidianDistance.CalculateDistance(new Posicao(18.0, 115.0),
                        new Posicao(20.0, 115.0)), "", true));//20
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("18"),Long.parseLong("20"),
                EuclidianDistance.CalculateDistance(new Posicao(18.0, 105.0),
                        new Posicao(20.0, 105.0)), "", true));//21
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("20"),Long.parseLong("21"),
                EuclidianDistance.CalculateDistance(new Posicao(20.0, 105.0),
                        new Posicao(22.0, 105.0)), "", true));//22
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("21"),Long.parseLong("22"),
                EuclidianDistance.CalculateDistance(new Posicao(22.0, 105.0),
                        new Posicao(22.0, 115.0)), "", true));//23
        SegmentController.getInstance().createSegment(new CreateSegment(Long.parseLong("22"),Long.parseLong("23"),
                EuclidianDistance.CalculateDistance(new Posicao(22.0, 115.0),
                        new Posicao(22.0, 130.0)), "", true));//24
    }
}
