（资料丢失）

### 🎨 主题和定制

#### 1. 自定义主题
```java
public class CustomThemeDemo {
    public static void createCustomThemeDemo() {
        JFrame frame = new JFrame("自定义主题演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        CControl control = new CControl(frame);
        frame.add(control.getContentArea());
        
        // 应用自定义主题
        applyCustomTheme(control);
        
        // 添加测试面板
        for (int i = 1; i <= 4; i++) {
            DefaultSingleCDockable dockable = createStyledDockable("面板" + i, i);
            control.addDockable(dockable);
            dockable.setVisible(true);
        }
        
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void applyCustomTheme(CControl control) {
        // 创建自定义主题
        ColorScheme customScheme = new ColorScheme() {
            @Override
            public void updateUI() {
                // 自定义颜色更新逻辑
            }
            
            public Color getTitleForeground() {
                return Color.WHITE;
            }
            
            public Color getTitleBackground() {
                return new Color(0x2C3E50);
            }
            
            public Color getTabForeground() {
                return Color.BLACK;
            }
            
            public Color getTabBackground() {
                return new Color(0xECF0F1);
            }
        };
        
        // 设置主题属性
        control.putProperty(StackDockStation.TAB_PLACEMENT, TabPlacement.TOP_OF_DOCKABLE);
        control.putProperty(EclipseTheme.TAB_PAINTER, 
                          new CustomTabPainter(customScheme));
    }
    
    private static DefaultSingleCDockable createStyledDockable(String title, int index) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 创建工具栏
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(new JButton("新建"));
        toolBar.add(new JButton("打开"));
        toolBar.add(new JButton("保存"));
        panel.add(toolBar, BorderLayout.NORTH);
        
        // 创建内容区域
        JTextArea content = new JTextArea();
        content.setText("这是" + title + "的内容区域。\n\n" +
                       "您可以在这里添加任何内容，\n" +
                       "包括文本、组件或其他UI元素。");
        content.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(new JScrollPane(content), BorderLayout.CENTER);
        
        return new DefaultSingleCDockable(
            "styled_panel_" + index,
            title,
            panel
        );
    }
    
    // 自定义标签页绘制器
    private static class CustomTabPainter extends BaseTabPainter {
        private ColorScheme colorScheme;
        
        public CustomTabPainter(ColorScheme colorScheme) {
            this.colorScheme = colorScheme;
        }
        
        @Override
        public void paintBackground(Graphics g, JComponent component, 
                                  Dockable dockable, Bounds bounds, 
                                  boolean selected, boolean mouseOver, 
                                  boolean focused) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                               RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color backgroundColor;
            if (selected) {
                backgroundColor = colorScheme.getTitleBackground();
            } else if (mouseOver) {
                backgroundColor = colorScheme.getTitleBackground().brighter();
            } else {
                backgroundColor = colorScheme.getTabBackground();
            }
            
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(bounds.getX(), bounds.getY(), 
                            bounds.getWidth(), bounds.getHeight(), 8, 8);
        }
        
        @Override
        public void paintForeground(Graphics g, JComponent component, 
                                  Dockable dockable, Bounds bounds, 
                                  boolean selected, boolean mouseOver, 
                                  boolean focused) {
            g.setColor(selected ? colorScheme.getTitleForeground() 
                               : colorScheme.getTabForeground());
            super.paintForeground(g, component, dockable, bounds, 
                                selected, mouseOver, focused);
        }
    }
}
```

---

## ModernDockingUI - 现代停靠界面

**版本**: 0.8.2  
**官网**: https://github.com/andrewauclair/ModernDocking  
**GitHub**: https://github.com/andrewauclair/ModernDocking

### 🎯 核心特性

ModernDockingUI是一个为Java Swing设计的现代停靠框架，专注于简单性和现代化的外观。

#### 主要优势
- **🎨 现代化设计**: 现代化的UI设计风格
- **🔧 简单API**: 简化的API，易于学习和使用
- **⚡ 高性能**: 优化的性能和内存使用
- **📱 响应式**: 支持现代化的响应式布局
- **🎯 专注**: 专注于停靠功能，避免过度复杂

#### 核心组件
- **ModernDocking**: 主要的停靠管理器
- **Dockable**: 可停靠组件接口
- **DockingRegion**: 停靠区域定义
- **DockingLayout**: 布局管理

