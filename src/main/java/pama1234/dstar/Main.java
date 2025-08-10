package pama1234.dstar;

import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import pama1234.dstar.ui.*;
import pama1234.dstar.util.*;
import pama1234.dstar.tray.SwingTrayApp; // 引入新的SwingTrayApp

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Swing增强库演示应用程序 - 主入口类
 *
 * 此应用程序演示了多个现代Swing增强库的功能：
 * - FlatLaf: 现代化Look and Feel主题
 * - Ikonli: 图标库支持
 * - MiGLayout: 强大的布局管理器
 * - JGoodies Forms: 专业表单布局
 * - RSyntaxTextArea: 语法高亮代码编辑器
 * - JFreeChart: 专业图表库
 * - Apache PDFBox: PDF文档处理
 * - GlazedLists: 高级列表操作
 * - Thumbnailator: 图片处理
 * - LoboEvolution: HTML渲染引擎
 * - jSystemThemeDetector: 系统主题检测
 *
 * @author pama1234
 * @version 2.0
 */
public class Main {
    private static JFrame mainFrame;
    private static ThemeManager themeManager;
    private static FontManager fontManager;
    private static LanguageManager languageManager;

    // UI组件管理器
    private static ChartPanelManager chartManager;
    private static CodeEditorManager codeEditorManager;
    private static DataTableManager dataTableManager;
    private static FormPanelManager formManager;
    private static InfoPanelManager infoPanelManager;

    // 系统托盘相关 - 使用新的SwingTrayApp
    private static SwingTrayApp swingTrayApp;

    // 控制面板组件，需要全局引用以便更新文本
    private static JPanel controlPanel; // 新增：持有控制面板的引用
    private static JComboBox<String> themeChooser;
    private static JButton pdfBtn;
    private static JButton imageBtn;
    private static JButton aboutBtn;
    private static JComboBox<String> languageChooser;
    private static JCheckBox followSystemLangCheckBox;
    private static JLabel themeLabel;
    private static JLabel languageLabel;

    public static void main(String[] args) {
        // 提前初始化 LanguageManager，确保在任何错误发生时，都可以通过它获取本地化字符串
        languageManager = LanguageManager.getInstance();
        languageManager.initialize();

        try {
            // 初始化应用程序的其余部分
            initializeApplicationRemainder();

            // 在EDT上创建和显示UI
            SwingUtilities.invokeLater(() -> {
                try {
                    createAndShowGUI();
                } catch (Exception e) {
                    handleError(languageManager.getString("dialog.error.ui_init_failed"), e);
                }
            });
        } catch (Exception e) {
            handleError(languageManager.getString("dialog.error.app_startup_failed"), e);
        }
    }

    /**
     * 初始化应用程序的其余部分 (不包含 languageManager 的初始化)
     */
    private static void initializeApplicationRemainder() {
        // 1. 初始化字体管理器
        fontManager = FontManager.getInstance();
        fontManager.loadCustomFont();

        // 2. 初始化主题管理器
        themeManager = ThemeManager.getInstance();
        themeManager.initialize();

        // 3. 设置系统属性
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", languageManager.getString("app.icon.name"));

        // 注册语言变化监听器
        languageManager.addLanguageChangeListener(newLocale -> {
            SwingUtilities.invokeLater(Main::updateAllComponents);
        });
    }

