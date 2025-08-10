package pama1234.dstar.ui;

import net.miginfocom.swing.MigLayout;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import pama1234.dstar.Main;
import pama1234.dstar.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 代码编辑器管理器
 * 负责管理代码编辑器的创建和功能
 */
public class CodeEditorManager {
    private RSyntaxTextArea codeEditor;
    private JComboBox<String> syntaxChooser;
    private JComboBox<String> editorThemeChooser;

    // 需要刷新文本的组件引用
    private JLabel syntaxLabel;
    private JLabel editorThemeLabel;
    private JButton loadSampleBtn;
    private JButton clearBtn;

    // 编辑器主题映射表 - 修复主题文件路径
    private final Map<String, String> editorThemes = new LinkedHashMap<>();

    public CodeEditorManager() {
        // 修复：使用RSyntaxTextArea内置的主题文件路径
        editorThemes.put("pama1234_light", "/pama1234/themes/pama1234-light.xml");
        editorThemes.put("pama1234_dark", "/pama1234/themes/pama1234-dark.xml");
        editorThemes.put("rsyntax_default", "/org/fife/ui/rsyntaxtextarea/themes/default.xml");
        editorThemes.put("rsyntax_dark", "/org/fife/ui/rsyntaxtextarea/themes/dark.xml");
    }

    /**
     * 创建代码编辑器面板
     */
    public JPanel createCodeEditorPanel() {
        JPanel panel = new JPanel(new MigLayout("fill", "[grow]", "[30!][grow]"));

        // 编辑器控制面板
        JPanel editorControls = createControlPanel();

        // 创建代码编辑器
        createCodeEditor();

        // 根据当前应用程序主题应用编辑器主题
        applyEditorThemeBasedOnAppTheme();

        RTextScrollPane scrollPane = new RTextScrollPane(codeEditor);
        scrollPane.setFoldIndicatorEnabled(true);
        scrollPane.setLineNumbersEnabled(true);
        scrollPane.setIconRowHeaderEnabled(true);

        panel.add(editorControls, "cell 0 0, grow");
        panel.add(scrollPane, "cell 0 1, grow");

        return panel;
    }

    /**
     * 创建控制面板
     */
    private JPanel createControlPanel() {
        JPanel editorControls = new JPanel(new MigLayout("", "[][][][][][grow]", ""));

        syntaxLabel = new JLabel(Main.getLanguageManager().getString("editor.syntax.label"));
        syntaxChooser = new JComboBox<>(new String[]{
                "Java", "Python", "JavaScript", "HTML", "CSS", "XML", "JSON", "SQL"
        });

        editorThemeLabel = new JLabel(Main.getLanguageManager().getString("editor.themes.label"));
        editorThemeChooser = new JComboBox<>();
        editorThemeChooser.addItem(Main.getLanguageManager().getString("editor.themes.pama1234_light"));
        editorThemeChooser.addItem(Main.getLanguageManager().getString("editor.themes.pama1234_dark"));
        editorThemeChooser.addItem(Main.getLanguageManager().getString("editor.themes.rsyntax_default"));
        editorThemeChooser.addItem(Main.getLanguageManager().getString("editor.themes.rsyntax_dark"));

        loadSampleBtn = new JButton(Main.getLanguageManager().getString("editor.button.load_sample"), createIcon(FontAwesome.FILE_CODE_O));
        clearBtn = new JButton(Main.getLanguageManager().getString("editor.button.clear"), createIcon(FontAwesome.ERASER));

        // 事件处理 - 语法
        syntaxChooser.addActionListener(e -> {
            String selected = (String) syntaxChooser.getSelectedItem();
            if (selected != null) {
                setSyntaxStyle(selected);
                loadSampleCode(selected);
            }
        });

        // 事件处理 - 编辑器主题
        editorThemeChooser.addActionListener(e -> {
            String selectedThemeName = (String) editorThemeChooser.getSelectedItem();
            if (selectedThemeName != null) {
                applyEditorTheme(selectedThemeName);
            }
        });

        loadSampleBtn.addActionListener(e -> {
            String selectedSyntax = (String) syntaxChooser.getSelectedItem();
            if (selectedSyntax != null) {
                loadSampleCode(selectedSyntax);
            }
        });
        clearBtn.addActionListener(e -> codeEditor.setText(""));

        editorControls.add(syntaxLabel);
        editorControls.add(syntaxChooser);
        editorControls.add(editorThemeLabel);
        editorControls.add(editorThemeChooser);
        editorControls.add(loadSampleBtn);
        editorControls.add(clearBtn);

        return editorControls;
    }

