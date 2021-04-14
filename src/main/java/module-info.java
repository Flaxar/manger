module org.manger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.jsoup;
    requires java.desktop;
    opens org.manger.frontend;
    exports org.manger.frontend;
    exports org.manger.backend.siteExtensions;
    exports org.manger;
}