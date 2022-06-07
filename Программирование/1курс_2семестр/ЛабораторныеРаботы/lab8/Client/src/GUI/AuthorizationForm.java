package GUI;

import Dependency.Command;
import NetInteraction.ClientEvents;
import NetInteraction.ClientManager;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthorizationForm {
    private final JFrame auth;
    private JPanel authPanel;
    private JTextField loginTextField;
    private JPasswordField passwordPasswordField;
    private JButton authorizeButton;
    private JButton regButton;
    private JLabel conditionOfAuth;
    private JComboBox languages;
    private JPanel loginPanel;
    private JLabel passwordLable;
    private JLabel loginLabel;
    private final ClientEvents clientEvents;
    private MainForm mainForm;
    private final ClientManager clientManager;
    private String message1 = "Вы не авторизованы!";
    private String message2 = "Пользаваетль с таким логином уже создан!";
    private String message3 = "Ошибка в логине или пароле!";
    private String language;
    final ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    final ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    final ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    final ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));

    public AuthorizationForm(ClientEvents clientEvents) {
        this.clientEvents = clientEvents;
        clientManager = clientEvents.getClientManager();
        auth = new JFrame();
        auth.setSize(600, 400);
        FlatLightLaf.setup();
        $$$setupUI$$$();
        auth.add(authPanel);
        auth.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        auth.setLocationRelativeTo(null);
        auth.setVisible(true);
        authorizeButton.addActionListener(new AuthorizeListener());
        regButton.addActionListener(new RegListener());
        languages.addActionListener(e -> {
            language = languages.getSelectedItem().toString();
            setupSelectedLanguages(language);
        });
    }
    public AuthorizationForm(ClientEvents clientEvents, Object languageIn) {
        this.clientEvents = clientEvents;
        clientManager = clientEvents.getClientManager();
        auth = new JFrame();
        auth.setSize(600, 400);
        FlatLightLaf.setup();
        $$$setupUI$$$();
        auth.add(authPanel);
        auth.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        auth.setLocationRelativeTo(null);
        auth.setVisible(true);
        authorizeButton.addActionListener(new AuthorizeListener());
        regButton.addActionListener(new RegListener());
        languages.addActionListener(e -> {
            language = languages.getSelectedItem().toString();
            setupSelectedLanguages(language);
        });
        languages.setSelectedItem(languageIn);
    }

    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian" -> {
                loginLabel.setText(bundleRu.getString("login"));
                passwordLable.setText(bundleRu.getString("password"));
                regButton.setText(bundleRu.getString("reg"));
                authorizeButton.setText(bundleRu.getString("auth"));
                message1 = bundleRu.getString("message1");
                message2 = bundleRu.getString("message2");
                message3 = bundleRu.getString("message3");
            }
            case "Lithuanian" -> {
                loginLabel.setText(bundleLt.getString("login"));
                passwordLable.setText(bundleLt.getString("password"));
                regButton.setText(bundleLt.getString("reg"));
                authorizeButton.setText(bundleLt.getString("auth"));
                message1 = bundleLt.getString("message1");
                message2 = bundleLt.getString("message2");
                message3 = bundleLt.getString("message3");
            }
            case "German" -> {
                loginLabel.setText(bundleDe.getString("login"));
                passwordLable.setText(bundleDe.getString("password"));
                regButton.setText(bundleDe.getString("reg"));
                authorizeButton.setText(bundleDe.getString("auth"));
                message1 = bundleDe.getString("message1");
                message2 = bundleDe.getString("message2");
                message3 = bundleDe.getString("message3");
            }
            case "Spanish (Mexico)" -> {
                loginLabel.setText(bundleEs.getString("login"));
                passwordLable.setText(bundleEs.getString("password"));
                regButton.setText(bundleEs.getString("reg"));
                authorizeButton.setText(bundleEs.getString("auth"));
                message1 = bundleEs.getString("message1");
                message2 = bundleEs.getString("message2");
                message3 = bundleEs.getString("message3");
            }
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        authPanel = new JPanel();
        authPanel.setLayout(new BorderLayout(0, 0));
        authPanel.setBackground(new Color(-1));
        Font authPanelFont = UIManager.getFont("Button.font");
        if (authPanelFont != null) authPanel.setFont(authPanelFont);
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        authPanel.add(loginPanel, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.setBackground(new Color(-855310));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(panel1, gbc);
        passwordPasswordField = new JPasswordField();
        passwordPasswordField.setPreferredSize(new Dimension(90, 30));
        passwordPasswordField.setText("");
        passwordPasswordField.setToolTipText("password");
        passwordPasswordField.setVerifyInputWhenFocusTarget(true);
        passwordPasswordField.setVisible(true);
        panel1.add(passwordPasswordField);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel2.setBackground(new Color(-855310));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        loginPanel.add(panel2, gbc);
        conditionOfAuth = new JLabel();
        conditionOfAuth.setForeground(new Color(-48818));
        conditionOfAuth.setText("");
        panel2.add(conditionOfAuth);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.setBackground(new Color(-855310));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        loginPanel.add(panel3, gbc);
        authorizeButton = new JButton();
        authorizeButton.setBackground(new Color(-855310));
        authorizeButton.setText("Авторизоваться");
        panel3.add(authorizeButton);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel4.setBackground(new Color(-855310));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        loginPanel.add(panel4, gbc);
        regButton = new JButton();
        regButton.setBackground(new Color(-855310));
        regButton.setEnabled(true);
        regButton.setFocusCycleRoot(false);
        regButton.setFocusPainted(true);
        regButton.setText("Регистрация");
        panel4.add(regButton);
        loginLabel = new JLabel();
        loginLabel.setBackground(new Color(-855310));
        loginLabel.setOpaque(true);
        loginLabel.setText("Логин:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(loginLabel, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel5.setBackground(new Color(-855310));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        loginPanel.add(panel5, gbc);
        passwordLable = new JLabel();
        passwordLable.setText("Пароль:");
        panel5.add(passwordLable);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel6.setBackground(new Color(-855310));
        panel6.setPreferredSize(new Dimension(100, 40));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        loginPanel.add(panel6, gbc);
        loginTextField = new JTextField();
        loginTextField.setPreferredSize(new Dimension(90, 30));
        loginTextField.setText("");
        loginTextField.setToolTipText("login");
        panel6.add(loginTextField);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        authPanel.add(panel7, BorderLayout.NORTH);
        languages = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Russian");
        defaultComboBoxModel1.addElement("Lithuanian");
        defaultComboBoxModel1.addElement("German");
        defaultComboBoxModel1.addElement("Spanish (Mexico)");
        languages.setModel(defaultComboBoxModel1);
        panel7.add(languages, BorderLayout.WEST);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return authPanel;
    }


    class AuthorizeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Command command = new Command("auth", loginTextField.getText()
                        , new String(passwordPasswordField.getPassword()));
                clientEvents.commandMode(command);
                if (clientManager.getConditionOfAuthorization().equals("not auth")) {
                    conditionOfAuth.setText(message1);
                } else {
                    auth.dispose();
                    auth.setVisible(false);
                    mainForm = new MainForm(auth, clientEvents, languages.getSelectedItem());
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    class RegListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Command command = new Command("reg", loginTextField.getText()
                        , new String(passwordPasswordField.getPassword()));
                clientEvents.commandMode(command);
                if (clientManager.getConditionOfAuthorization().equals("not reg")) {
                    conditionOfAuth.setText(message2);
                } else if(loginTextField.getText().trim().equals("") || Arrays.toString(passwordPasswordField.getPassword()).trim().equals("")){
                    conditionOfAuth.setText(message3);

                }else {
                    clientEvents.commandMode(new Command("getCollectionForTable"));
                    auth.dispose();
                    auth.setVisible(false);
                    mainForm = new MainForm(auth, clientEvents, language);
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
}