### 🔧 基础使用

#### 1. 简单的现代停靠演示
```java
import io.github.andrewauclair.ModernDockingUI.*;

public class ModernDockingDemo {
    public static void createModernDemo() {
        JFrame frame = new JFrame("ModernDockingUI 演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 初始化现代停靠框架
        ModernDocking docking = new ModernDocking(frame);
        
        // 创建可停靠面板
        JPanel contentPanel1 = createContentPanel("面板 1", Color.LIGHT_GRAY);
        JPanel contentPanel2 = createContentPanel("面板 2", Color.CYAN);
        JPanel contentPanel3 = createContentPanel("面板 3", Color.PINK);
        
        // 创建停靠组件
        Dockable dockable1 = new Dockable("panel1", "面板 1", contentPanel1);
        Dockable dockable2 = new Dockable("panel2", "面板 2", contentPanel2);
        Dockable dockable3 = new Dockable("panel3", "面板 3", contentPanel3);
        
        // 添加到停靠框架
        docking.dock(dockable1, DockingRegion.CENTER);
        docking.dock(dockable2, DockingRegion.WEST);
        docking.dock(dockable3, DockingRegion.SOUTH);
        
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static JPanel createContentPanel(String title, Color backgroundColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JTextArea textArea = new JTextArea();
        textArea.setText("这是" + title + "的内容区域。\n\n" +
                        "您可以拖拽标题栏来重新排列面板，\n" +
                        "或者使用右键菜单进行更多操作。");
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        return panel;
    }
}
```

#### 2. 高级布局管理
```java
public class AdvancedModernDockingDemo extends JFrame {
    private ModernDocking docking;
    private Map<String, Dockable> dockables;
    
    public AdvancedModernDockingDemo() {
        dockables = new HashMap<>();
        setupUI();
        createDockables();
        setupLayout();
    }
    
    private void setupUI() {
        setTitle("高级 ModernDockingUI 演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 初始化停靠框架
        docking = new ModernDocking(this);
        
        // 设置现代化主题
        docking.setTheme(ModernDockingTheme.DARK);
        
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    private void createDockables() {
        // 文件浏览器
        dockables.put("explorer", createFileExplorerDockable());
        
        // 代码编辑器
        dockables.put("editor", createCodeEditorDockable());
        
        // 输出窗口
        dockables.put("output", createOutputDockable());
        
        // 属性面板
        dockables.put("properties", createPropertiesDockable());
        
        // 工具箱
        dockables.put("toolbox", createToolboxDockable());
    }
    
    private void setupLayout() {
        // 主要布局
        docking.dock(dockables.get("editor"), DockingRegion.CENTER);
        docking.dock(dockables.get("explorer"), DockingRegion.WEST);
        docking.dock(dockables.get("output"), DockingRegion.SOUTH);
        docking.dock(dockables.get("properties"), DockingRegion.EAST);
        
        // 在东边区域创建标签页
        docking.dock(dockables.get("toolbox"), dockables.get("properties"), DockingRegion.CENTER);
        
        // 设置初始大小比例
        docking.setSplitProportion(DockingRegion.WEST, 0.2);
        docking.setSplitProportion(DockingRegion.EAST, 0.2);
        docking.setSplitProportion(DockingRegion.SOUTH, 0.25);
    }
    
    private Dockable createFileExplorerDockable() {
        // 创建文件树
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("项目");
        DefaultMutableTreeNode src = new DefaultMutableTreeNode("src");
        DefaultMutableTreeNode resources = new DefaultMutableTreeNode("resources");
        
        src.add(new DefaultMutableTreeNode("Main.java"));
        src.add(new DefaultMutableTreeNode("Utils.java"));
        src.add(new DefaultMutableTreeNode("Config.java"));
        
        resources.add(new DefaultMutableTreeNode("config.properties"));
        resources.add(new DefaultMutableTreeNode("icon.png"));
        
        root.add(src);
        root.add(resources);
        
        JTree tree = new JTree(root);
        tree.setRootVisible(true);
        
        // 展开所有节点
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tree), BorderLayout.CENTER);
        
        return new Dockable("explorer", "文件浏览器", panel);
    }
    
    private Dockable createCodeEditorDockable() {
        JTextArea editor = new JTextArea();
        editor.setFont(new Font("Consolas", Font.PLAIN, 14));
        editor.setText("""
            public class ModernDockingExample {
                public static void main(String[] args) {
                    SwingUtilities.invokeLater(() -> {
                        new AdvancedModernDockingDemo().setVisible(true);
                    });
                }
                
                private void setupDocking() {
                    // 现代化停靠框架设置
                    ModernDocking docking = new ModernDocking(this);
                    docking.setTheme(ModernDockingTheme.DARK);
                }
            }
            """);
        
        JPanel editorPanel = new JPanel(new BorderLayout());
        
        // 工具栏
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(new JButton("新建"));
        toolBar.add(new JButton("打开"));
        toolBar.add(new JButton("保存"));
        toolBar.addSeparator();
        toolBar.add(new JButton("运行"));
        toolBar.add(new JButton("调试"));
        
        editorPanel.add(toolBar, BorderLayout.NORTH);
        editorPanel.add(new JScrollPane(editor), BorderLayout.CENTER);
        
        return new Dockable("editor", "代码编辑器", editorPanel);
    }
    
    private Dockable createOutputDockable() {
        JTextArea output = new JTextArea();
        output.setBackground(Color.BLACK);
        output.setForeground(Color.WHITE);
        output.setFont(new Font("Consolas", Font.PLAIN, 12));
        output.setEditable(false);
        output.setText("""
            构建开始...
            编译 Main.java
            编译 Utils.java
            编译 Config.java
            构建成功
            
            运行 Main.java
            Hello, ModernDockingUI!
            应用程序已启动
            """);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(output), BorderLayout.CENTER);
        
        return new Dockable("output", "输出", panel);
    }
    
    private Dockable createPropertiesDockable() {
        Object[][] data = {
            {"名称", "Main.java"},
            {"大小", "2.3 KB"},
            {"修改时间", "2024-08-09 16:45:00"},
            {"创建时间", "2024-08-09 10:30:00"},
            {"编码", "UTF-8"},
            {"行数", "25"},
            {"字符数", "856"}
        };
        
        String[] columns = {"属性", "值"};
        
        JTable table = new JTable(data, columns);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        return new Dockable("properties", "属性", panel);
    }
    
    private Dockable createToolboxDockable() {
        JPanel toolbox = new JPanel(new GridLayout(0, 2, 5, 5));
        toolbox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 添加工具按钮
        String[] tools = {
            "选择", "移动", "矩形", "圆形", 
            "直线", "文本", "画笔", "橡皮擦"
        };
        
        for (String tool : tools) {
            JButton button = new JButton(tool);
            button.setPreferredSize(new Dimension(80, 40));
            toolbox.add(button);
        }
        
        JScrollPane scrollPane = new JScrollPane(toolbox);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        return new Dockable("toolbox", "工具箱", scrollPane);
    }
}
```

