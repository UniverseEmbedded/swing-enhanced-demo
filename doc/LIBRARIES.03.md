# Swing增强库补充文档 (LIBRARIES.03.md)

本文档补充了 LIBRARIES.01.md 和 LIBRARIES.02.md 之间缺失的库文档，重点介绍 DockingFrames 以及其他可能遗漏的重要库功能。

## 📖 目录
1. [DockingFrames - 专业停靠框架](#dockingframes---专业停靠框架)
2. [ModernDockingUI - 现代停靠界面](#moderndockingui---现代停靠界面)
3. [补充功能和最佳实践](#补充功能和最佳实践)

---

## DockingFrames - 专业停靠框架

**版本**: 1.1.1  
**官网**: https://www.docking-frames.org/  
**GitHub**: https://github.com/Benoker/DockingFrames  
**许可证**: LGPL 2.1

### 🎯 核心特性

DockingFrames是一个开源的Java Swing停靠框架，在LGPL 2.1许可证下发布。大多数图形用户界面由一些面板组成，DockingFrames提供了一种组织这些面板的方式，使用户可以拖放它们。

#### 主要优势
- **🔄 极致灵活性**: DockingFrames的理念是尽可能具有最大的灵活性，即使这意味着要牺牲简单性或清晰度。大多数模块可以由开发人员替换，而无需更改DockingFrames的代码。
- **🎨 多主题支持**: 内置Eclipse主题和其他可定制主题
- **📱 完全集成**: 与Swing组件完美集成
- **💾 布局持久化**: 支持布局的保存和加载
- **🔧 高度可定制**: 几乎所有组件都可以自定义替换

#### 架构组件
- **CControl**: 控制器管理DockingFrames的所有元素
- **CDockable**: 可停靠组件的接口
- **CGrid**: 网格只是描述布局，它不会成为停靠树的一部分
- **CLocation**: 位置定义系统
- **CContentArea**: 内容区域是一个JComponent，它有几个"Dockables"作为子组件

### 🔧 基础使用

#### 1. Hello World 示例

```java
import bibliothek.gui.dock.common.*;

public class DockingFramesDemo {
    public static void main(String[] args) {
        // 创建主框架
        JFrame frame = new JFrame("DockingFrames Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 创建控制器和内容区域
        CControl control = new CControl(frame);
        frame.add(control.getContentArea());
        
        // 使用网格布局创建停靠组件
        CGrid grid = new CGrid(control);
        grid.add(0, 0, 1, 1, createDockable("Red", Color.RED));
        grid.add(0, 1, 1, 1, createDockable("Green", Color.GREEN));
        grid.add(1, 0, 1, 1, createDockable("Blue", Color.BLUE));
        grid.add(1, 1, 1, 1, createDockable("Yellow", Color.YELLOW));
        
        // 部署网格
        control.getContentArea().deploy(grid);
        
        // 添加单独的停靠组件
        SingleCDockable black = createDockable("Black", Color.BLACK);
        control.addDockable(black);
        black.setLocation(CLocation.base().minimalNorth());
        black.setVisible(true);
        
        frame.setBounds(20, 20, 600, 400);
        frame.setVisible(true);
    }
    
    private static DefaultSingleCDockable createDockable(String title, Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setOpaque(true);
        panel.add(new JLabel(title, JLabel.CENTER));
        
        return new DefaultSingleCDockable(
            title.toLowerCase(), // 唯一ID
            title,               // 显示标题
            panel               // 内容组件
        );
    }
}
```

#### 2. 高级停靠布局

```java
public class AdvancedDockingDemo extends JFrame {
    private CControl control;
    
    public AdvancedDockingDemo() {
        setTitle("高级DockingFrames演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 初始化控制器
        control = new CControl(this);
        add(control.getContentArea());
        
        // 创建复杂布局
        setupComplexLayout();
        
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    private void setupComplexLayout() {
        // 主编辑区域
        DefaultSingleCDockable editor = new DefaultSingleCDockable(
            "editor", "代码编辑器", createCodeEditor());
        editor.setCloseable(false); // 不可关闭
        
        // 项目浏览器
        DefaultSingleCDockable explorer = new DefaultSingleCDockable(
            "explorer", "项目浏览器", createProjectExplorer());
        
        // 属性面板
        DefaultSingleCDockable properties = new DefaultSingleCDockable(
            "properties", "属性", createPropertiesPanel());
        
        // 输出窗口
        DefaultSingleCDockable output = new DefaultSingleCDockable(
            "output", "输出", createOutputPanel());
        
        // 使用网格布局
        CGrid grid = new CGrid(control);
        
        // 左侧放置项目浏览器
        grid.add(0, 0, 0.2, 0.7, explorer);
        
        // 中央放置编辑器
        grid.add(0.2, 0, 0.6, 0.7, editor);
        
        // 右侧放置属性面板
        grid.add(0.8, 0, 0.2, 0.7, properties);
        
        // 底部放置输出窗口
        grid.add(0, 0.7, 1.0, 0.3, output);
        
        // 选中编辑器作为活动组件
        grid.select(0.2, 0, 0.6, 0.7, editor);
        
        // 部署布局
        control.getContentArea().deploy(grid);
    }
    
    private JComponent createCodeEditor() {
        JTextArea editor = new JTextArea();
        editor.setFont(new Font("Consolas", Font.PLAIN, 14));
        editor.setText("""
            public class Main {
                public static void main(String[] args) {
                    System.out.println("Hello DockingFrames!");
                }
            }
            """);
        
        return new JScrollPane(editor);
    }
    
    private JComponent createProjectExplorer() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("项目");
        DefaultMutableTreeNode src = new DefaultMutableTreeNode("src");
        src.add(new DefaultMutableTreeNode("Main.java"));
        src.add(new DefaultMutableTreeNode("Utils.java"));
        root.add(src);
        
        JTree tree = new JTree(root);
        tree.setRootVisible(true);
        return new JScrollPane(tree);
    }
    
    private JComponent createPropertiesPanel() {
        Object[][] data = {
            {"文件名", "Main.java"},
            {"大小", "1.2 KB"},
            {"修改时间", "2024-08-09 16:30"}
        };
        String[] columns = {"属性", "值"};
        
        JTable table = new JTable(data, columns);
        return new JScrollPane(table);
    }
    
    private JComponent createOutputPanel() {
        JTextArea output = new JTextArea();
        output.setBackground(Color.BLACK);
        output.setForeground(Color.WHITE);
        output.setFont(new Font("Consolas", Font.PLAIN, 12));
        output.setText("构建输出:\nHello DockingFrames!\n编译成功");
        output.setEditable(false);
        
        return new JScrollPane(output);
    }
}
```

### 🎨 主题和定制

#### 1. 应用Eclipse主题

```java
public class ThemeDemo {
    public static void applyEclipseTheme(CControl control) {
        // 设置Eclipse主题
        control.setTheme(ThemeMap.KEY_ECLIPSE_THEME);
        
        // 自定义主题属性
        control.putProperty(StackDockStation.TAB_PLACEMENT, 
                          TabPlacement.TOP_OF_DOCKABLE);
        
        // 设置标签页配置
        control.putProperty(EclipseTheme.TAB_PAINTER, 
                          new CustomEclipseTabPainter());
    }
    
    private static class CustomEclipseTabPainter extends EclipseTabPainter {
        @Override
        public void paintBackground(Graphics g, JComponent component, 
                                  Dockable dockable, Bounds bounds, 
                                  boolean selected, boolean mouseOver, 
                                  boolean focused) {
            // 自定义标签页背景绘制
            if (selected) {
                g.setColor(new Color(70, 130, 190));
            } else {
                g.setColor(new Color(240, 240, 240));
            }
            g.fillRect(bounds.getX(), bounds.getY(), 
                      bounds.getWidth(), bounds.getHeight());
        }
    }
}
```

#### 2. 自定义停靠站点

```java
public class CustomStationDemo {
    // 创建自定义停靠站点
    private static class CustomCStation extends AbstractDockableCStation<StackDockStation> 
            implements CNormalModeArea {
        
        public CustomCStation(String id) {
            StackDockStation delegate = new StackDockStation(this);
            
            CLocation stationLocation = new CLocation() {
                @Override
                public CLocation getParent() { return null; }
                
                @Override
                public String findRoot() { return getUniqueId(); }
                
                @Override
                public DockableProperty findProperty(DockableProperty successor) {
                    return successor;
                }
                
                @Override
                public ExtendedMode findMode() {
                    return ExtendedMode.NORMALIZED;
                }
                
                @Override
                public CLocation aside() { return this; }
            };
            
            init(delegate, id, stationLocation, delegate);
        }
        
        @Override
        public boolean autoDefaultArea() { return true; }
        
        @Override
        public boolean isLocationRoot() { return true; }
        
        // 实现其他必需的方法...
    }
}
```

### 💾 布局持久化

#### 1. 保存和加载布局

```java
public class LayoutPersistenceDemo {
    private CControl control;
    
    public void saveLayout(String filename) {
        try {
            // 获取布局数据
            String layoutData = control.getResources().writeXML();
            
            // 保存到文件
            try (FileWriter writer = new FileWriter(filename)) {
                writer.write(layoutData);
            }
            
            System.out.println("布局已保存到: " + filename);
            
        } catch (IOException e) {
            System.err.println("保存布局失败: " + e.getMessage());
        }
    }
    
    public void loadLayout(String filename) {
        try {
            // 从文件读取布局数据
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            
            // 应用布局
            control.getResources().readXML(content.toString());
            
            System.out.println("布局已从文件加载: " + filename);
            
        } catch (IOException e) {
            System.err.println("加载布局失败: " + e.getMessage());
        }
    }
    
    // 自动保存布局
    public void setupAutoSave() {
        control.addControlListener(new CControlAdapter() {
            @Override
            public void closed(CControl control, CDockable dockable) {
                // 当停靠组件关闭时自动保存
                saveLayout("auto_save_layout.xml");
            }
            
            @Override
            public void added(CControl control, CDockable dockable) {
                // 当添加新的停靠组件时自动保存
                saveLayout("auto_save_layout.xml");
            }
        });
    }
}
```

### 🔧 高级功能

#### 1. 多框架支持

```java
public class MultiFrameDemo {
    private CControl control;
    private FocusedWindowProvider windowProvider;
    private List<JFrame> frames;
    
    public MultiFrameDemo() {
        // 使用FocusedWindowProvider支持多框架
        windowProvider = new FocusedWindowProvider();
        control = new CControl(windowProvider);
        frames = new ArrayList<>();
        
        setupMultipleFrames();
    }
    
    private void setupMultipleFrames() {
        // 创建多个框架
        for (int i = 0; i < 3; i++) {
            JFrame frame = createFrame("框架 " + (i + 1));
            frames.add(frame);
            windowProvider.add(frame);
        }
        
        // 在每个框架中添加内容区域
        for (JFrame frame : frames) {
            CContentArea contentArea = control.createContentArea("frame_" + frames.indexOf(frame));
            frame.add(contentArea);
        }
    }
    
    private JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        return frame;
    }
}
```

#### 2. 动态停靠组件管理

```java
public class DynamicDockableDemo {
    private CControl control;
    private int dockableCounter = 0;
    
    public void createDynamicDockable() {
        String id = "dynamic_" + (++dockableCounter);
        String title = "动态面板 " + dockableCounter;
        
        // 创建内容
        JPanel content = new JPanel(new BorderLayout());
        content.add(new JLabel("这是动态创建的面板: " + title, JLabel.CENTER));
        
        JButton closeButton = new JButton("关闭此面板");
        content.add(closeButton, BorderLayout.SOUTH);
        
        // 创建停靠组件
        DefaultSingleCDockable dockable = new DefaultSingleCDockable(id, title, content);
        dockable.setCloseable(true);
        
        // 添加关闭按钮事件
        closeButton.addActionListener(e -> {
            dockable.setVisible(false);
            control.removeDockable(dockable);
        });
        
        // 注册并显示
        control.addDockable(dockable);
        dockable.setLocation(CLocation.base().normal());
        dockable.setVisible(true);
        
        // 尝试获取焦点
        dockable.toFront();
    }
    
    // 批量管理停靠组件
    public void manageAllDockables() {
        // 获取所有注册的停靠组件
        SingleCDockable[] dockables = control.getRegister().getSingleDockables();
        
        for (SingleCDockable dockable : dockables) {
            System.out.println("停靠组件: " + dockable.getTitleText() + 
                             " - 可见: " + dockable.isVisible());
        }
    }
}
```

---

## ModernDockingUI - 现代停靠界面

**版本**: 0.8.2  
**GitHub**: https://github.com/andrewauclair/ModernDocking  
**许可证**: MIT

### 🎯 核心特性

ModernDocking是一个专为Java Swing应用程序设计的简单框架，用于添加停靠功能。虽然有许多现有的Java Swing停靠框架，但它们已经过时且不再维护。现有框架还由于定制功能而变得复杂。

#### 主要优势
- **🎨 现代化设计**: 简洁的现代UI设计
- **🔧 简单API**: 专注于易用性，避免过度复杂
- **⚡ 高性能**: 轻量级实现，优化的性能
- **📱 活跃维护**: 持续更新和维护
- **🎯 专注**: 专注于停靠功能，不包含不必要的特性

### 🔧 基础使用

#### 1. 简单停靠示例

```java
import io.github.andrewauclair.ModernDockingUI.*;

public class ModernDockingDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ModernDocking Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // 初始化现代停靠
            Docking docking = new Docking(frame);
            
            // 创建停靠面板
            JPanel panel1 = new JPanel();
            panel1.add(new JLabel("面板 1"));
            panel1.setBackground(Color.LIGHT_GRAY);
            
            JPanel panel2 = new JPanel(); 
            panel2.add(new JLabel("面板 2"));
            panel2.setBackground(Color.CYAN);
            
            // 创建可停靠组件
            Dockable dockable1 = new Dockable("panel1", "面板1", panel1);
            Dockable dockable2 = new Dockable("panel2", "面板2", panel2);
            
            // 停靠到不同区域
            docking.dock(dockable1, DockingRegion.CENTER);
            docking.dock(dockable2, DockingRegion.WEST);
            
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
```

#### 2. 高级布局管理

```java
public class AdvancedModernDockingDemo {
    private Docking docking;
    private JFrame frame;
    
    public AdvancedModernDockingDemo() {
        setupFrame();
        createDockables();
        setupLayoutManagement();
    }
    
    private void setupFrame() {
        frame = new JFrame("高级 ModernDocking 演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        docking = new Docking(frame);
        
        // 设置主题
        docking.setTheme(DockingTheme.DARK);
        
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
    }
    
    private void createDockables() {
        // 文本编辑器
        Dockable editor = createTextEditor();
        
        // 文件浏览器
        Dockable explorer = createFileExplorer();
        
        // 输出面板
        Dockable output = createOutputPanel();
        
        // 工具面板
        Dockable tools = createToolPanel();
        
        // 停靠到不同位置
        docking.dock(editor, DockingRegion.CENTER);
        docking.dock(explorer, DockingRegion.WEST);
        docking.dock(output, DockingRegion.SOUTH);
        docking.dock(tools, DockingRegion.EAST);
        
        // 设置初始尺寸比例
        docking.setSplitRatio(DockingRegion.WEST, 0.2);
        docking.setSplitRatio(DockingRegion.EAST, 0.2);
        docking.setSplitRatio(DockingRegion.SOUTH, 0.25);
    }
    
    private Dockable createTextEditor() {
        JTextArea editor = new JTextArea();
        editor.setFont(new Font("Consolas", Font.PLAIN, 14));
        editor.setText("// ModernDocking 文本编辑器\npublic class Example {\n    // 代码内容\n}");
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(editor), BorderLayout.CENTER);
        
        return new Dockable("editor", "编辑器", panel);
    }
    
    private Dockable createFileExplorer() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("项目");
        DefaultMutableTreeNode src = new DefaultMutableTreeNode("src");
        src.add(new DefaultMutableTreeNode("Main.java"));
        src.add(new DefaultMutableTreeNode("Utils.java"));
        root.add(src);
        
        JTree tree = new JTree(root);
        tree.setRootVisible(true);
        
        return new Dockable("explorer", "文件浏览器", new JScrollPane(tree));
    }
    
    private Dockable createOutputPanel() {
        JTextArea output = new JTextArea();
        output.setBackground(Color.BLACK);
        output.setForeground(Color.WHITE);
        output.setFont(new Font("Consolas", Font.PLAIN, 12));
        output.setText("ModernDocking 输出窗口\n> 应用启动成功\n> 就绪状态");
        output.setEditable(false);
        
        return new Dockable("output", "输出", new JScrollPane(output));
    }
    
    private Dockable createToolPanel() {
        JPanel toolPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        toolPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] tools = {"选择", "移动", "缩放", "旋转", "复制", "删除"};
        for (String tool : tools) {
            JButton button = new JButton(tool);
            toolPanel.add(button);
        }
        
        return new Dockable("tools", "工具", toolPanel);
    }
    
    private void setupLayoutManagement() {
        // 添加菜单栏进行布局管理
        JMenuBar menuBar = new JMenuBar();
        
        JMenu layoutMenu = new JMenu("布局");
        
        JMenuItem saveLayout = new JMenuItem("保存布局");
        saveLayout.addActionListener(e -> saveCurrentLayout());
        
        JMenuItem loadLayout = new JMenuItem("加载布局");
        loadLayout.addActionListener(e -> loadSavedLayout());
        
        JMenuItem resetLayout = new JMenuItem("重置布局");
        resetLayout.addActionListener(e -> resetToDefaultLayout());
        
        layoutMenu.add(saveLayout);
        layoutMenu.add(loadLayout);
        layoutMenu.addSeparator();
        layoutMenu.add(resetLayout);
        
        menuBar.add(layoutMenu);
        frame.setJMenuBar(menuBar);
    }
    
    private void saveCurrentLayout() {
        try {
            DockingLayout layout = docking.getLayoutManager().getCurrentLayout();
            LayoutSerializer.save(layout, "modern_layout.json");
            JOptionPane.showMessageDialog(frame, "布局保存成功!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "保存失败: " + e.getMessage());
        }
    }
    
    private void loadSavedLayout() {
        try {
            DockingLayout layout = LayoutSerializer.load("modern_layout.json");
            docking.getLayoutManager().applyLayout(layout);
            JOptionPane.showMessageDialog(frame, "布局加载成功!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "加载失败: " + e.getMessage());
        }
    }
    
    private void resetToDefaultLayout() {
        docking.getLayoutManager().resetToDefault();
        JOptionPane.showMessageDialog(frame, "布局已重置!");
    }
}
```

---

## 补充功能和最佳实践

### 💡 性能优化建议

#### 1. DockingFrames性能优化

```java
public class DockingPerformanceOptimization {
    
    public static void optimizeDockingFrames(CControl control) {
        // 1. 延迟加载停靠组件
        control.addSingleDockableFactory(new SingleCDockableFactory() {
            @Override
            public SingleCDockable createBackup(String id) {
                return createDockableLazily(id);
            }
        });
        
        // 2. 优化渲染性能
        control.putProperty(DockController.RESTRICTED_ENVIRONMENT, true);
        
        // 3. 减少不必要的重绘
        control.putProperty(DockController.DISABLE_CURSOR_BOUNDS, true);
        
        // 4. 优化拖拽性能
        control.putProperty(DockController.MERGE_DISABLED, true);
    }
    
    private static SingleCDockable createDockableLazily(String id) {
        // 延迟创建组件内容
        return new DefaultSingleCDockable(id, "加载中...", new JLabel("正在加载..."));
    }
    
    // 内存管理
    public static void cleanupResources(CControl control) {
        // 清理未使用的停靠组件
        SingleCDockable[] dockables = control.getRegister().getSingleDockables();
        for (SingleCDockable dockable : dockables) {
            if (!dockable.isVisible()) {
                control.removeDockable(dockable);
            }
        }
        
        // 清理主题资源
        control.getThemeManager().publish(Priority.CLIENT, new ResourceBundleTheme());
    }
}
```

#### 2. 内存管理

```java
public class MemoryManagement {
    
    // 弱引用管理停靠组件
    private WeakHashMap<String, CDockable> dockableCache = new WeakHashMap<>();
    
    public void addDockableToCache(String id, CDockable dockable) {
        dockableCache.put(id, dockable);
    }
    
    public CDockable getDockableFromCache(String id) {
        return dockableCache.get(id);
    }
    
    // 定期清理
    public void performPeriodicCleanup() {
        Timer cleanupTimer = new Timer(300000, e -> { // 每5分钟
            System.gc(); // 建议垃圾回收
            System.out.println("缓存大小: " + dockableCache.size());
        });
        cleanupTimer.start();
    }
}
```

### 🔧 集成最佳实践

#### 1. 与其他库的集成

```java
public class LibraryIntegration {
    
    // 与FlatLaf集成
    public static void integrateFlatLaf(CControl control) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            
            // 更新DockingFrames主题
            control.setTheme(ThemeMap.KEY_FLAT_THEME);
            
            // 自定义颜色
            control.getThemeManager().setBackgroundColor(
                TypedUIManager.TYPE_FLAP_DOCK_STATION, 
                new Color(0x2B2B2B));
                
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    
    // 与系统主题检测集成
    public static void integrateSystemTheme(CControl control) {
        OsThemeDetector detector = OsThemeDetector.getDetector();
        
        // 根据系统主题设置
        updateTheme(control, detector.isDark());
        
        // 监听主题变化
        detector.registerListener(isDark -> {
            SwingUtilities.invokeLater(() -> updateTheme(control, isDark));
        });
    }
    
    private static void updateTheme(CControl control, boolean isDark) {
        if (isDark) {
            control.setTheme(ThemeMap.KEY_ECLIPSE_THEME);
        } else {
            control.setTheme(ThemeMap.KEY_FLAT_THEME);
        }
    }
}
```

#### 2. 错误处理和恢复

```java
public class ErrorHandling {
    
    public static void setupErrorHandling(CControl control) {
        // 添加异常监听器
        control.addControlListener(new CControlAdapter() {
            @Override
            public void added(CControl control, CDockable dockable) {
                try {
                    // 验证停靠组件
                    validateDockable(dockable);
                } catch (Exception e) {
                    handleDockableError(e, dockable);
                }
            }
        });
        
        // 设置默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            System.err.println("未捕获异常: " + exception.getMessage());
            
            // 尝试恢复到安全状态
            recoverToSafeState(control);
        });
    }
    
    private static void validateDockable(CDockable dockable) {
        if (dockable.intern() == null) {
            throw new IllegalStateException("停靠组件未正确初始化");
        }
    }
    
    private static void handleDockableError(Exception e, CDockable dockable) {
        System.err.println("停靠组件错误: " + e.getMessage());
        
        // 移除有问题的停靠组件
        if (dockable.getControl() != null) {
            dockable.setVisible(false);
        }
    }
    
    private static void recoverToSafeState(CControl control) {
        try {
            // 关闭所有非必要的停靠组件
            SingleCDockable[] dockables = control.getRegister().getSingleDockables();
            for (SingleCDockable dockable : dockables) {
                if (dockable.isCloseable()) {
                    dockable.setVisible(false);
                }
            }
            
            System.out.println("已恢复到安全状态");
            
        } catch (Exception recoveryException) {
            System.err.println("恢复失败: " + recoveryException.getMessage());
        }
    }
}
```

### 📚 完整示例：IDE样式应用

```java
public class IDEStyleApplication extends JFrame {
    private CControl control;
    private Map<String, DefaultSingleCDockable> dockables;
    
    public IDEStyleApplication() {
        dockables = new HashMap<>();
        setupApplication();
        createAllDockables();
        setupMenuAndToolbar();
        setupEventHandlers();
    }
    
    private void setupApplication() {
        setTitle("IDE样式应用 - DockingFrames + ModernDocking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 初始化停靠控制器
        control = new CControl(this);
        add(control.getContentArea());
        
        // 应用主题
        LibraryIntegration.integrateFlatLaf(control);
        LibraryIntegration.integrateSystemTheme(control);
        
        // 设置错误处理
        ErrorHandling.setupErrorHandling(control);
        
        // 性能优化
        DockingPerformanceOptimization.optimizeDockingFrames(control);
    }
    
    private void createAllDockables() {
        // 使用网格创建主要布局
        CGrid grid = new CGrid(control);
        
        // 创建各种面板
        createAndAddDockable("editor", "代码编辑器", createEditor(), false);
        createAndAddDockable("explorer", "项目浏览器", createExplorer(), true);
        createAndAddDockable("console", "控制台", createConsole(), true);
        createAndAddDockable("properties", "属性", createProperties(), true);
        createAndAddDockable("outline", "大纲", createOutline(), true);
        
        // 布局设置
        grid.add(0, 0, 0.2, 0.7, dockables.get("explorer"));
        grid.add(0.2, 0, 0.6, 0.7, dockables.get("editor"));
        grid.add(0.8, 0, 0.2, 0.35, dockables.get("properties"));
        grid.add(0.8, 0.35, 0.2, 0.35, dockables.get("outline"));
        grid.add(0, 0.7, 1.0, 0.3, dockables.get("console"));
        
        // 选择主编辑器
        grid.select(0.2, 0, 0.6, 0.7, dockables.get("editor"));
        
        // 部署布局
        control.getContentArea().deploy(grid);
    }
    
    private void createAndAddDockable(String id, String title, 
                                    JComponent content, boolean closeable) {
        DefaultSingleCDockable dockable = 
            new DefaultSingleCDockable(id, title, content);
        dockable.setCloseable(closeable);
        dockable.setMinimizable(true);
        dockable.setMaximizable(true);
        
        dockables.put(id, dockable);
        control.addDockable(dockable);
    }
    
    private JComponent createEditor() {
        // 使用RSyntaxTextArea创建代码编辑器
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
        
        RTextScrollPane sp = new RTextScrollPane(textArea);
        sp.setFoldIndicatorEnabled(true);
        sp.setLineNumbersEnabled(true);
        
        return sp;
    }
    
    private JComponent createExplorer() {
        // 创建文件树
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("工作空间");
        DefaultMutableTreeNode project = new DefaultMutableTreeNode("示例项目");
        DefaultMutableTreeNode src = new DefaultMutableTreeNode("src");
        DefaultMutableTreeNode resources = new DefaultMutableTreeNode("resources");
        
        src.add(new DefaultMutableTreeNode("Main.java"));
        src.add(new DefaultMutableTreeNode("Utils.java"));
        src.add(new DefaultMutableTreeNode("Config.java"));
        
        project.add(src);
        project.add(resources);
        root.add(project);
        
        JTree tree = new JTree(root);
        tree.setRootVisible(true);
        
        // 展开所有节点
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        
        return new JScrollPane(tree);
    }
    
    private JComponent createConsole() {
        JTextArea console = new JTextArea();
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
        console.setFont(new Font("Consolas", Font.PLAIN, 12));
        console.setText("""
            IDE样式应用控制台
            > 应用启动成功
            > DockingFrames 版本: 1.1.1
            > 所有组件加载完成
            > 就绪状态
            """);
        console.setEditable(false);
        
        return new JScrollPane(console);
    }
    
    private JComponent createProperties() {
        Object[][] data = {
            {"名称", "Main.java"},
            {"类型", "Java源文件"},
            {"大小", "2.1 KB"},
            {"修改时间", "2024-08-09 16:45"},
            {"编码", "UTF-8"},
            {"行数", "45"}
        };
        
        String[] columns = {"属性", "值"};
        JTable table = new JTable(data, columns);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        
        return new JScrollPane(table);
    }
    
    private JComponent createOutline() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Main.java");
        DefaultMutableTreeNode classNode = new DefaultMutableTreeNode("Main");
        DefaultMutableTreeNode mainMethod = new DefaultMutableTreeNode("main(String[])");
        DefaultMutableTreeNode field = new DefaultMutableTreeNode("version : String");
        
        classNode.add(field);
        classNode.add(mainMethod);
        root.add(classNode);
        
        JTree outline = new JTree(root);
        outline.setRootVisible(true);
        outline.expandRow(0);
        outline.expandRow(1);
        
        return new JScrollPane(outline);
    }
    
    private void setupMenuAndToolbar() {
        JMenuBar menuBar = new JMenuBar();
        
        // 文件菜单
        JMenu fileMenu = new JMenu("文件");
        fileMenu.add(new JMenuItem("新建"));
        fileMenu.add(new JMenuItem("打开"));
        fileMenu.add(new JMenuItem("保存"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("退出"));
        
        // 视图菜单
        JMenu viewMenu = new JMenu("视图");
        for (String id : dockables.keySet()) {
            DefaultSingleCDockable dockable = dockables.get(id);
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(dockable.getTitleText());
            item.setSelected(dockable.isVisible());
            
            item.addActionListener(e -> {
                dockable.setVisible(item.isSelected());
            });
            
            viewMenu.add(item);
        }
        
        // 布局菜单
        JMenu layoutMenu = new JMenu("布局");
        layoutMenu.add(new JMenuItem("保存布局"));
        layoutMenu.add(new JMenuItem("加载布局"));
        layoutMenu.add(new JMenuItem("重置布局"));
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(layoutMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void setupEventHandlers() {
        // 监听停靠组件状态变化
        control.addControlListener(new CControlAdapter() {
            @Override
            public void opened(CControl control, CDockable dockable) {
                System.out.println("打开: " + dockable.getTitleText());
            }
            
            @Override
            public void closed(CControl control, CDockable dockable) {
                System.out.println("关闭: " + dockable.getTitleText());
            }
        });
        
        // 窗口关闭时保存布局
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveLayoutOnExit();
            }
        });
    }
    
    private void saveLayoutOnExit() {
        try {
            String layoutData = control.getResources().writeXML();
            try (FileWriter writer = new FileWriter("ide_layout.xml")) {
                writer.write(layoutData);
            }
            System.out.println("布局已自动保存");
        } catch (IOException e) {
            System.err.println("保存布局失败: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 设置系统Look and Feel
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            
            new IDEStyleApplication().setVisible(true);
        });
    }
}
```

## 📚 总结

本补充文档详细介绍了在LIBRARIES.01.md和LIBRARIES.02.md之间缺失的重要库，特别是：

1. **DockingFrames 1.1.1**: 专业级的Java Swing停靠框架，提供极致的灵活性和完整的停靠功能
2. **ModernDockingUI 0.8.2**: 现代化的轻量级停靠框架，专注于简单性和现代化外观

这些库为构建专业级的桌面应用程序提供了强大的停靠和窗口管理功能，与项目中的其他库（如FlatLaf、jSystemThemeDetector等）完美集成，共同构成了一个现代化的Swing应用程序开发工具集。

通过合理使用这些停靠框架，开发者可以创建出类似现代IDE的复杂用户界面，提供优秀的用户体验和专业的外观。

---

*最后更新: 2024-08-09*  
*版本: 3.0*