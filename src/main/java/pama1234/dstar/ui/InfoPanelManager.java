package pama1234.dstar.ui;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import pama1234.dstar.Main;
import pama1234.dstar.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 信息面板管理器
 */
public class InfoPanelManager {
    private JTextArea logArea;
    private JTextArea libraryInfoArea;
    private JTextArea systemInfoArea;

    // 日志控制按钮
    private JButton clearLogBtn;
    private JButton addLogBtn;

    // 保存用户添加的日志内容，用于语言切换时保留
    private StringBuilder userLogContent = new StringBuilder();

    public JPanel createLibraryInfoPanel() {
        JPanel panel = new JPanel(new MigLayout("fill", "[grow]", "[grow]"));

        libraryInfoArea = new JTextArea();
        libraryInfoArea.setEditable(false);
        libraryInfoArea.setText(getLibraryInfo());

        // 应用字体
        Font customFont = FontManager.getInstance().getCustomFont();
        if (customFont != null) {
            libraryInfoArea.setFont(customFont.deriveFont(Font.PLAIN, 12));
        }

        JScrollPane scrollPane = new JScrollPane(libraryInfoArea);
        panel.add(scrollPane, "grow");

        return panel;
    }

    public JPanel createSystemInfoPanel() {
        JPanel panel = new JPanel(new MigLayout("fill", "[grow]", "[grow]"));

        systemInfoArea = new JTextArea();
        systemInfoArea.setEditable(false);
        systemInfoArea.setText(getSystemInfo());

        Font customFont = FontManager.getInstance().getCustomFont();
        if (customFont != null) {
            systemInfoArea.setFont(customFont.deriveFont(Font.PLAIN, 12));
        }

        JScrollPane scrollPane = new JScrollPane(systemInfoArea);
        panel.add(scrollPane, "grow");

        return panel;
    }

    public JPanel createLogPanel() {
        JPanel panel = new JPanel(new MigLayout("fill", "[grow]", "[grow][30!]"));

        logArea = new JTextArea();
        logArea.setEditable(false);

        // 初始化日志内容
        initializeLogContent();

        Font customFont = FontManager.getInstance().getCustomFont();
        if (customFont != null) {
            logArea.setFont(customFont.deriveFont(Font.PLAIN, 11));
        }

        JScrollPane scrollPane = new JScrollPane(logArea);

        // 日志控制按钮
        JPanel logControls = new JPanel(new MigLayout("", "[][grow][]", ""));
        clearLogBtn = new JButton(Main.getLanguageManager().getString("info.log.clear"), FontIcon.of(FontAwesome.TRASH, 14));
        addLogBtn = new JButton(Main.getLanguageManager().getString("info.log.add"), FontIcon.of(FontAwesome.PLUS, 14));

        clearLogBtn.addActionListener(e -> {
            logArea.setText("");
            userLogContent.setLength(0); // 清空用户日志记录
        });

        addLogBtn.addActionListener(e -> {
            String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String newLogEntry = MessageFormat.format(Main.getLanguageManager().getString("info.log.user_action.add_log"), timestamp) + "\n";
            logArea.append(newLogEntry);
            userLogContent.append(newLogEntry); // 保存用户添加的日志
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });

        logControls.add(clearLogBtn);
        logControls.add(new JLabel(""), "grow");
        logControls.add(addLogBtn);

        panel.add(scrollPane, "cell 0 0, grow");
        panel.add(logControls, "cell 0 1, grow");

        return panel;
    }

    /**
     * 初始化日志内容
     */
    private void initializeLogContent() {
        logArea.setText("");
        logArea.append(Main.getLanguageManager().getString("info.log.title") + "\n\n");
        logArea.append(Main.getLanguageManager().getString("info.log.init_app") + "\n");
        logArea.append(Main.getLanguageManager().getString("info.log.font_loaded") + "\n");
        logArea.append(Main.getLanguageManager().getString("info.log.theme_init") + "\n");
        logArea.append(Main.getLanguageManager().getString("info.log.ui_created") + "\n");
        logArea.append(Main.getLanguageManager().getString("info.log.app_started") + "\n\n");

        // 添加用户之前添加的日志
        if (userLogContent.length() > 0) {
            logArea.append(userLogContent.toString());
        }
    }

