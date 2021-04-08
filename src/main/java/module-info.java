module org.manger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.jsoup;
    requires selenium.api;
    requires selenium.chrome.driver;
    requires selenium.support;
    exports org.manger.frontend;
}