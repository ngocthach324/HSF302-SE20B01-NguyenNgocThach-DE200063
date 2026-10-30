package edu.fpt.swt301.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckBox;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Label messageLabel;

    @FXML
    private Button loginButton;

    @FXML
    protected void handleLogin(ActionEvent event) {
        String username = usernameField.getText() != null ? usernameField.getText().trim() : "";
        String password = passwordField.getText() != null ? passwordField.getText().trim() : "";

        // Kiểm tra tài khoản: chấp nhận 'admin' và mật khẩu '123456' hoặc '1234567'
        boolean isUserValid = "admin".equalsIgnoreCase(username);
        boolean isPassValid = "1234567".equals(password) || "123456".equals(password);

        if (isUserValid && isPassValid) {
            if (messageLabel != null) {
                messageLabel.setStyle("-fx-text-fill: #2ECC71;");
                messageLabel.setText("Đăng nhập thành công!");
            }
            boolean isRemember = rememberCheckBox != null && rememberCheckBox.isSelected();
            showSuccessModal(username, isRemember);
        } else {
            if (messageLabel != null) {
                messageLabel.setStyle("-fx-text-fill: #E91E63;");
                messageLabel.setText("Đăng nhập không hợp lệ.");
            }
            showErrorAlert();
        }
    }

    // Hiển thị Modal khi đăng nhập thành công
    private void showSuccessModal(String username, boolean remember) {
        Alert infoModal = new Alert(Alert.AlertType.INFORMATION);
        infoModal.setTitle("Thông báo");
        infoModal.setHeaderText("Đăng nhập thành công!");
        infoModal.setContentText("Tài khoản: " + username + "\n" +
                "Trạng thái ghi nhớ: " + (remember ? "Có" : "Không"));
        infoModal.showAndWait();
    }

    // Hiển thị Alert khi đăng nhập thất bại
    private void showErrorAlert() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Lỗi");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Đăng nhập không hợp lệ. Vui lòng kiểm tra lại tài khoản hoặc mật khẩu.");
        errorAlert.showAndWait();
    }

    @FXML
    protected void handleLoginAction(ActionEvent event) {
        handleLogin(event);
    }

    @FXML
    protected void handleForgotPassword(ActionEvent event) {
        if (messageLabel != null) {
            messageLabel.setStyle("-fx-text-fill: #9C27B0;");
            messageLabel.setText("Chức năng 'Quên mật khẩu' đang được phát triển.");
        }
    }

    @FXML
    protected void handleRegister(ActionEvent event) {
        if (messageLabel != null) {
            messageLabel.setStyle("-fx-text-fill: #9C27B0;");
            messageLabel.setText("Chức năng 'Đăng ký tài khoản' đang được phát triển.");
        }
    }
}
