package pama1234.dstar.ui;

import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import pama1234.dstar.Main; // 引入Main以便获取LanguageManager
import pama1234.dstar.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

/**
 * 图表面板管理器
 * 负责管理图表的创建、更新和主题切换
 */
public class ChartPanelManager {
    private JFreeChart currentChart;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset barDataset;
    private DefaultPieDataset<String> pieDataset;
    private Timer refreshTimer;

    // 需要刷新文本的组件引用
    private JButton barChartBtn;
    private JButton pieChartBtn;
    private JButton refreshBtn;

    /**
     * 创建图表面板
     */
    public JPanel createChartPanel() {
        JPanel panel = new JPanel(new MigLayout("fill", "[grow]", "[30!][grow]"));

        // 图表控制面板
        JPanel chartControls = createControlPanel();

        // 初始化数据集
        initializeChartData();

        // 创建初始图表
        createInitialChart();

        // 组装面板
        panel.add(chartControls, "cell 0 0, grow");
        panel.add(chartPanel, "cell 0 1, grow");

        return panel;
    }

    /**
     * 创建控制面板
     */
    private JPanel createControlPanel() {
        JPanel chartControls = new JPanel(new MigLayout("", "[][][][grow]", ""));

        barChartBtn = new JButton(Main.getLanguageManager().getString("chart.button.bar_chart"), createIcon(FontAwesome.BAR_CHART));
        pieChartBtn = new JButton(Main.getLanguageManager().getString("chart.button.pie_chart"), createIcon(FontAwesome.PIE_CHART));
        refreshBtn = new JButton(Main.getLanguageManager().getString("chart.button.refresh_data"), createIcon(FontAwesome.REFRESH));

        // 按钮事件
        barChartBtn.addActionListener(e -> switchToBarChart());
        pieChartBtn.addActionListener(e -> switchToPieChart());
        refreshBtn.addActionListener(e -> refreshChartData());

        chartControls.add(barChartBtn);
        chartControls.add(pieChartBtn);
        chartControls.add(refreshBtn);

        return chartControls;
    }

    /**
     * 创建图标
     */
    private FontIcon createIcon(FontAwesome icon) {
        FontIcon fontIcon = FontIcon.of(icon, 14);
        // 根据主题设置图标颜色
        Color iconColor = UIManager.getColor("Button.foreground");
        if (iconColor != null) {
            fontIcon.setIconColor(iconColor);
        }
        return fontIcon;
    }

    /**
     * 初始化图表数据
     */
    private void initializeChartData() {
        barDataset = new DefaultCategoryDataset();
        pieDataset = new DefaultPieDataset<>();

        // 添加示例数据
        addChartData("Java", 85);
        addChartData("Python", 70);
        addChartData("JavaScript", 75);
        addChartData("C++", 60);
        addChartData("Go", 45);
    }

    /**
     * 添加图表数据
     */
    private void addChartData(String category, int value) {
        barDataset.addValue(value, Main.getLanguageManager().getString("chart.value.popularity_index"), category);
        pieDataset.setValue(category, value);
    }

