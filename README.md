# Swing Enhanced Demo

一个现代化的Java Swing增强库演示应用程序，展示了多个强大的第三方库如何提升Swing应用程序的用户体验和开发效率。

![Java](https://img.shields.io/badge/Java-17+-orange)
![Swing](https://img.shields.io/badge/Swing-Enhanced-blue)
![License](https://img.shields.io/badge/License-MIT-green)

[English Version / 英文版](README.en.md)

## 🌟 功能特色

### 🎨 现代化界面
-   **FlatLaf主题系统**: 支持Light、Dark、IntelliJ、Darcula等多种现代主题
-   **系统主题检测**: 自动检测并适配系统明暗模式
-   **高DPI支持**: 完美适配高分辨率显示器
-   **矢量图标**: 使用Ikonli提供的FontAwesome图标，支持任意缩放
-   **系统托盘集成**: 应用程序可最小化到系统托盘，提供便捷菜单操作 (如显示/隐藏窗口、退出)

### 💻 代码编辑器
-   **语法高亮**: 支持Java、Python、JavaScript、HTML、CSS等50+编程语言
-   **代码折叠**: 智能代码折叠和展开
-   **主题切换**: 编辑器主题与应用主题联动
-   **自动补全**: 括号匹配、自动缩进等智能功能

### 📊 图表展示
-   **动态图表**: 支持柱状图、饼图等多种图表类型
-   **实时更新**: 数据变化时图表自动刷新
-   **自定义样式**: 图表颜色和样式可自定义
-   **导出功能**: 支持导出为PNG、JPEG等格式

### 🔧 布局管理
-   **MiGLayout**: 强大而简洁的布局管理器
-   **JGoodies Forms**: 专业的表单布局解决方案
-   **响应式设计**: 界面自适应不同窗口尺寸

### 📋 数据管理
-   **GlazedLists**: 高性能列表操作，支持排序、过滤、分组
-   **表格展示**: 与Swing JTable完美集成
-   **数据绑定**: 数据变化实时反映到界面

### 📄 文档处理
-   **PDF生成**: 使用Apache PDFBox创建和编辑PDF文档
-   **图片处理**: Thumbnailator提供图片缩放、旋转等功能
-   **HTML渲染**: LoboEvolution提供HTML内容渲染支持

## 🚀 快速开始

### 环境要求

-   **Java 8+** (推荐Java 17+)
-   **Maven 3.6+** 或 **Gradle 7.0+**
-   **IDE**: IntelliJ IDEA、Eclipse或任何支持Maven/Gradle的IDE

### 克隆项目

```bash
git clone https://github.com/UniverseEmbedded/swing-enhanced-demo.git
cd swing-enhanced-demo
```

### 构建和运行

#### 使用Maven
```bash
# 编译项目
mvn clean compile

# 运行应用程序
mvn exec:java -Dexec.mainClass="pama1234.dstar.Main"

# 打包为可执行JAR
mvn clean package
java -jar target/swing-enhanced-demo-1.0-SNAPSHOT.jar
```

#### 使用Gradle
```bash
# 编译项目
./gradlew build

# 运行应用程序
./gradlew run

# 创建分发包
./gradlew distZip
```

### 添加自定义字体（可选）

1.  将字体文件放置在 `src/main/resources/fonts/` 目录下
2.  确保字体文件名为 `MapleMonoNormal-NF-CN-Regular.ttf`
3.  如果未添加自定义字体，应用程序将使用系统默认字体

## 📦 依赖库详情

| 库名称            | 版本   | 功能描述                     | 官方网站                                                 |
| ----------------- | ------ | ---------------------------- | -------------------------------------------------------- |
| FlatLaf           | 3.6.1  | 现代化Look and Feel          | [formdev.com/flatlaf](https://www.formdev.com/flatlaf/)  |
| Ikonli            | 12.4.0 | 图标库系统                   | [kordamp.org/ikonli](https://kordamp.org/ikonli/)        |
| MiGLayout         | 11.4.2 | 布局管理器                   | [miglayout.com](http://www.miglayout.com/)               |
| JGoodies Forms    | 1.9.0  | 表单布局                     | [jgoodies.com](https://www.jgoodies.com/freeware/libraries/forms/) |
| RSyntaxTextArea   | 3.6.0  | 语法高亮编辑器               | [bobbylight.github.io/RSyntaxTextArea](https://bobbylight.github.io/RSyntaxTextArea/) |
| JFreeChart        | 1.5.6  | 图表绘制库                   | [jfree.org/jfreechart](https://www.jfree.org/jfreechart/) |
| Apache PDFBox     | 3.0.5  | PDF处理库                    | [pdfbox.apache.org](https://pdfbox.apache.org/)          |
| GlazedLists      | 1.11.0 | 列表操作库                   | [glazedlists.dev.java.net](http://glazedlists.dev.java.net/) |
| Thumbnailator     | 0.4.20 | 图片处理库                   | [github.com/coobird/thumbnailator](https://github.com/coobird/thumbnailator) |
| jSystemThemeDetector | 3.6    | 系统主题检测                 | [github.com/Dansoftowner/jSystemThemeDetector](https://github.com/Dansoftowner/jSystemThemeDetector) |

## 📱 功能截图

### 主界面
-   🖥️ 现代化的多面板布局
-   🎨 支持明暗主题切换
-   📊 实时图表展示
-   💻 语法高亮代码编辑

### 系统托盘菜单
-   🖱️ 应用程序可最小化到系统托盘，通过右键点击托盘图标可弹出菜单。
-   👁️ 菜单选项包括“显示窗口”、“设置”和“退出程序”，方便快速操作。
-   💬 托盘图标工具提示文本支持多语言切换。
    **(建议在此处添加托盘菜单的截图)**

### 特色功能
-   📈 **动态图表**: 柱状图、饼图切换，数据实时更新
-   🔤 **代码编辑**: 50+语言语法高亮，智能代码折叠
-   📋 **数据表格**: 高性能列表操作，支持添加、删除、刷新
-   📝 **表单布局**: JGoodies Forms专业表单演示
-   ℹ️ **信息展示**: 库信息、系统信息、操作日志

## 🛠️ 项目结构

```
swing-enhanced-demo/
├── src/main/java/pama1234/dstar/
│   ├── Main.java                    # 主程序入口
│   ├── data/                        # 示例数据模型
│   ├── ui/                          # UI面板管理器
│   ├── util/                        # 工具类 (字体、主题、语言、对话框等)
│   └── tray/                        # 系统托盘相关实现 (SwingTrayApp)
├── src/main/resources/
│   ├── fonts/                       # 自定义字体目录（可选）
│   └── messages_xx.properties       # 国际化资源文件 (例如 messages_en.properties, messages_zh_CN.properties)
├── build.gradle                     # Gradle构建配置
├── pom.xml                          # Maven构建配置（如果使用Maven，本项目使用Gradle）
├── README.md                        # 项目说明文档
├── LIBRARIES.md                     # 库功能详细介绍
└── screenshots/                     # 项目截图
```

## 🔧 开发指南

### 添加新功能

1.  **添加新的演示面板**:
    ```java
    private static JPanel createNewDemoPanel() {
        JPanel panel = new JPanel(new MigLayout("fill"));
        // 添加组件...
        return panel;
    }
    ```

2.  **集成新的库**:
  -   在 `build.gradle` 中添加依赖
  -   在相应的演示面板中添加功能展示
  -   更新文档和README

3.  **自定义主题**:
    ```java
    // 在ThemeManager的applySelectedTheme方法中添加新主题
    case "CustomTheme":
        UIManager.setLookAndFeel(new CustomLookAndFeel());
        break;
    ```

4.  **国际化支持**:
  -   在 `src/main/resources/messages_en.properties` 和 `messages_zh_CN.properties` 中添加新的键值对。
  -   在代码中使用 `Main.getLanguageManager().getString("your.key")` 来获取本地化文本。

### 调试技巧

1.  **启用详细日志**:
    ```bash
    java -Djava.util.logging.level=FINE -jar your-app.jar
    ```

2.  **字体问题调试**:
  -   检查字体文件路径 (`src/main/resources/fonts/MapleMonoNormal-NF-CN-Regular.ttf`)
  -   验证字体文件格式
  -   查看控制台字体加载信息

3.  **主题问题**:
  -   确保FlatLaf版本兼容
  -   检查UIManager设置
  -   验证组件更新调用

4.  **托盘图标问题**:
  -   确认系统是否支持 `SystemTray.isSupported()`。
  -   检查应用程序图标 (`createAppIcon()`) 是否正确生成。
  -   在任务管理器或系统托盘设置中查看应用程序是否确实存在。

## 🎯 使用场景

### 学习目的
-   **Swing开发学习**: 了解现代Swing开发最佳实践
-   **库集成参考**: 学习如何整合多个第三方库
-   **界面设计inspiration**: 现代化界面设计参考

### 项目基础
-   **桌面应用开发**: 作为新项目的起始模板
-   **原型开发**: 快速创建功能原型
-   **技术演示**: 向客户或团队展示技术能力

### 教学用途
-   **编程教学**: Java GUI编程教学材料
-   **库功能展示**: 各个库的功能和用法演示
-   **最佳实践示例**: 代码组织和架构参考

## 🤝 贡献指南

我们欢迎所有形式的贡献！

### 如何贡献

1.  **Fork 项目**
2.  **创建特性分支** (`git checkout -b feature/AmazingFeature`)
3.  **提交变更** (`git commit -m 'Add some AmazingFeature'`)
4.  **推送到分支** (`git push origin feature/AmazingFeature`)
5.  **创建 Pull Request**

### 贡献类型

-   🐛 **Bug修复**: 修复已知问题
-   ✨ **新功能**: 添加新的演示功能
-   📚 **文档**: 改进文档和注释
-   🎨 **界面**: 改进UI/UX设计
-   ⚡ **性能**: 性能优化
-   🧪 **测试**: 添加或改进测试

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

感谢以下开源项目和社区的贡献：

-   [FlatLaf](https://www.formdev.com/flatlaf/) - 现代化Look and Feel
-   [RSyntaxTextArea](https://bobbylight.github.io/RSyntaxTextArea/) - 语法高亮编辑器
-   [JFreeChart](https://www.jfree.org/jfreechart/) - 图表库
-   [Apache PDFBox](https://pdfbox.apache.org/) - PDF处理
-   [MiGLayout](http://www.miglayout.com/) - 布局管理器
-   以及所有其他优秀的开源库作者

## 📞 联系信息

-   **作者**: pama1234
-   **邮箱**: your-email@example.com
-   **项目主页**: [GitHub Repository](https://github.com/UniverseEmbedded/swing-enhanced-demo)
-   **问题反馈**: [GitHub Issues](https://github.com/UniverseEmbedded/swing-enhanced-demo/issues)

---

⭐ 如果这个项目对您有帮助，请给它一个星标！

💡 有任何建议或问题，欢迎提交Issue或Pull Request！