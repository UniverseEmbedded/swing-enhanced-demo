## 代码需要完善的地方

1. 图表展示里头需要使用maple mono字体，不然中文字符无法展示
2. 暗色模式下图标不应该是黑色的，需要找到能够修改图标的办法
3. 左上角的tabs标签页有问题，“LibraryInfo”条目没有对应的标签，而且一上来就展示在右侧，挡住了左侧“图表展示”标签页，还有“系统信息”的标签在有些窗口大小下会展示，但是无法被点击，我认为这意味着某些地方有问题，或者库之间没有兼容
4. JOptionPane.showMessageDialog的左上角标题“关于应用程序”也需要使用maple mono字体
5. 把项目拆分成多个类文件

## 文档

1. doc/LIBRARIES.md写了一半，没写完，需要继续写才行，对于没写完的部分中要出现的库，每个库你都需要查阅网页，根据最新的API文档来写功能介绍和使用方法

---

我知道问题3的原因所在了（目前依然没有解决），左侧右侧两个面版的位置重叠了导致的

---

还有几个问题要解决的：

1. “关于”弹窗的字体对了，但是主窗口的字体没有使用maple mono
2. “演示数据图表”这几个字在深色模式下依然是黑色的
3. “柱状图”，“饼图”这些文本旁边的图表在深色模式下依然是黑色的
4. “代码编辑”标签页里面的代码文本区域没有正确遵守暗色模式，点击“切换主题”按钮可以看出文本区域好像被刷新了，但是高亮主题方面没有变化
5. “应用控制”面版超出窗口底边限制了，看不见下面的内容了

---

1. PDF生成，处理图片，以及其它任何情况下的弹窗都要使用maple mono字体来绘制标题
2. 图表的数值，类别，刻度线的颜色在深色模式下太暗了，要么是没有设置要么是没有刷新

---

1. “图片处理演示”弹窗的标题没有使用maple mono字体，需要进行修复
2. 加上托盘菜单的功能（参考示例代码）
3. “代码编辑”页面的代码面版一开始在深色模式下会呈现浅色配色，需要点一次“切换主题”相当于刷新一下，才能变为暗色主题，改成直接自动切换
4. 代码面版也要使用maple mono字体
5. 修复无法切换到饼图的问题

```text
Exception in thread "AWT-EventQueue-0" java.lang.ClassCastException: class org.jfree.chart.plot.PiePlot cannot be cast to class org.jfree.chart.plot.CategoryPlot (org.jfree.chart.plot.PiePlot and org.jfree.chart.plot.CategoryPlot are in unnamed module of loader 'app')
	at org.jfree.chart.JFreeChart.getCategoryPlot(JFreeChart.java:765)
	at pama1234.dstar.ui.ChartPanelManager.customizeChart(ChartPanelManager.java:152)
	at pama1234.dstar.ui.ChartPanelManager.switchToPieChart(ChartPanelManager.java:264)
	at pama1234.dstar.ui.ChartPanelManager.lambda$createControlPanel$1(ChartPanelManager.java:64)
	at java.desktop/javax.swing.AbstractButton.fireActionPerformed(AbstractButton.java:1972)
	at java.desktop/javax.swing.AbstractButton$Handler.actionPerformed(AbstractButton.java:2314)
	at java.desktop/javax.swing.DefaultButtonModel.fireActionPerformed(DefaultButtonModel.java:407)
	at java.desktop/javax.swing.DefaultButtonModel.setPressed(DefaultButtonModel.java:262)
```

---

1. “图片处理演示”弹窗的标题依旧没有使用maple mono字体，需要进行修复
2. 饼图在深色模式下的配色有问题，文本的背景矩形标签（目前是米黄色），以及标签连到饼上的线（目前黑色）都要改成符合暗色模式的配色

---

1. 代码编辑那块，能不能自定义一个配色出来？还有“切换主题”按钮改成和“语法”一样的下拉选项框
2. 给项目加上多语言方面的支持（所有UI方面都要有至少中英文支持，底部加上手动切换语言的功能，以及语言跟随系统的选项，参考主题方面的设置）
3. 写一小段XML的展示代码