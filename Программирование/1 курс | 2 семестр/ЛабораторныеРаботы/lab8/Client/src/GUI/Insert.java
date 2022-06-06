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

public class Insert extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField salary;
    private JTextField passport;
    private JTextField birthday;
    private JTextField name;
    private JTextField cordx;
    private JTextField cordy;
    private JTextField height;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JComboBox status;
    private JComboBox position;
    private JLabel аа;
    private DataCheckerForClient checker = new DataCheckerForClient();
    private ClientEvents clientEvents;
    private ClientManager clientManager;
    private JTextArea outPanel;
    private Command worker;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm a", Locale.US);
    private String language;
    private String message1, message2, message3, message4, message7, message8, message9, message10, message11;
    ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));

    public Insert(ClientEvents clientEvents, JTextArea outPanel, Command command, String languageIn) {
        this.clientEvents = clientEvents;
        this.outPanel = outPanel;
        language = languageIn;
        worker = command;
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
                buttonCancel.setText(bundleRu.getString("cancel"));
                buttonOK.setText(bundleRu.getString("confirm"));
                message1 = bundleRu.getString("insMessage1");
                message2 = bundleRu.getString("insMessage2");
                message3 = bundleRu.getString("insMessage3");
                message4 = bundleRu.getString("insMessage4");
                message7 = bundleRu.getString("insMessage7");
                message8 = bundleRu.getString("insMessage8");
                message9 = bundleRu.getString("insMessage9");
                message10 = bundleRu.getString("numbMessage1");
                message11 = bundleRu.getString("numbMessage2");
                break;
            case "Lithuanian":
                buttonCancel.setText(bundleLt.getString("cancel"));
                buttonOK.setText(bundleLt.getString("confirm"));
                message1 = bundleLt.getString("insMessage1");
                message2 = bundleLt.getString("insMessage2");
                message3 = bundleLt.getString("insMessage3");
                message4 = bundleLt.getString("insMessage4");
                message7 = bundleLt.getString("insMessage7");
                message8 = bundleLt.getString("insMessage8");
                message9 = bundleLt.getString("insMessage9");
                message10 = bundleLt.getString("numbMessage1");
                message11 = bundleLt.getString("numbMessage2");
                break;
            case "German":
                buttonCancel.setText(bundleDe.getString("cancel"));
                buttonOK.setText(bundleDe.getString("confirm"));
                message1 = bundleDe.getString("insMessage1");
                message2 = bundleDe.getString("insMessage2");
                message3 = bundleDe.getString("insMessage3");
                message4 = bundleDe.getString("insMessage4");
                message7 = bundleDe.getString("insMessage7");
                message8 = bundleDe.getString("insMessage8");
                message9 = bundleDe.getString("insMessage9");
                message10 = bundleDe.getString("numbMessage1");
                message11 = bundleDe.getString("numbMessage2");
                break;
            case "Spanish (Mexico)":
                buttonCancel.setText(bundleEs.getString("cancel"));
                buttonOK.setText(bundleEs.getString("confirm"));
                message1 = bundleEs.getString("insMessage1");
                message2 = bundleEs.getString("insMessage2");
                message3 = bundleEs.getString("insMessage3");
                message4 = bundleEs.getString("insMessage4");
                message7 = bundleEs.getString("insMessage7");
                message8 = bundleEs.getString("insMessage8");
                message9 = bundleEs.getString("insMessage9");
                message10 = bundleEs.getString("numbMessage1");
                message11 = bundleEs.getString("numbMessage2");
                break;
        }
    }

    public Insert(ClientEvents clientEvents, Command command) {
        this.clientEvents = clientEvents;
        worker = command;
        clientManager = clientEvents.getClientManager();
        $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
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

    private void onOK() throws IOException, ClassNotFoundException {
        // add your code here
        boolean isPassportSetUp = false;
        String nameField = name.getText();
        if (!nameField.equals("")) {
            if (checker.checkName(name.getText())) {
                worker.setName(name.getText());
                label1.setText("");
            }
        } else {
            label1.setText(message1);
        }
        String salaryField = salary.getText();
        if (!salaryField.equals("")) {
            Integer number = Utils.integerConverter(salaryField);
            if (number != null) {
                if (checker.checkSalary(number)) {
                    worker.setSalary(Integer.valueOf(salary.getText()));
                    label4.setText("");
                } else {
                    label4.setText(message2);
                }
            } else {
                label4.setText(message1);
            }
        } else {
            label4.setText(message2);
        }
        String cordxField = cordx.getText();
        if (!cordxField.equals("")) {
            Integer number = Utils.integerConverter(cordxField);
            if (number != null) {
                if (checker.checkX(number)) {
                    worker.setX(Integer.valueOf(cordxField));
                    label3.setText("");
                } else {
                    label3.setText(message3);
                }
            } else {
                label3.setText(message10);
            }
        } else {
            label3.setText(message3);
        }
        String cordyField = cordy.getText();
        if (!cordxField.equals("")) {
            Double number = Utils.doubleConverter(cordxField);
            if (number != null) {
                if (checker.checkY(number)) {
                    worker.setY(Utils.doubleConverter(cordxField));
                    label2.setText("");
                } else {
                    label2.setText(message4);
                }
            } else {
                label2.setText(message11);
            }
        } else {
            label2.setText(message4);
        }
        String positionField = position.getSelectedItem().toString();
        if (!positionField.equals("")) {
            if (checker.checkPosition(Utils.positionConverter(positionField, label5))) {
                worker.setPosition(Position.valueOf(positionField));
                label5.setText("");
            }
        } else {
            label5.setText("DIRECTOR,\n" +
                    "LABORER,\n" +
                    "HUMAN_RESOURCES,\n" +
                    "HEAD_OF_DEPARTMENT,\n" +
                    "MANAGER_OF_CLEANING");
        }
        String heightField = height.getText();
        if (!heightField.equals("")) {
            if (checker.checkHeight(Utils.floatConverter(heightField))) {
                worker.setHeight(Float.parseFloat(heightField));
                label9.setText("");
            }
        } else {
            label9.setText(message9);
        }
        String statusField = status.getSelectedItem().toString();
        if (!statusField.equals("")) {
            if (checker.checkStatus(Utils.statusConverter(statusField, label6))) {
                worker.setStatus(Status.valueOf(statusField));
                label6.setText("");
            }
        } else {
            label6.setText("HIRED,\n" +
                    "RECOMMENDED_FOR_PROMOTION,\n" +
                    "REGULAR,\n" +
                    "PROBATION");
        }
        String birthdayField = birthday.getText();
        if (!birthdayField.equals("")) {
            if (checker.checkBirthday(birthdayField)) {
                LocalDateTime birthdayParsed = LocalDateTime.parse(birthdayField, formatter);
                worker.setBirthday(birthdayParsed);
                label8.setText("");
            } else {
                label8.setText(message8);
            }
        } else {
            label8.setText(message8);
        }
        String passportField = passport.getText();
        if (!passportField.equals("")) {
            if (clientManager.setupPassport(worker, passportField)) {
                if (checker.checkWorker(worker)) {
                    if (outPanel != null) {
                        clientEvents.commandMode(worker, outPanel);
                    } else {
                        clientEvents.commandMode(worker);

                    }
                    dispose();
                }
                label7.setText("");
            } else {
                label7.setText(message7);
            }
        } else {
            label7.setText(message7);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        setVisible(false);
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
        contentPane.setPreferredSize(new Dimension(800, 700));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setPreferredSize(new Dimension(209, 30));
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
        buttonOK.setText("Подтвердить");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(buttonOK, gbc);
        buttonCancel = new JButton();
        buttonCancel.setText("Отменить");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(buttonCancel, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setPreferredSize(new Dimension(500, 315));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel3, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel4, gbc);
        passport = new JTextField();
        passport.setPreferredSize(new Dimension(100, 20));
        passport.setText("");
        passport.putClientProperty("html.disable", Boolean.FALSE);
        panel4.add(passport, BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel5, gbc);
        status = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("HIRED");
        defaultComboBoxModel1.addElement("RECOMMENDED_FOR_PROMOTION");
        defaultComboBoxModel1.addElement("REGULAR");
        defaultComboBoxModel1.addElement("PROBATION");
        status.setModel(defaultComboBoxModel1);
        status.setPreferredSize(new Dimension(150, 20));
        panel5.add(status, BorderLayout.WEST);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel6.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel6, gbc);
        position = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("DIRECTOR");
        defaultComboBoxModel2.addElement("LABORER");
        defaultComboBoxModel2.addElement("HUMAN_RESOURCES");
        defaultComboBoxModel2.addElement("HEAD_OF_DEPARTMENT");
        defaultComboBoxModel2.addElement("MANAGER_OF_CLEANING");
        position.setModel(defaultComboBoxModel2);
        position.setPreferredSize(new Dimension(150, 20));
        panel6.add(position, BorderLayout.WEST);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel7.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel7, gbc);
        salary = new JTextField();
        salary.setPreferredSize(new Dimension(100, 20));
        salary.setText("");
        salary.putClientProperty("html.disable", Boolean.FALSE);
        panel7.add(salary, BorderLayout.CENTER);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel8.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel8, gbc);
        cordy = new JTextField();
        cordy.setPreferredSize(new Dimension(100, 20));
        cordy.setText("");
        cordy.putClientProperty("html.disable", Boolean.FALSE);
        panel8.add(cordy, BorderLayout.CENTER);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel9.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel9, gbc);
        cordx = new JTextField();
        cordx.setPreferredSize(new Dimension(100, 20));
        cordx.setText("");
        cordx.putClientProperty("html.disable", Boolean.FALSE);
        panel9.add(cordx, BorderLayout.CENTER);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        panel10.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel10, gbc);
        name = new JTextField();
        name.setPreferredSize(new Dimension(100, 20));
        name.setText("");
        name.putClientProperty("html.disable", Boolean.FALSE);
        panel10.add(name, BorderLayout.CENTER);
        final JLabel label10 = new JLabel();
        label10.setText("Name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label10, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("Coordinates_x:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label11, gbc);
        final JLabel label12 = new JLabel();
        label12.setText("Coordinates_y:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label12, gbc);
        final JLabel label13 = new JLabel();
        label13.setText("Salary:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label13, gbc);
        final JLabel label14 = new JLabel();
        label14.setText("Position:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label14, gbc);
        final JLabel label15 = new JLabel();
        label15.setText("Status:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label15, gbc);
        final JLabel label16 = new JLabel();
        label16.setText("Passport:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label16, gbc);
        final JLabel label17 = new JLabel();
        label17.setText("Birthday:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label17, gbc);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new BorderLayout(0, 0));
        panel11.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 14;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel11, gbc);
        birthday = new JTextField();
        birthday.setPreferredSize(new Dimension(100, 20));
        birthday.setText("");
        birthday.putClientProperty("html.disable", Boolean.FALSE);
        panel11.add(birthday, BorderLayout.CENTER);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new BorderLayout(0, 0));
        panel12.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel12, gbc);
        height = new JTextField();
        height.setPreferredSize(new Dimension(100, 20));
        height.setText("");
        height.putClientProperty("html.disable", Boolean.FALSE);
        panel12.add(height, BorderLayout.CENTER);
        final JLabel label18 = new JLabel();
        label18.setText("Height:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label18, gbc);
        label1 = new JLabel();
        label1.setForeground(new Color(-48818));
        label1.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label1, gbc);
        label2 = new JLabel();
        label2.setForeground(new Color(-48818));
        label2.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label2, gbc);
        label3 = new JLabel();
        label3.setForeground(new Color(-48818));
        label3.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label3, gbc);
        label4 = new JLabel();
        label4.setForeground(new Color(-48818));
        label4.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label4, gbc);
        label5 = new JLabel();
        label5.setForeground(new Color(-48818));
        label5.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label5, gbc);
        label6 = new JLabel();
        label6.setForeground(new Color(-48818));
        label6.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label6, gbc);
        label7 = new JLabel();
        label7.setForeground(new Color(-48818));
        label7.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label7, gbc);
        label8 = new JLabel();
        label8.setForeground(new Color(-48818));
        label8.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 15;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label8, gbc);
        label9 = new JLabel();
        label9.setBackground(new Color(-48818));
        label9.setForeground(new Color(-48818));
        label9.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label9, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