#### 3. 动态停靠管理
```java
public class DynamicDockingDemo extends JFrame {
    private ModernDocking docking;
    private List<Dockable> availableDockables;
    private JMenuBar menuBar;
    
    public DynamicDockingDemo() {
        availableDockables = new ArrayList<>();
        setupUI();
        createAvailableDockables();
        createMenus();
    }
    
    private void setupUI() {
        setTitle("动态停靠管理演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        docking = new ModernDocking(this);
        docking.setTheme(ModernDockingTheme.LIGHT);
        
        setSize(900, 650);
        setLocationRelativeTo(null);
    }
    
    private void createAvailableDockables() {
        // 创建多个可用的停靠面板
        for (int i = 1; i <= 8; i++) {
            availableDockables.add(createDynamicDockable("面板" + i, i));
        }
        
        // 默认显示前3个
        docking.dock(availableDockables.get(0), DockingRegion.CENTER);
        docking.dock(availableDockables.get(1), DockingRegion.WEST);
        docking.dock(availableDockables.get(2), DockingRegion.SOUTH);
    }
    
    private void createMenus() {
        menuBar = new JMenuBar();
        
        // 视图菜单
        JMenu viewMenu = new JMenu("视图");
        
        // 为每个可用面板创建菜单项
        for (Dockable dockable : availableDockables) {
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(dockable.getTitle());
            menuItem.setSelected(docking.isDocked(dockable));
            
            menuItem.addActionListener(e -> {
                if (menuItem.isSelected()) {
                    // 显示面板
                    if (!docking.isDocked(dockable)) {
                        docking.dock(dockable, DockingRegion.CENTER);
                    }
                } else {
                    // 隐藏面板
                    docking.undock(dockable);
                }
            });
            
            viewMenu.add(menuItem);
        }
        
        viewMenu.addSeparator();
        
        // 布局管理
        JMenuItem saveLayoutItem = new JMenuItem("保存布局");
        saveLayoutItem.addActionListener(e -> saveLayout());
        
        JMenuItem loadLayoutItem = new JMenuItem("加载布局");
        loadLayoutItem.addActionListener(e -> loadLayout());
        
        JMenuItem resetLayoutItem = new JMenuItem("重置布局");
        resetLayoutItem.addActionListener(e -> resetLayout());
        
        viewMenu.add(saveLayoutItem);
        viewMenu.add(loadLayoutItem);
        viewMenu.add(resetLayoutItem);
        
        // 主题菜单
        JMenu themeMenu = new JMenu("主题");
        
        JRadioButtonMenuItem lightTheme = new JRadioButtonMenuItem("浅色主题", true);
        JRadioButtonMenuItem darkTheme = new JRadioButtonMenuItem("深色主题");
        
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightTheme);
        themeGroup.add(darkTheme);
        
        lightTheme.addActionListener(e -> docking.setTheme(ModernDockingTheme.LIGHT));
        darkTheme.addActionListener(e -> docking.setTheme(ModernDockingTheme.DARK));
        
        themeMenu.add(lightTheme);
        themeMenu.add(darkTheme);
        
        menuBar.add(viewMenu);
        menuBar.add(themeMenu);
        
        setJMenuBar(menuBar);
    }
    
    private Dockable createDynamicDockable(String title, int index) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 标题区域
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // 内容区域
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 添加一些示例控件
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("标签:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(new JTextField(15), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(new JLabel("选项:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(new JComboBox<>(new String[]{"选项1", "选项2", "选项3"}), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JCheckBox("启用"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(new JButton("确定"));
        buttonPanel.add(new JButton("取消"));
        contentPanel.add(buttonPanel, gbc);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        // 设置背景色
        Color[] colors = {
            Color.WHITE, new Color(0xE3F2FD), new Color(0xE8F5E8), 
            new Color(0xFFF3E0), new Color(0xF3E5F5), new Color(0xE0F2F1),
            new Color(0xFCE4EC), new Color(0xF1F8E9)
        };
        panel.setBackground(colors[index % colors.length]);
        
        return new Dockable("dynamic_" + index, title, panel);
    }
    
    private void saveLayout() {
        try {
            DockingLayout layout = docking.saveLayout();
            
            // 序列化布局到文件
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream("modern_layout.dat"))) {
                oos.writeObject(layout);
            }
            
            JOptionPane.showMessageDialog(this, 
                "布局已保存到 modern_layout.dat", 
                "保存成功", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "保存布局失败: " + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadLayout() {
        File layoutFile = new File("modern_layout.dat");
        if (!layoutFile.exists()) {
            JOptionPane.showMessageDialog(this, 
                "布局文件不存在", 
                "提示", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // 从文件反序列化布局
            DockingLayout layout;
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(layoutFile))) {
                layout = (DockingLayout) ois.readObject();
            }
            
            docking.loadLayout(layout);
            
            // 更新菜单状态
            updateMenuStates();
            
            JOptionPane.showMessageDialog(this, 
                "布局已从 modern_layout.dat 加载", 
                "加载成功", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, 
                "加载布局失败: " + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetLayout() {
        // 清除所有停靠组件
        for (Dockable dockable : availableDockables) {
            if (docking.isDocked(dockable)) {
                docking.undock(dockable);
            }
        }
        
        // 重新设置默认布局
        docking.dock(availableDockables.get(0), DockingRegion.CENTER);
        docking.dock(availableDockables.get(1), DockingRegion.WEST);
        docking.dock(availableDockables.get(2), DockingRegion.SOUTH);
        
        // 更新菜单状态
        updateMenuStates();
        
        JOptionPane.showMessageDialog(this, 
            "布局已重置为默认状态", 
            "重置完成", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateMenuStates() {
        JMenu viewMenu = menuBar.getMenu(0);
        for (int i = 0; i < availableDockables.size(); i++) {
            JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) viewMenu.getItem(i);
            menuItem.setSelected(docking.isDocked(availableDockables.get(i)));
        }
    }
}
```

