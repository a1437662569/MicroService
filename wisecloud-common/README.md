#非运行jar打包（否则编译不报错，运行提示无法找到程序包）
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
        <!--其他依赖、依赖声明等标签内容-->
        <build>
            <plugins>
                <!--打包：非运行jar包-->
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <configuration>
                        <classifier>exec</classifier>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </project>