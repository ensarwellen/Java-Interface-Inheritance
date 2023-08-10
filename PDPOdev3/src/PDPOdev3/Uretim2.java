package PDPOdev3;

//IUretim arayuzunden olusturulmus Uretim2 sinifi
public class Uretim2 implements IUretim{
	public int Uret() {
		return (int) (Math.random() * 10) + 1;	//0-10 arasi deger dondurur.
    }
}
