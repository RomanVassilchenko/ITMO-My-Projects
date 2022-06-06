package GUI;

import Dependency.Command;
import Dependency.DataCheckerForClient;
import Dependency.Utils;
import NetInteraction.ClientEvents;
import NetInteraction.ClientManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdatePerson extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField passport;
    private JLabel label3;
    private JLabel label2;
    private JLabel label1;
    private JTextField birthday;
    private JTextField height;
    private final ClientEvents clientEvents;
    private final ClientManager clientManager;
    private final JTextArea outPanel;
    private final Command command;
    private final DataCheckerForClient checker = new DataCheckerForClient();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm a", Locale.US);
    private String message7, message8, message9;

    final ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    final ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    final ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    final ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));

    public UpdatePerson(ClientEvents clientEvents, JTextArea outPanel, Command command, String languageIn) {
        this.clientEvents = clientEvents;
        this.outPanel = outPanel;
        this.command = command;
        clientManager = clientEvents.getClientManager();
        $$$setupUI$$$();
        setupSelectedLanguages(languageIn);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        });

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setVisible(true);
    }

    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian" -> {
                buttonOK.setText(bundleRu.getString("execute"));
                buttonCancel.setText(bundleRu.getString("cancel"));
                message7 = bundleRu.getString("insMessage7");
                message8 = bundleRu.getString("insMessage8");
                message9 = bundleRu.getString("insMessage9");
            }
            case "Lithuanian" -> {
                buttonOK.setText(bundleLt.getString("execute"));
                buttonCancel.setText(bundleLt.getString("cancel"));
                message7 = bundleLt.getString("insMessage7");
                message8 = bundleLt.getString("insMessage8");
                message9 = bundleLt.getString("insMessage9");
            }
            case "German" -> {
                buttonOK.setText(bundleDe.getString("execute"));
                buttonCancel.setText(bundleDe.getString("cancel"));
                message7 = bundleDe.getString("insMessage7");
                message8 = bundleDe.getString("insMessage8");
                message9 = bundleDe.getString("insMessage9");
            }
            case "Spanish (Mexico)" -> {
                buttonOK.setText(bundleEs.getString("execute"));
                buttonCancel.setText(bundleEs.getString("cancel"));
                message7 = bundleEs.getString("insMessage7");
                message8 = bundleEs.getString("insMessage8");
                message9 = bundleEs.getString("insMessage9");
            }
        }
    }

    private void onOK() throws IOException, ClassNotFoundException {
        // add your code here
        String heightField = height.getText();
        if (!heightField.equals("")) {
            if (checker.checkHeight(Utils.floatConverter(heightField, label1))) {
                command.setHeight(Float.parseFloat(heightField));
                label1.setText("");
            }
        } else {
            label1.setText(message9);
        }
        String birthdayField = birthday.getText();
        if (!birthdayField.equals("")) {
            if (checker.checkBirthday(birthdayField)) {
                LocalDateTime birthdayParsed = LocalDateTime.parse(birthdayField, formatter);
                command.setBirthday(birthdayParsed);
                label2.setText("");
            } else {
                label2.setText(message8);
            }
        } else {
            label2.setText(message8);
        }
        String passportField = passport.getText();
        if (!passportField.equals("")) {
            if (clientManager.setupPassport(command, passportField)) {
                if (checker.checkHeight(command.getHeight()) && checker.checkBirthday(command.getBirthday())) {
                    clientEvents.commandMode(command, outPanel);
                    dispose();
                }
                label3.setText("");
            } else {
                label3.setText(message7);
            }
        } else {
            label3.setText(message7);
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setPreferredSize(new Dimension(600, 300));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel1, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        buttonOK = new JButton();
        buttonOK.setText("Выполнить");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(buttonOK, gbc);
        buttonCancel = new JButton();
        buttonCancel.setText("Отмена");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(buttonCancel, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setPreferredSize(new Dimension(500, 200));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel3, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel3.add(panel4, BorderLayout.CENTER);
        final JLabel label4 = new JLabel();
        label4.setText("passport:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label4, gbc);
        label3 = new JLabel();
        label3.setBackground(new Color(-48818));
        label3.setForeground(new Color(-48818));
        label3.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label3, gbc);
        birthday = new JTextField();
        birthday.setPreferredSize(new Dimension(200, 30));
        birthday.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(birthday, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("birthday:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label5, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("height:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label6, gbc);
        height = new JTextField();
        height.setPreferredSize(new Dimension(200, 30));
        height.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(height, gbc);
        label2 = new JLabel();
        label2.setBackground(new Color(-48818));
        label2.setForeground(new Color(-48818));
        label2.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label2, gbc);
        label1 = new JLabel();
        label1.setAutoscrolls(false);
        label1.setBackground(new Color(-48818));
        label1.setForeground(new Color(-48818));
        label1.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label1, gbc);
        passport = new JTextField();
        passport.setPreferredSize(new Dimension(200, 30));
        passport.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(passport, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