    /**
     * 创建图标
     */
    private FontIcon createIcon(FontAwesome icon) {
        return FontIcon.of(icon, 14);
    }

    /**
     * 创建代码编辑器
     */
    private void createCodeEditor() {
        codeEditor = new RSyntaxTextArea(20, 60);
        codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        codeEditor.setCodeFoldingEnabled(true);
        codeEditor.setAntiAliasingEnabled(true);
        codeEditor.setAutoIndentEnabled(true);
        codeEditor.setBracketMatchingEnabled(true);
        codeEditor.setPaintTabLines(true);
        codeEditor.setTabsEmulated(true);
        codeEditor.setTabSize(4);

        // 应用自定义字体
        Font customFont = FontManager.getInstance().getCustomFont();
        if (customFont != null) {
            codeEditor.setFont(customFont.deriveFont(Font.PLAIN, 14));
        }

        // 加载默认代码
        loadSampleCode("Java");
    }

    /**
     * 设置语法高亮样式
     */
    private void setSyntaxStyle(String language) {
        switch (language) {
            case "Java":
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                break;
            case "Python":
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
                break;
            case "JavaScript":
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
                break;
            case "HTML":
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
                break;
            case "CSS":
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
                break;
            case "XML":
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
                break;
            case "JSON":
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
                break;
            case "SQL":
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
                break;
            default:
                codeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
                break;
        }
    }

    /**
     * 加载示例代码
     */
    private void loadSampleCode(String language) {
        String code = getSampleCode(language);
        codeEditor.setText(code);
        codeEditor.setCaretPosition(0);
    }

