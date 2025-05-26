
//    Я испугалась что-то испортить в общей программе поэтому закинула код сюда
//+ я не знаю, что в него можно добавить из возможностей и внешнего вида для пользователей
package org.example.chatclientjava41;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

    public class MainView extends JFrame {

        // Данные пользователя (профиль)
        private String userName = "Simon";
        private String phoneNumber = "+1234567890";
        private String description = "Ну типа описание";
        private ImageIcon avatarImage = null; // может быть null

        // Компоненты профиля
        private JLabel avatarLabel;
        private JLabel nameLabel;
        private JLabel phoneLabel;
        private JLabel descriptionLabel;

        // Контакты и чат
        private DefaultListModel<Contact> contactsModel;
        private JList<Contact> contactsList;

        private JTextArea chatArea;
        private JTextField messageField;
        private JButton sendButton;
        private JButton imageButton;
        private JButton voiceButton;

        public MainView() {
            setTitle("Chat Application");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(900, 600);
            setLocationRelativeTo(null);

            // Основной layout с тремя областями: лево - контакты, центр - чат, права - профиль
            setLayout(new BorderLayout(5,5));

            // Создаем панели
            JPanel contactsPanel = createContactsPanel();
            JPanel chatPanel = createChatPanel();
            JPanel profilePanel = createProfilePanel();

            add(contactsPanel, BorderLayout.WEST);
            add(chatPanel, BorderLayout.CENTER);
            add(profilePanel, BorderLayout.EAST);
        }

        private JPanel createProfilePanel() {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(250, 0));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createTitledBorder("Профиль"));
            panel.setBackground(Color.WHITE);

            avatarLabel = new JLabel();
            avatarLabel.setPreferredSize(new Dimension(100, 100));
            avatarLabel.setMaximumSize(new Dimension(100, 100));
            avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
            avatarLabel.setVerticalAlignment(SwingConstants.CENTER);
            avatarLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            updateAvatarDisplay();

            JButton changeAvatarButton = new JButton("Изменить аватар");
            changeAvatarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            changeAvatarButton.addActionListener(e -> changeAvatar());

            nameLabel = new JLabel("Имя пользователя: " + userName);
            phoneLabel = new JLabel("Телефон: " + phoneNumber);
            descriptionLabel = new JLabel("<html><body style='width:200px'>" + description + "</body></html>");

            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Кнопка редактирования профиля
            JButton editProfileButton = new JButton("Редактировать профиль");
            editProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            editProfileButton.addActionListener(e -> editProfile());

            panel.add(Box.createVerticalStrut(10));
            panel.add(avatarLabel);
            panel.add(Box.createVerticalStrut(5));
            panel.add(changeAvatarButton);
            panel.add(Box.createVerticalStrut(10));
            panel.add(nameLabel);
            panel.add(Box.createVerticalStrut(5));
            panel.add(phoneLabel);
            panel.add(Box.createVerticalStrut(5));
            panel.add(descriptionLabel);
            panel.add(Box.createVerticalStrut(20));
            panel.add(editProfileButton);
            panel.add(Box.createVerticalGlue());

            panel.setBorder(new EmptyBorder(10,10,10,10));

            return panel;
        }

        private void updateAvatarDisplay() {
            if (avatarImage != null) {
                // Масштабируем изображение под размер 100x100
                Image img = avatarImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                avatarLabel.setIcon(new ImageIcon(img));
                avatarLabel.setText("");
            } else {
                // Если нет аватарки, показываем первую букву имени
                String firstLetter = userName.isEmpty() ? "?" : userName.substring(0,1).toUpperCase();
                avatarLabel.setIcon(null);
                avatarLabel.setText(firstLetter);
                avatarLabel.setFont(new Font("Arial", Font.BOLD, 48));
                avatarLabel.setForeground(Color.DARK_GRAY);
            }
        }

        private void changeAvatar() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Выберите изображение");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Изображения", "jpg", "jpeg", "png", "gif"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                ImageIcon newAvatar = new ImageIcon(file.getAbsolutePath());
                avatarImage = newAvatar;
                updateAvatarDisplay();
            }
        }

        private void editProfile() {
            JTextField nameField = new JTextField(userName);
            JTextField phoneField = new JTextField(phoneNumber);
            JTextArea descArea = new JTextArea(description, 5, 20);
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            JScrollPane descScroll = new JScrollPane(descArea);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5,5,5,5);
            gbc.anchor = GridBagConstraints.WEST;

            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Имя пользователя:"), gbc);
            gbc.gridx = 1;
            panel.add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Телефон:"), gbc);
            gbc.gridx = 1;
            panel.add(phoneField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("Описание:"), gbc);
            gbc.gridx = 1;
            panel.add(descScroll, gbc);

            int result = JOptionPane.showConfirmDialog(this, panel, "Редактировать профиль", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                userName = nameField.getText().trim();
                phoneNumber = phoneField.getText().trim();
                description = descArea.getText().trim();

                // Обновляем отображение
                nameLabel.setText("Имя пользователя: " + userName);
                phoneLabel.setText("Телефон: " + phoneNumber);
                descriptionLabel.setText("<html><body style='width:200px'>" + description + "</body></html>");
                updateAvatarDisplay();
            }
        }

        private JPanel createContactsPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setPreferredSize(new Dimension(250, 0));
            panel.setBorder(BorderFactory.createTitledBorder("Контакты"));

            contactsModel = new DefaultListModel<>();
            // Пример контактов с последними сообщениями
            contactsModel.addElement(new Contact("Иван", "Привет! Как дела?"));
            contactsModel.addElement(new Contact("Мария", "Я тебе отправила на почту рецепт торта с водкой"));
            contactsModel.addElement(new Contact("Алексей", "Давай встретимся завтра"));
            contactsModel.addElement(new Contact("Ольга", "Работа с гибким графиком, пиши +"));
            contactsModel.addElement(new Contact("Саня", "Сотку когда вернешь?"));

            contactsList = new JList<>(contactsModel);
            contactsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            contactsList.setCellRenderer(new ContactCellRenderer());

            contactsList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    Contact selected = contactsList.getSelectedValue();
                    showChat(selected);
                }
            });

            JScrollPane scrollPane = new JScrollPane(contactsList);
            panel.add(scrollPane, BorderLayout.CENTER);

            return panel;
        }

        private JPanel createChatPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Чат"));

            chatArea = new JTextArea();
            chatArea.setEditable(false);
            chatArea.setLineWrap(true);
            chatArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(chatArea);

            panel.add(scrollPane, BorderLayout.CENTER);

            JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            messageField = new JTextField(30);

            sendButton = new JButton("Отправить");
            sendButton.addActionListener(e -> sendMessage());

            imageButton = new JButton("📷");
            imageButton.setToolTipText("Отправить изображение");
            imageButton.addActionListener(e -> sendImage());

            voiceButton = new JButton("🎤");
            voiceButton.setToolTipText("Голосовой ввод");
            voiceButton.addActionListener(e -> voiceInput());

            inputPanel.add(messageField);
            inputPanel.add(sendButton);
            inputPanel.add(imageButton);
            inputPanel.add(voiceButton);

            panel.add(inputPanel, BorderLayout.SOUTH);

            return panel;
        }

        private Contact currentChat = null;

        private void showChat(Contact contact) {
            currentChat = contact;
            chatArea.setText(""); // очищаем чат

            if (contact != null) {
                // В реальном приложении здесь загрузить историю сообщений с этим контактом
                chatArea.append("Чат с " + contact.getName() + "\n");
                chatArea.append(contact.getLastMessage() + "\n");
            }
        }

        private void sendMessage() {
            if (currentChat == null) {
                JOptionPane.showMessageDialog(this, "Выберите контакт для отправки сообщения.", "Внимание", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                chatArea.append("Вы: " + message + "\n");
                // Здесь логика отправки сообщения
                // обновим последнее сообщение у контакта
                currentChat.setLastMessage(message);
                contactsList.repaint();
                messageField.setText("");
            }
        }

        private void sendImage() {
            if (currentChat == null) {
                JOptionPane.showMessageDialog(this, "Выберите контакт для отправки изображения.", "Внимание", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Выберите изображение");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Изображения", "jpg", "jpeg", "png", "gif"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                chatArea.append("Вы отправили фото: " + file.getName() + "\n");
                currentChat.setLastMessage("[Фото]");
                contactsList.repaint();
            }
        }

        private void voiceInput() {
            // Заглушка для голосового ввода
            JOptionPane.showMessageDialog(this, "Голосовой ввод пока не реализован.", "Информация", JOptionPane.INFORMATION_MESSAGE);
        }

        // Вспомогательный класс для контактов
        private static class Contact {
            private String name;
            private String lastMessage;

            public Contact(String name, String lastMessage) {
                this.name = name;
                this.lastMessage = lastMessage;
            }

            public String getName() {
                return name;
            }

            public String getLastMessage() {
                return lastMessage;
            }

            public void setLastMessage(String lastMessage) {
                this.lastMessage = lastMessage;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        // Кастомный рендерер для отображения контактов с последними сообщениями
        private static class ContactCellRenderer extends JPanel implements ListCellRenderer<Contact> {
            private JLabel nameLabel = new JLabel();
            private JLabel messageLabel = new JLabel();

            public ContactCellRenderer() {
                setLayout(new BorderLayout());
                nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
                messageLabel.setFont(messageLabel.getFont().deriveFont(Font.PLAIN, 12f));
                messageLabel.setForeground(Color.GRAY);
                add(nameLabel, BorderLayout.NORTH);
                add(messageLabel, BorderLayout.SOUTH);
                setBorder(new EmptyBorder(5,5,5,5));
            }

            @Override
            public Component getListCellRendererComponent(JList<? extends Contact> list, Contact value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                nameLabel.setText(value.getName());
                String msg = value.getLastMessage();
                if (msg.length() > 30) {
                    msg = msg.substring(0, 27) + "...";
                }
                messageLabel.setText(msg);

                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                return this;
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                MainView view = new MainView();
                view.setVisible(true);
            });
        }
    }