    private String getLibraryInfo() {
        return MessageFormat.format(
                """
                    {0}
                    
                    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    🎨 {1} {2}
                       {3}
                    
                    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    🔧 {4} {5}
                       {6}
                    
                    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    💻 {7} {8}
                       {9}
                    
                    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    📊 {10} {11}
                       {12}
                    
                    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    📄 {13} {14}
                       {15}
                    
                    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    🎯 {16} {17}
                       {18}
                    
                    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    📋 {19} {20}
                       {21}
                    
                    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    📝 {22} {23}
                       {24}
                    """,
                Main.getLanguageManager().getString("info.library.title"),
                Main.getLanguageManager().getString("info.library.flatlaf.title"), "3.6.1",
                Main.getLanguageManager().getString("info.library.flatlaf.desc"),
                Main.getLanguageManager().getString("info.library.miglayout.title"), "11.4.2",
                Main.getLanguageManager().getString("info.library.miglayout.desc"),
                Main.getLanguageManager().getString("info.library.rsyntaxtextarea.title"), "3.6.0",
                Main.getLanguageManager().getString("info.library.rsyntaxtextarea.desc"),
                Main.getLanguageManager().getString("info.library.jfreechart.title"), "1.5.6",
                Main.getLanguageManager().getString("info.library.jfreechart.desc"),
                Main.getLanguageManager().getString("info.library.pdfbox.title"), "3.0.5",
                Main.getLanguageManager().getString("info.library.pdfbox.desc"),
                Main.getLanguageManager().getString("info.library.ikonli.title"), "12.4.0",
                Main.getLanguageManager().getString("info.library.ikonli.desc"),
                Main.getLanguageManager().getString("info.library.glazedlists.title"), "1.11.0",
                Main.getLanguageManager().getString("info.library.glazedlists.desc"),
                Main.getLanguageManager().getString("info.library.jgoodies_forms.title"), "1.9.0",
                Main.getLanguageManager().getString("info.library.jgoodies_forms.desc")
        );
    }

    private String getSystemInfo() {
        StringBuilder info = new StringBuilder();
        info.append(Main.getLanguageManager().getString("info.system.title")).append("\n\n");
        info.append(Main.getLanguageManager().getString("info.system.java_version")).append(" ").append(System.getProperty("java.version")).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.java_vendor")).append(" ").append(System.getProperty("java.vendor")).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.java_home")).append(" ").append(System.getProperty("java.home")).append("\n\n");
        info.append(Main.getLanguageManager().getString("info.system.os_name")).append(" ").append(System.getProperty("os.name")).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.os_version")).append(" ").append(System.getProperty("os.version")).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.os_arch")).append(" ").append(System.getProperty("os.arch")).append("\n\n");
        info.append(Main.getLanguageManager().getString("info.system.user_name")).append(" ").append(System.getProperty("user.name")).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.user_home")).append(" ").append(System.getProperty("user.home")).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.user_dir")).append(" ").append(System.getProperty("user.dir")).append("\n\n");

        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        info.append(Main.getLanguageManager().getString("info.system.memory.max")).append(" ").append(formatBytes(maxMemory)).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.memory.total")).append(" ").append(formatBytes(totalMemory)).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.memory.free")).append(" ").append(formatBytes(freeMemory)).append("\n");
        info.append(Main.getLanguageManager().getString("info.system.memory.used")).append(" ").append(formatBytes(totalMemory - freeMemory)).append("\n");

        return info.toString();
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    public void updateTheme(boolean isDark) {
        // 信息面板主题更新逻辑，这里暂时不需要特别处理，FlatLaf 会自动处理组件颜色
    }

    /**
     * 刷新所有文本标签
     */
    public void refreshTexts() {
        if (libraryInfoArea != null) {
            libraryInfoArea.setText(getLibraryInfo());
        }
        if (systemInfoArea != null) {
            systemInfoArea.setText(getSystemInfo());
        }
        if (clearLogBtn != null) {
            clearLogBtn.setText(Main.getLanguageManager().getString("info.log.clear"));
        }
        if (addLogBtn != null) {
            addLogBtn.setText(Main.getLanguageManager().getString("info.log.add"));
        }

        // 重新初始化日志内容，保留用户添加的日志
        if (logArea != null) {
            initializeLogContent();
        }
    }
}