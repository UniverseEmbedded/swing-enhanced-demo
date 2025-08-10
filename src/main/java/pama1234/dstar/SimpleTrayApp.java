package pama1234.dstar;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class SimpleTrayApp {
  private TrayIcon trayIcon;
  private SystemTray systemTray;
  private JPopupMenu popupMenu;
  private JFrame popupAnchor;

  public SimpleTrayApp() {
    // 加载自定义字体
    loadCustomFont();

    // 检查系统是否支持托盘
    if(!SystemTray.isSupported()) {
      System.out.println("系统不支持托盘功能");
      return;
    }

    try {
      // 获取系统托盘
      systemTray=SystemTray.getSystemTray();

      // 创建托盘图标
      Image image=createDummyImage(16,16);
      trayIcon=new TrayIcon(image,"托盘应用示例");
      trayIcon.setImageAutoSize(true);

      // 创建Swing弹出菜单
      popupMenu=new JPopupMenu();

      // 添加菜单项
      JMenuItem showItem=new JMenuItem("显示窗口");
      JMenuItem settingsItem=new JMenuItem("设置");
      JMenuItem exitItem=new JMenuItem("退出程序");

      // 添加动作监听器
      showItem.addActionListener(e->System.out.println("显示窗口"));
      settingsItem.addActionListener(e->System.out.println("打开设置"));
      exitItem.addActionListener(e-> {
        systemTray.remove(trayIcon);
        System.exit(0);
      });

      // 添加菜单项到弹出菜单
      popupMenu.add(showItem);
      popupMenu.add(settingsItem);
      popupMenu.addSeparator();
      popupMenu.add(exitItem);

      // 创建一个不可见的JFrame作为弹出菜单的锚点
      popupAnchor=new JFrame();
      popupAnchor.setUndecorated(true);
      popupAnchor.setType(Window.Type.UTILITY); // 使用UTILITY类型，在任务栏中不显示
      popupAnchor.setSize(1,1);
      popupAnchor.setAlwaysOnTop(true);

      // 添加鼠标事件处理器，显示弹出菜单
      trayIcon.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          // 在Windows上，通常是鼠标按下时触发右键菜单
          if(e.getButton()==MouseEvent.BUTTON3) {
            displayMenu(e);
          }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          // 在macOS和某些Linux桌面环境中，通常是鼠标释放时触发右键菜单
          if(e.getButton()==MouseEvent.BUTTON3) {
            displayMenu(e);
          }
        }

        private void displayMenu(MouseEvent e) {
          try {
            // 获取当前鼠标位置
            Point p=MouseInfo.getPointerInfo().getLocation();

            // 设置锚点窗口位置
            popupAnchor.setLocation(p.x,p.y);
            popupAnchor.setVisible(true);

            // 确保popupAnchor获得焦点
            popupAnchor.toFront();

            // 添加一个弹出菜单监听器，在菜单消失时隐藏锚点窗口
            popupMenu.addPopupMenuListener(new PopupMenuListener() {
              @Override
              public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

              @Override
              public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                popupAnchor.setVisible(false);
              }

              @Override
              public void popupMenuCanceled(PopupMenuEvent e) {
                popupAnchor.setVisible(false);
              }
            });

            // 显示弹出菜单
            SwingUtilities.invokeLater(()-> {
              popupMenu.show(popupAnchor,0,-popupMenu.getPreferredSize().height);
            });
          }catch(Exception ex) {
            ex.printStackTrace();
          }
        }
      });

      // 添加托盘图标到系统托盘
      systemTray.add(trayIcon);

      System.out.println("托盘应用已启动，请在系统托盘区域点击右键查看菜单");

    }catch(AWTException e) {
      System.err.println("创建托盘图标失败: "+e.getMessage());
      e.printStackTrace();
    }
  }

  // 创建一个简单的图像用于托盘图标
  private Image createDummyImage(int width,int height) {
    BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d=image.createGraphics();
    g2d.setColor(Color.RED);
    g2d.fillRect(0,0,width,height);
    g2d.setColor(Color.WHITE);
    g2d.drawRect(0,0,width-1,height-1);
    g2d.dispose();
    return image;
  }

  // 加载自定义字体
  private void loadCustomFont() {
    try {
      // 从资源文件加载字体
      InputStream is=getClass().getClassLoader().getResourceAsStream("fonts/MapleMonoNormal-NF-CN-Regular.ttf");
      if(is==null) {
        System.err.println("无法找到字体文件: font/MapleMonoNormal-NF-CN-Regular.ttf");
        return;
      }

      // 创建字体
      Font customFont=Font.createFont(Font.TRUETYPE_FONT,is);
      GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();

      // 注册字体
      ge.registerFont(customFont);

      // 派生字体并设置为默认字体
      Font font=customFont.deriveFont(Font.PLAIN,14);
      setDefaultFont(font);

      System.out.println("自定义字体加载成功");
    }catch(Exception e) {
      System.err.println("加载字体失败: "+e.getMessage());
      e.printStackTrace();
    }
  }

  // 设置所有组件的默认字体
  private void setDefaultFont(Font font) {
    UIManager.put("defaultFont",font);
    UIManager.put("Label.font",font);
    UIManager.put("Button.font",font);
    UIManager.put("MenuItem.font",font);
    UIManager.put("Menu.font",font);
    UIManager.put("PopupMenu.font",font);
    UIManager.put("CheckBox.font",font);
    UIManager.put("RadioButton.font",font);
    UIManager.put("ComboBox.font",font);
    UIManager.put("TextField.font",font);
    UIManager.put("TextArea.font",font);
    UIManager.put("List.font",font);
    UIManager.put("Table.font",font);
    UIManager.put("TableHeader.font",font);
  }

  public static void main(String[] args) {

    FlatMacLightLaf.setup();

    // 在EDT线程中创建应用
    SwingUtilities.invokeLater(()-> {
      new SimpleTrayApp();
    });
  }
}