### 💡 最佳实践和集成

#### 1. 与现有应用集成
```java
public class IntegrationBestPractices {
    
    // 1. 渐进式集成 - 从简单布局开始
    public static void progressiveIntegration(JFrame existingFrame) {
        // 保存现有内容
        Component existingContent = existingFrame.getContentPane();
        
        // 创建现代停靠框架
        ModernDocking docking = new ModernDocking(existingFrame);
        
        // 将现有内容包装为停靠组件
        Dockable mainContent = new Dockable("main", "主要内容", existingContent);
        docking.dock(mainContent, DockingRegion.CENTER);
        
        // 逐步添加新的停靠面板
        addToolPanels(docking);
    }
    
    // 2. 主题一致性
    public static void ensureThemeConsistency(ModernDocking docking) {
        // 根据系统主题自动设置
        boolean systemDarkMode = OsThemeDetector.getDetector().isDark();
        docking.setTheme(systemDarkMode ? ModernDockingTheme.DARK : ModernDockingTheme.LIGHT);
        
        // 监听系统主题变化
        OsThemeDetector.getDetector().registerListener(isDark -> {
            SwingUtilities.invokeLater(() -> {
                docking.setTheme(isDark ? ModernDockingTheme.DARK : ModernDockingTheme.LIGHT);
            });
        });
    }
    
    // 3. 性能优化
    public static void optimizePerformance(ModernDocking docking) {
        // 延迟加载停靠面板
        docking.setDockableFactory(id -> {
            return createDockableLazily(id);
        });
        
        // 设置合理的最小尺寸
        docking.setMinimumSize(new Dimension(200, 150));
        
        // 启用硬件加速
        System.setProperty("sun.java2d.opengl", "true");
    }
    
    // 4. 错误处理和恢复
    public static void setupErrorHandling(ModernDocking docking) {
        // 设置异常处理器
        docking.setExceptionHandler(exception -> {
            System.err.println("停靠操作异常: " + exception.getMessage());
            exception.printStackTrace();
            
            // 尝试恢复到安全状态
            recoverToSafeState(docking);
        });
    }
    
    private static void addToolPanels(ModernDocking docking) {
        // 添加工具面板的实现
    }
    
    private static Dockable createDockableLazily(String id) {
        // 延迟创建停靠组件的实现
        return new Dockable(id, "Panel " + id, new JLabel("Loading..."));
    }
    
    private static void recoverToSafeState(ModernDocking docking) {
        // 恢复到安全状态的实现
    }
}
```

