package GUI;

import Dependency.Command;
import Dependency.Worker;
import NetInteraction.ClientEvents;
import NetInteraction.ClientManager;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateWorkerForm {
    private JPanel panel1;
    private JButton updateButton;
    private JButton removeButton;
    private JComboBox comboBox1;
    private JTextArea name;
    private JTextArea cord_y;
    private JTextArea salary;
    private JTextArea position;
    private JTextArea status;
    private JTextArea passport;
    private JTextArea birthday;
    private JTextArea height;
    private JTextArea cord_x;
    private JTextArea textArea1;
    private JTextArea id;
    private JTextArea author;
    private JTextArea creationDate;
    private final ClientManager clientManager;
    private final int key;
    private JFrame parent;
    private final JFrame jFrame;
    private final String language;
    private DateFormat df;
    private final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US);

    final ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    final ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    final ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    final ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));

    public UpdateWorkerForm(ClientEvents clientEvents, Worker worker, String languageIn) {
        clientManager = clientEvents.getClientManager();
        this.key = Math.toIntExact(worker.getId());
        this.language = languageIn;
        setupSelectedLanguages(language);
        setupValues(worker);
        jFrame = new JFrame();
        jFrame.setSize(700, 1000);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.add(panel1);
        DefaultCaret caret = (DefaultCaret) textArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        updateButton.addActionListener(e -> {
            switch (comboBox1.getSelectedItem().toString()) {
                case "coordinates" -> {
                    Command upd1 = new Command("update " + key + " coordinates");
                    upd1.setId(key);
                    upd1.setElement(comboBox1.getSelectedItem().toString());
                    UpdateCoordinates updateCoordinates = new UpdateCoordinates(clientEvents
                            , textArea1, upd1, language);
                }
                case "person" -> {
                    Command upd2 = new Command("update " + key + " person");
                    upd2.setId(key);
                    upd2.setElement(comboBox1.getSelectedItem().toString());
                    UpdatePerson updatePerson = new UpdatePerson(clientEvents
                            , textArea1, upd2, language);
                }
                default -> {
                    Command upd3 = new Command("update "
                            + key + " " + comboBox1.getSelectedItem().toString());
                    upd3.setId(key);
                    upd3.setElement(comboBox1.getSelectedItem().toString());
                    Update update = new Update(clientEvents, textArea1, upd3, language);
                }
            }
            try {
                clientEvents.commandMode(new Command("getCollectionForTable"));
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
            setupValues(clientManager.getCollectionForTable().getWorkerList().get(key));

        });
        removeButton.addActionListener(e -> {
            Command remove = new Command("remove_key " + key);
            try {
                clientEvents.commandMode(remove, textArea1);
                jFrame.dispose();
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian" -> {
                removeButton.setText(bundleRu.getString("remove"));
                updateButton.setText(bundleRu.getString("update"));
                df = (DateFormat) bundleRu.getObject("date");
            }
            case "Lithuanian" -> {
                removeButton.setText(bundleLt.getString("remove"));
                updateButton.setText(bundleLt.getString("update"));
                df = (DateFormat) bundleLt.getObject("date");
            }
            case "German" -> {
                removeButton.setText(bundleDe.getString("remove"));
                updateButton.setText(bundleDe.getString("update"));
                df = (DateFormat) bundleDe.getObject("date");
            }
            case "Spanish (Mexico)" -> {
                removeButton.setText(bundleEs.getString("remove"));
                updateButton.setText(bundleEs.getString("update"));
                df = (DateFormat) bundleEs.getObject("date");
            }
        }
    }

    public void setupValues(Worker worker) {
        id.setText(String.valueOf(worker.getId()));
        author.setText(worker.getAuthor());
        name.setText(worker.getName());
        try {
            creationDate.setText(df.format(inputFormat.parse(String.valueOf(worker.getCreationDate()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cord_x.setText(String.valueOf(worker.getCoordinates().getX()));
        cord_y.setText(String.valueOf(worker.getCoordinates().getY()));
        salary.setText(String.valueOf(worker.getSalary()));
        position.setText(String.valueOf(worker.getPosition()));
        status.setText(String.valueOf(worker.getStatus()));
        passport.setText(worker.getPerson().getPassportID());
        try {
            birthday.setText(df.format(inputFormat.parse(String.valueOf(worker.getPerson().getBirthday()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        height.setText(String.valueOf(worker.getPerson().getHeight()));
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
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setPreferredSize(new Dimension(343, 550));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(panel2, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel3, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 18;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel4, gbc);
        passport = new JTextArea();
        panel4.add(passport, BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel5, gbc);
        status = new JTextArea();
        panel5.add(status, BorderLayout.CENTER);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel6.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 14;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel6, gbc);
        position = new JTextArea();
        panel6.add(position, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel7.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel7, gbc);
        salary = new JTextArea();
        panel7.add(salary, BorderLayout.CENTER);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel8.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel8, gbc);
        cord_y = new JTextArea();
        panel8.add(cord_y, BorderLayout.CENTER);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel9.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel9, gbc);
        cord_x = new JTextArea();
        panel9.add(cord_x, BorderLayout.CENTER);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        panel10.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel10, gbc);
        name = new JTextArea();
        name.setText("");
        panel10.add(name, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("Name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Coordinates_x:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Coordinates_y:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Salary:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Position:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label5, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Status:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label6, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Passport:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label7, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Birthday:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label8, gbc);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new BorderLayout(0, 0));
        panel11.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 20;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel11, gbc);
        birthday = new JTextArea();
        panel11.add(birthday, BorderLayout.CENTER);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new BorderLayout(0, 0));
        panel12.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 22;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel12, gbc);
        height = new JTextArea();
        panel12.add(height, BorderLayout.CENTER);
        final JLabel label9 = new JLabel();
        label9.setText("Height:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 22;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label9, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 19;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 21;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer8, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 23;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer10, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(spacer11, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("id:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label10, gbc);
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new BorderLayout(0, 0));
        panel13.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel13, gbc);
        id = new JTextArea();
        panel13.add(id, BorderLayout.CENTER);
        final JLabel label11 = new JLabel();
        label11.setText("author:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label11, gbc);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer12, gbc);
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new BorderLayout(0, 0));
        panel14.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel14, gbc);
        author = new JTextArea();
        author.setText("");
        panel14.add(author, BorderLayout.CENTER);
        final JLabel label12 = new JLabel();
        label12.setText("CreationDate:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label12, gbc);
        final JPanel spacer13 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer13, gbc);
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new BorderLayout(0, 0));
        panel15.setPreferredSize(new Dimension(150, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel15, gbc);
        creationDate = new JTextArea();
        panel15.add(creationDate, BorderLayout.CENTER);
        final JPanel panel16 = new JPanel();
        panel16.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel16, gbc);
        updateButton = new JButton();
        updateButton.setText("Обновить");
        panel16.add(updateButton);
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("name");
        defaultComboBoxModel1.addElement("coordinates");
        defaultComboBoxModel1.addElement("salary");
        defaultComboBoxModel1.addElement("position");
        defaultComboBoxModel1.addElement("status");
        defaultComboBoxModel1.addElement("person");
        comboBox1.setModel(defaultComboBoxModel1);
        panel16.add(comboBox1);
        final JPanel panel17 = new JPanel();
        panel17.setLayout(new GridBagLayout());
        panel16.add(panel17);
        final JPanel spacer14 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel17.add(spacer14, gbc);
        final JPanel spacer15 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel17.add(spacer15, gbc);
        final JPanel spacer16 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel17.add(spacer16, gbc);
        removeButton = new JButton();
        removeButton.setText("Удалить");
        panel16.add(removeButton);
        final JPanel panel18 = new JPanel();
        panel18.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel18, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(320, 50));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel18.add(scrollPane1, gbc);
        textArea1 = new JTextArea();
        scrollPane1.setViewportView(textArea1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
