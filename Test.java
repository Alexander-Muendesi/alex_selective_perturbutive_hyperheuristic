public class Test {
    public static void main(String[] args){
        int [][][]threeD = new int[3][3][3];
        int count = 1;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    threeD[i][j][k] = count++; 
                }
            }

        }

        int []oneD = new int[3*3*3];
        count = 1;
        int size = 3*3*3;
        for(int i=0; i < size; i++){
            oneD[i] = count++;
        }
        int xdim=3,ydim=3,zdim=3;

        System.out.println("Printing 3d array");

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    int target = i*(ydim*xdim)+j*zdim+k;
                    System.out.print(threeD[i][j][k] + " : " + oneD[target]);
                    System.out.print("\t"); 
                }
                System.out.println();
            }
            System.out.println();
        }

        

        int target = 2*(ydim*zdim)+1*zdim+0;
        System.out.println("Val3d: " + threeD[2][1][0] + " oneD: " + oneD[target]);

    }
}
