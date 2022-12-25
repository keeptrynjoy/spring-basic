package com.fastcampus.ch3.diCopy3;

import com.google.common.reflect.ClassPath;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component class Car{}
@Component class Truck extends Car{}
@Component class Engine {}

class AppContext{
    Map map; //객체 저장소

    AppContext(){
        map = new HashMap();
        doComponentScan();
    }

    private void doComponentScan() {
        // 1.패키지내의 클래스 목록을 가져온다.
        // 2.반복문으로 클래스를 하나씩 읽어와서 @Component이 붙어 있는지 확인
        // 3. @Component가 붙어 있으면 객체를 생성해서 Map에 저장

        ClassLoader classLoader=AppContext.class.getClassLoader();
        try {
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo>set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy3");

            for(ClassPath.ClassInfo classInfo : set){
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);

                if(component != null){
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id,clazz.newInstance());
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

    Object getBean(String key){
        return map.get(key);
    }
}

public class Main3 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car");
        Engine engine = (Engine) ac.getBean("engine") ;
        System.out.println("car = "+ car);
        System.out.println("Engine = "+ engine);
    }
}
