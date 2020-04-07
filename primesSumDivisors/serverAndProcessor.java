import java.net.*;
import java.io.*;

class IOWithTheClient {

  public static void sendDataToClient(DataOutputStream output, String data) throws IOException {

    output.writeUTF(data);
    output.flush();

    System.out.println( "Data send to client" );
  }

  public static String getDataFromClient(DataInputStream input) throws IOException {

      String data = input.readUTF();

      System.out.println( "Get data from client" );

      return data;
  }
}

class connectionsAndIO {

  private static int PORT = 8090;

  private static DataInputStream input = null;
  private static DataOutputStream output = null;
  private static Socket socket = null;
  private static ServerSocket server = null;

  private static BufferedReader keyboard;
  private static String data;
  private static int limitValue = 0;

  public static void stabiliseConnection () throws IOException {

    server = new ServerSocket(PORT);
    socket = server.accept();

    input = new DataInputStream (new BufferedInputStream (socket.getInputStream()));
    output = new DataOutputStream (new BufferedOutputStream (socket.getOutputStream()));
  }

  public static void processData () throws IOException {

    String number;

    number = IOWithTheClient.getDataFromClient (input);

    int limitValue = Integer.valueOf (number);

    IOWithTheClient.sendDataToClient (output, Integer.toString(limitValue));
  }
}

public class serverAndProcessor {

  public static void main (String[] args) throws IOException {

    connectionsAndIO.stabiliseConnection ();
    connectionsAndIO.processData ();
  }
}
