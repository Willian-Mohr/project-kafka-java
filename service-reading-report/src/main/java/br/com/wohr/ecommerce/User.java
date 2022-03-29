package br.com.wohr.ecommerce;

import lombok.Data;

@Data
public class User {

    private final String uuid;

    public String getReportPath() {
        return "target/" + uuid + "-report.txt";
    }
}