    /**
     * 获取示例代码
     */
    private String getSampleCode(String language) {
        switch (language) {
            case "Java":
                return """
                    public class SwingEnhancedDemo {
                        public static void main(String[] args) {
                            System.out.println("Hello, Enhanced Swing!");
                            
                            // 演示现代化Swing开发
                            SwingUtilities.invokeLater(() -> {
                                createModernUI();
                            });
                        }
                        
                        private static void createModernUI() {
                            // 使用FlatLaf主题
                            // UIManager.setLookAndFeel(new FlatDarkLaf()); // 不在此处直接设置
                            
                            // 创建主界面
                            JFrame frame = new JFrame("Modern Swing App");
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            
                            // 使用MiGLayout布局管理器
                            frame.setLayout(new MigLayout("fill"));
                            
                            frame.setVisible(true);
                        }
                    }
                    """;
            case "Python":
                return """
                    # Python 示例代码 - 现代化桌面应用开发
                    import tkinter as tk
                    from tkinter import ttk
                    import matplotlib.pyplot as plt
                    from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
                    
                    class ModernApp:
                        def __init__(self):
                            self.root = tk.Tk()
                            self.root.title("Modern Python GUI")
                            self.root.geometry("800x600")
                            
                            self.create_widgets()
                            
                        def create_widgets(self):
                            # 创建现代化界面组件
                            style = ttk.Style()
                            style.theme_use('clam')
                            
                            # 主框架
                            main_frame = ttk.Frame(self.root, padding="10")
                            main_frame.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))
                            
                            # 添加图表
                            self.create_chart(main_frame)
                            
                        def create_chart(self, parent):
                            # 创建matplotlib图表
                            fig, ax = plt.subplots(figsize=(6, 4))
                            ax.plot([1, 2, 3, 4], [1, 4, 2, 3])
                            ax.set_title('示例图表')
                            
                            canvas = FigureCanvasTkAgg(fig, parent)
                            canvas.draw()
                            canvas.get_tk_widget().grid(row=0, column=0)
                            
                        def run(self):
                            self.root.mainloop()
                    
                    if __name__ == "__main__":
                        app = ModernApp()
                        app.run()
                    """;
            case "JavaScript":
                return """
                    // 现代化JavaScript - ES6+特性演示
                    class ModernJSDemo {
                        constructor() {
                            this.data = new Map();
                            this.observers = new Set();
                            this.initializeApp();
                        }
                        
                        async initializeApp() {
                            try {
                                // 使用async/await异步编程
                                const config = await this.loadConfig();
                                console.log('应用初始化完成', config);
                                
                                // 使用箭头函数和解构赋值
                                const { theme, language } = config;
                                this.applyTheme(theme);
                                
                                // 使用模板字符串
                                document.title = `现代JS应用 - ${language}`;
                                
                            } catch (error) {
                                console.error('初始化失败:', error);
                            }
                        }
                        
                        async loadConfig() {
                            // 模拟API调用
                            return new Promise(resolve => {
                                setTimeout(() => {
                                    resolve({
                                        theme: 'dark',
                                        language: 'zh-CN',
                                        features: ['charts', 'editor', 'themes']
                                    });
                                }, 1000);
                            });
                        }
                        
                        applyTheme(theme) {
                            // 使用CSS变量动态切换主题
                            const root = document.documentElement;
                            if (theme === 'dark') {
                                root.style.setProperty('--bg-color', '#2c3e50');
                                root.style.setProperty('--text-color', '#ecf0f1');
                            } else {
                                root.style.setProperty('--bg-color', '#ecf0f1');
                                root.style.setProperty('--text-color', '#2c3e50');
                            }
                        }
                    }
                    
                    // 使用现代化的模块化开发
                    const app = new ModernJSDemo();
                    """;
            case "HTML":
                return """
                    <!DOCTYPE html>
                    <html lang="zh-CN">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>现代化Web应用演示</title>
                        <style>
                            :root {
                                --primary-color: #3498db;
                                --secondary-color: #2c3e50;
                                --background: #ecf0f1;
                                --text: #2c3e50;
                                --shadow: 0 4px 6px rgba(0,0,0,0.1);
                            }
                            
                            * {
                                margin: 0;
                                padding: 0;
                                box-sizing: border-box;
                            }
                            
                            body {
                                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                                background: var(--background);
                                color: var(--text);
                                line-height: 1.6;
                            }
                            
                            .container {
                                max-width: 1200px;
                                margin: 0 auto;
                                padding: 2rem;
                            }
                            
                            .card {
                                background: white;
                                border-radius: 12px;
                                padding: 2rem;
                                margin-bottom: 2rem;
                                box-shadow: var(--shadow);
                                transition: transform 0.3s ease;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <header class="header">
                                <h1>现代化Web应用演示</h1>
                                <p>使用HTML5、CSS3和现代JavaScript技术构建</p>
                            </header>
                        </div>
                    </body>
                    </html>
                    """;
            case "CSS":
                return """
                    /* 现代化CSS样式演示 */
                    
                    /* CSS自定义属性（变量） */
                    :root {
                        --primary-hue: 210;
                        --primary-saturation: 79%;
                        --primary-lightness: 46%;
                        --primary-color: hsl(var(--primary-hue), var(--primary-saturation), var(--primary-lightness));
                        --primary-light: hsl(var(--primary-hue), var(--primary-saturation), 66%);
                        --primary-dark: hsl(var(--primary-hue), var(--primary-saturation), 26%);
                        
                        --spacing-xs: 0.25rem;
                        --spacing-sm: 0.5rem;
                        --spacing-md: 1rem;
                        --spacing-lg: 2rem;
                        --spacing-xl: 4rem;
                        
                        --border-radius: 8px;
                        --shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
                    }
                    
                    /* 现代化重置样式 */
                    *, *::before, *::after {
                        box-sizing: border-box;
                    }
                    
                    body {
                        margin: 0;
                        font-family: system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif;
                        line-height: 1.6;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        min-height: 100vh;
                    }
                    """;
            case "XML":
                return """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <!-- Swing Enhanced Demo Configuration -->
                    <configuration>
                        <application>
                            <name>DStar Launcher</name>
                            <version>2.0.0</version>
                            <developer>pama1234</developer>
                        </application>
                        
                        <settings>
                            <theme default="system">
                                <option id="light" display="Light Mode"/>
                                <option id="dark" display="Dark Mode"/>
                                <option id="intellij" display="IntelliJ Darcula"/>
                            </theme>
                            <language default="system">
                                <option id="en" display="English"/>
                                <option id="zh_CN" display="简体中文"/>
                            </language>
                        </settings>
                        
                        <plugins>
                            <plugin id="chart-module" enabled="true">
                                <description>Advanced charting capabilities using JFreeChart.</description>
                                <dependencies>
                                    <dependency>jfreechart:1.5.6</dependency>
                                </dependencies>
                            </plugin>
                        </plugins>
                    </configuration>
                    """;
            case "JSON":
                return """
                    {
                      "name": "swing-enhanced-demo",
                      "version": "2.0.0",
                      "description": "现代化Swing库演示应用程序",
                      "author": "pama1234",
                      "main": "pama1234.dstar.Main",
                      "dependencies": {
                        "flatlaf": "3.6.1",
                        "ikonli-swing": "12.4.0",
                        "miglayout-swing": "11.4.2",
                        "jfreechart": "1.5.6"
                      },
                      "features": [
                        {
                          "name": "Modern Themes",
                          "description": "FlatLaf主题支持",
                          "enabled": true
                        },
                        {
                          "name": "Syntax Highlighting",
                          "description": "RSyntaxTextArea语法高亮",
                          "languages": ["Java", "Python", "JavaScript", "HTML", "CSS", "SQL"]
                        }
                      ]
                    }
                    """;
            case "SQL":
                return """
                    -- 现代化数据库设计演示
                    -- 创建用户管理系统的数据库结构
                    
                    CREATE DATABASE IF NOT EXISTS swing_demo_db 
                    CHARACTER SET utf8mb4 
                    COLLATE utf8mb4_unicode_ci;
                    
                    USE swing_demo_db;
                    
                    -- 用户表
                    CREATE TABLE users (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password_hash VARCHAR(255) NOT NULL,
                        full_name VARCHAR(100),
                        status ENUM('active', 'inactive', 'suspended') DEFAULT 'active',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        
                        INDEX idx_username (username),
                        INDEX idx_email (email),
                        INDEX idx_status (status)
                    ) ENGINE=InnoDB;
                    
                    -- 插入示例数据
                    INSERT INTO users (username, email, password_hash, full_name, status) VALUES 
                    ('admin', 'admin@example.com', 'hashed_password_1', '系统管理员', 'active'),
                    ('john_doe', 'john@example.com', 'hashed_password_2', 'John Doe', 'active'),
                    ('jane_smith', 'jane@example.com', 'hashed_password_3', 'Jane Smith', 'active');
                    
                    -- 查询用户信息
                    SELECT 
                        username,
                        full_name,
                        email,
                        status,
                        created_at
                    FROM users
                    WHERE status = 'active'
                    ORDER BY created_at DESC;
                    """;
            default:
                return Main.getLanguageManager().getString("editor.sample_code.placeholder");
        }
    }

