package UI;

import Models.AnimeManga;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class animeListCon {




    @FXML
    private ImageView t1Anime, t2Anime, t3Anime, t4Anime, t5Anime;

    @FXML
    private Label T1text, T2text, T3text, T4text, T5text;

    @FXML
    private Label T1name, T2name, T3name, T4name, T5name;

    int click = 1;

    @FXML
    public void homePage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        Scene rooter = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rooter);
        window.show();
    }

    @FXML
    public void topPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/topAnime.fxml"));
        Parent Parent = loader.load();

        Scene Scene = new Scene(Parent);

        animeListCon controller = loader.getController();
        controller.loadImage(1);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(Scene);
        window.show();

    }


    @FXML
    void signPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/signup.fxml"));
        Parent Parent = loader.load();

        Scene Scene = new Scene(Parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(Scene);
        window.show();
    }

    @FXML
    public void loginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene rooter = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rooter);
        window.show();
    }

    @FXML
    public void nextList(ActionEvent event) {
        click++;
        loadImage(click);
    }
    public AnimeManga preSet() {
        String type = "anime";
        String genre1 = "1";
        String genre2 = "10";
        String genre3 = "4";
        String orderBy = "start_date";
        String sort = "desc";
        List<String> searchQuery = new ArrayList<>();
        searchQuery.add(type);
        searchQuery.add(genre1);
        searchQuery.add(genre2);
        searchQuery.add(genre3);
        searchQuery.add(orderBy);
        searchQuery.add(sort);
        AnimeManga anime = AnimeManga.loadAnimeMangaDataBySearch(searchQuery);
        return anime;
    }
    public void loadImage(int n) {
        AnimeManga anime = preSet();
        t1Anime.setImage(new Image(anime.getImageUrlList().get(n*5-4)));
        T1name.setText(anime.getTitleList().get(n*5-4));
        T1text.setText(anime.getSynopsisList().get(n*5-4));
        t2Anime.setImage(new Image(anime.getImageUrlList().get(n*5-3)));
        T2name.setText(anime.getTitleList().get(n*5-3));
        T2text.setText(anime.getSynopsisList().get(n*5-3));
        t3Anime.setImage(new Image(anime.getImageUrlList().get(n*5-2)));
        T3name.setText(anime.getTitleList().get(n*5-2));
        T3text.setText(anime.getSynopsisList().get(n*5-2));
        t4Anime.setImage(new Image(anime.getImageUrlList().get(n*5-1)));
        T4name.setText(anime.getTitleList().get(n*5-1));
        T4text.setText(anime.getSynopsisList().get(n*5-1));
        t5Anime.setImage(new Image(anime.getImageUrlList().get(n*5-0)));
        T5name.setText(anime.getTitleList().get(n*5-0));
        T5text.setText(anime.getSynopsisList().get(n*5-0));


    }

    @FXML
    public void animeRec(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/recommendation_anime.fxml"));
        Scene rooter = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rooter);
        window.show();
    }

}
