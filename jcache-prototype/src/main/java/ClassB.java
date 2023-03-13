import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ClassB extends ClassA implements interfaceB{

  private int nr1 ;
  private  String st2;

  public ClassB() {
    super();
  }

  public ClassB(int nr1, String st2, int nrFromA, String strFromA) {
    super(nrFromA, strFromA);
    this.nr1 = nr1;
    this.st2 = st2;

  }

  public int getNr1() {
    return nr1;
  }

  public void setNr1(int nr1) {
    this.nr1 = nr1;
  }

  public String getSt2() {
    return st2;
  }

  public void setSt2(String st2) {
    this.st2 = st2;
  }

  @Override
  public void hello() {
    System.out.println("Hello form class B");
//    interfaceInt = 25;
  }
//
//  @Override
//  private void method1(){
//    System.out.println("I am method 1 form class B");
//  }
//
//  @Override
//  public  static void method2(){
//    System.out.println("I am method 2 from class B");
//  }

  public static void main(String[] args) {

//    ClassA obj1 =  new ClassB(1,  "Hello", 2 , "Word");
//    ClassC obj2 = new ClassB();
//    obj2 = (ClassB) obj2;
//
//    obj2.hello();
    LinkedHashMap<Integer, String>  map = new LinkedHashMap<>(Map.ofEntries(Map.entry(1, "hello"), Map.entry(3, "ciao") ));
    Set<Map.Entry<Integer,  String>>  entrySet=  map.entrySet();


  }


  @Override
  public void doSth() {
    System.out.println("Instance of B: I am doing sth");
  }
}

 interface interfaceB{
  public int interfaceInt = 23;
  public void doSth();
//  private void doSthElese();
}
