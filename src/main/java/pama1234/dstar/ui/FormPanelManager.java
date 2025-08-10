package pama1234.dstar.ui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import pama1234.dstar.Main; // 引入Main以便获取LanguageManager

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * 表单面板管理器
 */
public class FormPanelManager {

    // 需要刷新文本的组件引用
    private JLabel nameLabel, emailLabel, phoneLabel, addressLabel, typeLabel, descriptionLabel;
    private JComboBox<String> typeCombo;
    private JButton saveBtn, resetBtn;
    private JPanel formPanel; // 持有对主表单面板的引用，用于更新标题

    public JPanel createFormPanel() {
        // 使用JGoodies FormLayout
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow, 4dlu, pref", // 列规格
                "p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 9dlu, p" // 行规格
        );

        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        // 添加表单组件
        nameLabel = builder.addLabel(Main.getLanguageManager().getString("form.label.name"), cc.xy(1, 1));
        builder.add(new JTextField(20), cc.xy(3, 1));

        emailLabel = builder.addLabel(Main.getLanguageManager().getString("form.label.email"), cc.xy(1, 3));
        builder.add(new JTextField(20), cc.xy(3, 3));

        phoneLabel = builder.addLabel(Main.getLanguageManager().getString("form.label.phone"), cc.xy(1, 5));
        builder.add(new JTextField(20), cc.xy(3, 5));

        addressLabel = builder.addLabel(Main.getLanguageManager().getString("form.label.address"), cc.xy(1, 7));
        builder.add(new JTextArea(3, 20), cc.xy(3, 7));

        typeLabel = builder.addLabel(Main.getLanguageManager().getString("form.label.type"), cc.xy(1, 9));
        typeCombo = new JComboBox<>(new String[]{
            Main.getLanguageManager().getString("form.type.personal"),
            Main.getLanguageManager().getString("form.type.enterprise"),
            Main.getLanguageManager().getString("form.type.organization")
        });
        builder.add(typeCombo, cc.xy(3, 9));

        descriptionLabel = builder.addLabel(Main.getLanguageManager().getString("form.label.description"), cc.xy(1, 11));
        builder.add(new JScrollPane(new JTextArea(4, 20)), cc.xy(3, 11));

        // 按钮面板
        JPanel buttonPanel = new JPanel(new MigLayout("", "[][grow][]", ""));
        saveBtn = new JButton(Main.getLanguageManager().getString("form.button.save"), FontIcon.of(FontAwesome.SAVE, 14));
        buttonPanel.add(saveBtn);
        buttonPanel.add(new JLabel(""), "grow"); // 弹簧
        resetBtn = new JButton(Main.getLanguageManager().getString("form.button.reset"), FontIcon.of(FontAwesome.UNDO, 14));
        buttonPanel.add(resetBtn);
        builder.add(buttonPanel, cc.xy(3, 13));

        formPanel = builder.getPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder(Main.getLanguageManager().getString("form.title")));

        return formPanel;
    }

    public void updateTheme(boolean isDark) {
        // 表单主题更新逻辑，这里暂时不需要特别处理，FlatLaf 会自动处理组件颜色
    }

    /**
     * 刷新所有文本标签
     */
    public void refreshTexts() {
        if (nameLabel != null) nameLabel.setText(Main.getLanguageManager().getString("form.label.name"));
        if (emailLabel != null) emailLabel.setText(Main.getLanguageManager().getString("form.label.email"));
        if (phoneLabel != null) phoneLabel.setText(Main.getLanguageManager().getString("form.label.phone"));
        if (addressLabel != null) addressLabel.setText(Main.getLanguageManager().getString("form.label.address"));
        if (typeLabel != null) typeLabel.setText(Main.getLanguageManager().getString("form.label.type"));
        if (descriptionLabel != null) descriptionLabel.setText(Main.getLanguageManager().getString("form.label.description"));

        if (saveBtn != null) saveBtn.setText(Main.getLanguageManager().getString("form.button.save"));
        if (resetBtn != null) resetBtn.setText(Main.getLanguageManager().getString("form.button.reset"));

        if (typeCombo != null) {
            typeCombo.removeAllItems();
            typeCombo.addItem(Main.getLanguageManager().getString("form.type.personal"));
            typeCombo.addItem(Main.getLanguageManager().getString("form.type.enterprise"));
            typeCombo.addItem(Main.getLanguageManager().getString("form.type.organization"));
        }

        if (formPanel != null && formPanel.getBorder() instanceof TitledBorder) {
            ((TitledBorder) formPanel.getBorder()).setTitle(Main.getLanguageManager().getString("form.title"));
        }
    }
}