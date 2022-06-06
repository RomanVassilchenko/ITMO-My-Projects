package GUI;

import Dependency.*;
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

public class Update extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel label1;
    private JLabel text1Label;
    private ClientEvents clientEvents;
    private ClientManager clientManager;
    private JTextArea outPanel;
    private Command command;
    private DataCheckerForClient checker = new DataCheckerForClient();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm a", Locale.US);
    private String language;
    private String message1, message2, message9, message10;

    ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));

    public Update(ClientEvents clientEvents, JTextArea outPanel, Command command, String languageIn) {
        this.clientEvents = clientEvents;
        this.outPanel = outPanel;
        this.command = command;
        language = languageIn;
        clientManager = clientEvents.getClientManager();
        $$$setupUI$$$();
        setupSelectedLanguages(language);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setVisible(true);
    }

    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian":
                buttonOK.setText(bundleRu.getString("execute"));
                buttonCancel.setText(bundleRu.getString("cancel"));
                text1Label.setText(bundleRu.getString("newValue"));
                message1 = bundleRu.getString("insMessage1");
                message2 = bundleRu.getString("insMessage2");
                message9 = bundleRu.getString("insMessage9");
                message10 = bundleRu.getString("numbMessage1");
                break;
            case "Lithuanian":
                buttonOK.setText(bundleLt.getString("execute"));
                buttonCancel.setText(bundleLt.getString("cancel"));
                text1Label.setText(bundleLt.getString("newValue"));
                message1 = bundleLt.getString("insMessage1");
                message2 = bundleLt.getString("insMessage2");
                message9 = bundleLt.getString("insMessage9");
                message10 = bundleLt.getString("numbMessage1");
                break;
            case "German":
                buttonOK.setText(bundleDe.getString("execute"));
                buttonCancel.setText(bundleDe.getString("cancel"));
                text1Label.setText(bundleDe.getString("newValue"));
                message1 = bundleDe.getString("insMessage1");
                message2 = bundleDe.getString("insMessage2");
                message9 = bundleDe.getString("insMessage9");
                message10 = bundleDe.getString("numbMessage1");
                break;
            case "Spanish (Mexico)":
                buttonOK.setText(bundleEs.getString("execute"));
                buttonCancel.setText(bundleEs.getString("cancel"));
                text1Label.setText(bundleEs.getString("newValue"));
                message1 = bundleEs.getString("insMessage1");
                message2 = bundleEs.getString("insMessage2");
                message9 = bundleEs.getString("insMessage9");
                message10 = bundleEs.getString("numbMessage1");
                break;
        }
    }

    private void onOK() throws IOException, ClassNotFoundException {
        // add your code here
        String[] finalUserCommand = command.getUserCommand().trim().split(" +", 3);
        switch (finalUserCommand[2]) {
            case "name":
                String nameField = textField1.getText();
                if (!nameField.equals("")) {
                    if (checker.checkName(nameField)) {
                        command.setName(nameField);
                        clientEvents.commandMode(command, outPanel);
                        dispose();
                    }
                } else {
                    label1.setText(message1);
                }
                break;
            case "salary":
                String salaryField = textField1.getText();
                if (!salaryField.equals("")) {
                    Integer number = Utils.integerConverter(salaryField);
                    if (number != null) {
                        if (checker.checkSalary(Utils.integerConverter(salaryField))) {
                            command.setSalary(Utils.integerConverter(salaryField));
                            label1.setText("");
                        } else {
                            label1.setText(message2);
                        }
                    } else {
                        label1.setText(message10);
                    }
                } else {
                    label1.setText(message2);
                }
                break;
            case "position":
                String positionField = textField1.getText();
                if (!positionField.equals("")) {
                    if (checker.checkPosition(Position.valueOf(positionField))) {
                        command.setPosition(Position.valueOf(positionField));
                        clientEvents.commandMode(command, outPanel);
                        dispose();
                    }
                } else {
                    label1.setText("DIRECTOR,\n" +
                            "LABORER,\n" +
                            "HUMAN_RESOURCES,\n" +
                            "HEAD_OF_DEPARTMENT,\n" +
                            "MANAGER_OF_CLEANING");
                }
                break;
            case "height":
                String heightField = textField1.getText();
                if (!heightField.equals("")) {
                    if (checker.checkHeight(Utils.floatConverter(heightField, label1))) {
                        command.setHeight(Utils.floatConverter(heightField));
                        clientEvents.commandMode(command, outPanel);
                        dispose();
                    }
                } else {
                    label1.setText(message9);
                }
                break;
            case "status":
                String statusField = textField1.getText();
                if (!statusField.equals("")) {
                    if (checker.checkStatus(Status.valueOf(statusField))) {
                        command.setStatus(Status.valueOf(statusField));
                        clientEvents.commandMode(command, outPanel);
                        dispose();
                    }
                } else {
                    label1.setText("HIRED,\n" +
                            "RECOMMENDED_FOR_PROMOTION,\n" +
                            "REGULAR,\n" +
                            "PROBATION");
                }
                break;
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
        contentPane.setPreferredSize(new Dimension(800, 150));
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
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel3, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel4.setPreferredSize(new Dimension(800, 45));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel4, gbc);
        text1Label = new JLabel();
        text1Label.setText("Новое значение:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(text1Label, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(panel5, gbc);
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(200, 30));
        textField1.setText("");
        panel5.add(textField1, BorderLayout.CENTER);
        label1 = new JLabel();
        label1.setBackground(new Color(-48818));
        label1.setFocusCycleRoot(false);
        label1.setForeground(new Color(-48818));
        label1.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label1, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
