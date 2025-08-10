package pama1234.dstar.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import pama1234.dstar.Main; // 引入Main以便获取LanguageManager
import pama1234.dstar.util.DialogUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * PDF生成器
 * 负责生成演示PDF文件
 */
public class PDFGenerator {

    // 中文字体路径
    private static final String CHINESE_FONT_PATH = "/fonts/MapleMonoNormal-NF-CN-Regular.ttf";

    /**
     * 生成PDF演示
     */
    public static void generatePDF(Component parent) {
        SwingUtilities.invokeLater(() -> {
            try {
                String fileName = "swing_demo_output.pdf";
                createDemoPDF(fileName);
                DialogUtils.showInfoDialog(parent,
                        MessageFormat.format(Main.getLanguageManager().getString("dialog.info.pdf_generate_success"), fileName),
                        Main.getLanguageManager().getString("dialog.info.title"));
            } catch (IOException e) {
                DialogUtils.showErrorDialog(parent,
                        MessageFormat.format(Main.getLanguageManager().getString("dialog.error.pdf_generate_failed"), e.getMessage()),
                        Main.getLanguageManager().getString("dialog.error.title"));
            }
        });
    }

    /**
     * 创建演示PDF
     */
    private static void createDemoPDF(String fileName) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // 加载中文字体
            PDType0Font chineseFont = loadChineseFont(document);
            PDType0Font chineseFontBold = chineseFont; // 使用同一字体作为粗体

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // 标题
                contentStream.beginText();
                contentStream.setFont(chineseFontBold, 16);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(Main.getLanguageManager().getString("pdf.title"));
                contentStream.endText();

                // 内容
                contentStream.beginText();
                contentStream.setFont(chineseFont, 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText(Main.getLanguageManager().getString("pdf.content.line1"));
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(Main.getLanguageManager().getString("pdf.content.line2") + ": " + java.time.LocalDateTime.now());
                contentStream.newLineAtOffset(0, -40);
                contentStream.showText(Main.getLanguageManager().getString("pdf.content.features_title"));
                contentStream.newLineAtOffset(20, -20);
                contentStream.showText(Main.getLanguageManager().getString("pdf.content.feature1"));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText(Main.getLanguageManager().getString("pdf.content.feature2"));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText(Main.getLanguageManager().getString("pdf.content.feature3"));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText(Main.getLanguageManager().getString("pdf.content.feature4"));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText(Main.getLanguageManager().getString("pdf.content.feature5"));
                contentStream.endText();

                // 绘制矩形框
                contentStream.setStrokingColor(Color.BLUE);
                contentStream.setLineWidth(2);
                contentStream.addRect(100, 400, 400, 100);
                contentStream.stroke();

                // 框内文字
                contentStream.beginText();
                contentStream.setFont(chineseFontBold, 14);
                contentStream.newLineAtOffset(120, 460);
                contentStream.showText(Main.getLanguageManager().getString("pdf.box.title"));
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(chineseFont, 12);
                contentStream.showText(Main.getLanguageManager().getString("pdf.box.subtitle"));
                contentStream.endText();

                // 添加页脚
                contentStream.beginText();
                contentStream.setFont(chineseFont, 10);
                contentStream.newLineAtOffset(100, 50);
                contentStream.showText(Main.getLanguageManager().getString("pdf.footer"));
                contentStream.endText();
            }

            document.save(fileName);
        }
    }

    /**
     * 加载中文字体
     */
    private static PDType0Font loadChineseFont(PDDocument document) throws IOException {
        try {
            // 尝试从resources加载中文字体
            InputStream fontStream = PDFGenerator.class.getResourceAsStream(CHINESE_FONT_PATH);
            if (fontStream != null) {
                return PDType0Font.load(document, fontStream, false);
            }
        } catch (IOException e) {
            System.err.println("无法加载中文字体: " + e.getMessage());
        }

        // 如果无法加载中文字体，尝试加载系统字体
        try {
            // 尝试加载系统中文字体
            return PDType0Font.load(document,
                    PDFGenerator.class.getResourceAsStream("/fonts/simhei.ttf"), false);
        } catch (Exception e) {
            // 最后的fallback: 使用Arial Unicode MS或其他支持中文的字体
            try {
                // 在Windows系统上尝试加载常见的中文字体
                String[] systemFonts = {
                        "C:/Windows/Fonts/simhei.ttf",  // 黑体
                        "C:/Windows/Fonts/simsun.ttc",  // 宋体
                        "C:/Windows/Fonts/msyh.ttc",    // 微软雅黑
                        "C:/Windows/Fonts/arialuni.ttf" // Arial Unicode MS
                };

                for (String fontPath : systemFonts) {
                    try {
                        java.io.File fontFile = new java.io.File(fontPath);
                        if (fontFile.exists()) {
                            return PDType0Font.load(document, fontFile);
                        }
                    } catch (Exception ignored) {
                        // 继续尝试下一个字体
                    }
                }

                // 如果都失败了，抛出异常
                throw new IOException("无法找到支持中文的字体文件。请确保字体文件存在于 " + CHINESE_FONT_PATH);

            } catch (Exception ex) {
                throw new IOException("字体加载失败: " + ex.getMessage(), ex);
            }
        }
    }
}