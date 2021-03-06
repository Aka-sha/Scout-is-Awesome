import Models.City;
import db.UserDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * The class is the container for the Action methods that modify data and the View.
 * Last Updated 04/29/2021
 * @author Andy Cruse, modified by Aka'sh Carver
 */
public class controller {
    @FXML
    private ImageView t1Anime, t2Anime, t3Anime, t4Anime, t5Anime;

    @FXML
    private Label T1text, T2text, T3text, T4text, T5text;

    @FXML
    private Label T1name, T2name, T3name, T4name, T5name;

    @FXML
    private TextField reUserid;

    @FXML
    private PasswordField rePass1;

    @FXML
    private PasswordField rePass2;

    @FXML
    private TextField reEmail;

    @FXML
    private TextField reAge;

    @FXML
    private TextField reIP;

    @FXML
    private TextField userid;

    @FXML
    private PasswordField passWord;

    @FXML
    private TextField CityTitle;

    @FXML
    private TextField reCityTitle;

    @FXML
    private ListView<String> animeRec;

    @FXML
    private TextArea animeSub;

    List<String> addStuff = new ArrayList<String>();

    @FXML
    private ImageView recImg;

    @FXML
    private Button close;


    @FXML
    public void homePage(ActionEvent event) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
            Scene rooter = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(rooter);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void topPage(ActionEvent event) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/topAnime.fxml"));
            Scene rooter = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(rooter);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void signPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/signup.fxml"));
        Scene rooter = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rooter);
        window.show();
    }

    @FXML
    public void loginPage(ActionEvent event) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
            Scene rooter = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(rooter);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void alert(ActionEvent event, String info) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("Close", ButtonBar.ButtonData.YES));
        alert.setHeaderText(null);
        alert.setTitle("Alert");
        alert.show();
    }

    @FXML
    public void welcome(ActionEvent event, String info) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("Close", ButtonBar.ButtonData.YES));
        alert.setHeaderText(null);
        alert.setTitle("Welcome");
        alert.show();
    }

    @FXML
    public void loginB(ActionEvent event) throws Exception {
        userid.setUserData("admin");
        passWord.setUserData("admin123");
        String adminIDs = "admin";
        String adminPWs = "admin";
        String userids = userid.getText();
        String passWords = passWord.getText();
        if (userids.equals(userid.getUserData()) && passWords.equals(passWord.getUserData())) {
            System.out.println(userids + " login");
            Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
            Scene rooter = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(rooter);
            window.show();
        } else if (userids.equals(adminIDs) && passWords.equals(adminPWs)) {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/userlist.fxml"));
            Scene rooter = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(rooter);
            window.show();
        } else {
            String info = "Wrong username or password";
            alert(event, info);
        }
    }


    @FXML
    void signup(ActionEvent event) throws IOException {
        String userids = reUserid.getText();
        String passWords = rePass1.getText();
        String Confirm = rePass2.getText();
        String email = reEmail.getText();
        String IP = reIP.getText();
        String age = reAge.getText();
        String cityTitle = reCityTitle.getText();
        int min = age.compareTo("18");
        String empty = "";
        int intIP = Integer.parseInt(IP);


        if (!passWords.equals(Confirm)) {
            String info = "Passwords are not matched";
            alert(event, info);
        } else if (userids.length() < 4) {
            String info = "Username must be at least 4 characters in length.";
            alert(event, info);
        } else if (passWords.length() < 8) {
            String info = "Password must be at least 8 characters in length.";
            alert(event, info);
        } else if (userids.equals(empty) || passWords.equals(empty) || Confirm.equals(empty) || email.equals(empty) || IP.equals(empty) || age.equals(empty)) {
            String info = "Please fill all the form";
            alert(event, info);
        } else if (min < 0) {
            System.out.println(min);
            String info = "You need larger then 18 years old";
            alert(event, info);
        } else {
            String info = userids + " Welcome To Anime Scout";
            int ageInt = Integer.parseInt(age);

            UserDatabase userDB = new UserDatabase();
            userDB.loadUserDatabaseDefault();
            userDB.addNewApplicationUser(userids, email, passWords, ageInt, cityTitle, intIP);
            userDB.saveUserDatabaseDefault();

            welcome(event, info);
            Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
            Scene rooter = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(rooter);
            window.show();
            System.out.println(userids + " from " + cityTitle + " joined us");
        }
    }

    @FXML
    public void subAnimeRec(ActionEvent event) {
        String sub = animeSub.getText();
        addStuff.add(sub);
        ObservableList<String> add = FXCollections.observableList(addStuff);
        animeRec.setItems(add);
        animeSub.clear();
    }

    @FXML
    public void animeRec(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/recommendation_anime.fxml"));
        Scene rooter = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rooter);
        window.show();
    }

    @FXML
    public void clear(ActionEvent event) {
        reEmail.clear();
        rePass1.clear();
        reUserid.clear();
        rePass2.clear();
        reAge.clear();
        reIP.clear();
    }

    int click = 0;

    @FXML
    public void nextList(ActionEvent event) {
        click++;
        loadImage(click);
    }

    public void loadImage(int n) {
        t1Anime.setImage(new Image("https://cdn.myanimelist.net/images/anime/1436/106694.jpg?s=d395802efdb5b4a093f094f0090b7a07"));
        T1name.setText("Itai no wa Iya nano de Bougyoryoku ni Kyokufuri Shitai to Omoimasu. II");
        T1text.setText("Second season of Itai no wa Iya nano de Bougyoryoku ni Kyokufuri Shitai to Omoimasu.");
        t2Anime.setImage(new Image("https://cdn.myanimelist.net/images/anime/1407/111469.jpg?s=57f3024519667ef0d0fd15beeea628c1"));
        T2name.setText("Dungeon ni Deai wo Motomeru no wa Machigatteiru Darou ka IV");
        T2text.setText("Fourth season of Dungeon ni Deai wo Motomeru no wa Machigatteiru Darou ka.");
        t3Anime.setImage(new Image("https://cdn.myanimelist.net/images/anime/1978/111973.jpg?s=611c7f900ef12f49707e02b5d7435bb4"));
        T3name.setText("Toku: Touken Ranbu - Hanamaru - Setsugetsuka");
        T3text.setText("Touken Ranbu: Hanamaru anime movie trilogy.");
        t4Anime.setImage(new Image("https://cdn.myanimelist.net/images/anime/1340/110088.jpg?s=ee67671d1f7730716efef5ba82681170"));
        T4name.setText("Ousama Ranking");
        T4text.setText("The web manga centers around Bojji, a deaf, powerless prince who cannot even wield a children's sword. As the firstborn son, he strives hard and dreams of becoming the world's greatest king. However,...");
        t5Anime.setImage(new Image("https://cdn.myanimelist.net/images/anime/1236/113727.jpg?s=83919aa2db778f788cb7b7664b802a59"));
        T5name.setText("Tensei shitara Slime Datta Ken 2nd Season Part 2");
        T5text.setText("Second half of Tensei shitara Slime Datta Ken 2nd Season.");
    }

    @FXML
    void animeDetail(ActionEvent event) throws IOException {
        Parent settings = FXMLLoader.load(getClass().getResource("fxml/animeDetail.fxml"));
        Stage stage = new Stage();
        stage.setTitle("AnimeDetail");
        stage.setScene(new Scene(settings));
        stage.show();
    }

    @FXML
    void Close(ActionEvent event) throws IOException {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private ListView<String> userList;

    @FXML
    void manage(MouseEvent event) {
            String selected = userList.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    void loadUser(ActionEvent event) {
        UserDatabase userDB = new UserDatabase();
        userDB.loadUserDatabaseDefault();
        ArrayList<String> userListA = new ArrayList();
        for (int i = 0; i < userDB.getSize(); i++) {
            userListA.add(userDB.getUsernameByIndex(i));
        }
        System.out.println(userListA);
        for (String userListString : userListA) {
            userList.getItems().add(userListString);
        }

    }
}