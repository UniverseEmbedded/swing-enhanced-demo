package pama1234.dstar.util;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.jthemedetecor.OsThemeDetector;
import pama1234.dstar.Main;
import pama1234.dstar.util.FontManager;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 主题管理器
 * 负责管理应用程序的主题切换和主题感知功能
 */
public class ThemeManager {
    private static ThemeManager instance;
    private boolean darkMode = false;
    private String currentThemeKey = "system"; // 新增：保存当前主题的键，便于判断
    private List<ThemeChangeListener> listeners;
    private OsThemeDetector detector;

    private ThemeManager() {
        listeners = new ArrayList<>();
    }

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    /**
     * 初始化主题系统
     */
    public void initialize() {
        try {
            // 检测系统主题
            detector = OsThemeDetector.getDetector();
            darkMode = detector.isDark();

            // 注册系统主题变化监听器
            detector.registerListener(isDark -> {
                SwingUtilities.invokeLater(() -> {
                    // 只有当当前应用主题设置为"系统"时，才响应系统主题变化
                    if (isCurrentThemeSystem()) {
                        if (isDark != this.darkMode) {
                            this.darkMode = isDark;
                            applyTheme(isDark);
                            notifyListeners(isDark);
                        }
                    }
                });
            });

            // 应用程序启动时，默认应用系统主题
            applySelectedTheme(Main.getLanguageManager().getString("control.theme.system"));
            System.out.println(MessageFormat.format(Main.getLanguageManager().getString("log.theme_system_init_success"),
                    (darkMode ? Main.getLanguageManager().getString("control.theme.dark") : Main.getLanguageManager().getString("control.theme.light"))));
        } catch (Exception e) {
            System.err.println(MessageFormat.format(Main.getLanguageManager().getString("log.theme_system_init_failed"), e.getMessage()));
            // 使用默认主题
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                this.darkMode = false;
                this.currentThemeKey = "light";
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 判断当前是否为系统主题
     */
    private boolean isCurrentThemeSystem() {
        return "system".equals(currentThemeKey);
    }

    /**
     * 应用选中的主题
     * 修复：增强本地化字符串匹配，支持多种语言
     */
    public void applySelectedTheme(String themeName) {
        if (themeName == null) {
            return;
        }

        try {
            // 使用更灵活的主题名称匹配方式
            String themeKey = getThemeKeyFromDisplayName(themeName);

            switch (themeKey) {
                case "system":
                    boolean systemDark = detector.isDark();
                    applyTheme(systemDark);
                    darkMode = systemDark;
                    currentThemeKey = "system";
                    break;
                case "light":
                    applyTheme(false);
                    darkMode = false;
                    currentThemeKey = "light";
                    break;
                case "dark":
                    applyTheme(true);
                    darkMode = true;
                    currentThemeKey = "dark";
                    break;
                case "intellij":
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    darkMode = false;
                    currentThemeKey = "intellij";
                    break;
                case "darcula":
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                    darkMode = true;
                    currentThemeKey = "darcula";
                    break;
                default:
                    // Fallback to current mode if unknown
                    applyTheme(darkMode);
                    break;
            }

            // 应用自定义字体
            FontManager.getInstance().applyDefaultFont();

            // 通知监听器
            notifyListeners(darkMode);
            System.out.println(MessageFormat.format(Main.getLanguageManager().getString("log.theme_switch_success"),
                    themeName, (darkMode ? Main.getLanguageManager().getString("control.theme.dark") : Main.getLanguageManager().getString("control.theme.light"))));
        } catch (Exception e) {
            System.err.println(MessageFormat.format(Main.getLanguageManager().getString("log.theme_switch_failed"), e.getMessage()));
        }
    }

    /**
     * 从显示名称获取主题键
     * 支持多语言主题名称匹配
     */
    private String getThemeKeyFromDisplayName(String displayName) {
        if (displayName == null) {
            return "system";
        }

        // 英文匹配
        if ("System".equals(displayName) || "Light".equals(displayName) || "Dark".equals(displayName) ||
                "IntelliJ".equals(displayName) || "Darcula".equals(displayName)) {
            return displayName.toLowerCase();
        }

        // 中文匹配
        if ("系统".equals(displayName)) {
            return "system";
        } else if ("浅色".equals(displayName)) {
            return "light";
        } else if ("深色".equals(displayName)) {
            return "dark";
        }

        // 本地化字符串匹配
        try {
            if (displayName.equals(Main.getLanguageManager().getString("control.theme.system"))) {
                return "system";
            } else if (displayName.equals(Main.getLanguageManager().getString("control.theme.light"))) {
                return "light";
            } else if (displayName.equals(Main.getLanguageManager().getString("control.theme.dark"))) {
                return "dark";
            } else if (displayName.equals(Main.getLanguageManager().getString("control.theme.intellij"))) {
                return "intellij";
            } else if (displayName.equals(Main.getLanguageManager().getString("control.theme.darcula"))) {
                return "darcula";
            }
        } catch (Exception e) {
            System.err.println("主题名称本地化匹配失败: " + e.getMessage());
        }

        // 默认返回系统主题
        return "system";
    }

    /**
     * 应用主题
     */
    private void applyTheme(boolean isDark) {
        try {
            if (isDark) {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
            }
            // 应用自定义字体
            FontManager.getInstance().applyDefaultFont();
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println(MessageFormat.format(Main.getLanguageManager().getString("log.theme_set_failed"), ex.getMessage()));
        }
    }

    /**
     * 添加主题变化监听器
     */
    public void addThemeChangeListener(ThemeChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * 移除主题变化监听器
     */
    public void removeThemeChangeListener(ThemeChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * 通知所有监听器主题已变化
     */
    private void notifyListeners(boolean isDark) {
        for (ThemeChangeListener listener : listeners) {
            try {
                listener.onThemeChanged(isDark);
            } catch (Exception e) {
                System.err.println(MessageFormat.format(Main.getLanguageManager().getString("log.theme_listener_failed"), e.getMessage()));
            }
        }
    }

    /**
     * 获取当前是否为深色模式
     */
    public boolean isDarkMode() {
        return darkMode;
    }

    /**
     * 获取当前主题键
     */
    public String getCurrentThemeKey() {
        return currentThemeKey;
    }

    /**
     * 主题变化监听器接口
     */
    @FunctionalInterface
    public interface ThemeChangeListener {
        void onThemeChanged(boolean isDarkMode);
    }
}