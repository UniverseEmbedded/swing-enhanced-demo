package pama1234.dstar.tray;

import pama1234.dstar.Main;
import pama1234.dstar.util.FontManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class SwingTrayApp {
    private TrayIcon trayIcon;
    private SystemTray systemTray;
    private JPopupMenu popupMenu;
    private JDialog popupAnchor;
    private Runnable showMainWindowAction; // <--- 这里修改了类型

    // 菜单项，用于更新文本
    private JMenuItem showItem;
    private JMenuItem settingsItem;
    private JMenuItem exitItem;

    private boolean trayIconAdded = false;

    /**
     * 构造函数
     * @param iconImage 托盘图标图像
     * @param tooltip 托盘图标工具提示文本
     * @param showMainWindowAction 回调函数，用于显示主窗口 // <--- 这里也修改了描述
     */
    public SwingTrayApp(BufferedImage iconImage, String tooltip, Runnable showMainWindowAction) { // <--- 这里修改了类型
        this.showMainWindowAction = showMainWindowAction;

        // 检查系统是否支持托盘
        if (!SystemTray.isSupported()) {
            System.out.println(Main.getLanguageManager().getString("dialog.info.system_tray_not_supported"));
            return;
        }

        try {
            // 获取系统托盘
            systemTray = SystemTray.getSystemTray();

            // 创建托盘图标
            trayIcon = new TrayIcon(iconImage, tooltip);
            trayIcon.setImageAutoSize(true);

            // 创建Swing弹出菜单
            popupMenu = new JPopupMenu();

            // 添加菜单项
            showItem = new JMenuItem(Main.getLanguageManager().getString("tray.show_window"));
            settingsItem = new JMenuItem(Main.getLanguageManager().getString("tray.settings"));
            exitItem = new JMenuItem(Main.getLanguageManager().getString("tray.exit_app"));

            // 添加动作监听器
            showItem.addActionListener(e -> {
                if (this.showMainWindowAction != null) {
                    this.showMainWindowAction.run(); // <--- 这里修改了调用方法
                }
            });
            settingsItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
                    Main.getLanguageManager().getString("tray.settings_message"),
                    Main.getLanguageManager().getString("tray.settings_title"),
                    JOptionPane.INFORMATION_MESSAGE));
            exitItem.addActionListener(e -> {
                systemTray.remove(trayIcon);
                System.exit(0);
            });

            // 添加菜单项到弹出菜单
            popupMenu.add(showItem);
            popupMenu.add(settingsItem);
            popupMenu.addSeparator();
            popupMenu.add(exitItem);

            // 应用字体到弹出菜单
            FontManager.getInstance().applyToComponent(popupMenu);

            // 创建一个不可见的JDialog作为弹出菜单的锚点
            popupAnchor = new JDialog();
            popupAnchor.setUndecorated(true);
            popupAnchor.setAlwaysOnTop(true);
            popupAnchor.setSize(1, 1);
            popupAnchor.setType(Window.Type.UTILITY);

            // 添加鼠标事件处理器，显示弹出菜单
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        displayMenu(e);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        displayMenu(e);
                    }
                }

                private void displayMenu(MouseEvent e) {
                    try {
                        // 获取当前鼠标位置
                        Point p = MouseInfo.getPointerInfo().getLocation();

                        // 设置锚点窗口位置
                        popupAnchor.setLocation(p.x, p.y);
                        popupAnchor.setVisible(true);
                        popupAnchor.toFront();

                        // 确保每次显示前都移除旧的监听器，避免重复添加
                        for (PopupMenuListener listener : popupMenu.getPopupMenuListeners()) {
                            popupMenu.removePopupMenuListener(listener);
                        }
                        popupMenu.addPopupMenuListener(new PopupMenuListener() {
                            @Override
                            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

                            @Override
                            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                                // 延迟隐藏，避免UI闪烁问题
                                SwingUtilities.invokeLater(() -> {
                                    popupAnchor.setVisible(false);
                                    popupAnchor.dispose();
                                    popupAnchor = createPopupAnchor();
                                });
                            }

                            @Override
                            public void popupMenuCanceled(PopupMenuEvent e) {
                                SwingUtilities.invokeLater(() -> {
                                    popupAnchor.setVisible(false);
                                    popupAnchor.dispose();
                                    popupAnchor = createPopupAnchor();
                                });
                            }
                        });

                        // 显示弹出菜单
                        SwingUtilities.invokeLater(() -> {
                            int x = p.x;
                            int y = p.y;
                            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                            // 如果菜单底部会超出屏幕，则向上弹出
                            if (y + popupMenu.getPreferredSize().height > screenSize.height) {
                                popupMenu.show(popupAnchor, 0, -popupMenu.getPreferredSize().height);
                            } else { // 否则向下弹出
                                popupMenu.show(popupAnchor, 0, 0);
                            }
                        });
                    } catch (Exception ex) {
                        System.err.println(Main.getLanguageManager().getString("dialog.error.tray_menu_display_failed") + ": " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            });

            // 添加托盘图标到系统托盘
            systemTray.add(trayIcon);
            trayIconAdded = true;

            System.out.println(Main.getLanguageManager().getString("log.tray_app_started"));

        } catch (AWTException e) {
            System.err.println(Main.getLanguageManager().getString("dialog.error.tray_icon_create_failed") + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper to recreate popupAnchor
    private JDialog createPopupAnchor() {
        JDialog newAnchor = new JDialog();
        newAnchor.setUndecorated(true);
        newAnchor.setAlwaysOnTop(true);
        newAnchor.setSize(1, 1);
        newAnchor.setType(Window.Type.UTILITY);
        return newAnchor;
    }

    /**
     * 设置托盘图标的工具提示文本
     * @param tooltip 新的工具提示文本
     */
    public void setToolTip(String tooltip) {
        if (trayIcon != null) {
            trayIcon.setToolTip(tooltip);
        }
    }

    /**
     * 更新托盘菜单项的文本
     */
    public void updateTrayMenuTexts() {
        if (showItem != null) {
            showItem.setText(Main.getLanguageManager().getString("tray.show_window"));
        }
        if (settingsItem != null) {
            settingsItem.setText(Main.getLanguageManager().getString("tray.settings"));
        }
        if (exitItem != null) {
            exitItem.setText(Main.getLanguageManager().getString("tray.exit_app"));
        }
        // 重新应用字体，因为语言切换后字体可能会有变化
        if (popupMenu != null) {
            FontManager.getInstance().applyToComponent(popupMenu);
        }
    }

    /**
     * 检查托盘图标是否已成功添加到系统托盘
     */
    public boolean isTrayIconAdded() {
        return trayIconAdded;
    }
}