    /**
     * 创建初始图表
     */
    private void createInitialChart() {
        currentChart = ChartFactory.createBarChart(
                Main.getLanguageManager().getString("chart.title.bar"),
                Main.getLanguageManager().getString("chart.category.language"),
                Main.getLanguageManager().getString("chart.value.popularity_index"),
                barDataset,
                PlotOrientation.VERTICAL, true, true, false
        );

        // 自定义图表样式
        customizeChart(currentChart);

        // 设置字体
        applyChartFont(currentChart);

        chartPanel = new ChartPanel(currentChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
    }

    /**
     * 自定义图表样式
     * 修复饼图切换报错问题：增加对Plot类型的判断
     * 修复饼图在深色模式下的配色问题：调整标签背景、边框和连接线颜色
     */
    private void customizeChart(JFreeChart chart) {
        // 获取当前主题的颜色
        Color backgroundColor = UIManager.getColor("Panel.background");
        Color foregroundColor = UIManager.getColor("Label.foreground");
        Color gridColor = UIManager.getColor("Separator.foreground");

        // 设置图表背景
        chart.setBackgroundPaint(backgroundColor);

        // 设置标题颜色
        if (chart.getTitle() != null) {
            chart.getTitle().setPaint(foregroundColor);
        }

        // 设置图例颜色
        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(backgroundColor);
            chart.getLegend().setItemPaint(foregroundColor);
        }

        // 根据图表类型进行具体样式设置
        if (chart.getPlot() instanceof CategoryPlot) { // 判断是否为CategoryPlot (如柱状图、线图)
            CategoryPlot plot = (CategoryPlot) chart.getPlot();

            // 设置绘图区背景
            plot.setBackgroundPaint(backgroundColor);
            plot.setOutlinePaint(foregroundColor);

            // 设置网格线颜色
            plot.setDomainGridlinePaint(gridColor != null ? gridColor :
                    (foregroundColor != null ? new Color(foregroundColor.getRGB() & 0x40FFFFFF, true) : Color.GRAY));
            plot.setRangeGridlinePaint(gridColor != null ? gridColor :
                    (foregroundColor != null ? new Color(foregroundColor.getRGB() & 0x40FFFFFF, true) : Color.GRAY));

            // 设置坐标轴颜色
            if (plot.getDomainAxis() != null) {
                plot.getDomainAxis().setAxisLinePaint(foregroundColor);
                plot.getDomainAxis().setTickMarkPaint(foregroundColor);
                plot.getDomainAxis().setTickLabelPaint(foregroundColor);
                plot.getDomainAxis().setLabelPaint(foregroundColor);
            }

            if (plot.getRangeAxis() != null) {
                plot.getRangeAxis().setAxisLinePaint(foregroundColor);
                plot.getRangeAxis().setTickMarkPaint(foregroundColor);
                plot.getRangeAxis().setTickLabelPaint(foregroundColor);
                plot.getRangeAxis().setLabelPaint(foregroundColor);
            }

            // 设置柱状图颜色（保持原有的彩色设计，但确保在深色模式下可见）
            // 确保渲染器是BarRenderer，因为CategoryPlot可以有多种渲染器
            if (plot.getRenderer() instanceof BarRenderer) {
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                if (isDarkMode()) {
                    // 深色模式下使用更亮的颜色
                    renderer.setSeriesPaint(0, new Color(100, 180, 255)); // 更亮的蓝色
                    renderer.setSeriesPaint(1, new Color(255, 180, 50));  // 更亮的橙色
                    renderer.setSeriesPaint(2, new Color(120, 200, 120)); // 更亮的绿色
                } else {
                    // 浅色模式使用原有颜色
                    renderer.setSeriesPaint(0, new Color(66, 165, 245));
                    renderer.setSeriesPaint(1, new Color(255, 167, 38));
                    renderer.setSeriesPaint(2, new Color(102, 187, 106));
                }
            }
        } else if (chart.getPlot() instanceof org.jfree.chart.plot.PiePlot) { // 判断是否为PiePlot (如饼图)
            org.jfree.chart.plot.PiePlot piePlot = (org.jfree.chart.plot.PiePlot) chart.getPlot();
            piePlot.setBackgroundPaint(backgroundColor);
            piePlot.setOutlinePaint(foregroundColor);
            piePlot.setLabelPaint(foregroundColor); // 标签文本颜色

            // 修复问题2: 饼图标签背景和连接线颜色
            piePlot.setLabelBackgroundPaint(backgroundColor); // 标签背景色与面板背景色一致
            piePlot.setLabelOutlinePaint(foregroundColor);    // 标签边框色与前景色一致
            piePlot.setLabelLinkPaint(foregroundColor);       // 连接线颜色与前景色一致
            // piePlot.setLabelShadowPaint(null); // 可以考虑移除标签阴影，使其更扁平化

            // 根据主题设置饼图切片颜色，使其在深色模式下可见
            if (isDarkMode()) {
                piePlot.setSectionPaint("Java", new Color(100, 180, 255));
                piePlot.setSectionPaint("Python", new Color(255, 180, 50));
                piePlot.setSectionPaint("JavaScript", new Color(120, 200, 120));
                piePlot.setSectionPaint("C++", new Color(255, 100, 100));
                piePlot.setSectionPaint("Go", new Color(180, 120, 255));
                piePlot.setSectionPaint("Rust", new Color(255, 120, 180));
                piePlot.setSectionPaint("Swift", new Color(120, 255, 255));
            } else {
                piePlot.setSectionPaint("Java", new Color(66, 165, 245));
                piePlot.setSectionPaint("Python", new Color(255, 167, 38));
                piePlot.setSectionPaint("JavaScript", new Color(102, 187, 106));
                piePlot.setSectionPaint("C++", new Color(239, 83, 80));
                piePlot.setSectionPaint("Go", new Color(126, 87, 194));
                piePlot.setSectionPaint("Rust", new Color(255, 109, 209));
                piePlot.setSectionPaint("Swift", new Color(64, 204, 255));
            }
        }
    }

