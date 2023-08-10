package PDPOdev3;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Koloni {
	private String isim;
    private int nufus;
    private int yemekAdedi;
    private int kazanmaSayisi;
    private int kaybetmeSayisi;
    private IUretim uretim;
    private static List<String> kullanilanSemboller = new ArrayList<>();  //Kolonileri temsilen bir sembol dizisi

    public Koloni(int nufus, int yemekAdedi,IUretim uretim) {
        this.isim = koloniIsimUret();	//Koloni isim degeri koloniIsimUret fonksiyonundan rastgele bir deger ile belirleniyor.
        this.nufus = nufus;
        this.yemekAdedi = yemekAdedi;
        this.uretim = uretim;
        this.kazanmaSayisi = 0;
        this.kaybetmeSayisi = 0;
    }
    private String koloniIsimUret() {
    	String[] semboller = {"®","©","*","@","$","§","¥","ß","£","Æ","¶","Ø","¿","?","-","/","+","&","%","#"};	//sembol dizisi olusturdum
        Random random = new Random();								//Asagida rastgele bir sembol indexi secilmesi icin random nesnesi olusturdum.
        int index;
        do {
            index = random.nextInt(semboller.length);          		//semboller listesinin uzunluguna gore rastgele bir index no dondurur.   
        } while (kullanilanSemboller.contains(semboller[index]));	//secilen sembol kullanilan semboller listesinde ise dongu bir daha doner. Kullanilan sembol degilse donguden cikar.

        kullanilanSemboller.add(semboller[index]);					//secilen sembol kullanilan semboller dizisine eklenir.
        return semboller[index];									//sembol secilir ve secilen sembol dondurulur.
    }
    
    //GET-SET Fonksiyonlari ile degerlere ulasip uzerlerinde rahatca oynama yapabiliyoruz. Get mevcut degeri getirirken Set mevcut degeri gunceller.
    
    public IUretim getUretim() {
        return uretim;
    }

    public void setUretim(IUretim uretim) {
        this.uretim = uretim;
    }
    public int getKazanmaSayisi() {
        return kazanmaSayisi;
    }

    public int getKaybetmeSayisi() {
        return kaybetmeSayisi;
    }
    
    public String getIsim() {
        return isim;
    }

    public int getNufus() {
        return nufus;
    }

    public int getYemekAdedi() {
        return yemekAdedi;
    }
    public void setNufus(int nufus) {
        this.nufus = nufus;
    }

    public void setYemekAdedi(int yemekAdedi) {
        this.yemekAdedi = yemekAdedi;
    }
    //Kazanan koloninin yonlendirildigi fonksiyon
    public void kazandi(double transferMiktari) {
        yemekAdedi += (int) transferMiktari;	//kazananin yemek miktari kaybedenin kaybetme orani kadar artiriliyor.     
        kazanmaSayisi++;   						//kazanma sayisi 1 artiyor.
    }
    public void kaybetti(int saldiranSayi, int hedefSayi, Koloni hedefKoloni) {        
        double azalmaMiktari = Math.abs(saldiranSayi - hedefSayi) / 10.0;  //1000'e gore yuzde kac fark oldugu bulunur. Mesela 800 700 degeri icin (800-700)/10=10
        double azalmaOrani = azalmaMiktari / 100.0;							//10 degeri %10 hale getiriliyor.
        double azalma = getNufus() * azalmaOrani;							//nufus degeri %10 ile carpiliyor. Bu deger nufustan cikartilacak. Burası kaybedilen nufus degeri yani
        double azalmaYemek = getYemekAdedi() * azalmaOrani;        			//yemek adedi %10 ile carpiliyor. Bu deger yemek adedinden cikartilacak. Kaybedilen yemek degeri       
        double yeniNufus = getNufus() - azalma;								//nufus degerinden kaybedilen nufus degeri cikartiliyor.
        setNufus((int)yeniNufus);											//nufus degeri yeni deger ile guncelleniyor.
        double yeniYemek = getYemekAdedi() - azalmaYemek;					//yemek adedinden kaybedilen yemek degeri cikartiliyor.
        setYemekAdedi((int)yeniYemek);										//yemek adedi degeri yeni deger ile guncelleniyor.
        hedefKoloni.kazandi(azalmaYemek);       							//kaybedenin karsisindaki koloni kazandi fonksiyonuna kaybetme orani ile gonderiliyor.
        kaybetmeSayisi++;													//kaybetme sayisi 1 artiyor.
    }
    

}
