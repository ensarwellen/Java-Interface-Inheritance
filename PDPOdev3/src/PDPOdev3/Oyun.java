package PDPOdev3;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Oyun {
	public void baslat() {
		Scanner scanner = new Scanner(System.in);
		String nufusGirdisi;
		String[] nufusDizisi;
		//Bu do while dongusu input olarak 2'den daha az sayida input girilip girilmedigini kontrol eder.
		do {
		    System.out.println("Koloni nufuslarini birbirinden bosluklarla ayrilmis olarak girin: ");
		    nufusGirdisi = scanner.nextLine();		//Girilen girdileri bir stringe atar mesela 10 20 30 girildiyse "10 20 30" bir butun olarak alir.
		    nufusDizisi = nufusGirdisi.split(" ");	//Stringi bosluklardan ayirip bir diziye atar. Mesela [10][20][30] seklinde

		    if (nufusDizisi.length < 2) {			//Eger girilen input sayisi 2'den az ise
		        System.out.println("Savaş için en az 2 koloni populasyonu girilmelidir!");	//Hata verir
		    }
		} while (nufusDizisi.length < 2);			//Girilen input sayisi 2'den az iken kullanicidan input almaya devam eder.
		
	    //girilen girdi sayisi uzunlugunda bir koloni dizisi olusturuluyor. Aktif koloniler dizisi de olmemis kolonileri temsil ediyor.
	    Koloni[] koloniler = new Koloni[nufusDizisi.length];
	    List<Koloni> aktifKoloniler = new ArrayList<>();
	    //Burada nufus dizisi eleman sayisi kadar bir for dongusu olusturuldu. 
	    for (int i = 0; i < nufusDizisi.length; i++) {
	        int nufus = Integer.parseInt(nufusDizisi[i]);    //Her bir eleman bir nufus oldugu icin nufus degiskenine atandi.	       
	        int yemekAdedi = nufus * nufus;					//Nufusun karesi her koloninin yemek adedi olarak ayarlandi.
	        
	        IUretim uretim;					//Burada IUretim arayüzü kullaniliyor.
	        if (i % 2 == 0) {
	            uretim = new Uretim1();		//dizideki çift sayi indexe sahip koloniler uretim 2'yi kullaniyor
	        } else {
	            uretim = new Uretim2();		//dizideki tek sayi indexe sahip koloniler uretim 1'i kullaniyor
	        }

	        koloniler[i] = new Koloni(nufus, yemekAdedi, uretim);	//Koloni sinifindan yukaridaki bilgilerle koloni olusturuldu
	        aktifKoloniler.add(koloniler[i]);						//Her koloni aktif koloniler listesine atildi.
	    }
	    
	    System.out.println("Koloni   Populasyon   Yemek Stogu    Kazanma   Kaybetme");
	    //Nufus veya yemegi 0 veya negatif degerde olanlarin yerine --, digerlerine de kendi degerleri atandi. Yazdirma islemi de asagida printf ile yapildi. 
	    for (Koloni koloni : koloniler) {
	        String nufusString = (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) ? "--" : String.valueOf(koloni.getNufus());
	        String yemekAdediString = (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) ? "--" : String.valueOf(koloni.getYemekAdedi());
	        String kazanmaString = (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) ? "--" : String.valueOf(koloni.getKazanmaSayisi());
	        String kaybetmeString = (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) ? "--" : String.valueOf(koloni.getKaybetmeSayisi());
	        //printf fonksiyonu ile bicimlendirme yapildi. Ornegin %-9s sola dayali 9 karakter yer ayrilip string bir ifade yazilacagi anlamina geliyor.
	        System.out.printf("%-9s%-14s%-15s%-10s%s%n",
	                koloni.getIsim(), nufusString, yemekAdediString,	
	                kazanmaString, kaybetmeString);
	    }

	    System.out.println("\nOyun basliyor!");
	    //ITaktik arayuzunden 2 farklı taktikten degiskenler olusturuldu.
	    ITaktik taktik = new Taktik1();
	    ITaktik taktik2 = new Taktik2();
	    int turSayisi = 1;		//Tur sayisini tutacak degisken
	    
	    Random random = new Random();
	    //Yasayan koloni sayisi 1'den fazlayken oyunu devam ettirecek olan while dongusu
	    while (aktifKoloniler.size() > 1) {
	        System.out.println("\nTur Sayisi: " + turSayisi);
	        
	        for (int i = 0; i < aktifKoloniler.size() - 1; i++) {     //Burasi saldiran koloniyi belirliyor. Dizideki son eleman haric her eleman sirasiyla saldiran oluyor.
	            Koloni saldiranKoloni = aktifKoloniler.get(i);		  //Saldiran koloni belirlendi.
	            
	            for (int j = i + 1; j < aktifKoloniler.size(); j++) {  //Burasi hedef koloniyi belirliyor. Dizideki ilk eleman haric her eleman sirasiyla hedef oluyor.            	
	                Koloni hedefKoloni = aktifKoloniler.get(j);	       //Hedef koloni belirlendi                 
	                int saldiranSayi = taktik.Savas();				   //Saldiran koloninin kullanacagi sayi arayuzden cekildi.
	                int hedefSayi = taktik2.Savas();				   //Hedef koloninin kullanacagi sayi arayuzden cekildi.
	                if (saldiranSayi > hedefSayi) {										//Saldiran kazandiysa
                        hedefKoloni.kaybetti(hedefSayi, saldiranSayi, saldiranKoloni);  //hedef koloni kaybetti fonksiyonu ile cagirilir. Saldiran koloni de parametre olarak gonderilir.                        
                    } else if (saldiranSayi < hedefSayi) {								//Hedef kazandiysa
                        saldiranKoloni.kaybetti(saldiranSayi, hedefSayi, hedefKoloni);  //saldiran koloni kaybetti fonksiyonu ile cagirilir. Hedef koloni de parametre olarak gonderilir.                    
                    } else {
                        // Esit sayi cekildi
                        if (saldiranKoloni.getNufus() > hedefKoloni.getNufus()) {		//Saldiranin nufusu buyuk mu diye kontrol edilir
                            hedefKoloni.kaybetti(hedefSayi, saldiranSayi, saldiranKoloni);	//Saldiranin nufusu buyukse hedef kaybetti fonksiyonuna gonderilir.
                        } else if (saldiranKoloni.getNufus() < hedefKoloni.getNufus()) {	//Hedefin nufusu buyukse
                            saldiranKoloni.kaybetti(saldiranSayi, hedefSayi, hedefKoloni);	//Saldiran koloni kaybetti fonksiyonuna gonderilir.
                        } else {
                            // Nüfuslar da eşit, rastgele biri kazanacak
                            int randomIndex = random.nextInt(2);						//Bu satirda 0-1 degerinden rastgele bir deger cekilir
                            Koloni kazananKoloni = (randomIndex == 0) ? saldiranKoloni : hedefKoloni;	//Cekilen deger 0 ise kazananKoloni = saldiranKoloni olur. 1 ise kazananKoloni = hedefKoloni olur.
                            Koloni kaybedenKoloni = (randomIndex == 0) ? hedefKoloni : saldiranKoloni;	//Cekilen deger 0 ise kaybedenKoloni = hedefKoloni olur. 1 ise kaybedenKoloni = saldiranKoloni olur.
                            kaybedenKoloni.kaybetti(saldiranSayi, hedefSayi, kazananKoloni);	//Kaybeden koloni icin kaybetti fonksiyonu cagirilir.
                        }
                    }
	                
	            }
	        }

	        System.out.println("Koloni   Populasyon   Yemek Stogu    Kazanma   Kaybetme");

	        for (Koloni koloni : koloniler) {
	        	if (koloni.getNufus() > 0 && koloni.getYemekAdedi() > 0) {
	                // Yasayan koloniler icin nufus ve yemek adedi guncellemesi
	                int yeniNufus = (int) (koloni.getNufus() * 1.2);   //istenildigi gibi yeni nufus hesabi icin nufusun %20'si alinir.
	                int yeniYemekAdedi = koloni.getNufus() * 2;			//istenildigi gibi yeni yemek adedi icin nufusun 2 kati hesaplanir.
	                int yemek = koloni.getYemekAdedi();					//koloninin su anki yemek adedi cagirilir
	                int uretilenYemek = koloni.getUretim().Uret();      //Koloninin uretme fonksiyonu cagiriliyor. Bu yukarda rastgele arayuz ile basta koloniye atanmisti.
	                koloni.setYemekAdedi(yemek - yeniYemekAdedi);		//Koloninin yemek degerinde nufusun 2 kati kadar azalma meydana geliyor.
	                koloni.setYemekAdedi(yemek + uretilenYemek);	//Koloninin yemek degerinde uretilen sayi kadar artma meydana geliyor.
	                koloni.setNufus(yeniNufus);							//Koloninin yeni nufusu ataniyor.
	                
	            }
	        	//Nufus veya yemegi 0 veya negatif degerde olanlarin yerine --, digerlerine de kendi degerleri atandi. Yazdirma islemi de asagida printf ile yapildi.
	            String nufusString = (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) ? "--" : String.valueOf(koloni.getNufus());
	            String yemekAdediString = (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) ? "--" : String.valueOf(koloni.getYemekAdedi());
	            String kazanmaString = (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) ? "--" : String.valueOf(koloni.getKazanmaSayisi());
	            String kaybetmeString = (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) ? "--" : String.valueOf(koloni.getKaybetmeSayisi());
	            //Yazdirma islemi yapiliyor.
	            System.out.printf("%-9s%-14s%-15s%-10s%s%n",
	                    koloni.getIsim(), nufusString, yemekAdediString,
	                    kazanmaString, kaybetmeString);
	            
	            //Eger koloninin nufus veya yemek degeri 0 veya negatif degerde ise o koloni aktif koloni olmaktan cikariliyor.
	            if (koloni.getNufus() <= 0 || koloni.getYemekAdedi() <= 0) {
	                aktifKoloniler.remove(koloni);	                
	            }
	        }
	        System.out.println();
            System.out.println("-------------------------------------------------");
	        turSayisi++;  //tur sonunda tur sayisi artiriliyor.
	    }
	    	    

	    scanner.close();	//Scanner nesnesinin kullandigi kaynaklari kapatiyor ve program saglikli sekilde sonlaniyor. 
	}
}