    /**
     * 根据应用程序的主题（亮/暗）应用编辑器主题
     */
    private void applyEditorThemeBasedOnAppTheme() {
        boolean isDark = Main.getThemeManager().isDarkMode();
        String themeName;
        if (isDark) {
            themeName = Main.getLanguageManager().getString("editor.themes.pama1234_dark");
        } else {
            themeName = Main.getLanguageManager().getString("editor.themes.pama1234_light");
        }
        applyEditorTheme(themeName);
        editorThemeChooser.setSelectedItem(themeName);
    }

    /**
     * 应用指定名称的编辑器主题
     * 修复：增强错误处理和空值检查
     */
    private void applyEditorTheme(String themeDisplayName) {
        if (themeDisplayName == null || codeEditor == null) {
            return;
        }

        String themePath = null;

        try {
            if (themeDisplayName.equals(Main.getLanguageManager().getString("editor.themes.pama1234_light"))) {
                themePath = editorThemes.get("pama1234_light");
            } else if (themeDisplayName.equals(Main.getLanguageManager().getString("editor.themes.pama1234_dark"))) {
                themePath = editorThemes.get("pama1234_dark");
            } else if (themeDisplayName.equals(Main.getLanguageManager().getString("editor.themes.rsyntax_default"))) {
                themePath = editorThemes.get("rsyntax_default");
            } else if (themeDisplayName.equals(Main.getLanguageManager().getString("editor.themes.rsyntax_dark"))) {
                themePath = editorThemes.get("rsyntax_dark");
            } else {
                themePath = editorThemes.get("rsyntax_default"); // Fallback
            }

            if (themePath != null) {
                try (InputStream in = getClass().getResourceAsStream(themePath)) {
                    if (in != null) {
                        Theme theme = Theme.load(in);
                        theme.apply(codeEditor);
                        System.out.println("编辑器主题应用成功: " + themePath);
                    } else {
                        System.err.println("无法加载编辑器主题文件: " + themePath);
                        // 使用默认主题作为fallback
                        applyDefaultTheme();
                    }
                } catch (IOException e) {
                    System.err.println("应用编辑器主题失败: " + e.getMessage());
                    applyDefaultTheme();
                }
            }

            // 重新应用自定义字体，以防主题文件覆盖
            Font customFont = FontManager.getInstance().getCustomFont();
            if (customFont != null) {
                codeEditor.setFont(customFont.deriveFont(Font.PLAIN, 14));
            }
        } catch (Exception e) {
            System.err.println("编辑器主题应用过程中发生错误: " + e.getMessage());
            applyDefaultTheme();
        }
    }