    // 添加辅助方法判断是否为深色模式
    private boolean isDarkMode() {
        // 通过背景色亮度判断
        Color bg = UIManager.getColor("Panel.background");
        if (bg != null) {
            // 计算亮度 (0.299*R + 0.587*G + 0.114*B)
            double brightness = (0.299 * bg.getRed() + 0.587 * bg.getGreen() + 0.114 * bg.getBlue()) / 255.0;
            return brightness < 0.5; // 亮度小于0.5认为是深色模式
        }
        return false;
    }

    /**
     * 应用图表字体
     */
    private void applyChartFont(JFreeChart chart) {
        Font customFont = FontManager.getInstance().getCustomFont();
        if (customFont != null) {
            // 设置标题字体
            chart.getTitle().setFont(customFont.deriveFont(Font.BOLD, 16f));

            if (chart.getPlot() instanceof CategoryPlot) { // 检查是否为CategoryPlot
                CategoryPlot plot = (CategoryPlot) chart.getPlot();

                // 设置坐标轴字体
                plot.getDomainAxis().setLabelFont(customFont.deriveFont(Font.PLAIN, 12f));
                plot.getDomainAxis().setTickLabelFont(customFont.deriveFont(Font.PLAIN, 10f));
                plot.getRangeAxis().setLabelFont(customFont.deriveFont(Font.PLAIN, 12f));
                plot.getRangeAxis().setTickLabelFont(customFont.deriveFont(Font.PLAIN, 10f));

                // 设置图例字体
                if (chart.getLegend() != null) {
                    chart.getLegend().setItemFont(customFont.deriveFont(Font.PLAIN, 11f));
                }
            } else if (chart.getPlot() instanceof org.jfree.chart.plot.PiePlot) { // 检查是否为PiePlot
                org.jfree.chart.plot.PiePlot piePlot = (org.jfree.chart.plot.PiePlot) chart.getPlot();
                // 饼图标签字体
                piePlot.setLabelFont(customFont.deriveFont(Font.PLAIN, 10f));
                // 设置图例字体
                if (chart.getLegend() != null) {
                    chart.getLegend().setItemFont(customFont.deriveFont(Font.PLAIN, 11f));
                }
            }
        }
    }

    /**
     * 切换到柱状图
     */
    private void switchToBarChart() {
        currentChart = ChartFactory.createBarChart(
                Main.getLanguageManager().getString("chart.title.bar"),
                Main.getLanguageManager().getString("chart.category.language"),
                Main.getLanguageManager().getString("chart.value.popularity_index"),
                barDataset,
                PlotOrientation.VERTICAL, true, true, false
        );
        customizeChart(currentChart);
        applyChartFont(currentChart);
        chartPanel.setChart(currentChart);
    }

