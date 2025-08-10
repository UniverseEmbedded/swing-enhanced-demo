# Swing增强库详细功能介绍

本文档详细介绍了Swing Enhanced Demo项目中使用的所有第三方库的功能、特性和使用方法，基于最新的API文档和官方资料编写。

## 📖 目录

1. [FlatLaf - 现代化Look and Feel](#flatlaf---现代化look-and-feel)
2. [Ikonli - 图标库系统](#ikonli---图标库系统)
3. [MiGLayout - 强大的布局管理器](#miglayout---强大的布局管理器)
4. [JGoodies Forms - 专业表单布局](#jgoodies-forms---专业表单布局)
5. [RSyntaxTextArea - 语法高亮编辑器](#rsyntaxtextarea---语法高亮编辑器)
6. [JFreeChart - 专业图表库](#jfreechart---专业图表库)
7. [Apache PDFBox - PDF文档处理](#apache-pdfbox---pdf文档处理)
8. [GlazedLists - 高级列表操作](#glazedlists---高级列表操作)
9. [Thumbnailator - 图片处理](#thumbnailator---图片处理)
10. [LoboEvolution - HTML渲染引擎](#loboevolution---html渲染引擎)
11. [jSystemThemeDetector - 系统主题检测](#jsystemthemedetector---系统主题检测)

---

## FlatLaf - 现代化Look and Feel

**版本**: 3.6.1  
**官网**: https://www.formdev.com/flatlaf/  
**GitHub**: https://github.com/JFormDesigner/FlatLaf

### 🎨 核心特性

FlatLaf是一个现代化的Swing Look and Feel库，提供了类似IntelliJ IDEA和现代桌面应用的外观。

#### 主要优势
- **🌈 多主题支持**: Light、Dark、IntelliJ、Darcula等内置主题
- **📱 高DPI支持**: 完美适配高分辨率显示器
- **🔧 高度可定制**: 支持自定义颜色、字体、边距等
- **⚡ 性能优化**: 针对现代硬件优化的渲染性能
- **🎯 跨平台**: 在Windows、macOS、Linux上提供一致体验

#### 支持的主题类型
```java
// 浅色主题
UIManager.setLookAndFeel(new FlatLightLaf());
UIManager.setLookAndFeel(new FlatMacLightLaf());

// 深色主题  
UIManager.setLookAndFeel(new FlatDarkLaf());
UIManager.setLookAndFeel(new FlatMacDarkLaf());

// IntelliJ风格主题
UIManager.setLookAndFeel(new FlatIntelliJLaf());
UIManager.setLookAndFeel(new FlatDarculaLaf());
```

### 🔧 高级功能

#### 1. 客户端属性（Client Properties）
```java
// 按钮样式自定义
JButton button = new JButton("Click Me");
button.putClientProperty("JButton.buttonType", "roundRect");
button.putClientProperty("JButton.arc", 12);

// 滚动条样式
JScrollPane scrollPane = new JScrollPane();
scrollPane.putClientProperty("JScrollBar.showButtons", true);
scrollPane.putClientProperty("JScrollBar.width", 12);
```

#### 2. 窗口装饰
```java
// 启用原生窗口装饰
JFrame.setDefaultLookAndFeelDecorated(true);
JDialog.setDefaultLookAndFeelDecorated(true);

// 检查是否支持自定义装饰
if (FlatLaf.supportsNativeWindowDecorations()) {
    // 启用自定义标题栏
}
```

#### 3. 主题自定义
```java
// 注册自定义主题属性
FlatLaf.registerCustomDefaultsSource("com.myapp.themes");

// 动态修改颜色
UIManager.put("Button.background", new Color(66, 165, 245));
UIManager.put("Panel.background", Color.WHITE);
```

### 📝 使用示例

```java
public class FlatLafDemo {
    public static void main(String[] args) {
        // 设置系统属性（可选）
        System.setProperty("flatlaf.menuBarEmbedded", "false");
        System.setProperty("flatlaf.useRoundedPopupBorder", "true");
        
        try {
            // 应用FlatLaf主题
            UIManager.setLookAndFeel(new FlatDarkLaf());
            
            // 或者根据系统主题自动选择
            if (isDarkTheme()) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        // 创建UI...
        SwingUtilities.invokeLater(() -> createGUI());
    }
}
```

---

## Ikonli - 图标库系统

**版本**: 12.4.0  
**官网**: https://kordamp.org/ikonli/  
**GitHub**: https://github.com/kordamp/ikonli

### 🎯 核心特性

Ikonli提供了统一的图标API，支持55+图标包，包括FontAwesome、Material Design、Weather icons等。

#### 主要优势
- **📦 丰富图标库**: 支持55+图标包，超过10,000个图标
- **🔍 矢量可缩放**: 所有图标都是矢量的，支持任意缩放
- **🎨 可定制样式**: 支持颜色、大小、样式自定义
- **⚡ 高性能**: 基于字体的图标，渲染性能优异
- **🔧 易于使用**: 简单的API，与Swing和JavaFX完美集成

#### 支持的图标包
- **FontAwesome**: 经典的Web图标库
- **Material Design**: Google的Material Design图标
- **Bootstrap Icons**: Bootstrap的图标集
- **Feather Icons**: 简洁的开源图标
- **Weather Icons**: 天气相关图标
- **还有50+其他图标包**...

### 🔧 核心API

#### 1. 基本使用
```java
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

// 创建图标
FontIcon icon = FontIcon.of(FontAwesome.HOME, 24);
icon.setIconColor(Color.BLUE);

// 在按钮中使用
JButton button = new JButton("Home", FontIcon.of(FontAwesome.HOME, 16));

// 在标签中使用
JLabel label = new JLabel("Settings", FontIcon.of(FontAwesome.COG, 20));
```

#### 2. 图标自定义
```java
// 创建带颜色的图标
FontIcon coloredIcon = FontIcon.of(FontAwesome.HEART, 32, Color.RED);

// 动态修改图标属性
FontIcon dynamicIcon = FontIcon.of(FontAwesome.STAR, 20);
dynamicIcon.setIconColor(Color.YELLOW);
dynamicIcon.setIconSize(24);

// 使用渐变色
GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, 20, 20, Color.RED);
dynamicIcon.setIconColor(gradient);
```

#### 3. 堆叠图标
```java
import org.kordamp.ikonli.swing.StackedFontIcon;

// 创建堆叠图标
StackedFontIcon stackedIcon = new StackedFontIcon();
stackedIcon.addIcon(FontIcon.of(FontAwesome.CIRCLE, 32, Color.BLUE));
stackedIcon.addIcon(FontIcon.of(FontAwesome.USER, 16, Color.WHITE));
```

### 📚 图标包示例

#### FontAwesome图标
```java
// 常用图标
FontIcon.of(FontAwesome.HOME, 16)           // 首页
FontIcon.of(FontAwesome.USER, 16)           // 用户
FontIcon.of(FontAwesome.COG, 16)            // 设置
FontIcon.of(FontAwesome.HEART, 16)          // 心形
FontIcon.of(FontAwesome.STAR, 16)           // 星形
FontIcon.of(FontAwesome.DOWNLOAD, 16)       // 下载
FontIcon.of(FontAwesome.UPLOAD, 16)         // 上传
```

#### Material Design图标
```java
import org.kordamp.ikonli.materialdesign.MaterialDesign;

FontIcon.of(MaterialDesign.MDI_ACCOUNT, 16)     // 账户
FontIcon.of(MaterialDesign.MDI_SETTINGS, 16)    // 设置
FontIcon.of(MaterialDesign.MDI_DELETE, 16)      // 删除
FontIcon.of(MaterialDesign.MDI_EDIT, 16)        // 编辑
```

### 📝 使用示例

```java
public class IkonliDemo extends JFrame {
    public IkonliDemo() {
        setTitle("Ikonli Demo");
        setLayout(new FlowLayout());
        
        // 不同大小的图标
        add(new JButton("Small", FontIcon.of(FontAwesome.PLUS, 12)));
        add(new JButton("Medium", FontIcon.of(FontAwesome.PLUS, 16)));
        add(new JButton("Large", FontIcon.of(FontAwesome.PLUS, 24)));
        
        // 不同颜色的图标
        JButton redButton = new JButton("Red", FontIcon.of(FontAwesome.HEART, 16));
        redButton.getIcon().setIconColor(Color.RED);
        add(redButton);
        
        // 组合图标
        JLabel statusLabel = new JLabel("Online");
        statusLabel.setIcon(FontIcon.of(FontAwesome.CIRCLE, 8, Color.GREEN));
        add(statusLabel);
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
```

---

## MiGLayout - 强大的布局管理器

**版本**: 11.4.2  
**官网**: http://www.miglayout.com/  
**GitHub**: https://github.com/mikaelgrev/miglayout

### 🎯 核心特性

MiGLayout是一个功能强大且易于使用的布局管理器，可以替代几乎所有其他布局管理器。

#### 主要优势
- **🔄 统一API**: 一个布局管理器解决所有布局需求
- **📝 人性化语法**: 使用字符串约束，易读易写
- **🎯 精确控制**: 像素级的精确布局控制
- **📱 响应式**: 支持响应式布局和组件调整
- **🔧 灵活性**: 支持网格、流式、绝对定位等多种布局

#### 布局类型支持
- **网格布局**: 类似GridBagLayout但更简单
- **流式布局**: 类似FlowLayout但功能更强
- **停靠布局**: 类似BorderLayout但更灵活
- **绝对布局**: 支持链接和相对定位
- **分组布局**: 组件分组和对齐

### 🔧 核心概念

#### 1. 布局约束（Layout Constraints）
```java
// 基本语法: "layout约束"
new MigLayout("fill, insets 10")           // 填充父容器，内边距10px
new MigLayout("debug")                     // 显示调试网格
new MigLayout("wrap 2")                    // 每行2个组件后换行
```

#### 2. 列约束（Column Constraints）
```java
// "列1约束, 列2约束, ..."
new MigLayout("", "[100!][grow][50]")      // 第1列固定100px，第2列自动增长，第3列50px
new MigLayout("", "[]20[]")                // 两列，中间间距20px
new MigLayout("", "[left][center][right]") // 左对齐、居中、右对齐列
```

#### 3. 行约束（Row Constraints）
```java
// "行1约束, 行2约束, ..."
new MigLayout("", "", "[top][grow][bottom]") // 顶部、自动增长、底部行
new MigLayout("", "", "[]10[]")              // 两行，中间间距10px
```

### 🎨 组件约束

#### 1. 基本定位
```java
panel.add(component, "cell 0 0")           // 放在第0列第0行
panel.add(component, "cell 1 0 2 1")       // 从第1列第0行开始，跨2列1行
panel.add(component, "span 2")             // 跨越2列
panel.add(component, "wrap")               // 添加后换行
```

#### 2. 大小控制
```java
panel.add(component, "width 200!")         // 固定宽度200px
panel.add(component, "height 100:150:200") // 最小100px，首选150px，最大200px
panel.add(component, "grow")               // 自动增长
panel.add(component, "shrink")             // 允许收缩
```

#### 3. 对齐方式
```java
panel.add(component, "align left")         // 左对齐
panel.add(component, "align center")       // 居中对齐
panel.add(component, "align right top")    // 右上对齐
panel.add(component, "dock north")         // 停靠到北边
```

### 📝 实际应用示例

#### 1. 简单表单布局
```java
JPanel panel = new JPanel(new MigLayout(
    "insets 20, wrap 2",           // 内边距20px，每行2个组件
    "[right][grow]",               // 第1列右对齐，第2列自动增长
    "[]10[]10[]"                   // 行间距10px
));

panel.add(new JLabel("姓名:"));
panel.add(new JTextField(), "growx");      // 水平增长

panel.add(new JLabel("密码:"));
panel.add(new JPasswordField(), "growx");

panel.add(new JButton("登录"), "span, align center"); // 跨列居中
```

#### 2. 复杂面板布局
```java
JPanel panel = new JPanel(new MigLayout(
    "fill, insets 10",             // 填充容器，内边距10px
    "[200!][grow]",                // 左侧固定200px，右侧自动增长
    "[grow][40!]"                  // 上部自动增长，下部固定40px
));

// 左侧导航
panel.add(new JTree(), "cell 0 0, grow");

// 右侧内容区
panel.add(new JScrollPane(), "cell 1 0, grow");

// 底部状态栏
panel.add(new JLabel("状态栏"), "cell 0 1 2 1, growx"); // 跨两列
```

#### 3. 响应式布局
```java
JPanel panel = new JPanel(new MigLayout(
    "fill, wrap",                  // 自动换行
    "[grow, 200:300:]",           // 列宽度在200-300px之间自适应
    ""
));

// 添加组件，会自动换行和调整大小
for (int i = 0; i < 10; i++) {
    panel.add(new JButton("Button " + i), "growx");
}
```

### 🎯 高级特性

#### 1. 组件分组
```java
// 相同组的组件会有相同的大小
panel.add(button1, "sg buttons");         // 加入buttons组
panel.add(button2, "sg buttons");         // 同一组，大小会保持一致
panel.add(button3, "sg buttons");
```

#### 2. 动态布局
```java
// 根据条件动态调整布局
String constraint = isCompactMode ? "wrap 4" : "wrap 2";
panel.setLayout(new MigLayout(constraint));
```

#### 3. 外部约束文件
```java
// 可以将约束定义在外部文件中，便于维护
MigLayout layout = new MigLayout();
layout.setLayoutConstraints("fill, insets 10");
layout.setColumnConstraints("[100!][grow]");
layout.setRowConstraints("[][grow][]");
```

---

## JGoodies Forms - 专业表单布局

**版本**: 1.9.0  
**官网**: https://www.jgoodies.com/freeware/libraries/forms/  
**GitHub**: https://github.com/JFormDesigner/swing-jgoodies-forms

### 🎯 核心特性

JGoodies Forms提供了专业级的表单布局解决方案，特别适合创建数据输入表单和对话框。

#### 主要优势
- **📋 表单专用**: 专门为表单设计的布局管理器
- **🎯 精确控制**: 像素级的精确布局控制
- **📱 国际化支持**: 完美支持不同语言和字体
- **🔧 易于维护**: 清晰的行列定义，便于修改
- **⚡ 高性能**: 优化的布局算法，快速渲染

#### 核心组件
- **FormLayout**: 主要的布局管理器
- **CellConstraints**: 单元格约束定义
- **PanelBuilder**: 面板构建助手
- **FormSpecs**: 预定义的规格常量

### 🔧 基础语法

#### 1. FormLayout构造
```java
// FormLayout(列规格, 行规格)
FormLayout layout = new FormLayout(
    "right:pref, 4dlu, pref:grow, 4dlu, pref",    // 5列
    "p, 3dlu, p, 3dlu, p"                         // 5行
);
```

#### 2. 列规格语法
```java
"pref"              // 首选大小
"min"               // 最小大小
"default"           // 默认大小
"50dlu"             // 50个对话框单位
"100px"             // 100像素
"pref:grow"         // 首选大小但可增长
"50dlu:100dlu"      // 最小50dlu，首选100dlu
"left:pref"         // 左对齐的首选大小
"center:50dlu"      // 居中的50dlu
"right:pref"        // 右对齐的首选大小
"fill:pref:grow"    // 填充，首选大小，可增长
```

#### 3. 行规格语法
```java
"p"                 // 首选高度
"top:p"             // 顶部对齐的首选高度
"center:p"          // 居中对齐的首选高度
"bottom:p"          // 底部对齐的首选高度
"fill:p:grow"       // 填充，首选高度，可增长
```

### 🎨 实际应用

#### 1. 基本表单
```java
public class BasicFormDemo {
    public static JPanel createForm() {
        FormLayout layout = new FormLayout(
            "right:pref, 4dlu, pref:grow",           // 3列：标签，间距，输入框
            "p, 3dlu, p, 3dlu, p, 9dlu, p"          // 标签行，间距行，按钮行
        );
        
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        
        // 添加组件
        builder.addLabel("姓名:", cc.xy(1, 1));
        builder.add(new JTextField(), cc.xy(3, 1));
        
        builder.addLabel("邮箱:", cc.xy(1, 3));
        builder.add(new JTextField(), cc.xy(3, 3));
        
        builder.addLabel("电话:", cc.xy(1, 5));
        builder.add(new JTextField(), cc.xy(3, 5));
        
        // 按钮行
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(new JButton("确定"));
        buttonPanel.add(new JButton("取消"));
        builder.add(buttonPanel, cc.xy(3, 7));
        
        return builder.getPanel();
    }
}
```

#### 2. 复杂表单
```java
public class AdvancedFormDemo {
    public static JPanel createAdvancedForm() {
        FormLayout layout = new FormLayout(
            "right:pref, 4dlu, pref, 4dlu, right:pref, 4dlu, pref:grow", // 7列
            "p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, fill:pref:grow, 9dlu, p" // 11行
        );
        
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        
        // 第一行：姓名和性别
        builder.addLabel("姓名:", cc.xy(1, 1));
        builder.add(new JTextField(), cc.xy(3, 1));
        builder.addLabel("性别:", cc.xy(5, 1));
        builder.add(new JComboBox<>(new String[]{"男", "女"}), cc.xy(7, 1));
        
        // 第二行：邮箱（跨列）
        builder.addLabel("邮箱:", cc.xy(1, 3));
        builder.add(new JTextField(), cc.xyw(3, 3, 5)); // 跨5列
        
        // 第三行：地址（跨列）
        builder.addLabel("地址:", cc.xy(1, 5));
        builder.add(new JTextField(), cc.xyw(3, 5, 5));
        
        // 第四行：描述（多行文本）
        builder.addLabel("描述:", cc.xy(1, 7));
        JTextArea textArea = new JTextArea(5, 20);
        builder.add(new JScrollPane(textArea), cc.xyw(3, 7, 5));
        
        // 其他信息区域
        builder.addLabel("其他:", cc.xy(1, 9));
        JPanel otherPanel = createOtherInfoPanel();
        builder.add(otherPanel, cc.xyw(3, 9, 5, "fill, fill"));
        
        // 按钮行
        JPanel buttonPanel = createButtonPanel();
        builder.add(buttonPanel, cc.xyw(1, 11, 7, "center, fill"));
        
        return builder.getPanel();
    }
}
```

#### 3. 使用预定义规格
```java
import com.jgoodies.forms.layout.FormSpecs;

FormLayout layout = new FormLayout(
    FormSpecs.LABEL_COMPONENT_GAP_COLSPEC +      // 标签列
    FormSpecs.RELATED_GAP_COLSPEC +              // 间距列  
    FormSpecs.DEFAULT_COLSPEC,                   // 组件列
    
    FormSpecs.PARAGRAPH_GAP_ROWSPEC +            // 段落间距
    FormSpecs.LINE_GAP_ROWSPEC +                 // 行间距
    FormSpecs.DEFAULT_ROWSPEC                    // 默认行
);
```

### 🎯 高级特性

#### 1. 组件分组
```java
// 创建按钮组，所有按钮大小一致
FormLayout layout = new FormLayout("pref, 4dlu, pref, 4dlu, pref", "p");
PanelBuilder builder = new PanelBuilder(layout);

JButton btn1 = new JButton("确定");
JButton btn2 = new JButton("取消");  
JButton btn3 = new JButton("帮助");

// 设置按钮组
layout.setColumnGroups(new int[][]{{1, 3, 5}}); // 第1,3,5列大小相同

builder.add(btn1, cc.xy(1, 1));
builder.add(btn2, cc.xy(3, 1));
builder.add(btn3, cc.xy(5, 1));
```

#### 2. 动态表单
```java
public class DynamicFormDemo {
    private FormLayout layout;
    private PanelBuilder builder;
    private int currentRow = 1;
    
    public void addFormField(String label, JComponent component) {
        // 动态添加行
        layout.insertRow(currentRow, FormSpecs.RELATED_GAP_ROWSPEC);
        layout.insertRow(currentRow + 1, FormSpecs.DEFAULT_ROWSPEC);
        
        CellConstraints cc = new CellConstraints();
        builder.addLabel(label, cc.xy(1, currentRow + 1));
        builder.add(component, cc.xy(3, currentRow + 1));
        
        currentRow += 2;
        builder.getPanel().revalidate();
    }
}
```

#### 3. 国际化支持
```java
// 支持从右到左的语言
FormLayout layout = new FormLayout(
    "right:pref, 4dlu, pref:grow",
    "p, 3dlu, p"
);

// 对于阿拉伯语等RTL语言，会自动调整布局方向
if (ComponentOrientation.getOrientation(Locale.getDefault()).isLeftToRight()) {
    // LTR布局
} else {
    // RTL布局，会自动镜像
}
```

---

## RSyntaxTextArea - 语法高亮编辑器

**版本**: 3.6.0  
**官网**: https://bobbylight.github.io/RSyntaxTextArea/  
**GitHub**: https://github.com/bobbylight/RSyntaxTextArea

### 🎯 核心特性

RSyntaxTextArea是一个功能强大的语法高亮文本编辑器，支持50+编程语言，是构建IDE和代码编辑器的理想选择。

#### 主要优势
- **🌈 语法高亮**: 支持50+编程语言的语法高亮
- **📁 代码折叠**: 智能代码折叠和展开
- **🔍 搜索替换**: 强大的搜索和替换功能
- **💡 自动补全**: 可配合AutoComplete库实现代码补全
- **🎨 主题系统**: 内置多种编辑器主题
- **⚡ 高性能**: 优化的渲染性能，支持大文件

#### 支持的编程语言
- **Web**: HTML, CSS, JavaScript, TypeScript, PHP, JSP
- **系统**: Java, C, C++, C#, Python, Go, Rust
- **脚本**: Shell, Perl, Ruby, Lua, PowerShell
- **数据**: SQL, XML, JSON, YAML, CSV
- **标记**: Markdown, LaTeX, RTF
- **其他**: 40+种语言...

### 🔧 基础使用

#### 1. 创建编辑器
```java
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;

public class CodeEditorDemo {
    public static JPanel createEditor() {
        // 创建语法文本区域
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        
        // 设置语法样式
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        
        // 启用代码折叠
        textArea.setCodeFoldingEnabled(true);
        
        // 启用抗锯齿
        textArea.setAntiAliasingEnabled(true);
        
        // 启用自动缩进
        textArea.setAutoIndentEnabled(true);
        
        // 启用括号匹配
        textArea.setBracketMatchingEnabled(true);
        
        // 创建滚动面板
        RTextScrollPane sp = new RTextScrollPane(textArea);
        sp.setFoldIndicatorEnabled(true);    // 显示折叠指示器
        sp.setIconRowHeaderEnabled(true);    // 显示图标行
        sp.setLineNumbersEnabled(true);      // 显示行号
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(sp, BorderLayout.CENTER);
        
        return panel;
    }
}
```

#### 2. 语法样式设置
```java
// Java语法
textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

// Python语法
textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);

// JavaScript语法
textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);

// HTML语法
textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);

// CSS语法
textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);

// SQL语法
textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);

// XML语法
textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);

// JSON语法
textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
```

### 🎨 主题和样式

#### 1. 应用主题
```java
// 从文件加载主题
try {
    InputStream in = getClass().getResourceAsStream("/themes/dark.xml");
    Theme theme = Theme.load(in);
    theme.apply(textArea);
} catch (IOException e) {
    e.printStackTrace();
}

// 内置主题
String[] themeNames = {
    "/org/fife/ui/rsyntaxtextarea/themes/default.xml",     // 默认主题
    "/org/fife/ui/rsyntaxtextarea/themes/dark.xml",        // 深色主题
    "/org/fife/ui/rsyntaxtextarea/themes/eclipse.xml",     // Eclipse主题
    "/org/fife/ui/rsyntaxtextarea/themes/idea.xml",        // IntelliJ IDEA主题
    "/org/fife/ui/rsyntaxtextarea/themes/vs.xml"           // Visual Studio主题
};
```

#### 2. 自定义语法配色
```java
// 获取语法方案
SyntaxScheme scheme = textArea.getSyntaxScheme();

// 自定义关键字颜色
scheme.getStyle(Token.RESERVED_WORD).foreground = Color.BLUE;
scheme.getStyle(Token.RESERVED_WORD).font = new Font("Consolas", Font.BOLD, 12);

// 自定义注释颜色
scheme.getStyle(Token.COMMENT_EOL).foreground = Color.GRAY;
scheme.getStyle(Token.COMMENT_MULTILINE).foreground = Color.GRAY;

// 自定义字符串颜色
scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.GREEN;

// 应用更改
textArea.revalidate();
textArea.repaint();
```

### 🔧 高级功能

#### 1. 代码折叠配置
```java
// 启用代码折叠
textArea.setCodeFoldingEnabled(true);

// 配置折叠策略
CodeFoldingManager foldManager = textArea.getFoldManager();
foldManager.setCodeFoldingEnabled(true);

// 展开所有折叠
foldManager.expandAllFolds();

// 折叠所有可折叠区域
foldManager.collapseAllFolds();

// 监听折叠事件
textArea.addPropertyChangeListener(RSyntaxTextArea.CODE_FOLDING_PROPERTY, 
    new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
            System.out.println("代码折叠状态改变");
        }
    }
);
```

#### 2. 搜索和替换
```java
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

// 创建搜索上下文
SearchContext context = new SearchContext();
context.setSearchFor("class");           // 搜索内容
context.setReplaceWith("interface");     // 替换内容
context.setRegularExpression(false);     // 是否正则表达式
context.setMatchCase(true);              // 是否区分大小写
context.setWholeWord(false);             // 是否全词匹配

// 执行搜索
boolean found = SearchEngine.find(textArea, context);

// 执行替换
boolean replaced = SearchEngine.replace(textArea, context);

// 替换所有
int count = SearchEngine.replaceAll(textArea, context);
System.out.println("替换了 " + count + " 处");
```

#### 3. 添加解析器（语法检查）
```java
import org.fife.ui.rsyntaxtextarea.parser.*;

// 创建自定义解析器
public class MyParser extends AbstractParser {
    @Override
    public ParseResult parse(RSyntaxDocument doc, String style) {
        DefaultParseResult result = new DefaultParseResult(this);
        
        // 简单的语法检查示例
        String text = doc.getText(0, doc.getLength());
        if (text.contains("TODO")) {
            int offset = text.indexOf("TODO");
            DefaultParserNotice notice = new DefaultParserNotice(
                this, "TODO项目", 0, offset, 4);
            notice.setLevel(ParserNotice.Level.INFO);
            result.addNotice(notice);
        }
        
        return result;
    }
}

// 添加解析器
textArea.addParser(new MyParser());
```

#### 4. 自定义语法高亮
```java
// 注册自定义TokenMaker
AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)
    TokenMakerFactory.getDefaultInstance();
atmf.putMapping("text/myLanguage", "com.mycompany.MyTokenMaker");

// 使用自定义语法
textArea.setSyntaxEditingStyle("text/myLanguage");
```

### 📝 实际应用示例

#### 多语言代码编辑器
```java
public class MultiLanguageEditor extends JFrame {
    private RSyntaxTextArea textArea;
    private JComboBox<String> languageCombo;
    
    public MultiLanguageEditor() {
        setupUI();
        setupEventHandlers();
    }
    
    private void setupUI() {
        setTitle("多语言代码编辑器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 工具栏
        JToolBar toolBar = new JToolBar();
        languageCombo = new JComboBox<>(new String[]{
            "Java", "Python", "JavaScript", "HTML", "CSS", "SQL"
        });
        toolBar.add(new JLabel("语言: "));
        toolBar.add(languageCombo);
        toolBar.addSeparator();
        toolBar.add(new JButton("打开"));
        toolBar.add(new JButton("保存"));
        toolBar.add(new JButton("运行"));
        
        // 编辑器
        textArea = new RSyntaxTextArea(25, 80);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
        textArea.setAutoIndentEnabled(true);
        textArea.setBracketMatchingEnabled(true);
        textArea.setPaintTabLines(true);
        textArea.setTabsEmulated(true);
        textArea.setTabSize(4);
        
        // 滚动面板
        RTextScrollPane sp = new RTextScrollPane(textArea);
        sp.setFoldIndicatorEnabled(true);
        sp.setIconRowHeaderEnabled(true);
        sp.setLineNumbersEnabled(true);
        
        add(toolBar, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        
        // 状态栏
        JLabel statusBar = new JLabel(" 行: 1, 列: 1");
        add(statusBar, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void setupEventHandlers() {
        // 语言切换
        languageCombo.addActionListener(e -> {
            String language = (String) languageCombo.getSelectedItem();
            switchLanguage(language);
        });
        
        // 光标位置更新
        textArea.addCaretListener(e -> {
            int line = textArea.getCaretLineNumber() + 1;
            int col = textArea.getCaretOffsetFromLineStart() + 1;
            statusBar.setText(" 行: " + line + ", 列: " + col);
        });
    }
    
    private void switchLanguage(String language) {
        String style;
        switch (language) {
            case "Java":
                style = SyntaxConstants.SYNTAX_STYLE_JAVA;
                textArea.setText(getJavaSample());
                break;
            case "Python":
                style = SyntaxConstants.SYNTAX_STYLE_PYTHON;
                textArea.setText(getPythonSample());
                break;
            case "JavaScript":
                style = SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT;
                textArea.setText(getJavaScriptSample());
                break;
            default:
                style = SyntaxConstants.SYNTAX_STYLE_NONE;
        }
        textArea.setSyntaxEditingStyle(style);
    }
}
```

---

## JFreeChart - 专业图表库

**版本**: 1.5.6  
**官网**: https://www.jfree.org/jfreechart/  
**GitHub**: https://github.com/jfree/jfreechart

### 🎯 核心特性

JFreeChart是Java平台上最成熟和功能强大的图表库，支持丰富的图表类型和高度定制化。

#### 主要优势
- **📊 丰富图表类型**: 支持柱状图、饼图、线图、散点图、时间序列图等
- **🎨 高度定制**: 颜色、字体、样式、动画等完全可定制
- **📤 多格式导出**: 支持PNG、JPEG、PDF、SVG等格式导出
- **📱 组件集成**: 与Swing、JavaFX完美集成
- **⚡ 高性能**: 优化的渲染性能，支持大数据集
- **🌐 国际化**: 完整的国际化支持

#### 支持的图表类型
- **分类图表**: 柱状图、堆叠柱状图、条形图、面积图
- **数值图表**: 线图、散点图、气泡图、箱线图
- **时间序列**: 时间线图、高-低-开-收图、蜡烛图
- **饼图**: 饼图、环图、多层饼图
- **其他**: 雷达图、桑基图、热力图、甘特图

### 🔧 基础使用

#### 1. 创建柱状图
```java
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartDemo {
    public static JPanel createBarChart() {
        // 创建数据集
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(85, "2023", "Java");
        dataset.addValue(70, "2023", "Python");
        dataset.addValue(75, "2023", "JavaScript");
        dataset.addValue(60, "2023", "C++");
        dataset.addValue(45, "2023", "Go");
        
        // 创建图表
        JFreeChart chart = ChartFactory.createBarChart(
            "编程语言流行度",              // 标题
            "编程语言",                   // X轴标签
            "流行度指数",                 // Y轴标签
            dataset,                     // 数据集
            PlotOrientation.VERTICAL,    // 方向
            true,                        // 显示图例
            true,                        // 显示工具提示
            false                        // 生成URL
        );
        
        // 创建图表面板
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        
        return chartPanel;
    }
}
```

#### 2. 创建饼图
```java
import org.jfree.data.general.DefaultPieDataset;

public class PieChartDemo {
    public static ChartPanel createPieChart() {
        // 创建数据集
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Java", 35.0);
        dataset.setValue("Python", 25.0);
        dataset.setValue("JavaScript", 20.0);
        dataset.setValue("C++", 12.0);
        dataset.setValue("其他", 8.0);
        
        // 创建图表
        JFreeChart chart = ChartFactory.createPieChart(
            "编程语言市场份额",           // 标题
            dataset,                    // 数据集
            true,                       // 显示图例
            true,                       // 显示工具提示
            false                       // 生成URL
        );
        
        return new ChartPanel(chart);
    }
}
```

#### 3. 创建线图
```java
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChartDemo {
    public static ChartPanel createLineChart() {
        // 创建数据系列
        XYSeries series1 = new XYSeries("系列1");
        series1.add(1.0, 2.0);
        series1.add(2.0, 3.5);
        series1.add(3.0, 2.8);
        series1.add(4.0, 4.1);
        series1.add(5.0, 3.9);
        
        XYSeries series2 = new XYSeries("系列2");
        series2.add(1.0, 1.5);
        series2.add(2.0, 2.8);
        series2.add(3.0, 3.2);
        series2.add(4.0, 2.9);
        series2.add(5.0, 4.3);
        
        // 创建数据集
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        
        // 创建图表
        JFreeChart chart = ChartFactory.createXYLineChart(
            "性能对比图",                 // 标题
            "时间(秒)",                  // X轴标签
            "数值",                     // Y轴标签
            dataset,                    // 数据集
            PlotOrientation.VERTICAL,   // 方向
            true,                       // 显示图例
            true,                       // 显示工具提示
            false                       // 生成URL
        );
        
        return new ChartPanel(chart);
    }
}
```

### 🎨 图表定制

#### 1. 样式定制
```java
public class ChartCustomization {
    public static void customizeChart(JFreeChart chart) {
        // 设置背景色
        chart.setBackgroundPaint(Color.WHITE);
        
        // 设置标题字体
        chart.getTitle().setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        // 获取绘图区域
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        
        // 设置背景色和网格线
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        
        // 自定义渲染器
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(66, 165, 245));   // 蓝色
        renderer.setSeriesPaint(1, new Color(255, 167, 38));   // 橙色
        renderer.setSeriesPaint(2, new Color(102, 187, 106));  // 绿色
        
        // 设置柱状图间距
        renderer.setItemMargin(0.1);
        
        // 设置工具提示生成器
        renderer.setDefaultToolTipGenerator(
            new StandardCategoryToolTipGenerator("{0}: {2}", 
                NumberFormat.getInstance())
        );
        
        // 自定义坐标轴
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("微软雅黑", Font.PLAIN, 12));
        domainAxis.setTickLabelFont(new Font("微软雅黑", Font.PLAIN, 10));
        
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("微软雅黑", Font.PLAIN, 12));
        rangeAxis.setTickLabelFont(new Font("微软雅黑", Font.PLAIN, 10));
    }
}
```

#### 2. 动态更新
```java
public class DynamicChartDemo extends JPanel {
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private Timer timer;
    
    public DynamicChartDemo() {
        setupChart();
        startDataUpdate();
    }
    
    private void setupChart() {
        dataset = new DefaultCategoryDataset();
        
        chart = ChartFactory.createBarChart(
            "实时数据图表", "类别", "数值", dataset,
            PlotOrientation.VERTICAL, true, true, false
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }
    
    private void startDataUpdate() {
        timer = new Timer(1000, e -> updateData());
        timer.start();
    }
    
    private void updateData() {
        // 模拟数据更新
        Random random = new Random();
        dataset.setValue(random.nextInt(100), "系列1", "A");
        dataset.setValue(random.nextInt(100), "系列1", "B");
        dataset.setValue(random.nextInt(100), "系列1", "C");
        dataset.setValue(random.nextInt(100), "系列1", "D");
    }
}
```

#### 3. 图表导出
```java
import org.jfree.chart.ChartUtils;

public class ChartExporter {
    public static void exportChart(JFreeChart chart, String filename) {
        try {
            // 导出为PNG
            ChartUtils.saveChartAsPNG(
                new File(filename + ".png"), chart, 800, 600
            );
            
            // 导出为JPEG
            ChartUtils.saveChartAsJPEG(
                new File(filename + ".jpg"), chart, 800, 600
            );
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void exportToPDF(JFreeChart chart, String filename) {
        // 需要添加iText库依赖
        try {
            Rectangle pagesize = new Rectangle(800, 600);
            Document document = new Document(pagesize);
            PdfWriter writer = PdfWriter.getInstance(
                document, new FileOutputStream(filename + ".pdf")
            );
            document.open();
            
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(800, 600);
            Graphics2D g2 = tp.createGraphics(800, 600);
            chart.draw(g2, new Rectangle2D.Double(0, 0, 800, 600));
            g2.dispose();
            cb.addTemplate(tp, 0, 0);
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 📊 高级图表类型

#### 1. 时间序列图
```java
import org.jfree.data.time.*;

public class TimeSeriesDemo {
    public static ChartPanel createTimeSeriesChart() {
        // 创建时间序列
        TimeSeries series = new TimeSeries("股价");
        
        // 添加数据点
        series.add(new Day(1, 1, 2023), 100.0);
        series.add(new Day(2, 1, 2023), 105.0);
        series.add(new Day(3, 1, 2023), 102.0);
        series.add(new Day(4, 1, 2023), 108.0);
        series.add(new Day(5, 1, 2023), 110.0);
        
        // 创建数据集
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        
        // 创建图表
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "股价走势图", "日期", "价格(元)", dataset,
            true, true, false
        );
        
        return new ChartPanel(chart);
    }
}
```

#### 2. 散点图
```java
public class ScatterPlotDemo {
    public static ChartPanel createScatterPlot() {
        XYSeries series = new XYSeries("数据点");
        
        // 生成随机数据
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            double x = random.nextGaussian() * 10 + 50;
            double y = x + random.nextGaussian() * 5;
            series.add(x, y);
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        
        JFreeChart chart = ChartFactory.createScatterPlot(
            "散点图示例", "X轴", "Y轴", dataset,
            PlotOrientation.VERTICAL, true, true, false
        );
        
        return new ChartPanel(chart);
    }
}
```

---

## Apache PDFBox - PDF文档处理

**版本**: 3.0.5  
**官网**: https://pdfbox.apache.org/  
**GitHub**: https://github.com/apache/pdfbox

### 🎯 核心特性

Apache PDFBox是一个强大的Java PDF处理库，支持PDF文档的创建、编辑、内容提取和操作。

#### 主要优势
- **📄 完整PDF支持**: 创建、编辑、合并、分割PDF文档
- **🔍 内容提取**: 提取文本、图片、元数据等内容
- **🎨 丰富功能**: 支持表单、注释、书签、数字签名
- **📱 命令行工具**: 提供丰富的命令行工具
- **⚡ 高性能**: 优化的PDF处理性能
- **🔒 安全性**: 支持PDF加密和数字签名

#### 核心功能模块
- **PDDocument**: PDF文档操作
- **PDPage**: PDF页面操作
- **PDPageContentStream**: 页面内容绘制
- **PDFTextStripper**: 文本提取
- **PDFRenderer**: PDF渲染
- **PDDocumentInformation**: 文档信息管理

### 🔧 基础使用

#### 1. 创建PDF文档
```java
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

public class CreatePDFDemo {
    public static void createSimplePDF(String filename) {
        try (PDDocument document = new PDDocument()) {
            // 创建页面
            PDPage page = new PDPage();
            document.addPage(page);
            
            // 创建内容流
            try (PDPageContentStream contentStream = 
                 new PDPageContentStream(document, page)) {
                
                // 设置字体和大小
                contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 16);
                
                // 开始文本
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Hello, PDFBox!");
                contentStream.endText();
                
                // 绘制更多内容
                contentStream.setFont(new PDType1Font(FontName.HELVETICA), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("这是使用Apache PDFBox创建的PDF文档。");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("支持中文内容显示。");
                contentStream.endText();
                
                // 绘制矩形
                contentStream.setStrokingColor(Color.BLUE);
                contentStream.setLineWidth(2);
                contentStream.addRect(100, 500, 200, 100);
                contentStream.stroke();
                
                // 填充矩形
                contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
                contentStream.addRect(350, 500, 200, 100);
                contentStream.fill();
            }
            
            // 保存文档
            document.save(filename);
            System.out.println("PDF创建成功: " + filename);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### 2. 读取和提取文本
```java
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadPDFDemo {
    public static String extractText(String filename) {
        try (PDDocument document = Loader.loadPDF(new File(filename))) {
            // 创建文本提取器
            PDFTextStripper textStripper = new PDFTextStripper();
            
            // 设置提取范围（可选）
            textStripper.setStartPage(1);
            textStripper.setEndPage(document.getNumberOfPages());
            
            // 提取文本
            String text = textStripper.getText(document);
            
            System.out.println("提取的文本内容:");
            System.out.println(text);
            
            return text;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void extractTextByPage(String filename) {
        try (PDDocument document = Loader.loadPDF(new File(filename))) {
            PDFTextStripper textStripper = new PDFTextStripper();
            
            // 逐页提取文本
            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                textStripper.setStartPage(page);
                textStripper.setEndPage(page);
                
                String pageText = textStripper.getText(document);
                System.out.println("第" + page + "页内容:");
                System.out.println(pageText);
                System.out.println("-------------------");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### 3. PDF合并和分割
```java
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;

public class PDFManipulation {
    // 合并多个PDF
    public static void mergePDFs(List<String> inputFiles, String outputFile) {
        try {
            PDFMergerUtility merger = new PDFMergerUtility();
            merger.setDestinationFileName(outputFile);
            
            for (String inputFile : inputFiles) {
                merger.addSource(new File(inputFile));
            }
            
            merger.mergeDocuments();
            System.out.println("PDF合并完成: " + outputFile);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // 分割PDF
    public static void splitPDF(String inputFile, String outputPrefix) {
        try (PDDocument document = Loader.loadPDF(new File(inputFile))) {
            Splitter splitter = new Splitter();
            splitter.setSplitAtPage(1); // 每页分割
            
            List<PDDocument> splitDocs = splitter.split(document);
            
            for (int i = 0; i < splitDocs.size(); i++) {
                PDDocument splitDoc = splitDocs.get(i);
                String outputFile = outputPrefix + "_page_" + (i + 1) + ".pdf";
                splitDoc.save(outputFile);
                splitDoc.close();
                System.out.println("分割页面保存: " + outputFile);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### 🎨 高级功能

#### 1. 添加图片
```java
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class AddImageDemo {
    public static void addImageToPDF(String pdfFile, String imageFile) {
        try (PDDocument document = Loader.loadPDF(new File(pdfFile))) {
            PDPage page = document.getPage(0);
            
            // 加载图片
            PDImageXObject image = PDImageXObject.createFromFile(imageFile, document);
            
            try (PDPageContentStream contentStream = 
                 new PDPageContentStream(document, page, 
                     PDPageContentStream.AppendMode.APPEND, true)) {
                
                // 绘制图片
                contentStream.drawImage(image, 100, 400, 200, 150);
            }
            
            // 保存文档
            document.save("output_with_image.pdf");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### 2. 创建表格
```java
public class CreateTableDemo {
    public static void createTableInPDF(String filename) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            
            try (PDPageContentStream contentStream = 
                 new PDPageContentStream(document, page)) {
                
                // 表格参数
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                
                String[][] tableData = {
                    {"姓名", "年龄", "职业", "城市"},
                    {"张三", "25", "程序员", "北京"},
                    {"李四", "30", "设计师", "上海"},
                    {"王五", "28", "产品经理", "深圳"}
                };
                
                float rowHeight = 20;
                float cellMargin = 5;
                
                // 绘制表格
                for (int i = 0; i < tableData.length; i++) {
                    String[] row = tableData[i];
                    float cellWidth = tableWidth / row.length;
                    
                    for (int j = 0; j < row.length; j++) {
                        // 绘制单元格边框
                        contentStream.addRect(margin + j * cellWidth, 
                                            yPosition - rowHeight, 
                                            cellWidth, rowHeight);
                        contentStream.stroke();
                        
                        // 添加文本
                        contentStream.beginText();
                        contentStream.setFont(new PDType1Font(FontName.HELVETICA), 10);
                        contentStream.newLineAtOffset(
                            margin + j * cellWidth + cellMargin,
                            yPosition - rowHeight + cellMargin
                        );
                        contentStream.showText(row[j]);
                        contentStream.endText();
                    }
                    yPosition -= rowHeight;
                }
            }
            
            document.save(filename);
            System.out.println("表格PDF创建成功: " + filename);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### 3. 数字签名
```java
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.*;

public class DigitalSignatureDemo {
    public static void signPDF(String inputFile, String outputFile, 
                              String keystoreFile, String keystorePassword) {
        try (PDDocument document = Loader.loadPDF(new File(inputFile));
             FileInputStream keystoreStream = new FileInputStream(keystoreFile)) {
            
            // 加载密钥库
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(keystoreStream, keystorePassword.toCharArray());
            
            // 获取私钥和证书
            String alias = keystore.aliases().nextElement();
            PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, 
                keystorePassword.toCharArray());
            Certificate[] certificateChain = keystore.getCertificateChain(alias);
            
            // 创建签名
            PDSignature signature = new PDSignature();
            signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
            signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
            signature.setName("签名者姓名");
            signature.setLocation("签名地点");
            signature.setReason("签名原因");
            signature.setSignDate(Calendar.getInstance());
            
            // 应用签名
            document.addSignature(signature, new CreateSignature(privateKey, 
                certificateChain));
            
            // 保存签名后的文档
            document.saveIncremental(new FileOutputStream(outputFile));
            System.out.println("PDF签名完成: " + outputFile);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static class CreateSignature implements SignatureInterface {
        private PrivateKey privateKey;
        private Certificate[] certificateChain;
        
        public CreateSignature(PrivateKey privateKey, Certificate[] certificateChain) {
            this.privateKey = privateKey;
            this.certificateChain = certificateChain;
        }
        
        @Override
        public byte[] sign(InputStream content) throws IOException {
            try {
                // 实现签名逻辑
                CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
                ContentSigner sha256Signer = new JcaContentSignerBuilder("SHA256WithRSA")
                    .build(privateKey);
                gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                    new JcaDigestCalculatorProviderBuilder().build())
                    .build(sha256Signer, (X509Certificate) certificateChain[0]));
                gen.addCertificates(new JcaCertStore(Arrays.asList(certificateChain)));
                
                CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
                CMSSignedData signedData = gen.generate(msg, false);
                return signedData.getEncoded();
                
            } catch (Exception e) {
                throw new IOException("签名失败", e);
            }
        }
    }
}
```

### 📱 命令行工具

PDFBox还提供了丰富的命令行工具：

```bash
# 提取文本
java -jar pdfbox-app-3.0.5.jar ExtractText input.pdf

# 转换为图片
java -jar pdfbox-app-3.0.5.jar PDFToImage input.pdf

# 合并PDF
java -jar pdfbox-app-3.0.5.jar PDFMerger output.pdf input1.pdf input2.pdf

# 分割PDF
java -jar pdfbox-app-3.0.5.jar PDFSplit input.pdf

# 加密PDF
java -jar pdfbox-app-3.0.5.jar Encrypt -userPassword user123 input.pdf output.pdf

# 解密PDF
java -jar pdfbox-app-3.0.5.jar Decrypt -password user123 input.pdf output.pdf
```

---

## GlazedLists - 高级列表操作

**版本**: 1.11.0  
**官网**: http://glazedlists.dev.java.net/  
**GitHub**: https://github.com/glazedlists/glazedlists

### 🎯 核心特性

GlazedLists提供了高性能的列表操作功能，支持排序、过滤、分组等高级操作，与Swing完美集成。

#### 主要优势
- **⚡ 高性能**: 优化的列表操作算法
- **🔄 实时更新**: 数据变化自动反映到UI
- **🎯 丰富操作**: 排序、过滤、分组、搜索
- **📱 UI集成**: 与Swing JTable、JList完美集成
- **🔧 易于使用**: 简洁的API设计
- **📊 数据绑定**: 支持数据模型绑定

#### 核心组件
- **EventList**: 基础事件列表
- **SortedList**: 排序列表
- **FilterList**: 过滤列表
- **EventTableModel**: 表格模型
- **EventListModel**: 列表模型

### 🔧 基础使用

#### 1. 创建基本EventList
```java
import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.*;

public class BasicGlazedListDemo {
    public static void createBasicList() {
        // 创建基础事件列表
        EventList<String> eventList = new BasicEventList<>();
        eventList.add("Apple");
        eventList.add("Banana");
        eventList.add("Cherry");
        eventList.add("Date");
        
        // 创建JList模型
        EventListModel<String> listModel = new EventListModel<>(eventList);
        JList<String> jList = new JList<>(listModel);
        
        // 数据变化会自动反映到UI
        Timer timer = new Timer(2000, e -> {
            eventList.add("新项目 " + System.currentTimeMillis());
        });
        timer.start();
    }
}
```

#### 2. 排序功能
```java
public class SortedListDemo {
    public static void createSortedList() {
        // 创建基础列表
        EventList<Person> baseList = new BasicEventList<>();
        baseList.add(new Person("张三", 25));
        baseList.add(new Person("李四", 30));
        baseList.add(new Person("王五", 22));
        
        // 创建排序列表
        SortedList<Person> sortedList = new SortedList<>(baseList, 
            Comparator.comparing(Person::getName)); // 按姓名排序
        
        // 或者按年龄排序
        SortedList<Person> sortedByAge = new SortedList<>(baseList,
            Comparator.comparing(Person::getAge));
        
        // 创建表格模型
        EventTableModel<Person> tableModel = new EventTableModel<>(
            sortedList, new PersonTableFormat());
        JTable table = new JTable(tableModel);
        
        // 添加新数据，会自动排序
        baseList.add(new Person("赵六", 28));
    }
    
    static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        // Getters...
        public String getName() { return name; }
        public int getAge() { return age; }
    }
}
```

#### 3. 过滤功能
```java
import ca.odell.glazedlists.matchers.*;

public class FilterListDemo {
    public static void createFilteredList() {
        EventList<Person> baseList = new BasicEventList<>();
        // 添加测试数据...
        
        // 创建过滤列表
        FilterList<Person> filteredList = new FilterList<>(baseList);
        
        // 设置过滤条件：只显示年龄大于25的人
        filteredList.setMatcher(person -> person.getAge() > 25);
        
        // 动态过滤：根据文本框内容过滤
        JTextField filterField = new JTextField();
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateFilter(); }
            public void removeUpdate(DocumentEvent e) { updateFilter(); }
            public void changedUpdate(DocumentEvent e) { updateFilter(); }
            
            private void updateFilter() {
                String text = filterField.getText().toLowerCase();
                if (text.isEmpty()) {
                    filteredList.setMatcher(null); // 显示所有
                } else {
                    filteredList.setMatcher(person -> 
                        person.getName().toLowerCase().contains(text));
                }
            }
        });
        
        // 创建UI
        EventTableModel<Person> tableModel = new EventTableModel<>(
            filteredList, new PersonTableFormat());
        JTable table = new JTable(tableModel);
    }
}

// （未完待续）
```

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

---

## Thumbnailator - 图片处理

**版本**: 0.4.20  
**官网**: http://projects.coobird.net/thumbnailator/  
**GitHub**: https://github.com/coobird/thumbnailator

### 🎯 核心特性

Thumbnailator是一个高质量的Java图片处理库，专门用于生成缩略图和图片处理。它提供了流畅的API接口，使复杂的图片处理任务能够在几行代码内完成。

#### 主要优势
- **🎨 高质量缩放**: 使用高质量的图片缩放算法
- **🔧 流畅API**: 直观的流畅接口设计，代码类似英语表达
- **⚡ 高性能**: 优化的图片处理性能
- **📁 多格式支持**: 支持JPEG、PNG、GIF、BMP等多种格式
- **🎯 零依赖**: 单个JAR文件，无外部依赖
- **🔄 批量处理**: 支持批量图片处理

#### 核心功能
- **尺寸调整**: 按像素、比例或约束调整图片大小
- **格式转换**: 在不同图片格式间转换
- **旋转和翻转**: 图片旋转和镜像翻转
- **水印添加**: 添加文字或图片水印
- **质量控制**: 精确控制输出质量
- **批量操作**: 一次处理多个图片

### 🔧 基础使用

#### 1. 简单缩略图生成
```java
import net.coobird.thumbnailator.Thumbnails;

public class BasicThumbnailDemo {
    public static void createSimpleThumbnail() throws IOException {
        // 按尺寸缩放
        Thumbnails.of("input.jpg")
                .size(200, 200)
                .toFile("thumbnail.jpg");
        
        // 按比例缩放
        Thumbnails.of("input.jpg")
                .scale(0.5)
                .toFile("half_size.jpg");
        
        // 保持宽高比的约束缩放
        Thumbnails.of("input.jpg")
                .width(300)
                .keepAspectRatio(true)
                .toFile("width_constrained.jpg");
    }
}
```

#### 2. 高级图片处理
```java
public class AdvancedThumbnailDemo {
    public static void advancedProcessing() throws IOException {
        // 复合操作：缩放、旋转、格式转换、质量控制
        Thumbnails.of("input.png")
                .size(400, 300)
                .rotate(90)
                .outputQuality(0.8)
                .outputFormat("jpg")
                .toFile("processed.jpg");
        
        // 强制尺寸（不保持宽高比）
        Thumbnails.of("input.jpg")
                .forceSize(200, 200)
                .toFile("forced_size.jpg");
        
        // 添加水印
        BufferedImage watermark = ImageIO.read(new File("watermark.png"));
        Thumbnails.of("input.jpg")
                .size(500, 400)
                .watermark(Positions.BOTTOM_RIGHT, watermark, 0.7f)
                .toFile("watermarked.jpg");
    }
}
```

#### 3. 批量处理
```java
public class BatchProcessingDemo {
    public static void batchProcess() throws IOException {
        // 批量处理目录中的所有图片
        File inputDir = new File("input_images");
        File outputDir = new File("thumbnails");
        
        Thumbnails.of(inputDir.listFiles())
                .size(200, 200)
                .outputFormat("jpg")
                .toFiles(outputDir, Rename.PREFIX_DOT_THUMBNAIL);
        
        // 批量处理指定文件
        List<String> fileNames = Arrays.asList(
            "image1.jpg", "image2.png", "image3.gif"
        );
        
        Thumbnails.fromFilenames(fileNames)
                .size(150, 150)
                .asFiles(Rename.SUFFIX_HYPHEN_THUMBNAIL);
    }
}
```

### 🎨 高级功能

#### 1. 自定义处理和过滤器
```java
public class CustomProcessingDemo {
    public static void customFilters() throws IOException {
        // 自定义图片过滤器
        Thumbnails.of("input.jpg")
                .size(400, 300)
                .addFilter(new GrayScaleFilter())    // 灰度滤镜
                .addFilter(new BlurFilter(2))        // 模糊滤镜
                .toFile("filtered.jpg");
        
        // 自定义处理器
        Thumbnails.of("input.jpg")
                .size(300, 200)
                .imageType(BufferedImage.TYPE_INT_ARGB)
                .addFilter(new CustomWatermarkFilter())
                .toFile("custom_processed.png");
    }
    
    // 自定义灰度滤镜
    private static class GrayScaleFilter implements ImageFilter {
        @Override
        public BufferedImage apply(BufferedImage img) {
            BufferedImage grayImage = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g = grayImage.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            return grayImage;
        }
    }
    
    // 自定义水印滤镜
    private static class CustomWatermarkFilter implements ImageFilter {
        @Override
        public BufferedImage apply(BufferedImage img) {
            Graphics2D g = img.createGraphics();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("© 2024", img.getWidth() - 100, img.getHeight() - 20);
            g.dispose();
            return img;
        }
    }
}
```

#### 2. 内存优化和流处理
```java
public class MemoryOptimizedDemo {
    public static void memoryOptimizedProcessing() throws IOException {
        // 流式处理，节省内存
        try (InputStream is = new FileInputStream("large_image.jpg");
             OutputStream os = new FileOutputStream("thumbnail.jpg")) {
            
            Thumbnails.of(is)
                    .size(200, 200)
                    .outputFormat("jpg")
                    .toOutputStream(os);
        }
        
        // 处理大量图片时的内存管理
        for (File imageFile : largeImageDirectory.listFiles()) {
            // 逐个处理，避免内存溢出
            Thumbnails.of(imageFile)
                    .size(300, 300)
                    .toFile(new File(outputDir, "thumb_" + imageFile.getName()));
        }
    }
}
```

#### 3. 与Swing集成
```java
public class SwingIntegrationDemo extends JFrame {
    private JLabel imageLabel;
    private BufferedImage currentImage;
    
    public SwingIntegrationDemo() {
        setupUI();
    }
    
    private void setupUI() {
        setTitle("图片处理演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 图片显示区域
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);
        
        // 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        JButton loadBtn = new JButton("加载图片");
        JButton resizeBtn = new JButton("调整大小");
        JButton rotateBtn = new JButton("旋转90°");
        JButton saveBtn = new JButton("保存");
        
        loadBtn.addActionListener(e -> loadImage());
        resizeBtn.addActionListener(e -> resizeImage());
        rotateBtn.addActionListener(e -> rotateImage());
        saveBtn.addActionListener(e -> saveImage());
        
        controlPanel.add(loadBtn);
        controlPanel.add(resizeBtn);
        controlPanel.add(rotateBtn);
        controlPanel.add(saveBtn);
        
        add(controlPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                currentImage = ImageIO.read(selectedFile);
                displayImage(currentImage);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "加载图片失败: " + e.getMessage());
            }
        }
    }
    
    private void resizeImage() {
        if (currentImage == null) return;
        
        try {
            currentImage = Thumbnails.of(currentImage)
                    .size(currentImage.getWidth() / 2, currentImage.getHeight() / 2)
                    .asBufferedImage();
            displayImage(currentImage);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "调整大小失败: " + e.getMessage());
        }
    }
    
    private void rotateImage() {
        if (currentImage == null) return;
        
        try {
            currentImage = Thumbnails.of(currentImage)
                    .rotate(90)
                    .scale(1.0)
                    .asBufferedImage();
            displayImage(currentImage);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "旋转失败: " + e.getMessage());
        }
    }
    
    private void saveImage() {
        if (currentImage == null) return;
        
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File saveFile = fileChooser.getSelectedFile();
                Thumbnails.of(currentImage)
                        .scale(1.0)
                        .toFile(saveFile);
                JOptionPane.showMessageDialog(this, "保存成功!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "保存失败: " + e.getMessage());
            }
        }
    }
    
    private void displayImage(BufferedImage image) {
        if (image != null) {
            ImageIcon icon = new ImageIcon(image);
            imageLabel.setIcon(icon);
            pack();
        }
    }
}
```

### 💡 最佳实践

#### 1. 性能优化技巧
```java
public class PerformanceTips {
    public static void optimizationTechniques() throws IOException {
        // 1. 对于大图片，先缩放再做其他处理
        Thumbnails.of("large_image.jpg")
                .scale(0.5)               // 先缩放
                .rotate(45)               // 再旋转
                .toFile("optimized.jpg");
        
        // 2. 批量处理时使用迭代器，避免内存问题
        Iterable<BufferedImage> thumbnails = Thumbnails.of(imageFiles)
                .size(200, 200)
                .iterableBufferedImages();
        
        int count = 0;
        for (BufferedImage thumbnail : thumbnails) {
            if (thumbnail != null) {
                ImageIO.write(thumbnail, "jpg", new File("thumb_" + count++ + ".jpg"));
            }
        }
        
        // 3. 使用适当的图片质量设置
        Thumbnails.of("input.jpg")
                .size(300, 200)
                .outputQuality(0.8)       // 80%质量，平衡文件大小和质量
                .toFile("balanced.jpg");
    }
}
```

#### 2. 错误处理和验证
```java
public class ErrorHandlingDemo {
    public static void robustImageProcessing() {
        try {
            // 验证输入文件
            File inputFile = new File("input.jpg");
            if (!inputFile.exists()) {
                throw new FileNotFoundException("输入文件不存在");
            }
            
            // 检查文件格式
            String fileName = inputFile.getName().toLowerCase();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && 
                !fileName.endsWith(".gif")) {
                throw new IllegalArgumentException("不支持的文件格式");
            }
            
            // 安全的图片处理
            Thumbnails.of(inputFile)
                    .size(200, 200)
                    .keepAspectRatio(true)
                    .toFile("safe_thumbnail.jpg");
                    
        } catch (IOException e) {
            System.err.println("图片处理失败: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("未知错误: " + e.getMessage());
        }
    }
}
```

---

## LoboEvolution - HTML渲染引擎

**版本**: 3.0  
**官网**: https://sourceforge.net/projects/loboevolution/  
**GitHub**: https://github.com/LoboEvolution/LoboEvolution

### 🎯 核心特性

LoboEvolution是一个纯Java的Web浏览器和HTML渲染引擎，支持HTML 4、HTML 5、JavaScript、CSS2/CSS3和Java (Swing/AWT)渲染。

#### 主要优势
- **🌐 完整Web支持**: 支持HTML 4、HTML 5、JavaScript、CSS2/CSS3
- **☕ 纯Java实现**: 完全用Java编写，无需外部依赖
- **🔧 可扩展架构**: 支持插件和扩展开发
- **📱 Swing集成**: 完美集成到Swing应用程序中
- **🔒 安全性**: 内置安全特性和沙箱机制

#### 核心组件
- **CobraEvolution**: HTML渲染和解析引擎
- **HTML解析器**: 支持现代HTML标准的解析器
- **CSS引擎**: 支持CSS2和CSS3的样式引擎
- **JavaScript引擎**: 内置JavaScript执行环境
- **网络组件**: HTTP/HTTPS网络请求处理

### 🔧 基础使用

#### 1. 基本HTML渲染
```java
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.html.renderer.HtmlPanel;

public class BasicHTMLDemo {
    public static void createHTMLRenderer() {
        // 创建HTML面板
        HtmlPanel htmlPanel = new HtmlPanel();
        
        // 简单HTML内容
        String htmlContent = """
            <html>
            <head>
                <title>LoboEvolution Demo</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .header { color: #2c3e50; border-bottom: 2px solid #3498db; }
                    .content { margin-top: 20px; line-height: 1.6; }
                    .highlight { background-color: #f39c12; padding: 5px; }
                </style>
            </head>
            <body>
                <h1 class="header">欢迎使用LoboEvolution</h1>
                <div class="content">
                    <p>这是一个<span class="highlight">纯Java</span>的HTML渲染引擎演示。</p>
                    <ul>
                        <li>支持HTML 5标准</li>
                        <li>支持CSS样式</li>
                        <li>支持JavaScript</li>
                        <li>完美集成Swing</li>
                    </ul>
                </div>
            </body>
            </html>
            """;
        
        // 设置HTML内容
        htmlPanel.setHtml(htmlContent, "http://localhost/");
        
        // 创建窗口显示
        JFrame frame = new JFrame("LoboEvolution HTML Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(htmlPanel));
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

#### 2. 加载外部网页
```java
public class WebPageLoaderDemo {
    public static void loadWebPage() {
        HtmlPanel htmlPanel = new HtmlPanel();
        
        // 加载外部网页
        try {
            URL url = new URL("https://example.com");
            htmlPanel.navigate(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        // 添加导航监听器
        htmlPanel.addNavigationListener(new NavigationListener() {
            @Override
            public void beforeNavigate(NavigationEvent event) {
                System.out.println("即将导航到: " + event.getUrl());
            }
            
            @Override
            public void afterNavigate(NavigationEvent event) {
                System.out.println("导航完成: " + event.getUrl());
            }
            
            @Override
            public void navigationFailed(NavigationEvent event) {
                System.out.println("导航失败: " + event.getUrl());
            }
        });
        
        // 创建带导航功能的浏览器界面
        createBrowserFrame(htmlPanel);
    }
    
    private static void createBrowserFrame(HtmlPanel htmlPanel) {
        JFrame frame = new JFrame("LoboEvolution 浏览器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // 地址栏
        JPanel addressPanel = new JPanel(new BorderLayout());
        JTextField addressField = new JTextField();
        JButton goButton = new JButton("转到");
        
        addressPanel.add(new JLabel("地址: "), BorderLayout.WEST);
        addressPanel.add(addressField, BorderLayout.CENTER);
        addressPanel.add(goButton, BorderLayout.EAST);
        
        // 按钮事件
        goButton.addActionListener(e -> {
            try {
                URL url = new URL(addressField.getText());
                htmlPanel.navigate(url);
            } catch (MalformedURLException ex) {
                JOptionPane.showMessageDialog(frame, "无效的URL: " + ex.getMessage());
            }
        });
        
        // 组装界面
        frame.add(addressPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(htmlPanel), BorderLayout.CENTER);
        
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

#### 3. JavaScript交互
```java
public class JavaScriptInteractionDemo {
    public static void createInteractiveDemo() {
        HtmlPanel htmlPanel = new HtmlPanel();
        
        // 包含JavaScript的HTML
        String htmlWithJS = """
            <html>
            <head>
                <title>JavaScript交互演示</title>
                <style>
                    body { font-family: Arial, sans-serif; padding: 20px; }
                    .button { padding: 10px 20px; margin: 5px; }
                    #output { border: 1px solid #ccc; padding: 10px; margin-top: 20px; }
                </style>
            </head>
            <body>
                <h2>JavaScript交互演示</h2>
                
                <button class="button" onclick="showMessage()">显示消息</button>
                <button class="button" onclick="changeColor()">改变颜色</button>
                <button class="button" onclick="addContent()">添加内容</button>
                
                <div id="output">
                    <p>点击上面的按钮查看JavaScript效果</p>
                </div>
                
                <script>
                    function showMessage() {
                        alert('Hello from LoboEvolution JavaScript!');
                    }
                    
                    function changeColor() {
                        var output = document.getElementById('output');
                        var colors = ['#ffebee', '#e8f5e8', '#e3f2fd', '#fff3e0'];
                        var randomColor = colors[Math.floor(Math.random() * colors.length)];
                        output.style.backgroundColor = randomColor;
                    }
                    
                    function addContent() {
                        var output = document.getElementById('output');
                        var newP = document.createElement('p');
                        newP.textContent = '新添加的内容: ' + new Date().toLocaleTimeString();
                        output.appendChild(newP);
                    }
                    
                    // 页面加载完成后的初始化
                    window.onload = function() {
                        console.log('页面加载完成');
                        document.getElementById('output').innerHTML += 
                            '<p><em>JavaScript引擎已就绪</em></p>';
                    };
                </script>
            </body>
            </html>
            """;
        
        htmlPanel.setHtml(htmlWithJS, "http://localhost/");
        
        // 创建窗口
        JFrame frame = new JFrame("JavaScript交互演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(htmlPanel));
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
```

### 🎨 高级功能

#### 1. 自定义用户代理和请求处理
```java
public class CustomUserAgentDemo {
    public static void createCustomUserAgent() {
        // 自定义用户代理上下文
        UserAgentContext customContext = new UserAgentContext() {
            @Override
            public String getUserAgent() {
                return "LoboEvolution/3.0 (Custom Java Browser)";
            }
            
            @Override
            public boolean isCookieEnabled() {
                return true;
            }
            
            @Override
            public boolean isScriptingEnabled() {
                return true;
            }
            
            @Override
            public String getVendor() {
                return "Custom Browser Vendor";
            }
        };
        
        // 使用自定义上下文创建HTML面板
        HtmlPanel htmlPanel = new HtmlPanel();
        htmlPanel.setUserAgentContext(customContext);
        
        // 添加自定义请求拦截器
        htmlPanel.addRequestInterceptor(new RequestInterceptor() {
            @Override
            public boolean interceptRequest(String url) {
                System.out.println("拦截请求: " + url);
                // 可以在这里添加自定义逻辑，如阻止某些请求
                if (url.contains("ads")) {
                    return false; // 阻止广告请求
                }
                return true; // 允许请求
            }
        });
    }
}
```

#### 2. 嵌入式浏览器组件
```java
public class EmbeddedBrowserDemo extends JFrame {
    private HtmlPanel htmlPanel;
    private JTextField addressBar;
    private JButton backButton, forwardButton, refreshButton;
    private List<String> history;
    private int historyIndex;
    
    public EmbeddedBrowserDemo() {
        history = new ArrayList<>();
        historyIndex = -1;
        setupUI();
        setupEventHandlers();
    }
    
    private void setupUI() {
        setTitle("嵌入式浏览器 - LoboEvolution");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 工具栏
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        backButton = new JButton("◀");
        forwardButton = new JButton("▶");
        refreshButton = new JButton("⟳");
        addressBar = new JTextField();
        JButton goButton = new JButton("转到");
        
        backButton.setEnabled(false);
        forwardButton.setEnabled(false);
        
        toolBar.add(backButton);
        toolBar.add(forwardButton);
        toolBar.add(refreshButton);
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(new JLabel("地址: "));
        toolBar.add(addressBar);
        toolBar.add(goButton);
        
        // HTML面板
        htmlPanel = new HtmlPanel();
        
        // 状态栏
        JLabel statusBar = new JLabel("就绪");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // 组装界面
        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(htmlPanel), BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        
        setSize(900, 700);
        setLocationRelativeTo(null);
    }
    
    private void setupEventHandlers() {
        // 地址栏回车事件
        addressBar.addActionListener(e -> navigateToUrl(addressBar.getText()));
        
        // 按钮事件
        backButton.addActionListener(e -> goBack());
        forwardButton.addActionListener(e -> goForward());
        refreshButton.addActionListener(e -> refresh());
        
        // 导航监听器
        htmlPanel.addNavigationListener(new NavigationListener() {
            @Override
            public void beforeNavigate(NavigationEvent event) {
                statusBar.setText("正在加载: " + event.getUrl());
            }
            
            @Override
            public void afterNavigate(NavigationEvent event) {
                statusBar.setText("加载完成: " + event.getUrl());
                addToHistory(event.getUrl().toString());
                addressBar.setText(event.getUrl().toString());
                updateNavigationButtons();
            }
            
            @Override
            public void navigationFailed(NavigationEvent event) {
                statusBar.setText("加载失败: " + event.getUrl());
                JOptionPane.showMessageDialog(EmbeddedBrowserDemo.this, 
                    "无法加载页面: " + event.getUrl());
            }
        });
        
        // 默认加载页面
        loadHomePage();
    }
    
    private void navigateToUrl(String urlString) {
        try {
            if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
                urlString = "http://" + urlString;
            }
            URL url = new URL(urlString);
            htmlPanel.navigate(url);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(this, "无效的URL: " + e.getMessage());
        }
    }
    
    private void addToHistory(String url) {
        // 移除当前位置之后的历史记录
        while (history.size() > historyIndex + 1) {
            history.remove(history.size() - 1);
        }
        
        history.add(url);
        historyIndex = history.size() - 1;
    }
    
    private void goBack() {
        if (historyIndex > 0) {
            historyIndex--;
            navigateToUrl(history.get(historyIndex));
        }
    }
    
    private void goForward() {
        if (historyIndex < history.size() - 1) {
            historyIndex++;
            navigateToUrl(history.get(historyIndex));
        }
    }
    
    private void refresh() {
        if (historyIndex >= 0 && historyIndex < history.size()) {
            navigateToUrl(history.get(historyIndex));
        }
    }
    
    private void updateNavigationButtons() {
        backButton.setEnabled(historyIndex > 0);
        forwardButton.setEnabled(historyIndex < history.size() - 1);
    }
    
    private void loadHomePage() {
        String homePage = """
            <html>
            <head>
                <title>LoboEvolution 主页</title>
                <style>
                    body { 
                        font-family: 'Segoe UI', Arial, sans-serif; 
                        margin: 0; 
                        padding: 20px; 
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                    }
                    .container { 
                        max-width: 800px; 
                        margin: 0 auto; 
                        text-align: center; 
                    }
                    .card { 
                        background: rgba(255,255,255,0.1); 
                        padding: 20px; 
                        border-radius: 10px; 
                        margin: 20px 0; 
                        backdrop-filter: blur(10px);
                    }
                    .links a { 
                        color: #FFD700; 
                        text-decoration: none; 
                        margin: 0 15px; 
                        font-size: 18px;
                    }
                    .links a:hover { text-decoration: underline; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>欢迎使用 LoboEvolution 浏览器</h1>
                    
                    <div class="card">
                        <h2>功能特点</h2>
                        <ul style="text-align: left; display: inline-block;">
                            <li>纯Java实现的HTML渲染引擎</li>
                            <li>支持HTML 5和CSS 3</li>
                            <li>内置JavaScript引擎</li>
                            <li>完美集成Swing应用程序</li>
                            <li>支持现代Web标准</li>
                        </ul>
                    </div>
                    
                    <div class="card">
                        <h2>快速链接</h2>
                        <div class="links">
                            <a href="https://github.com/LoboEvolution/LoboEvolution">GitHub</a>
                            <a href="https://sourceforge.net/projects/loboevolution/">SourceForge</a>
                            <a href="javascript:showInfo()">关于</a>
                        </div>
                    </div>
                </div>
                
                <script>
                    function showInfo() {
                        alert('LoboEvolution - 纯Java Web浏览器\\n版本: 3.0\\n© 2024');
                    }
                </script>
            </body>
            </html>
            """;
        
        htmlPanel.setHtml(homePage, "http://localhost/home");
        addToHistory("http://localhost/home");
        addressBar.setText("http://localhost/home");
        updateNavigationButtons();
    }
}
```

### 💡 实际应用场景

#### 1. 帮助文档查看器
```java
public class HelpDocumentViewer extends JDialog {
    private HtmlPanel htmlPanel;
    private String helpBasePath;
    
    public HelpDocumentViewer(JFrame parent, String helpBasePath) {
        super(parent, "帮助文档", true);
        this.helpBasePath = helpBasePath;
        setupUI();
        loadHelpIndex();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // 创建HTML面板
        htmlPanel = new HtmlPanel();
        
        // 添加链接点击处理
        htmlPanel.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    String url = e.getURL().toString();
                    if (url.startsWith("help://")) {
                        loadHelpPage(url.substring(7));
                    }
                }
            }
        });
        
        add(new JScrollPane(htmlPanel), BorderLayout.CENTER);
        
        // 关闭按钮
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        setSize(600, 500);
        setLocationRelativeTo(getParent());
    }
    
    private void loadHelpIndex() {
        String indexHtml = generateHelpIndex();
        htmlPanel.setHtml(indexHtml, "help://index");
    }
    
    private void loadHelpPage(String pageName) {
        try {
            String helpContent = loadHelpContent(pageName);
            htmlPanel.setHtml(helpContent, "help://" + pageName);
        } catch (IOException e) {
            showErrorPage("无法加载帮助页面: " + pageName);
        }
    }
    
    private String generateHelpIndex() {
        return """
            <html>
            <head>
                <title>帮助文档</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .toc { border: 1px solid #ddd; padding: 15px; border-radius: 5px; }
                    .toc a { text-decoration: none; color: #2980b9; }
                    .toc a:hover { text-decoration: underline; }
                    .section { margin: 10px 0; }
                </style>
            </head>
            <body>
                <h1>应用程序帮助文档</h1>
                
                <div class="toc">
                    <h2>目录</h2>
                    <div class="section">
                        <h3>入门指南</h3>
                        <ul>
                            <li><a href="help://getting-started">快速开始</a></li>
                            <li><a href="help://installation">安装说明</a></li>
                            <li><a href="help://first-steps">第一步</a></li>
                        </ul>
                    </div>
                    
                    <div class="section">
                        <h3>功能说明</h3>
                        <ul>
                            <li><a href="help://features">主要功能</a></li>
                            <li><a href="help://advanced">高级功能</a></li>
                            <li><a href="help://customization">自定义设置</a></li>
                        </ul>
                    </div>
                    
                    <div class="section">
                        <h3>故障排除</h3>
                        <ul>
                            <li><a href="help://troubleshooting">常见问题</a></li>
                            <li><a href="help://faq">FAQ</a></li>
                            <li><a href="help://contact">联系支持</a></li>
                        </ul>
                    </div>
                </div>
            </body>
            </html>
            """;
    }
    
    private String loadHelpContent(String pageName) throws IOException {
        // 这里可以从文件系统或资源加载实际的帮助内容
        return switch (pageName) {
            case "getting-started" -> generateGettingStartedPage();
            case "features" -> generateFeaturesPage();
            case "troubleshooting" -> generateTroubleshootingPage();
            default -> generateNotFoundPage(pageName);
        };
    }
    
    private String generateGettingStartedPage() {
        return """
            <html>
            <head><title>快速开始</title></head>
            <body>
                <h1>快速开始</h1>
                <p>欢迎使用我们的应用程序！这个页面将帮助您快速上手。</p>
                
                <h2>第一步：启动应用程序</h2>
                <p>双击桌面图标或从开始菜单启动应用程序。</p>
                
                <h2>第二步：配置设置</h2>
                <p>首次运行时，系统会引导您完成基本配置。</p>
                
                <p><a href="help://index">返回目录</a></p>
            </body>
            </html>
            """;
    }
    
    private String generateFeaturesPage() {
        return """
            <html>
            <head><title>主要功能</title></head>
            <body>
                <h1>主要功能</h1>
                <p>本应用程序提供以下主要功能：</p>
                
                <ul>
                    <li><strong>功能A</strong>：详细描述功能A的用途和使用方法</li>
                    <li><strong>功能B</strong>：详细描述功能B的用途和使用方法</li>
                    <li><strong>功能C</strong>：详细描述功能C的用途和使用方法</li>
                </ul>
                
                <p><a href="help://index">返回目录</a></p>
            </body>
            </html>
            """;
    }
    
    private String generateTroubleshootingPage() {
        return """
            <html>
            <head><title>故障排除</title></head>
            <body>
                <h1>故障排除</h1>
                
                <h2>常见问题及解决方案</h2>
                
                <h3>问题1：应用程序无法启动</h3>
                <p><strong>解决方案：</strong>检查Java版本是否满足要求，确保系统有足够内存。</p>
                
                <h3>问题2：功能无响应</h3>
                <p><strong>解决方案：</strong>重启应用程序，检查网络连接。</p>
                
                <p><a href="help://index">返回目录</a></p>
            </body>
            </html>
            """;
    }
    
    private String generateNotFoundPage(String pageName) {
        return """
            <html>
            <head><title>页面未找到</title></head>
            <body>
                <h1>页面未找到</h1>
                <p>抱歉，无法找到页面：%s</p>
                <p><a href="help://index">返回目录</a></p>
            </body>
            </html>
            """.formatted(pageName);
    }
    
    private void showErrorPage(String error) {
        String errorHtml = """
            <html>
            <head><title>错误</title></head>
            <body>
                <h1>加载错误</h1>
                <p>%s</p>
                <p><a href="help://index">返回目录</a></p>
            </body>
            </html>
            """.formatted(error);
        
        htmlPanel.setHtml(errorHtml, "help://error");
    }
}
```

---

## jSystemThemeDetector - 系统主题检测

**版本**: 3.6  
**官网**: https://github.com/Dansoftowner/jSystemThemeDetector  
**GitHub**: https://github.com/Dansoftowner/jSystemThemeDetector

### 🎯 核心特性

jSystemThemeDetector是一个轻量级的Java库，用于检测操作系统的当前主题模式（深色/浅色），并监听主题变化事件。

#### 主要优势
- **🌓 主题检测**: 自动检测系统深色/浅色主题
- **🔄 实时监听**: 监听系统主题变化事件
- **🎯 跨平台**: 支持Windows、macOS、Linux
- **⚡ 轻量级**: 极小的库大小，无外部依赖
- **🔧 易于集成**: 简单的API，易于集成到现有应用

#### 支持的平台
- **Windows 10/11**: 检测系统深色模式设置
- **macOS**: 检测系统外观设置（深色/浅色）
- **Linux**: 检测GNOME、KDE等桌面环境的主题设置

### 🔧 基础使用

#### 1. 检测当前系统主题
```java
import com.jthemedetecor.OsThemeDetector;

public class BasicThemeDetection {
    public static void detectCurrentTheme() {
        // 获取主题检测器实例
        OsThemeDetector detector = OsThemeDetector.getDetector();
        
        // 检测当前主题
        boolean isDarkTheme = detector.isDark();
        
        System.out.println("当前系统主题: " + (isDarkTheme ? "深色" : "浅色"));
        
        // 根据主题应用不同的设置
        if (isDarkTheme) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    }
    
    private static void applyDarkTheme() {
        System.out.println("应用深色主题");
        // 这里添加深色主题的具体实现
    }
    
    private static void applyLightTheme() {
        System.out.println("应用浅色主题");
        // 这里添加浅色主题的具体实现
    }
}
```

#### 2. 监听主题变化
```java
public class ThemeChangeListener {
    public static void setupThemeListener() {
        OsThemeDetector detector = OsThemeDetector.getDetector();
        
        // 注册主题变化监听器
        detector.registerListener(isDark -> {
            System.out.println("系统主题已变更为: " + (isDark ? "深色" : "浅色"));
            
            // 在事件调度线程中更新UI
            SwingUtilities.invokeLater(() -> {
                updateApplicationTheme(isDark);
            });
        });
        
        System.out.println("主题监听器已注册，等待主题变化...");
    }
    
    private static void updateApplicationTheme(boolean isDark) {
        // 更新应用程序主题的具体实现
        try {
            if (isDark) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            
            // 更新所有已创建的窗口
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
            
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("主题切换失败: " + e.getMessage());
        }
    }
}
```

#### 3. 完整的主题管理系统
```java
public class ThemeManager {
    private static ThemeManager instance;
    private OsThemeDetector detector;
    private boolean darkMode;
    private List<ThemeChangeListener> listeners;
    
    private ThemeManager() {
        detector = OsThemeDetector.getDetector();
        darkMode = detector.isDark();
        listeners = new ArrayList<>();
        
        // 注册系统主题变化监听
        detector.registerListener(this::onSystemThemeChanged);
    }
    
    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }
    
    public boolean isDarkMode() {
        return darkMode;
    }
    
    public void addThemeChangeListener(ThemeChangeListener listener) {
        listeners.add(listener);
    }
    
    public void removeThemeChangeListener(ThemeChangeListener listener) {
        listeners.remove(listener);
    }
    
    public void setThemeMode(ThemeMode mode) {
        boolean newDarkMode;
        switch (mode) {
            case DARK:
                newDarkMode = true;
                break;
            case LIGHT:
                newDarkMode = false;
                break;
            case SYSTEM:
            default:
                newDarkMode = detector.isDark();
                break;
        }
        
        if (newDarkMode != darkMode) {
            darkMode = newDarkMode;
            notifyThemeChanged();
        }
    }
    
    private void onSystemThemeChanged(boolean isDark) {
        if (isDark != darkMode) {
            darkMode = isDark;
            SwingUtilities.invokeLater(this::notifyThemeChanged);
        }
    }
    
    private void notifyThemeChanged() {
        for (ThemeChangeListener listener : listeners) {
            try {
                listener.onThemeChanged(darkMode);
            } catch (Exception e) {
                System.err.println("主题变化监听器执行失败: " + e.getMessage());
            }
        }
    }
    
    // 主题模式枚举
    public enum ThemeMode {
        LIGHT("浅色"),
        DARK("深色"),
        SYSTEM("跟随系统");
        
        private final String displayName;
        
        ThemeMode(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // 主题变化监听器接口
    @FunctionalInterface
    public interface ThemeChangeListener {
        void onThemeChanged(boolean isDarkMode);
    }
}
```

### 🎨 实际应用示例

#### 1. 自适应主题应用程序
```java
public class AdaptiveThemeApp extends JFrame {
    private ThemeManager themeManager;
    private JComboBox<ThemeManager.ThemeMode> themeModeCombo;
    private JLabel statusLabel;
    
    public AdaptiveThemeApp() {
        themeManager = ThemeManager.getInstance();
        setupUI();
        setupThemeHandling();
        
        // 应用初始主题
        applyTheme(themeManager.isDarkMode());
    }
    
    private void setupUI() {
        setTitle("自适应主题演示应用");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 主要内容区域
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 标题
        JLabel titleLabel = new JLabel("自适应主题演示", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        // 中心内容
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        
        JLabel infoLabel = new JLabel("此应用程序会自动适应系统主题变化", JLabel.CENTER);
        centerPanel.add(infoLabel);
        
        // 主题模式选择
        JPanel themeModePanel = new JPanel(new FlowLayout());
        themeModePanel.add(new JLabel("主题模式:"));
        themeModeCombo = new JComboBox<>(ThemeManager.ThemeMode.values());
        themeModeCombo.setSelectedItem(ThemeManager.ThemeMode.SYSTEM);
        themeModePanel.add(themeModeCombo);
        centerPanel.add(themeModePanel);
        
        // 一些示例组件
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(new JButton("主要按钮"));
        buttonPanel.add(new JButton("次要按钮"));
        buttonPanel.add(new JButton("危险按钮"));
        centerPanel.add(buttonPanel);
        
        // 状态信息
        statusLabel = new JLabel("", JLabel.CENTER);
        centerPanel.add(statusLabel);
        
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
        
        // 底部状态栏
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.add(new JLabel("就绪"));
        add(statusBar, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void setupThemeHandling() {
        // 添加主题变化监听器
        themeManager.addThemeChangeListener(this::applyTheme);
        
        // 主题模式选择事件
        themeModeCombo.addActionListener(e -> {
            ThemeManager.ThemeMode selectedMode = 
                (ThemeManager.ThemeMode) themeModeCombo.getSelectedItem();
            themeManager.setThemeMode(selectedMode);
        });
        
        // 更新当前状态
        updateStatus();
    }
    
    private void applyTheme(boolean isDarkMode) {
        try {
            // 应用Look and Feel
            if (isDarkMode) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            
            // 更新UI
            SwingUtilities.updateComponentTreeUI(this);
            
            // 自定义颜色调整
            if (isDarkMode) {
                getContentPane().setBackground(Color.DARK_GRAY);
            } else {
                getContentPane().setBackground(Color.WHITE);
            }
            
            updateStatus();
            
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("主题应用失败: " + e.getMessage());
        }
    }
    
    private void updateStatus() {
        boolean isDark = themeManager.isDarkMode();
        String themeText = isDark ? "深色主题" : "浅色主题";
        String timeText = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        
        statusLabel.setText(String.format("当前主题: %s (更新时间: %s)", themeText, timeText));
    }
    
    public static void main(String[] args) {
        // 设置系统属性
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "自适应主题演示");
        
        SwingUtilities.invokeLater(() -> {
            new AdaptiveThemeApp().setVisible(true);
        });
    }
}
```

#### 2. 主题感知的组件库
```java
public class ThemeAwareComponents {
    
    // 主题感知按钮
    public static class ThemeAwareButton extends JButton {
        private Color lightBackground = new Color(0x007AFF);
        private Color darkBackground = new Color(0x0A84FF);
        private Color lightForeground = Color.WHITE;
        private Color darkForeground = Color.WHITE;
        
        public ThemeAwareButton(String text) {
            super(text);
            setupThemeAwareness();
        }
        
        private void setupThemeAwareness() {
            ThemeManager.getInstance().addThemeChangeListener(this::updateBackground);
            updateBackground(ThemeManager.getInstance().isDarkMode());
        }
        
        private void updateBackground(boolean isDarkMode) {
            if (isDarkMode) {
                setBackground(darkBackground);
            } else {
                setBackground(lightBackground);
            }
            repaint();
        }
    }
    
    // 主题感知文本区域
    public static class ThemeAwareTextArea extends JTextArea {
        private Color lightBackground = Color.WHITE;
        private Color darkBackground = new Color(0x2D2D2D);
        private Color lightForeground = Color.BLACK;
        private Color darkForeground = Color.WHITE;
        
        public ThemeAwareTextArea() {
            setupThemeAwareness();
        }
        
        public ThemeAwareTextArea(String text) {
            super(text);
            setupThemeAwareness();
        }
        
        private void setupThemeAwareness() {
            ThemeManager.getInstance().addThemeChangeListener(this::updateColors);
            updateColors(ThemeManager.getInstance().isDarkMode());
        }
        
        private void updateColors(boolean isDarkMode) {
            if (isDarkMode) {
                setBackground(darkBackground);
                setForeground(darkForeground);
                setCaretColor(darkForeground);
            } else {
                setBackground(lightBackground);
                setForeground(lightForeground);
                setCaretColor(lightForeground);
            }
            repaint();
        }
    }
}
```

### 💡 最佳实践

#### 1. 性能优化
```java
public class OptimizedThemeManager {
    private static final int DEBOUNCE_DELAY = 300; // 300ms防抖延迟
    private Timer debounceTimer;
    
    public OptimizedThemeManager() {
        // 使用防抖技术避免频繁的主题切换
        debounceTimer = new Timer(DEBOUNCE_DELAY, e -> applyThemeChange());
        debounceTimer.setRepeats(false);
        
        OsThemeDetector.getDetector().registerListener(isDark -> {
            // 重启防抖计时器
            debounceTimer.restart();
        });
    }
    
    private void applyThemeChange() {
        SwingUtilities.invokeLater(() -> {
            // 执行实际的主题变更操作
            boolean isDark = OsThemeDetector.getDetector().isDark();
            updateApplicationTheme(isDark);
        });
    }
    
    private void updateApplicationTheme(boolean isDark) {
        // 批量更新，提高性能
        Component[] components = collectAllComponents();
        
        // 临时禁用重绘
        for (Component comp : components) {
            if (comp instanceof JComponent) {
                ((JComponent) comp).setDoubleBuffered(false);
            }
        }
        
        try {
            // 应用主题
            applyLookAndFeel(isDark);
            
            // 更新组件
            for (Component comp : components) {
                SwingUtilities.updateComponentTreeUI(comp);
            }
        } finally {
            // 重新启用重绘
            for (Component comp : components) {
                if (comp instanceof JComponent) {
                    ((JComponent) comp).setDoubleBuffered(true);
                }
            }
        }
    }
}
```

#### 2. 主题配置管理
```java
public class ThemeConfiguration {
    private static final String CONFIG_FILE = "theme.properties";
    private Properties config;
    
    public ThemeConfiguration() {
        config = new Properties();
        loadConfiguration();
    }
    
    public void saveThemePreference(ThemeManager.ThemeMode mode) {
        config.setProperty("theme.mode", mode.name());
        saveConfiguration();
    }
    
    public ThemeManager.ThemeMode getThemePreference() {
        String mode = config.getProperty("theme.mode", "SYSTEM");
        try {
            return ThemeManager.ThemeMode.valueOf(mode);
        } catch (IllegalArgumentException e) {
            return ThemeManager.ThemeMode.SYSTEM;
        }
    }
    
    public boolean isAutoThemeEnabled() {
        return Boolean.parseBoolean(config.getProperty("theme.auto", "true"));
    }
    
    public void setAutoThemeEnabled(boolean enabled) {
        config.setProperty("theme.auto", String.valueOf(enabled));
        saveConfiguration();
    }
    
    private void loadConfiguration() {
        try (InputStream is = new FileInputStream(CONFIG_FILE)) {
            config.load(is);
        } catch (IOException e) {
            // 使用默认配置
            config.setProperty("theme.mode", "SYSTEM");
            config.setProperty("theme.auto", "true");
        }
    }
    
    private void saveConfiguration() {
        try (OutputStream os = new FileOutputStream(CONFIG_FILE)) {
            config.store(os, "主题配置");
        } catch (IOException e) {
            System.err.println("保存主题配置失败: " + e.getMessage());
        }
    }
}
```

---

## XChart - 轻量级图表库

**版本**: 3.8.8  
**官网**: https://knowm.org/open-source/xchart/  
**GitHub**: https://github.com/knowm/XChart

### 🎯 核心特性

XChart是一个轻量级和便捷的Java数据绘图库，旨在从数据到图表用最少的时间，并消除图表样式定制的猜测工作。创建XYChart实例，向其添加数据系列，然后显示或保存为位图。

#### 主要优势
- **⚡ 轻量级**: 库文件小，启动快速
- **🎯 简单易用**: 最少2行代码即可创建图表
- **🎨 多种图表**: 支持线图、柱状图、饼图、散点图等
- **📱 Swing集成**: 完美集成到Swing应用程序
- **🔧 高度定制**: 丰富的样式和配置选项
- **💾 多格式导出**: 支持PNG、JPEG、PDF、SVG等格式

#### 支持的图表类型
- **XYChart**: 线图、散点图、面积图
- **CategoryChart**: 柱状图、条形图、线图
- **PieChart**: 饼图、环形图
- **BubbleChart**: 气泡图
- **HeatMapChart**: 热力图

### 🔧 基础使用

#### 1. 快速开始
```java
import org.knowm.xchart.*;

public class QuickChartDemo {
    public static void createSimpleChart() throws IOException {
        // 准备数据
        double[] xData = new double[] { 0.0, 1.0, 2.0, 3.0, 4.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0, 1.0, 2.0 };
        
        // 创建图表
        XYChart chart = QuickChart.getChart("示例图表", "X轴", "Y轴", "数据系列", xData, yData);
        
        // 显示图表
        new SwingWrapper<>(chart).displayChart();
        
        // 保存图表
        BitmapEncoder.savePNG(chart, "./示例图表.png");
        
        // 保存高分辨率图表
        BitmapEncoder.savePNGWithDPI(chart, "./示例图表_300DPI.png", 300);
    }
}
```

#### 2. 多系列图表
```java
public class MultiSeriesDemo {
    public static void createMultiSeriesChart() {
        // 创建图表
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("多系列折线图")
                .xAxisTitle("时间")
                .yAxisTitle("数值")
                .build();
        
        // 第一个数据系列
        double[] x1 = {1, 2, 3, 4, 5};
        double[] y1 = {10, 15, 20, 18, 25};
        chart.addSeries("系列1", x1, y1);
        
        // 第二个数据系列
        double[] x2 = {1, 2, 3, 4, 5};
        double[] y2 = {5, 8, 12, 15, 20};
        chart.addSeries("系列2", x2, y2);
        
        // 第三个数据系列
        double[] x3 = {1, 2, 3, 4, 5};
        double[] y3 = {15, 12, 8, 10, 14};
        chart.addSeries("系列3", x3, y3);
        
        // 自定义样式
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setMarkerSize(8);
        
        // 显示图表
        new SwingWrapper<>(chart).displayChart();
    }
}
```

#### 3. 分类图表
```java
public class CategoryChartDemo {
    public static void createCategoryChart() {
        // 创建分类图表
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("季度销售报告")
                .xAxisTitle("季度")
                .yAxisTitle("销售额 (万元)")
                .build();
        
        // 准备数据
        List<String> quarters = Arrays.asList("Q1", "Q2", "Q3", "Q4");
        List<Number> sales2023 = Arrays.asList(120, 135, 150, 145);
        List<Number> sales2024 = Arrays.asList(130, 145, 160, 155);
        
        // 添加数据系列
        chart.addSeries("2023年", quarters, sales2023);
        chart.addSeries("2024年", quarters, sales2024);
        
        // 自定义样式
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        chart.getStyler().setOverlapped(true);
        chart.getStyler().setBarWidthPercentage(0.8);
        
        // 显示图表
        new SwingWrapper<>(chart).displayChart();
    }
}
```

### 🎨 高级功能

#### 1. 实时数据图表
```java
public class RealTimeChartDemo extends JFrame {
    private XYChart chart;
    private SwingWrapper<XYChart> swingWrapper;
    private List<Double> timeData;
    private List<Double> valueData;
    private Timer timer;
    private double time = 0;
    
    public RealTimeChartDemo() {
        setupChart();
        setupUI();
        startDataGeneration();
    }
    
    private void setupChart() {
        // 创建图表
        chart = new XYChartBuilder()
                .width(600)
                .height(400)
                .title("实时数据监控")
                .xAxisTitle("时间")
                .yAxisTitle("数值")
                .build();
        
        // 初始化数据
        timeData = new ArrayList<>();
        valueData = new ArrayList<>();
        
        // 添加初始数据系列
        chart.addSeries("传感器数据", timeData, valueData);
        
        // 配置样式
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setMarkerSize(4);
        chart.getStyler().setXAxisDecimalPattern("#.#");
    }
    
    private void setupUI() {
        setTitle("XChart 实时数据演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 图表面板
        swingWrapper = new SwingWrapper<>(chart);
        JPanel chartPanel = swingWrapper.getXChartPanel();
        add(chartPanel, BorderLayout.CENTER);
        
        // 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton startButton = new JButton("开始");
        JButton stopButton = new JButton("停止");
        JButton clearButton = new JButton("清除");
        
        startButton.addActionListener(e -> startDataGeneration());
        stopButton.addActionListener(e -> stopDataGeneration());
        clearButton.addActionListener(e -> clearData());
        
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(clearButton);
        
        add(controlPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void startDataGeneration() {
        if (timer != null) {
            timer.stop();
        }
        
        timer = new Timer(100, e -> {
            // 生成新数据点
            time += 0.1;
            double value = Math.sin(time) + 0.5 * Math.sin(3 * time) + 
                          0.1 * (Math.random() - 0.5);
            
            timeData.add(time);
            valueData.add(value);
            
            // 限制数据点数量，保持性能
            if (timeData.size() > 200) {
                timeData.remove(0);
                valueData.remove(0);
            }
            
            // 更新图表
            chart.updateXYSeries("传感器数据", timeData, valueData, null);
            swingWrapper.repaintChart();
        });
        
        timer.start();
    }
    
    private void stopDataGeneration() {
        if (timer != null) {
            timer.stop();
        }
    }
    
    private void clearData() {
        timeData.clear();
        valueData.clear();
        time = 0;
        chart.updateXYSeries("传感器数据", timeData, valueData, null);
        swingWrapper.repaintChart();
    }
}
```

#### 2. 多种图表类型组合
```java
public class MultiChartDemo extends JFrame {
    public MultiChartDemo() {
        setupUI();
    }
    
    private void setupUI() {
        setTitle("多图表展示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));
        
        // 线图
        add(createLineChartPanel());
        
        // 柱状图
        add(createBarChartPanel());
        
        // 饼图
        add(createPieChartPanel());
        
        // 散点图
        add(createScatterPlotPanel());
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private JPanel createLineChartPanel() {
        XYChart chart = new XYChartBuilder()
                .width(300)
                .height(250)
                .title("温度趋势")
                .build();
        
        // 生成示例数据
        List<Double> hours = new ArrayList<>();
        List<Double> temperatures = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hours.add((double) i);
            temperatures.add(20 + 10 * Math.sin(i * Math.PI / 12) + 
                           2 * (Math.random() - 0.5));
        }
        
        chart.addSeries("温度", hours, temperatures);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        
        return new XChartPanel<>(chart);
    }
    
    private JPanel createBarChartPanel() {
        CategoryChart chart = new CategoryChartBuilder()
                .width(300)
                .height(250)
                .title("月度销售")
                .build();
        
        List<String> months = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
        List<Number> sales = Arrays.asList(120, 135, 150, 145, 160, 155);
        
        chart.addSeries("销售额", months, sales);
        chart.getStyler().setDefaultSeriesRenderStyle(
            CategorySeries.CategorySeriesRenderStyle.Bar);
        
        return new XChartPanel<>(chart);
    }
    
    private JPanel createPieChartPanel() {
        PieChart chart = new PieChartBuilder()
                .width(300)
                .height(250)
                .title("市场份额")
                .build();
        
        chart.addSeries("产品A", 35);
        chart.addSeries("产品B", 25);
        chart.addSeries("产品C", 20);
        chart.addSeries("产品D", 15);
        chart.addSeries("其他", 5);
        
        return new XChartPanel<>(chart);
    }
    
    private JPanel createScatterPlotPanel() {
        XYChart chart = new XYChartBuilder()
                .width(300)
                .height(250)
                .title("相关性分析")
                .build();
        
        // 生成散点数据
        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < 50; i++) {
            double x = random.nextGaussian() * 10 + 50;
            double y = x * 0.8 + random.nextGaussian() * 5 + 10;
            xData.add(x);
            yData.add(y);
        }
        
        chart.addSeries("数据点", xData, yData);
        chart.getStyler().setDefaultSeriesRenderStyle(
            XYSeries.XYSeriesRenderStyle.Scatter);
        
        return new XChartPanel<>(chart);
    }
}
```

#### 3. 图表导出和打印
```java
public class ChartExportDemo {
    public static void exportCharts() throws IOException {
        // 创建示例图表
        XYChart chart = QuickChart.getChart("导出示例", "X", "Y", "数据", 
                                          new double[]{1,2,3,4,5}, 
                                          new double[]{2,4,3,5,6});
        
        // 导出为不同格式
        exportToPNG(chart);
        exportToJPEG(chart);
        exportToPDF(chart);
        exportToSVG(chart);
    }
    
    private static void exportToPNG(XYChart chart) throws IOException {
        // 标准PNG导出
        BitmapEncoder.savePNG(chart, "./chart.png");
        
        // 高分辨率PNG导出
        BitmapEncoder.savePNGWithDPI(chart, "./chart_300dpi.png", 300);
        
        // 自定义尺寸PNG导出
        BufferedImage image = BitmapEncoder.getBufferedImage(chart);
        ImageIO.write(image, "PNG", new File("./chart_custom.png"));
    }
    
    private static void exportToJPEG(XYChart chart) throws IOException {
        // JPEG导出
        BitmapEncoder.saveJPG(chart, "./chart.jpg");
        
        // 高质量JPEG导出
        BitmapEncoder.saveJPGWithQuality(chart, "./chart_hq.jpg", 0.95f);
    }
    
    private static void exportToPDF(XYChart chart) throws IOException {
        // PDF导出
        PDFEncoder.savePDF(chart, "./chart.pdf");
    }
    
    private static void exportToSVG(XYChart chart) throws IOException {
        // SVG导出（矢量格式）
        SVGEncoder.saveSVG(chart, "./chart.svg");
    }
    
    // 打印图表
    public static void printChart(XYChart chart) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new ChartPrintable(chart));
        
        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                System.err.println("打印失败: " + e.getMessage());
            }
        }
    }
    
    // 图表打印适配器
    private static class ChartPrintable implements Printable {
        private XYChart chart;
        
        public ChartPrintable(XYChart chart) {
            this.chart = chart;
        }
        
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }
            
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
            // 缩放图表以适应页面
            double scaleX = pageFormat.getImageableWidth() / chart.getWidth();
            double scaleY = pageFormat.getImageableHeight() / chart.getHeight();
            double scale = Math.min(scaleX, scaleY);
            
            g2d.scale(scale, scale);
            
            // 绘制图表
            chart.paint(g2d, chart.getWidth(), chart.getHeight());
            
            return PAGE_EXISTS;
        }
    }
}
```

### 💡 样式定制

#### 1. 主题和颜色
```java
public class ChartStylingDemo {
    public static void demonstrateThemes() {
        // 创建基础图表
        XYChart chart = new XYChartBuilder()
                .width(600)
                .height(400)
                .title("样式演示")
                .build();
        
        // 添加数据
        chart.addSeries("数据", Arrays.asList(1,2,3,4,5), Arrays.asList(2,4,3,5,1));
        
        // 应用不同主题
        applyCustomTheme(chart);
    }
    
    private static void applyCustomTheme(XYChart chart) {
        XYStyler styler = chart.getStyler();
        
        // 主题颜色
        styler.setChartBackgroundColor(Color.WHITE);
        styler.setPlotBackgroundColor(Color.WHITE);
        styler.setPlotBorderColor(Color.LIGHT_GRAY);
        
        // 图例样式
        styler.setLegendPosition(Styler.LegendPosition.OutsideE);
        styler.setLegendBackgroundColor(Color.WHITE);
        styler.setLegendBorderColor(Color.BLACK);
        
        // 坐标轴样式
        styler.setAxisTitlesVisible(true);
        styler.setAxisTitleFont(new Font("Arial", Font.BOLD, 14));
        styler.setAxisTickLabelsFont(new Font("Arial", Font.PLAIN, 12));
        
        // 网格线
        styler.setPlotGridLinesVisible(true);
        styler.setPlotGridLinesColor(Color.LIGHT_GRAY);
        
        // 数据系列样式
        styler.setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        styler.setMarkerSize(8);
        styler.setSeriesColors(new Color[]{
            new Color(0x1f77b4),  // 蓝色
            new Color(0xff7f0e),  // 橙色
            new Color(0x2ca02c),  // 绿色
            new Color(0xd62728),  // 红色
            new Color(0x9467bd)   // 紫色
        });
    }
    
    // Matlab风格主题
    private static void applyMatlabTheme(XYChart chart) {
        XYStyler styler = chart.getStyler();
        styler.setTheme(Styler.ChartTheme.Matlab);
    }
    
    // GGPlot2风格主题
    private static void applyGGPlot2Theme(XYChart chart) {
        XYStyler styler = chart.getStyler();
        styler.setTheme(Styler.ChartTheme.GGPlot2);
    }
}
```

---

## DockingFrames - 停靠框架

**版本**: 1.1.1  
**官网**: https://www.docking-frames.org/  
**GitHub**: https://github.com/Benoker/DockingFrames

### 🎯 核心特性

DockingFrames提供了一种组织面板的方式，使用户可以拖放它们。DockingFrames的理念是具有尽可能多的灵活性。

#### 主要优势
- **🔄 灵活停靠**: 支持复杂的停靠和拖拽操作
- **🎨 多种主题**: 内置多种外观主题
- **🔧 高度可定制**: 丰富的定制选项和扩展点
- **📱 Swing集成**: 与Swing组件完美集成
- **💾 布局持久化**: 支持布局的保存和恢复
- **🎯 专业级**: 适合构建专业的IDE和复杂应用

#### 核心组件
- **CControl**: 主控制器，管理所有停靠组件
- **CDockable**: 可停靠的面板接口
- **CGrid**: 网格布局助手
- **CLocation**: 位置定义系统

### 🔧 基础使用

#### 1. 简单的停靠示例
```java
import bibliothek.gui.dock.common.*;

public class BasicDockingDemo {
    public static void createBasicDemo() {
        // 创建主窗口
        JFrame frame = new JFrame("DockingFrames 基础演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 创建控制器
        CControl control = new CControl(frame);
        frame.add(control.getContentArea());
        
        // 创建网格布局
        CGrid grid = new CGrid(control);
        
        // 添加可停靠组件到网格
        grid.add(0, 0, 1, 1, createDockable("红色面板", Color.RED));
        grid.add(0, 1, 1, 1, createDockable("绿色面板", Color.GREEN));
        grid.add(1, 0, 1, 1, createDockable("蓝色面板", Color.BLUE));
        grid.add(1, 1, 1, 1, createDockable("黄色面板", Color.YELLOW));
        
        // 部署网格
        control.getContentArea().deploy(grid);
        
        // 添加单独的停靠组件
        DefaultSingleCDockable blackPanel = createDockable("黑色面板", Color.BLACK);
        control.addDockable(blackPanel);
        blackPanel.setLocation(CLocation.base().minimalNorth());
        blackPanel.setVisible(true);
        
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static DefaultSingleCDockable createDockable(String title, Color color) {
        // 创建内容面板
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setOpaque(true);
        panel.add(new JLabel(title, JLabel.CENTER));
        
        // 创建可停靠组件
        DefaultSingleCDockable dockable = new DefaultSingleCDockable(
            title.toLowerCase().replace(" ", "_"), // 唯一ID
            title, // 显示标题
            panel  // 内容
        );
        
        dockable.setCloseable(true);     // 可关闭
        dockable.setMinimizable(true);   // 可最小化
        dockable.setMaximizable(true);   // 可最大化
        
        return dockable;
    }
}
```

#### 2. 复杂的IDE风格布局
```java
public class IDELayoutDemo extends JFrame {
    private CControl control;
    private DefaultSingleCDockable projectExplorer;
    private DefaultSingleCDockable codeEditor;
    private DefaultSingleCDockable console;
    private DefaultSingleCDockable properties;
    private DefaultSingleCDockable outline;
    
    public IDELayoutDemo() {
        setupUI();
        createDockables();
        layoutDockables();
    }
    
    private void setupUI() {
        setTitle("IDE布局演示 - DockingFrames");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 创建停靠控制器
        control = new CControl(this);
        add(control.getContentArea());
        
        // 设置主题
        control.setTheme(ThemeMap.KEY_ECLIPSE_THEME);
        
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
    
    private void createDockables() {
        // 项目资源管理器
        projectExplorer = new DefaultSingleCDockable("project", "项目资源管理器", 
                                                   createProjectExplorerPanel());
        projectExplorer.setCloseable(false);
        
        // 代码编辑器
        codeEditor = new DefaultSingleCDwareness() {
            ThemeManager.getInstance().addThemeChangeListener(this::updateColors);
            updateColors(ThemeManager.getInstance().isDarkMode());
        }
        
        private void updateColors(boolean isDarkMode) {
            if (isDarkMode) {
                setBackground(darkBackground);
                setForeground(darkForeground);
            } else {
                setBackground(lightBackground);
                setForeground(lightForeground);
            }
            repaint();
        }
    }
    
    // 主题感知面板
    public static class ThemeAwarePanel extends JPanel {
        private Color lightBackground = Color.WHITE;
        private Color darkBackground = new Color(0x1E1E1E);
        
        public ThemeAwarePanel() {
            setupThemeAwareness();
        }
        
        public ThemeAwarePanel(LayoutManager layout) {
            super(layout);
            setupThemeAwareness();
        }}}

// （未完待续）
```