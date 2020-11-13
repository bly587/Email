import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
*  Email Java Program.
*  Waits for a Welcome message from the server
*  Takes in requested input from user
*  Connects to a SMTP Server
*  Outputs to server the required format of the input from the user
*  Records responses from the server
*  Closes the socket and exits
*
*  @author: Titan Mitchell
*     Email: tmitchell@chapman.edu
*     Date: 2/21/2020
*  @version: 3.0
*/

class Email {
  public static void main(String[] argv) throws Exception {
    Socket clientSocket = null;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    //Get email address of sender
    System.out.println("Enter email address of sender: ");
    final String sender = inFromUser.readLine();
    //Get email address of recipient
    System.out.println("Enter email address of recipient: ");
    final String recipient = inFromUser.readLine();
    //Name of recipient
    System.out.println("Enter name of recipient: ");
    final String recName = inFromUser.readLine();
    //Name of Sender
    System.out.println("Enter name of Sender: ");
    final String senderName = inFromUser.readLine();
    //Get Subject of message
    System.out.println("Enter the subject: ");
    final String subject = inFromUser.readLine();
    //message
    System.out.println("Enter your message: ");
    String full = "";
    String message = inFromUser.readLine();
    while (!".".equals(message)) {
      full += "\n";
      full += message;
      message = inFromUser.readLine();
    }

    //Connected to SERVER
    try {
      System.out.println("Attempting to open socket");
      clientSocket = new Socket("smtp.chapman.edu", 25);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }
    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
    BufferedReader inFromServer =
        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    String welcomeMessage = inFromServer.readLine();
    System.out.println("FROM SERVER:" + welcomeMessage);

    //HELO icd.chapman.edu
    System.out.println("HELO icd.chapman.edu");
    outToServer.println("HELO icd.chapman.edu");
    //response
    String response = inFromServer.readLine();
    System.out.println("FROM SERVER: " + response);

    //MAIL FROM:
    System.out.println("MAIL FROM: " + sender);
    outToServer.println("MAIL FROM: " + sender);
    response = inFromServer.readLine();
    System.out.println("FROM SERVER: " + response);

    //RCPT TO:
    System.out.println("RCPT TO: " + recipient);
    outToServer.println("RCPT TO: " + recipient);
    response = inFromServer.readLine();
    System.out.println("FROM SERVER: " + response);

    //DATA
    System.out.println("DATA");
    outToServer.println("DATA");
    response = inFromServer.readLine();
    System.out.println("FROM SERVER: " + response);

    //FROM:
    System.out.println("FROM: " + senderName);
    outToServer.println("FROM: " + senderName);

    //TO:
    System.out.println("TO: " + recName);
    outToServer.println("TO: " + recName);

    //Subject:
    System.out.println("Subject: " + subject);
    outToServer.println("Subject: " + subject);

    //Message:
    outToServer.println(full);
    outToServer.println(".");

    response = inFromServer.readLine();
    System.out.println("FROM SERVER: " + response);
    System.out.println("QUIT");
    outToServer.println("QUIT");
    response = inFromServer.readLine();
    System.out.println("FROM SERVER " + response);

    clientSocket.close();
  }
}
