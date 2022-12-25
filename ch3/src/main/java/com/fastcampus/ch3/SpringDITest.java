package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component //<bean id = "engine" class="com.fastcampus.ch3.Engine"/>
class Engine{}
@Component class SuperEngine extends Engine{}
@Component class TurboEngine extends Engine{}
@Component class Door{}

@Component
class Car{
    @Value("red") String color;
    @Value("100") int oil;
    @Autowired // byType -> byName
    Engine engine;
    // Autowired 의 특성 - 타입으로 먼저 검색, 여러개면 이름으로 검색(@Qualifier)
    // engine, superEngine, turboEngine
    // @Autowired
    // @Qualifier("superEngine") // 타입에 이름이 여러개일때 이름 지정, @Autowired 와 같이 사용
    // @Resource // byName, 이름만 검색
    @Autowired Door[] doors;

    public Car() {}

    public Car(String color, int oil, Engine engine, Door[] doors) {
        this.color = color;
        this.oil = oil;
        this.engine = engine;
        this.doors = doors;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", oil=" + oil +
                ", engine=" + engine +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }
}


public class SpringDITest {
    public static void main(String[] args) {
        // config.xml에 설정해둔 클래스 정보를 읽어서 저장소 만드는데, 만든 저장소안에 map 형태로 객체가 만들어짐.
        ApplicationContext ac = new GenericXmlApplicationContext("config1.xml");
        // getBean을 사용해 가져다씀.
//        Car car = (Car) ac.getBean("car");// byName
        Car car = (Car) ac.getBean("car", Car.class);// byName, 타입을 줘서 형변환을 생략
//        Car car2 = ac.getBean(Car.class);// byType
//        Engine engine = (Engine) ac.getBean("engine"); // byname
//        Engine engine = (Engine) ac.getBean(Engine.class); // bytype - 같은 타입이 3개라서 에러 발생
//        Engine engine = (Engine) ac.getBean("superEngine"); // byname
//        Door door = (Door) ac.getBean("door");

        //싱글톤으로 서로 같은 객체를 반환
        //bean scope 를 prototype 으로 바꿀 경우 클래스의 객체를 여러개로 생성 가능
//        System.out.println("car = " + car);
//        System.out.println("car2 = " + car2);
//        System.out.println("engine = " + engine);
//        System.out.println("door = "+ door);

//        car.setColor("red");
//        car.setOil(100);
//        car.setEngine(engine);
//        //getBeen이 반환하는게 Object 타입이라 형변환이 필요
//        car.setDoors(new Door[]{ac.getBean("door", Door.class), (Door) ac.getBean("door")});

        // setter 호출 없이 config.xml 에 property 태크를 사용하여 객체의 값 세팅함.
        System.out.println("car = " + car);
//        System.out.println("engine = " + engine);
    }
}
