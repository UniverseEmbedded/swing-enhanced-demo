package pama1234.dstar.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import pama1234.dstar.Main; // 引入Main以便获取LanguageManager
import pama1234.dstar.util.DialogUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * PDF生成器
 * 负责生成演示PDF文件
 */
public class PDFGenerator {

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

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // 标题
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 16);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(Main.getLanguageManager().getString("pdf.title"));
                contentStream.endText();

                // 内容
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(FontName.HELVETICA), 12);
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
                contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 14);
                contentStream.newLineAtOffset(120, 460);
                contentStream.showText(Main.getLanguageManager().getString("pdf.box.title"));
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(new PDType1Font(FontName.HELVETICA), 12);
                contentStream.showText(Main.getLanguageManager().getString("pdf.box.subtitle"));
                contentStream.endText();

                // 添加页脚
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(FontName.HELVETICA_OBLIQUE), 10);
                contentStream.newLineAtOffset(100, 50);
                contentStream.showText(Main.getLanguageManager().getString("pdf.footer"));
                contentStream.endText();
            }

            document.save(fileName);
        }
    }
}