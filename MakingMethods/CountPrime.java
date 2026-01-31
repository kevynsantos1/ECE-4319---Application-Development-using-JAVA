public class CountPrime {
    public static void main(String[] args) {
            int n =10;
            int ans = cntPrimesBasic(n);
            System.out.println(ans);
            System.out.println(countWithArray(n));
    }


    public static int cntPrimesBasic(int n) {
        int count = 1;
        System.out.println("prime number " + 2);

        for (int i = 2; i<n; i++){
            for (int j = 2; j<n; j++){
                if (i%j==0){
                    break;
                }
                if (j== i-1){
                    count++;
                    System.out.println("prime number " + i);
                }
            }
        }

        System.out.println("total prime numbers " + count);
        return count;
    }

    public static int countWithArray(int n){
        int count = 0;
        boolean[] NotPrime = new boolean[n];

        for (int i = 2; i<n; i++){
            if(!NotPrime[i]){
                count++;
                for(int j=2;i*j<n;j++){
                    NotPrime[i*j] = true;
                }
            }
        }
        return count;
    }

}

