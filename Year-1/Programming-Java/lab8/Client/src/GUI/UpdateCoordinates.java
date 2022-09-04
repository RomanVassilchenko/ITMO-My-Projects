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
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateCoordinates extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cordx;
    private JLabel label1;
    private JLabel label2;
    private JTextField cordy;
    private final ClientEvents clientEvents;
    private final JTextArea outPanel;
    private final Command command;
    private final DataCheckerForClient checker = new DataCheckerForClient();
    private String message3, message4, message10, message11;
    final ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    final ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    final ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    final ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));


    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian" -> {
                buttonOK.setText(bundleRu.getString("execute"));
                buttonCancel.setText(bundleRu.getString("cancel"));
                message3 = bundleRu.getString("insMessage3");
                message4 = bundleRu.getString("insMessage4");
                message10 = bundleRu.getString("numbMessage1");
                message11 = bundleRu.getString("numbMessage2");
            }
            case "Lithuanian" -> {
                buttonOK.setText(bundleLt.getString("execute"));
                buttonCancel.setText(bundleLt.getString("cancel"));
                message3 = bundleLt.getString("insMessage3");
                message4 = bundleLt.getString("insMessage4");
                message10 = bundleLt.getString("numbMessage1");
                message11 = bundleLt.getString("numbMessage2");
            }
            case "German" -> {
                buttonOK.setText(bundleDe.getString("execute"));
                buttonCancel.setText(bundleDe.getString("cancel"));
                message3 = bundleDe.getString("insMessage3");
                message4 = bundleDe.getString("insMessage4");
                message10 = bundleDe.getString("numbMessage1");
                message11 = bundleDe.getString("numbMessage2");
            }
            case "Spanish (Mexico)" -> {
                buttonOK.setText(bundleEs.getString("execute"));
                buttonCancel.setText(bundleEs.getString("cancel"));
                message3 = bundleEs.getString("insMessage3");
                message4 = bundleEs.getString("insMessage4");
                message10 = bundleEs.getString("numbMessage1");
                message11 = bundleEs.getString("numbMessage2");
            }
        }
    }

    public UpdateCoordinates(ClientEvents clientEvents, JTextArea outPanel, Command command, String languageIn) {
        this.clientEvents = clientEvents;
        this.outPanel = outPanel;
        this.command = command;
        ClientManager clientManager = clientEvents.getClientManager();
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

    private void onOK() throws IOException, ClassNotFoundException {
        // add your code here
        String cordxField = cordx.getText();
        if (!cordxField.equals("")) {
            Integer number = Utils.integerConverter(cordxField);
            if (number != null) {
                if (checker.checkX(number)) {
                    command.setX(Integer.valueOf(cordxField));
                    label1.setText("");
                } else {
                    label1.setText(message3);
                }
            } else {
                label1.setText(message10);
            }
        } else {
            label1.setText(message3);
        }
        String cordyField = cordy.getText();
        if (!cordyField.equals("")) {
            Double number = Utils.doubleConverter(cordyField);
            if (number != null) {
                if (checker.checkY(number)) {
                    command.setY(Utils.doubleConverter(cordyField));
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
        if (checker.checkY(Utils.doubleConverter(cordyField)) && checker.checkX(Utils.integerConverter(cordyField))) {
            clientEvents.commandMode(command, outPanel);
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
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
        contentPane.setPreferredSize(new Dimension(400, 200));
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
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel4, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("cord_x:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label3, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(panel5, gbc);
        label1 = new JLabel();
        label1.setForeground(new Color(-48818));
        label1.setPreferredSize(new Dimension(250, 20));
        label1.setText("");
        panel5.add(label1, BorderLayout.CENTER);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(panel6, gbc);
        label2 = new JLabel();
        label2.setBackground(new Color(-48818));
        label2.setForeground(new Color(-48818));
        label2.setPreferredSize(new Dimension(250, 20));
        label2.setText("");
        panel6.add(label2, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(panel7, gbc);
        cordx = new JTextField();
        cordx.setPreferredSize(new Dimension(250, 30));
        cordx.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel7.add(cordx, gbc);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(panel8, gbc);
        cordy = new JTextField();
        cordy.setPreferredSize(new Dimension(250, 30));
        cordy.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel8.add(cordy, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("cord_y:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label4, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
