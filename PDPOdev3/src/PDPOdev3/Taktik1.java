package PDPOdev3;

public class Taktik1 implements ITaktik {
	@Override			//ITaktik arayuzunden olusturulmus taktik sinifi
	public int Savas() {
        return (int) (Math.random() * 1001);	//0-1000 arasi deger dondurur.
    }
	
}
