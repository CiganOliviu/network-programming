import java.net.*;
import java.io.*;

class serverIO {

  public static void sendDataToServer (DataOutputStream output, String data) throws IOException {

    output.writeUTF (data);
    output.flush ();

    System.out.println("Data send to the server ");
  }

  public static String getDataFromServer (DataInputStream input) throws IOException {

    String data = input.readUTF ();

    System.out.println ("Get data from the server");

    return data;
  }
}

class connectionsAndIO {

  private static String LOCALHOST = "127.0.0.1";
  private static int PORT = 2000;

  private static DataInputStream input = null;
  private static DataOutputStream output = null;
  private static Socket socket = null;

  private static BufferedReader keyboard;
  private static String data;
  private static int limitValue = 0;

  public static void stabiliseConnection () throws IOException {

    socket = new Socket(LOCALHOST, PORT);

    input = new DataInputStream (new BufferedInputStream (socket.getInputStream()));
    output = new DataOutputStream (new BufferedOutputStream (socket.getOutputStream()));
  }

  public static void readDataFromClient () throws IOException {

    keyboard = new BufferedReader (new InputStreamReader (System.in));
    System.out.flush();

    System.out.println("Read limit");
    data = keyboard.readLine();
    Integer temporalData = Integer.valueOf(data);
    limitValue = temporalData;

    keyboard.close();
  }

  public static String implementClientServerCommunication () throws IOException {

    String result = "";

    serverIO.sendDataToServer (output, Integer.toString(limitValue));

    result = serverIO.getDataFromServer(input);

    return result;
  }
}

class fibonacciWorkFlow {

  public static int theFibonacciNumber (int number) {

    if ((number == 1) || (number == 2))
      return 1;
    else
      return theFibonacciNumber (number - 1) + theFibonacciNumber (number - 2);
  }
}

public class clientAndDataSource {

  public static void main (String[] args) throws IOException {

    String DataStream;
    int convertedData;

    connectionsAndIO.stabiliseConnection ();
    connectionsAndIO.readDataFromClient ();
    DataStream = connectionsAndIO.implementClientServerCommunication ();

    convertedData = Integer.valueOf(DataStream);
    System.out.println(fibonacciWorkFlow.theFibonacciNumber (convertedData));
  }
}
