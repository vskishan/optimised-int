public class Main {

  public static void main(String[] args) {

    IntegerOptimizer optimizer = new IntegerOptimizer();
    optimizer.setActualInteger(261);

    optimizer.printActualIntegerMetadata();

    optimizer.optimizeIntegerValue();
    optimizer.showEncodedInteger();

    optimizer.decodeOptimizedInteger();
  }
}