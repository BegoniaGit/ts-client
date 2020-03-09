package site.yan.mysql.constant;

public enum MysqlPairType {
    SQL("sql"),
    RESULT_TYPE("result type"),
    RESULT_SIZE("result size"),
    MYSQL_SERVER_NAME("mysql name");

    private final String value;

    MysqlPairType(String s) {
        this.value = s;
    }

    public String text() {
        return this.value;
    }
}
