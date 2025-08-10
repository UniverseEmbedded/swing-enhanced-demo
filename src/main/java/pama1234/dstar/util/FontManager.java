package pama1234.dstar.util;

import pama1234.dstar.Main; // 引入Main以便获取LanguageManager

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * 字体管理器
 * 负责管理应用程序的字体加载和应用
 */
public class FontManager {
    private static FontManager instance;
    private Font customFont;

    private FontManager() {
    }

    public static FontManager getInstance() {
        if (instance == null) {
            instance = new FontManager();
        }
        return instance;
    }

    /**
     * 加载自定义字体
     */
    public void loadCustomFont() {
        try (InputStream is = getClass().getResourceAsStream("/fonts/MapleMonoNormal-NF-CN-Regular.ttf")) {
            if (is == null) {
                System.out.println(Main.getLanguageManager().getString("log.font_not_found"));
                customFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
            } else {
                customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(14f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                System.out.println(MessageFormat.format(Main.getLanguageManager().getString("log.font_loaded_success"), customFont.getName()));
            }
        } catch (Exception e) {
            System.err.println(MessageFormat.format(Main.getLanguageManager().getString("log.font_load_failed"), e.getMessage()));
            customFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        }

        // 应用默认字体
        applyDefaultFont();
    }

    /**
     * 应用默认字体到UIManager
     */
    public void applyDefaultFont() {
        if (customFont != null) {
            setDefaultFont(customFont);
        }
    }

    /**
     * 设置默认字体
     */
    private void setDefaultFont(Font font) {
        String[] keys = {
                "Label.font", "Button.font", "TextField.font", "TextArea.font", "ComboBox.font",
                "List.font", "Table.font", "TableHeader.font", "Menu.font", "MenuItem.font",
                "PopupMenu.font", "Panel.font", "TabbedPane.font", "CheckBox.font",
                "RadioButton.font", "TitledBorder.font", "ToolTip.font", "Tree.font",
                "ScrollPane.font", "Viewport.font", "ProgressBar.font", "Slider.font",
                "Spinner.font", "FormattedTextField.font", "PasswordField.font", "TextPane.font",
                "EditorPane.font", "OptionPane.font", "OptionPane.messageFont", "OptionPane.buttonFont"
        };

        for (String key : keys) {
            UIManager.put(key, font);
        }
    }

    /**
     * 递归应用字体到所有组件
     */
    public void applyToComponent(Container container) {
        if (container == null || customFont == null) return;

        applyFontToAllComponents(container, customFont);
    }

    /**
     * 递归应用字体到所有组件
     */
    private void applyFontToAllComponents(Container container, Font font) {
        if (container == null || font == null) return;

        for (Component component : container.getComponents()) {
            // 跳过某些特殊组件，避免覆盖它们内部可能设置的字体或导致渲染问题
            // 例如RSyntaxTextArea的内部文本区域，我们希望它由Theme控制，但在CodeEditorManager中会强制应用字体
            if (!(component instanceof JRootPane) && !(component instanceof JScrollPane) &&
                    !(component instanceof JSplitPane) && !(component instanceof JTabbedPane) &&
                    !(component instanceof JMenuBar) && !(component instanceof JToolBar) &&
                    !(component instanceof JTextComponent)) { // 通常不直接对JTextComponent应用，因为它们可能内部有更细粒度的字体控制
                component.setFont(font);
            }

            // 特殊处理JTextComponent，确保它们也应用到主字体，除非有特殊渲染器
            if (component instanceof JTextComponent && !(component instanceof org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)) {
                component.setFont(font);
            }


            if (component instanceof Container) {
                applyFontToAllComponents((Container) component, font);
            }
        }
    }

    /**
     * 获取自定义字体
     */
    public Font getCustomFont() {
        return customFont;
    }

    /**
     * 获取指定大小的字体
     */
    public Font getFont(float size) {
        if (customFont != null) {
            return customFont.deriveFont(size);
        }
        return new Font(Font.SANS_SERIF, Font.PLAIN, (int) size);
    }

    /**
     * 获取指定样式和大小的字体
     */
    public Font getFont(int style, float size) {
        if (customFont != null) {
            return customFont.deriveFont(style, size);
        }
        return new Font(Font.SANS_SERIF, style, (int) size);
    }
}