    /**
     * 创建和显示主界面
     */
    private static void createAndShowGUI() {
        // 创建主窗口
        mainFrame = new JFrame(languageManager.getString("app.title"));
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 修改: 不再直接退出
        mainFrame.setSize(1400, 900);

        // 设置应用程序图标
        BufferedImage appIcon = createAppIcon();
        try {
            mainFrame.setIconImage(appIcon);
        } catch (Exception e) {
            System.err.println(MessageFormat.format(languageManager.getString("dialog.info.app_icon_set_failed"), e.getMessage()));
        }

        // 增加WindowListener来处理关闭事件，最小化到托盘
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (SystemTray.isSupported() && swingTrayApp != null && swingTrayApp.isTrayIconAdded()) {
                    mainFrame.setVisible(false); // 隐藏窗口
                    System.out.println(languageManager.getString("dialog.info.system_tray_minimized"));
                } else {
                    System.out.println(languageManager.getString("dialog.info.system_tray_not_supported_exit")); // 更新此处的文本
                    System.exit(0); // 如果不支持托盘或托盘图标未成功添加，则直接退出
                }
            }
        });

        // 新的布局定义
        mainFrame.setLayout(new MigLayout(
                "fill, insets 10",
                "[pref!][grow, push]", // 允许第一列根据其内容的首选尺寸来决定宽度，同时仍可增长
                // 第二列 'grow, push' 确保它获得剩余的所有空间
                "[grow][pref!]"
        ));

        // 初始化UI组件管理器
        initializeManagers();

        // 创建各个功能面板
        createLeftPanel();
        createRightPanel();
        createControlPanel();

        // 确保在所有UI创建完成后再应用字体
        SwingUtilities.invokeLater(() -> {
            // 应用自定义字体
            fontManager.applyToComponent(mainFrame);
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // 显示窗口
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // 启动后台演示任务
        startDemoTasks();

        // 初始化系统托盘 (现在使用 SwingTrayApp)
        initSwingTrayApp(appIcon);
    }

    /**
     * 初始化 SwingTrayApp 功能
     */
    private static void initSwingTrayApp(BufferedImage iconImage) {
        if (SystemTray.isSupported()) {
            swingTrayApp = new SwingTrayApp(iconImage, languageManager.getString("app.icon.name"), Main::showMainFrame);
            System.out.println(languageManager.getString("dialog.info.app_added_to_tray"));
        } else {
            System.out.println(languageManager.getString("dialog.info.system_tray_not_supported"));
        }
    }

    /**
     * 显示主窗口的方法，供托盘菜单调用
     */
    private static void showMainFrame() {
        SwingUtilities.invokeLater(() -> {
            if (mainFrame != null) {
                mainFrame.setVisible(true);
                mainFrame.setState(JFrame.NORMAL); // 确保从最小化状态恢复
                mainFrame.toFront(); // 确保窗口在最前面
            }
        });
    }


    /**
     * 初始化UI组件管理器
     */
    private static void initializeManagers() {
        chartManager = new ChartPanelManager();
        codeEditorManager = new CodeEditorManager();
        dataTableManager = new DataTableManager();
        formManager = new FormPanelManager();
        infoPanelManager = new InfoPanelManager();

        // 设置主题变化监听
        themeManager.addThemeChangeListener(isDark -> {
            SwingUtilities.invokeLater(() -> {
                updateAllComponents();
            });
        });
    }

    /**
     * 创建应用程序图标
     */
    private static BufferedImage createAppIcon() {
        BufferedImage icon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = icon.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制一个简单的图标
        g2.setColor(new Color(66, 165, 245));
        g2.fillOval(8, 8, 48, 48);
        g2.setColor(Color.WHITE);
        if (fontManager.getCustomFont() != null) {
            g2.setFont(fontManager.getCustomFont().deriveFont(Font.BOLD, 24));
        }
        g2.drawString("S", 24, 38);
        g2.dispose();
        return icon;
    }

    /**
     * 创建左侧面板
     */
    private static void createLeftPanel() {
        JPanel leftPanel = new JPanel(new MigLayout("fill, insets 5", "[grow]", ""));
        leftPanel.setBorder(new TitledBorder(languageManager.getString("panel.functions")));

        // 创建选项卡面板
        JTabbedPane tabbedPane = new JTabbedPane();

        // 图表演示选项卡
        tabbedPane.addTab(languageManager.getString("tab.charts"),
                createThemedIcon(FontAwesome.BAR_CHART, 16),
                chartManager.createChartPanel());

        // 代码编辑器选项卡
        tabbedPane.addTab(languageManager.getString("tab.code_editor"),
                createThemedIcon(FontAwesome.CODE, 16),
                codeEditorManager.createCodeEditorPanel());

        // 数据表格选项卡
        tabbedPane.addTab(languageManager.getString("tab.data_table"),
                createThemedIcon(FontAwesome.TABLE, 16),
                dataTableManager.createDataTablePanel());

        // JGoodies Forms演示选项卡
        tabbedPane.addTab(languageManager.getString("tab.form_layout"),
                createThemedIcon(FontAwesome.WPFORMS, 16),
                formManager.createFormPanel());

        leftPanel.add(tabbedPane, "grow");
        mainFrame.add(leftPanel, "cell 0 0, grow");
    }

    /**
     * 创建右侧面板
     */
    private static void createRightPanel() {
        JPanel rightPanel = new JPanel(new MigLayout("fill, insets 5", "[grow]", "[grow]"));
        rightPanel.setBorder(new TitledBorder(languageManager.getString("panel.info")));

        // 创建信息显示区域
        JTabbedPane infoTabs = new JTabbedPane();

        // 库信息选项卡
        infoTabs.addTab(languageManager.getString("tab.library_info"),
                createThemedIcon(FontAwesome.INFO_CIRCLE, 16),
                infoPanelManager.createLibraryInfoPanel());

        // 系统信息选项卡
        infoTabs.addTab(languageManager.getString("tab.system_info"),
                createThemedIcon(FontAwesome.DESKTOP, 16),
                infoPanelManager.createSystemInfoPanel());

        // 日志选项卡
        infoTabs.addTab(languageManager.getString("tab.operation_logs"),
                createThemedIcon(FontAwesome.LIST, 16),
                infoPanelManager.createLogPanel());

        rightPanel.add(infoTabs, "grow");
        // FIX: 将rightPanel添加到第1列（索引1），而不是第2列（索引2）
        mainFrame.add(rightPanel, "cell 1 0, grow");
    }

    /**
     * 创建控制面板
     */
    private static void createControlPanel() {
        // 移除旧的控制面板（如果存在）
        if (controlPanel != null) {
            mainFrame.remove(controlPanel);
        }

        controlPanel = new JPanel(new MigLayout("fill, insets 5", "[][][][][grow][][][][][][grow][]", ""));
        controlPanel.setBorder(BorderFactory.createTitledBorder(languageManager.getString("panel.controls")));
        controlPanel.setMinimumSize(new Dimension(0, 50));

        // 主题选择
        themeLabel = new JLabel(languageManager.getString("control.theme.label"));
        String[] themes = {
                languageManager.getString("control.theme.system"),
                languageManager.getString("control.theme.light"),
                languageManager.getString("control.theme.dark"),
                languageManager.getString("control.theme.intellij"),
                languageManager.getString("control.theme.darcula")
        };
        themeChooser = new JComboBox<>(themes);

        // 根据当前主题设置初始选项
        updateThemeChooserSelection();

        // 功能按钮 - 重新创建以确保文本更新
        pdfBtn = new JButton(languageManager.getString("control.button.generate_pdf"),
                createThemedIcon(FontAwesome.FILE_PDF_O, 14));
        imageBtn = new JButton(languageManager.getString("control.button.process_image"),
                createThemedIcon(FontAwesome.IMAGE, 14));
        aboutBtn = new JButton(languageManager.getString("control.button.about"),
                createThemedIcon(FontAwesome.INFO, 14));

        // 语言选择 - 重新创建
        languageLabel = new JLabel(languageManager.getString("control.language.label"));
        languageChooser = new JComboBox<>();

        // 重新构建语言选项
        Map<String, Locale> langMap = new LinkedHashMap<>();
        langMap.put(languageManager.getString("control.language.system"), null);
        langMap.put(languageManager.getString("control.language.english"), Locale.ENGLISH);
        langMap.put(languageManager.getString("control.language.chinese"), Locale.SIMPLIFIED_CHINESE);

        for (String langName : langMap.keySet()) {
            languageChooser.addItem(langName);
        }

        // 重新创建复选框
        followSystemLangCheckBox = new JCheckBox(languageManager.getString("control.language.follow_system"));
        followSystemLangCheckBox.setSelected(languageManager.isFollowSystemLocale());
        updateLanguageChooserSelection(languageManager.getCurrentLocale());
        languageChooser.setEnabled(!languageManager.isFollowSystemLocale());

        // 重新绑定事件处理器
        bindControlPanelEvents(langMap);

        // 布局组件
        controlPanel.add(themeLabel);
        controlPanel.add(themeChooser);
        controlPanel.add(pdfBtn);
        controlPanel.add(imageBtn);
        controlPanel.add(aboutBtn);
        controlPanel.add(new JLabel(""), "grow"); // 弹簧
        controlPanel.add(languageLabel);
        controlPanel.add(languageChooser);
        controlPanel.add(followSystemLangCheckBox);

        // 添加到主窗口
        mainFrame.add(controlPanel, "cell 0 1 2 1, grow");

        // 强制重新验证和重绘
        controlPanel.revalidate();
        controlPanel.repaint();
    }

    private static void bindControlPanelEvents(Map<String, Locale> langMap) {
        // 事件处理 - 主题
        themeChooser.addActionListener(e -> {
            String selectedTheme = (String) themeChooser.getSelectedItem();
            if (selectedTheme != null) {
                themeManager.applySelectedTheme(selectedTheme);
            }
        });

        // 事件处理 - 功能按钮
        pdfBtn.addActionListener(e -> PDFGenerator.generatePDF(mainFrame));
        imageBtn.addActionListener(e -> ImageProcessor.processImage(mainFrame));
        aboutBtn.addActionListener(e -> showAboutDialog());

        // 事件处理 - 语言
        languageChooser.addActionListener(e -> {
            if (!languageManager.isFollowSystemLocale()) {
                String selectedLangName = (String) languageChooser.getSelectedItem();
                if (selectedLangName != null) {
                    Locale selectedLocale = langMap.get(selectedLangName);
                    if (selectedLocale != null) {
                        languageManager.setLocale(selectedLocale);
                    }
                }
            }
        });

        followSystemLangCheckBox.addActionListener(e -> {
            boolean follow = followSystemLangCheckBox.isSelected();
            languageManager.setFollowSystemLocale(follow);
            languageChooser.setEnabled(!follow);
            if (follow) {
                updateLanguageChooserSelection(Locale.getDefault());
            } else {
                updateLanguageChooserSelection(languageManager.getCurrentLocale());
            }
        });
    }

    /**
     * 更新主题选择器当前选中项
     */
    private static void updateThemeChooserSelection() {
        if (themeChooser != null) {
            String currentThemeName = null; // 初始化为null，添加空值检查

            try {
                if (themeManager.isDarkMode()) {
                    currentThemeName = languageManager.getString("control.theme.dark");
                } else {
                    currentThemeName = languageManager.getString("control.theme.light");
                }

                // 尝试查找精确匹配的Laf名称，而不是只依赖Dark/Light
                String lafName = UIManager.getLookAndFeel().getName();
                if (lafName != null) {
                    if (lafName.contains("IntelliJ")) {
                        currentThemeName = languageManager.getString("control.theme.intellij");
                    } else if (lafName.contains("Darcula")) {
                        currentThemeName = languageManager.getString("control.theme.darcula");
                    } else if (themeManager.isDarkMode() && lafName.contains("Mac Dark")) {
                        currentThemeName = languageManager.getString("control.theme.dark");
                    } else if (!themeManager.isDarkMode() && lafName.contains("Mac Light")) {
                        currentThemeName = languageManager.getString("control.theme.light");
                    }
                }

                // 添加空值检查
                if (currentThemeName != null) {
                    themeChooser.setSelectedItem(currentThemeName);
                }
            } catch (Exception e) {
                System.err.println("更新主题选择器失败: " + e.getMessage());
            }
        }
    }

    /**
     * 更新语言选择器当前选中项
     * @param currentLocale 当前的Locale，用于匹配下拉框的显示文本
     */
    private static void updateLanguageChooserSelection(Locale currentLocale) {
        if (languageChooser != null && currentLocale != null) {
            try {
                String targetDisplayName = "";
                if (languageManager.isFollowSystemLocale()) {
                    targetDisplayName = languageManager.getString("control.language.system");
                } else if (Locale.ENGLISH.equals(currentLocale)) {
                    targetDisplayName = languageManager.getString("control.language.english");
                } else if (Locale.SIMPLIFIED_CHINESE.equals(currentLocale)) {
                    targetDisplayName = languageManager.getString("control.language.chinese");
                }

                // 遍历下拉框中的项，找到匹配的并设置选中
                for (int i = 0; i < languageChooser.getItemCount(); i++) {
                    String item = languageChooser.getItemAt(i);
                    if (item != null && item.equals(targetDisplayName)) {
                        languageChooser.setSelectedIndex(i);
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("更新语言选择器失败: " + e.getMessage());
            }
        }
    }

    /**
     * 创建带主题的图标
     */
    private static FontIcon createThemedIcon(FontAwesome icon, int size) {
        FontIcon fontIcon = FontIcon.of(icon, size);
        // 根据主题设置图标颜色
        if (themeManager.isDarkMode()) {
            fontIcon.setIconColor(Color.WHITE);
        } else {
            fontIcon.setIconColor(Color.BLACK);
        }
        return fontIcon;
    }

    /**
     * 显示关于对话框
     */
    private static void showAboutDialog() {
        String aboutText = createAboutContent();
        DialogUtils.showHtmlDialog(mainFrame, aboutText, languageManager.getString("about.dialog.title"),
                createThemedIcon(FontAwesome.INFO_CIRCLE, 48));
    }

    /**
     * 创建关于内容
     */
    private static String createAboutContent() {
        return "<html><body style='width: 400px; font-family: Maple Mono Normal NF CN;'>" +
                "<h2>" + languageManager.getString("about.title") + "</h2>" +
                "<p>" + languageManager.getString("about.description") + "</p>" +
                "<h3>" + languageManager.getString("about.features") + "</h3>" +
                "<ul>" +
                "<li>" + languageManager.getString("about.feature.modern_themes") + "</li>" +
                "<li>" + languageManager.getString("about.feature.syntax_highlighting") + "</li>" +
                "<li>" + languageManager.getString("about.feature.chart_visualization") + "</li>" +
                "<li>" + languageManager.getString("about.feature.layout_management") + "</li>" +
                "<li>" + languageManager.getString("about.feature.advanced_list") + "</li>" +
                "<li>" + languageManager.getString("about.feature.pdf_processing") + "</li>" +
                "<li>" + languageManager.getString("about.feature.image_processing") + "</li>" +
                "<li>" + languageManager.getString("about.feature.vector_icons") + "</li>" +
                "</ul>" +
                "<p><b>" + languageManager.getString("about.developer") + "</b> pama1234<br>" +
                "<b>" + languageManager.getString("about.tech_stack") + "</b> Java Swing + " + languageManager.getString("about.tech_stack_libs") + "</p>" +
                "</body></html>";
    }

    /**
     * 启动演示任务
     */
    private static void startDemoTasks() {
        // 启动后台任务
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 等待2秒
                System.out.println(languageManager.getString("demo.task.initialized"));
                // 通知各管理器可以开始后台任务
                if (chartManager != null) {
                    chartManager.startBackgroundTasks();
                }
            } catch (Exception e) {
                System.err.println(languageManager.getString("demo.task.failed") + ": " + e.getMessage());
            }
        }, "DemoTask").start();
    }

    /**
     * 更新所有UI组件，包括主题和语言
     */
    private static void updateAllComponents() {
        try {
            // 首先，更新Look and Feel
            SwingUtilities.updateComponentTreeUI(mainFrame);

            // 更新主窗口标题
            mainFrame.setTitle(languageManager.getString("app.title"));

            // 应用字体
            fontManager.applyToComponent(mainFrame);

            // 重新创建控制面板，确保所有语言文本被刷新
            createControlPanel();

            // 通知各管理器更新主题和语言相关的设置
            boolean isDark = themeManager.isDarkMode();
            if (chartManager != null) {
                chartManager.updateTheme(isDark);
                chartManager.refreshTexts();
            }
            if (codeEditorManager != null) {
                codeEditorManager.updateTheme(isDark);
                codeEditorManager.refreshTexts();
            }
            if (dataTableManager != null) {
                dataTableManager.updateTheme(isDark);
                dataTableManager.refreshTexts();
            }
            if (formManager != null) {
                formManager.updateTheme(isDark);
                formManager.refreshTexts();
            }
            if (infoPanelManager != null) {
                infoPanelManager.updateTheme(isDark);
                infoPanelManager.refreshTexts();
            }
            // 更新托盘图标名称 (如果需要的话，但通常托盘图标的tooltip不会频繁变动)
            if (swingTrayApp != null) {
                swingTrayApp.setToolTip(languageManager.getString("app.icon.name"));
                swingTrayApp.updateTrayMenuTexts(); // 更新托盘菜单文本
            }


            // 更新图标颜色
            updateIconColors();

            // 刷新所有容器的边框标题
            updateTitledBordersRecursively(mainFrame);

            // 刷新选项卡标题
            updateTabbedPaneTitlesRecursively(mainFrame);

            // 强制重绘所有组件 - 这里需要增强
            SwingUtilities.invokeLater(() -> {
                // 递归更新所有容器中的文本组件
                updateAllTextComponentsRecursively(mainFrame);

                mainFrame.invalidate();
                mainFrame.validate();
                mainFrame.repaint();
            });
        } catch (Exception e) {
            System.err.println("更新UI组件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void updateAllTextComponentsRecursively(Container container) {
        if (container == null) return;

        for (Component component : container.getComponents()) {
            // 更新JLabel
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                String text = label.getText();
                if (text != null && !text.isEmpty()) {
                    // 这里可以根据需要添加特定的文本更新逻辑
                    // 但通常UI管理器已经通过refreshTexts()方法处理了
                }
            }
            // 更新JButton
            else if (component instanceof JButton) {
                JButton button = (JButton) component;
                String text = button.getText();
                if (text != null && !text.isEmpty()) {
                    // 按钮文本通常由各自的Manager的refreshTexts()方法处理
                }
            }

            // 递归处理子容器
            if (component instanceof Container) {
                updateAllTextComponentsRecursively((Container) component);
            }
        }
    }

    /**
     * 递归更新所有 TitledBorder 的标题文本
     * @param container
     */
    private static void updateTitledBordersRecursively(Container container) {
        if (container == null) return;

        if (container instanceof JPanel) {
            JPanel panel = (JPanel) container;
            if (panel.getBorder() instanceof TitledBorder) {
                TitledBorder border = (TitledBorder) panel.getBorder();
                String currentBorderTitle = border.getTitle();

                if (currentBorderTitle != null) {
                    // 使用包含判断而不是严格相等，因为本地化字符串可能已经改变
                    if (currentBorderTitle.contains(languageManager.getString("panel.functions").substring(0, Math.min(languageManager.getString("panel.functions").length(), 2))) ||
                            currentBorderTitle.contains("Function")) {
                        border.setTitle(languageManager.getString("panel.functions"));
                    } else if (currentBorderTitle.contains(languageManager.getString("panel.info").substring(0, Math.min(languageManager.getString("panel.info").length(), 2))) ||
                            currentBorderTitle.contains("Information")) {
                        border.setTitle(languageManager.getString("panel.info"));
                    } else if (currentBorderTitle.contains(languageManager.getString("panel.controls").substring(0, Math.min(languageManager.getString("panel.controls").length(), 2))) ||
                            currentBorderTitle.contains("Control")) {
                        border.setTitle(languageManager.getString("panel.controls"));
                    } else if (currentBorderTitle.contains("JGoodies") || currentBorderTitle.contains(languageManager.getString("form.title").substring(0, Math.min(languageManager.getString("form.title").length(), 2)))) {
                        border.setTitle(languageManager.getString("form.title"));
                    }
                }
            }
        }

        for (Component component : container.getComponents()) {
            if (component instanceof Container) {
                updateTitledBordersRecursively((Container) component);
            }
        }
    }

    /**
     * 递归更新所有 JTabbedPane 的标签文本
     * @param container
     */
    private static void updateTabbedPaneTitlesRecursively(Container container) {
        if (container == null) return;

        if (container instanceof JTabbedPane) {
            JTabbedPane tabbedPane = (JTabbedPane) container;

            // 检查是否是左侧面板的选项卡（4个选项卡）
            if (tabbedPane.getTabCount() == 4) {
                String[] tabKeys = {
                        "tab.charts", "tab.code_editor", "tab.data_table", "tab.form_layout"
                };
                FontAwesome[] icons = {
                        FontAwesome.BAR_CHART, FontAwesome.CODE, FontAwesome.TABLE, FontAwesome.WPFORMS
                };

                for (int i = 0; i < Math.min(tabbedPane.getTabCount(), tabKeys.length); i++) {
                    tabbedPane.setTitleAt(i, languageManager.getString(tabKeys[i]));
                    tabbedPane.setIconAt(i, createThemedIcon(icons[i], 16));
                }
            }
            // 检查是否是右侧面板的选项卡（3个选项卡）
            else if (tabbedPane.getTabCount() == 3) {
                String[] tabKeys = {
                        "tab.library_info", "tab.system_info", "tab.operation_logs"
                };
                FontAwesome[] icons = {
                        FontAwesome.INFO_CIRCLE, FontAwesome.DESKTOP, FontAwesome.LIST
                };

                for (int i = 0; i < Math.min(tabbedPane.getTabCount(), tabKeys.length); i++) {
                    tabbedPane.setTitleAt(i, languageManager.getString(tabKeys[i]));
                    tabbedPane.setIconAt(i, createThemedIcon(icons[i], 16));
                }
            }
        }

        // 递归处理子容器
        for (Component component : container.getComponents()) {
            if (component instanceof Container) {
                updateTabbedPaneTitlesRecursively((Container) component);
            }
        }
    }

    /**
     * 更新图标颜色
     */
    private static void updateIconColors() {
        Color iconColor = themeManager.isDarkMode() ? Color.WHITE : Color.BLACK;
        updateIconColorsRecursively(mainFrame, iconColor);

        // 更新控制面板按钮的图标
        if (pdfBtn != null) pdfBtn.setIcon(createThemedIcon(FontAwesome.FILE_PDF_O, 14));
        if (imageBtn != null) imageBtn.setIcon(createThemedIcon(FontAwesome.IMAGE, 14));
        if (aboutBtn != null) aboutBtn.setIcon(createThemedIcon(FontAwesome.INFO, 14));
    }

    /**
     * 递归更新图标颜色
     */
    private static void updateIconColorsRecursively(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getIcon() instanceof FontIcon) {
                    ((FontIcon) button.getIcon()).setIconColor(color);
                }
            } else if (component instanceof JTabbedPane) {
                JTabbedPane tabbedPane = (JTabbedPane) component;
                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    Icon icon = tabbedPane.getIconAt(i);
                    if (icon instanceof FontIcon) {
                        ((FontIcon) icon).setIconColor(color);
                    }
                }
            }
            if (component instanceof Container) {
                updateIconColorsRecursively((Container) component, color);
            }
        }
    }

    /**
     * 错误处理
     */
    private static void handleError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
        SwingUtilities.invokeLater(() -> {
            DialogUtils.showErrorDialog(
                    mainFrame,
                    message + "\n" + languageManager.getString("dialog.error.details") + ": " + e.getMessage(),
                    languageManager.getString("dialog.error.title")
            );
        });
    }

    // Getter方法供其他类使用
    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static ThemeManager getThemeManager() {
        return themeManager;
    }

    public static FontManager getFontManager() {
        return fontManager;
    }

    public static LanguageManager getLanguageManager() {
        return languageManager;
    }
}