#### 2. 自定义停靠组件
```java
public class CustomDockableComponents {
    
    // 可关闭的停靠组件
    public static class CloseableDockable extends Dockable {
        private JButton closeButton;
        
        public CloseableDockable(String id, String title, Component content) {
            super(id, title, content);
            setupCloseButton();
        }
        
        private void setupCloseButton() {
            closeButton = new JButton("×");
            closeButton.setPreferredSize(new Dimension(20, 20));
            closeButton.setFont(new Font("Arial", Font.BOLD, 12));
            closeButton.setBorderPainted(false);
            closeButton.setContentAreaFilled(false);
            closeButton.setFocusPainted(false);
            
            closeButton.addActionListener(e -> {
                // 触发关闭事件
                fireCloseEvent();
            });
            
            // 添加到标题栏
            addToTitleBar(closeButton);
        }
        
        private void fireCloseEvent() {
            // 通知停靠框架关闭此组件
            ModernDocking.getInstance().undock(this);
        }
    }
    
    // 带状态指示的停靠组件
    public static class StatusDockable extends Dockable {
        private JLabel statusIndicator;
        private DockableStatus status;
        
        public enum DockableStatus {
            NORMAL(Color.GREEN, "正常"),
            WARNING(Color.ORANGE, "警告"),
            ERROR(Color.RED, "错误"),
            INACTIVE(Color.GRAY, "未激活");
            
            private final Color color;
            private final String description;
            
            DockableStatus(Color color, String description) {
                this.color = color;
                this.description = description;
            }
        }
        
        public StatusDockable(String id, String title, Component content) {
            super(id, title, content);
            setupStatusIndicator();
            setStatus(DockableStatus.NORMAL);
        }
        
        private void setupStatusIndicator() {
            statusIndicator = new JLabel("●");
            statusIndicator.setFont(new Font("Arial", Font.BOLD, 12));
            addToTitleBar(statusIndicator);
        }
        
        public void setStatus(DockableStatus status) {
            this.status = status;
            statusIndicator.setForeground(status.color);
            statusIndicator.setToolTipText(status.description);
        }
        
        public DockableStatus getStatus() {
            return status;
        }
    }
    
    // 可调节大小的内容面板
    public static class ResizableContentDockable extends Dockable {
        private JSlider sizeSlider;
        private JComponent content;
        
        public ResizableContentDockable(String id, String title, JComponent content) {
            super(id, title, createResizablePanel(content));
            this.content = content;
        }
        
        private static JPanel createResizablePanel(JComponent content) {
            JPanel panel = new JPanel(new BorderLayout());
            
            // 大小控制滑块
            JSlider sizeSlider = new JSlider(50, 200, 100);
            sizeSlider.setMajorTickSpacing(25);
            sizeSlider.setPaintTicks(true);
            sizeSlider.setPaintLabels(true);
            
            sizeSlider.addChangeListener(e -> {
                int value = sizeSlider.getValue();
                Font currentFont = content.getFont();
                if (currentFont != null) {
                    Font newFont = currentFont.deriveFont((float) (currentFont.getSize() * value / 100.0));
                    content.setFont(newFont);
                }
            });
            
            JPanel controlPanel = new JPanel(new FlowLayout());
            controlPanel.add(new JLabel("大小:"));
            controlPanel.add(sizeSlider);
            
            panel.add(controlPanel, BorderLayout.NORTH);
            panel.add(content, BorderLayout.CENTER);
            
            return panel;
        }
    }
}
```

