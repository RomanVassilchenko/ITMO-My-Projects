package GUI;

import Dependency.Command;
import NetInteraction.ClientEvents;
import NetInteraction.ClientManager;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class CommanderForm {
    private final ClientEvents clientEvents;
    private final JFrame commanderFrame;
    private JPanel commanderPanel;
    private JButton executorButton;
    private JTextField commandField;
    private JTextArea outputPanel;
    private JButton backButton;
    private JScrollPane scrollPane1;
    private JLabel titleLabel;
    private final String language;
    final ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    final ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    final ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    final ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));

    public CommanderForm(JFrame parent, ClientEvents clientEvents, Object languageIn) {
        this.clientEvents = clientEvents;
        $$$setupUI$$$();
        language = languageIn.toString();
        setupSelectedLanguages(language);
        DefaultCaret caret = (DefaultCaret) outputPanel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        ClientManager clientManager = clientEvents.getClientManager();
        commanderFrame = new JFrame();
        commanderFrame.setSize(600, 400);
        commanderFrame.setExtendedState(MAXIMIZED_BOTH);
        commanderFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        commanderFrame.setLocationRelativeTo(null);
        commanderFrame.setVisible(true);
        commanderFrame.add(commanderPanel);
        executorButton.addActionListener(new ExecutorListener());
        commanderPanel.registerKeyboardAction(new ExecutorListener()
                , KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        backButton.addActionListener(e -> {
            commanderFrame.dispose();
            parent.setVisible(true);
        });
    }


    class ExecutorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] finalUserCommand = commandField.getText().trim().split(" +", 3);
                Command command = new Command(commandField.getText());
                if (finalUserCommand[0].equals("insert")) {
                    command.setKey(Integer.valueOf(finalUserCommand[1]));
                    Insert insert = new Insert(clientEvents, outputPanel, command, language);
                } else if (finalUserCommand[0].equals("update")) {
                    command.setId(Integer.parseInt(finalUserCommand[1]));
                    command.setElement(finalUserCommand[2]);
                    if (finalUserCommand[2].equals("person")) {
                        UpdatePerson update = new UpdatePerson(clientEvents, outputPanel, command, language);

                    } else if (finalUserCommand[2].equals("coordinates")) {
                        UpdateCoordinates update = new UpdateCoordinates(clientEvents, outputPanel, command, language);
                    } else {
                        Update update = new Update(clientEvents, outputPanel, command, language);
                    }
                } else {
                    clientEvents.commandMode(command, outputPanel);
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian" -> {
                backButton.setText(bundleRu.getString("back"));
                executorButton.setText(bundleRu.getString("executeCommand"));
                titleLabel.setText(bundleRu.getString("executeTitle"));
            }
            case "Lithuanian" -> {
                backButton.setText(bundleLt.getString("back"));
                executorButton.setText(bundleLt.getString("executeCommand"));
                titleLabel.setText(bundleLt.getString("executeTitle"));
            }
            case "German" -> {
                backButton.setText(bundleDe.getString("back"));
                executorButton.setText(bundleDe.getString("executeCommand"));
                titleLabel.setText(bundleDe.getString("executeTitle"));
            }
            case "Spanish (Mexico)" -> {
                backButton.setText(bundleEs.getString("back"));
                executorButton.setText(bundleEs.getString("executeCommand"));
                titleLabel.setText(bundleEs.getString("executeTitle"));
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
        commanderPanel = new JPanel();
        commanderPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        commanderPanel.add(panel1, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(panel2, BorderLayout.WEST);
        backButton = new JButton();
        backButton.setText("Назад");
        panel2.add(backButton);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setPreferredSize(new Dimension(150, 44));
        commanderPanel.add(panel3, BorderLayout.CENTER);
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer1, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel4, gbc);
        executorButton = new JButton();
        executorButton.setText("Выполнить команду");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(executorButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel4.add(spacer2, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setOpaque(true);
        panel5.setPreferredSize(new Dimension(450, 260));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 300;
        panel3.add(panel5, gbc);
        scrollPane1 = new JScrollPane();
        scrollPane1.setAlignmentX(0.0f);
        scrollPane1.setAlignmentY(0.0f);
        scrollPane1.setHorizontalScrollBarPolicy(30);
        panel5.add(scrollPane1, BorderLayout.CENTER);
        outputPanel = new JTextArea();
        outputPanel.setDropMode(DropMode.USE_SELECTION);
        outputPanel.setPreferredSize(new Dimension(450, 10000));
        scrollPane1.setViewportView(outputPanel);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel6, gbc);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel7, gbc);
        commandField = new JTextField();
        commandField.setPreferredSize(new Dimension(500, 30));
        panel7.add(commandField);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return commanderPanel;
    }

}
