module org.manger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.jsoup;
    requires com.google.gson;
    opens org.manger.frontend;
    exports org.manger.frontend;
    exports org.manger.backend.siteExtensions;
    exports org.manger;
}