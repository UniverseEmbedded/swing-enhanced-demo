package pama1234.dstar.util;

import pama1234.dstar.Main; // 引入Main以便获取LanguageManager
import pama1234.dstar.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

public class DialogUtils {

    /**
     * 显示信息对话框，自动应用Maple Mono字体
     */
    public static void showInfoDialog(Component parent, String message, String title) {
        showDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE, null);
    }

    /**
     * 显示错误对话框，自动应用Maple Mono字体
     */
    public static void showErrorDialog(Component parent, String message, String title) {
        showDialog(parent, message, title, JOptionPane.ERROR_MESSAGE, null);
    }

    /**
     * 显示警告对话框，自动应用Maple Mono字体
     */
    public static void showWarningDialog(Component parent, String message, String title) {
        showDialog(parent, message, title, JOptionPane.WARNING_MESSAGE, null);
    }

    /**
     * 显示自定义对话框，自动应用Maple Mono字体
     */
    public static void showDialog(Component parent, Object message, String title,
                                  int messageType, Icon icon) {
        // 临时保存原有字体设置
        Font originalMessageFont = UIManager.getFont("OptionPane.messageFont");
        Font originalButtonFont = UIManager.getFont("OptionPane.buttonFont");

        try {
            // 设置OptionPane的字体
            Font customFont = FontManager.getInstance().getCustomFont();
            if (customFont != null) {
                UIManager.put("OptionPane.messageFont", customFont);
                UIManager.put("OptionPane.buttonFont", customFont);
            }

            JOptionPane optionPane = new JOptionPane(message, messageType,
                    JOptionPane.DEFAULT_OPTION, icon, new Object[]{Main.getLanguageManager().getString("dialog.button.ok")}); // 使用本地化的确定按钮

            JDialog dialog = optionPane.createDialog(parent, title);

            // 应用Maple Mono字体到整个对话框
            FontManager.getInstance().applyToComponent(dialog);

            dialog.setVisible(true);

        } finally {
            // 恢复原有字体设置
            if (originalMessageFont != null) {
                UIManager.put("OptionPane.messageFont", originalMessageFont);
            }
            if (originalButtonFont != null) {
                UIManager.put("OptionPane.buttonFont", originalButtonFont);
            }
        }
    }

    /**
     * 创建自定义对话框，完全控制字体
     */
    public static void showCustomDialog(Component parent, String message, String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        // 创建内容面板
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加消息
        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(messageLabel, BorderLayout.CENTER);

        // 添加按钮
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton(Main.getLanguageManager().getString("dialog.button.ok")); // 本地化按钮
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel);

        // 应用字体
        FontManager.getInstance().applyToComponent(dialog);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    /**
     * 显示HTML格式的对话框（用于关于对话框等）
     */
    public static void showHtmlDialog(Component parent, String htmlContent, String title, Icon icon) {
        // 临时设置字体
        Font customFont = FontManager.getInstance().getCustomFont();
        Font originalMessageFont = UIManager.getFont("OptionPane.messageFont");
        Font originalButtonFont = UIManager.getFont("OptionPane.buttonFont");

        try {
            if (customFont != null) {
                UIManager.put("OptionPane.messageFont", customFont);
                UIManager.put("OptionPane.buttonFont", customFont);
            }

            // 使用本地化的确定按钮
            JOptionPane optionPane = new JOptionPane(htmlContent, JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, icon, new Object[]{Main.getLanguageManager().getString("dialog.button.ok")});

            JDialog dialog = optionPane.createDialog(parent, title);

            // 应用字体到整个对话框
            FontManager.getInstance().applyToComponent(dialog);

            dialog.setVisible(true);

        } finally {
            // 恢复字体设置
            if (originalMessageFont != null) {
                UIManager.put("OptionPane.messageFont", originalMessageFont);
            }
            if (originalButtonFont != null) {
                UIManager.put("OptionPane.buttonFont", originalButtonFont);
            }
        }
    }
}