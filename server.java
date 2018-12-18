/** *************************************
 * Class: CIST 2372 Java Programming II
 * Spring: 2018
 * Instructor: Ronald Enz
 * Date: 02/22/2018
 *Chat Mini Project
 *
 * @author Levi Llewellyn Server chat 
 **************************************
 */
package chatpro;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class server extends Application implements EventHandler<ActionEvent> {

    Button btn;
    Button sub;
    TextArea texta = new TextArea();
    TextField textf = new TextField();
    Label inp;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage Stage) throws Exception {

        btn = new Button("Make Connection");
        btn.setOnAction(this);

        sub = new Button("Send");
        sub.setOnAction(this);

        inp = new Label("Input your text");

        VBox tfa = new VBox();
        tfa.getChildren().addAll(texta);
        tfa.setPadding(new Insets(10, 10, 5, 10));

        //send inout box
        HBox send = new HBox(10);
        send.getChildren().addAll(inp, textf, sub);
        send.setPadding(new Insets(5, 10, 10, 20));

        //main full box
        VBox main = new VBox(10);
        main.getChildren().addAll(btn, tfa, send);
        Scene root = new Scene(main, 400, 350);
        Stage.setScene(root);
        Stage.setTitle("Server");
        Stage.show();
    }
    PrintStream ps;

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == btn) {

            class thread1 extends Thread {

                @Override
                public void run() {
                    //This class listen to incoming messages              
                    try {

                        ServerSocket ss = new ServerSocket(8890);
                        System.out.println("testing");
                        Socket s1 = ss.accept();
                        InputStream is = s1.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        ps = new PrintStream(s1.getOutputStream());
                        while (true) {
                            System.out.println("Waiting for input.....");
                            String n = br.readLine();
                            texta.appendText("Him: " + n + "\n");
                        }
                    } catch (Exception a) {
                        System.out.println(a);

                    }
                }
            }
            thread1 listen = new thread1();
            listen.start();
        }
        if (event.getSource() == sub) {
            try {
                ps.println(textf.getText());
                texta.appendText("Me: " + textf.getText() + "\n");
                textf.clear();
            } catch (Exception a) {
                System.out.println();
            }

        }

    }
}
