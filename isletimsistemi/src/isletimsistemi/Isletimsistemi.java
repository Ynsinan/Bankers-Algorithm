
package isletimsistemi;
import java.util.Scanner;

public class Isletimsistemi {

    int n = 5; // toplam islem sayısı (P0,P1,P2,P3,P4)
    int m = 3; // toplam kaynak sayısı(A/B/C)
    int ihtiyac[][] = new int[n][m];
    int[][] max = new int[n][m]; // Max matrisi
    int[][] atanan;
    int[] kullanilabilir = new int[3]; //Kullanıcı tarafından girilen kaynakların toplam sayısı. Örneğin A=10,B=5,C=7
    int[] kalanKaynak = new int[3];//Kullanıcı tarafından girilen kaynakların hesabı yapılarak. Kullanılabilir kaynakların hesaplanıp atandığı değişken.
    int güvenliDizi[] = new int[n];
    Scanner scn = new Scanner(System.in);
    
    void degerleriBelirle() {
        // Burada P0, P1, P2, P3, P4 işlem isimleri
        // Atanan değerlerin bulunduğu matris  
        atanan = new int[][]{{0, 1, 0}, //P0    
                             {2, 0, 0}, //P1 
                             {3, 0, 2}, //P2 
                             {2, 1, 1}, //P3 
                             {0, 0, 2}};//P4 
    }

    void guvenlimi() {
        int sayac = 0;

        //gezilen ismindeki arraye gezilen islemleri booleanla gezilip gezilmediğini anlıyoruz
        boolean gezilen[] = new boolean[n];
        for (int i = 0; i < n; i++) {
            gezilen[i] = false;
        }

        //gecici isminde bir array kullanılabilir kaynakların kopyasını saklıyor
        int gecici[] = new int[m];
        for (int i = 0; i < m; i++) {
            gecici[i] = kalanKaynak[i];
        }

        while (sayac < n) {
            boolean flag = false;
            for (int i = 0; i < n; i++) {
                if (gezilen[i] == false) {
                    int j;
                    for (j = 0; j < m; j++) {
                        if (ihtiyac[i][j] > gecici[j]) {
                            break;
                        }
                    }
                    if (j == m) {
                        güvenliDizi[sayac++] = i;
                        gezilen[i] = true;
                        flag = true;

                        for (j = 0; j < m; j++) {
                            gecici[j] = gecici[j] + atanan[i][j];
                        }
                    }
                }
            }
            if (flag == false) {
                break;
            }
        }
        if (sayac < n) {
            System.out.println("Sistem Güvenli Değil!!");
        } else {

            System.out.println("\nGüvenli sıra aşağıdaki gibi olacak");
            for (int i = 0; i < n; i++) {
                System.out.print("P" + güvenliDizi[i]);
                if (i != n - 1) {
                    System.out.print(" -> ");
                    
                }
            }
            System.out.println(" ");
        }
    }

    void ihtiyaciHesapla() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ihtiyac[i][j] = max[i][j] - atanan[i][j];
            }
        }
    }
    void ekran(){
        int sayi=0;
        System.out.println("    ATANAN |   MAX   | İHTİYAÇ | KULLANILABİLİR");
        System.out.println("    A B C  |  A B C  |  A B C  |  A B C");
        System.out.println("    -------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.print("P"+sayi+"  ");
            for (int j = 0; j < m; j++) {
                System.out.print(atanan[i][j] + " ");
            }
            System.out.print(" |  ");
            for (int j = 0; j < m; j++) {
                System.out.print(max[i][j] + " ");
            }
            System.out.print(" |  ");
            for (int j = 0; j < m; j++) {
                System.out.print(ihtiyac[i][j] + " ");
            }
            System.out.print(" |  ");
            if(i==0){
                for (int j = 0; j < 3; j++) {
                    System.out.print(kalanKaynak[j] + " ");
                }
            }
            System.out.println();
            sayi++;
        }
    }
    
    void kullanilabilirAl(){
        System.out.println("Sırasıyla kullanılabilir A, B ve C kaynaklarını girin.");
        System.out.print("A kaynak degerini gir : ");
        kullanilabilir[0] = scn.nextInt();
        System.out.print("B kaynak degerini gir : ");
        kullanilabilir[1] = scn.nextInt();
        System.out.print("C kaynak degerini gir : ");
        kullanilabilir[2] = scn.nextInt();
    } 
    
    void maxMatrisAl(){
        int sayac=0;
        System.out.println("Max matrisini giriniz...");
        for (int i = 0; i < n; i++) {
            System.out.println("P"+sayac);
            for (int j = 0; j < m; j++) {
                System.out.print(" P[" +i+ "][" +j+ "] : ");
                max[i][j] = scn.nextInt();
            }
            sayac++;
        }
    }
    
    void kullanilabilirKaynakBul(){
        int aToplam=0,bToplam=0,cToplam=0;
        for (int i = 0; i < n; i++) {
            aToplam += atanan[i][0];
        }
        for (int i = 0; i < n; i++) {
            bToplam += atanan[i][1];
        }
        for (int i = 0; i < n; i++) {
            cToplam += atanan[i][2];
        }
        kalanKaynak[0] = kullanilabilir[0]-aToplam;
        kalanKaynak[1] = kullanilabilir[1]-bToplam;
        kalanKaynak[2] = kullanilabilir[2]-cToplam;
}

    public static void main(String[] args) {
        Isletimsistemi banker = new Isletimsistemi();
        
        banker.kullanilabilirAl();
        banker.maxMatrisAl();
        banker.degerleriBelirle();
        banker.kullanilabilirKaynakBul();
        //İhtiyaç matrisini hesapla  
        banker.ihtiyaciHesapla();
        banker.ekran();
        //Sistem güvenli durumda mı değil mi kontrol et 
        banker.guvenlimi();
    }
}