package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginPanel extends JPanel {

    // 登录成功后的回调，参数为 sessionID
    private final OnLoginSuccess callback;

    public interface OnLoginSuccess {
        void onSuccess(String sessionId, String username);
    }

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginBtn;
    private final JButton registerBtn;
    private final JLabel statusLabel;

    public LoginPanel(OnLoginSuccess callback) {
        this.callback = callback;

        setPreferredSize(new Dimension(Main.WINDOW_WIDTH,
                Main.WINDOW_HEIGHT));
        setLayout(null); // 绝对布局，方便精确摆放
        setFocusable(true);

        // ---- 标题 ----
        JLabel titleLabel = new JLabel("Aircraft War",
                SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 120, Main.WINDOW_WIDTH, 50);
        add(titleLabel);

        // ---- 用户名 ----
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userLabel.setBounds(130, 260, 70, 30);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 260, 180, 30);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(usernameField);

        // ---- 密码 ----
        JLabel passLabel = new JLabel("密  码:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passLabel.setBounds(130, 310, 70, 30);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 310, 180, 30);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(passwordField);

        // ---- 按钮 ----
        loginBtn = new JButton("登 录");
        loginBtn.setBounds(150, 380, 90, 35);
        add(loginBtn);

        registerBtn = new JButton("注 册");
        registerBtn.setBounds(270, 380, 90, 35);
        add(registerBtn);

        // ---- 状态提示 ----
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.YELLOW);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setBounds(0, 430, Main.WINDOW_WIDTH, 30);
        add(statusLabel);

        // ---- 事件绑定 ----
        loginBtn.addActionListener(this::onLogin);
        registerBtn.addActionListener(this::onRegister);

        // 回车也能触发登录
        getRootPaneOrDefault().registerKeyboardAction(
                e -> onLogin(null),
                KeyStroke.getKeyStroke("ENTER"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private JRootPane getRootPaneOrDefault() {
        RootPaneContainer rpc = (RootPaneContainer)
                SwingUtilities.getWindowAncestor(this);
        return rpc != null ? rpc.getRootPane() : new JRootPane();
    }

    /** 发 HTTP 请求，登录和注册复用同一逻辑，只是 URL 不同 */
    private void doRequest(String url, String username, String password)
    {
        statusLabel.setText("请求中...");
        loginBtn.setEnabled(false);
        registerBtn.setEnabled(false);

        new Thread(() -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());
                String respBody = response.body();

                SwingUtilities.invokeLater(() -> {
                    loginBtn.setEnabled(true);
                    registerBtn.setEnabled(true);

                    // 简单解析 JSON，不引 Gson
                    boolean success =
                            respBody.contains("\"success\":true") || respBody.contains("\"success\": true");
                    if (success) {
                        String sessionId = extractValue(respBody,
                                "session_id");
                        if (sessionId != null) {
                            callback.onSuccess(sessionId, username);
                        } else {
                            statusLabel.setText("登录失败：未获取到session");
                        }
                    } else {
                        String error = extractValue(respBody, "error");
                        statusLabel.setText(error != null ? error : "操作失败");
                    }
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    loginBtn.setEnabled(true);
                    registerBtn.setEnabled(true);
                    statusLabel.setText("网络错误：" + e.getMessage());
                });
            }
        }).start();
    }

    private void onLogin(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("请输入用户名和密码");
            return;
        }
        doRequest("http://127.0.0.1:8889/api/login", username, password);
    }

    private void onRegister(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("请输入用户名和密码");
            return;
        }
        doRequest("http://127.0.0.1:8889/api/register", username,
                password);
    }

    /** 从 JSON 字符串中简单提取一个字段的值（不用 Gson 的简易方案） */
    private String extractValue(String json, String key) {
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx < 0) return null;
        int colon = json.indexOf(':', idx + pattern.length());
        if (colon < 0) return null;
        int start = json.indexOf('"', colon + 1);
        if (start < 0) return null;
        int end = json.indexOf('"', start + 1);
        if (end < 0) return null;
        return json.substring(start + 1, end);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 画背景图
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, 0,
                Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, null);
    }
}