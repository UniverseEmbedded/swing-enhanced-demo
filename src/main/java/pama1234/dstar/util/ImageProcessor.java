package pama1234.dstar.util;

import net.coobird.thumbnailator.Thumbnails;
import pama1234.dstar.Main; // 引入Main以便获取LanguageManager
import pama1234.dstar.util.DialogUtils;
import pama1234.dstar.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * 图片处理器
 * 负责演示图片处理功能
 */
public class ImageProcessor {

    /**
     * 图片处理演示
     */
    public static void processImage(Component parent) {
        try {
            // 创建一个示例图片
            BufferedImage originalImage = createSampleImage();

            // 使用Thumbnailator创建缩略图
            BufferedImage thumbnail = Thumbnails.of(originalImage)
                    .size(100, 100)
                    .rotate(15)
                    .asBufferedImage();

            // 显示结果
            showImageDialog(parent, originalImage, thumbnail);

        } catch (IOException e) {
            DialogUtils.showErrorDialog(parent,
                    MessageFormat.format(Main.getLanguageManager().getString("dialog.error.image_process_failed"), e.getMessage()),
                    Main.getLanguageManager().getString("dialog.error.title"));
        }
    }

    /**
     * 创建示例图片
     */
    private static BufferedImage createSampleImage() {
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制彩色渐变
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, 200, 200, Color.RED);
        g2.setPaint(gradient);
        g2.fillRect(0, 0, 200, 200);

        // 添加文字
        g2.setColor(Color.WHITE);
        // 使用FontManager获取自定义字体
        Font font = FontManager.getInstance().getFont(Font.BOLD, 24);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        String text = Main.getLanguageManager().getString("dialog.image.original_text"); // 这里硬编码为"Original"，但为了演示多语言，最好在资源文件中加入
        int x = (200 - fm.stringWidth(text)) / 2;
        int y = (200 + fm.getAscent()) / 2;
        g2.drawString(text, x, y);

        g2.dispose();
        return image;
    }

    /**
     * 显示图片对话框
     * 修复问题1: 更改为使用 DialogUtils.showDialog 来创建对话框，以利用其对标题字体的一致性处理。
     */
    private static void showImageDialog(Component parent, BufferedImage original, BufferedImage processed) {
        // 创建内容面板，这个面板将作为 JOptionPane 的 'message'
        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加标签和图片
        contentPanel.add(new JLabel(MessageFormat.format(Main.getLanguageManager().getString("dialog.image.original"), original.getWidth(), original.getHeight()), JLabel.CENTER));
        contentPanel.add(new JLabel(MessageFormat.format(Main.getLanguageManager().getString("dialog.image.processed"), processed.getWidth(), processed.getHeight(), 15), JLabel.CENTER));
        contentPanel.add(new JLabel(new ImageIcon(original), JLabel.CENTER));
        contentPanel.add(new JLabel(new ImageIcon(processed), JLabel.CENTER));

        // 使用 DialogUtils.showDialog 来创建对话框
        DialogUtils.showDialog(
                parent,
                contentPanel, // 将包含图片的面板作为消息内容
                Main.getLanguageManager().getString("control.button.process_image"), // 对话框标题也本地化
                JOptionPane.PLAIN_MESSAGE, // 使用 PLAIN_MESSAGE 避免默认图标
                null // 不需要额外的图标
        );
    }
}