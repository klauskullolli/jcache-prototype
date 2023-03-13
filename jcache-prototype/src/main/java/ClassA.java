public class ClassA extends ClassC {

    private  int nrFromA;

    private String strFromB;

    public  ClassA(){

    }

    public ClassA(int nrFromA, String strFromB) {
        this.nrFromA = nrFromA;
        this.strFromB = strFromB;
    }


    public int getNrFromA() {
        return nrFromA;
    }

    public void setNrFromA(int nrFromA) {
        this.nrFromA = nrFromA;
    }

    public String getStrFromB() {
        return strFromB;
    }

    public void setStrFromB(String strFromB) {
        this.strFromB = strFromB;
    }

    private void method1(){
        System.out.println("I am method 1 form class A");
    }

    public  static void method2(){
        System.out.println("I am method 2");
    }

    @Override
    public void hello() {
        System.out.println("Hello form class A");
    }
}
