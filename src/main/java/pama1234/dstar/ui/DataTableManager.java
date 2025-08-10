package pama1234.dstar.ui;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.EventTableModel;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import pama1234.dstar.Main; // 引入Main以便获取LanguageManager
import pama1234.dstar.data.DemoData;

import javax.swing.*;

/**
 * 数据表格管理器
 */
public class DataTableManager {
    private EventList<DemoData> glazedList;
    private JTable dataTable;
    private TableFormat<DemoData> tableFormat; // 更改为成员变量

    // 需要刷新文本的组件引用
    private JButton addBtn;
    private JButton deleteBtn;
    private JButton refreshBtn;

    public JPanel createDataTablePanel() {
        JPanel panel = new JPanel(new MigLayout("fill", "[grow]", "[30!][grow]"));

        // 表格控制面板
        JPanel tableControls = createControlPanel();

        // 初始化GlazedLists数据
        glazedList = new BasicEventList<>();
        initializeTableData();

        // 创建表格模型
        tableFormat = new DemoDataTableFormat(); // 使用成员变量
        EventTableModel<DemoData> tableModel = new EventTableModel<>(glazedList, tableFormat);
        dataTable = new JTable(tableModel);

        // 自定义表格外观
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.setRowHeight(25);
        dataTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(dataTable);

        panel.add(tableControls, "cell 0 0, grow");
        panel.add(scrollPane, "cell 0 1, grow");

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel tableControls = new JPanel(new MigLayout("", "[][][][grow]", ""));

        addBtn = new JButton(Main.getLanguageManager().getString("table.button.add"), FontIcon.of(FontAwesome.PLUS, 14));
        deleteBtn = new JButton(Main.getLanguageManager().getString("table.button.delete"), FontIcon.of(FontAwesome.MINUS, 14));
        refreshBtn = new JButton(Main.getLanguageManager().getString("table.button.refresh"), FontIcon.of(FontAwesome.REFRESH, 14));

        // 事件处理
        addBtn.addActionListener(e -> addRandomData());
        deleteBtn.addActionListener(e -> deleteSelectedData());
        refreshBtn.addActionListener(e -> refreshTableData());

        tableControls.add(addBtn);
        tableControls.add(deleteBtn);
        tableControls.add(refreshBtn);

        return tableControls;
    }

    private void initializeTableData() {
        glazedList.add(new DemoData("FlatLaf", "UI库", 95));
        glazedList.add(new DemoData("RSyntaxTextArea", "编辑器", 90));
        glazedList.add(new DemoData("JFreeChart", "图表库", 88));
        glazedList.add(new DemoData("MiGLayout", "布局管理", 85));
        glazedList.add(new DemoData("Ikonli", "图标库", 82));
    }

    private void addRandomData() {
        String[] names = {"Apache Commons", "Google Guava", "Jackson", "Gson", "OkHttp"};
        String[] types = {"工具库", "JSON库", "网络库", "数据库", "测试框架"};
        String name = names[(int)(Math.random() * names.length)];
        String type = types[(int)(Math.random() * types.length)];
        int value = 60 + (int)(Math.random() * 40);
        glazedList.add(new DemoData(name, type, value));
    }

    private void deleteSelectedData() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < glazedList.size()) {
            glazedList.remove(selectedRow);
        }
    }

    private void refreshTableData() {
        glazedList.clear();
        initializeTableData();
    }

    public void updateTheme(boolean isDark) {
        // 更新表格主题相关设置
        if (dataTable != null) {
            dataTable.repaint();
        }
    }

    /**
     * 刷新所有文本标签和表格列头
     */
    public void refreshTexts() {
        if (addBtn != null) addBtn.setText(Main.getLanguageManager().getString("table.button.add"));
        if (deleteBtn != null) deleteBtn.setText(Main.getLanguageManager().getString("table.button.delete"));
        if (refreshBtn != null) refreshBtn.setText(Main.getLanguageManager().getString("table.button.refresh"));

        // 刷新表格列头
        if (dataTable != null && dataTable.getTableHeader() != null) {
            // 这需要重新设置 TableFormat 或触发模型刷新，但 GlazedLists 的 EventTableModel 会自动更新
            // 只需要确保 TableFormat 获取正确的本地化字符串
            dataTable.getTableHeader().repaint();
            // 如果列名是动态生成的，需要重新生成TableModel
            // 这里DemoDataTableFormat直接从LanguageManager获取，所以只需要repaint表头
        }
    }

    /**
     * 表格格式定义
     */
    private static class DemoDataTableFormat implements TableFormat<DemoData> {
        @Override
        public int getColumnCount() { return 3; }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0: return Main.getLanguageManager().getString("table.column.name");
                case 1: return Main.getLanguageManager().getString("table.column.type");
                case 2: return Main.getLanguageManager().getString("table.column.value");
                default: return "";
            }
        }

        @Override
        public Object getColumnValue(DemoData item, int column) {
            switch (column) {
                case 0: return item.getName();
                case 1: return item.getType();
                case 2: return item.getValue();
                default: return null;
            }
        }
    }
}