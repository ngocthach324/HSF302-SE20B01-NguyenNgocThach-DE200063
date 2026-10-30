module edu.fpt.swt301.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens edu.fpt.swt301.demo to javafx.fxml;
    exports edu.fpt.swt301.demo;

    exports com.fu.ch08.common;
    exports com.fu.ch08.b1;
    exports com.fu.ch08.b2;
    exports com.fu.ch08.b3;
}