    /**
     * 应用默认主题作为fallback
     */
    private void applyDefaultTheme() {
        try (InputStream in = getClass().getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/default.xml")) {
            if (in != null) {
                Theme theme = Theme.load(in);
                theme.apply(codeEditor);
                System.out.println("已应用默认编辑器主题");
            }
        } catch (IOException e) {
            System.err.println("无法应用默认编辑器主题: " + e.getMessage());
        }
    }

    /**
     * 更新主题（由 Main 调用）
     */
    public void updateTheme(boolean isDark) {
        SwingUtilities.invokeLater(() -> {
            applyEditorThemeBasedOnAppTheme();
        });
    }

    /**
     * 刷新所有文本标签
     */
    public void refreshTexts() {
        if (syntaxLabel != null) syntaxLabel.setText(Main.getLanguageManager().getString("editor.syntax.label"));
        if (editorThemeLabel != null) editorThemeLabel.setText(Main.getLanguageManager().getString("editor.themes.label"));
        if (loadSampleBtn != null) loadSampleBtn.setText(Main.getLanguageManager().getString("editor.button.load_sample"));
        if (clearBtn != null) clearBtn.setText(Main.getLanguageManager().getString("editor.button.clear"));

        // 刷新编辑器主题下拉框的显示文本
        if (editorThemeChooser != null) {
            editorThemeChooser.removeAllItems();
            editorThemeChooser.addItem(Main.getLanguageManager().getString("editor.themes.pama1234_light"));
            editorThemeChooser.addItem(Main.getLanguageManager().getString("editor.themes.pama1234_dark"));
            editorThemeChooser.addItem(Main.getLanguageManager().getString("editor.themes.rsyntax_default"));
            editorThemeChooser.addItem(Main.getLanguageManager().getString("editor.themes.rsyntax_dark"));

            // 重新设置选中的主题
            applyEditorThemeBasedOnAppTheme();
        }
    }
}