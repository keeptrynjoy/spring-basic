package com.fastcampus.ch3.diMyTest1;

import com.google.common.reflect.ClassPath;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component class Company{};
@Component class People{};
@Component class Samsung extends Company{};
class AppContexts {
    Map map; //객체 저장소

    AppContexts() {
        map = new HashMap();
        doComponentScan();
    }

    private void doComponentScan() {
        //java.lang 클래스
        ClassLoader classLoader = AppContexts.class.getClassLoader();

        try {
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diMyTest1");

            for (ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();

                Component component = (Component) clazz.getAnnotation(Component.class);

                if (component != null) {
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                    ;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    Object getBean(String key) {
        return map.get(key);
    }
}

public class MyTestMain1 {
    public static void main(String[] args) throws Exception {
        AppContexts ac = new AppContexts();
        Company company = (Company) ac.getBean("samsung");
        People people = (People) ac.getBean("people");
        System.out.println("people = " + people);
        System.out.println("company = " + company);
    }
}
