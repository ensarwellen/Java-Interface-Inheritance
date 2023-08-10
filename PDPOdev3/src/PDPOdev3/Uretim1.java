package PDPOdev3;

//IUretim arayuzunden olusturulmus Uretim1 sinifi
public class Uretim1 implements IUretim {
    public int Uret() {						
    	return (int) (Math.random() * 10) + 1;	//0-10 arasi deger dondurur.
    }
}
