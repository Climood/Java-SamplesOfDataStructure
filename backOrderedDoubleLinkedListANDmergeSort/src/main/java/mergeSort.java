

public class mergeSort {
    private static int[] bufferArray = null;

    //--------------------------------------------------------------------------------------------
    public static void mergeSort(int targetArray[]){
        bufferArray=new int[targetArray.length];
        recMergeSorting(targetArray,0,bufferArray.length-1);
    }
    //--------------------------------------------------------------------------------------------
    static void recMergeSorting(int [] targetArray, int beginIndex, int lastIndex){
        if(beginIndex == lastIndex){
            return;
        }else {
            int middle = (beginIndex+lastIndex) / 2;
            recMergeSorting(targetArray, beginIndex, middle);
            recMergeSorting(targetArray, middle+1, lastIndex);
            merge(targetArray,beginIndex,middle+1, lastIndex);
        }
    }

    //--------------------------------------------------------------------------------------------
    static void merge(int[] targetArray,int leftIndex,int rightIndex, int lastIndex){
        int addIndex=0;
        int middle=rightIndex-1;
        int beginIndex=leftIndex;
        int lengthOfElements=lastIndex-beginIndex+1;

        while(leftIndex <= middle && rightIndex<=lastIndex ){
            if(targetArray[leftIndex] <= targetArray[rightIndex]){
                bufferArray[addIndex++] = targetArray[leftIndex++];
            }else{
                bufferArray[addIndex++] = targetArray[rightIndex++];
            }
        }

        while(leftIndex <= middle){
            bufferArray[addIndex++] = targetArray[leftIndex++];
        }

        while(rightIndex<=lastIndex){
            bufferArray[addIndex++] = targetArray[rightIndex++];
        }
        for(addIndex=0; addIndex<lengthOfElements; addIndex++){
            targetArray[beginIndex+addIndex]=bufferArray[addIndex];
        }
    }
}