    /**
     * 切换到饼图
     */
    private void switchToPieChart() {
        currentChart = ChartFactory.createPieChart(
                Main.getLanguageManager().getString("chart.title.pie"), pieDataset, true, true, false
        );
        customizeChart(currentChart);
        applyChartFont(currentChart);
        chartPanel.setChart(currentChart);
    }

    /**
     * 刷新图表数据
     */
    private void refreshChartData() {
        // 生成随机数据
        barDataset.clear();
        pieDataset.clear();

        String[] languages = {"Java", "Python", "JavaScript", "C++", "Go", "Rust", "Swift"};
        for (String lang : languages) {
            int value = 30 + (int)(Math.random() * 70);
            addChartData(lang, value);
        }

        // 重新应用当前图表类型，确保数据更新后图表类型不变
        if (currentChart.getPlot() instanceof CategoryPlot) {
            switchToBarChart();
        } else if (currentChart.getPlot() instanceof org.jfree.chart.plot.PiePlot) {
            switchToPieChart();
        }
    }

    /**
     * 更新主题
     */
    public void updateTheme(boolean isDark) {
        if (currentChart != null) {
            // 重新应用样式
            customizeChart(currentChart);
            applyChartFont(currentChart);

            // 强制重绘
            SwingUtilities.invokeLater(() -> {
                chartPanel.revalidate();
                chartPanel.repaint();
            });
        }
    }

    /**
     * 刷新所有文本标签
     */
    public void refreshTexts() {
        if (barChartBtn != null) barChartBtn.setText(Main.getLanguageManager().getString("chart.button.bar_chart"));
        if (pieChartBtn != null) pieChartBtn.setText(Main.getLanguageManager().getString("chart.button.pie_chart"));
        if (refreshBtn != null) refreshBtn.setText(Main.getLanguageManager().getString("chart.button.refresh_data"));

        // 刷新图表标题和轴标签
        if (currentChart != null) {
            if (currentChart.getPlot() instanceof CategoryPlot) {
                currentChart.setTitle(Main.getLanguageManager().getString("chart.title.bar"));
                CategoryPlot plot = (CategoryPlot) currentChart.getPlot();
                if (plot.getDomainAxis() != null) {
                    plot.getDomainAxis().setLabel(Main.getLanguageManager().getString("chart.category.language"));
                }
                if (plot.getRangeAxis() != null) {
                    plot.getRangeAxis().setLabel(Main.getLanguageManager().getString("chart.value.popularity_index"));
                }
            } else if (currentChart.getPlot() instanceof org.jfree.chart.plot.PiePlot) {
                currentChart.setTitle(Main.getLanguageManager().getString("chart.title.pie"));
            }
            // 数据集中的系列名也需要更新，但默认的 JFreeChart dataset 不支持直接更新系列名，
            // 重新生成数据集更常见，或者在addChartData时就用本地化字符串。
            // 这里我们假设数据集内容不变，只更新图表本身的标签。
            refreshChartData(); // 刷新数据会重新添加本地化后的系列名
        }
    }


    /**
     * 启动后台任务
     */
    public void startBackgroundTasks() {
        // 启动数据自动刷新任务
        refreshTimer = new Timer(30000, e -> {
            // 每30秒自动刷新一次数据（演示用）
            if (Math.random() > 0.8) { // 20%的概率刷新
                refreshChartData();
            }
        });
        refreshTimer.start();

        System.out.println(MessageFormat.format(Main.getLanguageManager().getString("log.chart_manager.background_task_started"), "[ChartManager]"));
    }

    /**
     * 停止后台任务
     */
    public void stopBackgroundTasks() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
    }
}