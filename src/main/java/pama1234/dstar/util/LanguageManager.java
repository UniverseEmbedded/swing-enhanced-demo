package pama1234.dstar.util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 语言管理器
 * 负责管理应用程序的国际化和语言切换
 */
public class LanguageManager {
    private static LanguageManager instance;
    private ResourceBundle messages;
    private Locale currentLocale;
    private boolean followSystemLocale;

    private List<LanguageChangeListener> listeners;

    private LanguageManager() {
        listeners = new ArrayList<>();
        // 默认跟随系统语言
        setFollowSystemLocale(true);
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    /**
     * 初始化语言管理器，加载初始语言包
     */
    public void initialize() {
        // 初始加载将在构造函数或 setFollowSystemLocale 中完成
        System.out.println("语言系统初始化成功，当前语言: " + currentLocale.getDisplayName());
    }

    /**
     * 根据键获取本地化字符串
     *
     * @param key 字符串键
     * @return 本地化字符串
     */
    public String getString(String key) {
        try {
            return messages.getString(key);
        } catch (java.util.MissingResourceException e) {
            System.err.println("缺失资源键: " + key + " 在语言: " + currentLocale);
            return "MISSING_KEY_" + key;
        }
    }

    /**
     * 设置当前语言
     *
     * @param locale 要设置的Locale对象
     */
    public void setLocale(Locale locale) {
        if (!locale.equals(this.currentLocale)) {
            Locale oldLocale = this.currentLocale;
            this.currentLocale = locale;

            System.out.println("=== 语言切换调试信息 ===");
            System.out.println("从 Locale: " + (oldLocale != null ? oldLocale : "null"));
            System.out.println("到 Locale: " + locale);
            System.out.println("Locale.toString(): " + locale.toString());
            System.out.println("当前默认 Locale: " + Locale.getDefault());

            try {
                // 重新加载资源包
                messages = ResourceBundle.getBundle("messages", currentLocale);

                System.out.println("资源包加载成功:");
                System.out.println("  Bundle 名称: " + messages.getBaseBundleName());
                System.out.println("  Bundle Locale: " + messages.getLocale());
                System.out.println("  Bundle 类: " + messages.getClass().getSimpleName());

                // 测试几个关键键值
                System.out.println("测试键值:");
                try {
                    System.out.println("  app.title = " + messages.getString("app.title"));
                    System.out.println("  control.language.english = " + messages.getString("control.language.english"));
                    System.out.println("  panel.functions = " + messages.getString("panel.functions"));
                } catch (Exception e) {
                    System.err.println("  获取测试键值失败: " + e.getMessage());
                }

                // 列出资源包中的所有键（调试用）
                System.out.println("资源包包含的键数量: " + messages.keySet().size());

            } catch (Exception e) {
                System.err.println("资源包加载失败: " + e.getMessage());
                e.printStackTrace();
            }

            notifyListeners();
            System.out.println("=== 语言切换调试信息结束 ===");
            System.out.println("语言切换成功: " + currentLocale.getDisplayName());
        }
    }

    /**
     * 切换是否跟随系统语言
     *
     * @param follow true表示跟随系统，false表示手动设置
     */
    public void setFollowSystemLocale(boolean follow) {
        this.followSystemLocale = follow;
        if (follow) {
            setLocale(Locale.getDefault());
        }
        // 如果设置为不跟随系统，则保持当前手动设置的语言不变
    }

    /**
     * 获取当前Locale
     *
     * @return 当前Locale对象
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * 判断是否跟随系统语言
     *
     * @return true如果跟随系统语言，否则false
     */
    public boolean isFollowSystemLocale() {
        return followSystemLocale;
    }

    /**
     * 添加语言变化监听器
     *
     * @param listener 监听器实例
     */
    public void addLanguageChangeListener(LanguageChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * 移除语言变化监听器
     *
     * @param listener 监听器实例
     */
    public void removeLanguageChangeListener(LanguageChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * 通知所有监听器语言已变化
     */
    private void notifyListeners() {
        for (LanguageChangeListener listener : listeners) {
            try {
                listener.onLanguageChanged(currentLocale);
            } catch (Exception e) {
                System.err.println("语言变化监听器执行失败: " + e.getMessage());
            }
        }
    }

    /**
     * 语言变化监听器接口
     */
    @FunctionalInterface
    public interface LanguageChangeListener {
        void onLanguageChanged(Locale newLocale);
    }
}