import java.util.Arrays;

public class IntegerOptimizer {

  private Integer actualInteger;
  private String binaryInteger;
  private Integer[] encodedArray;

  public void setActualInteger(Integer integer){
    this.actualInteger = integer;
    this.binaryInteger = Integer.toBinaryString(actualInteger); //For our example i.e. '261', its binary = 100000101
  }

  public void printActualIntegerMetadata(){
    System.out.printf("The integer '%s' is expected to be stored as '%s'%n",actualInteger,binaryInteger);
    System.out.printf("Additional unused bits when stored as above : '%s'%n", 32-(binaryInteger.length()));
  }

  public void optimizeIntegerValue(){

    int binaryIntegerLength = binaryInteger.length();

    int leftOverBits = binaryIntegerLength % 7; //The bits that form the non-continuity Byte
    //In our example, 10 are the leftOverBits

    int continuityBytes = binaryIntegerLength / 7; //Number of continuity bytes
    //In our example, 0000101 form the continuity byte without MSB

    String temp = "1"; //MSB is '1' for continuity byte

    int count = 0;
    int encodedArraySize = continuityBytes + ( leftOverBits > 0 ? 1 : 0 ); //Total Bytes that will be used for storage

    encodedArray = new Integer[encodedArraySize];
    encodedArray[0] = Integer.parseInt((binaryInteger.substring(0, leftOverBits)), 2);
    //Taking the initial value of leftOverBits 10 in our case as is because appending 0 gives the same value.
    //Integer value of 5 will be equivalent to it.

    int encodedArrayIndex = 1;

    for(int i = leftOverBits; i < binaryIntegerLength; i++){
      temp = temp + binaryInteger.charAt(i); //Appending the MSB and forming the continuity byte
      count++;
      if(count == 7) {
        encodedArray[encodedArrayIndex++] = Integer.valueOf(temp, 2); //For our example, 10000101 forms the continuity byte and its equivalent integer 133 is stored
        count = 0;
        temp = "1";
      }
    }
    reverseArray(encodedArray); //Big-Endian Notation
    //The values after optimization [133, 2]
  }

  public void reverseArray(Integer[] arr){
    int length = arr.length;
    for(int i = 0; i < length/2; i++){
      Integer temp = arr[i];
      arr[i] = arr[length-i-1];
      arr[length-i-1] = temp;
    }
  }

  public void showEncodedInteger(){
    Arrays.stream(encodedArray).forEach(System.out::println);
    System.out.println(String.format("We've saved '%s' bytes !!", (4-encodedArray.length)));
  }

  public void decodeOptimizedInteger(){
    reverseArray(encodedArray); //Converting back from Big-Endian Notation
    //The array now is [2, 133]
    String decodedString = "";
    for(int i = 0; i < encodedArray.length; i++){
      if(i == 0){
        decodedString = decodedString + Integer.toBinaryString(encodedArray[i]); //Taking the non-continuity byte as is
      }
      else{
        decodedString = decodedString + Integer.toBinaryString(encodedArray[i]).substring(1); //Skipping the MSB value that is set to '1' in case of continuity bytes
      }
    }
    Integer decodedInteger = Integer.valueOf(decodedString, 2);
    System.out.println("The integer that you tried storing is : " + decodedInteger);
  }

}
