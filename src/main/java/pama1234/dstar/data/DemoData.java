package pama1234.dstar.data;

/**
 * 演示数据模型类
 * 用于表格和列表展示的示例数据
 */
public class DemoData {
    private String name;
    private String type;
    private int value;

    public DemoData(String name, String type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("DemoData{name='%s', type='%s', value=%d}",
                name, type, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DemoData demoData = (DemoData) obj;
        return value == demoData.value &&
                name.equals(demoData.name) &&
                type.equals(demoData.type);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + value;
        return result;
    }
}