### 📚 总结

本文档详细介绍了Swing Enhanced Demo项目中使用的所有第三方库。每个库都有其独特的优势和应用场景：

- **FlatLaf**: 为应用程序提供现代化的外观
- **Ikonli**: 丰富的矢量图标支持
- **MiGLayout & JGoodies Forms**: 强大的布局管理
- **RSyntaxTextArea**: 专业的代码编辑功能
- **JFreeChart & XChart**: 专业和轻量级图表解决方案
- **Apache PDFBox**: 完整的PDF处理能力
- **GlazedLists**: 高级列表操作和数据绑定
- **Thumbnailator**: 高质量图片处理
- **LoboEvolution**: 纯Java的HTML渲染
- **jSystemThemeDetector**: 系统主题感知
- **DockingFrames & ModernDockingUI**: 专业的停靠界面解决方案

这些库的组合为构建现代化、功能丰富的Java Swing应用程序提供了强大的基础。通过合理使用这些库，开发者可以创建出既美观又实用的桌面应用程序。

## 🔗 相关资源

- [项目GitHub仓库](https://github.com/your-repo/swing-enhanced-demo)
- [在线文档](https://your-docs-site.com)
- [示例代码集合](https://github.com/your-repo/examples)
- [社区论坛](https://community.your-site.com)

## 📝 更新日志

- **v2.0** (2024-08-09): 完成所有库的详细文档
- **v1.5** (2024-08-08): 添加高级使用示例
- **v1.0** (2024-08-07): 初始版本发布
  #### 4. 高级过滤功能
```java
import ca.odell.glazedlists.matchers.*;

public class AdvancedFilterDemo {
    public static void createAdvancedFilter() {
        EventList<Person> baseList = new BasicEventList<>();
        // 添加测试数据...
        
        // 创建多重过滤列表
        FilterList<Person> filteredList = new FilterList<>(baseList);
        
        // 复合过滤条件
        Matcher<Person> ageMatcher = person -> person.getAge() >= 18 && person.getAge() <= 65;
        Matcher<Person> nameMatcher = person -> person.getName().length() > 2;
        
        // 组合过滤器
        CompositeMatcherEditor<Person> compositeEditor = new CompositeMatcherEditor<>();
        compositeEditor.getMatcherEditors().add(new FixedMatcherEditor<>(ageMatcher));
        compositeEditor.getMatcherEditors().add(new FixedMatcherEditor<>(nameMatcher));
        
        filteredList.setMatcherEditor(compositeEditor);
        
        // 实时搜索过滤
        JTextField searchField = new JTextField();
        TextComponentMatcherEditor<Person> textMatcherEditor = 
            new TextComponentMatcherEditor<>(searchField, new PersonTextFilterator());
        filteredList.setMatcherEditor(textMatcherEditor);
    }
    
    // 自定义文本过滤器
    private static class PersonTextFilterator implements TextFilterator<Person> {
        @Override
        public void getFilterStrings(List<String> baseList, Person element) {
            baseList.add(element.getName().toLowerCase());
            baseList.add(element.getEmail().toLowerCase());
            baseList.add(String.valueOf(element.getAge()));
        }
    }
}
```

#### 5. 事件监听和处理
```java
public class GlazedListsEventsDemo {
    public static void setupEventHandling() {
        EventList<String> eventList = new BasicEventList<>();
        
        // 添加列表变化监听器
        eventList.addListEventListener(new ListEventListener<String>() {
            @Override
            public void listChanged(ListEvent<String> listChanges) {
                while (listChanges.next()) {
                    int changeIndex = listChanges.getIndex();
                    int changeType = listChanges.getType();
                    
                    switch (changeType) {
                        case ListEvent.INSERT:
                            System.out.println("插入元素在位置: " + changeIndex);
                            break;
                        case ListEvent.UPDATE:
                            System.out.println("更新元素在位置: " + changeIndex);
                            break;
                        case ListEvent.DELETE:
                            System.out.println("删除元素在位置: " + changeIndex);
                            break;
                    }
                }
            }
        });
        
        // 测试事件
        eventList.add("First");    // 触发INSERT事件
        eventList.set(0, "Modified"); // 触发UPDATE事件
        eventList.remove(0);       // 触发DELETE事件
    }
}
```

### 📊 与Swing组件集成

#### 1. 高级表格功能
```java
public class AdvancedTableDemo {
    public static JPanel createAdvancedTable() {
        // 创建数据列表
        EventList<Employee> employees = new BasicEventList<>();
        employees.add(new Employee("张三", "开发", 8000, "北京"));
        employees.add(new Employee("李四", "测试", 7000, "上海"));
        employees.add(new Employee("王五", "设计", 7500, "深圳"));
        
        // 创建排序列表
        SortedList<Employee> sortedEmployees = new SortedList<>(employees);
        
        // 创建过滤列表
        FilterList<Employee> filteredEmployees = new FilterList<>(sortedEmployees);
        
        // 创建表格格式
        AdvancedTableFormat<Employee> tableFormat = new AdvancedTableFormat<Employee>() {
            private String[] columnNames = {"姓名", "部门", "薪资", "城市"};
            private Class[] columnClasses = {String.class, String.class, Integer.class, String.class};
            
            @Override
            public int getColumnCount() { return columnNames.length; }
            
            @Override
            public String getColumnName(int column) { return columnNames[column]; }
            
            @Override
            public Class getColumnClass(int column) { return columnClasses[column]; }
            
            @Override
            public Object getColumnValue(Employee employee, int column) {
                switch (column) {
                    case 0: return employee.getName();
                    case 1: return employee.getDepartment();
                    case 2: return employee.getSalary();
                    case 3: return employee.getCity();
                    default: return null;
                }
            }
            
            @Override
            public boolean isEditable(Employee employee, int column) {
                return column != 0; // 姓名不可编辑
            }
            
            @Override
            public Employee setColumnValue(Employee employee, Object editedValue, int column) {
                Employee modified = new Employee(employee);
                switch (column) {
                    case 1: modified.setDepartment((String) editedValue); break;
                    case 2: modified.setSalary((Integer) editedValue); break;
                    case 3: modified.setCity((String) editedValue); break;
                }
                return modified;
            }
        };
        
        // 创建表格模型
        AdvancedTableModel<Employee> tableModel = 
            new EventTableModel<>(filteredEmployees, tableFormat);
        
        // 创建表格
        JTable table = new JTable(tableModel);
        
        // 添加排序支持
        TableComparatorChooser.install(table, sortedEmployees, 
            TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);
        
        // 创建搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        searchPanel.add(new JLabel("搜索:"));
        searchPanel.add(searchField);
        
        // 设置搜索过滤
        TextComponentMatcherEditor<Employee> textMatcherEditor = 
            new TextComponentMatcherEditor<>(searchField, new EmployeeTextFilterator());
        filteredEmployees.setMatcherEditor(textMatcherEditor);
        
        // 组装界面
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        return panel;
    }
}
```
